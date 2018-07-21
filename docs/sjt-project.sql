/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : sjt-project

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 21/07/2018 18:08:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sjt_article
-- ----------------------------
DROP TABLE IF EXISTS `sjt_article`;
CREATE TABLE `sjt_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '',
  `from_name` varchar(64) NOT NULL DEFAULT '',
  `from_url` varchar(255) NOT NULL DEFAULT '',
  `author` varchar(64) NOT NULL DEFAULT '',
  `thumb_url` varchar(255) NOT NULL DEFAULT '',
  `url` varchar(255) NOT NULL DEFAULT '',
  `target` varchar(8) NOT NULL DEFAULT '',
  `sort` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `content` longtext NOT NULL,
  `publish_time` bigint(20) NOT NULL DEFAULT '0',
  `created_time` bigint(20) NOT NULL DEFAULT '0',
  `created_uid` int(11) NOT NULL DEFAULT '0',
  `updated_time` bigint(20) NOT NULL DEFAULT '0',
  `updated_uid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_article
-- ----------------------------
BEGIN;
INSERT INTO `sjt_article` VALUES (1, '后台', '', '', '', '', '', '', 0, 1, '', '', 0, 1528082694531, 1, 1528082694531, 1);
INSERT INTO `sjt_article` VALUES (2, '后台管理', 'fa fa-home', '/', '', '', '', '', 0, 1, '', '', 0, 1528082833701, 1, 1528082833701, 1);
INSERT INTO `sjt_article` VALUES (3, '用户管理', 'fa fa-user-circle', '/user/index', '', '', '', '', 0, 1, '', '', 0, 1528082886891, 1, 1528082886891, 1);
INSERT INTO `sjt_article` VALUES (4, '角色管理', 'fa fa-user-secret', '/role/index', '', '', '', '', 0, 1, '', '', 0, 1528082942723, 1, 1528082942723, 1);
INSERT INTO `sjt_article` VALUES (5, '配置管理', 'fa fa-cogs', '/settings/index', '', '', '', '', 0, 1, '', '', 0, 1528083035344, 1, 1528083035344, 1);
INSERT INTO `sjt_article` VALUES (6, '菜单管理', 'fa fa-link', '/menu/index', '', '', '', '', 0, 1, '', '', 0, 1528083079182, 1, 1528083079182, 1);
INSERT INTO `sjt_article` VALUES (7, '资源管理', 'fa fa-tree', '/resource/index', '', '', '', '', 0, 1, '', '', 0, 1528083144253, 1, 1528083144253, 1);
COMMIT;

-- ----------------------------
-- Table structure for sjt_category
-- ----------------------------
DROP TABLE IF EXISTS `sjt_category`;
CREATE TABLE `sjt_category` (
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_category
-- ----------------------------
BEGIN;
INSERT INTO `sjt_category` VALUES (1, '后台', 0, '', '', '', 0, 1, '', 1528082694531, 1, 1528082694531, 1);
INSERT INTO `sjt_category` VALUES (2, '后台管理', 1, 'fa fa-home', '/', '', 0, 1, '', 1528082833701, 1, 1528082833701, 1);
INSERT INTO `sjt_category` VALUES (3, '用户管理', 2, 'fa fa-user-circle', '/user/index', '', 0, 1, '', 1528082886891, 1, 1528082886891, 1);
INSERT INTO `sjt_category` VALUES (4, '角色管理', 2, 'fa fa-user-secret', '/role/index', '', 0, 1, '', 1528082942723, 1, 1528082942723, 1);
INSERT INTO `sjt_category` VALUES (5, '配置管理', 2, 'fa fa-cogs', '/settings/index', '', 0, 1, '', 1528083035344, 1, 1528083035344, 1);
INSERT INTO `sjt_category` VALUES (6, '菜单管理', 2, 'fa fa-link', '/menu/index', '', 0, 1, '', 1528083079182, 1, 1528083079182, 1);
INSERT INTO `sjt_category` VALUES (7, '资源管理', 2, 'fa fa-tree', '/resource/index', '', 0, 1, '', 1528083144253, 1, 1528083144253, 1);
COMMIT;

-- ----------------------------
-- Table structure for sjt_comment
-- ----------------------------
DROP TABLE IF EXISTS `sjt_comment`;
CREATE TABLE `sjt_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `category_id` int(11) NOT NULL DEFAULT '0',
  `article_id` int(11) NOT NULL DEFAULT '0',
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `to_uid` int(11) NOT NULL DEFAULT '0',
  `sort` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `created_time` bigint(20) NOT NULL DEFAULT '0',
  `created_uid` int(11) NOT NULL DEFAULT '0',
  `updated_time` bigint(20) NOT NULL DEFAULT '0',
  `updated_uid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_comment
-- ----------------------------
BEGIN;
INSERT INTO `sjt_comment` VALUES (1, '', 0, 0, 0, 0, 0, 1, 1528082694531, 1, 1528082694531, 1);
INSERT INTO `sjt_comment` VALUES (2, '', 0, 0, 1, 0, 0, 1, 1528082833701, 1, 1528082833701, 1);
INSERT INTO `sjt_comment` VALUES (3, '', 0, 0, 2, 0, 0, 1, 1528082886891, 1, 1528082886891, 1);
INSERT INTO `sjt_comment` VALUES (4, '', 0, 0, 2, 0, 0, 1, 1528082942723, 1, 1528082942723, 1);
INSERT INTO `sjt_comment` VALUES (5, '', 0, 0, 2, 0, 0, 1, 1528083035344, 1, 1528083035344, 1);
INSERT INTO `sjt_comment` VALUES (6, '', 0, 0, 2, 0, 0, 1, 1528083079182, 1, 1528083079182, 1);
INSERT INTO `sjt_comment` VALUES (7, '', 0, 0, 2, 0, 0, 1, 1528083144253, 1, 1528083144253, 1);
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_menu
-- ----------------------------
BEGIN;
INSERT INTO `sjt_menu` VALUES (1, '后台', 0, '', '', '', 0, 1, '', 1528082694531, 1, 1528082694531, 1);
INSERT INTO `sjt_menu` VALUES (2, '后台管理', 1, 'fa fa-home', '/', '', 0, 1, '', 1528082833701, 1, 1528082833701, 1);
INSERT INTO `sjt_menu` VALUES (3, '用户管理', 2, 'fa fa-user-circle', '/user/index', '', 0, 1, '', 1528082886891, 1, 1528082886891, 1);
INSERT INTO `sjt_menu` VALUES (4, '角色管理', 2, 'fa fa-user-secret', '/role/index', '', 0, 1, '', 1528082942723, 1, 1528082942723, 1);
INSERT INTO `sjt_menu` VALUES (5, '配置管理', 2, 'fa fa-cogs', '/settings/index', '', 0, 1, '', 1528083035344, 1, 1528083035344, 1);
INSERT INTO `sjt_menu` VALUES (6, '菜单管理', 2, 'fa fa-link', '/menu/index', '', 0, 1, '', 1528083079182, 1, 1528083079182, 1);
INSERT INTO `sjt_menu` VALUES (7, '资源管理', 2, 'fa fa-tree', '/resource/index', '', 0, 1, '', 1528083144253, 1, 1528083144253, 1);
COMMIT;

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
BEGIN;
INSERT INTO `sjt_relation` VALUES ('role_menu_1_1', 'role_menu', 1, 1);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_2', 'role_menu', 1, 2);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_3', 'role_menu', 1, 3);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_4', 'role_menu', 1, 4);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_5', 'role_menu', 1, 5);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_6', 'role_menu', 1, 6);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_7', 'role_menu', 1, 7);
INSERT INTO `sjt_relation` VALUES ('role_menu_2_1', 'role_menu', 2, 1);
INSERT INTO `sjt_relation` VALUES ('role_menu_2_2', 'role_menu', 2, 2);
INSERT INTO `sjt_relation` VALUES ('role_menu_2_3', 'role_menu', 2, 3);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_1', 'role_resource', 1, 1);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_10', 'role_resource', 1, 10);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_11', 'role_resource', 1, 11);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_12', 'role_resource', 1, 12);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_13', 'role_resource', 1, 13);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_14', 'role_resource', 1, 14);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_15', 'role_resource', 1, 15);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_16', 'role_resource', 1, 16);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_17', 'role_resource', 1, 17);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_18', 'role_resource', 1, 18);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_19', 'role_resource', 1, 19);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_2', 'role_resource', 1, 2);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_20', 'role_resource', 1, 20);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_21', 'role_resource', 1, 21);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_22', 'role_resource', 1, 22);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_23', 'role_resource', 1, 23);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_24', 'role_resource', 1, 24);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_3', 'role_resource', 1, 3);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_4', 'role_resource', 1, 4);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_5', 'role_resource', 1, 5);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_6', 'role_resource', 1, 6);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_7', 'role_resource', 1, 7);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_8', 'role_resource', 1, 8);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_9', 'role_resource', 1, 9);
INSERT INTO `sjt_relation` VALUES ('role_resource_2_1', 'role_resource', 2, 1);
INSERT INTO `sjt_relation` VALUES ('role_resource_2_2', 'role_resource', 2, 2);
INSERT INTO `sjt_relation` VALUES ('role_resource_2_3', 'role_resource', 2, 3);
INSERT INTO `sjt_relation` VALUES ('role_resource_2_4', 'role_resource', 2, 4);
INSERT INTO `sjt_relation` VALUES ('role_resource_2_5', 'role_resource', 2, 5);
INSERT INTO `sjt_relation` VALUES ('role_resource_2_6', 'role_resource', 2, 6);
INSERT INTO `sjt_relation` VALUES ('user_role_1_1', 'user_role', 1, 1);
INSERT INTO `sjt_relation` VALUES ('user_role_2_2', 'user_role', 2, 2);
COMMIT;

-- ----------------------------
-- Table structure for sjt_resource
-- ----------------------------
DROP TABLE IF EXISTS `sjt_resource`;
CREATE TABLE `sjt_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `parent_id` int(11) NOT NULL DEFAULT '0',
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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_resource
-- ----------------------------
BEGIN;
INSERT INTO `sjt_resource` VALUES (1, '后台', 0, 'manage', '', '', 0, 1, '', 1528081670164, 1, 1528081742458, 1);
INSERT INTO `sjt_resource` VALUES (2, '后台-菜单', 1, 'manage', 'menu', '', 0, 1, '', 1528081696180, 1, 1528082280902, 1);
INSERT INTO `sjt_resource` VALUES (3, '后台-资源', 1, 'manage', 'resource', '', 0, 1, '', 1528081721468, 1, 1528082287209, 1);
INSERT INTO `sjt_resource` VALUES (4, '后台-角色', 1, 'manage', 'role', '', 0, 1, '', 1528081764195, 1, 1528082294533, 1);
INSERT INTO `sjt_resource` VALUES (5, '后台-用户', 1, 'manage', 'user', '', 0, 1, '', 1528081790426, 1, 1528082300610, 1);
INSERT INTO `sjt_resource` VALUES (6, '后台-配置', 1, 'manage', 'settings', '', 0, 1, '', 1528081858994, 1, 1528082308047, 1);
INSERT INTO `sjt_resource` VALUES (7, '后台-菜单-添加', 2, 'manage', 'menu', 'add', 0, 1, '', 1528081911866, 1, 1528082318631, 1);
INSERT INTO `sjt_resource` VALUES (8, '后台-菜单-修改', 2, 'manage', 'menu', 'modify', 0, 1, '', 1528081955009, 1, 1528082337498, 1);
INSERT INTO `sjt_resource` VALUES (9, '后台-菜单-删除', 2, 'manage', 'menu', 'delete', 0, 1, '', 1528081985121, 1, 1528082346365, 1);
INSERT INTO `sjt_resource` VALUES (10, '后台-资源-添加', 3, 'manage', 'resource', 'add', 0, 1, '', 1528082024771, 1, 1528082355791, 1);
INSERT INTO `sjt_resource` VALUES (11, '后台-资源-修改', 3, 'manage', 'resource', 'modify', 0, 1, '', 1528082042903, 1, 1528082364006, 1);
INSERT INTO `sjt_resource` VALUES (12, '后台-资源-删除', 3, 'manage', 'resource', 'delete', 0, 1, '', 1528082084243, 1, 1528082374750, 1);
INSERT INTO `sjt_resource` VALUES (13, '后台-角色-添加', 4, 'manage', 'role', 'add', 0, 1, '', 1528082104404, 1, 1528082387074, 1);
INSERT INTO `sjt_resource` VALUES (14, '后台-角色-修改', 4, 'manage', 'role', 'modify', 0, 1, '', 1528082198733, 1, 1528082396288, 1);
INSERT INTO `sjt_resource` VALUES (15, '后台-角色-删除', 4, 'manage', 'role', 'delete', 0, 1, '', 1528082214265, 1, 1528082406423, 1);
INSERT INTO `sjt_resource` VALUES (16, '后台-角色-菜单', 4, 'manage', 'role', 'menu', 0, 1, '', 1528082271647, 1, 1528082271647, 1);
INSERT INTO `sjt_resource` VALUES (17, '后台-角色-资源', 4, 'manage', 'role', 'resource', 0, 1, '', 1528082432853, 1, 1528082432853, 1);
INSERT INTO `sjt_resource` VALUES (18, '后台-用户-添加', 5, 'manage', 'user', 'add', 0, 1, '', 1528082465188, 1, 1528082465188, 1);
INSERT INTO `sjt_resource` VALUES (19, '后台-用户-修改', 5, 'manage', 'user', 'modify', 0, 1, '', 1528082492740, 1, 1528082492740, 1);
INSERT INTO `sjt_resource` VALUES (20, '后台-用户-删除', 5, 'manage', 'user', 'delete', 0, 1, '', 1528082514205, 1, 1528082514205, 1);
INSERT INTO `sjt_resource` VALUES (21, '后台-用户-密码', 5, 'manage', 'user', 'password', 0, 1, '', 1528082586393, 1, 1528082586393, 1);
INSERT INTO `sjt_resource` VALUES (22, '后台-配置-添加', 6, 'manage', 'settings', 'add', 0, 1, '', 1528082616922, 1, 1528082616922, 1);
INSERT INTO `sjt_resource` VALUES (23, '后台-配置-修改', 6, 'manage', 'settings', 'modify', 0, 1, '', 1528082642509, 1, 1528082642509, 1);
INSERT INTO `sjt_resource` VALUES (24, '后台-配置-删除', 6, 'manage', 'settings', 'delete', 0, 1, '', 1528082662922, 1, 1528082662922, 1);
COMMIT;

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
BEGIN;
INSERT INTO `sjt_role` VALUES (1, '后台管理', 0, 1, '', 1528081589495, 1, 1528266877684, 1);
INSERT INTO `sjt_role` VALUES (2, '普通用户', 0, 1, '', 1528081606670, 1, 1528081606670, 1);
COMMIT;

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
  `description` text NOT NULL,
  `updated_time` bigint(20) NOT NULL DEFAULT '0',
  `updated_uid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_settings
-- ----------------------------
BEGIN;
INSERT INTO `sjt_settings` VALUES (1, 'defaultPassword', 'system', 'password', 1, '', 1527731577113, 1);
INSERT INTO `sjt_settings` VALUES (2, 'siteName', 'system', '未命名站点', 2, '', 1527731583739, 1);
INSERT INTO `sjt_settings` VALUES (3, 'manageMenuParentId', 'system', '1', 0, '管理后台菜单根节点ID', 1528094436264, 1);
COMMIT;

-- ----------------------------
-- Table structure for sjt_user
-- ----------------------------
DROP TABLE IF EXISTS `sjt_user`;
CREATE TABLE `sjt_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(32) NOT NULL DEFAULT '',
  `password` char(32) NOT NULL DEFAULT '',
  `salt` char(4) NOT NULL DEFAULT '',
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
BEGIN;
INSERT INTO `sjt_user` VALUES (1, 'admin', '管理员', '3c6d390f90495e033c2bec60d9827aa8', '9508', 0, 1, '', 1528081552985, '127.0.0.1', 1, 1528081552985, 1, 1532154017735, '127.0.0.1', 0);
INSERT INTO `sjt_user` VALUES (2, 'test', '测试', '4b361be828611add84453a24f39772a5', '0905', 0, 1, '', 1528081567988, '127.0.0.1', 1, 1528081567988, 1, 1528267171953, '127.0.0.1', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
