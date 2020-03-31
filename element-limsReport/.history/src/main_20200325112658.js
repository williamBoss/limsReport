// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";
import request from "./request";

import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
Vue.use(ElementUI);

import Pagination from "@/components/Pagination";
// 全局组件挂载
Vue.component("Pagination", Pagination);

import store from "./store/index"; //仓库
import axios from "axios";
Vue.prototype.$axios = axios;
Vue.config.productionTip = false;

Vue.use(request);

router.beforeEach((to, from, next) => {
  if (to.meta.loginRequest == true) {
    if (localStorage.getItem("user")) {
      next();
    } else {
      next({
        path: "/login",
        query: {
          redirect: to.fullPath
        }
      });
    }
  } else {
    next();
  }
});

/* eslint-disable no-new */
new Vue({
  el: "#app",
  store,
  router,
  render: h => h(App)
});
