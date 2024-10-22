<template>
  <div>
    <!--页头信息-->
    <div class="head-title">我的信息</div>
    <el-divider></el-divider>
    <div class="data-mode">
      <div style="margin-right:36px;">头像:</div>
      <div>
        <!-- 头像       -->
        <el-upload
            class="avatar-uploader"
            action="http://localhost:8681/dex/user/uploadUserHeaderImage"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
          <!--此处做了调整-->
          <img v-if="true" :src="this.ruleForm.headimage" class="avatar">
        </el-upload>
      </div>
    </div>
    <div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="我的昵称" prop="nickname">
          <el-input v-model="ruleForm.nickname" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="ruleForm.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="出生日期" required prop="birthday">
            <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.birthday" style="width: 300px;"></el-date-picker>
        </el-form-item>

        <el-form-item label="所在地" prop="Area">
          <el-input v-model="ruleForm.Area" style="display: none"></el-input>
          <area-select @Area="AreaFunc"></area-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">点击修改</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

  </div>
</template>

<script>
import AreaSelect from '../../../components/dex-shop/shopAreaSelect'
export default {
  name: "dataUser",
  data(){
    return {
      ruleForm: {
        id:'',
        nickname: '我的昵称',
        sex:'女',
        birthday:'2020-02-02',
        Area:'',
        headimage:'',
      },
      rules: {
        nickname: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        region: [
          { required: true, message: '请选择活动区域', trigger: 'change' }
        ],
        date1: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        date2: [
          { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
        ],
        type: [
          { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
        ],
        resource: [
          { required: true, message: '请选择活动资源', trigger: 'change' }
        ],
        desc: [
          { required: true, message: '请填写活动形式', trigger: 'blur' }
        ]
      }
    }
  },

  created() {
    this.ruleForm.headimage=this.$store.state.userInfo.headimage
    this.ruleForm.nickname=this.$store.state.userInfo.nickname
    this.ruleForm.sex=this.$store.state.userInfo.sex
    this.ruleForm.birthday=this.$store.state.userInfo.birthday
    this.ruleForm.id=this.$store.state.userInfo.id
    this.ruleForm.Area=this.$store.state.userInfo.area
  },

  methods:{
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios({
            url:'http://localhost:8681/dex/user/updateUserInfo',
            method:'post',
            data:this.$qs.stringify(this.ruleForm)
          }).then((res)=>{
            if (res.data.code === 200){
              this.$message.success("信息更新成功")
              this.$store.commit("updateUserInfo",res.data.object)
              location.reload()
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
    AreaFunc(area){
      this.Area=area
    },
    /*上传图片的方法*/
    handleAvatarSuccess(res, file) {
      this.ruleForm.headimage = res.url
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    /*成功提示*/
    successMsg() {
      this.$notify({
        title: '更新成功',
        message: '成功更新信息',
        type: 'success'
      });
    },
    /*失败提示*/
    filedMsg() {
      this.$notify.error({
        title: '更新失败',
        message: '用户信息更新失败',
      });
    },
  },
  components:{
    'AreaSelect':AreaSelect,
  }
}
</script>

<style scoped>
  .head-title{
    margin-top: 14px;
    font-size: 28px;
    font-weight: 500;
  }
  .data-mode{
    display: flex;
    justify-content: left;
    align-items: center;
    margin-bottom: 20px;
    margin-left: 30px;
  }




  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>