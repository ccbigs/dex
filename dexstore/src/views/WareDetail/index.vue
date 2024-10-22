<template>
  <div>
    <!--返回最上的组件-->
    <shop-header></shop-header>
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
      <div class="ware-message">
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
        <div class="ware-mode"><div class="mode-one">服务说明</div><div class="mode-two">由DEX商城负责发货，并提供售后咨询服务</div></div>
        <div class="ware-mode"><div class="mode-one">商品编码</div><div class="mode-two">{{ ware.id }}</div></div>
        <div class="ware-mode"><div class="mode-one">商品剩余数量</div><div class="mode-two">{{ ware.ware_number }}</div></div>
        <el-divider></el-divider>
        <div>选取规格 :</div>
        <div style="display: flex;justify-content: left;">
          <div v-for="(o,i) in standard" class="ware-standrads">
            <!--如果改规格已经销售完，那么不展示该规格-->
            <div v-if="o.standards_number > 0" type="success" plain @click="changeWareMsg(i)" :class="[i===changedStandards?'change-button':'button-div']"><div>{{o.standards_name}}</div></div>
          </div>
        </div>
        <el-divider></el-divider>
        <div class="ware-mode"><div class="mode-one">发货地址</div><div class="mode-two">{{ this.busmanAddress }}</div></div>
        <el-divider></el-divider>
        <!--添加商品数量，加入购物车，点击购买-->
        <div class="ware-mode">
          <div class="mode-one"><el-input-number v-model="num" @change="handleChange" :min="1" :max="ware.ware_number" label="描述文字"></el-input-number></div>
          <div class="buy-button"><el-button @click="purchase" type="danger">点击购买</el-button></div>
          <div><el-button type="success" @click="addToCart">加入购物车</el-button></div>
          <div v-if="ware.ware_bonus === '开启'" style="margin-left: 10px"><el-button type="primary" @click="UserClickBonus" round>生成直播秒杀商品</el-button></div>
        </div>
      </div>


    </div>
    <!--商品信息简介-->
    <shop-detail-introduce :ware-details="ware.ware_connect"></shop-detail-introduce>
    <!--商品评论-->
    <shop-comment :comment="comment" :commentHeadMsg="commentHeadMsg"></shop-comment>

    <!-- 用户点击生成直播带货，填出输入时间的框   -->
    <div>
      <el-dialog title="直播带货" :visible.sync="dialogBonusVisible">
        <div style="margin-left: 20px;margin-bottom: 20px">
          <div>商品名称  :  {{this.ware.ware_name}}</div>
          <div>分红详情信息 : 当前商品:{{this.ware.ware_name}}分红为:
            <span style="color: red">{{this.ware.ware_bonusmoney}}(元)</span>,即每成功卖出一件{{this.ware.ware_name}}，你可获得
            <span style="color: red">{{this.ware.ware_bonusmoney}}(元)</span>的利润。
          </div>
        </div>
        <el-divider></el-divider>
        <el-form :model="bonusTime" :rules="bonusTimeRule" ref="bonusTimeForm" label-width="180px" class="demo-ruleForm">
          <el-form-item label="直播带货时长(分钟)" prop="ware_activeDiscount">
            <el-input-number v-model="bonusTime" :step="10" :min="10" :max="120" label="描述文字"></el-input-number>
          </el-form-item>
          <div>
            <span>注:点击参加直播带货后，系统会生成专属于你的带货商品链接</span><br>
            <span>注:成功卖出的定义为用户已签收订单，用户退货订单不计，利润收益可通过个人主页查看</span>
          </div>
          <el-form-item>
            <el-button type="primary" @click="submitBonusTimeForm('bonusTimeForm')">立即参加</el-button>
            <el-button @click="resetBonusTimeForm('bonusTimeForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>

    <!--  秒杀链接生成成功  -->
    <el-dialog
        title="您的直播带货专属链接"
        :visible.sync="dialogBonusUrlVisible"
        width="30%"
        :before-close="handleClose">
      <span>{{bonusUrl}}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="GoHome()">取 消</el-button>
        <el-button type="primary" @click="GoBonus(bonusUrl)">确 定</el-button>
      </span>
    </el-dialog>

<!--  尚未登陆  -->
    <div>
      <el-dialog
          title="注册提示"
          :visible.sync="NoLogin"
          width="30%"
          :before-close="handleClose">
        <span>系统显示您尚未登录，您需先进行登录才能购买或加入购物车</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="NoLogin = false">取 消</el-button>
          <el-button type="primary" @click="GoLogin">确 定</el-button>
      </span>
      </el-dialog>
    </div>

  <!--用户点击提交支付金额-->
    <div>
      <el-dialog
          title="选择地址"
          :visible.sync="purchasePage"
          width="59%"
          :before-close="handleClose">
        <el-table
            ref="singleTable"
            :data="addressData"
            highlight-current-row
            @current-change="handleCurrentChange"
            style="width: 100%">
          <el-table-column
              prop="name"
              label="收货人"
              width="150">
          </el-table-column>
          <el-table-column
              prop="phone"
              label="联系电话"
              width="150">
          </el-table-column>
          <el-table-column label="地址">
            <el-table-column
                prop="local"
                label="省份-市区"
                width="240">
            </el-table-column>
            <el-table-column
                prop="detail"
                label="地址"
                width="300">
            </el-table-column>
            <el-table-column
                prop="postal"
                label="邮编"
                width="120">
            </el-table-column>
          </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button @click="purchasePage = false">取 消</el-button>
          <el-button type="primary" @click="purchaseWare">支 付</el-button>
      </span>
      </el-dialog>
    </div>


    <shop-foot></shop-foot>
  </div>
</template>

<script>
import shopHeader from '../../common/dex-shop/shopHeader'
import shopDetailIntroduce from "../../components/dex-shop/shopDetailIntroduce";
import shopComment from "../../components/dex-shop/shopComment";
import shopFoot from "../../common/dex-shop/shopFoot";
import { mapState } from "vuex";

export default {
  name: "index",
  data(){
    return {
      ware:{},
      /*商品规格*/
      standard:{},
      /*商品选择的量*/
      changedStandards:0,
      ShowBigImage:'',
      shadow:false,
      num:1,
      /*评论组件的评论信息*/
      comment:[],
      /*评论的满意度，全部数据，有图数据*/
      commentHeadMsg:[],
      wareImageList:[],
      /*尚未登陆*/
      NoLogin:false,
      /*地址弹框*/
      purchasePage:false,
      /*选择地址*/
      addressData: [],
      /*用户选择得地址*/
      catchAddress:'',
      /*商家地址*/
      busmanAddress:'',
      /*直播带货*/
      dialogBonusVisible:false,
      bonusTime:'',
      /*链接生成成功，展示给用户*/
      dialogBonusUrlVisible:false,
      bonusUrl:'',

      bonusTimeRule:{
        bonusTime: [
          { required: true, message: '时长不能为0', trigger: 'blur' },
        ],
      },
    }
  },

  watch:{
    $route(to,from){
      this.$router.go(0)
    },
    /*加入购物车的数量不能超过商品数量*/
    num(newName,oldName){
      /*当数量和商品数量相等时，就要进行最大值和商品总数进行赋值*/
      if (newName === this.ware.ware_number){
        this.maxWare=this.ware.ware_number
        this.overNumber()
      }
      if (newName > this.ware.ware_number){
        this.num--;
        this.overNumber()
      }
    }
  },

  components:{
    'shopHeader':shopHeader,
    'shopDetailIntroduce':shopDetailIntroduce,
    'shopComment':shopComment,
    'shopFoot':shopFoot
  },


  async created() {
      await this.InitPage();
  },



  methods:{
    /*初始化页面*/
    async InitPage(){
      this.wareImageList = [];
      try {
        const resComment=await this.axios.get(`http://localhost:8681/dex/comment/getCommentByWare?ware_id=${this.$route.query.ware_id}`)
        const resWare=await this.axios.get(`http://localhost:8688/dex/wares/getWareById?ware_id=${this.$route.query.ware_id}`)
        const resBusmanAddress=await this.axios.post(`http://localhost:8688/dex/wares/getBusmanAddress`,this.$qs.stringify({
          ware_id:this.$route.query.ware_id
        }))
        const resStandard=await this.axios.post(`http://localhost:8680/dex/standards/getStandardByWare`,this.$qs.stringify({
          ware_id:this.$route.query.ware_id
        }))
        console.log("商品详情页",resWare);
        if (resWare.data.object === null){
          this.$message.warning("访问该商品失败");
          await this.$router.push({
            name: 'home'
          })
        }

        this.ware=resWare.data.object;
        /*获取全部的商品规格*/
        this.standard=resStandard.data.object;
        this.comment=resComment.data.object;
        /*商品评论数据*/
        this.commentHeadMsg=[resComment.data.message,resComment.data.size,resComment.data.code]
        /*获取商家的发货地址*/
        this.busmanAddress=resBusmanAddress.data.object
        this.ShowBigImage=this.ware.ware_img1;
        this.initWareStandard();
        this.FillImage()
      }catch (e) {
        console.log(e);
      }
    },
    /*生成直播链接后，点击按钮跳转到主页面*/
    GoHome(){
      this.dialogBonusUrlVisible=false;
      this.$router.push({
        name:'home'
      })
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
    async purchase(){
      await this.InitPage()
      if (!this.$store.state.login || this.ware.ware_number<=0){
        if (!this.$store.state.login){
          this.NoLogin=true
        }else {
          this.AddCartFiled()
        }
      }else {
        console.log("进入支付")
        this.purchasePage=true
        /*判断当前用户有没有地址*/
        if (this.$store.state.userAddress === ''){
          await this.$router.push({
            name: 'addressAdd'
          })
        }else {
          this.addressData=this.$store.state.userAddress;
        }

        console.log("wareDetail-index地址",this.addressData);
      }
    },
    /*点击加入购物车*/
    async addToCart(){
      await this.InitPage();
      if (!this.$store.state.login || this.ware.ware_number<=0){
        if (!this.$store.state.login){
          this.NoLogin=true
        }else {
          this.AddCartFiled()
        }
      }else {
        /*将购物车数量添加至数据库*/
        this.axios.post('http://localhost:8680/dex/cart/addCartWare',this.$qs.stringify({
          user_id:this.$store.state.userInfo.id,
          ware_id:this.ware.id,
          number:this.num,
          standard_id:this.standard[this.changedStandards].id,
        })).then((res)=>{
          if (res.data.code === 200){
            this.$message.success("添加购物车成功")
          }else {
            this.$message.error("添加购物车失败")
          }
        })

      }
    },
    /*-------------------------参加直播带货，并生成相应的链接---------------------*/
    /*用户点击参加直播带湖哦*/
    UserClickBonus(){
      if (!this.$store.state.login){
        this.NoLogin=true
      }else {
        this.dialogBonusVisible=true
        this.bonusTime=10;
      }
    },
    /*提交修改信息*/
    submitBonusTimeForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let bonus= {};
          bonus.id=this.bonusTime;
          bonus.ware_id=this.ware.id;
          bonus.user_id=this.$store.state.userInfo.id;
          bonus.bonus_money=this.ware.ware_bonusmoney;

          this.axios.post('http://localhost:8681/dex/bonus/userAttendBonusByWare',this.$qs.stringify(
              bonus
          )).then((res)=>{
            if (res.data.code === 200){
              this.$message.success("直播带货链接生成成功");
              this.dialogBonusVisible=false;
              /*展现生成的链接*/
              this.bonusUrl=res.data.message;
              this.dialogBonusUrlVisible=true;
              /*setTimeout(function(){
                  this.GoHome()
              },5000) //   function 里面的this指向的是windows*/

            }else {
              this.$message.error("直播带货链接生成失败")
            }
          })
        }
      });
    },
    /*重置表单信息*/
    resetBonusTimeForm(formName) {
      this.$refs[formName].resetFields();
    },
    /*生成商品连接成功*/
    GoBonus(url){
      window.location.href=url;
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

    /*关闭登录框*/
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    /*用户尚未登陆进行跳转*/
    GoLogin(){
      this.NoLogin=false;
      this.$router.push({
        name:'login'
      })
    },
    /*加入购物车成功提示*/
    AddCartSuccess() {
      this.$notify({
        title: '添加成功',
        message: '成功加入购物车',
        type: 'success'
      });
    },
    /*加入购物车失败提示*/
    AddCartFiled() {
      this.$notify.error({
        title: '添加失败',
        message: '商品添加失败，请查看商品是否还有剩余',
      });
    },
    /*用户购买商品*/
    async purchaseWare(){
      this.ware.address_id=this.catchAddress.id;
      this.ware.user_id=this.$store.state.userInfo.id;
      this.ware.buy_numbers=this.num;

      /*if (this.ware.ware_active === '满减'){
        this.ware.ware_img4=this.wareActive.full_price;
        this.ware.ware_img5=this.wareActive.full_reduction;
      }else if (this.ware.ware_active === '打折'){
        this.ware.ware_img4=this.ware.ware_activeDiscount;
      }*/

      /*减少用户购买商品模块的数量*/
      const resStandard=await this.axios.post('http://localhost:8680/dex/standards/reduceStandardNumberByUserBuyWare',this.$qs.stringify({
        standard_id:this.standard[this.changedStandards].id,
        buy_number:this.num,
      }))

      console.log(resStandard)
      if(resStandard.data.code === 200){
        /*商品数量减少*/
        const resWare=await this.axios.post('http://localhost:8688/dex/wares/reduceWareForWareDetail',this.$qs.stringify({
          ware_id:this.ware.id,
          /*先引用*/
          number:this.num,
        }))
        /*购买商品成功加入订单*/
        const resOrder=await this.axios.post('http://localhost:8681/dex/order/setOrderForWareDetail',this.$qs.stringify(
            this.ware
        ))
        if (resOrder.data.code === 200){
          this.successMsg();
          await this.$router.push({
            name: 'orAll'
          })
        }else {
          location.reload()
          this.filedMsg()
        }
      }else {
        this.$message.warning("商品太火了，买不了那么多数量哦")
        location.reload()
      }


    },
    /*选择的地址*/
    handleCurrentChange(val) {
      this.catchAddress=val
    },
    /*成功提示*/
    successMsg() {
      this.$notify({
        title: '购买成功',
        message: '成功购买商品',
        type: 'success'
      });
    },
    /*失败提示*/
    filedMsg() {
      this.$notify.error({
        title: '购买失败',
        message: '商品购买失败',
      });
    },
    /*商品添加超量提示*/
    overNumber() {
      this.$notify({
        title: '警告',
        message: '添加量以及超过商品总数量',
        type: 'warning'
      });
    },
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