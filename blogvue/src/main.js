// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";
import axios from "axios";
import ElementUI from "element-ui";
import store from "./store/store";
import VueParticles from "vue-particles";
import mavonEditor from 'mavon-editor'
import "element-ui/lib/theme-chalk/index.css";
import 'mavon-editor/dist/css/index.css'
axios.defaults.baseURL = 'http://localhost:8081/'; // 填写后台请求统一的地址

Vue.use(mavonEditor)
Vue.use(VueParticles);
Vue.use(ElementUI);
Vue.config.productionTip = false;
Vue.prototype.$axios = axios; //this.$axios
/* eslint-disable no-new */
new Vue({
  el: "#app",
  router,
  store,
  components: { App },
  template: "<App/>"
});
