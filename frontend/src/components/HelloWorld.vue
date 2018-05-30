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
  </div>
</template>

<script>
  import axios from 'axios'
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
  methods: {
    test: function () {
      var thisMsg = this
      axios.get('http://127.0.0.1:8080/customer/login?name='+this.user.username+'&password='+this.user.password)
        .then(function (response) {
          console.log(response.data)
          thisMsg.msg = response.data
        })
        .catch(function (error) {
          console.log(error)
          thisMsg.msg = error.data
        })
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
