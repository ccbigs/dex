import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state:sessionStorage.getItem('state') ? JSON.parse(sessionStorage.getItem('state')): {
    login: sessionStorage.getItem('login') || false,//是否登录
    busmanInfo:sessionStorage.getItem('busmanInfo'),//用户信息
    token:sessionStorage.getItem('token'),//商家的token
  },
  mutations: {
    updateLogin(state,Login){
      sessionStorage.setItem('login',Login);
      state.login = Login;
    },
    updateBusmanInfo(state,busman){
      sessionStorage.setItem('busmanInfo',busman);
      state.busmanInfo=busman
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
