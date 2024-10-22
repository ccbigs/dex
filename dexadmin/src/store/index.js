import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state:sessionStorage.getItem('state') ? JSON.parse(sessionStorage.getItem('state')): {
    login: sessionStorage.getItem('login') || false,//是否登录
    adminInfo:sessionStorage.getItem('adminInfo'),//用户信息
    token:sessionStorage.getItem('token'),//管理员的token
  },
  mutations: {
    updateLogin(state,Login){
      sessionStorage.setItem('login',Login);
      state.login = Login;
    },
    updateAdminInfo(state,admin){
      sessionStorage.setItem('adminInfo',admin);
      state.adminInfo=admin
    },
    updateToken(state,token){
      sessionStorage.setItem('token',token);
      state.token=token
    }

  },
  actions: {
  },
  modules: {
  }
})
