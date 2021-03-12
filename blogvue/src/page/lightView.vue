<!-- 右边栏 -->
<template>
  <div class="">
    <div style="width: 330px; text-align: center">
      <div class="search">
        <el-input
          v-model="input"
          placeholder="请输入内容"
          clearable
          style="width: 200px"
        ></el-input>
        <el-button @click="btn" type="primary" plain>搜素</el-button>
      </div>
      <el-calendar v-model="value"> </el-calendar>
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
      input: "",
      data: {},
    };
  },
  //监听属性 类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    btn() {
      /**
       * 将得到的数据传递给index.vue 在组件中预留位置 接受兄弟组件的值
       */
      let url = "/search?value=";
      this.$axios
        .get(url + this.input)
        .then((res) => {
          //   console.log(res);
          this.$emit("msgData", res);
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {},
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
</style>