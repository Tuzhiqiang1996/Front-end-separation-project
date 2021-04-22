<!-- 详情 -->
<template>
  <div class="mblog" v-loading="loading">
    <h2>{{ formName.title }}</h2>
    <div class="top">
      <el-link icon="el-icon-edit">
        <router-link :to="{ name: 'editblogid', params: { blogId: id } }">
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
          size="medium"
          @blur="handleInputConfirm"
        ></el-input>
        <el-tag v-else @click="handleClose" effect="plain">
          {{ formName.label }}
        </el-tag>
      </div>
    </div>
    <div style="max-width: 960px; width: 100%">
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
        <el-divider style="max-width: 960px"></el-divider>
        <div class="markdown-body" v-html="formName.content"></div>
      </div>
    </div>
    <el-backtop target=""></el-backtop>
    <!-- 底部功能 -->
    <div class="bottombox">
      <div class="botcontent">
        <!-- 赞 -->
        <el-button @click="zan" type="text">
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
              :fill="iszan ? '#d81e06' : '#bfbfbf'"
              p-id="1895"
            ></path>
            <path
              d="M251.671415 423.299819L109.214912 423.299819c-29.420053 0-29.873378 28.89612-29.873378 28.89612l29.420053 476.202703c0 30.309306 30.361495 30.309306 30.361495 30.309306L262.420223 958.707948c25.686009 0 25.458835-20.049638 25.458835-20.049638L287.879058 459.411271C287.879058 422.837284 251.671415 423.299819 251.671415 423.299819L251.671415 423.299819 251.671415 423.299819zM251.671415 423.299819"
              :fill="iszan ? '#d81e06' : '#bfbfbf'"
              p-id="1896"
            ></path>
          </svg>
          <span>{{ zanNum }}</span>
        </el-button>
        <!-- 评论 -->
        <el-button @click="btncomment" type="text">
          <svg
            t="1618903837663"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="2777"
            width="20"
            height="20"
          >
            <path
              d="M949.570625 207.6734375l-41.80125 41.518125L768.565625 110.84375l41.80125-41.46c15.3403125-15.283125 39.52125-16.08375 53.8921875-1.768125l87.0234375 86.454375c14.428125 14.3128125 13.6284375 38.3240625-1.711875 53.6034375z m-369.196875 366.920625L441.1690625 436.244375l306.238125-304.299375L886.61375 270.29375 580.3728125 574.5940625z m-19.389375 19.275L366.12125 649.184375l55.659375-193.666875L560.984375 593.8690625z m-324.433125-420.75c-48.13125 0-87.080625 38.9484375-87.080625 87.0234375V782.515625c0 48.0628125 38.9625 87.02625 87.0253125 87.02625h522.4865625c48.06375 0 87.028125-38.9625 87.028125-87.028125V434.2484375l87.07875-87.028125V811.540625c0 80.1253125-64.9546875 145.078125-145.078125 145.078125H207.468125c-80.124375 0-145.078125-64.95375-145.078125-145.078125V231.115625c0-80.1253125 64.9546875-145.078125 145.078125-145.078125h464.435625l-87.0834375 87.0834375H236.55125v-0.0009375z"
              fill="#0997F7"
              p-id="2778"
            ></path></svg
          ><span>{{ commnum }}</span>
        </el-button>
        <!-- 浏览量 -->
        <el-button type="text">
          <svg
            t="1618913171466"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="8235"
            width="16"
            height="16"
          >
            <path
              d="M231.819636 293.143273h-63.534545A63.557818 63.557818 0 0 0 104.727273 356.701091V960.465455A63.557818 63.557818 0 0 0 168.285091 1024h63.534545a63.557818 63.557818 0 0 0 63.557819-63.534545V356.701091a63.534545 63.534545 0 0 0-63.557819-63.557818zM549.585455 7.168h-63.534546a63.557818 63.557818 0 0 0-63.557818 63.557818V960.465455A63.534545 63.534545 0 0 0 486.050909 1024h63.534546a63.534545 63.534545 0 0 0 63.534545-63.534545V70.725818a63.534545 63.534545 0 0 0-63.534545-63.557818zM867.374545 515.584h-63.557818a63.534545 63.534545 0 0 0-63.534545 63.557818V960.465455a63.534545 63.534545 0 0 0 63.534545 63.534545h63.557818A63.534545 63.534545 0 0 0 930.909091 960.465455V579.141818a63.557818 63.557818 0 0 0-63.534546-63.557818z"
              fill="#0997F7"
              p-id="8236"
            ></path>
          </svg>
          <span>{{ readynum }}</span></el-button
        >
      </div>
      <div class="contentcom">
        <el-collapse-transition>
          <div v-show="iscontent">
            <div class="savebox">
              <div style="height: 100%; line-height: 2.5">
                <el-avatar
                  size="medium"
                  :src="useravatar"
                  @error="errorHandler"
                >
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
                  <span style="vertical-align: text-top">{{
                    item.likeNum
                  }}</span>
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
                              :fill="
                                haszan2(replist.id) ? '#d81e06' : '#bfbfbf'
                              "
                              p-id="1895"
                            ></path>
                            <path
                              d="M251.671415 423.299819L109.214912 423.299819c-29.420053 0-29.873378 28.89612-29.873378 28.89612l29.420053 476.202703c0 30.309306 30.361495 30.309306 30.361495 30.309306L262.420223 958.707948c25.686009 0 25.458835-20.049638 25.458835-20.049638L287.879058 459.411271C287.879058 422.837284 251.671415 423.299819 251.671415 423.299819L251.671415 423.299819 251.671415 423.299819zM251.671415 423.299819"
                              :fill="
                                haszan2(replist.id) ? '#d81e06' : '#bfbfbf'
                              "
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
                                  <el-button
                                    type="danger"
                                    @click="Submitcomments"
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
      <!-- <div>
<Comment :iscontens='iscontent'/>
      </div> -->
    </div>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
// import 'mavon-editor/dist/css/index.css'
//  import 'github-markdown-css'
import Comment from "./comment";
import { mapState } from "vuex";
export default {
  name: "blogId",
  //import引入的组件需要注入到对象中才能使用
  components: { Comment },
  data() {
    //这里存放数据
    return {
      loading: true,
      inputVisible: false,
      iscontent: false,
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
      this.blogId = this.$route.params.blogid;
      this.useravatar = this.userInfo.avatar;
      this.usernames = this.userInfo.username;
      this.userids = this.userInfo.id;
      // console.log(this.useravatar);
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
            this.readynum = data.readyNumber;
            this.zanNum = data.getlike;
            this.commnum = data.comments;
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
    //点赞
    zan() {
      this.iszan = !this.iszan;
      if (this.iszan) {
        this.zanNum++;
        this.likecomments(2, 3);
        this.$message({
          message: "点赞成功！",
          center: true,
          type: "success",
        });
      } else {
        this.$message({
          message: "已取消！",
          center: true,
          type: "success",
        });
        this.likecomments(2, 4);
        this.zanNum--;
      }
    },
    //评论 对人评论-0，对项目评论-1，
    btncomment() {
      this.iscontent = !this.iscontent;
      this.getcommentlist(this.blogId, 1);
    },
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
              // this.iscontent = !this.iscontent;
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
  // height: 100%;
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
.bottombox {
  max-width: 960px;
  width: 100%;
}
.botcontent {
  display: flex;
  justify-content: center;
  align-content: center;
}
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
