<!-- 评论 -->
<template>
  <div class="" style="min-height: 200px">
    <div class="contentcom">
      <el-collapse-transition>
        <div v-show="iscontent">
          <div class="savebox">
            <el-input
              type="textarea"
              placeholder="请输入内容"
              :autosize="{ minRows: 2, maxRows: 4 }"
              v-model="content"
              maxlength="500"
              show-word-limit
            >
            </el-input>
            <el-button
              style="margin: 0 0 0 10px"
              type="danger"
              @click="postcomment"
              >提交</el-button
            >
          </div>
          <div class="combox">
            <div v-for="item in comlist" :key="item.id" class="commentsub">
              <el-avatar size="small" :src="item.fromAvatar"></el-avatar>
              <span style="color: #606266; vertical-align: super"
                >{{ item.fromName }}：</span
              >
              <span style="color: #606266; vertical-align: super">{{
                item.createTime
              }}</span>
              <el-button
                type="text"
                style="vertical-align: super"
                @click="reply(item)"
                >回复</el-button
              >
              <el-button type="text" style="vertical-align: super">
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
                    :fill="iszans ? '#d81e06' : '#bfbfbf'"
                    p-id="1895"
                  ></path>
                  <path
                    d="M251.671415 423.299819L109.214912 423.299819c-29.420053 0-29.873378 28.89612-29.873378 28.89612l29.420053 476.202703c0 30.309306 30.361495 30.309306 30.361495 30.309306L262.420223 958.707948c25.686009 0 25.458835-20.049638 25.458835-20.049638L287.879058 459.411271C287.879058 422.837284 251.671415 423.299819 251.671415 423.299819L251.671415 423.299819 251.671415 423.299819zM251.671415 423.299819"
                    :fill="iszans ? '#d81e06' : '#bfbfbf'"
                    p-id="1896"
                  ></path>
                </svg>
                <span style="vertical-align: text-top">{{ item.likeNum }}</span>
              </el-button>
              <!-- <span style="vertical-align: super">{{ item.content }}</span> -->
              <el-collapse v-model="activeName" accordion>
                <el-collapse-item :title="item.content" :name="item.id">
                  <template slot="title">
                    {{ item.content }}
                  </template>
                  <div>
                    与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念；
                  </div></el-collapse-item
                ></el-collapse
              >
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

export default {
  name: "",
  //import引入的组件需要注入到对象中才能使用
  components: {},
  props: ["iscontents"],
  data() {
    //这里存放数据
    return {
      content: "", //评论内容
      blogId: 0, //文章id
      comlist: [],
      activeName: "1",
      iscontent: this.iscontents,
      iszans: false,
    };
  },
  //监听属性 类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {
    iscontents: (e, n) => {
      console.log(e, n);
      console.log(this.iscontents);

    },
     'iscontents'(val){
            console.log('val',val);
              //监听外部对props属性person的变更，并同步到组件内的data属性childData中
        },
  },
  //方法集合
  methods: {
    //提交评论
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
        likeNum: 22,
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
            this.frist();
          }
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //回复
    reply(data) {
      console.log("e", data);
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.blogId = this.$route.params.blogid;
    console.log(this.iscontent);
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
}
.combox {
  .commentsub {
    margin-top: 10px;
    background: #fff;
    padding: 10px;
    border-radius: 4px;
  }
  >>> .el-collapse-item__header {
    height: 100%;
    padding: 10px;
  }
  >>> .el-collapse-item__wrap {
    height: 100%;
    padding: 10px;
  }
}
</style>
