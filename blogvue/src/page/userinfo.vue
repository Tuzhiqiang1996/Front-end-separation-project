<!-- 个人信息 -->
<template>
  <div class="">
    <Header />
    <div class="content">
      <el-card class="box-card">
        <div slot="header" style="width: 350px">
          <span>个人信息</span>
        </div>
        <div class="content">
          <el-avatar :size="100" :src="userInfo.avatar"> </el-avatar>
        </div>
        <div class="infobox">
          <p><i class="el-icon-user"></i>用户昵称</p>
          <p>{{ userInfo.username }}</p>
        </div>
        <div class="infobox">
          <p><i class="el-icon-message"></i>用户邮箱</p>
          <p>{{ userInfo.email }}</p>
        </div>
        <div class="infobox">
          <p><i class="el-icon-s-custom"></i>用户角色</p>
          <p>
            {{
              userInfo.status == 0 ? "超级管理员" : `${userInfo.status}级用户`
            }}
          </p>
        </div>
        <div class="infobox">
          <p><i class="el-icon-date"></i>创建时间</p>
          <p>{{ userInfo.created }}</p>
        </div>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>基本信息</span>
        </div>
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="基本信息" name="first">
            <div>
              <el-form
                :model="formInline"
                ref="formInline"
                :rules="rules"
                label-position="right"
                label-width="80px"
                status-icon
              >
                <el-form-item label="用户名称" prop="username">
                  <el-input
                    v-model="formInline.username"
                    placeholder="用户名称"
                  ></el-input>
                </el-form-item>
                <el-form-item label="用户邮箱" prop="email">
                  <el-input
                    v-model="formInline.email"
                    placeholder="用户邮箱"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="onSubmit('formInline')"
                    >保存</el-button
                  >
                </el-form-item>
              </el-form>
            </div>
          </el-tab-pane>
          <el-tab-pane label="修改密码" name="second">
            <el-form
              :model="passwordForm"
              status-icon
              :rules="rulespass"
              ref="passwordForm"
              label-width="100px"
              class="demo-ruleForm"
            >
              <el-form-item label="旧密码" prop="oldpass">
                <el-input
                  type="password"
                  autocomplete="off"
                  v-model="passwordForm.oldpass"
                ></el-input>
              </el-form-item>
              <el-form-item label="新密码" prop="pass">
                <el-input
                  type="password"
                  v-model="passwordForm.pass"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="checkPass">
                <el-input
                  type="password"
                  v-model="passwordForm.checkPass"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  @click="submitpasswordForm('passwordForm')"
                  >提交</el-button
                >
                <el-button @click="resetpasswordForm('passwordForm')"
                  >重置</el-button
                >
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </el-card>
    </div>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import Header from "./header";
import { mapState, mapMutations } from "vuex";
export default {
  name: "",
  //import引入的组件需要注入到对象中才能使用
  components: { Header },
  props: [],
  data() {
    //这里存放数据
    var validatoremail = (rule, value, callback) => {
      // var MobileRegex = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      var MobileRegex = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
      if (value === "") {
        callback(new Error("请输入正确的邮箱"));
      } else if (!MobileRegex.test(value)) {
        callback(new Error("请输入正确的邮箱格式"));
      } else {
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.passwordForm.checkPass !== "") {
          this.$refs.passwordForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass3 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        // if (this.passwordForm.oldpass !== "") {
        //   this.$refs.passwordForm.validateField("oldpass");
        // }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.passwordForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      activeName: "first",
      formInline: {
        username: "",
        email: "",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        email: [
          {
            validator: validatoremail,
            trigger: "blur",
          },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
          { required: true, message: "请输入正确的邮箱格式", trigger: "blur" },
        ],
      },
      passwordForm: {
        pass: "",
        checkPass: "",
        oldpass: "",
      },
      rulespass: {
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
        oldpass: [{ validator: validatePass3, trigger: "blur" }],
      },
    };
  },
  //监听属性 类似于data概念
  computed: { ...mapState(["userInfo"]) },
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    ...mapMutations({
      SET_NAME: "SET_NAME",
      SET_EMAIL: "SET_EMAIL",
      SET_USERINFO: "SET_USERINFO",
    }),
    handleClick(tab, event) {
      // console.log(tab, event);
    },
    //修改名称 邮箱
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let url = "/user/edit";
          let params = JSON.stringify(this.formInline);

          this.$axios
            .post(url, params, {
              headers: {
                Authorization: localStorage.getItem("token"),
                "Content-Type": "application/json;charset=utf-8",
              },
            })
            .then((res) => {
              if (res.data.code == 200) {
                // this.SET_USERINFO(res.data.data);
                // this.SET_EMAIL(res.data.data);
                this.SET_USERINFO(res.data.data);

                this.$message({
                  message: res.data.msg,
                  center: true,
                  type: "success",
                });
              } else {
                this.$message({
                  message: res.data.msg,
                  center: true,
                  type: "error",
                });
              }
              console.log(res);
            })
            .catch((err) => {
              console.error(err);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });

    },
    //修改密码
    submitpasswordForm(formName) {
      console.log(this.passwordForm);
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let url = `/user/pass?id=${this.userInfo.id}&newpassword=${this.passwordForm.checkPass}&password=${this.passwordForm.oldpass}`;
          // let params = JSON.stringify(this.passwordForm);
          let params = {
            id: this.userInfo.id,
            password: this.passwordForm.oldpass,
            newpassword: this.passwordForm.checkPass,
          };
          let data = JSON.stringify(params);
          this.$axios
            .post(url, {
              headers: {
                Authorization: localStorage.getItem("token"),
                "Content-Type": "application/json;charset=utf-8",
              },
            })
            .then((res) => {
              if (res.data.code == 200) {
                this.SET_USERINFO(res.data.data);

                this.$message({
                  message: res.data.msg,
                  center: true,
                  type: "success",
                });
              } else {
                this.$message({
                  message: res.data.msg,
                  center: true,
                  type: "error",
                });
              }
              console.log(res);
            })
            .catch((err) => {
              console.error(err);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetpasswordForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.formInline.username = this.userInfo.username;
    this.formInline.email = this.userInfo.email;
    this.formInline.status = this.userInfo.status;
    this.formInline.avatar = this.userInfo.avatar;
    this.formInline.id = this.userInfo.id;
    this.passwordForm.id = this.userInfo.id;
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
.clearfix {
  width: 400px;
}
.content {
  display: flex;
  justify-content: center;
}
.box-card {
  margin: 20px;
}
.infobox {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #ebeef5;
}
</style>