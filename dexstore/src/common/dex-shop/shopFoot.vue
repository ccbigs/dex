<template>
  <div class="foot">
    <div class="foot-second">
      <!--第一段标签语-->
      <div class="foot-one">
        <div class="one-demo"><img src="/static/images/home_one.png" alt=""><div>官方商城 品质保证</div></div>
        <div class="one-demo"><img src="/static/images/home_two.png" alt=""><div>7天退货 15天换货</div></div>
        <div class="one-demo"><img src="/static/images/home_three.png" alt=""><div>48元起免运费</div></div>
        <div class="one-demo"><img src="/static/images/home_four.png" alt=""><div>1800+服务店 全国联保</div></div>
      </div>
      <el-divider></el-divider>

      <div class="foot-two" :key="compentKey">
        <!--推荐产品-->
        <div v-for="(demo,i) in footData" :key="i">
          <ul>
            <li><b>{{ demo.tabName }}</b></li>
            <li style="color: #888888;" v-for="(tab,i) in demo.sonChain" @click="GoInfo(tab)">{{tab.tabName}}</li>
          </ul>
        </div>
        <div style="height:200px;border-left:3px solid #888888;margin-top: 16px; "></div>
        <!---->
        <div class="hot-content">
          <p>商城热线</p>
          <p>{{ phone }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "shopFooter",
  data(){
    return{
      footData:[],
      compentKey:0,
      phone:'',
    }
  },
  mounted() {
    this.getAllTab()
    this.getDexPhone()
  },
  methods:{
    async getAllTab(){
      const res = await this.axios.post('http://localhost:8680/dex/home/getAllFootTab')

      if (res.data.code === 200){
        this.asyxTab(res.data.object)
      }else {
        this.$message.warning("暂无标题信息")
      }
    },
    /*获取商城联系电话*/
    async getDexPhone(){
      const res = await this.axios.post('http://localhost:8680/dex/system/getDexPhone')

      if (res.data.code === 200){
        this.phone = res.data.object
      }
    },

    asyxTab(object){
      let index=0;
      /*遍历出一个数组*/
      for (let i = 0; i < object.length; i++) {
        if (object[i].tabType === '一级标题'){
          this.footData[index]= object[i];
          this.footData[index].sonChain = [];
          index++;
        }
      }

      /*数组里面再追加数组*/
      for (let i = 0; i < object.length; i++) {
        for (let j = 0; j <this.footData.length ; j++) {
          if (object[i].tabChain === this.footData[j].tabName){
            this.footData[j].sonChain.push(object[i])
          }
        }
      }


      console.log("=======================>")
      this.compentKey+=1;
    },
    /*跳转*/
    GoInfo(tab){
      this.$router.push({
        name:'shopPublicInfo',
        params:tab
      })
    }
  }
}
</script>

<style scoped>
  li{
    margin-bottom: 6px;
    cursor: pointer;
  }

  .foot{
    width: 1684.44px;
    height: 400px;
    background-color: white;
  }

  .foot-second{
    width: 1484.44px;
    height: 360px;
    margin: 30px 100px 0px 100px;
    padding: 30px 0px 0px 0px;
  }

  .foot-one{
    display: flex;
    justify-content: space-around;
    align-items: center;
  }

  .foot-one img{
    width: 50px;
    height: 50px;
    margin-right: 8px;
  }

  .foot-one .one-demo{
    display: flex;
    align-items: center;
    cursor: pointer;
  }


  .foot-two{
    display: flex;
    justify-content: space-around;
    align-items: flex-start;
  }

  .hot-content{
    font-size: 30px;
    font-weight: bold;
  }
</style>