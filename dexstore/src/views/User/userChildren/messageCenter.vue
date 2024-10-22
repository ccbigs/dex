<template>
  <div>
    <!--页头信息-->
    <div class="head-title">消息中心</div>
    <el-divider></el-divider>
    <div v-for="(i,o) in 3" style="margin-top: 10px;margin-bottom: 10px">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>系统消息</span>
          </div>
        </template>
        <div>系统消息是指网站管理者以信息广播形式向目标用户发布的公开的消息、公告、通知、提示等。具有群发性，高可达性等消息特性。</div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "messageCenter",
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


</style>