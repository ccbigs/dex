import Vue from 'vue'
import VueRouter from 'vue-router'
import login from "../views/login/login";
import index from '../views/index'
/*二级页面*/
import dataPanel from "../views/adminChildren/dataPanel";
import busmanManager from "../views/adminChildren/busmanManager";
import userManager from "../views/adminChildren/userManager";
import commentManager from "../views/adminChildren/commentManager";
import wareManager from "../views/adminChildren/wareManager";
import orderManager from "../views/adminChildren/orderManager";
import quotientMessage from "../views/adminChildren/quotientCarousel";
import quotientOrders from "../views/adminChildren/quotientOrders";
import quotientRecommend from "../views/adminChildren/quotientRecommend";
import quotientPrice from "../views/adminChildren/quotientPrice";
import categorieManager from "../views/adminChildren/categorieManager";
import pictureVerificationSetting from "../views/adminChildren/setting/pictureVerificationSetting";
import logSetting from "../views/adminChildren/setting/logSetting";
import bonusManager from "../views/adminChildren/bonusManager";
import bonusOrder from "../views/adminChildren/bonusOrder";
import publicInformation from "../views/adminChildren/SystemInformation/publicInformation"
import InformationManager from "../views/adminChildren/SystemInformation/InformationManager";
import InformationShow from "../views/adminChildren/SystemInformation/InformationShow";
import dexSearch from "../views/adminChildren/setting/dexSearch";
import goCloudAndKafka from "../views/adminChildren/setting/goCloudAndKafka";
import adminManager from "../views/adminChildren/setting/adminManager";
import dexFootMessage from "../views/adminChildren/setting/dexFootMessage";
import dexFootMessageShow from "../views/adminChildren/setting/dexFootMessageShow"
import dexSystemParams from "../views/adminChildren/setting/dexSystemParams"
import updateInformation from "../views/adminChildren/SystemInformation/updateInformation";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name:'login',
    component: login,
    redirect:'/login',
    children: [
      {
        path:"/login",
        component:login
      }
    ]
  },
  {
    path: '/adminManger',
    name: 'index',
    /*表示访问/adminManger路径以及他路径下所有的子路径都要开启限制*/
    meta:{
      auth:true
    },
    component: index,
    children:[
      {
        path:'/adminManger/dataPanel',
        name:'dataPanel',
        component:dataPanel
      },
      {
        path: '/adminManger/busmanManager',
        name:'busmanManager',
        component: busmanManager,
      },
      {
        path: '/adminManger/wareManager',
        name: 'wareManager',
        component: wareManager,
      },
      {
        path: '/adminManger/orderManager',
        name:'orderManager',
        component: orderManager,
      },
      {
        path: '/adminManger/userManager',
        name: 'userManager',
        component: userManager,
      },
      {
        path: '/adminManger/commentManager',
        name:'commentManager',
        component: commentManager
      },
      {
        path: '/adminManger/quotientMessage',
        name:'quotientMessage',
        component: quotientMessage,
      },
      {
        path: '/adminManger/quotientOrders',
        name:'quotientOrders',
        component: quotientOrders,
      },
      {
        path: '/adminManger/quotientRecommend',
        name: 'quotientRecommend',
        component: quotientRecommend,
      },
      {
        path: '/adminManger/quotientPrice',
        name: 'quotientPrice',
        component: quotientPrice,
      },
      {
        path: '/adminManger/categorieManager',
        name: 'categorieManager',
        component: categorieManager
      },
      {
        path: '/adminManger/setting/pictureVerificationSetting',
        name:'pictureVerificationSetting',
        component: pictureVerificationSetting
      },
      {
        path: '/adminManger/setting/logSetting',
        name:'logSetting',
        component: logSetting
      },
      {
        path: '/adminManger/bonusManager',
        name:'bonusManager',
        component: bonusManager
      },
      {
        path: '/adminManger/bonusOrder',
        name:'bonusOrder',
        component: bonusOrder
      },
      {
        path: '/adminManger/publicInformation',
        name:'publicInformation',
        component: publicInformation
      },
      {
        path: '/adminManger/InformationManager',
        name:'InformationManager',
        component: InformationManager
      },
      {
        path: '/adminManger/dexSearch',
        name:'dexSearch',
        component: dexSearch
      },
      {
        path: '/adminManger/goCloudAndKafka',
        name:'goCloudAndKafka',
        component: goCloudAndKafka
      },
      {
        path: '/adminManger/adminManager',
        name:'adminManager',
        component: adminManager
      },
      {
        path: '/adminManger/dexFootMessage',
        name:'dexFootMessage',
        component: dexFootMessage
      },
      {
        path: '/adminManger/dexFootMessageShow',
        name:'dexFootMessageShow',
        component: dexFootMessageShow
      },
      {
        path: '/adminManger/dexSystemParams',
        name:'dexSystemParams',
        component: dexSystemParams
      },
      {
        path: '/adminManger/updateInformation',
        name:'updateInformation',
        component: updateInformation
      },
    ]
  },
  {
    path: '/adminManger/InformationShow',
    name:'InformationShow',
    component: InformationShow
  },
]

const router = new VueRouter({
  mode:'history',
  routes
})

export default router
