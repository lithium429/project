/*
Navicat MySQL Data Transfer

Source Server         : server_3306
Source Server Version : 50096
Source Host           : 192.168.2.233:3306
Source Database       : project

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2015-03-27 09:04:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `action`
-- ----------------------------
DROP TABLE IF EXISTS `action`;
CREATE TABLE `action` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  `sort` int(11) NOT NULL default '0',
  `create_time` datetime NOT NULL,
  `menu_id` int(11) NOT NULL COMMENT '菜单_id',
  PRIMARY KEY  (`id`),
  KEY `fk_action_menu_id` (`menu_id`),
  CONSTRAINT `fk_action_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of action
-- ----------------------------
INSERT INTO `action` VALUES ('1', '添加', '1', '2014-07-18 16:42:05', '2');
INSERT INTO `action` VALUES ('2', '修改', '2', '2014-07-18 16:42:10', '2');
INSERT INTO `action` VALUES ('3', '删除', '3', '2014-08-20 11:16:41', '2');
INSERT INTO `action` VALUES ('4', '设置权限', '4', '2014-08-20 11:17:00', '2');
INSERT INTO `action` VALUES ('5', '添加', '5', '2014-08-20 11:19:12', '3');
INSERT INTO `action` VALUES ('6', '修改', '6', '2014-08-20 13:54:15', '3');
INSERT INTO `action` VALUES ('7', '删除', '7', '2014-08-20 13:54:40', '3');
INSERT INTO `action` VALUES ('8', '查看', '8', '2014-08-20 13:54:46', '3');
INSERT INTO `action` VALUES ('9', '批量删除', '9', '2014-08-20 13:55:09', '3');
INSERT INTO `action` VALUES ('10', '禁用', '10', '2014-08-20 13:55:18', '3');
INSERT INTO `action` VALUES ('11', '启用', '11', '2014-08-20 13:55:24', '3');
INSERT INTO `action` VALUES ('12', '批量禁用', '12', '2014-08-20 13:55:33', '3');
INSERT INTO `action` VALUES ('13', '批量启用', '13', '2014-08-20 13:55:41', '3');
INSERT INTO `action` VALUES ('14', '重置密码', '14', '2014-08-20 13:55:54', '3');
INSERT INTO `action` VALUES ('15', '部门添加', '15', '2014-08-20 13:56:43', '3');
INSERT INTO `action` VALUES ('16', '部门修改', '16', '2014-08-20 13:56:55', '3');
INSERT INTO `action` VALUES ('17', '部门删除', '17', '2014-08-20 13:57:05', '3');
INSERT INTO `action` VALUES ('37', '重新发送', '37', '2014-10-11 17:26:07', '16');
INSERT INTO `action` VALUES ('38', '查看', '38', '2014-10-11 17:26:16', '16');
INSERT INTO `action` VALUES ('39', '添加', '39', '2014-10-13 12:01:25', '17');
INSERT INTO `action` VALUES ('40', '修改', '40', '2014-10-13 12:01:31', '17');
INSERT INTO `action` VALUES ('41', '删除', '41', '2014-10-13 12:01:36', '17');
INSERT INTO `action` VALUES ('42', '批量删除', '42', '2014-10-13 12:01:43', '17');
INSERT INTO `action` VALUES ('43', '查看', '43', '2014-10-13 12:01:48', '17');
INSERT INTO `action` VALUES ('72', '添加', '72', '2014-12-25 10:16:33', '27');
INSERT INTO `action` VALUES ('73', '修改', '73', '2014-12-25 10:16:38', '27');
INSERT INTO `action` VALUES ('74', '删除', '74', '2014-12-25 10:16:47', '27');

-- ----------------------------
-- Table structure for `company`
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(500) NOT NULL,
  `phone` varchar(20) default NULL,
  `fax` varchar(20) default NULL,
  `zip_code` varchar(10) default NULL,
  `address` varchar(500) default NULL,
  `site` varchar(200) default NULL,
  `email` varchar(60) default NULL,
  `info` varchar(4000) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('1', '易标标', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(60) NOT NULL COMMENT '名称',
  `sort` int(11) NOT NULL default '0' COMMENT '排序',
  `parent_id` int(11) default NULL COMMENT '父级id',
  `layer` int(11) NOT NULL default '0' COMMENT '层级',
  `is_leaf` bit(1) NOT NULL default b'1',
  `create_time` datetime NOT NULL,
  `acount` int(11) NOT NULL default '0' COMMENT '通讯录数量',
  `type` int(1) default '1' COMMENT '类型：1.快赔中心，2.保险公司',
  `code` varchar(255) default NULL COMMENT '首字母组合',
  PRIMARY KEY  (`id`),
  KEY `fk_p_dept_id` (`parent_id`),
  CONSTRAINT `department_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `department` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', '初始化部门', '1', null, '1', '', '2014-10-08 14:18:36', '0', '0', null);

-- ----------------------------
-- Table structure for `login_log`
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `type` int(11) NOT NULL default '1' COMMENT '类型：1.普通web，2.即时通讯客户端',
  `ip` varchar(40) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_login_log_user_id` (`user_id`),
  CONSTRAINT `fk_login_log_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=289 DEFAULT CHARSET=utf8;
 
-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(60) NOT NULL COMMENT '名称',
  `url` varchar(128) NOT NULL COMMENT '路径',
  `parent_id` int(11) default NULL COMMENT '父级id',
  `is_leaf` bit(1) NOT NULL default b'0' COMMENT '是否叶子节点',
  `layer` int(11) NOT NULL default '0' COMMENT '层级',
  `sort` int(11) NOT NULL default '0' COMMENT '排序',
  `child_ids` varchar(200) default NULL COMMENT '子集id集合',
  `is_enable` bit(1) NOT NULL default b'1' COMMENT '是否启用',
  `icon_class` varchar(20) default NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_menu_parent_id` (`parent_id`),
  CONSTRAINT `fk_menu_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '系统管理', 'javascript:void(0);', null, '', '1', '1', '2,3,27', '', 'os_setting', '2014-07-18 14:26:57');
INSERT INTO `menu` VALUES ('2', '角色权限', 'admin/role/list.do', '1', '', '2', '2', '', '', '', '2014-07-18 14:30:36');
INSERT INTO `menu` VALUES ('3', '组织架构', 'admin/user/list.do', '1', '', '2', '3', '', '', null, '2014-07-18 14:34:07');
INSERT INTO `menu` VALUES ('4', '系统日志', 'javascript:void(1)', null, '', '1', '5', '5,6', '', 'crm', '2014-09-05 15:21:47');
INSERT INTO `menu` VALUES ('5', '登录日志', 'admin/loginLog/list.do', '4', '', '2', '6', '', '', null, '2014-09-05 15:22:43');
INSERT INTO `menu` VALUES ('6', '操作日志', 'admin/systemLog/list.do', '4', '', '2', '7', '', '', null, '2014-09-05 15:23:41');
INSERT INTO `menu` VALUES ('15', '短信管理', 'javascript:void(2);', null, '', '1', '33', '16,17', '', 'portal', '2014-10-11 17:25:12');
INSERT INTO `menu` VALUES ('16', '短信记录', 'admin/sms/list.do', '15', '', '2', '34', '', '', '', '2014-10-11 17:25:41');
INSERT INTO `menu` VALUES ('17', '短信模板管理', 'admin/smsTemplate/list.do', '1', '', '2', '35', '', '', '', '2014-10-13 12:01:11');
INSERT INTO `menu` VALUES ('27', '区域管理', 'admin/region/list.do', '1', '', '2', '4', '', '', '', '2014-12-25 10:16:18');

-- ----------------------------
-- Table structure for `region`
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` int(11) NOT NULL default '0',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `type` int(11) NOT NULL default '1' COMMENT '类型（1省，2市，3县、区）',
  `parent_id` int(11) default NULL COMMENT '父级ID',
  PRIMARY KEY  (`id`),
  KEY `fk_region_parent_id` (`parent_id`),
  CONSTRAINT `fk_region_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `region` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of region
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `sort` int(11) NOT NULL default '0' COMMENT '排序',
  `create_time` datetime NOT NULL,
  `is_super` bit(1) NOT NULL default b'0' COMMENT '是否超管',
  `role_id` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '系统管理员', '1', '2014-07-23 14:44:09', '', null);

-- ----------------------------
-- Table structure for `role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` int(11) NOT NULL auto_increment,
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) default NULL,
  `action_id` int(11) default NULL COMMENT '动作id',
  `is_action` bit(1) NOT NULL default b'0' COMMENT '是否动作',
  PRIMARY KEY  (`id`),
  KEY `fk_menu_id` USING BTREE (`menu_id`),
  KEY `fk_role_id` USING BTREE (`role_id`),
  KEY `fk_action_id` (`action_id`),
  CONSTRAINT `fk_action_id` FOREIGN KEY (`action_id`) REFERENCES `action` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1', '1', null, '');
INSERT INTO `role_menu` VALUES ('2', '1', '2', null, '');
INSERT INTO `role_menu` VALUES ('3', '1', '3', null, '');
INSERT INTO `role_menu` VALUES ('4', '1', '4', null, '');
INSERT INTO `role_menu` VALUES ('5', '1', '5', null, '');
INSERT INTO `role_menu` VALUES ('6', '1', '6', null, '');
INSERT INTO `role_menu` VALUES ('7', '1', null, '1', '');
INSERT INTO `role_menu` VALUES ('8', '1', null, '2', '');
INSERT INTO `role_menu` VALUES ('9', '1', null, '3', '');
INSERT INTO `role_menu` VALUES ('10', '1', null, '4', '');
INSERT INTO `role_menu` VALUES ('11', '1', null, '5', '');
INSERT INTO `role_menu` VALUES ('12', '1', null, '6', '');
INSERT INTO `role_menu` VALUES ('13', '1', null, '7', '');
INSERT INTO `role_menu` VALUES ('14', '1', null, '8', '');
INSERT INTO `role_menu` VALUES ('15', '1', null, '9', '');
INSERT INTO `role_menu` VALUES ('16', '1', null, '10', '');
INSERT INTO `role_menu` VALUES ('17', '1', null, '11', '');
INSERT INTO `role_menu` VALUES ('18', '1', null, '12', '');
INSERT INTO `role_menu` VALUES ('19', '1', null, '13', '');
INSERT INTO `role_menu` VALUES ('20', '1', null, '14', '');
INSERT INTO `role_menu` VALUES ('21', '1', null, '15', '');
INSERT INTO `role_menu` VALUES ('22', '1', null, '16', '');
INSERT INTO `role_menu` VALUES ('23', '1', null, '17', '');

-- ----------------------------
-- Table structure for `sms`
-- ----------------------------
DROP TABLE IF EXISTS `sms`;
CREATE TABLE `sms` (
  `id` int(11) NOT NULL auto_increment,
  `sender_name` varchar(20) NOT NULL COMMENT '发送人',
  `receiver_name` varchar(20) default NULL COMMENT '接收人',
  `interface_type` int(11) default NULL COMMENT '接口类型：待定',
  `state` int(11) NOT NULL default '0' COMMENT '状态：0.成功，1.失败',
  `phone` varchar(20) NOT NULL COMMENT '手机号码',
  `content` varchar(2000) NOT NULL COMMENT '内容',
  `is_retry` bit(1) NOT NULL default b'0' COMMENT '是否是重发',
  `retry_count` int(11) NOT NULL default '0' COMMENT '重发次数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sms
-- ----------------------------

-- ----------------------------
-- Table structure for `sms_template`
-- ----------------------------
DROP TABLE IF EXISTS `sms_template`;
CREATE TABLE `sms_template` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  `create_time` datetime NOT NULL,
  `is_default` bit(1) NOT NULL default b'0' COMMENT '是否默认',
  `content` varchar(1000) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sms_template
-- ----------------------------

-- ----------------------------
-- Table structure for `system_log`
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `module` int(4) NOT NULL default '1' COMMENT ' ',
  `operate` varchar(20) NOT NULL,
  `content` varchar(200) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_system_log_user_id` USING BTREE (`user_id`),
  CONSTRAINT `fk_system_log_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_log
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(40) NOT NULL COMMENT '密码',
  `des_password` varchar(60) NOT NULL,
  `salt` varchar(16) NOT NULL COMMENT '密码盐值',
  `real_name` varchar(20) NOT NULL COMMENT '姓名',
  `dept_id` int(11) NOT NULL COMMENT '部门科室id',
  `is_allowso` bit(1) NOT NULL default b'0' COMMENT '是否支持外发短信',
  `state` int(11) NOT NULL COMMENT '状态：1.正常，2.冻结',
  `mobile` varchar(16) NOT NULL default '',
  `email` varchar(60) NOT NULL default '' COMMENT '电子邮箱',
  `sex` int(11) NOT NULL default '1' COMMENT '性别：1.男，2.女',
  `qq` varchar(16) default NULL,
  `remark` varchar(1000) default NULL,
  `is_first_login` bit(1) NOT NULL default b'1' COMMENT '是否第一次登录',
  `is_reseted_psw` bit(1) NOT NULL default b'0' COMMENT '是否重置密码',
  `has_address` bit(1) NOT NULL default b'0' COMMENT '是否创建通讯录',
  `create_time` datetime NOT NULL,
  `fname` varchar(20) NOT NULL COMMENT '拼音首字母',
  `pname` varchar(60) NOT NULL COMMENT '名称拼音',
  PRIMARY KEY  (`id`),
  KEY `fk_user_dept_id` (`dept_id`),
  CONSTRAINT `fk_user_dept_id` FOREIGN KEY (`dept_id`) REFERENCES `department` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '5e522a08081cdc2e0c177783b533e2614dd86878', '8479068fda430523', 'ef5be24b63689887', '系统管理员', '1', '', '1', '13666666666', 'admin@163.com', '1', '', '', '', '', '', '2014-07-22 00:00:00', 'X', 'xitongguanliyuan');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_user_role_id` (`role_id`),
  KEY `fk_role_user_id` (`user_id`),
  CONSTRAINT `fk_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
