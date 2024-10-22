<template>
  <div>
    <!--页头信息-->
    <div class="head-title">我的秒杀收益</div>
    <el-divider></el-divider>

    <div style="height: 1024px">
    <el-empty v-if="EMPTY" :image-size="300" description="当前暂无信息"></el-empty>
    <div class="mode" v-else>
      <!--   收益总计   -->
      <el-card class="box-card">
        <div class="mode-head">
          <div>
            <p>总收益(元)</p>
            <p class="number">￥{{ this.AllProfit }}</p>
          </div>
          <div>
            <p>到账收益(元)</p>
            <p class="number">￥{{this.ActuallyProfit}}</p>
          </div>
        </div>
      </el-card>

      <!--  收益详细    -->
      <el-divider content-position="left">收益详细</el-divider>
      <div>
        <div class="busman-manager">
          <el-table
              :data="tableData.filter(data => !search || data.behavior.toLowerCase().includes(search.toLowerCase()))"
              stripe
              border
              style="width: 100%"
              height="756">
            <el-table-column
                align="center"
                prop="id"
                label="秒杀编号"
                width="150">
            </el-table-column>
            <el-table-column
                prop="begindate"
                label="开始时间"
                width="190"
                align="center">
            </el-table-column>
            <el-table-column
                prop="enddate"
                label="结束时间"
                width="190"
                align="center">
            </el-table-column>
            <el-table-column
                prop="bonus_money"
                label="商品分红单价(元)"
                align="center"
                width="150">
            </el-table-column>
            <el-table-column
                prop="sale_number"
                label="销售量"
                align="center"
                width="100">
            </el-table-column>
            <el-table-column
                prop="ware_bonusmoney"
                label="总计收益(元)"
                align="center"
                width="220">
            </el-table-column>
            <el-table-column
                prop="bonus_state"
                label="秒杀状态"
                align="center">
            </el-table-column>
          </el-table>
          <div class="busman-page">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[10, 20, 30, 40]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="AllPageSize">
            </el-pagination>
          </div>
        </div>
      </div>
    </div>
  </div>
  </div>
</template>

<script>
export default {
  name: "bonusDate",
  data(){
    return{
      EMPTY:false,
      /*搜索的关键字*/
      search: '',
      /*翻页*/
      currentPage: 1,
      pageSize:20,
      AllPageSize:0,

      AllData: [],
      tableData: [],

      /*全部收益*/
      AllProfit:'',
      ActuallyProfit:'',
    }
  },
  created() {
    this.getBonusTableDate();
    this.getUserProfit();
  },
  methods:{
    /*获得秒杀详细表单数据*/
    async getBonusTableDate(){
      const res=await this.axios.post('http://localhost:8681/dex/bonus/getUserBonusDate',this.$qs.stringify({
        user_id:this.$store.state.userInfo.id
      }))
      if (res.data.code === 200){
        this.AllData=res.data.object;
        this.AllPageSize=this.AllData.length
        this.PagingSystem()
      }else {
        this.EMPTY=true
      }
    },
    /*获得秒杀收益*/
    async getUserProfit(){
      const res=await this.axios.post('http://localhost:8681/dex/bonus/getUserProfit',this.$qs.stringify({
        user_id:this.$store.state.userInfo.id
      }))
      if (res.data.code === 200){
        console.log(res.data.object)
        this.AllProfit=res.data.object[0];
        this.ActuallyProfit=res.data.object[1];
      }else {
        this.EMPTY=true
      }
    },
    /*----------------------------翻页的方法--------------------------*/
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize=val;
      this.PagingSystem();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage=val;
      this.PagingSystem();
    },
    /*实现每条页数的方法*/
    PagingSystem(){
      if (this.currentPage === 1){
        this.tableData=this.AllData.slice(this.currentPage-1,this.currentPage*this.pageSize)
      }else {
        this.tableData=this.AllData.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
      }

    },
  }
}
</script>

<style scoped>
  .mode{
    margin-top: 10px;
  }
  .mode-head{
    width: 100%;
    height: 100px;
    margin-bottom: 20px;
    display: flex;
    align-items: center;
    justify-content: space-around;
  }
  .mode-head p{
    text-align: center;
    margin-top: 5px;
    margin-bottom: 5px;
    font-size: 18px;
  }
  .mode-head .number{
    font-size: 20px;
    font-weight: bold;
    color: red;
  }
  .busman-page{
    margin-top: 26px;
    float: right;
  }
  .head-title{
    margin-top: 14px;
    font-size: 28px;
    font-weight: 500;
  }

</style>