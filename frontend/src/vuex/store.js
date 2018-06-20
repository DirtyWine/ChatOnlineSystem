import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    name: '',
    stompClient: null,
    avatar:'',
    nickname:'',
  },
  mutations: {
    newName(state,msg) {
      state.name = msg
    },
    newAvatar(state,msg) {
      state.avatar = msg
    },
    newNickname(state,msg) {
      state.nickname = msg
    }
  }
})

export default store
