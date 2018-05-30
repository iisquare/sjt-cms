/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : sjt-cms

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-05-30 18:24:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sjt_menu
-- ----------------------------
DROP TABLE IF EXISTS `sjt_menu`;
CREATE TABLE `sjt_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `parent_id` int(11) NOT NULL DEFAULT '0',
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_menu
-- ----------------------------
INSERT INTO `sjt_menu` VALUES ('1', 'asf', '0', 'fa fa-bars fa-lg', '/asf/asf?asfasf', '', '0', '1', '', '1527667454646', '1', '1527671759549', '1');
INSERT INTO `sjt_menu` VALUES ('2', 'dddd', '1', '', '', '', '0', '2', '', '1527667508701', '1', '1527672406166', '1');
INSERT INTO `sjt_menu` VALUES ('3', 'ffff', '0', '', '', '', '0', '1', '', '1527667518466', '1', '1527667518466', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_role
-- ----------------------------
INSERT INTO `sjt_role` VALUES ('1', '超级管理员', '0', '-1', '', '0', '0', '0', '0');
INSERT INTO `sjt_role` VALUES ('2', '普通用户', '0', '-1', '', '0', '0', '0', '0');
INSERT INTO `sjt_role` VALUES ('3', '测试', '0', '-1', '', '1527583105457', '1', '1527583105457', '1');
INSERT INTO `sjt_role` VALUES ('4', 'test', '0', '-1', '', '1527583177070', '1', '1527583177070', '1');
INSERT INTO `sjt_role` VALUES ('5', '超级管理员', '1', '-1', '', '1527583325327', '1', '1527583325327', '1');
INSERT INTO `sjt_role` VALUES ('6', 'sad', '0', '-1', '', '0', '0', '1527583577467', '1');
INSERT INTO `sjt_role` VALUES ('7', 'fsaf', '0', '-1', '', '0', '0', '1527583582842', '1');
INSERT INTO `sjt_role` VALUES ('8', 'ds', '4', '2', '这是一段描述', '1527584884840', '1', '1527661456804', '1');
INSERT INTO `sjt_role` VALUES ('9', '测试', '0', '1', '', '1527584889765', '1', '1527584922228', '1');
INSERT INTO `sjt_role` VALUES ('10', 'safdd', '0', '1', '', '1527589963577', '1', '1527653205231', '1');
INSERT INTO `sjt_role` VALUES ('11', 'dddd', '0', '2', '', '1527652152631', '1', '1527652152631', '1');
INSERT INTO `sjt_role` VALUES ('12', 'saf', '0', '2', '', '1527660984958', '1', '1527660984958', '1');
INSERT INTO `sjt_role` VALUES ('13', 'asf', '0', '1', '', '1527660988745', '1', '1527660988745', '1');
INSERT INTO `sjt_role` VALUES ('14', 'asf', '0', '1', '', '1527660992498', '1', '1527660992498', '1');
INSERT INTO `sjt_role` VALUES ('15', 'asf', '0', '1', '', '1527660997737', '1', '1527660997737', '1');
INSERT INTO `sjt_role` VALUES ('16', 'asf', '0', '-1', '', '1527661001451', '1', '1527661001451', '1');
INSERT INTO `sjt_role` VALUES ('17', 'asf', '0', '1', '', '1527661005483', '1', '1527661005483', '1');
INSERT INTO `sjt_role` VALUES ('18', 'asf', '0', '1', '', '1527661009394', '1', '1527661009394', '1');
INSERT INTO `sjt_role` VALUES ('19', 'asf', '0', '1', '', '1527661017657', '1', '1527661017657', '1');
INSERT INTO `sjt_role` VALUES ('20', 'asf', '0', '1', '', '1527661021490', '1', '1527661021490', '1');
INSERT INTO `sjt_role` VALUES ('21', 'asf', '0', '1', '', '1527661025258', '1', '1527661025258', '1');
INSERT INTO `sjt_role` VALUES ('22', 'asf', '0', '1', '', '1527661032386', '1', '1527661032386', '1');
INSERT INTO `sjt_role` VALUES ('23', 'asf', '0', '2', '', '1527661036743', '1', '1527661036743', '1');
INSERT INTO `sjt_role` VALUES ('24', 'asf', '0', '1', '', '1527661042458', '1', '1527661042458', '1');

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
