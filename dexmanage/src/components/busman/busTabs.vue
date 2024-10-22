<template>
  <div>
    <el-tabs v-model="TabChoose" @tab-click="handleClick">
      <el-tab-pane label="全部商品" name="wareSale"></el-tab-pane>
      <el-tab-pane label="商品上线" name="wareAdd"></el-tab-pane>
      <el-tab-pane label="全部订单" name="orderDid"></el-tab-pane>
      <el-tab-pane label="订单搜索" name="orderSearch"></el-tab-pane>
      <el-tab-pane label="参与商品" name="activeDid"></el-tab-pane>
      <el-tab-pane label="活动商品上线" name="activeAdd"></el-tab-pane>
      <el-tab-pane label="已购轮播图商位" name="hpCarouselDid"></el-tab-pane>
      <el-tab-pane label="已购推荐商位" name="hpRecommendDid"></el-tab-pane>
      <el-tab-pane label="商位购买" name="hpPurchase"></el-tab-pane>
      <el-tab-pane label="商位购买" name="hpPurchase"></el-tab-pane>
      <el-tab-pane label="加入商品" name="bonusAdd"></el-tab-pane>
      <el-tab-pane label="已上线商品" name="bonusDid"></el-tab-pane>
      <el-tab-pane label="秒杀订单" name="bonusOrder"></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  /*props:['TabChoose'],*/
  data() {
    return {
      TabChoose: ''
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(this.TabChoose);
      this.$router.push({
        name:this.TabChoose
      })
    },
  }
};
</script>


<style scoped>

</style>