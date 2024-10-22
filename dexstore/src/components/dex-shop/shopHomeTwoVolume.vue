<template>
  <div>
    <el-card shadow="hover" :body-style="{padding:'0px'}" class="mode-chart">
      <div ref="twoEChart" class="two-echart"></div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "shopHomeTwoVolume",
  data(){
    return{
      twoData:[],
    }
  },
  created() {
    this.getTwoData()
  },
  methods:{
    async getTwoData(){
      const res=await this.axios.post('http://localhost:8680/dex/home/getTwoData')

      this.twoData = res.data

      this.twoData.userActive.unshift("用户活跃数据")
      this.twoData.busmanActive.unshift("商家活跃数据")
      this.twoData.touristActive.unshift("秒杀活跃数据")
      console.log(res)

      this.twoEcharts()
    },
    /*获取时间*/
    getNextDate(day) {
      let dd = new Date();
      dd.setDate(dd.getDate() + day);
      let d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();

      return d.toString();
    },
    twoEcharts(){
      const twoEChart = this.$refs.twoEChart
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(twoEChart)
      // 绘制图表
      myChart.setOption({
        title:{
          text: 'Dex商城用户活跃量',
        },
        legend: {
          top:'20'
        },
        tooltip: {
          trigger: 'axis',
          showContent: false
        },
        dataset: {
          source: [
            ['date', this.getNextDate(-6), this.getNextDate(-5), this.getNextDate(-4), this.getNextDate(-3), this.getNextDate(-2), this.getNextDate(-1), this.getNextDate(0)],
            this.twoData.userActive,
            this.twoData.busmanActive,
            this.twoData.touristActive,
          ]
        },
        xAxis: {type: 'category'},
        yAxis: {gridIndex: 0},
        grid: {top: '55%'},
        series: [
          {type: 'line', smooth: true, seriesLayoutBy: 'row', emphasis: {focus: 'series'}},
          {type: 'line', smooth: true, seriesLayoutBy: 'row', emphasis: {focus: 'series'}},
          {type: 'line', smooth: true, seriesLayoutBy: 'row', emphasis: {focus: 'series'}},
          {
            type: 'pie',
            id: 'pie',
            radius: '30%',
            center: ['50%', '25%'],
            emphasis: {focus: 'data'},
            label: {
              formatter: '{b}: {@2021} ({d}%)'
            },
            encode: {
              itemName: 'date',
              value: '2021',
              tooltip: '2021'
            }
          }
        ]
      })
      myChart.on('updateAxisPointer', function (event) {
        var xAxisInfo = event.axesInfo[0];
        if (xAxisInfo) {
          var dimension = xAxisInfo.value + 1;
          myChart.setOption({
            series: {
              id: 'pie',
              label: {
                formatter: '{b}: {@[' + dimension + ']} ({d}%)'
              },
              encode: {
                value: dimension,
                tooltip: dimension
              }
            }
          });
        }
      });

     /* myChart.setOption(option);*/
    },

  }
}
</script>

<style scoped>
  .mode-chart{
    width: 800px;
    height: 500px;
    padding: 10px;
  }

  .two-echart{
    width: 800px;
    height: 500px;
  }


</style>