<template>
  <div>
    <!--页头信息-->
    <div class="head-title">活动推送</div>
    <el-divider></el-divider>
    <div class="center-demo">
      <div class="demo-title">
        <div class="title">青春不散场，优惠享不停！</div>
        <div class="date">2021-06-26 23:40:52</div>
      </div>
      <div class="demo-img">
        <img :src="imgUrl" alt="">
      </div>
      <div class="demo-content">
        <div class="content">教育购领券最高享200元优惠~</div>
        <div class="details">查看详情</div>
      </div>
      <div></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "messageActive",
  data(){
    return{
      imgUrl:"https://res.vmallres.com/pimages//pages/cdnImages/94853079332617035849.png"
    }
  }
}
</script>

<style scoped>
  .head-title{
    margin-top: 14px;
    font-size: 28px;
    font-weight: 500;
  }

  .center-demo{
    width: 1160px;
    height: 360px;
    padding: 20px;
    background-color: #EEEEEE;
    border-radius: 18px;
  }

  .center-demo .demo-title{
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;

  }
  .demo-title .title{
    font-size: 30px;
  }

  .center-demo .demo-img{
    width: 1160px;
    height: 280px;
    margin-bottom: 20px;
  }
  .demo-img img{
    width: 1160px;
    height: 280px;
  }

  .center-demo .demo-content{
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

</style>