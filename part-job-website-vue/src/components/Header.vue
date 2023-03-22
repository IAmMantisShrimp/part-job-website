<template>
    <el-header class="me-area">
      <el-row class="me-header">

        <el-col :span="4" class="me-header-left">
          <el-menu style="margin-top: -15px" :router=true menu-trigger="click" active-text-color="#FAFAFA"
                   mode="horizontal">
            <el-menu-item index>
              <router-link to="/" class="me-title">
                <img src="../assets/logo.png" />
              </router-link>
            </el-menu-item>
          </el-menu>
        </el-col>

        <el-col  :span="8">
          <el-menu mode="horizontal" :default-active="this.$store.getters.getIndex" @select="handleSelect">
            <el-menu-item index="1"><i class="el-icon-s-home"></i><span class="index-content">首页</span></el-menu-item>
            <el-menu-item index="3"><i class="el-icon-message"></i>消息</el-menu-item>
            <el-menu-item index="2"><i class="el-icon-plus"></i>发布兼职</el-menu-item>
          </el-menu>
        </el-col>

        <el-col  :span="8">
          <el-menu :router=true menu-trigger="click" active-text-color="#FAFAFA"
                   mode="horizontal">
            <el-menu-item index>
              <el-input placeholder="搜索兼职" v-model="searchText" style="width: 300px">
                <el-button @click="searchPartJob" slot="append" icon="el-icon-search"></el-button>
              </el-input>
            </el-menu-item>
          </el-menu>
        </el-col>

        <el-col :span="4">
          <el-menu :router=true menu-trigger="click" mode="horizontal" >

            <template v-if="!hasLogin">
              <el-menu-item index="/login">
                <el-button type="text">登录</el-button>
              </el-menu-item>
              <el-menu-item index="/register">
                <el-button type="text">注册</el-button>
              </el-menu-item>
            </template>

            <template v-else>
              <el-submenu index>
                <template slot="title">
                  <el-avatar :size="45" :src="user.avatar"></el-avatar>
                  <span class="username-content">{{ user.nickname }}</span>
                </template>
                <el-menu-item  index="/collectList"><i class="el-icon-star-on"></i>我的收藏</el-menu-item>
                <el-menu-item  index="/editUser"><i class="el-icon-s-tools"></i>账户设置</el-menu-item>
                <el-menu-item  index="/editPass"><i class="el-icon-edit-outline"></i>修改密码</el-menu-item>
                <el-menu-item index @click="logout()"><i class="el-icon-circle-close"></i>退出登录</el-menu-item>
              </el-submenu>
            </template>
          </el-menu>
        </el-col>

      </el-row>
    </el-header>
</template>

<script>
import Element from "element-ui";

export default {
  name: "LoginHeader",
  data() {
    return {
      user:{
        nickname: "",
        avatar: ''
      },
      hasLogin: false,
      searchText:'',
    };
  },
  methods: {
    logout(){
      this.$axios.get("/user/logout").then(res => {
        this.$store.commit("REMOVE_INFO")
        this.$router.push("/login")
      })
    },
    searchPartJob(){
      if(this.searchText.trim()){
        console.log(this.searchText);
        this.$router.push("/search/"+this.searchText)
      }else{
        this.$message({
          message: "请输入内容！",
          duration: 1000,
          type: 'warning'
        });
      }
    },
    handleSelect(key, keyPath) {
      if(key == '1' && this.$route.path !== '/index'){
        this.$router.push("/index")
      } else if(key == '2' && this.$route.path !== '/add'){
        this.$router.push("/add")
      } else if(key == '3' && this.$route.path !== '/chat'){
        this.$router.push("/chat")
      }
    },
    initUserInfo() {
      console.log(this.$store.getters.getUser)
      if(this.$store.getters.getUser.nickname) {
        this.user.nickname = this.$store.getters.getUser.nickname
        this.user.avatar = this.$store.getters.getUser.avatar
        this.hasLogin = true
      }
    }
  },
  created() {
    this.initUserInfo()
  },
}
</script>

<style scoped>
  .user-content{
    text-align: right;
    margin-right: 50px;
  }
  .index-content{
    margin-top: 5px;
  }
  .img-content
  .me-title {
    margin-top: 10px;
    font-size: 24px;
  }

  .me-header-left {
    margin-top: 15px;
  }

  .me-title img {
    max-height: 2.4rem;
    max-width: 100%;
    margin-left: 100px;

  }

  .username-content{
    font-size: 20px;
    margin-left: 5px;
  }
  .me-area{
    border-radius: 1px;
  }
</style>