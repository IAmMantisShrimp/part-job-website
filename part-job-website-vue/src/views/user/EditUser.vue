<template>
  <el-container class="login-content">
    <el-header>
      <Header></Header>
    </el-header>
    <el-main>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="昵称" prop="nickname">
          <el-input  v-model="ruleForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="ruleForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="ruleForm.email"></el-input>
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
            <i  class="el-icon-plus" ></i>
            <div slot="tip" class="el-upload__tip">只能上传不超过4MB的图片(可使用默认头像！)</div>
          </el-upload>
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
        nickname: '',
        enrollmentYear: '',
        phone:'',
        email:'',
        sex: '',
        avatar:''
      },
      uploadDisabled:false,
      //上传的文件信息列表
      fileList:[],
      filesUploadUrl: "http://localhost:8088/files/upload",
      rules: {
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
          console.log(this.ruleForm.enrollmentYear)
          var year = this.ruleForm.enrollmentYear.getFullYear()
          this.ruleForm.enrollmentYear = year
          this.$axios.post('/user/edit', this.ruleForm).then(res => {

            console.log(res)
            this.$message({
              message: '修改成功',
              type: 'success'
            });
            // 刷新vuex中存放的数据
            const userInfo = res.data.data
            this.$store.commit("SET_USERINFO", userInfo)
            this.$router.push("/index")
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
    var userInfo = this.$store.getters.getUser
    if(this.$store.getters.getUser.username) {
      this.ruleForm.userId = userInfo.userId
      this.ruleForm.nickname = userInfo.nickname
      this.ruleForm.phone = userInfo.phone
      this.ruleForm.email = userInfo.email
      this.ruleForm.sex = userInfo.sex
      var date = new Date();
      date.setFullYear(userInfo.enrollmentYear);
      this.ruleForm.enrollmentYear = date;

      //this.user.avatar = this.$store.getters.getUser.avatar
      this.hasLogin = true
    }
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