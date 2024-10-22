<template>
  <div>
    <!--  秒杀活动过后显示为空页面  -->
    <div style="width: 100%;height: 800px;background-color: white" v-if="this.bonus_empty">
      <el-empty :image-size="400" description="当前抢购已结束，下次早点来吧">
        <el-button type="primary" @click="GoHome">返回主页面</el-button>
      </el-empty>
    </div>
    <!--  强杀页面  -->
    <div v-else>
      <shop-cut-down v-if="this.bonus !== ''" :end-time="this.bonus.enddate" :ware_id="this.ware.id" :bonus_id="this.bonus.id" ></shop-cut-down>
      <div class="wareDemo">
        <!--左边的图片展示-->
        <div class="ware-img">
          <div class="big-img">
            <img v-lazy="ShowBigImage">
          </div>
          <!--大图片下面的小图片一排列表-->
          <div class="list-img">
            <div v-for="(o,i) in wareImageList" class="alone-img" v-if="o!=null">
              <img :src="o" alt="" @click="ChangeImage(o)" :class="{shadow:o===ShowBigImage}">
            </div>
          </div>
        </div>
        <!--右边的信息展示-->
        <div  class="ware-message">
          <!--商品标题-->
          <div class="ware-title">{{ware.ware_name}}</div>
          <!--价格以及活动-->
          <div class="ware-Import">
            <div class="import-one" v-if="ware.ware_active === '打折'"><div class="ware-price-it">原价</div><div class="ware-price">￥<span style="text-decoration:line-through ">{{ware.ware_price}}</span></div></div>
            <div class="import-one" v-if="ware.ware_active === '打折'"><div class="ware-price-it">折后价</div><div class="ware-price">￥{{ (ware.ware_price * ware.ware_activeDiscount * 0.1).toFixed(2)}}</div></div>
            <div class="import-one" v-else><div class="ware-price-it">价 格</div><div class="ware-price">￥{{ware.ware_price}}</div></div>
            <div class="import-two">
              <div class="ware-activity">活 动</div>
              <div class="ware-activity-message">
                <div>商品活动:{{ ware.ware_active }}</div>
                <div v-if="ware.ware_active === '满减'">满减详情: 购买{{ware.ware_name}},满<span class="span-red">{{ ware.ware_activeFullPrice }}</span>(元)减<span class="span-red">{{ware.ware_activeFullReduction}}</span>(元)</div>
                <div v-else-if="ware.ware_active === '打折'">商品折扣详情: 购买{{ware.ware_name}},可以享受<span class="span-red">{{ware.ware_activeDiscount}}</span>折</div>
              </div>
            </div>
          </div>
          <!--服务人员和商品编码-->
          <div class="ware-mode"><div class="mode-one">服务说明</div><div class="mode-two">由DEX商城商家负责发货，并提供售后咨询服务</div></div>
          <div class="ware-mode"><div class="mode-one">商品编码</div><div class="mode-two">{{ ware.id }}</div></div>
          <div class="ware-mode"><div class="mode-one">商品数量</div><div class="mode-two">{{ ware.ware_number }}</div></div>
          <el-divider></el-divider>
          <div class="ware-mode"><div class="mode-one">发货地址</div><div class="mode-two">{{ busmanAddress }}</div></div>
          <el-divider></el-divider>
          <div>选取规格 :</div>
          <div style="display: flex;justify-content: left;">
            <div v-for="(o,i) in standard" class="ware-standrads">
              <!--如果改规格已经销售完，那么不展示该规格-->
              <div v-if="o.standards_number > 0" type="success" plain @click="changeWareMsg(i)" :class="[i===changedStandards?'change-button':'button-div']"><div>{{o.standards_name}}</div></div>
            </div>
          </div>
          <el-divider></el-divider>
          <!--添加商品数量，加入购物车，点击购买-->
          <div class="ware-mode">
            <div class="mode-one"><el-input-number v-model="num" @change="handleChange" :min="1" :max="ware.ware_number" label="描述文字"></el-input-number></div>
            <div class="buy-button"><el-button @click="purchase" type="danger">立即抢购</el-button></div>
          </div>
        </div>
<!--        <el-skeleton :rows="6" animated :throttle="500" v-else></el-skeleton>-->
      </div>
      <!--商品信息简介-->
      <shop-detail-introduce :ware-details="ware.ware_connect"></shop-detail-introduce>
      <!--收货地址    -->
      <div>
        <el-dialog title="收货地址" :visible.sync="dialogAddressVisible">
          <!--表单信息-->
          <el-form :model="TouristAddress" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="收货人姓名" prop="name">
              <el-input v-model="TouristAddress.name" placeholder="填写收货人姓名" style="width: 300px;"></el-input>
            </el-form-item>
            <!--手机号码-->
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="TouristAddress.phone" placeholder="填写收货人联系电话" style="width: 300px;"></el-input>
            </el-form-item>
            <!--邮政编码-->
            <el-form-item label="邮政编码" prop="postal">
              <el-input v-model="TouristAddress.postal" placeholder="填写收货人所在地的邮政编码" style="width: 200px;"></el-input>
            </el-form-item>
            <!--地址信息-->
            <el-form-item label="地址信息" prop="local">
              <el-input v-model="TouristAddress.local" style="display: none"></el-input>
              <area-select @Area="AreaFunc"></area-select>
            </el-form-item>
            <!--详细地址-->
            <el-form-item label="详细地址" prop="detail">
              <el-input type="textarea" v-model="TouristAddress.detail" placeholder="请输入详细地址信息，如道路、门牌号、小区、楼栋号、单元等信息"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
<!--          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
          </div>-->
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import shopDetailIntroduce from "../../components/dex-shop/shopDetailIntroduce";
import shopCutDown from "../../components/dex-shop/shopCutDown";
import areaSelect from '../../components/dex-shop/shopAreaSelect'

export default {
  name: "index",
  data(){
    return {
      /*定时器时间参数*/
      timer: null,
      /*秒杀是否为空*/
      bonus_empty:false,
      ware:'',
      bonus:'',
      ShowBigImage:'',
      shadow:false,
      num:1,

      /*商品规格*/
      standard:{},
      /*商品选择的量*/
      changedStandards:0,

      /*商家地址*/
      busmanAddress:'',

      wareImageList:[],

      /*用户收货地址*/
      dialogAddressVisible:false,
      /*用户填写收货地址*/
      TouristAddress:{
        name:'',
        phone:'',
        postal:'',
        detail: '',
        local:'',
        user_id:'',
      },
      rules: {
        name:[
          { required: true, message: '请输入收货人姓名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        phone:[
          { required: true, message: '请输入收货人联系电话', trigger: 'blur' },
          { min: 11, max: 11, message: '输入的电话不正确', trigger: 'blur'}
        ],
        postal:[
          { required: true, message: '请输入收货人所在地的邮政编码', trigger: 'blur' },
          { min: 6, max: 6, message: '输入的邮政编码不正确', trigger: 'blur'}
        ],
        Area: [
          { required: true, message: '请输入地址信息', trigger: 'blur' }
        ],
        detail: [
          { required: true, message: '请填写详细地址', trigger: 'blur' }
        ]
      }

    }
  },

  watch:{

  },

  components:{
    'shopDetailIntroduce':shopDetailIntroduce,
    'shopCutDown':shopCutDown,
    'areaSelect':areaSelect,
  },


  async created() {

      /*商品信息、商品规格、商品秒杀信息不聚合*/
      const resWare=await this.axios.post(`http://localhost:8688/dex/bonus/getWareByBonus`,this.$qs.stringify({
        bonus_id:this.$route.params.bonus_id
      }))

      /*返回秒杀信息*/
      const resBonus=await this.axios.post(`http://localhost:8688/dex/bonus/getBonus`,this.$qs.stringify({
        bonus_id:this.$route.params.bonus_id
      }))

      if (resWare.data.code === 500 || resBonus.data.code === 500){
        this.bonus_empty=true;
        this.stopTimer();
      }else {
        /*成获取了该商品*/
        /*获取商品规格信息*/
        const resStandard= await this.axios.post('http://localhost:8688/dex/bonus/getStandard',this.$qs.stringify({
          bonus_id:this.$route.params.bonus_id
        }))
        this.standard=resStandard.data.object;

        const resBusmanAddress=await this.axios.post(`http://localhost:8688/dex/wares/getBusmanAddress`,this.$qs.stringify({
          ware_id:resWare.data.object.id
        }))
        this.busmanAddress=resBusmanAddress.data.object;
        console.log("地址+",resBusmanAddress.data.object)
        this.ware=resWare.data.object;
        this.bonus=resBonus.data.object;
        this.ShowBigImage=this.ware.ware_img1;
        this.initWareStandard();
        this.FillImage();
      }


  },

  mounted() {
    // 每次进入界面时，先清除之前的所有定时器，然后启动新的定时器
    clearInterval(this.timer)
    this.timer = null
    this.setTimer()
  },



  methods:{
    /*设置定时器，每秒刷新页面*/
    setTimer() {
      if(this.timer == null) {
        this.timer = setInterval( () => {
          this.getWareStandards()
        }, 1500)
      }
    },
    /*清除定时器*/
    stopTimer(){
      // 每次离开当前界面时，清除定时器
      clearInterval(this.timer)
      this.timer = null
    },
    /*获取商品规格信息*/
    async getWareStandards(){
      /*获取商品的规格*/
      const resStandard= await this.axios.post('http://localhost:8688/dex/bonus/getStandard',this.$qs.stringify({
        bonus_id:this.$route.params.bonus_id
      }))
      if (resStandard.data.code === 200){
        this.standard=resStandard.data.object;
        this.ware.ware_price=this.standard[this.changedStandards].standards_price;
        this.ware.ware_number=this.standard[this.changedStandards].standards_number;
        this.ware.standard_name=this.standard[this.changedStandards].standards_name;
        /*this.ware.ware_number=this.standard[this.changedStandards].standards_number;*/
        if (this.num > this.ware.ware_number){
          this.num=this.ware.ware_number
        }
      }else {
        this.bonus_empty = true;
        this.$message.success("秒杀已结束")
        this.stopTimer()
      }

      /*判断数量*/
      let length=this.standard.length;
      let flag=0;
      for (let i=0;i<length;i++){
        if ( this.standard[i].standards_number === 0 ){
          flag++;
        }
      }
      if (flag === length){
        this.bonus_empty=true
      }
    },
    /*用户点击规格，页面更改相关的页面信息*/
    changeWareMsg(index){
      this.changedStandards=index
      this.ware.ware_price=this.standard[index].standards_price;
      this.ware.ware_number=this.standard[index].standards_number;
      this.ware.standard_name=this.standard[index].standards_name;
      /*this.ware.ware_number=this.standard[index].standards_number;*/
      if (this.num > this.ware.ware_number){
        this.num=this.ware.ware_number
      }
    },
    /*初始化商品规格，不能用下标做定义，必须要用商品的数量做定义*/
    initWareStandard(){
      let standardLength = this.standard.length;
      let index = 0;
      while (true){
        if (this.standard[index].standards_number === 0 && standardLength>index){
          index++;
        }else {
          break;
        }
      }
      this.changeWareMsg(index)
    },
    AreaFunc(area){
      this.TouristAddress.local=area
    },
    ChangeImage(o){
      this.ShowBigImage=o;
      this.shadow=true
    },
    /*添加照片*/
    FillImage(){
      if (this.ware.ware_img1 != ''){
        this.wareImageList.push(this.ware.ware_img1)
      }if (this.ware.ware_img2 != ''){
        this.wareImageList.push(this.ware.ware_img2)
      }if (this.ware.ware_img3 != ''){
        this.wareImageList.push(this.ware.ware_img3)
      }if (this.ware.ware_img4 != ''){
        this.wareImageList.push(this.ware.ware_img4)
      }if (this.ware.ware_img5 != ''){
        this.wareImageList.push(this.ware.ware_img5)
      }
    },
    handleChange(){

    },
    /*点击购买*/
    purchase(){
      this.dialogAddressVisible=true;
    },
    /*选择的地址*/
    handleCurrentChange(val) {
      this.catchAddress=val
    },
    /*游客提交地址信息进行支付*/
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let touristOrder={};
          touristOrder.bonus_id=this.bonus.id;
          touristOrder.ware_id=this.ware.id;
          touristOrder.buy_number=this.num;
          touristOrder.standard_id=this.standard[this.changedStandards].id;
          touristOrder.add_name=this.TouristAddress.name;
          touristOrder.add_phone=this.TouristAddress.phone;
          touristOrder.add_local=this.TouristAddress.local+this.TouristAddress.detail;
          touristOrder.add_postal=this.TouristAddress.postal;
          touristOrder.busman_local=this.busmanAddress

          this.axios({
            url:'http://localhost:8688/dex/bonus/TouristBuyBonusWare',
            method:'post',
            data:this.$qs.stringify(touristOrder)
          }).then((res)=>{
            if (res.data.code === 200){
              this.$message.success("抢购成功")
              this.dialogAddressVisible=false;
              this.standard[this.changedStandards].standards_number-=this.num
              /*返货活动商品信息*/
              this.getWareStandards();
            }else if(res.data.code === 202){
              this.$message.success("数量已抢完，抢购关闭")
              this.bonus_empty=true;
            }else {
              this.$message.error("抢购失败")
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    /*商品添加超量提示*/
    overNumber() {
      this.$notify({
        title: '警告',
        message: '添加量以及超过商品总数量',
        type: 'warning'
      });
    },
    /*返回主页面*/
    GoHome(){
      this.$router.push({
        name:"home"
      })
    }
  },
  computed:{

  },
}
</script>

<style scoped>
  /*给选中的图片加阴影*/
  .shadow{
    box-shadow:0px -2px 0px 0px #e5e5e5,   /*上边阴影 */
    -2px 0px 0px 0px #e5e5e5,   /*左边阴影  */
    2px 0px 0px 0px #e5e5e5,    /*右边阴影 */
    0px 2px 0px 0px #e5e5e5;     /*下边阴影 */
  }

  .wareDemo{
    margin: 20px 100px 20px 100px;
    width: 1464.440px;
    height: 600px;
    background-color: white;
    padding: 10px;
    display: flex;
    justify-content: space-between;
  }
  .ware-img{
    margin-left: 10px;
    width:700px;
    height: 600px;
    margin-right: 20px;
  }
  .ware-img .big-img{
    width:450px ;
    height: 450px;
    margin-left: 125px;
  }
  .ware-img .big-img img{
    width: 450px;
    height: 450px;
  }
  .ware-img .list-img{
    width: 700px;
    height: 100px;
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }
  .ware-img .list-img .alone-img{
    width: 100px;
    height: 100px;
    margin-left: 5px;
    margin-right: 5px;
  }
  .ware-img .list-img .alone-img img{
    width: 100px;
    height: 100px;
    border: 1px solid #eeeeee;
  }
  .ware-message{
    width: 734.44px;
    height: 600px;
  }
  .ware-message .ware-title{
    height: 40px;
    font-size: 30px;
    font-weight: 500;
    margin-top: 10px;
  }
  /*第二部分：介绍价格以及优惠力度也就是活动*/
  .ware-message .ware-Import{
    margin-top: 16px;
    background-color: #F5F5F5;
    padding: 10px;
  }
  .ware-message .ware-Import .import-one{
    display: flex;
    justify-content: left;
    align-items: center;
  }
  .ware-message .ware-Import .ware-price-it{
    font-size: 20px;
    margin-right: 10px;
  }
  .ware-message .ware-Import .ware-price{
    color: red;
    font-size: 30px;
    font-weight: 1000;
  }
  /*优惠*/
  .ware-message .ware-Import .import-two{
    display: flex;
    justify-content: left;
    align-items: center;
    margin-top: 10px;
  }
  .ware-message .ware-Import .import-two .ware-activity{
    margin-right: 10px;
    font-size: 20px;
  }
  .ware-message .ware-Import .import-two .ware-activity-message div{
    margin-top: 4px;
  }
  /*服务说明和商品编吗*/
  .ware-mode{
    margin-top: 10px;
    display: flex;
    justify-content: left;
  }
  .ware-mode .mode-one{
    margin-right: 20px;
  }
  .ware-mode .buy-button{
    margin-right: 10px;
  }
  /*商品详情组件的设计*/
  .shop-detail-introduce{
    margin: 20px 100px 20px 100px;
    width: 1484.440px;
  }
  /*商品评论*/
  .shop-detail-comment{
    margin: 20px 100px 20px 100px;
    width: 1484.440px;
  }

  /*span定义的颜色*/
  .span-red{
    color: red;
    font-weight: bold;
  }

  .ware-standrads{
    margin-top: 5px;
    margin-right: 8px;
    cursor: pointer;
  }

  .button-div{
    height: 30px;
    border-radius: 8px;
    border: 1px solid #888888;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 5px 10px 5px 10px;
  }

  .change-button{
    height: 30px;
    border-radius: 8px;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 5px 10px 5px 10px;
    border: 3px solid red;
  }
</style>