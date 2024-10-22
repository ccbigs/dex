<template>
  <div>
    <!--标题-->
    <div>
      <h2>{{title}}</h2>
    </div>
    <!--显示内容部分-->
    <div>
      <slot name="content"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "shopDemo",
  props:['title']
}
</script>

<style scoped>

</style>