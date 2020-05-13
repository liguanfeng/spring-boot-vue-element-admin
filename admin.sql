/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : testdb

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 13/05/2020 17:21:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
                          `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '唯一编号',
                          `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '登录名称',
                          `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '登录密码',
                          `createTime` datetime(0) DEFAULT NULL COMMENT '创建时间',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '平台管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '2020-05-09 15:27:58');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
                         `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '唯一编号',
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单路由名称',
                         `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单名称',
                         `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '路径',
                         `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'icon',
                         `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组件路径',
                         `hidden` tinyint(1) DEFAULT 0 COMMENT '是否隐藏: 0-不隐藏 1-隐藏',
                         `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '重定向地址',
                         `parentId` int(0) DEFAULT NULL COMMENT '父级id',
                         `type` int(0) DEFAULT 0 COMMENT '菜单类型: 0-大后台',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 'system', '系统管理', '/system', NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `menu` VALUES (2, 'menu', '菜单管理', '/menu', NULL, '/menu/list', 0, NULL, 1, 0);
INSERT INTO `menu` VALUES (3, 'admin', '账号管理', '/admin', NULL, '/admin/list', 0, NULL, 1, 0);
INSERT INTO `menu` VALUES (4, 'yunyin', '运营管理', '/test', NULL, NULL, 0, NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
