<template>
  <el-container>
    <el-header>
      <h1 class="login-title">校园兼职平台</h1>
    </el-header>
    <el-main>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账号" prop="username">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type = "password" v-model="ruleForm.password"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input  v-model="ruleForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="入学年份" prop="enrollmentYear">
          <el-date-picker v-model="ruleForm.enrollmentYear" type="year"></el-date-picker>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="ruleForm.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="头像" >
          <el-upload
              :action="filesUploadUrl"
              ref="upload"
              list-type="picture-card"
              :class="{ disabled:uploadDisabled }"
              :before-upload="beforeAvatarUpload"
              :file-list="fileList"
              :on-progress="onProgress"
              :on-success="imgSuccess"
              :on-error="imgError"
              :on-remove="imgRemove"
          >
            <i  class="el-icon-plus" :class="{ disabled:uploadDisabled }"></i>
            <div slot="tip" class="el-upload__tip">只能上传不超过4MB的图片(可使用默认头像！)</div>
          </el-upload>
        </el-form-item>
        <el-form-item align="center">
          <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
          <el-button @click="toLogin()">登录</el-button>
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
  name: "register",
  components:{FOOTER},
  data() {
    return {
      ruleForm: {
        username: '',
        password: '',
        nickname: '',
        enrollmentYear: '',
        sex: '',
        avatar: '',
      },
      uploadDisabled:false,
      //上传的文件信息列表
      fileList:[],
      filesUploadUrl: "http://localhost:8088/files/upload",
      rules: {
        username: [
          { required: true, message: '请输入帐号', trigger: 'blur' },
          { min: 5, max: 12, message: '长度在 5 到 12 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 2, max: 7, message: '长度在 2 到 7 个字符', trigger: 'blur' }
        ],
        resource: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if(this.ruleForm.enrollmentYear){
            var year = this.ruleForm.enrollmentYear.getFullYear()
            this.ruleForm.enrollmentYear = year
          }
          this.$axios.post('/user/register', this.ruleForm).then(res => {
            console.log(res)
            this.$message({
              message: '注册成功',
              type: 'success'
            });
            this.$router.push("/login")
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    toLogin() {
      this.$router.push("/login");
    },
    beforeAvatarUpload(file) {
      let isLt4M = file.size / 1024 / 1024 < 4;
      if (!isLt4M) {
        this.$message.error('上传头像图片大小不能超过 4MB!');
      }
      return isLt4M;
    },
    // 上传中
    onProgress(event, file, fileList){
      this.uploadDisabled = true;
    },
    // 图片上传成功
    imgSuccess(response, file, fileList) {
      this.uploadDisabled = true;
      this.ruleForm.avatar=response.data;//将返回的路径给表单的头像属性
      console.log("图片url为："+this.ruleForm.avatar);
      console.log("图片url为："+this.uploadDisabled);
      console.log("图片url为："+this.fileList);
    },
    // 图片上传失败
    imgError(err, file, fileList){
      this.$message.error("上传失败");
      this.uploadDisabled = false;
    },
    //移除图片
    imgRemove(file,fileList){
      this.uploadDisabled = false;
    },
  },
  created() {

  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (vm.$refs['upload']) {
        vm.$refs['upload'].clearFiles()  // 清除历史文件列表
        vm.uploadDisabled = false  // 清除历史文件列表
      }
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
    line-height: 260px;
  }
</style>