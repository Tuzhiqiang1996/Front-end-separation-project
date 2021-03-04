<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
#app {
  width: 100%;
  height: 100%;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}
body {
  overflow-x: hidden;
  height: 100%;
  margin: 0;
  padding: 0;
  background: url("./assets/image/11.jpg")  ;
  background-size:   100%;
}
html {
  /* height: 100%; */
}
</style>
