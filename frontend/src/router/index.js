import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import ChatWindow from '@/components/ChatWindow'



Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld,

    },
    {
      path: '/chat',
      name: 'ChatWindow',
      meta: {
        requireAuth: true,
      },
      component: ChatWindow
    }
  ]
})

