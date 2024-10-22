<template>
  <div id="part">
      <!--商标部分-->
      <div class="dex-logo" @click="GoHome" style="cursor: pointer">
<!--        <img src="/static/images/dex-logo1.png" alt="">-->
        <img :src="this.logo" alt="">
      </div>
      <!--登录-->
      <div class="person">

          <div v-if="!this.$store.state.login">
            <a><span @click="GoLogin">登录</span></a>
            <el-divider direction="vertical"></el-divider>
            <a><span @click="GoRegister">注册</span></a>
          </div>

        <a>
          <div v-if="this.$store.state.login" @click="GoUser" :class="[clickChangeColor==='user'?'clickColor':'']"><span>{{this.$store.state.userInfo.nickname}}</span></div>
        </a>
      </div>
      <div>
        <a @click="GoCart">购物车</a>
      </div>
      <!--快速跳转链接-->
      <div>
        <a><span :class="[clickChangeColor==='ware'?'clickColor':'']" @click="GoWares()">全部商品</span></a>
      </div>
      <!--搜索栏-->
      <div class="research">
        <input type="text" class="input01" :placeholder="pl" @click="text" v-model="researchWord"><input @click="search"  type="submit" class="input02">
      </div>

      <div>
        <a><span v-if="this.$store.state.login" @click="loginOut">退出DEX商城</span></a>
      </div>

      <!--输入信息，显示推荐文字-->
      <div :class="[this.$store.state.login?'prompt0':'prompt']" v-show="prompt">
        <ul>
          <li v-for="(o,i) in promptData" @click="GoDetail(o.id)">{{o.ware_name}}</li>
        </ul>
      </div>

      <el-dialog
          title="注册提示"
          :visible.sync="NoLogin"
          width="30%"
          :before-close="handleClose">
        <span>系统检测您尚未登录，您需先进行登录才能查看你的购物车</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="NoLogin = false">取 消</el-button>
          <el-button type="primary" @click="GoLogin">确 定</el-button>
      </span>
      </el-dialog>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: "shopHeader",
  data(){
    return {
        pl:"大家都在搜:空调降价",
        prompt:false,
        researchWord:'',
        promptData:[],
        NoLogin:false,
      /*点击显示颜色提示*/
        clickChangeColor:'',
      /*商城logo*/
        logo:'',
    }
  },
  created() {
    console.log("刷新",this.$store.state.userInfo);
    this.getDexSearch()
    this.getDexLogo()
  },
  methods:{
    text(){
      if (this.prompt){
        this.prompt=false;
      }else {
        this.prompt=true;
      }
    },
    GoWares:function () {
      this.$router.push({
        name:'wares',
      });
      this.clickChangeColor='ware';
    },
    GoCart:function (){
      if (!this.$store.state.login){
        this.NoLogin=true
      }else {
        this.$router.push({
          name:'myCart'
        })
      }

    },
    GoLogin:function (){
      this.$router.push({
        name:'login'
      })
    },GoUser:function (){
      this.clickChangeColor='user';
      this.$router.push({
        name:'user'
      });

    },
    GoHome:function () {
      this.$router.push({
        name:'home'
      })
    },
    GoDetail:function (id) {
      this.$router.push({
        name:'wareDetail',
        query:{
          ware_id:id
        }
      })
    },
    GoRegister:function () {
      this.$router.push({
        name:'userRegister'
      })
    },
    /*搜索跳转到全部商品页面*/
    search:function () {
      this.$router.push({
        name:'wares',
        query:{
          ware_name:this.researchWord
        }
      })
    },
    /*用户退出Dex商城*/
    loginOut(){
      this.axios.post('http://localhost:8681/dex/user/loginOut',this.$qs.stringify({
        user_id:this.$store.state.userInfo.id
      }))
      sessionStorage.clear();
      this.$store.commit("clearAllMessage")
      this.$router.push({
        name:'home'
      })
    },
    /*关闭登录框*/
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    /*推荐搜索商品*/
    async getDexSearch(){
      const res =await this.axios.post('http://localhost:8680/dex/system/getDexSearch')
      if (res.data.code === 200){
        this.pl = res.data.object;
      }
    },
    /*dex商城Logo*/
    async getDexLogo(){
      const res = await this.axios.post('http://localhost:8680/dex/system/getDexLogo')
      if (res.data.code === 200){
        this.logo = res.data.object
      }
    }
  },
  watch:{
    async researchWord(newName,oldName){
      const res=await axios.get(`http://localhost:8688/dex/wares/getPromptWare?ware_name=${this.researchWord}`)
      this.promptData=res.data.object
      this.prompt=true
      console.log("下拉栏的数据",res);
    }
  }

}
</script>

<style scoped>
  a:hover{
    color: #409EFF;
  }
  .clickColor{
    color: #409EFF;
  }
  span{
    cursor:pointer
  }
  input{
    outline: none;
  }
  #part{
    width:1684.440px;
    height: 100px;
    display: flex;
    justify-content: space-around;
    align-items: center;
    overflow: hidden;
    background-color: white;
  }
  .dex-logo{
    width: 200px;
    height: 100px;
  }
  .dex-logo img{
    width: 200px;
    height: 100px;
  }

  .research{
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .research .input01{
    width: 420px;
    height: 44px;
    border-top: #000000 4px solid;
    border-left: #000000 4px solid;
    border-bottom: #000000 4px solid;
    border-right: #000000 1px solid;
    border-top-left-radius: 16px;
    border-bottom-left-radius: 16px;
    font-size: 20px;
  }
  .research .input02{
    width: 140px;
    height: 54px;
    background-color: #000000;
    border-top: #000000 1px solid;
    border-left: #000000 1px solid;
    border-bottom: #000000 1px solid;
    border-top-right-radius: 16px;
    border-bottom-right-radius: 16px;
    color: white;
    font-weight: 100;
    font-size: 24px;
    cursor: pointer;
  }

  .prompt{
    width: 420px;
    background-color: white;
    position: absolute;
    top: 82px;
    right: 320px;
    box-shadow: 0px 0px 3px #000;
    border-bottom-left-radius: 8px;
    border-bottom-right-radius: 8px;
    z-index: 100;
    cursor: pointer;
  }

  .prompt0{
    width: 420px;
    background-color: white;
    position: absolute;
    top: 82px;
    right: 400px;
    box-shadow: 0px 0px 3px #000;
    border-bottom-left-radius: 8px;
    border-bottom-right-radius: 8px;
    z-index: 100;
    cursor: pointer;
  }

  .prompt ul{
    padding-left: 20px;
  }

  .prompt li{
    padding-top: 8px;
    cursor: pointer;
  }
</style>