<template>
  <div>
    <div class="busman-manager">
      <el-table
          :data="tableData"
          stripe
          border
          style="width: 100%"
          height="568">
        <el-table-column
            type="index"
            width="40">
        </el-table-column>
        <el-table-column
            align="center"
            prop="bonus_id"
            label="秒杀详情id"
            width="150">
        </el-table-column>
        <el-table-column
            prop="busman_id"
            align="center"
            label="商家id"
            width="80">
        </el-table-column>
        <el-table-column
            prop="user_id"
            label="网红id"
            align="center"
            width="80">
        </el-table-column>
        <el-table-column
            prop="ware_id"
            label="商品id"
            align="center"
            width="80">
        </el-table-column>
        <el-table-column
            prop="buy_number"
            label="销售数量"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="bonus_totalprice"
            label="销售总金额(元)"
            align="center"
            width="130">
        </el-table-column>
        <el-table-column
            prop="ware_bonusmoney"
            label="每单分成"
            align="center"
            width="100">
        </el-table-column>
        <el-table-column
            label="订单状态"
            align="center"
            width="100">
          <template slot-scope="scope">
            <el-tag size="medium" v-if="scope.row.order_state === '备货中'">{{ scope.row.order_state }}</el-tag>
            <el-tag size="medium" type="success" v-if="scope.row.order_state === '运输中'">{{ scope.row.order_state }}</el-tag>
         </template>
        </el-table-column>
        <el-table-column
            prop="busman_local"
            label="发货地址"
            width="200">
        </el-table-column>
        <el-table-column
            prop="add_name"
            label="收货人"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="add_phone"
            label="收货人电话"
            align="center"
            width="130">
        </el-table-column>
        <el-table-column
            prop="add_local"
            label="收货地址"
            align="center">
        </el-table-column>
      </el-table>
      <div class="busman-page">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[8, 20, 30, 40]"
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
  name: "bonusOrder",
  components:{
    'adminFoot':adminFoot,
  },
  data(){
    return{
      /*翻页*/
      currentPage: 1,
      pageSize:8,
      AllPageSize:0,

      AllData: [],
      tableData: [],
    }
  },
  created() {
    this.getAllBonusOrder();
  },
  methods:{
    async getAllBonusOrder(){
      const res =await this.axios.post('DexAdmin/adminGetAllBonusOrder')
      if (res.data.code === 200){
        this.AllData=res.data.object;
        this.AllPageSize=this.AllData.length
        this.PagingSystem()
      }else {
        this.$message.error("获取秒杀订单信息失败，你代码写错了吧")
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
.busman-manager{
  margin: 10px auto;
  width: 1536.670px;
  padding: 30px 20px 20px 20px;
  height: 620px;
  background-color: white;
}

.busman-page{
  margin-top: 26px;
  float: right;
}
</style>