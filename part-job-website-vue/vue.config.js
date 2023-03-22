const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    port: 9093,
    proxy: {
      "/job": {
        //���غ��
        target: "http://localhost:8088",
        changeOrigin: true, //�Ƿ����
        pathRewrite: {
          //Ҫ�滻����(֧��������ʽ) : �滻Ϊ����
          "^/job": "",
          //  ^ Ϊ��ʲô��ͷ,�滻�ɿ��ַ���
        },
      },
    },
  }
})
