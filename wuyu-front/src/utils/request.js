import axios from "axios";
import { MessageBox, Message } from "element-ui";
import store from "@/store";
import { getToken } from "@/utils/auth";
import { baseUrl as globalBaseUrl } from "@/api/baseapi";
import router from "@/router"; // 引入路由模块

// create an axios instance
const service = axios.create({
  //baseURL: "http://36.111.68.174:33380",
  // baseURL: 'http://49.51.69.99:9200',
  baseURL: 'http://localhost:9085',
  // baseURL: "http://49.51.69.99:33304",
  // baseURL: "http://36.111.68.174:33380",
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000, // request timeout
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
