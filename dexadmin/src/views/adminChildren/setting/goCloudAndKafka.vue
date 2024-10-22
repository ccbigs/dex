<template>
  <div>
    <div class="demo">
      <div class="demo-top">
        <el-card class="demo-top-one" :body-style="{padding:'5px'}">
          <el-button type="warning" round @click="GoKafKa()">前往DEX商城KafKa控制面板</el-button>
        </el-card>
        <el-card class="demo-top-two" :body-style="{padding:'5px'}">
          <el-button type="warning" round @click="GoNacos()">前往DEX商城Nacos控制面板</el-button>
        </el-card>
      </div>
      <div class="demo-bottom">
        <el-card class="demo-bottom-one" :body-style="{padding:'5px'}">
          <el-button type="warning" round @click="GoSentinel()">前往DEX商城Sentinel控制面板</el-button>
        </el-card>
        <el-card class="demo-bottom-two" :body-style="{padding:'5px'}">
          <el-button type="warning" round @click="GoZabbix()">前往DEX商城Zabbix控制面板</el-button>
        </el-card>
      </div>
    </div>
    <admin-foot style="position: absolute;bottom: 0px;"></admin-foot>
  </div>

</template>

<script>
import adminFoot from "../../../components/adminFoot";
export default {
  name: "goCloudAndKafka",
  components:{
    'adminFoot':adminFoot,
  },
  methods:{
    GoKafKa(){
      window.open("http://172.20.17.41:8048/ke", '_blank');
    },
    GoNacos(){
      window.open("http://172.20.17.42:8848/nacos", '_blank');
      //window.location.href = "http:172.20.17.42:8848"
    },
    GoSentinel(){
      window.open("http://172.20.17.42:8086", '_blank');
    },
    GoZabbix(){
      window.open("http://172.20.17.41",'_blank')
    }
  }
}
</script>

<style scoped>
  .demo{
    margin: 10px auto;
    width: 1536.670px;
    padding: 30px 20px 20px 20px;
    height: 620px;
    background-color: white;
  }

  .demo-top{
    height: 260px;
    display: flex;
    justify-content: space-around;
    align-items: center;
    margin-top: 20px;
    margin-bottom: 30px;
  }

  .demo-top div{
    height: 220px;
    width: 400px;
  }

  .demo-bottom {
    height: 260px;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }

  .demo-bottom div{
    height: 220px;
    width: 400px;
  }

  .demo-top-one{
    background: url('/static/image/kafka.jpeg');
    background-size: 100% 100%;
    display: flex;
    align-items: end;
    justify-content: right;
    margin-bottom: 5px;
  }

  .demo-top-two{
    background: url('/static/image/nacos.png');
    background-size: 100% 100%;
    display: flex;
    align-items: end;
    justify-content: right;
  }

  .demo-bottom-one{
    background: url('/static/image/sentinel.png');
    background-size: 100% 100%;
    display: flex;
    align-items: end;
    justify-content: right;
  }

  .demo-bottom-two{
    background: url('/static/image/zabbix.jpeg');
    background-size: 100% 100%;
    display: flex;
    align-items: end;
    justify-content: right;
    margin-bottom: 5px;
  }


</style>