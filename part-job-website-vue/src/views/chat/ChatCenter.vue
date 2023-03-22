<template>

  <el-container class="login-content">
    <el-header>
      <Header></Header>
    </el-header>
    <el-main style="margin: 0 auto">

      <div style="padding: 10px">
        <el-row>
          <el-col :span="4">
            <el-card style="width: 300px; height: 300px; color: #333;margin-left: 100px">
              <div style="padding-bottom: 10px; border-bottom: 1px solid #ccc">用户<span style="font-size: 12px">（点击用户头像开始聊天）</span></div>
              <div :class="curSessionListId == item.id ? 'box_select' : 'box'" v-for="item in sessionList_already" :key="item.id">
              <div></div>
                <div class="box_left" @click="startSession(item.id,item.listName,item.toUserAvatar)">
                  <el-avatar :size="40" style="float:left;" :src="item.toUserAvatar"></el-avatar>
                  <div style="float:left;margin-top: 5px;font-size: 19px;margin-left: 10px">
                    <span v-if="item.remark">{{item.remark}}</span>
                    <span v-else>{{item.listName}}</span>
                  </div>
                  <el-badge :value="item.unReadCount" v-if="item.unReadCount > 0" style="float: right;margin-right: -25px" />
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="2">
            <div style="width: 900px; margin-left: 220px; background-color: white;
                    border-radius: 5px; box-shadow: 0 0 10px #ccc">
              <div style="text-align: center; line-height: 50px;">
                消息（{{ this.curSessionListName }}）
                <el-dropdown v-if="this.curSessionListId" style="float:right;margin-right: 20px;" @command="handleCommand">
                  <span class="el-dropdown-link" >
                    <i class="el-icon-more" ></i>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="a">修改备注</el-dropdown-item>
                    <el-dropdown-item command="b">删除用户</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              <!--一个隐藏的el-image 用于聊天图片的预览-->
              <el-image style="display:none" ref="previewImg" :src="activeImg" :preview-src-list="[activeImg]"></el-image>
              <div style="height: 350px; overflow:auto; border-top: 1px solid #ccc" @click="getImg($event)" v-html="content"></div>
              <div style="height: 200px;border-top: 1px solid #ccc;
             border-bottom: 1px solid #ccc; outline: none;">
                <el-popover placement="top-start" width="400" trigger="click" class="emoBox">
                  <div class="emotionList">
                    <a href="javascript:void(0);" @click="getEmo(index)" v-for="(item,index) in faceList" :key="index" class="emotionItem">{{item}}</a>
                  </div>
                  <el-button id="emojiBtn" class="emotionSelect" slot="reference">
                    <i class="fa fa-smile-o" style="font-size: 17px" aria-hidden="true"></i>
                  </el-button>
                </el-popover>
                <el-upload
                    class="upload-btn"
                    :action="filesUploadUrl"
                    :before-upload="beforeAvatarUpload"
                    :on-success="imgSuccess"
                    :on-error="imgError"
                    :show-file-list="false"
                    accept=".jpg,.jpeg,.png,.JPG,JPEG,.PNG,.gif,.GIF"
                >
                  <el-button id="uploadImgBtn" style="font-size: 17px" icon="el-icon-picture-outline"></el-button>
                </el-upload>
                <textarea id="textarea" placeholder="请输入内容，回车发送！" v-model="textarea" @keyup.enter = "sendMsg">
                </textarea>
<!--                  <el-input
                      type="textarea"
                      :rows="7"
                      placeholder="请输入内容，回车发送！"
                      @keyup.enter.native = "sendMsg"
                      v-model="textarea"
                      style="width: 100%;border-top: 0px;">
                  </el-input>-->
                <div style="text-align: right; padding-right: 10px;margin-top: 5px">
                  <el-button type="primary" size="mini" @click="sendMsg">发送</el-button>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>

    </el-main>
    <el-footer>
      <FOOTER></FOOTER>
    </el-footer>
  </el-container>

</template>

<script>
//增加了表情功能，如果数据库无法存储表情，请参考：https://blog.csdn.net/qq_34709784/article/details/108751353
import FOOTER from "../../components/Footer";
import Header from "../../components/Header";
const appData=require("../../utils/emoji.json")//引入存放emoji表情的json文件
export default {

  name: 'ChatCenter',
  components:{FOOTER,Header},
  data(){
    return{
      dialogVisible: false,
      dialogTitle: '',
      loginName: '',
      textarea: "",
      list: [],
      curUserId: "",  //登录用户的userId
      curUserName: "",
      curSessionListName:"",
      toUserAvatar:"",
      curUserAvatar: this.$store.getters.getUser.avatar,
      curSessionListId: '',
      sessionList_already:[],
      sessionList_not:[],
      content:'',
      faceList:[],//表情数据
      filesUploadUrl: "http://localhost:8088/files/upload",
      activeImg:''
    }
  },
  created() { // 页面创建生命周期函数
  },
  mounted(){
    for (let i in appData){//读取json文件保存数据给数组
      this.faceList.push(appData[i].char);
    }
  },
  updated(){
    // 解决每次发消息到最后面
    var elmnt = document.getElementById("msg_end");
    //elmnt.scrollTop = elmnt.scrollHeight;
  },
  destroyed: function () { // 离开页面生命周期函数
    this.websocketclose();
  },
  methods: {
    initWebSocket: function (userId,sessionListId) {
      // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
      console.log("1111dddd"+sessionListId)
      this.websock = new WebSocket("ws://localhost:8088/websocket/"+userId+"/"+sessionListId);
      this.websock.onopen = this.websocketonopen;
      this.websock.onerror = this.websocketonerror;
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onclose = this.websocketclose;
    },
    websocketonopen: function () {
      console.log("WebSocket连接成功");
    },
    websocketonerror: function (e) {
      console.log("WebSocket连接发生错误",e);
    },
    websocketonmessage: function (e) {  //接收到消息时调用
      let data = JSON.parse(e.data);
      if(data instanceof Array){
        // 列表数据
        this.sessionListAlready()
      }else{
        // 消息数据
        this.createContent(data.toUserName, null, data.content, data.msgType)
      }
    },
    websocketclose: function (e) {
      if(this.curUserId != null){
        if(this.curSessionListId != null){
          this.initWebSocket(this.curUserId, this.curSessionListId)
        }else{
          this.initWebSocket(this.curUserId, 'keepConnect')
        }
      }
      console.log("connection closed",e);
    },
    // 消息发送
    sendMsg(){
      if(this.curSessionListId == '' || this.curSessionListId == 'keepConnect'){
        return this.$message.error("请选择左边的对话框开始聊天!")
      }
      if (!this.textarea) {
        this.$message({type: 'warning', message: "请输入内容"})
      } else {
        if (typeof (WebSocket) == "undefined") {
          console.log("您的浏览器不支持WebSocket");
        } else {
          // 组装待发送的消息 json
          let message = {curSessionListId: this.curSessionListId, text: this.textarea,msgType: '1'}
          console.log(message)
          this.websock.send(JSON.stringify(message));  // 将组装好的json发送给服务端，由服务端进行转发
          //发送消息之后修改页面，将自己发的消息，加到聊天框中
          this.createContent(null, this.curUserName, this.textarea, 1)
          this.textarea = ''
        }
      }
    },
    // 发送图片类型的消息
    sendImgMsg(imgUrl){
      if (!imgUrl) {
        this.$message({type: 'warning', message: "图片发送异常"})
      } else {
        if (typeof (WebSocket) == "undefined") {
          console.log("您的浏览器不支持WebSocket");
        } else {
          // 组装待发送的消息 json
          let message = {curSessionListId: this.curSessionListId, text: imgUrl,msgType: '2'}
          console.log(message)
          this.websock.send(JSON.stringify(message));  // 将组装好的json发送给服务端，由服务端进行转发
          //发送消息之后修改页面，将自己发的消息，加到聊天框中
          this.createContent(null, this.curUserName, imgUrl,'2')
        }
      }
    },
    //用于聊天图片的预览功能
    getImg($event){
      if($event.target.currentSrc){
        this.previewImg($event.target.currentSrc)
      }
    },
    previewImg(img) {
      this.activeImg = img;
      this.$refs.previewImg.clickHandler();
    },
    // 获取已存在的会话列表(历史会话)
    sessionListAlready(){
      this.$axios.get('/sessionList/getHistory/' + this.curUserId).then(res => {
        this.sessionList_already = res.data.data
      })
    },
    // 创建会话
    createSession(toUserId){
      this.$axios.get('/sessionList/add/' + this.curUserId + "/" + toUserId).then(res => {
        //this.getSessionListNot()
        this.sessionListAlready()
      })
    },
    // 开始会话
    startSession(sessionListId,sessionListName,toUserAvatar){
      if(this.websock != undefined){
        this.websock.close()
      }
      if(sessionListId != 'keepConnect'){
        this.curSessionListId = sessionListId
        this.curSessionListName = sessionListName
        this.toUserAvatar = toUserAvatar
      }
      this.initWebSocket(this.curUserId, sessionListId)
      this.msgList(sessionListId)
    },
    // 删除会话
    delSession(){
      console.log("wdnmd")
      this.$axios.delete('/sessionList/del/' + this.curSessionListId).then(res => {
        this.sessionListAlready()
        this.curSessionListId = ''
        this.content = ''
        this.curSessionListName = ''
      })
    },
    // 获取消息数据
    msgList(sessionListId){
      this.content =''
      if(sessionListId != 'keepConnect'){
        this.$axios.get('/msgInfo/list/' + sessionListId).then(res => {
          //将查询到的消息展示在页面上
          const list = res.data.data
          console.log(list)
          for (var i=0;i<list.length;i++) {
            //判断消息是谁发的，从而确定页面上显示在左边还是右边
            if(list[i].fromUserId == this.curUserId){
              this.createContent(null,list[i].fromUserName,list[i].content, list[i].msgType)
            }else{
              this.createContent(list[i].toUserName,null,list[i].content, list[i].msgType)
            }
          }
          //重新获取会话列表
          this.sessionListAlready()
        })
      }
    },
    createContent(remoteUser, nowUser, text, msgType) {  // 这个方法是用来将 json的聊天消息数据转换成 html的。
      let html
      console.log(text)
      //判断本条消息是图片还是文本 2图片 其他则是文本
      if(msgType == '2'){
        // 当前用户消息
        if (nowUser) { // nowUser 表示是否显示当前用户发送的聊天消息，绿色气泡
          html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
              "  <div class=\"el-col el-col-22\" style=\"text-align: right; padding-right: 10px\">\n" +
              "    <div class=\"tip\">" + "<img @click=\"test\" src=\"" +text + "\" class=\"img\">" + "</div>\n" +
              "  </div>\n" +
              "  <div class=\"el-col el-col-2\">\n" +
              "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
              "    <img src=\""+this.curUserAvatar+"\" style=\"object-fit: cover;\">\n" +
              "  </span>\n" +
              "  </div>\n" +
              "</div>";
        } else if (remoteUser) {   // remoteUser表示远程用户聊天消息，蓝色的气泡
          html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
              "  <div class=\"el-col el-col-2\" style=\"text-align: right\">\n" +
              "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
              "    <img src=\""+this.toUserAvatar+"\" style=\"object-fit: cover;\">\n" +
              "  </span>\n" +
              "  </div>\n" +
              "  <div class=\"el-col el-col-22\" style=\"text-align: left; padding-left: 10px\">\n" +
              "    <div class=\"tip\">" + "<img @click=\"test\" src=\"" +text + "\" class=\"img\">" + "</div>\n" +
              "  </div>\n" +
              "</div>";
        }
      }else{
        // 当前用户消息
        if (nowUser) { // nowUser 表示是否显示当前用户发送的聊天消息，绿色气泡
          html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
              "  <div class=\"el-col el-col-22\" style=\"text-align: right; padding-right: 10px\">\n" +
              "    <div class=\"tip left\">" + text + "</div>\n" +
              "  </div>\n" +
              "  <div class=\"el-col el-col-2\">\n" +
              "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
              "    <img src=\""+this.curUserAvatar+"\" style=\"object-fit: cover;\">\n" +
              "  </span>\n" +
              "  </div>\n" +
              "</div>";
        } else if (remoteUser) {   // remoteUser表示远程用户聊天消息，蓝色的气泡
          html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
              "  <div class=\"el-col el-col-2\" style=\"text-align: right\">\n" +
              "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
              "    <img src=\""+this.toUserAvatar+"\" style=\"object-fit: cover;\">\n" +
              "  </span>\n" +
              "  </div>\n" +
              "  <div class=\"el-col el-col-22\" style=\"text-align: left; padding-left: 10px\">\n" +
              "    <div class=\"tip right\">" + text + "</div>\n" +
              "  </div>\n" +
              "</div>";
        }
      }
      console.log(html)
      this.content += html;
    },
    handleCommand(command) {
      if(command == 'a'){
        this.$prompt('请输入备注', '修改备注', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        }).then(({ value }) => {
          if(value != null && value.replace(/\s+/g,"")){
            this.$axios.get('/sessionList/edit/' + this.curSessionListId + "/" + value).then(res => {
              //重新获取会话列表
              this.sessionListAlready()
            })
          }else{
            this.$message({
              type: 'info',
              message: '请输入内容',
              duration: 1000,
            });
          }
        })
      }else if(command == 'b'){
        this.delSession()
      }
    },
    initSessionListInfo(){
      this.curUserId = this.$store.getters.getUser.userId
      this.curUserName = this.$store.getters.getUser.nickname

      const toUserId = this.$route.params.toUserId
      console.log(toUserId,this.curUserId)
      if (toUserId) {
        this.createSession(toUserId)
      }else{
        this.sessionListAlready()
        //进入消息中心页面之后要开启websocket连接，因为可能会接收到列表消息
        //这样即使有人发来消息，页面会自动显示，不需要用户手动刷新
        this.startSession('keepConnect')
      }
    },
    //获取Emoji
    getEmo(index){
      var textArea=document.getElementById('textarea');
      //将选中的表情插入到输入文本的光标之后
      function changeSelectedText(obj, str) {
        if (window.getSelection) {
          // 非IE浏览器
          textArea.setRangeText(str);
          // 在未选中文本的情况下，重新设置光标位置
          textArea.selectionStart += str.length;
          textArea.focus()
        } else if (document.selection) {
          // IE浏览器
          obj.focus();
          var sel = document.selection.createRange();
          sel.text = str;
        }
      }
      changeSelectedText(textArea,this.faceList[index]);
      this.textArea=textArea.value;// 要同步data中的数据
      // console.log(this.faceList[index]);
      return;
    },
    /**
     *    图片上传的相关方法
     */
    //上传前
    beforeAvatarUpload(file) {
      if(this.curSessionListId == '' || this.curSessionListId == 'keepConnect'){
        this.$message.error("请选择左边的对话框选择发送的用户！")
        return false;
      }
      //判断图片大小
      let isLt1M = file.size / 1024 / 1024 < 1;
      console.log(file)
      if (!isLt1M) {
        this.$message.error('上传图片大小不能超过 1MB!');
      }
      //判断图片的格式
      let imgType=file.name.substring(file.name.lastIndexOf(".")+1);
      imgType=imgType.toLowerCase();
      let isImg=imgType==='jpg'|| imgType==='png'|| imgType==='jpeg'||imgType==='gif';
      if (!isImg){
        this.$message.error('上传图片格式不符合要求！');
      }
      return isLt1M&&isImg;
    },
    // 图片上传成功
    imgSuccess(response, file, fileList) {
      console.log("图片url为："+response);
      this.sendImgMsg(response.data)
    },
    // 图片上传失败
    imgError(err, file, fileList){
      this.$message.error("上传失败");
    },
  },
  //切换路由时将data置空
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initSessionListInfo()
      vm.$store.commit("SET_Index", '3')
    });
  },
  beforeRouteLeave (to, from, next) {
    this.$store.commit("SET_Index", '0')
    this.curUserId = null
    next()
  }
}
</script>

<style>
/* el-popover是和app同级的，所以scoped的局部属性设置无效 */
/* 需要设置全局style */
.el-popover{
  height:200px;
  width:450px;
  overflow: scroll;
  overflow-x:auto;
}
</style>

<!-- Add "scoped" attribute to limit CSS to this component only -->v
<style scoped>
  /*聊天记录的css*/
  /deep/ .tip {
    color: white;
    text-align: left;
    border-radius: 10px;
    font-family: sans-serif;
    padding: 10px;
    width:auto;
    max-width: 600px;
    display:inline-block !important;
    display:inline;
  }
  /deep/ .right {
    background-color: deepskyblue;
  }
  /deep/ .left {
    background-color: forestgreen;
  }
  /deep/ .img {
    width: 100px;
    height: 100px;
    object-fit: contain
  }
.item {
  margin-top: 10px;
  margin-right: 40px;
}

.el-main{
  min-height: calc(75vh);
  margin: 0 auto;
}
/*用户卡片的css*/
.box{
  width: 235px;
  height: 35px;
  padding: 5px 25px 10px 0px;
  display: flex;
  justify-self: flex-end;
  /* border: 1px red solid; */
}
.box:hover{
  background-color: gainsboro;
  cursor: pointer;
}
.box_select{
  width: 235px;
  height: 35px;
  padding: 5px 25px 10px 0px;
  display: flex;
  justify-self: flex-end;
  background-color: gainsboro;
}
.box_left{
  width: 230px;
  height: 22px;
}
.right_left{
  margin-left: 90px;
  width: 50px;
  height: 22px;
  display: flex;
  justify-content: space-between;
  /* border: 1px red solid; */
}
.right_left_count{
  /* border: 1px blue solid; */
  padding-left: 10px;
  padding-top: 6px;

  width: 20px;
}
.right_left_del{
  padding-top: 6px;
  background-color: red;
}

.el-header, .el-footer {
  text-align: center;
  line-height: 60px;
}

  .el-dropdown-link {
    cursor: pointer;
  }
  .el-icon-more {
    font-size: 20px;
  }

/*  表情相关css*/
  #emojiBtn{
    border: none;
    padding-right: 0px;
    padding-bottom: 0px;
    margin-bottom: 0px;
  }
  #emojiBtn:hover{
    background-color: white;
  }
  .emotionList{
    display: flex;
    flex-wrap: wrap;
    padding:5px;
  }
  .emotionItem{
    width:10%;
    font-size:20px;
    text-align:center;
  }
  #textarea {
    resize:none;
    padding: 10px;
    width: 95%;
    height: 50%;
    border: none;
    outline: none;
  }
  #uploadImgBtn{
    border: none;
    padding-bottom: 0px;
    margin-bottom: 0px;
    padding-left: 12px;
  }
  #uploadImgBtn:hover{
    background-color: white;
  }
  .upload-btn{
    display: inline-block;
  }
  /*包含以下四种的链接*/
  .emotionItem {
    text-decoration: none;
  }
  /*正常的未被访问过的链接*/
  .emotionItem:link {
    text-decoration: none;
  }
  /*已经访问过的链接*/
  .emotionItem:visited {
    text-decoration: none;
  }
  /*鼠标划过(停留)的链接*/
  .emotionItem:hover {
    text-decoration: none;
  }
  /* 正在点击的链接*/
  .emotionItem:active {
    text-decoration: none;
  }
</style>
