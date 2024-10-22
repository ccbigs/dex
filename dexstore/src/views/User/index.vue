<template>
  <div>
    <shop-header></shop-header>
    <div class="user">
      <div class="user-left">
        <!--头像和用户名-->
        <div class="left-one">
          <div class="one-image"><el-avatar shape="square" :size="100" :src="user.headimage"></el-avatar></div>
          <div class="one-name"><div>{{user.nickname}}</div></div>
        </div>
        <el-divider></el-divider>
        <!--我的消息-->
        <div class="left-message">
          <span style="font-size: 20px;font-weight: bold">DEX商城公告信息</span>
          <ul>
            <router-link to="/user/InformationShow"><li><a @click="clickChange(0)" :class="{redColor:mouseclick===0}">信息中心</a></li></router-link>
            <router-link to="/user/dataPanle"><li><a @click="clickChange(0.5)" :class="{redColor:mouseclick===0.5}">开放数据分析</a></li></router-link>
          </ul>
        </div>
        <!--我的订单-->
        <el-divider></el-divider>
        <div>
          <span style="font-size: 20px;font-weight: bold">秒杀收益</span>
          <ul>
            <router-link to="/user/bonusDate"><li><a @click="clickChange(13)" :class="{redColor:mouseclick===13}">秒杀收益总计</a></li></router-link>
          </ul>
        </div>
        <el-divider></el-divider>
        <div>
          <span style="font-size: 20px;font-weight: bold">我的订单</span>
          <ul>
            <router-link to="/user/orAll"><li><a @click="clickChange(3)" :class="{redColor:mouseclick===3}">全部订单</a></li></router-link>
            <router-link to="/user/orTransit"><li><a @click="clickChange(5)" :class="{redColor:mouseclick===5}">运输中订单</a></li></router-link>
            <router-link to="/user/orDid"><li><a @click="clickChange(4)" :class="{redColor:mouseclick===4}">已签收订单</a></li></router-link>
            <router-link to="/user/orReturn"><li><a @click="clickChange(6)" :class="{redColor:mouseclick===6}">退换货订单</a></li></router-link>
          </ul>
        </div>
        <!--地址管理-->
        <el-divider></el-divider>
        <div>
          <span style="font-size: 20px;font-weight: bold">地址管理</span>
          <ul>
            <router-link to="/user/addressUser"><li><a @click="clickChange(7)" :class="{redColor:mouseclick===7}">我的地址</a></li></router-link>
            <router-link to="/user/addressAdd"><li><a @click="clickChange(8)" :class="{redColor:mouseclick===8}">新增地址</a></li></router-link>
          </ul>
        </div>
        <!--评论管理-->
        <el-divider></el-divider>
        <div>
          <span style="font-size: 20px;font-weight: bold">评论管理</span>
          <ul>
            <router-link to="/user/commentUser"><li><a @click="clickChange(9)" :class="{redColor:mouseclick===9}">我的评论</a></li></router-link>
            <router-link to="/user/commentPublic"><li><a @click="clickChange(10)" :class="{redColor:mouseclick===10}">发布评论</a></li></router-link>
          </ul>
        </div>
        <!--个人信息-->
        <el-divider></el-divider>
        <div>
          <span style="font-size: 20px;font-weight: bold">我的资料</span>
          <ul>
            <router-link to="/user/dataUser"><li><a @click="clickChange(11)" :class="{redColor:mouseclick===11}">我的信息</a></li></router-link>
            <router-link to="/user/dataModify"><li><a @click="clickChange(12)" :class="{redColor:mouseclick===12}">账号与安全</a></li></router-link>
          </ul>
        </div>
      </div>

      <div class="user-right"><router-view></router-view></div>
    </div>
    <shop-foot></shop-foot>
  </div>
</template>

<script>
import shopHeader from '../../common/dex-shop/shopHeader'
import shopUserFoot from "../../common/dex-shop/shopUserFoot";


export default {
  name: "index",
  data(){
    return {
      user:'',
      mouseclick:0,
      mouseEnter:0,
    }
  },
  created() {
    this.user=this.$store.state.userInfo
  },
  components:{
    'shopHeader':shopHeader,
    'shopFoot':shopUserFoot,
  },
  methods:{
    clickChange(click){
      this.mouseclick = click;
      console.log("点击了")
    },
    enterChange(enter){
      this.mouseEnter=enter;
    },
    reSet(){
      this.mouseEnter=0;
    },
  }
}
</script>

<style scoped>
  ul{
    padding-left: 20px;
  }
  li{
    margin-top: 16px;
    font-size: 20px;
    cursor: pointer;
  }
  #red{
    color: red;
  }
  .redColor{
    color: red;
  }
  .user{
    width: 1506.670px;
    margin: 20px 100px 20px 100px;
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
  }

  .user-left{
    width: 200px;
    padding: 10px 20px 10px 20px;
    background-color: white;
  }
  .left-one{
    padding-top: 10px;
  }
  .left-one .one-image{
    width: 100px;
    height: 100px;
    margin-left: 50px;
    border: 1px solid #eeeeee;
    border-radius: 10px;
  }
  .left-one .one-name{
    margin-top: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .user-right{
    width: 1200px;
    padding: 10px 20px 10px 20px;
    background-color: white;
  }
  a{
    color: #888888;
  }
  a:hover{
    color: red;
  }



</style>