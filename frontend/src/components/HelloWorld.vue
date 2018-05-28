<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <button @click="test()">test</button>
  </div>
</template>

<script>
  import axios from 'axios'
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  methods: {
    test: function () {
      var thisMsg = this
      axios.get('http://127.0.0.1:8080/customer/test?user=123')
        .then(function (response) {
          console.log(response.data)
          thisMsg.msg = response.data
        })
        .catch(function (error) {
          console.log(error)
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
