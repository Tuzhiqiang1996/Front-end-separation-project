
[开发地址](https://juejin.cn/post/6844903823966732302)

[github](https://github.com/MarkerHub/vueblog)

[视频地址](https://www.bilibili.com/video/BV1PQ4y1P7hZ/)

### docker学习
[docker学习](https://usthe.com/2017/12/docker_learn/)

### mybatis plus
[MyBatis-Plus](https://mp.baomidou.com/guide/install.html)
![](https://upload-images.jianshu.io/upload_images/16598307-1ab9b66561007142.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)

##开始
创建 springboot 项目
新建项目 - develop-1.2+web-1+mysql

### 问题

 启动是报错
将解决办法：跨域配置报错，将.allowedOrigins替换成.allowedOriginPatterns即可。

### 访问
登录
http://localhost:8081/login


### 项目结构
```
com.example   根目录
        ├─ common 公共类
                ├─ dto
                ├─ exception
                └─ lang 
        ├─ config 配置信息类
                ├─ CoreConfig.java
                ├─ MybatisPlusConfig.java
                └─ShiroConfig.lava
        ├─ controller 前端控制器
                    
        ├─ entity
        ├─ mapper 
        ├─ service 数据服务层
        ├─ shiro        
        ├─ util 工具类
        ├─CondeGenerator
        └─VueblogApplication.java 工程启动类
        
          
          
```
