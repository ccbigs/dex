<template>
  <div class="shop-cart">
    <cart-header></cart-header>
    <div v-if="cart.length === 0">
      <el-empty description="购物车里暂无商品，赶快去添加吧">
        <el-button type="primary" @click="GoWare">点击前往</el-button>
      </el-empty>
    </div>
    <div v-else>
      <!--表单内容-->
      <cart-table :cartWares="cart"></cart-table>
    </div>
  </div>
</template>

<script>
import cartHeader from '../../common/dex-shop/shopCartHeader'
import cartTable from '../../components/dex-shop/shopCartTable'
import { mapState } from "vuex";

export default {
  name: "index",
  data(){
    return {
      cart:[],
    }
  },

  async created() {
    await this.getUserCartWare();
  },
  methods:{
    /*跳转到商品页面*/
    GoWare(){
      this.$router.push({
        name:'wares'
      })
    },
    async getUserCartWare(){
      const cart=await this.axios.post('http://localhost:8680/dex/cart/getUserCartWare',this.$qs.stringify({
        user_id:this.$store.state.userInfo.id
      }))
      let carts=cart.data.object;
      /*计算出活动价格信息,新增键值totalprice,总价格*/
      for(let i = 0, l = carts.length; i < l; i++){
        if (carts[i].ware_active === '未参与活动'){
          carts[i].totalPrice=carts[i].ware_price*carts[i].cart_number;
        }else if(carts[i].ware_active === '打折'){
          carts[i].totalPrice=Number(carts[i].ware_price*carts[i].cart_number*0.1*carts[i].ware_activeDiscount).toFixed();
          carts[i].totalPrice=Number(carts[i].totalPrice)
        }else {
          if (carts[i].ware_price*carts[i].cart_number > carts[i].ware_activeFullPrice){
            carts[i].totalPrice=carts[i].ware_price*carts[i].cart_number-carts[i].ware_activeFullReduction;
          }else {
            carts[i].totalPrice=carts[i].ware_price*carts[i].cart_number;
          }
        }
      }
      this.cart=carts;
    }

  },
  components:{
    'cartHeader':cartHeader,
    'cartTable':cartTable
  },
  computed:{
    ...mapState(['login','userInfo','cartList','showCart'])
  }
}
</script>

<style scoped>

</style>