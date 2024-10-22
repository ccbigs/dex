<template>
  <div class="mode">
    <div class="header">
      DEX商城开放数据
    </div>
    <el-divider></el-divider>
    <div class="demo">
      <one-volume></one-volume>
    </div>
    <div class="demo">
      <two-volume></two-volume>
    </div>

  </div>
</template>

<script>
import shopHomeOneVolume from "../../../components/dex-shop/shopHomeOneVolume";
import shopHomeTwoVolume from "../../../components/dex-shop/shopHomeTwoVolume";
export default {
  name: "dataPanle",
  components:{
    'oneVolume':shopHomeOneVolume,
    'twoVolume':shopHomeTwoVolume
  },
  data(){
    return{

    }
  },
  methods:{

  }
}
</script>

<style scoped>
  .mode .header{
    margin-top: 12px;
    font-size: 30px;
    font-weight: bold;
  }
  .mode .demo{
    display: flex;
    justify-content: center;
    margin-bottom: 10px;
    margin-top: 10px;
  }
</style>