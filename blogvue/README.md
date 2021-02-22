# blogvue

> A Vue.js project

## Build Setup

```bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).

## 引入 element-ui

```
$ npm i element-ui -S
$ npm install babel-plugin-component -D

```

## 在 mian.js

```
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);
```

## 安装 axios

···
npm install axios
···

## vue 登录 中使用动画 vue-particles

```
npm install vue-particles --save-dev
main.js
import VueParticles from 'vue-particles'
Vue.use(VueParticles)


<vue-particles
        color="#dedede"
        :particleOpacity="0.7"
        :particlesNumber="80"
        shapeType="circle"
        :particleSize="4"
        linesColor="#dedede"
        :linesWidth="1"
        :lineLinked="true"
        :lineOpacity="0.4"
        :linesDistance="150"
        :moveSpeed="3"
        :hoverEffect="true"
        hoverMode="grab"
        :clickEffect="true"
        clickMode="push"
      >
      </vue-particles>



      color: String类型。默认'#dedede'。粒子颜色。
particleOpacity: Number类型。默认0.7。粒子透明度。
particlesNumber: Number类型。默认80。粒子数量。
shapeType: String类型。默认'circle'。可用的粒子外观类型有："circle","edge","triangle", "polygon","star"。
particleSize: Number类型。默认80。单个粒子大小。
linesColor: String类型。默认'#dedede'。线条颜色。
linesWidth: Number类型。默认1。线条宽度。
lineLinked: 布尔类型。默认true。连接线是否可用。
lineOpacity: Number类型。默认0.4。线条透明度。
linesDistance: Number类型。默认150。线条距离。
moveSpeed: Number类型。默认3。粒子运动速度。
hoverEffect: 布尔类型。默认true。是否有hover特效。
hoverMode: String类型。默认true。可用的hover模式有: "grab", "repulse", "bubble"。
clickEffect: 布尔类型。默认true。是否有click特效。
clickMode: String类型。默认true。可用的click模式有: "push", "remove", "repulse", "bubble"
```

## vuex

```
npm i vuex -s
```

## 问题

···
lang='scss' 报错
This dependency was not found:

- !!vue-style-loader!css-loader?{"minimize":false,"sourceMap":false}!../../node_modules/vue-loader/lib/style-compiler/index?{"vue":true,"id":"data-v-1d57e5ea","scoped":false,"hasInlineConfig":false}!stylus-loader?{"sourceMap":false}!../../node_modules/vue-loader/lib/selector?type=styles&index=0!./a.vue in ./src/components/a.vue

To install it, you can run: npm install --save !!vue-style-loader!css-loader?{"minimize":false,"sourceMap":false}!../../node_modules/vue-loader/lib/style-compiler/index?{"vue":true,"id":"data-v-1d57e5ea","scoped":false,"hasInlineConfig":false}!stylus-loader?{"sourceMap":false}!../../node_modules/vue-loader/lib/selector?type=styles&index=0!./a.vu

1、
npm install sass-loader --save;

···

## 网站的图标展示

首先将 favicon.ico 图片放在根目录下，通过以下两种方法使其显示正确。

方法一：修改 >index.html 文件

```html
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
```

方法二：修改 webpack 配置文件

1、找到 build 下的 webpack.dev.conf.js 文件

```
new HtmlWebpackPlugin({
      filename: 'index.html',
      template: 'index.html',
      inject: true,
      favicon: path.resolve('favicon.ico') // 增加
    }),
        2、找到build下的webpack.prod.conf.js文件

 new HtmlWebpackPlugin({
      filename: config.build.index,
      template: 'index.html',
      inject: true,
      favicon: path.resolve('favicon.ico'), //新增
      minify: {
        removeComments: true,
        collapseWhitespace: true,
        removeAttributeQuotes: true
        ...
    }),

```

修改配置文件后需重启 npm run dev。

### VUE 配置启动后自动打开浏览器

config/index.js 属性 autoOpenBrowser 配置为 true

### vuex 使用

```
<p>{{ userInfo.username }}</p>
import { mapState,mapMutations } from "vuex";
 computed: {
    ...mapState(["userInfo"]),
  },
console.log(this.userInfo)//有效
  muthods:{
      ...mapMutations({SET_TOKEN:"SET_TOKEN", SET_USERINFO:"SET_USERINFO"}),

  }
相当于映射成 this.SET_TOKEN(参数);
```
### vuex 持久化

> 在 app.vue 中
```
created() {
  //在页面加载时读取sessionStorage里的状态信息
  if (sessionStorage.getItem("store")) {
    this.$store.replaceState(
      Object.assign(
        {},
        this.$store.state,
        JSON.parse(sessionStorage.getItem("store"))
      )
    );
  }
  //在页面刷新时将vuex里的信息保存到sessionStorage里
  window.addEventListener("beforeunload", () => {
    sessionStorage.setItem("store", JSON.stringify(this.$store.state));
  });
},

```

## 启动Redis
```
redis-server.exe
```
## 安装mavon-editor
基于Vue的markdown编辑器mavon-editor

cnpm install mavon-editor --save
## 用于解析md文档
cnpm install markdown-it --save
