/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : caipiao

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2016-09-15 22:38:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for zdylb
-- ----------------------------
DROP TABLE IF EXISTS `zdylb`;
CREATE TABLE `zdylb` (
  `id` int(11) NOT NULL,
  `sj` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  `hszszdyl` int(2) default '0' COMMENT '后三组三最大遗漏数量',
  `zszszdyl` int(2) default '0' COMMENT '中三组三最大遗漏数量',
  `qszszdyl` int(2) default '0' COMMENT '前三组三最大遗漏数量',
  `hszlzdyl` int(2) default '0' COMMENT '后三组六最大遗漏',
  `qszlzdyl` int(2) default '0' COMMENT '前三组六最大遗漏',
  `zszlzdyl` int(2) default '0' COMMENT '中三组三最大遗漏数量',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='最大遗漏表';

-- ----------------------------
-- Records of zdylb
-- ----------------------------
