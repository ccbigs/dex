<template>
  <div class="shop-detail-introduce">
    <el-card class="box-card" shadow="hover">
      <div slot="header" class="clearfix" >
        <p class="text item">商品详情</p>
      </div>
      <p>{{wareDetails}}</p>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "shopDetailIntroduce",
  data(){
    return {

    }
  },
  props:['wareDetails']
}
</script>

<style scoped>
.text {
  font-size: 14px;
}
.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}


</style>