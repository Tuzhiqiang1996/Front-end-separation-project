<!-- 头部 -->
<template>
  <div class="header">
    <div class="user">
      <el-avatar :size="100" :src="userInfo.avatar" @error="errorHandler">
        <img
          src="https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp"
        />
      </el-avatar>
      <!-- <img :src="userInfo.avatar" alt="" class="img" /> -->
    </div>
    <div class="user">
      <h3>欢迎</h3>
      <p>{{ userInfo.username }}</p>
      <h3>来到TuTu的博客</h3>
    </div>

    <div class="user">
      <el-button @click="main"> 主页 </el-button>
      <el-button @click="edit">
        <el-link type="success" href="/edits">发表博客</el-link>
      </el-button>

      <el-button @click="btn"> 登出 </el-button>
      <!-- <el-button>
        <el-link type="success" href="/homeView">页面</el-link>
      </el-button> -->
      <el-button @click="user"> 用户列表 </el-button>
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
      this.$confirm("退出, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axios
            .get("/logout", {
              headers: {
                Authorization: localStorage.getItem("token"),
              },
            })
            .then((res) => {
              if (res.data.code == 200) {
                this.$store.commit("REMOVE_INFO");
                this.$router.push("/");
                // console.log("d", res);
                this.$message({
                  type: "success",
                  message: "退出成功!",
                });
              } else {
                this.$message({
                  type: "error",
                  message: "退出失败!",
                });
              }
            });
        })
        .catch((err) => {
          this.$message({
            type: "error",
            message: err,
          });
        });
    },
    main() {
      this.$router.push("/home");
    },
    edit() {
      // this.$router.push("/edits");
    },
    user() {
      this.$router.push("/userlist");
    },
    errorHandler() {
      return true;
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    // console.log(this.userInfo);
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
.header {
  padding: 20px 0 20px 0;
}
.user {
  display: flex;
  justify-content: center;
  align-items: center;
}
.img {
  width: 100px;
  border-radius: 50%;
}
</style>