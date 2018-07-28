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

 Date: 28/07/2018 13:15:40
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
  `category_id` int(11) NOT NULL DEFAULT '0',
  `from_name` varchar(64) NOT NULL DEFAULT '',
  `from_url` varchar(255) NOT NULL DEFAULT '',
  `author` varchar(64) NOT NULL DEFAULT '',
  `thumb_url` varchar(255) NOT NULL DEFAULT '',
  `url` varchar(255) NOT NULL DEFAULT '',
  `target` varchar(8) NOT NULL DEFAULT '',
  `comment_enable` tinyint(4) NOT NULL DEFAULT '0',
  `sort` bigint(20) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `keywords` varchar(255) NOT NULL DEFAULT '',
  `description` text NOT NULL,
  `content` longtext NOT NULL,
  `publish_time` bigint(20) NOT NULL DEFAULT '0',
  `created_time` bigint(20) NOT NULL DEFAULT '0',
  `created_uid` int(11) NOT NULL DEFAULT '0',
  `updated_time` bigint(20) NOT NULL DEFAULT '0',
  `updated_uid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_article
-- ----------------------------
BEGIN;
INSERT INTO `sjt_article` VALUES (4, 'asfasfasf', 1, '', '', '', 'http://127.0.0.1:8080/files/image/3604551532597366968.jpg', '', '', 0, 1532597373045, 1, '', '', '<p>assaas</p>', 1532597373045, 1532597373045, 1, 1532597373045, 1);
INSERT INTO `sjt_article` VALUES (5, 'sasaf', 2, '', '', '', '', '', '', 1, 1532597387696, 1, '', '', '<p>safffffffffff</p><p>saf</p><p>as</p><p>f</p><p>asf</p><p>as</p><p>f</p><p>asf</p><p>as</p><p>fa</p><p>sf</p><p>asf</p><p>as</p><p>f</p><p>asf</p><p>as</p><p>f</p><p>asfasfasfasfasfasfasfasfasfasfasf</p>', 1532597387696, 1532597387696, 1, 1532600689136, 1);
INSERT INTO `sjt_article` VALUES (6, 'saafasf', 3, '', '', '', '', '', '', 0, 1532597402194, 1, '', '', '<p>sdgsdg</p>', 1532597402194, 1532597402194, 1, 1532597402194, 1);
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
  `keywords` varchar(255) NOT NULL DEFAULT '',
  `description` text NOT NULL,
  `created_time` bigint(20) NOT NULL DEFAULT '0',
  `created_uid` int(11) NOT NULL DEFAULT '0',
  `updated_time` bigint(20) NOT NULL DEFAULT '0',
  `updated_uid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_category
-- ----------------------------
BEGIN;
INSERT INTO `sjt_category` VALUES (1, '栏目一', 0, '', 'http://127.0.0.1:8001/columns-1-1.shtml', '', 0, 1, '', '', 1532420625702, 1, 1532420625702, 1);
INSERT INTO `sjt_category` VALUES (2, '栏目二', 0, '', 'http://127.0.0.1:8001/columns-2-1.shtml', '', 0, 1, '', '', 1532420653999, 1, 1532420653999, 1);
INSERT INTO `sjt_category` VALUES (3, '栏目三', 0, '', 'http://127.0.0.1:8001/columns-3-1.shtml', '', 0, 1, '', '', 1532420663511, 1, 1532420663511, 1);
INSERT INTO `sjt_category` VALUES (4, '子栏目', 2, '', 'http://127.0.0.1:8001/columns-4-1.shtml', '', 0, 1, '', '', 1532439692157, 1, 1532439692157, 1);
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
  `parent_uid` int(11) NOT NULL DEFAULT '0',
  `top_id` int(11) NOT NULL DEFAULT '0',
  `sort` bigint(20) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `publish_time` bigint(20) NOT NULL DEFAULT '0',
  `created_time` bigint(20) NOT NULL DEFAULT '0',
  `created_ip` varchar(16) NOT NULL DEFAULT '',
  `created_uid` int(11) NOT NULL DEFAULT '0',
  `updated_time` bigint(20) NOT NULL DEFAULT '0',
  `updated_uid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_comment
-- ----------------------------
BEGIN;
INSERT INTO `sjt_comment` VALUES (1, 'sfaasfafsafsafasfsaf', 2, 1, 0, 0, 0, 1532506177363, -1, 1532506177363, 1532506177363, '127.0.0.1', 1, 1532506363688, 1);
INSERT INTO `sjt_comment` VALUES (2, 'fasf', 2, 1, 0, 0, 0, 1532506372536, 1, 1532506372536, 1532506372536, '127.0.0.1', 1, 1532506372536, 1);
INSERT INTO `sjt_comment` VALUES (3, 'ffddfdfdf', 2, 1, 2, 1, 2, 1532506391305, 1, 1532506391305, 1532506391305, '127.0.0.1', 1, 1532506391305, 1);
INSERT INTO `sjt_comment` VALUES (4, 'ffff', 1, 2, 0, 0, 0, 1532506770763, 1, 1532506770763, 1532506770763, '127.0.0.1', 1, 1532506770763, 1);
INSERT INTO `sjt_comment` VALUES (5, 'vvvvvvvvvvv', 2, 5, 0, 0, 0, 1532599095779, 1, 1532599095779, 1532599095779, '127.0.0.1', 1, 1532599095779, 1);
INSERT INTO `sjt_comment` VALUES (6, 'vvvvdsdgsdg', 2, 5, 0, 0, 0, 1532599115877, 1, 1532599115877, 1532599115877, '127.0.0.1', 1, 1532599115877, 1);
INSERT INTO `sjt_comment` VALUES (7, 'nnnnn', 2, 5, 5, 1, 5, 1532599137076, 1, 1532599137076, 1532599137076, '127.0.0.1', 1, 1532599137076, 1);
INSERT INTO `sjt_comment` VALUES (8, 'asasf', 2, 5, 0, 0, 0, 1532709498319, 2, 1532709498319, 1532709498319, '127.0.0.1', 3, 1532709498319, 3);
INSERT INTO `sjt_comment` VALUES (9, 'asasf', 2, 5, 0, 0, 0, 1532709618950, 2, 1532709618950, 1532709618950, '127.0.0.1', 3, 1532709618950, 3);
INSERT INTO `sjt_comment` VALUES (10, 'ssss', 2, 5, 0, 0, 0, 1532709751872, 1, 1532709751872, 1532709751872, '127.0.0.1', 3, 1532709964280, 1);
INSERT INTO `sjt_comment` VALUES (11, 'ccccccc', 2, 5, 5, 1, 5, 1532709895993, 1, 1532709895993, 1532709895993, '127.0.0.1', 3, 1532709971988, 1);
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_menu
-- ----------------------------
BEGIN;
INSERT INTO `sjt_menu` VALUES (1, '后台', 0, '', '', '', 0, 1, '', 1528082694531, 1, 1528082694531, 1);
INSERT INTO `sjt_menu` VALUES (2, '后台管理', 1, 'fa fa-home', '/', '', 0, 1, '', 1528082833701, 1, 1528082833701, 1);
INSERT INTO `sjt_menu` VALUES (3, '用户管理', 2, 'fa fa-user-circle', '/user/index', '', 0, 1, '', 1528082886891, 1, 1528082886891, 1);
INSERT INTO `sjt_menu` VALUES (4, '角色管理', 2, 'fa fa-user-secret', '/role/index', '', 0, 1, '', 1528082942723, 1, 1528082942723, 1);
INSERT INTO `sjt_menu` VALUES (5, '配置管理', 2, 'fa fa-cogs', '/setting/index', '', 0, 1, '', 1528083035344, 1, 1532400091815, 1);
INSERT INTO `sjt_menu` VALUES (6, '菜单管理', 2, 'fa fa-link', '/menu/index', '', 0, 1, '', 1528083079182, 1, 1528083079182, 1);
INSERT INTO `sjt_menu` VALUES (7, '资源管理', 2, 'fa fa-tree', '/resource/index', '', 0, 1, '', 1528083144253, 1, 1528083144253, 1);
INSERT INTO `sjt_menu` VALUES (8, '文件管理', 2, 'fa fa-files-o', '/upload/index', '', 0, 1, '', 1532412594129, 1, 1532412621732, 1);
INSERT INTO `sjt_menu` VALUES (9, '发布内容', 1, 'fa fa-newspaper-o', '', '', 2, 1, '', 1532420277048, 1, 1532420430126, 1);
INSERT INTO `sjt_menu` VALUES (10, '栏目管理', 9, 'fa fa-certificate', '/category/index', '', 0, 1, '', 1532420307513, 1, 1532420307513, 1);
INSERT INTO `sjt_menu` VALUES (11, '文章管理', 9, 'fa fa-hacker-news', '/article/index', '', 2, 1, '', 1532438377409, 1, 1532438405904, 1);
INSERT INTO `sjt_menu` VALUES (12, '评论管理', 9, 'fa fa-comment-o', '/comment/index', '', 0, 1, '', 1532504918238, 1, 1532504918238, 1);
INSERT INTO `sjt_menu` VALUES (13, 'CMS', 0, '', '', '', 0, 1, '', 1532512109203, 1, 1532512120889, 1);
INSERT INTO `sjt_menu` VALUES (14, '菜单项', 13, '', '', '', 0, 1, '', 1532512702598, 1, 1532512702598, 1);
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
INSERT INTO `sjt_relation` VALUES ('role_menu_1_10', 'role_menu', 1, 10);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_11', 'role_menu', 1, 11);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_12', 'role_menu', 1, 12);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_2', 'role_menu', 1, 2);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_3', 'role_menu', 1, 3);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_4', 'role_menu', 1, 4);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_5', 'role_menu', 1, 5);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_6', 'role_menu', 1, 6);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_7', 'role_menu', 1, 7);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_8', 'role_menu', 1, 8);
INSERT INTO `sjt_relation` VALUES ('role_menu_1_9', 'role_menu', 1, 9);
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
INSERT INTO `sjt_relation` VALUES ('role_resource_1_25', 'role_resource', 1, 25);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_26', 'role_resource', 1, 26);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_27', 'role_resource', 1, 27);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_28', 'role_resource', 1, 28);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_29', 'role_resource', 1, 29);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_3', 'role_resource', 1, 3);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_30', 'role_resource', 1, 30);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_31', 'role_resource', 1, 31);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_32', 'role_resource', 1, 32);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_33', 'role_resource', 1, 33);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_34', 'role_resource', 1, 34);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_35', 'role_resource', 1, 35);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_36', 'role_resource', 1, 36);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_37', 'role_resource', 1, 37);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_38', 'role_resource', 1, 38);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_39', 'role_resource', 1, 39);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_4', 'role_resource', 1, 4);
INSERT INTO `sjt_relation` VALUES ('role_resource_1_40', 'role_resource', 1, 40);
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
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_resource
-- ----------------------------
BEGIN;
INSERT INTO `sjt_resource` VALUES (1, '后台', 0, 'manage', '', '', 0, 1, '', 1528081670164, 1, 1528081742458, 1);
INSERT INTO `sjt_resource` VALUES (2, '后台-菜单', 1, 'manage', 'menu', '', 0, 1, '', 1528081696180, 1, 1528082280902, 1);
INSERT INTO `sjt_resource` VALUES (3, '后台-资源', 1, 'manage', 'resource', '', 0, 1, '', 1528081721468, 1, 1528082287209, 1);
INSERT INTO `sjt_resource` VALUES (4, '后台-角色', 1, 'manage', 'role', '', 0, 1, '', 1528081764195, 1, 1528082294533, 1);
INSERT INTO `sjt_resource` VALUES (5, '后台-用户', 1, 'manage', 'user', '', 0, 1, '', 1528081790426, 1, 1528082300610, 1);
INSERT INTO `sjt_resource` VALUES (6, '后台-配置', 1, 'manage', 'setting', '', 0, 1, '', 1528081858994, 1, 1532400110993, 1);
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
INSERT INTO `sjt_resource` VALUES (22, '后台-配置-添加', 6, 'manage', 'setting', 'add', 0, 1, '', 1528082616922, 1, 1532400115508, 1);
INSERT INTO `sjt_resource` VALUES (23, '后台-配置-修改', 6, 'manage', 'setting', 'modify', 0, 1, '', 1528082642509, 1, 1532400119332, 1);
INSERT INTO `sjt_resource` VALUES (24, '后台-配置-删除', 6, 'manage', 'setting', 'delete', 0, 1, '', 1528082662922, 1, 1532400123596, 1);
INSERT INTO `sjt_resource` VALUES (25, '后台-文件', 1, 'manage', 'upload', '', 0, 1, '', 1528081858994, 1, 1532400110993, 1);
INSERT INTO `sjt_resource` VALUES (26, '后台-文件-添加', 25, 'manage', 'upload', 'add', 0, 1, '', 1528082616922, 1, 1532400115508, 1);
INSERT INTO `sjt_resource` VALUES (27, '后台-文件-修改', 25, 'manage', 'upload', 'modify', 0, 1, '', 1528082642509, 1, 1532400119332, 1);
INSERT INTO `sjt_resource` VALUES (28, '后台-文件-删除', 25, 'manage', 'upload', 'delete', 0, 1, '', 1528082662922, 1, 1532400123596, 1);
INSERT INTO `sjt_resource` VALUES (29, '后台-栏目', 1, 'manage', 'category', '', 0, 1, '', 1528081858994, 1, 1532400110993, 1);
INSERT INTO `sjt_resource` VALUES (30, '后台-栏目-添加', 29, 'manage', 'category', 'add', 0, 1, '', 1528082616922, 1, 1532400115508, 1);
INSERT INTO `sjt_resource` VALUES (31, '后台-栏目-修改', 29, 'manage', 'category', 'modify', 0, 1, '', 1528082642509, 1, 1532400119332, 1);
INSERT INTO `sjt_resource` VALUES (32, '后台-栏目-删除', 29, 'manage', 'category', 'delete', 0, 1, '', 1528082662922, 1, 1532400123596, 1);
INSERT INTO `sjt_resource` VALUES (33, '后台-文章', 1, 'manage', 'article', '', 0, 1, '', 1528081858994, 1, 1532400110993, 1);
INSERT INTO `sjt_resource` VALUES (34, '后台-文章-添加', 33, 'manage', 'article', 'add', 0, 1, '', 1528082616922, 1, 1532400115508, 1);
INSERT INTO `sjt_resource` VALUES (35, '后台-文章-修改', 33, 'manage', 'article', 'modify', 0, 1, '', 1528082642509, 1, 1532400119332, 1);
INSERT INTO `sjt_resource` VALUES (36, '后台-文章-删除', 33, 'manage', 'article', 'delete', 0, 1, '', 1528082662922, 1, 1532400123596, 1);
INSERT INTO `sjt_resource` VALUES (37, '后台-评论', 1, 'manage', 'comment', '', 0, 1, '', 1528081858994, 1, 1532400110993, 1);
INSERT INTO `sjt_resource` VALUES (38, '后台-评论-添加', 37, 'manage', 'comment', 'add', 0, 1, '', 1528082616922, 1, 1532400115508, 1);
INSERT INTO `sjt_resource` VALUES (39, '后台-评论-修改', 37, 'manage', 'comment', 'modify', 0, 1, '', 1528082642509, 1, 1532400119332, 1);
INSERT INTO `sjt_resource` VALUES (40, '后台-评论-删除', 37, 'manage', 'comment', 'delete', 0, 1, '', 1528082662922, 1, 1532400123596, 1);
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
-- Table structure for sjt_setting
-- ----------------------------
DROP TABLE IF EXISTS `sjt_setting`;
CREATE TABLE `sjt_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL DEFAULT '',
  `type` varchar(64) NOT NULL DEFAULT '',
  `content` text NOT NULL,
  `sort` tinyint(4) NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `updated_time` bigint(20) NOT NULL DEFAULT '0',
  `updated_uid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_setting
-- ----------------------------
BEGIN;
INSERT INTO `sjt_setting` VALUES (1, 'defaultPassword', 'system', 'password', 1, '', 1527731577113, 1);
INSERT INTO `sjt_setting` VALUES (2, 'siteName', 'system', '未命名站点', 2, '', 1532509471041, 1);
INSERT INTO `sjt_setting` VALUES (3, 'manageMenuParentId', 'system', '1', 1, '管理后台菜单根节点ID', 1532400139499, 1);
INSERT INTO `sjt_setting` VALUES (4, 'defaultCommentStatus', 'cms', '2', 0, '评论默认状态', 1532420732295, 1);
INSERT INTO `sjt_setting` VALUES (5, 'siteKeywords', 'cms', '', 0, '默认关键词', 1532509449362, 1);
INSERT INTO `sjt_setting` VALUES (6, 'siteName', 'cms', '一点业', 0, '站点名称', 1532509529929, 1);
INSERT INTO `sjt_setting` VALUES (7, 'siteDescription', 'cms', '', 0, '页面默认描述', 1532509548655, 1);
INSERT INTO `sjt_setting` VALUES (8, 'topTips', 'cmsSectionComm', '<div class=\"welcome lf\"><i class=\"fa fa-file-text\"></i>康美生态圈、大健康共创共享平台！</div>\n            <ul class=\"topnav rt\">\n                <li><a href=\"\">品牌介绍</a></li>\n                <li><a href=\"\">联系我们</a></li>\n                <li><a href=\"\">合作加盟</a></li>\n            </ul>', 0, '顶部导航', 1532511195071, 1);
INSERT INTO `sjt_setting` VALUES (9, 'carousel', 'cmsSectionIndex', '<div id=\"picshow\">\n                    <div id=\"picshow_img\">\n                        <ul>\n                            <li><a href=\"\" target=\"_blank\"><img src=\"/static/images/1.jpg\"></a></li>\n                            <li><a href=\"\" target=\"_blank\"><img src=\"/static/images/2.jpg\"></a></li>\n                            <li><a href=\"\" target=\"_blank\"><img src=\"/static/images/3.jpg\"></a></li>\n                            <li><a href=\"\" target=\"_blank\"><img src=\"/static/images/4.jpg\"></a></li>\n                            <li><a href=\"\" target=\"_blank\"><img src=\"/static/images/5.jpg\"></a></li>\n                            <li><a href=\"\" target=\"_blank\"><img src=\"/static/images/6.jpg\"></a></li>\n                        </ul>\n                    </div>\n                    <div id=\"picshow_tx\">\n                        <ul>\n                            <li><a href=\"\" target=\"_blank\">中国死飞店铺推介：上海死飞店FACTORY FIVE</a></li>\n                            <li><a href=\"\" target=\"_blank\">骑看世界：纯美的世界恬静的心冰岛骑游之旅</a></li>\n                            <li><a href=\"\" target=\"_blank\">空气糟糕透了！推荐几款实用的骑行防毒口罩</a></li>\n                            <li><a href=\"\" target=\"_blank\">[组图]1200万像素带Wi-Fi 骑行记录仪Gopro Hero3评测</a></li>\n                            <li><a href=\"\" target=\"_blank\">张向东：南非无危险的骑行与有纠结的ubuntu(组图)</a></li>\n                            <li><a href=\"\" target=\"_blank\">单车文化课堂⑥：学会撬胎补胎爆胎不再烦</a></li>\n                        </ul>\n                    </div>\n                </div>\n                <div id=\"select_btn\">\n                    <ul>\n                        <li><a href=\"\" target=\"_blank\">新闻</a></li>\n                        <li><a href=\"\" target=\"_blank\">观点</a></li>\n                        <li><a href=\"\" target=\"_blank\">跨界</a></li>\n                        <li><a href=\"\" target=\"_blank\">产品</a></li>\n                        <li><a href=\"\" target=\"_blank\">项目</a></li>\n                        <li><a href=\"\" target=\"_blank\">机构</a></li>\n                    </ul>\n                </div>', 0, '首页幻灯片', 1532511918901, 1);
INSERT INTO `sjt_setting` VALUES (10, 'tabs', 'cmsSectionIndex', '<li data-category=\"\" class=\"news_act\">全部</li>\n                <li data-category=\"1\">医疗</li>\n                <li data-category=\"2\">美容</li>\n                <li data-category=\"3\">健康</li>\n                <li data-category=\"4\">养生</li>', 0, '首页切签', 1532528151968, 1);
INSERT INTO `sjt_setting` VALUES (11, 'rigthSide', 'cmsSectionComm', '<!-- 电话 -->\n            <div class=\"phone1\"><img src=\"/static/images/phone.png\"></div>\n            <!-- 幻灯片2 -->\n            <div class=\"slider-outer\" id=\"j_silder_outer\">\n                <div class=\"img-item\">\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                </div>\n                <div class=\"img-item\">\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                </div>\n                <div class=\"img-item\">\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                </div>\n                <div class=\"img-item\">\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                </div>\n                <div class=\"img-item\">\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                    <div class=\"img\"></div>\n                </div>\n                <div class=\"btns\" data-num=\"0\">\n                    <div class=\"prev\"><</div>\n                    <div class=\"next\">></div>\n                </div>\n            </div>\n            <!-- 服务机构 -->\n            <div class=\"service_jg\">\n                <div class=\"ser_til\"><span>服务机构</span></div>\n                <ul>\n                    <li><a href=\"\"><img src=\"/static/images/jg1.png\"></a></li>\n                    <li><a href=\"\"><img src=\"/static/images/jg2.png\"></a></li>\n                </ul>\n            </div>\n            <!-- 精选优品 -->\n            <div class=\"service_jg\">\n                <div class=\"ser_til\"><span>精选优品</span></div>\n                <ul>\n                    <li><a href=\"\"><img src=\"/static/images/jx1.png\"></a></li>\n                    <li><a href=\"\"><img src=\"/static/images/jx2.png\"></a></li>\n                </ul>\n            </div>\n            <!-- 联系我们 -->\n            <div class=\"index_contact\">\n                <div class=\"contact_til\"><span>联系我们</span></div>\n                <div class=\"erweima\">\n                    <div class=\"erwm1\"><img src=\"/static/images/dyh.png\"><br>一点业订阅号</div>\n                    <div class=\"erwm2\"><img src=\"/static/images/fwh.png\"><br>一点业服务号</div>\n                </div>\n                <div class=\"phone2\"><img src=\"/static/images/phone2.png\"></div>\n            </div>', 0, '侧边栏目', 1532576632701, 1);
INSERT INTO `sjt_setting` VALUES (12, 'footer', 'cmsSectionComm', '<ul class=\"bot1\">\n        <li><a href=\"\">关于我们</a></li>\n        <li><a href=\"\">版权声明</a></li>\n        <li><a href=\"\">合作说明</a></li>\n        <li><a href=\"\">联系我们</a></li>\n        <li><a href=\"\">一点业商城</a></li>\n        <li class=\"bot1last\"><a href=\"\">友情链接</a></li>\n    </ul>\n    <div class=\"bot2\">Copyright © 2017-2018 The Paper All rights reserved.北京中科网软科技有限公司   京ICP备15035481号</div>\n    <div class=\"bot3\"><a href=\"\">沪公网安备31002645645454号</a></div>', 0, '底部信息', 1532511200063, 1);
INSERT INTO `sjt_setting` VALUES (13, 'cmsMenuParentId', 'system', '13', 0, '新闻站页面菜单根节点', 1532512133859, 1);
INSERT INTO `sjt_setting` VALUES (14, 'link', 'cmsSectionComm', '<a href=\"\">一点商城</a>', 0, '顶部导航外链', 1532513245469, 1);
INSERT INTO `sjt_setting` VALUES (15, 'detailTips', 'cmsSectionArticle', '<span class=\"cpcomm lf\">一点业产品报料：18614090994　　　一点业资讯，未经授权不得转载。</span>', 0, '详情页转载提示', 1532595727021, 1);
INSERT INTO `sjt_setting` VALUES (16, 'agreement', 'cmsSectionLogin', '<a href=\"\">用户协议</a>', 0, '用户协议', 1532680298140, 1);
INSERT INTO `sjt_setting` VALUES (17, 'apiUrl', 'sms', 'http://utf8.api.smschinese.cn', 0, '短信网关地址', 1532754092553, 1);
INSERT INTO `sjt_setting` VALUES (18, 'apiUser', 'sms', '', 0, '短信网关用户', 1532754871448, 1);
INSERT INTO `sjt_setting` VALUES (19, 'apiKey', 'sms', '', 0, '短信网关认证Key', 1532754876987, 1);
INSERT INTO `sjt_setting` VALUES (20, 'sendCode', 'sms', '验证码{code}三十分钟内有效', 0, '短信验证码内容', 1532754481936, 1);
COMMIT;

-- ----------------------------
-- Table structure for sjt_upload
-- ----------------------------
DROP TABLE IF EXISTS `sjt_upload`;
CREATE TABLE `sjt_upload` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `type` varchar(64) NOT NULL DEFAULT '',
  `content_type` varchar(255) NOT NULL DEFAULT '',
  `path` varchar(255) NOT NULL DEFAULT '',
  `size` bigint(20) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `created_time` bigint(20) NOT NULL DEFAULT '0',
  `created_uid` int(11) NOT NULL DEFAULT '0',
  `updated_time` bigint(20) NOT NULL DEFAULT '0',
  `updated_uid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_upload
-- ----------------------------
BEGIN;
INSERT INTO `sjt_upload` VALUES (1, '2018031510274349.jpg', 'image', 'image/jpeg', 'image/9729711532415535181.jpg', 0, -1, 1532415535181, 1, 1532415650835, 1);
INSERT INTO `sjt_upload` VALUES (2, '2018031510274349.jpg', 'image', 'image/jpeg', 'image/7597861532415666399.jpg', 0, -1, 1532415666400, 1, 1532415690026, 1);
INSERT INTO `sjt_upload` VALUES (3, '2018031510274349.jpg', 'image', 'image/jpeg', 'image/5422761532415800352.jpg', 0, -1, 1532415800352, 1, 1532419604697, 1);
INSERT INTO `sjt_upload` VALUES (4, '2018031510274349.jpg', 'image', 'image/jpeg', 'image/8437111532419726255.jpg', 0, -1, 1532419726256, 1, 1532419743379, 1);
INSERT INTO `sjt_upload` VALUES (5, '2018031510274349.jpg', 'image', 'image/jpeg', 'image/7114831532486875433.jpg', 0, -1, 1532486875434, 1, 1532487077688, 1);
INSERT INTO `sjt_upload` VALUES (6, '2018031510274349.jpg', 'image', 'image/jpeg', 'image/1725971532486902678.jpg', 0, -1, 1532486902678, 1, 1532487080746, 1);
INSERT INTO `sjt_upload` VALUES (7, '2018031510274349.jpg', 'image', 'image/jpeg', 'image/5064821532487050588.jpg', 0, -1, 1532487050588, 1, 1532487083631, 1);
INSERT INTO `sjt_upload` VALUES (8, '2018031510274349.jpg', 'image', 'image/jpeg', 'image/0286311532501450963.jpg', 474971, -1, 1532501450965, 0, 1532506917423, 1);
INSERT INTO `sjt_upload` VALUES (9, '2018031510274349.jpg', 'image', 'image/jpeg', 'image/0572841532501744138.jpg', 474971, -1, 1532501744142, 0, 1532506920175, 1);
INSERT INTO `sjt_upload` VALUES (10, '2018031510274349.jpg', 'image', 'image/jpeg', 'image/0422961532502158764.jpg', 474971, -1, 1532502158775, 1, 1532506923954, 1);
INSERT INTO `sjt_upload` VALUES (11, '2018031510274349.jpg', 'image', 'image/jpeg', 'image/3568551532502450241.jpg', 474971, -1, 1532502450242, 1, 1532506926575, 1);
INSERT INTO `sjt_upload` VALUES (12, '2018031510274349.jpg', 'image', 'image/jpeg', 'image/3604551532597366968.jpg', 474971, 1, 1532597366969, 1, 1532597366969, 1);
COMMIT;

-- ----------------------------
-- Table structure for sjt_user
-- ----------------------------
DROP TABLE IF EXISTS `sjt_user`;
CREATE TABLE `sjt_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(64) NOT NULL DEFAULT '',
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sjt_user
-- ----------------------------
BEGIN;
INSERT INTO `sjt_user` VALUES (1, 'admin', '管理员', '3c6d390f90495e033c2bec60d9827aa8', '9508', 0, 1, '', 1528081552985, '127.0.0.1', 1, 1528081552985, 1, 1532754868420, '127.0.0.1', 0);
INSERT INTO `sjt_user` VALUES (2, 'test', '测试', '4b361be828611add84453a24f39772a5', '0905', 0, 1, '', 1528081567988, '127.0.0.1', 1, 1528081567988, 1, 1528267171953, '127.0.0.1', 0);
INSERT INTO `sjt_user` VALUES (3, '11111111111', '网友_1532687002385709708', '', '', 0, 1, 'CMSWeb登录自动注册', 1532687002386, '127.0.0.1', 0, 0, 0, 1532754929371, '127.0.0.1', 0);
INSERT INTO `sjt_user` VALUES (4, '11111111112', '网友_f37d91de60694e148b5d69a24f67a7e0', '', '', 0, 1, 'CMSWeb登录自动注册', 1532748836829, '127.0.0.1', 0, 0, 0, 1532748894173, '127.0.0.1', 0);
INSERT INTO `sjt_user` VALUES (5, '11111111113', '网友_0ebfe158f01d4c3ea521c492f26298f4', '', '', 0, 1, 'CMSWeb登录自动注册', 1532749022284, '127.0.0.1', 0, 0, 0, 1532749022283, '127.0.0.1', 0);
INSERT INTO `sjt_user` VALUES (6, '11111111114', '网友_11158683fad24611af652acec9a803b6', '', '', 0, 1, 'CMSWeb登录自动注册', 1532749476950, '127.0.0.1', 0, 0, 0, 1532749476949, '127.0.0.1', 0);
INSERT INTO `sjt_user` VALUES (7, '11111111119', '网友_9260377fe8ef4dafbb09223ff2be8707', '', '', 0, 1, 'CMSWeb登录自动注册', 1532749877180, '127.0.0.1', 0, 0, 0, 1532749877180, '127.0.0.1', 0);
INSERT INTO `sjt_user` VALUES (8, '66666', '网友_dfdb9cb31d7e4489a95ae3af64543dd8', '', '', 0, 1, 'CMSWeb登录自动注册', 1532754803329, '127.0.0.1', 0, 0, 0, 1532754803327, '127.0.0.1', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
