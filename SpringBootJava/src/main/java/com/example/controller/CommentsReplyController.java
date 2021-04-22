package com.example.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.lang.Result;
import com.example.entity.Cominfo;
import com.example.entity.CommentsReply;
import com.example.service.CommentsReplyService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jca.cci.RecordTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评论回复表 前端控制器
 * </p>
 *
 * @author Tu
 * @since 2021-04-21
 */
@Data
@RestController
//@RequestMapping("/comments-reply")
public class CommentsReplyController {
    @Autowired
    CommentsReplyService commentsReplyService;

    /**
     * [java.lang.Integer]
     *
     * @return com.example.common.lang.Result
     * @author Tu
     * @date 2021/4/22 9:25
     * @message 根据
     * 评论表与回复表
     * id   --->  commentid
     */
    @GetMapping("/getreply")
    public Result getreply(Integer commentid) {
        QueryWrapper<CommentsReply> queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("create_time");
        queryWrapper.like("comment_id", commentid);
        List<CommentsReply> cominfos = commentsReplyService.list(queryWrapper);

        return Result.succ("", cominfos);
    }
    @PostMapping("/replypost")
    public Result replypost(@RequestBody CommentsReply commentsReply){
        CommentsReply commentsReply1=new CommentsReply();
        BeanUtil.copyProperties(commentsReply,commentsReply1);
        commentsReplyService.saveOrUpdate(commentsReply1);
        return Result.succ("回复成功！");
    }
    @GetMapping("/replyzanlike")
    public Result replyzanlike(Integer id,Integer adddnum){
        CommentsReply cominfoService1 = commentsReplyService.getById(id);
        if(adddnum==1){
            cominfoService1.setLikenum(cominfoService1.getLikenum() + 1);
        }else{
            cominfoService1.setLikenum(cominfoService1.getLikenum() -1);
        }
        commentsReplyService.saveOrUpdate(cominfoService1);
        return Result.succ("success");
    }
}
