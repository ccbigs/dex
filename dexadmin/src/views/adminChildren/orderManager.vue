<template>
  <div>
    <div class="order-manager">
      <el-table
          :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
          stripe
          border
          style="width: 100%"
          height="560">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <div>
                <el-form-item label="发货人:">
                  <span>{{ props.row.bus_name }}</span>
                </el-form-item>

                <el-form-item label="联系电话:">
                  <span>{{ props.row.bus_phone }}</span>
                </el-form-item>

                <el-form-item label="发货人地址:">
                  <span>{{ props.row.bus_local }}</span>
                </el-form-item>
              </div>

              <div>
                <el-form-item label="收货人:">
                  <span>{{ props.row.name }}</span>
                </el-form-item>

                <el-form-item label="联系电话:">
                  <span>{{ props.row.phone }}</span>
                </el-form-item>

                <el-form-item label="收货人地址:">
                  <span>{{ props.row.local }}{{props.row.detail}}</span>
                </el-form-item>
              </div>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
            label="订单编号"
            prop="id"
            align="center"
            width="80">
        </el-table-column>
        <el-table-column
            prop="nickname"
            label="顾客"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="ware_name"
            label="商品名称"
            align="center"
            width="260">
        </el-table-column>
        <el-table-column
            prop="bus_name"
            label="商家"
            align="center"
            width="100">
        </el-table-column>
        <el-table-column
            prop="ware_price"
            label="商品单价"
            align="center"
            width="80">
        </el-table-column>
        <el-table-column
            prop="orders_number"
            label="购买数量"
            align="center"
            width="80">
        </el-table-column>
        <el-table-column
            prop="orders_totalprice"
            label="支付金额"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            label="订单状态"
            align="center"
            width="120">
          <template slot-scope="scope">
            <el-tag size="medium" type="success" v-if="scope.row.logistics_status === '已下单' || scope.row.logistics_status === '已签收'  ">{{ scope.row.logistics_status }}</el-tag>
            <el-tag size="medium"  v-if="scope.row.logistics_status === '运输中'">{{ scope.row.logistics_status }}</el-tag>
            <el-tag size="medium" type="warning" v-if="scope.row.logistics_status === '退换中'">{{ scope.row.logistics_status }}</el-tag>
            <el-tag size="medium" type="info" v-if="scope.row.logistics_status === '已关闭'">{{ scope.row.logistics_status }}</el-tag>
            <el-tag size="medium" type="danger" v-if="scope.row.logistics_status === '已取消'">{{ scope.row.logistics_status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="orders_date"
            label="创建日期"
            align="center"
            width="220">
        </el-table-column>
        <el-table-column
            min-width="200px"
            align="center"
            fixed="right">
          <template slot="header" slot-scope="scope">
            <el-input
                v-model="search"
                size="mini"
                placeholder="输入关键字搜索"/>
          </template>
          <template slot-scope="scope">
            <el-button
                size="mini"
                type="danger" round
                @click="handleDelete(scope.row.id)">删除</el-button>
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
    </div>
    <admin-foot style="position: absolute;bottom: 0px;"></admin-foot>
  </div>
</template>

<script>
import adminFoot from "../../components/adminFoot";
export default {
  name: "ordermanager",
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
    this.getAllOrder()
  },
  methods:{
    /*------------------获得全部用户信息------------------*/
    async getAllOrder(){
      const res= await this.axios.post('DexAdmin/adminGetAllOrder')
      if (res.data.code === 200){
        this.tableData = [];
        this.AllData=res.data.object;
        this.AllPageSize=this.AllData.length
        this.PagingSystem(this.pageSize,this.currentPage);
      }else {
        this.$message.error("获取数据失败")
      }
    },
    /*--------------------------表格中的方法:删除订单--------------------------*/
    async handleDelete(order_id) {
      const res= await this.axios.post('DexAdmin/adminDeleteOrder',this.$qs.stringify({
        id:order_id
      }))
      if (res.data.code === 200){
        this.$message.success("成功删除订单信息");
        await this.getAllOrder();
        this.adminLogDelete(order_id)
      }else {
        this.$message.error("删除订单信息失败")
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
    /*---------------日志行为---------------*/
    adminLogDelete(id){
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员不可逆操作，删除订单ID为:'+id+' 的所有信息'}`)
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