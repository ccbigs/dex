<template>
  <div>
    <shop-header></shop-header>

    <div class="info-demo">
      <el-card class="box-card">
        <el-page-header class="tabName" @back="goBack" :content="this.$route.params.tabName"></el-page-header>
        <div v-html="information.content"></div>
      </el-card>
    </div>

    <shop-foot></shop-foot>
  </div>
</template>

<script>
import shopHeader from "../../common/dex-shop/shopHeader";
import shopFoot from "./shopFoot";
export default {
  name: "shopPublicInfo",
  components:{
    'shopHeader':shopHeader,
    'shopFoot':shopFoot
  },
  data(){
    return{
      information:'',
    }
  },
  created() {
    this.getInfo()
  },
  methods:{
    async getInfo(){
      const res =await this.axios.post('http://localhost:8680/dex/info/getInformationDetailByUser',this.$qs.stringify({
        info_id:this.$route.params.infoId
      }))

      if (res.data.code === 200){
        console.log("----------->",res.data.object)
        this.information=res.data.object
      }else {
        this.$message.error("标题文章获取失败")
      }
    },
    /*回退*/
    goBack(){
      this.$router.go(-1);
    }
  }

}
</script>

<style scoped>
  .info-demo{
    width: 1470px;
    margin: 20px auto;
    background-color: #FFFFFF;
    border-radius: 16px;
    padding: 20px;
  }
</style>