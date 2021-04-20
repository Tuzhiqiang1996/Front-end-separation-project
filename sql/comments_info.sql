CREATE TABLE `comments_info` (
                                 `id` varchar(32) NOT NULL COMMENT '评论主键id',
                                 `type_id` tinyint(1) NOT NULL COMMENT '评论类型：对人评论-0，对项目评论-1，对资源评论-3',
                                 `owner_id` varchar(32) NOT NULL COMMENT '被评论者-id，可以是人、项目、资源',
                                 `from_id` varchar(32) NOT NULL COMMENT '评论者id',
                                 `from_name` varchar(32) NOT NULL COMMENT '评论者名字',
                                 `from_avatar` varchar(512) DEFAULT '' COMMENT '评论者头像',
                                 `like_num` int(11) DEFAULT '0' COMMENT '点赞的数量',
                                 `content` varchar(512) DEFAULT NULL COMMENT '评论内容',
                                 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                 PRIMARY KEY (`id`),
                                 KEY `owner_id` (`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论主表';
