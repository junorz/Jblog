<template>
  <div>
    <el-card>
      <el-form :model="blog" label-width="120px" v-if="!blogInfo.initialized">
        <el-form-item label="博客名称">
          <el-input v-model="blog.blogName"></el-input>
        </el-form-item>
        <el-form-item label="副标题">
          <el-input v-model="blog.subtitle"></el-input>
        </el-form-item>
        <el-form-item label="管理员名称">
          <el-input v-model="blog.adminName"></el-input>
        </el-form-item>
        <el-form-item label="管理员密码">
          <el-input v-model="blog.password" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input v-model="blog.confirm" show-password></el-input>
        </el-form-item>
        <el-form-item label="私有">
          <el-switch v-model="blog.private" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
        </el-form-item>
        <el-form-item label="阅览密码" v-if="blog.private">
          <el-input v-model="blog.viewPassword" show-password></el-input>
        </el-form-item>
        <el-form-item label="可否评论">
          <el-switch v-model="blog.commentable" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="createBlog">初始化</el-button>
        </el-form-item>
      </el-form>
      <el-alert
        v-if="blogInfo.initialized"
        title="此博客已经初始化"
        type="error"
        show-icon
        :closable="false"
      ></el-alert>
    </el-card>
  </div>
</template>


<style scoped>
</style>

<script>
import { post, msg } from "../utils/APIUtil";
import { URLs } from "../context/Consts";
import { mapState, mapActions } from "vuex";
import * as Types from "../store/types";

export default {
  methods: {
    createBlog: function() {
      post(
        URLs.blog.create,
        this.blog,
        () => {
          this.$router.push("/");
        },
        error => this.$message({ message: msg(error), type: "error" })
      );
    },
    ...mapActions([Types.SET_BLOG_INFO])
  },
  computed: {
    ...mapState(["blogInfo"])
  },
  data() {
    return {
      isInitialized: true,
      blog: {
        blogName: "",
        subtitle: "",
        adminName: "",
        password: "",
        confirm: "",
        private: false,
        viewPassword: "",
        commentable: true
      }
    };
  },
  mounted() {
    this.SET_BLOG_INFO();
  }
};
</script>

