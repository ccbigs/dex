<template>

  <div class="ware-mode" >
    <el-row>
      <el-col>
        <el-tooltip class="item" v-if="ware.id === 0" effect="dark" content="商位待售，赶快购买吧" placement="top">
          <h3 class="medium" @click="buyRecommendQuotient" ><el-empty description="商位待售，期待你的购买"></el-empty></h3>
        </el-tooltip>
        <div v-else>
        <el-card  :body-style="{ padding: '0px' }" shadow="hover">
          <img  :src="ware.ware_img1" class="image">
          <div class="font-demo">
            <h4 class="PName">{{ ware.ware_name }}</h4>
          </div>
          <div class="font-demo">
            <p class="PContent">{{ ware.ware_describe }}</p>
          </div>
          <div class="font-demo">
            <p class="PPrice">￥{{Number(ware.ware_price).toFixed(2)}}</p>
          </div>

        </el-card>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "hpWare",
  props:['ware'],
  methods:{
    buyRecommendQuotient(){
      this.$parent.buyRecommend();
    }
  }
}
</script>

<style scoped>
.ware-mode{
  width: 260px;
  height: 340px;
  display: inline-block;
  margin-right: 10px;
  margin-left: 10px;
}
img{
  width: 150px;
  height: 150px;
  margin-left: 45px;
  margin-top: 20px;
}

.PContent{
  text-overflow: -o-ellipsis-lastline;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  line-clamp: 1;
  -webkit-box-orient: vertical;
}

.font-demo{
  display: flex;
  justify-content: center;
}

h4{
  margin-top: 20px;
  margin-bottom: 10px;
}

p{
  margin-top: 8px;
  margin-bottom: 10px;
  padding: 0px 6px 0px 6px;
}

.PPrice{
  color: red;
  font-weight: bold;
}

.medium{
  cursor: pointer;
}
</style>