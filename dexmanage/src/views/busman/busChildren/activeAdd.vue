<template>
  <div>
    <active-online v-for="(o,i) in activeOnline" :ware="o"></active-online>
  </div>
</template>

<script>
import activeOnline from "../../../components/busman/activeOnline";
export default {
  name: "activeAdd",
  components:{
    'activeOnline':activeOnline
  },
  data(){
    return{
      activeOnline:'',
    }
  },
  async created() {
    await this.getNoActiveWare();
  },
  methods:{
    async getNoActiveWare(){
      const res=await this.axios.post('active/busmanNoActive',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))
      if (res.data.code === 200){
        this.activeOnline=res.data.object
      }else {
        this.$message.info("你当前未有参与活动的商品")
      }
    }
  }

}
</script>

<style scoped>

</style>