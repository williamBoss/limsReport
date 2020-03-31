// import Vue from 'vue'
import axios from "axios";
import store from "../store";
import { getToken } from "@/common/auth";
import { Notification } from "element-ui";
import router from "../router";

const urlV0 = "http://127.0.0.1:8080/report-upload"; // 本地
const urlV1 = "http://47.100.221.10:8089/"; // 测试环境
let url = "";
if (process.env.NODE_ENV === "development") {
  url = urlV1;
} else if (process.env.NODE_ENV === "production") {
  url = "";
}
let baseURL = url;
// 配置 Content-Type
axios.defaults.headers.post["Content-Type"] = "aplication/json";
axios.defaults.baseURL = baseURL;
axios.defaults.timeout = 50000;
// const service = axios.create({
//   baseURL: baseURL, // url = base url + request url
//   timeout: 50000 // request timeout
// })

let pending = []; //声明一个数组用于存储每个ajax请求的取消函数和ajax标识
let cancelToken = axios.CancelToken;
let removePending = ever => {
  if (!ever.headers.needCancel) return;
  const newUrl = fmtUrl(ever.url);
  for (let p in pending) {
    if (pending[p].u === newUrl + "&" + ever.method) {
      //当当前请求在数组中存在时执行函数体
      pending[p].f(); //执行取消操作
      pending.splice(p, 1); //把这条记录从数组中移除
    }
  }
};

function fmtUrl(url) {
  return url.substr(0, url.lastIndexOf("/"));
}
/**
 * 配置 axios
 */
// http request 拦截器
axios.interceptors.request.use(
  config => {
    if (store.getters.token) {
      config.headers["X-Token"] = getToken();
    }
    // ------------------------------------------------------------------------------------
    removePending(config); //在一个ajax发送前执行一下取消操作
    config.cancelToken = new cancelToken(c => {
      // 这里的ajax标识我是用请求地址&请求方式拼接的字符串，当然你可以选择其他的一些方式
      if (config.headers.needCancel) {
        const newUrl = fmtUrl(config.url);
        pending.push({ u: newUrl + "&" + config.method, f: c });
      }
    });
    // -----------------------------------------------------------------------------------------
    return config;
  },
  err => {
    return Promise.reject(err);
  }
);

// http response 拦截器
axios.interceptors.response.use(
  response => {
    console.log("返回结果：response", response);
    // ------------------------------------------------------------------------------------------
    removePending(response.config); //在一个ajax响应后再执行一下取消操作，把已经完成的请求从pending中移除
    // -------------------------------------------------------------------------------------------
    const { code, message, result } = response.data;
    if (code === -100) {
      console.log("11");
      // 授权问题 -100
      Notification({
        title: message,
        message: result,
        type: "error",
        offset: 35,
        duration: 2000
      });
      store.dispatch("user/resetToken").then(() => {
        window.location.reload();
      });
    } else if (code === 500) {
      // 其他异常
      Notification({
        title: message,
        message: message,
        type: "error",
        offset: 35,
        duration: 2000
      });
    } else if (code == 401) {
      router.replace({
        path: "/login",
        query: {
          redirect: router.currentRoute.fullPath
        }
      });
      return Promise.reject(response); // 返回接口返回的错误信息
    } else {
      return response;
    }
  },
  error => {
    console.log(error);
    return Promise.reject(error);
  }
);

export default axios;
