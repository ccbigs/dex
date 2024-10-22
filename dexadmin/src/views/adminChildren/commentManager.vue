<template>
  <div>
    <div class="comment-manager">
    <el-table
        :data="tableData.filter(data => !search || data.id.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%"
        border
        stripe
        height="560">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <div>
            <el-form-item label="评论信息">
              <span>{{ props.row.comment_connect }}</span>
            </el-form-item>
            </div>
            <el-form-item label="评论图片">
              <div class="comment-image">
                <img :src="props.row.comment_img1" alt="">
                <img :src="props.row.comment_img2" alt="">
                <img :src="props.row.comment_img3" alt="">
              </div>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
          prop="id"
          align="center"
          label="评论id"
          width="120">
      </el-table-column>

      <el-table-column
          prop="orders_id"
          align="center"
          label="订单号"
          width="120">
      </el-table-column>

      <el-table-column
          prop="ware_id"
          align="center"
          label="商品编号"
          width="120">
      </el-table-column>

      <el-table-column
          width="80"
          align="center"
          label="商品图片">
        <template slot-scope="scope">
          <el-image
              style="width: 40px; height: 40px"
              :src="scope.row.ware_img1"
              :preview-src-list="[scope.row.ware_img1]">
          </el-image>
        </template>
      </el-table-column>

      <el-table-column
          width="230"
          align="center"
          label="商品信息">
        <template slot-scope="props">
          <div class="props-mode"><div>{{props.row.ware_name}}</div></div>
        </template>
      </el-table-column>


      <el-table-column
      width="160"
      align="center"
      prop="user_nickname"
      label="用户昵称">
      </el-table-column>

      <el-table-column
          label="评分"
          align="center"
          width="180">
        <template #default="scope">
          <el-rate
              v-model="scope.row.comment_rate"
              disabled
              show-score
              text-color="#ff9900"
              score-template="{value}">
          </el-rate>
        </template>
      </el-table-column>
      <el-table-column
          width="180"
          align="center"
          prop="comment_date"
          label="评论时间">
      </el-table-column>

      <el-table-column
          align="center"
          min-width="200px"
          fixed="right">
        <template slot="header" slot-scope="scope">
          <el-input
              v-model="search"
              size="mini"
              placeholder="输入订单号搜索"/>
        </template>
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="danger" round
              @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="comment-page">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[7, 10, 20, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="AllPageSize">
      </el-pagination>
    </div>
  </div>
    <admin-foot style="position: fixed;bottom: 0px;"></admin-foot>
  </div>
</template>

<script>
import adminFoot from "../../components/adminFoot";
export default {
  name: "commentManager",
  components:{
    'adminFoot':adminFoot,
  },
  data() {
    return {
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
    this.getAllComment()
  },
  methods:{
    /*------------------获得全部用户信息------------------*/
    async getAllComment(){
      const res= await this.axios.post('DexAdmin/adminGetAllComment')
      if (res.data.code === 200){
        this.tableData = [];
        this.AllData=res.data.object;
        this.AllPageSize=this.AllData.length
    /*    console.log(res);*/
        this.PagingSystem(this.pageSize,this.currentPage);
      }else {
        this.$message.error("获取数据失败")
      }
    },
    /*--------------------------表格中的方法--------------------------*/
    async handleDelete(comment_id) {
      const res=await this.axios.post('DexAdmin/adminDeleteComment',this.$qs.stringify({
        id:comment_id,
      }))
      if (res.data.code === 200){
        await this.getAllComment();
        this.$message.success('删除评论信息成功');
        this.adminLogDelete(comment_id)
      }else {
        this.$message.error('删除评论信息失败');
      }
    },
    /*----------------------------翻页的方法--------------------------*/
    handleSizeChange(val) {
      this.pageSize=val;
      this.PagingSystem(this.pageSize,this.currentPage);
    },
    handleCurrentChange(val) {
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
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员不可逆操作，删除日志ID为:'+id+' 的所有信息'}`)
    }
  }
}
</script>

<style scoped>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
.comment-manager{
  margin: 20px auto;
  width: 1466.670px;
  padding: 20px;
  height: 630px;
  background-color: white;
}

.comment-page{
  margin-top: 26px;
  float: right;
}

.props-mode{
  display: flex;
  justify-content: center;
}

.comment-image{
  display: flex;
  justify-content: left;
  align-items: center;
  margin-top: 10px;
}

.comment-image img{
  height: 100px;
  margin-right: 10px;
}

</style>