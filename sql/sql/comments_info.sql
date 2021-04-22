/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : tudemo

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2021-04-22 17:10:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comments_info
-- ----------------------------
DROP TABLE IF EXISTS `comments_info`;
CREATE TABLE `comments_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论主键id',
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='评论主表';

-- ----------------------------
-- Records of comments_info
-- ----------------------------
INSERT INTO `comments_info` VALUES ('1', '0', '1', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp', '22', '不错哦', '2021-04-20 17:27:31', '2021-04-20 17:27:31');
INSERT INTO `comments_info` VALUES ('2', '1', '1', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp', '22', '不错哦', '2021-04-20 17:29:35', '2021-04-20 17:29:35');
INSERT INTO `comments_info` VALUES ('5', '1', '40', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '22', '欧克欧克', '2021-04-21 11:40:26', '2021-04-21 11:40:26');
INSERT INTO `comments_info` VALUES ('6', '1', '40', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '22', '666', '2021-04-21 11:42:37', '2021-04-21 11:42:37');
INSERT INTO `comments_info` VALUES ('7', '1', '40', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '22', '666', '2021-04-21 14:41:34', '2021-04-21 17:11:42');
INSERT INTO `comments_info` VALUES ('8', '0', '40', '2', 'admin', 'https://img2.woyaogexing.com/2020/04/10/28450a7007f640968727cf5ee6721cbd!400x400.webp', '22', '真棒！', '2021-04-21 14:58:14', '2021-04-21 17:41:56');
INSERT INTO `comments_info` VALUES ('9', '1', '40', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '22', '   @Override\n    public PageResult getAll(int pageNum, int pageSize) {\n        PageHelper.startPage(pageNum, pageSize);\n        List<BlogComment> blogCategories = blogCommentMapper.getAllComment();\n        int totalCategories = blogCommentMapper.getTotalComments();\n        PageResult pageResult = new PageResult(blogCategories, totalCategories, 10, pageNum);\n        return pageResult;\n    }\n\n    @Override\n    public int getTotalComments() {\n        return blogCommentMapper.getTotalComments();\n———', '2021-04-21 15:07:51', '2021-04-21 15:07:51');
INSERT INTO `comments_info` VALUES ('10', '1', '31', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '22', '写的真好！', '2021-04-21 15:29:14', '2021-04-21 15:29:14');
INSERT INTO `comments_info` VALUES ('11', '1', '40', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp\r\n', '2', '实在是666', '2021-04-22 14:58:23', '2021-04-22 14:58:23');
