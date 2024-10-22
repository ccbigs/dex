<template>
  <div class="admin-foot">
    <div class="foot-time"><p>{{data}}</p></div>
  </div>
</template>

<script>
export default {
  name: "adminFoot",
  data(){
    return{
      data:'',
    }
  },
  mounted() {
    this.getTimeNow()
  },
  methods:{
    getTimeNow(){
      setInterval(()=>{
        this.data=new Date()
      },1000)
    }
  }
}
</script>

<style scoped>
  .admin-foot{
    width: 100%;
    height: 40px;
    background-color: #545C64;
    color: white;
  }

  p{
    text-align: center;
    margin-top: 8px;
    margin-bottom: 0px;
  }
</style>