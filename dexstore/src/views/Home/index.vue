<template>
  <div class="home">

    <!--轮播图组件-->
    <div class="banner">
      <el-carousel :interval="4000" type="card" height="500px">
        <el-carousel-item v-for="(o,i) in banner" :key="i">
          <img :src="o.img" alt="" @click="GoWare(o.ware_id)">
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- 推荐商品   -->
    <div class="demo3">
      <shop-mode :title="recommend_name">
        <div slot="content">
          <shop-wares v-for="(o,index) in hp_recommend" :class="[index%5===0?'demo3-first':'demo3-ch']" :ware="o"></shop-wares>
        </div>
      </shop-mode>
    </div>

    <!-- 火爆商品   -->
    <div class="demo3">
      <shop-mode :title="hot_name">
        <div slot="content">
          <shop-wares v-for="(o,index) in hp_hot" :class="[index%5===0?'demo3-first':'demo3-ch']" :ware="o"></shop-wares>
        </div>
      </shop-mode>
    </div>


    <shop-top></shop-top>
  </div>
</template>

<script>
import shopWares from '../../components/dex-shop/shopWares';
import shopMode from '../../components/dex-shop/shopDemo';
import shopTop from "../../components/dex-shop/shopTop";

export default {
  name: "index",
  data(){
    return {
      /*轮播图*/
      banner:[],
      hp_new:[],
      hp_hot:[],
      hp_recommend:[],
      hot_name:'火爆商品',
      recommend_name:'好物推荐',
    }
  },
  async created() {
    try {
      const res =await this.axios.get("http://localhost:8680/dex/home/getAllHome");
      console.log("返回值",res)
      if (res.data.code === 200){
        this.banner = res.data.carousel;
        this.hp_new = res.data.hp_new;
        this.hp_hot = res.data.hp_hot;
        this.hp_recommend = res.data.hp_recommend;
      }
    }catch (err){
      console.log("输出失败")
    }


  },
  components:{
    'shopMode':shopMode,
    'shopWares':shopWares,
    'shopTop':shopTop,
  },
  methods:{
    GoWare(ware_id){
      this.$router.push({
        name:'wareDetail',
        query:{
          ware_id:ware_id
        }
      })
    }
  }
}
</script>

<style scoped>

  .banner{
    margin: 40px 100px 30px 100px;
  }
  .banner img{
    width: 900px;
    height: 500px;
  }
  /*最近上新*/
  .demo01{
    width: 1484.44px;
    margin: 0px 100px 30px 100px;
  }
  .demo01 .row{
    width: 300px;
    margin-left: 94.8px;
  }
  .row-no{
    width: 300px;
  }
  .demo01 img{
    width: 100%;
    height: 200px;
  }


  /*demo3*/
  .demo3{
    width: 1484.44px;
    margin: 20px 100px 20px 100px;
  }

  .demo3-ch{
    display: inline-block;
    width: 240px;
    height: 290px;
    margin-left: 71.11px ;
    cursor: pointer;
    margin-bottom: 36px;
  }

  .demo3-first{
    display: inline-block;
    width: 240px;
    height: 290px;
    cursor: pointer;
    margin-bottom: 36px;
  }

  .demo01-echarts{
    display: flex;
    justify-content: space-between;
    align-items: center;
  }






</style>