<template>
  <div class="ware-mode">
    <el-row>
      <el-col>
        <el-card :body-style="{ padding: '0px' }" shadow="hover">
          <img :src="ware.ware_img1" class="image">
          <div class="font-demo">
            <h4 class="PName">{{ ware.ware_name }}</h4>
          </div>
          <div class="active-attend">
            <div>参与的活动 : <el-tag type="success" >{{ ware.ware_active }}</el-tag></div>
          </div>
          <div class="font-demo" style="margin-bottom: 15px;margin-top: 5px">
            <el-button size="mini" type="primary" icon="el-icon-edit" @click="editMessage(ware)">修改信息</el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete" @click="outActive(ware)">退出活动</el-button>
          </div>

        </el-card>
      </el-col>
    </el-row>

    <el-dialog title="参与满减" v-model="dialogFullVisible" :visible.sync="dialogFullVisible">
      <div style="margin-left: 20px;margin-bottom: 20px">
        <div>商品名称  :  {{this.changeWare.ware_name}}</div>
      </div>
      <el-divider></el-divider>
      <el-form :model="fullWare" :rules="fullRule" ref="fullForm" label-width="180px" class="demo-ruleForm">
        <el-form-item label="商品满足的价格" prop="ware_activeFullPrice">
          <el-input-number v-model="fullWare.ware_activeFullPrice" :precision="2" :step="0.1" :min="this.changeWare.ware_price"></el-input-number>
        </el-form-item>
        <el-form-item label="商品减价的价格" prop="ware_activeFullReduction">
          <el-input-number v-model="fullWare.ware_activeFullReduction" :precision="2" :step="0.1" :max="this.changeWare.ware_price-1"></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitFullForm('fullForm')">立即参加</el-button>
          <el-button @click="resetFullForm('fullForm')">重置</el-button>
        </el-form-item>
      </el-form>
      <div style="display: flex;justify-content: left;">
        <div>*注:商品价格不能低于{{this.fullWare.ware_price}}</div>
      </div>
    </el-dialog>

    <el-dialog title="修改折扣信息" v-model="dialogDiscountVisible" :visible.sync="dialogDiscountVisible">
      <div style="margin-left: 20px;margin-bottom: 20px">
        <div>商品名称  :  {{this.changeWare.ware_name}}</div>
      </div>
      <el-divider></el-divider>
      <el-form :model="discountWare" :rules="discountRule" ref="discountForm" label-width="180px" class="demo-ruleForm">
        <el-form-item label="商品折扣力度" prop="ware_activeDiscount">
          <el-input-number v-model="discountWare.ware_activeDiscount" :precision="1" :step="0.1" :min="0.1" :max="9.9" label="描述文字"></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitDiscountForm('discountForm')">立即参加</el-button>
          <el-button @click="resetDiscountForm('discountForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "activeWare",
  props:['ware'],
  data(){
    return{
      discountWare:'',
      fullWare:'',
      dialogFullVisible:false,
      dialogDiscountVisible:false,

      /*选中的商品*/
      changeWare:'',
      discountRule:{
        ware_activeDiscount: [
          { required: true, message: '请输入商品的折扣', trigger: 'blur' },
        ],
      },
      fullRule: {
        ware_activeFullPrice: [
          { required: true, message: '请输入商品的价格', trigger: 'blur' },
        ],
        ware_activeFullReduction:[
          { required: true, message: '请输入商品的价格', trigger: 'blur' },
          /* { min:this.fullWare.ware_price,message: '必须达到商品的价格后才能进行满减',trigger: 'blur'}*/
        ]
      },
    }
  },
  methods:{
    /*--------------------------------------------------------------*/
    /*退出活动*/
    async outActive(ware){
      let busmanId=this.$store.state.busmanInfo.id;
      const res=await this.axios.post('active/outActive',this.$qs.stringify({
        busman_id:busmanId,
        ware_id:ware.id,
      }))
      if (res.data.code === 200){
        this.$message.success("退出活动成功")
        await this.$parent.getActiveDidWare();
      }else {
        this.$message.error("退出活动失败")
      }
    },

    /*--------------------------修改活动信息,先获取该商品的活动信息----------------------------*/
    async editMessage(ware){
      this.changeWare=ware;
      /*异步请求*/
      const res=await this.axios.post('active/getWareActiveMessage',this.$qs.stringify({
        ware_id:ware.id,
        busman_id:this.$store.state.busmanInfo.id
      }))
      if (res.data.code === 200){

        /*更具商品参与活动类型返回相应的值*/
        if (ware.ware_active === '打折'){
          this.dialogDiscountVisible=true;
          this.discountWare=res.data.object;
        }else {
          this.dialogFullVisible=true;
          this.fullWare=res.data.object;
        }
      }else {
        this.$message.error("获取活动信息失败")
      }

    },

    /*---------------------------修改折扣表单-----------------------------*/
    /*提交修改信息*/
    submitDiscountForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.discountWare)
          this.axios.post('active/busmanUpdateDiscount',this.$qs.stringify(
              this.discountWare
          )).then((res)=>{
            if (res.data.code === 200){
              this.$message.success("修改商品打折信息成功")
              this.dialogDiscountVisible=false;
            }else {
              this.$message.error("修改商品打折信息失败")
            }
          })
        }
      });
    },
    /*重置表单信息*/
    resetDiscountForm(formName) {
      this.$refs[formName].resetFields();
    },
    /*--------------------------修改商品满减信息----------------------------*/
    /*---------------------------满减表单-----------------------------*/
    /*提交修改信息*/
    submitFullForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('active/busmanUpdateFull',this.$qs.stringify(
              this.fullWare
          )).then((res)=>{
            if (res.data.code === 200){
              this.$message.success("修改满减信息成功");
              this.dialogFullVisible=false;
            }else {
              this.$message.error("新增商品打折信息失败")
            }
          })
        }
      });
    },
    /*重置表单信息*/
    resetFullForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
}
</script>

<style scoped>
.ware-mode{
  width: 260px;
  height: 340px;
  display: inline-block;
  margin-right: 10px;
  margin-left: 10px;
}
img{
  width: 150px;
  height: 150px;
  margin-left: 45px;
  margin-top: 20px;
}


.font-demo{
  display: flex;
  justify-content: center;
}

h4{
  margin-top: 20px;
  margin-bottom: 10px;
}
.active-attend{
  padding: 8px 6px 10px 8px;
}


.PPrice{
  color: red;
  font-weight: bold;
}
</style>