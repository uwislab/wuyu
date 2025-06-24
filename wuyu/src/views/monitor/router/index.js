import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Layout from "../Layout/Layout";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:"/Home2",
    children:[
        {
          path: '/Home2',
          name: 'Home2',
          component: ()=>import("../views/Home2")
    } ,
      {
        path: '/HomeView',
        name: 'HomeView',
        component: ()=>import("../views/HomeView")

      },
      {
        path: '/Home',
        name: 'Home',
        component: ()=>import("../views/Home")
      },
      {
        path: '/Home3',
        name: 'Home3',
        component: ()=>import("../views/Home3")
      },
      {
        path: '/Echarts',
        name: 'Echarts',
        component: ()=>import("../views/Echarts")
      },
      {
        path: '/Home4',
        name: 'Home4',
        component: ()=>import("../views/Home4")
      },
      {
        path: '/Home5',
        name: 'Home5',
        component: ()=>import("../views/Home5")
      }


    ]
  }

]

const router = new VueRouter({

  mode: 'history',
  base: process.env.BASE_URL,
  routes

})

export default router
