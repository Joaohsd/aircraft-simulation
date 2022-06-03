import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import VueRouter from 'vue-router'
import Home from '@/pages/Home'
import ParameterRegister from '@/pages/ParameterRegister'
import ParameterDelete from '@/pages/ParameterDelete'
import ParameterUpdate from '@/pages/ParameterUpdate'
import TestConsultation from '@/pages/TestConsultation'
import Test from '@/pages/Test'

Vue.config.productionTip = false
Vue.use(VueRouter)

const routes = 
[
  {
    path:'/',
    component:Home,
    meta:{}
  },
  {
    path:'/Home',
    component:Home,
    meta:{}
  },
  {
    path:'/ParameterRegister',
    component:ParameterRegister,
    meta:{}
  },
  {
    path:'/ParameterDelete',
    component:ParameterDelete,
    meta:{}
  },
  {
    path:'/ParameterUpdate',
    component:ParameterUpdate,
    meta:{}
  },
  {
    path:'/Test',
    component:Test,
    meta:{}
  },
  {
    path:'/TestConsultation',
    component:TestConsultation,
    meta:{}
  }
];

const router = new VueRouter({
  routes,
  mode:'history'
});

new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount('#app')
