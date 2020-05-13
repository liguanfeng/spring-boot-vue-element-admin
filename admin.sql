CREATE TABLE `admin` (
`id` INT NOT NULL AUTO_INCREMENT COMMENT '唯一编号',
`name` VARCHAR ( 32 ) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '登录名称',
`password` VARCHAR ( 64 ) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '登录密码',
`createTime` datetime DEFAULT NULL COMMENT '创建时间',
PRIMARY KEY ( `id` )
) ENGINE = INNODB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_bin;

CREATE TABLE `menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '唯一编号',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '路径',
  `icon` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'icon',
  `parentId` int DEFAULT NULL COMMENT '父级id',
  `component` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组件路径',
  `hidden` tinyint(1) DEFAULT '0' COMMENT '是否隐藏: 0-不隐藏 1-隐藏',
  `redirect` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '重定向地址',
  `type` int DEFAULT '0' COMMENT '菜单类型: 0-大后台',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='菜单表';

INSERT INTO `admin`(`id`, `name`, `password`, `createTime`) VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '2020-05-09 15:27:58');

ALTER TABLE `menu`
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单路由名称' AFTER `id`,
    ADD COLUMN `title` varchar(32) COMMENT '菜单名称' AFTER `name`,
    MODIFY COLUMN `parentId` int(0) DEFAULT NULL COMMENT '父级id' AFTER `redirect`;

