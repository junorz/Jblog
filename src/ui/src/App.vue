<template>
  <div id="app">
    <Header/>
    <div id="view-wrapper">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import Header from "./components/Header";

export default {
  components: { Header }
};
</script>

<style>
body {
  background-color: #eee;
}
#view-wrapper {
  padding: 20px;
}
</style>
