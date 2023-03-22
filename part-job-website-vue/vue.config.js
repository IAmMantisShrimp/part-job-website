const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    port: 9093,
    proxy: {
      "/job": {
        //本地后端
        target: "http://localhost:8088",
        changeOrigin: true, //是否跨域
        pathRewrite: {
          //要替换数据(支持正则表达式) : 替换为数据
          "^/job": "",
          //  ^ 为以什么开头,替换成空字符串
        },
      },
    },
  }
})
