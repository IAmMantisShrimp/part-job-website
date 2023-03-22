import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/user/Login.vue'
import Register from '../views/user/Register.vue'
import PartJobList from '../views/partjob/PartJobList.vue'
import EditUser from '../views/user/EditUser.vue'
import EditPassword from '../views/user/EditPassword.vue'
import PartJobSearch from '../views/partjob/PartJobSearch.vue'
import PartJobAdd from '../views/partjob/PartJobAdd.vue'
import PortJobDetail from '../views/partjob/PortJobDetail.vue'
import CollectPartJobList from '../views/partjob/CollectPartJobList.vue'
import ChatCenter from '../views/chat/ChatCenter.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    redirect: {name: "Login"}
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/index',
    name: 'PartJobList',
    component: PartJobList
  },
  {
    path: '/editUser',
    name: 'EditUser',
    component: EditUser
  },
  {
    path: '/editPass',
    name: 'EditPassword',
    component: EditPassword
  },
  {
    path: '/search/:keyword',
    name: 'PartJobSearch',
    component: PartJobSearch
  },
  {
    path: '/add',
    name: 'PartJobAdd',
    component: PartJobAdd
  },
  {
    path: '/PortJobDetail/:partJobId',
    name: 'PortJobDetail',
    component: PortJobDetail
  },
  {
    path: '/edit/:partJobId',
    name: 'PartJobEdit',
    component: PartJobAdd
  },
  {
    path: '/collectList',
    name: 'CollectPartJobList',
    component: CollectPartJobList
  },
  {
    path: '/chat',
    name: 'ChatCenter',
    component: ChatCenter
  },
  {
    path: '/sendMsg/:toUserId',
    name: 'ChatCenter',
    component: ChatCenter
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
export default router
