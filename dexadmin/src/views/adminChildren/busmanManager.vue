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
            prop="bus_account"
            label="商家账号"
            width="150">
        </el-table-column>
        <el-table-column
            prop="bus_password"
            label="商家密码"
            width="120">
        </el-table-column>
        <el-table-column
            prop="bus_phone"
            label="商家电话"
            align="center"
            width="140">
        </el-table-column>
        <el-table-column
            prop="bus_name"
            label="真实姓名"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="bus_idcard"
            label="商家身份证号"
            align="center"
            width="200">
        </el-table-column>
        <el-table-column
            label="商家状态"
            align="center"
            width="100">
          <template slot-scope="scope">
            <el-tag size="medium" v-if="scope.row.bus_busman_state === '正常'">{{ scope.row.bus_busman_state }}</el-tag>
            <el-tag size="medium" type="danger" v-if="scope.row.bus_busman_state === '封号'">{{ scope.row.bus_busman_state }}</el-tag>
            <el-tag size="medium" type="success" v-if="scope.row.bus_busman_state === '审核中'">{{ scope.row.bus_busman_state }}</el-tag>
            <el-tag size="medium" type="warning" v-if="scope.row.bus_busman_state === '申请注销'">{{ scope.row.bus_busman_state }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="bus_local"
            label="发货地址"
            width="200">
        </el-table-column>
        <el-table-column
            prop="bus_register_time"
            label="注册日期"
            align="center"
            width="100">
        </el-table-column>
        <el-table-column
            prop="bus_lasttime"
            label="最后一次登录"
            align="center"
            width="110">
        </el-table-column>
        <el-table-column
            align="center"
            min-width="200px"
            fixed="right">
          <template slot="header" slot-scope="scope">
            <el-input
                v-model="search"
                size="mini"
                placeholder="输入关键字搜索"/>
          </template>
          <template slot-scope="scope">
            <el-button
                size="mini" type="primary" round
                v-if="scope.row.bus_busman_state === '封号'"
                @click="handleEdit(scope.row.id,'解禁账号')">解禁账号</el-button>
            <el-button
                size="mini"
                type="danger" round
                v-if="scope.row.bus_busman_state === '正常'"
                @click="handleEdit(scope.row.id,'封禁账号')">封禁账号</el-button>
            <el-button
                size="mini"
                type="primary" round
                v-if="scope.row.bus_busman_state === '审核中'"
                @click="handleEdit(scope.row.id,'审核通过')">审核通过</el-button>
            <el-button
                size="mini"
                type="danger" round
                v-if="scope.row.bus_busman_state === '申请注销'"
                @click="handleEdit(scope.row.id,'申请注销')">申请注销</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="busman-page">
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
    <admin-foot style="position: absolute;bottom: 0px;"></admin-foot>
  </div>
</template>

<script>
import adminFoot from "../../components/adminFoot";
export default {
  name: "busmanManager",
  components:{
    'adminFoot':adminFoot,
  },
  data(){
    return{
      /*搜索的关键字*/
      search: '',
      /*翻页*/
      currentPage: 1,
      pageSize:7,
      AllPageSize:0,

      AllData: [],
      tableData: [],
    }
  },
  created() {
    this.getAllBusman();
  },
  methods:{
    /*----------------------获得全部管理员信息---------------------*/
    async getAllBusman(){
      const res=await this.axios.post('DexAdmin/adminGetAllBusman')
      if (res.data.code === 200){
        this.tableData = [];
        this.AllData=res.data.object;
        this.AllPageSize=this.AllData.length
        this.PagingSystem()
      }else {
        this.$message.error("获取商家信息失败，你代码写错了吧")
      }
    },
    /*--------------------------表格中的方法--------------------------*/
    async handleEdit(id, state) {
      const res=await this.axios.post('DexAdmin/EditBusmanState',this.$qs.stringify({
        id:id,
        busman_state:state,
      }))
      if (res.data.code === 200){
        await this.getAllBusman();
        this.$message.success('更改商户信息成功');
        this.adminLog(id,state)
      }else {
        this.$message.error('更改商户信息失败');
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