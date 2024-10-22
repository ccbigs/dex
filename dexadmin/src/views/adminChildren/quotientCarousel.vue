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
            label="轮播图编号"
            prop="id"
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
            label="轮播图片"
            align="center"
            width="200">
          <template slot-scope="scope">
            <el-image
                style="width: 40px; height: 40px"
                :src="scope.row.img"
                :preview-src-list="[scope.row.img]">
            </el-image>
          </template>
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
            prop="createDate"
            label="购买日期"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="endDate"
            label="截止日期"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="carousel_state"
            label="轮播图使用状态"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            label="轮播图时间状态"
            align="center"
            width="120">
          <template slot-scope="scope">
            <el-tag size="medium" v-if="scope.row.type_time === '尚未过期'">{{scope.row.type_time}}</el-tag>
            <el-tag size="medium" type="danger" v-else>{{ scope.row.type_time }}</el-tag>
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
                placeholder="输入关键字搜索"/>
          </template>
          <template slot-scope="scope">
            <el-button
                size="mini" type="warning" round
                v-if="scope.row.type_time === '尚未过期' && scope.row.carousel_state === '启用'"
                @click="handleEdit(scope.row.id,'禁用轮播图')">禁用轮播图</el-button>
            <el-button
                size="mini" type="primary" round
                v-if="scope.row.type_time === '尚未过期' && scope.row.carousel_state === '停用'"
                @click="handleEdit(scope.row.id,'启用轮播图')">启用轮播图</el-button>
            <el-button
                size="mini"
                type="danger" round
                @click="handleDelete(scope.row.id)">删除</el-button>
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
  name: "quotientCarousel",
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
    this.getAllCarousel()
  },
  methods:{
    /*------------------获得全部用户信息------------------*/
    async getAllCarousel(){
      const res= await this.axios.post('DexAdmin/adminGetAllCarousel')
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
      const res=await this.axios.post('DexAdmin/adminModifyCarouselState',this.$qs.stringify({
        id:id,
        carousel_state:state,
      }))
      if (res.data.code === 200){
        await this.getAllCarousel();
        this.$message.success('更改轮播图状态成功');
        this.adminLogEdit(id,state)
      }else {
        this.$message.error('更改轮播图状态失败');
      }
    },
    async handleDelete(id){
      const res = await this.axios.post('DexAdmin/DeleteCarouse',this.$qs.stringify({
        CarouselId:id
      }))

      if (res.data.code === 200){
        this.$message.success("删除成功")
        await this.getAllCarousel();
      }else {
        this.$message.error("删除失败")
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
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员更改商位轮播图ID为:'+id+'的商位轮播图状态，更改状态为:'+message}`)
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