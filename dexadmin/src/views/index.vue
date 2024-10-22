<template>
  <div>
    <div><el-menu
        default-active="/adminManger/dataPanel"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        background-color="#545c64"
        :router="true"
        text-color="#fff"
        active-text-color="#ffd04b">
      <el-menu-item index="/adminManger/dataPanel">DEX管理后台</el-menu-item>
      <el-menu-item index="/adminManger/dataPanel">数据面板</el-menu-item>
      <el-menu-item index="/adminManger/busmanManager">商家管理</el-menu-item>
      <el-menu-item index="/adminManger/userManager">用户管理</el-menu-item>
      <el-menu-item index="/adminManger/wareManager">商品管理</el-menu-item>
      <el-menu-item index="/adminManger/orderManager">订单管理</el-menu-item>
      <el-menu-item index="/adminManger/commentManager">评论管理</el-menu-item>
      <el-menu-item index="/adminManger/categorieManager">商品分类管理</el-menu-item>
      <el-submenu index="6">
        <template slot="title">秒杀管理</template>
        <el-menu-item index="/adminManger/bonusManager">秒杀信息</el-menu-item>
        <el-menu-item index="/adminManger/bonusOrder">秒杀订单</el-menu-item>
      </el-submenu>
      <el-submenu index="7">
        <template slot="title">商位管理</template>
        <el-menu-item index="/adminManger/quotientPrice">商位价格信息</el-menu-item>
        <el-menu-item index="/adminManger/quotientRecommend">推荐商位信息</el-menu-item>
        <el-menu-item index="/adminManger/quotientMessage">轮播图商位信息</el-menu-item>
        <el-menu-item index="/adminManger/quotientOrders">商位订单</el-menu-item>
      </el-submenu>
      <el-submenu index="8">
        <template slot="title">发布新闻</template>
        <el-menu-item index="/adminManger/publicInformation">DEX商城新闻发布</el-menu-item>
        <el-menu-item index="/adminManger/InformationManager">新闻管理</el-menu-item>
      </el-submenu>
      <el-submenu index="9">
        <template slot="title">DEX系统配置</template>
        <el-menu-item v-if="this.$store.state.adminInfo.admin_type === '一级管理员'" index="/adminManger/adminManager">管理人员管理</el-menu-item>
        <el-menu-item index="/adminManger/dexFootMessage">DEX商城底部标题信息</el-menu-item>
        <el-menu-item index="/adminManger/dexSystemParams">DEX系统参数配置</el-menu-item>
        <el-menu-item index="/adminManger/setting/pictureVerificationSetting">图形验证设置</el-menu-item>
        <el-menu-item index="/adminManger/setting/logSetting">日志管理</el-menu-item>
        <el-menu-item index="/adminManger/dexSearch">DEX搜索引擎</el-menu-item>
        <el-menu-item index="/adminManger/goCloudAndKafka">DEX商城服务端控制界面</el-menu-item>
      </el-submenu>

      <el-menu-item style="float: right;right: 40px;" @click="LoginOut">退出</el-menu-item>
      <el-menu-item style="float: right;right: 70px;">{{ this.$store.state.adminInfo.account }}</el-menu-item>
    </el-menu></div>
    <div>
      <router-view></router-view>
    </div>

  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      activeIndex: '1'
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    LoginOut(){
      sessionStorage.clear();
      this.$store.state.login=false;
      this.$message.success("数据清除成功，正在退出至登陆页面");
      this.$router.push({
        name:'login'
      })
    }
  }
}
</script>

<style scoped>

</style>