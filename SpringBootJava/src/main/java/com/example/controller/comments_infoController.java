package com.example.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.common.lang.Result;
import com.example.entity.Cominfo;
import com.example.service.CominfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author Tu
 * @Package com.example.controller
 * @date 2021/4/20-16:21
 * 评论
 */
@RestController
public class comments_infoController {
    @Autowired
    CominfoService cominfoService;

    @GetMapping("/getcom")
    public Result getcom() {
        return Result.succ("");
    }

    @PostMapping("/comment")
    public Result comment(@RequestBody Cominfo cominfo) {
        Cominfo com = new Cominfo();
//        com.setCreateTime(LocalDateTime.now());
        BeanUtil.copyProperties(cominfo, com);
//        System.out.println(cominfo);
        cominfoService.saveOrUpdate(com);
        return Result.succ("评论成功！");
    }

}
