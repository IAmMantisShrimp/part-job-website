import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: JSON.parse(sessionStorage.getItem("userInfo")),
    pbList: {},
    activeIndex:'1'
  },
  getters: {
    getUser: state => {
      return state.userInfo
    },
    getIndex: state => {
      return state.activeIndex
    },
    getPbList: state => {
      return state.pbList
    }
  },
  mutations: {
    SET_USERINFO: (state, userInfo) => {
      state.userInfo = userInfo
      sessionStorage.setItem("userInfo", JSON.stringify(userInfo))
    },
    SET_PbList: (state, pbList) => {
      state.pbList = pbList
    },
    SET_Index: (state, activeIndex) => {
      state.activeIndex = activeIndex
    },
    REMOVE_INFO: (state) => {
      state.userInfo = {}
      sessionStorage.setItem("userInfo", JSON.stringify(''))
    },
    REMOVE_PbList: (state) => {
      state.pbList = {}
    },
    REMOVE_INDEX: (state) => {
      state.activeIndex = ''
    }
  },
  actions: {
  },
  modules: {
  }
})
