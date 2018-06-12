import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import ChatWindow from '@/components/ChatWindow'
import Login from '@/components/Login'
import Register from '@/components/Register'
import ChatTest from '@/components/ChatTest'



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
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/test',
      name: 'test',
      component: ChatTest
    }
  ]
})

