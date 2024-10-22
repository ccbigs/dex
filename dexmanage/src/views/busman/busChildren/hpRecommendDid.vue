<template>
  <div style="background: #f5f5f5;padding: 10px;">
    <!--轮播图-->
    <el-divider>已购好物推荐商位</el-divider>
    <div class="order-manager">
      <el-table
          :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
          stripe
          border
          style="width: 100%"
          height="460">
        <el-table-column
            label="订单编号"
            prop="quotient_order_id"
            align="center"
            width="140">
        </el-table-column>
        <el-table-column
            prop="ware_name"
            label="商品名称"
            align="center">
        </el-table-column>
        <el-table-column
            prop="ware_name"
            label="商品图片"
            align="center"
            width="260">
          <template slot-scope="scope">
            <el-image
                style="width: 40px; height: 40px"
                :src="scope.row.ware_img1"
                :preview-src-list="[scope.row.ware_img1]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column
            prop="month"
            label="购买时长(月)"
            align="center"
            width="140">
        </el-table-column>
        <el-table-column
            prop="pay_price"
            label="支付金额"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            label="订单状态"
            align="center"
            width="120">
          <template slot-scope="scope">
            <el-tag size="medium" type="success" v-if="scope.row.recommend_state === '启用'">{{ scope.row.recommend_state }}</el-tag>
            <el-tag size="medium" type="danger" v-if="scope.row.recommend_state === '停用'">{{ scope.row.recommend_state }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="createDate"
            label="购买日期"
            align="center"
            width="220">
        </el-table-column>
        <el-table-column
            prop="endDate"
            label="有效期至"
            align="center"
            width="220">
        </el-table-column>
      </el-table>
      <div class="order-page">
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
</template>

<script>

export default {
  name: "hpRecommendDid",
  async created() {
    await this.getDidBuyRecommend();
  },
  data(){
    return{
      /*搜索的关键字*/
      search: '',
      /*翻页*/
      currentPage: 1,
      pageSize:10,
      AllPageSize:0,


      AllData: [],
      tableData: [],
    }
  },
  methods:{
    async getDidBuyRecommend(){
      const res=await this.axios.post('quotient/getDidBuyRecommend',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))
      if (res.data.code === 200){
        this.AllData=res.data.object
        this.AllPageSize=this.AllData.length
        this.PagingSystem(10,1);
      }
    },
    /*----------------------------翻页的方法--------------------------*/
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize=val;
      this.PagingSystem(this.pageSize,this.currentPage);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage=val;
      this.PagingSystem(this.pageSize,this.currentPage);
    },
    /*实现每条页数的方法*/
    PagingSystem(pageSize,currentPage){

      if (this.currentPage === 1){
        this.tableData=this.AllData.slice(currentPage-1,currentPage*pageSize)
      }else {
        this.tableData=this.AllData.slice((currentPage-1)*pageSize,currentPage*pageSize)
      }

    },
  }
}
</script>

<style scoped>
.order-page{
  margin-top: 26px;
  float: right;
}
</style>