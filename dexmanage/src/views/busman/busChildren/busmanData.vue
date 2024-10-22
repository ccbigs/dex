<template>
  <div>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="商家账号" prop="name">
        <el-input disabled v-model="ruleForm.bus_account"></el-input>
      </el-form-item>
      <el-form-item label="我的姓名" prop="region">
        <el-input disabled  v-model="ruleForm.bus_name"></el-input>
      </el-form-item>
      <el-form-item label="身份信息" prop="region">
        <el-input disabled v-model="ruleForm.bus_idcard"></el-input>
      </el-form-item>
      <el-form-item label="账号状态">
        <el-switch disabled v-model="ruleForm.bus_busman_state === '正常'"></el-switch>
      </el-form-item>
      <el-form-item label="注册时间" prop="region">
        <el-input disabled v-model="ruleForm.bus_register_time"></el-input>
      </el-form-item>
      <el-form-item label="发货地址" prop="">
        <el-input v-model="ruleForm.bus_local"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "busmanData",
  data() {
    return {
      ruleForm: {
        bus_account:'',
        bus_password:'',
        bus_phone:'',
        bus_name:'',
        bus_idcard:'',
        bus_busman_state:'',
        bus_register_time:'',
        bus_lasttime:'',
        bus_local:'',
      },
      rules: {
        bus_local: [
          { required: true, message: '请输入发货地址', trigger: 'blur' },
          { min: 9, max: 30, message: '长度在 9 到 30 个字符', trigger: 'blur' }
        ],
      }
    };
  },
  created() {
    this.ruleForm = this.$store.state.busmanInfo
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('busman/updateLocal',this.$qs.stringify(this.ruleForm)).then((res)=>{
            if (res.data.code === 200){
              this.$message.success('更改信息成功')
            }else {
              this.$message.error('更改信息失败')
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>

</style>