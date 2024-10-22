<template>
  <div>
    <div class="busman-manager">
      <div class="admin-catch">
        <el-button type="primary" icon="el-icon-plus" @click="dialogAdminVisible=true">添加</el-button>
      </div>
      <el-table
          :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
          stripe
          border
          style="width: 100%"
          height="538">
        <el-table-column
            sortable
            prop="id"
            label="管理员唯一编码"
            align="center"
            width="150">
        </el-table-column>
        <el-table-column
            prop="account"
            label="管理员账号"
            width="120">
        </el-table-column>
        <el-table-column
            prop="password"
            label="管理员密码"
            align="center"
            width="140">
        </el-table-column>
        <el-table-column
            prop="admin_type"
            label="管理员类型"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="create_time"
            label="创建时间"
            align="center"
            width="200">
        </el-table-column>
        <el-table-column
            prop="last_time"
            label="上一次登录时间"
            align="center"
            width="200">
        </el-table-column>
        <el-table-column
            label="商家状态"
            align="center"
            width="100">
          <template slot-scope="scope">
            <el-tag size="medium" v-if="scope.row.admin_state === '启用'">{{ scope.row.admin_state }}</el-tag>
            <el-tag size="medium" type="danger" v-else>{{ scope.row.admin_state }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="remarks"
            label="备注信息"
            align="center"
            width="200">
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
                size="mini" type="danger" round
                v-if="scope.row.admin_state === '启用'"
                @click="updateAdminState(scope.row.id,'禁用账号')">禁用账号</el-button>
            <el-button
                size="mini" round
                v-else
                @click="updateAdminState(scope.row.id,'启用账号')">启用账号</el-button>
            <el-button
                size="mini"
                type="primary" round
                @click="updateAdminRemarks(scope.row,scope.$index)">修改信息</el-button>
            <el-button
                size="mini"
                type="danger" round
                @click="deleteSecondAdmin(scope.row.id)">注销</el-button>
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

    <el-dialog title="修改备注信息" :visible.sync="dialogRemarksVisible">
      <el-form :model="remarkFrom">
        <el-form-item label="备注信息">
          <el-input type="textarea" v-model="remarkFrom.remarks" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogRemarksVisible = false">取 消</el-button>
        <el-button type="primary" @click="uploadUpdateRemarks()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="添加二级管理员" :visible.sync="dialogAdminVisible">
      <el-form :model="adminFrom">
        <el-form-item label="账号">
          <el-input v-model="adminFrom.account" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="adminFrom.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注信息">
          <el-input type="textarea" v-model="adminFrom.remarks" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAdminVisible = false">取 消</el-button>
        <el-button type="primary" @click="addSecondAdmin()">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import adminFoot from "../../../components/adminFoot";

export default {
  name: "adminManager",
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

      /*修改信息页面*/
      dialogRemarksVisible:false,
      remarkFrom:{
        id:'',
        remarks:'',
        index:'',
      },

      /*二级管理员页面信息*/
      dialogAdminVisible:false,
      adminFrom:{
        account:'',
        password:'',
        remarks:'',
      }
    }
  },
  created() {
    this.getAllAdmin();
  },
  methods:{
    /*获取全部的商家信息*/
    async getAllAdmin(){
      const res= await this.axios.post('DexAdmin/GetAllAdmins',this.$qs.stringify({
        level:this.$store.state.adminInfo.admin_type
      }))

      if (res.data.code === 200){
        this.AllData = res.data.object;
        this.AllPageSize=this.AllData.length
        this.PagingSystem()
      }else {
        this.$message.warning("系统繁忙，请稍后访问。")
      }

    },
    /*更改管理员的状态*/
    async updateAdminState(id,state){
      const res= await this.axios.post('DexAdmin/UpdateAdminState',this.$qs.stringify({
        admin_id:id,
        state:state
      }))

      if (res.data.code === 200){
        this.$message.success("更改状态成功")
        await this.getAllAdmin();
      }else {
        this.$message.error("更改信息失败")
      }
    },

    /*修改备注信息*/
    updateAdminRemarks(admin,index){
      this.remarkFrom.id = admin.id;
      this.remarkFrom.remarks = admin.remarks;
      this.remarkFrom.index = index;
      this.dialogRemarksVisible = true;

    },
    /*上传修改的备注信息*/
    async uploadUpdateRemarks(){
      const res =await this.axios.post('DexAdmin/UpdateRemarks',this.$qs.stringify({
        adminId:this.remarkFrom.id,
        remarks:this.remarkFrom.remarks,
      }))

      if (res.data.code === 200){
        this.$message.success("备注信息成功")
        this.AllData[this.remarkFrom.index].remarks = this.remarkFrom.remarks;
        this.dialogRemarksVisible=false;
      }else {
        this.$message.error("备注信息修改失败")
      }
    },

    /*添加二级管理员*/
    async addSecondAdmin(){
      const res =await this.axios.post('DexAdmin/addSecondAdmin',this.$qs.stringify(this.adminFrom))

      if (res.data.code === 200){
        this.$message.success("创建二级管理员成功")
        await this.getAllAdmin();
        this.dialogAdminVisible=false;
      }else {
        this.$message.error("创建二级管理员失败")
      }
    },
    /*注销二级管理员*/
    async deleteSecondAdmin(id){
      const res = await this.axios.post('DexAdmin/deleteSecondAdmin',this.$qs.stringify({
        adminId:id
      }))

      if (res.data.code === 200){
        this.$message.success("二级管理员删除成功")
        await this.getAllAdmin();
      }else {
        this.$message.error("二级管理员删除失败")
      }
    },
    /*================翻页方法===============*/
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
  margin-top: 16px;
  float: right;
}

.admin-catch{
  margin-bottom: 10px;
}
</style>