import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import { param } from '@/utils/index'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000, // request timeout
  transformRequest: [function (data , headers) {
    //获取内容请求类型
    let contentType = headers['Content-Type']
    //body 转换
    if( contentType && contentType.toUpperCase().includes('JSON')){
        return JSON.stringify(data);
    }
    // 将数据转换为表单数据
    return param(data)
  }],
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  }
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent
    // config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['X-Token'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    // debugger
    // if the custom code is not 20000, it is judged as an error.
    if (res.code !== 200) {
      Message({
        message: res.message || '请求接口异常',
        type: 'error',
        duration: 5 * 1000
      })

      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (res.code ===  401 ) {
        // to re-login
        MessageBox.confirm('登录已过期，请重新登录', '退出登录', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.message || '请求接口异常'))
    } else {
      return res
    }
  },
  error => {
    // debugger
    // let status = error.response.status;
    let data = error.response.data;
    console.log(error) // for debug
    // console.log('err' + error) // for debug
    Message({
      message: data.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
