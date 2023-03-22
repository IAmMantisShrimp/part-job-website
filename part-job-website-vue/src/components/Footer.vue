<template>
  <div class="footer">
    <div class="github">

    </div>
    <div class="maction">
      <span><el-link :href="hbustUrl">江财官网</el-link></span>
      <el-divider direction="vertical"></el-divider>

      <span><el-link :href="githubUrl">软件学院</el-link></span>


    </div>
    <div style="height: 20px">
      <span class="copyright-content">Copyright  2022  Jiangxi University of Finance and Economics</span>
    </div>

  </div>
</template>

<script>
import giteeIcon from "../assets/gitee_icon.png"

export default {
  name: "Footer",
  data(){
    return {
      hbustUrl: "https://www.jxufe.edu.cn/",
      githubUrl:"https://software.jxufe.edu.cn/",
      giteeIcon: giteeIcon
    }
  }
}
</script>

<style scoped>
  .footer{
    height: 130px;
    width: 100%;
    margin: 0 auto;
    text-align: center;
    left: 0;
  }
  .footer .github {
    height: 30px;
    line-height: 50px;
    margin-top: 20px;
  }

  .copyright-content{
    font-size: 15px;
    color: #848484;
  }
  .maction{
    height: 30px;
  }
</style>