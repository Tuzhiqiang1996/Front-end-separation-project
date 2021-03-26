import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);
const routes = [
  {
    path: "/",
    name: "Login",
    component: resolve => require(["@/view/Login.vue"], resolve)
  },
  {
    path: "/home",
    name: "home",
    component: resolve => require(["@/page/index.vue"], resolve),
    meta: {
      requireAuth: true // 添加该字段，表示进入这个路由是需要登录的
    }
  },
  {
    path: "/edit/:blogid/edit",
    name: "editblogid",
    component: resolve => require(["@/page/blogsedit.vue"], resolve),
    meta: {
      requireAuth: true // 添加该字段，表示进入这个路由是需要登录的
    }
  },
  {
    path: "/edits",
    name: "edit",
    component: resolve => require(["@/page/blogsedit.vue"], resolve),
    meta: {
      requireAuth: true // 添加该字段，表示进入这个路由是需要登录的
    }
  },
  {
    path: "/blogs/:blogid",
    name: "blogId",
    component: resolve => require(["@/page/blogs.vue"], resolve)
  },
  {
    path: "/editchange",
    name: "editchange",
    component: resolve => require(["@/page/editchange.vue"], resolve)
  },
  {
    path: "/userlist",
    name: "userlist",
    component: resolve => require(["@/page/userlist.vue"], resolve)
  },
  {
    path: "/userinfo",
    name: "userinfo",
    component: resolve => require(["@/page/userinfo.vue"], resolve)
  },
  {
    path: "*",
    redirect: "/404"
  },
  {
    name: "404",
    path: "/404",
    component: () => import("@/view/notFound.vue")
  }
];
const router = new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

//避免重复跳转产生问题
const VueRouterPush = Router.prototype.push;
Router.prototype.push = function push(to) {
  return VueRouterPush.call(this, to).catch(err => err);
};
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    // 判断该路由是否需要登录权限

    if (localStorage.getItem("token")) {
      // 通过vuex state获取当前的token/name是否存在
      next();
    } else {
      next({
        path: "/",
        query: { redirect: to.fullPath } // 将跳转的路由path作为参数，登录成功后跳转到该路由
      });
    }
  } else {
    next();
  }
});
export default router;
