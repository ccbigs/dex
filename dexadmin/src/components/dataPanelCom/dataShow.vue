<template>
  <div style="cursor: pointer;">
      <el-card shadow="hover" class="card-mode" :body-style="	{ padding: '0px' }">
        <div  style="display: flex">
          <div class="mode-img"><img :src="img" alt=""></div>
          <div class="mode-data">
            <div class="data-p1"><p>{{ data }}</p></div>
            <div><p>{{ dec }}</p></div>
          </div>
        </div>
      </el-card>
  </div>
</template>

<script>
export default {
  name: "dataShow",
  props:['img','data','dec']
}
</script>

<style scoped>
  .card-mode{
    height: 76px;
  }

  .mode-img{
    width: 76px;
    height: 76px;
    margin-left: 5px;
  }
  img{
    width: 76px;
    height: 76px;
  }

  .mode-data{
    width: 202px;
    height: 76px;
  }

  .mode-data p{
    margin: 0px auto;
    text-align: center;
  }

  .data-p1{
    margin-top:16px;
    font-size: 22px;
    font-weight: bold;
    color: dodgerblue;
  }
</style>