
<template>
  <div class="login-container">
    <div class="layer">
      <div class="some-space">
        <div class="form">
          <div class="imgbox" v-if="!log">
            <div v-for="(item, index) in Image" :key="index">
              <el-radio v-model="radioimg" :label="index" border @change="changeimg"
                ><img :src="item.img" alt="" srcset="" class="imgs" />
              </el-radio>
            </div>
          </div>
          <h2>{{ log ? "登录许可" : "注册" }}</h2>
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
            <div class="item">
              <i class="iconfont icon-user"></i>
              <el-form-item prop="userName" class="itemwidth">
                <el-input
                  autocomplete="off"
                  type="text"
                  class="input"
                  v-model="ruleForm.userName"
                  placeholder="请输入用户名"
              /></el-form-item>
            </div>
            <div class="item">
              <i class="iconfont icon-password"></i>
              <el-form-item prop="userPwd" class="itemwidth">
                <el-input
                  autocomplete="off"
                  type="password"
                  class="input"
                  v-model="ruleForm.userPwd"
                  maxlength="20"
                  @keyup.enter="login"
                  placeholder="请输入密码"
              /></el-form-item>
            </div>
            <div v-if="!log">
              <!-- <div class="item">
                <i class="iconfont icon-password"></i>
                <el-form-item prop="avatar" class="itemwidth">
                  <el-input
                    autocomplete="off"
                    type="text"
                    class="input"
                    v-model="ruleForm.avatar"
                    maxlength="20"
                    @keyup.enter="regina"
                    placeholder="作者"
                /></el-form-item>
              </div> -->

              <div class="item">
                <i class="iconfont icon-password"></i>
                <el-form-item prop="email" class="itemwidth">
                  <el-input
                    autocomplete="off"
                    type="email"
                    class="input"
                    v-model="ruleForm.email"
                    maxlength="20"
                    @keyup.enter="regina"
                    placeholder="请输入邮箱"
                  />
                </el-form-item>
              </div>
            </div>
            <div v-if="!log" class="permissions">
              <el-radio v-model="radio" label="1">权限1</el-radio>
              <el-radio v-model="radio" label="2">权限2</el-radio>
            </div>
            <el-button
              :plain="true"
              class="loginBtn"
              :disabled="isLoginAble"
              @click="login('ruleForm')"
              v-if="log"
            >
              立即登录
            </el-button>
            <el-button
              v-else
              :plain="true"
              class="loginBtn"
              :disabled="isLoginAble"
              @click="regina('ruleForm')"
            >
              注册
            </el-button>
          </el-form>

          <div class="tip">
            <p>默认用户名：markerhub ，默认密码：111111</p>
            <div @click="toggle">
              <p>{{ log ? "还没账号？" : "已有账号！" }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <vue-particles
      color="#6495ED"
      :particleOpacity="0.7"
      :particlesNumber="80"
      shapeType="circle"
      :particleSize="4"
      linesColor="#6495ED"
      :linesWidth="1"
      :lineLinked="true"
      :lineOpacity="0.6"
      :linesDistance="150"
      :moveSpeed="3"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="push"
    >
    </vue-particles>

    <BgAnimation />
  </div>
</template>

<script>
import BgAnimation from "@/bgAnimation";
// import { login } from "@/api/login.js";
import { mapMutations } from "vuex";
import Image from "../utils/image.json";
export default {
  name: "Login",
  components: {
    BgAnimation,
  },
  data() {
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
    return {
      radio: "1",
      radioimg: 1,
      log: true,
      Image: Image,
      ruleForm: {
        userName: "markerhub",
        userPwd: "111111",
        avatar: null,
        email: "",
        status: null,
        created: null,
      },
      rules: {
        //全部效验吧
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
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        userPwd: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
    };
  },
  computed: {
    isLoginAble() {
      // return !(this.userName && this.userPwd);
    },
  },
  created() {
    // console.log(Image);
  },
  mounted() {},
  watch: {
    log: function (newVal, oldVal) {
      console.log(newVal, oldVal);
      this.ruleForm.userName = newVal ? "markerhub" : "";
      this.ruleForm.userPwd = newVal ? "111111" : "";
      this.ruleForm.avatar = Image[this.radioimg].img;
    },
  },
  methods: {
    login(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //正确的账号: doudou  密码:123456
          let url = "http://101.96.128.94:9999/data/user/login.php";
          let options = {
            username: this.ruleForm.userName,
            password: this.ruleForm.userPwd,
          };
          this.$axios
            .post("/login", options)
            .then((res) => {
              console.log(res);
              const { code } = res.data;
              if (code == 200) {
                console.log(res);
                const jwt = res.headers["authorization"];
                const userInfo = res.data.data;
                // // 把数据共享出去
                // this.$store.commit("SET_TOKEN", jwt);
                // this.$store.commit("SET_USERINFO", userInfo);
                this.SET_TOKEN(jwt);
                this.SET_USERINFO(userInfo);
                this.$router.push({
                  path: "/home",
                });
                this.$message({
                  message: "登录成功！",
                  showClose: true,
                  type: "success",
                });
              } else {
                this.$message({
                  message: res.data.msg,
                  center: true,
                  type: "error",
                });
              }
            })
            .catch((err) => {
              console.error("抛出异常" + err);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    ...mapMutations({ SET_TOKEN: "SET_TOKEN", SET_USERINFO: "SET_USERINFO" }),
    regina(ruleForm) {
      console.log(666)
      this.$refs[ruleForm].validate((valid) => {
        console.log(661)
        if (valid) {
          console.log(662)
          let options = {
            username: this.ruleForm.userName,
            password: this.ruleForm.userPwd,
            avatar: this.ruleForm.avatar,
            email: this.ruleForm.email,
            status: this.radio,
          };
          this.$axios
            .post("/regina", options)
            .then((res) => {
              const { code } = res.data;
              if (code == 200) {
                // this.$router.push({
                //   path: "/home",
                // });
                this.log = true;
                   this.ruleForm.userName=options.username
              this.ruleForm.userPwd=options.username
                this.$message({
                  message: "注册成功！",
                  showClose: true,
                  type: "success",
                });
              }
                this.$message({
                  message: res.data.msg,
                  showClose: true,
                  type: "error",
                });

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
    toggle() {
      this.log = !this.log;
      this.ruleForm.userName = "";
      this.ruleForm.userPwd = "";
      this.ruleForm.avatar = "";
      this.ruleForm.email = "";
    },
    changeimg(img){
      // console.log(Image[img].img)
      this.ruleForm.avatar = Image[img].img;

    }
  },
};
</script>

<style lang="scss" scoped>
.login-container {
  .layer {
    position: absolute;
    height: 100%;
    width: 100%;
  }
  #particles-js {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 1000;
  }
  .some-space {
    color: white;
    font-weight: 100;
    letter-spacing: 2px;
    position: absolute;
    top: 50%;
    left: 50%;
    z-index: 1001;
    -webkit-transform: translate3d(-50%, -50%, 0);
    transform: translate3d(-50%, -50%, 0);

    -ms-animation: cloud 2s 3s ease-in infinite alternate;
    -moz-animation: cloud 2s 3s ease-in infinite alternate;
    -webkit-animation: cloud 2s 3s ease-in infinite alternate;
    -o-animation: cloud 2s 3s ease-in infinite alternate;
    -webkit-animation: cloud 2s 3s ease-in infinite alternate;
    animation: cloud 2s 3s ease-in infinite alternate;

    .form {
      width: 460px;
      height: auto;
      background: rgba(36, 36, 85, 0.5);
      margin: 0 auto;
      padding: 35px 30px 25px;
      box-shadow: 0 0 25px rgba(255, 255, 255, 0.5);
      border-radius: 10px;
      .item {
        display: flex;
        align-items: center;
        // margin-bottom: 25px;
        border-bottom: 1px solid #d3d7f7;
        height: 70px;
        i {
          color: #d3d7f7;
          margin-right: 10px;
        }
        .itemwidth {
          width: 100%;
          >>> .el-form-item {
            margin: 0;
          }
        }
      }
      .input >>> .el-input__inner {
        background-color: transparent !important ;
        border: 0;
        color: #fff !important;
      }
      .imgbox {
        display: flex;
        justify-content: space-evenly;
        >>> .el-radio__input {
          display: none;
        }
        >>>.el-radio.is-bordered{
          padding: 0;
          height: 40px;
          width: 40px;
        }
        >>>.el-radio__label{
          padding: 0;
        }
        .imgs {
          width: 38px;
          height: 38px;
          border-radius:4px;
        }
      }
      h2 {
        text-align: center;
        font-weight: normal;
        font-size: 26px;
        color: #d3d7f7;
        // padding-bottom: 35px;
      }
      .input {
        font-size: 16px;
        line-height: 30px;
        width: 100%;
        color: #d3d7f7;
        outline: none;
        border: none;
        background-color: rgba(0, 0, 0, 0);
        // padding: 15px 0;
      }
      .loginBtn {
        width: 100%;
        padding: 12px 0;
        border: 1px solid #d3d7f7;
        font-size: 16px;
        color: #d3d7f7;
        cursor: pointer;
        background: transparent;
        border-radius: 4px;
        margin-top: 20px;
        &:hover {
          color: #fff;
          background: #0090ff;
          border-color: #0090ff;
        }
      }
      .tip {
        font-size: 12px;
        padding-top: 20px;
        display: flex;
        justify-content: space-between;
      }
    }
  }
}

input::-webkit-input-placeholder {
  color: #d3d7f7;
}
input::-moz-placeholder {
  /* Mozilla Firefox 19+ */
  color: #d3d7f7;
}
input:-moz-placeholder {
  /* Mozilla Firefox 4 to 18 */
  color: #d3d7f7;
}
input:-ms-input-placeholder {
  /* Internet Explorer 10-11 */
  color: #d3d7f7;
}

@-ms-keyframes cloud {
  0% {
    -ms-transform: translate(-50%, -50%);
  }
  40% {
    opacity: 1;
  }
  60% {
    opacity: 1;
  }
  100% {
    -ms-transform: translate(-50%, -40%);
  }
}
@-moz-keyframes cloud {
  0% {
    -moz-transform: translate(-50%, -50%);
  }
  40% {
    opacity: 1;
  }
  60% {
    opacity: 1;
  }
  100% {
    -moz-transform: translate(-50%, -40%);
  }
}
@-o-keyframes cloud {
  0% {
    -o-transform: translate(-50%, -50%);
  }
  40% {
    opacity: 1;
  }
  60% {
    opacity: 1;
  }
  100% {
    -o-transform: translate(-50%, -40%);
  }
}
@-webkit-keyframes cloud {
  0% {
    -webkit-transform: translate(-50%, -50%);
  }
  40% {
    opacity: 1;
  }
  60% {
    opacity: 1;
  }
  100% {
    -webkit-transform: translate(-50%, -40%);
  }
}
@keyframes cloud {
  0% {
    transform: translate(-50%, -50%);
  }
  40% {
    opacity: 1;
  }
  60% {
    opacity: 1;
  }
  100% {
    transform: translate(-50%, -40%);
  }
}
</style>
