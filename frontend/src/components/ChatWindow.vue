<template>
  <div>
    <div>
      <p>{{name}}</p>
      <label>input your name</label>
      <input v-model="name">
      <button :disabled="isConnected" @click="connect()">connect</button>
      <button :disabled="!isConnected" @click="disconnect()">disconnect</button>
      <button @click="sendName()">send</button>
    </div>
    <div>{{msg}}</div>
  </div>
</template>

<script>
  var thisStomp = null

  export default {
    name: "ChatWindow",
    data() {
      return {
        isConnected: false,
        msg: 'origin',
        name: '123'
      }
    },
    created() {
    },
    methods: {
      connect: function () {
        var that = this
        var socket = new SockJS('http://127.0.0.1:8080/server')
        thisStomp = Stomp.over(socket)
        thisStomp.connect({
          'name': this.name
        },function (frame) {
          that.isConnected = true
          console.log('Connected:'+frame)
          thisStomp.subscribe('/topic/chat',function (response) {
            that.msg = JSON.parse(response.body).responseMessage
          })
          thisStomp.subscribe('/user/queue/messageController',function (response) {
            that.msg = JSON.parse(response.body).responseMessage
          })
          thisStomp.subscribe('/user/queue/friendController',function (response) {
            that.msg = JSON.parse(response.body).responseMessage
          })
        })
      },

      disconnect: function () {
        if (thisStomp != null) {
          thisStomp.disconnect()
          this.$store.state.stompClient = null
        }
        this.isConnected = false
        console.log('Disconnected')
      },

      sendName: function () {
        var myName = this.name
        console.log('name: '+this.name)
        thisStomp.send('/app/message',{},JSON.stringify({'name':myName}))
      }
    }
  }
</script>

<style scoped>

</style>
