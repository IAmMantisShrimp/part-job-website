<template>
  <el-container class="login-content">
    <el-header>
      <Header></Header>
    </el-header>
    <el-main class="partjob-content">
      <PartJobContent></PartJobContent>
    </el-main>
    <el-footer>
      <FOOTER></FOOTER>
    </el-footer>
  </el-container>
</template>

<script>
import FOOTER from "../../components/Footer";
import Header from "../../components/Header";
import PartJobContent from "../../components/PartJobContent";

export default {
  name: "PartJobList",
  components:{FOOTER,Header,PartJobContent},
  created() {
    const keyword = this.$route.params.keyword
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
  .el-main{
    min-height: calc(70vh);
  }
</style>