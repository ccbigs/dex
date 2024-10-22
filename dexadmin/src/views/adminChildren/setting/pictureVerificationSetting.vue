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
            width="150">
        </el-table-column>
        <el-table-column
            label="图片"
            align="center"
            width="500">
          <template slot-scope="scope">
            <el-image
                style="width: 60px; height: 60px"
                :src="scope.row.img_url"
                :preview-src-list="[scope.row.img_url]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column
            prop="img_type"
            label="图片类型"
            align="center"
            width="400">
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
                @click="handleEdit(scope.$index)">修改</el-button>
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
            :page-sizes="[5, 20, 30, 40]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="AllPageSize">
        </el-pagination>
      </div>
    </div>
    <admin-foot style="position:fixed;bottom: 0px;"></admin-foot>

    <el-dialog title="添加图片验证" :visible.sync="dialogCategorieVisible">
      <el-form :model="form">
        <el-form-item label="上传图片">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8683/dex/DexAdmin/uploadUserHeaderImage"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
            <img v-if="form.img_url" :src="form.img_url" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="图片类型">
          <el-input v-model="form.img_type" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogCategorieVisible = false">取 消</el-button>
        <el-button type="primary" @click="adminAddPictureVerification()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="修改图片信息" :visible.sync="dialogEditCategorieVisible">
      <el-form :model="EditForm">
        <el-form-item label="上传图片">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8683/dex/DexAdmin/uploadUserHeaderImage"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
            <img v-if="EditForm.img_url" :src="EditForm.img_url" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="图片类型">
          <el-input v-model="EditForm.img_type" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditCategorieVisible = false">取 消</el-button>
        <el-button type="primary" @click="editCategorie()">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import adminFoot from "../../../components/adminFoot";
export default {
  name: "pictureVerificationSetting",
  components:{
    'adminFoot':adminFoot,
  },
  data(){
    return{
      /*搜索的关键字*/
      search: '',
      /*翻页*/
      currentPage: 1,
      pageSize:5,
      AllPageSize:0,

      AllData: [],
      tableData: [],



      /*显示框*/
      dialogEditCategorieVisible:false,
      dialogCategorieVisible:false,
      form: {
        img_type: '',
        img_url: '',
      },
      EditForm: {
        id:'',
        img_type: '',
        img_url: '',
      },
    }
  },
  created() {
    this.getPictureVerification();
  },
  methods:{
    /*----------------------获得全部验证图片的信息---------------------*/
    async getPictureVerification(){
      const res=await this.axios.post('DexAdmin/adminGetPictureVerification')
      if (res.data.code === 200){
        this.AllData=res.data.object;
        this.AllPageSize=this.AllData.length
        this.PagingSystem()
      }else {
        this.tableData=[];
        this.$message.error("当前暂无商品信息")
      }
    },
    /*--------------------------编辑图片信息的方法--------------------------*/
    handleEdit(index) {
      this.EditForm.id=this.tableData[index].id;
      this.EditForm.img_type=this.tableData[index].img_type;
      this.EditForm.img_url=this.tableData[index].img_url;
      this.dialogEditCategorieVisible=true;
    },

    async editCategorie(){
      const res=await this.axios.post('DexAdmin/adminEditPictureVerification',this.$qs.stringify(
          this.EditForm
      ))

      if (res.data.code === 200){
        this.adminLogEdit(this.EditForm.id)
        this.dialogEditCategorieVisible=false;
        this.$message.success("商品分类修改成功")
        await this.getPictureVerification()
      }else {
        this.$message.error("商品分类修改失败")
      }
    },

    /*------------------------------------------------*/
    /*删除验证的图片*/
    async handleDelete(id){
      const res=await this.axios.post('DexAdmin/adminDetelePicture',this.$qs.stringify({
        Picture_id:id,
      }))

      if (res.data.code === 200){
        this.adminLogDelete(id)
        this.$message.success("删除商品分类成功")
        await this.getPictureVerification();
      }else {
        this.$message.error("删除商品分类失败")
      }
    },


    /*新增图片信息*/
    async adminAddPictureVerification(){
      if (!this.form.img_url){
        this.$message.error("图片不能为空")
      }else {
        const res=await this.axios.post('DexAdmin/adminAddPictureVerification',this.$qs.stringify(
            this.form
        ))
        if (res.data.code === 200){
          this.adminLogAdd(this.form.img_type)
          this.dialogCategorieVisible=false;
          this.$message.success("商品分类数添加成功")
          this.form.img_url='';
          this.form.img_type='';
          await this.getPictureVerification()
        }else {
          this.$message.error("商品分类数据添加失败")
        }
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
    /*上传图片*/
    handleAvatarSuccess(res, file) {
      this.form.img_url = res.url;
    },
    /*---------------日志行为---------------*/
    adminLogAdd(name){
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员新增验证图片类名为:'+name+'的验证图片'}`)
    },
    adminLogEdit(id){
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员更改验证图片ID为:'+id+'的验证图片'}`)
    },
    adminLogDelete(id){
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员不可逆操作，删除验证图片ID为:'+id+' 的所有信息'}`)
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