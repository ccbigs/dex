<template>
  <div>
    <el-card shadow="hover" :body-style="{padding:'0px'}" class="mode-chart">
      <div ref="twoEChart" class="two-echart"></div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "dataEchartTwo",
  props:['Data'],
  data(){
    return{
      twoDate:[
        {name:"秒杀效益",value:2000}
      ],
    }
  },
  created() {
    this.getTwoPanleData()
  },
  methods:{
    async getTwoPanleData(){
      const res=await this.axios.post('DexAdmin/DexTwoPanleData')
      if (res.data.code === 200){
        this.twoDate = res.data.object;
        this.oneEChart()
      }
      console.log(res);
    },
    oneEChart(){
      const twoEChart = this.$refs.twoEChart
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(twoEChart)
      // 绘制图表
      myChart.setOption({
        title: {
          text: '秒杀效益与普通购买效益对比',
          left: 'center',
        },
        label:{ //饼图文字的显示
          show:true, //显示文字
          formatter:function (arg) {
            return arg.name+':'+arg.value+'元\n'+arg.percent+"%"
          },
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          bottom: 10,
          left: 'center',
          data: ['实际秒杀效益', '实际普通购买','普通总销售','秒杀总效益']
        },
        series: [
          {
            type: 'pie',
            radius: '65%',
            center: ['50%', '50%'],
            selectedMode: 'single',
            data: this.twoDate,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(255, 0, 0, 0.5)'
              }
            }
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

  .two-echart{
    width: 480px;
    height: 460px;
  }
</style>