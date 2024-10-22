<template>
  <div>
    <div class="ware-manager">
      <el-table
          :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
          stripe
          border
          style="width: 100%"
          height="560">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <div>
                <el-form-item label="商品信息">
                  <span>{{ props.row.ware_connect }}</span>
                </el-form-item>
              </div>
              <el-form-item label="商品图片">
                <div class="comment-image">
                  <img :src="props.row.ware_img1" alt="">
                  <img :src="props.row.ware_img2" alt="">
                  <img :src="props.row.ware_img3" alt="">
                  <img :src="props.row.ware_img4" alt="">
                  <img :src="props.row.ware_img5" alt="">
                </div>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
            label="编号"
            prop="id"
            align="center"
            width="80">
        </el-table-column>
        <el-table-column
            prop="ware_name"
            label="商品名称"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            prop="ware_describe"
            label="商品描述"
            align="center"
            width="250">
        </el-table-column>
        <el-table-column
            prop="ware_price"
            label="单价(元)"
            align="center"
            width="100">
        </el-table-column>
        <el-table-column
            prop="ware_number"
            label="库存"
            align="center"
            width="80">
        </el-table-column>
        <el-table-column
            label="销量"
            align="center"
            width="60">
          <template slot-scope="scope">
            <span>{{ scope.row.ware_totalnumber - scope.row.ware_number }}</span>
          </template>
        </el-table-column>
        <el-table-column
            prop="ware_totalnumber"
            label="商品累计总数"
            align="center"
            width="120">
        </el-table-column>
        <el-table-column
            label="商品状态"
            align="center"
            width="100">
          <template slot-scope="scope">
            <el-tag size="medium" v-if="scope.row.ware_state === '售卖中'">{{ scope.row.ware_state }}</el-tag>
            <el-tag size="medium" type="danger" v-if="scope.row.ware_state === '已下架'">{{ scope.row.ware_state }}</el-tag>
            <el-tag size="medium" type="success" v-if="scope.row.ware_state === '审核中'">{{ scope.row.ware_state }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            label="活动参与"
            align="center"
            width="120">
          <template slot-scope="scope">
            <el-tag size="medium" type="warning" v-if="scope.row.ware_active === '未参与活动'">{{ scope.row.ware_active }}</el-tag>
            <el-tag size="medium" type="success" v-if="scope.row.ware_active === '打折'">{{ scope.row.ware_active }}</el-tag>
            <el-tag size="medium" type="success" v-if="scope.row.ware_active === '满减'">{{ scope.row.ware_active }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="ware_date"
            label="上线日期"
            align="center"
            width="180">
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
                size="mini" type="primary" round v-if="scope.row.ware_state === '审核中'"
                @click="handleEditState(scope.row.id,'商品上架')">商品上架</el-button>
            <el-button
                size="mini" type="danger" round v-if="scope.row.ware_state === '售卖中'"
                @click="handleEditState(scope.row.id,'点击下架')">点击下架</el-button>
            <el-button
                size="mini" type="success" round v-if="scope.row.ware_state === '已下架'"
                @click="handleEditState(scope.row.id,'点击上架')">点击上架</el-button>
            <el-button
                size="mini"
                type="danger" round
                @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="ware-page">
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
    <admin-foot style="position: absolute;bottom: 0px;"></admin-foot>
  </div>
</template>

<script>
import adminFoot from "../../components/adminFoot";
export default {
  name: "wareManager",
  components:{
    'adminFoot':adminFoot,
  },
  data(){
    return{
      /*搜索的关键字*/
      search: '',
      /*翻页*/
      currentPage: 1,
      pageSize:6,
      AllPageSize:0,


      AllData: [],
      tableData: [],
    }
  },
  created() {
    this.getAllWare()
  },
  methods:{
    /*------------------获得全部用户信息------------------*/
    async getAllWare(){
      const res= await this.axios.post('DexAdmin/adminGetAllWare')
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
    async handleEditState(id, state) {
      const res=await this.axios.post('DexAdmin/adminModifyWareState',this.$qs.stringify({
        id:id,
        ware_state:state,
      }))
      if (res.data.code === 200){
        await this.getAllWare();
        this.$message.success('更改商品状态成功');
        this.adminLogEdit(id,state)
      }else {
        this.$message.error('更改商品状态失败');
      }
    },

    async handleDelete(ware_id) {
      const res=await this.axios.post('DexAdmin/adminDeleteWare',this.$qs.stringify({
        id:ware_id,
      }))
      if (res.data.code === 200){
        await this.getAllWare();
        this.$message.success('删除商品成功');
        this.adminLogDelete(ware_id);
      }else {
        this.$message.error('删除商品信息失败');
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
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员更改商品ID为: '+id+' 的商品状态，更改状态为:'+message}`)
    },
    adminLogDelete(id){
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员不可逆操作，删除商品ID为:'+id+' 的所有信息'}`)
    }
  }
}
</script>

<style scoped>
.ware-manager{
  margin: 20px auto;
  width: 1466.670px;
  padding: 20px;
  height: 630px;
  background-color: white;
}

.ware-page{
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