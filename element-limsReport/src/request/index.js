import { fetch } from './fetch'

export default {
  install(Vue) {
    // 将fetch方法注入到VUE原型中供全局使用
    Vue.prototype.$fetch = fetch
  }
}
