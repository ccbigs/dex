<template>
  <div>
    <!--搜索框-->
    <div class="search-body">
      <div>
        <div class="block">
          <el-cascader
              v-model="selectValue"
              :options="options"
              @change="handleChange"></el-cascader>
        </div>
      </div>
      <div>
        <el-input
            placeholder="请输入需要搜索的内容"
            v-model="input"
            class="input-with-select"
            style="width: 500px"
        >
          <template #prepend>
            <span>订单号</span>
          </template>
          <template #append>
            <el-button icon="el-icon-search" @click="searchOrder"></el-button>
          </template>
        </el-input>
      </div>
      <div>
        <el-switch
            v-model="ESOrMySQL"
            active-text="快速搜索"
            inactive-text="普通搜索">
        </el-switch>
      </div>
    </div>
    <!--搜索结果-->
    <el-divider class="hr"></el-divider>
    <!--  普通订单  -->
    <div v-if="selectValue === '普通订单'">
      <div v-if="!orders">
        <el-empty description="尚无搜索订单"></el-empty>
      </div>
      <div v-else>
        <search-card :orders="orders"></search-card>
      </div>
    </div>
    <!--秒杀订单-->
    <div v-else>
      <div v-if="!orders">
        <el-empty description="尚无搜索订单"></el-empty>
      </div>
      <div v-else>
        <el-card>
          <el-descriptions class="margin-top" title="秒杀订单详情" :column="3"  border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                订单号
              </template>
              {{ orders.id }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-shopping-bag-1"></i>
                商品名称
              </template>
              {{ orders.ware_name }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-picture-outline"></i>
                商品图片
              </template>
              <el-image
                  style="width: 50px; height: 50px"
                  :src="orders.ware_img1"
                  :preview-src-list="[orders.ware_img1]">
              </el-image>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-coin"></i>
                商品原价(元)
              </template>
              {{orders.ware_price}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-box"></i>
                购买数量
              </template>
              {{orders.buy_number}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-coin"></i>
                实付金额(元)
              </template>
              {{orders.bonus_totalprice}}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-folder"></i>
                订单状态
              </template>
              {{orders.order_state}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-date"></i>
                下单时间
              </template>
              {{orders.order_time}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document-remove"></i>
                快递单号
              </template>
              {{orders.bonusorder_couriernumber}}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                收货人
              </template>
              {{orders.add_name}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-phone-outline"></i>
                收货人联系电话
              </template>
              {{orders.add_phone}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                收货人地址
              </template>
              {{orders.add_local}}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-location-outline"></i>
                发货地址
              </template>
              {{ orders.busman_local }}
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import searchCard from "../../../components/busman/searchCard";
export default {
  name: "orderSearch",
  components:{
    'searchCard':searchCard
  },
  data(){
    return{
      input: '',
      search:'',
      orders:'',
      ESOrMySQL:false,
      selectValue:'普通订单',
      options: [{
        value: '普通订单',
        label: '普通订单',
      },{
        value: '秒杀订单',
        label: '秒杀订单',
      },],
    }
  },
  methods:{
    /*查询选项*/
    handleChange(){

    },
    async searchOrder(){
      if (this.input){
        if (this.selectValue === '普通订单') {
          if (!this.ESOrMySQL) {
            const res = await this.axios.post('order/busmanSearchOrder', this.$qs.stringify({
              busman_id: this.$store.state.busmanInfo.id,
              order_id: this.input
            }))
            console.log(res);
            if (res.data.code === 200) {
              this.orders = res.data.object
            } else if (res.data.code === 500) {
              this.$message.error("该订单号属于其他商家，您无权查询")
              this.orders = '';
            } else {
              this.$message.error("您输入的订单号不存在");
              this.orders = '';
            }
          } else {
            const res = await this.axios.post('order/busmanSearchOrderByES', this.$qs.stringify({
              busman_id: this.$store.state.busmanInfo.id,
              order_id: this.input
            }))
            console.log(res);
            if (res.data.code === 200) {
              this.orders = res.data.object
            } else if (res.data.code === 500) {
              this.$message.error("该订单号属于其他商家，您无权查询")
              this.orders = '';
            } else {
              this.$message.error("您输入的订单号不存在");
              this.orders = '';
            }
          }
        }else {

            /*普通搜索*/
            const res = await this.axios.post('order/busmanSearchBonusOrder', this.$qs.stringify({
              busman_id: this.$store.state.busmanInfo.id,
              order_id: this.input
            }))

            if (res.data.code === 200){
              this.orders = res.data.object
              console.log(this.orders);
            }else {
              this.$message.error("订单查询失败，暂无该订单信息")
            }

        }
      }else {
        this.$message.error("请输入订单号");
        this.orders='';
      }
    }
  }
}
</script>

<style scoped>
  .search-body{
    width: 1000px;
    height: 50px;
    margin: 0px auto;
    margin-top: 20px;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
  .hr{
    margin: 50px 0px 10px 0px;
  }
  .search-result{
  }
</style>