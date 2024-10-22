<template>
  <div>
    <el-card shadow="hover" :body-style="{padding:'0px'}" class="mode-chart">
      <div ref="twoEChart" class="three-echart"></div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "dataEchartThree",
  props:['Data'],
  data(){
    return{
      threeData:[],
    }
  },
  async created() {
    await this.DexTherePanleData();
  },
  methods:{
    async DexTherePanleData(){
      const res = await this.axios.post('DexAdmin/DexTherePanleData')
      if (res.data.code === 200){
        this.threeData = res.data.object
        this.oneEChart()
        console.log(this.threeData);
      }
    },
    /*获取时间*/
    getNextDate(day) {
      let dd = new Date();
      dd.setDate(dd.getDate() + day);
      let d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
      return d;
    },
    oneEChart(){
      const twoEChart = this.$refs.twoEChart
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(twoEChart)
      // 绘制图表
      myChart.setOption({
        title: {
          text: '商家用户活跃数据'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          data: ['用户每日活跃数据', '商家每日活跃数据'],
          top:'24'
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: [this.getNextDate(-6), this.getNextDate(-5), this.getNextDate(-4), this.getNextDate(-3), this.getNextDate(-2), this.getNextDate(-1), this.getNextDate(0)]
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '用户每日活跃数据',
            type: 'line',
            stack: '总量',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            data: this.threeData.userDate
          },
          {
            name: '商家每日活跃数据',
            type: 'line',
            stack: '总量',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            data: this.threeData.busmanDate
          },
        ]
      })
    },
  }
}
</script>

<style scoped>
  .mode-chart{
    width: 480px;
    height: 460px;
    padding: 10px;
  }

  .three-echart{
    width: 480px;
    height: 460px;
  }
</style>