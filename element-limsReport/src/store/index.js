import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex);

const key = 'user'
//创建一个store仓库
const store = new Vuex.Store({
  //state用来存放数据的对象
  state () {
    return {
      user: null
    }
    
  },
  //定义取数据
  getters: {
    getStorage: function (state) {
      if (!state.user) {
        state.user = JSON.parse(localStorage.getItem(key))
      }
      return state.user
    }
  },
  mutations: {
    $_setStorage (state, value) {
      state.user = value
      //localStorage.setItem('user',this.loginForm.username);
      localStorage.setItem(key, value)
    },
    $_removeStorage (state) {
      state.user = null
      localStorage.removeItem(key)
    }
  }
})
    
export default store;