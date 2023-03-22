<template>
  <div class="me-view-comment-item">
    <div class="me-view-comment-author">
      <div style="float:left;">
        <a class="">
          <el-avatar style="margin-top: 5px" :size="40" :src="comment.author.avatar"></el-avatar>
        </a>
      </div>
      <div class="me-view-info">
        <span class="me-view-nickname">{{comment.author.nickname}}</span>
        <div class="me-view-meta">
          <span>{{rootCommentCounts - index}}楼</span>
          <span>{{comment.createTime}}</span>
        </div>
      </div>
      <div class="clear"></div>
    </div>
    <div style="margin-top:-8px;">
      <p class="me-view-comment-content">{{comment.content}}</p>
      <div class="me-view-comment-tools">
        <!--<a class="me-view-comment-tool">-->
        <!--<i class="el-icon-caret-top"></i> 20-->
        <!--</a>-->
        <a class="me-view-comment-tool" @click="showComment(-1)">
          <i class="me-icon-comment"></i>&nbsp; 评论
        </a>
      </div>

      <div class="me-reply-list">
        <div class="me-reply-item" v-for="c in comment.childComment" :key="c.id">
          <div style="font-size: 14px">
            <span class="me-reply-user">{{c.author.nickname}}:&nbsp;&nbsp;</span>

            <span v-if="c.level == 2" class="me-reply-user">@{{c.toUser.nickname}} </span>

            <span>{{c.content}}</span>
          </div>
          <div class="me-view-meta">
            <span style="padding-right: 10px">{{c.createTime}}</span>
            <a class="me-view-comment-tool" @click="showComment(c.id, c.author)">
              <i class="me-icon-comment"></i>&nbsp;回复
            </a>
          </div>

        </div>

        <div class="me-view-comment-write" v-show="commentShow">

          <el-input
              v-model="reply.content"
              type="input"
              style="width: 90%"
              :placeholder="placeholder"
              class="me-view-comment-text"
              resize="none">
          </el-input>

          <el-button style="margin-left: 8px" @click="publishComment()" type="text">评论</el-button>

        </div>

      </div>

    </div>
  </div>
</template>

<script>

export default {
  name: "CommentItem",
  props: {
    partJobId: String,
    comment: Object,
    index: Number,
    rootCommentCounts: Number
  },
  data() {
    return {
      placeholder: '你的评论...',
      commentShow: false,
      commentShowIndex: '',
      reply: this.getEmptyReply()
    }
  },
  methods: {
    showComment(commentShowIndex, toUser) {
      this.reply = this.getEmptyReply()

      if (this.commentShowIndex !== commentShowIndex) {

        if (toUser) {
          this.placeholder = `@${toUser.nickname} `
          this.reply.toUser = toUser
        } else {
          this.placeholder = '你的评论...'
        }

        this.commentShow = true
        this.commentShowIndex = commentShowIndex
      } else {
        //关闭评论框
        this.commentShow = false
        this.commentShowIndex = ''
      }
    },
    publishComment() {
      if(this.reply.content){
        this.reply.userId = this.$store.getters.getUser.userId
        console.log(this.reply)
        this.$axios.post('/partJobComment/add/' , this.reply).then(res => {
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
    getEmptyReply() {
      return {
        /*partJob: {
          partJobId: this.partJobId
        },
        parent: {
          id: this.comment.id
        },*/
        partJobId: this.partJobId,
        parentId: this.comment.partJobCommentId,
        toUid: this.comment.userId,
        content: '',
        userId:''
      }
    }
  }
}
</script>

<style scoped>
.me-view-tag-item {
  margin: 0 4px;
}

.me-view-comment {
  margin-top: 60px;
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

.v-show-content {
  padding: 8px 25px 15px 0px !important;
}

.v-note-wrapper .v-note-panel {
  box-shadow: none !important;
}

.me-view-comment-item {
  margin-top: 20px;
  padding-bottom: 16px;
  border-bottom: 1px solid #f0f0f0;
}

.me-view-comment-author {
  margin: 10px 0;
  vertical-align: middle;
}

.me-view-nickname {
  font-size: 14px;
}

.me-view-comment-content {
  line-height: 1.5;
}

.me-view-comment-tools {
  margin-top: 4px;
  margin-bottom: 10px;
}

.me-view-comment-tool {
  font-size: 13px;
  color: #a6a6a6;
  padding-right: 14px;
}

.v-note-wrapper .v-note-panel .v-note-show .v-show-content, .v-note-wrapper .v-note-panel .v-note-show .v-show-content-html {
  background: #fff !important;
}

.me-reply-list {
  padding-left: 16px;
  border-left: 4px solid #c5cac3;
}

.me-reply-item {
  margin-bottom: 8px;
  border-bottom: 1px solid #f0f0f0;
}

.me-reply-user {
  color: #78b6f7;
}

.me-view-info {
  float:left;
  display: inline-block;
  vertical-align: middle;
  margin-left: 8px;
}
.me-view-meta {
  font-size: 12px;
  color: #969696;
}

.me-view-end {
  margin-top: 20px;
}
.clear{clear:both;} /* 清除空白盒子的所有浮动 */
</style>
