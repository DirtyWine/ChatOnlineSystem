import axios from 'axios'

axios.defaults.timeout = 5000
axios.defaults.baseURL = 'http://127.0.0.1:8080'

export default axios
