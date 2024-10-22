<template>
  <div class="foot">
    <div class="one">
      <ul style="display: inline-block">
        <li v-for="(o,i) in liData">{{o}} <el-divider direction="vertical"></el-divider></li>
      </ul>
    </div>
    <div class="two">
      <div>DEX帐号 版权所有 © 2011-2021</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "shopLoginFoot",
  data(){
    return{
      liData:['DEX帐号用户协议','关于DEX帐号与隐私的声明','常见问题']
    }
  }
}
</script>

<style scoped>
  .foot{
    width: 1684.44px;
    height: 72px;

  }

  .one{
    width: 1684.44px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  li{
    display: inline-block;
    color: #888888;
  }
  ul{
    margin-top: 10px;
    margin-bottom: 10px;
  }
  .two{
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>