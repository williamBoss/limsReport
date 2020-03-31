import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/pages/login/index'
import Home from '@/pages/home/index'


Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/login",
      name: "Login",
      component: Login
    },
    {
      path: "/",
      meta: { loginRequest: true },
      redirect: "/home"
    },
    {
      path: "/home",
      name: "Home",
      component: Home,
      meta: { loginRequest: true },
      redirect: "/reportsearch",
      children: [
        {
          path: "reportsearch",
          component: () => import("@/components/Reportsearch"),
          name: "reportSearch",
          meta: { loginRequest: true }
        },
        {
          path: "upload",
          component: () => import("@/components/Upload"),
          name: "upload",
          meta: { loginRequest: true }
        },
        {
          path: "auditreport",
          component: () => import("@/components/Auditreport"),
          name: "auditreport",
          meta: { loginRequest: true }
        },
        {
          path: "download",
          component: () => import("@/components/Download"),
          name: "download",
          meta: { loginRequest: true }
        }
      ]
    }
  ]
});




// /**
//  * 重写路由的push方法
//  */
// const routerPush = Router.prototype.push
// Router.prototype.push = function push(location) {
//   return routerPush.call(this, location).catch(error=> error)
// }
