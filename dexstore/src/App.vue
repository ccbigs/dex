<template>
  <div id="app">
    <router-view></router-view>
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
    margin: 0px;
    background-color: #F5F5F5;
  }

  a{
    text-decoration: none;
    color: #1a1a1a;
  }
  a:hover{
    color: #6b95ea;
    cursor: pointer;
  }

  ul{
    list-style: none;
  }
</style>
