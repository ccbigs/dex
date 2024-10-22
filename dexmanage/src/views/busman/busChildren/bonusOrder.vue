<template>
  <div class="order-manager">
    <el-table
        :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
        stripe
        border
        style="width: 100%"
        height="660">
      <el-table-column
          label="编号"
          prop="id"
          align="center"
          width="60">
      </el-table-column>
      <el-table-column
          prop="ware_name"
          label="商品名称"
          align="center">
      </el-table-column>
      <el-table-column
          prop="standards_name"
          label="商品规格"
          align="center"
          width="120">
      </el-table-column>
      <el-table-column
          prop="standards_price"
          label="商品单价"
          align="center"
          width="120">
      </el-table-column>
      <el-table-column
          prop="buy_number"
          label="销售量"
          align="center"
          width="80">
      </el-table-column>
      <el-table-column
          prop="bonus_totalprice"
          label="销售总金额"
          align="center"
          width="120">
      </el-table-column>
      <el-table-column
          prop="ware_bonusmoney"
          label="销售分红"
          align="center"
          width="120">
      </el-table-column>
      <el-table-column
          prop="add_name"
          label="顾客收货人"
          align="center"
          width="120">
      </el-table-column>
      <el-table-column
          prop="add_phone"
          label="联系电话"
          align="center"
          width="120">
      </el-table-column>
      <el-table-column
          prop="add_local"
          label="收货地址"
          align="center"
          width="200">
      </el-table-column>
      <el-table-column
          label="订单状态"
          align="center"
          width="100">
        <template slot-scope="scope">
          <el-tag size="medium" type="success" v-if="scope.row.order_state === '备货中'">{{ scope.row.order_state }}</el-tag>
          <el-tag size="medium" type="danger" v-if="scope.row.order_state === '运输中'">{{ scope.row.order_state }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="order_time"
          label="下单时间"
          align="center"
          width="160">
      </el-table-column>
      <el-table-column
          align="center"
          fixed="right"
          width="140">
        <template slot="header" slot-scope="scope">
          <el-input
              v-model="search"
              size="mini"
              placeholder="输入关键字搜索"/>
        </template>
        <template slot-scope="scope">
          <el-button v-if="scope.row.order_state==='备货中'"
                     type="primary" size="mini"
                     @click="wirterExpress(scope.row.id)">发出商品</el-button>
          <el-button v-if="scope.row.order_state==='运输中'"
                     type="primary" size="mini" disabled
                     @click="signWare(scope.$index, scope.row.id)">待顾客签收</el-button>
          <el-button v-if="scope.row.order_state==='已签收'"
                     type="primary" size="mini" disabled>签收货物</el-button>
        </template>
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
    <el-dialog title="输入快递单号" :visible.sync="dialogExpressVisible">
      <el-form :model="form">
        <el-form-item label="快递单号">
          <el-input v-model="form.ExpressNumber" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogExpressVisible = false">取 消</el-button>
        <el-button type="primary" @click="sendWare()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "bonusOrder",
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

      /*商人发出货物，填写快递单号*/
      dialogExpressVisible:false,
      form: {
        id:'',
        ExpressNumber:'',
      },
    }
  },
  created() {
    this.getAllBonusOrder();
  },
  methods:{
    async getAllBonusOrder(){
      const res=await this.axios.post('bonus/getAllBonusOrder',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))
      if (res.data.code === 200){
        this.tableData = [];
        this.AllData=res.data.object
        this.AllPageSize=this.AllData.length
        this.PagingSystem(this.pageSize,this.currentPage);
      }
    },
    /*商家填写快递单号，发出商品*/
    wirterExpress(id){
      this.dialogExpressVisible = true;
      this.form.id = id;
    },
    /*发出商品*/
    async sendWare(){
      const res=await this.axios.post('bonus/sendWareByBusman',this.$qs.stringify({
        bonusOrder_id:this.form.id,
        expressNumber:this.form.ExpressNumber,
      }))
      if (res.data.code === 200){
        this.$message.success("修改商品状态成功")
        this.dialogExpressVisible = false;
        await this.getAllBonusOrder();
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