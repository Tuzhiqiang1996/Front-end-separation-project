package com.example.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.dto.LoginDto;
import com.example.common.lang.Result;
import com.example.entity.Blog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Cominfo;
import com.example.service.BlogService;
import com.example.service.CominfoService;
import com.example.service.UserService;
import com.example.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @RestController 相当于  @ResponseBody+ @Controller合在一起
 * @ResponseBody的作用其实是将java对象转为json格式的数据。
 * @Controller
 * @since 2021-01-29
 */
@RestController
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;

    @Autowired
    CominfoService cominfoService;

    /**
     * [java.lang.Integer, java.lang.String]
     *
     * @return com.example.common.lang.Result
     * @author Tu
     * @date 2021/4/19 17:28
     * @message 同时带条件查询，
     */
    @GetMapping("/blogs")
    public Result blogs(Integer currentPage, String value, String label) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        Page page = new Page(currentPage, 5);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created");

        if (value != null && value.length() != 0) {
            queryWrapper.like("title", value);
        }
        if (label != null && label.length() != 0) {
            queryWrapper.like("label", label);
        }
        List<Blog> Blogs = blogService.list(queryWrapper);
        if (Blogs == null || Blogs.size() == 0) {
            return Result.fail("没有数据！");
        }
        IPage pageData = blogService.page(page, queryWrapper);
        return Result.succ(pageData);
    }

    /**
     * [java.lang.Long]
     *
     * @return com.example.common.lang.Result
     * @author Tu
     * @date 2021/4/21 9:39
     * @message 根据id获取文章
     */
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已删除！");
        blog.setReadyNumber(blog.getReadyNumber() + 1);
        QueryWrapper<Cominfo> queryWrapper = new QueryWrapper();
        queryWrapper.like("owner_id", id);
        List<Cominfo> cominfos = cominfoService.list(queryWrapper);
        blog.setComments(cominfos.size());

//        System.out.println(blog.getReadyNumber()+1);
//        System.out.println(cominfos.size());
        blogService.saveOrUpdate(blog);
        return Result.succ(blog);
    }

    /**
     * [com.example.entity.Blog]
     *
     * @return com.example.common.lang.Result
     * @author Tu
     * @date 2021/3/16 10:25
     * @message 修改功能
     * @RequiresAuthentication 需要登录后的放回token
     */
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog temp = null;

        /**
         * @date 2021/2/21-11:37
         * 不知道声明错
         * if语句中的含义
         */
//        if(blog.getId() != null) {
//            temp = blogService.getById(blog.getId());
//           Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()), "没有权限编辑");
//            System.out.println("没有权限编辑...");
//        } else {
//        temp = new Blog();
//        temp.setUserId(ShiroUtil.getProfile().getId());
//        temp.setCreated(LocalDateTime.now());
//        temp.setStatus(0);
//        System.out.println("编辑中...");
//       }
        temp = new Blog();
//        temp.setUserId(ShiroUtil.getProfile().getId());
        temp.setCreated(LocalDateTime.now());
        temp.setStatus(blog.getStatus());
        // System.out.println("编辑中...");

//        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        BeanUtil.copyProperties(blog, temp);
        //        System.out.println(blog.getStatus() + "" + temp);

        blogService.saveOrUpdate(temp);
        return Result.succ("操作成功", null);
    }

    /**
     * [com.example.entity.Blog]
     *
     * @return com.example.common.lang.Result
     * @author Tu
     * @date 2021/4/14 15:30
     * @message 根据id修改 标签
     * blogService.saveOrUpdate(label);
     * this.blogService.updateById(label);
     * 以上都支持修改一个是全修改
     * 后一个是根据id修改
     */
    @RequiresAuthentication
    @PostMapping("/blog/fix")
    public Result fix(@Validated @RequestBody Blog blog) {

        Blog label = blogService.getById(blog.getId());
        label.setLabel(blog.getLabel());
        label.setCreated(LocalDateTime.now());
        blogService.saveOrUpdate(label);
//        this.blogService.updateById(label);
        return Result.succ("操作成功", label);
    }

    /**
     * [com.example.entity.Blog]
     *
     * @return com.example.common.lang.Result
     * @author Tu
     * @date 2021/3/16 10:08
     * @message 新增功能
     * BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status"); 为曾
     * BeanUtil.copyProperties(blog, temp); 为改
     * 新增功能要添加userID
     * 上面的修改功能 注释掉useID
     * userID 对应用户id
     */
    @PostMapping("/blog/add")
    public Result editadd(@Validated @RequestBody Blog blog) {
        Blog temp = null;
        temp = new Blog();
        temp.setCreated(LocalDateTime.now());
        temp.setUserId(blog.getUserId());
        temp.setStatus(blog.getStatus());
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.succ("操作成功", null);
    }

    /**
     * 删除功能
     *
     * @param id
     * @return
     */


    @ResponseBody
    @DeleteMapping("delete/{id}")
    public Result deleteBlog(@PathVariable(name = "id") Long id, HttpServletRequest request, LoginDto loginDto) {


        /**
         * 想要获取哪个用户操作的
         * 下文
         * 可以有前端传一个参数 status 与当前  blog.getStatus()对比
         * 前端已做
         */


        Blog blog = blogService.getById(id);
//        System.out.println(blog.getStatus());
//        System.out.println(id);
//        System.out.println(blog);

        //blogService.delete(id);

        blogService.removeById(id);
        return Result.succ("删除成功", null);
    }

    /**
     * [java.lang.String]
     *
     * @return com.example.common.lang.Result
     * 来源
     * https://blog.csdn.net/qq_40222207/article/details/105347632?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_title-1&spm=1001.2101.3001.4242
     * https://blog.csdn.net/weixin_51370812/article/details/111686736
     * @author Tu
     * @date 2021/3/10 17:52
     * @message 查询功能(模糊查询)
     */
    @GetMapping("/search")
    public Result search(String value) {
        if (value == null) {
            System.out.println(value);
            return Result.fail("没有数据！");
        }
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", value);
        List<Blog> Blogs = blogService.list(queryWrapper);
//        System.out.println(Blogs);
        //判断数据是否为空
        if (Blogs == null || Blogs.size() == 0) {
            return Result.fail("没有数据！");
        }
        return Result.succ("操作成功", Blogs);
    }

    /**
     * []
     *
     * @return com.example.common.lang.Result
     * @author Tu
     * @date 2021/3/11 15:11
     * @message 在线人数统计
     */
    @GetMapping("/getOnlineCount")
    public Result getonlinecount() {

        return Result.succ("1");
    }

    /**
     * []
     *
     * @return com.example.common.lang.Result
     * @author Tu
     * @date 2021/4/21 14:18
     * @message 0.0
     * 评论数 点赞数  +   -  文章id commid
     * 1      2     3   4
     */
    @GetMapping("/getzpnum")
    public Result getnum(Integer typeid, Integer num, Integer commid) {
        Blog blog = blogService.getById(commid);
//        if (typeid == 1) {
//            if (num == 3) {
//                blog.setComments(blog.getComments() + 1);
//            } else {
//                blog.setComments(blog.getComments() - 1);
//            }
//        }
        if (typeid == 2) {
            if (num == 3) {
                blog.setGetlike(blog.getGetlike() + 1);
            } else {
                blog.setGetlike(blog.getGetlike() - 1);
            }
        }
        blogService.saveOrUpdate(blog);
        return Result.succ("success",blog);
    }
}

