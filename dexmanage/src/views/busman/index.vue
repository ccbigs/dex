<template>
  <div class="index">
    <div class="head">
      <div class="head-one">{{ showTime }}</div>
      <div class="head-two">DEX商家生意参谋</div>
      <div class="head-three" @click="GoMain" style="cursor: pointer;"><a>前往后台面板</a></div>
    </div>
    <!--第二模块-->
    <div class="chart-body">
      <div class="body-one">
        <div class="one-top" ref="one"></div>
        <div class="one-bottom" ref="two"></div>
      </div>
      <div class="body-two">
        <div class="middle-top">
          <div>
            <div style="color: red;font-size: 30px">{{ allSale }}</div>
            <div class="demo"><div>总成交额(元)</div></div>
          </div>
          <div>
            <div style="color: red;font-size: 30px">{{ orderNumber }}</div>
            <div class="demo">总订单数</div>
          </div>
          <div>
            <div style="color: red;font-size: 30px">{{ listings }}</div>
            <div class="demo">上架量</div>
          </div>
          <div>
            <div style="color: red;font-size: 30px">{{ allSaleNumber }}</div>
            <div class="demo">售出量</div>
          </div>
        </div>
        <div class="two-middle" ref="three"></div>
      </div>
      <div class="body-three">
        <div class="three-top" ref="four"></div>
        <div class="three-bottom" ref="five"></div>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "index",
  data(){
    return{
      dialogVisible:false,
      /*左上，总销售额，实际销售额*/
      SaleAll:[],
      SaleActually:[],
      /*第二块数据*/
      twoData:[],
      dataMax : [
        {
          name: '易用性',
          max: 100
        },
        {
          name: '功能',
          max: 100
        },
        {
          name: '拍照',
          max: 100
        },
        {
          name: '跑分',
          max: 100
        },
        {
          name: '续航',
          max: 100
        }
      ],
      chData : [],
      /*第三块数据内容*/
      listings:'',
      allSale:'',
      allSaleNumber:'',
      orderNumber:'',
      hits:'',
      /*第五块数库*/
      fiveDatas:[],
     /*第六块数据*/
      sixDatas:[],
      /*时间*/
      time:'',
      /*左上角的展示时间*/
      showTime:'',
      NowDay:'',
    }
  },
  async created() {
    clearInterval(this.timer)
    this.time = null
    this.setTimer();
    this.setShowTime();
    this.NowDay=new Date().getDate();

    await this.salesVolume();
    await this.getTwoData()
    await this.threeData();
    await this.fourData();
    await this.fiveData();
    await this.SixData();
  },
  methods:{
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    /*左边最上方的表盘*/
    oneTop(){
      const one = this.$refs.one
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(one)
      // 绘制图表
      myChart.setOption({
        title:{
          text:"销售数据",
          textStyle:{
            color: 'black'
          },
          left:"left",
        },
        legend:{
          data:[
            '总销售额','实际销售额'
          ]
        },
        tooltip:{
          trigger:'item',
          formatter: {}
        },
        toolbox:{
          feature:{
            restore:{}, //重置
            magicType: {
              type:['bar','line']
            },
          }
        },
        yAxis:{
          type:'value',
        },
        xAxis: {
          type:'category',
          data:['第一季度','第二季度','第三季度','第四季度'],
        },
        series:[
          {
            name:'总销售额',
            type:'bar',
            data:this.SaleAll,
            itemStyle:{
              color:'#E5DA42'
            },
            label:{show:true,position:'top'},
          },
          {
            name:'实际销售额',
            type:'bar',
            data:this.SaleActually,
            itemStyle:{
              color:'#5754ED'
            },
            label:{show:true,position:'top'},
          },


        ]
      })
    },
    /*左边下方的表盘*/
    oneBottom(){
      const two = this.$refs.two
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(two)
      myChart.setOption({
        title: {
          text: '普通销售与带货效益对比图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          top: '8%'
        },
        series: [
          {
            name: '访问来源',
            type: 'pie',
            radius: '50%',
            selectedMode: 'single',
            data: this.twoData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ],
      })
    },
    /*中间的地图*/
    async twoMiddle(){
      const three = this.$refs.three
      await this.axios.get('http://localhost:8081/static/lib/china.json').then((res)=>{
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.$echarts.init(three)
        this.$echarts.registerMap('chinaMap',res.data)
        myChart.setOption({
          geo:{
            type:'map',
            map:'chinaMap',
            roam:true,
            label:{
              show:true //展示每一个省份的标签
            },
            zoom:1,//初始化地图比例
            /*center:[],//中心点*/
          },
          series:[
            {
              data:this.chData,
              //将series下的数据和geo关联起来
              geoIndex:0, //将空气质量的数据和第0个geo配置关连起来
              type:'map',
            }
          ],
          visualMap:{
            max:0,
            min:300000,
            inRange:{
              color:['white','green']
            },//颜色渐变的范围
            calculable:true //出现滑块的效果
          },
        })
      })

    },
    /*右上的折线图√*/
    threeTop(){
      const four = this.$refs.four
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(four)
      myChart.setOption({
        color: ['#80FFA5', '#00DDFF', '#37A2FF', '#FF0087', '#FFBF00'],
        title: {
          text: '七日出货量',
          left:'center'
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
          data: [this.fiveDatas[0].ware_name, this.fiveDatas[1].ware_name, this.fiveDatas[2].ware_name, this.fiveDatas[3].ware_name, this.fiveDatas[4].ware_name],
          top:'20px',
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
            data: [this.getNextDate(-6), this.getNextDate(-5), this.getNextDate(-4), this.getNextDate(-3), this.getNextDate(-2), this.getNextDate(-1), this.NowDay],
          }
        ],
        yAxis: [
          {
            type: 'value',
          }
        ],
        series: [
          {
            name: this.fiveDatas[0].ware_name,
            type: 'line',
            stack: '总量',
            smooth: true,
            lineStyle: {
              width: 0
            },
            showSymbol: false,
            areaStyle: {
              opacity: 0.8,
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(128, 255, 165)'
              }, {
                offset: 1,
                color: 'rgba(1, 191, 236)'
              }])
            },
            emphasis: {
              focus: 'series'
            },
            data: this.fiveDatas[0].ware_number,
          },
          {
            name: this.fiveDatas[1].ware_name,
            type: 'line',
            stack: '总量',
            smooth: true,
            lineStyle: {
              width: 0
            },
            showSymbol: false,
            areaStyle: {
              opacity: 0.8,
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(0, 221, 255)'
              }, {
                offset: 1,
                color: 'rgba(77, 119, 255)'
              }])
            },
            emphasis: {
              focus: 'series'
            },
            data: this.fiveDatas[1].ware_number,
          },
          {
            name: this.fiveDatas[2].ware_name,
            type: 'line',
            stack: '总量',
            smooth: true,
            lineStyle: {
              width: 0
            },
            showSymbol: false,
            areaStyle: {
              opacity: 0.8,
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(55, 162, 255)'
              }, {
                offset: 1,
                color: 'rgba(116, 21, 219)'
              }])
            },
            emphasis: {
              focus: 'series'
            },
            data: this.fiveDatas[2].ware_number,
          },
          {
            name: this.fiveDatas[3].ware_name,
            type: 'line',
            stack: '总量',
            smooth: true,
            lineStyle: {
              width: 0
            },
            showSymbol: false,
            areaStyle: {
              opacity: 0.8,
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(255, 0, 135)'
              }, {
                offset: 1,
                color: 'rgba(135, 0, 157)'
              }])
            },
            emphasis: {
              focus: 'series'
            },
            data: this.fiveDatas[3].ware_number,
          },
          {
            name: this.fiveDatas[4].ware_name,
            type: 'line',
            stack: '总量',
            smooth: true,
            lineStyle: {
              width: 0
            },
            showSymbol: false,
            label: {
              show: true,
              position: 'top'
            },
            areaStyle: {
              opacity: 0.8,
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(255, 191, 0)'
              }, {
                offset: 1,
                color: 'rgba(224, 62, 76)'
              }])
            },
            emphasis: {
              focus: 'series'
            },
            data: this.fiveDatas[4].ware_number,
          }
        ]
      })
    },
    /*右下的饼状图*/
    threeBottom(){
      const five = this.$refs.five
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(five)
      myChart.setOption({
        title: {
          text: '上架商品库存数据(件)',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
        },
        series: [
          {
            name: '剩余库存',
            type: 'pie',
            radius: '50%',
            data: this.sixDatas,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      })
    },
    /*跳转到main页面*/
    GoMain(){
      clearInterval(this.time)
      this.time = null
      this.$router.push({
        name:'busMain'
      })
    },


    /*-----------------------------后端数据库传递信息部分---------------------------*/
    async salesVolume(){
      const res=await this.axios.post('visual/salesVolume',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))

      if(res.data.code === 200){
        this.SaleAll.splice(0,this.SaleAll.length)
        this.SaleActually.splice(0,this.SaleActually.length)
        this.SaleAll.push(res.data.sale1[0])
        this.SaleAll.push(res.data.sale1[1])
        this.SaleAll.push(res.data.sale1[2])
        this.SaleAll.push(res.data.sale1[3])
        this.SaleActually.push(res.data.sale2[0])
        this.SaleActually.push(res.data.sale2[1])
        this.SaleActually.push(res.data.sale2[2])
        this.SaleActually.push(res.data.sale2[3])
        this.oneTop();
      }
    },
    /*获得第二块数据的内容*/
    async getTwoData(){
      const res= await this.axios.post('visual/twoData',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))

      console.log(res);

      this.twoData = res.data;
      console.log("调用二",this.twoData);
      this.oneBottom();

    },
    /*获得第三块内容的值*/
    async threeData(){
      const res=await this.axios.post('visual/threeData',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))

      if (res.data.code === 200){
        this.allSale=res.data.allSale;
        this.allSaleNumber=res.data.allSaleNumber;
        this.listings=res.data.listings;
        this.orderNumber=res.data.orderNumber;
      }
    },
    /*获得第五块内容*/
    async fiveData(){
      const res=await this.axios.post('visual/fiveData',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))
      if (res.data.code === 200){

        for (let i = 0; i < res.data.object.length; i++) {
          this.fiveDatas[i]=res.data.object[i];
        }

        /*如果该商家数量小于5*/
        if (this.fiveDatas.length < 5){
          for (let i = this.fiveDatas.length; i < 5; i++) {
            this.fiveDatas[i]=0;
          }
        }

        this.threeTop();
      }
    },
    /*获得第六块内容*/
    async SixData(){
      const res=await this.axios.post('visual/sixData',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))
      console.log("六",res);
      if (res.data.code === 200){
        this.sixDatas=res.data.object
        this.threeBottom();
      }
    },
    /*获得第四块数据*/
    async fourData(){
      const res=await this.axios.post('visual/fourData',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))
      console.log(res);
      if (res.data.code === 200){
        this.chData=res.data.object
        await this.twoMiddle();
      }
    },

    /*---------------------------设置每秒刷新一次------------------------*/
    setTimer() {
      if(this.time == null) {
        this.time = setInterval( () => {
          this.salesVolume();
          this.getTwoData()
          this.threeData();
          this.fourData();
          if (this.fiveDatas.length >5){
            console.log("五",this.fiveDatas)
            this.fiveDatas.push(this.fiveDatas.shift());
            this.threeTop()
          }

          /*this.fiveData();*/
          this.SixData();


        }, 3000)
      }
    },
    /*左上角时间刷新*/
    setShowTime(){
      setInterval(()=>{
        let time = new Date();
        this.showTime=time.toLocaleString();

      })
    },
    getNextDate(day) {
      let dd = new Date();
      dd.setDate(dd.getDate() + day);
      let d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
      return d;
    },
  }
}
</script>

<style scoped>
  .index{
    width: 1706.670px;
    height: 811px;
    background-color: #161522;
  }

  .head{
    width: 1606.67px;
    height: 59px;
    margin: 0px 50px 0px 50px;
    border-bottom:#2095BA 1px solid ;
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: white;
  }
  .head-two{
    font-size: 28px;
  }

  .chart-body{
    width: 1606.67px;
    height: 741px;
    margin: 0px 50px 0px 50px;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
  .body-one{
    width: 410px;
    height: 741px;
  }
  .one-top{
    width: 410px;
    height: 360px;
    margin-top: 10px;
    background-color: #FFFFFF;
  }
  .one-bottom{
    width: 410px;
    height: 360px;
    margin-top: 10px;
    background-color: #FFFFFF;
  }

  .body-two{
    width: 740px;
    height: 741px;
    margin-left: 20px;
  }
  .two-middle{
    width: 740px;
    height: 600px;
    background-color: #FFFFFF;
    margin-top: 18px;
  }
  .middle-top{
    width: 740px;
    height: 100px;
    margin-top: 10px;
    background-color: #FFFFFF;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }

  .body-three{
    width: 416px;
    height: 741px;
    margin-left: 20px;
  }
  .three-top{
    width: 416px;
    height: 360px;
    margin-top: 10px;
    background-color: #FFFFFF;
  }
  .three-bottom{
    width: 416px;
    height: 360px;
    margin-top: 10px;
    background-color: #FFFFFF;
  }
  .demo{
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>