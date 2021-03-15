<!--  -->
<template>
  <div class="" v-loading="loading">
    <Header />
    <div style="margin: 0 auto; display: table">
      <el-table
        ref="filterTable"
        :data="tableData"
        style="width: 100%;text-angle"
        border
      >
        <el-table-column prop="acttimer" label="acttimer" width="80">
        </el-table-column>
        <el-table-column prop="cmd" label="cmd" width="180"> </el-table-column>
        <el-table-column prop="display" label="display" width="100">
        </el-table-column>
        <el-table-column prop="end" label="end" width="200"> </el-table-column>
        <el-table-column prop="failed" label="failed" width="200">
        </el-table-column>
        <el-table-column prop="filter" label="filter" width="180">
        </el-table-column>
        <el-table-column prop="flag" label="flag" width="180">
        </el-table-column>
        <el-table-column prop="function" label="function" width="180">
        </el-table-column>
        <el-table-column prop="id" label="id" width="100"> </el-table-column>
        <el-table-column prop="pass" label="pass" width="80">
        </el-table-column>
      </el-table>
    </div>
    <div>
      <div style="text-align: center; padding: 20px 0">
        <el-pagination
          layout="prev, pager, next"
          :total="total"
          :current-page="currentpage"
          :page-size="pagesize"
          @current-change="page"
          background
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import Header from "./header";
export default {
  name: "systemList",
  //import引入的组件需要注入到对象中才能使用
  components: { Header },
  props: [],
  data() {
    //这里存放数据
    return {
      loading: true,
      tableData: [],
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
    page(num) {
      let url = "/message?currentPage=";
      this.$axios
        .get(url + num)
        .then((res) => {
          const { code, data } = res.data;
          if (code == 200) {
            console.log(data);
            this.loading = false;
            this.tableData = data.records;
            this.total = data.total;
            this.currentpage = data.current;
            this.pagesize = data.size;
          }
        })
        .catch((err) => {
          console.error(err);
        });
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
</style>