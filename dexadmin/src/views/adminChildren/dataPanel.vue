<template>
  <div>
    <!--商家数据-->
    <div class="data-one">
      <el-card shadow="hover" class="one-admin">
        <div class="reload-icon">
          <img src="/static/image/reload.png" alt="" title="点击刷新面板数据" @click="reloadDataPanleData">
        </div>
        <div class="mode">
          <div class="block"><el-avatar :size="60" :src="circleUrl"></el-avatar></div>
        </div>
        <div class="mode"><div>欢迎你:{{ this.$store.state.adminInfo.account }}</div></div>
        <div></div>
      </el-card>
      <div class="one-admin">
       <data-show :img="'/static/image/DataUser.png'" :dec="'用户总人数'" :data="PanleTopData.allUser"></data-show>
       <data-show class="dataShowStyle" :img="'/static/image/DataBusman.png'" :dec="'入驻商家总数'" :data="PanleTopData.allBusman"></data-show>
      </div>
      <div class="one-admin">
        <data-show :img="'/static/image/DataOrder.png'" :dec="'当前订单总数'" :data="PanleTopData.allOrder"></data-show>
        <data-show class="dataShowStyle" :img="'/static/image/DataComment.png'" :dec="'累计评论总数'" :data="PanleTopData.allComment"></data-show>
      </div>
      <div class="one-admin">
        <data-show :img="'/static/image/DataSumMoney.png'" :dec="'累计交易金额'" :data="PanleTopData.allSumTurnover"></data-show>
        <data-show class="dataShowStyle" :img="'/static/image/DataActually.png'" :dec="'实际成交额'" :data="PanleTopData.allActuallyTurnover"></data-show>
      </div>
      <div class="one-admin">
        <data-show :img="'/static/image/Dataqut.png'" :dec="'商位剩余数'" :data="PanleTopData.limitQutient"></data-show>
        <data-show class="dataShowStyle" :img="'/static/image/DataQutAct.png'" :dec="'商位成交额'" :data="PanleTopData.allQutientTurnover"></data-show>
      </div>

    </div>

    <!--第二块数据-->
    <div class="data-two">
      <echart-one></echart-one>
      <echart-two></echart-two>
      <echart-three></echart-three>
    </div>

    <!--第三块，最下方视图-->
      <admin-foot style="position: absolute;bottom: 0px;"></admin-foot>
  </div>
</template>

<script>
import dataShow from "../../components/dataPanelCom/dataShow";
import dataEchartOne from "../../components/dataPanelCom/dataEchartOne";
import dataEchartTwo from "../../components/dataPanelCom/dataEchartTwo";
import dataEchartThree from "../../components/dataPanelCom/dataEchartThree";
import adminFoot from "../../components/adminFoot";
export default {
  name: "dataPanel",
  components:{
    'dataShow':dataShow,
    'echartOne':dataEchartOne,
    'echartTwo':dataEchartTwo,
    'echartThree':dataEchartThree,
    'adminFoot':adminFoot,
  },
  data(){
    return{
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      PanleTopData:'',
    }
  },
  async created() {
    await this.getAllPanleTop();
  },
  methods:{
     async getAllPanleTop(){
      const res=await this.axios.post('DexAdmin/GetAllPanleTop');
      this.PanleTopData=res.data.object
    },
    async reloadDataPanleData(){
      const res=await this.axios.post('DexAdmin/PanelDataSynchronization');
      if (res.data.code === 200){
        await this.getAllPanleTop();
        this.$message.success("面板数据更新成功")
      }

    }
  }
}
</script>

<style scoped>
  .data-one{
    height: 180px;
    padding: 10px;
    display: flex;
  }

  .one-admin{
    width: 280px;
    height: 180px;
    margin-left: 46px;
  }

  .one-admin .mode{
    display: flex;
    justify-content: center;
    margin-top: 10px;
  }

  .dataShowStyle{
    margin-top: 24px;
  }
/*第二块css*/
  .data-two{
    height: 460px;
    margin-top: 20px;
    margin-left: 52px;
    margin-right: 64px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .reload-icon{
    width: 20px;
    height: 20px;
    display: flex;
    justify-content: flex-end;
  }
  .reload-icon img{
    width: 20px;
    height: 20px;
  }
</style>