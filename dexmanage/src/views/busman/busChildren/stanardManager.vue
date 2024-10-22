<template>
  <div>
    <div class="busman-manager">
      <el-table
          :data="tableData.filter(data => !search || data.standards_name.toLowerCase().includes(search.toLowerCase()))"
          stripe
          border
          style="width: 100%"
          height="538">
        <el-table-column
            sortable
            prop="id"
            label="规格id"
            align="center"
            width="130">
        </el-table-column>
        <el-table-column
            prop="ware_name"
            label="商品名称"
            align="center"
            width="260">
        </el-table-column>
        <el-table-column
            prop="standards_name"
            label="商品规格"
            align="center"
            width="180">
        </el-table-column>
        <el-table-column
            prop="standards_total"
            label="总数量(件)"
            align="center"
            width="100">
        </el-table-column>
        <el-table-column
            prop="standards_number"
            label="剩余数量(件)"
            align="center"
            width="100">
        </el-table-column>
        <el-table-column
            prop="standards_price"
            label="定价(元)"
            align="center"
            width="140">
        </el-table-column>
        <el-table-column
            prop="create_time"
            label="创建时间"
            align="center"
            width="180">
        </el-table-column>
        <el-table-column
            align="center"
            min-width="200px"
            fixed="right">
          <template slot="header" slot-scope="scope">
            <el-input
                v-model="search"
                size="mini"
                placeholder="输入商品规格信息搜索"/>
          </template>
          <template slot-scope="scope">
            <el-button
                size="mini" type="primary" round
                @click="addStandardNumber(scope.row.id,scope.row.ware_id)">添加规格库存</el-button>
            <el-button
                size="mini"
                type="danger" round
                @click="deleteStandard(scope.row.id)">删除商品规格</el-button>
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

    <!--添加库存-->
    <el-dialog title="商品库存添加" v-model="dialogTotalVisible" :visible.sync="dialogTotalVisible">
      <el-form :model="totalForm" :rules="rulesForm" ref="ruleForm" label-width="120px" class="demo-ruleForm">
        <el-form-item label="添加库存数量" prop="ware_totalnumber">
          <el-input-number v-model="totalForm.ware_totalnumber" :min="1" :max="1000" label="描述文字"></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitTotal('ruleForm')">立即创建</el-button>
          <el-button @click="restTotal('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "stanardManager",
  data(){
    return{
      dialogTotalVisible:false,
      /*搜索的关键字*/
      search: '',
      /*翻页*/
      currentPage: 1,
      pageSize:7,
      AllPageSize:0,
      AllData: [],
      tableData: [],

      /*添加规格库存*/
      totalForm:{
        id:'',
        ware_id:'',
        ware_totalnumber:'',
      },
      rulesForm:{
        ware_totalnumber:[{ required: true, message: '请输入商品的数量', trigger: 'blur' }],
      },

    }
  },
  created() {
    this.getStandardsByBusman();
  },
  methods:{
    /*----------------------获得全部商品规格信息---------------------*/
    async getStandardsByBusman(){
      const res=await this.axios.post('Standard/getStandardByBusman',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))
      if (res.data.code === 200){
        this.AllData=res.data.object;
        this.AllPageSize=this.AllData.length
        this.PagingSystem()
      }else {
        this.$message.error("获取商家信息失败，你代码写错了吧")
      }
    },
    /*--------------------------删除选中的商品--------------------------*/
    deleteStandard(id){
      this.axios.post('Standard/deleteStandardByBusman',this.$qs.stringify({
        standard_id:id
      })).then((res)=>{
        if (res.data.code === 200){
          this.getStandardsByBusman();
          this.$message.success("删除成功")
        }else {
          this.$message.error("删除失败")
        }
      })
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
    /*-------------------------------------添加商品库存-----------------------------------*/
    addStandardNumber(id,ware_id) {
        this.dialogTotalVisible=true;
        this.totalForm.id=id;
        this.totalForm.ware_id=ware_id;
    },
    /*添加商品库存*/
    submitTotal(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('Standard/busmanAddStandardNumber',this.$qs.stringify({
            standard_id:this.totalForm.id,
            number:this.totalForm.ware_totalnumber,
            ware_id:this.totalForm.ware_id,
          })).then((res)=>{
            if (res.data.code === 200){
              this.$message.success("添加商品数量成功")
              this.getStandardsByBusman();
              this.dialogTotalVisible=false;
              this.totalForm.ware_totalnumber=1;
            }else {
              this.$message.error("添加商品数量失败")
              this.dialogTotalVisible=false;
            }
          })
        }
      });
    },
    /*重置输入人的商品库存*/
    restTotal(formName) {
      this.$refs[formName].resetFields();
    },


  }
}
</script>

<style scoped>
.busman-manager{
  margin: auto;
  width: 1406.670px;
  padding: 30px 20px 20px 20px;
  height: 620px;
  background-color: white;
}

.busman-page{
  margin-top: 26px;
  float: right;
}
</style>