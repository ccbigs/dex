import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import axios from 'axios';
import qs from 'qs'
import echarts from 'echarts'


/*懒加载*/
import VueLazyload from 'vue-lazyload';
Vue.use(VueLazyload);
Vue.use(VueLazyload, {
  preLoad: 1.3,
  error: 'static/images/error.png',
  loading: 'static/images/load.gif',
  attempt: 1
})
Vue.config.productionTip = false
axios.defaults.withCredentials=true;
/*挂在axios到vue原型，由于继承性，所有组件都可以使用this.$http*/
Vue.prototype.axios=axios;
// 首先对拦截器的请求进行设置，并在方法中return config，此处为固定格式
axios.interceptors.request.use(config => {
  // 表示在配置中的设置头消息的字段Authorization为从本地获取的token值
  config.headers.Authorization = window.sessionStorage.getItem('token')
  //config.headers.Authorization = "12321sdsiadsaaduwqewq"
  return config
})
/*echarts的全局变量*/
Vue.prototype.$echarts = echarts
//定义全局变量
Vue.prototype.$qs = qs




/*全局守卫*/
router.beforeEach((to,from,next)=>{
  console.log("状态",store.state.login)
    if (to.matched.some(record => record.meta.auth)) {
      //用户未登录 需要跳转到登陆页面
      if (!store.state.login){
        next({
          path: '/login',
          query: {
            redirect: to.fullPath
          }
        })
      }else {
        next()
      }

  }else {
    next()
  }

})


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
