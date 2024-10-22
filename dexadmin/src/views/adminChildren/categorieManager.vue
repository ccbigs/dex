<template>
  <div>
    <div class="busman-manager">
      <div class="admin-catch">
        <el-button type="primary" icon="el-icon-plus" @click="dialogCategorieVisible=true">添加</el-button>
      </div>
      <el-table
          :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
          stripe
          border
          style="width: 100%"
          height="500">
        <el-table-column
            sortable
            prop="id"
            align="center"
            label="商品分类id"
            width="180">
        </el-table-column>
        <el-table-column
            prop="categorie_name"
            label="分类名称"
            align="center"
            width="250">
        </el-table-column>
        <el-table-column
            prop="categorie_create_time"
            label="分类创建时间"
            align="center"
            width="440">
        </el-table-column>
        <el-table-column
            prop="categorie_create_admin"
            label="创建分类管理员id"
            align="center"
            width="220">
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
                @click="handleEdit(scope.row.id,scope.row.categorie_name)">修改</el-button>
            <el-button
                size="mini" type="danger" round
                @click="handleDelete(scope.row.id)">删除</el-button>
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
    <admin-foot style="position:fixed;bottom: 0px;"></admin-foot>

    <el-dialog title="添加商品分类" :visible.sync="dialogCategorieVisible">
      <el-form :model="form">
        <el-form-item label="分类名称">
          <el-input v-model="form.categorie_name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogCategorieVisible = false">取 消</el-button>
        <el-button type="primary" @click="adminAddCategorie()">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import adminFoot from "../../components/adminFoot";
export default {
  name: "categorieManager",
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

      /*显示框*/
      dialogEditCategorieVisible:false,
      dialogCategorieVisible:false,
      form: {
        id:'',
        categorie_name: '',
        categorie_create_admin:'',
      },
      EditForm: {
        id:'',
        categorie_name: '',
        categorie_create_admin:'',
      },
    }
  },
  created() {
    this.getAllCatagorie();
  },
  methods:{
    /*----------------------获得全部管理员信息---------------------*/
    async getAllCatagorie(){
      const res=await this.axios.post('DexAdmin/adminGetAllCategorie')
      if (res.data.code === 200){
        this.AllData=res.data.object;
        this.AllPageSize=this.AllData.length
        this.PagingSystem()
      }else {
        this.tableData=[];
        this.$message.error("当前暂无商品信息")
      }
    },
    /*--------------------------表格中的方法--------------------------*/
    handleEdit(id,name) {
      this.EditForm.categorie_name=name;
      this.EditForm.id=id;
      this.dialogEditCategorieVisible=true;
    },

    async editCategorie(){
      this.EditForm.categorie_create_admin=1;
      const res=await this.axios.post('DexAdmin/adminEditCategorie',this.$qs.stringify(
          this.EditForm
      ))

      if (res.data.code === 200){
        this.adminLogEdit(this.EditForm.id)
        this.dialogEditCategorieVisible=false;
        this.$message.success("商品分类修改成功")
        await this.getAllCatagorie()
      }else {
        this.$message.error("商品分类修改失败")
      }
    },

    /*------------------------------------------------*/
    /*删除分类信息*/
    async handleDelete(id){
      const res=await this.axios.post('DexAdmin/adminDeleteCategorie',this.$qs.stringify({
        categorie_id:id,
      }))

      if (res.data.code === 200){
        this.$message.success("删除商品分类成功")
        await this.getAllCatagorie();
        this.adminLogDelete(id)
      }else {
        this.$message.error("删除商品分类失败")
      }
    },


    /*新增分类模块*/
    async adminAddCategorie(){
      this.form.categorie_create_admin=1;
      const res=await this.axios.post('DexAdmin/adminAddCategorie',this.$qs.stringify(
          this.form
      ))

      if (res.data.code === 200){
        this.adminLogAdd(this.form.categorie_name)
        this.dialogCategorieVisible=false;
        this.$message.success("商品分类数添加成功")
        await this.getAllCatagorie()
      }else {
        this.$message.error("商品分类数据添加失败")
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
    /*---------------日志行为---------------*/
    adminLogAdd(name){
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员新增商品分类名为:'+name+'的商品分类'}`)
    },
    adminLogEdit(id){
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员更改商品分类ID为:'+id+'的商品分类信息'}`)
    },
    adminLogDelete(id){
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员不可逆操作，删除商品分类ID为:'+id+' 的所有信息'}`)
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

.admin-catch{
  margin-bottom: 10px;
}
</style>