/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : sjt-cms

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-05-29 13:37:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sjt_menu
-- ----------------------------
DROP TABLE IF EXISTS `sjt_menu`;
CREATE TABLE `sjt_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `parent` int(11) NOT NULL DEFAULT '0',
  `icon` varchar(64) NOT NULL DEFAULT '',
  `url` varchar(255) NOT NULL DEFAULT '',
  `target` varchar(8) NOT NULL DEFAULT '',
  `sort` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `created_time` bigint(20) NOT NULL DEFAULT '0',
  `created_uid` int(11) NOT NULL DEFAULT '0',
  `updated_time` bigint(20) NOT NULL DEFAULT '0',
  `updated_uid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sjt_relation
-- ----------------------------
DROP TABLE IF EXISTS `sjt_relation`;
CREATE TABLE `sjt_relation` (
  `id` varchar(64) NOT NULL,
  `type` varchar(32) NOT NULL DEFAULT '',
  `aid` int(11) NOT NULL DEFAULT '0',
  `bid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_relation
-- ----------------------------

-- ----------------------------
-- Table structure for sjt_resource
-- ----------------------------
DROP TABLE IF EXISTS `sjt_resource`;
CREATE TABLE `sjt_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `parent` int(11) NOT NULL DEFAULT '0',
  `module` varchar(64) NOT NULL DEFAULT '',
  `controller` varchar(64) NOT NULL DEFAULT '',
  `action` varchar(64) NOT NULL DEFAULT '',
  `sort` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `created_time` bigint(20) NOT NULL DEFAULT '0',
  `created_uid` int(11) NOT NULL DEFAULT '0',
  `updated_time` bigint(20) NOT NULL DEFAULT '0',
  `updated_uid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_resource
-- ----------------------------

-- ----------------------------
-- Table structure for sjt_role
-- ----------------------------
DROP TABLE IF EXISTS `sjt_role`;
CREATE TABLE `sjt_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `sort` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `created_time` bigint(20) NOT NULL DEFAULT '0',
  `created_uid` int(11) NOT NULL DEFAULT '0',
  `updated_time` bigint(20) NOT NULL DEFAULT '0',
  `updated_uid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_role
-- ----------------------------
INSERT INTO `sjt_role` VALUES ('1', '超级管理员', '0', '1', '', '0', '0', '0', '0');
INSERT INTO `sjt_role` VALUES ('2', '普通用户', '0', '1', '', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for sjt_settings
-- ----------------------------
DROP TABLE IF EXISTS `sjt_settings`;
CREATE TABLE `sjt_settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL DEFAULT '',
  `type` varchar(64) NOT NULL DEFAULT '',
  `content` text NOT NULL,
  `sort` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `updated_time` bigint(20) NOT NULL DEFAULT '0',
  `updated_uid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_settings
-- ----------------------------
INSERT INTO `sjt_settings` VALUES ('1', 'defaultPassword', 'system', 'password', '0', '1', '', '0', '0');
INSERT INTO `sjt_settings` VALUES ('2', 'siteName', 'system', '未命名站点', '0', '1', '', '0', '0');

-- ----------------------------
-- Table structure for sjt_user
-- ----------------------------
DROP TABLE IF EXISTS `sjt_user`;
CREATE TABLE `sjt_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(32) NOT NULL DEFAULT '',
  `password` char(32) NOT NULL DEFAULT '',
  `salt` tinyint(4) NOT NULL DEFAULT '0',
  `sort` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `created_time` bigint(20) NOT NULL DEFAULT '0',
  `created_ip` varchar(16) NOT NULL DEFAULT '',
  `created_uid` int(11) NOT NULL DEFAULT '0',
  `updated_time` bigint(20) NOT NULL DEFAULT '0',
  `updated_uid` int(11) NOT NULL DEFAULT '0',
  `logined_time` bigint(20) NOT NULL DEFAULT '0',
  `logined_ip` varchar(16) NOT NULL DEFAULT '',
  `locked_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `unq_serial` (`serial`),
  KEY `unq_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_user
-- ----------------------------
INSERT INTO `sjt_user` VALUES ('1', 'admin', '管理员', '', '0', '0', '1', '', '0', '', '0', '0', '0', '0', '', '0');
INSERT INTO `sjt_user` VALUES ('2', 'test', '测试', '', '0', '0', '1', '', '0', '', '0', '0', '0', '0', '', '0');
