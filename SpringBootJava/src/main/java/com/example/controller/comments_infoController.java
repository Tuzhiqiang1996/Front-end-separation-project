package com.example.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.lang.Result;
import com.example.entity.Cominfo;
import com.example.entity.CommentsReply;
import com.example.service.CominfoService;
import com.example.service.CommentsReplyService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tu
 * @Package com.example.controller
 * @date 2021/4/20-16:21
 * 评论
 */
@Data
@RestController
public class comments_infoController {
    @Autowired
    CominfoService cominfoService;
    @Autowired
    CommentsReplyService commentsReplyService;

    /**
     * [java.lang.Integer]
     *
     * @return com.example.common.lang.Result
     * @author Tu
     * @date 2021/4/21 11:34
     * @message 根据文章id  获取owerID 的数据
     * 想要将返回值 循环添加
     * 当 jsonData.getId() 值与 this.po(jsonData.getId())查询到的数据并入到当前集合中
     * typeid==1 是
     */
    @GetMapping("/getcom")
    public Result getcom(Integer ownerId, Integer typeid, Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (typeid == null || typeid < 1) {
            typeid = 1;
        }
        Page page = new Page(currentPage, 5);
        QueryWrapper<Cominfo> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.like("owner_id", ownerId);
        //queryWrapper.like("type_id", 1);
        //查询所有 未分类页
        List<Cominfo> cominfos = cominfoService.list(queryWrapper);
        if (cominfos == null || cominfos.size() == 0) {
            return Result.fail("没有数据！");
        }
        //s
//        List<CommentsReply> userinfo = new ArrayList<>();
//        for (int i = 0; i < cominfos.size(); i++) {
//            Cominfo jsonData = cominfos.get(i);
//            System.out.println(jsonData.getId());
//            //
//            if (this.po(jsonData.getId()).size() > 0) {
//                System.out.println(this.po(jsonData.getId()));
//            }
//        }
        //e

        //分页
        IPage pageData = cominfoService.page(page, queryWrapper);
        return Result.succ("success", pageData);
    }

    /**
     * [java.lang.Integer]
     *
     * @return java.util.List<com.example.entity.CommentsReply>
     * @author Tu
     * @date 2021/4/23 9:51
     * @message 通过getcom函数 在循环中查询到数据并插入到当前 cominfos.get(i)的数据中返回结果
     */
    public List<CommentsReply> po(Integer commentid) {
        QueryWrapper<CommentsReply> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.like("comment_id", commentid);
        List<CommentsReply> cominfos = commentsReplyService.list(queryWrapper);
        return cominfos;
    }

    /**
     * [com.example.entity.Cominfo]
     *
     * @return com.example.common.lang.Result
     * @author Tu
     * @date 2021/4/23 14:56
     * @message 评论文章
     */
    @PostMapping("/comment")
    public Result comment(@RequestBody Cominfo cominfo) {
        Cominfo com = new Cominfo();
//        com.setCreateTime(LocalDateTime.now());
        BeanUtil.copyProperties(cominfo, com);
//        System.out.println(cominfo);
        cominfoService.saveOrUpdate(com);
        return Result.succ("评论成功！");
    }

    @GetMapping("/commentzanlike")
    public Result commentzanlike(Integer id, Integer adddnum) {
        Cominfo cominfoService1 = cominfoService.getById(id);
        if (adddnum == 1) {
            cominfoService1.setLikeNum(cominfoService1.getLikeNum() + 1);
        } else {
            cominfoService1.setLikeNum(cominfoService1.getLikeNum() - 1);
        }
        cominfoService.saveOrUpdate(cominfoService1);
        return Result.succ("success");
    }

}
