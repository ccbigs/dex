<template>
  <div>
    <Dex-header></Dex-header>
    <!--子路由的出口-->
    <router-view></router-view>
    <!--网页脚部-->
    <shop-foot></shop-foot>
  </div>

</template>

<script>
import DexHeader from '../common/dex-shop/shopHeader';
import shopFoot from "../common/dex-shop/shopFoot";
export default {

  name: "Index",
  components:{
    'Dex-header':DexHeader,
    'shopFoot':shopFoot,
  }
}
</script>

<style scoped>

</style>