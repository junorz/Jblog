<template>
  <div v-loading="loading">
    <el-row :gutter="10">
      <el-col :md="24" :lg="18">
        <el-card shadow="never" :key="post.id" v-for="post in postList">
          <h3 @click="showPost(post.id)">{{ post.title }}</h3>
          <p class="postContent">{{ post.content }}</p>
          <el-divider></el-divider>
          <span class="postMeta">
            <i class="el-icon-time"></i>
            &nbsp;{{ post.createDateTime | formatToDate }}
          </span>&nbsp;
          <span class="postMeta">
            <i class="el-icon-chat-dot-round"></i>
            &nbsp;{{ post.commentsCount }}
          </span>
        </el-card>
        <el-row type="flex" justify="center">
          <el-pagination
            layout="prev, pager, next"
            :total="blogInfo.postsCount"
            :pager-count="5"
            :page-size="6"
            small
            background
            @current-change="changePage"
          ></el-pagination>
        </el-row>
      </el-col>
      <el-col :md="24" :lg="6">
        <el-card id="sumInfo" shadow="never">
          <p>
            <i class="el-icon-notebook-1"></i>
            &nbsp;Posts:&nbsp;{{ blogInfo.postsCount }}
          </p>
          <p>
            <i class="el-icon-chat-dot-round"></i>
            &nbsp;Comments:&nbsp;{{ blogInfo.commentsCount }}
          </p>
          <p>
            <i class="el-icon-time"></i>
            &nbsp;Since:&nbsp;{{ blogInfo.since | formatToDate }}
          </p>
        </el-card>
      </el-col>
    </el-row>
    <el-row id="footer" type="flex" justify="center">
      <span>Copyright &copy; 2019. All rights reserved.</span>
    </el-row>
  </div>
</template>

<style scoped>
h3 {
  font-weight: 500;
  cursor: pointer;
}

.postContent {
  color: #98a6ad;
}

.postMeta {
  font-size: 0.8rem;
  color: #98a6ad;
}

.el-card {
  color: #58666e;
  margin-top: 10px;
  padding: 0 5px;
  font-size: 0.85rem;
}

.el-pagination {
  margin-top: 20px;
}

.el-divider {
  margin: 15px 0 15px;
}

#sumInfo {
  color: #58666e;
  margin-top: 10px;
  padding: 0 5px;
  font-size: 0.8rem;
}

#footer {
  color: grey;
  margin: 20px 0;
  font-size: 0.7rem;
}
</style>

<script>
import { get } from "../utils/APIUtil";
import { URLs } from "../context/Consts";
import { mapState, mapActions } from "vuex";
import * as Types from "../store/types";
import CommFunc from "../context/CommFunc";

export default {
  mixins: [CommFunc],
  data() {
    return {
      postList: [],
      loading: true
    };
  },
  computed: {
    ...mapState(["blogInfo"])
  },
  watch: {
    "blogInfo.initialized": function() {
      if (!this.blogInfo.initialized) {
        this.$router.push("/initialize");
      }
    }
  },
  methods: {
    getPosts: function() {
      this.changePage(1);
    },
    changePage: function(pageNum) {
      this.loading = true;
      get(
        URLs.post.base,
        {
          pageNum,
          limit: 6,
          isPostView: false
        },
        response => {
          this.postList = response.data;
          this.loading = false;
        }
      );
    },
    showPost: function(id) {
      this.$router.push({ name: "posts", params: { id } });
    },
    ...mapActions([Types.SET_BLOG_INFO])
  },
  mounted() {
    this.SET_BLOG_INFO();
    this.getPosts();
  }
};
</script>


