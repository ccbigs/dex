<template>
  <div>
    <div class="busman-manager">
      <div class="admin-catch">
        <el-button type="primary" icon="el-icon-plus" @click="ClickAddNewTab">添加</el-button>
      </div>
      <el-table
          :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
          stripe
          border
          style="width: 100%"
          height="500">
        <el-table-column
            prop="tabName"
            label="分类名称"
            align="center"
            width="250">
        </el-table-column>
        <el-table-column
            prop="tabType"
            label="标题等级"
            align="center"
            width="200">
        </el-table-column>
        <el-table-column
            label="父标题"
            align="center"
            width="220">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.tabChain === '顶级标题'" type="success">顶级标题</el-tag>
            <el-tag v-else>{{scope.row.tabChain}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="infoId"
            label="关联文章"
            align="center"
            width="200">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.infoId === -1" type="success">无需关联文章</el-tag>
            <el-tag v-else-if="scope.row.infoId === 0" type="warning">暂无文章，赶快去撰写文章吧</el-tag>
            <el-tag v-else>已有文章</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="tabCreatetime"
            label="创建时间"
            align="center"
            width="360">
        </el-table-column>
        <el-table-column
            align="center"
            min-width="300px"
            fixed="right">
          <template slot="header" slot-scope="scope">
            <el-input
                v-model="search"
                size="mini"
                placeholder="输入关键字搜索"/>
          </template>
          <template slot-scope="scope">
            <el-button
                size="mini" type="primary" round v-if="scope.row.tabType !== '一级标题'"
                @click="handleEdit(scope.row)">修改内容</el-button>
            <el-button
                size="mini" type="danger" round
                @click="handleDelete(scope.row)">删除</el-button>
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

    <shop-footer :footTableDate="footData" :key="componentKey"></shop-footer>
    <admin-foot style="position: fixed;bottom: 0px;"></admin-foot>

    <el-dialog title="添加Dex商城标题" :visible.sync="dialogTabVisible">
      <el-form :model="formTab" :rules="rules" ref="formTab">
        <el-form-item label="标题名称">
          <el-input v-model="formTab.tabName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="标题等级">
          <el-select v-model="formTab.tabType" placeholder="活动区域">
            <el-option label="一级标题" value="一级标题"></el-option>
            <el-option label="二级标题" value="二级标题"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="formTab.tabType==='二级标题'" label="所属父标题">
          <el-select v-model="formTab.tabChain" placeholder="父标题">
            <el-option v-if="o.tabType==='一级标题'" :label="o.tabName" :value="o.tabName" v-for="(o,i) in AllData"></el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTabVisible = false">取 消</el-button>
        <el-button type="primary" @click="adminAddTab('formTab')">添 加</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import adminFoot from "../../../components/adminFoot";
import shopFooter from "../../../components/dataPanelCom/shopFooter";
export default {
  name: "dexFootMessage",
  components:{
    adminFoot:adminFoot,
    shopFooter:shopFooter
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

      /*底部数据*/
      footData:[],

      dialogTabVisible:false,
      formTab: {
        id:'',
        tabName: '',
        tabType:'',
        tabChain:'',
      },
      rules: {
        tabName: [
          {required: true, message: '请输入标题名称', trigger: 'blur'},
          {min: 0, max: 25, message: '长度在 0 到 25 个字符', trigger: 'blur'}
        ],
      },
      componentKey: 0,
    }
  },
  created() {
    this.getAllTab()
  },
  methods:{
    /*获取已有的所有标题*/
    async getAllTab(){
      const res =await this.axios.post('tab/getAllTab')
      if (res.data.code === 200){
        this.tableData = [];
        this.AllData = res.data.object;
        this.AllPageSize = res.data.size;
        this.PagingSystem();
        this.asyxTab(this.AllData)
      }else {
        this.$message.error("当前暂无数据")
      }
    },
    /*-------修改内容---------*/
    handleEdit(tab){
      this.$router.push({
        name:'dexFootMessageShow',
        query:tab
      })
    },
    /*删除改标题*/
    handleDelete(tab){
      if (tab.tabType === '一级标题'){
        this.$confirm('删除一级标题会一同删除它的子标题, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.DeleteDate(tab)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }else {
        this.$confirm('删除二级标题后文章信息也会永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.DeleteDate(tab)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    },
    async DeleteDate(tab){
      const res = await this.axios.post('tab/DeleteTab',this.$qs.stringify(tab))
      if (res.data.code === 200){
        this.$message.success("删除成功")
        await this.getAllTab();
      }else {
        this.$message.error("删除失败")
      }
    },
    /*-------------------新增标题-------------------*/
    ClickAddNewTab(){
      this.dialogTabVisible = true;
      this.formTab.tabName='';
      this.formTab.tabType='';
      this.formTab.tabChain='';
    },
    async adminAddTab(formTab){
      this.$refs[formTab].validate((valid) => {
        if (valid) {
          this.axios.post('tab/AdminBuildNewTab',this.$qs.stringify(
              this.formTab
          )).then((res)=>{
            if (res.data.code === 200){
              this.getAllTab();
              this.dialogTabVisible = false;
              this.$message.success("添加成功")
            }else {
              this.$message.error("新增标题失败")
            }
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    /*------------返回父标题的名称---------------*/
    returnFatherTab(tabChain){
      for (let i = 0; i < this.AllData.length; i++) {
        if (tabChain === this.AllData[i].id){
          return this.AllData[i].tabName
        }
      }
    },
    /*-------计算底部数据，并发送给子组件--------*/
    /*分配标题个标题的内容*/
    asyxTab(object){
      let index=0;
      /*遍历出一个数组*/
      for (let i = 0; i < object.length; i++) {
        if (object[i].tabType === '一级标题'){
          this.footData[index]= object[i];
          this.footData[index].sonChain = [];
          index++;
        }
      }

      /*数组里面再追加数组*/
      for (let i = 0; i < object.length; i++) {
        for (let j = 0; j <this.footData.length ; j++) {
          if (object[i].tabChain === this.footData[j].tabName){
            this.footData[j].sonChain.push(object[i])
          }
        }
      }

      this.componentKey += 1;

      console.log("=======================>")
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
      console.log("进入1------------------")
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
</style>