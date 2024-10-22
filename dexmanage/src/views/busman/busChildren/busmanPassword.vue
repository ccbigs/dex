<template>
  <div>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>修改密码</span>
      </div>
      <el-form :model="password" status-icon  label-width="100px" class="demo-ruleForm">
        <el-form-item label="输入原密码" prop="pass">
          <el-input type="password" v-model="password.oldPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="输入新密码" prop="checkPass">
          <el-input type="password" v-model="password.newPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm()">修改密码</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "busmanPassword",
  data(){
    return{
      password:{
        oldPass:'',
        newPass:''
      }
    }
  },
  methods:{
    submitForm(){
      if (this.password.oldPass === this.$store.state.busmanInfo.bus_password){
        if (this.password.newPass === ''){
          this.$message.error("输入的新密码为空，请重新输入")
        }else {
          this.axios.post('busman/updatePassword',this.$qs.stringify({
            id:this.$store.state.busmanInfo.id,
            newPass:this.password.newPass
          })).then((res)=>{
            if (res.data.code === 200){
              this.$message.success("密码修改成功，请重新登录")
              sessionStorage.clear();
              this.$router.push({
                name:'login'
              })
            }else {
              this.$message.error("密码修改失败")
            }
          })
        }
      }else {
        this.$message.error("输入的密码有误")
        this.password.newPass='';
        this.password.oldPass='';
      }
    }
  }
}
</script>

<style scoped>

</style>