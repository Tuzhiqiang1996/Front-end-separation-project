<template>
  <div class="login-container">
    <div class="layer">
      <div class="some-space">
        <div class="form">
          <h2>{{ log ? "登录许可" : "注册" }}</h2>
          <div class="item">
            <i class="iconfont icon-user"></i>
            <input
              autocomplete="off"
              type="text"
              class="input"
              v-model="userName"
              placeholder="请输入用户名"
            />
          </div>
          <div class="item">
            <i class="iconfont icon-password"></i>
            <input
              autocomplete="off"
              type="password"
              class="input"
              v-model="userPwd"
              maxlength="20"
              @keyup.enter="login"
              placeholder="请输入密码"
            />
          </div>
          <div v-if="!log">
            <div class="item">
              <i class="iconfont icon-password"></i>
              <input
                autocomplete="off"
                type="password"
                class="input"
                v-model="avatar"
                maxlength="20"
                @keyup.enter="login"
                placeholder="作者"
              />
            </div>
          </div>
          <el-button
            :plain="true"
            class="loginBtn"
            :disabled="isLoginAble"
            @click="login"
            v-if="log"
          >
            立即登录
          </el-button>
          <el-button
            v-else
            :plain="true"
            class="loginBtn"
            :disabled="isLoginAble"
            @click="regina"
          >
            注册
          </el-button>
          <div class="tip">
            <p>默认用户名：markerhub ，默认密码：111111</p>
            <ediv @click="toggle">
              <p>{{ log ? "还没账号？" : "已有账号！" }}</p>
            </ediv>
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
export default {
  name: "Login",
  components: {
    BgAnimation,
  },
  data() {
    return {
      userName: "markerhub",
      userPwd: "111111",
      log: true,
      avatar: null,
      email: "",
      status: null,
      created: null,
    };
  },
  computed: {
    isLoginAble() {
      // return !(this.userName && this.userPwd);
    },
  },
  created() {},
  mounted() {},
  methods: {
    login() {
      if (this.userName !== "markerhub" && this.userPwd !== "111111") {
        this.$message({
          message: "请输入正确的用户名和密码",
          center: true,
          type: "error",
        });
        console.log(this.userName, this.userPwd);
      } else {
        //正确的账号: doudou  密码:123456
        let url = "http://101.96.128.94:9999/data/user/login.php";
        let options = {
          username: this.userName,
          password: this.userPwd,
        };
        this.$axios
          .post("http://localhost:8081/login", options)
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
      }
    },
    ...mapMutations({ SET_TOKEN: "SET_TOKEN", SET_USERINFO: "SET_USERINFO" }),
    regina() {
      // http://localhost:8081/user/save
      //     "id": null,
      // "username": "markerHub1",
      // "avatar": null,
      // "email": "16202222@qq.com",
      // "password": "123456",
      // "status": null,
      // "created": null,
      // "lastLogin": null
    },
    toggle() {
      this.log = !this.log;
    },
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
        margin-bottom: 25px;
        border-bottom: 1px solid #d3d7f7;
        i {
          color: #d3d7f7;
          margin-right: 10px;
        }
      }
      h2 {
        text-align: center;
        font-weight: normal;
        font-size: 26px;
        color: #d3d7f7;
        padding-bottom: 35px;
      }
      .input {
        font-size: 16px;
        line-height: 30px;
        width: 100%;
        color: #d3d7f7;
        outline: none;
        border: none;
        background-color: rgba(0, 0, 0, 0);
        padding: 10px 0;
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
        margin-top: 10px;
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
