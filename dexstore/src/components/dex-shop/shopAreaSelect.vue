<template>
  <div>
      <el-cascader
          size="large"
          :options="options"
          v-model="selectedOptions"
          @change="handleChange"
          placeholder="请选择您所在的城市"
      >
      </el-cascader>
  </div>
</template>

<script>
import {  regionData,  CodeToText, } from "element-china-area-data";
export default {
  name: "shopAreaSelect",
  props:['selectedOptions'],
  data(){
    return {
      options: regionData,
      selectedOptions:[]
    }
  },
  methods:{
    handleChange() {
      let loc = "";
      for (let i = 0; i < this.selectedOptions.length; i++) {
        loc += CodeToText[this.selectedOptions[i]];
      }
      this.$emit("Area",loc)
    },
  }

}
</script>

<style scoped>

</style>