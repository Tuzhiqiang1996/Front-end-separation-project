<!-- 右边栏 -->
<template>
  <div class="">
    <div style="width: 330px; text-align: center">
      <div class="search">
        <el-input
          v-model="input.value"
          placeholder="请输入内容"
          clearable
          style="width: 200px"
        ></el-input>
        <el-button @click="btn" type="primary" plain>搜素</el-button>
      </div>
      <el-calendar v-model="value"> </el-calendar>
      <div>
        <el-carousel
          trigger="click"
          height="150px"
          ref="carousel"
          indicator-position="none"
          @change="
            (pre, next) => {
              change(pre, next);
            }
          "
          :autoplay="false"
        >
          <el-carousel-item
            v-for="item in pages"
            :key="item"
            class="carouselbox"
          >
            <div v-for="items in data" :key="items.id" class="cardiv">
              <el-tag
                v-if="items.label"
                effect="plain"
                @click="tag(items.label)"
                :color="'#' + Math.floor(Math.random() * 0xffffff).toString(16)"
              >
                {{ items.label }}
              </el-tag>
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>
      <div>
        <p>666</p>
      </div>
    </div>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import bus from "../utils/eventBus";
export default {
  name: "",
  //import引入的组件需要注入到对象中才能使用
  components: {},
  data() {
    //这里存放数据
    return {
      value: new Date(),
      input: {
        value: "",
        label: "",
      },
      data: [],
      pages: 1,
    };
  },
  //监听属性 类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    btn() {
      this.$emit("msgData", this.input);
      /**
       * 将得到的数据传递给index.vue 在组件中预留位置 接受兄弟组件的值
       */
    },

    change(pre, next) {
      // 当前页  上一页

      let num = pre + 1;
      this.page(num);
      console.log(num);
    },
    /**
     *  标签
     */
    page(num) {
      let url = `http://localhost:8081/blogs/?currentPage=${num}`;

      this.$axios
        .get(url)
        .then((res) => {
          if (res.data.code == 200) {
            this.data = res.data.data.records;
            this.pages = res.data.data.pages;
          } else {
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
    tag(e) {
      this.input.label = e;
      this.btn();
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.page(1);
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
.search {
  display: flex;
  justify-content: space-evenly;
}
.taglabel {
  color: #000;
}
.carouselbox {
  // display: flex;
  // justify-content: center;
}
.cardiv {
  float: left;
  padding: 15px;
}
</style>
