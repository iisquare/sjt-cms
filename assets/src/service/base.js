import axios from 'axios'

// @link:https://www.npmjs.com/package/axios
let $axios = axios.create({
  baseURL: process.env.apiURL
})

// axios.interceptors.response.use((response) => {
//   return Promise.resolve(response)
// }, (error) => {
//   return Promise.reject(error)
// })

export default {
  $axios,
  process () {

  }
}
