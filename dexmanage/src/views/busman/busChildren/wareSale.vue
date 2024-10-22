<template>
  <div>
    <div class="table-data">
      <el-table
          :data="tableData.filter(data => !search || data.ware_name.toLowerCase().includes(search.toLowerCase()))"
          style="width: 100%">
        <!--此处写上商品的图片和名称-->
        <el-table-column label="商品" align="center" prop="">
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
        <!--单价-->
        <el-table-column label="单价" align="center" width="140">
          <template #default="scope">
            <div>￥{{scope.row.ware_price}}</div>
          </template>
        </el-table-column>
        <!--已售-->
        <el-table-column
            label="已售"
            align="center"
            width="130"
            show-overflow-tooltip>
          <template #default="scope">
            {{scope.row.ware_totalnumber - scope.row.ware_number}}
          </template>
        </el-table-column>
        <!--库存-->
        <el-table-column
            label="库存"
            align="center"
            width="130"
            show-overflow-tooltip>
          <template #default="scope">
            {{scope.row.ware_number}}
          </template>
        </el-table-column>
        <!--成交额-->
        <el-table-column
            label="总成交额"
            align="center"
            width="200"
            show-overflow-tooltip>
          <template #default="scope">
            <div style="color: red;font-size: 22px;font-weight: bold">￥{{scope.row.totalSale}}</div>
          </template>
        </el-table-column>
        <!--商品状态-->
        <el-table-column
            label="商品状态"
            align="center"
            width="160"
            show-overflow-tooltip>
          <template #default="scope">
            <div style="color: green;" v-if="scope.row.ware_state==='售卖中'">{{scope.row.ware_state}}</div>
            <div style="color: blue;" v-if="scope.row.ware_state==='审核中'">{{scope.row.ware_state}}(需添加规格)</div>
            <div style="color: red;" v-if="scope.row.ware_state==='已下架'">{{scope.row.ware_state}}</div>
            <div style="color: red;" v-if="scope.row.ware_state==='秒杀中'">{{scope.row.ware_state}}</div>
          </template>
        </el-table-column>
        <el-table-column
            align="center"
            fixed="right"
            width="310">
          <template slot="header" slot-scope="scope">
            <el-input
                v-model="search"
                size="mini"
                placeholder="输入关键字搜索"/>
          </template>
          <template slot-scope="scope">
              <el-button type="primary" size="mini"
                         @click="AddStandardsWare(scope.$index, scope.row.id)">添加规格</el-button>
              <el-button v-if="scope.row.ware_state==='售卖中'"
                         type="primary" size="mini"
                         @click="editMessage(scope.$index, scope.row.id)">修改信息</el-button>
              <el-button v-if="scope.row.ware_state==='已下架' && scope.row.ware_number > 0"
                         type="primary" size="mini"
                         @click="ground(scope.row.id)">点击上架</el-button>
              <el-button v-if="scope.row.ware_state==='售卖中'"
                         type="danger" size="mini" @click="underCarriage(scope.row.id)">下架商品</el-button>
              <el-button v-if="scope.row.ware_state==='已下架'"
                         type="danger" size="mini" @click="deleteWare(scope.row.id)">删除商品</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="order-page">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 20, 40]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="AllPageSize">
        </el-pagination>
      </div>
    </div>

    <!--修改信息-->
    <el-dialog title="商品信息修改" v-model="dialogTableVisible" :visible.sync="dialogTableVisible">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
        <el-form-item label="商品名称" prop="ware_name">
          <el-input v-model="ruleForm.ware_name"></el-input>
        </el-form-item>
        <el-form-item label="商品副标题" prop="ware_describe">
          <el-input v-model="ruleForm.ware_describe"></el-input>
        </el-form-item>
<!--        <el-form-item label="商品定价(￥)" prop="ware_price">
          <el-input-number v-model="ruleForm.ware_price" :precision="2" :step="0.1"></el-input-number>
        </el-form-item>-->
        <el-form-item label="商品描述" prop="ware_connect">
          <el-input type="textarea" style="height: 180px;" v-model="ruleForm.ware_connect"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--  新增规格  -->
    <el-dialog title="新增商品规格" :visible.sync="dialogStandardsFormVisible">
      <el-form :model="standardForm" :rules="rulesStandard" ref="ruleForm">
        <el-form-item label="商品规格" :label-width="'100px'" prop="standards_name">
          <el-input v-model="standardForm.standards_name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品数量" :label-width="'100px'" prop="standards_total">
          <el-input-number v-model="standardForm.standards_total" :min="1" label="商品数量"></el-input-number>
        </el-form-item>
        <el-form-item label="商品价格" :label-width="'100px'" prop="standards_price">
          <el-input-number v-model="standardForm.standards_price" :precision="2" :step="0.1" :min="1"></el-input-number>
        </el-form-item>
        <el-form-item style="margin-left: 100px">
          <el-button type="primary" @click="submitStandardForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetStandardForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
export default {
name: "wareSale",
  data(){
    return {
      tableData:'',
      search:'',
      dialogTableVisible:false,
      dialogStandardsFormVisible:false,
      ruleForm: {
        id:'',
        ware_name: '',
        ware_describe: '',
        ware_connect: '',
        /*ware_price: '',*/
      },
      /*规格信息*/
      standardForm:{
        ware_id:'',
        busman_id:'',
        standards_name:'',
        standards_total:1,
        standards_price:1,
      },
      rules: {
        ware_name: [
          { required: true, message: '请输入商品名称', trigger: 'blur' },
          { min: 3, max: 20, message: '商品名称长度在 3 到 10 个字符之间', trigger: 'blur' }
        ],
        ware_describe:[
          { required: true, message: '请输入商品副标题', trigger: 'blur' },
          { min: 3, max: 50, message: '商品副标题长度在 3 到 20 个字符之间', trigger: 'blur' }
        ],
/*        ware_price: [
          { required: true, message: '请输入商品的价格', trigger: 'blur' },
        ],*/
        ware_connect:[
          { required: true, message: '请输入商品的内容信息', trigger: 'blur' },
          { min: 10, message: '商品内容信息必须有10个字以上', trigger: 'blur' }
        ],
      },
      rulesStandard:{
        standards_name:[
          { required: true, message: '请输入商品规格名称', trigger: 'blur' },
          { min: 2, message: '商品规格名称必须有2个字以上', trigger: 'blur' }
        ],
      },
      /*翻页*/
      AllData: [],
      currentPage: 1,
      pageSize:5,
      AllPageSize:0,
    }
  },
  /*创建指出访问所有的商品信息*/
  async created() {
    console.log(this.$store.state.busmanInfo.id);
    const res=await this.axios.post('wares/getWareByBusman',this.$qs.stringify({
      busman_id:this.$store.state.busmanInfo.id
    }))

    if (res.data.code === 200){
      //初始化tableDate
      this.tableData = '';
      this.AllData = res.data.object;
      this.AllPageSize=res.data.size;
      await this.PagingSystem(this.pageSize,this.currentPage);
    }else {
      this.$message.error("你当前还未有商品哦")
    }
  },
  methods:{
    handleDelete(index, row) {
      console.log(index, row);
    },
    /*----------------------------添加商品规格-------------------------*/
    /*添加商品规格*/
    AddStandardsWare(index,id){
      this.standardForm.ware_id=id;
      this.dialogStandardsFormVisible=true;
    },
    /*提交商品规格信息*/
    submitStandardForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.standardForm.busman_id=this.$store.state.busmanInfo.id
          this.axios.post('Standard/AddStandards',this.$qs.stringify(
              this.standardForm
          )).then((res)=>{
            if (res.data.code === 200){
              this.$message.success("商品规格创建成功")
              this.dialogStandardsFormVisible=false
              this.updateWare()
            }
          })

        } else {
          this.$message.error("表单信息提交失败")
          return false;
        }
      });
    },
    resetStandardForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogStandardsFormVisible=false;
    },


    /*------------------------------修改商品信息-----------------------------*/
    /*修改商品信息*/
    editMessage(index,row){
      this.ruleForm.id=this.tableData[index].id;
      this.ruleForm.ware_name=this.tableData[index].ware_name;
      this.ruleForm.ware_describe=this.tableData[index].ware_describe;
      this.ruleForm.ware_connect=this.tableData[index].ware_connect;
      /*this.ruleForm.ware_price=this.tableData[index].ware_price;*/
      this.dialogTableVisible=true;
    },
    /*添加商品库存*/
    addTotal(index,row){
      this.totalForm.id=this.tableData[index].id;
      this.dialogTotalVisible=true;
    },
    /*提交修改信息*/
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('wares/busmanUpdateWareMessage',this.$qs.stringify(
                this.ruleForm
            )).then((res)=>{
              if (res.data.code === 200){
                this.$message.success("修改商品信息成功")
                this.updateWare();
                this.dialogTableVisible=false;
              }else {
                this.$message.error("新增商品失败")
                this.dialogTableVisible=false;

              }
            })
          }
      });
    },
    /*重置表单信息*/
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    /*-------------------------------------------------------------------------*/
    /*下架商品*/
    async underCarriage(id){
      const res=await this.axios.post('wares/busmanUnderCarriageWare',this.$qs.stringify({
        id:id
      }))
      if (res.data.code === 200){
        this.$message.success("成功下架该商品")
        await this.updateWare();
      }else {
        this.$message.error("下架该商品失败")
      }
    },
    /*上架商品信息*/
    async ground(id){

      const res=await this.axios.post('wares/busmanGroundWare',this.$qs.stringify({
        id:id
      }));
      if (res.data.code === 200){
        this.$message.success("成功上架该商品");
        await this.updateWare();
      }else {
        this.$message.error("上架该商品失败")
      }
    },
    /*-------------------------------------------------------------------------*/
    /*更新商品信息*/
    async updateWare(){
      const res=await this.axios.post('wares/getWareByBusman',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))

      if (res.data.code === 200){
        //初始化tableDate
        this.tableData = '';
        this.AllData = res.data.object;
        this.AllPageSize=res.data.size;
        await this.PagingSystem(this.pageSize,this.currentPage);
      }else {
        this.$message.error("你当前还未有商品哦")
      }
    },
    /*商家删除商品*/
    async deleteWare(wareId){
      const res = await this.axios.post('wares/deleteWare',this.$qs.stringify({
        busmanId:this.$store.state.busmanInfo.id,
        wareId:wareId
      }))

      if (res.data.code === 200){
        await this.updateWare();
        this.$message.success("删除成功")
      }else {
        this.$message.error("删除失败")
      }
    },



    /*操作成功*/
    successBuy() {
      this.$notify({
        title: '操作成功',
        message: '订单操作成功',
        type: 'success'
      });
    },
    /*失败提示*/
    filedMsg() {
      this.$notify.error({
        title: '操作失败',
        message: '订单操作失败',
      });
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
      console.log("进入分页");
      if (this.currentPage === 1){
        this.tableData=this.AllData.slice(currentPage-1,currentPage*pageSize)
      }else {
        console.log("进入二级分页");
        this.tableData=this.AllData.slice((currentPage-1)*pageSize,currentPage*pageSize)
      }

    },
  },
}
</script>

<style scoped>
.order-page{
  margin-top: 26px;
  margin-bottom: 10px;
  float: right;
}
</style>