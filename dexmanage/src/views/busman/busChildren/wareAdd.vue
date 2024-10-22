<template>
  <div class="ware-add">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
      <el-form-item label="商品名称" prop="ware_name">
        <el-input v-model="ruleForm.ware_name"></el-input>
      </el-form-item>
      <el-form-item label="商品副标题" prop="ware_describe">
        <el-input v-model="ruleForm.ware_describe"></el-input>
      </el-form-item>
      <el-form-item label="商品分类" prop="ware_describe">
        <el-select v-model="ruleForm.categorie_name"  placeholder="请选择商品分类">
          <el-option
              v-for="item in allCategorie"
              :key="item.categorie_name"
              :label="item.categorie_name"
              :value="item.categorie_name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品描述" prop="ware_connect">
        <el-input type="textarea" v-model="ruleForm.ware_connect"></el-input>
      </el-form-item>
      <el-form-item label="上传图片">
        <el-upload
            class="upload-demo"
            action="http://localhost:8682/dex/pictureVerification/uploadUserHeaderImage"
            list-type="picture-card"
            :drag="true"
            :on-preview="handlePictureCardPreview"
            :on-success="handleAvatarSuccess"
            :on-remove="handleRemove"
            :before-upload="checkLength">
          <i class="el-icon-plus"></i>
        </el-upload>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
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
  name: "wareAdd",
  data() {
    return {
      ruleForm: {
        busman_id:'',
        ware_name: '',
        ware_describe: '',
        ware_connect: '',
        ware_img1: '',
        ware_img2: '',
        ware_img3: '',
        ware_img4: '',
        ware_img5: '',
        /*全部的分类信息*/
        categorie_name:'',
        categorie_id:'',
      },
      rules: {
        ware_name: [
          { required: true, message: '请输入商品名称', trigger: 'blur' },
          { min: 5, max: 50, message: '商品名称长度在 5 到 50 个字符之间', trigger: 'blur' }
        ],
        ware_describe:[
          { required: true, message: '请输入商品副标题', trigger: 'blur' },
          { min: 40, max: 100, message: '商品副标题长度在 40 到 100 个字符之间', trigger: 'blur' }
        ],
        ware_connect:[
          { required: true, message: '请输入商品的内容信息', trigger: 'blur' },
          { min: 10, message: '商品内容信息必须有10个字以上', trigger: 'blur' }
        ],
      },
      imageUrl:'',
      image_list:[],
      dialogImageUrl: '',
      dialogVisible: false,
      /*商品的全部类型*/
      allCategorie:[]
    };
  },
  created() {
    this.getAllCategorie();
  },
  methods: {
    /*上传商品图片*/
    /*用户对上传的图片的增删改查的操作*/
    changePicture(removeUrl){
      for (let i = 0; i < this.image_list.length; i++) {
        if (this.image_list[i] === removeUrl){
          this.image_list.splice(i,1)
          break;
        }
      }
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      /*将上传成功的图片添加到图片列表中*/
      this.image_list.push(res.url)
    },
    handleRemove(file, fileList) {
      this.changePicture(file.response.url)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    /*判断图片的数量*/
    checkLength(file){
      if (this.image_list.length > 4) {
        this.$message.error("最多只能上传五张图片")
        return false;
      }else {
        return true;
      }
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.image_list.length < 2){
            this.$message.error("必须要上传至少三张照片")
          }else {
            this.ruleForm.busman_id = this.$store.state.busmanInfo.id;
            if (this.image_list.length === 3) {
              this.ruleForm.ware_img1 = this.image_list[0]
              this.ruleForm.ware_img2 = this.image_list[1]
              this.ruleForm.ware_img3 = this.image_list[2]
            }
            if (this.image_list.length === 4) {
              this.ruleForm.ware_img1 = this.image_list[0]
              this.ruleForm.ware_img2 = this.image_list[1]
              this.ruleForm.ware_img3 = this.image_list[2]
              this.ruleForm.ware_img4 = this.image_list[3]
            }
            if (this.image_list.length === 5) {
              this.ruleForm.ware_img1 = this.image_list[0]
              this.ruleForm.ware_img2 = this.image_list[1]
              this.ruleForm.ware_img3 = this.image_list[2]
              this.ruleForm.ware_img4 = this.image_list[3]
              this.ruleForm.ware_img5 = this.image_list[4]
            }

            /*确定分类商品的id*/
            for (let i = 0; i < this.allCategorie.length; i++) {
              if (this.allCategorie[i].categorie_name === this.ruleForm.categorie_name) {
                this.ruleForm.categorie_id = this.allCategorie[i].id;
              }
            }

            if (this.ruleForm.categorie_id === "") {
              this.$message.error("请选择商品分类")
            } else {
              console.log(this.ruleForm);
              this.axios.post('wares/busmanAddWare', this.$qs.stringify(
                  this.ruleForm
              )).then((res) => {
                if (res.data.code === 200) {
                  this.$message.success("新增商品成功")
                  this.$router.push({
                    name: 'wareSale'
                  })
                } else {
                  this.$message.error("新增商品失败")
                }
              })
            }
          }

        } else {
          this.$message.error("提交信息失败。")
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    /*获取全部的商品分类*/
    async getAllCategorie(){
      const res=await this.axios.post('categorie/getAllCategorie');
      if (res.data.code === 200){
        this.allCategorie=res.data.object;
      }else {
        this.$message.error("获取商品分类信息失败")
      }
    },
    /*选中库存时的点击事件*/
    handleChange(){

    },
    choose(){
      alert("11")
    }
  }
}
</script>

<style scoped>
  .ware-add{
    width: 1200px;
  }

</style>