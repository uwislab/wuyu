//  使用模拟数据
// import "./mock"; 

import Vue from 'vue'

import Cookies from 'js-cookie'
// 分页组件
import Pagination from "@/components/Pagination";
// 通用函数工具
import { parseTime, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree } from "@/utils/ruoyi";
import plugins from './plugins' // plugins

import 'normalize.css/normalize.css' // a modern alternative to CSS resets


import Element from 'element-ui'
import './styles/element-variables.scss'
import enLang from 'element-ui/lib/locale/lang/zh-CN'// 如果使用中文语言包请默认支持，无需额外引入，请删除该依赖
import VueAMap from 'vue-amap'
import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'
import api from '@/api/cultivationModule/assessmentProcess'

import './icons' // icon
import './permission' // permission control
import './utils/error-log' // error log

import * as filters from './filters' // global filters
import htmlToPdf from '@/utils/htmlToPdf'  //导出

// 图表库引用

import '@icon-park/vue/styles/index.css';

import JsonExcel from 'vue-json-excel'
Vue.component('downloadExcel', JsonExcel)

import dayjs from 'dayjs'
Vue.prototype.dayjs = dayjs;
Vue.use(htmlToPdf) //导出PDF

import * as echarts from 'echarts'
Vue.prototype.$echarts = echarts

import re_htmlToPdf from '@/utils/re_htmlToPdf'
Vue.use(re_htmlToPdf)

const ElTreeGrid = require('element-tree-grid');
Vue.component(ElTreeGrid.name, ElTreeGrid);



Vue.use(VueAMap)
VueAMap.initAMapApiLoader({
  key: 'e1b4433618b2f4f6ab1df2bbea565f33',
  plugin: ['AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PlaceSearch', 'AMap.Geolocation', 'AMap.Geocoder'],
  v: '1.4.4',
  uiVersion: '1.0'

})
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */

// if (process.env.NODE_ENV === 'production') {
//   const { mockXHR } = require('../mock')
//   mockXHR()
// }

Vue.use(Element, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  locale: enLang // 如果使用中文，无需设置，请删除
})

Vue.component('Pagination', Pagination)
// 全局方法挂载
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.handleTree = handleTree
Vue.prototype.$api = api//挂载在vue原型上

Vue.use(plugins)

// register global utility filters
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
