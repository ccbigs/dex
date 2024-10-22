<template>
  <div style="padding-bottom: 100px">
    <!--页头信息-->
    <div class="head-title">账号与安全</div>
    <el-divider></el-divider>
    <!--具体内容-->
    <div class="content-body">
      <div style="font-size: 26px;margin-bottom: 10px">保障你的帐号、电话、地址安全</div>
    </div>
    <!--账号安全-->
    <div class="security-demo">
      <div class="demo-img">
        <img src="/static/images/security-account.png" alt="">
      </div>
      <div>
        <div class="demo-right">
          <div class="right-font">密码管理</div>
          <div><el-button type="primary" round  @click="dialogAlterPassword = true">更改</el-button></div>
        </div>
        <hr style="margin-left: 20px">
      </div>
    </div>

    <el-dialog title="修改密码" :visible.sync="dialogAlterPassword">
      <el-form :model="formPassword" :rules="Passrules" ref="ruleForm" label-width="100px">
        <el-form-item label="原密码" prop="oldPass">
          <el-input type="password" v-model="formPassword.oldPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPass">
          <el-input type="password" v-model="formPassword.newPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="formPassword.checkPass" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAlterPassword = false">取 消</el-button>
        <el-button type="primary" @click="submitPass('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>


    <!--身份证绑定-->
    <div class="security-demo">
      <div class="demo-img">
        <img src="/static/images/security-idcard.png" alt="">
      </div>
      <div>
        <div class="demo-right">
          <div class="right-font" v-if="userMessage.idcard === null" style="color: red">身份证尚未绑定</div>
          <div class="right-font" v-else>身份证绑定</div>
          <div><el-button type="primary" round @click="dialogBindIdCard = true">更改</el-button></div>
        </div>
        <hr style="margin-left: 20px;">
      </div>
    </div>

    <el-dialog title="身份证绑定" :visible.sync="dialogBindIdCard">
      <el-form :model="formIdCard" :rules="IdCard" ref="ruleForm" label-width="140px">
        <el-form-item label="输入姓名" prop="realname">
          <el-input type="text" v-model="formIdCard.realname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="输入身份证号码" prop="idcard">
          <el-input type="text" v-model="formIdCard.idcard" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogBindIdCard = false">取 消</el-button>
        <el-button type="primary" @click="submitIdCard('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>


    <!--手机号绑定-->
    <div class="security-demo">
      <div class="demo-img">
        <img src="/static/images/security-phone.png" alt="">
      </div>
      <div>
        <div class="demo-right">
          <div class="right-font">手机号绑定</div>
          <div><el-button type="primary" round @click="dialogBindPhone = true">更改</el-button></div>
        </div>
        <hr style="margin-left: 20px;">
      </div>
    </div>

    <el-dialog title="手机号绑定" :visible.sync="dialogBindPhone">
      <el-form :model="formPhone" :rules="Phone" ref="ruleForm" label-width="140px">
        <el-form-item label="输入手机号" prop="phone">
          <el-input type="text" v-model="formPhone.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogBindPhone = false">取 消</el-button>
        <el-button type="primary" @click="submitPhone('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>

    <!--邮件地址-->
    <div class="security-demo">
      <div class="demo-img">
        <img src="/static/images/security-postal.png" alt="">
      </div>
      <div>
        <div class="demo-right">
          <div class="right-font" v-if="userMessage.email === null" style="color: red;">邮件尚未绑定</div>
          <div class="right-font" v-else>邮件绑定</div>
          <div><el-button type="primary" round @click="dialogBindPostal = true">更改</el-button></div>
        </div>
        <hr style="margin-left: 20px;">
      </div>
    </div>

    <el-dialog title="邮箱绑定" :visible.sync="dialogBindPostal">
      <el-form :model="formPostal" :rules="Postal" ref="ruleForm" label-width="140px">
        <el-form-item label="输入邮箱" prop="email">
          <el-input type="text" v-model="formPostal.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogBindPostal = false">取 消</el-button>
        <el-button type="primary" @click="submitEmail('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "dataUser",
  data() {
    /*更改密码验证*/
    let validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.formPassword.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.formPassword.newPass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    /*用户输入原密码，需要原密码正确才能进行下一步*/
    let validateOldPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入原密码'));
      } else if (value !== this.$store.state.userInfo.password) {
        callback(new Error('原密码输入错误!'));
      } else {
        callback();
      }
    };

    return {
        Passrules: {
          newPass: [
            {validator: validatePass, trigger: 'blur'}
          ],
          checkPass: [
            {validator: validatePass2, trigger: 'blur'}
          ],
          oldPass: [
            {validator: validateOldPass, trigger: 'blur'}
          ],
        },

        IdCard:{
          realname: [
            { required: true, message: '请输入您的姓名', trigger: 'blur' },
            { min: 2, max: 5, message: '请输入正确的姓名', trigger: 'blur' }
          ],
          idcard: [
            { required: true, message: '请输入活动名称', trigger: 'blur' },
            { min: 18, max: 18, message: '您输入的身份证号不正确', trigger: 'blur' }
          ],
        },

        Postal:{
          email: [
            { required: true, message: '请输入您的邮箱', trigger: 'blur' },
          ],
        },
        Phone:{
          phone: [
            { required: true, message: '请输入您的手机号', trigger: 'blur' },
            { min: 11, max: 11, message: '您输入的手机号不正确', trigger: 'blur' }
          ],
        },

        dialogAlterPassword: false,
        dialogBindIdCard: false,
        dialogBindPhone: false,
        dialogBindPostal: false,

        user_id:'',
        userMessage:'',

        formPassword: {
          newPass: '',
          checkPass: '',
          oldPass:'',
        },
        formIdCard: {
          realname: '',
          idcard: '',
        },
        formPhone: {
          phone: '',
        },
        formPostal: {
          email: '',
        },
        formLabelWidth: '120px'
      }
  },
  created() {
    this.user_id=this.$store.state.userInfo.id
    this.userMessage=this.$store.state.userInfo

    /*给表单中的值赋值*/
    this.formPostal.email = this.userMessage.email;
    this.formPhone.phone = this.userMessage.phone;
    this.formIdCard.realname = this.userMessage.realname;
    this.formIdCard.idcard = this.userMessage.idcard;
  },
  methods:{
    /*用户修改信息成功，重新获取用户的信息*/
    async getUserMessage(user_id){
      const res = await this.axios.post('http://localhost:8681/dex/user/getUserMessage',this.$qs.stringify({
        user_id:user_id
      }))

      if (res.data.code === 200){
        this.userMessage=res.data.object;
        this.$store.commit("updateUserInfo",res.data.object)
      }else {
        this.$message.error("刷新信息失败")
      }
    },
    /*修改密码*/
    submitPass(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios({
            url:'http://localhost:8681/dex/user/submitPass',
            method:'post',
            data:this.$qs.stringify({
              user_id:this.user_id,
              password:this.formPassword.newPass
            })
          }).then((res)=>{
            if (res.data.code === 200){
              this.dialogAlterPassword=false;
              /*修改成功，跳转到登陆页面，清除前端用户数据和后端的session数据*/
              sessionStorage.clear();
              this.axios.post('http://localhost:8681/dex/user/loginOut',this.$qs.stringify({
                user_id:this.$store.state.userInfo.id
              }))
              this.$store.commit("clearAllMessage")
              this.$message.success("修改密码后请重新登录")
              this.$router.push({
                name:'login'
              })
            }else {
              this.filedMsg()
            }
          })
        } else {
          this.filedMsg()
          return false;
        }
      });

    },
    /*身份证绑定*/
    submitIdCard(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.formIdCard.id=this.user_id
          this.axios({
            url:'http://localhost:8681/dex/user/submitIdCard',
            method:'post',
            data:this.$qs.stringify(this.formIdCard)
          }).then((res)=>{
            if (res.data.code === 200){
              this.successMsg()
              this.dialogBindIdCard=false;
              this.getUserMessage(this.user_id)
            }else {
              this.filedMsg()
            }
          })
        } else {
          this.filedMsg()
          return false;
        }
      });
    },
    /*手机号更换*/
    submitPhone(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.formPhone.id=this.user_id
          this.axios({
            url:'http://localhost:8681/dex/user/submitPhone',
            method:'post',
            data:this.$qs.stringify(this.formPhone)
          }).then((res)=>{
            if (res.data.code === 200){
              this.successMsg()
              this.dialogBindPhone=false;
              sessionStorage.clear();
              this.$store.commit("clearAllMessage")
              this.$router.push({
                name:'login'
              })
            }else {
              this.filedMsg()
            }
          })
        } else {
          this.filedMsg()
          return false;
        }
      });

    },
    /*邮箱绑定*/
    submitEmail(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.formPostal.id=this.user_id
          this.axios({
            url:'http://localhost:8681/dex/user/submitEmail',
            method:'post',
            data:this.$qs.stringify(this.formPostal)
          }).then((res)=>{
            if (res.data.code === 200){
              this.successMsg()
              this.dialogBindPostal=false
              this.getUserMessage(this.user_id)
            }else {
              this.filedMsg()
            }
          })
        } else {
          this.filedMsg()
          return false;
        }
      });
    },

    /*成功提示*/
    successMsg() {
      this.$notify({
        title: '提交成功',
        message: '成功提交信息',
        type: 'success'
      });
    },
    /*失败提示*/
    filedMsg() {
      this.$notify.error({
        title: '提交失败',
        message: '提交信息失败',
      });
    },

  }
}
</script>

<style scoped>
  .head-title{
    margin-top: 14px;
    font-size: 28px;
    font-weight: 500;
  }
  .security-demo{
    margin-top: 36px;
    display: flex;
    justify-content: left;
    align-items: center;
  }
  .demo-img img{
    width: 50px;
    height: 50px;
  }
  .demo-right{
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-left: 20px;
  }
  .right-font{
    width: 1000px;

  }
</style>