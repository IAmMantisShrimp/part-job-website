<template>
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

    <el-pagination class="mpage"
                   background
                   layout="prev, pager, next"
                   :current-page="currentPage"
                   :page-size="pageSize"
                   :total="total"
                   @current-change=page>
    </el-pagination>

  </div>
</template>

<script>
export default {
  name: "PartJobContent",
  data() {
    return {
      partjobs: {},
      currentPage: 1,
      total: 0,
      pageSize: 5,
      keyword:''
    }
  },
  methods: {
    page(currentPage) {
      if(this.keyword){
        this.$axios.get("/partJob/search/" + this.keyword + "/" + currentPage).then(res => {
          console.log(res)
          this.partjobs = res.data.data.records
          this.currentPage = res.data.data.current
          this.total = res.data.data.total
          this.pageSize = res.data.data.size
          this.$store.commit("SET_PbList", this.partjobs)
        })
      }
    },
    toEditPartJob(partJobId){
      this.$router.push("/edit/"+partJobId)
    }
  },
  created() {
    this.keyword = this.$route.params.keyword
    this.page(1)
  }
}
</script>
<style scoped>
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