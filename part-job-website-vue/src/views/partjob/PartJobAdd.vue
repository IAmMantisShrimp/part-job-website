<template>
  <el-container class="login-content">
    <el-header>
      <Header></Header>
    </el-header>
    <el-main style="width: 1000px;margin:0 auto">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="兼职标题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>
        <el-form-item label="兼职分类" prop="categoryId">
          <el-select v-model="ruleForm.categoryId" placeholder="请选择兼职分类">
            <el-option :label="category.categoryName" :value="category.categoryId" v-for="category in partJobCategorys"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="校内外" prop="isInsideSchool">
          <el-radio-group v-model="ruleForm.isInsideSchool">
            <el-radio label="校内"></el-radio>
            <el-radio label="校外"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="薪酬类型" prop="salaryCategory">
          <el-radio-group v-model="ruleForm.salaryCategory" @change="changSalaryCategory">
            <el-radio label="日结"></el-radio>
            <el-radio label="月结"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="薪酬" prop="salary">
          <el-input v-model="ruleForm.salary" style="width: 225px" @keyup.native="limitSalaryNum"></el-input>
          <span style="font-size: 20px;margin-left: 8px">{{salaryCategoryContent}}</span>
        </el-form-item>
        <el-form-item label="详情" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"  :rows="5" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
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
  name: "PartJobList",
  components:{FOOTER,Header},
  data() {
    return {
      partJobCategorys: {},
      salaryCategoryContent: "/月",
      ruleForm: {
        title: '',
        description: '',
        categoryId: '',
        salary: '',
        salaryCategory: '',
        isInsideSchool: '',
        userId: '',
      },
      rules: {
        title: [
          { required: true, message: '请输入兼职标题', trigger: 'blur' },
          { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: '请选择兼职分类', trigger: 'change' }
        ],
        isInsideSchool: [
          { required: true, message: '请选择校内外', trigger: 'change' }
        ],
        salaryCategory: [
          { required: true, message: '请选择薪酬类型', trigger: 'change' }
        ],
        salary: [
          { required: true, message: '请输入薪酬', trigger: 'blur' },
        ],
        description: [
          { required: true, message: '请填写兼职详情', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      console.log(this.ruleForm.salary)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.userId = this.$store.getters.getUser.userId
          this.$axios.post('/partJob/edit', this.ruleForm).then(res => {
            console.log(res)
            this.$message({
              message: '保存成功',
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
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getPartJobCategory(){
      this.$axios.get("/partJob/category/").then(res => {
        this.partJobCategorys = res.data.data
      })
    },
    changSalaryCategory(){
      if(this.ruleForm.salaryCategory == "日结"){
        this.salaryCategoryContent = "/日";
      }else if(this.ruleForm.salaryCategory == "月结"){
        this.salaryCategoryContent = "/月";
      }
    },
    limitSalaryNum(){
      //限制薪酬的输入只能是数字
      this.ruleForm.salary = this.ruleForm.salary.replace(/\D/g,'')
    },
    initPartJobInfo() {
      const partJobId = this.$route.params.partJobId
      console.log(partJobId)
      if (partJobId) {
        this.$axios.get('/partJob/' + partJobId).then(res => {
          const partJob = res.data.data
          this.ruleForm.userId = partJob.userId
          this.ruleForm.partJobId = partJob.partJobId
          this.ruleForm.title = partJob.title
          this.ruleForm.description = partJob.description
          this.ruleForm.categoryId = partJob.categoryId
          this.ruleForm.isInsideSchool = partJob.isInsideSchool
          this.ruleForm.salaryCategory = partJob.salaryCategory
          this.ruleForm.salary = partJob.salary

          if (this.ruleForm.salaryCategory == "日结") {
            this.salaryCategoryContent = "/日";
          } else if (this.ruleForm.salaryCategory == "月结") {
            this.salaryCategoryContent = "/月";
          }
        })
      }
    },
    resetPartJobInfo(vm){
      vm.$store.commit("SET_Index", '2')
      vm.ruleForm.userId = ''
      vm.ruleForm.title = ''
      vm.ruleForm.description = ''
      vm.ruleForm.categoryId = ''
      vm.ruleForm.isInsideSchool = ''
      vm.ruleForm.salaryCategory = ''
      vm.ruleForm.salary = ''
    }
  },
  //切换路由时将data置空
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.getPartJobCategory()
      vm.resetPartJobInfo(vm)
      vm.initPartJobInfo()
    });
  },
  beforeRouteLeave (to, from, next) {
    this.$store.commit("SET_Index", '0')
    next()
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  text-align: center;
  line-height: 60px;
}
</style>