package com.example.controller;

/**
 * @author Tu
 * @Package com.example.controller
 * @date 2021/2/1-11:39
 */

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.dto.LoginDto;
import com.example.common.lang.Result;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.util.JwtUtils;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * 登录接口
 */
@Data
@RestController

public class AccountController {

    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @ResponseBody
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response, HttpServletRequest request) {
        User user = userService.getOne(new QueryWrapper<User>().eq("userName", loginDto.getUsername()));

/**
 *出现错误 及时 return
 */
        if (user == null) {
            System.out.println("用户不存在");

            //Assert.notNull(user, "用户不存在");
            return Result.fail("用户不存在", "-2");
        } else if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            System.out.println("密码不正确");
            return Result.fail("密码不正确");
        }


        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .put("status", user.getStatus())
                .map()
        );
    }

    /**
     * 登出接口
     *
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

    /**
     * 注册功能
     * Regina
     */
    @ResponseBody
    @PostMapping("/regina")
    public Result regina(@Validated @RequestBody LoginDto loginDto) {
        User user = userService.getOne(new QueryWrapper<User>().eq("userName", loginDto.getUsername()));

        if (user != null) {
            System.out.println(user);
            return Result.fail("用户已存在");
        }
        System.out.println(user);
        User userRegina = null;
        userRegina = new User();
        //加密
        String pass = user.getPassword();
        userRegina.setUsername(user.getUsername());
        userRegina.setPassword(SecureUtil.md5(pass));
        userRegina.setAvatar(user.getAvatar());
        userRegina.setEmail(user.getEmail());
        userRegina.setCreated(LocalDateTime.now());
        userRegina.setStatus(user.getStatus());
        userService.saveOrUpdate(userRegina);
        System.out.println("注册成功！" + userRegina);
        return Result.succ("注册成功！");
    }

    /**
     * @return com.example.common.lang.Result
     * @author Tu
     * @date 2021/3/4 10:00
     * @message 获取用户列表
     * 仿写Blog列表
     */
    @ResponseBody
    @GetMapping("/userList")
    public Result users(Integer currentPage) {
        /**
         * [java.lang.Integer]
         * @author Tu
         * @date 2021/3/5 14:33
         * @message
         * @return com.example.common.lang.Result
         * 将用户列表返回
         * 将密码过滤掉返回
         * 参考：https://blog.csdn.net/dudufine/article/details/52218463
         */
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        Page page = new Page(currentPage, 5);
        IPage userlists = userService.page(page, new QueryWrapper<User>().orderByDesc("created"));
        JSONObject jsonObject = new JSONObject(userlists); //可以将json格式的字符串变成json对象
        JSONArray jsonArray = (JSONArray) jsonObject.get("records");
//        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonData = (JSONObject) jsonArray.get(i);//得到对象中的第i条记录
            jsonData.remove("password");
//            System.out.println("data[" + i + "]:" + jsonData.remove("password"));
        }
//        System.out.println(String.valueOf(jsonObject));
//        return Result.succ(userlists);
        //过滤后的
        return Result.succ(jsonObject);
    }

    /**
     * [java.lang.Long]
     *
     * @return com.example.common.lang.Result
     * @author Tu
     * @date 2021/3/5 15:41
     * @message 删除用户
     * http://localhost:8081/deleteUser?id=1&ststus=10
     * get方式接受多个参数
     */
    @ResponseBody
    @GetMapping("/deleteUser")
    public Result deleteUser(@RequestParam Integer id, @RequestParam Integer status) {
//前端status 当前操作者
        if (status != 0) {
            return Result.fail("没有权限");
        }
        User userid = userService.getById(id);
//        System.out.println(userid);
        userService.removeById(userid);
        return Result.succ("删除成功", null);
    }
}
