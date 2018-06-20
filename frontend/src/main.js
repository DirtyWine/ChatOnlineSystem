// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from './axios'
import VueAxios from 'vue-axios'
import Vuex from 'vuex'
import store from './vuex/store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import CollapseTransition from 'element-ui/lib/transitions/collapse-transition'


router.beforeEach((to,from,next) => {
  if(to.meta.requireAuth) {
    console.log(store.state.name)
    if(store.state.name) {
      next()
    } else {
      next({
        path: '/'
      })
    }
  } else {
    next()
  }
})

Vue.config.productionTip = false

Vue.use(VueAxios,axios)
Vue.use(Vuex)
Vue.use(ElementUI)


Vue.component(CollapseTransition.name, CollapseTransition)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  components: { App },
  template: '<App/>'
})
