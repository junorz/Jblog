<template>
  <div>
    <div id="nav-menu">
      <router-link to="/">{{ blogInfo.blogName }}</router-link>
      <el-divider direction="vertical"></el-divider>&nbsp;
      <span id="subtitle">{{ blogInfo.subtitle }}</span>
    </div>
  </div>
</template>

<style scoped>
#nav-menu {
  height: 65px;
  line-height: 65px;
  background-color: #fff;
  box-shadow: 0 1px 1px rgba(197, 89, 89, 0.08);
  margin: 0;
  outline: 0;
  padding: 0 30px;
  font-size: 0.95rem;
}

#subtitle {
  word-spacing: 2px;
  font-size: 0.8rem;
  color: lightslategray;
}

a {
  color: #58666e;
  text-decoration-line: none;
}
</style>

<script>
import { mapState } from "vuex";

export default {
  computed: {
    ...mapState(["blogInfo"])
  },
  data() {
    return {};
  }
};
</script>


