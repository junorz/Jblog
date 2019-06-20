<template>
  <div>
    <el-row type="flex" justify="center">
      <el-col :md="24" :lg="12">
        <el-card>
          <el-form :model="loginInfo">
            <el-form-item label="用户名">
              <el-input v-model="loginInfo.username"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="loginInfo.password" show-password></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="login">登录</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>


<style scoped>
</style>

<script>
import { post } from "../utils/APIUtil";
import { URLs } from "../context/Consts";
import { mapActions } from "vuex";
import * as Types from "../store/types";

export default {
  methods: {
    login: function() {
      let formData = new FormData();
      formData.append("username", this.loginInfo.username);
      formData.append("password", this.loginInfo.password);
      post(
        URLs.blog.login,
        formData,
        () => this.$router.push("/admin"),
        () => this.$message.error("认证失败")
      );
    },
    ...mapActions([Types.SET_BLOG_INFO])
  },
  data() {
    return {
      loginInfo: {
        username: "",
        password: ""
      }
    };
  },
  mounted() {
    this.SET_BLOG_INFO();
  }
};
</script>

