<template>
  <div>
  <div>请选择是{{pvName}}的图片</div>
  <div class="pv-mode">
    <el-row>
      <el-col :span="8" v-for="(o, index) in pvImage" :key="o.id">
        <el-card :body-style="{ padding: '0px' }" class="demo">
          <img :src="o.img_url" alt="" @click="chooseImage(index)" :class="[o.img_type===1?'choose':'']">
        </el-card>
      </el-col>
    </el-row>
  </div>
  </div>
</template>

<script>
export default {
  name: "pictureVerification",
  props:['pvName','pvImage'],
  methods:{
    chooseImage(index){
      if (this.pvImage[index].img_type === 1){
        this.pvImage[index].img_type=2
      }else {
        this.pvImage[index].img_type=1
      }
    }
  }
}
</script>

<style scoped>
  .pv-mode{

  }
  img{
    width: 100px;
    height: 100px;
    cursor: pointer;
  }
  .demo{
    width: 100px;
    height: 100px;
    margin-top: 20px;
    margin-left: 20px;
  }

  .choose{
    opacity: 0.3;
  }

</style>