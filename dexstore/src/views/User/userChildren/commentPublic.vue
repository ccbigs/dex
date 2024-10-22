<template>
  <div>
    <!--  如果直接点这个，那么商品是没有参数的，就需要提示他去签收页面去选取商品发布评论  -->
    <div v-if="commentOrder === undefined" style="height: 400px">
      <el-empty description="前往已签收订单选择商品评价">
        <el-button type="primary" @click="GoOrderDid">点击前往</el-button>
      </el-empty>
    </div>
    <div v-else>
      <!--页头信息-->
      <div class="head-title">发布评论</div>
      <el-divider></el-divider>
      <div class="ware-decs">
        <div>商品名称</div>
        <div class="img-name">
          <div class="img"><div><img :src="this.commentOrder.ware_img1" alt=""></div></div>
          <div class="name"><div>{{this.commentOrder.ware_name}}</div></div>
        </div>
      </div>
      <div>
        <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="评价" prop="desc">
            <el-input type="textarea" v-model="ruleForm.desc"></el-input>
          </el-form-item>
          <el-form-item label="打分">
            <div style="display: flex;height:40px;align-items: center">
              <el-rate
                  @click="showData"
                  v-model="value"
                  show-text>
              </el-rate>
            </div>
          </el-form-item>
          <el-form-item label="发布图片">
            <el-upload
                class="upload-demo"
                action="http://localhost:8681/dex/user/uploadUserHeaderImage"
                list-type="picture-card"
                drag="true"
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
    </div>
  </div>
</template>

<script>
export default {
  name: "commentUser",
  data(){
    return{
      value:'',
      ruleForm:{
        desc:'',
      },
      image_list:[],
      dialogImageUrl: '',
      dialogVisible: false,
      commentOrder:'',
    }
  },
  created() {
    this.commentOrder=this.$route.query.order

  },
  methods:{
    /*前往签收页面*/
    GoOrderDid(){
      this.$router.push({
        name:'orDid'
      })
    },
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
    checkLength(file){
      if (this.image_list.length > 2) {
        this.filedMsg()
        return false;
      }else {
        return true;
      }
    },

    /*点击提交表单*/
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let comment={};
          comment.user_id=this.$store.state.userInfo.id;
          comment.orders_id=this.commentOrder.id;
          comment.ware_id=this.commentOrder.ware_id;
          comment.user_nickname=this.$store.state.userInfo.nickname;
          comment.user_headimage=this.$store.state.userInfo.headimage;
          comment.comment_rate=this.value;
          comment.comment_connect=this.ruleForm.desc;
          comment.comment_date=null;

          if (this.image_list.length === 1){
            comment.comment_img1=this.image_list[0]
          }else if (this.image_list.length === 2){
            comment.comment_img1=this.image_list[0]
            comment.comment_img2=this.image_list[1]
          }else {
            comment.comment_img1=this.image_list[0]
            comment.comment_img2=this.image_list[1]
            comment.comment_img3=this.image_list[2]
          }

          this.axios({
            url:'http://localhost:8681/dex/comment/publicComment',
            method:'post',
            data:this.$qs.stringify(comment)
          }).then((res)=>{
            if (res.data.code === 200){
              this.successMsg();
              this.commentOrder = '';
              this.$router.push({
                name:'orDid'
              })
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
        title: '评论成功',
        message: '成功发布评论',
        type: 'success'
      });
    },
    /*失败提示*/
    filedMsg() {
      this.$notify.error({
        title: '上传失败',
        message: '超过图片的最大上传数量',
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

  .ware-decs{
    height: 200px;
    display: flex;
    justify-content: left;
    align-items: center;
  }
  .img-name{
    margin-left: 40px;
  }

  .img-name img{
    width: 120px;
    height: 120px;
  }

  .img{
    display: flex;
    justify-content: center;
  }

  .name{
    display: flex;
    justify-content: center;
  }

  .upload-image{
    width: 200px;
    height: 100px;
  }

</style>