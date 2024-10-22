<template>
  <div>
    <div class="busman-manager">
      <el-table
          :data="tableData.filter(data => !search || data.behavior.toLowerCase().includes(search.toLowerCase()))"
          stripe
          border
          style="width: 100%"
          height="538">
        <el-table-column
            prop="id"
            width="40">
        </el-table-column>
        <el-table-column
            align="center"
            prop="user"
            label="角色"
            width="150">
        </el-table-column>
        <el-table-column
            prop="user_id"
            label="角色ID"
            width="120">
        </el-table-column>
        <el-table-column
            prop="behavior"
            label="角色行为"
            align="center">
        </el-table-column>
        <el-table-column
            prop="user_type"
            label="角色类型"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            sortable
            prop="time"
            label="日志产生时间"
            align="center"
            width="200">
        </el-table-column>
        <el-table-column
            prop="internetProtocol"
            label="IP地址"
            align="center"
            width="300">
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
import adminFoot from "../../../components/adminFoot";
export default {
  name: "logSetting",
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
    this.adminGetAllLogs();
  },
  methods:{
    async adminGetAllLogs(){
      const res=await this.axios.post('DexAdmin/adminGetAllLog')
      if (res.data.code === 200){
        this.AllData=res.data.object;
        this.AllPageSize=this.AllData.length
        this.PagingSystem()
      }else {
        this.$message.warning("当前暂无日志信息")
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

.admin-catch{
  margin-bottom: 10px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>