<!-- 评论点赞回复 -->
<template>
  <div class="">
    <div class="contentcom">
      <el-collapse-transition>
        <div v-show="true">
          <div class="savebox">
            <div style="height: 100%; line-height: 2.5">
              <el-avatar size="medium" :src="useravatar" @error="errorHandler">
                <img
                  src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"
                />
              </el-avatar>
            </div>
            <el-input
              type="textarea"
              placeholder="请输入内容"
              :autosize="{ minRows: 2, maxRows: 4 }"
              v-model="content"
              maxlength="500"
              show-word-limit
              style="margin-left: 8px"
            >
            </el-input>
            <el-button
              style="margin: 0 0 0 10px"
              type="danger"
              @click="postcomment"
              >提交</el-button
            >
          </div>
          <!-- 回复列表 -->
          <div class="combox">
            <div
              v-for="(item, index) in comlist"
              :key="item.id"
              class="commentsub"
            >
              <el-avatar size="medium" :src="item.fromAvatar"></el-avatar>
              <span style="color: #606266; vertical-align: super"
                >{{ item.fromName }}：</span
              >
              <span style="color: #606266; vertical-align: super">{{
                item.createTime
              }}</span>
              <!-- 一级回复 -->
              <el-button
                type="text"
                style="vertical-align: super"
                @click="reply(item)"
                >回复</el-button
              >
              <el-button
                type="text"
                @click="zanlike(item, index)"
                style="vertical-align: super"
              >
                <svg
                  t="1618903754460"
                  class="icon"
                  viewBox="0 0 1024 1024"
                  version="1.1"
                  xmlns="http://www.w3.org/2000/svg"
                  p-id="1894"
                  width="16"
                  height="16"
                >
                  <path
                    d="M884.875894 423.143253L646.970506 423.143253c92.185562-340.464205-63.516616-357.853247-63.516616-357.853247-65.993017 0-52.312436 52.182476-57.3031 60.881602 0 166.502152-176.849824 296.971645-176.849824 296.971645l0 472.171899c0 46.607504 63.516616 63.393819 88.433098 63.393819l357.452111 0c33.641191 0 61.036122-88.224344 61.036122-88.224344 88.434122-300.70569 88.434122-390.177444 88.434122-390.177444C944.657442 418.179195 884.875894 423.143253 884.875894 423.143253L884.875894 423.143253 884.875894 423.143253zM884.875894 423.143253"
                    :fill="haszan(item.id) ? '#d81e06' : '#bfbfbf'"
                    p-id="1895"
                  ></path>
                  <path
                    d="M251.671415 423.299819L109.214912 423.299819c-29.420053 0-29.873378 28.89612-29.873378 28.89612l29.420053 476.202703c0 30.309306 30.361495 30.309306 30.361495 30.309306L262.420223 958.707948c25.686009 0 25.458835-20.049638 25.458835-20.049638L287.879058 459.411271C287.879058 422.837284 251.671415 423.299819 251.671415 423.299819L251.671415 423.299819 251.671415 423.299819zM251.671415 423.299819"
                    :fill="haszan(item.id) ? '#d81e06' : '#bfbfbf'"
                    p-id="1896"
                  ></path>
                </svg>
                <span style="vertical-align: text-top">{{ item.likeNum }}</span>
              </el-button>
              <el-collapse
                v-model="activeName"
                accordion
                @change="childlist(item)"
              >
                <el-collapse-item :title="item.content" :name="item.id">
                  <template slot="title">
                    {{ item.content }}
                  </template>
                  <!-- :style="!isdisabled && height0" -->
                  <div v-if="replydata.length">
                    <div
                      v-for="(replist, index) in replydata"
                      :key="replist.id"
                    >
                      <el-avatar
                        size="medium"
                        :src="replist.fromAvatar"
                      ></el-avatar>
                      <span style="color: #606266; vertical-align: super">{{
                        replist.fromName
                      }}</span>
                      <span style="color: #606266; vertical-align: super"
                        >回复</span
                      >
                      <el-avatar
                        size="medium"
                        :src="replist.toAvatar"
                      ></el-avatar>
                      <span style="color: #606266; vertical-align: super"
                        >{{ replist.toName }}：</span
                      >
                      <span style="color: #222226; vertical-align: super">{{
                        replist.content
                      }}</span>
                      <span style="color: #606266; vertical-align: super">{{
                        replist.createTime.split("T")[0]
                      }}</span>
                      <!-- 对第一个人的回复 -->
                      <el-button
                        type="text"
                        style="vertical-align: super"
                        class=""
                        @click="btnreplyangle(replist)"
                        >回复</el-button
                      >
                      <el-button
                        type="text"
                        @click="zanlike2(replist, index)"
                        style="vertical-align: super"
                      >
                        <svg
                          t="1618903754460"
                          class="icon"
                          viewBox="0 0 1024 1024"
                          version="1.1"
                          xmlns="http://www.w3.org/2000/svg"
                          p-id="1894"
                          width="16"
                          height="16"
                        >
                          <path
                            d="M884.875894 423.143253L646.970506 423.143253c92.185562-340.464205-63.516616-357.853247-63.516616-357.853247-65.993017 0-52.312436 52.182476-57.3031 60.881602 0 166.502152-176.849824 296.971645-176.849824 296.971645l0 472.171899c0 46.607504 63.516616 63.393819 88.433098 63.393819l357.452111 0c33.641191 0 61.036122-88.224344 61.036122-88.224344 88.434122-300.70569 88.434122-390.177444 88.434122-390.177444C944.657442 418.179195 884.875894 423.143253 884.875894 423.143253L884.875894 423.143253 884.875894 423.143253zM884.875894 423.143253"
                            :fill="haszan2(replist.id) ? '#d81e06' : '#bfbfbf'"
                            p-id="1895"
                          ></path>
                          <path
                            d="M251.671415 423.299819L109.214912 423.299819c-29.420053 0-29.873378 28.89612-29.873378 28.89612l29.420053 476.202703c0 30.309306 30.361495 30.309306 30.361495 30.309306L262.420223 958.707948c25.686009 0 25.458835-20.049638 25.458835-20.049638L287.879058 459.411271C287.879058 422.837284 251.671415 423.299819 251.671415 423.299819L251.671415 423.299819 251.671415 423.299819zM251.671415 423.299819"
                            :fill="haszan2(replist.id) ? '#d81e06' : '#bfbfbf'"
                            p-id="1896"
                          ></path>
                        </svg>
                        <span style="vertical-align: text-top">{{
                          replist.likenum
                        }}</span>
                      </el-button>
                      <!-- 是否可移植 -->
                      <el-collapse
                        v-model="replyangle"
                        accordion
                        @change="btnreplychange(replist)"
                        class="replychild"
                      >
                        <el-collapse-item :name="replist.id">
                          <div class="replycomment">
                            <el-avatar
                              size="medium"
                              :src="useravatar"
                              @error="errorHandler"
                            >
                              <img
                                src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"
                              />
                            </el-avatar>
                            <el-form
                              :model="formInline"
                              class="demo-form-reply"
                            >
                              <el-form-item>
                                <el-input
                                  type="textarea"
                                  maxlength="500"
                                  show-word-limit
                                  :autosize="{ minRows: 2, maxRows: 4 }"
                                  v-model="formInline.usercoment"
                                  :placeholder="'回复：' + replist.fromName"
                                ></el-input>
                              </el-form-item>
                              <el-form-item>
                                <el-button
                                  type="info"
                                  plain
                                  @click="Cancelcomment"
                                  >取消评论</el-button
                                >
                                <el-button type="danger" @click="Submitcomments"
                                  >提交评论</el-button
                                >
                              </el-form-item>
                            </el-form>
                          </div>
                        </el-collapse-item>
                      </el-collapse>
                    </div>
                  </div>
                  <div v-else>无回复</div>
                </el-collapse-item>
              </el-collapse>
              <!-- 移植到外部 -->
              <el-collapse
                v-model="activeName2"
                accordion
                @change="btnreplychange2(item)"
                class="replychild"
              >
                <el-collapse-item :name="item.id">
                  <div class="replycomment">
                    <el-avatar
                      size="medium"
                      :src="useravatar"
                      @error="errorHandler"
                    >
                      <img
                        src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"
                      />
                    </el-avatar>
                    <el-form :model="formInline" class="demo-form-reply">
                      <el-form-item>
                        <el-input
                          type="textarea"
                          maxlength="500"
                          show-word-limit
                          :autosize="{ minRows: 2, maxRows: 4 }"
                          v-model="formInline.usercoment"
                          :placeholder="'回复：' + item.fromName"
                        ></el-input>
                      </el-form-item>
                      <el-form-item>
                        <el-button type="info" plain @click="Cancelcomment2"
                          >取消评论</el-button
                        >
                        <el-button type="danger" @click="Submitcomments"
                          >提交评论</el-button
                        >
                      </el-form-item>
                    </el-form>
                  </div>
                </el-collapse-item>
              </el-collapse>
            </div>
          </div>
        </div>
      </el-collapse-transition>
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
  props: ['iscontens'],
  data() {
    //这里存放数据
    return {
      loading: true,
      inputVisible: false,
      iszan: false,
      iszans: [],
      iszans2: [],
      isdisabled: false, //是否有回复
      zanNum: 0, //赞
      commnum: 0, //评论数
      readynum: 0, //文章阅读量
      formName: {
        id: "",
        content: "",
        title: "",
        status: "",
        label: "",
        description: "",
      },
      content: "", //评论内容
      blogId: 0, //文章id
      comlist: [],
      activeName: "1",
      activeName2: "1",
      replydata: [],
      height0: "height:0",
      formInline: {
        usercoment: "",
      },
      useravatar: "", //当前用户头像
      usernames: "", //当前用户名字
      userids: "", //当前用户id
      replyangle: 0,
      isthisbool: true, //保留
      isthisbool2: true, //保留
      replydata2: {
        //对谁的回复
        fromName2: "",
        fromAvatar2: "",
        fromId2: "",
        commentId2: "",
      },
      replyinput: {},
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

    //赞 评论
    likecomments(num1, num2) {
      let blogid = this.blogId;
      let url = `/getzpnum?typeid=${num1}&num=${num2}&commid=${blogid}`;
      this.$axios
        .get(url)
        .then((res) => {
          if (res.data.code == 200) {
            console.log(res);
          }
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //提交评论 对文章
    /**
     * {
    "fromAvatar":"https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp",
    "fromName":"markerhub",
    "fromId":1,
    "ownerId":1,
    "typeId":1,
    "likeNum":22,
    "content":"不错哦"
}
     */
    postcomment() {
      let data = {
        fromAvatar: this.userInfo.avatar,
        fromName: this.userInfo.username,
        fromId: this.userInfo.id,
        ownerId: this.$route.params.blogid,
        typeId: 1,
        likeNum: 0,
        content: this.content,
      };
      if (this.content) {
        let url = "http://localhost:8081/comment";
        this.$axios
          .post(url, data)
          .then((res) => {
            if (res.data.code == 200) {
              this.content = "";
              this.getcommentlist(this.blogId, 1);
              // this.likecomments(1, 3);
            }
          })
          .catch((err) => {
            console.error(err);
          });
      } else {
        this.$message({
          message: "内容为空！",
          center: true,
          type: "error",
        });
      }
    },
    /**
     * 获取该文章id下的评论
     */
    getcommentlist(id, typeid) {
      let url = `http://localhost:8081/getcom?ownerId=${id}&typeid=${typeid}`;

      this.$axios
        .get(url)
        .then((res) => {
          if (res.data.code == 200) {
            this.comlist = res.data.data;
            // console.log(this.comlist);
            // this.frist();
          }
        })
        .catch((err) => {
          console.error(err);
        });
    },

    //获取二级回复
    childlist(data) {
      // console.log('e',data);
      this.replyangle = "";
      this.formInline.usercoment = "";
      let url = `http://localhost:8081/getreply?commentid=`;
      this.$axios
        .get(url + data.id)
        .then((res) => {
          if (res.data.code == 200) {
            this.replydata = res.data.data;
            if (!res.data.data.length) {
              this.isdisabled = true;
            }
          }
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //回复  对父级回复
    reply(data) {
      console.log("e", data);
      this.replyangle = "";
      this.activeName2 = data.id;
      this.replydata2.fromName2 = data.fromName;
      this.replydata2.fromId2 = data.fromId;
      this.replydata2.fromAvatar2 = data.fromAvatar;
      this.replydata2.commentId2 = data.id;
    },
    //取消回复
    Cancelcomment() {
      this.replyangle = "";
    },
    //取消回复
    Cancelcomment2() {
      this.activeName2 = "";
    },
    //提交回复
    Submitcomments() {
      let data = {
        commentId: this.replydata2.commentId2,
        toId: this.replydata2.fromId2,
        toAvatar: this.replydata2.fromAvatar2,
        toName: this.replydata2.fromName2,
        fromId: this.userids,
        fromName: this.usernames,
        fromAvatar: this.useravatar,
        likenum: 0,
        content: this.formInline.usercoment,
      };
      console.log("e提交回复", data);

      let url = "http://localhost:8081/replypost";
      this.$axios
        .post(url, data)
        .then((res) => {
          if (res.data.code == 200) {
            this.formInline.usercoment = "";
            this.replyangle = "";
            this.activeName2 = "";
            this.$message({
              message: res.data.msg,
              center: true,
              type: "success",
            });
          }
        })
        .catch((err) => {
          console.error(err);
        });
    },
    errorHandler() {
      return true;
    },
    //对回复的回复  切换
    btnreplyangle(data) {
      this.replyangle = data.id;
      this.activeName2 = "";
      console.log("e", data);
      this.replydata2.fromName2 = data.fromName;
      this.replydata2.fromId2 = data.fromId;
      this.replydata2.fromAvatar2 = data.fromAvatar;
      this.replydata2.commentId2 = data.commentId;
    },
    //对回复的回复 gaibin
    btnreplychange(e) {
      console.log("e", e);
    },
    btnreplychange2() {},
    //对回复点赞
    zanlike(data, index) {
      let id = data.id;

      let indexnum = this.iszans.indexOf(id);
      if (indexnum > -1) {
        this.comlist[index].likeNum -= 1;
        this.iszans.splice(indexnum, 1);
        this.$message({
          message: "已取消！",
          center: true,
          type: "success",
        });
        this.likereply(id, 0);
      } else {
        this.comlist[index].likeNum += 1;
        this.iszans.push(id);
        this.likereply(id, 1);
        this.$message({
          message: "点赞成功！",
          center: true,
          type: "success",
        });
      }
    },
    //效验是否存在
    haszan(id) {
      const set = new Set(this.iszans); //去重
      if (set.has(id)) {
        return true;
      } else {
        return false;
      }
    },
    likereply(id, num) {
      let url = `/commentzanlike?id=${id}&adddnum=${num}`;
      this.$axios
        .get(url)
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //二级点赞
    zanlike2(data, index) {
      let id = data.id;

      let indexnum = this.iszans2.indexOf(id);
      if (indexnum > -1) {
        this.replydata[index].likenum -= 1;
        this.iszans2.splice(indexnum, 1);
        this.$message({
          message: "已取消！",
          center: true,
          type: "success",
        });
        this.likereply2(id, 0);
      } else {
        this.replydata[index].likenum += 1;
        this.iszans2.push(id);
        this.likereply2(id, 1);
        this.$message({
          message: "点赞成功！",
          center: true,
          type: "success",
        });
      }
    },
    //效验是否存在
    haszan2(id) {
      const set = new Set(this.iszans2); //去重
      if (set.has(id)) {
        return true;
      } else {
        return false;
      }
    },
    likereply2(id, num) {
      let url = `/replyzanlike?id=${id}&adddnum=${num}`;
      this.$axios
        .get(url)
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.blogId = this.$route.params.blogid;
    this.useravatar = this.userInfo.avatar;
    this.usernames = this.userInfo.username;
    this.userids = this.userInfo.id;
    // console.log('e',this.blogId);
    this.getcommentlist(this.blogId, 1);

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

.contentcom {
  height: 100%;
  margin-top: 20px;
}
.savebox {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  background: #fff;
  padding: 16px 10px 8px;
  border-radius: 4px;
}
.combox {
  .commentsub {
    margin-top: 10px;
    background: #fff;
    padding: 10px;
    border-radius: 4px;
    .subbox {
      >>> .el-collapse-item__header.is-active {
        display: none;
      }
      >>> .el-collapse-item__header {
        display: none;
      }
    }
    >>> .el-collapse {
      border: 0;
    }
  }
  >>> .el-collapse-item__header {
    height: 100%;
    padding: 10px;
  }
  >>> .el-collapse-item__wrap {
    height: 100%;
    padding: 10px;
  }
  >>> .el-collapse-item__content {
    padding: 0;
  }
}
.replycomment {
  padding: 16px 24px 8px;
  display: flex;
  .demo-form-reply {
    width: 100%;
    margin-left: 8px;
  }
}
.replychild {
  >>> .el-collapse-item__header.is-active {
    display: none;
  }
  >>> .el-collapse-item__header {
    display: none;
  }
}
</style>
