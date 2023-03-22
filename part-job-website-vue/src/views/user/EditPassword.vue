<template>
  <el-container class="login-content">
    <el-header>
      <Header></Header>
    </el-header>
    <el-main>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="密码" prop="password">
          <el-input type = "password" v-model="ruleForm.password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword1">
          <el-input type = "password" v-model="ruleForm.newPassword1"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="newPassword2">
          <el-input type = "password" v-model="ruleForm.newPassword2"></el-input>
        </el-form-item>
        <el-form-item align="center">
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
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
import Header from "../../components/Header";

export default {
  name: "EditUser",
  components:{FOOTER,Header},
  data() {
    return {
      ruleForm: {
        userId:'',
        password: '',
        newPassword1:'',
        newPassword2:'',
      },
      rules: {
        password: [
          { required: true, message: '请输入旧密码', trigger: 'blur' },
          { min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur' }
        ],
        newPassword1: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur' }
        ],
        newPassword2: [
          { required: true, message: '请确认新密码', trigger: 'blur' },
          { min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur' }
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/user/editPassword', this.ruleForm).then(res => {
            console.log(res)
            this.$message({
              message: '修改成功',
              type: 'success'
            });
            this.$router.push("/index")
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
  },
  created() {
    var userInfo = this.$store.getters.getUser
    if(this.$store.getters.getUser.username) {
      this.ruleForm.userId = userInfo.userId
    }else {
      this.$router.push("/login")
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.ruleForm.newPassword1 = '';
      vm.ruleForm.newPassword2 = '';
      vm.ruleForm.password = '';
    });
  },
}
</script>

<style scoped>
.disabled >>> .el-upload--picture-card{
  display: none;
}
.el-header, .el-footer {
  text-align: center;
  line-height: 60px;
}
.demo-ruleForm {
  max-width: 500px;
  margin: 0 auto;
}
.el-main {
  min-height: calc(63vh);
  margin: 20px auto;
  width: 500px;
  /*background-color: #E9EEF3;*/
  color: #333;
  line-height: 260px;
}
</style>