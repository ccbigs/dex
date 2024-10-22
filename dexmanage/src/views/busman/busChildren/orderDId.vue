<template>
  <div>
    <div class="table-data">
      <el-table
          :data="tableData.filter(data => !search || data.ware_name.toLowerCase().includes(search.toLowerCase()))"
          height="820px"
          style="width: 100%">
        <!--订单号-->
        <el-table-column
            label="订单号"
            align="center"
            width="100"
            show-overflow-tooltip>
          <template #default="scope">
            {{scope.row.id}}
          </template>
        </el-table-column>
        <!--此处写上商品的图片和名称-->
        <el-table-column label="商品" align="center" width="350" prop="">
          <template #default="scope">
            <div class="table-ware">
              <div class="ware-image">
                <el-image style="width: 100px; height: 100px" :src="scope.row.ware_img1">
                </el-image>
              </div>
              <div class="ware-name">{{ scope.row.ware_name }}</div>
            </div>
          </template>
        </el-table-column>
        <!--规格-->
        <el-table-column label="商品规格" align="center" width="140">
          <template #default="scope">
            <div>{{scope.row.standard_name}}</div>
          </template>
        </el-table-column>
        <!--单价-->
        <el-table-column label="单价" align="center" width="140">
          <template #default="scope">
            <div>￥{{scope.row.unit_price}}</div>
          </template>
        </el-table-column>
        <!--已售-->
        <el-table-column
            label="购买数量"
            align="center"
            width="140"
            show-overflow-tooltip>
          <template #default="scope">
            {{scope.row.orders_number}}
          </template>
        </el-table-column>
        <!--成交额-->
        <el-table-column
            label="用户实付金额"
            align="center"
            width="200"
            show-overflow-tooltip>
          <template #default="scope">
            <div style="color: red;font-size: 22px;font-weight: bold">￥{{scope.row.orders_totalprice}}</div>
          </template>
        </el-table-column>
        <!--收货地址-->
        <el-table-column
            label="用户收货地址"
            align="center"
            width="240"
            show-overflow-tooltip>
          <template #default="scope">
            <span>{{scope.row.local}}<br>{{scope.row.detail}}<br>收货人:{{scope.row.name}}<br>联系电话:{{scope.row.phone}}</span>
          </template>
        </el-table-column>
        <!--商品状态-->
        <el-table-column
            label="商品状态"
            align="center"
            width="160"
            show-overflow-tooltip>
          <template #default="scope">
            <div style="color: green;" v-if="scope.row.logistics_status==='已下单'">用户已下单</div>
            <div style="color: #E6A23C;" v-if="scope.row.logistics_status==='已关闭'">当前订单已关闭</div>
            <div style="color: blue;" v-if="scope.row.logistics_status==='已取消'">用户已取消订单</div>
            <div style="color: red;" v-if="scope.row.logistics_status==='已签收'">用户已签收</div>
            <div style="color: red;" v-if="scope.row.logistics_status==='退换中'">商品退换中</div>
            <div style="color: dodgerblue;" v-if="scope.row.logistics_status==='运输中'">商品运输中</div>
          </template>
        </el-table-column>
        <el-table-column
            align="center"
            fixed="right"
            width="200">
          <template slot="header" slot-scope="scope">
            <el-input
                v-model="search"
                size="mini"
                placeholder="输入关键字搜索"/>
          </template>
          <template slot-scope="scope">
            <el-button v-if="scope.row.logistics_status==='已下单'"
                       type="primary" size="mini"
                       @click="wirterExpress(scope.$index, scope.row.id)">发出商品</el-button>
            <el-button v-if="scope.row.logistics_status==='退换中'"
                       type="primary" size="mini"
                       @click="signWare(scope.$index, scope.row.id)">签收货物</el-button>
            <el-button v-if="scope.row.logistics_status==='已取消'"
                                                    size="mini" disabled>交易已关闭</el-button>
            <el-button v-if="scope.row.logistics_status==='已关闭'"
                                                    size="mini" disabled>交易已关闭</el-button>
            <el-button v-if="scope.row.logistics_status==='已签收'"
                                      type="primary" size="mini" disabled>用户已签收</el-button>
            <el-button v-if="scope.row.logistics_status==='运输中'"
                                      type="primary" size="mini" disabled>商品正在运输中</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="order-page">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 20, 40]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="AllPageSize">
        </el-pagination>
      </div>
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
  name: "orderDId",
  data(){
    return{
      tableData: '',
      search:'',
      /*翻页*/
      AllData: [],
      currentPage: 1,
      pageSize:5,
      AllPageSize:0,
      /*商人发出货物，填写快递单号*/
      dialogExpressVisible:false,
      form: {
        id:'',
        ExpressNumber:'',
      },

    }
  },
  async created() {
    await this.getAllOrderByBusman();
  },
  methods:{
    async getAllOrderByBusman(){
      const res=await this.axios.post('order/busmanGetOrders',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }));
      if (res.data.code === 200){
        this.AllData = res.data.object;
        this.AllPageSize=res.data.size;
        await this.PagingSystem(5,1);
      }else {
        this.$message.error("当前没有订单信息")
      }
    },
    /*商家填写快递单号，发出商品*/
    wirterExpress(index,id){
      this.dialogExpressVisible = true;
      this.form.id = id;
    },
    /*商家发出商品*/
    async sendWare(){
      const res=await this.axios.post('order/busmanSendWare',this.$qs.stringify({
        id:this.form.id,
        expressNumber:this.form.ExpressNumber,
      }))
      if (res.data.code === 200){
        this.$message.success("货物已发出，已更新订单")
        this.dialogExpressVisible=false;
        await this.getAllOrderByBusman();
      }else {
        this.$message.error("货物发出失败")
      }
    },

    /*商家签收退换货物*/
    async signWare(index,id){
      const res=await this.axios.post('order/busmanSignWare',this.$qs.stringify({
        id:id
      }))
      if (res.data.code === 200){
        this.$message.success("签收退换货成功")
        await this.getAllOrderByBusman();
      }else {
        this.$message.error("货物签收失败")
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
  margin-bottom: 10px;
  float: right;
}
</style>