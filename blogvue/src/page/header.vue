<!-- 头部 -->
<template>
  <div class="">
    <div class="user">
      <h3>欢迎来到</h3>
      <p>{{ userInfo.username }}</p>
      <h3>的博客</h3>
      <el-button type="danger" icon="el-icon-delete" circle @click="btn">
      </el-button>
    </div>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import { mapState } from "vuex";
export default {
  name: "",
  //import引入的组件需要注入到对象中才能使用
  components: {},
  data() {
    //这里存放数据
    return {
      // userInfo:userInfo
    };
  },
  //监听属性 类似于data概念
  computed: {
    ...mapState(["userInfo"]),
  },
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    btn() {
    //   this.$store.commit("REMOVE_INFO");
    //   this.$router.push("/");
        this.$axios
          .get("http://localhost:8081/logout", {
            headers: {
              Authorization: localStorage.getItem("token"),
            },
          })
          .then((res) => {
            //   _this.$store.commit("REMOVE_INFO");
              _this.$router.push("/");
            console.log("d", res);
          });
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    console.log(this.userInfo.username);
  },
  //生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {},
  beforeCreate() {}, //生命周期 - 创建之前
  beforeMount() {}, //生命周期 - 挂载之前
  beforeUpdate() {}, //生命周期 - 更新之前
  updated() {}, //生命周期 - 更新之后
  beforeDestroy() {}, //生命周期 - 销毁之前
  destroyed() {}, //生命周期 - 销毁完成
  activated() {}, //如果页面有keep-alive缓存功能，这个函数会触发
};
</script>
<style lang='scss' scoped>
//@import url(); 引入公共css类
.user {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>