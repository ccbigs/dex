<template>
  <div class="bus-man">
    <el-container>
      <el-header class="bus-header">
        <div class="header-title">DEX商家后台
          <i class="el-icon-s-fold" v-if="!isCollapse" @click="aside"></i>
          <i class="el-icon-s-unfold" v-else @click="aside"></i>
        </div>
        <div class="header-right">
          <div class="right-name">
            <div class="right-image"> <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar></div>
            <div>{{ this.$store.state.busmanInfo.bus_name }}</div>
          </div>
          <div @click="GoBusIndex"><a>前往生意参谋面板</a></div>
          <div @click="loginOut"><a><i class="el-icon-switch-button"></i>退出</a></div>
        </div>
      </el-header>
      <el-container>
        <!--侧边栏-->
        <div :class="{bus_menu:menu}" >
          <el-menu
              default-active="1-1"
              class="el-menu-vertical-demo"
              hide-timeout=5000
              @open="handleOpen"
              @close="handleClose"
              active-text-color="#409EFF"
              :collapse="isCollapse">
            <el-submenu index="0" >
              <template #title>
                <i class="el-icon-message-solid"></i>
                <span>DEX商城新闻</span>
              </template>
              <router-link to="/busMain/InformationShow">
                <el-menu-item index="0-1"><i class="el-icon-chat-line-round"></i>DEX新闻查看</el-menu-item>
              </router-link>
            </el-submenu>
            <el-submenu index="1" >
              <template #title>
                <i class="el-icon-s-shop"></i>
                <span>商品管理</span>
              </template>
              <router-link to="/busMain/wareSale">
                <el-menu-item index="1-1" @click="TabChoose='1-2'"><i class="el-icon-goods"></i>全部商品</el-menu-item>
              </router-link>
              <router-link to="/busMain/wareAdd">
                <el-menu-item index="1-2"><i class="el-icon-plus"></i>商品上线</el-menu-item>
              </router-link>
              <router-link to="/busMain/stanardManager">
                <el-menu-item index="1-3"><i class="el-icon-files"></i>商品规格管理</el-menu-item>
              </router-link>
            </el-submenu>
            <el-submenu index="2" >
              <template #title>
                <i class="el-icon-s-claim"></i>
                <span>订单管理</span>
              </template>
              <router-link to="/busMain/orderDid">
              <el-menu-item index="2-1"><i class="el-icon-tickets"></i>全部订单</el-menu-item>
              </router-link>
              <router-link to="/busMain/orderSearch">
              <el-menu-item index="2-2"><i class="el-icon-search"></i>订单搜索</el-menu-item>
              </router-link>
            </el-submenu>
            <el-submenu index="3" >
              <template #title>
                <i class="el-icon-s-marketing"></i>
                <span>活动促销</span>
              </template>
              <router-link to="/busMain/activeDid">
              <el-menu-item index="3-1"><i class="el-icon-document"></i>参与商品</el-menu-item>
              </router-link>

              <router-link to="/busMain/activeAdd">
              <el-menu-item index="3-2"><i class="el-icon-document-add"></i>活动商品上线</el-menu-item>
              </router-link>
            </el-submenu>
            <el-submenu index="4" >
              <template #title>
                <i class="el-icon-s-finance"></i>
                <span>广告位购买</span>
              </template>
              <router-link to="/busMain/hpCarouselDid">
                <el-menu-item index="4-1"><i class="el-icon-postcard"></i>已购轮播图商位</el-menu-item>
              </router-link>
              <router-link to="/busMain/hpRecommendDid">
                <el-menu-item index="4-2"><i class="el-icon-postcard"></i>已购推荐商位</el-menu-item>
              </router-link>
              <router-link to="/busMain/hpPurchase">
                <el-menu-item index="4-3"><i class="el-icon-sold-out"></i>商位购买</el-menu-item>
              </router-link>
            </el-submenu>
            <el-submenu index="5" >
              <template #title>
                <i class="el-icon-s-marketing"></i>
                <span>直播带货商品</span>
              </template>
              <router-link to="/busMain/bonusAdd">
                <el-menu-item index="5-1"><i class="el-icon-plus"></i>加入商品</el-menu-item>
              </router-link>
              <router-link to="/busMain/bonusDid">
                <el-menu-item index="5-2"><i class="el-icon-finished"></i>已上线商品</el-menu-item>
              </router-link>
              <router-link to="/busMain/bonusOrder">
                <el-menu-item index="5-3"><i class="el-icon-document-checked"></i>秒杀订单</el-menu-item>
              </router-link>
            </el-submenu>
            <el-submenu index="6" >
              <template #title>
                <i class="el-icon-user-solid"></i>
                <span>商家信息</span>
              </template>
              <router-link to="/busMain/busmanData">
                <el-menu-item index="6-1"><i class="el-icon-document-checked"></i>我的信息</el-menu-item>
              </router-link>
              <router-link to="/busMain/busmanPassword">
                <el-menu-item index="6-2"><i class="el-icon-document-checked"></i>修改密码</el-menu-item>
              </router-link>
            </el-submenu>
          </el-menu>
        </div>
        <el-main style="">
          <bus-tabs :tab-choose="TabChoose"></bus-tabs>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
<!--    <busman-foot></busman-foot>-->
  </div>
</template>

<script>
import busTabs from "../../components/busman/busTabs";
import busmanFoot from "../../components/busman/busmanFoot";
export default {
  name: "index",
  components:{
    'busTabs':busTabs,
    'busmanFoot':busmanFoot,
  },
  data() {
    return {
      isCollapse: true,
      menu:false,
      TabChoose:'1-1',
    }
  },
  mounted() {

  },
  methods:{
    GoBusIndex(){
      this.$router.push({
        name:'busIndex'
      })
    },
    aside(){
      this.isCollapse=!this.isCollapse;
      this.menu=!this.menu;
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    loginOut(){
      sessionStorage.clear();
      this.$message.success("数据清除成功，正在退出至登陆页面");
      this.$router.push({
        name:'login'
      })
    },
    TackeTabs(data){
      this.TabChoose=data
    }
  },

}
</script>

<style scoped>
  .bus-man{
    height: 811px;
  }
  .bus-header{
    text-align: right;
    font-size: 12px;
    background-color: #222222;
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: white;
  }
  .bus-header .header-title{
    font-size: 34px;
    color: white;
    margin-left: 20px;
  }
  .bus-header .header-right{
    display: flex;
    align-items: center;
    justify-content: space-around;
    font-size: 14px;
  }
  .header-right div{
    margin-right: 30px;
    cursor: pointer;
  }
  .right-image{
    display: flex;
    justify-content: center;
  }

  .bus_menu{
    width: 200px;
    /*background-color: #304155;*/
  }

  .bus-aside{
    width:200px;
    height: 751px;
    background-color: #304155;
  }
</style>