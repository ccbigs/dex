<template>
  <div>
    <el-carousel :height="this.windowsHeight+'px'" direction="vertical" :autoplay="true">
      <el-carousel-item v-for="(o,i) in back_img">
        <img :src="o.url" alt="">
      </el-carousel-item>
    </el-carousel>
    <div class="login">
      <div class="login-title"><div>DEX商城系统管理后台登录</div></div>
      <div class="login-from">
        <div class="from-son">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="管理员账号" prop="account">
              <el-input v-model="ruleForm.account"></el-input>
            </el-form-item>
            <el-form-item label="管理员密码" prop="password">
              <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
            </el-form-item>
            <div style="display: flex;justify-content: center;margin-bottom: 5px;">
              <div>
                <el-button style="padding-top: 0px;" type="text" @click="dialogShow" v-if="!pvConfirm">点击进行图片验证</el-button>
                <span v-else style="color: green;">图片验证成功</span>
              </div>
            </div>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
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
  </div>
</template>

<script>
import pictureVerification from "../../components/dataPanelCom/pictureVerification";
export default {
  name: "login",
  components:{
    'pictureVerification':pictureVerification
  },
  data(){
    return{
      /*验证码图片*/
      dialogVisible:false,
      pvName:'',
      pvImage:'',
      pvConfirm:false,

      back_img:[
        {
          url:'/static/image/background1.jpg'
        },{
          url:'/static/image/background2.jpg'
        },{
          url:'/static/image/background3.jpg'
        }
      ],
      windowsHeight:'',
      ruleForm: {
        account: '',
        password:'',
      },
      rules: {
        account: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 50, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 50, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    this.getHight()
  },
  mounted() {
    this.$nextTick(()=>{
      window.addEventListener('resize', () => { //监听浏览器窗口大小改变
        //浏览器变化执行动作
        this.getHight()
      });
    })

  },
  methods:{
    getHight(){
      this.windowsHeight=document.documentElement.clientHeight;
      console.log(document.documentElement.clientHeight);
    },
    /*管理员点击登录*/
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.pvConfirm){
            this.axios.post('DexAdmin/adminLogin',this.$qs.stringify({
              account:this.ruleForm.account,
              password:this.ruleForm.password
            })).then((res)=>{
              if (res.data.code === 200){
                this.$message.success("登陆成功")
                this.$store.commit('updateLogin',true)
                this.$store.commit('updateAdminInfo',res.data.object)
                this.$store.commit('updateToken',res.data.message)
                this.$router.push({
                  name:'dataPanel'
                })
              }else if (res.data.code === 500){
                this.$message.error("您输入的密码有误")
              }else if (res.data.code === 401){
                this.$message.warning("该账号当前已被冻结,有需要请联系一级管理员")
              }else {
                this.$message.error("您输入的账号不存在")
              }
            })
          }else {
            this.$message.error("尚未进行图形验证")
          }
        } else {
          this.$message.error("尚未填写登录信息")
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    /*验证码图片显示*/
    async dialogShow(){
      this.dialogVisible=true
      const res=await this.axios.post('DexAdmin/pictureVerification')
      console.log(res);
      this.pvName=res.data.message;
      this.pvImage=res.data.object;
    },
    /**/
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    /*确认提交用户选择的验证图片*/
    async confirmSubmit(){
      let pv=JSON.stringify(this.$refs.pv.pvImage)
      const res=await this.axios.post('DexAdmin/getUserConfirmPV',this.$qs.stringify({
        pv:pv
      }))
      if (res.data.code === 200){
        this.$message.success("图片验证成功")
        this.pvConfirm=true;
        this.dialogVisible=false;
      }else {
        this.$message.error("验证失败,重新再来");
        this.dialogShow();
      }
    },
  }
}
</script>

<style scoped>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }

  img{
    width: 100%;
  }

  .login{
    position: fixed;
    top: 20%;
    left: 32%;
    z-index: 100;

    width: 40%;
    height: 50%;
    background-color: white;
    border-radius: 16px;

  }

  .login-title{
    font-size: 24px;
    display: flex;
    justify-content: center;
    margin-top: 5%;
    margin-bottom: 3%;
  }

  .login-from{
    margin-top: 6%;
    display: flex;
    justify-content: center;
  }

  .from-son{
    width: 500px;
  }
</style>