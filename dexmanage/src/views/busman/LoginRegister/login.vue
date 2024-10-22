<template>
  <div>
    <div class="header">
      <div class="inner-header">
        <div class="from-mode">
        <div class="from-title">DEX商家后台管理中心</div>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="80px" class="demo-ruleForm">
          <el-form-item label="账号" prop="account">
            <el-input type="text" v-model="ruleForm.account" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password" autocomplete="off" style="height: 40px"></el-input>
          </el-form-item>
          <el-button type="text" @click="dialogShow" v-if="!pvConfirm">点击进行图片验证</el-button>
          <span v-else style="color: green;">图片验证成功</span>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
            <span style="margin-left: 20px;cursor: pointer" @click="GoRegister">前往注册</span>
          </el-form-item>
        </el-form>
        </div>
      </div>

      <div>
        <svg class="waves"
             viewBox="0 24 150 28" preserveAspectRatio="none" shape-rendering="auto">
          <defs>
            <path id="gentle-wave"
                  d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z" />
          </defs>
          <g class="parallax">
            <use xlink:href="#gentle-wave" x="48" y="0" fill="#F56C6C" />
            <use xlink:href="#gentle-wave" x="48" y="3" fill="#67C23A" />
            <use xlink:href="#gentle-wave" x="48" y="5" fill="#E6A23C" />
            <use xlink:href="#gentle-wave" x="48" y="7" fill="#409EFF" />
          </g>
        </svg>
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
import pictureVerification from "../../../components/busman/pictureVerification";

export default {
  name: "login",
  components:{
    'pictureVerification':pictureVerification
  },
  data(){
    return {
      ruleForm: {
        account: '',
        password: '',
      },
      dialogVisible:false,
      pvName:'',
      pvImage:'',
      pvConfirm:false,
      rules: {
        account: [
          { required: true, message: '请输入您的账号', trigger: 'blur' },
          { min: 3, max: 18, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入您的密码', trigger: 'blur' },
          { min: 3, max: 18, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ]
      }
    };
  },
  methods:{
    async dialogShow(){
      this.dialogVisible=true
      const res=await this.axios.post('pictureVerification/pictureVerification')
      console.log(res);
      this.pvName=res.data.message;
      this.pvImage=res.data.object;
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.pvConfirm){
            this.axios.post('busman/busmanLogin',this.$qs.stringify({
              account:this.ruleForm.account,
              password:this.ruleForm.password
            })).then((res)=>{
              if (res.data.code === 200){
                this.$message.success("登陆成功");
                this.$store.commit('updateLogin',true);
                this.$store.commit('updateBusmanInfo',res.data.object);
                this.$store.commit('updateToken',res.data.message)
                this.$router.push({
                  name:'busIndex'
                })
              }else if (res.data.code === 500){
                this.$message.error("你的密码输入错误")
              }else if (res.data.code === 501){
                this.$message.info(res.data.message)
              }else {
                this.GoRegister();
                this.$message.info("你输入的账号尚未注册")
              }
            })
          }else {
            this.$message.error("你还未通过图片验证")
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
      const res=await this.axios.post('pictureVerification/getUserConfirmPV',this.$qs.stringify({
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
    GoRegister(){
      this.$router.push({
        name:'register'
      })
    },
  }
}
</script>

<style scoped>
h1 {
  font-family: 'Lato', sans-serif;
  font-weight:300;
  letter-spacing: 2px;
  font-size:48px;
}
p {
  font-family: 'Lato', sans-serif;
  letter-spacing: 1px;
  font-size:14px;
  color: #333333;
}

.header {
  position:absolute;
  text-align:center;
  background: linear-gradient(60deg, rgba(84,58,183,1) 0%, rgba(0,172,193,1) 100%);
  height: 810px;
  width: 100%;
}


.inner-header {
  position: relative;
  margin-top: 160px;
  display: flex;
  justify-content: center;
}

.from-title{
  color: black;
  font-size: 30px;
  margin-bottom: 20px;
}

.from-mode{
  height:270px;
  width:450px;
  padding: 20px;
  background-color: white;
  border-radius: 8px;
}



.waves {
  position:relative;
  width: 100%;
  height:15vh;
  margin-bottom:-7px; /*Fix for safari gap*/
  min-height:282px;
  max-height:300px;
  margin-top: 60px;
}



/* Animation */

.parallax > use {
  animation: move-forever 25s cubic-bezier(.55,.5,.45,.5)     infinite;
}
.parallax > use:nth-child(1) {
  animation-delay: -2s;
  animation-duration: 7s;
}
.parallax > use:nth-child(2) {
  animation-delay: -3s;
  animation-duration: 10s;
}
.parallax > use:nth-child(3) {
  animation-delay: -4s;
  animation-duration: 13s;
}
.parallax > use:nth-child(4) {
  animation-delay: -5s;
  animation-duration: 20s;
}
@keyframes move-forever {
  0% {
    transform: translate3d(-90px,0,0);
  }
  100% {
    transform: translate3d(85px,0,0);
  }
}
/*Shrinking for mobile*/
@media (max-width: 768px) {
  .waves {
    height:40px;
    min-height:40px;
  }
  .content {
    height:30vh;
  }
  h1 {
    font-size:24px;
  }
}
</style>