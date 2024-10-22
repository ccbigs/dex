<template>
  <div>
    <div class="user-manager">
      <el-table
          :data="tableData.filter(data => !search || data.bus_account.toLowerCase().includes(search.toLowerCase()))"
          stripe
          border
          style="width: 100%"
          height="560">
        <el-table-column
            type="index"
            align="center"
            width="40">
        </el-table-column>
        <el-table-column
            prop="account"
            label="账号"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="password"
            label="密码"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="nickname"
            label="昵称"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            label="头像"
            width="60">
          <template slot-scope="scope">
              <el-image
                  style="width: 40px; height: 40px"
                  :src="scope.row.headimage"
                  :preview-src-list="[scope.row.headimage]">
              </el-image>
          </template>
        </el-table-column>
        <el-table-column
            prop="realname"
            label="姓名"
            align="center"
            width="100">
        </el-table-column>
        <el-table-column
            label="性别"
            align="center"
            width="60">
          <template slot-scope="scope">
            <el-tag size="medium" v-if="scope.row.sex === '男'">{{ scope.row.sex }}</el-tag>
            <el-tag size="medium" type="success" v-if="scope.row.sex === '女'">{{ scope.row.sex }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="idcard"
            label="身份证"
            align="center"
            width="180">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="手机"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="email"
            label="邮箱"
            align="center"
            width="200">
        </el-table-column>
        <el-table-column
            prop="birthday"
            label="出生日期"
            width="120">
        </el-table-column>
        <el-table-column
            prop="area"
            label="所在地"
            width="220">
        </el-table-column>
        <el-table-column
            label="帐号状态"
            align="center"
            width="100">
          <template slot-scope="scope">
            <el-tag size="medium" v-if="scope.row.user_state === '正常'">{{ scope.row.user_state }}</el-tag>
            <el-tag size="medium" type="danger" v-if="scope.row.user_state === '封禁'">{{ scope.row.user_state }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            min-width="200px"
            align="center"
            fixed="right">
          <template slot="header" slot-scope="scope">
            <el-input
                v-model="search"
                size="mini"
                placeholder="输入商家账号搜索"/>
          </template>
          <template slot-scope="scope">
            <el-button
                size="mini" type="danger" round v-if="scope.row.user_state === '正常'"
                @click="handleEdit(scope.row.id,'账号封禁')">账号封禁</el-button>
            <el-button
                size="mini" type="primary" round v-if="scope.row.user_state === '封禁'"
                @click="handleEdit(scope.row.id,'账号启用')">账号启用</el-button>
            <el-button
                size="mini"
                type="danger" round
                @click="handleDelete(scope.row.id)">注销账号</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="user-page">
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
  name: "userManager",
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
    this.getAllUser()
  },
  methods:{
    /*------------------获得全部用户信息------------------*/
    async getAllUser(){
      const res= await this.axios.post('DexAdmin/adminGetAllUser')
      if (res.data.code === 200){
        this.tableData = [];
        this.AllData=res.data.object;
        this.AllPageSize=this.AllData.length
        this.PagingSystem(this.pageSize,this.currentPage);
      }else {
        this.$message.error("获取数据失败")
      }
    },
    /*--------------------------表格中的方法--------------------------*/
    async handleEdit(id, state) {
      const res=await this.axios.post('DexAdmin/adminModifyUserState',this.$qs.stringify({
        id:id,
        state:state,
      }))

      if (res.data.code === 200){
        await this.getAllUser();
        this.$message.success("修改用户状态成功")
        this.adminLogEdit(id,state)
      }else {
        this.$message.error("修改用户状态失败")
      }

    },
    async handleDelete(id) {
      const res=await this.axios.post('DexAdmin/adminDeleteUser',this.$qs.stringify({
        id:id,
      }))

      if (res.data.code === 200){
        await this.getAllUser();
        this.$message.success("删除用户成功")
        this.adminLogDelete(id)
      }else {
        this.$message.error("删除用户失败")
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
    adminLogEdit(id,message){
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员更改用户ID为:'+id+'的用户状态，更改状态为:'+message}`)
    },
    adminLogDelete(id){
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员不可逆操作，删除用户ID为:'+id+' 的所有信息'}`)
    }
  }
}
</script>

<style scoped>
  .user-manager{
    margin: 20px auto;
    width: 1466.670px;
    padding: 20px;
    height: 630px;
    background-color: white;
  }

  .user-page{
    margin-top: 26px;
    float: right;
  }
</style>