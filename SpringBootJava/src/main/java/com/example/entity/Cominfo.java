package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2021-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comments_info")
public class Cominfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;  //原来 long

    //'评论者头像'

    private String fromAvatar;

    //评论者名字

    private String fromName;
    //''评论者id''

    private Integer fromId;

    //''被评论者-id，可以是人、项目、资源'

    private Integer ownerId;

    //'评论类型：对人评论-0，对项目评论-1，对资源评论-3'

    private String typeId;
    //'点赞的数量'

    private Integer likeNum;

    @NotBlank(message = "内容不能为空")
    @NotNull
    private String content;

//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


}
