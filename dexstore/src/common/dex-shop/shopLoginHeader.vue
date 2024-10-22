<template>
  <div class="header">
    <div class="header-demo">
      <img style="cursor:pointer" src="/static/images/dex-logo1.png" @click="GoHome" alt="">
      <el-divider direction="vertical"></el-divider>
      <div class="size">DEX商城{{name}}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "shopLoginHeader",
  props:['name'],
  methods:{
    GoHome(){
      this.$router.push({
        name:'home'
      })
    }
  }
}
</script>

<style scoped>
  .header{
    width: 1684.44px;
    height: 48px;
    background-color: #F5F5F5;
  }
  .header img{
    width: 100px;
    height: 48px;
    margin-left: 60px;
  }
  .header-demo{
    display: flex;
    align-items: center;
  }

  .size{
    font-size: 34px;
  }
</style>