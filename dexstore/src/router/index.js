import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '../views/Index'
import Login from '../views/Login/index'
import Home from '../views/Home'
import Ware from '../views/Ware'
import WareDetail from '../views/WareDetail'
import ShopCart from '../views/ShopCart'
import User from '../views/User/index'
/*第二级路由页面*/
import msgCenter from '../views/User/userChildren/messageCenter'
import activeCenter from '../views/User/userChildren/messageActive'
import orAll from '../views/User/userChildren/orderAll'
import orDid from '../views/User/userChildren/orderDid'
import orReturn from '../views/User/userChildren/orderReturn'
import orTransit from '../views/User/userChildren/orderTransit'
import addressUser from "../views/User/userChildren/addressUser"
import addressAdd from "../views/User/userChildren/addressAdd"
import commentUser from "../views/User/userChildren/commentUser"
import commentPublic from "../views/User/userChildren/commentPublic"
import dataUser from "../views/User/userChildren/dataUser"
import dataModify from "../views/User/userChildren/dataModify"
import userRegister from "../views/Login/userRegister";
/*秒杀路由*/
import dexSeckill from "../views/Bonus/index"
import bonusDate from "../views/User/userChildren/bonusDate";
/*公告信息*/
import InformationShow from "../views/User/userChildren/InformationShow";
import InformationDetail from "../views/User/userChildren/InformationDetail";
/*用户的数据面板*/
import dataPanle from "../views/User/userChildren/dataPanle";
/*dex商城公共面板*/
import shopPublicInfo from "../common/dex-shop/shopPublicInfo"


const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path:'/',
    redirect:'/home',
    name:'home',
    component:Index,
    /*对应import的Index*/
    children:[
      {
        path:"home",
        component:Home
      }
    ]
  },
  {
    path:'/login',
    name:'login',
    component: Login
  },
  {
    path: '/userRegister',
    name: 'userRegister',
    component: userRegister
  },
  {
    path: '/wares',
    name: 'wares',
    component: Ware
  },{
    path: '/wareDetail',
    name: 'wareDetail',
    component: WareDetail,
  },{
    path: '/myCart',
    name:'myCart',
    component: ShopCart,
  },{
    path: '/dexSeckill/:bonus_id',
    name:'dexSeckill',
    component: dexSeckill,
  },{
    path: '/shopPublicInfo',
    name:'shopPublicInfo',
    component: shopPublicInfo,
  },{
    path: '/user',
    name: 'user',
    component: User,
    redirect:'/user/InformationShow',
    /*表示访问/user路径以及他路径下所有的子路径都要开启限制*/
    meta:{
      auth:true
    },
    children: [
      {
        path: '/user/msgCenter',
        name:'msgCenter',
        component: msgCenter
      },
      {
        path: '/user/activeCenter',
        name:'activeCenter',
        component: activeCenter
      },
      {
        path: '/user/orAll',
        name:'orAll',
        component: orAll,
      },
      {
        path: '/user/orDid',
        name:'orDid',
        component: orDid,
      },
      {
        path: '/user/orReturn',
        name:'orReturn',
        component: orReturn,
      },
      {
        path: '/user/orTransit',
        name:'orTransit',
        component: orTransit,
      },
      {
        path: '/user/addressAdd',
        name:'addressAdd',
        component: addressAdd
      },
      {
        path: '/user/addressUser',
        name:'addressUser',
        component: addressUser
      },
      {
        path: '/user/commentUser',
        name:'commentUser',
        component: commentUser
      },
      {
        path: '/user/commentPublic',
        name:'commentPublic',
        component: commentPublic
      },
      {
        path: '/user/dataUser',
        name:'dataUser',
        component: dataUser
      },
      {
        path: '/user/dataModify',
        name:'dataModify',
        component: dataModify
      },
      {
        path: '/user/bonusDate',
        name:'bonusDate',
        component: bonusDate
      },
      {
        path: '/user/InformationShow',
        name:'InformationShow',
        component: InformationShow
      },
      {
        path: '/user/InformationDetail',
        name:'InformationDetail',
        component: InformationDetail
      },
      {
        path: '/user/dataPanle',
        name:'dataPanle',
        component: dataPanle
      },
    ]
  }

]



const router = new VueRouter({
  mode:'history',
  routes
})

export default router
