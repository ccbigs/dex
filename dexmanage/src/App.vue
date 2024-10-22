<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'app',
  mounted() {
    window.addEventListener('unload', this.saveState)
  },
  methods: {
    saveState() {
      sessionStorage.setItem('state', JSON.stringify(this.$store.state))
    }
  }
}
</script>

<style>
body{
  padding: 0px;
  margin: 0;
}
a{
  text-decoration: none;
  color: white;
}

a:hover{
  color: red;
}
</style>
