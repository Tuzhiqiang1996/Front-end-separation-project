package com.example.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.lang.Result;
import com.example.entity.Cominfo;
import com.example.service.CominfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * [java.lang.Integer]
     *
     * @return com.example.common.lang.Result
     * @author Tu
     * @date 2021/4/21 11:34
     * @message 根据文章id  获取owerID 的数据
     */
    @GetMapping("/getcom")
    public Result getcom(Integer ownerId, Integer typeid) {
        QueryWrapper<Cominfo> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.like("owner_id", ownerId);
        queryWrapper.like("type_id", typeid);

        List<Cominfo> cominfos = cominfoService.list(queryWrapper);
        //s
        Cominfo jsonData = null;
        for (int i = 0; i < cominfos.size(); i++) {
            jsonData = cominfos.get(i);

            System.out.println(this.po(jsonData.getCommentid()));

        }
        //e
        if (cominfos == null || cominfos.size() == 0) {
            return Result.fail("没有数据！");
        }
        return Result.succ("success", cominfos);
    }
public List<Cominfo> po(Integer commentid){
    QueryWrapper<Cominfo> queryWrapper = new QueryWrapper();
    queryWrapper.orderByDesc("create_time");
    queryWrapper.like("commentid", commentid);
    List<Cominfo> cominfos = cominfoService.list(queryWrapper);
        return cominfos;
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
