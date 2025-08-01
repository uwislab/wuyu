/*
 * @Author: hezeliangfj
 * @Date: 2025-06-13 09:30:51
 * @LastEditors: hezeliangfj
 * @LastEditTime: 2025-06-19 19:50:17
 * @version: 0.0.1
 * @FilePath: \wuyu-front\src\utils\request.js
 * @Descripttion: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import axios from "axios";
import { MessageBox, Message } from "element-ui";
import store from "@/store";
import { getToken } from "@/utils/auth";
import { baseUrl as globalBaseUrl } from "@/api/baseapi";
import router from "@/router"; // 引入路由模块

// create an axios instance
const service = axios.create({
  // baseURL: "http://36.111.68.174:33380",
  // baseURL: 'http://49.51.69.99:9200',
  //baseURL: 'http://localhost:9200',
  // baseURL: 'http://localhost:9080',
  // baseURL: "http://49.51.69.99:33304",
  baseURL: "http://us.uwis.cn:9080",
  // baseURL: "http://localhost:9080",
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 30000, // request timeout
});

// request interceptor
service.interceptors.request.use(
  (config) => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      // config.headers['x-Token'] = getToken()
    }
    return config;
  },
  (error) => {
    console.log(error);
    return Promise.reject(error);
  }
);

// response interceptor
service.interceptors.response.use(
  (response) => {
    if (response.config.responseType === 'blob') {
      return response; // 保持响应结构完整
    }
    const res = response.data;
    return res;
  },
  (error) => {
    if (error.response) {
      const { status } = error.response;

      if (status === 401) {
        // 401 Unauthorized
        Message({
          message: "未授权，请重新登录",
          type: "error",
          duration: 5 * 1000,
        });

        // 重定向到登录页面
        router.push({ name: 'login' });

        // 清除 token
        store.dispatch('user/resetToken').then(() => {
          location.reload();
        });
      } else {
        Message({
          message: error.response.data.message || 'Error',
          type: "error",
          duration: 5 * 1000,
        });
      }
    } else {
      Message({
        message: error.message,
        type: "error",
        duration: 5 * 1000,
      });
    }

    return Promise.reject(error);
  }
);

export default service;
