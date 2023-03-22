<template>
  <el-container class="login-content">
    <el-header>
      <Header></Header>
    </el-header>
    <el-main class="partjob-content">
      <div>
        <el-card class="box-card" style="margin-top: 30px;background-color: #FAFAFA" shadow="hover"  v-for="partjob in partjobs">
          <div slot="header" class="clearfix">
            <router-link style="font-size: 20px;color: black" :to="{name: 'PortJobDetail', params: {partJobId: partjob.partJobId}}">
              {{partjob.title}}
            </router-link>
            <!--      <el-button v-if="ownPartJob" style="float: right; padding: 3px 0;color: #F78181" @click="toEditPartJob(partjob.partJobId)" type="text">
                    <i class="el-icon-delete"></i>删除
                  </el-button>
                  <el-button v-if="ownPartJob" style="float: right; padding: 3px 0;width: 80px" @click="toEditPartJob(partjob.partJobId)" type="text">
                    <i class="el-icon-edit"></i>编辑
                  </el-button>-->
          </div>
          <div class="text item">
            <span>校内外： {{partjob.isInsideSchool}}</span>
            <span style="margin-left: 63px">兼职类型： {{partjob.categoryName}}</span>
          </div>
          <div class="text item">
            <span>薪酬类型： {{partjob.salaryCategory}}</span>
            <span style="margin-left: 50px">薪酬： {{partjob.salary}}</span>
          </div>
        </el-card>
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
export default {
  name: "CollectPartJobList",
  components:{FOOTER,Header},
  data(){
    return {
      partjobs: {},
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.$axios.get("/collect/list/" + vm.$store.getters.getUser.userId).then(res => {
        console.log(res)
        vm.partjobs = res.data.data
      })
    });
  }
}
</script>

<style scoped>
  .partjob-content{
    margin: 0 auto;
  }
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
    height: 200px;
  }

  .mpage {
    margin-top: 30px;
    text-align: center;
  }
  .my-label {
    background: #E1F3D8;
  }

  .my-content {
    background: #FDE2E2;
  }
</style>