<template>
  <div>
    <div class="order-manager">
    <el-table
        :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
        stripe
        border
        style="width: 100%"
        height="560">
      <el-table-column
          label="订单编号"
          prop="id"
          align="center"
          width="120">
      </el-table-column>
      <el-table-column
          prop="ware_name"
          label="商品名称"
          align="center">
      </el-table-column>
      <el-table-column
          prop="pay_price"
          label="支付金额(元)"
          align="center"
          width="180">
      </el-table-column>
      <el-table-column
          prop="month"
          label="购买月份"
          align="center"
          width="140">
      </el-table-column>
      <el-table-column
          prop="bus_name"
          label="商家姓名"
          align="center"
          width="160">
      </el-table-column>
      <el-table-column
          prop="order_type"
          label="商位类型"
          align="center"
          width="140">
      </el-table-column>
      <el-table-column
          prop="order_time"
          label="订单日期"
          align="center"
          width="160">
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
    <admin-foot style="position: absolute;bottom: 0px;"></admin-foot>
  </div>
</template>

<script>
import adminFoot from "../../components/adminFoot";
export default {
  name: "quotientOrders",
  components:{
    'adminFoot':adminFoot,
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
  created() {
    this.getAllQutientOrder()
  },
  methods:{
    /*------------------获得全部用户信息------------------*/
    async getAllQutientOrder(){
      const res= await this.axios.post('DexAdmin/adminGetAllQutientOrder')
      if (res.data.code === 200){
        this.AllData=res.data.object;
        this.AllPageSize=this.AllData.length
        this.PagingSystem(10,1);
      }else {
        this.$message.error("获取数据失败")
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

    }
  }
}
</script>

<style scoped>
.order-manager{
  margin: 20px auto;
  width: 1466.670px;
  padding: 20px;
  height: 630px;
  background-color: white;
}

.order-page{
  margin-top: 26px;
  float: right;
}

.comment-image{
  display: flex;
  align-items: center;
}

.comment-image img{
  height: 100px;
  margin-right: 10px;
}
</style>