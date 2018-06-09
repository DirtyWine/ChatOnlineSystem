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
  var stompClient = null

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
        stompClient = Stomp.over(socket)
        stompClient.connect({
          'name': this.name
        },function (frame) {
          that.isConnected = true
          console.log('Connected:'+frame)
          stompClient.subscribe('/topic/getResponse',function (response) {
            that.msg = JSON.parse(response.body).responseMessage
          })
          stompClient.subscribe('/user/queue/dot',function (response) {
            that.msg = JSON.parse(response.body).responseMessage
          })
        })
      },

      disconnect: function () {
        if (stompClient != null) {
          stompClient.disconnect()
        }
        this.isConnected = false
        console.log('Disconnected')
      },

      sendName: function () {
        var myName = this.name
        console.log('name: '+this.name)
        stompClient.send('/app/message',{},JSON.stringify({'name':myName}))
      }
    }
  }
</script>

<style scoped>

</style>
