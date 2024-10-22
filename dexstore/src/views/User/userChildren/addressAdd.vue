<template>
  <div>
    <!--页头信息-->
    <div class="head-title">新增地址</div>
    <el-divider></el-divider>
    <!--表单信息-->
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="收货人姓名" prop="name">
        <el-input v-model="ruleForm.name" placeholder="填写收货人姓名" style="width: 300px;"></el-input>
      </el-form-item>
      <!--手机号码-->
      <el-form-item label="联系电话" prop="phone">
        <el-input v-model="ruleForm.phone" placeholder="填写收货人联系电话" style="width: 300px;"></el-input>
      </el-form-item>
      <!--邮政编码-->
      <el-form-item label="邮政编码" prop="postal">
        <el-input v-model="ruleForm.postal" placeholder="填写收货人所在地的邮政编码" style="width: 200px;"></el-input>
      </el-form-item>
      <!--地址信息-->
      <el-form-item label="地址信息" prop="local">
        <el-input v-model="ruleForm.local" style="display: none"></el-input>
        <area-select @Area="AreaFunc"></area-select>
      </el-form-item>
      <!--详细地址-->
      <el-form-item label="详细地址" prop="detail">
        <el-input type="textarea" v-model="ruleForm.detail" placeholder="请输入详细地址信息，如道路、门牌号、小区、楼栋号、单元等信息"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">新增地址</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import areaSelect from '../../../components/dex-shop/shopAreaSelect'
export default {
  name: "addressUser",
  data() {
    return {
      ruleForm: {
        name:'',
        phone:'',
        postal:'',
        detail: '',
        local:'',
        user_id:'',
      },
      rules: {
        name:[
          { required: true, message: '请输入收货人姓名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        phone:[
          { required: true, message: '请输入收货人联系电话', trigger: 'blur' },
          { min: 11, max: 11, message: '输入的电话不正确', trigger: 'blur'}
        ],
        postal:[
          { required: true, message: '请输入收货人所在地的邮政编码', trigger: 'blur' },
          { min: 6, max: 6, message: '输入的邮政编码不正确', trigger: 'blur'}
        ],
        Area: [
          { required: true, message: '请输入地址信息', trigger: 'blur' }
        ],
        detail: [
          { required: true, message: '请填写详细地址', trigger: 'blur' }
        ]
      }
    };
  },
  components:{
    'areaSelect':areaSelect
  },
  methods:{
    AreaFunc(area){
      this.ruleForm.local=area
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.user_id=this.$store.state.userInfo.id;
          this.axios({
            url:'http://localhost:8681/dex/address/addUserAddress',
            method:'post',
            data:this.$qs.stringify(this.ruleForm)
          }).then((res)=>{
            if (res.data.code === 200){
              this.$router.push({
                name:'addressUser'
              })
              /*返回全部地址*/
              /*const address=this.axios.post('http://localhost:8681/dex/address/getUserAddress',this.$qs.stringify({
                user_id:res.data.object.id
              }))
              this.$store.commit('updateUserAddress',address.data.object)*/

              this.successMsg();
            }else {
              this.filedMsg();
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
    },
    /*成功提示*/
    successMsg() {
      this.$notify({
        title: '添加成功',
        message: '成功添加地址',
        type: 'success'
      });
    },
    /*失败提示*/
    filedMsg() {
      this.$notify.error({
        title: '添加失败',
        message: '地址添加失败',
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
</style>