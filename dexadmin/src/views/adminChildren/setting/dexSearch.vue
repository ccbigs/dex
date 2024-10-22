<template>
  <div>
    <div class="es-demo">
      <div class="es-title">
        <div>DEX搜索引擎配置</div>
      </div>
      <el-divider content-position="left">扼要参数</el-divider>
      <!--   商品索引节点   -->
      <el-card style="margin-bottom: 10px">
        <el-descriptions class="margin-top" title="商品索引节点" :column="3" size="mini" border>
          <template slot="extra">
            <el-button type="primary" size="small" @click="asyncWareData()">同步数据</el-button>
          </template>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              节点名
            </template>
            {{ ware.index }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              节点是否启用
            </template>
            <el-tag type="success" v-if="ware.status === 'open'">正在使用</el-tag>
            <el-tag type="danger" v-else>已停用</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              节点状态
            </template>
            {{ ware.health }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              索引长度
            </template>
            <el-tag size="small">{{ ware.count }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              索引uuid
            </template>
            {{ ware.uuid }}
          </el-descriptions-item>
        </el-descriptions>
      </el-card>

      <!--   普通订单索引节点   -->
      <el-card style="margin-bottom: 10px">
        <el-descriptions class="margin-top" title="普通订单索引节点" :column="3" size="mini" border>
          <template slot="extra">
            <el-button type="primary" size="small" @click="asyncOrdersData()">同步数据</el-button>
          </template>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              节点名
            </template>
            {{ orders.index }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              节点是否启用
            </template>
            <el-tag type="success" v-if="orders.status === 'open'">正在使用</el-tag>
            <el-tag type="danger" v-else>已停用</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              节点状态
            </template>
            {{ orders.health }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              索引长度
            </template>
            <el-tag size="small">{{ orders.count }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              索引uuid
            </template>
            {{ orders.uuid }}
          </el-descriptions-item>
        </el-descriptions>
      </el-card>


      <!--  秒杀订单索引节点    -->
      <el-card>
        <el-descriptions class="margin-top" title="秒杀订单索引节点" :column="3" size="mini" border>
          <template slot="extra">
            <el-button type="primary" size="small" @click="asyncBonusOrderData">同步数据</el-button>
          </template>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              节点名
            </template>
            {{ bonusOrder.index }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              节点是否启用
            </template>
            <el-tag type="success" v-if="bonusOrder.status === 'open'">正在使用</el-tag>
            <el-tag type="danger" v-else>已停用</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              节点状态
            </template>
            {{ bonusOrder.health }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              索引长度
            </template>
            <el-tag size="small">{{ bonusOrder.count }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              索引uuid
            </template>
            {{ bonusOrder.uuid }}
          </el-descriptions-item>
        </el-descriptions>
      </el-card>

    </div>

    <admin-foot style="position: absolute;bottom: 0px;"></admin-foot>
  </div>
</template>

<script>
import adminFoot from "../../../components/adminFoot";

export default {
  name: "dexSearch",
  components:{
    'adminFoot':adminFoot,
  },
  data(){
    return{
      ware:'',
      orders:'',
      bonusOrder:''
    }
  },
  created() {
    this.getESNodeMessage()
  },
  methods:{
      /*获取es的全部节点信息*/
    async getESNodeMessage(){
      const res =await this.axios.post('DexAdmin/getNodeMessage')
      console.log(res.data.object);
      if (res.data.code === 200){

        this.ware = res.data.object[0]
        this.orders = res.data.object[1]
        this.bonusOrder = res.data.object[2]
      }
      console.log(res);
      console.log(this.ware);
    },
    /*同步ware的数据*/
    async asyncWareData(){
      const res = await this.axios.post('DexAdmin/adminSynchronizationWareData',this.$qs.stringify({
        account:this.$store.state.adminInfo.account
      }))

      if (res.data.code === 200){
        this.$message.success("ElasticSearch同步数据成功")
        await this.getESNodeMessage();
      }

    },
    /*同步bonusorder的数据*/
    async asyncBonusOrderData(){
      const res = await this.axios.post('DexAdmin/adminSynchronizationBonusOrderData',this.$qs.stringify({
        account:this.$store.state.adminInfo.account
      }))

      if (res.data.code === 200){
        this.$message.success("ElasticSearch同步数据成功")
        await this.getESNodeMessage();
      }

    },
    /*同步orders的数据*/
    async asyncOrdersData(){
      const res = await this.axios.post('DexAdmin/adminSynchronizationOrderData',this.$qs.stringify({
        account:this.$store.state.adminInfo.account
      }))

      if (res.data.code === 200){
        this.$message.success("ElasticSearch同步数据成功")
        await this.getESNodeMessage();
      }

    },
  }
}
</script>

<style scoped>
  .es-demo{
    margin: 10px auto;
    width: 1536.670px;
    padding: 30px 20px 20px 20px;
    height: 620px;
    background-color: white;
  }


  .es-title{
    height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .es-title div{
    font-size: 40px;
    font-weight: bold;
  }
</style>