<template>
  <el-container class="login-content">
    <el-header>
      <h1 class="login-title">校园兼职平台</h1>
    </el-header>
    <el-main>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账号" prop="username" >
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item  label="密码" prop="password">
          <el-input type="password" v-model="ruleForm.password"></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="code" style="text-align: left">
          <el-input type="text" @keydown.enter.native="submitForm('ruleForm')" v-model="ruleForm.code" auto-complete="off" style="width:240px;"></el-input>
          <img :src="verifyCode" title="点击切换验证码" @click="changeVerifyCode" style="margin-left: 20px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
          <el-button @click="toRegister">注册</el-button>
        </el-form-item>
      </el-form>
    </el-main>
    <el-footer>
      <FOOTER></FOOTER>
    </el-footer>
  </el-container>
</template>

<script>
import FOOTER from "../../components/Footer";

export default {
  name: "login",
  components:{FOOTER},
  data() {
    return {
      ruleForm: {
        username: '',
        password: '',
        code:''
      },
      verifyCode: 'http://localhost:8088/user/verifyCode',
      // verifyCode: '/user/verifyCode',
      rules: {
        username: [
          { required: true, message: '请输入帐号', trigger: 'blur' },
          { min: 5, max: 12, message: '长度在 5 到 12 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur' }
        ],
        /*code: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
        ]*/
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/user/login', this.ruleForm).then(res => {

            console.log(res.data)
            const userInfo = res.data.data

            // 把数据存放到vuex的state中
            this.$store.commit("SET_USERINFO", userInfo)

            this.$router.push("/index")
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    toRegister() {
      this.$router.push("/register");
    },
    changeVerifyCode(){
      this.verifyCode="http://localhost:8088/user/verifyCode?time="+new Date();
    },
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  text-align: center;
  line-height: 60px;
}
.login-title{
  font-size:30px;
  margin: 0 auto;
}
.el-header {
  background-color: #E6E6E6;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.demo-ruleForm {
  max-width: 500px;
  margin: 0 auto;
}
.el-main {
  margin: 20px auto;
  width: 500px;
  /*background-color: #E9EEF3;*/
  color: #333;
  text-align: center;
  line-height: 260px;
  height: 470px;
}
</style>