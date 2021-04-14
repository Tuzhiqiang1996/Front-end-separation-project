<!-- 详情 -->
<template>
  <div class="mblog" v-loading="loading">
    <h2>{{ formName.title }}</h2>
    <div class="top">
      <el-link icon="el-icon-edit">
        <router-link
          :to="{ name: 'editblogid', params: { blogId: id } }"
          style="color: #fff"
        >
          编辑
        </router-link>
      </el-link>
      <el-button
        type="danger"
        style="margin: 0 10px"
        icon="el-icon-delete"
        circle
        @click="btndelete"
      ></el-button>
      <div>
        <el-input
          v-focus
          v-if="inputVisible"
          class="input-new-tag"
          v-model="formName.label"
          ref="saveTagInput"
          size="small"
          @blur="handleInputConfirm"
        ></el-input>
        <el-tag v-else @click="handleClose" effect="plain">
          {{ formName.label }}
        </el-tag>
      </div>
    </div>
    <div style="max-width: 960px">
      <div style="position: fixed; top: 5%; left: 5%">
        <el-button
          type="primary"
          icon="el-icon-back"
          circle
          @click="back"
        ></el-button>
      </div>
      <div>
        <p>
          {{ formName.description }}
        </p>
        <el-divider></el-divider>
        <div class="markdown-body" v-html="formName.content"></div>
      </div>
    </div>
    <el-backtop target=""></el-backtop>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
// import 'mavon-editor/dist/css/index.css'
//  import 'github-markdown-css'
import { mapState } from "vuex";
export default {
  name: "blogId",
  //import引入的组件需要注入到对象中才能使用
  components: {},
  data() {
    //这里存放数据
    return {
      loading: true,
      inputVisible: false,
      formName: {
        id: "",
        content: "",
        title: "",
        status: "",
        label: "",
        description: "",
      },
    };
  },
  directives: {
    //注册一个局部的自定义指令 v-focus
    focus: {
      // 指令的定义
      inserted: function (el) {
        // 聚焦元素
        el.querySelector("input").focus();
      },
    },
  },
  //监听属性 类似于data概念
  computed: {
    ...mapState(["userInfo"]),
  },
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    back() {
      this.$router.go(-1);
    },
    btndelete() {
      let url = "/delete/";
      //同级
      // if(this.status==this.userInfo.status){
      //最高级 0
      if (this.userInfo.status === 0) {
        this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.$axios
              .delete(url + this.id)
              .then((res) => {
                // console.log(res);
                if (res.data.code == 200) {
                  this.$router.push({
                    path: "/home",
                  });
                  this.$message({
                    message: "删除成功！",
                    showClose: true,
                    type: "success",
                  });
                } else {
                  this.$message({
                    message: "删除失败！",
                    showClose: true,
                    type: "error",
                  });
                }
              })
              .catch((err) => {
                console.error(err);
                this.$message({
                  message: err,
                  showClose: true,
                  type: "error",
                });
              });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      } else {
        this.$message({
          message: "没有权限！",
          showClose: true,
          type: "error",
        });
      }
    },
    handleClose() {
      this.inputVisible = true;
    },
    handleInputConfirm() {
      this.inputVisible = false;
      this.editlabel();
    },
    //修改标签
    editlabel() {
      let data = JSON.stringify(this.formName);

      this.$axios
        .post("/blog/fix", data, {
          headers: {
            Authorization: localStorage.getItem("token"),
            "Content-Type": "application/json;charset=utf-8",
          },
        })
        .then((res) => {
          // console.log(this.ruleForm);
          if (res.data.code == 200) {
            this.frist();
          }
        });
    },
    //chushi
    frist() {
      // console.log(this.userInfo.status);
      // console.log(this.$route.params.blogid);
      let url = "http://localhost:8081/blog/";
      let id = this.$route.params.blogid;
      this.id = id;
      this.$axios
        .get(url + id)
        .then((res) => {
          const { code, data } = res.data;

          if (code == 200) {
            // this.content = data.content;
            // this.content = data.content.replace(/\\n/gm, "<br/>");
            this.formName.id = data.id;
            this.formName.title = data.title;
            this.formName.status = data.status;
            this.formName.label = data.label;
            this.formName.description = data.description;
            this.loading = false;
            var MardownIt = require("markdown-it");
            var md = new MardownIt();

            var result = md.render(data.content);
            this.formName.content = result;
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
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.frist();
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
  // width: 80%;
  min-width: 960px;
  align-items: center;
}
.mblog {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 100%;
  min-height: 700px;
  padding: 20px 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
