<template>
  <div>
    <login-header :name="headerName"></login-header>
    <div class="login-body">
      <div class="body-form">
        <div class="title">DEX账号登陆</div>
        <el-form :model="ruleForm"  :rules="rules" ref="ruleForm" label-width="60px" class="demo-ruleForm">
          <el-form-item label="账号" prop="account">
            <el-input v-model="ruleForm.account" placeholder="输入您绑定的手机号"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" style="margin-bottom: 8px;">
            <el-input type="password" v-model="ruleForm.password"></el-input>
          </el-form-item>
          <div class="pv">
            <div>
              <el-button type="text" @click="dialogShow" v-if="!pvConfirm">点击进行图片验证</el-button>
              <span v-else style="color: green;">图片验证成功</span>
            </div>
          </div>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">立即登录</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <login-foot></login-foot>

    <el-dialog
        title="提示"
        v-model="dialogVisible"
        width="30%"
        :visible.sync="dialogVisible"
        :before-close="handleClose" :append-to-body="true">
      <picture-verification :pv-name="pvName" :pv-image="pvImage" ref="pv"></picture-verification>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirmSubmit">确 定</el-button>
          </span>
      </template>
    </el-dialog>
    <div>
      <el-dialog
          title="注册提示"
          :visible.sync="NoRegister"
          width="30%"
          :before-close="handleClose">
        <span>系统显示您输入的用户尚未注册，现在是否立即前往注册</span>
        <span slot="footer" class="dialog-footer">
    <el-button @click="NoRegister = false">取 消</el-button>
    <el-button type="primary" @click="GoRegister">确 定</el-button>
      </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import shopLoginHeader from "../../common/dex-shop/shopLoginHeader";
import shopLoginFoot from "../../common/dex-shop/shopLoginFoot";
import pictureVerification from "../../components/dex-shop/pictureVerification";

export default {
  name: "index",
  data() {
    return {
      /*验证图片*/
      dialogVisible:false,
      pvName:'',
      pvImage:'',
      pvConfirm:false,
      /*是否展示注册框*/
      NoRegister:false,
      headerName:'登录',
      ruleForm: {
        account: '',
        password: '',
      },
      rules: {
        account: [
          { required: true, message: '请输入你的账号', trigger: 'blur' },
          { min: 3, max: 11, message: '账号有误', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入你的密码', trigger: 'blur' },
          { min: 3, max: 10, message: '密码有误', trigger: 'blur' }
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
       this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.pvConfirm) {
            this.axios({
              method: 'post',
              url: 'http://localhost:8681/dex/user/login',
              data: this.$qs.stringify({
                account: this.ruleForm.account,
                password: this.ruleForm.password
              })
            }).then((res) => {
              console.log(res)
              if (res.data.code === 200) {
                /*$store.getters.mutations.updateLogin(true)*/
                this.$store.commit('updateLogin', true)
                this.$store.commit('updateUserInfo', res.data.object);
                this.$store.commit('updateToken', res.data.message);
                this.LoginSuccess(res.data.object.nickname)
                this.getUserShopCart(res.data.object.id)
                this.$router.push({
                  name: 'home'
                })
              } else if (res.data.code === 500) {
                this.passwordFiled()
              } else if (res.data.code === 501) {
                this.$message.error("该账号已被封禁，解封或有问题请咨询DEX商城工作人员")
              } else {
                this.NoRegister = true;
              }
            })
          }else {
            this.$message.warning("你尚未通过图片验证")
          }

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    LoginSuccess(name) {
      this.$notify({
        title: '登录成功',
        message: '欢迎'+name+'的登录',
        type: 'success'
      });
    },
    passwordFiled() {
      this.$notify.error({
        title: '密码错误',
        message: '你好，尊敬的用户，你的密码有误',
      });
    },
    /*显示登陆验证信息*/
    async dialogShow(){
      this.dialogVisible=true
      const res=await this.axios.post('http://localhost:8681/dex/pictureVerification/pictureVerification')
      console.log(res);
      this.pvName=res.data.message;
      this.pvImage=res.data.object;
    },
    /*确认提交用户选择的验证图片*/
    async confirmSubmit(){
      let pv=JSON.stringify(this.$refs.pv.pvImage)
      const res=await this.axios.post('http://localhost:8681/dex/pictureVerification/getUserConfirmPV',this.$qs.stringify({
        pv:pv
      }))
      console.log("进入提交")
      if (res.data.code === 200){
        this.$message.success("图片验证成功")
        this.pvConfirm=true;
        this.dialogVisible=false;
      }else {
        this.$message.error("验证失败,重新再来");
        await this.dialogShow();
      }
    },
    /*关闭注册框*/
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    /*前往注册*/
    GoRegister(){
      this.NoRegister=false;
      this.$router.push({
        name:'userRegister'
      })
    },
    /*用户登陆成功，返回用户得购物车数据*/
    async getUserShopCart(userId){
     /* console.log("进入getUserShopCart")
      const cart=await this.axios.post('http://localhost:8680/dex/cart/getUserCartWare',this.$qs.stringify({
        user_id:userId
      }))
      this.$store.commit('updateCartList',cart.data.object)*/
      /*返回全部地址*/
      const address=await this.axios.post('http://localhost:8681/dex/address/getUserAddress',this.$qs.stringify({
        user_id:userId
      }))
      this.$store.commit('updateUserAddress',address.data.object)
    }
  },
  components:{
    'loginHeader':shopLoginHeader,
    'loginFoot':shopLoginFoot,
    'pictureVerification':pictureVerification
  }
}
</script>

<style scoped>
  .login-body{
    width: 100%;
    height: 690px;
    background-color: white;
  }
  .login-body{
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .body-form{
    width: 400px;
    height: 240px;
  }
  .title{
    font-size: 30px;
    margin-bottom: 20px;
    margin-left: 60px;
  }
  .pv{
    width: 400px;
    height: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>