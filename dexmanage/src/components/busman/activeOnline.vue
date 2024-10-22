<template>
    <div class="ware-mode">
      <el-row>
        <el-col>
          <el-card :body-style="{ padding: '0px' }" shadow="hover">
            <img :src="ware.ware_img1" class="image">
            <div class="font-demo">
              <h4 class="PName">{{ ware.ware_name }}</h4>
            </div>
            <div class="font-demo">
              <p class="PContent">{{ ware.ware_describe }}</p>
            </div>
            <div class="font-demo">
              <p class="PPrice">￥{{Number(ware.ware_price).toFixed(2)}}</p>
            </div>
            <div class="font-demo" style="margin-bottom: 10px">
              <el-button size="mini" type="primary" icon="el-icon-receiving" @click="full(ware)">参与满减</el-button>
              <el-button size="mini" type="primary" icon="el-icon-sell" @click="discount(ware)">参与打折</el-button>
            </div>

          </el-card>
        </el-col>
      </el-row>

      <el-dialog title="参与满减" v-model="dialogFullVisible" :visible.sync="dialogFullVisible">
        <div style="margin-left: 20px;margin-bottom: 20px">
          <div>商品名称  :  {{this.fullWare.ware_name}}</div>
        </div>
        <el-divider></el-divider>
        <el-form :model="fullForm" :rules="fullRule" ref="fullForm" label-width="180px" class="demo-ruleForm">
          <el-form-item label="商品满足的价格" prop="full_price">
            <el-input-number v-model="fullForm.full_price" :precision="2" :step="0.1" :min="this.fullWare.ware_price"></el-input-number>
          </el-form-item>
          <el-form-item label="商品减价的价格" prop="full_reduction">
            <el-input-number v-model="fullForm.full_reduction" :precision="2" :step="0.1" :max="this.fullWare.ware_price-1"></el-input-number>
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

      <el-dialog title="参与折扣" v-model="dialogDiscountVisible" :visible.sync="dialogDiscountVisible">
        <div style="margin-left: 20px;margin-bottom: 20px">
          <div>商品名称  :  {{this.discountWare.ware_name}}</div>
        </div>
        <el-divider></el-divider>
        <el-form :model="discountForm" :rules="discountRule" ref="discountForm" label-width="180px" class="demo-ruleForm">
          <el-form-item label="商品满足的价格" prop="discount_detail">
            <el-input-number v-model="discountForm.discount_detail" :precision="1" :step="0.1" :min="0.1" :max="9.9" label="描述文字"></el-input-number>
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
  name: "activeOnline",
  props:['ware'],
  data(){
    return{
      dialogFullVisible:false,
      dialogDiscountVisible:false,
      fullWare:'',
      discountWare:'',
      fullForm:{
        ware_id:'',
        busman_id:'',
        full_reduction:'',
        full_price:''
      },
      discountForm:{
        ware_id:'',
        busman_id:'',
        discount_detail:'',
      },
      fullRule: {
        full_price: [
          { required: true, message: '请输入商品的价格', trigger: 'blur' },
        ],
        full_reduction:[
          { required: true, message: '请输入商品的价格', trigger: 'blur' },
         /* { min:this.fullWare.ware_price,message: '必须达到商品的价格后才能进行满减',trigger: 'blur'}*/
        ]
      },
      discountRule:{
        discount_detail: [
          { required: true, message: '请输入商品的折扣', trigger: 'blur' },
        ],
      }
    }
  },
  methods:{
    /*参与满减*/
    full(ware){
      this.dialogFullVisible=true;
      this.fullWare=ware;
      this.fullForm.full_price=ware.ware_price;
      this.fullForm.ware_id=ware.id;
      this.fullForm.busman_id=this.$store.state.busmanInfo.id;
    },
    /*参与打折*/
    discount(ware){
      this.dialogDiscountVisible=true;
      this.discountWare=ware;
      this.discountForm.busman_id=this.$store.state.busmanInfo.id;
      this.discountForm.ware_id=ware.id;
    },

    /*---------------------------满减表单-----------------------------*/
    /*提交修改信息*/
    submitFullForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('active/busmanAddFullReduction',this.$qs.stringify(
              this.fullForm
          )).then((res)=>{
            if (res.data.code === 200){
              this.$message.success("商品参与满减活动成功")
              this.$parent.getNoActiveWare();
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
    /*---------------------------折扣表单-----------------------------*/
    /*提交修改信息*/
    submitDiscountForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('active/busmanAddDiscount',this.$qs.stringify(
              this.discountForm
          )).then((res)=>{
            if (res.data.code === 200){
              this.$message.success("添加商品打折成功")
              this.$parent.getNoActiveWare();
              this.dialogDiscountVisible=false;
            }else {
              this.$message.error("添加商品打折失败")
            }
          })
        }
      });
    },
    /*重置表单信息*/
    resetDiscountForm(formName) {
      this.$refs[formName].resetFields();
    },
    /*---------------------------修改商品活动属性-----------------------*/
    /*修改商品属性*/
/*    async ModifyWareActive(ware_active,ware_id){
      const res=await this.axios.post('active/ModifyWareActive',this.$qs.stringify({
        ware_active:ware_active,
        ware_id:ware_id
      }))
      if (res.data.code === 200){
        this.$message.success("商品参加活动成功");
        this.$parent.getNoActiveWare();
        this.dialogDiscountVisible=false;
        this.dialogFullVisible=false;
      }else {
        this.$message.error("商品参加活动失败")
      }
    }*/
  }
}
</script>

<style scoped>
.ware-mode{
  width: 260px;
  height: 380px;
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

.PContent{
  text-overflow: -o-ellipsis-lastline;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  line-clamp: 1;
  -webkit-box-orient: vertical;
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