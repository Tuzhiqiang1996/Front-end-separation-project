<!-- 发表文章 -->
<template>
  <div class="editbox">
    <Header />
    <div class="edit">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="标题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>

        <el-form-item label="摘要" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <mavon-editor v-model="ruleForm.content"></mavon-editor>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >立即创建</el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-backtop target=""></el-backtop>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import Header from "./header";
import { mapState } from "vuex";
export default {
  name: "blogsedit",
  //import引入的组件需要注入到对象中才能使用
  components: { Header },
  data() {
    //这里存放数据
    return {
      ruleForm: {
        // id: "",
        title: "",
        description: "",
        content: "",
        status: "",
      },
      rules: {
        title: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
          {
            min: 3,
            max: 25,
            message: "长度在 3 到 25 个字符",
            trigger: "blur",
          },
        ],
        description: [
          { required: true, message: "请输入摘要", trigger: "blur" },
        ],
        content: [{ required: true, message: "请输入内容", trigger: "blur" }],
      },
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
    submitForm(formName) {
      let ISNAME = this.$route.params.blogId  ? "/blog/edit":"/blog/add"

      let data = JSON.stringify(this.ruleForm);
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this;
          this.$axios
            .post(ISNAME, data, {
              headers: {
                Authorization: localStorage.getItem("token"),
                "Content-Type": "application/json;charset=utf-8",
              },
            })
            .then((res) => {
              // console.log(this.ruleForm);
              _this.$alert("操作成功", "提示", {
                confirmButtonText: "确定",
                callback: (action) => {
                  _this.$router.push("/home");
                },
              });
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
      console.log(JSON.stringify(this.ruleForm));
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    const blogId = this.$route.params.blogId;
    //将当前的的用户权限传给status
    this.ruleForm.status = this.userInfo.status;
    console.log(this.$route.params);
    const _this = this;
    if (blogId) {
      this.$axios.get("http://localhost:8081/blog/" + blogId).then((res) => {
        const blog = res.data.data;
        console.log(blog);
        _this.ruleForm.id = blog.id;
        _this.ruleForm.title = blog.title;
        _this.ruleForm.description = blog.description;
        _this.ruleForm.content = blog.content;
      });
    }
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
.editbox {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}
.edit {
  width: 80%;
  >>> .el-form-item__label {
    color: aliceblue;
  }
}
</style>