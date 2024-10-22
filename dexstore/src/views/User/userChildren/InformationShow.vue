<template>
  <div>
    <!--页头信息-->
    <div class="head-title">DEX商城新闻</div>
    <el-divider></el-divider>
    <div class="mode">
      <div class="mode-title" v-for="(o,i) in titleDate" :key="o.id">
        <el-card :body-style="{ padding: '10px' }" shadow="hover">
          <div style="display: flex;justify-content: space-between;align-items: center">
            <h2>{{ o.title }}</h2>
            <div>时间:{{ o.public_time }}</div>
          </div>
          <div class="span">
            <span>
              &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
              {{ o.subtitle }}
            </span>
          </div>
          <div class="button">
            <div>
              <el-button type="text" class="button" @click="GoInformationDetail(o.id)">>>查看全文</el-button>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "InformationShow",
  data(){
    return{
      titleDate:[],
    }
  },
  created() {
    this.getInformation()
  },
  methods:{
    /*刚打开页面，获取全部文章信息*/
    async getInformation(){
      const res= await this.axios.post('http://localhost:8680/dex/info/GetInformationByUser')
      if (res.data.code === 200){
        this.titleDate= res.data.object
      }else {
        this.$message.error("当前暂无文章信息")
      }
    },
    /*/!*返回总共的文章数量*!/
    async getInformationNumber(){
      const res=await this.axios.post('http://localhost:8683/dex/DexAdmin/getTotalInformationNumber')
      if (res.data.code === 200){
        this.AllPageSize = res.data.size
      }else {
        this.$message.error("获取文章总数量失败")
      }
    },*/
    /*查看全文跳转*/
    GoInformationDetail(info_id){
      this.$router.push({
        name:'InformationDetail',
        query:{
          info_id:info_id
        },
      })
    },
  }
}
</script>

<style scoped>
  h2{
    margin: 0px;
  }

  .span{
    margin-top: 8px;
    margin-bottom: 8px;
    font-size: 18px;
  }

  .mode{
    margin: 10px auto;
    padding: 0px 15px 0px 15px;
    width: 1166.670px;
    height: 1015px;
    background-color: white;
  }

  .mode-title{
    margin-top: 18px;
  }

  .button{
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }

  .head-title{
    margin-top: 14px;
    font-size: 28px;
    font-weight: 500;
  }
</style>