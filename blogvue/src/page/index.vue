<!--  -->
<template>
  <div class="content">
    <Header />
    <div class="box">
      <div
        style="max-width: 960px; padding: 40px; min-width: 760px"
        v-loading="loading"
      >
        <el-timeline>
          <el-timeline-item
            :timestamp="item.created"
            placement="top"
            v-for="item in data"
            :key="item.id"
          >
            <el-card>
              <h4>
                <!-- :to="{path:'/blogId',query:{blogid: item.id} }" -->
                <router-link
                  :to="{
                    name: 'blogId',
                    params: { blogid: item.id, status: item.status },
                  }"
                >
                  {{ item.title }}
                </router-link>
              </h4>
              <p>{{ item.description }}</p>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </div>
      <div>
        <LightView v-on:msgData="updateData" />
      </div>
    </div>

    <div style="text-align: center; padding: 20px 0">
      <el-pagination
        layout="prev, pager, next"
        :total="total"
        :current-page="currentpage"
        :page-size="pagesize"
        @current-change="pages"
        background
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import Header from "./header";
import LightView from "./lightView";
import bus from "../utils/eventBus";
export default {
  name: "index",
  //import引入的组件需要注入到对象中才能使用
  components: { Header, LightView },
  data() {
    //这里存放数据
    return {
      total: 0, //总条目数
      currentpage: 1, //当前页数
      pagesize: 5, //每页显示条目个数
      data: {}, //数据
      loading: true,
    };
  },
  //监听属性 类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    pages(page) {
      let url = "http://localhost:8081/blogs/?currentPage=";

      this.$axios
        .get(url + page)
        .then((res) => {
          // console.log(res);
          if (res.data.code == 200) {
            this.total = res.data.data.total;
            this.currentpage = res.data.data.current;
            this.pagesize = res.data.data.size;
            this.data = res.data.data.records;
            this.loading = false;
          } else {
            this.loading = true;
            this.$message({
              message: res.data.msg,
              showClose: true,
              type: "error",
            });
          }
        })
        .catch((err) => {
          console.error(err);
          this.$message({
            message: res.data.msg,
            showClose: true,
            type: "error",
          });
        });
    },
    updateData(msg) {
      var self = this;
      if (msg.data.code == 200) {
        self.data = msg.data.data;
        self.total = msg.data.data.length;
        self.loading = false;
      } else {
        this.$message({
          message: msg.data.msg,
          showClose: true,
          type: "error",
        });
        const loading = this.$loading({
          lock: true,
          text: "Loading",
          spinner: "el-icon-loading",
          background: "rgba(0, 0, 0, 0.7)",
        });
        setTimeout(() => {
          loading.close();
        }, 2000);
      }
    },
    //动态加载script
    scriptinit() {
      // 创建script标签，引入外部文件
      let script = document.getElementById("gaodeMapScript");
      if (!script) {
        let link = document.createElement("link");
        link.id='links'
        link.href =
          "https://cdn.jsdelivr.net/npm/font-awesome/css/font-awesome.min.css";
        link.rel = "stylesheet";
        script = document.createElement("script");
        script.id = "gaodeMapScript";
        script.type = "text/javascript";
        script.src =
          "https://cdn.jsdelivr.net/gh/stevenjoezhang/live2d-widget/autoload.js";
        document.getElementsByTagName("head")[0].append(script, link);
      }
      if (!JSON.parse(sessionStorage.getItem("userInfo"))) {
        script.remove();
        link.remove();
      }
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.pages(1);
    this.scriptinit();
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
.content >>> .el-calendar-table .el-calendar-day {
  height: 30px;
}
.content >>> .el-calendar {
  background: transparent;
}
.box {
  display: flex;
  justify-content: center;
  background: transparent;
}
</style>