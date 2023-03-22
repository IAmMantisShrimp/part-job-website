<template>
  <el-container class="login-content">
    <el-header>
      <Header></Header>
    </el-header>
    <el-main class="partjob-content">

      <div class="block">
        <el-carousel height="300px">
          <el-carousel-item v-for="item in indexImg" :key="item">
            <el-image :src="item" style="height: 300px;width: 100%" fit="fill"></el-image>
          </el-carousel-item>
        </el-carousel>
      </div>

      <el-tabs value="all" style="margin-top: 20px" @tab-click="handleClick">
        <el-tab-pane label="全部" name="all"></el-tab-pane>
        <el-tab-pane :label="partJobCategory.categoryName" :name="partJobCategory.categoryId" v-for="partJobCategory in partJobCategorys"></el-tab-pane>
      </el-tabs>
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
    </el-main>
    <el-footer>
      <FOOTER></FOOTER>
    </el-footer>
  </el-container>
</template>

<script>
import FOOTER from "../../components/Footer";
import Header from "../../components/Header";
import indexImg01 from "../../assets/index-img01.jpg"
import indexImg02 from "../../assets/index-img02.jpg"
import indexImg03 from "../../assets/index-img03.png"

export default {
  name: "PartJobList",
  components:{FOOTER,Header},
  data() {
    return {
      partJobCategorys: {},
      categoryId:'',
      categoryName:'',
      partjobs: {},
      currentPage: 1,
      total: 0,
      pageSize: 5,
      indexImg:[indexImg01,indexImg02,indexImg03]
    }
  },
  methods: {
    page(currentPage) {
      if(this.categoryId){
        this.$axios.get("/partJob/searchByCategory/"+ this.categoryId + "/" + currentPage).then(res => {
          console.log(res)
          this.partjobs = res.data.data.records
          this.currentPage = res.data.data.current
          this.total = res.data.data.total
          this.pageSize = res.data.data.size
          this.$store.commit("SET_PbList", this.partjobs)
        })
      }else{
        this.$axios.get("/partJob/list/" + currentPage).then(res => {
          console.log(res)
          this.partjobs = res.data.data.records
          this.currentPage = res.data.data.current
          this.total = res.data.data.total
          this.pageSize = res.data.data.size
          this.$store.commit("SET_PbList", this.partjobs)
        })
      }
    },
    handleClick(tab, event) {
      if(tab.name == 'all'){
        this.categoryId = ''
        this.page(1)
      }else{
        this.categoryId = tab.name
        this.page(1)
      }
    },
    getPartJobCategory(){
      this.$axios.get("/partJob/category/").then(res => {
        this.partJobCategorys = res.data.data
      })
    },
  },
  created() {
    this.page(1)
    this.getPartJobCategory()
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.page(1)
      vm.getPartJobCategory()
      vm.$store.commit("SET_Index", '1')
    });
  },
  beforeRouteLeave (to, from, next) {
    next(vm => {
      vm.$store.commit("SET_Index", '')
      next()
    });
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  text-align: center;
  line-height: 60px;
}
.partjob-content{
  margin: 0 auto;
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
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>