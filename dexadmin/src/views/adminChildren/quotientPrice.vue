<template>
  <div>
    <div class="mode-price">
      <div class="price-title"><div class="title-message">商位信息价格明细</div></div>

      <!--  价格表单  -->
      <div class="mode-from">
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="200px" class="demo-ruleForm">
          <el-form-item label="轮播图价格" prop="carousel_price">
            <el-input-number v-model="ruleForm.carousel_price" :precision="2" :step="0.1"></el-input-number>
          </el-form-item>
          <el-form-item label="商品推荐商位价格" prop="recommend_price">
            <el-input-number v-model="ruleForm.recommend_price" :precision="2" :step="0.1"></el-input-number>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <admin-foot style="position:fixed;bottom: 0px;"></admin-foot>
  </div>
</template>

<script>
import adminFoot from "../../components/adminFoot";
export default {
  name: "quotientPrice",
  components:{
    'adminFoot':adminFoot,
  },
  data() {
    return {
      ruleForm: {
        carousel_price: '' ,
        recommend_price: '',
      },
      rules: {
        carousel_price: [
          { required: true, message: '请输入轮播图商位价格', trigger: 'blur' },
        ],
        recommend_price: [
          { required: true, message: '请输入推荐商位的价格', trigger: 'blur' },
        ],
      }
    };
  },
  created() {
    this.adminGetAllQuitentPrice();
  },
  methods: {
    /*获取全部价格的信息*/
    async adminGetAllQuitentPrice(){
      const res=await this.axios.post('DexAdmin/adminGetAllQuitentPrice')
      if (res.data.code === 200){
        this.ruleForm.carousel_price=res.data.object.carousel_price;
        this.ruleForm.recommend_price=res.data.object.recommend_price;
      }
    },
    /*修改价格信息*/
    async adminModifyQuitentPrice(){
      const res=await this.axios.post('DexAdmin/adminModifyQuitentPrice',this.$qs.stringify(
          this.ruleForm
      ))
      if (res.data.code === 200){
        this.adminLogEdit(this.ruleForm.carousel_price,this.ruleForm.recommend_price)
        this.$message.success("修改商位价格成功")
        location.reload()
      }else {
        this.$message.error("修改商位价格失败")
      }
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.adminModifyQuitentPrice();
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    /*---------------日志行为---------------*/
    adminLogEdit(cal,rec){
      this.axios.get(`DexAdmin/adminLog?behavior=${'管理员修改商位的价格信息，商位轮播图价格'+cal+'商位推荐价格'+rec}`)
    }
  }
}
</script>

<style scoped>
  .mode-price{
    margin: 80px auto;
    width: 1000px;
    height: 500px;
    background-color: white;
    border-radius: 16px;
  }

  .price-title{
    display: flex;
    justify-content: center;
    align-items: center;

  }

  .title-message{
    font-size: 40px;
    margin-top: 60px;
  }

  .mode-from{
    width: 700px;
    height: 500px;
    margin: 40px auto;
  }
</style>