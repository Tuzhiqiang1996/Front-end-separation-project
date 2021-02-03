import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: resolve => require(["@/view/Login.vue"], resolve)
    },
    {
      path: '/home',
      name: 'home',
      component: resolve => require(["@/page/index.vue"], resolve)
    },
  ]
})
