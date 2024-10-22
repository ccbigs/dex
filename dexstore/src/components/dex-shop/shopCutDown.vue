<template>
  <div style="background:darkred;width: 100%;color:#fff;text-align:center;">
    <P style="margin: 0px">距活动结束还有：</P>

    <!-- 容器内类名必须按以下定义，需要显示什么数据就定义什么类名。
    比如只需要显示分、秒，则只定义.countdown-mini和.countdown-sec类即可 -->
    <div id="timeBox">
      <span class="countdown-hour">{{ hou }}</span>时
      <span class="countdown-mini">{{ min }}</span>分
      <span class="countdown-sec">{{ sec }}</span>秒
      <span class="countdown-hm">{{ ssec }}</span>
    </div>
  </div>
</template>

<script>

export default {
  name:'shopCutDown',
  props:['endTime','ware_id','bonus_id'],
  data() {
    return {
      hou:0,
      min:0,
      sec:0,
      ssec:0,
      disprArr:[],
    }
  },
  mounted(){
    let that=this
    that.time()
  },
  methods:{
    time(){
      var that=this
      var interval = setInterval(function timestampToTime(){
        let strTime=that.endTime;
        console.log(strTime);
        var date=(new Date(Date.parse(strTime.replace(/-/g,  "/")))) - (new Date());
        //new Date当前的时间戳，也可以换成自定义的时间戳
        if (date > 0) {
          let time = date / 1000;
          // 获取时、分、秒,毫秒
          that.hou = parseInt((time % (60 * 60 * 24)) / 3600)<10?('0'+parseInt((time % (60 * 60 * 24)) / 3600)):parseInt((time % (60 * 60 * 24)) / 3600)
          that.min = parseInt(((time % (60 * 60 * 24)) % 3600) / 60)<10?('0'+parseInt(((time % (60 * 60 * 24)) % 3600) / 60)):parseInt(((time % (60 * 60 * 24)) % 3600) / 60);
          that.sec = parseInt(((time % (60 * 60 * 24)) % 3600) % 60)<10?('0'+parseInt(((time % (60 * 60 * 24)) % 3600) % 60)):parseInt(((time % (60 * 60 * 24)) % 3600) % 60);
          that.ssec= parseInt(((date % (60 * 60 * 24)) % 3600) / 60)%10
        } else {
          //活动已结束，全部设置为'00'
            that.day="00";
            that.hou="00";
            that.min="00";
            that.sec="00";

            clearTimeout(interval)

            that.axios.post(`http://localhost:8688/dex/bonus/exitBonus`,that.$qs.stringify({
              ware_id:that.ware_id,
              bonus_id:that.bonus_id,
            })).catch((res)=>{
              if (res.data.code === 200){
                that.$message.warning("当前活动已经结束了");
                that.$parent.stopTimer();
                that.$router.push({
                  name:'home'
                })
              }
            })

        }
      },100)
    }
  },
};

</script>

<style scoped>
#timeBox span {
  background: #C71C60;
  color: #fff;
  line-height: 49px;
  font-size: 36px;
  font-family: Arial;
  padding: 0 10px;
  margin-right: 10px;
  border-radius: 5px;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2);
}
</style>