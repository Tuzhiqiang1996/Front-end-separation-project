/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : tudemo

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2021-04-22 17:11:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for m_user
-- ----------------------------
DROP TABLE IF EXISTS `m_user`;
CREATE TABLE `m_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `status` int(5) NOT NULL COMMENT '用户权限',
  `created` datetime DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_USERNAME` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_user
-- ----------------------------
INSERT INTO `m_user` VALUES ('1', 'markerhub', 'https://img2.woyaogexing.com/2020/04/10/2911e52acd544055ab7f434108037632!400x400.webp\r\n', '11111@qq.com', '96e79218965eb72c92a549dd5a330112', '0', '2020-04-20 10:44:01', '2021-04-14 15:47:03');
INSERT INTO `m_user` VALUES ('2', 'admin', 'https://img2.woyaogexing.com/2020/04/10/28450a7007f640968727cf5ee6721cbd!400x400.webp', null, 'e10adc3949ba59abbe56e057f20f883e', '1', '2021-03-02 11:32:02', null);
INSERT INTO `m_user` VALUES ('3', 'tu', 'https://img2.woyaogexing.com/2020/04/10/a5883aa3fb1841fbb38f3e9adf465473!400x400.webp', null, 'e10adc3949ba59abbe56e057f20f883e', '2', '2021-03-03 11:15:21', null);
INSERT INTO `m_user` VALUES ('4', 'ceshi1', 'https://img2.woyaogexing.com/2020/04/10/94ff4de5a7d6463ca8069d0423a57606!400x400.webp', null, '90db7a241ee36462c1d24a2cc45c1c8c', '2', '2021-03-03 11:32:28', null);
INSERT INTO `m_user` VALUES ('6', 'ceshi3', 'https://img2.woyaogexing.com/2020/04/10/a2fa93db65364f21b0de0cf08950a74c!400x400.webp', '1602072414@qq.com', '3eec4a416f2af2dbc75e2e7a04f5ccf9', '1', '2021-03-03 14:11:34', null);
INSERT INTO `m_user` VALUES ('8', 'uu', 'https://img2.woyaogexing.com/2020/04/10/a2fa93db65364f21b0de0cf08950a74c!400x400.webp', 'rr123465@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '1', '2021-03-15 14:23:26', null);
