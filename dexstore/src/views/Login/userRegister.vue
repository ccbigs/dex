<template>
  <div>
    <headers :name="headerName"></headers>
    <div class="register-body">
      <div class="register-comp">
        <el-form :model="form" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="上传头像">
            <el-upload
                class="avatar-uploader"
                action="http://localhost:8681/dex/user/uploadUserHeaderImage"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="所在地区" prop="area">
            <el-input v-model="form.area" style="display: none"></el-input>
            <area-select @Area="AreaFunc"></area-select>
          </el-form-item>
          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="form.phone"></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="form.nickname"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="form.checkPass" autocomplete="off"></el-input>
        </el-form-item>
          <el-form-item label="出生日期" prop="birthday">
            <el-col :span="11">
              <el-date-picker type="date" placeholder="选择日期" v-model="form.birthday" style="width: 100%;"></el-date-picker>
            </el-col>
          </el-form-item>

          <el-form-item label="性别" prop="sex">
            <el-radio-group v-model="form.sex">
              <el-radio label="男"></el-radio>
              <el-radio label="女"></el-radio>
            </el-radio-group>
          </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
      </div>
    </div>
    <foot></foot>
  </div>
</template>

<script>
import shopLoginHeader from "../../common/dex-shop/shopLoginHeader";
import shopLoginFoot from "../../common/dex-shop/shopLoginFoot";
import areaSelect from '../../components/dex-shop/shopAreaSelect'
export default {
  name: "userRegister",
  components:{
    'headers':shopLoginHeader,
    'foot':shopLoginFoot,
    'areaSelect':areaSelect,
  },
  data() {
    let validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.form.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      form: {
        phone:'',
        nickname:'',
        password:'',
        checkPass:'',
        birthday:'',
        sex:'',
        /*地区*/
        area:'',
        /*将图片传送给后台的地址*/
        headimage:'',
      },
      /*头像显示的地址*/
      imageUrl: '',

      /*imgUrl:'',*/
      headerName:'注册页面',


      rules: {
        password: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        phone:[
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { min: 11, max: 11, message: '手机号码输入不正确', trigger: 'blur' }
        ],
        nickname:[
          { required: true, message: '请输入您的昵称', trigger: 'blur' },
          { min: 3, max: 11, message: '请输入如3到11个字符之间的昵称', trigger: 'blur' }
        ],
        birthday:[
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        area:[
          { required: true, message: '请选择所在地区', trigger: 'blur' },
        ],
        sex:[
          { required: true, message: '请选择性别', trigger: 'change' }
        ]
      }
    }

  },
  methods: {
    AreaFunc(area){
      this.form.area=area
    },
    /*表单提交*/
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios({
            url:'http://localhost:8681/dex/user/register',
            method:'post',
            data:this.$qs.stringify(this.form)
          }).then((res)=>{
/*            console.log(res);*/
            if (res.data.code === 200){
              this.$message.success("恭喜你注册成功，请重新登录")
              this.$router.push({
                name:'login'
              })
            }else {
              this.$message.error(res.data.message)
              /*this.registerFiled()*/
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    /*注册失败*/
   registerFiled() {
      this.$notify.error({
        title: '注册失败',
        message: '你好，尊敬的用户，你的注册信息有误',
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    /*图片上传*/
    handleAvatarSuccess(res, file) {
/*      console.log("照片",res)*/
      this.imageUrl = URL.createObjectURL(file.raw);
      this.form.headimage=res.url;
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
    }
  }
}
</script>

<style scoped>

  .register-body{
    width: 100%;
    height: 690px;
    background-color: white;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .register-comp{
    width: 500px;
  }

  /*图片的样式*/
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
    width: 128px;
    height: 128px;
    border: 1px dashed  #888888;
    line-height: 128px;
    text-align: center;
  }
  .avatar {
    width: 128px;
    height: 128px;
    display: block;
  }




</style>