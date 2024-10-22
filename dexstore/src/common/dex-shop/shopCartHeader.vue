<template>
  <div class="cart-header">
    <!--左边内容-->
    <div class="header-left">
      <div class="left-img" @click="GoBack">
        <img src="/static/images/dex-logo1.png" alt="">
      </div>
      <div class="left-font">
        <span>我的购物车</span>
      </div>
    </div>
    <!--右边内容-->
    <div class="header-right">
      <el-steps :active="1">
        <el-step title="步骤 1" description="我的购物车"></el-step>
        <el-step title="步骤 2" description="填写核对订单"></el-step>
        <el-step title="步骤 3" description="成功提交订单"></el-step>
      </el-steps>
    </div>
  </div>
</template>

<script>
export default {
  name: "shopCartHeader",
  data(){
    return {}
  },
  methods:{
    GoBack:function () {
      this.$router.push({
        name:'home'
      })
    }
  }
}
</script>

<style scoped>
  .cart-header{
    background-color: white;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100px;
  }

  .header-left{
    display: flex;
    justify-content: left;
    align-items: center;
    margin-left: 100px;
  }

  .left-img{
    width: 200px;
    height: 100px;
    margin-right: 10px;
  }
  .left-img img{
    width: 200px;
    height: 100px;
  }
  .left-font{
    font-size: 36px;
    margin-left: 20px;
  }

  /*步骤条*/
  .header-right{
    width: 500px;
    margin-right: 120px;
  }


</style>