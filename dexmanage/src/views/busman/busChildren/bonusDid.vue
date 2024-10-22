<template>
  <div>
    <div class="ware-mode" v-for="(ware,i) in DidWare">
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
            <div class="font-demo" style="margin-bottom: 10px">
              <el-button size="mini" type="warning" icon="el-icon-receiving" @click="UpdateBonus(ware)">分红价格:{{ware.ware_bonusmoney}}(元)</el-button>
              <el-button size="mini" type="danger"  @click="outBonus(ware)">退出</el-button>
            </div>

          </el-card>
        </el-col>
      </el-row>
    </div>

    <el-dialog title="参与满减" v-model="dialogBonusVisible" :visible.sync="dialogBonusVisible">
      <div style="margin-left: 20px;margin-bottom: 20px">
        <div>商品名称  :  {{this.ChangeWare.ware_name}}</div>
      </div>
      <el-divider></el-divider>
      <el-form :model="ChangeWare" :rules="BonusRule" ref="ChangeWare" label-width="180px" class="demo-ruleForm">
        <el-form-item label="网红分红(元)" prop="ware_bonusmoney">
          <el-input-number v-model="ChangeWare.ware_bonusmoney" :precision="2" :step="0.1" :max="this.ChangeWare.ware_price"></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ChangeWare')">立即参加</el-button>
          <el-button @click="resetForm('ChangeWare')">重置</el-button>
        </el-form-item>
      </el-form>
      <div style="display: flex;justify-content: left;">
        <div>*注:商品价格不能低于{{this.ChangeWare.ware_price}}</div>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "bonusDid",
  data(){
    return{
      DidWare:'',
      dialogBonusVisible:false,
      ChangeWare:'',

      BonusRule: {
        ware_bonusmoney: [
          { required: true, message: '请输入参与直播的价格', trigger: 'blur' },
        ],
      },
    }
  },
  created() {
    this.getAttendBonusWare();
  },
  methods:{
    async getAttendBonusWare(){
      const res=await this.axios.post(`bonus/getAttendBonusWare`,this.$qs.stringify({
        busman_id:this.$store.state.busmanInfo.id
      }))

      if (res.data.code === 200){
        this.DidWare=res.data.object
      }else {
        this.$message.warning("当前未有参与直播带货的商品")
      }

    },

    UpdateBonus(ware){
      console.log("商品名",ware);
      this.ChangeWare=ware;
      this.dialogBonusVisible=true;

    },

    /*---------------------------修改提交的直播带货的商品信息-----------------------------*/
    /*提交修改信息*/
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('bonus/UpdateAttendBonusMessage',this.$qs.stringify(
              this.ChangeWare
          )).then((res)=>{
            if (res.data.code === 200){
              this.$message.success("商品参加成功")
              this.dialogBonusVisible=false;
              this.getNoAttendBonusWare()
            }else {
              this.$message.error("修改商品打折信息失败")
            }
          })
        }
      });
    },
    /*重置表单信息*/
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    /*商家退出活动*/
    async outBonus(ware){
      const res=await this.axios.post(`bonus/wareOutBonus`,this.$qs.stringify({
        ware_id:ware.id,
        busman_id:this.$store.state.busmanInfo.id
      }))

      if (res.data.code === 200){
        this.$message.success("商品退出直播带货活动成功")
        await this.getAttendBonusWare()
      }else {
        this.$message.error("商品退出直播带货活动失败")
      }
    }

  }
}
</script>

<style scoped>
.ware-mode{
  width: 260px;
  height: 380px;
  display: inline-block;
  margin-right: 10px;
  margin-left: 10px;
}
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
.active-attend{
  padding: 8px 6px 10px 8px;
}


.PPrice{
  color: red;
  font-weight: bold;
}
</style>