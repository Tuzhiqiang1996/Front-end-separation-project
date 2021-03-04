package com.example.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.dto.LoginDto;
import com.example.common.lang.Result;
import com.example.entity.Blog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.User;
import com.example.service.BlogService;
import com.example.service.UserService;
import com.example.util.IpUtil;
import com.example.util.ShiroUtil;
import jdk.nashorn.internal.ir.ReturnNode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2021-01-29
 */
@RestController
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;

    @GetMapping("/blogs")
    public Result blogs(Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
//        System.out.println(pageData);
        return Result.succ(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已删除！");
        return Result.succ(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog  ) {
        // System.out.println(blog.toString());
        //System.out.println("输入中...");
        Blog temp = null; ;
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
        temp.setUserId(ShiroUtil.getProfile().getId());
        temp.setCreated(LocalDateTime.now());
        temp.setStatus(blog.getStatus());
        // System.out.println("编辑中...");

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
//        System.out.println(blog.getStatus() + "" + temp);

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
 */
        //System.out.println(id);

        Blog blog = blogService.getById(id);
        //System.out.println(blog.getStatus());


        //blogService.delete(id);

        blogService.removeById(id);
        return Result.succ("删除成功", null);
    }


}

