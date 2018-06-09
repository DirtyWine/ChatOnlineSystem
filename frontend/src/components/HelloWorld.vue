<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <label>用户名</label>
    <input v-model="user.username"/>
    <br>
    <label>密码</label>
    <input v-model="user.password"/>
    <br>
    <button @click="test()">test</button>
    <br>
    <button @click="goChat()">goChat</button>
    <h1>{{author}}</h1>
  </div>
</template>

<script>

export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      user: {
        username:'',
        password:''
      }

    }
  },
  computed: {
    author () {
      return this.$store.state.user
    }
  },
  methods: {
    test: function () {
      var thisMsg = this
      this.axios.get('/customer/login?name='+this.user.username+'&password='+this.user.password)
        .then(function (response) {
          console.log(response.data)
          thisMsg.msg = response.data
        })
        .catch(function (error) {
          console.log(error)
          thisMsg.msg = error.data
        })
    },
    goChat: function () {
      this.$router.push({name:'ChatWindow'})
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
