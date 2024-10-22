import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state:sessionStorage.getItem('state') ? JSON.parse(sessionStorage.getItem('state')): {
    login: sessionStorage.getItem('login') || false,//是否登录
    userInfo:sessionStorage.getItem('userInfo'),//用户信息
    cartList:sessionStorage.getItem('cartList') || [],//加入购物车商品
    showCart:false,
    userAddress:sessionStorage.getItem('userAddress') || '',
    token:sessionStorage.getItem('token'),//商家的token
  },
  mutations: {
    updateLogin(state,Login){
      sessionStorage.setItem('login',Login),
      state.login = Login;
    },
    updateUserInfo(state,user){
      sessionStorage.setItem('userInfo',user)
      state.userInfo=user;
    },
    updateCartList(state,cartList){
      sessionStorage.setItem('cartList',cartList)
      state.cartList=cartList
    },
    updateUserAddress(state,userAddress){
      sessionStorage.setItem('userAddress',userAddress)
      state.userAddress=userAddress
    },
    updateToken(state,token){
      sessionStorage.setItem('token',token);
      state.token=token
    },
    clearAllMessage(state){
      state.login=false;
      state.userInfo=null;
      state.cartList=null;
      state.userAddress=null;
    }

  },
  actions: {
  },
  modules: {
  }
})
