import Vue from 'vue'
import VueRouter from 'vue-router'
/*这里导入商家*/
import Index from '../views/busman/index'
import busMain from '../views/busman/busMain'
import login from "../views/busman/LoginRegister/login";
import register from "../views/busman/LoginRegister/register";
/*商家二级页面*/
import wareSale from "../views/busman/busChildren/wareSale";
import wareAdd from "../views/busman/busChildren/wareAdd";
import activeAdd from "../views/busman/busChildren/activeAdd";
import activeDid from "../views/busman/busChildren/activeDid";
import hpCarouselDid from "../views/busman/busChildren/hpCarouselDid";
import hpRecommendDid from "../views/busman/busChildren/hpRecommendDid";
import hpPurchase from "../views/busman/busChildren/hpPurchase";
import orderDid from "../views/busman/busChildren/orderDId";
import orderSearch from "../views/busman/busChildren/orderSearch";
import bonusAdd from "../views/busman/busChildren/bonusAdd";
import bonusDid from "../views/busman/busChildren/bonusDid";
import bonusOrder from "../views/busman/busChildren/bonusOrder";
import InformationShow from "../views/busman/busChildren/InformationShow";
import InformationDetail from "../views/busman/busChildren/InformationDetail";
import stanardManager from "../views/busman/busChildren/stanardManager";
import busmanData from "../views/busman/busChildren/busmanData"
import busmanPassword from "../views/busman/busChildren/busmanPassword";


Vue.use(VueRouter)

const routes = [
  {
    path:'/',
    redirect:'/login',
    name:'login',
    component:login,
    children: [
      {
        path:"login",
        component:login
      }
    ]
  },
  {
    path: '/busRegister',
    name: 'register',
    component: register
  },
  {
    path: '/busIndex',
    name: 'busIndex',
    /*表示访问/busIndex路径以及他路径下所有的子路径都要开启限制*/
    meta:{
      auth:true
    },
    component: Index,
  },{
    path: '/busMain',
    name:'busMain',
    /*表示访问/busMain路径以及他路径下所有的子路径都要开启限制*/
    meta:{
      auth:true
    },
    component: busMain,
    redirect: '/busMain/wareSale',
    children:[
      {
        path:'/busMain/wareSale',
        name:'wareSale',
        component:wareSale,
      },
      {
        path:'/busMain/wareAdd',
        name:'wareAdd',
        component: wareAdd,
      },
      {
        path: '/busMain/activeAdd',
        name: 'activeAdd',
        component: activeAdd
      },{
        path: '/busMain/activeDid',
        name: 'activeDid',
        component: activeDid
      },{
        path: '/busMain/hpCarouselDid',
        name: 'hpCarouselDid',
        component: hpCarouselDid
      },{
        path: '/busMain/hpRecommendDid',
        name: 'hpRecommendDid',
        component: hpRecommendDid
      },{
        path: '/busMain/hpPurchase',
        name: 'hpPurchase',
        component: hpPurchase
      },{
        path: '/busMain/orderDid',
        name: 'orderDid',
        component: orderDid
      },{
        path: '/busMain/orderSearch',
        name: 'orderSearch',
        component: orderSearch
      },{
        path: '/busMain/bonusAdd',
        name: 'bonusAdd',
        component: bonusAdd
      },{
        path: '/busMain/bonusDid',
        name: 'bonusDid',
        component: bonusDid
      },{
        path: '/busMain/bonusOrder',
        name: 'bonusOrder',
        component: bonusOrder
      },{
        path: '/busMain/InformationShow',
        name: 'InformationShow',
        component: InformationShow
      },{
        path: '/busMain/InformationDetail',
        name: 'InformationDetail',
        component: InformationDetail
      },{
        path: '/busMain/stanardManager',
        name: 'stanardManager',
        component: stanardManager
      },{
        path: '/busMain/busmanData',
        name: 'busmanData',
        component: busmanData
      },{
        path: '/busMain/busmanPassword',
        name: 'busmanPassword',
        component: busmanPassword
      },
    ]
  }

]

const router = new VueRouter({
  mode:'history',
  routes
})

export default router
