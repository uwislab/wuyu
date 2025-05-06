/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50734
Source Host           : 127.0.0.1:3306
Source Database       : fiveup

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2022-04-23 23:00:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for demonstrate_basic_class
-- ----------------------------
DROP TABLE IF EXISTS `demonstrate_basic_class`;
CREATE TABLE `demonstrate_basic_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '班级Id',
  `grade` varchar(45) NOT NULL DEFAULT '' COMMENT '班级',
  `class_name` varchar(45) NOT NULL DEFAULT '' COMMENT '班级名',
  `monitor_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '班主任Id',
  `deleted` bigint(20) NOT NULL DEFAULT '0' COMMENT '是否被删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of demonstrate_basic_class
-- ----------------------------
INSERT INTO `demonstrate_basic_class` VALUES ('1', '一年级', '一班', '0', '0');
INSERT INTO `demonstrate_basic_class` VALUES ('2', '一年级', '二班', '0', '0');
INSERT INTO `demonstrate_basic_class` VALUES ('3', '二年级', '一班', '0', '0');
INSERT INTO `demonstrate_basic_class` VALUES ('4', '二年级', '二班', '0', '0');
INSERT INTO `demonstrate_basic_class` VALUES ('5', '三年级', '一班', '0', '0');
INSERT INTO `demonstrate_basic_class` VALUES ('6', '三年级', '二班', '0', '0');
INSERT INTO `demonstrate_basic_class` VALUES ('7', '四年级', '一班', '0', '0');
INSERT INTO `demonstrate_basic_class` VALUES ('8', '四年级', '二班', '0', '0');
INSERT INTO `demonstrate_basic_class` VALUES ('9', '五年级', '一班', '0', '0');
INSERT INTO `demonstrate_basic_class` VALUES ('10', '五年级', '二班', '0', '0');
INSERT INTO `demonstrate_basic_class` VALUES ('11', '六年级', '一班', '0', '0');
INSERT INTO `demonstrate_basic_class` VALUES ('12', '六年级', '二班', '0', '0');

-- ----------------------------
-- Table structure for demonstrate_basic_student
-- ----------------------------
DROP TABLE IF EXISTS `demonstrate_basic_student`;
CREATE TABLE `demonstrate_basic_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `student_num` varchar(127) NOT NULL DEFAULT '' COMMENT '学号',
  `student_name` varchar(127) NOT NULL DEFAULT '' COMMENT '姓名',
  `gender` int(11) NOT NULL DEFAULT '1' COMMENT '性别',
  `class_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '班级id',
  `parent_phone_num` varchar(127) NOT NULL DEFAULT '' COMMENT '家长电话',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否被删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of demonstrate_basic_student
-- ----------------------------
INSERT INTO `demonstrate_basic_student` VALUES ('45', '202010', '李10', '1', '1', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('46', '202011', '李11', '1', '1', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('47', '202012', '李12', '1', '1', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('48', '202013', '李13', '1', '1', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('49', '202020', '李20', '1', '2', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('50', '202021', '李21', '1', '2', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('51', '202022', '李22', '1', '2', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('52', '202023', '李23', '1', '2', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('53', '202030', '李30', '1', '3', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('54', '202031', '李31', '1', '3', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('55', '202032', '李32', '1', '3', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('56', '202033', '李33', '1', '3', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('57', '202040', '李40', '1', '4', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('58', '202041', '李41', '1', '4', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('59', '202042', '李42', '1', '4', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('60', '202043', '李43', '1', '4', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('61', '202050', '李50', '1', '5', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('62', '202051', '李51', '1', '5', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('63', '202052', '李52', '1', '5', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('64', '202053', '李53', '1', '5', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('65', '202060', '李60', '1', '6', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('66', '202061', '李61', '1', '6', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('67', '202062', '李62', '1', '6', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('68', '202063', '李63', '1', '6', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('69', '202070', '李70', '1', '7', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('70', '202071', '李71', '1', '7', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('71', '202072', '李72', '1', '7', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('72', '202073', '李73', '1', '7', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('73', '202080', '李80', '1', '8', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('74', '202081', '李81', '1', '8', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('75', '202082', '李82', '1', '8', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('76', '202083', '李83', '1', '8', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('77', '202090', '李90', '1', '9', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('78', '202091', '李91', '1', '9', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('79', '202092', '李92', '1', '9', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('80', '202093', '李93', '1', '9', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('81', '2020100', '李100', '1', '10', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('82', '2020101', '李101', '1', '10', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('83', '2020102', '李102', '1', '10', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('84', '2020103', '李103', '1', '10', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('85', '2020110', '李110', '1', '11', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('86', '2020111', '李111', '1', '11', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('87', '2020112', '李112', '1', '11', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('88', '2020113', '李113', '1', '11', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('89', '2020120', '李120', '1', '12', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('90', '2020121', '李121', '1', '12', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('91', '2020122', '李122', '1', '12', '', '0');
INSERT INTO `demonstrate_basic_student` VALUES ('92', '2020123', '李123', '1', '12', '', '0');

-- ----------------------------
-- Table structure for demonstrate_st_score
-- ----------------------------
DROP TABLE IF EXISTS `demonstrate_st_score`;
CREATE TABLE `demonstrate_st_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `deyu` varchar(255) DEFAULT NULL,
  `zhiyu` varchar(255) DEFAULT NULL,
  `tiyu` varchar(255) DEFAULT NULL,
  `meiyu` varchar(255) DEFAULT NULL,
  `laoyu` varchar(255) DEFAULT NULL,
  `cla` varchar(255) DEFAULT NULL,
  `riqi` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of demonstrate_st_score
-- ----------------------------
INSERT INTO `demonstrate_st_score` VALUES ('73', '李10', '71', '57', '67', '88', '72', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('74', '李10', '97', '83', '88', '54', '57', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('75', '李10', '79', '82', '98', '51', '94', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('76', '李11', '84', '75', '96', '69', '75', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('77', '李11', '98', '95', '62', '80', '84', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('78', '李11', '85', '88', '50', '85', '59', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('79', '李12', '51', '69', '74', '83', '55', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('80', '李12', '81', '78', '83', '58', '73', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('81', '李12', '72', '66', '78', '53', '87', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('82', '李13', '79', '74', '52', '95', '87', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('83', '李13', '57', '57', '78', '78', '57', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('84', '李13', '70', '57', '67', '59', '92', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('85', '李20', '51', '64', '78', '67', '83', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('86', '李20', '86', '93', '66', '75', '95', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('87', '李20', '71', '67', '58', '51', '95', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('88', '李21', '56', '92', '66', '96', '94', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('89', '李21', '91', '74', '63', '71', '56', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('90', '李21', '66', '77', '53', '78', '70', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('91', '李22', '66', '61', '70', '52', '91', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('92', '李22', '50', '85', '59', '92', '65', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('93', '李22', '80', '80', '60', '51', '51', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('94', '李23', '57', '56', '60', '71', '71', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('95', '李23', '80', '53', '57', '64', '65', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('96', '李23', '96', '91', '94', '98', '99', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('97', '李30', '59', '91', '79', '60', '88', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('98', '李30', '68', '63', '97', '60', '64', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('99', '李30', '66', '80', '55', '54', '98', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('100', '李31', '89', '64', '78', '56', '53', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('101', '李31', '77', '55', '53', '64', '95', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('102', '李31', '70', '68', '68', '69', '52', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('103', '李32', '57', '86', '97', '71', '92', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('104', '李32', '92', '75', '55', '93', '71', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('105', '李32', '57', '94', '53', '95', '85', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('106', '李33', '65', '97', '90', '83', '60', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('107', '李33', '68', '77', '64', '79', '67', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('108', '李33', '93', '61', '85', '86', '65', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('109', '李40', '51', '77', '85', '61', '66', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('110', '李40', '73', '73', '85', '53', '78', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('111', '李40', '60', '76', '97', '88', '53', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('112', '李41', '71', '93', '91', '96', '90', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('113', '李41', '51', '57', '79', '55', '94', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('114', '李41', '82', '72', '98', '75', '70', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('115', '李42', '52', '74', '55', '70', '51', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('116', '李42', '86', '93', '63', '87', '84', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('117', '李42', '78', '90', '72', '75', '53', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('118', '李43', '86', '67', '56', '63', '71', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('119', '李43', '61', '95', '52', '81', '90', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('120', '李43', '75', '90', '79', '53', '93', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('121', '李50', '70', '88', '79', '90', '67', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('122', '李50', '58', '94', '66', '56', '85', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('123', '李50', '90', '86', '90', '53', '67', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('124', '李51', '84', '66', '89', '87', '53', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('125', '李51', '58', '54', '53', '70', '91', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('126', '李51', '88', '78', '84', '85', '91', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('127', '李52', '74', '81', '56', '78', '81', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('128', '李52', '61', '97', '78', '69', '88', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('129', '李52', '59', '96', '92', '70', '58', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('130', '李53', '95', '73', '60', '51', '76', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('131', '李53', '86', '70', '61', '56', '53', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('132', '李53', '63', '69', '68', '72', '87', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('133', '李60', '86', '98', '95', '50', '67', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('134', '李60', '74', '55', '69', '95', '57', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('135', '李60', '66', '95', '51', '76', '85', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('136', '李61', '51', '90', '51', '92', '56', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('137', '李61', '55', '86', '95', '64', '59', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('138', '李61', '56', '57', '92', '69', '78', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('139', '李62', '69', '61', '93', '53', '78', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('140', '李62', '61', '52', '82', '86', '76', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('141', '李62', '84', '91', '91', '66', '59', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('142', '李63', '62', '85', '82', '56', '65', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('143', '李63', '76', '56', '77', '69', '53', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('144', '李63', '81', '83', '86', '88', '91', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('145', '李70', '70', '84', '64', '86', '98', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('146', '李70', '53', '51', '88', '76', '52', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('147', '李70', '52', '70', '61', '79', '65', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('148', '李71', '78', '68', '81', '95', '99', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('149', '李71', '87', '55', '63', '56', '70', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('150', '李71', '89', '81', '93', '53', '64', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('151', '李72', '98', '55', '74', '88', '86', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('152', '李72', '65', '89', '92', '58', '50', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('153', '李72', '71', '54', '90', '57', '60', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('154', '李73', '94', '55', '96', '56', '74', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('155', '李73', '53', '82', '75', '92', '99', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('156', '李73', '84', '65', '91', '51', '66', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('157', '李80', '81', '94', '66', '69', '78', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('158', '李80', '72', '93', '60', '80', '83', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('159', '李80', '58', '77', '68', '71', '85', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('160', '李81', '84', '95', '89', '60', '88', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('161', '李81', '98', '95', '90', '78', '75', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('162', '李81', '91', '64', '73', '62', '78', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('163', '李82', '84', '98', '86', '68', '51', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('164', '李82', '53', '98', '65', '58', '55', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('165', '李82', '93', '89', '76', '66', '87', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('166', '李83', '72', '68', '57', '69', '67', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('167', '李83', '59', '84', '95', '78', '53', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('168', '李83', '62', '89', '68', '58', '74', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('169', '李90', '82', '73', '86', '76', '97', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('170', '李90', '68', '82', '70', '99', '58', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('171', '李90', '70', '94', '95', '67', '68', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('172', '李91', '68', '82', '66', '77', '54', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('173', '李91', '64', '88', '85', '59', '54', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('174', '李91', '77', '95', '54', '64', '87', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('175', '李92', '99', '68', '73', '73', '68', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('176', '李92', '91', '50', '59', '80', '78', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('177', '李92', '82', '73', '92', '95', '73', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('178', '李93', '94', '82', '63', '93', '68', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('179', '李93', '98', '93', '87', '63', '89', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('180', '李93', '54', '99', '80', '55', '86', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('181', '李100', '82', '81', '88', '69', '53', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('182', '李100', '72', '76', '68', '51', '95', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('183', '李100', '85', '83', '61', '50', '59', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('184', '李101', '97', '62', '58', '59', '51', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('185', '李101', '88', '60', '83', '60', '89', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('186', '李101', '80', '71', '87', '91', '86', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('187', '李102', '60', '65', '86', '78', '69', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('188', '李102', '61', '59', '74', '63', '97', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('189', '李102', '87', '73', '83', '97', '98', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('190', '李103', '86', '96', '84', '68', '81', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('191', '李103', '53', '59', '52', '96', '89', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('192', '李103', '85', '85', '51', '96', '79', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('193', '李110', '53', '85', '62', '65', '99', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('194', '李110', '63', '75', '88', '81', '97', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('195', '李110', '74', '82', '57', '82', '63', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('196', '李111', '70', '67', '88', '74', '91', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('197', '李111', '79', '94', '53', '94', '61', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('198', '李111', '88', '80', '75', '95', '59', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('199', '李112', '55', '75', '90', '83', '51', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('200', '李112', '83', '89', '91', '61', '74', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('201', '李112', '82', '76', '69', '59', '97', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('202', '李113', '81', '63', '63', '52', '98', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('203', '李113', '74', '63', '95', '82', '76', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('204', '李113', '83', '67', '84', '67', '81', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('205', '李120', '99', '62', '89', '94', '50', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('206', '李120', '54', '74', '60', '63', '87', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('207', '李120', '61', '58', '81', '75', '87', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('208', '李121', '93', '64', '62', '69', '90', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('209', '李121', '71', '87', '98', '56', '69', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('210', '李121', '94', '91', '70', '62', '82', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('211', '李122', '81', '90', '54', '82', '67', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('212', '李122', '68', '76', '78', '90', '66', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('213', '李122', '78', '66', '92', '95', '75', null, '2022');
INSERT INTO `demonstrate_st_score` VALUES ('214', '李123', '78', '91', '82', '95', '75', null, '2020');
INSERT INTO `demonstrate_st_score` VALUES ('215', '李123', '97', '86', '97', '86', '85', null, '2021');
INSERT INTO `demonstrate_st_score` VALUES ('216', '李123', '51', '62', '76', '84', '94', null, '2022');
