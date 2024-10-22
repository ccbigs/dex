<template>
  <div>
    <div class="busman-manager">
      <el-table
          :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
          stripe
          border
          style="width: 100%"
          height="538">
        <el-table-column
            type="index"
            width="40">
        </el-table-column>
        <el-table-column
            sortable
            prop="id"
            label="秒杀编号"
            width="150">
        </el-table-column>
        <el-table-column
            prop="result_url"
            label="秒杀链接"
            align="center">
        </el-table-column>
        <el-table-column
            prop="user_id"
            label="秒杀发起人id"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="ware_id"
            label="秒杀商品id"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="begindate"
            label="秒杀开始时间"
            align="center"
            width="200">
        </el-table-column>
        <el-table-column
            prop="enddate"
            label="秒杀结束时间"
            align="center"
            width="200">
        </el-table-column>
        <el-table-column
            prop="bonus_money"
            label="网红每单分红(元)"
            align="center"
            width="140">
        </el-table-column>
        <el-table-column
            label="秒杀状态"
            align="center"
            width="120">
          <template slot-scope="scope">
            <el-tag size="medium" v-if="scope.row.bonus_state === '启用'">{{ scope.row.bonus_state }}</el-tag>
            <el-tag size="medium" type="danger" v-if="scope.row.bonus_state === '关闭'">{{ scope.row.bonus_state }}</el-tag>
          </template>
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
    <admin-foot style="position: absolute;bottom: 0px;"></admin-foot>
  </div>
</template>

<script>
import adminFoot from "../../components/adminFoot";
export default {
name: "bonusManager",
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
    this.getAllBonusMessage();
  },
  methods:{
    /*----------------------获得全部管理员信息---------------------*/
    async getAllBonusMessage(){
      const res=await this.axios.post('DexAdmin/adminGetAllBonus')
      if (res.data.code === 200){
        this.AllData=res.data.object;
        this.AllPageSize=this.AllData.length
        this.PagingSystem()
      }else {
        this.$message.error("获取秒杀信息失败，你代码写错了吧")
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
    /*--------------------管理员后台日志---------------------------*/
    adminLog(id,message){
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员更改商家ID为:'+id+'的商家状态，更改状态为:'+message}`)
    }
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