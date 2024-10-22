<template>
  <div>
    <el-card shadow="hover" :body-style="{padding:'0px'}" class="mode-chart">
      <div  ref="oneEChart" class="one-echart"></div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "dataEchart",
  props:['refData'],
  data(){
    return{
      oneData:[]
    }
  },
  created() {
    this.DexOnePanleData();
  },
  methods:{
    async DexOnePanleData(){
      const res = await this.axios.post('DexAdmin/DexOnePanleData')
      if (res.data.code === 200){
        this.oneData = res.data.object;
        console.log(this.oneData);
        this.oneEChart()
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
      const oneEChart = this.$refs.oneEChart
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(oneEChart)
      // 绘制图表
      myChart.setOption({
        title: {
          text: '七天出货量销售量总表',
          left: 'center',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {            // Use axis to trigger tooltip
            type: 'shadow'        // 'shadow' as default; can also be 'line' or 'shadow'
          }
        },
        legend: {
          data: ['普通购买', '秒杀购买', '购买总金额', '秒杀总金额'],
          top: '24'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          data: [this.getNextDate(-6), this.getNextDate(-5), this.getNextDate(-4), this.getNextDate(-3), this.getNextDate(-2), this.getNextDate(-1), this.getNextDate(0)]
        },
        series: [
          {
            name: '普通购买',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: this.oneData[0].orders
          },
          {
            name: '秒杀购买',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: this.oneData[0].bonusOrders
          },
          {
            name: '购买总金额',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: this.oneData[0].ordersSale
          },
          {
            name: '秒杀总金额',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: this.oneData[0].bonusOrdersSale
          }
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

  .one-echart{
    width: 480px;
    height: 460px;
  }
</style>