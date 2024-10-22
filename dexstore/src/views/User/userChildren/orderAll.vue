<template>
  <div>
    <!--页头信息-->
    <div class="head-title">全部订单</div>
    <el-divider></el-divider>
    <div class="table-data">
      <el-table
          :data="tableData.filter(data => !search || data.ware_name.toLowerCase().includes(search.toLowerCase()))"
          height="966px"
          style="width: 100%;">
        <!--站开业内容-->
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <div>
              <el-form-item label="订单编号：">
                <span>{{ props.row.id }}</span>
              </el-form-item>
              <el-form-item label="商品 ID：">
                <span>{{ props.row.ware_id }}</span>
              </el-form-item>
              <el-form-item label="商品名称：">
                <span>{{ props.row.ware_name }}</span>
              </el-form-item>
              </div>

              <div>
              <el-form-item label="收货人：">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="收货地址：">
                <span>{{ props.row.local}}{{props.row.detail}}</span>
              </el-form-item>
              <el-form-item label="联系电话：">
                <span>{{ props.row.phone}}</span>
              </el-form-item>
              </div>

              <el-form-item label="下单的时间：">
                <span>{{ props.row.orders_date }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <!--此处写上商品的图片和名称-->
        <el-table-column label="商品" align="center" width="370" prop="">
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
        <!--单价-->
        <el-table-column label="单价/件" align="center" width="140">
          <template #default="scope">
            <div>￥{{scope.row.unit_price}}</div>
          </template>
        </el-table-column>
        <!--数量-->
        <el-table-column
            label="数量"
            align="center"
            width="140"
            show-overflow-tooltip>
          <template #default="scope">
            {{scope.row.orders_number}}
          </template>
        </el-table-column>
        <!--小计-->
        <el-table-column
            label="实付金额"
            align="center"
            width="200"
            show-overflow-tooltip>
          <template #default="scope">
            <div style="color: red;font-size: 22px;font-weight: bold">￥{{scope.row.orders_totalprice}}</div>
          </template>
        </el-table-column>
        <!--商品状态-->
        <el-table-column
            label="商品状态"
            align="center"
            width="160"
            show-overflow-tooltip>
          <template #default="scope">
            <div style="color: green;" v-if="scope.row.logistics_status==='已下单'">{{scope.row.logistics_status}}</div>
            <div style="color: blue;" v-if="scope.row.logistics_status==='运输中'">{{scope.row.logistics_status}}</div>
            <div style="color: red;" v-if="scope.row.logistics_status==='已签收'">{{scope.row.logistics_status}}</div>
            <div style="color:red;" v-if="scope.row.logistics_status==='退换中'">{{scope.row.logistics_status}}</div>
            <div style="color: red;" v-if="scope.row.logistics_status==='已取消'">{{scope.row.logistics_status}}</div>
            <div style="color: red;" v-if="scope.row.logistics_status==='已关闭'">{{scope.row.logistics_status}}</div>
          </template>
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
              <el-button v-if="scope.row.logistics_status==='运输中'"
                  type="primary" plain disabled
              >确认收货</el-button>

              <el-button v-if="scope.row.logistics_status==='退换中'"
                  type="primary" plain disabled
              >等待商家收货</el-button>

              <el-button v-if="scope.row.logistics_status==='已关闭'"
                  type="primary" plain disabled
              >已退款</el-button>

              <el-button v-if="scope.row.logistics_status==='已取消'"
                  type="warning" plain disabled
              >订单已取消</el-button>

              <el-button v-if="scope.row.logistics_status==='已下单'"
                  type="primary"
                  @click="cancelOrder(scope.$index, scope.row.id)">取消订单</el-button>

              <el-button v-if="scope.row.logistics_status==='已签收'"
                  type="success" disabled
                  @click="handleDelete(scope.$index, scope.row.id)">订单已签收</el-button>
            </template>
        </el-table-column>
      </el-table>
      <div class="order-page">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[7, 20, 30, 40]"
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
  name: "orderAll",
  data(){
    return {
      totalNum:0,
      totalPrice:0,

      tableData: '',
      multipleSelection: [],
      search:'',

      /*翻页*/
      AllData: [],
      currentPage: 1,
      pageSize:7,
      AllPageSize:0,
    }
  },
  async created() {
    const res=await this.axios.post('http://localhost:8681/dex/order/getUserOrder',this.$qs.stringify({
      user_id:this.$store.state.userInfo.id
    }))
    console.log("orderAll返回的全部订单",res);
    if (res.data.code === 200){
      this.AllData = res.data.object;
      this.AllPageSize=res.data.size;
      await this.PagingSystem(6,1);
    }else {
      alert("尚未有订单")
    }
  },
  methods:{
    handleDelete(index, row) {
      console.log(index, row);
    },
    /*点击取消订单*/
    async cancelOrder(index, id){
      const res=await this.axios.post('http://localhost:8681/dex/order/cancelOrder',this.$qs.stringify(this.tableData[index]));
      if (res.data.code === 200){
        this.tableData[index].logistics_status="已取消";
        this.successBuy()
      }else {
        this.filedMsg()
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

    /*操作成功*/
    successBuy() {
      this.$notify({
        title: '操作成功',
        message: '订单操作成功',
        type: 'success'
      });
    },
    /*失败提示*/
    filedMsg() {
      this.$notify.error({
        title: '操作失败',
        message: '订单操作失败',
      });
    },
  },
}
</script>

<style scoped>
  .head-title{
    margin-top: 14px;
    font-size: 28px;
    font-weight: 500;
  }
  .table-data{
    width: 1200px;
    background-color: #EEEEEE;
    border-radius: 18px;
  }
  .table-ware{
    display: flex;
    justify-content: left;
    align-items: center;
  }
  .ware-image{
    margin-left: 30px;
    margin-right: 50px;
  }
  .ware-name{
    font-size: 16px;
  }

  .order-page{
    margin-top: 16px;
    margin-bottom: 10px;
    float: right;
  }
</style>