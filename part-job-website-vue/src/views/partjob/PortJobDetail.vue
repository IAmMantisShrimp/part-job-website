<template>
  <el-container class="login-content">
    <el-header>
      <Header></Header>
    </el-header>
    <el-main class="partjob-deail" >
      <div style="width: 1120px;">
        <div style="float: left">
          <el-card class="box-card" style="margin-top: 30px;background-color: #FAFAFA;float:left;" shadow="always">
            <div slot="header" class="clearfix">
              <span>{{partJob.title}}</span>
              <el-popconfirm
                  @confirm="deletePartJob"
                  confirm-button-text='确定'
                  cancel-button-text='取消'
                  icon="el-icon-info"
                  icon-color="red"
                  title="确认删除该兼职吗?"
              >
                <el-button  v-show="ownPartJob" style="float: right; padding: 3px 0;color: #F78181;height: 30px" type="text" slot="reference">
                  <i class="el-icon-delete"></i>删除
                </el-button>
              </el-popconfirm>
              <el-button v-show="ownPartJob" style="float: right; padding: 3px 0;width: 80px;height: 30px" @click="toEditPartJob()" type="text">
                <i class="el-icon-edit"></i>编辑
              </el-button>
              <el-button style="float: right; padding: 3px 0;width: 30px;height: 30px" @click="collectPartJob()" :type="collectType" icon="el-icon-star-off" circle>
              </el-button>
            </div>
            <div class="text item">校内外： {{partJob.isInsideSchool}}</div>
            <div class="text item">兼职类型： {{partJob.categoryName}}</div>
            <div class="text item">薪酬类型：{{partJob.salaryCategory}}</div>
            <div class="text item">
              薪酬：{{partJob.salary}}
              <span style="margin-left: 2px">{{salaryCategoryContent}}</span>
            </div>
            <div class="text item">
              <span style="float:left;">详情：</span>
              <span style="display:inline-block;width: 700px;word-wrap: break-word;float:left;margin-bottom: 30px">{{partJob.description}}</span>
            </div>
          </el-card>
        </div>
        <div style="float:left;">
          <el-card class="user-card">
            <div slot="header" style="height: 30px">
              <div style="width: 130px;float:left;">
                <el-avatar style="float: left" :size="40" :src="user.avatar"></el-avatar>
                <div style="float:left;margin-top: 5px;margin-left:5px;font-size: 19px">{{ user.nickname }}</div>
              </div>

              <div style="float: left; margin-left: 30px;" ><el-button style="padding: 2px 0;margin-top: 10px" @click="toSendMsg" type="text">发消息</el-button></div>
            </div>
            <div class="text item">
              手机号: {{user.phone}}
            </div>
            <div class="text item">
              邮箱: {{user.email}}
            </div>
          </el-card>
        </div>
        <div class="clear"></div>
      </div>

      <div class="me-view-comment">
        <div class="me-view-comment-write">
          <el-row :gutter="20">
            <el-col :span="2">
              <a class="">
                <el-avatar style="margin-top: 5px" :size="50" :src="avatar"></el-avatar>
              </a>
            </el-col>
            <el-col :span="22">
              <el-input
                  type="textarea"
                  :autosize="{ minRows: 2}"
                  placeholder="你的评论..."
                  class="me-view-comment-text"
                  v-model="comment.content"
                  resize="none">
              </el-input>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="2" :offset="22">
              <el-button type="text" @click="publishComment()">评论</el-button>
            </el-col>
          </el-row>
        </div>

        <div class="me-view-comment-title">
          <span>{{partJob.commentCount}} 条评论</span>
        </div>

        <CommentItem
            v-for="(c,index) in comments"
            :comment="c"
            :partJobId="partJob.partJobId"
            :index="index"
            :rootCommentCounts="comments.length"
            @commentCountIncrement="commentCountIncrement"
            :key="c.id">
        </CommentItem>
      </div>
    </el-main>
    <el-footer>
      <FOOTER></FOOTER>
    </el-footer>
  </el-container>
</template>

<script>
import FOOTER from "../../components/Footer";
import Header from "../../components/Header";
import CommentItem from '@/components/CommentItem'

export default {
  name: "PartJobList",
  components:{FOOTER,Header,CommentItem},
  data() {
    return {
      salaryCategoryContent: "",
      partJob: {
        partJobId:'',
        title: '',
        description: '',
        categoryId: '',
        categoryName: '',
        salary: '',
        salaryCategory: '',
        isInsideSchool: '',
        userId: '',
        commentCount:''
      },
      avatar: this.$store.getters.getUser.avatar,
      user:{
        userId:'',
        nickname:'',
        phone:'',
        email:'',
        avatar:''
      },
      ownPartJob: false,
      collectType: '',
      comments: [],
      comment: {
        userId:'',
        partJobId:'',
        parentId:'',
        content: '',
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.userId = this.$store.getters.getUser.userId
          this.$axios.post('/partJob/edit', this.ruleForm).then(res => {
            console.log(res)
            this.$message({
              message: '添加成功',
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
    toEditPartJob(){
      this.$router.push("/edit/"+this.partJob.partJobId)
    },
    deletePartJob(){
      console.log(this.partJob.partJobId)
      this.$axios.delete('/partJob/' + this.partJob.partJobId).then(res => {
        this.$router.push("/index/")
      })
    },
    collectPartJob(){
      //根据collectType判断是收藏还是取消收藏
      if(this.collectType){
        this.$axios.delete('/collect/del/'+ this.$store.getters.getUser.userId + '/' + this.partJob.partJobId).then(res => {
          this.$message({
            message: '取消收藏成功',
            type: 'success'
          });
          this.collectType = ''
        })
      }else{
        this.$axios.get('/collect/add/'+ this.$store.getters.getUser.userId + '/' + this.partJob.partJobId).then(res => {
          this.$message({
            message: '收藏成功',
            type: 'success'
          });
          this.collectType = 'primary'
        })
      }
    },
    initPartJobInfo(){
      const partJobId = this.$route.params.partJobId
      console.log(partJobId)
      this.$axios.get('/partJob/' + partJobId).then(res => {
        const partJob = res.data.data
        console.log(partJob)
        this.partJob.userId = partJob.userId
        this.partJob.partJobId = partJob.partJobId
        this.partJob.title = partJob.title
        this.partJob.description = partJob.description
        this.partJob.categoryName = partJob.categoryName
        this.partJob.isInsideSchool = partJob.isInsideSchool
        this.partJob.salaryCategory = partJob.salaryCategory
        this.partJob.commentCount = partJob.commentCount
        this.partJob.salary = partJob.salary

        if(this.partJob.salaryCategory == "日结"){
          this.salaryCategoryContent = "/日";
        }else if(this.partJob.salaryCategory == "月结"){
          this.salaryCategoryContent = "/月";
        }
        //判断是否显示编辑按钮
        this.ownPartJob = (partJob.userId === this.$store.getters.getUser.userId)

        //初始化兼职发布者用户信息
        this.initUserInfo()
        //初始化评论信息
        this.initCommentList()
      })
      //判断当前兼职是否被登录用户收藏
      this.$axios.get('/collect/check/'+ this.$store.getters.getUser.userId + '/' + partJobId).then(res => {
        console.log(res.data.data)
        if(res.data.data){
          this.collectType = 'primary'
        }
      })
    },
    initUserInfo(){
      this.$axios.get('/user/get/' + this.partJob.userId).then(res => {
        const user = res.data.data
        console.log(user)
        this.user.userId = user.userId
        this.user.nickname = user.nickname
        this.user.phone = user.phone
        this.user.email = user.email
        this.user.avatar = user.avatar
      })
    },
    toSendMsg(){
      if(this.user.userId == this.$store.getters.getUser.userId){
        return this.$message({
          message: "无法对自己进行此操作！",
          duration: 1000,
          type: 'warning'
        });
      }
      this.$router.push("/sendMsg/"+this.user.userId)
    },
    /**
     * 评论相关的方法
     */
    publishComment(){
      if(this.comment.content){
        this.comment.userId = this.$store.getters.getUser.userId
        this.comment.partJobId = this.partJob.partJobId
        this.$axios.post('/partJobComment/add/' , this.comment).then(res => {
          location.reload()
        })
      }else{
        this.$message({
          message: "请输入内容！",
          duration: 1000,
          type: 'warning'
        });
      }
    },
    commentCountIncrement() {
      this.partJob.commentCount += 1
    },
    initCommentList(){
      if(this.partJob.commentCount > 0){
        this.$axios.get('/partJobComment/listByPartJob/' + this.partJob.partJobId).then(res => {
          this.comments = res.data.data
        })
      }
    }
  },
  created() {
  },
  //切换路由时初始化页面
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initPartJobInfo()
    });
  },
}
</script>

<style scoped>
.el-header, .el-footer {
  text-align: center;
  line-height: 60px;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 850px;
}
.user-card{
  width: 250px;
  float:right;
  margin-top: 30px;
  margin-left: 10px;
  background-color: #FAFAFA;
}
.el-main{
  min-height: calc(70vh);
  margin: 0 auto;
}
.me-view-comment {
  margin-top: 30px;
  width: 850px;
}
/*评论相关样式*/
.me-view-picture {
  padding-top: 10px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.me-view-comment-title {
  font-weight: 600;
  border-bottom: 1px solid #f0f0f0;
  padding-bottom: 20px;
}

.me-view-comment-write {
  margin-top: 20px;
}

.me-view-comment-text {
  font-size: 16px;
}
.clear{clear:both;} /* 清除空白盒子的所有浮动 */

</style>