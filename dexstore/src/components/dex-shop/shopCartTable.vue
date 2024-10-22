<template>
  <div>
    <div class="cart-table">
      <el-table
          ref="multipleTable"
          :data="cartWares"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange">
        <el-table-column
            label="全选"
            type="selection"
            :selectable='selectable'
            :reserve-selection="true"
            width="55">
        </el-table-column>
        <!--此处写上商品的图片和名称-->
        <el-table-column label="商品" align="center" width="500" prop="">
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
        <el-table-column
            label="商品规格"
            align="center"
            prop="standards_name"
            width="140">
        </el-table-column>
        <!--单价-->
        <el-table-column label="单价" align="center" width="180">
          <template #default="scope">
            <div>￥{{scope.row.ware_price}}</div>
          </template>
        </el-table-column>
        <!--数量-->
        <el-table-column
            label="数量"
            align="center"
            width="240"
            show-overflow-tooltip>
          <template #default="scope">
            <el-input-number v-if="scope.row.cart_number>0" v-model="scope.row.cart_number" @change="checkPrice(scope.row.id,scope.$index)" :min="1" :max="scope.row.ware_number" label="描述文字"></el-input-number>
            <div v-else style="color: red;"> 当前商品已售馨 </div>
          </template>
        </el-table-column>
        <!--小计-->
        <el-table-column
            label="小计"
            align="center"
            width="220"
            show-overflow-tooltip>
          <template #default="scope">
            <div class="price-class">￥{{scope.row.totalPrice}}</div>
          </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="120">
          <template #default="scope">
            <el-button
                @click.prevent="deleteRow(scope.$index, cartWares,scope.row.id)"
                type="text"
                size="small" align="center">
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="cart-total">
      <div class="total-font" >
        已选 <span style="color: red">{{totalNum}}</span> 件商品
      </div>
      <div class="total-right">
        <div class="totalPrice"><i data-v-baa454ba></i>总计:<span style="color: red">￥{{totalPrice}}</span></div>
        <div @click="buyFromCart" class="settlement"><div>立即结算</div></div>
      </div>
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
          <el-button type="primary" @click="settlement">支 付</el-button>
      </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "shopCartTable",
  props:['cartWares'],
  async created() {
    const address=await this.axios.post('http://localhost:8681/dex/address/getUserAddress',this.$qs.stringify({
      user_id:this.$store.state.userInfo.id
    }))
    this.addressData = address.data.object;
  },

  watch:{

  },

  data() {
    return {
      totalNum:0,
      totalPrice:0,
      /*选中框选中的商品*/
      multipleSelection: [],
      /*地址信息*/
      purchasePage:false,
      addressData:'',
      catchAddress:'',
    }
  },
  methods:{
    /*用户点击购买*/
    async settlement(){

      for (let i=0 ;i < this.multipleSelection.length ;i++){
        /*购买商品*/
        this.multipleSelection[i].ware_connect=this.catchAddress.id;

        /*减少用户购买商品模块的数量*/
        const resStandard=await this.axios.post('http://localhost:8680/dex/standards/reduceStandardNumberByUserBuyWare',this.$qs.stringify({
          standard_id:this.multipleSelection[i].standard_id,
          buy_number:this.multipleSelection[i].cart_number,
        }))

        if (resStandard.data.code === 200) {
          /*1.先减少dexWare中的数量*/
          const reduceWareNumber = await this.axios.post('http://localhost:8688/dex/wares/reduceWareForWareDetail', this.$qs.stringify({
            ware_id: this.multipleSelection[i].ware_id,
            number: this.multipleSelection[i].cart_number,
          }))
          const res = await this.axios.post('http://localhost:8681/dex/order/setOrderForShopCart', this.$qs.stringify(this.multipleSelection[i]))
          /*购买成功后删除购物车中的数据*/
          if (res.data.code === 200) {
            const resmove = await this.axios.post('http://localhost:8680/dex/cart/removeUserCartWare', this.$qs.stringify({
              user_id: this.multipleSelection[i].user_id,
              cart_id: this.multipleSelection[i].id
            }))
            if (resmove.data.code === 200) {
              this.successBuy()
              await this.$router.push({
                name: 'orAll'
              })
            }
          }
        }else {
          this.$message.warning("商品数量不足，要赶快哦")
          location.reload()
        }
      }

    },

    buyFromCart(){
      if (this.multipleSelection.length>0){
        this.purchasePage = true;
      }else {
        this.$message.warning("请先选择要购买的商品才能结算哦")
      }

    },
    /*移除已有的*/
    async deleteRow(index, rows , ware_id) {
      rows.splice(index, 1);
      let user_id=this.$store.state.userInfo.id
      const res=await this.axios.post('http://localhost:8680/dex/cart/removeUserCartWare',this.$qs.stringify({
        user_id:user_id,
        cart_id:ware_id
      }))

      if (res.data.code === 200){
          this.successMsg()
      }else {
        this.filedMsg()
      }

    },
    /*当前商品是否可以勾选*/
    selectable(row,index) {

      if(row.ware_number===0){
        return false
      }else {
        return true
      }
    },
    /*更新选中的商品价格*/
    handleSelectionChange(val) {
      this.totalPrice=0;
      this.multipleSelection = val;
      this.totalNum=this.multipleSelection.length;
      for (let i = 0; i < this.totalNum; i++) {
        this.totalPrice+=this.multipleSelection[i].totalPrice;
      }
    },
    /*选中物品时，更改物品数量，进行数量得确认*/
    checkPrice(wareId,index){
      /*商品数量同步到数据库中*/
      this.axios.post('http://localhost:8680/dex/cart/updateCartNumber',this.$qs.stringify({
        cart_id:this.cartWares[index].id,
        cart_number:this.cartWares[index].cart_number,
      }))
      /*更新总价*/
      this.computeActive(this.cartWares[index],index)
      /*选中的总价*/
      for (let i = 0; i < this.multipleSelection.length; i++) {
        if (this.multipleSelection[i].id === wareId){
          this.multipleSelection[i] = this.cartWares[index];
          this.handleSelectionChange(this.multipleSelection)
          break;
        }
      }
    },
    /*关闭登录框*/
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    /*选择的地址*/
    handleCurrentChange(val) {
      this.catchAddress=val
    },
    /*活动价格计算*/
    computeActive(cart,index){
      if (cart.ware_active === '未参与活动'){
        cart.totalPrice=cart.ware_price*cart.cart_number;
      }else if(cart.ware_active === '打折'){
        cart.totalPrice=Number(cart.ware_price*cart.cart_number*0.1*cart.ware_activeDiscount).toFixed(2);
        cart.totalPrice=Number(cart.totalPrice);
      }else {
        if (cart.ware_price*cart.cart_number > cart.ware_activeFullPrice){
          cart.totalPrice=cart.ware_price*cart.cart_number-cart.ware_activeFullReduction;
        }else {
          cart.totalPrice=cart.ware_price*cart.cart_number;
        }
      }

      /*将更改的值返回到原来cartWare中*/
      this.cartWares[index]=cart
    },


    /*成功提示*/
    successMsg() {
      this.$notify({
        title: '移除成功',
        message: '成功移除商品',
        type: 'success'
      });
    },
    /*购买成功*/
    successBuy() {
      this.$notify({
        title: '购买成功',
        message: '成功购买商品',
        type: 'success'
      });
    },
    /*失败提示*/
    filedMsg() {
      this.$notify.error({
        title: '移除失败',
        message: '商品移除失败',
      });
    },
  }
}
</script>

<style scoped>
  .cart-table{
    width: 1464.440px;
    margin-left: 100px;
    margin-right: 100px;
    padding: 10px 20px 10px 20px;
    margin-top: 20px;
    background-color: white;
    border-radius: 8px;
  }
  .cart-total{
    width: 1504.440px;
    height: 70px;
    margin-left: 100px;
    margin-right: 100px;
    margin-top: 20px;
    background-color: white;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .table-ware{
    display: flex;
    justify-content: left;
    align-items: center;
  }
  .ware-image{
    margin-left: 30px;
    margin-right: 50px;
  }
  .ware-name{
    font-size: 24px;
  }

  /*结算条*/
  .total-font{
    color: #999999;
    margin-left: 20px;
  }
  .total-right{
    display: flex;
    justify-content: right;
    align-items: center;

  }
  .total-right .settlement{
    width: 200px;
    height: 70px;
    background-color: red;
    color: white;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
  .totalPrice{
    margin-right: 10px;
    font-weight: bold;
    font-size: 20px;
  }

  .price-class{
    color: red;
    font-size: 22px;
    font-weight: bold
  }

</style>