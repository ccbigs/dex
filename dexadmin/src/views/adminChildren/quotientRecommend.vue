<template>
  <div>
    <div class="order-carousel-manager">
      <el-table
          :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
          stripe
          border
          style="width: 100%"
          height="560">
        <el-table-column
            label="推荐商品编号"
            prop="id"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            label="商品编号"
            prop="ware_id"
            align="center"
            width="80">
        </el-table-column>
        <el-table-column
            label="商家编号"
            prop="busman_id"
            align="center"
            width="80">
        </el-table-column>
        <el-table-column
            prop="ware_name"
            label="商品名称"
            align="center"
            width="300">
        </el-table-column>
        <el-table-column
            prop="bus_name"
            label="商家姓名"
            align="center"
            width="100">
        </el-table-column>
        <el-table-column
            prop="quotient_order_id"
            label="订单编号"
            align="center"
            width="100">
        </el-table-column>
        <el-table-column
            prop="createdate"
            label="购买日期"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="enddate"
            label="截止日期"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="recommend_state"
            label="商品使用状态"
            align="center"
            width="120">
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
                size="mini" type="warning" round
                v-if="scope.row.type_time === '尚未过期' && scope.row.recommend_state === '启用'"
                @click="handleEdit(scope.row.id,'禁用推荐商品')">禁用推荐商品</el-button>
            <el-button
                size="mini" type="primary" round
                v-if="scope.row.type_time === '尚未过期' && scope.row.recommend_state === '停用'"
                @click="handleEdit(scope.row.id,'启用推荐商品')">启用推荐商品</el-button>
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
  name: "quotientRecommend",
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
    this.getAllRecommend()
  },
  methods:{
    /*------------------获得全部用户信息------------------*/
    async getAllRecommend(){
      const res= await this.axios.post('DexAdmin/adminGetAllRecommend')
      if (res.data.code === 200){
        this.AllData=res.data.object;
        this.AllPageSize=this.AllData.length
        this.PagingSystem(10,1);
      }else {
        this.$message.error("获取数据失败")
      }
    },
    /*--------------------------表格中的方法--------------------------*/
    async handleEdit(id, state) {
      const res=await this.axios.post('DexAdmin/adminModifyRecommendState',this.$qs.stringify({
        id:id,
        recommend_state:state,
      }))
      if (res.data.code === 200){
        await this.getAllRecommend();
        this.$message.success('更改推荐商品状态成功');
        this.adminLogEdit(id,state)
      }else {
        this.$message.error('更改推荐商品状态失败');
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
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员更改商位推荐ID为:'+id+'的商位推荐状态，更改状态为:'+message}`)
    },
  }
}
</script>

<style scoped>
.order-carousel-manager{
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