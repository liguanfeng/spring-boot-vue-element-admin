/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : testdb

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 17/05/2020 23:31:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '唯一编号',
  `realName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '真实姓名',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '登录名称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '登录密码',
  `roleId` int DEFAULT NULL COMMENT '角色id',
  `isEnable` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `isMaster` tinyint DEFAULT NULL COMMENT '是否超级管理员',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='平台管理员';

-- ----------------------------
-- Records of admin
-- ----------------------------
BEGIN;
INSERT INTO `admin` VALUES (1, 'lgf', 'admin', '21232f297a57a5a743894a0e4a801fc3', NULL, 1, 1, '2020-05-09 15:27:58');
INSERT INTO `admin` VALUES (2, '测试-01', 'test', 'c4ca4238a0b923820dcc509a6f75849b', 5, 1, 0, '2020-05-17 10:29:10');
COMMIT;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of customer
-- ----------------------------
BEGIN;
INSERT INTO `customer` VALUES (1, 'jack_8325', '123456', '2020-05-03 11:45:58');
COMMIT;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '唯一编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单路由名称',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '路径',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'icon',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组件路径',
  `hidden` tinyint(1) DEFAULT '0' COMMENT '是否隐藏: 0-不隐藏 1-隐藏',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '重定向地址',
  `parentId` int DEFAULT NULL COMMENT '父级id',
  `type` int DEFAULT '0' COMMENT '菜单类型: 0-大后台',
  `isApi` tinyint(1) DEFAULT '0' COMMENT 'API接口类型：0-否 1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='菜单表';

-- ----------------------------
-- Records of menu
-- ----------------------------
BEGIN;
INSERT INTO `menu` VALUES (1, '', '系统管理', '/system', NULL, NULL, 0, '/admin', NULL, 0, 0);
INSERT INTO `menu` VALUES (2, 'MenuList', '菜单管理', '/menu', NULL, '/menu/MenuList', 0, NULL, 1, 0, 0);
INSERT INTO `menu` VALUES (3, 'admin', '账号管理', '/admin', NULL, '/admin/AdminList', 0, NULL, 1, 0, 0);
INSERT INTO `menu` VALUES (15, 'admin.*', '全部权限', '/api/admin/**', NULL, NULL, 0, NULL, 3, 0, 1);
INSERT INTO `menu` VALUES (17, 'RoleList', '角色管理', '/role', NULL, '/admin/RoleList', 0, NULL, 1, 0, 0);
INSERT INTO `menu` VALUES (19, 'role.list', '列表', '/api/role/list', NULL, NULL, 0, NULL, 17, 0, 1);
INSERT INTO `menu` VALUES (20, 'menu.list', '列表', '/api/menu/list', NULL, NULL, 0, NULL, 2, 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '唯一编号',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `menuIds` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '拥有的菜单与权限',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (4, '财务', '0,1,3,15', '2020-05-16 22:42:54');
INSERT INTO `role` VALUES (5, '开发', '0,1,2,20,3,15,17,19', '2020-05-16 23:09:30');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
