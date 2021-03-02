<!-- 详情 -->
<template>
  <div
    class=""
    style="
      justify-content: center;
      align-items: center;
      display: inline-flex;
      height: 100%;
      background: #eee;
      width: 100%;
      flex-direction: column;
    "
  >
    <div class="top">
      <el-link icon="el-icon-edit">
        <router-link :to="{ name: 'editblogid', params: { blogId: id } }">
          编辑
        </router-link>
      </el-link>
      <el-button
        type="danger"
        style="margin-left: 10px;"
        icon="el-icon-delete"
        circle
        @click="btndelete"
      ></el-button>
    </div>
    <div style="width: 80%">
      <div style="position: fixed; top: 5%">
        <el-button
          type="primary"
          icon="el-icon-back"
          circle
          @click="back"
        ></el-button>
      </div>
      <div>
        <el-card>
          <h4>{{ title }}</h4>
          <div class="cmm-wrappe" v-html="content"></div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';

export default {
  name: "blogId",
  //import引入的组件需要注入到对象中才能使用
  components: {},
  data() {
    //这里存放数据
    return {
      id: "",
      content: "",
      title: "",
    };
  },
  //监听属性 类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    back() {
      this.$router.go(-1);
    },
    btndelete() {
      let url = "http://localhost:8081/delete/";
      this.$axios
        .delete(url + this.id)
        .then((res) => {
          console.log(res);
          if (res.data.code == 200) {
            this.$router.push({
              path: "/home",
            });
            this.$message({
              message: "删除成功！",
              showClose: true,
              type: "success",
            });
          }else{
             this.$message({
              message: "删除失败！",
              showClose: true,
              type: "error",
            });
          }
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    console.log(this.$route.params.blogid);
    let url = "http://localhost:8081/blog/";
    let id = this.$route.params.blogid;
    this.id = id;
    this.$axios
      .get(url + id)
      .then((res) => {
        const { code, data } = res.data;
        if (code == 200) {
          // this.content = data.content;
          this.content = data.content.replace(/\\n/gm, "<br/>");
          this.title = data.title;
        } else {
          this.$message({
            message: res.data.msg,
            center: true,
            type: "error",
          });
        }
      })
      .catch((err) => {
        console.error(err);
      });
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
.cmm-wrapper {
  white-space: pre-wrap; //解决的关键就是这一句,
  line-height: 40px;
  color: #000032;
  font-size: 28px; /*px*/
}
.top {
  display: flex;
  padding: 10px;
  width: 80%;
}
</style>