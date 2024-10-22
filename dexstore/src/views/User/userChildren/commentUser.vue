<template>
  <div>
    <!--页头信息-->
    <div class="head-title">我的评论</div>
    <el-divider></el-divider>
    <div class="table-data">
      <el-table
          :data="tableData"
          style="width: 100%">
        <!--站开业内容-->
        <el-table-column type="expand" width="40">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <div class="comment-image">
                <el-form-item label="评论图片">
                  <div> <img :src="props.row.comment_img1" alt=""></div>
                  <div> <img :src="props.row.comment_img2" alt=""></div>
                  <div> <img :src="props.row.comment_img3" alt=""></div>
                </el-form-item>
              </div>

              <el-form-item label="评论时间">
                <span>{{ props.row.comment_date }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <!--此处写上商品的图片和名称-->
        <el-table-column label="商品" align="center" width="200" prop="">
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
        <!--评价内容-->
        <el-table-column label="评价详情" align="center" width="600">
          <template #default="scope">
            <div>
            {{scope.row.comment_connect}}
            </div>
          </template>
        </el-table-column>
        <!--商品状态-->
        <el-table-column
            label="评价星级"
            align="center"
            width="160"
            show-overflow-tooltip>
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
            align="center"
            fixed="right"
            label="操作"
            width="200">
          <template slot-scope="scope">
            <el-button type="danger" size="small"
                       @click="handleDelete(scope.$index, scope.row.id)">删除评价</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="order-page">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[6, 20, 30, 40]"
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
  name: "commentUser",
  data(){
    return {
      value:4,
      totalNum:0,
      totalPrice:0,
      tableData:'',
      multipleSelection: [],

      /*翻页*/
      AllData: [],
      currentPage: 1,
      pageSize:6,
      AllPageSize:0,
    }
  },
  async created() {
    await this.getAllComment();
  },
  methods:{
    /*获取用户的全部评论*/
    async getAllComment(){
      const res=await this.axios.post('http://localhost:8681/dex/comment/getCommentByUser',this.$qs.stringify({
        user_id:this.$store.state.userInfo.id
      }))
      if (res.data.code === 200){
        this.AllData = res.data.object;
        this.AllPageSize=res.data.size;
        await this.PagingSystem(6,1);
      }
    },
    /*删除用户当前的评价*/
    async handleDelete(index, id) {
      const res=await this.axios.post('http://localhost:8681/dex/comment/removeUserComment',this.$qs.stringify({
        comment_id:id
      }))

      if (res.data.code === 200){
        this.successMsg()
        await this.getAllComment();
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
    /*成功提示*/
    successMsg() {
      this.$notify({
        title: '删除成功',
        message: '成功删除评论',
        type: 'success'
      });
    },
    /*失败提示*/
    filedMsg() {
      this.$notify.error({
        title: '删除失败',
        message: '评论删除失败',
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
  .comment-image{
    display: flex;
    align-items: center;
  }

  .comment-image div{
    display: inline-block;
  }

  .comment-image img{
    height: 100px;
    margin-right: 20px;
  }

  .order-page{
    margin-top: 16px;
    margin-bottom: 10px;
    float: right;
  }
</style>