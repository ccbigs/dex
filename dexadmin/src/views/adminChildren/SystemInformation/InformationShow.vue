<template>
  <div class="title">
    <div class="title-title">
      <el-page-header style="margin-top: 10px" @back="GoBack"  content="文章信息"></el-page-header>
      <p style="text-align: center">{{information.title}}</p>
    </div>
    <div class="admin-msg">
      <p style="text-align: center">作者:{{information.auther}}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp发布时间:{{information.public_time}}</p>
    </div>
    <div v-html="information.content"></div>
    <div class="img_dianzan">
      <img v-if="dianzan === 0" src="/static/image/dianzan.png" alt="" @click="approve">
      <img v-else src="/static/image/dianzaned.png" alt="" @click="approve">
    </div>
    <div class="bottom-msg">
      <div>
        <span>点赞人数: {{information.approve_number}}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
        <span>总浏览人数: {{information.view_number}}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "InformationShow",
  data(){
    return{
      information:'',
      dianzan:0,
    }
  },
  created() {
    this.increaseArticleViews();
  },
  mounted() {
    this.getInformationDetail();
  },
  methods:{
    /*获取点击文章到的全部信息*/
    async getInformationDetail() {
      const res=await this.axios.post('DexAdmin/getInformationDetail',this.$qs.stringify({
        info_id:this.$route.query.info_id
      }))
      console.log(res);
      if (res.data.code === 200){
        this.information=res.data.object
      }else{
        this.$message.error("获取文章失败")
      }
    },
    /*用户打开文章，文章浏览量加1*/
    async increaseArticleViews() {
      const res=await this.axios.post('DexAdmin/increaseArticleViews',this.$qs.stringify({
        info_id:this.$route.query.info_id
      }))
    },
    /*返回上级页面*/
    GoBack(){
      this.$router.go(-1);
    },
    /*用户点击啊*/
    approve(){
      if (this.dianzan === 0){
        this.dianzan=1
        this.axios.post('DexAdmin/readersLikeArticles',this.$qs.stringify({
          info_id:this.information.id
        })).then((res)=>{
          if (res.data.code === 200){
            this.getInformationDetail()
          }else {
            this.$message.error("文章点赞失败")
          }
        })
      }else {
        this.dianzan=0;
        this.axios.post('DexAdmin/readersCancelFavoriteArticles',this.$qs.stringify({
          info_id:this.information.id
        })).then((res)=>{
          if (res.data.code === 200){
            this.getInformationDetail()
          }else {
            this.$message.error("文章点赞失败")
          }
        })
      }
    }
  }
}
</script>

<style scoped>
  .title{
    margin: 0 auto;
    padding: 10px 20px 10px 20px;
    width: 1200px;
    background: white;
  }

  .title-title p{
    font-size: 40px;
    font-weight: bold;
    margin: 16px 0px 12px 0px;
  }

  .admin-msg{
    color: #8c939d;
    margin-top: 30px;
    margin-bottom: 10px;
  }

  img{
    width: 100px;
    height: 100px;
    cursor: pointer;
  }

  .img_dianzan{
    margin-top: 30px;
    margin-bottom: 40px;
    display: flex;
    justify-content: center;
  }

  .bottom-msg{
    margin-bottom: 20px;
    display: flex;
    justify-content: flex-end;
  }

  .bottom-msg span{
    color: #8c939d;
    font-size: 20px;
  }


</style>