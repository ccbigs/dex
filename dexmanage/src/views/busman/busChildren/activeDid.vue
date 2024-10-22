<template>
  <div>
    <active-ware v-for="(o,i) in activeDidWare" :ware="o"></active-ware>
  </div>
</template>

<script>
import activeWare from "../../../components/busman/activeWare";
export default {
  name: "activeDid",
  data(){
    return{
      activeDidWare:'',
    }
  },
  components:{
    'activeWare':activeWare,
  },
  async created() {
    await this.getActiveDidWare();
  },
  methods:{
    async getActiveDidWare(){
      const res=await this.axios.post('active/getActiveDidWare',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))
      if (res.data.code === 200){
        this.activeDidWare=res.data.object
      }else {
        this.$message.error("当前尚未有参与活动的商品")
      }
    },
  }
}
</script>

<style scoped>

</style>