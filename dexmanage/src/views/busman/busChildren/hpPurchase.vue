<template>
  <div>

    <!--轮播图商位购买-->
    <el-divider>轮播图商位</el-divider>
    <div>
      <el-carousel :interval="4000" type="card" height="400px">
        <el-carousel-item v-for="(o,i) in ware_carousel" >
          <el-tooltip class="item" v-if="o.id === 0" effect="dark" content="轮播图商位高效引流，赶快购买吧" placement="top">
          <h3 class="medium"  @click="buyCarousel"><el-empty description="轮播图商位高效引流，期待你的购买"></el-empty></h3>
          </el-tooltip>
          <img :src="o.img" style="height: 400px" v-else>
        </el-carousel-item>
      </el-carousel>
    </div>
    <!--介绍商位购买-->
    <el-divider>商品推荐商位-(按购买日期排序)</el-divider>
    <div>
      <hp-ware v-for="(o,i) in ware_recommend" :ware="o"></hp-ware>
    </div>

    <el-dialog width="90%" title="选择购买轮播图商位的商品" v-model="dialogChangeWareVisible" :visible.sync="dialogChangeWareVisible">
      <change-ware @change-ware="changeWare" v-for="(o,i) in busmanWare" :ware="o"></change-ware>
    </el-dialog>


  <!-- 购买轮播图商位的弹窗 -->
    <el-dialog title="购买轮播图商位" v-model="dialogCarouselVisible" :visible.sync="dialogCarouselVisible">
      <div style="margin-left: 20px;margin-bottom: 20px">
        <div>商品名称  :  {{this.busmanChangeWare.ware_name}}</div>
        <div style="margin-top: 10px;">单价：{{this.QuotientPrice.carousel_price}} 元/月</div>
      </div>
      <el-divider></el-divider>
      <el-form :model="CarouseForm" :rules="CarouseRule" ref="CarouseForm" label-width="180px" class="demo-ruleForm">
        <el-form-item label="购买商位月份数量" prop="month">
          <el-input-number v-model="CarouseForm.month" :min="1" :max="100"></el-input-number>
        </el-form-item>
        <el-form-item label="上传轮播图片">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8682/dex/pictureVerification/uploadUserHeaderImage"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar" style="width: 300px;height: 300px">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-divider></el-divider>
        <div style="margin-bottom: 10px;">
          <div>应支付：<span style="color: red;">{{this.QuotientPrice.carousel_price*CarouseForm.month}}</span> 元</div>
          <div>*注:购买成功后时间从今日开始计算</div>
        </div>
        <el-form-item>
          <el-button type="primary" @click="submitCarouseForm('CarouseForm')">立即支付</el-button>
          <el-button @click="resetCarouseForm('CarouseForm')">重置</el-button>
        </el-form-item>
      </el-form>
      <div>

      </div>
    </el-dialog>

  <!--购买推荐商品的弹窗-->
    <el-dialog title="购买推荐商品商位" v-model="dialogRecommendVisible" :visible.sync="dialogRecommendVisible">
      <div style="margin-left: 20px;margin-bottom: 20px">
        <div>商品名称  :  {{this.busmanChangeWare.ware_name}}</div>
        <div style="margin-top: 10px;">单价：{{this.QuotientPrice.recommend_price}} 元/月</div>
      </div>
      <el-divider></el-divider>
      <el-form :model="CarouseForm" :rules="CarouseRule" ref="CarouseForm" label-width="180px" class="demo-ruleForm">
        <el-form-item label="购买商位月份数量" prop="month">
          <el-input-number v-model="CarouseForm.month" :min="1" :max="100"></el-input-number>
        </el-form-item>
        <el-divider></el-divider>
        <div style="margin-bottom: 10px;">
          <div>应支付：<span style="color: red;">{{this.QuotientPrice.recommend_price*CarouseForm.month}}</span> 元</div>
          <div>*注:购买成功后时间从今日开始计算</div>
        </div>
        <el-form-item>
          <el-button type="primary" @click="submitRecommendForm('CarouseForm')">立即支付</el-button>
          <el-button @click="resetRecommendForm('CarouseForm')">重置</el-button>
        </el-form-item>
      </el-form>
      <div>

      </div>
    </el-dialog>

  </div>
</template>

<script>
import hpWare from "../../../components/busman/hpWare";
import changeWare from "../../../components/busman/changeWare";
export default {
  name: "hpPurchase",
  components:{
    'hpWare':hpWare,
    'changeWare':changeWare,
  },
  async created() {
    await this.getOnlineQuotient();
    await this.getQuotientPrice();
    await this.getBusmanWare();
  },
  data(){
    return{
      ware_carousel:'',
      ware_recommend:'',
      QuotientPrice:'',
      dialogCarouselVisible:false,
      dialogRecommendVisible:false,
      dialogChangeWareVisible:false,
      busmanWare:'',
      /*用户选择的商品*/
      busmanChangeWare:'',
      /*记录用户选择的是轮播图还是商位*/
      busmanCatch:'',
      /*图片*/
      imageUrl: '',
      CarouseForm:{
        month:'',
        busman_id:'',
        ware_id:'',
      },
      CarouseRule: {
        month: [
          { required: true, message: '请输入月份', trigger: 'blur' },
        ],
      },
    }
  },
  methods:{
    /*页面刷新前调用三个方法*/
    async getOnlineQuotient(){
      const res=await this.axios.post('quotient/getOnlineQuotient')
      if (res.data.code === 200){
        this.ware_carousel=res.data.carousel;
        this.ware_recommend=res.data.hp_recommend;
      }
    },
    async getQuotientPrice(){
      const res=await this.axios.post('quotient/getQuotientPrice')
      if (res.data.code === 200){
        this.QuotientPrice=res.data.object
      }else {
        this.$message.error("获取尚未价格失败")
      }
    },
    async getBusmanWare(){
      const res=await this.axios.post('wares/busmanAlreadyGroundWare',this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))
      console.log(res);
      if (res.data.code === 200){
        this.busmanWare=res.data.object
      }else {
        this.$message.error("获取上架商品失败")
      }
    },
    /*----------------------------------------*/
    buyCarousel(){
      this.CarouseForm.month=1;
      this.imageUrl="";
      this.dialogChangeWareVisible=true;
      this.busmanCatch='轮播图'
    },
    buyRecommend(){
      this.CarouseForm.month=1;
      this.dialogChangeWareVisible=true;
      this.busmanCatch='推荐商位'
    },
    /*----------------------------------------*/
    /*商家选择好商品后数据回显*/
    changeWare(data){
      this.busmanChangeWare=data;
      this.dialogChangeWareVisible=false;
      if (this.busmanCatch === '轮播图'){
        this.dialogCarouselVisible=true;
      }else {
        this.dialogRecommendVisible=true;
      }
    },

    /*---------------------------购买轮播图信息-----------------------------*/
    /*购买轮播图*/
    submitCarouseForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //先跳转去创建订单
          this.createOrder();
        }
      });
    },
    /*重置购买轮播图信息*/
    resetCarouseForm(formName) {
      this.$refs[formName].resetFields();
    },
    /*支付订单后调用订单*/
    async addNewCarousel(order_id){
      let carousel={};
      carousel.img=this.imageUrl;
      carousel.ware_id=this.busmanChangeWare.id;
      carousel.busman_id=this.$store.state.busmanInfo.id;
      carousel.id=this.CarouseForm.month;
      carousel.quotient_order_id=order_id;
      const res=await this.axios.post('quotient/insertCarouselByBusman',this.$qs.stringify(
          carousel
      ))
      if (res.data.code === 200){
        this.$message.success("购买成功")
        this.dialogCarouselVisible=false;
        await this.getOnlineQuotient()
      }else {
        this.$message.error("购买失败")
      }
    },
    /*--------------------------生成购买订单------------------------*/
    async createOrder(){
      let qo={};
      qo.busman_id=this.$store.state.busmanInfo.id;
      qo.pay_price=this.CarouseForm.month*this.QuotientPrice.carousel_price;
      qo.month=this.CarouseForm.month;
      qo.order_type=this.busmanCatch;
      qo.ware_id=this.busmanChangeWare.id;
      /*qo.order_time='123';
      qo.id=0;*/
      const res=await this.axios.post('quotient/createOrderByBusman',this.$qs.stringify(
          qo
      ))
      if (res.data.code === 200){
        if (this.busmanCatch === '轮播图'){
          await this.addNewCarousel(res.data.size)
        }else {
          await this.addNewRecommend(res.data.size)
        }
      }else {
        this.$message.error("支付失败")
      }
    },
    /*---------------------------购买轮播图信息-----------------------------*/
    /*购买轮播图*/
    submitRecommendForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //先跳转去创建订单
          this.createOrder();
        }
      });
    },
    /*重置购买轮播图信息*/
    resetRecommendForm(formName) {
      this.$refs[formName].resetFields();
    },
    /*支付订单后调用订单*/
    async addNewRecommend(order_id){
      let Recommend={};
      Recommend.id=this.CarouseForm.month;
      Recommend.ware_id=this.busmanChangeWare.id;
      Recommend.busman_id=this.$store.state.busmanInfo.id;
      Recommend.quotient_order_id=order_id;
      const res=await this.axios.post('quotient/insertRecommendByBusman',this.$qs.stringify(
          Recommend
      ))
      if (res.data.code === 200){
        this.$message.success("推荐商位购买成功")
        this.dialogRecommendVisible=false;
        await this.getOnlineQuotient()
      }else {
        this.$message.error("购买失败")
      }
    },

    /*------------------图片----------------------*/
    /*上传商品图片*/
    /*用户对上传的图片的增删改查的操作*/
    handleAvatarSuccess(res, file) {
      this.imageUrl = res.url;
    },
  }
}
</script>

<style scoped>

</style>