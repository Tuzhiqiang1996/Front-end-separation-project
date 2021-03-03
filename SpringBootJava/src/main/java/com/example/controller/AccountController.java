package com.example.controller;

/**
 * @author Tu
 * @Package com.example.controller
 * @date 2021/2/1-11:39
 */

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.dto.LoginDto;
import com.example.common.lang.Result;
import com.example.entity.Blog;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.util.JwtUtils;
import com.example.util.coolutil;
import lombok.Data;
import org.apache.logging.log4j.util.Base64Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
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
/**
 * 登录成功后 用户信息会保存在session中
 */

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
        System.out.println("注册成功！"+userRegina);
        return Result.succ("注册成功！");
    }

}
