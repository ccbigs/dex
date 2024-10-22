<template>
  <div>
    <el-card shadow="hover" :body-style="{padding:'0px'}" class="mode-chart">
      <div ref="oneEChart" class="one-echart"></div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "shopHomeOneVolume",
  data(){
    return{
      oneData:[],
    }
  },
  created() {
    this.getOneData()
  },
  methods:{
    async getOneData(){
      const res = await this.axios.post('http://localhost:8680/dex/home/getOneData')

      this.oneData = res.data;
      console.log(res);
      this.OneEcharts();
    },
    /*用来展示网红之间的带货量，带货排序8680*/
    OneEcharts(){
      const oneEChart = this.$refs.oneEChart
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(oneEChart)
      // 绘制图表
      myChart.setOption({
        title: {
          text: '网红带货排行榜',
          subtext: 'Dex商城提供计算引擎'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['成交额(元)']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: this.oneData.name
        },
        series: [
          {
            name: '成交额(元)',
            type: 'bar',
            data: this.oneData.value
          }
        ]
      })

    }
  }
}
</script>

<style scoped>
  .mode-chart{
    width: 600px;
    height: 500px;
    padding: 10px;
  }
  .one-echart{
    width: 580px;
    height: 480px;
  }
</style>