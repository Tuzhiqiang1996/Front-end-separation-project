/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : tudemo

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2021-04-22 17:10:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comments_reply
-- ----------------------------
DROP TABLE IF EXISTS `comments_reply`;
CREATE TABLE `comments_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` varchar(32) NOT NULL COMMENT '评论主表id',
  `from_id` varchar(32) NOT NULL COMMENT '评论者id',
  `from_name` varchar(32) NOT NULL COMMENT '评论者名字',
  `from_avatar` varchar(512) DEFAULT '' COMMENT '评论者头像',
  `to_id` varchar(32) NOT NULL COMMENT '被评论者id',
  `to_name` varchar(32) NOT NULL COMMENT '被评论者名字',
  `to_avatar` varchar(512) DEFAULT '' COMMENT '被评论者头像',
  `content` varchar(512) DEFAULT NULL COMMENT '评论内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `likenum` bigint(20) NOT NULL COMMENT '点赞数',
  PRIMARY KEY (`id`),
  KEY `comment_id` (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='评论回复表';

-- ----------------------------
-- Records of comments_reply
-- ----------------------------
INSERT INTO `comments_reply` VALUES ('1', '7', '2', 'admin', 'https://img2.woyaogexing.com/2020/04/10/28450a7007f640968727cf5ee6721cbd!400x400.webp', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '699', '2021-04-21 18:01:14', '2021-04-21 18:01:14', '0');
INSERT INTO `comments_reply` VALUES ('2', '9', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '理解很快到位！', '2021-04-22 09:45:45', '2021-04-22 09:46:20', '1');
INSERT INTO `comments_reply` VALUES ('3', '9', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp\r\n', '谢谢', '2021-04-22 12:02:45', '2021-04-22 12:12:06', '0');
INSERT INTO `comments_reply` VALUES ('4', '9', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp\r\n', '是的tutu', '2021-04-22 14:46:01', '2021-04-22 14:46:01', '0');
INSERT INTO `comments_reply` VALUES ('5', '9', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp\r\n', '是的tutu', '2021-04-22 14:49:22', '2021-04-22 14:49:22', '0');
INSERT INTO `comments_reply` VALUES ('6', '9', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp\r\n', '123', '2021-04-22 14:50:26', '2021-04-22 14:50:26', '0');
INSERT INTO `comments_reply` VALUES ('7', '9', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp\r\n', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '你好', '2021-04-22 14:52:52', '2021-04-22 14:52:52', '0');
INSERT INTO `comments_reply` VALUES ('8', '9', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp\r\n', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '123你好', '2021-04-22 14:53:07', '2021-04-22 14:53:07', '0');
INSERT INTO `comments_reply` VALUES ('9', '9', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp\r\n', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp', '谢谢666', '2021-04-22 17:03:28', '2021-04-22 17:03:28', '0');
INSERT INTO `comments_reply` VALUES ('10', '5', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp\r\n', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '谢谢66', '2021-04-22 17:07:31', '2021-04-22 17:07:31', '0');
INSERT INTO `comments_reply` VALUES ('11', '5', '1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp\r\n', '3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', '哈哈', '2021-04-22 17:08:42', '2021-04-22 17:08:42', '0');
