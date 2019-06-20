<template>
  <div v-loading="loading">
    <el-card shadow="never">
      <h3>{{ this.postInfo.title }}</h3>
      <span class="postMeta">
        <i class="el-icon-time"></i>
        &nbsp;{{ this.postInfo.createDateTime | formatToDate }}&nbsp;
        <i
          class="el-icon-chat-dot-round"
        ></i>
        &nbsp;{{ this.postInfo.commentList.length }}
      </span>
      <el-divider></el-divider>
      <div class="postContent">{{ this.postInfo.content }}</div>
    </el-card>
    <el-row class="commentArea" :gutter="40" v-if="blogInfo.commentable">
      <el-col :md="24" :lg="12" style="margin-top: 15px;" v-if="postInfo.commentList.length != 0">
        <el-card shadow="never" :key="comment.id" v-for="comment in this.postInfo.commentList">
          <el-row type="flex" justify="space-between">
            <div>
              <i class="el-icon-user"></i>
              &nbsp;
              <span class="commentAuthor">{{ comment.author }}</span>&nbsp;
              <span class="commentContent">{{ comment.comment }}</span>
            </div>
            <div class="commentTime">
              <span>{{ comment.createDateTime | formatToReadableTime }}</span>
            </div>
          </el-row>
        </el-card>
      </el-col>
      <el-col :md="24" :lg="12">
        <div>
          <div style="margin: 25px 0 20px">
            <i class="el-icon-edit"></i>&nbsp;新评论
          </div>
          <el-form :model="comment" label-width="80px">
            <el-form-item label="姓名">
              <el-input v-model="comment.author"></el-input>
            </el-form-item>
            <el-form-item label="E-mail">
              <el-input v-model="comment.email"></el-input>
            </el-form-item>
            <el-form-item label="评论">
              <el-input type="textarea" v-model="comment.comment" :rows="5"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="createComment">提交</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
h3 {
  font-size: 1.1rem;
  font-weight: 500;
  color: #58666e;
}
div {
  color: #58666e;
}
.el-form-item {
  margin-bottom: 6px;
}
.postMeta {
  font-size: 0.8rem;
  color: #98a6ad;
}
.postContent {
  color: #98a6ad;
  font-size: 0.85rem;
}
.commentArea {
  margin-top: 20px;
}
.commentArea .el-card {
  margin-top: 5px;
}
.commentTime {
  color: #98a6aa;
  font-size: 0.7rem;
  line-height: 1.7;
  min-width: 50px;
  display: flex;
  justify-content: flex-end;
}
.commentAuthor {
  font-size: 0.9rem;
}
.commentContent {
  color: #98a6ad;
  font-size: 0.85rem;
}
</style>

<script>
import { get, post, msg } from "../utils/APIUtil";
import { URLs } from "../context/Consts";
import CommFunc from "../context/CommFunc";
import { mapState, mapActions } from "vuex";
import * as Types from "../store/types";

export default {
  mixins: [CommFunc],
  data() {
    return {
      loading: true,
      postInfo: {
        commentList: []
      },
      comment: {
        author: "",
        email: "",
        comment: "",
        postId: ""
      }
    };
  },
  computed: {
    ...mapState(["blogInfo"])
  },
  methods: {
    getPost: function() {
      get(URLs.post.base + "/" + this.$route.params.id, null, response => {
        this.postInfo = response.data;
        this.loading = false;
      });
    },
    resetCommentForm: function() {
      (this.comment.author = ""),
        (this.comment.email = ""),
        (this.comment.comment = ""),
        (this.comment.postId = "");
    },
    createComment: function() {
      this.comment.postId = this.$route.params.id;
      post(
        URLs.comment.create,
        this.comment,
        () => {
          this.getPost();
          this.resetCommentForm();
        },
        error => this.$message.error(msg(error))
      );
    },
    ...mapActions([Types.SET_BLOG_INFO])
  },
  mounted() {
    this.getPost();
    this.SET_BLOG_INFO();
  }
};
</script>


