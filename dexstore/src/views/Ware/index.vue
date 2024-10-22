<template>
  <div>
    <dex-header></dex-header>
    <div class="catalog">
      <div>
        <!--分类-->
        <div>分类：<a @click="getAllWares" :class="{titleColor:999===catalogColor}">全部<el-divider direction="vertical"></el-divider></a>
          <a @click="handleCatalog(i,o.id)" :class="{titleColor:i===catalogColor}" v-for="(o,i) in categories">{{o.categorie_name}}<el-divider direction="vertical"></el-divider></a></div>
        <el-divider></el-divider>
        <!--排序-->
        <div class="sort">排序：<a @click="handleSort(i)" :class="{titleColor:i===titleColor}" href="#" v-for="(o,i) in navList">{{o.title}}<el-divider direction="vertical"></el-divider></a></div>
        <div class="Value-range">
          <input type="text" placeholder="最低价格" v-model="min" style="height: 20px">
          <span>-</span>
          <input type="text" placeholder="最高价格" v-model="max" style="height: 20px">
          <el-button type="primary" size="mini" @click="ValueRange" style="margin-left: 6px">确定</el-button>
        </div>
        <el-divider></el-divider>
        <!--其他-->
        <div class="other">
          活动：
          <a @click="handleActive('满减',1000)" :class="{titleColor:1000===catalogColor}">满减商品<el-divider direction="vertical"></el-divider></a>
          <a @click="handleActive('打折',1001)" :class="{titleColor:1001===catalogColor}">打折商品<el-divider direction="vertical"></el-divider></a>
        </div>
      </div>
    </div>
    <div class="wares">
      <div class="allWares">
        <shop-wares v-for="(ware,index) in allWares" :key="ware.id" :ware="ware" :class="[index%5===0?'ware-first':'ware-demo']"></shop-wares>
      </div>
      <div class="Navigation-page">
        <div class="block">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[8, 20, 40, 80]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
          </el-pagination>
        </div>
      </div>
    </div>

    <shop-top></shop-top>
  </div>
</template>

<script>
import shopHeader from "../../common/dex-shop/shopHeader";
import shopWares from "../../components/dex-shop/shopWares";
import shopTop from "../../common/dex-shop/shopFoot";

export default {
  name: "index",
  data(){
    return{
      min:'',
      max:'',
      navList:[
        {title:"综合排序"},
        {title:"价格由低到高"},
        {title:"价格由高到低"}
      ],
      titleColor:'',
      catalogColor:'',
      isIndex:0,
      currentPage:1,
      pageSize:20,
      sort:'',
      total:'',
      /*渲染所有商品的变量*/
      allWares:[],
      /*持久化存储*/
      final:[],
      /*用于存储商品分类信息*/
      categories:'',
    }
  },
  created() {
    this.getAllCategorie();
    /*判断是点击全部商品进入的还是输入搜索的词语进入的*/
      if (this.$route.query.ware_name === undefined){
        this.getAllWares();
      }else {
        this.getSearchWares(this.$route.query.ware_name);
      }


  },
  components:{
    'dexHeader':shopHeader,
    'shopWares':shopWares,
    'shopTop':shopTop,
  },
  watch:{
    $route(to,from){
      this.$router.go(0)
    }
  },
  computed:{

  },
  methods:{
    /*点击商品进入商品详情页*/
    async getAllWares() {
      this.catalogColor=999;
      try {
        const res=await this.axios.get('http://localhost:8688/dex/wares/getAllWare')
        console.log(res);
        this.allWares=res.data.object;
        this.final=res.data.object;
        this.total=res.data.size;
      }catch (e) {
        console.log(e);
      }
    },
    /*获取分类商品的数量*/
    async getCategorieWare(categorie_id){
      const res=await this.axios.post('http://localhost:8688/dex/wares/getWareByCategorie',this.$qs.stringify({
        categorie_id:categorie_id
      }))
      console.log(res.data.object);
      if (res.data.code === 200){
        console.log(res.data.object);
        this.allWares=res.data.object;
        this.final=res.data.object;
        this.total=res.data.size;
      }else {
        this.$message.error("当前分类暂无商品，已经在催商家上架了哦")
      }
    },
    /*获取满减商品*/

    /*点击商品进入商品详情页*/
    async getSearchWares() {
      try {
        const res=await this.axios.get(`http://localhost:8688/dex/wares/getSearchWares?ware_name=${this.$route.query.ware_name}`)
        /*console.log(res);*/
        console.log("路由数据",this.$route.query.ware_name)
        this.allWares=res.data.object;
        this.final=res.data.object;
        this.total=res.data.size;
      }catch (e) {
        console.log(e);
      }
    },
    /*正序*/
    LowHighSort:function(property){
      return function (a, b) {
        let value1 = a[property];
        let value2 = b[property];
        return value1 - value2
      }
    },
    /*倒叙*/
    HighLowSort:function(property){
      return function (a, b) {
        let value1 = a[property];
        let value2 = b[property];
        return value2 - value1
      }
    },
    /*进行价格排序*/
    handleSort:function(i){
      this.titleColor=i
        if (i === 0){
          /*综合排序*/
          this.getAllWares()
        }else if(i === 1){
          /*由低到高*/
          this.axios.post('http://localhost:8688/dex/wares/priceFromLowToHigh').then((res)=>{
            if (res.data.code === 200){
              this.allWares=res.data.object
              console.log("进入1");
            }
          })
        }else {
          this.axios.post('http://localhost:8688/dex/wares/priceFromHighToLow').then((res)=>{
            if (res.data.code === 200){
              this.allWares=res.data.object
              console.log(this.allWares,"进入2");
            }
          })

        }
    },
    /*点击分类操作*/
    handleCatalog:function (i,categorie_id) {
      this.catalogColor=i;
      this.getCategorieWare(categorie_id)
    },
    /*满减*/
    async handleActive(data,catalogColor){
      this.catalogColor=catalogColor;
      const res=await this.axios.post('http://localhost:8688/dex/wares/getWareByActive',this.$qs.stringify({
        ware_active:data
      }))

      if (res.data.code === 200){
        this.allWares=res.data.object;
        this.final=res.data.object;
        this.total=res.data.size;
      }else {
        this.$message.error("获取活动商品失败，当前暂未有活动商品")
      }
    },
    extendDeep(parent, child) {
      child = child || {};
      for(let i in parent) {
        if(parent.hasOwnProperty(i)) {
          //检测当前属性是否为对象
          if(typeof parent[i] === "object") {
            //如果当前属性为对象，还要检测它是否为数组
            //这是因为数组的字面量表示和对象的字面量表示不同
            //前者是[],而后者是{}
            child[i] = (Object.prototype.toString.call(parent[i]) === "[object Array]") ? [] : {};
            //递归调用extend
            this.extendDeep(parent[i], child[i]);
          } else {
            child[i] = parent[i];
          }
        }
      }
      return child;
    },
    /*判断在~价格到~价格之间的数据*/
    ValueRange:function () {
      let a=[]
      for (let i = 0; i <this.allWares.length; i++) {
        if (this.allWares[i].ware_price >this.min && this.allWares[i].ware_price<this.max){
          a.push(this.allWares[i])
        }
      }
      console.log("凭借后的数据",a)
      this.allWares=a;
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.handleCurrentChange(this.currentPage)
      /*console.log(`每页 ${val} 条`);*/
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.currentChangePage(this.final,this.currentPage)
      /*console.log(`当前页: ${val}`);*/
    },currentChangePage(list,currentPage) {
      let from = (currentPage - 1) * this.pageSize;
      let to = currentPage * this.pageSize;
      this.allWares = [];
      for (; from < to; from++) {
        if (list[from]) {
          this.allWares.push(list[from]);
        }
      }
    },

    /*获取全部的分类信息*/
    async getAllCategorie(){
      const res=await this.axios.post('http://localhost:8688/dex/wares/getAllCategorie')

      if (res.data.code === 200){
        this.categories=res.data.object;
      }else {
        this.$message.error("当前分类暂无商品，已经在催商家上架了哦")
      }
    }


  }
}
</script>

<style scoped>
  a{
    cursor: pointer;
  }
  /*点击标题也变蓝*/
  .titleColor{
    color: #6b95ea;
  }

  .catalogColor{
    color: #6b95ea;
  }

  .wares{
    margin-bottom: 80px;
  }

  .catalog{
    margin: 20px 100px 20px 100px;
    height: 180px;
    background-color: white;
    border-radius: 18px;
    padding: 30px;
  }

  .allWares{
    margin-left: 100px;
    margin-right: 100px;
    margin-top: 20px;
  }
  .demo3-ch{
    display: inline-block;
    width: 25%;
    height: 300px;
  }

  .sort{
    display: inline-block;
  }

  .Value-range{
    display: inline-block;
  }

  .Navigation-page{
    margin: 20px 100px 20px 0px;
    float: right;
  }

  .ware-demo{
    display: inline-block;
    width: 240px;
    height: 290px;
    margin-left: 71.11px ;
    cursor: pointer;
    margin-bottom: 36px;
  }

  .ware-first{
    display: inline-block;
    width: 240px;
    height: 290px;
    cursor: pointer;
    margin-bottom: 36px;
  }


</style>