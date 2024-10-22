<template>
<div  @click="GoWareDetail(ware.id)" style="border-radius: 15px;">
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

      </el-card>
    </el-col>
  </el-row>
</div>
</template>

<script>
export default {
  name: "shop-wares",
  props:['ware'],
  methods:{
    GoWareDetail:function (id) {
      console.log("进入商品页",id)
      this.$router.push({
        name:'wareDetail',
        query:{
          ware_id:id
        }
      })
    },
  }
}
</script>

<style scoped>

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

  p{
    margin-top: 8px;
    margin-bottom: 10px;
    padding: 0px 6px 0px 6px;
  }

  .PPrice{
    color: red;
    font-weight: bold;
  }
</style>