<template>
  <div>
    <el-card class="box-card">
      <!--   订单基本信息   -->
      <div class="card-one">
        <div><el-tag>订单状态</el-tag>:<span style="color: red">{{orders.logistics_status}}</span></div>
        <div style="margin-right: 20px">
          发货快递:
          <span v-if="orders.logistics_status==='运输中' || orders.logistics_status==='已签收' || orders.logistics_status==='已关闭'">{{orders.order_couriernumber}}</span>
          <span v-else style="color: red">暂无快递信息</span>
        </div>
      </div>
      <el-divider>基本信息</el-divider>
      <div class="one-mode">
        <div>订单编号：{{orders.id}}</div>
        <div style="display: flex;align-items: center;"><div>商品：{{orders.ware_name}}</div><img :src="orders.ware_img1" alt=""></div>
        <div>售价(人民币):{{orders.ware_price}}</div>
      </div>
      <el-divider>买家付款信息</el-divider>
      <div class="one-mode">
        <div>购买数量:{{orders.orders_number}}</div>
        <div>实付金额(人民币):{{orders.orders_totalprice}}</div>
        <div>付款时间:{{orders.orders_date}}</div>
      </div>
      <el-divider>买家收货地址</el-divider>
      <div class="one-mode">
        <div>收货人 : {{orders.name}}</div>
        <div>收货人电话 : {{orders.phone}}</div>

      </div>
      <div class="one-mode">
        <div>收货地址 : {{orders.local}}{{orders.detail}}</div>
        <div>地区邮政编码 : {{orders.postal}}</div>
      </div>

    </el-card>
  </div>
</template>

<script>
export default {
  name: "searchCard",
  props:['orders']
}
</script>

<style scoped>
  .one-mode{
    display: flex;
    justify-content: space-around;
    align-items: center;
  }

  img{
    width: 100px;
    height: 100px;
  }

  .card-one{
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
</style>