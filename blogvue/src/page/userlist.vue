<!--  -->
<template>
  <div class="" v-loading="loading">
    <Header />
    <div style="margin: 0 auto; display: table">
      <el-table
        ref="filterTable"
        :data="tableData"
        style="width: 100%;text-angle"
      >
        <el-table-column label="头像" width="100">
          <template slot-scope="scope">
            <!-- <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"  >编辑</el-button -->
            <el-avatar size="large" :src="scope.row.avatar"></el-avatar>
            <!-- <img :src="scope.row.avatar" alt="" class="img" /> -->
          </template>
        </el-table-column>
        <el-table-column label="姓名" width="100">
          <template slot-scope="scope">
            <el-tag size="medium">{{ scope.row.username }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" width="200">
        </el-table-column>
        <el-table-column prop="created" label="日期" width="200">
        </el-table-column>
        <el-table-column prop="status" label="权限" width="180">
          <template slot-scope="scope">
            <el-rate
              v-model="scope.row.status"
              disabled
              disabled-void-color="#f7ba2a"
              :colors="['#ffffff', '#F7BA2A', '#FF9900']"
            >
            </el-rate>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <!-- <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">编辑</el-button> -->
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
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
import { mapState } from "vuex";
export default {
  name: "userlist",
  //import引入的组件需要注入到对象中才能使用
  components: { Header },
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
  computed: {
    ...mapState(["userInfo"]),
  },
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    pages(page) {
      let url = "/userList?currentPage=";
      this.$axios
        .get(url + page)
        .then((res) => {
          if (res.data.code == 200) {
            this.loading = false;
            this.tableData = res.data.data.records;
            this.total = res.data.data.total;
            this.currentpage = res.data.data.current;
            this.pagesize = res.data.data.size;
          } else{

            this.$message({
              message: res.data.msg,
              showClose: true,
              type: "error",
            });
          }
          // console.log(res);
        })
        .catch((err) => {
          // console.error(err);
          this.$message({
            message: res.data.msg,
            showClose: true,
            type: "error",
          });
        });
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      this.$confirm("此操作将永久删除该用户？, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // if (this.userInfo.status === 0) {
        this.$axios
          .get(`/deleteUser?id=${row.id}&status=${this.userInfo.status}`)
          .then((res) => {
            if (res.data.code == 200) {
              this.$message({
                message: res.data.msg,
                showClose: true,
                type: "success",
              });
              this.pages(1);
            } else {
              this.$message({
                message: res.data.msg,
                showClose: true,
                type: "error",
              });
            }
          })
          .catch((err) => {
            this.$message({
              message: err,
              showClose: true,
              type: "error",
            });
          });
      });

      console.log(row.id, row.status);
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.pages(1);
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
.img {
  width: 80px;
  border-radius: 50%;
}
</style>