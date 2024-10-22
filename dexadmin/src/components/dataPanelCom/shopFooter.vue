<template>
  <div class="foot">
    <div class="foot-second">
      <!--第一段标签语-->
      <div class="foot-one">
        <div class="one-demo"><img src="/static/image/home_one.png" alt=""><div>官方商城 品质保证</div></div>
        <div class="one-demo"><img src="/static/image/home_two.png" alt=""><div>7天退货 15天换货</div></div>
        <div class="one-demo"><img src="/static/image/home_three.png" alt=""><div>48元起免运费</div></div>
        <div class="one-demo"><img src="/static/image/home_four.png" alt=""><div>1800+服务 全国联保</div></div>
      </div>
      <el-divider></el-divider>

      <div class="foot-two">
        <!--推荐产品-->
        <div v-for="(demo) in footTableDate">
          <ul>
            <li><b>{{ demo.tabName }}</b></li>
              <li style="color: #888888;" v-for="(tab,i) in demo.sonChain" @click="GoTabInfo(tab)">{{tab.tabName}}</li>
          </ul>
        </div>

        <div style="height:200px;border-left:3px solid #888888;margin-top: 16px; "></div>
        <!---->
        <div class="hot-content">
          <p>商城热线</p>
          <p>888 8888 888</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "shopFooter",
  props:['footTableDate'],
  data(){
    return{
      recommend:['手机','笔记本','平板','智慧屏','智能穿戴','智能家居'],
      brand:['关于DEX','新闻','活动','视频','关于我们'],
      shopRead:['购物指南','退换货政策','配送方式','支付方式'],
      support:['报修政策','价格保护规则']
    }
  },
  watch:{
    footTableDate(oldName,newName){
      this.footTableDate = newName
    }
  },
  methods:{
    GoTabInfo(tab){
      this.$parent.handleEdit(tab);
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