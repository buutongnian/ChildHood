/*
Navicat MySQL Data Transfer

Source Server         : server
Source Server Version : 50713
Source Host           : 123.57.52.135:3306
Source Database       : childhood

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2016-10-26 14:49:13
*/

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE `childhood` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE childhood;

-- ----------------------------
-- Table structure for t_achvmt_event_rules
-- ----------------------------
DROP TABLE IF EXISTS `t_achvmt_event_rules`;
CREATE TABLE `t_achvmt_event_rules` (
  `RULE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RULE_NAME` varchar(50) NOT NULL,
  `RULE_SYNOPSIS` varchar(200) NOT NULL,
  `RULE_MODULE` int(11) NOT NULL,
  `RULE_MODULE_NAME` varchar(50) NOT NULL,
  `RULE_PROCEDURE` varchar(50) NOT NULL,
  `PROCEDURE_HASARG` enum('Y','N') NOT NULL DEFAULT 'N',
  `NOTE` varchar(200) DEFAULT NULL,
  `ENABLE` enum('Y','N') NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`RULE_ID`),
  KEY `FK_T_ACHVMT_EVENT_RULES_T_ACHVMT_MODULE_MODULE_ID` (`RULE_MODULE`),
  CONSTRAINT `FK_T_ACHVMT_EVENT_RULES_T_ACHVMT_MODULE_MODULE_ID` FOREIGN KEY (`RULE_MODULE`) REFERENCES `t_achvmt_module` (`MODULE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_achvmt_event_rules
-- ----------------------------
INSERT INTO `t_achvmt_event_rules` VALUES ('1', 'welcome_to_tongnian', '用户注册后，在足迹中加入“加入童年的世界”事件', '1', 'register', 'PRC_RULE_REGISTER_WELCOME', 'Y', null, 'Y');
INSERT INTO `t_achvmt_event_rules` VALUES ('2', 'auto_insert_user_medal_list', '用户注册后，自动在用户勋章表中插入用户勋章记录，默认所有勋章状态为N', '1', 'register', 'PRC_RULE_AUTO_INSERT_USER_MEDAL', 'Y', null, 'Y');
INSERT INTO `t_achvmt_event_rules` VALUES ('3', 'everydat_first_login_add_achievement_point', '用户每天第一次登陆增加成就点', '4', 'login', 'PRC_RULE_EVERYDAY_FIRST_LOGIN', 'Y', null, 'Y');
INSERT INTO `t_achvmt_event_rules` VALUES ('4', 'login_days_event', '用户累计登录达一定天数后触发事件', '4', 'login', 'PRC_RULE_LOGIN_DAYS_EVENT', 'Y', null, 'Y');
INSERT INTO `t_achvmt_event_rules` VALUES ('5', 'like_count_event', '用户被点赞达一定数量后触发事件', '2', 'rank', 'PRC_RULE_LIKE_COUNT', 'Y', null, 'Y');
INSERT INTO `t_achvmt_event_rules` VALUES ('6', 'game_count_event', '用户完成一定数量游戏后触发事件', '3', 'onekey', 'PRC_RULE_GAME_COUNT_EVENT', 'Y', null, 'Y');
INSERT INTO `t_achvmt_event_rules` VALUES ('7', 'found_game_count_event', '用户一键呼唤并完成一定数量游戏后触发事件', '3', 'onekey', 'PRC_RULE_FOUND_GAME_COUNT_EVENT', 'Y', null, 'Y');
INSERT INTO `t_achvmt_event_rules` VALUES ('8', 'invite_user_count_event', '用户邀请玩家达一定数量获得勋章触发事件', '3', 'onekey', 'PRC_RULE_INVITE_USER_EVENT', 'Y', null, 'Y');
INSERT INTO `t_achvmt_event_rules` VALUES ('9', 'kind_game_count_event', '用户玩过游戏种数达到一定数量获得勋章触发事件', '3', 'onekey', 'PRC_RULE_KIND_GAME_COUNT_EVENT', 'Y', null, 'Y');
INSERT INTO `t_achvmt_event_rules` VALUES ('10', 'achvmt_top_event', '用户成就点满后获得勋章触发事件', '0', '未设置', 'PRC_RULE_ACHVMT_TOP_EVENT', 'N', '数据库中触发器触发事件，不经调度器调度', 'N');
INSERT INTO `t_achvmt_event_rules` VALUES ('11', 'continue_game_day', '用户连续玩游戏一定天数后触发事件', '3', 'onekey', 'PRC_RULE_CONTINUE_GAME_DAY_EVENT', 'Y', null, 'Y');
INSERT INTO `t_achvmt_event_rules` VALUES ('12', 'continue_like_day_event', '用户连续获得点赞达到一定天数后触发事件', '2', 'rank', 'PRC_RULE_CONTINUE_LIKE_DAY_EVENT', 'Y', null, 'Y');
INSERT INTO `t_achvmt_event_rules` VALUES ('13', 'first_feedback_event', '用户第一次反馈时触发事件', '9', 'feedback', 'PRC_RULE_FIRST_FEEDBACK_EVENT', 'Y', null, 'Y');

-- ----------------------------
-- Table structure for t_achvmt_event_template
-- ----------------------------
DROP TABLE IF EXISTS `t_achvmt_event_template`;
CREATE TABLE `t_achvmt_event_template` (
  `TEMPLATE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TEMPLATE_CODE` char(20) NOT NULL,
  `TEMPLATE_NAME` varchar(50) NOT NULL,
  `TEMPLATE_STR` varchar(200) NOT NULL,
  `TEMPLATE_PARAMS` varchar(200) NOT NULL,
  `TEMPLATE_MODULE` int(11) NOT NULL,
  `TEMPLATE_MODULE_NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`TEMPLATE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_achvmt_event_template
-- ----------------------------
INSERT INTO `t_achvmt_event_template` VALUES ('1', 'REGISTER_EVENT', '用户注册成功时事件模板', '进入童年的世界', '{}', '1', 'register');
INSERT INTO `t_achvmt_event_template` VALUES ('2', 'EVERYDAY_KPI_FULL', '用户当日完成游戏、获得点赞模板', '完成${gameCount}次游戏，收获${likeCount}个赞，获得${achievementPoint}成就点', '{\"${gameCount}\":\"gameCountPlaceHolder\",\"${achievementPoint}\":\"achievementPointPlaceHolder\",\"${likeCount}\":\"likeCountPlaceHolder\"}', '5', 'everyday');
INSERT INTO `t_achvmt_event_template` VALUES ('3', 'EVERYDAY_KPI_GAME', '用户当日完成游戏模板', '完成${gameCount}次游戏，获得${achievementPoint}成就点', '{\"${gameCount}\":\"gameCountPlaceHolder\",\"${achievementPoint}\":\"achievementPointPlaceHolder\"}', '5', 'everyday');
INSERT INTO `t_achvmt_event_template` VALUES ('4', 'EVERYDAY_KPI_LIKE', '用户当日获得点赞模板', '收获${likeCount}个赞，获得${achievementPoint}成就点', '{\"${likeCount}\":\"likeCountPlaceHolder\",\"${likeCount}\":\"likeCountPlaceHolder\"}', '5', 'everyday');
INSERT INTO `t_achvmt_event_template` VALUES ('5', 'LEVEL_UP', '用户升级事件模板', '升到${level}级，获得${title}称号', '{\"${title}\":\"titlePlaceHolder\",\"${level}\":\"levelPlaceHolder\"}', '6', 'level');
INSERT INTO `t_achvmt_event_template` VALUES ('6', 'MEDAL_COUNT', '用户获得指定数量勋章事件模板', '获得${medal}个勋章', '{\"${medal}\":\"medalPlaceHolder\"}', '7', 'medal');
INSERT INTO `t_achvmt_event_template` VALUES ('7', 'LOGIN_TIMES', '用户累计登录达到一定天数触发事件模板', '累计登录${count}天，达成成就：${achievement}', '{\"${count}\":\"countPlaceHolder\",\"${achievement}\":\"achievementPlaceHolder\"}', '4', 'login');
INSERT INTO `t_achvmt_event_template` VALUES ('8', 'GAME_TIMES_EVENT', '用户累计完成游戏次数达到一定数量触发事件模板', '完成游戏次数达到${count}次，达成成就：${achievement}', '{\"${count}\":\"countPlaceHolder\",\"${achievement}\":\"achievementPlaceHolder\"}', '3', 'onekey');
INSERT INTO `t_achvmt_event_template` VALUES ('9', 'ONEKEY_TIMES_EVENT', '一键呼唤并完成游戏数达到一定数量触发事件模板', '一键呼唤${count}次，达成成就：${achievement}', '{\"${count}\":\"countPlaceHolder\",\"${achievement}\":\"achievementPlaceHolder\"}', '3', 'onekey');
INSERT INTO `t_achvmt_event_template` VALUES ('10', 'FIRST_FEEDBACK', '第一次反馈获得成就事件模板', '第一次给童年提出意见，达成成就：火眼金睛', '{}', '9', 'feedback');
INSERT INTO `t_achvmt_event_template` VALUES ('11', 'LIKE_COUNT_EVENT', '累计收获指定数量点赞数事件模板', '累计收获${likeCount}个赞，达成成就：${achievement}', '{\"${likeCount}\":\"likeCountPlaceHolder\",\"${achievement}\":\"achievementPlaceHolder\"}', '2', 'rank');
INSERT INTO `t_achvmt_event_template` VALUES ('12', 'GET_MEDAL_EVENT', '获得勋章后添加事件模板', '获得${medalName}勋章', '{\"${medalName}\":\"medalNamePlaceHolder\"}', '7', 'medal');

-- ----------------------------
-- Table structure for t_achvmt_medal
-- ----------------------------
DROP TABLE IF EXISTS `t_achvmt_medal`;
CREATE TABLE `t_achvmt_medal` (
  `MEDAL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MEDAL_NAME` varchar(50) NOT NULL,
  `MEDAL_DESCRIBE` varchar(200) DEFAULT NULL,
  `ACHIEVEMENT_POINT` int(11) DEFAULT '0',
  PRIMARY KEY (`MEDAL_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_achvmt_medal
-- ----------------------------
INSERT INTO `t_achvmt_medal` VALUES ('1', '一派掌门', '累计登录300天', '0');
INSERT INTO `t_achvmt_medal` VALUES ('2', '胜友如云', '一键呼唤小伙伴加入游戏累计150人', '0');
INSERT INTO `t_achvmt_medal` VALUES ('3', '游戏新星', '第一次完成游戏', '0');
INSERT INTO `t_achvmt_medal` VALUES ('4', '游戏大师', '累计完成150次游戏', '0');
INSERT INTO `t_achvmt_medal` VALUES ('5', '大收藏家', '累计完成十种不同游戏', '0');
INSERT INTO `t_achvmt_medal` VALUES ('6', '钻石玩家', '连续10天进行游戏', '0');
INSERT INTO `t_achvmt_medal` VALUES ('7', '成就之王', '成就点数达到5000点', '0');
INSERT INTO `t_achvmt_medal` VALUES ('8', '人气萌芽', '第一次被点赞', '0');
INSERT INTO `t_achvmt_medal` VALUES ('9', '人气超凡', '连续10天被点赞', '0');
INSERT INTO `t_achvmt_medal` VALUES ('10', '名扬四海', '点赞数达到1000', '0');

-- ----------------------------
-- Table structure for t_achvmt_module
-- ----------------------------
DROP TABLE IF EXISTS `t_achvmt_module`;
CREATE TABLE `t_achvmt_module` (
  `MODULE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MODULE_NAME` varchar(50) NOT NULL,
  `MODULE_SYNOPSIS` varchar(200) NOT NULL,
  PRIMARY KEY (`MODULE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_achvmt_module
-- ----------------------------
INSERT INTO `t_achvmt_module` VALUES ('0', '未设置', '未设置所属模块');
INSERT INTO `t_achvmt_module` VALUES ('1', 'register', '用户注册模块，对应用户注册后所触发事件');
INSERT INTO `t_achvmt_module` VALUES ('2', 'rank', '点赞模块，对应用户被点赞后系列事件');
INSERT INTO `t_achvmt_module` VALUES ('3', 'onekey', '一键呼唤模块，对应用户召集游戏并完成后所触发事件');
INSERT INTO `t_achvmt_module` VALUES ('4', 'login', '登录模块，对应用户登录后触发的事件');
INSERT INTO `t_achvmt_module` VALUES ('5', 'everyday', '日常事件模块');
INSERT INTO `t_achvmt_module` VALUES ('6', 'level', '等级相关事件');
INSERT INTO `t_achvmt_module` VALUES ('7', 'medal', '勋章相关事件');
INSERT INTO `t_achvmt_module` VALUES ('8', 'like', '点赞相关事件');
INSERT INTO `t_achvmt_module` VALUES ('9', 'feedback', '反馈相关事件');

-- ----------------------------
-- Table structure for t_achvmt_user_event_list
-- ----------------------------
DROP TABLE IF EXISTS `t_achvmt_user_event_list`;
CREATE TABLE `t_achvmt_user_event_list` (
  `LIST_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(50) NOT NULL,
  `EVENT_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `EVENT_TEMPLATE` int(11) NOT NULL,
  `EVENT_TEMPLATE_NAME` varchar(200) NOT NULL,
  `EVENT_PARAMS` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`LIST_ID`),
  KEY `FK_T_ACHVMT_USER_EVENT_LIST_T_ACHVMT_EVENT_TEMPLATE_ID` (`EVENT_TEMPLATE`),
  CONSTRAINT `FK_T_ACHVMT_USER_EVENT_LIST_T_ACHVMT_EVENT_TEMPLATE_ID` FOREIGN KEY (`EVENT_TEMPLATE`) REFERENCES `t_achvmt_event_template` (`TEMPLATE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_achvmt_user_event_list
-- ----------------------------
INSERT INTO `t_achvmt_user_event_list` VALUES ('1', '13269128687', '2016-09-29 21:57:04', '1', 'USER_REGISTERED', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('2', '13269128687', '2016-09-28 21:57:15', '1', 'USER_REGISTERED', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('3', '13269128687', '2016-09-27 21:57:26', '1', 'USER_REGISTERED', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('4', '13269128687', '2016-09-26 21:57:33', '1', 'USER_REGISTERED', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('5', '13269128687', '2016-09-25 21:57:41', '1', 'USER_REGISTERED', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('6', '13269128687', '2016-09-16 21:57:49', '1', 'USER_REGISTERED', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('7', '13269128687', '2016-09-30 13:53:09', '1', 'USER_REGISTERED', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('13', '13156326887', '2016-10-02 23:13:37', '1', 'USER_REGISTERED', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('14', '13156326886', '2016-10-02 23:15:46', '1', 'USER_REGISTERED', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('15', '13269128687', '2016-10-08 15:57:23', '1', 'USER_REGISTERED', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('16', '13240150408', '2016-10-08 16:22:20', '1', 'USER_REGISTERED', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('21', '13269158110', '2016-10-12 15:11:56', '1', '用户注册成功时事件模板', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('22', '13611108060', '2016-10-12 22:19:07', '1', '用户注册成功时事件模板', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('23', '13611108060', '2016-10-12 22:26:08', '7', '用户累计登录达到一定天数触发事件模板', '{\"${count}\":\"1\",\"${achievement}\":\"成长新星\"}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('24', '13269128687', '2016-10-12 23:41:10', '7', '用户累计登录达到一定天数触发事件模板', '{\"${count}\":\"1\",\"${achievement}\":\"成长新星\"}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('26', '13269158110', '2016-10-13 22:42:07', '7', '用户累计登录达到一定天数触发事件模板', '{\"${count}\":\"1\",\"${achievement}\":\"成长新星\"}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('27', '13269158110', '2016-10-13 23:07:11', '12', '获得勋章后添加事件模板', '{\"${medalName}\":\"人气萌芽\"}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('28', '13611108060', '2016-10-13 23:07:22', '12', '获得勋章后添加事件模板', '{\"${medalName}\":\"人气萌芽\"}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('31', '13269128687', '2016-10-14 14:31:15', '11', '累计收获指定数量点赞数事件模板', '{\"${likeCount}\":\"10\",\"${achievement}\":\"赫赫有名\"}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('33', '18888888888', '2016-10-14 19:54:31', '1', '用户注册成功时事件模板', '{}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('34', '18888888888', '2016-10-14 19:58:35', '12', '获得勋章后添加事件模板', '{\"${medalName}\":\"人气萌芽\"}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('35', '18888888888', '2016-10-14 20:00:25', '7', '用户累计登录达到一定天数触发事件模板', '{\"${count}\":\"1\",\"${achievement}\":\"成长新星\"}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('36', '13269158110', '2016-10-15 11:42:50', '11', '累计收获指定数量点赞数事件模板', '{\"${likeCount}\":\"10\",\"${achievement}\":\"赫赫有名\"}');
INSERT INTO `t_achvmt_user_event_list` VALUES ('37', '13611108060', '2016-10-15 19:51:52', '11', '累计收获指定数量点赞数事件模板', '{\"${likeCount}\":\"10\",\"${achievement}\":\"赫赫有名\"}');

-- ----------------------------
-- Table structure for t_achvmt_user_medal_list
-- ----------------------------
DROP TABLE IF EXISTS `t_achvmt_user_medal_list`;
CREATE TABLE `t_achvmt_user_medal_list` (
  `USER_NAME` varchar(50) NOT NULL,
  `MEDAL_ID` int(11) NOT NULL,
  `GET_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ENABLE` enum('Y','N') NOT NULL DEFAULT 'N',
  PRIMARY KEY (`USER_NAME`,`MEDAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_achvmt_user_medal_list
-- ----------------------------
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13240150408', '1', '2016-10-12 20:27:27', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13240150408', '2', '2016-10-13 20:35:23', 'Y');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13240150408', '3', '2016-10-12 20:28:07', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13240150408', '4', '2016-10-13 20:35:25', 'Y');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13240150408', '5', '2016-10-12 20:34:25', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13240150408', '6', '2016-10-13 20:35:26', 'Y');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13240150408', '7', '2016-10-12 20:37:04', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13240150408', '8', '2016-10-13 20:35:30', 'Y');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13240150408', '9', '2016-10-12 20:37:42', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13240150408', '10', '2016-10-13 20:35:32', 'Y');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269128687', '1', '2016-10-14 19:41:25', 'Y');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269128687', '2', '2016-10-14 19:41:27', 'Y');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269128687', '3', '2016-10-12 20:28:07', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269128687', '4', '2016-10-14 19:41:29', 'Y');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269128687', '5', '2016-10-12 20:34:25', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269128687', '6', '2016-10-14 19:41:31', 'Y');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269128687', '7', '2016-10-12 20:37:04', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269128687', '8', '2016-10-14 19:41:33', 'Y');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269128687', '9', '2016-10-12 20:37:42', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269128687', '10', '2016-10-12 20:38:57', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269158110', '1', '2016-10-12 20:27:27', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269158110', '2', '2016-10-12 20:27:59', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269158110', '3', '2016-10-12 20:28:07', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269158110', '4', '2016-10-12 20:28:14', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269158110', '5', '2016-10-12 20:34:25', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269158110', '6', '2016-10-12 20:34:43', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269158110', '7', '2016-10-12 20:37:04', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269158110', '8', '2016-10-13 23:07:11', 'Y');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269158110', '9', '2016-10-12 20:37:42', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13269158110', '10', '2016-10-12 20:38:57', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13611108060', '1', '2016-10-12 20:27:27', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13611108060', '2', '2016-10-12 20:27:59', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13611108060', '3', '2016-10-12 20:28:07', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13611108060', '4', '2016-10-12 20:28:14', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13611108060', '5', '2016-10-12 20:34:25', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13611108060', '6', '2016-10-12 20:34:43', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13611108060', '7', '2016-10-12 20:37:04', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13611108060', '8', '2016-10-13 23:07:22', 'Y');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13611108060', '9', '2016-10-12 20:37:42', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('13611108060', '10', '2016-10-12 20:38:57', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('18888888888', '1', '2016-10-14 19:54:31', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('18888888888', '2', '2016-10-14 19:54:31', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('18888888888', '3', '2016-10-14 19:54:31', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('18888888888', '4', '2016-10-14 19:54:31', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('18888888888', '5', '2016-10-14 19:54:31', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('18888888888', '6', '2016-10-14 19:54:31', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('18888888888', '7', '2016-10-14 19:54:31', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('18888888888', '8', '2016-10-14 19:58:35', 'Y');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('18888888888', '9', '2016-10-14 19:54:31', 'N');
INSERT INTO `t_achvmt_user_medal_list` VALUES ('18888888888', '10', '2016-10-14 19:54:31', 'N');

-- ----------------------------
-- Table structure for t_find_reg
-- ----------------------------
DROP TABLE IF EXISTS `t_find_reg`;
CREATE TABLE `t_find_reg` (
  `USER_NAME` varchar(50) NOT NULL,
  `AGE` int(11) DEFAULT NULL,
  `SEX` enum('m','f') DEFAULT NULL,
  `YOUNG_LATITUDE` double(8,6) DEFAULT NULL,
  `YOUNG_LONGITUDE` double(9,6) DEFAULT NULL,
  `YOUNG_NICKNAME` varchar(100) DEFAULT NULL,
  `YOUNG_ROAD` varchar(200) DEFAULT NULL,
  `MESSAGE` varchar(500) DEFAULT NULL,
  `ENABLE` enum('Y','N') DEFAULT NULL,
  `REG_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`USER_NAME`),
  KEY `FK_USER_NAME_FIND_REG` (`USER_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_find_reg
-- ----------------------------
INSERT INTO `t_find_reg` VALUES ('13240150408', '1995', 'm', '39.928818', '116.278204', '涛涛', '老地方餐馆', null, 'Y', '2016-10-14 23:30:52');
INSERT INTO `t_find_reg` VALUES ('13269128687', '1995', 'm', '39.995830', '116.417155', '狗蛋', '人民路', null, 'Y', '2016-09-26 20:43:11');
INSERT INTO `t_find_reg` VALUES ('13611108060', '1994', 'm', '1.000000', '1.000000', '狗蛋', '人民路', null, 'Y', '2016-09-26 20:48:28');

-- ----------------------------
-- Table structure for t_find_result
-- ----------------------------
DROP TABLE IF EXISTS `t_find_result`;
CREATE TABLE `t_find_result` (
  `USER_NAME` varchar(50) NOT NULL,
  `MATCH_USER` varchar(50) NOT NULL,
  `ENABLE` enum('Y','N') DEFAULT 'Y',
  PRIMARY KEY (`USER_NAME`,`MATCH_USER`),
  KEY `FK_USER_NAME_RESULT` (`USER_NAME`),
  KEY `FK_MATCH_USER_RESULT` (`MATCH_USER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_find_result
-- ----------------------------
INSERT INTO `t_find_result` VALUES ('13240150408', '13269128687', 'Y');
INSERT INTO `t_find_result` VALUES ('13269128687', '13240150408', 'Y');
INSERT INTO `t_find_result` VALUES ('13269128687', '13611108060', 'Y');
INSERT INTO `t_find_result` VALUES ('13611108060', '13269128687', 'Y');

-- ----------------------------
-- Table structure for t_gamerule_content
-- ----------------------------
DROP TABLE IF EXISTS `t_gamerule_content`;
CREATE TABLE `t_gamerule_content` (
  `GAME_CODE` int(11) NOT NULL,
  `GAME_CONTENT` text,
  `ENABLE` enum('Y','N') DEFAULT NULL,
  PRIMARY KEY (`GAME_CODE`),
  CONSTRAINT `FK_GAME_CODE` FOREIGN KEY (`GAME_CODE`) REFERENCES `t_gamerule_head` (`GAME_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_gamerule_content
-- ----------------------------
INSERT INTO `t_gamerule_content` VALUES ('1', 'http://mp.weixin.qq.com/s?__biz=MzIyNTU3MjY5Mg==&mid=2247483687&idx=1&sn=fdc848d86319480fd8d91fd43f1fb1d1&scene=4#wechat_redirect', 'Y');
INSERT INTO `t_gamerule_content` VALUES ('2', 'http://mp.weixin.qq.com/s?__biz=MzIyNTU3MjY5Mg==&mid=2247483687&idx=2&sn=cb7055ec00605a1075b7f2dce06e8cf6&scene=4#wechat_redirect', 'Y');
INSERT INTO `t_gamerule_content` VALUES ('3', 'http://mp.weixin.qq.com/s?__biz=MzIyNTU3MjY5Mg==&mid=2247483687&idx=3&sn=833513fd66104948dbbc9120ecea4f39&scene=4#wechat_redirect', 'Y');
INSERT INTO `t_gamerule_content` VALUES ('4', 'http://mp.weixin.qq.com/s?__biz=MzIyNTU3MjY5Mg==&mid=2247483687&idx=4&sn=be0d16517a4493260933e69be6638bcf&scene=4#wechat_redirect', 'Y');
INSERT INTO `t_gamerule_content` VALUES ('5', 'http://mp.weixin.qq.com/s?__biz=MzIyNTU3MjY5Mg==&mid=2247483687&idx=5&sn=aec041632c6af3ab9767e1c1812e5f07&scene=4#wechat_redirect', 'Y');

-- ----------------------------
-- Table structure for t_gamerule_head
-- ----------------------------
DROP TABLE IF EXISTS `t_gamerule_head`;
CREATE TABLE `t_gamerule_head` (
  `GAME_CODE` int(11) NOT NULL,
  `GAME_TITLE` varchar(100) NOT NULL,
  `GAME_ICON` mediumtext,
  `GAME_IMAGE` varchar(100) DEFAULT NULL,
  `GAME_AREA` int(11) DEFAULT NULL,
  `GAME_TYPE` int(11) DEFAULT NULL,
  `GAME_SYNOPSIS` varchar(500) DEFAULT NULL,
  `AGE_CODE` int(11) DEFAULT NULL,
  `MEMNUM_CODE` int(11) DEFAULT NULL,
  `RECOMMEND_COUNT` int(11) DEFAULT NULL,
  `LEAST_COUNT` int(11) DEFAULT NULL,
  `TOP_COUNT` int(11) DEFAULT NULL,
  `GAME_CONTRIBUTOR` varchar(50) DEFAULT NULL,
  `ADD_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `GAME_SCORE` double(3,2) DEFAULT '5.00',
  `WATCH_TIMES` int(11) NOT NULL DEFAULT '0',
  `ENABLE` enum('Y','N') DEFAULT NULL,
  PRIMARY KEY (`GAME_CODE`),
  KEY `FK_GAME_AREA` (`GAME_AREA`),
  KEY `FK_GAME_TYPE` (`GAME_TYPE`),
  KEY `FK_GAME_FIT_AGE` (`AGE_CODE`),
  KEY `FK_GAME_FIT_MEMNUM` (`MEMNUM_CODE`),
  KEY `FK_GAME_GAME_CONTRIBUTOR` (`GAME_CONTRIBUTOR`),
  CONSTRAINT `FK_GAME_AREA` FOREIGN KEY (`GAME_AREA`) REFERENCES `t_pub_area` (`AREA_CODE`),
  CONSTRAINT `FK_GAME_FIT_AGE` FOREIGN KEY (`AGE_CODE`) REFERENCES `t_pub_game_age` (`AGE_CODE`),
  CONSTRAINT `FK_GAME_FIT_MEMNUM` FOREIGN KEY (`MEMNUM_CODE`) REFERENCES `t_pub_game_memnum` (`MEMNUM_CODE`),
  CONSTRAINT `FK_GAME_TYPE` FOREIGN KEY (`GAME_TYPE`) REFERENCES `t_pub_game_type` (`TYPE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_gamerule_head
-- ----------------------------
INSERT INTO `t_gamerule_head` VALUES ('1', '丢沙包', 'http://123.57.52.135:8080/ChildHood/gameicon/dsb.png', 'http://123.57.52.135:8080/ChildHood/pics/rule1.jpg', '1', '1', '丢沙包：玩家在规定场地内，两边的玩家用沙包投掷站在中间的人，中间的人若被沙包打中则下场，只要用手接住“打手”们扔过来的沙包就能多一条命。', '1', '1', '6', '2', '12', null, '2016-06-28 19:18:59', '4.81', '22', 'Y');
INSERT INTO `t_gamerule_head` VALUES ('2', '跳皮筋', 'http://123.57.52.135:8080/ChildHood/gameicon/tpj.png', 'http://123.57.52.135:8080/ChildHood/pics/rule2.jpg', '1', '1', '跳皮筋：也叫跳橡皮筋、跳橡皮绳、跳猴皮筋，皮筋是用橡胶制成的有弹性的细绳，长3米左右，皮筋被牵直固定之后，即可来回踏跳。可三人至五人一起玩，亦可分两组比赛，边跳边唱非常有趣。', '2', '1', '6', '2', '12', null, '2016-06-28 20:07:37', '5.00', '3', 'Y');
INSERT INTO `t_gamerule_head` VALUES ('3', '推铁环', 'http://123.57.52.135:8080/ChildHood/gameicon/tth.png', 'http://123.57.52.135:8080/ChildHood/pics/rule4.jpg', '1', '1', '推铁环：孩子们手捏顶头是V字形的铁棍或铁丝，推一个直径66厘米左右的黑铁环向前跑，发出哗啷哗啷的声音。', '2', '1', '6', '2', '12', null, '2016-09-18 07:11:58', '5.00', '3', 'Y');
INSERT INTO `t_gamerule_head` VALUES ('4', '打弹珠', 'http://123.57.52.135:8080/ChildHood/gameicon/ddz.png', 'http://123.57.52.135:8080/ChildHood/pics/rule3.jpg', '1', '1', '打弹珠：又称“打玻璃珠、弹玻璃球、弹球儿、打弹子、弹溜溜”。即玩的人各出数枚玻璃珠，输者将丧失对玻璃珠的所有权。', '2', '1', '6', '2', '12', null, '2016-06-28 20:08:24', '5.00', '0', 'Y');
INSERT INTO `t_gamerule_head` VALUES ('5', '抽陀螺', 'http://123.57.52.135:8080/ChildHood/gameicon/ctl.png', 'http://123.57.52.135:8080/ChildHood/pics/rule5.jpg', '1', '1', '陀螺：形状上半部分为圆形，下方尖锐。从前多用木头制成，现代多为塑料或铁制。玩时可用绳子缠绕，用力抽绳，使直立旋转。', '1', '1', '6', '2', '12', null, '2016-06-28 20:08:38', '1.50', '0', 'Y');
INSERT INTO `t_gamerule_head` VALUES ('6', '跳房子', 'http://123.57.52.135:8080/ChildHood/gameicon/tfz.png', 'http://123.57.52.135:8080/ChildHood/pics/rule6.jpg', '1', '1', '跳房子：也叫跳飞机，用粉笔或树枝在地上画上九个格，玩家依次轮流跳过标有数字的格子。', '2', '1', '6', '2', '12', null, '2016-06-28 20:08:29', '5.00', '0', 'Y');
INSERT INTO `t_gamerule_head` VALUES ('7', '丢手绢', 'http://123.57.52.135:8080/ChildHood/gameicon/dsj.png', 'http://123.57.52.135:8080/ChildHood/pics/rule7.jpg', '1', '1', '丢手绢：又叫丢手帕，我国汉族传统的民间儿童游戏。', '1', '1', '6', '2', '12', null, '2016-06-28 20:08:31', '5.00', '0', 'Y');
INSERT INTO `t_gamerule_head` VALUES ('8', '过家家', 'http://123.57.52.135:8080/ChildHood/gameicon/gjj.png', 'http://123.57.52.135:8080/ChildHood/pics/rule8.jpg', '1', '1', '过家家：是孩子模仿成年人的一种游戏，一个人或几个人都可进行。几个人一起玩时，有当“爸爸”、“妈妈”、“弟弟”；有的去“买菜”，有的“煮饭”，有的抱“娃娃”等，也有摹拟种瓜等生产活动的，模仿大人过日子。', '2', '1', '6', '2', '12', null, '2016-06-28 20:08:33', '3.00', '0', 'Y');
INSERT INTO `t_gamerule_head` VALUES ('9', '翻花绳', 'http://123.57.52.135:8080/ChildHood/gameicon/fhs.png', 'http://123.57.52.135:8080/ChildHood/pics/rule9.jpg', '1', '1', '翻花绳：我国民间流传的儿童游戏。在不同的地域，有不同的称法，如线翻花、翻花鼓、挑绷绷、解股等等。这是一种利用绳子玩的玩意，只需灵巧的手指，就可翻转出许多的花样。', '1', '1', '6', '2', '12', null, '2016-06-28 20:08:35', '5.00', '0', 'Y');
INSERT INTO `t_gamerule_head` VALUES ('10', '打弹弓', 'http://123.57.52.135:8080/ChildHood/gameicon/ddg.png', 'http://123.57.52.135:8080/ChildHood/pics/rule10.jpg', '1', '1', '弹弓：是一种冷兵器或者是游戏工具。弹弓一般用树木的枝桠制作，呈“丫”字形，上两头系上皮筋，皮筋中段系上一包裹弹丸的皮块。', '2', '1', '6', '2', '12', null, '2016-06-28 20:08:37', '5.00', '0', 'Y');
INSERT INTO `t_gamerule_head` VALUES ('11', '踢毽子', 'http://123.57.52.135:8080/ChildHood/gameicon/tjz.png', 'http://123.57.52.135:8080/ChildHood/pics/rule5.jpg', '1', '1', '踢毽子：又叫“打鸡”。起源于汉代，盛行于南北朝和隋唐，至今已有两千多年的历史了，是中国民间体育活动之一，是一项简便易行的健身活动。', '1', '1', '6', '2', '12', null, '2016-06-28 20:08:25', '5.00', '0', 'Y');
INSERT INTO `t_gamerule_head` VALUES ('12', '捉迷藏', 'http://123.57.52.135:8080/ChildHood/gameicon/zmc.png', 'http://123.57.52.135:8080/ChildHood/pics/rule3.jpg', '1', '1', '捉迷藏：亦称摸瞎子。即蒙住眼睛寻找躲藏者的游戏。', '1', '1', '6', '2', '12', null, '2016-06-28 20:08:21', '5.00', '1', 'Y');

-- ----------------------------
-- Table structure for t_pub_area
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_area`;
CREATE TABLE `t_pub_area` (
  `AREA_CODE` int(11) NOT NULL,
  `AREA_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`AREA_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_area
-- ----------------------------
INSERT INTO `t_pub_area` VALUES ('0', '未设置');
INSERT INTO `t_pub_area` VALUES ('1', '华东地区');
INSERT INTO `t_pub_area` VALUES ('2', '华南地区');
INSERT INTO `t_pub_area` VALUES ('3', '华中地区');
INSERT INTO `t_pub_area` VALUES ('4', '华北地区');
INSERT INTO `t_pub_area` VALUES ('5', '西北地区');
INSERT INTO `t_pub_area` VALUES ('6', '西南地区');
INSERT INTO `t_pub_area` VALUES ('7', '东北地区');
INSERT INTO `t_pub_area` VALUES ('8', '港澳台地区');

-- ----------------------------
-- Table structure for t_pub_city
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_city`;
CREATE TABLE `t_pub_city` (
  `CITY_CODE` int(11) NOT NULL,
  `BELONGING` int(11) DEFAULT NULL,
  `CITY_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CITY_CODE`),
  KEY `FK_BELONGING_PROVINCE` (`BELONGING`),
  CONSTRAINT `FK_BELONGING_PROVINCE` FOREIGN KEY (`BELONGING`) REFERENCES `t_pub_province` (`PROVINCE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_city
-- ----------------------------
INSERT INTO `t_pub_city` VALUES ('0', '0', '未设置');
INSERT INTO `t_pub_city` VALUES ('1', '1', '北京市');
INSERT INTO `t_pub_city` VALUES ('2', '2', '天津市');
INSERT INTO `t_pub_city` VALUES ('3', '3', '上海市');
INSERT INTO `t_pub_city` VALUES ('4', '4', '重庆市');
INSERT INTO `t_pub_city` VALUES ('5', '5', '石家庄市');
INSERT INTO `t_pub_city` VALUES ('6', '5', '唐山市');
INSERT INTO `t_pub_city` VALUES ('7', '5', '秦皇岛市');
INSERT INTO `t_pub_city` VALUES ('8', '5', '邯郸市');
INSERT INTO `t_pub_city` VALUES ('9', '5', '邢台市');
INSERT INTO `t_pub_city` VALUES ('10', '5', '保定市');
INSERT INTO `t_pub_city` VALUES ('11', '5', '张家口市');
INSERT INTO `t_pub_city` VALUES ('12', '5', '承德市');
INSERT INTO `t_pub_city` VALUES ('13', '5', '沧州市');
INSERT INTO `t_pub_city` VALUES ('14', '5', '廊坊市');
INSERT INTO `t_pub_city` VALUES ('15', '5', '衡水市');
INSERT INTO `t_pub_city` VALUES ('16', '6', '太原市');
INSERT INTO `t_pub_city` VALUES ('17', '6', '大同市');
INSERT INTO `t_pub_city` VALUES ('18', '6', '阳泉市');
INSERT INTO `t_pub_city` VALUES ('19', '6', '长治市');
INSERT INTO `t_pub_city` VALUES ('20', '6', '晋城市');
INSERT INTO `t_pub_city` VALUES ('21', '6', '朔州市');
INSERT INTO `t_pub_city` VALUES ('22', '6', '晋中市');
INSERT INTO `t_pub_city` VALUES ('23', '6', '运城市');
INSERT INTO `t_pub_city` VALUES ('24', '6', '忻州市');
INSERT INTO `t_pub_city` VALUES ('25', '6', '临汾市');
INSERT INTO `t_pub_city` VALUES ('26', '6', '吕梁市');
INSERT INTO `t_pub_city` VALUES ('27', '7', '沈阳市');
INSERT INTO `t_pub_city` VALUES ('28', '7', '大连市');
INSERT INTO `t_pub_city` VALUES ('29', '7', '鞍山市');
INSERT INTO `t_pub_city` VALUES ('30', '7', '抚顺市');
INSERT INTO `t_pub_city` VALUES ('31', '7', '本溪市');
INSERT INTO `t_pub_city` VALUES ('32', '7', '丹东市');
INSERT INTO `t_pub_city` VALUES ('33', '7', '锦州市');
INSERT INTO `t_pub_city` VALUES ('34', '7', '营口市');
INSERT INTO `t_pub_city` VALUES ('35', '7', '阜新市');
INSERT INTO `t_pub_city` VALUES ('36', '7', '辽阳市');
INSERT INTO `t_pub_city` VALUES ('37', '7', '盘锦市');
INSERT INTO `t_pub_city` VALUES ('38', '7', '铁岭市');
INSERT INTO `t_pub_city` VALUES ('39', '7', '朝阳市');
INSERT INTO `t_pub_city` VALUES ('40', '7', '葫芦岛市');
INSERT INTO `t_pub_city` VALUES ('41', '7', '昌图县');
INSERT INTO `t_pub_city` VALUES ('42', '7', '绥中县');
INSERT INTO `t_pub_city` VALUES ('43', '8', '长春市');
INSERT INTO `t_pub_city` VALUES ('44', '8', '吉林市');
INSERT INTO `t_pub_city` VALUES ('45', '8', '四平市');
INSERT INTO `t_pub_city` VALUES ('46', '8', '辽源市');
INSERT INTO `t_pub_city` VALUES ('47', '8', '通化市');
INSERT INTO `t_pub_city` VALUES ('48', '8', '白山市');
INSERT INTO `t_pub_city` VALUES ('49', '8', '白城市');
INSERT INTO `t_pub_city` VALUES ('50', '8', '松原市');
INSERT INTO `t_pub_city` VALUES ('51', '8', '延边朝鲜族自治州');
INSERT INTO `t_pub_city` VALUES ('52', '8', '吉林省长白山保护开发区');
INSERT INTO `t_pub_city` VALUES ('53', '8', '梅河口市');
INSERT INTO `t_pub_city` VALUES ('54', '8', '公主岭市');
INSERT INTO `t_pub_city` VALUES ('55', '9', '哈尔滨市');
INSERT INTO `t_pub_city` VALUES ('56', '9', '齐齐哈尔市');
INSERT INTO `t_pub_city` VALUES ('57', '9', '鸡西市');
INSERT INTO `t_pub_city` VALUES ('58', '9', '鹤岗市');
INSERT INTO `t_pub_city` VALUES ('59', '9', '双鸭山市');
INSERT INTO `t_pub_city` VALUES ('60', '9', '大庆市');
INSERT INTO `t_pub_city` VALUES ('61', '9', '伊春市');
INSERT INTO `t_pub_city` VALUES ('62', '9', '佳木斯市');
INSERT INTO `t_pub_city` VALUES ('63', '9', '七台河市');
INSERT INTO `t_pub_city` VALUES ('64', '9', '牡丹江市');
INSERT INTO `t_pub_city` VALUES ('65', '9', '黑河市');
INSERT INTO `t_pub_city` VALUES ('66', '9', '绥化市');
INSERT INTO `t_pub_city` VALUES ('67', '9', '大兴安岭地区');
INSERT INTO `t_pub_city` VALUES ('68', '10', '南京市');
INSERT INTO `t_pub_city` VALUES ('69', '10', '无锡市');
INSERT INTO `t_pub_city` VALUES ('70', '10', '徐州市');
INSERT INTO `t_pub_city` VALUES ('71', '10', '常州市');
INSERT INTO `t_pub_city` VALUES ('72', '10', '苏州市');
INSERT INTO `t_pub_city` VALUES ('73', '10', '南通市');
INSERT INTO `t_pub_city` VALUES ('74', '10', '连云港市');
INSERT INTO `t_pub_city` VALUES ('75', '10', '淮安市');
INSERT INTO `t_pub_city` VALUES ('76', '10', '盐城市');
INSERT INTO `t_pub_city` VALUES ('77', '10', '扬州市');
INSERT INTO `t_pub_city` VALUES ('78', '10', '镇江市');
INSERT INTO `t_pub_city` VALUES ('79', '10', '泰州市');
INSERT INTO `t_pub_city` VALUES ('80', '10', '宿迁市');
INSERT INTO `t_pub_city` VALUES ('81', '11', '杭州市');
INSERT INTO `t_pub_city` VALUES ('82', '11', '宁波市');
INSERT INTO `t_pub_city` VALUES ('83', '11', '温州市');
INSERT INTO `t_pub_city` VALUES ('84', '11', '绍兴市');
INSERT INTO `t_pub_city` VALUES ('85', '11', '湖州市');
INSERT INTO `t_pub_city` VALUES ('86', '11', '嘉兴市');
INSERT INTO `t_pub_city` VALUES ('87', '11', '金华市');
INSERT INTO `t_pub_city` VALUES ('88', '11', '衢州市');
INSERT INTO `t_pub_city` VALUES ('89', '11', '台州市');
INSERT INTO `t_pub_city` VALUES ('90', '11', '丽水市');
INSERT INTO `t_pub_city` VALUES ('91', '11', '舟山市');
INSERT INTO `t_pub_city` VALUES ('92', '11', '义乌市');
INSERT INTO `t_pub_city` VALUES ('93', '12', '合肥市');
INSERT INTO `t_pub_city` VALUES ('94', '12', '芜湖市');
INSERT INTO `t_pub_city` VALUES ('95', '12', '蚌埠市');
INSERT INTO `t_pub_city` VALUES ('96', '12', '淮南市');
INSERT INTO `t_pub_city` VALUES ('97', '12', '马鞍山市');
INSERT INTO `t_pub_city` VALUES ('98', '12', '淮北市');
INSERT INTO `t_pub_city` VALUES ('99', '12', '铜陵市');
INSERT INTO `t_pub_city` VALUES ('100', '12', '安庆市');
INSERT INTO `t_pub_city` VALUES ('101', '12', '黄山市');
INSERT INTO `t_pub_city` VALUES ('102', '12', '阜阳市');
INSERT INTO `t_pub_city` VALUES ('103', '12', '宿州市');
INSERT INTO `t_pub_city` VALUES ('104', '12', '滁州市');
INSERT INTO `t_pub_city` VALUES ('105', '12', '六安市');
INSERT INTO `t_pub_city` VALUES ('106', '12', '宣城市');
INSERT INTO `t_pub_city` VALUES ('107', '12', '池州市');
INSERT INTO `t_pub_city` VALUES ('108', '12', '亳州市');
INSERT INTO `t_pub_city` VALUES ('109', '12', '广德县');
INSERT INTO `t_pub_city` VALUES ('110', '12', '宿松县');
INSERT INTO `t_pub_city` VALUES ('111', '13', '福州市');
INSERT INTO `t_pub_city` VALUES ('112', '13', '厦门市');
INSERT INTO `t_pub_city` VALUES ('113', '13', '漳州市');
INSERT INTO `t_pub_city` VALUES ('114', '13', '泉州市');
INSERT INTO `t_pub_city` VALUES ('115', '13', '三明市');
INSERT INTO `t_pub_city` VALUES ('116', '13', '莆田市');
INSERT INTO `t_pub_city` VALUES ('117', '13', '南平市');
INSERT INTO `t_pub_city` VALUES ('118', '13', '龙岩市');
INSERT INTO `t_pub_city` VALUES ('119', '13', '宁德市');
INSERT INTO `t_pub_city` VALUES ('120', '13', '平潭市');
INSERT INTO `t_pub_city` VALUES ('121', '14', '南昌市');
INSERT INTO `t_pub_city` VALUES ('122', '14', '九江市');
INSERT INTO `t_pub_city` VALUES ('123', '14', '上饶市');
INSERT INTO `t_pub_city` VALUES ('124', '14', '抚州市');
INSERT INTO `t_pub_city` VALUES ('125', '14', '宜春市');
INSERT INTO `t_pub_city` VALUES ('126', '14', '吉安市');
INSERT INTO `t_pub_city` VALUES ('127', '14', '赣州市');
INSERT INTO `t_pub_city` VALUES ('128', '14', '景德镇市');
INSERT INTO `t_pub_city` VALUES ('129', '14', '萍乡市');
INSERT INTO `t_pub_city` VALUES ('130', '14', '新余市');
INSERT INTO `t_pub_city` VALUES ('131', '14', '鹰潭市');
INSERT INTO `t_pub_city` VALUES ('132', '14', '共青城市');
INSERT INTO `t_pub_city` VALUES ('133', '14', '瑞金市');
INSERT INTO `t_pub_city` VALUES ('134', '14', '丰城市');
INSERT INTO `t_pub_city` VALUES ('135', '14', '鄱阳县');
INSERT INTO `t_pub_city` VALUES ('136', '14', '安福县');
INSERT INTO `t_pub_city` VALUES ('137', '14', '南城县');
INSERT INTO `t_pub_city` VALUES ('138', '15', '济南市');
INSERT INTO `t_pub_city` VALUES ('139', '15', '青岛市');
INSERT INTO `t_pub_city` VALUES ('140', '15', '淄博市');
INSERT INTO `t_pub_city` VALUES ('141', '15', '枣庄市');
INSERT INTO `t_pub_city` VALUES ('142', '15', '东营市');
INSERT INTO `t_pub_city` VALUES ('143', '15', '烟台市');
INSERT INTO `t_pub_city` VALUES ('144', '15', '潍坊市');
INSERT INTO `t_pub_city` VALUES ('145', '15', '济宁市');
INSERT INTO `t_pub_city` VALUES ('146', '15', '泰安市');
INSERT INTO `t_pub_city` VALUES ('147', '15', '威海市');
INSERT INTO `t_pub_city` VALUES ('148', '15', '日照市');
INSERT INTO `t_pub_city` VALUES ('149', '15', '滨州市');
INSERT INTO `t_pub_city` VALUES ('150', '15', '德州市');
INSERT INTO `t_pub_city` VALUES ('151', '15', '聊城市');
INSERT INTO `t_pub_city` VALUES ('152', '15', '临沂市');
INSERT INTO `t_pub_city` VALUES ('153', '15', '菏泽市');
INSERT INTO `t_pub_city` VALUES ('154', '15', '莱芜市');
INSERT INTO `t_pub_city` VALUES ('155', '16', '郑州市');
INSERT INTO `t_pub_city` VALUES ('156', '16', '开封市');
INSERT INTO `t_pub_city` VALUES ('157', '16', '洛阳市');
INSERT INTO `t_pub_city` VALUES ('158', '16', '平顶山市');
INSERT INTO `t_pub_city` VALUES ('159', '16', '安阳市');
INSERT INTO `t_pub_city` VALUES ('160', '16', '鹤壁市');
INSERT INTO `t_pub_city` VALUES ('161', '16', '新乡市');
INSERT INTO `t_pub_city` VALUES ('162', '16', '焦作市');
INSERT INTO `t_pub_city` VALUES ('163', '16', '濮阳市');
INSERT INTO `t_pub_city` VALUES ('164', '16', '许昌市');
INSERT INTO `t_pub_city` VALUES ('165', '16', '漯河市');
INSERT INTO `t_pub_city` VALUES ('166', '16', '三门峡市');
INSERT INTO `t_pub_city` VALUES ('167', '16', '商丘市');
INSERT INTO `t_pub_city` VALUES ('168', '16', '周口市');
INSERT INTO `t_pub_city` VALUES ('169', '16', '驻马店市');
INSERT INTO `t_pub_city` VALUES ('170', '16', '南阳市');
INSERT INTO `t_pub_city` VALUES ('171', '16', '信阳市');
INSERT INTO `t_pub_city` VALUES ('172', '16', '济源市');
INSERT INTO `t_pub_city` VALUES ('173', '16', '巩义市');
INSERT INTO `t_pub_city` VALUES ('174', '16', '邓州市');
INSERT INTO `t_pub_city` VALUES ('175', '16', '汝州市');
INSERT INTO `t_pub_city` VALUES ('176', '16', '长垣县');
INSERT INTO `t_pub_city` VALUES ('177', '16', '滑县');
INSERT INTO `t_pub_city` VALUES ('178', '16', '兰考县');
INSERT INTO `t_pub_city` VALUES ('179', '16', '新蔡县');
INSERT INTO `t_pub_city` VALUES ('180', '16', '固始县');
INSERT INTO `t_pub_city` VALUES ('181', '16', '鹿邑县');
INSERT INTO `t_pub_city` VALUES ('182', '16', '永城市');
INSERT INTO `t_pub_city` VALUES ('183', '17', '黄冈市');
INSERT INTO `t_pub_city` VALUES ('184', '17', '黄石市');
INSERT INTO `t_pub_city` VALUES ('185', '17', '荆门市');
INSERT INTO `t_pub_city` VALUES ('186', '17', '荆州市');
INSERT INTO `t_pub_city` VALUES ('187', '17', '鄂州市');
INSERT INTO `t_pub_city` VALUES ('188', '17', '十堰市');
INSERT INTO `t_pub_city` VALUES ('189', '17', '随州市');
INSERT INTO `t_pub_city` VALUES ('190', '17', '武汉市');
INSERT INTO `t_pub_city` VALUES ('191', '17', '孝感市');
INSERT INTO `t_pub_city` VALUES ('192', '17', '咸宁市');
INSERT INTO `t_pub_city` VALUES ('193', '17', '襄阳市');
INSERT INTO `t_pub_city` VALUES ('194', '17', '宜昌市');
INSERT INTO `t_pub_city` VALUES ('195', '17', '恩施土家族苗族自治州');
INSERT INTO `t_pub_city` VALUES ('196', '17', '仙桃市');
INSERT INTO `t_pub_city` VALUES ('197', '17', '潜江市');
INSERT INTO `t_pub_city` VALUES ('198', '17', '天门市');
INSERT INTO `t_pub_city` VALUES ('199', '17', '神农架林区');
INSERT INTO `t_pub_city` VALUES ('200', '18', '长沙市');
INSERT INTO `t_pub_city` VALUES ('201', '18', '株洲市');
INSERT INTO `t_pub_city` VALUES ('202', '18', '湘潭市');
INSERT INTO `t_pub_city` VALUES ('203', '18', '衡阳市');
INSERT INTO `t_pub_city` VALUES ('204', '18', '邵阳市');
INSERT INTO `t_pub_city` VALUES ('205', '18', '岳阳市');
INSERT INTO `t_pub_city` VALUES ('206', '18', '常德市');
INSERT INTO `t_pub_city` VALUES ('207', '18', '张家界市');
INSERT INTO `t_pub_city` VALUES ('208', '18', '益阳市');
INSERT INTO `t_pub_city` VALUES ('209', '18', '娄底市');
INSERT INTO `t_pub_city` VALUES ('210', '18', '郴州市');
INSERT INTO `t_pub_city` VALUES ('211', '18', '永州市');
INSERT INTO `t_pub_city` VALUES ('212', '18', '怀化市');
INSERT INTO `t_pub_city` VALUES ('213', '18', '湘西土家族苗族自治州');
INSERT INTO `t_pub_city` VALUES ('214', '18', '浏阳市');
INSERT INTO `t_pub_city` VALUES ('215', '18', '茶陵县');
INSERT INTO `t_pub_city` VALUES ('216', '18', '湘乡市');
INSERT INTO `t_pub_city` VALUES ('217', '18', '平江县');
INSERT INTO `t_pub_city` VALUES ('218', '18', '石门县');
INSERT INTO `t_pub_city` VALUES ('219', '18', '耒阳市');
INSERT INTO `t_pub_city` VALUES ('220', '18', '武冈市');
INSERT INTO `t_pub_city` VALUES ('221', '18', '慈利县');
INSERT INTO `t_pub_city` VALUES ('222', '18', '安化县');
INSERT INTO `t_pub_city` VALUES ('223', '18', '宜章县');
INSERT INTO `t_pub_city` VALUES ('224', '18', '蓝山县');
INSERT INTO `t_pub_city` VALUES ('225', '18', '溆浦县');
INSERT INTO `t_pub_city` VALUES ('226', '18', '新化县');
INSERT INTO `t_pub_city` VALUES ('227', '19', '广州市');
INSERT INTO `t_pub_city` VALUES ('228', '19', '深圳市');
INSERT INTO `t_pub_city` VALUES ('229', '19', '珠海市');
INSERT INTO `t_pub_city` VALUES ('230', '19', '汕头市');
INSERT INTO `t_pub_city` VALUES ('231', '19', '佛山市');
INSERT INTO `t_pub_city` VALUES ('232', '19', '韶关市');
INSERT INTO `t_pub_city` VALUES ('233', '19', '湛江市');
INSERT INTO `t_pub_city` VALUES ('234', '19', '肇庆市');
INSERT INTO `t_pub_city` VALUES ('235', '19', '江门市');
INSERT INTO `t_pub_city` VALUES ('236', '19', '茂名市');
INSERT INTO `t_pub_city` VALUES ('237', '19', '惠州市');
INSERT INTO `t_pub_city` VALUES ('238', '19', '梅州市');
INSERT INTO `t_pub_city` VALUES ('239', '19', '汕尾市');
INSERT INTO `t_pub_city` VALUES ('240', '19', '河源市');
INSERT INTO `t_pub_city` VALUES ('241', '19', '阳江市');
INSERT INTO `t_pub_city` VALUES ('242', '19', '清远市');
INSERT INTO `t_pub_city` VALUES ('243', '19', '东莞市');
INSERT INTO `t_pub_city` VALUES ('244', '19', '中山市');
INSERT INTO `t_pub_city` VALUES ('245', '19', '潮州市');
INSERT INTO `t_pub_city` VALUES ('246', '19', '揭阳市');
INSERT INTO `t_pub_city` VALUES ('247', '19', '云浮市');
INSERT INTO `t_pub_city` VALUES ('248', '19', '佛山市顺德区');
INSERT INTO `t_pub_city` VALUES ('249', '20', '海口市');
INSERT INTO `t_pub_city` VALUES ('250', '20', '三亚市');
INSERT INTO `t_pub_city` VALUES ('251', '20', '三沙市');
INSERT INTO `t_pub_city` VALUES ('252', '20', '儋州市');
INSERT INTO `t_pub_city` VALUES ('253', '20', '五指山市');
INSERT INTO `t_pub_city` VALUES ('254', '20', '文昌市');
INSERT INTO `t_pub_city` VALUES ('255', '20', '琼海市');
INSERT INTO `t_pub_city` VALUES ('256', '20', '万宁市');
INSERT INTO `t_pub_city` VALUES ('257', '20', '东方市');
INSERT INTO `t_pub_city` VALUES ('258', '20', '定安县');
INSERT INTO `t_pub_city` VALUES ('259', '20', '屯昌县');
INSERT INTO `t_pub_city` VALUES ('260', '20', '澄迈县');
INSERT INTO `t_pub_city` VALUES ('261', '20', '临高县');
INSERT INTO `t_pub_city` VALUES ('262', '20', '琼中黎族苗族自治县');
INSERT INTO `t_pub_city` VALUES ('263', '20', '保亭黎族苗族自治县');
INSERT INTO `t_pub_city` VALUES ('264', '20', '白沙黎族自治县');
INSERT INTO `t_pub_city` VALUES ('265', '20', '昌江黎族自治县');
INSERT INTO `t_pub_city` VALUES ('266', '20', '乐东黎族自治县');
INSERT INTO `t_pub_city` VALUES ('267', '20', '陵水黎族自治县');
INSERT INTO `t_pub_city` VALUES ('268', '21', '成都市');
INSERT INTO `t_pub_city` VALUES ('269', '21', '绵阳市');
INSERT INTO `t_pub_city` VALUES ('270', '21', '自贡市');
INSERT INTO `t_pub_city` VALUES ('271', '21', '攀枝花市');
INSERT INTO `t_pub_city` VALUES ('272', '21', '泸州市');
INSERT INTO `t_pub_city` VALUES ('273', '21', '德阳市');
INSERT INTO `t_pub_city` VALUES ('274', '21', '广元市');
INSERT INTO `t_pub_city` VALUES ('275', '21', '遂宁市');
INSERT INTO `t_pub_city` VALUES ('276', '21', '内江市');
INSERT INTO `t_pub_city` VALUES ('277', '21', '乐山市');
INSERT INTO `t_pub_city` VALUES ('278', '21', '资阳市');
INSERT INTO `t_pub_city` VALUES ('279', '21', '宜宾市');
INSERT INTO `t_pub_city` VALUES ('280', '21', '南充市');
INSERT INTO `t_pub_city` VALUES ('281', '21', '达州市');
INSERT INTO `t_pub_city` VALUES ('282', '21', '雅安市');
INSERT INTO `t_pub_city` VALUES ('283', '21', '阿坝藏族羌族自治州');
INSERT INTO `t_pub_city` VALUES ('284', '21', '甘孜藏族自治州');
INSERT INTO `t_pub_city` VALUES ('285', '21', '凉山彝族自治州');
INSERT INTO `t_pub_city` VALUES ('286', '21', '广安市');
INSERT INTO `t_pub_city` VALUES ('287', '21', '巴中市');
INSERT INTO `t_pub_city` VALUES ('288', '21', '眉山市');
INSERT INTO `t_pub_city` VALUES ('289', '22', '贵阳市');
INSERT INTO `t_pub_city` VALUES ('290', '22', '六盘水市');
INSERT INTO `t_pub_city` VALUES ('291', '22', '遵义市');
INSERT INTO `t_pub_city` VALUES ('292', '22', '安顺市');
INSERT INTO `t_pub_city` VALUES ('293', '22', '毕节市');
INSERT INTO `t_pub_city` VALUES ('294', '22', '铜仁市');
INSERT INTO `t_pub_city` VALUES ('295', '22', '黔西南布依族苗族自治州');
INSERT INTO `t_pub_city` VALUES ('296', '22', '黔东南苗族侗族自治州');
INSERT INTO `t_pub_city` VALUES ('297', '22', '黔南布依族苗族自治州');
INSERT INTO `t_pub_city` VALUES ('298', '22', '仁怀市');
INSERT INTO `t_pub_city` VALUES ('299', '22', '赤水市');
INSERT INTO `t_pub_city` VALUES ('300', '22', '威宁彝族回族苗族自治县');
INSERT INTO `t_pub_city` VALUES ('301', '22', '福泉市');
INSERT INTO `t_pub_city` VALUES ('302', '22', '镇远县');
INSERT INTO `t_pub_city` VALUES ('303', '22', '黎平县');
INSERT INTO `t_pub_city` VALUES ('304', '23', '昆明市');
INSERT INTO `t_pub_city` VALUES ('305', '23', '曲靖市');
INSERT INTO `t_pub_city` VALUES ('306', '23', '玉溪市');
INSERT INTO `t_pub_city` VALUES ('307', '23', '昭通市');
INSERT INTO `t_pub_city` VALUES ('308', '23', '保山市');
INSERT INTO `t_pub_city` VALUES ('309', '23', '丽江市');
INSERT INTO `t_pub_city` VALUES ('310', '23', '普洱市');
INSERT INTO `t_pub_city` VALUES ('311', '23', '临沧市');
INSERT INTO `t_pub_city` VALUES ('312', '23', '德宏傣族景颇族自治州');
INSERT INTO `t_pub_city` VALUES ('313', '23', '怒江傈僳族自治州');
INSERT INTO `t_pub_city` VALUES ('314', '23', '迪庆藏族自治州');
INSERT INTO `t_pub_city` VALUES ('315', '23', '大理白族自治州');
INSERT INTO `t_pub_city` VALUES ('316', '23', '楚雄彝族自治州');
INSERT INTO `t_pub_city` VALUES ('317', '23', '红河哈尼族彝族自治州');
INSERT INTO `t_pub_city` VALUES ('318', '23', '文山壮族苗族自治州');
INSERT INTO `t_pub_city` VALUES ('319', '23', '西双版纳傣族自治州');
INSERT INTO `t_pub_city` VALUES ('320', '24', '西安市');
INSERT INTO `t_pub_city` VALUES ('321', '24', '宝鸡市');
INSERT INTO `t_pub_city` VALUES ('322', '24', '咸阳市');
INSERT INTO `t_pub_city` VALUES ('323', '24', '渭南市');
INSERT INTO `t_pub_city` VALUES ('324', '24', '铜川市');
INSERT INTO `t_pub_city` VALUES ('325', '24', '延安市');
INSERT INTO `t_pub_city` VALUES ('326', '24', '榆林市');
INSERT INTO `t_pub_city` VALUES ('327', '24', '安康市');
INSERT INTO `t_pub_city` VALUES ('328', '24', '汉中市');
INSERT INTO `t_pub_city` VALUES ('329', '24', '商洛市');
INSERT INTO `t_pub_city` VALUES ('330', '24', '杨凌示范区');
INSERT INTO `t_pub_city` VALUES ('331', '24', '韩城市');
INSERT INTO `t_pub_city` VALUES ('332', '24', '神木县');
INSERT INTO `t_pub_city` VALUES ('333', '24', '府谷县');
INSERT INTO `t_pub_city` VALUES ('334', '25', '兰州市');
INSERT INTO `t_pub_city` VALUES ('335', '25', '嘉峪关市');
INSERT INTO `t_pub_city` VALUES ('336', '25', '金昌市');
INSERT INTO `t_pub_city` VALUES ('337', '25', '白银市');
INSERT INTO `t_pub_city` VALUES ('338', '25', '天水市');
INSERT INTO `t_pub_city` VALUES ('339', '25', '酒泉市');
INSERT INTO `t_pub_city` VALUES ('340', '25', '张掖市');
INSERT INTO `t_pub_city` VALUES ('341', '25', '武威市');
INSERT INTO `t_pub_city` VALUES ('342', '25', '定西市');
INSERT INTO `t_pub_city` VALUES ('343', '25', '陇南市');
INSERT INTO `t_pub_city` VALUES ('344', '25', '平凉市');
INSERT INTO `t_pub_city` VALUES ('345', '25', '庆阳市');
INSERT INTO `t_pub_city` VALUES ('346', '25', '临夏回族自治州');
INSERT INTO `t_pub_city` VALUES ('347', '25', '甘南藏族自治州');
INSERT INTO `t_pub_city` VALUES ('348', '26', '西宁市');
INSERT INTO `t_pub_city` VALUES ('349', '26', '海东市');
INSERT INTO `t_pub_city` VALUES ('350', '26', '海北藏族自治州');
INSERT INTO `t_pub_city` VALUES ('351', '26', '黄南藏族自治州');
INSERT INTO `t_pub_city` VALUES ('352', '26', '海南藏族自治州');
INSERT INTO `t_pub_city` VALUES ('353', '26', '果洛藏族自治州');
INSERT INTO `t_pub_city` VALUES ('354', '26', '玉树藏族自治州');
INSERT INTO `t_pub_city` VALUES ('355', '26', '海西蒙古族藏族自治州');
INSERT INTO `t_pub_city` VALUES ('356', '27', '拉萨市');
INSERT INTO `t_pub_city` VALUES ('357', '27', '昌都市');
INSERT INTO `t_pub_city` VALUES ('358', '27', '日喀则市');
INSERT INTO `t_pub_city` VALUES ('359', '27', '林芝市');
INSERT INTO `t_pub_city` VALUES ('360', '27', '山南市');
INSERT INTO `t_pub_city` VALUES ('361', '27', '那曲地区');
INSERT INTO `t_pub_city` VALUES ('362', '27', '阿里地区');
INSERT INTO `t_pub_city` VALUES ('363', '28', '南宁市');
INSERT INTO `t_pub_city` VALUES ('364', '28', '柳州市');
INSERT INTO `t_pub_city` VALUES ('365', '28', '桂林市');
INSERT INTO `t_pub_city` VALUES ('366', '28', '梧州市');
INSERT INTO `t_pub_city` VALUES ('367', '28', '北海市');
INSERT INTO `t_pub_city` VALUES ('368', '28', '防城港市');
INSERT INTO `t_pub_city` VALUES ('369', '28', '钦州市');
INSERT INTO `t_pub_city` VALUES ('370', '28', '贵港市');
INSERT INTO `t_pub_city` VALUES ('371', '28', '玉林市');
INSERT INTO `t_pub_city` VALUES ('372', '28', '百色市');
INSERT INTO `t_pub_city` VALUES ('373', '28', '贺州市');
INSERT INTO `t_pub_city` VALUES ('374', '28', '河池市');
INSERT INTO `t_pub_city` VALUES ('375', '28', '来宾市');
INSERT INTO `t_pub_city` VALUES ('376', '28', '崇左市');
INSERT INTO `t_pub_city` VALUES ('377', '29', '呼和浩特市');
INSERT INTO `t_pub_city` VALUES ('378', '29', '包头市');
INSERT INTO `t_pub_city` VALUES ('379', '29', '乌海市');
INSERT INTO `t_pub_city` VALUES ('380', '29', '赤峰市');
INSERT INTO `t_pub_city` VALUES ('381', '29', '通辽市');
INSERT INTO `t_pub_city` VALUES ('382', '29', '鄂尔多斯市');
INSERT INTO `t_pub_city` VALUES ('383', '29', '呼伦贝尔市');
INSERT INTO `t_pub_city` VALUES ('384', '29', '巴彦淖尔市');
INSERT INTO `t_pub_city` VALUES ('385', '29', '乌兰察布市');
INSERT INTO `t_pub_city` VALUES ('386', '29', '兴安盟');
INSERT INTO `t_pub_city` VALUES ('387', '29', '锡林郭勒盟');
INSERT INTO `t_pub_city` VALUES ('388', '29', '阿拉善盟');
INSERT INTO `t_pub_city` VALUES ('389', '30', '银川市');
INSERT INTO `t_pub_city` VALUES ('390', '30', '石嘴山市');
INSERT INTO `t_pub_city` VALUES ('391', '30', '吴忠市');
INSERT INTO `t_pub_city` VALUES ('392', '30', '固原市');
INSERT INTO `t_pub_city` VALUES ('393', '30', '中卫市');
INSERT INTO `t_pub_city` VALUES ('394', '30', '同心县');
INSERT INTO `t_pub_city` VALUES ('395', '30', '盐池县');
INSERT INTO `t_pub_city` VALUES ('396', '31', '乌鲁木齐市');
INSERT INTO `t_pub_city` VALUES ('397', '31', '克拉玛依市');
INSERT INTO `t_pub_city` VALUES ('398', '31', '吐鲁番市');
INSERT INTO `t_pub_city` VALUES ('399', '31', '哈密市');
INSERT INTO `t_pub_city` VALUES ('400', '31', '阿克苏地区');
INSERT INTO `t_pub_city` VALUES ('401', '31', '喀什地区');
INSERT INTO `t_pub_city` VALUES ('402', '31', '和田地区　');
INSERT INTO `t_pub_city` VALUES ('403', '31', '昌吉回族自治州');
INSERT INTO `t_pub_city` VALUES ('404', '31', '博尔塔拉蒙古自治州');
INSERT INTO `t_pub_city` VALUES ('405', '31', '巴音郭楞蒙古自治州　');
INSERT INTO `t_pub_city` VALUES ('406', '31', '克孜勒苏柯尔克孜自治州');
INSERT INTO `t_pub_city` VALUES ('407', '31', '伊犁哈萨克自治州');
INSERT INTO `t_pub_city` VALUES ('408', '31', '石河子市');
INSERT INTO `t_pub_city` VALUES ('409', '31', '阿拉尔市');
INSERT INTO `t_pub_city` VALUES ('410', '31', '图木舒克市');
INSERT INTO `t_pub_city` VALUES ('411', '31', '五家渠市');
INSERT INTO `t_pub_city` VALUES ('412', '31', '北屯市');
INSERT INTO `t_pub_city` VALUES ('413', '31', '铁门关市');
INSERT INTO `t_pub_city` VALUES ('414', '31', '双河市');
INSERT INTO `t_pub_city` VALUES ('415', '31', '可克达拉市');
INSERT INTO `t_pub_city` VALUES ('416', '31', '昆玉市');
INSERT INTO `t_pub_city` VALUES ('417', '32', '香港岛');
INSERT INTO `t_pub_city` VALUES ('418', '32', '九龙半岛');
INSERT INTO `t_pub_city` VALUES ('419', '32', '新界');
INSERT INTO `t_pub_city` VALUES ('420', '33', '澳门半岛');
INSERT INTO `t_pub_city` VALUES ('421', '33', '离岛');
INSERT INTO `t_pub_city` VALUES ('422', '33', '无堂区划分区域');
INSERT INTO `t_pub_city` VALUES ('423', '34', '台北市');
INSERT INTO `t_pub_city` VALUES ('424', '34', '新北市');
INSERT INTO `t_pub_city` VALUES ('425', '34', '桃园市');
INSERT INTO `t_pub_city` VALUES ('426', '34', '台中市');
INSERT INTO `t_pub_city` VALUES ('427', '34', '台南市');
INSERT INTO `t_pub_city` VALUES ('428', '34', '高雄市');
INSERT INTO `t_pub_city` VALUES ('429', '34', '基隆市');
INSERT INTO `t_pub_city` VALUES ('430', '34', '新竹市');
INSERT INTO `t_pub_city` VALUES ('431', '34', '嘉义市');

-- ----------------------------
-- Table structure for t_pub_community
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_community`;
CREATE TABLE `t_pub_community` (
  `COMMUNITY_CODE` int(11) NOT NULL,
  `BELONGING` int(11) DEFAULT NULL,
  `COMMUNITY_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`COMMUNITY_CODE`),
  KEY `FK_BELONGING_OF_COMMUNITY` (`BELONGING`),
  CONSTRAINT `FK_BELONGING_OF_COMMUNITY` FOREIGN KEY (`BELONGING`) REFERENCES `t_pub_district` (`DISTRICT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_community
-- ----------------------------
INSERT INTO `t_pub_community` VALUES ('0', '0', '未设置');
INSERT INTO `t_pub_community` VALUES ('1', '1', '测试小区一');
INSERT INTO `t_pub_community` VALUES ('2', '1', '测试小区二');
INSERT INTO `t_pub_community` VALUES ('3', '1', '测试小区三');
INSERT INTO `t_pub_community` VALUES ('4', '1', '测试小区四');
INSERT INTO `t_pub_community` VALUES ('5', '1', '测试小区五');
INSERT INTO `t_pub_community` VALUES ('6', '1', '测试小区六');

-- ----------------------------
-- Table structure for t_pub_consts
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_consts`;
CREATE TABLE `t_pub_consts` (
  `CONST_ID` int(11) NOT NULL,
  `CONST_CODE` varchar(20) DEFAULT NULL,
  `CONST_EN` varchar(100) DEFAULT NULL,
  `CONST_CN` varchar(100) DEFAULT NULL,
  `CONST_NOTE` varchar(500) DEFAULT NULL,
  `CONST_MODULE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CONST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_consts
-- ----------------------------
INSERT INTO `t_pub_consts` VALUES ('0', 'S000', 'NOT_START', '游戏未开始', '游戏已开始召集，但没有开始进行', 'status');
INSERT INTO `t_pub_consts` VALUES ('1', 'S001', 'STARTED', '游戏已开始', '游戏已经开始，处于可加入状态（暂时规定游戏无人数上限，超过推荐人数或最高人数后的参与者由游戏参与者协商解决）', 'status');
INSERT INTO `t_pub_consts` VALUES ('2', 'S002', 'STARTED_FULL', '游戏已开始满员', '游戏已经开始且人数达到可参与人数最大值（保留状态，暂不使用）', 'status');
INSERT INTO `t_pub_consts` VALUES ('3', 'S003', 'STARTED_NOTFULL', '游戏已开始未满员', '游戏已经开始且人数未达到可参与人数最大值（保留状态，暂不使用）', 'status');
INSERT INTO `t_pub_consts` VALUES ('4', 'S004', 'FINISHED', '游戏已结束', '游戏已经结束（正常结束（超时自动结束、手动结束）或取消，可进行评价，并且修改游戏参与者状态）', 'status');
INSERT INTO `t_pub_consts` VALUES ('5', 'S005', 'CANNOT_START', '召集中不可开始', '游戏已开始召集，但人数没有达到最小开始人数，不可开始', 'status');
INSERT INTO `t_pub_consts` VALUES ('6', 'S006', 'CAN_START', '召集中可开始', '游戏已开始召集，人数已达到最小开始人数，可以开始', 'status');
INSERT INTO `t_pub_consts` VALUES ('7', 'S007', 'GATHERED', '召集完成', '召集已完成，游戏不可加入（保留状态，暂不使用）', 'status');
INSERT INTO `t_pub_consts` VALUES ('8', 'S008', 'JOINING', '参与者参与中', '游戏参与者正常加入游戏，并且游戏未开始、进行中', 'status');
INSERT INTO `t_pub_consts` VALUES ('9', 'S009', 'QUIT', '参与者已退出', '游戏参与者退出已开始的游戏（情景为加入进行中游戏并且不想去，可以退出再加入其它游戏）', 'status');
INSERT INTO `t_pub_consts` VALUES ('10', 'S010', 'CANCEL', '参与者已取消', '游戏参与者取消参与未开始的游戏（情景为加入未开始游戏并且不想去，可以取消再加入其它游戏）', 'status');
INSERT INTO `t_pub_consts` VALUES ('11', 'S011', 'JOINED', '参与者已完成游戏', '游戏参与者已正常参与完游戏过程，可对游戏进行评价打分，并且可以参加下一场游戏', 'status');
INSERT INTO `t_pub_consts` VALUES ('12', '0', 'CUSTOM_COMMUNITY', '用户自定义小区', 'COMMUNITY_CODE=0即小区编码为0的小区为用户自定义小区', 'def');
INSERT INTO `t_pub_consts` VALUES ('13', '0', 'CUSTOM_SCHOOL', '用户自定义学校', 'SCHOOL_CODE=0即学校编码为0的学校为用户自定义学校', 'def');
INSERT INTO `t_pub_consts` VALUES ('14', 'Y', 'ENABLED', '可用', '系统中可用标识符即\'Y\'', 'def');
INSERT INTO `t_pub_consts` VALUES ('15', 'N', 'DISABLED', '不可用', '系统中不可用标识符即\'N\'', 'def');
INSERT INTO `t_pub_consts` VALUES ('16', 'S012', 'BECANCELED', '参与者被清出', '游戏创建者取消游戏，所有参与者置为此状态', 'status');
INSERT INTO `t_pub_consts` VALUES ('17', 'S013', 'BEGOTOUT', '参与者被踢出', '参与者被游戏创建者踢出游戏', 'status');
INSERT INTO `t_pub_consts` VALUES ('18', 'S014', 'GAME_CANCEL', '游戏解散', '游戏创建者取消游戏', 'status');

-- ----------------------------
-- Table structure for t_pub_district
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_district`;
CREATE TABLE `t_pub_district` (
  `DISTRICT_CODE` int(11) NOT NULL,
  `BELONGING` int(11) DEFAULT NULL,
  `DISTRICT_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`DISTRICT_CODE`),
  KEY `FK_BELONGING_CITY` (`BELONGING`),
  CONSTRAINT `FK_BELONGING_CITY` FOREIGN KEY (`BELONGING`) REFERENCES `t_pub_city` (`CITY_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_district
-- ----------------------------
INSERT INTO `t_pub_district` VALUES ('0', '0', '未设置');
INSERT INTO `t_pub_district` VALUES ('1', '1', '东城区');
INSERT INTO `t_pub_district` VALUES ('2', '1', '西城区');
INSERT INTO `t_pub_district` VALUES ('3', '1', '海淀区');
INSERT INTO `t_pub_district` VALUES ('4', '1', '朝阳区');
INSERT INTO `t_pub_district` VALUES ('5', '1', '丰台区');
INSERT INTO `t_pub_district` VALUES ('6', '1', '石景山区');
INSERT INTO `t_pub_district` VALUES ('7', '1', '门头沟区');
INSERT INTO `t_pub_district` VALUES ('8', '1', '通州区');
INSERT INTO `t_pub_district` VALUES ('9', '1', '顺义区');
INSERT INTO `t_pub_district` VALUES ('10', '1', '房山区');
INSERT INTO `t_pub_district` VALUES ('11', '1', '大兴区');
INSERT INTO `t_pub_district` VALUES ('12', '1', '昌平区');
INSERT INTO `t_pub_district` VALUES ('13', '1', '怀柔区');
INSERT INTO `t_pub_district` VALUES ('14', '1', '平谷区');
INSERT INTO `t_pub_district` VALUES ('15', '1', '密云区');
INSERT INTO `t_pub_district` VALUES ('16', '1', '延庆区');
INSERT INTO `t_pub_district` VALUES ('17', '2', '和平区');
INSERT INTO `t_pub_district` VALUES ('18', '2', '河东区');
INSERT INTO `t_pub_district` VALUES ('19', '2', '河西区');
INSERT INTO `t_pub_district` VALUES ('20', '2', '南开区');
INSERT INTO `t_pub_district` VALUES ('21', '2', '河北区');
INSERT INTO `t_pub_district` VALUES ('22', '2', '红桥区');
INSERT INTO `t_pub_district` VALUES ('23', '2', '滨海新区');
INSERT INTO `t_pub_district` VALUES ('24', '2', '东丽区');
INSERT INTO `t_pub_district` VALUES ('25', '2', '西青区');
INSERT INTO `t_pub_district` VALUES ('26', '2', '津南区');
INSERT INTO `t_pub_district` VALUES ('27', '2', '北辰区');
INSERT INTO `t_pub_district` VALUES ('28', '2', '武清区');
INSERT INTO `t_pub_district` VALUES ('29', '2', '宝坻区');
INSERT INTO `t_pub_district` VALUES ('30', '2', '宁河区');
INSERT INTO `t_pub_district` VALUES ('31', '2', '静海区');
INSERT INTO `t_pub_district` VALUES ('32', '2', '蓟县');
INSERT INTO `t_pub_district` VALUES ('33', '3', '黄浦区');
INSERT INTO `t_pub_district` VALUES ('34', '3', '徐汇区');
INSERT INTO `t_pub_district` VALUES ('35', '3', '长宁区');
INSERT INTO `t_pub_district` VALUES ('36', '3', '静安区');
INSERT INTO `t_pub_district` VALUES ('37', '3', '普陀区');
INSERT INTO `t_pub_district` VALUES ('38', '3', '虹口区');
INSERT INTO `t_pub_district` VALUES ('39', '3', '杨浦区');
INSERT INTO `t_pub_district` VALUES ('40', '3', '浦东新区');
INSERT INTO `t_pub_district` VALUES ('41', '3', '闵行区');
INSERT INTO `t_pub_district` VALUES ('42', '3', '宝山区');
INSERT INTO `t_pub_district` VALUES ('43', '3', '嘉定区');
INSERT INTO `t_pub_district` VALUES ('44', '3', '金山区');
INSERT INTO `t_pub_district` VALUES ('45', '3', '松江区');
INSERT INTO `t_pub_district` VALUES ('46', '3', '青浦区');
INSERT INTO `t_pub_district` VALUES ('47', '3', '奉贤区');
INSERT INTO `t_pub_district` VALUES ('48', '3', '崇明县');
INSERT INTO `t_pub_district` VALUES ('49', '4', '渝中区');
INSERT INTO `t_pub_district` VALUES ('50', '4', '大渡口区');
INSERT INTO `t_pub_district` VALUES ('51', '4', '江北区');
INSERT INTO `t_pub_district` VALUES ('52', '4', '沙坪坝区');
INSERT INTO `t_pub_district` VALUES ('53', '4', '九龙坡区');
INSERT INTO `t_pub_district` VALUES ('54', '4', '南岸区');
INSERT INTO `t_pub_district` VALUES ('55', '4', '北碚区');
INSERT INTO `t_pub_district` VALUES ('56', '4', '渝北区');
INSERT INTO `t_pub_district` VALUES ('57', '4', '巴南区');
INSERT INTO `t_pub_district` VALUES ('58', '4', '涪陵区');
INSERT INTO `t_pub_district` VALUES ('59', '4', '綦江区');
INSERT INTO `t_pub_district` VALUES ('60', '4', '大足区');
INSERT INTO `t_pub_district` VALUES ('61', '4', '长寿区');
INSERT INTO `t_pub_district` VALUES ('62', '4', '江津区');
INSERT INTO `t_pub_district` VALUES ('63', '4', '合川区');
INSERT INTO `t_pub_district` VALUES ('64', '4', '永川区');
INSERT INTO `t_pub_district` VALUES ('65', '4', '南川区');
INSERT INTO `t_pub_district` VALUES ('66', '4', '璧山区');
INSERT INTO `t_pub_district` VALUES ('67', '4', '铜梁区');
INSERT INTO `t_pub_district` VALUES ('68', '4', '潼南区');
INSERT INTO `t_pub_district` VALUES ('69', '4', '荣昌区');
INSERT INTO `t_pub_district` VALUES ('70', '4', '万州区');
INSERT INTO `t_pub_district` VALUES ('71', '4', '梁平县');
INSERT INTO `t_pub_district` VALUES ('72', '4', '城口县');
INSERT INTO `t_pub_district` VALUES ('73', '4', '丰都县');
INSERT INTO `t_pub_district` VALUES ('74', '4', '垫江县');
INSERT INTO `t_pub_district` VALUES ('75', '4', '忠县');
INSERT INTO `t_pub_district` VALUES ('76', '4', '开县');
INSERT INTO `t_pub_district` VALUES ('77', '4', '云阳县');
INSERT INTO `t_pub_district` VALUES ('78', '4', '奉节县');
INSERT INTO `t_pub_district` VALUES ('79', '4', '巫山县');
INSERT INTO `t_pub_district` VALUES ('80', '4', '巫溪县');
INSERT INTO `t_pub_district` VALUES ('81', '4', '黔江区');
INSERT INTO `t_pub_district` VALUES ('82', '4', '武隆县');
INSERT INTO `t_pub_district` VALUES ('83', '4', '石柱土家族自治县');
INSERT INTO `t_pub_district` VALUES ('84', '4', '秀山土家族苗族自治县');
INSERT INTO `t_pub_district` VALUES ('85', '4', '酉阳土家族苗族自治县');
INSERT INTO `t_pub_district` VALUES ('86', '4', '彭水苗族土家族自治县');
INSERT INTO `t_pub_district` VALUES ('87', '5', '长安区');
INSERT INTO `t_pub_district` VALUES ('88', '5', '桥西区');
INSERT INTO `t_pub_district` VALUES ('89', '5', '新华区');
INSERT INTO `t_pub_district` VALUES ('90', '5', '井陉矿区');
INSERT INTO `t_pub_district` VALUES ('91', '5', '裕华区');
INSERT INTO `t_pub_district` VALUES ('92', '5', '藁城区');
INSERT INTO `t_pub_district` VALUES ('93', '5', '鹿泉区');
INSERT INTO `t_pub_district` VALUES ('94', '5', '栾城区');
INSERT INTO `t_pub_district` VALUES ('95', '5', '井陉县');
INSERT INTO `t_pub_district` VALUES ('96', '5', '正定县');
INSERT INTO `t_pub_district` VALUES ('97', '5', '行唐县');
INSERT INTO `t_pub_district` VALUES ('98', '5', '灵寿县');
INSERT INTO `t_pub_district` VALUES ('99', '5', '高邑县');
INSERT INTO `t_pub_district` VALUES ('100', '5', '深泽县');
INSERT INTO `t_pub_district` VALUES ('101', '5', '赞皇县');
INSERT INTO `t_pub_district` VALUES ('102', '5', '无极县');
INSERT INTO `t_pub_district` VALUES ('103', '5', '平山县');
INSERT INTO `t_pub_district` VALUES ('104', '5', '元氏县');
INSERT INTO `t_pub_district` VALUES ('105', '5', '赵县');
INSERT INTO `t_pub_district` VALUES ('106', '5', '晋州市');
INSERT INTO `t_pub_district` VALUES ('107', '5', '新乐市');
INSERT INTO `t_pub_district` VALUES ('108', '5', '辛集市');
INSERT INTO `t_pub_district` VALUES ('109', '6', '路北区');
INSERT INTO `t_pub_district` VALUES ('110', '6', '路南区');
INSERT INTO `t_pub_district` VALUES ('111', '6', '古冶区');
INSERT INTO `t_pub_district` VALUES ('112', '6', '开平区');
INSERT INTO `t_pub_district` VALUES ('113', '6', '丰南区');
INSERT INTO `t_pub_district` VALUES ('114', '6', '丰润区');
INSERT INTO `t_pub_district` VALUES ('115', '6', '曹妃甸区');
INSERT INTO `t_pub_district` VALUES ('116', '6', '滦县');
INSERT INTO `t_pub_district` VALUES ('117', '6', '滦南县');
INSERT INTO `t_pub_district` VALUES ('118', '6', '乐亭县');
INSERT INTO `t_pub_district` VALUES ('119', '6', '迁西县');
INSERT INTO `t_pub_district` VALUES ('120', '6', '玉田县');
INSERT INTO `t_pub_district` VALUES ('121', '6', '遵化市');
INSERT INTO `t_pub_district` VALUES ('122', '6', '迁安市');
INSERT INTO `t_pub_district` VALUES ('123', '7', '海港区');
INSERT INTO `t_pub_district` VALUES ('124', '7', '山海关区');
INSERT INTO `t_pub_district` VALUES ('125', '7', '抚宁区');
INSERT INTO `t_pub_district` VALUES ('126', '7', '北戴河区');
INSERT INTO `t_pub_district` VALUES ('127', '7', '青龙满族自治县');
INSERT INTO `t_pub_district` VALUES ('128', '7', '昌黎县');
INSERT INTO `t_pub_district` VALUES ('129', '7', '卢龙县');
INSERT INTO `t_pub_district` VALUES ('130', '8', '邯山区');
INSERT INTO `t_pub_district` VALUES ('131', '8', '丛台区');
INSERT INTO `t_pub_district` VALUES ('132', '8', '复兴区');
INSERT INTO `t_pub_district` VALUES ('133', '8', '峰峰矿区');
INSERT INTO `t_pub_district` VALUES ('134', '8', '邯郸县');
INSERT INTO `t_pub_district` VALUES ('135', '8', '临漳县');
INSERT INTO `t_pub_district` VALUES ('136', '8', '成安县');
INSERT INTO `t_pub_district` VALUES ('137', '8', '大名县');
INSERT INTO `t_pub_district` VALUES ('138', '8', '涉县');
INSERT INTO `t_pub_district` VALUES ('139', '8', '磁县');
INSERT INTO `t_pub_district` VALUES ('140', '8', '肥乡县');
INSERT INTO `t_pub_district` VALUES ('141', '8', '永年县');
INSERT INTO `t_pub_district` VALUES ('142', '8', '邱县');
INSERT INTO `t_pub_district` VALUES ('143', '8', '鸡泽县');
INSERT INTO `t_pub_district` VALUES ('144', '8', '广平县');
INSERT INTO `t_pub_district` VALUES ('145', '8', '馆陶县');
INSERT INTO `t_pub_district` VALUES ('146', '8', '曲周县');
INSERT INTO `t_pub_district` VALUES ('147', '8', '武安市');
INSERT INTO `t_pub_district` VALUES ('148', '8', '魏县');
INSERT INTO `t_pub_district` VALUES ('149', '9', '桥东区');
INSERT INTO `t_pub_district` VALUES ('150', '9', '桥西区');
INSERT INTO `t_pub_district` VALUES ('151', '9', '邢台县');
INSERT INTO `t_pub_district` VALUES ('152', '9', '临城县');
INSERT INTO `t_pub_district` VALUES ('153', '9', '内丘县');
INSERT INTO `t_pub_district` VALUES ('154', '9', '柏乡县');
INSERT INTO `t_pub_district` VALUES ('155', '9', '隆尧县');
INSERT INTO `t_pub_district` VALUES ('156', '9', '任县');
INSERT INTO `t_pub_district` VALUES ('157', '9', '南和县');
INSERT INTO `t_pub_district` VALUES ('158', '9', '巨鹿县');
INSERT INTO `t_pub_district` VALUES ('159', '9', '新和县');
INSERT INTO `t_pub_district` VALUES ('160', '9', '广宗县');
INSERT INTO `t_pub_district` VALUES ('161', '9', '平乡县');
INSERT INTO `t_pub_district` VALUES ('162', '9', '威县');
INSERT INTO `t_pub_district` VALUES ('163', '9', '清河县');
INSERT INTO `t_pub_district` VALUES ('164', '9', '临西县');
INSERT INTO `t_pub_district` VALUES ('165', '9', '南宫市');
INSERT INTO `t_pub_district` VALUES ('166', '9', '沙河市');
INSERT INTO `t_pub_district` VALUES ('167', '9', '宁晋县');
INSERT INTO `t_pub_district` VALUES ('168', '10', '竞秀区');
INSERT INTO `t_pub_district` VALUES ('169', '10', '莲池区');
INSERT INTO `t_pub_district` VALUES ('170', '10', '满城区');
INSERT INTO `t_pub_district` VALUES ('171', '10', '清苑区');
INSERT INTO `t_pub_district` VALUES ('172', '10', '徐水区');
INSERT INTO `t_pub_district` VALUES ('173', '10', '涞水县');
INSERT INTO `t_pub_district` VALUES ('174', '10', '阜平县');
INSERT INTO `t_pub_district` VALUES ('175', '10', '定兴县');
INSERT INTO `t_pub_district` VALUES ('176', '10', '唐县');
INSERT INTO `t_pub_district` VALUES ('177', '10', '高阳县');
INSERT INTO `t_pub_district` VALUES ('178', '10', '容城县');
INSERT INTO `t_pub_district` VALUES ('179', '10', '涞源县');
INSERT INTO `t_pub_district` VALUES ('180', '10', '望都县');
INSERT INTO `t_pub_district` VALUES ('181', '10', '安新县');
INSERT INTO `t_pub_district` VALUES ('182', '10', '易县');
INSERT INTO `t_pub_district` VALUES ('183', '10', '曲阳县');
INSERT INTO `t_pub_district` VALUES ('184', '10', '蠡县');
INSERT INTO `t_pub_district` VALUES ('185', '10', '顺平县');
INSERT INTO `t_pub_district` VALUES ('186', '10', '博野县');
INSERT INTO `t_pub_district` VALUES ('187', '10', '雄县');
INSERT INTO `t_pub_district` VALUES ('188', '10', '安国市');
INSERT INTO `t_pub_district` VALUES ('189', '10', '高碑店市');
INSERT INTO `t_pub_district` VALUES ('190', '10', '定州市');
INSERT INTO `t_pub_district` VALUES ('191', '10', '涿州市');
INSERT INTO `t_pub_district` VALUES ('192', '11', '桥东区');
INSERT INTO `t_pub_district` VALUES ('193', '11', '桥西区');
INSERT INTO `t_pub_district` VALUES ('194', '11', '宣化区');
INSERT INTO `t_pub_district` VALUES ('195', '11', '下花园区');
INSERT INTO `t_pub_district` VALUES ('196', '11', '崇礼区');
INSERT INTO `t_pub_district` VALUES ('197', '11', '万全区');
INSERT INTO `t_pub_district` VALUES ('198', '11', '张北县');
INSERT INTO `t_pub_district` VALUES ('199', '11', '康保县');
INSERT INTO `t_pub_district` VALUES ('200', '11', '沽源县');
INSERT INTO `t_pub_district` VALUES ('201', '11', '尚义县');
INSERT INTO `t_pub_district` VALUES ('202', '11', '蔚县');
INSERT INTO `t_pub_district` VALUES ('203', '11', '阳原县');
INSERT INTO `t_pub_district` VALUES ('204', '11', '怀安县');
INSERT INTO `t_pub_district` VALUES ('205', '11', '涿鹿县');
INSERT INTO `t_pub_district` VALUES ('206', '11', '赤城县');
INSERT INTO `t_pub_district` VALUES ('207', '11', '怀来县');
INSERT INTO `t_pub_district` VALUES ('208', '12', '双桥区');
INSERT INTO `t_pub_district` VALUES ('209', '12', '双滦区');
INSERT INTO `t_pub_district` VALUES ('210', '12', '鹰手营子矿区');
INSERT INTO `t_pub_district` VALUES ('211', '12', '承德县');
INSERT INTO `t_pub_district` VALUES ('212', '12', '兴隆县');
INSERT INTO `t_pub_district` VALUES ('213', '12', '滦平县');
INSERT INTO `t_pub_district` VALUES ('214', '12', '隆化县');
INSERT INTO `t_pub_district` VALUES ('215', '12', '丰宁满族自治县');
INSERT INTO `t_pub_district` VALUES ('216', '12', '宽城满族自治县');
INSERT INTO `t_pub_district` VALUES ('217', '12', '围场满族蒙古族自治县');
INSERT INTO `t_pub_district` VALUES ('218', '12', '平泉县');
INSERT INTO `t_pub_district` VALUES ('219', '13', '运河区');
INSERT INTO `t_pub_district` VALUES ('220', '13', '新华区');
INSERT INTO `t_pub_district` VALUES ('221', '13', '沧县');
INSERT INTO `t_pub_district` VALUES ('222', '13', '青县');
INSERT INTO `t_pub_district` VALUES ('223', '13', '东光县');
INSERT INTO `t_pub_district` VALUES ('224', '13', '海兴县');
INSERT INTO `t_pub_district` VALUES ('225', '13', '盐山县');
INSERT INTO `t_pub_district` VALUES ('226', '13', '肃宁县');
INSERT INTO `t_pub_district` VALUES ('227', '13', '南皮县');
INSERT INTO `t_pub_district` VALUES ('228', '13', '吴桥县');
INSERT INTO `t_pub_district` VALUES ('229', '13', '献县');
INSERT INTO `t_pub_district` VALUES ('230', '13', '孟村回族自治县');
INSERT INTO `t_pub_district` VALUES ('231', '13', '泊头市');
INSERT INTO `t_pub_district` VALUES ('232', '13', '黄骅市');
INSERT INTO `t_pub_district` VALUES ('233', '13', '河间市');
INSERT INTO `t_pub_district` VALUES ('234', '13', '任丘市');
INSERT INTO `t_pub_district` VALUES ('235', '14', '广阳区');
INSERT INTO `t_pub_district` VALUES ('236', '14', '安次区');
INSERT INTO `t_pub_district` VALUES ('237', '14', '固安县');
INSERT INTO `t_pub_district` VALUES ('238', '14', '永清县');
INSERT INTO `t_pub_district` VALUES ('239', '14', '香河县');
INSERT INTO `t_pub_district` VALUES ('240', '14', '大城县');
INSERT INTO `t_pub_district` VALUES ('241', '14', '文安县');
INSERT INTO `t_pub_district` VALUES ('242', '14', '大厂回族自治县');
INSERT INTO `t_pub_district` VALUES ('243', '14', '霸州市');
INSERT INTO `t_pub_district` VALUES ('244', '14', '三河市');
INSERT INTO `t_pub_district` VALUES ('245', '15', '桃城区');
INSERT INTO `t_pub_district` VALUES ('246', '15', '枣强县');
INSERT INTO `t_pub_district` VALUES ('247', '15', '武邑县');
INSERT INTO `t_pub_district` VALUES ('248', '15', '武强县');
INSERT INTO `t_pub_district` VALUES ('249', '15', '饶阳县');
INSERT INTO `t_pub_district` VALUES ('250', '15', '安平县');
INSERT INTO `t_pub_district` VALUES ('251', '15', '故城县');
INSERT INTO `t_pub_district` VALUES ('252', '15', '阜城县');
INSERT INTO `t_pub_district` VALUES ('253', '15', '冀州市');
INSERT INTO `t_pub_district` VALUES ('254', '15', '深州市');
INSERT INTO `t_pub_district` VALUES ('255', '15', '景县');
INSERT INTO `t_pub_district` VALUES ('256', '16', '迎泽区');
INSERT INTO `t_pub_district` VALUES ('257', '16', '杏花岭区');
INSERT INTO `t_pub_district` VALUES ('258', '16', '万柏林区');
INSERT INTO `t_pub_district` VALUES ('259', '16', '尖草坪区');
INSERT INTO `t_pub_district` VALUES ('260', '16', '小店区');
INSERT INTO `t_pub_district` VALUES ('261', '16', '晋源区');
INSERT INTO `t_pub_district` VALUES ('262', '16', '清徐县');
INSERT INTO `t_pub_district` VALUES ('263', '16', '阳曲县');
INSERT INTO `t_pub_district` VALUES ('264', '16', '娄烦县');
INSERT INTO `t_pub_district` VALUES ('265', '16', '古交市');
INSERT INTO `t_pub_district` VALUES ('266', '17', '城区');
INSERT INTO `t_pub_district` VALUES ('267', '17', '矿区');
INSERT INTO `t_pub_district` VALUES ('268', '17', '南郊区');
INSERT INTO `t_pub_district` VALUES ('269', '17', '新荣区');
INSERT INTO `t_pub_district` VALUES ('270', '17', '左云县');
INSERT INTO `t_pub_district` VALUES ('271', '17', '大同县');
INSERT INTO `t_pub_district` VALUES ('272', '17', '天镇县');
INSERT INTO `t_pub_district` VALUES ('273', '17', '浑源县');
INSERT INTO `t_pub_district` VALUES ('274', '17', '广灵县');
INSERT INTO `t_pub_district` VALUES ('275', '17', '灵丘县');
INSERT INTO `t_pub_district` VALUES ('276', '17', '阳高县');
INSERT INTO `t_pub_district` VALUES ('277', '18', '城区');
INSERT INTO `t_pub_district` VALUES ('278', '18', '矿区');
INSERT INTO `t_pub_district` VALUES ('279', '18', '郊区');
INSERT INTO `t_pub_district` VALUES ('280', '18', '盂县');
INSERT INTO `t_pub_district` VALUES ('281', '18', '平定县');
INSERT INTO `t_pub_district` VALUES ('282', '19', '城区');
INSERT INTO `t_pub_district` VALUES ('283', '19', '郊区');
INSERT INTO `t_pub_district` VALUES ('284', '19', '长治县');
INSERT INTO `t_pub_district` VALUES ('285', '19', '襄垣县');
INSERT INTO `t_pub_district` VALUES ('286', '19', '屯留县');
INSERT INTO `t_pub_district` VALUES ('287', '19', '平顺县');
INSERT INTO `t_pub_district` VALUES ('288', '19', '黎城县');
INSERT INTO `t_pub_district` VALUES ('289', '19', '壶关县');
INSERT INTO `t_pub_district` VALUES ('290', '19', '长子县');
INSERT INTO `t_pub_district` VALUES ('291', '19', '武乡县');
INSERT INTO `t_pub_district` VALUES ('292', '19', '沁县');
INSERT INTO `t_pub_district` VALUES ('293', '19', '沁源县');
INSERT INTO `t_pub_district` VALUES ('294', '19', '潞城市');
INSERT INTO `t_pub_district` VALUES ('295', '20', '城区');
INSERT INTO `t_pub_district` VALUES ('296', '20', '泽州县');
INSERT INTO `t_pub_district` VALUES ('297', '20', '阳城县');
INSERT INTO `t_pub_district` VALUES ('298', '20', '陵川县');
INSERT INTO `t_pub_district` VALUES ('299', '20', '沁水县');
INSERT INTO `t_pub_district` VALUES ('300', '20', '高平市');
INSERT INTO `t_pub_district` VALUES ('301', '21', '朔城区');
INSERT INTO `t_pub_district` VALUES ('302', '21', '平鲁区');
INSERT INTO `t_pub_district` VALUES ('303', '21', '山阴县');
INSERT INTO `t_pub_district` VALUES ('304', '21', '应县');
INSERT INTO `t_pub_district` VALUES ('305', '21', '怀仁县');
INSERT INTO `t_pub_district` VALUES ('306', '21', '右玉县');
INSERT INTO `t_pub_district` VALUES ('307', '22', '榆次区');
INSERT INTO `t_pub_district` VALUES ('308', '22', '榆社县');
INSERT INTO `t_pub_district` VALUES ('309', '22', '左权县');
INSERT INTO `t_pub_district` VALUES ('310', '22', '和顺县');
INSERT INTO `t_pub_district` VALUES ('311', '22', '昔阳县');
INSERT INTO `t_pub_district` VALUES ('312', '22', '寿阳县');
INSERT INTO `t_pub_district` VALUES ('313', '22', '太谷县');
INSERT INTO `t_pub_district` VALUES ('314', '22', '祁县');
INSERT INTO `t_pub_district` VALUES ('315', '22', '平遥县');
INSERT INTO `t_pub_district` VALUES ('316', '22', '灵石县');
INSERT INTO `t_pub_district` VALUES ('317', '22', '介休市');
INSERT INTO `t_pub_district` VALUES ('318', '23', '盐湖区');
INSERT INTO `t_pub_district` VALUES ('319', '23', '临猗县');
INSERT INTO `t_pub_district` VALUES ('320', '23', '芮城县');
INSERT INTO `t_pub_district` VALUES ('321', '23', '万荣县');
INSERT INTO `t_pub_district` VALUES ('322', '23', '新绛县');
INSERT INTO `t_pub_district` VALUES ('323', '23', '稷山县');
INSERT INTO `t_pub_district` VALUES ('324', '23', '闻喜县');
INSERT INTO `t_pub_district` VALUES ('325', '23', '夏县');
INSERT INTO `t_pub_district` VALUES ('326', '23', '绛县');
INSERT INTO `t_pub_district` VALUES ('327', '23', '平陆县');
INSERT INTO `t_pub_district` VALUES ('328', '23', '垣曲县');
INSERT INTO `t_pub_district` VALUES ('329', '23', '河津市');
INSERT INTO `t_pub_district` VALUES ('330', '23', '永济市');
INSERT INTO `t_pub_district` VALUES ('331', '24', '忻府区');
INSERT INTO `t_pub_district` VALUES ('332', '24', '定襄县');
INSERT INTO `t_pub_district` VALUES ('333', '24', '五台县');
INSERT INTO `t_pub_district` VALUES ('334', '24', '代县');
INSERT INTO `t_pub_district` VALUES ('335', '24', '繁峙县');
INSERT INTO `t_pub_district` VALUES ('336', '24', '宁武县');
INSERT INTO `t_pub_district` VALUES ('337', '24', '静乐县');
INSERT INTO `t_pub_district` VALUES ('338', '24', '神池县');
INSERT INTO `t_pub_district` VALUES ('339', '24', '五寨县');
INSERT INTO `t_pub_district` VALUES ('340', '24', '岢岚县');
INSERT INTO `t_pub_district` VALUES ('341', '24', '偏关县');
INSERT INTO `t_pub_district` VALUES ('342', '24', '河曲县');
INSERT INTO `t_pub_district` VALUES ('343', '24', '保德县');
INSERT INTO `t_pub_district` VALUES ('344', '24', '原平市');
INSERT INTO `t_pub_district` VALUES ('345', '25', '尧都区');
INSERT INTO `t_pub_district` VALUES ('346', '25', '曲沃县');
INSERT INTO `t_pub_district` VALUES ('347', '25', '翼城县');
INSERT INTO `t_pub_district` VALUES ('348', '25', '襄汾县');
INSERT INTO `t_pub_district` VALUES ('349', '25', '洪洞县');
INSERT INTO `t_pub_district` VALUES ('350', '25', '古县');
INSERT INTO `t_pub_district` VALUES ('351', '25', '浮山县');
INSERT INTO `t_pub_district` VALUES ('352', '25', '吉县');
INSERT INTO `t_pub_district` VALUES ('353', '25', '乡宁县');
INSERT INTO `t_pub_district` VALUES ('354', '25', '蒲县');
INSERT INTO `t_pub_district` VALUES ('355', '25', '大宁县');
INSERT INTO `t_pub_district` VALUES ('356', '25', '永和县');
INSERT INTO `t_pub_district` VALUES ('357', '25', '汾西县');
INSERT INTO `t_pub_district` VALUES ('358', '25', '隰县');
INSERT INTO `t_pub_district` VALUES ('359', '25', '安泽县');
INSERT INTO `t_pub_district` VALUES ('360', '25', '侯马市');
INSERT INTO `t_pub_district` VALUES ('361', '25', '霍州市');
INSERT INTO `t_pub_district` VALUES ('362', '26', '离石区');
INSERT INTO `t_pub_district` VALUES ('363', '26', '文水县');
INSERT INTO `t_pub_district` VALUES ('364', '26', '交城县');
INSERT INTO `t_pub_district` VALUES ('365', '26', '兴县');
INSERT INTO `t_pub_district` VALUES ('366', '26', '临县');
INSERT INTO `t_pub_district` VALUES ('367', '26', '柳林县');
INSERT INTO `t_pub_district` VALUES ('368', '26', '岚县');
INSERT INTO `t_pub_district` VALUES ('369', '26', '石楼县');
INSERT INTO `t_pub_district` VALUES ('370', '26', '交口县');
INSERT INTO `t_pub_district` VALUES ('371', '26', '方山县');
INSERT INTO `t_pub_district` VALUES ('372', '26', '中阳县');
INSERT INTO `t_pub_district` VALUES ('373', '26', '孝义市');
INSERT INTO `t_pub_district` VALUES ('374', '26', '汾阳市');
INSERT INTO `t_pub_district` VALUES ('375', '27', '和平区');
INSERT INTO `t_pub_district` VALUES ('376', '27', '沈河区');
INSERT INTO `t_pub_district` VALUES ('377', '27', '大东区');
INSERT INTO `t_pub_district` VALUES ('378', '27', '皇姑区');
INSERT INTO `t_pub_district` VALUES ('379', '27', '铁西区');
INSERT INTO `t_pub_district` VALUES ('380', '27', '苏家屯区');
INSERT INTO `t_pub_district` VALUES ('381', '27', '浑南区');
INSERT INTO `t_pub_district` VALUES ('382', '27', '沈北新区');
INSERT INTO `t_pub_district` VALUES ('383', '27', '于洪区');
INSERT INTO `t_pub_district` VALUES ('384', '27', '新民市');
INSERT INTO `t_pub_district` VALUES ('385', '27', '辽中区');
INSERT INTO `t_pub_district` VALUES ('386', '27', '康平县');
INSERT INTO `t_pub_district` VALUES ('387', '27', '法库县');
INSERT INTO `t_pub_district` VALUES ('388', '28', '西岗区');
INSERT INTO `t_pub_district` VALUES ('389', '28', '中山区');
INSERT INTO `t_pub_district` VALUES ('390', '28', '沙河口区');
INSERT INTO `t_pub_district` VALUES ('391', '28', '甘井子区');
INSERT INTO `t_pub_district` VALUES ('392', '28', '旅顺口区');
INSERT INTO `t_pub_district` VALUES ('393', '28', '金州区');
INSERT INTO `t_pub_district` VALUES ('394', '28', '普兰店区');
INSERT INTO `t_pub_district` VALUES ('395', '28', '瓦房店市');
INSERT INTO `t_pub_district` VALUES ('396', '28', '庄河市');
INSERT INTO `t_pub_district` VALUES ('397', '28', '长海县');
INSERT INTO `t_pub_district` VALUES ('398', '29', '铁东区');
INSERT INTO `t_pub_district` VALUES ('399', '29', '铁西区');
INSERT INTO `t_pub_district` VALUES ('400', '29', '立山区');
INSERT INTO `t_pub_district` VALUES ('401', '29', '千山区');
INSERT INTO `t_pub_district` VALUES ('402', '29', '海城市');
INSERT INTO `t_pub_district` VALUES ('403', '29', '台安县');
INSERT INTO `t_pub_district` VALUES ('404', '29', '岫岩满族自治县');
INSERT INTO `t_pub_district` VALUES ('405', '30', '新抚区');
INSERT INTO `t_pub_district` VALUES ('406', '30', '东洲区');
INSERT INTO `t_pub_district` VALUES ('407', '30', '望花区');
INSERT INTO `t_pub_district` VALUES ('408', '30', '顺城区');
INSERT INTO `t_pub_district` VALUES ('409', '30', '抚顺县');
INSERT INTO `t_pub_district` VALUES ('410', '30', '新宾满族自治县');
INSERT INTO `t_pub_district` VALUES ('411', '30', '清原满族自治县');
INSERT INTO `t_pub_district` VALUES ('412', '31', '平山区');
INSERT INTO `t_pub_district` VALUES ('413', '31', '溪湖区');
INSERT INTO `t_pub_district` VALUES ('414', '31', '明山区');
INSERT INTO `t_pub_district` VALUES ('415', '31', '南芬区');
INSERT INTO `t_pub_district` VALUES ('416', '31', '本溪满族自治县');
INSERT INTO `t_pub_district` VALUES ('417', '31', '桓仁满族自治县');
INSERT INTO `t_pub_district` VALUES ('418', '32', '元宝区');
INSERT INTO `t_pub_district` VALUES ('419', '32', '振兴区');
INSERT INTO `t_pub_district` VALUES ('420', '32', '振安区');
INSERT INTO `t_pub_district` VALUES ('421', '32', '东港市');
INSERT INTO `t_pub_district` VALUES ('422', '32', '凤城市');
INSERT INTO `t_pub_district` VALUES ('423', '32', '宽甸满族自治县');
INSERT INTO `t_pub_district` VALUES ('424', '33', '古塔区');
INSERT INTO `t_pub_district` VALUES ('425', '33', '凌河区');
INSERT INTO `t_pub_district` VALUES ('426', '33', '太和区');
INSERT INTO `t_pub_district` VALUES ('427', '33', '松山新区');
INSERT INTO `t_pub_district` VALUES ('428', '33', '凌海市');
INSERT INTO `t_pub_district` VALUES ('429', '33', '北镇市');
INSERT INTO `t_pub_district` VALUES ('430', '33', '黑山县');
INSERT INTO `t_pub_district` VALUES ('431', '33', '义县');
INSERT INTO `t_pub_district` VALUES ('432', '34', '站前区');
INSERT INTO `t_pub_district` VALUES ('433', '34', '西市区');
INSERT INTO `t_pub_district` VALUES ('434', '34', '老边区');
INSERT INTO `t_pub_district` VALUES ('435', '34', '鲅鱼圈区');
INSERT INTO `t_pub_district` VALUES ('436', '34', '大石桥市');
INSERT INTO `t_pub_district` VALUES ('437', '34', '盖州市');
INSERT INTO `t_pub_district` VALUES ('438', '35', '海州区');
INSERT INTO `t_pub_district` VALUES ('439', '35', '太平区');
INSERT INTO `t_pub_district` VALUES ('440', '35', '新邱区');
INSERT INTO `t_pub_district` VALUES ('441', '35', '细河区');
INSERT INTO `t_pub_district` VALUES ('442', '35', '清河门区');
INSERT INTO `t_pub_district` VALUES ('443', '35', '阜新蒙古族自治县');
INSERT INTO `t_pub_district` VALUES ('444', '35', '彰武县');
INSERT INTO `t_pub_district` VALUES ('445', '36', '白塔区');
INSERT INTO `t_pub_district` VALUES ('446', '36', '文圣区');
INSERT INTO `t_pub_district` VALUES ('447', '36', '宏伟区');
INSERT INTO `t_pub_district` VALUES ('448', '36', '弓长岭区');
INSERT INTO `t_pub_district` VALUES ('449', '36', '太子河区');
INSERT INTO `t_pub_district` VALUES ('450', '36', '灯塔市');
INSERT INTO `t_pub_district` VALUES ('451', '36', '辽阳县');
INSERT INTO `t_pub_district` VALUES ('452', '37', '双台子区');
INSERT INTO `t_pub_district` VALUES ('453', '37', '兴隆台区');
INSERT INTO `t_pub_district` VALUES ('454', '37', '盘山县');
INSERT INTO `t_pub_district` VALUES ('455', '37', '大洼县');
INSERT INTO `t_pub_district` VALUES ('456', '38', '银州区');
INSERT INTO `t_pub_district` VALUES ('457', '38', '清河区');
INSERT INTO `t_pub_district` VALUES ('458', '38', '调兵山市');
INSERT INTO `t_pub_district` VALUES ('459', '38', '开原市');
INSERT INTO `t_pub_district` VALUES ('460', '38', '铁岭县');
INSERT INTO `t_pub_district` VALUES ('461', '38', '西丰县');
INSERT INTO `t_pub_district` VALUES ('462', '39', '双塔区');
INSERT INTO `t_pub_district` VALUES ('463', '39', '龙城区');
INSERT INTO `t_pub_district` VALUES ('464', '39', '北票市');
INSERT INTO `t_pub_district` VALUES ('465', '39', '凌源市');
INSERT INTO `t_pub_district` VALUES ('466', '39', '朝阳县');
INSERT INTO `t_pub_district` VALUES ('467', '39', '建平县');
INSERT INTO `t_pub_district` VALUES ('468', '39', '喀喇沁左翼蒙古族自治县');
INSERT INTO `t_pub_district` VALUES ('469', '40', '连山区');
INSERT INTO `t_pub_district` VALUES ('470', '40', '南票区');
INSERT INTO `t_pub_district` VALUES ('471', '40', '龙港区');
INSERT INTO `t_pub_district` VALUES ('472', '40', '兴城市');
INSERT INTO `t_pub_district` VALUES ('473', '40', '建昌县');
INSERT INTO `t_pub_district` VALUES ('474', '41', '昌图县');
INSERT INTO `t_pub_district` VALUES ('475', '42', '绥中县');
INSERT INTO `t_pub_district` VALUES ('476', '43', '南关区');
INSERT INTO `t_pub_district` VALUES ('477', '43', '朝阳区');
INSERT INTO `t_pub_district` VALUES ('478', '43', '绿园区');
INSERT INTO `t_pub_district` VALUES ('479', '43', '二道区');
INSERT INTO `t_pub_district` VALUES ('480', '43', '双阳区');
INSERT INTO `t_pub_district` VALUES ('481', '43', '宽城区');
INSERT INTO `t_pub_district` VALUES ('482', '43', '九台区');
INSERT INTO `t_pub_district` VALUES ('483', '43', '榆树市');
INSERT INTO `t_pub_district` VALUES ('484', '43', '德惠市');
INSERT INTO `t_pub_district` VALUES ('485', '43', '农安县');
INSERT INTO `t_pub_district` VALUES ('486', '44', '船营区');
INSERT INTO `t_pub_district` VALUES ('487', '44', '龙潭区');
INSERT INTO `t_pub_district` VALUES ('488', '44', '昌邑区');
INSERT INTO `t_pub_district` VALUES ('489', '44', '丰满区');
INSERT INTO `t_pub_district` VALUES ('490', '44', '磐石市');
INSERT INTO `t_pub_district` VALUES ('491', '44', '桦甸市');
INSERT INTO `t_pub_district` VALUES ('492', '44', '蛟河市');
INSERT INTO `t_pub_district` VALUES ('493', '44', '舒兰市');
INSERT INTO `t_pub_district` VALUES ('494', '44', '永吉县');
INSERT INTO `t_pub_district` VALUES ('495', '45', '铁西区');
INSERT INTO `t_pub_district` VALUES ('496', '45', '铁东区');
INSERT INTO `t_pub_district` VALUES ('497', '45', '双辽市');
INSERT INTO `t_pub_district` VALUES ('498', '45', '梨树县');
INSERT INTO `t_pub_district` VALUES ('499', '45', '伊通满族自治县');
INSERT INTO `t_pub_district` VALUES ('500', '45', '四平辽河农垦管理区');
INSERT INTO `t_pub_district` VALUES ('501', '46', '龙山区');
INSERT INTO `t_pub_district` VALUES ('502', '46', '西安区');
INSERT INTO `t_pub_district` VALUES ('503', '46', '东丰县');
INSERT INTO `t_pub_district` VALUES ('504', '46', '东辽县');
INSERT INTO `t_pub_district` VALUES ('505', '47', '东昌区');
INSERT INTO `t_pub_district` VALUES ('506', '47', '二道江区');
INSERT INTO `t_pub_district` VALUES ('507', '47', '集安市');
INSERT INTO `t_pub_district` VALUES ('508', '47', '通化县');
INSERT INTO `t_pub_district` VALUES ('509', '47', '辉南县');
INSERT INTO `t_pub_district` VALUES ('510', '47', '柳河县');
INSERT INTO `t_pub_district` VALUES ('511', '48', '浑江区');
INSERT INTO `t_pub_district` VALUES ('512', '48', '江源区');
INSERT INTO `t_pub_district` VALUES ('513', '48', '临江市');
INSERT INTO `t_pub_district` VALUES ('514', '48', '抚松县');
INSERT INTO `t_pub_district` VALUES ('515', '48', '靖宇县');
INSERT INTO `t_pub_district` VALUES ('516', '48', '长白朝鲜族自治县');
INSERT INTO `t_pub_district` VALUES ('517', '49', '洮北区');
INSERT INTO `t_pub_district` VALUES ('518', '49', '洮南市');
INSERT INTO `t_pub_district` VALUES ('519', '49', '大安市');
INSERT INTO `t_pub_district` VALUES ('520', '49', '镇赉县');
INSERT INTO `t_pub_district` VALUES ('521', '49', '通榆县');
INSERT INTO `t_pub_district` VALUES ('522', '50', '宁江区');
INSERT INTO `t_pub_district` VALUES ('523', '50', '扶余市');
INSERT INTO `t_pub_district` VALUES ('524', '50', '乾安县');
INSERT INTO `t_pub_district` VALUES ('525', '50', '长岭县');
INSERT INTO `t_pub_district` VALUES ('526', '50', '前郭尔罗斯蒙古族自治县');
INSERT INTO `t_pub_district` VALUES ('527', '51', '延吉市');
INSERT INTO `t_pub_district` VALUES ('528', '51', '图们市');
INSERT INTO `t_pub_district` VALUES ('529', '51', '敦化市');
INSERT INTO `t_pub_district` VALUES ('530', '51', '和龙市');
INSERT INTO `t_pub_district` VALUES ('531', '51', '珲春市');
INSERT INTO `t_pub_district` VALUES ('532', '51', '龙井市');
INSERT INTO `t_pub_district` VALUES ('533', '51', '汪清县');
INSERT INTO `t_pub_district` VALUES ('534', '51', '安图县');
INSERT INTO `t_pub_district` VALUES ('535', '52', '池北区');
INSERT INTO `t_pub_district` VALUES ('536', '52', '池西区');
INSERT INTO `t_pub_district` VALUES ('537', '52', '池南区');
INSERT INTO `t_pub_district` VALUES ('538', '53', '梅河口市');
INSERT INTO `t_pub_district` VALUES ('539', '54', '公主岭市');
INSERT INTO `t_pub_district` VALUES ('540', '55', '道里区');
INSERT INTO `t_pub_district` VALUES ('541', '55', '南岗区');
INSERT INTO `t_pub_district` VALUES ('542', '55', '道外区');
INSERT INTO `t_pub_district` VALUES ('543', '55', '平房区');
INSERT INTO `t_pub_district` VALUES ('544', '55', '松北区');
INSERT INTO `t_pub_district` VALUES ('545', '55', '香坊区');
INSERT INTO `t_pub_district` VALUES ('546', '55', '呼兰区');
INSERT INTO `t_pub_district` VALUES ('547', '55', '阿城区');
INSERT INTO `t_pub_district` VALUES ('548', '55', '双城区');
INSERT INTO `t_pub_district` VALUES ('549', '55', '依兰县');
INSERT INTO `t_pub_district` VALUES ('550', '55', '方正县');
INSERT INTO `t_pub_district` VALUES ('551', '55', '宾县');
INSERT INTO `t_pub_district` VALUES ('552', '55', '巴彦县');
INSERT INTO `t_pub_district` VALUES ('553', '55', '木兰县');
INSERT INTO `t_pub_district` VALUES ('554', '55', '通河县');
INSERT INTO `t_pub_district` VALUES ('555', '55', '延寿县');
INSERT INTO `t_pub_district` VALUES ('556', '55', '尚志市');
INSERT INTO `t_pub_district` VALUES ('557', '55', '五常市');
INSERT INTO `t_pub_district` VALUES ('558', '56', '龙沙区');
INSERT INTO `t_pub_district` VALUES ('559', '56', '建华区');
INSERT INTO `t_pub_district` VALUES ('560', '56', '铁锋区');
INSERT INTO `t_pub_district` VALUES ('561', '56', '昂昂溪区');
INSERT INTO `t_pub_district` VALUES ('562', '56', '富拉尔基区');
INSERT INTO `t_pub_district` VALUES ('563', '56', '碾子山区');
INSERT INTO `t_pub_district` VALUES ('564', '56', '梅里斯达斡尔族区');
INSERT INTO `t_pub_district` VALUES ('565', '56', '龙江县');
INSERT INTO `t_pub_district` VALUES ('566', '56', '依安县');
INSERT INTO `t_pub_district` VALUES ('567', '56', '泰来县');
INSERT INTO `t_pub_district` VALUES ('568', '56', '甘南县');
INSERT INTO `t_pub_district` VALUES ('569', '56', '富裕县');
INSERT INTO `t_pub_district` VALUES ('570', '56', '克山县');
INSERT INTO `t_pub_district` VALUES ('571', '56', '克东县');
INSERT INTO `t_pub_district` VALUES ('572', '56', '拜泉县');
INSERT INTO `t_pub_district` VALUES ('573', '56', '讷河市');
INSERT INTO `t_pub_district` VALUES ('574', '57', '鸡冠区');
INSERT INTO `t_pub_district` VALUES ('575', '57', '恒山区');
INSERT INTO `t_pub_district` VALUES ('576', '57', '滴道区');
INSERT INTO `t_pub_district` VALUES ('577', '57', '梨树区');
INSERT INTO `t_pub_district` VALUES ('578', '57', '城子河区');
INSERT INTO `t_pub_district` VALUES ('579', '57', '麻山区');
INSERT INTO `t_pub_district` VALUES ('580', '57', '鸡东县');
INSERT INTO `t_pub_district` VALUES ('581', '57', '虎林市');
INSERT INTO `t_pub_district` VALUES ('582', '57', '密山市');
INSERT INTO `t_pub_district` VALUES ('583', '58', '向阳区');
INSERT INTO `t_pub_district` VALUES ('584', '58', '工农区');
INSERT INTO `t_pub_district` VALUES ('585', '58', '南山区');
INSERT INTO `t_pub_district` VALUES ('586', '58', '兴安区');
INSERT INTO `t_pub_district` VALUES ('587', '58', '东山区');
INSERT INTO `t_pub_district` VALUES ('588', '58', '兴山区');
INSERT INTO `t_pub_district` VALUES ('589', '58', '萝北县');
INSERT INTO `t_pub_district` VALUES ('590', '58', '绥滨县');
INSERT INTO `t_pub_district` VALUES ('591', '59', '尖山区');
INSERT INTO `t_pub_district` VALUES ('592', '59', '岭东区');
INSERT INTO `t_pub_district` VALUES ('593', '59', '四方台区');
INSERT INTO `t_pub_district` VALUES ('594', '59', '宝山区');
INSERT INTO `t_pub_district` VALUES ('595', '59', '集贤县');
INSERT INTO `t_pub_district` VALUES ('596', '59', '友谊县');
INSERT INTO `t_pub_district` VALUES ('597', '59', '宝清县');
INSERT INTO `t_pub_district` VALUES ('598', '59', '饶河县');
INSERT INTO `t_pub_district` VALUES ('599', '60', '萨尔图区');
INSERT INTO `t_pub_district` VALUES ('600', '60', '龙凤区');
INSERT INTO `t_pub_district` VALUES ('601', '60', '让胡路区');
INSERT INTO `t_pub_district` VALUES ('602', '60', '红岗区');
INSERT INTO `t_pub_district` VALUES ('603', '60', '大同区');
INSERT INTO `t_pub_district` VALUES ('604', '60', '肇州县');
INSERT INTO `t_pub_district` VALUES ('605', '60', '肇源县');
INSERT INTO `t_pub_district` VALUES ('606', '60', '林甸县');
INSERT INTO `t_pub_district` VALUES ('607', '60', '杜尔伯特蒙古族自治县');
INSERT INTO `t_pub_district` VALUES ('608', '61', '伊春区');
INSERT INTO `t_pub_district` VALUES ('609', '61', '南岔区');
INSERT INTO `t_pub_district` VALUES ('610', '61', '友好区');
INSERT INTO `t_pub_district` VALUES ('611', '61', '西林区');
INSERT INTO `t_pub_district` VALUES ('612', '61', '翠峦区');
INSERT INTO `t_pub_district` VALUES ('613', '61', '新青区');
INSERT INTO `t_pub_district` VALUES ('614', '61', '美溪区');
INSERT INTO `t_pub_district` VALUES ('615', '61', '金山屯区');
INSERT INTO `t_pub_district` VALUES ('616', '61', '五营区');
INSERT INTO `t_pub_district` VALUES ('617', '61', '乌马河区');
INSERT INTO `t_pub_district` VALUES ('618', '61', '汤旺河区');
INSERT INTO `t_pub_district` VALUES ('619', '61', '带岭区');
INSERT INTO `t_pub_district` VALUES ('620', '61', '乌伊岭区');
INSERT INTO `t_pub_district` VALUES ('621', '61', '红星区');
INSERT INTO `t_pub_district` VALUES ('622', '61', '上甘岭区');
INSERT INTO `t_pub_district` VALUES ('623', '61', '嘉荫县');
INSERT INTO `t_pub_district` VALUES ('624', '61', '铁力市');
INSERT INTO `t_pub_district` VALUES ('625', '62', '向阳区');
INSERT INTO `t_pub_district` VALUES ('626', '62', '前进区');
INSERT INTO `t_pub_district` VALUES ('627', '62', '东风区');
INSERT INTO `t_pub_district` VALUES ('628', '62', '郊区');
INSERT INTO `t_pub_district` VALUES ('629', '62', '桦南县');
INSERT INTO `t_pub_district` VALUES ('630', '62', '桦川县');
INSERT INTO `t_pub_district` VALUES ('631', '62', '汤原县');
INSERT INTO `t_pub_district` VALUES ('632', '62', '同江市');
INSERT INTO `t_pub_district` VALUES ('633', '62', '富锦市');
INSERT INTO `t_pub_district` VALUES ('634', '62', '抚远市');
INSERT INTO `t_pub_district` VALUES ('635', '63', '新兴区');
INSERT INTO `t_pub_district` VALUES ('636', '63', '桃山区');
INSERT INTO `t_pub_district` VALUES ('637', '63', '茄子河区');
INSERT INTO `t_pub_district` VALUES ('638', '63', '勃利县');
INSERT INTO `t_pub_district` VALUES ('639', '64', '东安区');
INSERT INTO `t_pub_district` VALUES ('640', '64', '阳明区');
INSERT INTO `t_pub_district` VALUES ('641', '64', '爱民区');
INSERT INTO `t_pub_district` VALUES ('642', '64', '西安区');
INSERT INTO `t_pub_district` VALUES ('643', '64', '林口县');
INSERT INTO `t_pub_district` VALUES ('644', '64', '绥芬河市');
INSERT INTO `t_pub_district` VALUES ('645', '64', '海林市');
INSERT INTO `t_pub_district` VALUES ('646', '64', '宁安市');
INSERT INTO `t_pub_district` VALUES ('647', '64', '穆棱市');
INSERT INTO `t_pub_district` VALUES ('648', '64', '东宁市');
INSERT INTO `t_pub_district` VALUES ('649', '65', '爱辉区');
INSERT INTO `t_pub_district` VALUES ('650', '65', '嫩江县');
INSERT INTO `t_pub_district` VALUES ('651', '65', '逊克县');
INSERT INTO `t_pub_district` VALUES ('652', '65', '孙吴县');
INSERT INTO `t_pub_district` VALUES ('653', '65', '北安市');
INSERT INTO `t_pub_district` VALUES ('654', '65', '五大连池市');
INSERT INTO `t_pub_district` VALUES ('655', '66', '北林区');
INSERT INTO `t_pub_district` VALUES ('656', '66', '望奎县');
INSERT INTO `t_pub_district` VALUES ('657', '66', '兰西县');
INSERT INTO `t_pub_district` VALUES ('658', '66', '青冈县');
INSERT INTO `t_pub_district` VALUES ('659', '66', '庆安县');
INSERT INTO `t_pub_district` VALUES ('660', '66', '明水县');
INSERT INTO `t_pub_district` VALUES ('661', '66', '绥棱县');
INSERT INTO `t_pub_district` VALUES ('662', '66', '安达市');
INSERT INTO `t_pub_district` VALUES ('663', '66', '肇东市');
INSERT INTO `t_pub_district` VALUES ('664', '66', '海伦市');
INSERT INTO `t_pub_district` VALUES ('665', '67', '加格达奇区');
INSERT INTO `t_pub_district` VALUES ('666', '67', '松岭区');
INSERT INTO `t_pub_district` VALUES ('667', '67', '新林区');
INSERT INTO `t_pub_district` VALUES ('668', '67', '呼中区');
INSERT INTO `t_pub_district` VALUES ('669', '67', '呼玛县');
INSERT INTO `t_pub_district` VALUES ('670', '67', '塔河县');
INSERT INTO `t_pub_district` VALUES ('671', '67', '漠河县');
INSERT INTO `t_pub_district` VALUES ('672', '68', '玄武区');
INSERT INTO `t_pub_district` VALUES ('673', '68', '秦淮区');
INSERT INTO `t_pub_district` VALUES ('674', '68', '鼓楼区');
INSERT INTO `t_pub_district` VALUES ('675', '68', '建邺区');
INSERT INTO `t_pub_district` VALUES ('676', '68', '栖霞区');
INSERT INTO `t_pub_district` VALUES ('677', '68', '雨花台区');
INSERT INTO `t_pub_district` VALUES ('678', '68', '江宁区');
INSERT INTO `t_pub_district` VALUES ('679', '68', '浦口区');
INSERT INTO `t_pub_district` VALUES ('680', '68', '六合区');
INSERT INTO `t_pub_district` VALUES ('681', '68', '溧水区');
INSERT INTO `t_pub_district` VALUES ('682', '68', '高淳区');
INSERT INTO `t_pub_district` VALUES ('683', '69', '新吴区');
INSERT INTO `t_pub_district` VALUES ('684', '69', '梁溪区');
INSERT INTO `t_pub_district` VALUES ('685', '69', '锡山区');
INSERT INTO `t_pub_district` VALUES ('686', '69', '惠山区');
INSERT INTO `t_pub_district` VALUES ('687', '69', '滨湖区');
INSERT INTO `t_pub_district` VALUES ('688', '69', '江阴市');
INSERT INTO `t_pub_district` VALUES ('689', '69', '宜兴市');
INSERT INTO `t_pub_district` VALUES ('690', '70', '云龙区');
INSERT INTO `t_pub_district` VALUES ('691', '70', '鼓楼区');
INSERT INTO `t_pub_district` VALUES ('692', '70', '贾汪区');
INSERT INTO `t_pub_district` VALUES ('693', '70', '泉山区');
INSERT INTO `t_pub_district` VALUES ('694', '70', '铜山区');
INSERT INTO `t_pub_district` VALUES ('695', '70', '丰县');
INSERT INTO `t_pub_district` VALUES ('696', '70', '沛县');
INSERT INTO `t_pub_district` VALUES ('697', '70', '睢宁县');
INSERT INTO `t_pub_district` VALUES ('698', '70', '邳州市');
INSERT INTO `t_pub_district` VALUES ('699', '70', '新沂市');
INSERT INTO `t_pub_district` VALUES ('700', '71', '天宁区');
INSERT INTO `t_pub_district` VALUES ('701', '71', '钟楼区');
INSERT INTO `t_pub_district` VALUES ('702', '71', '新北区');
INSERT INTO `t_pub_district` VALUES ('703', '71', '武进区');
INSERT INTO `t_pub_district` VALUES ('704', '71', '金坛区');
INSERT INTO `t_pub_district` VALUES ('705', '71', '溧阳市');
INSERT INTO `t_pub_district` VALUES ('706', '72', '姑苏区');
INSERT INTO `t_pub_district` VALUES ('707', '72', '虎丘区');
INSERT INTO `t_pub_district` VALUES ('708', '72', '吴中区');
INSERT INTO `t_pub_district` VALUES ('709', '72', '相城区');
INSERT INTO `t_pub_district` VALUES ('710', '72', '吴江区');
INSERT INTO `t_pub_district` VALUES ('711', '72', '张家港市');
INSERT INTO `t_pub_district` VALUES ('712', '72', '昆山市');
INSERT INTO `t_pub_district` VALUES ('713', '72', '太仓市');
INSERT INTO `t_pub_district` VALUES ('714', '72', '常熟市');
INSERT INTO `t_pub_district` VALUES ('715', '73', '崇川区');
INSERT INTO `t_pub_district` VALUES ('716', '73', '港闸区');
INSERT INTO `t_pub_district` VALUES ('717', '73', '通州区');
INSERT INTO `t_pub_district` VALUES ('718', '73', '海安县');
INSERT INTO `t_pub_district` VALUES ('719', '73', '如东县');
INSERT INTO `t_pub_district` VALUES ('720', '73', '如皋市');
INSERT INTO `t_pub_district` VALUES ('721', '73', '海门市');
INSERT INTO `t_pub_district` VALUES ('722', '73', '启东市');
INSERT INTO `t_pub_district` VALUES ('723', '74', '连云区');
INSERT INTO `t_pub_district` VALUES ('724', '74', '海州区');
INSERT INTO `t_pub_district` VALUES ('725', '74', '赣榆区');
INSERT INTO `t_pub_district` VALUES ('726', '74', '东海县');
INSERT INTO `t_pub_district` VALUES ('727', '74', '灌云县');
INSERT INTO `t_pub_district` VALUES ('728', '74', '灌南县');
INSERT INTO `t_pub_district` VALUES ('729', '75', '清河区');
INSERT INTO `t_pub_district` VALUES ('730', '75', '清浦区');
INSERT INTO `t_pub_district` VALUES ('731', '75', '淮安区');
INSERT INTO `t_pub_district` VALUES ('732', '75', '淮阴区');
INSERT INTO `t_pub_district` VALUES ('733', '75', '涟水县');
INSERT INTO `t_pub_district` VALUES ('734', '75', '洪泽县');
INSERT INTO `t_pub_district` VALUES ('735', '75', '盱眙县');
INSERT INTO `t_pub_district` VALUES ('736', '75', '金湖县');
INSERT INTO `t_pub_district` VALUES ('737', '76', '亭湖区');
INSERT INTO `t_pub_district` VALUES ('738', '76', '盐都区');
INSERT INTO `t_pub_district` VALUES ('739', '76', '大丰区');
INSERT INTO `t_pub_district` VALUES ('740', '76', '响水县');
INSERT INTO `t_pub_district` VALUES ('741', '76', '滨海县');
INSERT INTO `t_pub_district` VALUES ('742', '76', '阜宁县');
INSERT INTO `t_pub_district` VALUES ('743', '76', '射阳县');
INSERT INTO `t_pub_district` VALUES ('744', '76', '建湖县');
INSERT INTO `t_pub_district` VALUES ('745', '76', '东台市');
INSERT INTO `t_pub_district` VALUES ('746', '77', '广陵区');
INSERT INTO `t_pub_district` VALUES ('747', '77', '邗江区');
INSERT INTO `t_pub_district` VALUES ('748', '77', '江都区');
INSERT INTO `t_pub_district` VALUES ('749', '77', '宝应县');
INSERT INTO `t_pub_district` VALUES ('750', '77', '仪征市');
INSERT INTO `t_pub_district` VALUES ('751', '77', '高邮市');
INSERT INTO `t_pub_district` VALUES ('752', '78', '京口区');
INSERT INTO `t_pub_district` VALUES ('753', '78', '润州区');
INSERT INTO `t_pub_district` VALUES ('754', '78', '丹徒区');
INSERT INTO `t_pub_district` VALUES ('755', '78', '丹阳市');
INSERT INTO `t_pub_district` VALUES ('756', '78', '扬中市');
INSERT INTO `t_pub_district` VALUES ('757', '78', '句容市');
INSERT INTO `t_pub_district` VALUES ('758', '79', '海陵区');
INSERT INTO `t_pub_district` VALUES ('759', '79', '高港区');
INSERT INTO `t_pub_district` VALUES ('760', '79', '姜堰区');
INSERT INTO `t_pub_district` VALUES ('761', '79', '兴化市');
INSERT INTO `t_pub_district` VALUES ('762', '79', '靖江市');
INSERT INTO `t_pub_district` VALUES ('763', '79', '泰兴市');
INSERT INTO `t_pub_district` VALUES ('764', '80', '宿城区');
INSERT INTO `t_pub_district` VALUES ('765', '80', '宿豫区');
INSERT INTO `t_pub_district` VALUES ('766', '80', '沭阳县');
INSERT INTO `t_pub_district` VALUES ('767', '80', '泗阳县');
INSERT INTO `t_pub_district` VALUES ('768', '80', '泗洪县');
INSERT INTO `t_pub_district` VALUES ('769', '81', '上城区');
INSERT INTO `t_pub_district` VALUES ('770', '81', '下城区');
INSERT INTO `t_pub_district` VALUES ('771', '81', '江干区');
INSERT INTO `t_pub_district` VALUES ('772', '81', '拱墅区');
INSERT INTO `t_pub_district` VALUES ('773', '81', '西湖区');
INSERT INTO `t_pub_district` VALUES ('774', '81', '滨江区');
INSERT INTO `t_pub_district` VALUES ('775', '81', '余杭区');
INSERT INTO `t_pub_district` VALUES ('776', '81', '萧山区');
INSERT INTO `t_pub_district` VALUES ('777', '81', '富阳区');
INSERT INTO `t_pub_district` VALUES ('778', '81', '建德市');
INSERT INTO `t_pub_district` VALUES ('779', '81', '临安市');
INSERT INTO `t_pub_district` VALUES ('780', '81', '桐庐县');
INSERT INTO `t_pub_district` VALUES ('781', '81', '淳安县');
INSERT INTO `t_pub_district` VALUES ('782', '82', '海曙区');
INSERT INTO `t_pub_district` VALUES ('783', '82', '江东区');
INSERT INTO `t_pub_district` VALUES ('784', '82', '江北区');
INSERT INTO `t_pub_district` VALUES ('785', '82', '北仑区');
INSERT INTO `t_pub_district` VALUES ('786', '82', '镇海区');
INSERT INTO `t_pub_district` VALUES ('787', '82', '鄞州区');
INSERT INTO `t_pub_district` VALUES ('788', '82', '余姚市');
INSERT INTO `t_pub_district` VALUES ('789', '82', '慈溪市');
INSERT INTO `t_pub_district` VALUES ('790', '82', '奉化市');
INSERT INTO `t_pub_district` VALUES ('791', '82', '象山县');
INSERT INTO `t_pub_district` VALUES ('792', '82', '宁海县');
INSERT INTO `t_pub_district` VALUES ('793', '83', '鹿城区');
INSERT INTO `t_pub_district` VALUES ('794', '83', '龙湾区');
INSERT INTO `t_pub_district` VALUES ('795', '83', '瓯海区');
INSERT INTO `t_pub_district` VALUES ('796', '83', '洞头区');
INSERT INTO `t_pub_district` VALUES ('797', '83', '瑞安市');
INSERT INTO `t_pub_district` VALUES ('798', '83', '乐清市');
INSERT INTO `t_pub_district` VALUES ('799', '83', '永嘉县');
INSERT INTO `t_pub_district` VALUES ('800', '83', '平阳县');
INSERT INTO `t_pub_district` VALUES ('801', '83', '苍南县');
INSERT INTO `t_pub_district` VALUES ('802', '83', '文成县');
INSERT INTO `t_pub_district` VALUES ('803', '83', '泰顺县');
INSERT INTO `t_pub_district` VALUES ('804', '84', '越城区');
INSERT INTO `t_pub_district` VALUES ('805', '84', '柯桥区');
INSERT INTO `t_pub_district` VALUES ('806', '84', '上虞区');
INSERT INTO `t_pub_district` VALUES ('807', '84', '诸暨市');
INSERT INTO `t_pub_district` VALUES ('808', '84', '嵊州市');
INSERT INTO `t_pub_district` VALUES ('809', '84', '新昌县');
INSERT INTO `t_pub_district` VALUES ('810', '85', '吴兴区');
INSERT INTO `t_pub_district` VALUES ('811', '85', '南浔区');
INSERT INTO `t_pub_district` VALUES ('812', '85', '德清县');
INSERT INTO `t_pub_district` VALUES ('813', '85', '长兴县');
INSERT INTO `t_pub_district` VALUES ('814', '85', '安吉县');
INSERT INTO `t_pub_district` VALUES ('815', '86', '南湖区');
INSERT INTO `t_pub_district` VALUES ('816', '86', '秀洲区');
INSERT INTO `t_pub_district` VALUES ('817', '86', '海宁市');
INSERT INTO `t_pub_district` VALUES ('818', '86', '平湖市');
INSERT INTO `t_pub_district` VALUES ('819', '86', '桐乡市');
INSERT INTO `t_pub_district` VALUES ('820', '86', '嘉善县');
INSERT INTO `t_pub_district` VALUES ('821', '86', '海盐县');
INSERT INTO `t_pub_district` VALUES ('822', '87', '婺城区');
INSERT INTO `t_pub_district` VALUES ('823', '87', '金东区');
INSERT INTO `t_pub_district` VALUES ('824', '87', '兰溪市');
INSERT INTO `t_pub_district` VALUES ('825', '87', '东阳市');
INSERT INTO `t_pub_district` VALUES ('826', '87', '永康市');
INSERT INTO `t_pub_district` VALUES ('827', '87', '武义县');
INSERT INTO `t_pub_district` VALUES ('828', '87', '浦江县');
INSERT INTO `t_pub_district` VALUES ('829', '87', '磐安县');
INSERT INTO `t_pub_district` VALUES ('830', '88', '柯城区');
INSERT INTO `t_pub_district` VALUES ('831', '88', '衢江区');
INSERT INTO `t_pub_district` VALUES ('832', '88', '江山市');
INSERT INTO `t_pub_district` VALUES ('833', '88', '常山县');
INSERT INTO `t_pub_district` VALUES ('834', '88', '开化县');
INSERT INTO `t_pub_district` VALUES ('835', '88', '龙游县');
INSERT INTO `t_pub_district` VALUES ('836', '89', '椒江区');
INSERT INTO `t_pub_district` VALUES ('837', '89', '黄岩区');
INSERT INTO `t_pub_district` VALUES ('838', '89', '路桥区');
INSERT INTO `t_pub_district` VALUES ('839', '89', '临海市');
INSERT INTO `t_pub_district` VALUES ('840', '89', '温岭市');
INSERT INTO `t_pub_district` VALUES ('841', '89', '玉环县');
INSERT INTO `t_pub_district` VALUES ('842', '89', '三门县');
INSERT INTO `t_pub_district` VALUES ('843', '89', '天台县');
INSERT INTO `t_pub_district` VALUES ('844', '89', '仙居县');
INSERT INTO `t_pub_district` VALUES ('845', '90', '莲都区');
INSERT INTO `t_pub_district` VALUES ('846', '90', '龙泉市');
INSERT INTO `t_pub_district` VALUES ('847', '90', '青田县');
INSERT INTO `t_pub_district` VALUES ('848', '90', '缙云县');
INSERT INTO `t_pub_district` VALUES ('849', '90', '遂昌县');
INSERT INTO `t_pub_district` VALUES ('850', '90', '松阳县');
INSERT INTO `t_pub_district` VALUES ('851', '90', '云和县');
INSERT INTO `t_pub_district` VALUES ('852', '90', '庆元县');
INSERT INTO `t_pub_district` VALUES ('853', '90', '景宁畲族自治县');
INSERT INTO `t_pub_district` VALUES ('854', '91', '定海区');
INSERT INTO `t_pub_district` VALUES ('855', '91', '普陀区');
INSERT INTO `t_pub_district` VALUES ('856', '91', '岱山县');
INSERT INTO `t_pub_district` VALUES ('857', '91', '嵊泗县');
INSERT INTO `t_pub_district` VALUES ('858', '92', '义乌市');
INSERT INTO `t_pub_district` VALUES ('859', '93', '瑶海区');
INSERT INTO `t_pub_district` VALUES ('860', '93', '庐阳区');
INSERT INTO `t_pub_district` VALUES ('861', '93', '蜀山区');
INSERT INTO `t_pub_district` VALUES ('862', '93', '包河区');
INSERT INTO `t_pub_district` VALUES ('863', '93', '肥东县');
INSERT INTO `t_pub_district` VALUES ('864', '93', '肥西县');
INSERT INTO `t_pub_district` VALUES ('865', '93', '长丰县');
INSERT INTO `t_pub_district` VALUES ('866', '93', '庐江县');
INSERT INTO `t_pub_district` VALUES ('867', '93', '巢湖市');
INSERT INTO `t_pub_district` VALUES ('868', '94', '镜湖区');
INSERT INTO `t_pub_district` VALUES ('869', '94', '弋江区');
INSERT INTO `t_pub_district` VALUES ('870', '94', '鸠江区');
INSERT INTO `t_pub_district` VALUES ('871', '94', '三山区');
INSERT INTO `t_pub_district` VALUES ('872', '94', '无为县');
INSERT INTO `t_pub_district` VALUES ('873', '94', '芜湖县');
INSERT INTO `t_pub_district` VALUES ('874', '94', '繁昌县');
INSERT INTO `t_pub_district` VALUES ('875', '94', '南陵县');
INSERT INTO `t_pub_district` VALUES ('876', '95', '龙子湖区');
INSERT INTO `t_pub_district` VALUES ('877', '95', '蚌山区');
INSERT INTO `t_pub_district` VALUES ('878', '95', '禹会区');
INSERT INTO `t_pub_district` VALUES ('879', '95', '淮上区');
INSERT INTO `t_pub_district` VALUES ('880', '95', '五河县');
INSERT INTO `t_pub_district` VALUES ('881', '95', '固镇县');
INSERT INTO `t_pub_district` VALUES ('882', '95', '怀远县');
INSERT INTO `t_pub_district` VALUES ('883', '96', '大通区');
INSERT INTO `t_pub_district` VALUES ('884', '96', '田家庵区');
INSERT INTO `t_pub_district` VALUES ('885', '96', '谢家集区');
INSERT INTO `t_pub_district` VALUES ('886', '96', '八公山区');
INSERT INTO `t_pub_district` VALUES ('887', '96', '潘集区');
INSERT INTO `t_pub_district` VALUES ('888', '96', '凤台县');
INSERT INTO `t_pub_district` VALUES ('889', '96', '寿县');
INSERT INTO `t_pub_district` VALUES ('890', '97', '花山区');
INSERT INTO `t_pub_district` VALUES ('891', '97', '雨山区');
INSERT INTO `t_pub_district` VALUES ('892', '97', '博望区');
INSERT INTO `t_pub_district` VALUES ('893', '97', '含山县');
INSERT INTO `t_pub_district` VALUES ('894', '97', '和县');
INSERT INTO `t_pub_district` VALUES ('895', '97', '当涂县');
INSERT INTO `t_pub_district` VALUES ('896', '98', '相山区');
INSERT INTO `t_pub_district` VALUES ('897', '98', '杜集区');
INSERT INTO `t_pub_district` VALUES ('898', '98', '烈山区');
INSERT INTO `t_pub_district` VALUES ('899', '98', '濉溪县');
INSERT INTO `t_pub_district` VALUES ('900', '99', '铜官区');
INSERT INTO `t_pub_district` VALUES ('901', '99', '郊区');
INSERT INTO `t_pub_district` VALUES ('902', '99', '义安区');
INSERT INTO `t_pub_district` VALUES ('903', '99', '枞阳县');
INSERT INTO `t_pub_district` VALUES ('904', '100', '迎江区');
INSERT INTO `t_pub_district` VALUES ('905', '100', '大观区');
INSERT INTO `t_pub_district` VALUES ('906', '100', '宜秀区');
INSERT INTO `t_pub_district` VALUES ('907', '100', '怀宁县');
INSERT INTO `t_pub_district` VALUES ('908', '100', '桐城市');
INSERT INTO `t_pub_district` VALUES ('909', '100', '潜山县');
INSERT INTO `t_pub_district` VALUES ('910', '100', '太湖县');
INSERT INTO `t_pub_district` VALUES ('911', '100', '望江县');
INSERT INTO `t_pub_district` VALUES ('912', '100', '岳西县');
INSERT INTO `t_pub_district` VALUES ('913', '101', '屯溪区');
INSERT INTO `t_pub_district` VALUES ('914', '101', '黄山区');
INSERT INTO `t_pub_district` VALUES ('915', '101', '徽州区');
INSERT INTO `t_pub_district` VALUES ('916', '101', '歙县');
INSERT INTO `t_pub_district` VALUES ('917', '101', '休宁县');
INSERT INTO `t_pub_district` VALUES ('918', '101', '黟县');
INSERT INTO `t_pub_district` VALUES ('919', '101', '祁门县');
INSERT INTO `t_pub_district` VALUES ('920', '102', '颍州区');
INSERT INTO `t_pub_district` VALUES ('921', '102', '颍泉区');
INSERT INTO `t_pub_district` VALUES ('922', '102', '颍东区');
INSERT INTO `t_pub_district` VALUES ('923', '102', '颍上县');
INSERT INTO `t_pub_district` VALUES ('924', '102', '界首市');
INSERT INTO `t_pub_district` VALUES ('925', '102', '临泉县');
INSERT INTO `t_pub_district` VALUES ('926', '102', '阜南县');
INSERT INTO `t_pub_district` VALUES ('927', '102', '太和县');
INSERT INTO `t_pub_district` VALUES ('928', '103', '埇桥区');
INSERT INTO `t_pub_district` VALUES ('929', '103', '萧县');
INSERT INTO `t_pub_district` VALUES ('930', '103', '砀山县');
INSERT INTO `t_pub_district` VALUES ('931', '103', '灵璧县');
INSERT INTO `t_pub_district` VALUES ('932', '103', '泗县');
INSERT INTO `t_pub_district` VALUES ('933', '104', '琅琊区');
INSERT INTO `t_pub_district` VALUES ('934', '104', '南谯区');
INSERT INTO `t_pub_district` VALUES ('935', '104', '天长市');
INSERT INTO `t_pub_district` VALUES ('936', '104', '明光市');
INSERT INTO `t_pub_district` VALUES ('937', '104', '全椒县');
INSERT INTO `t_pub_district` VALUES ('938', '104', '来安县');
INSERT INTO `t_pub_district` VALUES ('939', '104', '凤阳县');
INSERT INTO `t_pub_district` VALUES ('940', '104', '定远县');
INSERT INTO `t_pub_district` VALUES ('941', '105', '金安区');
INSERT INTO `t_pub_district` VALUES ('942', '105', '裕安区');
INSERT INTO `t_pub_district` VALUES ('943', '105', '叶集区');
INSERT INTO `t_pub_district` VALUES ('944', '105', '霍邱县');
INSERT INTO `t_pub_district` VALUES ('945', '105', '霍山县');
INSERT INTO `t_pub_district` VALUES ('946', '105', '金寨县');
INSERT INTO `t_pub_district` VALUES ('947', '105', '舒城县');
INSERT INTO `t_pub_district` VALUES ('948', '106', '宣州区');
INSERT INTO `t_pub_district` VALUES ('949', '106', '郎溪县');
INSERT INTO `t_pub_district` VALUES ('950', '106', '宁国市');
INSERT INTO `t_pub_district` VALUES ('951', '106', '泾县');
INSERT INTO `t_pub_district` VALUES ('952', '106', '绩溪县');
INSERT INTO `t_pub_district` VALUES ('953', '106', '旌德县');
INSERT INTO `t_pub_district` VALUES ('954', '107', '贵池区');
INSERT INTO `t_pub_district` VALUES ('955', '107', '青阳县');
INSERT INTO `t_pub_district` VALUES ('956', '107', '石台县');
INSERT INTO `t_pub_district` VALUES ('957', '107', '东至县');
INSERT INTO `t_pub_district` VALUES ('958', '108', '谯城区');
INSERT INTO `t_pub_district` VALUES ('959', '108', '蒙城县');
INSERT INTO `t_pub_district` VALUES ('960', '108', '涡阳县');
INSERT INTO `t_pub_district` VALUES ('961', '108', '利辛县');
INSERT INTO `t_pub_district` VALUES ('962', '109', '广德县');
INSERT INTO `t_pub_district` VALUES ('963', '110', '宿松县');
INSERT INTO `t_pub_district` VALUES ('964', '111', '鼓楼区');
INSERT INTO `t_pub_district` VALUES ('965', '111', '台江区');
INSERT INTO `t_pub_district` VALUES ('966', '111', '仓山区');
INSERT INTO `t_pub_district` VALUES ('967', '111', '马尾区');
INSERT INTO `t_pub_district` VALUES ('968', '111', '晋安区');
INSERT INTO `t_pub_district` VALUES ('969', '111', '福清市');
INSERT INTO `t_pub_district` VALUES ('970', '111', '长乐市');
INSERT INTO `t_pub_district` VALUES ('971', '111', '闽侯县');
INSERT INTO `t_pub_district` VALUES ('972', '111', '连江县');
INSERT INTO `t_pub_district` VALUES ('973', '111', '罗源县');
INSERT INTO `t_pub_district` VALUES ('974', '111', '闽清县');
INSERT INTO `t_pub_district` VALUES ('975', '111', '永泰县');
INSERT INTO `t_pub_district` VALUES ('976', '112', '思明区');
INSERT INTO `t_pub_district` VALUES ('977', '112', '海沧区');
INSERT INTO `t_pub_district` VALUES ('978', '112', '湖里区');
INSERT INTO `t_pub_district` VALUES ('979', '112', '集美区');
INSERT INTO `t_pub_district` VALUES ('980', '112', '同安区');
INSERT INTO `t_pub_district` VALUES ('981', '112', '翔安区');
INSERT INTO `t_pub_district` VALUES ('982', '113', '芗城区');
INSERT INTO `t_pub_district` VALUES ('983', '113', '龙文区');
INSERT INTO `t_pub_district` VALUES ('984', '113', '龙海市');
INSERT INTO `t_pub_district` VALUES ('985', '113', '漳浦县');
INSERT INTO `t_pub_district` VALUES ('986', '113', '南靖县');
INSERT INTO `t_pub_district` VALUES ('987', '113', '云霄县');
INSERT INTO `t_pub_district` VALUES ('988', '113', '平和县');
INSERT INTO `t_pub_district` VALUES ('989', '113', '华安县');
INSERT INTO `t_pub_district` VALUES ('990', '113', '长泰县');
INSERT INTO `t_pub_district` VALUES ('991', '113', '诏安县');
INSERT INTO `t_pub_district` VALUES ('992', '113', '东山县');
INSERT INTO `t_pub_district` VALUES ('993', '114', '丰泽区');
INSERT INTO `t_pub_district` VALUES ('994', '114', '鲤城区');
INSERT INTO `t_pub_district` VALUES ('995', '114', '洛江区');
INSERT INTO `t_pub_district` VALUES ('996', '114', '泉港区');
INSERT INTO `t_pub_district` VALUES ('997', '114', '石狮市');
INSERT INTO `t_pub_district` VALUES ('998', '114', '晋江市');
INSERT INTO `t_pub_district` VALUES ('999', '114', '南安市');
INSERT INTO `t_pub_district` VALUES ('1000', '114', '惠安县');
INSERT INTO `t_pub_district` VALUES ('1001', '114', '安溪县');
INSERT INTO `t_pub_district` VALUES ('1002', '114', '永春县');
INSERT INTO `t_pub_district` VALUES ('1003', '114', '德化县');
INSERT INTO `t_pub_district` VALUES ('1004', '114', '金门县');
INSERT INTO `t_pub_district` VALUES ('1005', '115', '梅列区');
INSERT INTO `t_pub_district` VALUES ('1006', '115', '三元区');
INSERT INTO `t_pub_district` VALUES ('1007', '115', '永安市');
INSERT INTO `t_pub_district` VALUES ('1008', '115', '明溪县');
INSERT INTO `t_pub_district` VALUES ('1009', '115', '清流县');
INSERT INTO `t_pub_district` VALUES ('1010', '115', '宁化县');
INSERT INTO `t_pub_district` VALUES ('1011', '115', '大田县');
INSERT INTO `t_pub_district` VALUES ('1012', '115', '尤溪县');
INSERT INTO `t_pub_district` VALUES ('1013', '115', '沙县');
INSERT INTO `t_pub_district` VALUES ('1014', '115', '将乐县');
INSERT INTO `t_pub_district` VALUES ('1015', '115', '泰宁县');
INSERT INTO `t_pub_district` VALUES ('1016', '115', '建宁县');
INSERT INTO `t_pub_district` VALUES ('1017', '116', '城厢区');
INSERT INTO `t_pub_district` VALUES ('1018', '116', '涵江区');
INSERT INTO `t_pub_district` VALUES ('1019', '116', '荔城区');
INSERT INTO `t_pub_district` VALUES ('1020', '116', '秀屿区');
INSERT INTO `t_pub_district` VALUES ('1021', '116', '仙游县');
INSERT INTO `t_pub_district` VALUES ('1022', '117', '延平区');
INSERT INTO `t_pub_district` VALUES ('1023', '117', '建阳区');
INSERT INTO `t_pub_district` VALUES ('1024', '117', '邵武市');
INSERT INTO `t_pub_district` VALUES ('1025', '117', '武夷山市');
INSERT INTO `t_pub_district` VALUES ('1026', '117', '建瓯市');
INSERT INTO `t_pub_district` VALUES ('1027', '117', '顺昌县');
INSERT INTO `t_pub_district` VALUES ('1028', '117', '浦城县');
INSERT INTO `t_pub_district` VALUES ('1029', '117', '光泽县');
INSERT INTO `t_pub_district` VALUES ('1030', '117', '松溪县');
INSERT INTO `t_pub_district` VALUES ('1031', '117', '政和县');
INSERT INTO `t_pub_district` VALUES ('1032', '118', '新罗区');
INSERT INTO `t_pub_district` VALUES ('1033', '118', '永定区');
INSERT INTO `t_pub_district` VALUES ('1034', '118', '漳平市');
INSERT INTO `t_pub_district` VALUES ('1035', '118', '长汀县');
INSERT INTO `t_pub_district` VALUES ('1036', '118', '上杭县');
INSERT INTO `t_pub_district` VALUES ('1037', '118', '武平县');
INSERT INTO `t_pub_district` VALUES ('1038', '118', '连城县');
INSERT INTO `t_pub_district` VALUES ('1039', '119', '蕉城区');
INSERT INTO `t_pub_district` VALUES ('1040', '119', '东侨新区');
INSERT INTO `t_pub_district` VALUES ('1041', '119', '福安市');
INSERT INTO `t_pub_district` VALUES ('1042', '119', '福鼎市');
INSERT INTO `t_pub_district` VALUES ('1043', '119', '霞浦县');
INSERT INTO `t_pub_district` VALUES ('1044', '119', '古田县');
INSERT INTO `t_pub_district` VALUES ('1045', '119', '屏南县');
INSERT INTO `t_pub_district` VALUES ('1046', '119', '寿宁县');
INSERT INTO `t_pub_district` VALUES ('1047', '119', '周宁县');
INSERT INTO `t_pub_district` VALUES ('1048', '119', '柘荣县');
INSERT INTO `t_pub_district` VALUES ('1049', '120', '潭城');
INSERT INTO `t_pub_district` VALUES ('1050', '120', '苏澳');
INSERT INTO `t_pub_district` VALUES ('1051', '120', '流水');
INSERT INTO `t_pub_district` VALUES ('1052', '120', '澳前');
INSERT INTO `t_pub_district` VALUES ('1053', '120', '北厝');
INSERT INTO `t_pub_district` VALUES ('1054', '120', '平原');
INSERT INTO `t_pub_district` VALUES ('1055', '120', '敖东');
INSERT INTO `t_pub_district` VALUES ('1056', '121', '东湖区');
INSERT INTO `t_pub_district` VALUES ('1057', '121', '西湖区');
INSERT INTO `t_pub_district` VALUES ('1058', '121', '青云谱区');
INSERT INTO `t_pub_district` VALUES ('1059', '121', '湾里区');
INSERT INTO `t_pub_district` VALUES ('1060', '121', '青山湖区');
INSERT INTO `t_pub_district` VALUES ('1061', '121', '新建区');
INSERT INTO `t_pub_district` VALUES ('1062', '121', '南昌县');
INSERT INTO `t_pub_district` VALUES ('1063', '121', '安义县');
INSERT INTO `t_pub_district` VALUES ('1064', '121', '进贤县');
INSERT INTO `t_pub_district` VALUES ('1065', '122', '浔阳区');
INSERT INTO `t_pub_district` VALUES ('1066', '122', '濂溪区');
INSERT INTO `t_pub_district` VALUES ('1067', '122', '九江县');
INSERT INTO `t_pub_district` VALUES ('1068', '122', '永修县');
INSERT INTO `t_pub_district` VALUES ('1069', '122', '德安县');
INSERT INTO `t_pub_district` VALUES ('1070', '122', '都昌县');
INSERT INTO `t_pub_district` VALUES ('1071', '122', '湖口县');
INSERT INTO `t_pub_district` VALUES ('1072', '122', '彭泽县');
INSERT INTO `t_pub_district` VALUES ('1073', '122', '武宁县');
INSERT INTO `t_pub_district` VALUES ('1074', '122', '修水县');
INSERT INTO `t_pub_district` VALUES ('1075', '122', '瑞昌市');
INSERT INTO `t_pub_district` VALUES ('1076', '122', '庐山市');
INSERT INTO `t_pub_district` VALUES ('1077', '123', '信州区');
INSERT INTO `t_pub_district` VALUES ('1078', '123', '广丰区');
INSERT INTO `t_pub_district` VALUES ('1079', '123', '上饶县');
INSERT INTO `t_pub_district` VALUES ('1080', '123', '玉山县');
INSERT INTO `t_pub_district` VALUES ('1081', '123', '铅山县');
INSERT INTO `t_pub_district` VALUES ('1082', '123', '横峰县');
INSERT INTO `t_pub_district` VALUES ('1083', '123', '弋阳县');
INSERT INTO `t_pub_district` VALUES ('1084', '123', '余干县');
INSERT INTO `t_pub_district` VALUES ('1085', '123', '万年县');
INSERT INTO `t_pub_district` VALUES ('1086', '123', '婺源县');
INSERT INTO `t_pub_district` VALUES ('1087', '123', '德兴市');
INSERT INTO `t_pub_district` VALUES ('1088', '124', '临川区');
INSERT INTO `t_pub_district` VALUES ('1089', '124', '黎川县');
INSERT INTO `t_pub_district` VALUES ('1090', '124', '南丰县');
INSERT INTO `t_pub_district` VALUES ('1091', '124', '崇仁县');
INSERT INTO `t_pub_district` VALUES ('1092', '124', '乐安县');
INSERT INTO `t_pub_district` VALUES ('1093', '124', '宜黄县');
INSERT INTO `t_pub_district` VALUES ('1094', '124', '金溪县');
INSERT INTO `t_pub_district` VALUES ('1095', '124', '资溪县');
INSERT INTO `t_pub_district` VALUES ('1096', '124', '东乡县');
INSERT INTO `t_pub_district` VALUES ('1097', '124', '广昌县');
INSERT INTO `t_pub_district` VALUES ('1098', '125', '袁州区');
INSERT INTO `t_pub_district` VALUES ('1099', '125', '高安市');
INSERT INTO `t_pub_district` VALUES ('1100', '125', '樟树市');
INSERT INTO `t_pub_district` VALUES ('1101', '125', '奉新县');
INSERT INTO `t_pub_district` VALUES ('1102', '125', '万载县');
INSERT INTO `t_pub_district` VALUES ('1103', '125', '上高县');
INSERT INTO `t_pub_district` VALUES ('1104', '125', '宜丰县');
INSERT INTO `t_pub_district` VALUES ('1105', '125', '靖安县');
INSERT INTO `t_pub_district` VALUES ('1106', '125', '铜鼓县');
INSERT INTO `t_pub_district` VALUES ('1107', '126', '吉州区');
INSERT INTO `t_pub_district` VALUES ('1108', '126', '青原区');
INSERT INTO `t_pub_district` VALUES ('1109', '126', '吉安县');
INSERT INTO `t_pub_district` VALUES ('1110', '126', '井冈山市');
INSERT INTO `t_pub_district` VALUES ('1111', '126', '吉水县');
INSERT INTO `t_pub_district` VALUES ('1112', '126', '新干县');
INSERT INTO `t_pub_district` VALUES ('1113', '126', '永丰县');
INSERT INTO `t_pub_district` VALUES ('1114', '126', '泰和县');
INSERT INTO `t_pub_district` VALUES ('1115', '126', '遂川县');
INSERT INTO `t_pub_district` VALUES ('1116', '126', '万安县');
INSERT INTO `t_pub_district` VALUES ('1117', '126', '永新县');
INSERT INTO `t_pub_district` VALUES ('1118', '126', '峡江县');
INSERT INTO `t_pub_district` VALUES ('1119', '127', '章贡区');
INSERT INTO `t_pub_district` VALUES ('1120', '127', '南康区');
INSERT INTO `t_pub_district` VALUES ('1121', '127', '信丰县');
INSERT INTO `t_pub_district` VALUES ('1122', '127', '大余县');
INSERT INTO `t_pub_district` VALUES ('1123', '127', '赣县');
INSERT INTO `t_pub_district` VALUES ('1124', '127', '龙南县');
INSERT INTO `t_pub_district` VALUES ('1125', '127', '定南县');
INSERT INTO `t_pub_district` VALUES ('1126', '127', '全南县');
INSERT INTO `t_pub_district` VALUES ('1127', '127', '寻乌县');
INSERT INTO `t_pub_district` VALUES ('1128', '127', '安远县');
INSERT INTO `t_pub_district` VALUES ('1129', '127', '宁都县');
INSERT INTO `t_pub_district` VALUES ('1130', '127', '于都县');
INSERT INTO `t_pub_district` VALUES ('1131', '127', '会昌县');
INSERT INTO `t_pub_district` VALUES ('1132', '127', '石城县');
INSERT INTO `t_pub_district` VALUES ('1133', '127', '上犹县');
INSERT INTO `t_pub_district` VALUES ('1134', '127', '兴国县');
INSERT INTO `t_pub_district` VALUES ('1135', '127', '崇义县');
INSERT INTO `t_pub_district` VALUES ('1136', '128', '珠山区');
INSERT INTO `t_pub_district` VALUES ('1137', '128', '昌江区');
INSERT INTO `t_pub_district` VALUES ('1138', '128', '浮梁县');
INSERT INTO `t_pub_district` VALUES ('1139', '128', '乐平市');
INSERT INTO `t_pub_district` VALUES ('1140', '129', '安源区');
INSERT INTO `t_pub_district` VALUES ('1141', '129', '湘东区');
INSERT INTO `t_pub_district` VALUES ('1142', '129', '莲花县');
INSERT INTO `t_pub_district` VALUES ('1143', '129', '上栗县');
INSERT INTO `t_pub_district` VALUES ('1144', '129', '芦溪县');
INSERT INTO `t_pub_district` VALUES ('1145', '130', '渝水区');
INSERT INTO `t_pub_district` VALUES ('1146', '130', '分宜县');
INSERT INTO `t_pub_district` VALUES ('1147', '131', '月湖区');
INSERT INTO `t_pub_district` VALUES ('1148', '131', '余江县');
INSERT INTO `t_pub_district` VALUES ('1149', '131', '贵溪市');
INSERT INTO `t_pub_district` VALUES ('1150', '132', '共青城市');
INSERT INTO `t_pub_district` VALUES ('1151', '133', '瑞金市');
INSERT INTO `t_pub_district` VALUES ('1152', '134', '丰城市');
INSERT INTO `t_pub_district` VALUES ('1153', '135', '鄱阳县');
INSERT INTO `t_pub_district` VALUES ('1154', '136', '安福县');
INSERT INTO `t_pub_district` VALUES ('1155', '137', '南城县');
INSERT INTO `t_pub_district` VALUES ('1156', '138', '历下区');
INSERT INTO `t_pub_district` VALUES ('1157', '138', '市中区');
INSERT INTO `t_pub_district` VALUES ('1158', '138', '槐荫区');
INSERT INTO `t_pub_district` VALUES ('1159', '138', '天桥区');
INSERT INTO `t_pub_district` VALUES ('1160', '138', '历城区');
INSERT INTO `t_pub_district` VALUES ('1161', '138', '长清区');
INSERT INTO `t_pub_district` VALUES ('1162', '138', '章丘市');
INSERT INTO `t_pub_district` VALUES ('1163', '138', '平阴县');
INSERT INTO `t_pub_district` VALUES ('1164', '138', '济阳县');
INSERT INTO `t_pub_district` VALUES ('1165', '138', '商河县');
INSERT INTO `t_pub_district` VALUES ('1166', '139', '市南区');
INSERT INTO `t_pub_district` VALUES ('1167', '139', '市北区');
INSERT INTO `t_pub_district` VALUES ('1168', '139', '李沧区');
INSERT INTO `t_pub_district` VALUES ('1169', '139', '城阳区');
INSERT INTO `t_pub_district` VALUES ('1170', '139', '崂山区');
INSERT INTO `t_pub_district` VALUES ('1171', '139', '黄岛区');
INSERT INTO `t_pub_district` VALUES ('1172', '139', '即墨市');
INSERT INTO `t_pub_district` VALUES ('1173', '139', '胶州市');
INSERT INTO `t_pub_district` VALUES ('1174', '139', '平度市');
INSERT INTO `t_pub_district` VALUES ('1175', '139', '莱西市');
INSERT INTO `t_pub_district` VALUES ('1176', '140', '张店区');
INSERT INTO `t_pub_district` VALUES ('1177', '140', '淄川区');
INSERT INTO `t_pub_district` VALUES ('1178', '140', '周村区');
INSERT INTO `t_pub_district` VALUES ('1179', '140', '博山区');
INSERT INTO `t_pub_district` VALUES ('1180', '140', '临淄区');
INSERT INTO `t_pub_district` VALUES ('1181', '140', '桓台县');
INSERT INTO `t_pub_district` VALUES ('1182', '140', '高青县');
INSERT INTO `t_pub_district` VALUES ('1183', '140', '沂源县');
INSERT INTO `t_pub_district` VALUES ('1184', '141', '薛城区');
INSERT INTO `t_pub_district` VALUES ('1185', '141', '市中区');
INSERT INTO `t_pub_district` VALUES ('1186', '141', '峄城区');
INSERT INTO `t_pub_district` VALUES ('1187', '141', '山亭区');
INSERT INTO `t_pub_district` VALUES ('1188', '141', '台儿庄区');
INSERT INTO `t_pub_district` VALUES ('1189', '141', '滕州市');
INSERT INTO `t_pub_district` VALUES ('1190', '142', '东营区');
INSERT INTO `t_pub_district` VALUES ('1191', '142', '河口区');
INSERT INTO `t_pub_district` VALUES ('1192', '142', '广饶县');
INSERT INTO `t_pub_district` VALUES ('1193', '142', '垦利县');
INSERT INTO `t_pub_district` VALUES ('1194', '142', '利津县');
INSERT INTO `t_pub_district` VALUES ('1195', '143', '莱山区');
INSERT INTO `t_pub_district` VALUES ('1196', '143', '芝罘区');
INSERT INTO `t_pub_district` VALUES ('1197', '143', '福山区');
INSERT INTO `t_pub_district` VALUES ('1198', '143', '牟平区');
INSERT INTO `t_pub_district` VALUES ('1199', '143', '龙口市');
INSERT INTO `t_pub_district` VALUES ('1200', '143', '莱阳市');
INSERT INTO `t_pub_district` VALUES ('1201', '143', '莱州市');
INSERT INTO `t_pub_district` VALUES ('1202', '143', '蓬莱市');
INSERT INTO `t_pub_district` VALUES ('1203', '143', '招远市');
INSERT INTO `t_pub_district` VALUES ('1204', '143', '栖霞市');
INSERT INTO `t_pub_district` VALUES ('1205', '143', '海阳市');
INSERT INTO `t_pub_district` VALUES ('1206', '143', '长岛县');
INSERT INTO `t_pub_district` VALUES ('1207', '144', '奎文区');
INSERT INTO `t_pub_district` VALUES ('1208', '144', '潍城区');
INSERT INTO `t_pub_district` VALUES ('1209', '144', '寒亭区');
INSERT INTO `t_pub_district` VALUES ('1210', '144', '坊子区');
INSERT INTO `t_pub_district` VALUES ('1211', '144', '诸城市');
INSERT INTO `t_pub_district` VALUES ('1212', '144', '青州市');
INSERT INTO `t_pub_district` VALUES ('1213', '144', '寿光市');
INSERT INTO `t_pub_district` VALUES ('1214', '144', '安丘市');
INSERT INTO `t_pub_district` VALUES ('1215', '144', '昌邑市');
INSERT INTO `t_pub_district` VALUES ('1216', '144', '高密市');
INSERT INTO `t_pub_district` VALUES ('1217', '144', '临朐县');
INSERT INTO `t_pub_district` VALUES ('1218', '144', '昌乐县');
INSERT INTO `t_pub_district` VALUES ('1219', '145', '任城区');
INSERT INTO `t_pub_district` VALUES ('1220', '145', '兖州区');
INSERT INTO `t_pub_district` VALUES ('1221', '145', '邹城市');
INSERT INTO `t_pub_district` VALUES ('1222', '145', '曲阜市');
INSERT INTO `t_pub_district` VALUES ('1223', '145', '嘉祥县');
INSERT INTO `t_pub_district` VALUES ('1224', '145', '汶上县');
INSERT INTO `t_pub_district` VALUES ('1225', '145', '梁山县');
INSERT INTO `t_pub_district` VALUES ('1226', '145', '微山县');
INSERT INTO `t_pub_district` VALUES ('1227', '145', '鱼台县');
INSERT INTO `t_pub_district` VALUES ('1228', '145', '金乡县');
INSERT INTO `t_pub_district` VALUES ('1229', '145', '泗水县');
INSERT INTO `t_pub_district` VALUES ('1230', '146', '泰山区');
INSERT INTO `t_pub_district` VALUES ('1231', '146', '岱岳区');
INSERT INTO `t_pub_district` VALUES ('1232', '146', '新泰市');
INSERT INTO `t_pub_district` VALUES ('1233', '146', '肥城市');
INSERT INTO `t_pub_district` VALUES ('1234', '146', '宁阳县');
INSERT INTO `t_pub_district` VALUES ('1235', '146', '东平县');
INSERT INTO `t_pub_district` VALUES ('1236', '147', '环翠区');
INSERT INTO `t_pub_district` VALUES ('1237', '147', '文登区');
INSERT INTO `t_pub_district` VALUES ('1238', '147', '荣成市');
INSERT INTO `t_pub_district` VALUES ('1239', '147', '乳山市');
INSERT INTO `t_pub_district` VALUES ('1240', '148', '东港区');
INSERT INTO `t_pub_district` VALUES ('1241', '148', '岚山区');
INSERT INTO `t_pub_district` VALUES ('1242', '148', '五莲县');
INSERT INTO `t_pub_district` VALUES ('1243', '148', '莒县');
INSERT INTO `t_pub_district` VALUES ('1244', '149', '滨城区');
INSERT INTO `t_pub_district` VALUES ('1245', '149', '沾化区');
INSERT INTO `t_pub_district` VALUES ('1246', '149', '惠民县');
INSERT INTO `t_pub_district` VALUES ('1247', '149', '阳信县');
INSERT INTO `t_pub_district` VALUES ('1248', '149', '无棣县');
INSERT INTO `t_pub_district` VALUES ('1249', '149', '博兴县');
INSERT INTO `t_pub_district` VALUES ('1250', '149', '邹平县');
INSERT INTO `t_pub_district` VALUES ('1251', '150', '德城区');
INSERT INTO `t_pub_district` VALUES ('1252', '150', '陵城区');
INSERT INTO `t_pub_district` VALUES ('1253', '150', '乐陵市');
INSERT INTO `t_pub_district` VALUES ('1254', '150', '禹城市');
INSERT INTO `t_pub_district` VALUES ('1255', '150', '临邑县');
INSERT INTO `t_pub_district` VALUES ('1256', '150', '平原县');
INSERT INTO `t_pub_district` VALUES ('1257', '150', '夏津县');
INSERT INTO `t_pub_district` VALUES ('1258', '150', '武城县');
INSERT INTO `t_pub_district` VALUES ('1259', '150', '庆云县');
INSERT INTO `t_pub_district` VALUES ('1260', '150', '宁津县');
INSERT INTO `t_pub_district` VALUES ('1261', '150', '齐河县');
INSERT INTO `t_pub_district` VALUES ('1262', '151', '东昌府区');
INSERT INTO `t_pub_district` VALUES ('1263', '151', '临清市');
INSERT INTO `t_pub_district` VALUES ('1264', '151', '茌平县');
INSERT INTO `t_pub_district` VALUES ('1265', '151', '东阿县');
INSERT INTO `t_pub_district` VALUES ('1266', '151', '高唐县');
INSERT INTO `t_pub_district` VALUES ('1267', '151', '阳谷县');
INSERT INTO `t_pub_district` VALUES ('1268', '151', '冠县');
INSERT INTO `t_pub_district` VALUES ('1269', '151', '莘县');
INSERT INTO `t_pub_district` VALUES ('1270', '151', '聊城开发区');
INSERT INTO `t_pub_district` VALUES ('1271', '152', '兰山区');
INSERT INTO `t_pub_district` VALUES ('1272', '152', '河东区');
INSERT INTO `t_pub_district` VALUES ('1273', '152', '罗庄区');
INSERT INTO `t_pub_district` VALUES ('1274', '152', '兰陵县');
INSERT INTO `t_pub_district` VALUES ('1275', '152', '郯城县');
INSERT INTO `t_pub_district` VALUES ('1276', '152', '莒南县');
INSERT INTO `t_pub_district` VALUES ('1277', '152', '沂水县');
INSERT INTO `t_pub_district` VALUES ('1278', '152', '蒙阴县');
INSERT INTO `t_pub_district` VALUES ('1279', '152', '平邑县');
INSERT INTO `t_pub_district` VALUES ('1280', '152', '沂南县');
INSERT INTO `t_pub_district` VALUES ('1281', '152', '临沭县');
INSERT INTO `t_pub_district` VALUES ('1282', '152', '费县');
INSERT INTO `t_pub_district` VALUES ('1283', '153', '牡丹区');
INSERT INTO `t_pub_district` VALUES ('1284', '153', '定陶区');
INSERT INTO `t_pub_district` VALUES ('1285', '153', '曹县');
INSERT INTO `t_pub_district` VALUES ('1286', '153', '单县');
INSERT INTO `t_pub_district` VALUES ('1287', '153', '巨野县');
INSERT INTO `t_pub_district` VALUES ('1288', '153', '成武县');
INSERT INTO `t_pub_district` VALUES ('1289', '153', '郓城县');
INSERT INTO `t_pub_district` VALUES ('1290', '153', '鄄城县');
INSERT INTO `t_pub_district` VALUES ('1291', '153', '东明县');
INSERT INTO `t_pub_district` VALUES ('1292', '154', '莱城区');
INSERT INTO `t_pub_district` VALUES ('1293', '154', '钢城区');
INSERT INTO `t_pub_district` VALUES ('1294', '155', '中原区');
INSERT INTO `t_pub_district` VALUES ('1295', '155', '二七区');
INSERT INTO `t_pub_district` VALUES ('1296', '155', '金水区');
INSERT INTO `t_pub_district` VALUES ('1297', '155', '惠济区');
INSERT INTO `t_pub_district` VALUES ('1298', '155', '管城区');
INSERT INTO `t_pub_district` VALUES ('1299', '155', '上街区');
INSERT INTO `t_pub_district` VALUES ('1300', '155', '新郑市');
INSERT INTO `t_pub_district` VALUES ('1301', '155', '登封市');
INSERT INTO `t_pub_district` VALUES ('1302', '155', '荥阳市');
INSERT INTO `t_pub_district` VALUES ('1303', '155', '新密市');
INSERT INTO `t_pub_district` VALUES ('1304', '155', '中牟县');
INSERT INTO `t_pub_district` VALUES ('1305', '156', '龙亭区');
INSERT INTO `t_pub_district` VALUES ('1306', '156', '鼓楼区');
INSERT INTO `t_pub_district` VALUES ('1307', '156', '禹王台区');
INSERT INTO `t_pub_district` VALUES ('1308', '156', '顺河区');
INSERT INTO `t_pub_district` VALUES ('1309', '156', '祥符区');
INSERT INTO `t_pub_district` VALUES ('1310', '156', '通许县');
INSERT INTO `t_pub_district` VALUES ('1311', '156', '杞县');
INSERT INTO `t_pub_district` VALUES ('1312', '156', '尉氏县');
INSERT INTO `t_pub_district` VALUES ('1313', '157', '涧西区');
INSERT INTO `t_pub_district` VALUES ('1314', '157', '西工区');
INSERT INTO `t_pub_district` VALUES ('1315', '157', '老城区');
INSERT INTO `t_pub_district` VALUES ('1316', '157', '瀍河区');
INSERT INTO `t_pub_district` VALUES ('1317', '157', '洛龙区');
INSERT INTO `t_pub_district` VALUES ('1318', '157', '吉利区');
INSERT INTO `t_pub_district` VALUES ('1319', '157', '偃师市');
INSERT INTO `t_pub_district` VALUES ('1320', '157', '宜阳县');
INSERT INTO `t_pub_district` VALUES ('1321', '157', '孟津县');
INSERT INTO `t_pub_district` VALUES ('1322', '157', '新安县');
INSERT INTO `t_pub_district` VALUES ('1323', '157', '洛宁县');
INSERT INTO `t_pub_district` VALUES ('1324', '157', '栾川县');
INSERT INTO `t_pub_district` VALUES ('1325', '157', '伊川县');
INSERT INTO `t_pub_district` VALUES ('1326', '157', '汝阳县');
INSERT INTO `t_pub_district` VALUES ('1327', '157', '嵩县');
INSERT INTO `t_pub_district` VALUES ('1328', '158', '新华区');
INSERT INTO `t_pub_district` VALUES ('1329', '158', '卫东区');
INSERT INTO `t_pub_district` VALUES ('1330', '158', '石龙区');
INSERT INTO `t_pub_district` VALUES ('1331', '158', '湛河区');
INSERT INTO `t_pub_district` VALUES ('1332', '158', '舞钢市');
INSERT INTO `t_pub_district` VALUES ('1333', '158', '鲁山县');
INSERT INTO `t_pub_district` VALUES ('1334', '158', '宝丰县');
INSERT INTO `t_pub_district` VALUES ('1335', '158', '叶县');
INSERT INTO `t_pub_district` VALUES ('1336', '158', '郏县');
INSERT INTO `t_pub_district` VALUES ('1337', '159', '文峰区');
INSERT INTO `t_pub_district` VALUES ('1338', '159', '北关区');
INSERT INTO `t_pub_district` VALUES ('1339', '159', '殷都区');
INSERT INTO `t_pub_district` VALUES ('1340', '159', '龙安区');
INSERT INTO `t_pub_district` VALUES ('1341', '159', '林州市');
INSERT INTO `t_pub_district` VALUES ('1342', '159', '安阳县');
INSERT INTO `t_pub_district` VALUES ('1343', '159', '汤阴县');
INSERT INTO `t_pub_district` VALUES ('1344', '159', '内黄县');
INSERT INTO `t_pub_district` VALUES ('1345', '160', '鹤山区');
INSERT INTO `t_pub_district` VALUES ('1346', '160', '山城区');
INSERT INTO `t_pub_district` VALUES ('1347', '160', '淇滨区');
INSERT INTO `t_pub_district` VALUES ('1348', '160', '浚县');
INSERT INTO `t_pub_district` VALUES ('1349', '160', '淇县');
INSERT INTO `t_pub_district` VALUES ('1350', '161', '红旗区');
INSERT INTO `t_pub_district` VALUES ('1351', '161', '卫滨区');
INSERT INTO `t_pub_district` VALUES ('1352', '161', '牧野区');
INSERT INTO `t_pub_district` VALUES ('1353', '161', '凤泉区');
INSERT INTO `t_pub_district` VALUES ('1354', '161', '卫辉市');
INSERT INTO `t_pub_district` VALUES ('1355', '161', '辉县市');
INSERT INTO `t_pub_district` VALUES ('1356', '161', '新乡县');
INSERT INTO `t_pub_district` VALUES ('1357', '161', '获嘉县');
INSERT INTO `t_pub_district` VALUES ('1358', '161', '原阳县');
INSERT INTO `t_pub_district` VALUES ('1359', '161', '延津县');
INSERT INTO `t_pub_district` VALUES ('1360', '161', '封丘县');
INSERT INTO `t_pub_district` VALUES ('1361', '162', '山阳区');
INSERT INTO `t_pub_district` VALUES ('1362', '162', '中站区');
INSERT INTO `t_pub_district` VALUES ('1363', '162', '解放区');
INSERT INTO `t_pub_district` VALUES ('1364', '162', '马村区');
INSERT INTO `t_pub_district` VALUES ('1365', '162', '沁阳市');
INSERT INTO `t_pub_district` VALUES ('1366', '162', '孟州市');
INSERT INTO `t_pub_district` VALUES ('1367', '162', '修武县');
INSERT INTO `t_pub_district` VALUES ('1368', '162', '博爱县');
INSERT INTO `t_pub_district` VALUES ('1369', '162', '武陟县');
INSERT INTO `t_pub_district` VALUES ('1370', '162', '温县');
INSERT INTO `t_pub_district` VALUES ('1371', '163', '华龙区');
INSERT INTO `t_pub_district` VALUES ('1372', '163', '濮阳县');
INSERT INTO `t_pub_district` VALUES ('1373', '163', '清丰县');
INSERT INTO `t_pub_district` VALUES ('1374', '163', '南乐县');
INSERT INTO `t_pub_district` VALUES ('1375', '163', '台前县');
INSERT INTO `t_pub_district` VALUES ('1376', '163', '范县');
INSERT INTO `t_pub_district` VALUES ('1377', '164', '魏都区');
INSERT INTO `t_pub_district` VALUES ('1378', '164', '禹州市');
INSERT INTO `t_pub_district` VALUES ('1379', '164', '长葛市');
INSERT INTO `t_pub_district` VALUES ('1380', '164', '许昌县');
INSERT INTO `t_pub_district` VALUES ('1381', '164', '鄢陵县');
INSERT INTO `t_pub_district` VALUES ('1382', '164', '襄城县');
INSERT INTO `t_pub_district` VALUES ('1383', '165', '郾城区');
INSERT INTO `t_pub_district` VALUES ('1384', '165', '源汇区');
INSERT INTO `t_pub_district` VALUES ('1385', '165', '召陵区');
INSERT INTO `t_pub_district` VALUES ('1386', '165', '舞阳县');
INSERT INTO `t_pub_district` VALUES ('1387', '165', '临颍县');
INSERT INTO `t_pub_district` VALUES ('1388', '166', '湖滨区');
INSERT INTO `t_pub_district` VALUES ('1389', '166', '陕州区');
INSERT INTO `t_pub_district` VALUES ('1390', '166', '灵宝市');
INSERT INTO `t_pub_district` VALUES ('1391', '166', '义马市');
INSERT INTO `t_pub_district` VALUES ('1392', '166', '渑池县');
INSERT INTO `t_pub_district` VALUES ('1393', '166', '卢氏县');
INSERT INTO `t_pub_district` VALUES ('1394', '167', '睢阳区');
INSERT INTO `t_pub_district` VALUES ('1395', '167', '梁园区');
INSERT INTO `t_pub_district` VALUES ('1396', '167', '民权县');
INSERT INTO `t_pub_district` VALUES ('1397', '167', '宁陵县');
INSERT INTO `t_pub_district` VALUES ('1398', '167', '柘城县');
INSERT INTO `t_pub_district` VALUES ('1399', '167', '虞城县');
INSERT INTO `t_pub_district` VALUES ('1400', '167', '夏邑县');
INSERT INTO `t_pub_district` VALUES ('1401', '167', '睢县');
INSERT INTO `t_pub_district` VALUES ('1402', '168', '川汇区');
INSERT INTO `t_pub_district` VALUES ('1403', '168', '项城市');
INSERT INTO `t_pub_district` VALUES ('1404', '168', '扶沟县');
INSERT INTO `t_pub_district` VALUES ('1405', '168', '西华县');
INSERT INTO `t_pub_district` VALUES ('1406', '168', '商水县');
INSERT INTO `t_pub_district` VALUES ('1407', '168', '沈丘县');
INSERT INTO `t_pub_district` VALUES ('1408', '168', '淮阳县');
INSERT INTO `t_pub_district` VALUES ('1409', '168', '郸城县');
INSERT INTO `t_pub_district` VALUES ('1410', '168', '太康县');
INSERT INTO `t_pub_district` VALUES ('1411', '169', '驿城区');
INSERT INTO `t_pub_district` VALUES ('1412', '169', '西平县');
INSERT INTO `t_pub_district` VALUES ('1413', '169', '遂平县');
INSERT INTO `t_pub_district` VALUES ('1414', '169', '平舆县');
INSERT INTO `t_pub_district` VALUES ('1415', '169', '上蔡县');
INSERT INTO `t_pub_district` VALUES ('1416', '169', '正阳县');
INSERT INTO `t_pub_district` VALUES ('1417', '169', '泌阳县');
INSERT INTO `t_pub_district` VALUES ('1418', '169', '确山县');
INSERT INTO `t_pub_district` VALUES ('1419', '169', '汝南县');
INSERT INTO `t_pub_district` VALUES ('1420', '170', '宛城区');
INSERT INTO `t_pub_district` VALUES ('1421', '170', '卧龙区');
INSERT INTO `t_pub_district` VALUES ('1422', '170', '南召县');
INSERT INTO `t_pub_district` VALUES ('1423', '170', '西峡县');
INSERT INTO `t_pub_district` VALUES ('1424', '170', '方城县');
INSERT INTO `t_pub_district` VALUES ('1425', '170', '镇平县');
INSERT INTO `t_pub_district` VALUES ('1426', '170', '内乡县');
INSERT INTO `t_pub_district` VALUES ('1427', '170', '淅川县');
INSERT INTO `t_pub_district` VALUES ('1428', '170', '社旗县');
INSERT INTO `t_pub_district` VALUES ('1429', '170', '唐河县');
INSERT INTO `t_pub_district` VALUES ('1430', '170', '新野县');
INSERT INTO `t_pub_district` VALUES ('1431', '170', '桐柏县');
INSERT INTO `t_pub_district` VALUES ('1432', '171', '浉河区');
INSERT INTO `t_pub_district` VALUES ('1433', '171', '平桥区');
INSERT INTO `t_pub_district` VALUES ('1434', '171', '罗山县');
INSERT INTO `t_pub_district` VALUES ('1435', '171', '光山县');
INSERT INTO `t_pub_district` VALUES ('1436', '171', '潢川县');
INSERT INTO `t_pub_district` VALUES ('1437', '171', '淮滨县');
INSERT INTO `t_pub_district` VALUES ('1438', '171', '商城县');
INSERT INTO `t_pub_district` VALUES ('1439', '171', '新县');
INSERT INTO `t_pub_district` VALUES ('1440', '171', '息县');
INSERT INTO `t_pub_district` VALUES ('1441', '172', '济源市');
INSERT INTO `t_pub_district` VALUES ('1442', '173', '巩义市');
INSERT INTO `t_pub_district` VALUES ('1443', '155', '邓州市');
INSERT INTO `t_pub_district` VALUES ('1444', '175', '汝州市');
INSERT INTO `t_pub_district` VALUES ('1445', '176', '长垣县');
INSERT INTO `t_pub_district` VALUES ('1446', '177', '滑县');
INSERT INTO `t_pub_district` VALUES ('1447', '178', '兰考县');
INSERT INTO `t_pub_district` VALUES ('1448', '179', '新蔡县');
INSERT INTO `t_pub_district` VALUES ('1449', '180', '固始县');
INSERT INTO `t_pub_district` VALUES ('1450', '181', '鹿邑县');
INSERT INTO `t_pub_district` VALUES ('1451', '182', '永城市');
INSERT INTO `t_pub_district` VALUES ('1452', '183', '黄州区');
INSERT INTO `t_pub_district` VALUES ('1453', '183', '麻城市');
INSERT INTO `t_pub_district` VALUES ('1454', '183', '武穴市');
INSERT INTO `t_pub_district` VALUES ('1455', '183', '红安县');
INSERT INTO `t_pub_district` VALUES ('1456', '183', '黄梅县');
INSERT INTO `t_pub_district` VALUES ('1457', '183', '罗田县');
INSERT INTO `t_pub_district` VALUES ('1458', '183', '蕲春县');
INSERT INTO `t_pub_district` VALUES ('1459', '183', '团风县');
INSERT INTO `t_pub_district` VALUES ('1460', '183', '浠水县');
INSERT INTO `t_pub_district` VALUES ('1461', '183', '英山县');
INSERT INTO `t_pub_district` VALUES ('1462', '184', '黄石港区');
INSERT INTO `t_pub_district` VALUES ('1463', '184', '西塞山区');
INSERT INTO `t_pub_district` VALUES ('1464', '184', '铁山区');
INSERT INTO `t_pub_district` VALUES ('1465', '184', '下陆区');
INSERT INTO `t_pub_district` VALUES ('1466', '184', '大冶市');
INSERT INTO `t_pub_district` VALUES ('1467', '184', '阳新县');
INSERT INTO `t_pub_district` VALUES ('1468', '185', '掇刀区');
INSERT INTO `t_pub_district` VALUES ('1469', '185', '东宝区');
INSERT INTO `t_pub_district` VALUES ('1470', '185', '钟祥市');
INSERT INTO `t_pub_district` VALUES ('1471', '185', '京山县');
INSERT INTO `t_pub_district` VALUES ('1472', '185', '沙洋县');
INSERT INTO `t_pub_district` VALUES ('1473', '186', '荆州区');
INSERT INTO `t_pub_district` VALUES ('1474', '186', '洪湖市');
INSERT INTO `t_pub_district` VALUES ('1475', '186', '松滋市');
INSERT INTO `t_pub_district` VALUES ('1476', '186', '石首市');
INSERT INTO `t_pub_district` VALUES ('1477', '186', '沙市区');
INSERT INTO `t_pub_district` VALUES ('1478', '186', '公安县');
INSERT INTO `t_pub_district` VALUES ('1479', '186', '监利县');
INSERT INTO `t_pub_district` VALUES ('1480', '186', '江陵县');
INSERT INTO `t_pub_district` VALUES ('1481', '187', '鄂城区');
INSERT INTO `t_pub_district` VALUES ('1482', '187', '华容区');
INSERT INTO `t_pub_district` VALUES ('1483', '187', '梁子湖区');
INSERT INTO `t_pub_district` VALUES ('1484', '188', '郧阳区');
INSERT INTO `t_pub_district` VALUES ('1485', '188', '茅箭区');
INSERT INTO `t_pub_district` VALUES ('1486', '188', '张湾区');
INSERT INTO `t_pub_district` VALUES ('1487', '188', '丹江口市');
INSERT INTO `t_pub_district` VALUES ('1488', '188', '房县');
INSERT INTO `t_pub_district` VALUES ('1489', '188', '竹山县');
INSERT INTO `t_pub_district` VALUES ('1490', '188', '竹溪县');
INSERT INTO `t_pub_district` VALUES ('1491', '188', '郧西县');
INSERT INTO `t_pub_district` VALUES ('1492', '189', '随县');
INSERT INTO `t_pub_district` VALUES ('1493', '189', '广水市');
INSERT INTO `t_pub_district` VALUES ('1494', '189', '曾都区');
INSERT INTO `t_pub_district` VALUES ('1495', '190', '武昌区');
INSERT INTO `t_pub_district` VALUES ('1496', '190', '东西湖区');
INSERT INTO `t_pub_district` VALUES ('1497', '190', '汉南区');
INSERT INTO `t_pub_district` VALUES ('1498', '190', '汉阳区');
INSERT INTO `t_pub_district` VALUES ('1499', '190', '江岸区');
INSERT INTO `t_pub_district` VALUES ('1500', '190', '江汉区');
INSERT INTO `t_pub_district` VALUES ('1501', '190', '江夏区');
INSERT INTO `t_pub_district` VALUES ('1502', '190', '蔡甸区');
INSERT INTO `t_pub_district` VALUES ('1503', '190', '黄陂区');
INSERT INTO `t_pub_district` VALUES ('1504', '190', '洪山区');
INSERT INTO `t_pub_district` VALUES ('1505', '190', '硚口区');
INSERT INTO `t_pub_district` VALUES ('1506', '190', '青山区');
INSERT INTO `t_pub_district` VALUES ('1507', '190', '新洲区');
INSERT INTO `t_pub_district` VALUES ('1508', '191', '孝南区');
INSERT INTO `t_pub_district` VALUES ('1509', '191', '安陆市');
INSERT INTO `t_pub_district` VALUES ('1510', '191', '汉川市');
INSERT INTO `t_pub_district` VALUES ('1511', '191', '应城市');
INSERT INTO `t_pub_district` VALUES ('1512', '191', '大悟县');
INSERT INTO `t_pub_district` VALUES ('1513', '191', '孝昌县');
INSERT INTO `t_pub_district` VALUES ('1514', '191', '云梦县');
INSERT INTO `t_pub_district` VALUES ('1515', '192', '赤壁市');
INSERT INTO `t_pub_district` VALUES ('1516', '192', '咸安区');
INSERT INTO `t_pub_district` VALUES ('1517', '192', '崇阳县');
INSERT INTO `t_pub_district` VALUES ('1518', '192', '嘉鱼县');
INSERT INTO `t_pub_district` VALUES ('1519', '192', '通城县');
INSERT INTO `t_pub_district` VALUES ('1520', '192', '通山县');
INSERT INTO `t_pub_district` VALUES ('1521', '193', '宜城市');
INSERT INTO `t_pub_district` VALUES ('1522', '193', '枣阳市');
INSERT INTO `t_pub_district` VALUES ('1523', '193', '老河口市');
INSERT INTO `t_pub_district` VALUES ('1524', '193', '樊城区');
INSERT INTO `t_pub_district` VALUES ('1525', '193', '襄城区');
INSERT INTO `t_pub_district` VALUES ('1526', '193', '襄州区');
INSERT INTO `t_pub_district` VALUES ('1527', '193', '保康县');
INSERT INTO `t_pub_district` VALUES ('1528', '193', '谷城县');
INSERT INTO `t_pub_district` VALUES ('1529', '193', '南漳县');
INSERT INTO `t_pub_district` VALUES ('1530', '194', '当阳市');
INSERT INTO `t_pub_district` VALUES ('1531', '194', '宜都市');
INSERT INTO `t_pub_district` VALUES ('1532', '194', '枝江市');
INSERT INTO `t_pub_district` VALUES ('1533', '194', '点军区');
INSERT INTO `t_pub_district` VALUES ('1534', '194', '西陵区');
INSERT INTO `t_pub_district` VALUES ('1535', '194', '猇亭区');
INSERT INTO `t_pub_district` VALUES ('1536', '194', '夷陵区');
INSERT INTO `t_pub_district` VALUES ('1537', '194', '伍家岗区');
INSERT INTO `t_pub_district` VALUES ('1538', '194', '兴山县');
INSERT INTO `t_pub_district` VALUES ('1539', '194', '远安县');
INSERT INTO `t_pub_district` VALUES ('1540', '194', '秭归县');
INSERT INTO `t_pub_district` VALUES ('1541', '194', '长阳土家族自治县');
INSERT INTO `t_pub_district` VALUES ('1542', '194', '五峰土家族自治县');
INSERT INTO `t_pub_district` VALUES ('1543', '195', '恩施市');
INSERT INTO `t_pub_district` VALUES ('1544', '195', '利川市');
INSERT INTO `t_pub_district` VALUES ('1545', '195', '巴东县');
INSERT INTO `t_pub_district` VALUES ('1546', '195', '鹤峰县');
INSERT INTO `t_pub_district` VALUES ('1547', '195', '建始县');
INSERT INTO `t_pub_district` VALUES ('1548', '195', '来凤县');
INSERT INTO `t_pub_district` VALUES ('1549', '195', '宣恩县');
INSERT INTO `t_pub_district` VALUES ('1550', '195', '咸丰县');
INSERT INTO `t_pub_district` VALUES ('1551', '196', '仙桃市');
INSERT INTO `t_pub_district` VALUES ('1552', '197', '潜江市');
INSERT INTO `t_pub_district` VALUES ('1553', '198', '天门市');
INSERT INTO `t_pub_district` VALUES ('1554', '199', '神农架林区');
INSERT INTO `t_pub_district` VALUES ('1555', '200', '芙蓉区');
INSERT INTO `t_pub_district` VALUES ('1556', '200', '天心区');
INSERT INTO `t_pub_district` VALUES ('1557', '200', '岳麓区');
INSERT INTO `t_pub_district` VALUES ('1558', '200', '开福区');
INSERT INTO `t_pub_district` VALUES ('1559', '200', '雨花区');
INSERT INTO `t_pub_district` VALUES ('1560', '200', '望城区');
INSERT INTO `t_pub_district` VALUES ('1561', '200', '长沙县');
INSERT INTO `t_pub_district` VALUES ('1562', '200', '宁乡县');
INSERT INTO `t_pub_district` VALUES ('1563', '201', '天元区');
INSERT INTO `t_pub_district` VALUES ('1564', '201', '荷塘区');
INSERT INTO `t_pub_district` VALUES ('1565', '201', '芦淞区');
INSERT INTO `t_pub_district` VALUES ('1566', '201', '石峰区');
INSERT INTO `t_pub_district` VALUES ('1567', '201', '云龙示范区');
INSERT INTO `t_pub_district` VALUES ('1568', '201', '株洲县');
INSERT INTO `t_pub_district` VALUES ('1569', '201', '攸县');
INSERT INTO `t_pub_district` VALUES ('1570', '201', '炎陵县');
INSERT INTO `t_pub_district` VALUES ('1571', '201', '醴陵市');
INSERT INTO `t_pub_district` VALUES ('1572', '202', '雨湖区');
INSERT INTO `t_pub_district` VALUES ('1573', '202', '岳塘区');
INSERT INTO `t_pub_district` VALUES ('1574', '202', '湘潭县');
INSERT INTO `t_pub_district` VALUES ('1575', '202', '韶山市');
INSERT INTO `t_pub_district` VALUES ('1576', '203', '雁峰区');
INSERT INTO `t_pub_district` VALUES ('1577', '203', '石鼓区');
INSERT INTO `t_pub_district` VALUES ('1578', '203', '珠晖区');
INSERT INTO `t_pub_district` VALUES ('1579', '203', '蒸湘区');
INSERT INTO `t_pub_district` VALUES ('1580', '203', '南岳区');
INSERT INTO `t_pub_district` VALUES ('1581', '203', '衡阳县');
INSERT INTO `t_pub_district` VALUES ('1582', '203', '衡南县');
INSERT INTO `t_pub_district` VALUES ('1583', '203', '衡山县');
INSERT INTO `t_pub_district` VALUES ('1584', '203', '衡东县');
INSERT INTO `t_pub_district` VALUES ('1585', '203', '祁东县');
INSERT INTO `t_pub_district` VALUES ('1586', '203', '常宁市');
INSERT INTO `t_pub_district` VALUES ('1587', '204', '双清区');
INSERT INTO `t_pub_district` VALUES ('1588', '204', '大祥区');
INSERT INTO `t_pub_district` VALUES ('1589', '204', '北塔区');
INSERT INTO `t_pub_district` VALUES ('1590', '204', '邵东县');
INSERT INTO `t_pub_district` VALUES ('1591', '204', '新邵县');
INSERT INTO `t_pub_district` VALUES ('1592', '204', '邵阳县');
INSERT INTO `t_pub_district` VALUES ('1593', '204', '隆回县');
INSERT INTO `t_pub_district` VALUES ('1594', '204', '洞口县');
INSERT INTO `t_pub_district` VALUES ('1595', '204', '绥宁县');
INSERT INTO `t_pub_district` VALUES ('1596', '204', '武宁县');
INSERT INTO `t_pub_district` VALUES ('1597', '204', '城步苗族自治县');
INSERT INTO `t_pub_district` VALUES ('1598', '205', '岳阳楼区');
INSERT INTO `t_pub_district` VALUES ('1599', '205', '君山区');
INSERT INTO `t_pub_district` VALUES ('1600', '205', '云溪区');
INSERT INTO `t_pub_district` VALUES ('1601', '205', '岳阳县');
INSERT INTO `t_pub_district` VALUES ('1602', '205', '湘阴县');
INSERT INTO `t_pub_district` VALUES ('1603', '205', '华容县');
INSERT INTO `t_pub_district` VALUES ('1604', '205', '汨罗市');
INSERT INTO `t_pub_district` VALUES ('1605', '205', '临湘市');
INSERT INTO `t_pub_district` VALUES ('1606', '206', '武陵区');
INSERT INTO `t_pub_district` VALUES ('1607', '206', '鼎城区');
INSERT INTO `t_pub_district` VALUES ('1608', '206', '安乡县');
INSERT INTO `t_pub_district` VALUES ('1609', '206', '汉寿县');
INSERT INTO `t_pub_district` VALUES ('1610', '206', '澧县');
INSERT INTO `t_pub_district` VALUES ('1611', '206', '临澧县');
INSERT INTO `t_pub_district` VALUES ('1612', '206', '桃源县');
INSERT INTO `t_pub_district` VALUES ('1613', '206', '津市市');
INSERT INTO `t_pub_district` VALUES ('1614', '207', '永定区');
INSERT INTO `t_pub_district` VALUES ('1615', '207', '武陵源区');
INSERT INTO `t_pub_district` VALUES ('1616', '207', '桑植县');
INSERT INTO `t_pub_district` VALUES ('1617', '208', '资阳区');
INSERT INTO `t_pub_district` VALUES ('1618', '208', '赫山区');
INSERT INTO `t_pub_district` VALUES ('1619', '208', '大通湖区');
INSERT INTO `t_pub_district` VALUES ('1620', '208', '桃江县');
INSERT INTO `t_pub_district` VALUES ('1621', '208', '南县');
INSERT INTO `t_pub_district` VALUES ('1622', '208', '沅江市');
INSERT INTO `t_pub_district` VALUES ('1623', '209', '娄星区');
INSERT INTO `t_pub_district` VALUES ('1624', '209', '双峰县');
INSERT INTO `t_pub_district` VALUES ('1625', '209', '涟源市');
INSERT INTO `t_pub_district` VALUES ('1626', '209', '冷水江市');
INSERT INTO `t_pub_district` VALUES ('1627', '210', '苏仙区');
INSERT INTO `t_pub_district` VALUES ('1628', '210', '北湖区');
INSERT INTO `t_pub_district` VALUES ('1629', '210', '安仁县');
INSERT INTO `t_pub_district` VALUES ('1630', '210', '桂阳县');
INSERT INTO `t_pub_district` VALUES ('1631', '210', '永兴县');
INSERT INTO `t_pub_district` VALUES ('1632', '210', '嘉禾县');
INSERT INTO `t_pub_district` VALUES ('1633', '210', '临武县');
INSERT INTO `t_pub_district` VALUES ('1634', '210', '汝城县');
INSERT INTO `t_pub_district` VALUES ('1635', '210', '桂东县');
INSERT INTO `t_pub_district` VALUES ('1636', '210', '资兴市');
INSERT INTO `t_pub_district` VALUES ('1637', '211', '零陵区');
INSERT INTO `t_pub_district` VALUES ('1638', '211', '冷水滩区');
INSERT INTO `t_pub_district` VALUES ('1639', '211', '道县');
INSERT INTO `t_pub_district` VALUES ('1640', '211', '祁阳县');
INSERT INTO `t_pub_district` VALUES ('1641', '211', '东安县');
INSERT INTO `t_pub_district` VALUES ('1642', '211', '双牌县');
INSERT INTO `t_pub_district` VALUES ('1643', '211', '江永县');
INSERT INTO `t_pub_district` VALUES ('1644', '211', '宁远县');
INSERT INTO `t_pub_district` VALUES ('1645', '211', '新田县');
INSERT INTO `t_pub_district` VALUES ('1646', '211', '江华瑶族自治县');
INSERT INTO `t_pub_district` VALUES ('1647', '211', '金洞管理区');
INSERT INTO `t_pub_district` VALUES ('1648', '211', '回龙圩管理区');
INSERT INTO `t_pub_district` VALUES ('1649', '212', '鹤城区');
INSERT INTO `t_pub_district` VALUES ('1650', '212', '中方县');
INSERT INTO `t_pub_district` VALUES ('1651', '212', '沅陵县');
INSERT INTO `t_pub_district` VALUES ('1652', '212', '辰溪县');
INSERT INTO `t_pub_district` VALUES ('1653', '212', '会同县');
INSERT INTO `t_pub_district` VALUES ('1654', '212', '麻阳苗族自治县');
INSERT INTO `t_pub_district` VALUES ('1655', '212', '新晃侗族自治县');
INSERT INTO `t_pub_district` VALUES ('1656', '212', '芷江侗族自治县');
INSERT INTO `t_pub_district` VALUES ('1657', '212', '靖州苗族侗族自治县');
INSERT INTO `t_pub_district` VALUES ('1658', '212', '通道侗族自治县');
INSERT INTO `t_pub_district` VALUES ('1659', '212', '洪江市');
INSERT INTO `t_pub_district` VALUES ('1660', '212', '洪江管理区');
INSERT INTO `t_pub_district` VALUES ('1661', '213', '吉首市');
INSERT INTO `t_pub_district` VALUES ('1662', '213', '泸溪县');
INSERT INTO `t_pub_district` VALUES ('1663', '213', '凤凰县');
INSERT INTO `t_pub_district` VALUES ('1664', '213', '花垣县');
INSERT INTO `t_pub_district` VALUES ('1665', '213', '保靖县');
INSERT INTO `t_pub_district` VALUES ('1666', '213', '古丈县');
INSERT INTO `t_pub_district` VALUES ('1667', '213', '永顺县');
INSERT INTO `t_pub_district` VALUES ('1668', '213', '龙山县');
INSERT INTO `t_pub_district` VALUES ('1669', '214', '浏阳市');
INSERT INTO `t_pub_district` VALUES ('1670', '215', '茶陵县');
INSERT INTO `t_pub_district` VALUES ('1671', '216', '湘乡市');
INSERT INTO `t_pub_district` VALUES ('1672', '217', '平江县');
INSERT INTO `t_pub_district` VALUES ('1673', '218', '石门县');
INSERT INTO `t_pub_district` VALUES ('1674', '219', '耒阳市');
INSERT INTO `t_pub_district` VALUES ('1675', '220', '武冈市');
INSERT INTO `t_pub_district` VALUES ('1676', '221', '慈利县');
INSERT INTO `t_pub_district` VALUES ('1677', '222', '安化县');
INSERT INTO `t_pub_district` VALUES ('1678', '223', '宜章县');
INSERT INTO `t_pub_district` VALUES ('1679', '224', '蓝山县');
INSERT INTO `t_pub_district` VALUES ('1680', '225', '溆浦县');
INSERT INTO `t_pub_district` VALUES ('1681', '226', '新化县');
INSERT INTO `t_pub_district` VALUES ('1682', '227', '越秀区');
INSERT INTO `t_pub_district` VALUES ('1683', '227', '荔湾区');
INSERT INTO `t_pub_district` VALUES ('1684', '227', '海珠区');
INSERT INTO `t_pub_district` VALUES ('1685', '227', '天河区');
INSERT INTO `t_pub_district` VALUES ('1686', '227', '白云区');
INSERT INTO `t_pub_district` VALUES ('1687', '227', '黄埔区');
INSERT INTO `t_pub_district` VALUES ('1688', '227', '花都区');
INSERT INTO `t_pub_district` VALUES ('1689', '227', '番禺区');
INSERT INTO `t_pub_district` VALUES ('1690', '227', '南沙区');
INSERT INTO `t_pub_district` VALUES ('1691', '227', '增城区');
INSERT INTO `t_pub_district` VALUES ('1692', '227', '从化区');
INSERT INTO `t_pub_district` VALUES ('1693', '228', '福田区');
INSERT INTO `t_pub_district` VALUES ('1694', '228', '罗湖区');
INSERT INTO `t_pub_district` VALUES ('1695', '228', '南山区');
INSERT INTO `t_pub_district` VALUES ('1696', '228', '盐田区');
INSERT INTO `t_pub_district` VALUES ('1697', '228', '宝安区');
INSERT INTO `t_pub_district` VALUES ('1698', '228', '龙岗区');
INSERT INTO `t_pub_district` VALUES ('1699', '229', '香洲区');
INSERT INTO `t_pub_district` VALUES ('1700', '229', '斗门区');
INSERT INTO `t_pub_district` VALUES ('1701', '229', '金湾区');
INSERT INTO `t_pub_district` VALUES ('1702', '230', '金平区');
INSERT INTO `t_pub_district` VALUES ('1703', '230', '龙湖区');
INSERT INTO `t_pub_district` VALUES ('1704', '230', '濠江区');
INSERT INTO `t_pub_district` VALUES ('1705', '230', '潮南区');
INSERT INTO `t_pub_district` VALUES ('1706', '230', '潮阳区');
INSERT INTO `t_pub_district` VALUES ('1707', '230', '澄海区');
INSERT INTO `t_pub_district` VALUES ('1708', '230', '南澳县');
INSERT INTO `t_pub_district` VALUES ('1709', '231', '禅城区');
INSERT INTO `t_pub_district` VALUES ('1710', '231', '高明区');
INSERT INTO `t_pub_district` VALUES ('1711', '231', '三水区');
INSERT INTO `t_pub_district` VALUES ('1712', '231', '南海区');
INSERT INTO `t_pub_district` VALUES ('1713', '232', '浈江区');
INSERT INTO `t_pub_district` VALUES ('1714', '232', '武江区');
INSERT INTO `t_pub_district` VALUES ('1715', '232', '曲江区');
INSERT INTO `t_pub_district` VALUES ('1716', '232', '乐昌市');
INSERT INTO `t_pub_district` VALUES ('1717', '232', '南雄市');
INSERT INTO `t_pub_district` VALUES ('1718', '232', '始兴县');
INSERT INTO `t_pub_district` VALUES ('1719', '232', '仁化县');
INSERT INTO `t_pub_district` VALUES ('1720', '232', '翁源县');
INSERT INTO `t_pub_district` VALUES ('1721', '232', '新丰县');
INSERT INTO `t_pub_district` VALUES ('1722', '232', '乳源瑶族自治县');
INSERT INTO `t_pub_district` VALUES ('1723', '233', '赤坎区');
INSERT INTO `t_pub_district` VALUES ('1724', '233', '霞山区');
INSERT INTO `t_pub_district` VALUES ('1725', '233', '坡头区');
INSERT INTO `t_pub_district` VALUES ('1726', '233', '麻章区');
INSERT INTO `t_pub_district` VALUES ('1727', '233', '廉江市');
INSERT INTO `t_pub_district` VALUES ('1728', '233', '吴川市');
INSERT INTO `t_pub_district` VALUES ('1729', '233', '雷州市');
INSERT INTO `t_pub_district` VALUES ('1730', '233', '遂溪县');
INSERT INTO `t_pub_district` VALUES ('1731', '233', '徐闻县');
INSERT INTO `t_pub_district` VALUES ('1732', '234', '端州区');
INSERT INTO `t_pub_district` VALUES ('1733', '234', '鼎湖区');
INSERT INTO `t_pub_district` VALUES ('1734', '234', '高要区');
INSERT INTO `t_pub_district` VALUES ('1735', '234', '四会市');
INSERT INTO `t_pub_district` VALUES ('1736', '234', '广宁县');
INSERT INTO `t_pub_district` VALUES ('1737', '234', '怀集县');
INSERT INTO `t_pub_district` VALUES ('1738', '234', '封开县');
INSERT INTO `t_pub_district` VALUES ('1739', '234', '德庆县');
INSERT INTO `t_pub_district` VALUES ('1740', '235', '江海区');
INSERT INTO `t_pub_district` VALUES ('1741', '235', '蓬江区');
INSERT INTO `t_pub_district` VALUES ('1742', '235', '新会区');
INSERT INTO `t_pub_district` VALUES ('1743', '235', '台山市');
INSERT INTO `t_pub_district` VALUES ('1744', '235', '鹤山市');
INSERT INTO `t_pub_district` VALUES ('1745', '235', '开平市');
INSERT INTO `t_pub_district` VALUES ('1746', '235', '恩平市');
INSERT INTO `t_pub_district` VALUES ('1747', '236', '茂南区');
INSERT INTO `t_pub_district` VALUES ('1748', '236', '电白区');
INSERT INTO `t_pub_district` VALUES ('1749', '236', '高州市');
INSERT INTO `t_pub_district` VALUES ('1750', '236', '化州市');
INSERT INTO `t_pub_district` VALUES ('1751', '236', '信宜市');
INSERT INTO `t_pub_district` VALUES ('1752', '237', '惠城区');
INSERT INTO `t_pub_district` VALUES ('1753', '237', '惠阳区');
INSERT INTO `t_pub_district` VALUES ('1754', '237', '博罗县');
INSERT INTO `t_pub_district` VALUES ('1755', '237', '龙门县');
INSERT INTO `t_pub_district` VALUES ('1756', '237', '惠东县');
INSERT INTO `t_pub_district` VALUES ('1757', '238', '梅江区');
INSERT INTO `t_pub_district` VALUES ('1758', '238', '梅县区');
INSERT INTO `t_pub_district` VALUES ('1759', '238', '兴宁市');
INSERT INTO `t_pub_district` VALUES ('1760', '238', '大埔县');
INSERT INTO `t_pub_district` VALUES ('1761', '238', '丰顺县');
INSERT INTO `t_pub_district` VALUES ('1762', '238', '五华县');
INSERT INTO `t_pub_district` VALUES ('1763', '238', '平远县');
INSERT INTO `t_pub_district` VALUES ('1764', '238', '蕉岭县');
INSERT INTO `t_pub_district` VALUES ('1765', '239', '城区');
INSERT INTO `t_pub_district` VALUES ('1766', '239', '陆丰市');
INSERT INTO `t_pub_district` VALUES ('1767', '239', '海丰县');
INSERT INTO `t_pub_district` VALUES ('1768', '239', '陆河县');
INSERT INTO `t_pub_district` VALUES ('1769', '240', '源城区');
INSERT INTO `t_pub_district` VALUES ('1770', '240', '龙川县');
INSERT INTO `t_pub_district` VALUES ('1771', '240', '连平县');
INSERT INTO `t_pub_district` VALUES ('1772', '240', '东源县');
INSERT INTO `t_pub_district` VALUES ('1773', '240', '和平县');
INSERT INTO `t_pub_district` VALUES ('1774', '240', '紫金县');
INSERT INTO `t_pub_district` VALUES ('1775', '241', '江城区');
INSERT INTO `t_pub_district` VALUES ('1776', '241', '阳东区');
INSERT INTO `t_pub_district` VALUES ('1777', '241', '阳春市');
INSERT INTO `t_pub_district` VALUES ('1778', '241', '阳西县');
INSERT INTO `t_pub_district` VALUES ('1779', '242', '清城区');
INSERT INTO `t_pub_district` VALUES ('1780', '242', '清新区');
INSERT INTO `t_pub_district` VALUES ('1781', '242', '英德市');
INSERT INTO `t_pub_district` VALUES ('1782', '242', '连州市');
INSERT INTO `t_pub_district` VALUES ('1783', '242', '佛冈县');
INSERT INTO `t_pub_district` VALUES ('1784', '242', '阳山县');
INSERT INTO `t_pub_district` VALUES ('1785', '242', '连山壮族瑶族自治县');
INSERT INTO `t_pub_district` VALUES ('1786', '242', '连南瑶族自治县');
INSERT INTO `t_pub_district` VALUES ('1787', '243', '东莞市');
INSERT INTO `t_pub_district` VALUES ('1788', '244', '中山市');
INSERT INTO `t_pub_district` VALUES ('1789', '245', '湘桥区');
INSERT INTO `t_pub_district` VALUES ('1790', '245', '潮安区');
INSERT INTO `t_pub_district` VALUES ('1791', '245', '饶平县');
INSERT INTO `t_pub_district` VALUES ('1792', '246', '榕城区');
INSERT INTO `t_pub_district` VALUES ('1793', '246', '揭东区');
INSERT INTO `t_pub_district` VALUES ('1794', '246', '普宁市');
INSERT INTO `t_pub_district` VALUES ('1795', '246', '惠来县');
INSERT INTO `t_pub_district` VALUES ('1796', '246', '揭西县');
INSERT INTO `t_pub_district` VALUES ('1797', '247', '云城区');
INSERT INTO `t_pub_district` VALUES ('1798', '247', '云安区');
INSERT INTO `t_pub_district` VALUES ('1799', '247', '罗定市');
INSERT INTO `t_pub_district` VALUES ('1800', '247', '新兴县');
INSERT INTO `t_pub_district` VALUES ('1801', '247', '郁南县');
INSERT INTO `t_pub_district` VALUES ('1802', '248', '佛山市顺德区');
INSERT INTO `t_pub_district` VALUES ('1803', '249', '秀英区');
INSERT INTO `t_pub_district` VALUES ('1804', '249', '龙华区');
INSERT INTO `t_pub_district` VALUES ('1805', '249', '琼山区');
INSERT INTO `t_pub_district` VALUES ('1806', '249', '美兰区');
INSERT INTO `t_pub_district` VALUES ('1807', '250', '吉阳区');
INSERT INTO `t_pub_district` VALUES ('1808', '250', '崖州区');
INSERT INTO `t_pub_district` VALUES ('1809', '250', '天涯区');
INSERT INTO `t_pub_district` VALUES ('1810', '250', '海棠区');
INSERT INTO `t_pub_district` VALUES ('1811', '251', '西沙群岛');
INSERT INTO `t_pub_district` VALUES ('1812', '251', '南沙群岛');
INSERT INTO `t_pub_district` VALUES ('1813', '251', '中沙群岛');
INSERT INTO `t_pub_district` VALUES ('1814', '252', '那大镇');
INSERT INTO `t_pub_district` VALUES ('1815', '252', '和庆镇');
INSERT INTO `t_pub_district` VALUES ('1816', '252', '南丰镇');
INSERT INTO `t_pub_district` VALUES ('1817', '252', '大成镇');
INSERT INTO `t_pub_district` VALUES ('1818', '252', '雅星镇');
INSERT INTO `t_pub_district` VALUES ('1819', '252', '兰洋镇');
INSERT INTO `t_pub_district` VALUES ('1820', '252', '光村镇');
INSERT INTO `t_pub_district` VALUES ('1821', '252', '木棠镇');
INSERT INTO `t_pub_district` VALUES ('1822', '252', '海头镇');
INSERT INTO `t_pub_district` VALUES ('1823', '252', '峨蔓镇');
INSERT INTO `t_pub_district` VALUES ('1824', '252', '三都镇');
INSERT INTO `t_pub_district` VALUES ('1825', '252', '王五镇');
INSERT INTO `t_pub_district` VALUES ('1826', '252', '白马井镇');
INSERT INTO `t_pub_district` VALUES ('1827', '252', '中和镇');
INSERT INTO `t_pub_district` VALUES ('1828', '252', '排浦镇');
INSERT INTO `t_pub_district` VALUES ('1829', '252', '东成镇');
INSERT INTO `t_pub_district` VALUES ('1830', '252', '新州镇');
INSERT INTO `t_pub_district` VALUES ('1831', '253', '五指山市');
INSERT INTO `t_pub_district` VALUES ('1832', '254', '文昌市');
INSERT INTO `t_pub_district` VALUES ('1833', '255', '琼海市');
INSERT INTO `t_pub_district` VALUES ('1834', '256', '万宁市');
INSERT INTO `t_pub_district` VALUES ('1835', '257', '东方市');
INSERT INTO `t_pub_district` VALUES ('1836', '258', '定安县');
INSERT INTO `t_pub_district` VALUES ('1837', '259', '屯昌县');
INSERT INTO `t_pub_district` VALUES ('1838', '260', '澄迈县');
INSERT INTO `t_pub_district` VALUES ('1839', '261', '临高县');
INSERT INTO `t_pub_district` VALUES ('1840', '262', '琼中黎族苗族自治县');
INSERT INTO `t_pub_district` VALUES ('1841', '263', '保亭黎族苗族自治县');
INSERT INTO `t_pub_district` VALUES ('1842', '264', '白沙黎族自治县');
INSERT INTO `t_pub_district` VALUES ('1843', '265', '昌江黎族自治县');
INSERT INTO `t_pub_district` VALUES ('1844', '266', '乐东黎族自治县');
INSERT INTO `t_pub_district` VALUES ('1845', '267', '陵水黎族自治县');
INSERT INTO `t_pub_district` VALUES ('1846', '268', '锦江区');
INSERT INTO `t_pub_district` VALUES ('1847', '268', '青羊区');
INSERT INTO `t_pub_district` VALUES ('1848', '268', '金牛区');
INSERT INTO `t_pub_district` VALUES ('1849', '268', '武侯区');
INSERT INTO `t_pub_district` VALUES ('1850', '268', '成华区');
INSERT INTO `t_pub_district` VALUES ('1851', '268', '青白江区');
INSERT INTO `t_pub_district` VALUES ('1852', '268', '龙泉驿区');
INSERT INTO `t_pub_district` VALUES ('1853', '268', '新都区');
INSERT INTO `t_pub_district` VALUES ('1854', '268', '温江区');
INSERT INTO `t_pub_district` VALUES ('1855', '268', '双流区');
INSERT INTO `t_pub_district` VALUES ('1856', '268', '郫县');
INSERT INTO `t_pub_district` VALUES ('1857', '268', '金堂县');
INSERT INTO `t_pub_district` VALUES ('1858', '268', '大邑县');
INSERT INTO `t_pub_district` VALUES ('1859', '268', '蒲江县');
INSERT INTO `t_pub_district` VALUES ('1860', '268', '新津县');
INSERT INTO `t_pub_district` VALUES ('1861', '268', '都江堰市');
INSERT INTO `t_pub_district` VALUES ('1862', '268', '彭州市');
INSERT INTO `t_pub_district` VALUES ('1863', '268', '崇州市');
INSERT INTO `t_pub_district` VALUES ('1864', '268', '邛崃市');
INSERT INTO `t_pub_district` VALUES ('1865', '268', '简阳市');
INSERT INTO `t_pub_district` VALUES ('1866', '269', '涪城区');
INSERT INTO `t_pub_district` VALUES ('1867', '269', '游仙区');
INSERT INTO `t_pub_district` VALUES ('1868', '269', '安州区');
INSERT INTO `t_pub_district` VALUES ('1869', '269', '梓潼县');
INSERT INTO `t_pub_district` VALUES ('1870', '269', '三台县');
INSERT INTO `t_pub_district` VALUES ('1871', '269', '盐亭县');
INSERT INTO `t_pub_district` VALUES ('1872', '269', '平武县');
INSERT INTO `t_pub_district` VALUES ('1873', '269', '江油市');
INSERT INTO `t_pub_district` VALUES ('1874', '269', '北川羌族自治县');
INSERT INTO `t_pub_district` VALUES ('1875', '270', '自流井区');
INSERT INTO `t_pub_district` VALUES ('1876', '270', '贡井区');
INSERT INTO `t_pub_district` VALUES ('1877', '270', '大安区');
INSERT INTO `t_pub_district` VALUES ('1878', '270', '沿滩区');
INSERT INTO `t_pub_district` VALUES ('1879', '270', '荣县');
INSERT INTO `t_pub_district` VALUES ('1880', '270', '富顺县');
INSERT INTO `t_pub_district` VALUES ('1881', '271', '东区');
INSERT INTO `t_pub_district` VALUES ('1882', '271', '西区');
INSERT INTO `t_pub_district` VALUES ('1883', '271', '仁和区');
INSERT INTO `t_pub_district` VALUES ('1884', '271', '米易县');
INSERT INTO `t_pub_district` VALUES ('1885', '271', '盐边县');
INSERT INTO `t_pub_district` VALUES ('1886', '272', '江阳区');
INSERT INTO `t_pub_district` VALUES ('1887', '272', '龙马潭区');
INSERT INTO `t_pub_district` VALUES ('1888', '272', '纳溪区');
INSERT INTO `t_pub_district` VALUES ('1889', '272', '泸县');
INSERT INTO `t_pub_district` VALUES ('1890', '272', '合江县');
INSERT INTO `t_pub_district` VALUES ('1891', '272', '叙永县');
INSERT INTO `t_pub_district` VALUES ('1892', '272', '古蔺县');
INSERT INTO `t_pub_district` VALUES ('1893', '273', '旌阳区');
INSERT INTO `t_pub_district` VALUES ('1894', '273', '广汉市');
INSERT INTO `t_pub_district` VALUES ('1895', '273', '什邡市');
INSERT INTO `t_pub_district` VALUES ('1896', '273', '绵竹市');
INSERT INTO `t_pub_district` VALUES ('1897', '273', '中江县');
INSERT INTO `t_pub_district` VALUES ('1898', '273', '罗江县');
INSERT INTO `t_pub_district` VALUES ('1899', '274', '利州区');
INSERT INTO `t_pub_district` VALUES ('1900', '274', '昭化区');
INSERT INTO `t_pub_district` VALUES ('1901', '274', '朝天区');
INSERT INTO `t_pub_district` VALUES ('1902', '274', '旺苍县');
INSERT INTO `t_pub_district` VALUES ('1903', '274', '青川县');
INSERT INTO `t_pub_district` VALUES ('1904', '274', '剑阁县');
INSERT INTO `t_pub_district` VALUES ('1905', '274', '苍溪县');
INSERT INTO `t_pub_district` VALUES ('1906', '275', '船山区');
INSERT INTO `t_pub_district` VALUES ('1907', '275', '安居区');
INSERT INTO `t_pub_district` VALUES ('1908', '275', '射洪县');
INSERT INTO `t_pub_district` VALUES ('1909', '275', '蓬溪县');
INSERT INTO `t_pub_district` VALUES ('1910', '275', '大英县');
INSERT INTO `t_pub_district` VALUES ('1911', '276', '市中区');
INSERT INTO `t_pub_district` VALUES ('1912', '276', '东兴区');
INSERT INTO `t_pub_district` VALUES ('1913', '276', '资中县');
INSERT INTO `t_pub_district` VALUES ('1914', '276', '隆昌县');
INSERT INTO `t_pub_district` VALUES ('1915', '276', '威远县');
INSERT INTO `t_pub_district` VALUES ('1916', '277', '市中区');
INSERT INTO `t_pub_district` VALUES ('1917', '277', '沙湾区');
INSERT INTO `t_pub_district` VALUES ('1918', '277', '五通桥区');
INSERT INTO `t_pub_district` VALUES ('1919', '277', '金口河区');
INSERT INTO `t_pub_district` VALUES ('1920', '277', '犍为县');
INSERT INTO `t_pub_district` VALUES ('1921', '277', '井研县');
INSERT INTO `t_pub_district` VALUES ('1922', '277', '夹江县');
INSERT INTO `t_pub_district` VALUES ('1923', '277', '沐川县');
INSERT INTO `t_pub_district` VALUES ('1924', '277', '峨眉山市');
INSERT INTO `t_pub_district` VALUES ('1925', '277', '峨边彝族自治县');
INSERT INTO `t_pub_district` VALUES ('1926', '277', '马边彝族自治县');
INSERT INTO `t_pub_district` VALUES ('1927', '278', '雁江区');
INSERT INTO `t_pub_district` VALUES ('1928', '278', '安岳县');
INSERT INTO `t_pub_district` VALUES ('1929', '278', '乐至县');
INSERT INTO `t_pub_district` VALUES ('1930', '279', '翠屏区');
INSERT INTO `t_pub_district` VALUES ('1931', '279', '南溪区');
INSERT INTO `t_pub_district` VALUES ('1932', '279', '宜宾县');
INSERT INTO `t_pub_district` VALUES ('1933', '279', '江安县');
INSERT INTO `t_pub_district` VALUES ('1934', '279', '长宁县');
INSERT INTO `t_pub_district` VALUES ('1935', '279', '高县');
INSERT INTO `t_pub_district` VALUES ('1936', '279', '筠连县');
INSERT INTO `t_pub_district` VALUES ('1937', '279', '珙县');
INSERT INTO `t_pub_district` VALUES ('1938', '279', '兴文县');
INSERT INTO `t_pub_district` VALUES ('1939', '279', '屏山县');
INSERT INTO `t_pub_district` VALUES ('1940', '280', '顺庆区');
INSERT INTO `t_pub_district` VALUES ('1941', '280', '高坪区');
INSERT INTO `t_pub_district` VALUES ('1942', '280', '嘉陵区');
INSERT INTO `t_pub_district` VALUES ('1943', '280', '西充县');
INSERT INTO `t_pub_district` VALUES ('1944', '280', '南部县');
INSERT INTO `t_pub_district` VALUES ('1945', '280', '蓬安县');
INSERT INTO `t_pub_district` VALUES ('1946', '280', '营山县');
INSERT INTO `t_pub_district` VALUES ('1947', '280', '仪陇县');
INSERT INTO `t_pub_district` VALUES ('1948', '280', '阆中市');
INSERT INTO `t_pub_district` VALUES ('1949', '281', '通川区');
INSERT INTO `t_pub_district` VALUES ('1950', '281', '达川区');
INSERT INTO `t_pub_district` VALUES ('1951', '281', '宣汉县');
INSERT INTO `t_pub_district` VALUES ('1952', '281', '开江县');
INSERT INTO `t_pub_district` VALUES ('1953', '281', '大竹县');
INSERT INTO `t_pub_district` VALUES ('1954', '281', '渠县');
INSERT INTO `t_pub_district` VALUES ('1955', '281', '万源市');
INSERT INTO `t_pub_district` VALUES ('1956', '282', '雨城区');
INSERT INTO `t_pub_district` VALUES ('1957', '282', '名山区');
INSERT INTO `t_pub_district` VALUES ('1958', '282', '荥经县');
INSERT INTO `t_pub_district` VALUES ('1959', '282', '汉源县');
INSERT INTO `t_pub_district` VALUES ('1960', '282', '石棉县');
INSERT INTO `t_pub_district` VALUES ('1961', '282', '天全县');
INSERT INTO `t_pub_district` VALUES ('1962', '282', '芦山县');
INSERT INTO `t_pub_district` VALUES ('1963', '282', '宝兴县');
INSERT INTO `t_pub_district` VALUES ('1964', '283', '马尔康市');
INSERT INTO `t_pub_district` VALUES ('1965', '283', '金川县');
INSERT INTO `t_pub_district` VALUES ('1966', '283', '小金县');
INSERT INTO `t_pub_district` VALUES ('1967', '283', '阿坝县');
INSERT INTO `t_pub_district` VALUES ('1968', '283', '若尔盖县');
INSERT INTO `t_pub_district` VALUES ('1969', '283', '红原县');
INSERT INTO `t_pub_district` VALUES ('1970', '283', '壤塘县');
INSERT INTO `t_pub_district` VALUES ('1971', '283', '汶川县');
INSERT INTO `t_pub_district` VALUES ('1972', '283', '理县');
INSERT INTO `t_pub_district` VALUES ('1973', '283', '茂县');
INSERT INTO `t_pub_district` VALUES ('1974', '283', '松潘县');
INSERT INTO `t_pub_district` VALUES ('1975', '283', '九寨沟县');
INSERT INTO `t_pub_district` VALUES ('1976', '283', '黑水县');
INSERT INTO `t_pub_district` VALUES ('1977', '284', '康定市');
INSERT INTO `t_pub_district` VALUES ('1978', '284', '泸定县');
INSERT INTO `t_pub_district` VALUES ('1979', '284', '丹巴县');
INSERT INTO `t_pub_district` VALUES ('1980', '284', '九龙县');
INSERT INTO `t_pub_district` VALUES ('1981', '284', '雅江县');
INSERT INTO `t_pub_district` VALUES ('1982', '284', '道孚县');
INSERT INTO `t_pub_district` VALUES ('1983', '284', '炉霍县');
INSERT INTO `t_pub_district` VALUES ('1984', '284', '甘孜县');
INSERT INTO `t_pub_district` VALUES ('1985', '284', '新龙县');
INSERT INTO `t_pub_district` VALUES ('1986', '284', '德格县');
INSERT INTO `t_pub_district` VALUES ('1987', '284', '白玉县');
INSERT INTO `t_pub_district` VALUES ('1988', '284', '石渠县');
INSERT INTO `t_pub_district` VALUES ('1989', '284', '色达县');
INSERT INTO `t_pub_district` VALUES ('1990', '284', '理塘县');
INSERT INTO `t_pub_district` VALUES ('1991', '284', '巴塘县');
INSERT INTO `t_pub_district` VALUES ('1992', '284', '乡城县');
INSERT INTO `t_pub_district` VALUES ('1993', '284', '稻城县');
INSERT INTO `t_pub_district` VALUES ('1994', '284', '得荣县');
INSERT INTO `t_pub_district` VALUES ('1995', '285', '西昌市');
INSERT INTO `t_pub_district` VALUES ('1996', '285', '德昌县');
INSERT INTO `t_pub_district` VALUES ('1997', '285', '会理县');
INSERT INTO `t_pub_district` VALUES ('1998', '285', '会东县');
INSERT INTO `t_pub_district` VALUES ('1999', '285', '宁南县');
INSERT INTO `t_pub_district` VALUES ('2000', '285', '普格县');
INSERT INTO `t_pub_district` VALUES ('2001', '285', '布拖县');
INSERT INTO `t_pub_district` VALUES ('2002', '285', '昭觉县');
INSERT INTO `t_pub_district` VALUES ('2003', '285', '金阳县');
INSERT INTO `t_pub_district` VALUES ('2004', '285', '雷波县');
INSERT INTO `t_pub_district` VALUES ('2005', '285', '美姑县');
INSERT INTO `t_pub_district` VALUES ('2006', '285', '甘洛县');
INSERT INTO `t_pub_district` VALUES ('2007', '285', '越西县');
INSERT INTO `t_pub_district` VALUES ('2008', '285', '喜德县');
INSERT INTO `t_pub_district` VALUES ('2009', '285', '冕宁县');
INSERT INTO `t_pub_district` VALUES ('2010', '285', '盐源县');
INSERT INTO `t_pub_district` VALUES ('2011', '285', '木里藏族自治县');
INSERT INTO `t_pub_district` VALUES ('2012', '286', '广安区');
INSERT INTO `t_pub_district` VALUES ('2013', '286', '前锋区');
INSERT INTO `t_pub_district` VALUES ('2014', '286', '邻水县');
INSERT INTO `t_pub_district` VALUES ('2015', '286', '武胜县');
INSERT INTO `t_pub_district` VALUES ('2016', '286', '岳池县');
INSERT INTO `t_pub_district` VALUES ('2017', '286', '华蓥市');
INSERT INTO `t_pub_district` VALUES ('2018', '287', '巴州区');
INSERT INTO `t_pub_district` VALUES ('2019', '287', '恩阳区');
INSERT INTO `t_pub_district` VALUES ('2020', '287', '平昌县');
INSERT INTO `t_pub_district` VALUES ('2021', '287', '通江县');
INSERT INTO `t_pub_district` VALUES ('2022', '287', '南江县');
INSERT INTO `t_pub_district` VALUES ('2023', '288', '东坡区');
INSERT INTO `t_pub_district` VALUES ('2024', '288', '彭山区');
INSERT INTO `t_pub_district` VALUES ('2025', '288', '仁寿县');
INSERT INTO `t_pub_district` VALUES ('2026', '288', '丹棱县');
INSERT INTO `t_pub_district` VALUES ('2027', '288', '青神县');
INSERT INTO `t_pub_district` VALUES ('2028', '288', '洪雅县');
INSERT INTO `t_pub_district` VALUES ('2029', '289', '南明区');
INSERT INTO `t_pub_district` VALUES ('2030', '289', '云岩区');
INSERT INTO `t_pub_district` VALUES ('2031', '289', '花溪区');
INSERT INTO `t_pub_district` VALUES ('2032', '289', '乌当区');
INSERT INTO `t_pub_district` VALUES ('2033', '289', '白云区');
INSERT INTO `t_pub_district` VALUES ('2034', '289', '观山湖区');
INSERT INTO `t_pub_district` VALUES ('2035', '289', '开阳县');
INSERT INTO `t_pub_district` VALUES ('2036', '289', '息烽县');
INSERT INTO `t_pub_district` VALUES ('2037', '289', '修文县');
INSERT INTO `t_pub_district` VALUES ('2038', '289', '清镇市');
INSERT INTO `t_pub_district` VALUES ('2039', '290', '钟山区');
INSERT INTO `t_pub_district` VALUES ('2040', '290', '六枝特区');
INSERT INTO `t_pub_district` VALUES ('2041', '290', '水城县');
INSERT INTO `t_pub_district` VALUES ('2042', '290', '盘县');
INSERT INTO `t_pub_district` VALUES ('2043', '291', '红花岗区');
INSERT INTO `t_pub_district` VALUES ('2044', '291', '汇川区');
INSERT INTO `t_pub_district` VALUES ('2045', '291', '播州区');
INSERT INTO `t_pub_district` VALUES ('2046', '291', '桐梓县');
INSERT INTO `t_pub_district` VALUES ('2047', '291', '绥阳县');
INSERT INTO `t_pub_district` VALUES ('2048', '291', '正安县');
INSERT INTO `t_pub_district` VALUES ('2049', '291', '道真县');
INSERT INTO `t_pub_district` VALUES ('2050', '291', '务川县');
INSERT INTO `t_pub_district` VALUES ('2051', '291', '凤冈县');
INSERT INTO `t_pub_district` VALUES ('2052', '291', '湄潭县');
INSERT INTO `t_pub_district` VALUES ('2053', '291', '余庆县');
INSERT INTO `t_pub_district` VALUES ('2054', '291', '习水县');
INSERT INTO `t_pub_district` VALUES ('2055', '291', '赤水市');
INSERT INTO `t_pub_district` VALUES ('2056', '291', '仁怀市');
INSERT INTO `t_pub_district` VALUES ('2057', '292', '镇宁县');
INSERT INTO `t_pub_district` VALUES ('2058', '292', '西秀区');
INSERT INTO `t_pub_district` VALUES ('2059', '292', '平坝区');
INSERT INTO `t_pub_district` VALUES ('2060', '292', '普定县');
INSERT INTO `t_pub_district` VALUES ('2061', '292', '关岭县');
INSERT INTO `t_pub_district` VALUES ('2062', '292', '紫云县');
INSERT INTO `t_pub_district` VALUES ('2063', '293', '七星关区');
INSERT INTO `t_pub_district` VALUES ('2064', '293', '大方县');
INSERT INTO `t_pub_district` VALUES ('2065', '293', '黔西县');
INSERT INTO `t_pub_district` VALUES ('2066', '293', '金沙县');
INSERT INTO `t_pub_district` VALUES ('2067', '293', '织金县');
INSERT INTO `t_pub_district` VALUES ('2068', '293', '纳雍县');
INSERT INTO `t_pub_district` VALUES ('2069', '293', '威宁县');
INSERT INTO `t_pub_district` VALUES ('2070', '293', '赫章县');
INSERT INTO `t_pub_district` VALUES ('2071', '294', '碧江区');
INSERT INTO `t_pub_district` VALUES ('2072', '294', '万山区');
INSERT INTO `t_pub_district` VALUES ('2073', '294', '江口县');
INSERT INTO `t_pub_district` VALUES ('2074', '294', '玉屏县');
INSERT INTO `t_pub_district` VALUES ('2075', '294', '石阡县');
INSERT INTO `t_pub_district` VALUES ('2076', '294', '思南县');
INSERT INTO `t_pub_district` VALUES ('2077', '294', '印江县');
INSERT INTO `t_pub_district` VALUES ('2078', '294', '德江县');
INSERT INTO `t_pub_district` VALUES ('2079', '294', '沿河县');
INSERT INTO `t_pub_district` VALUES ('2080', '294', '松桃苗族自治县');
INSERT INTO `t_pub_district` VALUES ('2081', '295', '兴义市');
INSERT INTO `t_pub_district` VALUES ('2082', '295', '兴仁县');
INSERT INTO `t_pub_district` VALUES ('2083', '295', '普安县');
INSERT INTO `t_pub_district` VALUES ('2084', '295', '晴隆县');
INSERT INTO `t_pub_district` VALUES ('2085', '295', '贞丰县');
INSERT INTO `t_pub_district` VALUES ('2086', '295', '望谟县');
INSERT INTO `t_pub_district` VALUES ('2087', '295', '册亨县');
INSERT INTO `t_pub_district` VALUES ('2088', '295', '安龙县');
INSERT INTO `t_pub_district` VALUES ('2089', '296', '镇远县');
INSERT INTO `t_pub_district` VALUES ('2090', '296', '凯里市');
INSERT INTO `t_pub_district` VALUES ('2091', '296', '黄平县');
INSERT INTO `t_pub_district` VALUES ('2092', '296', '施秉县');
INSERT INTO `t_pub_district` VALUES ('2093', '296', '三穗县');
INSERT INTO `t_pub_district` VALUES ('2094', '296', '岑巩县');
INSERT INTO `t_pub_district` VALUES ('2095', '296', '天柱县');
INSERT INTO `t_pub_district` VALUES ('2096', '296', '锦屏县');
INSERT INTO `t_pub_district` VALUES ('2097', '296', '剑河县');
INSERT INTO `t_pub_district` VALUES ('2098', '296', '台江县');
INSERT INTO `t_pub_district` VALUES ('2099', '296', '黎平县');
INSERT INTO `t_pub_district` VALUES ('2100', '296', '榕江县');
INSERT INTO `t_pub_district` VALUES ('2101', '296', '从江县');
INSERT INTO `t_pub_district` VALUES ('2102', '296', '雷山县');
INSERT INTO `t_pub_district` VALUES ('2103', '296', '麻江县');
INSERT INTO `t_pub_district` VALUES ('2104', '296', '丹寨县');
INSERT INTO `t_pub_district` VALUES ('2105', '297', '都匀市');
INSERT INTO `t_pub_district` VALUES ('2106', '297', '福泉市');
INSERT INTO `t_pub_district` VALUES ('2107', '297', '荔波县');
INSERT INTO `t_pub_district` VALUES ('2108', '297', '贵定县');
INSERT INTO `t_pub_district` VALUES ('2109', '297', '瓮安县');
INSERT INTO `t_pub_district` VALUES ('2110', '297', '独山县');
INSERT INTO `t_pub_district` VALUES ('2111', '297', '平塘县');
INSERT INTO `t_pub_district` VALUES ('2112', '297', '罗甸县');
INSERT INTO `t_pub_district` VALUES ('2113', '297', '长顺县');
INSERT INTO `t_pub_district` VALUES ('2114', '297', '龙里县');
INSERT INTO `t_pub_district` VALUES ('2115', '297', '惠水县');
INSERT INTO `t_pub_district` VALUES ('2116', '297', '三都水族自治县');
INSERT INTO `t_pub_district` VALUES ('2117', '298', '仁怀市');
INSERT INTO `t_pub_district` VALUES ('2118', '299', '赤水市');
INSERT INTO `t_pub_district` VALUES ('2119', '300', '威宁彝族回族苗族自治县');
INSERT INTO `t_pub_district` VALUES ('2120', '301', '福泉市');
INSERT INTO `t_pub_district` VALUES ('2121', '302', '镇远县');
INSERT INTO `t_pub_district` VALUES ('2122', '303', '黎平县');
INSERT INTO `t_pub_district` VALUES ('2123', '304', '呈贡区');
INSERT INTO `t_pub_district` VALUES ('2124', '304', '盘龙区');
INSERT INTO `t_pub_district` VALUES ('2125', '304', '五华区');
INSERT INTO `t_pub_district` VALUES ('2126', '304', '官渡区');
INSERT INTO `t_pub_district` VALUES ('2127', '304', '西山区');
INSERT INTO `t_pub_district` VALUES ('2128', '304', '东川区');
INSERT INTO `t_pub_district` VALUES ('2129', '304', '安宁市');
INSERT INTO `t_pub_district` VALUES ('2130', '304', '晋宁县');
INSERT INTO `t_pub_district` VALUES ('2131', '304', '富民县');
INSERT INTO `t_pub_district` VALUES ('2132', '304', '宜良县');
INSERT INTO `t_pub_district` VALUES ('2133', '304', '嵩明县');
INSERT INTO `t_pub_district` VALUES ('2134', '304', '石林彝族自治县');
INSERT INTO `t_pub_district` VALUES ('2135', '304', '禄劝彝族苗族自治县');
INSERT INTO `t_pub_district` VALUES ('2136', '304', '寻甸回族彝族自治县');
INSERT INTO `t_pub_district` VALUES ('2137', '305', '麒麟区');
INSERT INTO `t_pub_district` VALUES ('2138', '305', '沾益区');
INSERT INTO `t_pub_district` VALUES ('2139', '305', '宣威市');
INSERT INTO `t_pub_district` VALUES ('2140', '305', '马龙县');
INSERT INTO `t_pub_district` VALUES ('2141', '305', '富源县');
INSERT INTO `t_pub_district` VALUES ('2142', '305', '罗平县');
INSERT INTO `t_pub_district` VALUES ('2143', '305', '师宗县');
INSERT INTO `t_pub_district` VALUES ('2144', '305', '陆良县');
INSERT INTO `t_pub_district` VALUES ('2145', '305', '会泽县');
INSERT INTO `t_pub_district` VALUES ('2146', '306', '红塔区');
INSERT INTO `t_pub_district` VALUES ('2147', '306', '江川区');
INSERT INTO `t_pub_district` VALUES ('2148', '306', '澄江县');
INSERT INTO `t_pub_district` VALUES ('2149', '306', '通海县');
INSERT INTO `t_pub_district` VALUES ('2150', '306', '华宁县');
INSERT INTO `t_pub_district` VALUES ('2151', '306', '易门县');
INSERT INTO `t_pub_district` VALUES ('2152', '306', '峨山彝族自治县');
INSERT INTO `t_pub_district` VALUES ('2153', '306', '新平彝族傣族自治县');
INSERT INTO `t_pub_district` VALUES ('2154', '306', '元江哈尼族彝族傣族自治县');
INSERT INTO `t_pub_district` VALUES ('2155', '307', '昭阳区');
INSERT INTO `t_pub_district` VALUES ('2156', '307', '鲁甸县');
INSERT INTO `t_pub_district` VALUES ('2157', '307', '巧家县');
INSERT INTO `t_pub_district` VALUES ('2158', '307', '盐津县');
INSERT INTO `t_pub_district` VALUES ('2159', '307', '大关县');
INSERT INTO `t_pub_district` VALUES ('2160', '307', '永善县');
INSERT INTO `t_pub_district` VALUES ('2161', '307', '绥江县');
INSERT INTO `t_pub_district` VALUES ('2162', '307', '镇雄县');
INSERT INTO `t_pub_district` VALUES ('2163', '307', '彝良县');
INSERT INTO `t_pub_district` VALUES ('2164', '307', '威信县');
INSERT INTO `t_pub_district` VALUES ('2165', '307', '水富县');
INSERT INTO `t_pub_district` VALUES ('2166', '308', '隆阳区');
INSERT INTO `t_pub_district` VALUES ('2167', '308', '腾冲市');
INSERT INTO `t_pub_district` VALUES ('2168', '308', '施甸县');
INSERT INTO `t_pub_district` VALUES ('2169', '308', '龙陵县');
INSERT INTO `t_pub_district` VALUES ('2170', '308', '昌宁县');
INSERT INTO `t_pub_district` VALUES ('2171', '309', '古城区');
INSERT INTO `t_pub_district` VALUES ('2172', '309', '永胜县');
INSERT INTO `t_pub_district` VALUES ('2173', '309', '华坪县');
INSERT INTO `t_pub_district` VALUES ('2174', '309', '玉龙纳西族自治县');
INSERT INTO `t_pub_district` VALUES ('2175', '309', '宁蒗彝族自治县');
INSERT INTO `t_pub_district` VALUES ('2176', '310', '思茅区');
INSERT INTO `t_pub_district` VALUES ('2177', '310', '宁洱哈尼族彝族自治县');
INSERT INTO `t_pub_district` VALUES ('2178', '310', '墨江哈尼族自治县');
INSERT INTO `t_pub_district` VALUES ('2179', '310', '景东彝族自治县');
INSERT INTO `t_pub_district` VALUES ('2180', '310', '景谷傣族彝族自治县');
INSERT INTO `t_pub_district` VALUES ('2181', '310', '镇沅彝族哈尼族拉祜族自治县');
INSERT INTO `t_pub_district` VALUES ('2182', '310', '江城哈尼族彝族自治县');
INSERT INTO `t_pub_district` VALUES ('2183', '310', '孟连傣族拉祜族佤族自治县');
INSERT INTO `t_pub_district` VALUES ('2184', '310', '澜沧拉祜族自治县');
INSERT INTO `t_pub_district` VALUES ('2185', '310', '西盟佤族自治县');
INSERT INTO `t_pub_district` VALUES ('2186', '311', '临翔区');
INSERT INTO `t_pub_district` VALUES ('2187', '311', '凤庆县');
INSERT INTO `t_pub_district` VALUES ('2188', '311', '云县');
INSERT INTO `t_pub_district` VALUES ('2189', '311', '永德县');
INSERT INTO `t_pub_district` VALUES ('2190', '311', '镇康县');
INSERT INTO `t_pub_district` VALUES ('2191', '311', '双江拉祜族佤族布朗族傣族自治县');
INSERT INTO `t_pub_district` VALUES ('2192', '311', '耿马傣族佤族自治县');
INSERT INTO `t_pub_district` VALUES ('2193', '311', '沧源佤族自治县');
INSERT INTO `t_pub_district` VALUES ('2194', '312', '芒市');
INSERT INTO `t_pub_district` VALUES ('2195', '312', '瑞丽市');
INSERT INTO `t_pub_district` VALUES ('2196', '312', '梁河县');
INSERT INTO `t_pub_district` VALUES ('2197', '312', '盈江县');
INSERT INTO `t_pub_district` VALUES ('2198', '312', '陇川县');
INSERT INTO `t_pub_district` VALUES ('2199', '313', '泸水县');
INSERT INTO `t_pub_district` VALUES ('2200', '313', '福贡县');
INSERT INTO `t_pub_district` VALUES ('2201', '313', '贡山独龙族怒族自治县');
INSERT INTO `t_pub_district` VALUES ('2202', '313', '兰坪白族普米族自治县');
INSERT INTO `t_pub_district` VALUES ('2203', '314', '香格里拉市');
INSERT INTO `t_pub_district` VALUES ('2204', '314', '德钦县');
INSERT INTO `t_pub_district` VALUES ('2205', '314', '维西傈僳族自治县');
INSERT INTO `t_pub_district` VALUES ('2206', '315', '大理市');
INSERT INTO `t_pub_district` VALUES ('2207', '315', '祥云县');
INSERT INTO `t_pub_district` VALUES ('2208', '315', '宾川县');
INSERT INTO `t_pub_district` VALUES ('2209', '315', '弥渡县');
INSERT INTO `t_pub_district` VALUES ('2210', '315', '永平县');
INSERT INTO `t_pub_district` VALUES ('2211', '315', '云龙县');
INSERT INTO `t_pub_district` VALUES ('2212', '315', '洱源县');
INSERT INTO `t_pub_district` VALUES ('2213', '315', '剑川县');
INSERT INTO `t_pub_district` VALUES ('2214', '315', '鹤庆县');
INSERT INTO `t_pub_district` VALUES ('2215', '315', '漾濞彝族自治县');
INSERT INTO `t_pub_district` VALUES ('2216', '315', '南涧彝族自治县');
INSERT INTO `t_pub_district` VALUES ('2217', '315', '巍山彝族回族自治县');
INSERT INTO `t_pub_district` VALUES ('2218', '316', '楚雄市');
INSERT INTO `t_pub_district` VALUES ('2219', '316', '双柏县');
INSERT INTO `t_pub_district` VALUES ('2220', '316', '牟定县');
INSERT INTO `t_pub_district` VALUES ('2221', '316', '南华县');
INSERT INTO `t_pub_district` VALUES ('2222', '316', '姚安县');
INSERT INTO `t_pub_district` VALUES ('2223', '316', '大姚县');
INSERT INTO `t_pub_district` VALUES ('2224', '316', '永仁县');
INSERT INTO `t_pub_district` VALUES ('2225', '316', '元谋县');
INSERT INTO `t_pub_district` VALUES ('2226', '316', '武定县');
INSERT INTO `t_pub_district` VALUES ('2227', '316', '禄丰县');
INSERT INTO `t_pub_district` VALUES ('2228', '317', '蒙自市');
INSERT INTO `t_pub_district` VALUES ('2229', '317', '个旧市');
INSERT INTO `t_pub_district` VALUES ('2230', '317', '开远市');
INSERT INTO `t_pub_district` VALUES ('2231', '317', '弥勒市');
INSERT INTO `t_pub_district` VALUES ('2232', '317', '建水县');
INSERT INTO `t_pub_district` VALUES ('2233', '317', '石屏县');
INSERT INTO `t_pub_district` VALUES ('2234', '317', '泸西县');
INSERT INTO `t_pub_district` VALUES ('2235', '317', '绿春县');
INSERT INTO `t_pub_district` VALUES ('2236', '317', '元阳县');
INSERT INTO `t_pub_district` VALUES ('2237', '317', '红河县');
INSERT INTO `t_pub_district` VALUES ('2238', '317', '金平苗族瑶族傣族自治县');
INSERT INTO `t_pub_district` VALUES ('2239', '317', '河口瑶族自治县');
INSERT INTO `t_pub_district` VALUES ('2240', '317', '屏边苗族自治县');
INSERT INTO `t_pub_district` VALUES ('2241', '318', '文山市');
INSERT INTO `t_pub_district` VALUES ('2242', '318', '砚山县');
INSERT INTO `t_pub_district` VALUES ('2243', '318', '西畴县');
INSERT INTO `t_pub_district` VALUES ('2244', '318', '麻栗坡县');
INSERT INTO `t_pub_district` VALUES ('2245', '318', '马关县');
INSERT INTO `t_pub_district` VALUES ('2246', '318', '丘北县');
INSERT INTO `t_pub_district` VALUES ('2247', '318', '广南县');
INSERT INTO `t_pub_district` VALUES ('2248', '318', '富宁县');
INSERT INTO `t_pub_district` VALUES ('2249', '319', '景洪市');
INSERT INTO `t_pub_district` VALUES ('2250', '319', '勐海县');
INSERT INTO `t_pub_district` VALUES ('2251', '319', '勐腊县');
INSERT INTO `t_pub_district` VALUES ('2252', '320', '新城区');
INSERT INTO `t_pub_district` VALUES ('2253', '320', '碑林区');
INSERT INTO `t_pub_district` VALUES ('2254', '320', '莲湖区');
INSERT INTO `t_pub_district` VALUES ('2255', '320', '灞桥区');
INSERT INTO `t_pub_district` VALUES ('2256', '320', '未央区');
INSERT INTO `t_pub_district` VALUES ('2257', '320', '雁塔区');
INSERT INTO `t_pub_district` VALUES ('2258', '320', '阎良区');
INSERT INTO `t_pub_district` VALUES ('2259', '320', '临潼区');
INSERT INTO `t_pub_district` VALUES ('2260', '320', '长安区');
INSERT INTO `t_pub_district` VALUES ('2261', '320', '高陵区');
INSERT INTO `t_pub_district` VALUES ('2262', '320', '蓝田县');
INSERT INTO `t_pub_district` VALUES ('2263', '320', '周至县');
INSERT INTO `t_pub_district` VALUES ('2264', '320', '户县');
INSERT INTO `t_pub_district` VALUES ('2265', '321', '渭滨区');
INSERT INTO `t_pub_district` VALUES ('2266', '321', '金台区');
INSERT INTO `t_pub_district` VALUES ('2267', '321', '陈仓区');
INSERT INTO `t_pub_district` VALUES ('2268', '321', '凤翔县');
INSERT INTO `t_pub_district` VALUES ('2269', '321', '岐山县');
INSERT INTO `t_pub_district` VALUES ('2270', '321', '扶风县');
INSERT INTO `t_pub_district` VALUES ('2271', '321', '眉县');
INSERT INTO `t_pub_district` VALUES ('2272', '321', '陇县');
INSERT INTO `t_pub_district` VALUES ('2273', '321', '千阳县');
INSERT INTO `t_pub_district` VALUES ('2274', '321', '麟游县');
INSERT INTO `t_pub_district` VALUES ('2275', '321', '凤县');
INSERT INTO `t_pub_district` VALUES ('2276', '321', '太白县');
INSERT INTO `t_pub_district` VALUES ('2277', '322', '秦都区');
INSERT INTO `t_pub_district` VALUES ('2278', '322', '渭城区');
INSERT INTO `t_pub_district` VALUES ('2279', '322', '兴平市');
INSERT INTO `t_pub_district` VALUES ('2280', '322', '三原县');
INSERT INTO `t_pub_district` VALUES ('2281', '322', '泾阳县');
INSERT INTO `t_pub_district` VALUES ('2282', '322', '武功县');
INSERT INTO `t_pub_district` VALUES ('2283', '322', '乾县');
INSERT INTO `t_pub_district` VALUES ('2284', '322', '礼泉县');
INSERT INTO `t_pub_district` VALUES ('2285', '322', '永寿县');
INSERT INTO `t_pub_district` VALUES ('2286', '322', '彬县');
INSERT INTO `t_pub_district` VALUES ('2287', '322', '长武县');
INSERT INTO `t_pub_district` VALUES ('2288', '322', '旬邑县');
INSERT INTO `t_pub_district` VALUES ('2289', '322', '淳化县');
INSERT INTO `t_pub_district` VALUES ('2290', '323', '临渭区');
INSERT INTO `t_pub_district` VALUES ('2291', '323', '华州区');
INSERT INTO `t_pub_district` VALUES ('2292', '323', '华阴市');
INSERT INTO `t_pub_district` VALUES ('2293', '323', '蒲城县');
INSERT INTO `t_pub_district` VALUES ('2294', '323', '富平县');
INSERT INTO `t_pub_district` VALUES ('2295', '323', '潼关县');
INSERT INTO `t_pub_district` VALUES ('2296', '323', '大荔县');
INSERT INTO `t_pub_district` VALUES ('2297', '323', '合阳县');
INSERT INTO `t_pub_district` VALUES ('2298', '323', '澄城县');
INSERT INTO `t_pub_district` VALUES ('2299', '323', '白水县');
INSERT INTO `t_pub_district` VALUES ('2300', '324', '耀州区');
INSERT INTO `t_pub_district` VALUES ('2301', '324', '王益区');
INSERT INTO `t_pub_district` VALUES ('2302', '324', '印台区');
INSERT INTO `t_pub_district` VALUES ('2303', '324', '宜君县');
INSERT INTO `t_pub_district` VALUES ('2304', '325', '宝塔区');
INSERT INTO `t_pub_district` VALUES ('2305', '325', '延长县');
INSERT INTO `t_pub_district` VALUES ('2306', '325', '延川县');
INSERT INTO `t_pub_district` VALUES ('2307', '325', '子长县');
INSERT INTO `t_pub_district` VALUES ('2308', '325', '安塞县');
INSERT INTO `t_pub_district` VALUES ('2309', '325', '志丹县');
INSERT INTO `t_pub_district` VALUES ('2310', '325', '吴起县');
INSERT INTO `t_pub_district` VALUES ('2311', '325', '甘泉县');
INSERT INTO `t_pub_district` VALUES ('2312', '325', '富县');
INSERT INTO `t_pub_district` VALUES ('2313', '325', '洛川县');
INSERT INTO `t_pub_district` VALUES ('2314', '325', '宜川县');
INSERT INTO `t_pub_district` VALUES ('2315', '325', '黄龙县');
INSERT INTO `t_pub_district` VALUES ('2316', '325', '黄陵县');
INSERT INTO `t_pub_district` VALUES ('2317', '326', '榆阳区');
INSERT INTO `t_pub_district` VALUES ('2318', '326', '横山区');
INSERT INTO `t_pub_district` VALUES ('2319', '326', '靖边县');
INSERT INTO `t_pub_district` VALUES ('2320', '326', '定边县');
INSERT INTO `t_pub_district` VALUES ('2321', '326', '绥德县');
INSERT INTO `t_pub_district` VALUES ('2322', '326', '米脂县');
INSERT INTO `t_pub_district` VALUES ('2323', '326', '佳县');
INSERT INTO `t_pub_district` VALUES ('2324', '326', '吴堡县');
INSERT INTO `t_pub_district` VALUES ('2325', '326', '清涧县');
INSERT INTO `t_pub_district` VALUES ('2326', '326', '子洲县');
INSERT INTO `t_pub_district` VALUES ('2327', '327', '汉滨区');
INSERT INTO `t_pub_district` VALUES ('2328', '327', '旬阳县');
INSERT INTO `t_pub_district` VALUES ('2329', '327', '石泉县');
INSERT INTO `t_pub_district` VALUES ('2330', '327', '平利县');
INSERT INTO `t_pub_district` VALUES ('2331', '327', '汉阴县');
INSERT INTO `t_pub_district` VALUES ('2332', '327', '宁陕县');
INSERT INTO `t_pub_district` VALUES ('2333', '327', '紫阳县');
INSERT INTO `t_pub_district` VALUES ('2334', '327', '岚皋县');
INSERT INTO `t_pub_district` VALUES ('2335', '327', '镇坪县');
INSERT INTO `t_pub_district` VALUES ('2336', '327', '白河县');
INSERT INTO `t_pub_district` VALUES ('2337', '328', '汉台区');
INSERT INTO `t_pub_district` VALUES ('2338', '328', '南郑县');
INSERT INTO `t_pub_district` VALUES ('2339', '328', '城固县');
INSERT INTO `t_pub_district` VALUES ('2340', '328', '洋县');
INSERT INTO `t_pub_district` VALUES ('2341', '328', '西乡县');
INSERT INTO `t_pub_district` VALUES ('2342', '328', '勉县');
INSERT INTO `t_pub_district` VALUES ('2343', '328', '宁强县');
INSERT INTO `t_pub_district` VALUES ('2344', '328', '略阳县');
INSERT INTO `t_pub_district` VALUES ('2345', '328', '镇巴县');
INSERT INTO `t_pub_district` VALUES ('2346', '328', '留坝县');
INSERT INTO `t_pub_district` VALUES ('2347', '328', '佛坪县');
INSERT INTO `t_pub_district` VALUES ('2348', '329', '商州区');
INSERT INTO `t_pub_district` VALUES ('2349', '329', '洛南县');
INSERT INTO `t_pub_district` VALUES ('2350', '329', '丹凤县');
INSERT INTO `t_pub_district` VALUES ('2351', '329', '商南县');
INSERT INTO `t_pub_district` VALUES ('2352', '329', '山阳县');
INSERT INTO `t_pub_district` VALUES ('2353', '329', '镇安县');
INSERT INTO `t_pub_district` VALUES ('2354', '329', '柞水县');
INSERT INTO `t_pub_district` VALUES ('2355', '330', '杨凌区');
INSERT INTO `t_pub_district` VALUES ('2356', '331', '韩城市');
INSERT INTO `t_pub_district` VALUES ('2357', '332', '神木县');
INSERT INTO `t_pub_district` VALUES ('2358', '333', '府谷县');
INSERT INTO `t_pub_district` VALUES ('2359', '334', '城关区');
INSERT INTO `t_pub_district` VALUES ('2360', '334', '七里河区');
INSERT INTO `t_pub_district` VALUES ('2361', '334', '西固区');
INSERT INTO `t_pub_district` VALUES ('2362', '334', '安宁区');
INSERT INTO `t_pub_district` VALUES ('2363', '334', '红古区');
INSERT INTO `t_pub_district` VALUES ('2364', '334', '榆中县');
INSERT INTO `t_pub_district` VALUES ('2365', '334', '皋兰县');
INSERT INTO `t_pub_district` VALUES ('2366', '334', '永登县');
INSERT INTO `t_pub_district` VALUES ('2367', '335', '峪泉镇');
INSERT INTO `t_pub_district` VALUES ('2368', '335', '文殊镇');
INSERT INTO `t_pub_district` VALUES ('2369', '335', '新城镇');
INSERT INTO `t_pub_district` VALUES ('2370', '336', '金川区');
INSERT INTO `t_pub_district` VALUES ('2371', '336', '永昌县');
INSERT INTO `t_pub_district` VALUES ('2372', '337', '白银区');
INSERT INTO `t_pub_district` VALUES ('2373', '337', '平川区');
INSERT INTO `t_pub_district` VALUES ('2374', '337', '会宁县');
INSERT INTO `t_pub_district` VALUES ('2375', '337', '靖远县');
INSERT INTO `t_pub_district` VALUES ('2376', '337', '景泰县');
INSERT INTO `t_pub_district` VALUES ('2377', '338', '秦州区');
INSERT INTO `t_pub_district` VALUES ('2378', '338', '麦积区');
INSERT INTO `t_pub_district` VALUES ('2379', '338', '清水县');
INSERT INTO `t_pub_district` VALUES ('2380', '338', '秦安县');
INSERT INTO `t_pub_district` VALUES ('2381', '338', '甘谷县');
INSERT INTO `t_pub_district` VALUES ('2382', '338', '武山县');
INSERT INTO `t_pub_district` VALUES ('2383', '338', '张家川回族自治县');
INSERT INTO `t_pub_district` VALUES ('2384', '339', '肃州区');
INSERT INTO `t_pub_district` VALUES ('2385', '339', '玉门市');
INSERT INTO `t_pub_district` VALUES ('2386', '339', '敦煌市');
INSERT INTO `t_pub_district` VALUES ('2387', '339', '瓜州县');
INSERT INTO `t_pub_district` VALUES ('2388', '339', '金塔县');
INSERT INTO `t_pub_district` VALUES ('2389', '339', '肃北蒙古族自治县');
INSERT INTO `t_pub_district` VALUES ('2390', '339', '阿克塞哈萨克族自治县');
INSERT INTO `t_pub_district` VALUES ('2391', '340', '甘州区');
INSERT INTO `t_pub_district` VALUES ('2392', '340', '山丹县');
INSERT INTO `t_pub_district` VALUES ('2393', '340', '民乐县');
INSERT INTO `t_pub_district` VALUES ('2394', '340', '临泽县');
INSERT INTO `t_pub_district` VALUES ('2395', '340', '高台县');
INSERT INTO `t_pub_district` VALUES ('2396', '340', '肃南裕固族自治县');
INSERT INTO `t_pub_district` VALUES ('2397', '341', '凉州区');
INSERT INTO `t_pub_district` VALUES ('2398', '341', '古浪县');
INSERT INTO `t_pub_district` VALUES ('2399', '341', '民勤县');
INSERT INTO `t_pub_district` VALUES ('2400', '341', '天祝藏族自治县');
INSERT INTO `t_pub_district` VALUES ('2401', '342', '安定区');
INSERT INTO `t_pub_district` VALUES ('2402', '342', '通渭县');
INSERT INTO `t_pub_district` VALUES ('2403', '342', '陇西县');
INSERT INTO `t_pub_district` VALUES ('2404', '342', '漳县');
INSERT INTO `t_pub_district` VALUES ('2405', '342', '渭源县');
INSERT INTO `t_pub_district` VALUES ('2406', '342', '岷县');
INSERT INTO `t_pub_district` VALUES ('2407', '342', '临洮县');
INSERT INTO `t_pub_district` VALUES ('2408', '343', '武都区');
INSERT INTO `t_pub_district` VALUES ('2409', '343', '宕昌县');
INSERT INTO `t_pub_district` VALUES ('2410', '343', '两当县');
INSERT INTO `t_pub_district` VALUES ('2411', '343', '徽县');
INSERT INTO `t_pub_district` VALUES ('2412', '343', '成县');
INSERT INTO `t_pub_district` VALUES ('2413', '343', '西和县');
INSERT INTO `t_pub_district` VALUES ('2414', '343', '礼县');
INSERT INTO `t_pub_district` VALUES ('2415', '343', '康县');
INSERT INTO `t_pub_district` VALUES ('2416', '343', '文县');
INSERT INTO `t_pub_district` VALUES ('2417', '344', '崆峒区');
INSERT INTO `t_pub_district` VALUES ('2418', '344', '泾川县');
INSERT INTO `t_pub_district` VALUES ('2419', '344', '灵台县');
INSERT INTO `t_pub_district` VALUES ('2420', '344', '崇信县');
INSERT INTO `t_pub_district` VALUES ('2421', '344', '华亭县');
INSERT INTO `t_pub_district` VALUES ('2422', '344', '庄浪县');
INSERT INTO `t_pub_district` VALUES ('2423', '344', '静宁县');
INSERT INTO `t_pub_district` VALUES ('2424', '345', '西峰区');
INSERT INTO `t_pub_district` VALUES ('2425', '345', '正宁县');
INSERT INTO `t_pub_district` VALUES ('2426', '345', '华池县');
INSERT INTO `t_pub_district` VALUES ('2427', '345', '合水县');
INSERT INTO `t_pub_district` VALUES ('2428', '345', '宁县');
INSERT INTO `t_pub_district` VALUES ('2429', '345', '庆城县');
INSERT INTO `t_pub_district` VALUES ('2430', '345', '镇原县');
INSERT INTO `t_pub_district` VALUES ('2431', '345', '环县');
INSERT INTO `t_pub_district` VALUES ('2432', '346', '临夏市');
INSERT INTO `t_pub_district` VALUES ('2433', '346', '临夏县');
INSERT INTO `t_pub_district` VALUES ('2434', '346', '康乐县');
INSERT INTO `t_pub_district` VALUES ('2435', '346', '广河县');
INSERT INTO `t_pub_district` VALUES ('2436', '346', '永靖县');
INSERT INTO `t_pub_district` VALUES ('2437', '346', '和政县');
INSERT INTO `t_pub_district` VALUES ('2438', '346', '东乡族自治县');
INSERT INTO `t_pub_district` VALUES ('2439', '346', '积石山保安族东乡族撒拉族自治县');
INSERT INTO `t_pub_district` VALUES ('2440', '347', '合作市');
INSERT INTO `t_pub_district` VALUES ('2441', '347', '夏河县');
INSERT INTO `t_pub_district` VALUES ('2442', '347', '玛曲县');
INSERT INTO `t_pub_district` VALUES ('2443', '347', '舟曲县');
INSERT INTO `t_pub_district` VALUES ('2444', '347', '碌曲县');
INSERT INTO `t_pub_district` VALUES ('2445', '347', '迭部县');
INSERT INTO `t_pub_district` VALUES ('2446', '347', '临潭县');
INSERT INTO `t_pub_district` VALUES ('2447', '347', '卓尼县');
INSERT INTO `t_pub_district` VALUES ('2448', '348', '城中区');
INSERT INTO `t_pub_district` VALUES ('2449', '348', '城东区');
INSERT INTO `t_pub_district` VALUES ('2450', '348', '城西区');
INSERT INTO `t_pub_district` VALUES ('2451', '348', '城北区');
INSERT INTO `t_pub_district` VALUES ('2452', '348', '大通回族土族自治县');
INSERT INTO `t_pub_district` VALUES ('2453', '348', '湟中县');
INSERT INTO `t_pub_district` VALUES ('2454', '348', '湟源县');
INSERT INTO `t_pub_district` VALUES ('2455', '349', '乐都区');
INSERT INTO `t_pub_district` VALUES ('2456', '349', '平安区');
INSERT INTO `t_pub_district` VALUES ('2457', '349', '民和回族土族自治县');
INSERT INTO `t_pub_district` VALUES ('2458', '349', '互助土族自治县');
INSERT INTO `t_pub_district` VALUES ('2459', '349', '化隆回族自治县');
INSERT INTO `t_pub_district` VALUES ('2460', '349', '循化撒拉族自治县');
INSERT INTO `t_pub_district` VALUES ('2461', '350', '海晏县');
INSERT INTO `t_pub_district` VALUES ('2462', '350', '祁连县');
INSERT INTO `t_pub_district` VALUES ('2463', '350', '刚察县');
INSERT INTO `t_pub_district` VALUES ('2464', '350', '门源回族自治县');
INSERT INTO `t_pub_district` VALUES ('2465', '351', '同仁县');
INSERT INTO `t_pub_district` VALUES ('2466', '351', '尖扎县');
INSERT INTO `t_pub_district` VALUES ('2467', '351', '泽库县');
INSERT INTO `t_pub_district` VALUES ('2468', '351', '河南蒙古族自治县');
INSERT INTO `t_pub_district` VALUES ('2469', '352', '共和县');
INSERT INTO `t_pub_district` VALUES ('2470', '352', '同德县');
INSERT INTO `t_pub_district` VALUES ('2471', '352', '贵德县');
INSERT INTO `t_pub_district` VALUES ('2472', '352', '兴海县');
INSERT INTO `t_pub_district` VALUES ('2473', '352', '贵南县');
INSERT INTO `t_pub_district` VALUES ('2474', '353', '玛沁县');
INSERT INTO `t_pub_district` VALUES ('2475', '353', '班玛县');
INSERT INTO `t_pub_district` VALUES ('2476', '353', '甘德县');
INSERT INTO `t_pub_district` VALUES ('2477', '353', '达日县');
INSERT INTO `t_pub_district` VALUES ('2478', '353', '久治县');
INSERT INTO `t_pub_district` VALUES ('2479', '353', '玛多县');
INSERT INTO `t_pub_district` VALUES ('2480', '354', '玉树市');
INSERT INTO `t_pub_district` VALUES ('2481', '354', '杂多县');
INSERT INTO `t_pub_district` VALUES ('2482', '354', '称多县');
INSERT INTO `t_pub_district` VALUES ('2483', '354', '治多县');
INSERT INTO `t_pub_district` VALUES ('2484', '354', '囊谦县');
INSERT INTO `t_pub_district` VALUES ('2485', '354', '曲麻莱县');
INSERT INTO `t_pub_district` VALUES ('2486', '355', '德令哈市');
INSERT INTO `t_pub_district` VALUES ('2487', '355', '格尔木市');
INSERT INTO `t_pub_district` VALUES ('2488', '355', '天峻县');
INSERT INTO `t_pub_district` VALUES ('2489', '355', '都兰县');
INSERT INTO `t_pub_district` VALUES ('2490', '355', '乌兰县');
INSERT INTO `t_pub_district` VALUES ('2491', '356', '城关区');
INSERT INTO `t_pub_district` VALUES ('2492', '356', '堆龙德庆区');
INSERT INTO `t_pub_district` VALUES ('2493', '356', '林周县');
INSERT INTO `t_pub_district` VALUES ('2494', '356', '达孜县');
INSERT INTO `t_pub_district` VALUES ('2495', '356', '尼木县');
INSERT INTO `t_pub_district` VALUES ('2496', '356', '当雄县');
INSERT INTO `t_pub_district` VALUES ('2497', '356', '曲水县');
INSERT INTO `t_pub_district` VALUES ('2498', '356', '墨竹工卡县');
INSERT INTO `t_pub_district` VALUES ('2499', '357', '卡若区');
INSERT INTO `t_pub_district` VALUES ('2500', '357', '察雅县');
INSERT INTO `t_pub_district` VALUES ('2501', '357', '左贡县');
INSERT INTO `t_pub_district` VALUES ('2502', '357', '芒康县');
INSERT INTO `t_pub_district` VALUES ('2503', '357', '洛隆县');
INSERT INTO `t_pub_district` VALUES ('2504', '357', '边坝县');
INSERT INTO `t_pub_district` VALUES ('2505', '357', '江达县');
INSERT INTO `t_pub_district` VALUES ('2506', '357', '贡觉县');
INSERT INTO `t_pub_district` VALUES ('2507', '357', '丁青县');
INSERT INTO `t_pub_district` VALUES ('2508', '357', '八宿县');
INSERT INTO `t_pub_district` VALUES ('2509', '357', '类乌齐县');
INSERT INTO `t_pub_district` VALUES ('2510', '358', '桑珠孜区');
INSERT INTO `t_pub_district` VALUES ('2511', '358', '南木林县');
INSERT INTO `t_pub_district` VALUES ('2512', '358', '江孜县');
INSERT INTO `t_pub_district` VALUES ('2513', '358', '定日县');
INSERT INTO `t_pub_district` VALUES ('2514', '358', '萨迦县');
INSERT INTO `t_pub_district` VALUES ('2515', '358', '拉孜县');
INSERT INTO `t_pub_district` VALUES ('2516', '358', '昂仁县');
INSERT INTO `t_pub_district` VALUES ('2517', '358', '谢通门县');
INSERT INTO `t_pub_district` VALUES ('2518', '358', '白朗县');
INSERT INTO `t_pub_district` VALUES ('2519', '358', '仁布县');
INSERT INTO `t_pub_district` VALUES ('2520', '358', '康马县');
INSERT INTO `t_pub_district` VALUES ('2521', '358', '定结县');
INSERT INTO `t_pub_district` VALUES ('2522', '358', '仲巴县');
INSERT INTO `t_pub_district` VALUES ('2523', '358', '亚东县');
INSERT INTO `t_pub_district` VALUES ('2524', '358', '吉隆县');
INSERT INTO `t_pub_district` VALUES ('2525', '358', '聂拉木县');
INSERT INTO `t_pub_district` VALUES ('2526', '358', '萨嘎县');
INSERT INTO `t_pub_district` VALUES ('2527', '358', '岗巴县');
INSERT INTO `t_pub_district` VALUES ('2528', '359', '巴宜区');
INSERT INTO `t_pub_district` VALUES ('2529', '359', '米林县');
INSERT INTO `t_pub_district` VALUES ('2530', '359', '墨脱县');
INSERT INTO `t_pub_district` VALUES ('2531', '359', '察隅县');
INSERT INTO `t_pub_district` VALUES ('2532', '359', '波密县');
INSERT INTO `t_pub_district` VALUES ('2533', '359', '朗县');
INSERT INTO `t_pub_district` VALUES ('2534', '359', '工布江达县');
INSERT INTO `t_pub_district` VALUES ('2535', '360', '乃东区');
INSERT INTO `t_pub_district` VALUES ('2536', '360', '扎囊县');
INSERT INTO `t_pub_district` VALUES ('2537', '360', '贡嘎县');
INSERT INTO `t_pub_district` VALUES ('2538', '360', '桑日县');
INSERT INTO `t_pub_district` VALUES ('2539', '360', '琼结县');
INSERT INTO `t_pub_district` VALUES ('2540', '360', '洛扎县');
INSERT INTO `t_pub_district` VALUES ('2541', '360', '加查县');
INSERT INTO `t_pub_district` VALUES ('2542', '360', '隆子县');
INSERT INTO `t_pub_district` VALUES ('2543', '360', '曲松县');
INSERT INTO `t_pub_district` VALUES ('2544', '360', '措美县');
INSERT INTO `t_pub_district` VALUES ('2545', '360', '错那县');
INSERT INTO `t_pub_district` VALUES ('2546', '360', '浪卡子县');
INSERT INTO `t_pub_district` VALUES ('2547', '361', '那曲县');
INSERT INTO `t_pub_district` VALUES ('2548', '361', '申扎县');
INSERT INTO `t_pub_district` VALUES ('2549', '361', '班戈县');
INSERT INTO `t_pub_district` VALUES ('2550', '361', '聂荣县');
INSERT INTO `t_pub_district` VALUES ('2551', '361', '安多县');
INSERT INTO `t_pub_district` VALUES ('2552', '361', '嘉黎县');
INSERT INTO `t_pub_district` VALUES ('2553', '361', '巴青县');
INSERT INTO `t_pub_district` VALUES ('2554', '361', '比如县');
INSERT INTO `t_pub_district` VALUES ('2555', '361', '索县');
INSERT INTO `t_pub_district` VALUES ('2556', '361', '尼玛县');
INSERT INTO `t_pub_district` VALUES ('2557', '362', '噶尔县');
INSERT INTO `t_pub_district` VALUES ('2558', '362', '普兰县');
INSERT INTO `t_pub_district` VALUES ('2559', '362', '札达县');
INSERT INTO `t_pub_district` VALUES ('2560', '362', '日土县');
INSERT INTO `t_pub_district` VALUES ('2561', '362', '革吉县');
INSERT INTO `t_pub_district` VALUES ('2562', '362', '改则县');
INSERT INTO `t_pub_district` VALUES ('2563', '362', '措勤县');
INSERT INTO `t_pub_district` VALUES ('2564', '363', '青秀区');
INSERT INTO `t_pub_district` VALUES ('2565', '363', '兴宁区');
INSERT INTO `t_pub_district` VALUES ('2566', '363', '西乡塘区');
INSERT INTO `t_pub_district` VALUES ('2567', '363', '江南区');
INSERT INTO `t_pub_district` VALUES ('2568', '363', '良庆区');
INSERT INTO `t_pub_district` VALUES ('2569', '363', '邕宁区');
INSERT INTO `t_pub_district` VALUES ('2570', '363', '武鸣区');
INSERT INTO `t_pub_district` VALUES ('2571', '363', '隆安县');
INSERT INTO `t_pub_district` VALUES ('2572', '363', '马山县');
INSERT INTO `t_pub_district` VALUES ('2573', '363', '上林县');
INSERT INTO `t_pub_district` VALUES ('2574', '363', '宾阳县');
INSERT INTO `t_pub_district` VALUES ('2575', '363', '横县');
INSERT INTO `t_pub_district` VALUES ('2576', '364', '柳北区');
INSERT INTO `t_pub_district` VALUES ('2577', '364', '柳南区');
INSERT INTO `t_pub_district` VALUES ('2578', '364', '城中区');
INSERT INTO `t_pub_district` VALUES ('2579', '364', '鱼峰区');
INSERT INTO `t_pub_district` VALUES ('2580', '364', '柳城县');
INSERT INTO `t_pub_district` VALUES ('2581', '364', '柳江县');
INSERT INTO `t_pub_district` VALUES ('2582', '364', '鹿寨县');
INSERT INTO `t_pub_district` VALUES ('2583', '364', '融安县');
INSERT INTO `t_pub_district` VALUES ('2584', '364', '融水苗族自治县');
INSERT INTO `t_pub_district` VALUES ('2585', '364', '三江侗族自治县');
INSERT INTO `t_pub_district` VALUES ('2586', '365', '象山区');
INSERT INTO `t_pub_district` VALUES ('2587', '365', '秀峰区');
INSERT INTO `t_pub_district` VALUES ('2588', '365', '叠彩区');
INSERT INTO `t_pub_district` VALUES ('2589', '365', '七星区');
INSERT INTO `t_pub_district` VALUES ('2590', '365', '雁山区');
INSERT INTO `t_pub_district` VALUES ('2591', '365', '临桂区');
INSERT INTO `t_pub_district` VALUES ('2592', '365', '阳朔县');
INSERT INTO `t_pub_district` VALUES ('2593', '365', '灵川县');
INSERT INTO `t_pub_district` VALUES ('2594', '365', '全州县');
INSERT INTO `t_pub_district` VALUES ('2595', '365', '平乐县');
INSERT INTO `t_pub_district` VALUES ('2596', '365', '兴安县');
INSERT INTO `t_pub_district` VALUES ('2597', '365', '灌阳县');
INSERT INTO `t_pub_district` VALUES ('2598', '365', '荔浦县');
INSERT INTO `t_pub_district` VALUES ('2599', '365', '资源县');
INSERT INTO `t_pub_district` VALUES ('2600', '365', '永福县');
INSERT INTO `t_pub_district` VALUES ('2601', '365', '龙胜各族自治县');
INSERT INTO `t_pub_district` VALUES ('2602', '365', '恭城瑶族自治县');
INSERT INTO `t_pub_district` VALUES ('2603', '366', '长洲区');
INSERT INTO `t_pub_district` VALUES ('2604', '366', '万秀区');
INSERT INTO `t_pub_district` VALUES ('2605', '366', '龙圩区');
INSERT INTO `t_pub_district` VALUES ('2606', '366', '岑溪市');
INSERT INTO `t_pub_district` VALUES ('2607', '366', '苍梧县');
INSERT INTO `t_pub_district` VALUES ('2608', '366', '蒙山县');
INSERT INTO `t_pub_district` VALUES ('2609', '366', '藤县');
INSERT INTO `t_pub_district` VALUES ('2610', '367', '海城区');
INSERT INTO `t_pub_district` VALUES ('2611', '367', '银海区');
INSERT INTO `t_pub_district` VALUES ('2612', '367', '铁山港区');
INSERT INTO `t_pub_district` VALUES ('2613', '367', '合浦县');
INSERT INTO `t_pub_district` VALUES ('2614', '368', '港口区');
INSERT INTO `t_pub_district` VALUES ('2615', '368', '防城区');
INSERT INTO `t_pub_district` VALUES ('2616', '368', '东兴市');
INSERT INTO `t_pub_district` VALUES ('2617', '368', '上思县');
INSERT INTO `t_pub_district` VALUES ('2618', '369', '钦南区');
INSERT INTO `t_pub_district` VALUES ('2619', '369', '钦北区');
INSERT INTO `t_pub_district` VALUES ('2620', '369', '灵山县');
INSERT INTO `t_pub_district` VALUES ('2621', '369', '浦北县');
INSERT INTO `t_pub_district` VALUES ('2622', '370', '港北区');
INSERT INTO `t_pub_district` VALUES ('2623', '370', '港南区');
INSERT INTO `t_pub_district` VALUES ('2624', '370', '覃塘区');
INSERT INTO `t_pub_district` VALUES ('2625', '370', '桂平市');
INSERT INTO `t_pub_district` VALUES ('2626', '370', '平南县');
INSERT INTO `t_pub_district` VALUES ('2627', '371', '玉州区');
INSERT INTO `t_pub_district` VALUES ('2628', '371', '福绵区');
INSERT INTO `t_pub_district` VALUES ('2629', '371', '北流市');
INSERT INTO `t_pub_district` VALUES ('2630', '371', '容县');
INSERT INTO `t_pub_district` VALUES ('2631', '371', '陆川县');
INSERT INTO `t_pub_district` VALUES ('2632', '371', '博白县');
INSERT INTO `t_pub_district` VALUES ('2633', '371', '兴业县');
INSERT INTO `t_pub_district` VALUES ('2634', '372', '右江区');
INSERT INTO `t_pub_district` VALUES ('2635', '372', '靖西市');
INSERT INTO `t_pub_district` VALUES ('2636', '372', '田阳县');
INSERT INTO `t_pub_district` VALUES ('2637', '372', '田东县');
INSERT INTO `t_pub_district` VALUES ('2638', '372', '平果县');
INSERT INTO `t_pub_district` VALUES ('2639', '372', '德保县');
INSERT INTO `t_pub_district` VALUES ('2640', '372', '那坡县');
INSERT INTO `t_pub_district` VALUES ('2641', '372', '凌云县');
INSERT INTO `t_pub_district` VALUES ('2642', '372', '乐业县');
INSERT INTO `t_pub_district` VALUES ('2643', '372', '田林县');
INSERT INTO `t_pub_district` VALUES ('2644', '372', '西林县');
INSERT INTO `t_pub_district` VALUES ('2645', '372', '隆林各族自治县');
INSERT INTO `t_pub_district` VALUES ('2646', '373', '八步区');
INSERT INTO `t_pub_district` VALUES ('2647', '373', '昭平县');
INSERT INTO `t_pub_district` VALUES ('2648', '373', '钟山县');
INSERT INTO `t_pub_district` VALUES ('2649', '373', '富川瑶族自治县');
INSERT INTO `t_pub_district` VALUES ('2650', '374', '金城江区');
INSERT INTO `t_pub_district` VALUES ('2651', '374', '宜州市');
INSERT INTO `t_pub_district` VALUES ('2652', '374', '南丹县');
INSERT INTO `t_pub_district` VALUES ('2653', '374', '天峨县');
INSERT INTO `t_pub_district` VALUES ('2654', '374', '凤山县');
INSERT INTO `t_pub_district` VALUES ('2655', '374', '东兰县');
INSERT INTO `t_pub_district` VALUES ('2656', '374', '巴马瑶族自治县');
INSERT INTO `t_pub_district` VALUES ('2657', '374', '都安瑶族自治县');
INSERT INTO `t_pub_district` VALUES ('2658', '374', '大化瑶族自治县');
INSERT INTO `t_pub_district` VALUES ('2659', '374', '罗城仫佬族自治县');
INSERT INTO `t_pub_district` VALUES ('2660', '374', '环江毛南族自治县');
INSERT INTO `t_pub_district` VALUES ('2661', '375', '兴宾区');
INSERT INTO `t_pub_district` VALUES ('2662', '375', '合山市');
INSERT INTO `t_pub_district` VALUES ('2663', '375', '象州县');
INSERT INTO `t_pub_district` VALUES ('2664', '375', '武宣县');
INSERT INTO `t_pub_district` VALUES ('2665', '375', '忻城县');
INSERT INTO `t_pub_district` VALUES ('2666', '375', '金秀瑶族自治县');
INSERT INTO `t_pub_district` VALUES ('2667', '376', '江州区');
INSERT INTO `t_pub_district` VALUES ('2668', '376', '凭祥市');
INSERT INTO `t_pub_district` VALUES ('2669', '376', '扶绥县');
INSERT INTO `t_pub_district` VALUES ('2670', '376', '宁明县');
INSERT INTO `t_pub_district` VALUES ('2671', '376', '龙州县');
INSERT INTO `t_pub_district` VALUES ('2672', '376', '大新县');
INSERT INTO `t_pub_district` VALUES ('2673', '376', '天等县');
INSERT INTO `t_pub_district` VALUES ('2674', '377', '回民区');
INSERT INTO `t_pub_district` VALUES ('2675', '377', '新城区');
INSERT INTO `t_pub_district` VALUES ('2676', '377', '玉泉区');
INSERT INTO `t_pub_district` VALUES ('2677', '377', '赛罕区');
INSERT INTO `t_pub_district` VALUES ('2678', '377', '土默特左旗');
INSERT INTO `t_pub_district` VALUES ('2679', '377', '托克托县');
INSERT INTO `t_pub_district` VALUES ('2680', '377', '和林格尔县');
INSERT INTO `t_pub_district` VALUES ('2681', '377', '武川县');
INSERT INTO `t_pub_district` VALUES ('2682', '377', '清水河县');
INSERT INTO `t_pub_district` VALUES ('2683', '378', '昆都仑区');
INSERT INTO `t_pub_district` VALUES ('2684', '378', '东河区');
INSERT INTO `t_pub_district` VALUES ('2685', '378', '青山区');
INSERT INTO `t_pub_district` VALUES ('2686', '378', '石拐区');
INSERT INTO `t_pub_district` VALUES ('2687', '378', '九原区');
INSERT INTO `t_pub_district` VALUES ('2688', '378', '白云鄂博矿区');
INSERT INTO `t_pub_district` VALUES ('2689', '378', '土默特右旗');
INSERT INTO `t_pub_district` VALUES ('2690', '378', '固阳县');
INSERT INTO `t_pub_district` VALUES ('2691', '378', '达尔罕茂明安联合旗');
INSERT INTO `t_pub_district` VALUES ('2692', '379', '海勃湾区');
INSERT INTO `t_pub_district` VALUES ('2693', '379', '海南区');
INSERT INTO `t_pub_district` VALUES ('2694', '379', '乌达区');
INSERT INTO `t_pub_district` VALUES ('2695', '380', '红山区');
INSERT INTO `t_pub_district` VALUES ('2696', '380', '元宝山区');
INSERT INTO `t_pub_district` VALUES ('2697', '380', '松山区');
INSERT INTO `t_pub_district` VALUES ('2698', '380', '阿鲁科尔沁旗');
INSERT INTO `t_pub_district` VALUES ('2699', '380', '巴林左旗');
INSERT INTO `t_pub_district` VALUES ('2700', '380', '巴林右旗');
INSERT INTO `t_pub_district` VALUES ('2701', '380', '林西县');
INSERT INTO `t_pub_district` VALUES ('2702', '380', '克什克腾旗');
INSERT INTO `t_pub_district` VALUES ('2703', '380', '翁牛特旗');
INSERT INTO `t_pub_district` VALUES ('2704', '380', '喀喇沁旗');
INSERT INTO `t_pub_district` VALUES ('2705', '380', '宁城县');
INSERT INTO `t_pub_district` VALUES ('2706', '380', '敖汉旗');
INSERT INTO `t_pub_district` VALUES ('2707', '381', '科尔沁区');
INSERT INTO `t_pub_district` VALUES ('2708', '381', '霍林郭勒市');
INSERT INTO `t_pub_district` VALUES ('2709', '381', '科尔沁左翼中旗');
INSERT INTO `t_pub_district` VALUES ('2710', '381', '科尔沁左翼后旗');
INSERT INTO `t_pub_district` VALUES ('2711', '381', '开鲁县');
INSERT INTO `t_pub_district` VALUES ('2712', '381', '库伦旗');
INSERT INTO `t_pub_district` VALUES ('2713', '381', '奈曼旗');
INSERT INTO `t_pub_district` VALUES ('2714', '381', '扎鲁特旗');
INSERT INTO `t_pub_district` VALUES ('2715', '382', '东胜区');
INSERT INTO `t_pub_district` VALUES ('2716', '382', '达拉特旗');
INSERT INTO `t_pub_district` VALUES ('2717', '382', '准格尔旗');
INSERT INTO `t_pub_district` VALUES ('2718', '382', '鄂托克前旗');
INSERT INTO `t_pub_district` VALUES ('2719', '382', '鄂托克旗');
INSERT INTO `t_pub_district` VALUES ('2720', '382', '杭锦旗');
INSERT INTO `t_pub_district` VALUES ('2721', '382', '乌审旗');
INSERT INTO `t_pub_district` VALUES ('2722', '382', '伊金霍洛旗');
INSERT INTO `t_pub_district` VALUES ('2723', '383', '海拉尔区');
INSERT INTO `t_pub_district` VALUES ('2724', '383', '满洲里市');
INSERT INTO `t_pub_district` VALUES ('2725', '383', '牙克石市');
INSERT INTO `t_pub_district` VALUES ('2726', '383', '扎兰屯市');
INSERT INTO `t_pub_district` VALUES ('2727', '383', '额尔古纳市');
INSERT INTO `t_pub_district` VALUES ('2728', '383', '根河市');
INSERT INTO `t_pub_district` VALUES ('2729', '383', '阿荣旗');
INSERT INTO `t_pub_district` VALUES ('2730', '383', '鄂伦春自治旗');
INSERT INTO `t_pub_district` VALUES ('2731', '383', '莫力达瓦达斡尔族自治旗');
INSERT INTO `t_pub_district` VALUES ('2732', '383', '鄂温克族自治旗');
INSERT INTO `t_pub_district` VALUES ('2733', '383', '陈巴尔虎旗');
INSERT INTO `t_pub_district` VALUES ('2734', '383', '新巴尔虎左旗');
INSERT INTO `t_pub_district` VALUES ('2735', '383', '新巴尔虎右旗');
INSERT INTO `t_pub_district` VALUES ('2736', '384', '临河区');
INSERT INTO `t_pub_district` VALUES ('2737', '384', '五原县');
INSERT INTO `t_pub_district` VALUES ('2738', '384', '磴口县');
INSERT INTO `t_pub_district` VALUES ('2739', '384', '乌拉特前旗');
INSERT INTO `t_pub_district` VALUES ('2740', '384', '乌拉特中旗');
INSERT INTO `t_pub_district` VALUES ('2741', '384', '乌拉特后旗');
INSERT INTO `t_pub_district` VALUES ('2742', '384', '杭锦后旗');
INSERT INTO `t_pub_district` VALUES ('2743', '385', '集宁区');
INSERT INTO `t_pub_district` VALUES ('2744', '385', '丰镇市');
INSERT INTO `t_pub_district` VALUES ('2745', '385', '卓资县');
INSERT INTO `t_pub_district` VALUES ('2746', '385', '化德县');
INSERT INTO `t_pub_district` VALUES ('2747', '385', '商都县');
INSERT INTO `t_pub_district` VALUES ('2748', '385', '兴和县');
INSERT INTO `t_pub_district` VALUES ('2749', '385', '凉城县');
INSERT INTO `t_pub_district` VALUES ('2750', '385', '察哈尔右翼前旗');
INSERT INTO `t_pub_district` VALUES ('2751', '385', '察哈尔右翼中旗');
INSERT INTO `t_pub_district` VALUES ('2752', '385', '察哈尔右翼后旗');
INSERT INTO `t_pub_district` VALUES ('2753', '385', '四子王旗');
INSERT INTO `t_pub_district` VALUES ('2754', '386', '乌兰浩特市');
INSERT INTO `t_pub_district` VALUES ('2755', '386', '阿尔山市');
INSERT INTO `t_pub_district` VALUES ('2756', '386', '科尔沁右翼前旗');
INSERT INTO `t_pub_district` VALUES ('2757', '386', '科尔沁右翼中旗');
INSERT INTO `t_pub_district` VALUES ('2758', '386', '扎赉特旗');
INSERT INTO `t_pub_district` VALUES ('2759', '386', '突泉县');
INSERT INTO `t_pub_district` VALUES ('2760', '387', '锡林浩特市');
INSERT INTO `t_pub_district` VALUES ('2761', '387', '二连浩特市');
INSERT INTO `t_pub_district` VALUES ('2762', '387', '阿巴嘎旗');
INSERT INTO `t_pub_district` VALUES ('2763', '387', '苏尼特左旗');
INSERT INTO `t_pub_district` VALUES ('2764', '387', '苏尼特右旗');
INSERT INTO `t_pub_district` VALUES ('2765', '387', '东乌珠穆沁旗');
INSERT INTO `t_pub_district` VALUES ('2766', '387', '西乌珠穆沁旗');
INSERT INTO `t_pub_district` VALUES ('2767', '387', '太仆寺旗');
INSERT INTO `t_pub_district` VALUES ('2768', '387', '镶黄旗');
INSERT INTO `t_pub_district` VALUES ('2769', '387', '正镶白旗');
INSERT INTO `t_pub_district` VALUES ('2770', '387', '正蓝旗');
INSERT INTO `t_pub_district` VALUES ('2771', '387', '多伦县');
INSERT INTO `t_pub_district` VALUES ('2772', '388', '阿拉善左旗');
INSERT INTO `t_pub_district` VALUES ('2773', '388', '阿拉善右旗');
INSERT INTO `t_pub_district` VALUES ('2774', '388', '额济纳旗');
INSERT INTO `t_pub_district` VALUES ('2775', '389', '兴庆区');
INSERT INTO `t_pub_district` VALUES ('2776', '389', '金凤区');
INSERT INTO `t_pub_district` VALUES ('2777', '389', '西夏区');
INSERT INTO `t_pub_district` VALUES ('2778', '389', '灵武市');
INSERT INTO `t_pub_district` VALUES ('2779', '389', '永宁县');
INSERT INTO `t_pub_district` VALUES ('2780', '389', '贺兰县');
INSERT INTO `t_pub_district` VALUES ('2781', '390', '大武口区');
INSERT INTO `t_pub_district` VALUES ('2782', '390', '惠农区');
INSERT INTO `t_pub_district` VALUES ('2783', '390', '平罗县');
INSERT INTO `t_pub_district` VALUES ('2784', '391', '利通区');
INSERT INTO `t_pub_district` VALUES ('2785', '391', '红寺堡区');
INSERT INTO `t_pub_district` VALUES ('2786', '391', '青铜峡市');
INSERT INTO `t_pub_district` VALUES ('2787', '392', '原州区');
INSERT INTO `t_pub_district` VALUES ('2788', '392', '西吉县');
INSERT INTO `t_pub_district` VALUES ('2789', '392', '隆德县');
INSERT INTO `t_pub_district` VALUES ('2790', '392', '泾源县');
INSERT INTO `t_pub_district` VALUES ('2791', '392', '彭阳县');
INSERT INTO `t_pub_district` VALUES ('2792', '393', '沙坡头区');
INSERT INTO `t_pub_district` VALUES ('2793', '393', '中宁县');
INSERT INTO `t_pub_district` VALUES ('2794', '393', '海原县');
INSERT INTO `t_pub_district` VALUES ('2795', '394', '同心县');
INSERT INTO `t_pub_district` VALUES ('2796', '395', '盐池县');
INSERT INTO `t_pub_district` VALUES ('2797', '396', '天山区');
INSERT INTO `t_pub_district` VALUES ('2798', '396', '沙依巴克区');
INSERT INTO `t_pub_district` VALUES ('2799', '396', '新市区');
INSERT INTO `t_pub_district` VALUES ('2800', '396', '水磨沟区');
INSERT INTO `t_pub_district` VALUES ('2801', '396', '头屯河区');
INSERT INTO `t_pub_district` VALUES ('2802', '396', '达坂城区');
INSERT INTO `t_pub_district` VALUES ('2803', '396', '米东区');
INSERT INTO `t_pub_district` VALUES ('2804', '396', '乌鲁木齐县');
INSERT INTO `t_pub_district` VALUES ('2805', '397', '独山子区');
INSERT INTO `t_pub_district` VALUES ('2806', '397', '克拉玛依区');
INSERT INTO `t_pub_district` VALUES ('2807', '397', '白碱滩区');
INSERT INTO `t_pub_district` VALUES ('2808', '397', '乌尔禾区');
INSERT INTO `t_pub_district` VALUES ('2809', '398', '高昌区');
INSERT INTO `t_pub_district` VALUES ('2810', '398', '鄯善县');
INSERT INTO `t_pub_district` VALUES ('2811', '398', '托克逊县');
INSERT INTO `t_pub_district` VALUES ('2812', '399', '伊州区');
INSERT INTO `t_pub_district` VALUES ('2813', '399', '伊吾县');
INSERT INTO `t_pub_district` VALUES ('2814', '399', '巴里坤哈萨克自治县');
INSERT INTO `t_pub_district` VALUES ('2815', '400', '阿克苏市');
INSERT INTO `t_pub_district` VALUES ('2816', '400', '温宿县');
INSERT INTO `t_pub_district` VALUES ('2817', '400', '库车县');
INSERT INTO `t_pub_district` VALUES ('2818', '400', '沙雅县');
INSERT INTO `t_pub_district` VALUES ('2819', '400', '新和县');
INSERT INTO `t_pub_district` VALUES ('2820', '400', '拜城县');
INSERT INTO `t_pub_district` VALUES ('2821', '400', '乌什县');
INSERT INTO `t_pub_district` VALUES ('2822', '400', '阿瓦提县');
INSERT INTO `t_pub_district` VALUES ('2823', '400', '柯坪县');
INSERT INTO `t_pub_district` VALUES ('2824', '401', '喀什市');
INSERT INTO `t_pub_district` VALUES ('2825', '401', '疏附县');
INSERT INTO `t_pub_district` VALUES ('2826', '401', '疏勒县');
INSERT INTO `t_pub_district` VALUES ('2827', '401', '英吉沙县');
INSERT INTO `t_pub_district` VALUES ('2828', '401', '泽普县');
INSERT INTO `t_pub_district` VALUES ('2829', '401', '莎车县');
INSERT INTO `t_pub_district` VALUES ('2830', '401', '叶城县');
INSERT INTO `t_pub_district` VALUES ('2831', '401', '麦盖提县');
INSERT INTO `t_pub_district` VALUES ('2832', '401', '岳普湖县');
INSERT INTO `t_pub_district` VALUES ('2833', '401', '伽师县');
INSERT INTO `t_pub_district` VALUES ('2834', '401', '巴楚县');
INSERT INTO `t_pub_district` VALUES ('2835', '401', '塔什库尔干塔吉克自治县');
INSERT INTO `t_pub_district` VALUES ('2836', '402', '和田市');
INSERT INTO `t_pub_district` VALUES ('2837', '402', '和田县');
INSERT INTO `t_pub_district` VALUES ('2838', '402', '墨玉县');
INSERT INTO `t_pub_district` VALUES ('2839', '402', '皮山县');
INSERT INTO `t_pub_district` VALUES ('2840', '402', '洛浦县');
INSERT INTO `t_pub_district` VALUES ('2841', '402', '策勒县');
INSERT INTO `t_pub_district` VALUES ('2842', '402', '于田县');
INSERT INTO `t_pub_district` VALUES ('2843', '402', '民丰县');
INSERT INTO `t_pub_district` VALUES ('2844', '403', '昌吉市');
INSERT INTO `t_pub_district` VALUES ('2845', '403', '阜康市');
INSERT INTO `t_pub_district` VALUES ('2846', '403', '呼图壁县');
INSERT INTO `t_pub_district` VALUES ('2847', '403', '玛纳斯县');
INSERT INTO `t_pub_district` VALUES ('2848', '403', '奇台县');
INSERT INTO `t_pub_district` VALUES ('2849', '403', '吉木萨尔县');
INSERT INTO `t_pub_district` VALUES ('2850', '403', '木垒哈萨克自治县');
INSERT INTO `t_pub_district` VALUES ('2851', '404', '博乐市');
INSERT INTO `t_pub_district` VALUES ('2852', '404', '精河县');
INSERT INTO `t_pub_district` VALUES ('2853', '404', '温泉县');
INSERT INTO `t_pub_district` VALUES ('2854', '404', '阿拉山口市');
INSERT INTO `t_pub_district` VALUES ('2855', '405', '库尔勒市');
INSERT INTO `t_pub_district` VALUES ('2856', '405', '焉耆回族自治县');
INSERT INTO `t_pub_district` VALUES ('2857', '405', '轮台县');
INSERT INTO `t_pub_district` VALUES ('2858', '405', '尉犁县');
INSERT INTO `t_pub_district` VALUES ('2859', '405', '若羌县');
INSERT INTO `t_pub_district` VALUES ('2860', '405', '且末县');
INSERT INTO `t_pub_district` VALUES ('2861', '405', '和静县');
INSERT INTO `t_pub_district` VALUES ('2862', '405', '和硕县');
INSERT INTO `t_pub_district` VALUES ('2863', '405', '博湖县');
INSERT INTO `t_pub_district` VALUES ('2864', '406', '阿图什市');
INSERT INTO `t_pub_district` VALUES ('2865', '406', '阿克陶县');
INSERT INTO `t_pub_district` VALUES ('2866', '406', '阿合奇县');
INSERT INTO `t_pub_district` VALUES ('2867', '406', '乌恰县');
INSERT INTO `t_pub_district` VALUES ('2868', '407', '伊宁市');
INSERT INTO `t_pub_district` VALUES ('2869', '407', '奎屯市');
INSERT INTO `t_pub_district` VALUES ('2870', '407', '霍尔果斯市');
INSERT INTO `t_pub_district` VALUES ('2871', '407', '尼勒克县');
INSERT INTO `t_pub_district` VALUES ('2872', '407', '伊宁县');
INSERT INTO `t_pub_district` VALUES ('2873', '407', '霍城县');
INSERT INTO `t_pub_district` VALUES ('2874', '407', '巩留县');
INSERT INTO `t_pub_district` VALUES ('2875', '407', '新源县');
INSERT INTO `t_pub_district` VALUES ('2876', '407', '昭苏县');
INSERT INTO `t_pub_district` VALUES ('2877', '407', '特克斯县');
INSERT INTO `t_pub_district` VALUES ('2878', '407', '察布查尔锡伯自治县');
INSERT INTO `t_pub_district` VALUES ('2879', '407', '塔城市');
INSERT INTO `t_pub_district` VALUES ('2880', '407', '乌苏市');
INSERT INTO `t_pub_district` VALUES ('2881', '407', '额敏县');
INSERT INTO `t_pub_district` VALUES ('2882', '407', '沙湾县');
INSERT INTO `t_pub_district` VALUES ('2883', '407', '托里县');
INSERT INTO `t_pub_district` VALUES ('2884', '407', '裕民县');
INSERT INTO `t_pub_district` VALUES ('2885', '407', '和布克赛尔蒙古自治县');
INSERT INTO `t_pub_district` VALUES ('2886', '407', '阿勒泰市');
INSERT INTO `t_pub_district` VALUES ('2887', '407', '布尔津县');
INSERT INTO `t_pub_district` VALUES ('2888', '407', '富蕴县');
INSERT INTO `t_pub_district` VALUES ('2889', '407', '福海县');
INSERT INTO `t_pub_district` VALUES ('2890', '407', '哈巴河县');
INSERT INTO `t_pub_district` VALUES ('2891', '407', '青河县');
INSERT INTO `t_pub_district` VALUES ('2892', '407', '吉木乃县');
INSERT INTO `t_pub_district` VALUES ('2893', '408', '石河子市');
INSERT INTO `t_pub_district` VALUES ('2894', '409', '阿拉尔市');
INSERT INTO `t_pub_district` VALUES ('2895', '410', '图木舒克市');
INSERT INTO `t_pub_district` VALUES ('2896', '411', '五家渠市');
INSERT INTO `t_pub_district` VALUES ('2897', '412', '北屯市');
INSERT INTO `t_pub_district` VALUES ('2898', '413', '铁门关市');
INSERT INTO `t_pub_district` VALUES ('2899', '414', '双河市');
INSERT INTO `t_pub_district` VALUES ('2900', '415', '可克达拉市');
INSERT INTO `t_pub_district` VALUES ('2901', '416', '昆玉市');
INSERT INTO `t_pub_district` VALUES ('2902', '417', '中西区');
INSERT INTO `t_pub_district` VALUES ('2903', '417', '湾仔区');
INSERT INTO `t_pub_district` VALUES ('2904', '417', '东区');
INSERT INTO `t_pub_district` VALUES ('2905', '417', '南区');
INSERT INTO `t_pub_district` VALUES ('2906', '418', '油尖旺区');
INSERT INTO `t_pub_district` VALUES ('2907', '418', '深水埗区');
INSERT INTO `t_pub_district` VALUES ('2908', '418', '九龙城区');
INSERT INTO `t_pub_district` VALUES ('2909', '418', '黄大仙区');
INSERT INTO `t_pub_district` VALUES ('2910', '418', '观塘区');
INSERT INTO `t_pub_district` VALUES ('2911', '419', '北区');
INSERT INTO `t_pub_district` VALUES ('2912', '419', '大埔区');
INSERT INTO `t_pub_district` VALUES ('2913', '419', '沙田区');
INSERT INTO `t_pub_district` VALUES ('2914', '419', '西贡区');
INSERT INTO `t_pub_district` VALUES ('2915', '419', '荃湾区');
INSERT INTO `t_pub_district` VALUES ('2916', '419', '屯门区');
INSERT INTO `t_pub_district` VALUES ('2917', '419', '元朗区');
INSERT INTO `t_pub_district` VALUES ('2918', '419', '葵青区');
INSERT INTO `t_pub_district` VALUES ('2919', '419', '离岛区');
INSERT INTO `t_pub_district` VALUES ('2920', '420', '花地玛堂区');
INSERT INTO `t_pub_district` VALUES ('2921', '420', '圣安多尼堂区');
INSERT INTO `t_pub_district` VALUES ('2922', '420', '大堂区');
INSERT INTO `t_pub_district` VALUES ('2923', '420', '望德堂区');
INSERT INTO `t_pub_district` VALUES ('2924', '420', '风顺堂区');
INSERT INTO `t_pub_district` VALUES ('2925', '421', '嘉模堂区');
INSERT INTO `t_pub_district` VALUES ('2926', '421', '圣方济各堂区');
INSERT INTO `t_pub_district` VALUES ('2927', '422', '路氹城');
INSERT INTO `t_pub_district` VALUES ('2928', '423', '中正区');
INSERT INTO `t_pub_district` VALUES ('2929', '423', '大同区');
INSERT INTO `t_pub_district` VALUES ('2930', '423', '中山区');
INSERT INTO `t_pub_district` VALUES ('2931', '423', '万华区');
INSERT INTO `t_pub_district` VALUES ('2932', '423', '信义区');
INSERT INTO `t_pub_district` VALUES ('2933', '423', '松山区');
INSERT INTO `t_pub_district` VALUES ('2934', '423', '大安区');
INSERT INTO `t_pub_district` VALUES ('2935', '423', '南港区');
INSERT INTO `t_pub_district` VALUES ('2936', '423', '北投区');
INSERT INTO `t_pub_district` VALUES ('2937', '423', '内湖区');
INSERT INTO `t_pub_district` VALUES ('2938', '423', '士林区');
INSERT INTO `t_pub_district` VALUES ('2939', '423', '文山区');
INSERT INTO `t_pub_district` VALUES ('2940', '424', '板桥区');
INSERT INTO `t_pub_district` VALUES ('2941', '424', '土城区');
INSERT INTO `t_pub_district` VALUES ('2942', '424', '新庄区');
INSERT INTO `t_pub_district` VALUES ('2943', '424', '新店区');
INSERT INTO `t_pub_district` VALUES ('2944', '424', '深坑区');
INSERT INTO `t_pub_district` VALUES ('2945', '424', '石碇区');
INSERT INTO `t_pub_district` VALUES ('2946', '424', '坪林区');
INSERT INTO `t_pub_district` VALUES ('2947', '424', '乌来区');
INSERT INTO `t_pub_district` VALUES ('2948', '424', '五股区');
INSERT INTO `t_pub_district` VALUES ('2949', '424', '八里区');
INSERT INTO `t_pub_district` VALUES ('2950', '424', '林口区');
INSERT INTO `t_pub_district` VALUES ('2951', '424', '淡水区');
INSERT INTO `t_pub_district` VALUES ('2952', '424', '中和区');
INSERT INTO `t_pub_district` VALUES ('2953', '424', '永和区');
INSERT INTO `t_pub_district` VALUES ('2954', '424', '三重区');
INSERT INTO `t_pub_district` VALUES ('2955', '424', '芦洲区');
INSERT INTO `t_pub_district` VALUES ('2956', '424', '泰山区');
INSERT INTO `t_pub_district` VALUES ('2957', '424', '树林区');
INSERT INTO `t_pub_district` VALUES ('2958', '424', '莺歌区');
INSERT INTO `t_pub_district` VALUES ('2959', '424', '三峡区');
INSERT INTO `t_pub_district` VALUES ('2960', '424', '汐止区');
INSERT INTO `t_pub_district` VALUES ('2961', '424', '金山区');
INSERT INTO `t_pub_district` VALUES ('2962', '424', '万里区');
INSERT INTO `t_pub_district` VALUES ('2963', '424', '三芝区');
INSERT INTO `t_pub_district` VALUES ('2964', '424', '石门区');
INSERT INTO `t_pub_district` VALUES ('2965', '424', '瑞芳区');
INSERT INTO `t_pub_district` VALUES ('2966', '424', '贡寮区');
INSERT INTO `t_pub_district` VALUES ('2967', '424', '双溪区');
INSERT INTO `t_pub_district` VALUES ('2968', '424', '平溪区');
INSERT INTO `t_pub_district` VALUES ('2969', '425', '桃园区');
INSERT INTO `t_pub_district` VALUES ('2970', '425', '中坜区');
INSERT INTO `t_pub_district` VALUES ('2971', '425', '平镇区');
INSERT INTO `t_pub_district` VALUES ('2972', '425', '八德区');
INSERT INTO `t_pub_district` VALUES ('2973', '425', '杨梅区');
INSERT INTO `t_pub_district` VALUES ('2974', '425', '芦竹区');
INSERT INTO `t_pub_district` VALUES ('2975', '425', '大溪区');
INSERT INTO `t_pub_district` VALUES ('2976', '425', '龙潭区');
INSERT INTO `t_pub_district` VALUES ('2977', '425', '龟山区');
INSERT INTO `t_pub_district` VALUES ('2978', '425', '大园区');
INSERT INTO `t_pub_district` VALUES ('2979', '425', '观音区');
INSERT INTO `t_pub_district` VALUES ('2980', '425', '新屋区');
INSERT INTO `t_pub_district` VALUES ('2981', '425', '复兴区');
INSERT INTO `t_pub_district` VALUES ('2982', '426', '中区');
INSERT INTO `t_pub_district` VALUES ('2983', '426', '东区');
INSERT INTO `t_pub_district` VALUES ('2984', '426', '西区');
INSERT INTO `t_pub_district` VALUES ('2985', '426', '南区');
INSERT INTO `t_pub_district` VALUES ('2986', '426', '北区');
INSERT INTO `t_pub_district` VALUES ('2987', '426', '西屯区');
INSERT INTO `t_pub_district` VALUES ('2988', '426', '南屯区');
INSERT INTO `t_pub_district` VALUES ('2989', '426', '北屯区');
INSERT INTO `t_pub_district` VALUES ('2990', '426', '丰原区');
INSERT INTO `t_pub_district` VALUES ('2991', '426', '大里区');
INSERT INTO `t_pub_district` VALUES ('2992', '426', '太平区');
INSERT INTO `t_pub_district` VALUES ('2993', '426', '东势区');
INSERT INTO `t_pub_district` VALUES ('2994', '426', '大甲区');
INSERT INTO `t_pub_district` VALUES ('2995', '426', '清水区');
INSERT INTO `t_pub_district` VALUES ('2996', '426', '沙鹿区');
INSERT INTO `t_pub_district` VALUES ('2997', '426', '梧栖区');
INSERT INTO `t_pub_district` VALUES ('2998', '426', '后里区');
INSERT INTO `t_pub_district` VALUES ('2999', '426', '神冈区');
INSERT INTO `t_pub_district` VALUES ('3000', '426', '潭子区');
INSERT INTO `t_pub_district` VALUES ('3001', '426', '大雅区');
INSERT INTO `t_pub_district` VALUES ('3002', '426', '新小区');
INSERT INTO `t_pub_district` VALUES ('3003', '426', '石冈区');
INSERT INTO `t_pub_district` VALUES ('3004', '426', '外埔区');
INSERT INTO `t_pub_district` VALUES ('3005', '426', '大安区');
INSERT INTO `t_pub_district` VALUES ('3006', '426', '乌日区');
INSERT INTO `t_pub_district` VALUES ('3007', '426', '大肚区');
INSERT INTO `t_pub_district` VALUES ('3008', '426', '龙井区');
INSERT INTO `t_pub_district` VALUES ('3009', '426', '雾峰区');
INSERT INTO `t_pub_district` VALUES ('3010', '426', '和平区');
INSERT INTO `t_pub_district` VALUES ('3011', '427', '中西区');
INSERT INTO `t_pub_district` VALUES ('3012', '427', '东区');
INSERT INTO `t_pub_district` VALUES ('3013', '427', '南区');
INSERT INTO `t_pub_district` VALUES ('3014', '427', '北区');
INSERT INTO `t_pub_district` VALUES ('3015', '427', '安平区');
INSERT INTO `t_pub_district` VALUES ('3016', '427', '安南区');
INSERT INTO `t_pub_district` VALUES ('3017', '427', '永康区');
INSERT INTO `t_pub_district` VALUES ('3018', '427', '归仁区');
INSERT INTO `t_pub_district` VALUES ('3019', '427', '新化区');
INSERT INTO `t_pub_district` VALUES ('3020', '427', '左镇区');
INSERT INTO `t_pub_district` VALUES ('3021', '427', '玉井区');
INSERT INTO `t_pub_district` VALUES ('3022', '427', '楠西区');
INSERT INTO `t_pub_district` VALUES ('3023', '427', '南化区');
INSERT INTO `t_pub_district` VALUES ('3024', '427', '仁德区');
INSERT INTO `t_pub_district` VALUES ('3025', '427', '关庙区');
INSERT INTO `t_pub_district` VALUES ('3026', '427', '龙崎区');
INSERT INTO `t_pub_district` VALUES ('3027', '427', '官田区');
INSERT INTO `t_pub_district` VALUES ('3028', '427', '麻豆区');
INSERT INTO `t_pub_district` VALUES ('3029', '427', '佳里区');
INSERT INTO `t_pub_district` VALUES ('3030', '427', '西港区');
INSERT INTO `t_pub_district` VALUES ('3031', '427', '七股区');
INSERT INTO `t_pub_district` VALUES ('3032', '427', '将军区');
INSERT INTO `t_pub_district` VALUES ('3033', '427', '学甲区');
INSERT INTO `t_pub_district` VALUES ('3034', '427', '北门区');
INSERT INTO `t_pub_district` VALUES ('3035', '427', '新营区');
INSERT INTO `t_pub_district` VALUES ('3036', '427', '后壁区');
INSERT INTO `t_pub_district` VALUES ('3037', '427', '白河区');
INSERT INTO `t_pub_district` VALUES ('3038', '427', '东山区');
INSERT INTO `t_pub_district` VALUES ('3039', '427', '六甲区');
INSERT INTO `t_pub_district` VALUES ('3040', '427', '下营区');
INSERT INTO `t_pub_district` VALUES ('3041', '427', '柳营区');
INSERT INTO `t_pub_district` VALUES ('3042', '427', '盐水区');
INSERT INTO `t_pub_district` VALUES ('3043', '427', '善化区');
INSERT INTO `t_pub_district` VALUES ('3044', '427', '大内区');
INSERT INTO `t_pub_district` VALUES ('3045', '427', '山上区');
INSERT INTO `t_pub_district` VALUES ('3046', '427', '新市区');
INSERT INTO `t_pub_district` VALUES ('3047', '427', '安定区');
INSERT INTO `t_pub_district` VALUES ('3048', '428', '楠梓区');
INSERT INTO `t_pub_district` VALUES ('3049', '428', '左营区');
INSERT INTO `t_pub_district` VALUES ('3050', '428', '鼓山区');
INSERT INTO `t_pub_district` VALUES ('3051', '428', '三民区');
INSERT INTO `t_pub_district` VALUES ('3052', '428', '盐埕区');
INSERT INTO `t_pub_district` VALUES ('3053', '428', '前金区');
INSERT INTO `t_pub_district` VALUES ('3054', '428', '新兴区');
INSERT INTO `t_pub_district` VALUES ('3055', '428', '苓雅区');
INSERT INTO `t_pub_district` VALUES ('3056', '428', '前镇区');
INSERT INTO `t_pub_district` VALUES ('3057', '428', '旗津区');
INSERT INTO `t_pub_district` VALUES ('3058', '428', '小港区');
INSERT INTO `t_pub_district` VALUES ('3059', '428', '凤山区');
INSERT INTO `t_pub_district` VALUES ('3060', '428', '大寮区');
INSERT INTO `t_pub_district` VALUES ('3061', '428', '鸟松区');
INSERT INTO `t_pub_district` VALUES ('3062', '428', '林园区');
INSERT INTO `t_pub_district` VALUES ('3063', '428', '仁武区');
INSERT INTO `t_pub_district` VALUES ('3064', '428', '大树区');
INSERT INTO `t_pub_district` VALUES ('3065', '428', '大社区');
INSERT INTO `t_pub_district` VALUES ('3066', '428', '冈山区');
INSERT INTO `t_pub_district` VALUES ('3067', '428', '路竹区');
INSERT INTO `t_pub_district` VALUES ('3068', '428', '桥头区');
INSERT INTO `t_pub_district` VALUES ('3069', '428', '梓官区');
INSERT INTO `t_pub_district` VALUES ('3070', '428', '弥陀区');
INSERT INTO `t_pub_district` VALUES ('3071', '428', '永安区');
INSERT INTO `t_pub_district` VALUES ('3072', '428', '燕巢区');
INSERT INTO `t_pub_district` VALUES ('3073', '428', '阿莲区');
INSERT INTO `t_pub_district` VALUES ('3074', '428', '茄萣区');
INSERT INTO `t_pub_district` VALUES ('3075', '428', '湖内区');
INSERT INTO `t_pub_district` VALUES ('3076', '428', '旗山区');
INSERT INTO `t_pub_district` VALUES ('3077', '428', '美浓区');
INSERT INTO `t_pub_district` VALUES ('3078', '428', '内门区');
INSERT INTO `t_pub_district` VALUES ('3079', '428', '杉林区');
INSERT INTO `t_pub_district` VALUES ('3080', '428', '甲仙区');
INSERT INTO `t_pub_district` VALUES ('3081', '428', '六龟区');
INSERT INTO `t_pub_district` VALUES ('3082', '428', '茂林区');
INSERT INTO `t_pub_district` VALUES ('3083', '428', '桃源区');
INSERT INTO `t_pub_district` VALUES ('3084', '428', '那玛夏区');
INSERT INTO `t_pub_district` VALUES ('3085', '429', '七堵区');
INSERT INTO `t_pub_district` VALUES ('3086', '429', '中山区');
INSERT INTO `t_pub_district` VALUES ('3087', '429', '中正区');
INSERT INTO `t_pub_district` VALUES ('3088', '429', '信义区');
INSERT INTO `t_pub_district` VALUES ('3089', '429', '仁爱区');
INSERT INTO `t_pub_district` VALUES ('3090', '429', '安乐区');
INSERT INTO `t_pub_district` VALUES ('3091', '429', '暖暖区');
INSERT INTO `t_pub_district` VALUES ('3092', '430', '东区');
INSERT INTO `t_pub_district` VALUES ('3093', '430', '北区');
INSERT INTO `t_pub_district` VALUES ('3094', '430', '香山区');
INSERT INTO `t_pub_district` VALUES ('3095', '431', '东区');
INSERT INTO `t_pub_district` VALUES ('3096', '431', '西区');

-- ----------------------------
-- Table structure for t_pub_education
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_education`;
CREATE TABLE `t_pub_education` (
  `EDUCATION_CODE` int(11) NOT NULL,
  `EDUCATION_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`EDUCATION_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_education
-- ----------------------------
INSERT INTO `t_pub_education` VALUES ('0', '未设置');
INSERT INTO `t_pub_education` VALUES ('1', '学龄前');
INSERT INTO `t_pub_education` VALUES ('2', '小学一年级');
INSERT INTO `t_pub_education` VALUES ('3', '小学二年级');
INSERT INTO `t_pub_education` VALUES ('4', '小学三年级');
INSERT INTO `t_pub_education` VALUES ('5', '小学四年级');
INSERT INTO `t_pub_education` VALUES ('6', '小学五年级');
INSERT INTO `t_pub_education` VALUES ('7', '小学六年级');
INSERT INTO `t_pub_education` VALUES ('8', '初中一年级');
INSERT INTO `t_pub_education` VALUES ('9', '初中二年级');
INSERT INTO `t_pub_education` VALUES ('10', '初中三年级');
INSERT INTO `t_pub_education` VALUES ('11', '高中一年级');
INSERT INTO `t_pub_education` VALUES ('12', '高中二年级');
INSERT INTO `t_pub_education` VALUES ('13', '高中三年级');

-- ----------------------------
-- Table structure for t_pub_game_age
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_game_age`;
CREATE TABLE `t_pub_game_age` (
  `AGE_CODE` int(11) NOT NULL,
  `AGE_INTERVAL` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`AGE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_game_age
-- ----------------------------
INSERT INTO `t_pub_game_age` VALUES ('0', '全年龄段');
INSERT INTO `t_pub_game_age` VALUES ('1', '0-6岁');
INSERT INTO `t_pub_game_age` VALUES ('2', '6-9岁');
INSERT INTO `t_pub_game_age` VALUES ('3', '9-12岁');
INSERT INTO `t_pub_game_age` VALUES ('4', '12-15岁');
INSERT INTO `t_pub_game_age` VALUES ('5', '15-18岁');

-- ----------------------------
-- Table structure for t_pub_game_memnum
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_game_memnum`;
CREATE TABLE `t_pub_game_memnum` (
  `MEMNUM_CODE` int(11) NOT NULL,
  `MEMNUM_INTERVAL` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`MEMNUM_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_game_memnum
-- ----------------------------
INSERT INTO `t_pub_game_memnum` VALUES ('0', '不限人数');
INSERT INTO `t_pub_game_memnum` VALUES ('1', '2-4人');
INSERT INTO `t_pub_game_memnum` VALUES ('2', '4-6人');
INSERT INTO `t_pub_game_memnum` VALUES ('3', '6-8人');
INSERT INTO `t_pub_game_memnum` VALUES ('4', '8-10人');
INSERT INTO `t_pub_game_memnum` VALUES ('5', '10-12人');
INSERT INTO `t_pub_game_memnum` VALUES ('6', '12人以上');

-- ----------------------------
-- Table structure for t_pub_game_type
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_game_type`;
CREATE TABLE `t_pub_game_type` (
  `TYPE_CODE` int(11) NOT NULL,
  `TYPE_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`TYPE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_game_type
-- ----------------------------
INSERT INTO `t_pub_game_type` VALUES ('1', '测试类型');

-- ----------------------------
-- Table structure for t_pub_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_grade`;
CREATE TABLE `t_pub_grade` (
  `GRADE_CODE` int(11) NOT NULL,
  `GRADE_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`GRADE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_grade
-- ----------------------------
INSERT INTO `t_pub_grade` VALUES ('0', '未设置');

-- ----------------------------
-- Table structure for t_pub_level
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_level`;
CREATE TABLE `t_pub_level` (
  `LEVEL_CODE` int(11) NOT NULL,
  `LEVEL_NAME` varchar(100) DEFAULT NULL,
  `LEVEL_POINT` int(11) DEFAULT NULL,
  PRIMARY KEY (`LEVEL_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_level
-- ----------------------------
INSERT INTO `t_pub_level` VALUES ('0', '少先队员', '0');
INSERT INTO `t_pub_level` VALUES ('1', '少先队员', '20');
INSERT INTO `t_pub_level` VALUES ('2', '少先队员', '50');
INSERT INTO `t_pub_level` VALUES ('3', '小组长', '100');
INSERT INTO `t_pub_level` VALUES ('4', '小组长', '200');
INSERT INTO `t_pub_level` VALUES ('5', '小组长', '350');
INSERT INTO `t_pub_level` VALUES ('6', '小队长', '500');
INSERT INTO `t_pub_level` VALUES ('7', '小队长', '660');
INSERT INTO `t_pub_level` VALUES ('8', '小队长', '820');
INSERT INTO `t_pub_level` VALUES ('9', '中队长', '1000');
INSERT INTO `t_pub_level` VALUES ('10', '中队长', '1500');
INSERT INTO `t_pub_level` VALUES ('11', '中队长', '2200');
INSERT INTO `t_pub_level` VALUES ('12', '大队长', '3000');
INSERT INTO `t_pub_level` VALUES ('13', '大队长', '4000');
INSERT INTO `t_pub_level` VALUES ('14', '大队长', '5000');

-- ----------------------------
-- Table structure for t_pub_province
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_province`;
CREATE TABLE `t_pub_province` (
  `PROVINCE_CODE` int(11) NOT NULL,
  `BELONGING` int(11) DEFAULT NULL,
  `PROVINCE_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PROVINCE_CODE`),
  KEY `FK_BELONGING_AREA` (`BELONGING`),
  CONSTRAINT `FK_BELONGING_AREA` FOREIGN KEY (`BELONGING`) REFERENCES `t_pub_area` (`AREA_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_province
-- ----------------------------
INSERT INTO `t_pub_province` VALUES ('0', '0', '未设置');
INSERT INTO `t_pub_province` VALUES ('1', '4', '北京市');
INSERT INTO `t_pub_province` VALUES ('2', '4', '天津市');
INSERT INTO `t_pub_province` VALUES ('3', '1', '上海市');
INSERT INTO `t_pub_province` VALUES ('4', '6', '重庆市');
INSERT INTO `t_pub_province` VALUES ('5', '4', '河北省');
INSERT INTO `t_pub_province` VALUES ('6', '4', '山西省');
INSERT INTO `t_pub_province` VALUES ('7', '7', '辽宁省');
INSERT INTO `t_pub_province` VALUES ('8', '7', '吉林省');
INSERT INTO `t_pub_province` VALUES ('9', '7', '黑龙江省');
INSERT INTO `t_pub_province` VALUES ('10', '1', '江苏省');
INSERT INTO `t_pub_province` VALUES ('11', '1', '浙江省');
INSERT INTO `t_pub_province` VALUES ('12', '1', '安徽省');
INSERT INTO `t_pub_province` VALUES ('13', '1', '福建省');
INSERT INTO `t_pub_province` VALUES ('14', '3', '江西省');
INSERT INTO `t_pub_province` VALUES ('15', '1', '山东省');
INSERT INTO `t_pub_province` VALUES ('16', '3', '河南省');
INSERT INTO `t_pub_province` VALUES ('17', '3', '湖北省');
INSERT INTO `t_pub_province` VALUES ('18', '3', '湖南省');
INSERT INTO `t_pub_province` VALUES ('19', '2', '广东省');
INSERT INTO `t_pub_province` VALUES ('20', '2', '海南省');
INSERT INTO `t_pub_province` VALUES ('21', '6', '四川省');
INSERT INTO `t_pub_province` VALUES ('22', '6', '贵州省');
INSERT INTO `t_pub_province` VALUES ('23', '6', '云南省');
INSERT INTO `t_pub_province` VALUES ('24', '5', '陕西省');
INSERT INTO `t_pub_province` VALUES ('25', '5', '甘肃省');
INSERT INTO `t_pub_province` VALUES ('26', '5', '青海省');
INSERT INTO `t_pub_province` VALUES ('27', '6', '西藏自治区');
INSERT INTO `t_pub_province` VALUES ('28', '2', '广西壮族自治区');
INSERT INTO `t_pub_province` VALUES ('29', '4', '内蒙古自治区');
INSERT INTO `t_pub_province` VALUES ('30', '5', '宁夏回族自治区');
INSERT INTO `t_pub_province` VALUES ('31', '5', '新疆维吾尔自治区');
INSERT INTO `t_pub_province` VALUES ('32', '8', '香港特别行政区');
INSERT INTO `t_pub_province` VALUES ('33', '8', '澳门地区');
INSERT INTO `t_pub_province` VALUES ('34', '8', '台湾省');

-- ----------------------------
-- Table structure for t_pub_school
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_school`;
CREATE TABLE `t_pub_school` (
  `SCHOOL_CODE` int(11) NOT NULL,
  `BELONGING` int(11) DEFAULT NULL,
  `SCHOOL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`SCHOOL_CODE`),
  KEY `FK_BELONGING_OF_SCHOOL` (`BELONGING`),
  CONSTRAINT `FK_BELONGING_OF_SCHOOL` FOREIGN KEY (`BELONGING`) REFERENCES `t_pub_district` (`DISTRICT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_school
-- ----------------------------
INSERT INTO `t_pub_school` VALUES ('0', '0', '未设置');

-- ----------------------------
-- Table structure for t_pub_version
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_version`;
CREATE TABLE `t_pub_version` (
  `VERSION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `VERSION` varchar(100) DEFAULT NULL,
  `MODIFY_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PACKAGE_URL` varchar(100) DEFAULT NULL,
  `NOTE` varchar(255) DEFAULT NULL,
  `SYSTEM` enum('Android','IOS') DEFAULT NULL,
  `ENABLE` enum('Y','N') DEFAULT NULL,
  PRIMARY KEY (`VERSION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_version
-- ----------------------------
INSERT INTO `t_pub_version` VALUES ('4', 'V1.0.1', '2016-08-28 14:32:30', 'http://123.57.52.135:8080/ChildHood/packages/app-debug.apk', '一键呼唤逻辑、聊天未做', 'Android', 'Y');
INSERT INTO `t_pub_version` VALUES ('5', 'V1.0.0', '2016-08-31 13:01:32', 'http://123.57.52.135:8080/ChildHood/childhood.apk', '测试版本信息', 'Android', 'N');

-- ----------------------------
-- Table structure for t_status_game
-- ----------------------------
DROP TABLE IF EXISTS `t_status_game`;
CREATE TABLE `t_status_game` (
  `GAME_ID` int(11) NOT NULL AUTO_INCREMENT,
  `GAME_CODE` int(11) DEFAULT NULL,
  `GAME_FOUNDER` varchar(50) DEFAULT NULL,
  `FOUND_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `START_TIME` datetime DEFAULT NULL,
  `FINISH_TIME` datetime DEFAULT NULL,
  `GAME_LATITUDE` double(8,6) DEFAULT NULL,
  `GAME_LONGITUDE` double(9,6) DEFAULT NULL,
  `GATHER_PLACE` varchar(100) DEFAULT '',
  `CUSTOM_INF` varchar(1000) DEFAULT '',
  `CUSTOM_COUNT` int(11) DEFAULT '0',
  `GAME_STATUS` char(4) DEFAULT 'S000',
  PRIMARY KEY (`GAME_ID`),
  KEY `FK_GAME_CODE_STATUS` (`GAME_CODE`),
  KEY `FK_GAME_FOUNDER_STATUS` (`GAME_FOUNDER`),
  CONSTRAINT `FK_GAME_CODE_STATUS` FOREIGN KEY (`GAME_CODE`) REFERENCES `t_gamerule_head` (`GAME_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_status_game
-- ----------------------------
INSERT INTO `t_status_game` VALUES ('1', '1', '13269128687', '2016-10-04 19:41:53', null, '2016-10-10 18:42:44', '39.996956', '116.438719', '', '', '0', 'S014');
INSERT INTO `t_status_game` VALUES ('2', '2', '13240150408', '2016-10-09 13:16:51', null, '2016-10-09 14:58:40', '39.995790', '116.433959', '好', null, '2', 'S014');
INSERT INTO `t_status_game` VALUES ('3', '3', '13240150408', '2016-10-09 15:09:39', null, '2016-10-09 15:13:52', '39.999513', '116.428334', '明天', null, '2', 'S014');
INSERT INTO `t_status_game` VALUES ('4', '4', '13240150408', '2016-10-09 15:14:12', null, '2016-10-09 15:19:37', '39.999473', '116.428384', '嗨', null, '2', 'S014');
INSERT INTO `t_status_game` VALUES ('5', '3', '13240150408', '2016-10-09 15:19:52', null, '2016-10-09 16:27:20', '39.999629', '116.428381', '好', null, '2', 'S014');
INSERT INTO `t_status_game` VALUES ('6', '1', '13240150408', '2016-10-09 16:27:49', null, '2016-10-09 17:21:50', '39.996956', '116.438719', '学校操场', '我已经带好沙包', '10', 'S014');
INSERT INTO `t_status_game` VALUES ('7', '1', '13240150408', '2016-10-09 18:14:56', '2016-10-10 18:43:08', '2016-10-10 18:44:01', '39.997481', '116.437244', '未来广场', '一起来吧', '5', 'S004');
INSERT INTO `t_status_game` VALUES ('8', '2', '13240150408', '2016-10-12 17:32:41', null, '2016-10-12 20:40:19', '39.996947', '116.438754', '联大', '都来玩咯', '5', 'S014');
INSERT INTO `t_status_game` VALUES ('10', '3', '13240150408', '2016-10-12 20:40:41', null, '2016-10-14 18:54:16', '39.996894', '116.438421', '未来广场', null, '2', 'S014');
INSERT INTO `t_status_game` VALUES ('11', '1', '13269128687', '2016-10-14 18:54:57', null, '2016-10-14 18:57:11', '39.996870', '116.433860', '奥林匹克森林公园', '我准备好了沙包', '10', 'S014');
INSERT INTO `t_status_game` VALUES ('12', '1', '13240150408', '2016-10-14 18:57:39', null, '2016-10-14 19:09:40', '39.996955', '116.438733', '紫薇公园', null, '10', 'S014');
INSERT INTO `t_status_game` VALUES ('13', '1', '13240150408', '2016-10-14 19:10:45', null, '2016-10-15 12:18:36', '39.997575', '116.434154', '紫薇公园', null, '10', 'S014');
INSERT INTO `t_status_game` VALUES ('14', '1', '13269128687', '2016-10-15 12:19:16', null, '2016-10-15 12:20:14', '39.997577', '116.434247', '低点', null, '10', 'S014');
INSERT INTO `t_status_game` VALUES ('15', '5', '13269128687', '2016-10-15 12:22:14', '2016-10-15 13:30:24', '2016-10-15 13:30:31', '39.997577', '116.434247', '地点', null, '12', 'S004');
INSERT INTO `t_status_game` VALUES ('16', '8', '13269128687', '2016-10-15 13:37:56', '2016-10-15 13:38:23', '2016-10-15 13:38:28', '39.997883', '116.434706', '地点', null, '5', 'S004');
INSERT INTO `t_status_game` VALUES ('17', '4', '13269128687', '2016-10-15 18:48:51', null, '2016-10-15 19:01:53', '39.996048', '116.433736', '北京联合大学', null, '5', 'S014');
INSERT INTO `t_status_game` VALUES ('18', '1', '13240150408', '2016-10-15 19:19:25', null, '2016-10-19 21:51:35', '39.995932', '116.433693', '北京联合大学', null, '10', 'S014');

-- ----------------------------
-- Table structure for t_status_joined
-- ----------------------------
DROP TABLE IF EXISTS `t_status_joined`;
CREATE TABLE `t_status_joined` (
  `JOINED_ID` int(11) NOT NULL AUTO_INCREMENT,
  `GAME_ID` int(11) NOT NULL,
  `USER_NAME` varchar(50) NOT NULL,
  `JOIN_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `GAME_SCORE` enum('1','2','3','4','5') DEFAULT '5',
  `JOIN_STATUS` char(4) DEFAULT 'S008',
  `IS_SCORED` enum('Y','N') NOT NULL DEFAULT 'N',
  PRIMARY KEY (`JOINED_ID`),
  KEY `FK_USER_NAME_STATUS` (`USER_NAME`),
  KEY `FK_GAME_ID_STATUS` (`GAME_ID`),
  CONSTRAINT `FK_GAME_ID_STATUS` FOREIGN KEY (`GAME_ID`) REFERENCES `t_status_game` (`GAME_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_status_joined
-- ----------------------------
INSERT INTO `t_status_joined` VALUES ('1', '1', '13269128687', '2016-10-04 19:41:53', '5', 'S012', 'Y');
INSERT INTO `t_status_joined` VALUES ('2', '2', '13240150408', '2016-10-09 13:16:51', '5', 'S012', 'N');
INSERT INTO `t_status_joined` VALUES ('3', '3', '13240150408', '2016-10-09 15:09:39', '5', 'S012', 'Y');
INSERT INTO `t_status_joined` VALUES ('4', '4', '13240150408', '2016-10-09 15:14:12', '5', 'S012', 'N');
INSERT INTO `t_status_joined` VALUES ('5', '5', '13240150408', '2016-10-09 15:19:52', '5', 'S012', 'Y');
INSERT INTO `t_status_joined` VALUES ('6', '6', '13240150408', '2016-10-09 16:27:49', '5', 'S012', 'N');
INSERT INTO `t_status_joined` VALUES ('7', '7', '13240150408', '2016-10-09 18:14:56', '3', 'S011', 'Y');
INSERT INTO `t_status_joined` VALUES ('8', '7', '13269128687', '2016-10-10 18:42:52', '4', 'S011', 'Y');
INSERT INTO `t_status_joined` VALUES ('9', '8', '13240150408', '2016-10-12 17:32:41', '5', 'S012', 'N');
INSERT INTO `t_status_joined` VALUES ('10', '10', '13240150408', '2016-10-12 20:40:41', '5', 'S012', 'N');
INSERT INTO `t_status_joined` VALUES ('11', '11', '13269128687', '2016-10-14 18:54:57', '5', 'S012', 'N');
INSERT INTO `t_status_joined` VALUES ('12', '12', '13240150408', '2016-10-14 18:57:39', '5', 'S012', 'N');
INSERT INTO `t_status_joined` VALUES ('13', '13', '13240150408', '2016-10-14 19:10:45', '5', 'S012', 'N');
INSERT INTO `t_status_joined` VALUES ('14', '13', '13269128687', '2016-10-14 19:11:34', '5', 'S010', 'N');
INSERT INTO `t_status_joined` VALUES ('15', '13', '13269128687', '2016-10-15 11:01:32', '5', 'S010', 'N');
INSERT INTO `t_status_joined` VALUES ('16', '13', '13269128687', '2016-10-15 12:17:59', '5', 'S010', 'N');
INSERT INTO `t_status_joined` VALUES ('17', '13', '13269128687', '2016-10-15 12:18:17', '5', 'S010', 'N');
INSERT INTO `t_status_joined` VALUES ('18', '14', '13269128687', '2016-10-15 12:19:16', '5', 'S012', 'N');
INSERT INTO `t_status_joined` VALUES ('19', '15', '13269128687', '2016-10-15 12:22:14', '5', 'S011', 'Y');
INSERT INTO `t_status_joined` VALUES ('20', '15', '13240150408', '2016-10-15 12:48:38', '2', 'S010', 'Y');
INSERT INTO `t_status_joined` VALUES ('21', '15', '13240150408', '2016-10-15 12:50:27', '2', 'S010', 'Y');
INSERT INTO `t_status_joined` VALUES ('22', '15', '13240150408', '2016-10-15 13:10:42', '2', 'S011', 'Y');
INSERT INTO `t_status_joined` VALUES ('23', '16', '13269128687', '2016-10-15 13:37:56', '5', 'S011', 'N');
INSERT INTO `t_status_joined` VALUES ('24', '16', '13240150408', '2016-10-15 13:38:12', '1', 'S011', 'Y');
INSERT INTO `t_status_joined` VALUES ('25', '17', '13269128687', '2016-10-15 18:48:51', '5', 'S012', 'N');
INSERT INTO `t_status_joined` VALUES ('26', '18', '13240150408', '2016-10-15 19:19:25', '5', 'S012', 'N');
INSERT INTO `t_status_joined` VALUES ('27', '18', '13269128687', '2016-10-15 19:20:40', '5', 'S010', 'N');
INSERT INTO `t_status_joined` VALUES ('28', '18', '13269128687', '2016-10-15 19:26:02', '5', 'S010', 'N');
INSERT INTO `t_status_joined` VALUES ('29', '18', '13269128687', '2016-10-15 19:44:39', '5', 'S012', 'N');

-- ----------------------------
-- Table structure for t_status_user_rank
-- ----------------------------
DROP TABLE IF EXISTS `t_status_user_rank`;
CREATE TABLE `t_status_user_rank` (
  `USER_NAME` char(50) NOT NULL,
  `LIKE_COUNT` int(11) DEFAULT '0',
  PRIMARY KEY (`USER_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_status_user_rank
-- ----------------------------
INSERT INTO `t_status_user_rank` VALUES ('13240150408', '129');
INSERT INTO `t_status_user_rank` VALUES ('13269128687', '23');
INSERT INTO `t_status_user_rank` VALUES ('13269158110', '15');
INSERT INTO `t_status_user_rank` VALUES ('13611108060', '11');
INSERT INTO `t_status_user_rank` VALUES ('18888888888', '8');

-- ----------------------------
-- Table structure for t_status_user_rank_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_status_user_rank_detail`;
CREATE TABLE `t_status_user_rank_detail` (
  `DETAIL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` char(50) NOT NULL,
  `LIKE_USER` char(50) NOT NULL,
  `LIKE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ACCESS` enum('RankingList','PersonalPage','GameOver') NOT NULL,
  `GAME_ID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`DETAIL_ID`),
  KEY `FK_STATUS_RANK_USER_NAME` (`USER_NAME`),
  KEY `FK_STATUS_RANK_LIKE_USER` (`LIKE_USER`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_status_user_rank_detail
-- ----------------------------
INSERT INTO `t_status_user_rank_detail` VALUES ('1', '13269128687', '13611108060', '2016-10-04 18:37:41', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('2', '13269128687', '13611108060', '2016-10-04 18:37:42', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('3', '13269128687', '13611108060', '2016-10-04 18:38:12', 'PersonalPage', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('4', '13269128687', '13611108060', '2016-10-04 18:38:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('5', '13269128687', '13611108060', '2016-10-04 18:38:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('6', '13269128687', '13611108060', '2016-10-04 18:38:12', 'GameOver', '1');
INSERT INTO `t_status_user_rank_detail` VALUES ('7', '13269128687', '13611108060', '2016-10-04 18:38:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('8', '13269128687', '13611108060', '2016-10-04 18:38:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('9', '13269128687', '13611108060', '2016-10-04 18:38:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('10', '13269128687', '13611108060', '2016-10-04 18:38:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('11', '13269128687', '13611108060', '2016-10-04 18:38:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('12', '13269128687', '13611108060', '2016-10-04 18:38:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('13', '13269128687', '13611108060', '2016-10-04 18:38:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('14', '13269128687', '13611108060', '2016-10-04 18:38:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('15', '13269128687', '13611108060', '2016-10-04 18:38:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('16', '13269128687', '13611108060', '2016-10-04 18:38:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('17', '13269128687', '13611108060', '2016-10-04 18:38:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('18', '13269128687', '13611108060', '2016-10-04 21:04:26', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('19', '13269128687', '13611108060', '2016-10-04 21:04:53', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('25', '13240150408', '13269128687', '2016-10-08 19:30:21', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('26', '13240150408', '13611108060', '2016-10-08 19:31:24', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('27', '13269128687', '13611108060', '2016-10-08 21:27:47', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('28', '13269128687', '13611108060', '2016-10-09 10:03:17', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('29', '13240150408', '13611108060', '2016-10-09 10:03:37', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('30', '13240150408', '13269128687', '2016-10-09 10:12:50', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('31', '13240150408', '13269128687', '2016-10-10 11:03:55', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('32', '13269128687', '13240150408', '2016-10-10 16:33:12', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('33', '13240150408', '13611108060', '2016-10-10 19:20:45', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('34', '13240150408', '13269128687', '2016-10-11 18:10:33', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('35', '13240150408', '13611108060', '2016-10-11 18:10:36', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('36', '13240150408', '13240150408', '2016-10-11 20:20:09', 'GameOver', '7');
INSERT INTO `t_status_user_rank_detail` VALUES ('37', '13240150408', '13269128687', '2016-10-11 20:20:11', 'GameOver', '7');
INSERT INTO `t_status_user_rank_detail` VALUES ('38', '13240150408', '13611108060', '2016-10-12 11:10:49', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('39', '13240150408', '13269128687', '2016-10-12 11:10:51', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('40', '13240150408', '13269158110', '2016-10-13 23:07:11', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('41', '13240150408', '13611108060', '2016-10-13 23:07:22', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('42', '13240150408', '13269128687', '2016-10-13 23:08:02', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('43', '13269158110', '13269128687', '2016-10-13 23:08:57', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('44', '13269158110', '13611108060', '2016-10-13 23:08:59', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('45', '13269158110', '13240150408', '2016-10-13 23:09:01', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('46', '13269128687', '13240150408', '2016-10-14 09:55:50', 'GameOver', '7');
INSERT INTO `t_status_user_rank_detail` VALUES ('47', '13269158110', '13240150408', '2016-10-14 15:51:59', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('48', '13269158110', '13269128687', '2016-10-14 15:52:00', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('81', '13240150408', '13269158110', '2016-10-15 11:53:26', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('82', '13240150408', '18888888888', '2016-10-15 11:53:29', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('83', '13240150408', '13269128687', '2016-10-15 11:53:32', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('84', '13240150408', '13611108060', '2016-10-15 11:53:34', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('85', '13240150408', '13269128687', '2016-10-15 17:37:02', 'GameOver', '15');
INSERT INTO `t_status_user_rank_detail` VALUES ('86', '13240150408', '13269128687', '2016-10-15 17:56:52', 'GameOver', '16');
INSERT INTO `t_status_user_rank_detail` VALUES ('87', '13269128687', '13240150408', '2016-10-15 19:45:45', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('88', '13269128687', '13269158110', '2016-10-15 19:51:51', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('89', '13269128687', '13611108060', '2016-10-15 19:51:52', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('90', '13269128687', '13240150408', '2016-10-16 10:53:26', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('91', '13269128687', '13269158110', '2016-10-16 11:01:24', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('92', '13269128687', '13240150408', '2016-10-18 10:58:05', 'GameOver', '15');
INSERT INTO `t_status_user_rank_detail` VALUES ('93', '13269128687', '13240150408', '2016-10-18 11:10:02', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('94', '13269128687', '13269158110', '2016-10-18 11:10:03', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('95', '13269128687', '13611108060', '2016-10-18 11:10:04', 'RankingList', '0');
INSERT INTO `t_status_user_rank_detail` VALUES ('96', '13269128687', '18888888888', '2016-10-18 11:10:06', 'RankingList', '0');

-- ----------------------------
-- Table structure for t_sys_child_inf
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_child_inf`;
CREATE TABLE `t_sys_child_inf` (
  `CHILD_INF` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(50) DEFAULT NULL,
  `CHILD_NAME` varchar(50) DEFAULT NULL,
  `CHILD_SEX` enum('m','f') DEFAULT NULL,
  `CHILD_BIRTHDAY` date DEFAULT NULL,
  `EDUCATION_CODE` int(11) DEFAULT '0',
  `GRADE_CODE` int(11) DEFAULT '0',
  `SCHOOL_CODE` int(11) DEFAULT '0',
  `CUSTOM_SCHOOL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CHILD_INF`),
  KEY `FK_EDUCATION_CODE` (`EDUCATION_CODE`),
  KEY `FK_GRADE_CODE` (`GRADE_CODE`),
  KEY `FK_SCHOOL_CODE` (`SCHOOL_CODE`),
  KEY `FK_USER_NAME_CHILD` (`USER_NAME`),
  CONSTRAINT `FK_EDUCATION_CODE` FOREIGN KEY (`EDUCATION_CODE`) REFERENCES `t_pub_education` (`EDUCATION_CODE`),
  CONSTRAINT `FK_GRADE_CODE` FOREIGN KEY (`GRADE_CODE`) REFERENCES `t_pub_grade` (`GRADE_CODE`),
  CONSTRAINT `FK_SCHOOL_CODE` FOREIGN KEY (`SCHOOL_CODE`) REFERENCES `t_pub_school` (`SCHOOL_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_child_inf
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_feedback
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_feedback`;
CREATE TABLE `t_sys_feedback` (
  `FEEDBACK_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(50) DEFAULT NULL,
  `FEEDBACK_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `FEEDBACK_CONTENT` varchar(2000) NOT NULL,
  `FEEDBACK_MODULE` enum('register','onekey') DEFAULT NULL,
  `RESULT` varchar(1000) DEFAULT NULL,
  `RESULT_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `RESULT_USER` varchar(50) DEFAULT NULL,
  `PROCESSIBLE` enum('Y','N') NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`FEEDBACK_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_feedback
-- ----------------------------
INSERT INTO `t_sys_feedback` VALUES ('1', '13269128687', '2016-10-10 17:32:00', '做的很好很好啊实打实达到爱死达到阿萨德阿大声道阿萨德阿萨德我企鹅阿萨德阿萨德去玩的阿萨德河段是单位五家渠掉几千万级大师的五黑好的挖的话我会打卡时间段可接受的驱蚊液哈的空间哈五点后哇哈的卡号等我就开始就爱看换位', 'register', '好的马上处理123', '2016-10-10 17:32:00', 'admin', 'N');
INSERT INTO `t_sys_feedback` VALUES ('2', '13240150408', '2016-10-10 17:32:03', 'asdasd asdasdasdasdasdasd', 'register', '123', '2016-10-10 17:32:03', 'admin', 'N');
INSERT INTO `t_sys_feedback` VALUES ('26', '13269128687', '2016-10-10 17:31:03', 'asdasdqwdaxdqwedqwd', 'onekey', '123123', '2016-10-10 17:31:03', 'admin', 'N');
INSERT INTO `t_sys_feedback` VALUES ('27', '13269128687', '2016-10-13 16:15:12', 'asdasdqwdaxdqwedqwd', 'onekey', '好的知道了', '2016-10-13 16:15:12', '13269128687', 'N');
INSERT INTO `t_sys_feedback` VALUES ('28', '13269128687', '2016-10-14 14:54:56', 'asdasdqwdaxdqwedqwd', 'onekey', '你傻逼啊', '2016-10-14 14:54:56', '13269158110', 'N');
INSERT INTO `t_sys_feedback` VALUES ('29', '13269128687', '2016-10-22 13:35:33', 'asdasdqwdaxdqwedqwd', 'onekey', '好，马上处理！', '2016-10-22 13:35:33', 'admin', 'N');
INSERT INTO `t_sys_feedback` VALUES ('30', '13269128687', '2016-10-10 16:42:41', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:41', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('31', '13269128687', '2016-10-10 16:42:41', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:41', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('32', '13269128687', '2016-10-10 16:42:42', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:42', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('33', '13269128687', '2016-10-10 16:42:42', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:42', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('34', '13269128687', '2016-10-10 16:42:42', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:42', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('35', '13269128687', '2016-10-10 16:42:42', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:42', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('36', '13269128687', '2016-10-10 16:42:42', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:42', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('37', '13269128687', '2016-10-10 16:42:42', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:42', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('38', '13269128687', '2016-10-10 16:42:49', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:49', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('39', '13269128687', '2016-10-10 16:42:49', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:49', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('40', '13269128687', '2016-10-10 16:42:49', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:49', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('41', '13269128687', '2016-10-10 16:42:49', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:49', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('42', '13269128687', '2016-10-10 16:42:49', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:49', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('43', '13269128687', '2016-10-10 16:42:49', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:49', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('44', '13269128687', '2016-10-10 16:42:50', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:50', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('45', '13269128687', '2016-10-10 16:42:50', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:50', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('46', '13269128687', '2016-10-10 16:42:50', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:50', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('47', '13269128687', '2016-10-10 16:42:50', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:50', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('48', '13269128687', '2016-10-10 16:42:50', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:50', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('49', '13269128687', '2016-10-10 16:42:50', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:50', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('50', '13269128687', '2016-10-10 16:42:50', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:50', null, 'Y');
INSERT INTO `t_sys_feedback` VALUES ('51', '13269128687', '2016-10-10 16:42:50', 'asdasdqwdaxdqwedqwd', 'onekey', null, '2016-10-10 16:42:50', null, 'Y');

-- ----------------------------
-- Table structure for t_sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log_login`;
CREATE TABLE `t_sys_log_login` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(50) NOT NULL,
  `LOGIN_LATITUDE` double(8,6) DEFAULT NULL,
  `LOGIN_LONGITUDE` double(9,6) DEFAULT NULL,
  `LOGIN_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LOGIN_IP` char(15) DEFAULT '0.0.0.0',
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=685 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_log_login
-- ----------------------------
INSERT INTO `t_sys_log_login` VALUES ('1', '13240150408', '39.997598', '116.434199', '2016-10-10 21:41:34', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('3', '13240150408', '39.996951', '116.438751', '2016-10-10 21:43:39', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('4', '13240150408', '39.996951', '116.438751', '2016-10-10 21:45:06', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('5', '13240150408', '39.996893', '116.438399', '2016-10-10 21:47:14', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('6', '13240150408', '39.996893', '116.438399', '2016-10-10 21:48:25', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('7', '13240150408', '39.996893', '116.438399', '2016-10-10 21:49:26', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('8', '13240150408', '39.996893', '116.438399', '2016-10-10 21:52:13', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('9', '13240150408', '39.996893', '116.438399', '2016-10-10 21:53:37', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('10', '13240150408', '39.996893', '116.438399', '2016-10-10 21:55:04', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('11', '13240150408', '39.996873', '116.438410', '2016-10-10 21:56:50', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('12', '13240150408', '39.996893', '116.438399', '2016-10-10 21:59:48', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('13', '13240150408', '39.996893', '116.438399', '2016-10-10 22:56:28', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('14', '13240150408', '39.996893', '116.438399', '2016-10-10 22:56:57', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('15', '13240150408', '39.996893', '116.438399', '2016-10-10 22:58:43', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('16', '13240150408', '39.996893', '116.438399', '2016-10-10 23:01:13', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('17', '13240150408', '39.996893', '116.438399', '2016-10-10 23:02:16', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('18', '13240150408', '39.996873', '116.438410', '2016-10-10 23:04:19', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('19', '13240150408', '39.997598', '116.434199', '2016-10-10 23:27:19', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('20', '13240150408', '39.997598', '116.434199', '2016-10-10 23:31:04', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('21', '13240150408', '39.997597', '116.434204', '2016-10-10 23:33:08', '114.242.250.218');
INSERT INTO `t_sys_log_login` VALUES ('22', '13240150408', '39.996660', '116.432577', '2016-10-11 10:53:27', '114.242.248.47');
INSERT INTO `t_sys_log_login` VALUES ('23', '13240150408', '39.996669', '116.432570', '2016-10-11 11:04:09', '114.242.248.47');
INSERT INTO `t_sys_log_login` VALUES ('24', '13240150408', '39.996660', '116.432577', '2016-10-11 11:06:06', '114.242.248.47');
INSERT INTO `t_sys_log_login` VALUES ('25', '13240150408', '39.996660', '116.432577', '2016-10-11 11:15:54', '114.242.248.47');
INSERT INTO `t_sys_log_login` VALUES ('26', '13240150408', '39.997575', '116.434157', '2016-10-11 11:37:48', '114.242.248.47');
INSERT INTO `t_sys_log_login` VALUES ('27', '13240150408', '39.996873', '116.438410', '2016-10-11 13:07:56', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('28', '13240150408', '39.997655', '116.434687', '2016-10-11 13:10:25', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('29', '13240150408', '39.997654', '116.434687', '2016-10-11 13:12:46', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('30', '13240150408', '39.996873', '116.438410', '2016-10-11 13:47:23', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('31', '13240150408', '39.997575', '116.434247', '2016-10-11 13:48:07', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('32', '13240150408', '39.997575', '116.434247', '2016-10-11 13:48:39', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('33', '13240150408', '39.996873', '116.438410', '2016-10-11 13:52:31', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('34', '13240150408', '39.996873', '116.438410', '2016-10-11 14:05:11', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('35', '13240150408', '39.997578', '116.434607', '2016-10-11 14:08:56', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('36', '13240150408', '39.996873', '116.438410', '2016-10-11 14:10:49', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('37', '13240150408', '39.997672', '116.434675', '2016-10-11 14:15:02', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('38', '13240150408', '39.996873', '116.438410', '2016-10-11 14:22:09', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('39', '13240150408', '39.997672', '116.434675', '2016-10-11 14:32:10', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('40', '13240150408', '39.997672', '116.434675', '2016-10-11 14:47:20', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('41', '13240150408', '39.997684', '116.434696', '2016-10-11 14:50:13', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('42', '13240150408', '39.997575', '116.434247', '2016-10-11 14:52:01', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('43', '13240150408', '39.997805', '116.434648', '2016-10-11 14:54:46', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('44', '13240150408', '39.997575', '116.434247', '2016-10-11 14:57:15', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('45', '13240150408', '39.997652', '116.434687', '2016-10-11 15:03:35', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('46', '13240150408', '39.996873', '116.438410', '2016-10-11 15:11:46', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('47', '13240150408', '39.997575', '116.434247', '2016-10-11 15:20:00', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('48', '13240150408', '39.997872', '116.434886', '2016-10-11 15:26:51', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('49', '13240150408', '39.996873', '116.438410', '2016-10-11 15:32:12', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('50', '13240150408', '39.997575', '116.434247', '2016-10-11 15:46:43', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('51', '13240150408', '39.997496', '116.435016', '2016-10-11 15:51:42', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('52', '13240150408', '39.997575', '116.434247', '2016-10-11 15:56:42', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('53', '13240150408', '39.997575', '116.434247', '2016-10-11 16:07:08', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('54', '13240150408', '39.997684', '116.434696', '2016-10-11 16:09:04', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('55', '13240150408', '39.997343', '116.434497', '2016-10-11 16:11:20', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('56', '13240150408', '39.997575', '116.434247', '2016-10-11 16:16:37', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('57', '13240150408', '39.997805', '116.434667', '2016-10-11 16:25:17', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('58', '13240150408', '39.996873', '116.438410', '2016-10-11 16:26:19', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('59', '13240150408', '39.996754', '116.433907', '2016-10-11 16:55:23', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('60', '13240150408', '39.996745', '116.433627', '2016-10-11 17:16:22', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('61', '13240150408', '39.996700', '116.433809', '2016-10-11 17:16:39', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('62', '13240150408', '39.996821', '116.433815', '2016-10-11 17:21:21', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('63', '13240150408', '39.996830', '116.433959', '2016-10-11 17:30:07', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('64', '13240150408', '39.996814', '116.433836', '2016-10-11 17:34:16', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('65', '13240150408', '39.996814', '116.433836', '2016-10-11 17:35:50', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('66', '13240150408', '39.996700', '116.433809', '2016-10-11 17:36:55', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('67', '13240150408', '39.996775', '116.433851', '2016-10-11 17:38:26', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('68', '13240150408', '39.996761', '116.433859', '2016-10-11 17:40:37', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('69', '13240150408', '39.996808', '116.433860', '2016-10-11 17:41:57', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('70', '13240150408', '39.996808', '116.433860', '2016-10-11 17:42:42', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('71', '13240150408', '39.996759', '116.433796', '2016-10-11 17:43:26', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('72', '13240150408', '39.996700', '116.433809', '2016-10-11 17:52:38', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('73', '13240150408', '39.996800', '116.433844', '2016-10-11 17:53:10', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('74', '13240150408', '39.996850', '116.433848', '2016-10-11 17:55:41', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('75', '13240150408', '39.996759', '116.433871', '2016-10-11 17:59:20', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('76', '13240150408', '39.996831', '116.433900', '2016-10-11 18:01:26', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('77', '13240150408', '39.997250', '116.434716', '2016-10-11 18:05:01', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('78', '13240150408', '39.997250', '116.434716', '2016-10-11 18:05:31', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('79', '13240150408', '39.991629', '116.430507', '2016-10-11 18:08:29', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('80', '13240150408', '39.997573', '116.434169', '2016-10-11 18:14:22', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('81', '13240150408', '39.997573', '116.434169', '2016-10-11 18:14:38', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('82', '13240150408', '39.999513', '116.428334', '2016-10-11 19:28:58', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('83', '13240150408', '39.999628', '116.428381', '2016-10-11 19:46:25', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('84', '13240150408', '39.999628', '116.428381', '2016-10-11 20:08:50', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('85', '13240150408', '39.991638', '116.430543', '2016-10-11 20:12:36', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('86', '13240150408', '39.997575', '116.434157', '2016-10-11 20:14:55', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('87', '13240150408', '39.999472', '116.428392', '2016-10-11 20:17:32', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('88', '13240150408', '39.999628', '116.428381', '2016-10-11 20:19:50', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('89', '13240150408', '39.996700', '116.433809', '2016-10-11 20:23:13', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('90', '13240150408', '39.999628', '116.428381', '2016-10-11 20:26:01', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('91', '13240150408', '39.999628', '116.428381', '2016-10-11 20:30:52', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('92', '13240150408', '39.995129', '116.434052', '2016-10-11 20:33:07', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('93', '13240150408', '39.995129', '116.434052', '2016-10-11 20:33:58', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('94', '13240150408', '39.995129', '116.434052', '2016-10-11 20:36:23', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('95', '13240150408', '39.991638', '116.430543', '2016-10-11 20:43:56', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('96', '13240150408', '39.999472', '116.428392', '2016-10-11 21:01:27', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('97', '13240150408', '39.999472', '116.428392', '2016-10-11 21:04:10', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('98', '13240150408', '39.999472', '116.428392', '2016-10-11 21:42:29', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('99', '13240150408', '39.999472', '116.428392', '2016-10-11 21:45:50', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('100', '13240150408', '39.996700', '116.433809', '2016-10-11 21:47:04', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('101', '13240150408', '39.996700', '116.433809', '2016-10-11 22:15:23', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('102', '13240150408', '39.999472', '116.428392', '2016-10-11 22:41:01', '61.148.244.1');
INSERT INTO `t_sys_log_login` VALUES ('103', '13240150408', '39.996805', '116.433846', '2016-10-11 22:44:27', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('104', '13240150408', '39.996805', '116.433846', '2016-10-11 22:47:01', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('105', '13240150408', '39.996739', '116.433866', '2016-10-11 22:49:40', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('106', '13240150408', '39.996739', '116.433866', '2016-10-11 22:52:53', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('107', '13240150408', '39.996739', '116.433866', '2016-10-11 22:55:55', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('108', '13240150408', '39.996739', '116.433866', '2016-10-11 22:57:58', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('109', '13240150408', '39.996700', '116.433809', '2016-10-11 23:01:11', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('110', '13240150408', '39.996842', '116.433834', '2016-10-11 23:02:44', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('111', '13240150408', '39.997542', '116.435001', '2016-10-11 23:27:55', '61.148.242.103');
INSERT INTO `t_sys_log_login` VALUES ('112', '13240150408', '39.997644', '116.434960', '2016-10-12 00:21:31', '61.148.242.5');
INSERT INTO `t_sys_log_login` VALUES ('113', '13240150408', '39.997648', '116.434639', '2016-10-12 09:39:33', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('114', '13269128687', '39.997685', '116.434669', '2016-10-12 10:04:27', '61.148.244.83');
INSERT INTO `t_sys_log_login` VALUES ('115', '13240150408', '39.997656', '116.434688', '2016-10-12 11:10:10', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('116', '13240150408', '39.997683', '116.434697', '2016-10-12 11:11:50', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('117', '13240150408', '39.996700', '116.433809', '2016-10-12 13:20:36', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('118', '13240150408', '39.997718', '116.434473', '2016-10-12 13:20:55', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('119', '13240150408', '39.996712', '116.434018', '2016-10-12 14:01:12', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('120', '13240150408', '39.996662', '116.433933', '2016-10-12 14:03:08', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('121', '13240150408', '39.996762', '116.434000', '2016-10-12 14:04:53', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('122', '13240150408', '39.996700', '116.433809', '2016-10-12 14:16:07', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('123', '13240150408', '39.996700', '116.433809', '2016-10-12 14:17:13', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('124', '13240150408', '39.996594', '116.433968', '2016-10-12 14:18:08', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('125', '13240150408', '39.997575', '116.434154', '2016-10-12 14:18:37', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('126', '13240150408', '39.997481', '116.437227', '2016-10-12 14:22:32', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('127', '13240150408', '39.999853', '116.436851', '2016-10-12 14:23:13', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('128', '13240150408', '39.999472', '116.428395', '2016-10-12 14:24:31', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('129', '13240150408', '39.997599', '116.434197', '2016-10-12 14:25:16', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('130', '13240150408', '39.999472', '116.428395', '2016-10-12 14:30:47', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('131', '13240150408', '39.999472', '116.428395', '2016-10-12 14:36:58', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('132', '13240150408', '39.997599', '116.434197', '2016-10-12 14:42:41', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('133', '13240150408', '39.997598', '116.434197', '2016-10-12 14:43:09', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('134', '13240150408', '39.997599', '116.434197', '2016-10-12 14:44:31', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('135', '13240150408', '39.997599', '116.434197', '2016-10-12 14:45:54', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('136', '13240150408', '39.999627', '116.428385', '2016-10-12 14:49:45', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('137', '13240150408', '39.997598', '116.434197', '2016-10-12 14:53:14', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('138', '13240150408', '39.997599', '116.434197', '2016-10-12 14:53:46', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('139', '13240150408', '40.000138', '116.436857', '2016-10-12 14:54:51', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('140', '13240150408', '39.999860', '116.436825', '2016-10-12 14:55:13', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('141', '13240150408', '39.997575', '116.434154', '2016-10-12 14:58:39', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('142', '13240150408', '39.999627', '116.428385', '2016-10-12 14:59:55', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('143', '13240150408', '39.991634', '116.430612', '2016-10-12 15:07:15', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('144', '13240150408', '39.999472', '116.428395', '2016-10-12 15:10:44', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('145', '13240150408', '39.997598', '116.434197', '2016-10-12 15:11:20', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('146', '13240150408', '39.997475', '116.437215', '2016-10-12 15:17:05', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('147', '13240150408', '39.991634', '116.430612', '2016-10-12 15:47:49', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('148', '13240150408', '39.997598', '116.434197', '2016-10-12 15:53:29', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('149', '13240150408', '39.996947', '116.438754', '2016-10-12 17:29:02', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('150', '13240150408', '39.996894', '116.438421', '2016-10-12 17:46:04', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('151', '13240150408', '39.991634', '116.430612', '2016-10-12 17:49:05', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('152', '13240150408', '39.996894', '116.438421', '2016-10-12 17:51:52', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('153', '13240150408', '39.997598', '116.434197', '2016-10-12 17:57:22', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('154', '13240150408', '39.997598', '116.434197', '2016-10-12 17:57:53', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('155', '13240150408', '39.991634', '116.430612', '2016-10-12 18:06:15', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('156', '13240150408', '39.996894', '116.438421', '2016-10-12 18:17:30', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('157', '13240150408', '39.991634', '116.430612', '2016-10-12 18:35:16', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('158', '13240150408', '39.996895', '116.438421', '2016-10-12 18:35:43', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('159', '13240150408', '39.996895', '116.438421', '2016-10-12 19:03:09', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('160', '13240150408', '39.997575', '116.434154', '2016-10-12 19:03:09', '61.148.243.173');
INSERT INTO `t_sys_log_login` VALUES ('161', '13240150408', '39.996818', '116.433788', '2016-10-12 19:03:09', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('162', '13240150408', '39.996894', '116.438421', '2016-10-12 19:03:09', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('163', '13240150408', '39.997575', '116.434154', '2016-10-12 19:03:09', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('164', '13269128687', '0.000000', '0.000000', '2016-10-12 19:42:30', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('165', '13240150408', '39.996886', '116.433761', '2016-10-12 19:43:27', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('166', '13269128687', '0.000000', '0.000000', '2016-10-12 19:43:27', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('167', '13269128687', '0.000000', '0.000000', '2016-10-12 19:48:18', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('168', '13240150408', '39.996886', '116.433761', '2016-10-12 19:49:19', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('169', '13240150408', '39.996684', '116.433797', '2016-10-12 19:51:20', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('170', '13240150408', '39.996684', '116.433797', '2016-10-12 19:51:33', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('171', '13240150408', '39.996684', '116.433797', '2016-10-12 19:53:19', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('172', '13240150408', '39.996684', '116.433797', '2016-10-12 19:53:34', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('173', '13240150408', '39.996684', '116.433797', '2016-10-12 19:57:02', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('174', '13240150408', '39.996841', '116.433607', '2016-10-12 19:58:28', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('175', '13240150408', '39.996720', '116.433740', '2016-10-12 20:00:19', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('176', '13240150408', '39.996700', '116.433728', '2016-10-12 20:02:19', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('177', '13240150408', '39.991634', '116.430612', '2016-10-12 20:08:07', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('178', '13240150408', '39.996947', '116.438754', '2016-10-12 20:16:17', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('179', '13240150408', '39.996947', '116.438754', '2016-10-12 20:17:45', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('180', '13240150408', '39.996947', '116.438754', '2016-10-12 20:19:18', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('181', '13240150408', '39.996947', '116.438754', '2016-10-12 20:20:14', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('182', '13240150408', '39.997575', '116.434154', '2016-10-12 20:23:17', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('183', '13240150408', '39.997575', '116.434154', '2016-10-12 20:25:14', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('184', '13240150408', '39.996894', '116.438421', '2016-10-12 20:29:52', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('185', '13240150408', '39.996894', '116.438421', '2016-10-12 20:31:45', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('186', '13240150408', '39.996895', '116.438421', '2016-10-12 20:32:01', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('187', '13240150408', '39.996894', '116.438421', '2016-10-12 20:33:50', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('188', '13240150408', '39.996947', '116.438754', '2016-10-12 20:43:05', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('189', '13240150408', '39.996841', '116.433607', '2016-10-12 20:51:00', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('190', '13240150408', '39.996894', '116.438421', '2016-10-12 20:52:20', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('191', '13240150408', '39.996894', '116.438421', '2016-10-12 20:54:16', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('192', '13240150408', '39.997575', '116.434154', '2016-10-12 20:55:21', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('193', '13240150408', '39.996894', '116.438421', '2016-10-12 20:55:47', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('194', '13240150408', '39.996894', '116.438421', '2016-10-12 20:56:46', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('195', '13240150408', '39.996894', '116.438421', '2016-10-12 20:57:21', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('196', '13240150408', '39.996894', '116.438421', '2016-10-12 21:09:12', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('197', '13240150408', '39.996894', '116.438421', '2016-10-12 21:14:14', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('198', '13240150408', '39.996947', '116.438754', '2016-10-12 21:15:06', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('199', '13240150408', '39.997481', '116.437227', '2016-10-12 21:17:10', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('200', '13240150408', '39.997598', '116.434197', '2016-10-12 21:17:35', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('201', '13240150408', '39.997598', '116.434197', '2016-10-12 21:18:07', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('202', '13240150408', '39.996947', '116.438754', '2016-10-12 21:20:05', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('203', '13240150408', '39.991634', '116.430612', '2016-10-12 21:21:56', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('204', '13240150408', '39.997598', '116.434197', '2016-10-12 21:23:44', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('205', '13240150408', '39.997598', '116.434197', '2016-10-12 21:24:03', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('206', '13240150408', '39.996947', '116.438754', '2016-10-12 21:25:44', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('207', '13240150408', '39.996947', '116.438754', '2016-10-12 21:26:03', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('208', '13240150408', '39.996947', '116.438754', '2016-10-12 21:27:25', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('209', '13240150408', '39.996947', '116.438754', '2016-10-12 21:27:40', '61.148.242.49');
INSERT INTO `t_sys_log_login` VALUES ('210', '13240150408', '39.997482', '116.437227', '2016-10-12 21:37:10', '61.148.242.13');
INSERT INTO `t_sys_log_login` VALUES ('211', '13240150408', '39.996894', '116.438421', '2016-10-12 21:42:13', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('212', '13240150408', '39.997598', '116.434197', '2016-10-12 21:57:35', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('213', '13240150408', '39.996841', '116.433607', '2016-10-12 21:59:30', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('214', '13240150408', '39.997598', '116.434197', '2016-10-12 22:04:39', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('215', '13240150408', '39.996947', '116.438754', '2016-10-12 22:05:19', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('216', '13240150408', '39.996947', '116.438754', '2016-10-12 22:08:48', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('217', '13240150408', '39.996947', '116.438754', '2016-10-12 22:13:00', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('218', '13240150408', '39.996841', '116.433607', '2016-10-12 22:17:17', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('219', '13240150408', '39.996894', '116.438421', '2016-10-12 22:24:30', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('220', '13611108060', '39.996895', '116.438421', '2016-10-12 22:24:55', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('221', '13611108060', '39.997575', '116.434154', '2016-10-12 22:26:08', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('222', '13611108060', '39.996947', '116.438754', '2016-10-12 22:39:44', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('223', '13611108060', '39.996947', '116.438754', '2016-10-12 22:41:28', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('224', '13611108060', '39.996948', '116.438754', '2016-10-12 22:42:45', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('225', '13240150408', '39.996947', '116.438754', '2016-10-12 22:44:28', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('226', '13240150408', '39.997575', '116.434154', '2016-10-12 22:51:07', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('227', '13240150408', '39.996947', '116.438754', '2016-10-12 22:59:15', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('228', '13611108060', '39.996947', '116.438754', '2016-10-12 23:00:25', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('229', '13611108060', '39.996948', '116.438754', '2016-10-12 23:01:05', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('230', '13611108060', '39.996947', '116.438754', '2016-10-12 23:03:33', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('231', '13240150408', '39.996947', '116.438754', '2016-10-12 23:03:59', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('232', '13240150408', '39.996894', '116.438421', '2016-10-12 23:06:04', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('233', '13240150408', '39.996894', '116.438421', '2016-10-12 23:08:59', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('234', '13611108060', '39.996895', '116.438421', '2016-10-12 23:09:28', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('235', '13611108060', '39.996894', '116.438421', '2016-10-12 23:10:46', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('236', '13611108060', '39.997575', '116.434249', '2016-10-12 23:12:45', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('237', '13240150408', '39.995123', '116.434101', '2016-10-12 23:13:06', '61.148.244.252');
INSERT INTO `t_sys_log_login` VALUES ('238', '13269128687', '39.997685', '116.434668', '2016-10-12 23:41:10', '61.148.244.83');
INSERT INTO `t_sys_log_login` VALUES ('239', '13269128687', '39.997685', '116.434668', '2016-10-12 23:42:00', '61.148.244.83');
INSERT INTO `t_sys_log_login` VALUES ('240', '13269128687', '39.997685', '116.434669', '2016-10-12 23:43:08', '61.148.244.83');
INSERT INTO `t_sys_log_login` VALUES ('241', '13240150408', '39.997805', '116.434649', '2016-10-13 10:10:35', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('242', '13240150408', '39.997660', '116.434658', '2016-10-13 10:12:50', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('243', '13240150408', '39.997921', '116.434750', '2016-10-13 10:40:13', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('244', '13240150408', '39.997921', '116.434749', '2016-10-13 10:46:41', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('245', '13240150408', '39.997921', '116.434749', '2016-10-13 10:47:55', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('246', '13240150408', '39.997921', '116.434750', '2016-10-13 10:48:52', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('247', '13240150408', '39.997575', '116.434246', '2016-10-13 10:50:16', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('248', '13240150408', '39.997575', '116.434246', '2016-10-13 10:51:04', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('249', '13240150408', '39.997575', '116.434246', '2016-10-13 10:55:49', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('250', '13240150408', '39.997575', '116.434246', '2016-10-13 10:56:39', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('251', '13240150408', '39.997575', '116.434246', '2016-10-13 10:58:22', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('252', '13240150408', '39.997575', '116.434246', '2016-10-13 10:59:14', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('253', '13240150408', '39.997575', '116.434246', '2016-10-13 11:00:19', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('254', '13240150408', '39.997575', '116.434246', '2016-10-13 11:01:17', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('255', '13240150408', '39.997575', '116.434246', '2016-10-13 11:03:18', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('256', '13240150408', '39.997575', '116.434246', '2016-10-13 11:13:30', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('257', '13240150408', '39.997575', '116.434246', '2016-10-13 11:21:46', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('258', '13240150408', '39.997575', '116.434246', '2016-10-13 11:22:54', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('259', '13240150408', '39.997921', '116.434750', '2016-10-13 11:29:30', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('260', '13240150408', '39.997921', '116.434750', '2016-10-13 11:33:40', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('261', '13240150408', '39.997729', '116.434875', '2016-10-13 12:10:39', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('262', '13240150408', '39.997921', '116.434750', '2016-10-13 12:12:13', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('263', '13240150408', '39.997669', '116.434713', '2016-10-13 12:15:04', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('264', '13240150408', '39.997777', '116.434766', '2016-10-13 12:19:59', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('265', '13240150408', '39.997890', '116.434735', '2016-10-13 12:23:57', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('266', '13240150408', '39.997890', '116.434735', '2016-10-13 12:24:56', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('267', '13240150408', '39.997669', '116.434713', '2016-10-13 12:31:58', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('268', '13240150408', '39.997575', '116.434246', '2016-10-13 12:33:36', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('269', '13240150408', '39.997668', '116.434710', '2016-10-13 12:37:01', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('270', '13269128687', '39.996953', '116.438746', '2016-10-13 12:37:25', '61.148.242.173');
INSERT INTO `t_sys_log_login` VALUES ('271', '13240150408', '39.997840', '116.434720', '2016-10-13 12:37:25', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('272', '13240150408', '39.997236', '116.434442', '2016-10-13 12:42:37', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('273', '13240150408', '39.997403', '116.434596', '2016-10-13 12:58:46', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('274', '13240150408', '39.997464', '116.434458', '2016-10-13 13:00:05', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('275', '13240150408', '39.997447', '116.434548', '2016-10-13 13:00:21', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('276', '13240150408', '39.997796', '116.434542', '2016-10-13 13:01:08', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('277', '13240150408', '39.997596', '116.434196', '2016-10-13 13:01:45', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('278', '13240150408', '39.997575', '116.434246', '2016-10-13 13:03:52', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('279', '13240150408', '39.997798', '116.434683', '2016-10-13 13:16:59', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('280', '13240150408', '39.997743', '116.434797', '2016-10-13 13:23:50', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('281', '13240150408', '39.997672', '116.434677', '2016-10-13 13:25:17', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('282', '13240150408', '39.997575', '116.434246', '2016-10-13 13:34:12', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('283', '13240150408', '39.997666', '116.434671', '2016-10-13 13:38:47', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('284', '13269128687', '39.997666', '116.434670', '2016-10-13 13:39:46', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('285', '13269128687', '39.997666', '116.434671', '2016-10-13 13:40:06', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('286', '13269128687', '39.997666', '116.434671', '2016-10-13 13:40:32', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('287', '13269128687', '39.997666', '116.434670', '2016-10-13 13:43:32', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('288', '13269128687', '39.997666', '116.434670', '2016-10-13 13:48:01', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('289', '13269128687', '39.997665', '116.434669', '2016-10-13 14:00:53', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('290', '13269128687', '39.997672', '116.434677', '2016-10-13 14:02:34', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('291', '13269128687', '39.997470', '116.434557', '2016-10-13 14:13:28', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('292', '13269128687', '39.997665', '116.434669', '2016-10-13 14:14:47', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('293', '13269128687', '39.997665', '116.434669', '2016-10-13 14:16:08', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('294', '13269128687', '39.997665', '116.434670', '2016-10-13 14:17:29', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('295', '13269128687', '39.997575', '116.434246', '2016-10-13 14:21:47', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('296', '13269128687', '39.997575', '116.434246', '2016-10-13 14:24:24', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('297', '13269128687', '39.997657', '116.434653', '2016-10-13 14:31:06', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('298', '13269128687', '39.997575', '116.434246', '2016-10-13 14:32:42', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('299', '13269128687', '39.997666', '116.434670', '2016-10-13 14:33:56', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('300', '13269128687', '39.997575', '116.434246', '2016-10-13 14:34:27', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('301', '13269128687', '39.997575', '116.434246', '2016-10-13 14:38:01', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('302', '13269128687', '39.997672', '116.434677', '2016-10-13 15:03:13', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('303', '13269128687', '39.997666', '116.434670', '2016-10-13 15:06:44', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('304', '13269128687', '39.997575', '116.434246', '2016-10-13 15:07:38', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('305', '13269128687', '39.997672', '116.434677', '2016-10-13 15:10:18', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('306', '13269128687', '39.997672', '116.434677', '2016-10-13 15:11:21', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('307', '13269128687', '39.997575', '116.434246', '2016-10-13 15:12:03', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('308', '13269128687', '39.997575', '116.434246', '2016-10-13 15:12:59', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('309', '13269128687', '39.997575', '116.434246', '2016-10-13 15:13:48', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('310', '13269128687', '39.997660', '116.434659', '2016-10-13 15:14:55', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('311', '13269128687', '39.997921', '116.434749', '2016-10-13 15:18:49', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('312', '13269128687', '39.997575', '116.434246', '2016-10-13 15:22:05', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('313', '13269128687', '39.997611', '116.434804', '2016-10-13 15:27:25', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('314', '13269128687', '39.997611', '116.434804', '2016-10-13 15:28:19', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('315', '13269128687', '39.997613', '116.434801', '2016-10-13 15:29:54', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('316', '13269128687', '39.997575', '116.434246', '2016-10-13 15:45:26', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('317', '13269128687', '39.997672', '116.434677', '2016-10-13 15:48:11', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('318', '13240150408', '39.997971', '116.434456', '2016-10-13 15:49:56', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('319', '13240150408', '39.997806', '116.434338', '2016-10-13 15:50:53', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('320', '13240150408', '39.997730', '116.434774', '2016-10-13 15:56:06', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('321', '13240150408', '39.991580', '116.430649', '2016-10-13 16:01:02', '114.242.249.226');
INSERT INTO `t_sys_log_login` VALUES ('322', '13240150408', '39.997596', '116.434196', '2016-10-13 16:07:30', '61.148.243.247');
INSERT INTO `t_sys_log_login` VALUES ('323', '13240150408', '39.997596', '116.434196', '2016-10-13 16:09:06', '61.148.242.50');
INSERT INTO `t_sys_log_login` VALUES ('324', '13269128687', '39.997596', '116.434196', '2016-10-13 16:12:11', '61.148.244.90');
INSERT INTO `t_sys_log_login` VALUES ('325', '13240150408', '39.997599', '116.434197', '2016-10-13 16:12:44', '61.148.244.90');
INSERT INTO `t_sys_log_login` VALUES ('326', '13240150408', '39.997596', '116.434196', '2016-10-13 16:15:36', '61.148.242.145');
INSERT INTO `t_sys_log_login` VALUES ('327', '13240150408', '39.997596', '116.434196', '2016-10-13 16:17:25', '61.148.242.114');
INSERT INTO `t_sys_log_login` VALUES ('328', '13240150408', '39.997596', '116.434196', '2016-10-13 16:18:31', '61.148.242.114');
INSERT INTO `t_sys_log_login` VALUES ('329', '13240150408', '39.997599', '116.434197', '2016-10-13 16:19:47', '61.148.242.114');
INSERT INTO `t_sys_log_login` VALUES ('330', '13240150408', '39.997596', '116.434196', '2016-10-13 16:20:27', '61.148.242.114');
INSERT INTO `t_sys_log_login` VALUES ('331', '13240150408', '39.997596', '116.434196', '2016-10-13 16:22:41', '61.148.242.114');
INSERT INTO `t_sys_log_login` VALUES ('332', '13240150408', '39.997599', '116.434197', '2016-10-13 16:24:42', '61.148.242.114');
INSERT INTO `t_sys_log_login` VALUES ('333', '13240150408', '39.997599', '116.434197', '2016-10-13 16:28:59', '61.148.242.114');
INSERT INTO `t_sys_log_login` VALUES ('334', '13240150408', '39.997596', '116.434196', '2016-10-13 17:24:24', '61.148.242.114');
INSERT INTO `t_sys_log_login` VALUES ('335', '13240150408', '39.997596', '116.434196', '2016-10-13 17:29:38', '61.148.242.114');
INSERT INTO `t_sys_log_login` VALUES ('336', '13240150408', '39.997596', '116.434196', '2016-10-13 17:31:28', '61.148.242.114');
INSERT INTO `t_sys_log_login` VALUES ('337', '13240150408', '39.997575', '116.434246', '2016-10-13 17:42:17', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('338', '13240150408', '39.997681', '116.434701', '2016-10-13 18:05:30', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('339', '13240150408', '39.997672', '116.434677', '2016-10-13 18:08:46', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('340', '13240150408', '39.997672', '116.434677', '2016-10-13 18:14:34', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('341', '13240150408', '39.997921', '116.434749', '2016-10-13 18:50:19', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('342', '13240150408', '39.997921', '116.434749', '2016-10-13 18:50:25', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('343', '13240150408', '39.997921', '116.434749', '2016-10-13 18:51:03', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('344', '13240150408', '39.997921', '116.434749', '2016-10-13 18:51:45', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('345', '13240150408', '39.997921', '116.434749', '2016-10-13 18:51:58', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('346', '13240150408', '39.997923', '116.434756', '2016-10-13 18:55:36', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('347', '13240150408', '39.999473', '116.428394', '2016-10-13 20:36:23', '61.148.243.53');
INSERT INTO `t_sys_log_login` VALUES ('348', '13240150408', '39.996838', '116.433886', '2016-10-13 20:45:03', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('349', '13240150408', '39.996838', '116.433886', '2016-10-13 20:48:15', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('350', '13240150408', '39.996838', '116.433886', '2016-10-13 20:52:03', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('351', '13240150408', '39.999103', '116.428652', '2016-10-13 20:55:15', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('352', '13240150408', '39.999103', '116.428652', '2016-10-13 20:56:24', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('353', '13240150408', '39.999103', '116.428652', '2016-10-13 20:57:40', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('354', '13240150408', '39.999103', '116.428652', '2016-10-13 21:00:11', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('355', '13240150408', '39.999103', '116.428652', '2016-10-13 21:01:20', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('356', '13240150408', '39.999103', '116.428652', '2016-10-13 21:04:16', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('357', '13240150408', '39.996701', '116.433879', '2016-10-13 21:05:25', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('358', '13240150408', '39.996701', '116.433879', '2016-10-13 21:06:21', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('359', '13240150408', '39.996701', '116.433879', '2016-10-13 21:08:46', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('360', '13240150408', '39.996701', '116.433879', '2016-10-13 21:10:13', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('361', '13240150408', '39.996701', '116.433879', '2016-10-13 21:11:32', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('362', '13240150408', '39.996726', '116.433943', '2016-10-13 21:13:59', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('363', '13240150408', '39.996763', '116.433940', '2016-10-13 21:16:07', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('364', '13240150408', '39.996780', '116.433940', '2016-10-13 21:17:39', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('365', '13240150408', '39.996799', '116.433886', '2016-10-13 21:19:39', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('366', '13240150408', '39.996795', '116.433932', '2016-10-13 21:21:39', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('367', '13240150408', '39.996781', '116.433921', '2016-10-13 21:24:44', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('368', '13240150408', '39.996790', '116.433811', '2016-10-13 21:27:36', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('369', '13240150408', '39.996772', '116.433831', '2016-10-13 21:30:48', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('370', '13240150408', '39.996750', '116.433868', '2016-10-13 21:32:50', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('371', '13240150408', '39.996746', '116.433888', '2016-10-13 21:38:48', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('372', '13240150408', '39.996746', '116.433888', '2016-10-13 21:40:46', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('373', '13240150408', '39.996746', '116.433888', '2016-10-13 21:43:32', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('374', '13240150408', '39.996746', '116.433888', '2016-10-13 21:45:06', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('375', '13240150408', '39.996746', '116.433888', '2016-10-13 21:49:42', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('376', '13240150408', '39.996845', '116.433754', '2016-10-13 21:55:39', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('377', '13240150408', '39.996873', '116.433724', '2016-10-13 21:58:27', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('378', '13240150408', '39.996868', '116.433683', '2016-10-13 21:59:56', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('379', '13240150408', '39.996773', '116.433944', '2016-10-13 22:02:12', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('380', '13240150408', '39.996750', '116.433868', '2016-10-13 22:04:36', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('381', '13240150408', '39.996803', '116.433919', '2016-10-13 22:05:21', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('382', '13240150408', '39.996803', '116.433919', '2016-10-13 22:07:01', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('383', '13240150408', '39.996780', '116.433775', '2016-10-13 22:08:28', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('384', '13240150408', '39.996848', '116.433874', '2016-10-13 22:13:41', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('385', '13240150408', '39.996774', '116.433912', '2016-10-13 22:16:27', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('386', '13240150408', '39.996783', '116.433749', '2016-10-13 22:17:35', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('387', '13240150408', '39.996750', '116.433868', '2016-10-13 22:19:02', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('388', '13240150408', '39.996762', '116.433824', '2016-10-13 22:21:25', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('389', '13240150408', '39.996668', '116.433842', '2016-10-13 22:24:48', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('390', '13240150408', '39.996669', '116.433767', '2016-10-13 22:29:26', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('391', '13240150408', '39.996637', '116.433678', '2016-10-13 22:30:32', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('392', '13269158110', '39.999859', '116.436824', '2016-10-13 22:38:55', '61.148.242.173');
INSERT INTO `t_sys_log_login` VALUES ('393', '13269158110', '39.999631', '116.428384', '2016-10-13 22:42:07', '61.148.242.173');
INSERT INTO `t_sys_log_login` VALUES ('394', '13269128687', '39.996694', '116.433993', '2016-10-13 22:47:05', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('395', '13269158110', '39.999631', '116.428384', '2016-10-13 22:47:16', '61.148.242.173');
INSERT INTO `t_sys_log_login` VALUES ('396', '13269158110', '39.999631', '116.428384', '2016-10-13 22:51:55', '61.148.242.173');
INSERT INTO `t_sys_log_login` VALUES ('397', '13269158110', '39.999631', '116.428384', '2016-10-13 23:05:19', '61.148.242.173');
INSERT INTO `t_sys_log_login` VALUES ('398', '13269128687', '39.996803', '116.433758', '2016-10-13 23:06:14', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('399', '13240150408', '39.996803', '116.433758', '2016-10-13 23:07:05', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('400', '13240150408', '39.997806', '116.434651', '2016-10-13 23:25:48', '114.242.248.45');
INSERT INTO `t_sys_log_login` VALUES ('401', '13240150408', '39.997354', '116.434766', '2016-10-13 23:37:00', '114.242.248.45');
INSERT INTO `t_sys_log_login` VALUES ('402', '13240150408', '39.997657', '116.434798', '2016-10-13 23:38:56', '114.242.248.45');
INSERT INTO `t_sys_log_login` VALUES ('403', '13240150408', '39.997627', '116.434718', '2016-10-13 23:40:26', '114.242.248.45');
INSERT INTO `t_sys_log_login` VALUES ('404', '13240150408', '39.997575', '116.434161', '2016-10-13 23:43:05', '114.242.248.45');
INSERT INTO `t_sys_log_login` VALUES ('405', '13269158110', '39.997806', '116.434651', '2016-10-14 00:07:59', '61.148.242.173');
INSERT INTO `t_sys_log_login` VALUES ('406', '13269158110', '39.997596', '116.434196', '2016-10-14 08:36:54', '61.148.242.248');
INSERT INTO `t_sys_log_login` VALUES ('407', '13269128687', '39.999859', '116.436825', '2016-10-14 09:03:24', '61.148.242.248');
INSERT INTO `t_sys_log_login` VALUES ('408', '13240150408', '39.999859', '116.436825', '2016-10-14 09:05:56', '61.148.242.248');
INSERT INTO `t_sys_log_login` VALUES ('409', '13240150408', '39.997477', '116.437214', '2016-10-14 09:29:43', '61.148.242.38');
INSERT INTO `t_sys_log_login` VALUES ('410', '13269128687', '39.997477', '116.437214', '2016-10-14 09:30:05', '61.148.242.38');
INSERT INTO `t_sys_log_login` VALUES ('411', '13269128687', '39.999472', '116.428395', '2016-10-14 09:52:04', '61.148.242.38');
INSERT INTO `t_sys_log_login` VALUES ('412', '13269128687', '39.999631', '116.428384', '2016-10-14 09:59:01', '61.148.242.38');
INSERT INTO `t_sys_log_login` VALUES ('413', '13240150408', '39.999631', '116.428384', '2016-10-14 09:59:16', '61.148.242.38');
INSERT INTO `t_sys_log_login` VALUES ('414', '13240150408', '39.999627', '116.428385', '2016-10-14 10:02:27', '61.148.242.38');
INSERT INTO `t_sys_log_login` VALUES ('415', '13240150408', '39.999627', '116.428385', '2016-10-14 10:02:56', '61.148.242.38');
INSERT INTO `t_sys_log_login` VALUES ('416', '13240150408', '39.999627', '116.428385', '2016-10-14 10:02:57', '61.148.242.38');
INSERT INTO `t_sys_log_login` VALUES ('417', '13240150408', '39.999473', '116.428394', '2016-10-14 11:21:07', '61.148.242.248');
INSERT INTO `t_sys_log_login` VALUES ('418', '13269158110', '39.999473', '116.428394', '2016-10-14 11:21:27', '61.148.242.248');
INSERT INTO `t_sys_log_login` VALUES ('419', '13269128687', '39.999473', '116.428394', '2016-10-14 11:23:34', '61.148.242.248');
INSERT INTO `t_sys_log_login` VALUES ('420', '13269128687', '39.997596', '116.434196', '2016-10-14 12:25:28', '61.148.242.248');
INSERT INTO `t_sys_log_login` VALUES ('421', '13269128687', '39.997596', '116.434196', '2016-10-14 12:32:40', '61.148.242.248');
INSERT INTO `t_sys_log_login` VALUES ('422', '13269128687', '39.997902', '116.434203', '2016-10-14 13:20:46', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('423', '13240150408', '39.997921', '116.434749', '2016-10-14 13:45:25', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('424', '13240150408', '39.997575', '116.434243', '2016-10-14 14:07:13', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('425', '13240150408', '39.997575', '116.434246', '2016-10-14 14:08:55', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('426', '13240150408', '39.997575', '116.434243', '2016-10-14 14:10:25', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('427', '13240150408', '39.997825', '116.434711', '2016-10-14 14:12:11', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('428', '13240150408', '39.997667', '116.434708', '2016-10-14 14:13:45', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('429', '13269128687', '39.996955', '116.438733', '2016-10-14 14:14:34', '61.148.244.174');
INSERT INTO `t_sys_log_login` VALUES ('430', '13240150408', '39.997672', '116.434718', '2016-10-14 14:18:45', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('431', '13269128687', '39.997672', '116.434717', '2016-10-14 14:20:16', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('432', '13269128687', '39.997574', '116.434161', '2016-10-14 14:23:50', '61.148.244.174');
INSERT INTO `t_sys_log_login` VALUES ('433', '13269128687', '39.997673', '116.434716', '2016-10-14 14:26:44', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('434', '13269128687', '39.997674', '116.434715', '2016-10-14 14:30:46', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('435', '13240150408', '39.997674', '116.434715', '2016-10-14 14:31:04', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('436', '13240150408', '39.997673', '116.434716', '2016-10-14 14:34:56', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('437', '13240150408', '39.997667', '116.434707', '2016-10-14 14:38:57', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('438', '13240150408', '39.997672', '116.434717', '2016-10-14 14:45:45', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('439', '13240150408', '39.997668', '116.434704', '2016-10-14 15:01:41', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('440', '13240150408', '39.997672', '116.434717', '2016-10-14 15:07:06', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('441', '13240150408', '39.997667', '116.434707', '2016-10-14 15:13:02', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('442', '13240150408', '39.997673', '116.434716', '2016-10-14 15:15:45', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('443', '13240150408', '39.997672', '116.434717', '2016-10-14 15:25:44', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('444', '13240150408', '39.997673', '116.434716', '2016-10-14 15:27:45', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('445', '13240150408', '39.997657', '116.434665', '2016-10-14 15:30:20', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('446', '13240150408', '39.997575', '116.434243', '2016-10-14 15:32:18', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('447', '13240150408', '39.997672', '116.434679', '2016-10-14 15:33:35', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('448', '13240150408', '39.997672', '116.434679', '2016-10-14 15:36:36', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('449', '13240150408', '39.997575', '116.434243', '2016-10-14 15:38:16', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('450', '13269128687', '39.997596', '116.434195', '2016-10-14 15:38:35', '61.148.244.174');
INSERT INTO `t_sys_log_login` VALUES ('451', '13240150408', '39.997660', '116.434658', '2016-10-14 15:42:55', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('452', '13240150408', '39.997575', '116.434243', '2016-10-14 15:45:04', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('453', '13240150408', '39.997672', '116.434679', '2016-10-14 15:49:34', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('454', '13269158110', '39.996977', '116.433786', '2016-10-14 15:49:50', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('455', '13269158110', '39.997200', '116.433782', '2016-10-14 15:52:46', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('456', '13269158110', '39.997182', '116.433965', '2016-10-14 15:53:01', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('457', '13240150408', '39.997672', '116.434679', '2016-10-14 15:54:35', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('458', '13240150408', '39.997672', '116.434679', '2016-10-14 15:55:50', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('459', '13240150408', '39.997662', '116.434661', '2016-10-14 16:40:23', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('460', '13240150408', '39.997752', '116.434789', '2016-10-14 17:12:00', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('461', '13240150408', '39.997573', '116.434525', '2016-10-14 17:18:37', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('462', '13269128687', '39.996870', '116.433860', '2016-10-14 18:37:48', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('463', '13240150408', '39.991634', '116.430612', '2016-10-14 18:50:46', '114.242.250.239');
INSERT INTO `t_sys_log_login` VALUES ('464', '13240150408', '39.996955', '116.438733', '2016-10-14 18:56:22', '114.242.250.239');
INSERT INTO `t_sys_log_login` VALUES ('465', '13269128687', '39.997575', '116.434154', '2016-10-14 19:02:36', '114.242.250.239');
INSERT INTO `t_sys_log_login` VALUES ('466', '13240150408', '39.997575', '116.434154', '2016-10-14 19:08:02', '114.242.250.239');
INSERT INTO `t_sys_log_login` VALUES ('467', '13269128687', '39.996808', '116.433885', '2016-10-14 19:09:56', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('468', '13240150408', '39.997575', '116.434154', '2016-10-14 19:10:27', '114.242.250.239');
INSERT INTO `t_sys_log_login` VALUES ('469', '13269128687', '39.996808', '116.433885', '2016-10-14 19:12:02', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('470', '13240150408', '39.991661', '116.430882', '2016-10-14 19:23:31', '114.242.250.239');
INSERT INTO `t_sys_log_login` VALUES ('471', '13269128687', '39.997476', '116.437215', '2016-10-14 19:40:42', '114.242.250.239');
INSERT INTO `t_sys_log_login` VALUES ('472', '13240150408', '39.996955', '116.433844', '2016-10-14 19:47:11', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('473', '18888888888', '39.996886', '116.433745', '2016-10-14 19:55:48', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('474', '13240150408', '39.996886', '116.433745', '2016-10-14 19:57:37', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('475', '13240150408', '39.996886', '116.433745', '2016-10-14 19:58:54', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('476', '18888888888', '39.996886', '116.433745', '2016-10-14 20:00:25', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('477', '13240150408', '39.996886', '116.433745', '2016-10-14 20:02:33', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('478', '13240150408', '39.996947', '116.433916', '2016-10-14 20:06:54', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('479', '13240150408', '39.996827', '116.433892', '2016-10-14 21:23:14', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('480', '13269128687', '39.997052', '116.433721', '2016-10-14 21:27:33', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('481', '13269128687', '39.997052', '116.433721', '2016-10-14 21:31:06', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('482', '13269128687', '39.997052', '116.433721', '2016-10-14 21:32:21', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('483', '13269128687', '39.997052', '116.433721', '2016-10-14 21:33:01', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('484', '13240150408', '39.997919', '116.434746', '2016-10-14 22:43:17', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('485', '13240150408', '39.997852', '116.434699', '2016-10-14 22:47:59', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('486', '13240150408', '39.997672', '116.434679', '2016-10-14 22:59:18', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('487', '13240150408', '39.997672', '116.434679', '2016-10-14 22:59:48', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('488', '13240150408', '39.997672', '116.434679', '2016-10-14 23:02:36', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('489', '13240150408', '39.997575', '116.434243', '2016-10-14 23:22:33', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('490', '13240150408', '39.997672', '116.434679', '2016-10-14 23:24:40', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('491', '13240150408', '39.997805', '116.434649', '2016-10-14 23:30:02', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('492', '13240150408', '39.997575', '116.434243', '2016-10-14 23:30:23', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('493', '13240150408', '39.997575', '116.434243', '2016-10-14 23:37:43', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('494', '13240150408', '39.997672', '116.434679', '2016-10-14 23:40:31', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('495', '13240150408', '39.997575', '116.434243', '2016-10-14 23:44:26', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('496', '13240150408', '39.997575', '116.434243', '2016-10-14 23:54:02', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('497', '13240150408', '39.997575', '116.434243', '2016-10-14 23:55:59', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('498', '13240150408', '39.997672', '116.434679', '2016-10-14 23:57:15', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('499', '13240150408', '39.997575', '116.434243', '2016-10-15 00:01:33', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('500', '13240150408', '39.997825', '116.434711', '2016-10-15 00:04:24', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('501', '13240150408', '39.997575', '116.434243', '2016-10-15 00:11:56', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('502', '13240150408', '39.997575', '116.434243', '2016-10-15 00:12:17', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('503', '13240150408', '39.997660', '116.434657', '2016-10-15 00:21:25', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('504', '13269128687', '39.997660', '116.434658', '2016-10-15 00:21:46', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('505', '13269128687', '39.997660', '116.434658', '2016-10-15 00:21:54', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('506', '13269128687', '39.997052', '116.433721', '2016-10-15 09:18:08', '114.242.248.21');
INSERT INTO `t_sys_log_login` VALUES ('507', '13269128687', '39.997597', '116.434194', '2016-10-15 09:26:25', '114.242.248.21');
INSERT INTO `t_sys_log_login` VALUES ('508', '13269128687', '39.997596', '116.434195', '2016-10-15 09:26:53', '114.242.248.21');
INSERT INTO `t_sys_log_login` VALUES ('509', '13240150408', '39.997596', '116.434195', '2016-10-15 09:27:56', '114.242.248.21');
INSERT INTO `t_sys_log_login` VALUES ('510', '13269128687', '39.997596', '116.434195', '2016-10-15 09:30:20', '114.242.248.21');
INSERT INTO `t_sys_log_login` VALUES ('511', '13269128687', '39.997596', '116.434195', '2016-10-15 09:43:16', '114.242.248.21');
INSERT INTO `t_sys_log_login` VALUES ('512', '13269128687', '39.997596', '116.434195', '2016-10-15 09:43:51', '114.242.248.21');
INSERT INTO `t_sys_log_login` VALUES ('513', '13269128687', '39.997596', '116.434195', '2016-10-15 09:50:30', '114.242.248.21');
INSERT INTO `t_sys_log_login` VALUES ('514', '13269128687', '39.997718', '116.434753', '2016-10-15 10:08:04', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('515', '13240150408', '39.997642', '116.434622', '2016-10-15 10:24:45', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('516', '13240150408', '39.997577', '116.434247', '2016-10-15 10:39:42', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('517', '13240150408', '39.997577', '116.434247', '2016-10-15 10:42:51', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('518', '13240150408', '39.997577', '116.434247', '2016-10-15 10:44:36', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('519', '13240150408', '39.997672', '116.434679', '2016-10-15 10:45:42', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('520', '13240150408', '39.997577', '116.434247', '2016-10-15 10:54:10', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('521', '13269128687', '39.997766', '116.434747', '2016-10-15 10:54:17', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('522', '13240150408', '39.997825', '116.434711', '2016-10-15 10:58:01', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('523', '13240150408', '39.997825', '116.434711', '2016-10-15 11:00:02', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('524', '13240150408', '39.997642', '116.434622', '2016-10-15 11:03:19', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('525', '13240150408', '39.997672', '116.434679', '2016-10-15 11:04:23', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('526', '13240150408', '39.997672', '116.434679', '2016-10-15 11:07:29', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('527', '13240150408', '39.997577', '116.434247', '2016-10-15 11:08:26', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('528', '13240150408', '39.997577', '116.434247', '2016-10-15 11:09:44', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('529', '13240150408', '39.997577', '116.434247', '2016-10-15 11:11:51', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('530', '13240150408', '39.997577', '116.434247', '2016-10-15 11:13:24', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('531', '13240150408', '39.997577', '116.434247', '2016-10-15 11:15:05', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('532', '13240150408', '39.997642', '116.434622', '2016-10-15 11:16:23', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('533', '13240150408', '39.997672', '116.434679', '2016-10-15 11:17:19', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('534', '13269128687', '39.997855', '116.434695', '2016-10-15 11:18:31', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('535', '13240150408', '39.997577', '116.434247', '2016-10-15 11:20:35', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('536', '13240150408', '39.997577', '116.434247', '2016-10-15 11:21:52', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('537', '13240150408', '39.997577', '116.434247', '2016-10-15 11:23:17', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('538', '13240150408', '39.997577', '116.434247', '2016-10-15 11:24:39', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('539', '13240150408', '39.997577', '116.434247', '2016-10-15 11:26:55', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('540', '13240150408', '39.997577', '116.434247', '2016-10-15 11:28:47', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('541', '13240150408', '39.997658', '116.434653', '2016-10-15 11:31:23', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('542', '13240150408', '39.997577', '116.434247', '2016-10-15 11:34:53', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('543', '13240150408', '39.997577', '116.434247', '2016-10-15 11:36:30', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('544', '13240150408', '39.997577', '116.434247', '2016-10-15 11:41:52', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('545', '13240150408', '39.997577', '116.434247', '2016-10-15 11:43:12', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('546', '13240150408', '39.997923', '116.434733', '2016-10-15 11:52:34', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('547', '13240150408', '39.997577', '116.434247', '2016-10-15 11:53:17', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('548', '13240150408', '39.997577', '116.434247', '2016-10-15 11:58:36', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('549', '13269128687', '39.997852', '116.434699', '2016-10-15 12:00:57', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('550', '13269128687', '39.997852', '116.434699', '2016-10-15 12:03:00', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('551', '13269128687', '39.997852', '116.434699', '2016-10-15 12:05:16', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('552', '13269128687', '39.997852', '116.434699', '2016-10-15 12:08:03', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('553', '13269128687', '39.997852', '116.434699', '2016-10-15 12:09:14', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('554', '13269128687', '39.997852', '116.434699', '2016-10-15 12:10:46', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('555', '13269128687', '39.997852', '116.434699', '2016-10-15 12:11:30', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('556', '13269128687', '39.997577', '116.434247', '2016-10-15 12:13:34', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('557', '13269128687', '39.997577', '116.434247', '2016-10-15 12:15:32', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('558', '13240150408', '39.997577', '116.434247', '2016-10-15 12:24:38', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('559', '13240150408', '39.997672', '116.434679', '2016-10-15 12:50:18', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('560', '13240150408', '39.997672', '116.434679', '2016-10-15 12:54:28', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('561', '13240150408', '39.997672', '116.434679', '2016-10-15 12:59:50', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('562', '13240150408', '39.997672', '116.434679', '2016-10-15 13:02:13', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('563', '13240150408', '39.997642', '116.434622', '2016-10-15 13:03:44', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('564', '13240150408', '39.997672', '116.434679', '2016-10-15 13:04:08', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('565', '13240150408', '39.997672', '116.434679', '2016-10-15 13:08:09', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('566', '13240150408', '39.997577', '116.434247', '2016-10-15 13:09:35', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('567', '13240150408', '39.997577', '116.434247', '2016-10-15 13:10:28', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('568', '13269128687', '39.997883', '116.434706', '2016-10-15 13:11:24', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('569', '13240150408', '39.997577', '116.434247', '2016-10-15 13:15:15', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('570', '13240150408', '39.997577', '116.434247', '2016-10-15 13:19:22', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('571', '13240150408', '39.997577', '116.434247', '2016-10-15 13:21:37', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('572', '13240150408', '39.997577', '116.434247', '2016-10-15 13:24:07', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('573', '13240150408', '39.997577', '116.434247', '2016-10-15 13:26:48', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('574', '13240150408', '39.997652', '116.434795', '2016-10-15 13:30:46', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('575', '13240150408', '39.997577', '116.434247', '2016-10-15 13:37:53', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('576', '13240150408', '39.997672', '116.434679', '2016-10-15 13:39:30', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('577', '13240150408', '39.997577', '116.434247', '2016-10-15 13:40:42', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('578', '13269128687', '39.997672', '116.434679', '2016-10-15 13:46:42', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('579', '13269128687', '39.997649', '116.434803', '2016-10-15 13:49:54', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('580', '13269128687', '39.997649', '116.434805', '2016-10-15 13:51:23', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('581', '13269128687', '39.997649', '116.434804', '2016-10-15 13:52:47', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('582', '13269128687', '39.997649', '116.434804', '2016-10-15 13:53:46', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('583', '13269128687', '39.997649', '116.434805', '2016-10-15 13:55:26', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('584', '13269128687', '39.997672', '116.434679', '2016-10-15 13:56:33', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('585', '13269128687', '39.997672', '116.434679', '2016-10-15 13:58:17', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('586', '13269128687', '39.997672', '116.434679', '2016-10-15 14:02:38', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('587', '13269128687', '39.997577', '116.434247', '2016-10-15 14:16:09', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('588', '13269128687', '39.996137', '116.433635', '2016-10-15 15:01:55', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('589', '13269128687', '39.995983', '116.433668', '2016-10-15 15:09:24', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('590', '13269128687', '39.995861', '116.433767', '2016-10-15 15:12:24', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('591', '13269128687', '39.997672', '116.434679', '2016-10-15 15:15:07', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('592', '13269128687', '39.999857', '116.436850', '2016-10-15 15:16:54', '114.242.250.253');
INSERT INTO `t_sys_log_login` VALUES ('593', '13269128687', '39.999470', '116.428396', '2016-10-15 15:19:38', '114.242.250.253');
INSERT INTO `t_sys_log_login` VALUES ('594', '13269128687', '39.991642', '116.430796', '2016-10-15 15:21:00', '114.242.250.253');
INSERT INTO `t_sys_log_login` VALUES ('595', '13269128687', '39.999470', '116.428396', '2016-10-15 15:21:54', '114.242.250.253');
INSERT INTO `t_sys_log_login` VALUES ('596', '13269128687', '39.997599', '116.434197', '2016-10-15 15:22:18', '114.242.250.253');
INSERT INTO `t_sys_log_login` VALUES ('597', '13269128687', '39.999857', '116.436850', '2016-10-15 15:23:32', '114.242.250.253');
INSERT INTO `t_sys_log_login` VALUES ('598', '13269128687', '39.991642', '116.430796', '2016-10-15 15:31:37', '114.242.250.253');
INSERT INTO `t_sys_log_login` VALUES ('599', '13269128687', '39.997672', '116.434679', '2016-10-15 15:46:46', '114.242.250.253');
INSERT INTO `t_sys_log_login` VALUES ('600', '13240150408', '39.996177', '116.433818', '2016-10-15 16:31:58', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('601', '13240150408', '39.996027', '116.433811', '2016-10-15 17:30:38', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('602', '13240150408', '39.995934', '116.433743', '2016-10-15 17:35:29', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('603', '13240150408', '39.995934', '116.433743', '2016-10-15 17:35:42', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('604', '13240150408', '39.995974', '116.433706', '2016-10-15 17:38:37', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('605', '13240150408', '39.996118', '116.433750', '2016-10-15 17:39:32', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('606', '13240150408', '39.996069', '116.433711', '2016-10-15 18:41:36', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('607', '13240150408', '39.995822', '116.433773', '2016-10-15 18:44:19', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('608', '13240150408', '39.995948', '116.433824', '2016-10-15 18:45:34', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('609', '13269128687', '39.996048', '116.433736', '2016-10-15 18:48:25', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('610', '13240150408', '39.996058', '116.433784', '2016-10-15 18:49:26', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('611', '13240150408', '39.995746', '116.433799', '2016-10-15 18:56:53', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('612', '13240150408', '39.995786', '116.433725', '2016-10-15 19:01:04', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('613', '13269128687', '39.995991', '116.433859', '2016-10-15 19:11:08', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('614', '13269128687', '39.996005', '116.433797', '2016-10-15 19:12:02', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('615', '13269128687', '39.995934', '116.433767', '2016-10-15 19:17:07', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('616', '13269128687', '39.995977', '116.433689', '2016-10-15 19:18:10', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('617', '13240150408', '39.995932', '116.433693', '2016-10-15 19:18:42', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('618', '13269128687', '39.996089', '116.433853', '2016-10-15 19:25:06', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('619', '13269128687', '39.995953', '116.433737', '2016-10-15 19:27:57', '210.82.53.188');
INSERT INTO `t_sys_log_login` VALUES ('620', '13269128687', '39.999857', '116.436850', '2016-10-15 19:29:39', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('621', '13269128687', '39.999859', '116.436823', '2016-10-15 19:30:36', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('622', '13269128687', '39.999859', '116.436823', '2016-10-15 19:32:34', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('623', '13240150408', '39.999860', '116.436825', '2016-10-15 19:33:32', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('624', '13240150408', '39.999860', '116.436825', '2016-10-15 19:36:33', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('625', '13269128687', '39.999857', '116.436850', '2016-10-15 19:39:46', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('626', '13269128687', '39.999857', '116.436850', '2016-10-15 19:41:28', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('627', '13269128687', '39.999857', '116.436850', '2016-10-15 19:43:16', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('628', '13269128687', '39.999853', '116.436851', '2016-10-15 19:46:10', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('629', '13269128687', '39.997575', '116.434159', '2016-10-15 19:48:48', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('630', '13269128687', '39.999859', '116.436823', '2016-10-15 19:49:25', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('631', '13269128687', '39.997575', '116.434154', '2016-10-15 19:49:43', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('632', '13269128687', '39.999860', '116.436825', '2016-10-15 19:51:41', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('633', '13269128687', '39.999859', '116.436823', '2016-10-15 19:52:17', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('634', '13269128687', '39.999860', '116.436825', '2016-10-15 19:52:37', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('635', '13269128687', '39.997575', '116.434159', '2016-10-15 19:52:50', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('636', '13269128687', '39.999859', '116.436823', '2016-10-15 19:53:53', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('637', '13269128687', '39.999859', '116.436823', '2016-10-15 19:54:38', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('638', '13269128687', '39.991642', '116.430796', '2016-10-15 19:56:53', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('639', '13269128687', '39.999857', '116.436850', '2016-10-15 19:57:38', '61.148.242.152');
INSERT INTO `t_sys_log_login` VALUES ('640', '13269128687', '39.997596', '116.434195', '2016-10-15 20:03:11', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('641', '13240150408', '39.999857', '116.436850', '2016-10-15 20:03:57', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('642', '13269128687', '39.996047', '116.433788', '2016-10-15 20:04:53', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('643', '13240150408', '39.999853', '116.436851', '2016-10-15 20:05:08', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('644', '13269128687', '39.996087', '116.433883', '2016-10-15 20:06:35', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('645', '13240150408', '40.000151', '116.436861', '2016-10-15 20:07:26', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('646', '13269128687', '39.996139', '116.433811', '2016-10-15 20:12:34', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('647', '13269128687', '39.999857', '116.436850', '2016-10-15 20:14:42', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('648', '13240150408', '39.995750', '116.433613', '2016-10-15 20:15:13', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('649', '13269128687', '39.999857', '116.436850', '2016-10-15 20:18:46', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('650', '13240150408', '39.999859', '116.436823', '2016-10-15 20:20:36', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('651', '13240150408', '39.991642', '116.430796', '2016-10-15 20:21:48', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('652', '13240150408', '39.999859', '116.436823', '2016-10-15 20:27:16', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('653', '13240150408', '39.999860', '116.436825', '2016-10-15 20:30:41', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('654', '13240150408', '39.996160', '116.433902', '2016-10-15 20:39:24', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('655', '13269128687', '39.996160', '116.433902', '2016-10-15 20:40:28', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('656', '13240150408', '39.999859', '116.436823', '2016-10-15 20:40:42', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('657', '13269128687', '39.996081', '116.433796', '2016-10-15 20:48:58', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('658', '13240150408', '39.999859', '116.436823', '2016-10-15 20:49:49', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('659', '13240150408', '39.999860', '116.436825', '2016-10-15 20:50:05', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('660', '13240150408', '39.999859', '116.436823', '2016-10-15 20:53:09', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('661', '13240150408', '39.999859', '116.436823', '2016-10-15 20:54:55', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('662', '13240150408', '39.999859', '116.436823', '2016-10-15 20:57:34', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('663', '13240150408', '39.999857', '116.436850', '2016-10-15 21:54:53', '114.242.249.147');
INSERT INTO `t_sys_log_login` VALUES ('664', '13240150408', '39.997785', '116.434788', '2016-10-15 22:30:05', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('665', '13269128687', '39.995660', '116.433889', '2016-10-16 10:47:13', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('666', '13269128687', '39.995546', '116.433684', '2016-10-16 10:57:59', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('667', '13269128687', '39.999469', '116.428395', '2016-10-16 21:06:25', '61.148.244.51');
INSERT INTO `t_sys_log_login` VALUES ('668', '13269128687', '39.997865', '116.434673', '2016-10-17 18:31:26', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('669', '13269128687', '39.996381', '116.432544', '2016-10-18 10:56:51', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('670', '13269128687', '39.996236', '116.432657', '2016-10-18 10:57:17', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('671', '13269128687', '39.996283', '116.432563', '2016-10-18 10:59:06', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('672', '13269128687', '39.996283', '116.432563', '2016-10-18 10:59:44', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('673', '13269128687', '39.996282', '116.432659', '2016-10-18 11:03:53', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('674', '13269128687', '39.996337', '116.432700', '2016-10-18 11:07:20', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('675', '13269128687', '39.996337', '116.432700', '2016-10-18 11:07:48', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('676', '13269128687', '39.997814', '116.434820', '2016-10-18 20:26:01', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('677', '13269128687', '39.997595', '116.434985', '2016-10-18 20:35:36', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('678', '13240150408', '39.995901', '116.433695', '2016-10-19 21:50:43', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('679', '13240150408', '39.996019', '116.433639', '2016-10-19 21:54:04', '210.82.53.193');
INSERT INTO `t_sys_log_login` VALUES ('680', '13269128687', '39.999052', '116.428726', '2016-10-20 13:04:45', '114.242.248.22');
INSERT INTO `t_sys_log_login` VALUES ('681', '13269128687', '39.996207', '116.433729', '2016-10-20 22:48:59', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('682', '18888888888', '39.995700', '116.433798', '2016-10-20 22:58:30', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('683', '18888888888', '39.995700', '116.433798', '2016-10-20 22:58:58', '210.82.53.195');
INSERT INTO `t_sys_log_login` VALUES ('684', '18888888888', '39.997579', '116.434250', '2016-10-24 16:45:22', '210.82.53.195');

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `USER_NAME` varchar(50) NOT NULL,
  `USER_ID` varchar(50) DEFAULT NULL,
  `USER_TELNUM` varchar(50) DEFAULT NULL,
  `REG_DATE` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `REG_LATITUDE` double(8,6) DEFAULT '0.000000',
  `REG_LONGITUDE` double(9,6) DEFAULT '0.000000',
  `USER_PWD` char(32) DEFAULT NULL,
  `USER_HEADIMAGE` varchar(200) DEFAULT 'http://123.57.52.135:8080/ChildHood/avatar/default.png',
  `USER_NICKNAME` varchar(100) DEFAULT NULL,
  `LAST_LATITUDE` double(8,6) DEFAULT NULL,
  `LAST_LONGITUDE` double(9,6) DEFAULT NULL,
  `LAST_LOGINDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `BELONGING_AREA` int(11) DEFAULT '0',
  `BELONGING_PROVINCE` int(11) DEFAULT '0',
  `BELONGING_CITY` int(11) DEFAULT '0',
  `BELONGING_DISTRICT` int(11) DEFAULT '0',
  `COMMUNITY` varchar(50) DEFAULT NULL,
  `DETAIL_ADDR` varchar(50) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `ACHIEVEMENT_POINT` int(11) DEFAULT '0',
  `USER_LEVEL` int(11) DEFAULT '0',
  `LOGIN_STATUS` enum('online','offline') DEFAULT 'offline',
  PRIMARY KEY (`USER_NAME`),
  KEY `FK_BELONGING_PROVINCE_USER` (`BELONGING_PROVINCE`) USING BTREE,
  KEY `FK_BELONGING_CITY_USER` (`BELONGING_CITY`) USING BTREE,
  KEY `FK_BELONGING_DISTRICT_USER` (`BELONGING_DISTRICT`) USING BTREE,
  KEY `FK_BELONGING_AREA_USER` (`BELONGING_AREA`) USING BTREE,
  KEY `FK_LEVEL_CODE` (`USER_LEVEL`) USING BTREE,
  CONSTRAINT `FK_BELONGING_AREA_USER` FOREIGN KEY (`BELONGING_AREA`) REFERENCES `t_pub_area` (`AREA_CODE`),
  CONSTRAINT `FK_BELONGING_CITY_USER` FOREIGN KEY (`BELONGING_CITY`) REFERENCES `t_pub_city` (`CITY_CODE`),
  CONSTRAINT `FK_BELONGING_DISTRICT_USER` FOREIGN KEY (`BELONGING_DISTRICT`) REFERENCES `t_pub_district` (`DISTRICT_CODE`),
  CONSTRAINT `FK_BELONGING_PROVINCE_USER` FOREIGN KEY (`BELONGING_PROVINCE`) REFERENCES `t_pub_province` (`PROVINCE_CODE`),
  CONSTRAINT `FK_LEVEL_CODE` FOREIGN KEY (`USER_LEVEL`) REFERENCES `t_pub_level` (`LEVEL_CODE`),
  CONSTRAINT `FK_USER_LEVEL` FOREIGN KEY (`USER_LEVEL`) REFERENCES `t_pub_level` (`LEVEL_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('13240150408', 'vbXBc6fd', '13240150408', '2016-10-08 16:22:20', '87.123456', '123.123456', '1c99810f4efd101a2827cd0ba2ed3640', 'http://123.57.52.135:8080/ChildHood/avatar/13240150408_1476505031543.png', '欧阳涛', '39.996019', '116.433639', '2016-10-19 21:54:04', '4', '1', '1', '4', null, null, null, '225', '4', 'online');
INSERT INTO `t_sys_user` VALUES ('13269128687', 'gexS7SIh', '13269128687', '2016-09-16 19:30:01', '87.123456', '123.123456', '10ff4f961ef3f31a0a72dbabe4dc5784', 'http://123.57.52.135:8080/ChildHood/avatar/13269128687_1476529231751.png', '季文健', '39.996207', '116.433729', '2016-10-20 22:48:59', '4', '1', '1', '4', null, null, null, '111', '3', 'online');
INSERT INTO `t_sys_user` VALUES ('13269158110', 'oVVfFEFB', '13269158110', '2016-10-12 15:11:56', '39.997598', '116.434197', 'c2517285a5b5f583aac4b23e6ff87529', 'http://123.57.52.135:8080/ChildHood/avatar/default.png', '石嘉铭', '39.997182', '116.433965', '2016-10-18 11:10:03', '4', '1', '1', '4', null, null, null, '17', '0', 'online');
INSERT INTO `t_sys_user` VALUES ('13611108060', 'qLGejC89', '13611108060', '2016-10-12 22:19:07', '39.996894', '116.438421', '5a21d088af551102a51471dbd0a5f8c4', 'http://123.57.52.135:8080/ChildHood/avatar/default.png', '徐楚嫣', '39.997575', '116.434249', '2016-10-18 11:10:04', '4', '1', '1', '4', null, null, null, '12', '0', 'online');
INSERT INTO `t_sys_user` VALUES ('18888888888', 'RyBmuuLr', '18888888888', '2016-10-14 19:54:31', '39.996886', '116.433745', '47ec2dd791e31e2ef2076caf64ed9b3d', 'http://123.57.52.135:8080/ChildHood/avatar/default.png', '测试账号', '39.997579', '116.434250', '2016-10-24 16:45:22', '4', '1', '1', '4', null, null, null, '11', '0', 'online');
INSERT INTO `t_sys_user` VALUES ('admin', null, null, '2016-09-26 20:38:37', '0.000000', '0.000000', '084cab7810988b7b3f8cc46f62306287', 'system', null, '0.000000', '0.000000', '2016-09-27 13:12:22', '0', '0', '0', '0', null, null, null, '0', '0', 'offline');
INSERT INTO `t_sys_user` VALUES ('pushmaster', null, null, '2016-10-08 16:24:09', '0.000000', '0.000000', 'e10adc3949ba59abbe56e057f20f883e', 'system', null, null, null, '2016-10-08 16:24:41', '0', '0', '0', '0', null, null, null, '0', '0', 'offline');

-- ----------------------------
-- Table structure for t_sys_vcode
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_vcode`;
CREATE TABLE `t_sys_vcode` (
  `VCODE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TELNUM` varchar(50) DEFAULT NULL,
  `GENERATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `VCODE` varchar(10) DEFAULT NULL,
  `SMS_STATUS` varchar(50) DEFAULT NULL,
  `VCODE_STATUS` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`VCODE_ID`),
  KEY `FK_USER_NAME_VCODE` (`TELNUM`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_vcode
-- ----------------------------
INSERT INTO `t_sys_vcode` VALUES ('1', '13269128687', '2016-09-26 20:10:23', '160882', '11', 'VC03');
INSERT INTO `t_sys_vcode` VALUES ('2', '13269128687', '2016-09-26 20:11:57', '610762', '11', 'VC03');
INSERT INTO `t_sys_vcode` VALUES ('3', '13269128687', '2016-10-08 16:16:30', '157353', '0', 'VC03');
INSERT INTO `t_sys_vcode` VALUES ('4', '13269136008', '2016-09-27 10:13:53', '921349', '0', 'VC02');
INSERT INTO `t_sys_vcode` VALUES ('5', '13269128687', '2016-10-08 16:20:10', '416705', '0', 'VC02');
INSERT INTO `t_sys_vcode` VALUES ('6', '13269128688', '2016-10-11 09:14:20', '123456', '0', 'VC02');
INSERT INTO `t_sys_vcode` VALUES ('7', null, '2016-10-12 15:07:40', '447343', '40', 'VC04');
INSERT INTO `t_sys_vcode` VALUES ('8', null, '2016-10-12 15:08:14', '386931', '40', 'VC04');
INSERT INTO `t_sys_vcode` VALUES ('9', '13269158110', '2016-10-12 15:11:56', '974915', '0', 'VC02');
INSERT INTO `t_sys_vcode` VALUES ('10', null, '2016-10-12 17:49:33', '213119', '40', 'VC04');
INSERT INTO `t_sys_vcode` VALUES ('11', '13240150408', '2016-10-12 18:17:42', '449164', '0', 'VC03');
INSERT INTO `t_sys_vcode` VALUES ('12', '13240150408', '2016-10-15 16:36:32', '596189', '0', 'VC03');
INSERT INTO `t_sys_vcode` VALUES ('13', '13611108060', '2016-10-12 22:19:07', '918902', '0', 'VC02');
INSERT INTO `t_sys_vcode` VALUES ('14', '18888888888', '2016-10-14 21:21:52', '123456', '0', 'VC02');
INSERT INTO `t_sys_vcode` VALUES ('16', '13240150408', '2016-10-15 17:01:33', '432990', '0', 'VC02');

-- ----------------------------
-- View structure for v_achvmt_user_event
-- ----------------------------
DROP VIEW IF EXISTS `v_achvmt_user_event`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_achvmt_user_event` AS select `t1`.`USER_NAME` AS `USER_NAME`,date_format(`t1`.`EVENT_DATE`,'%Y-%m-%d') AS `EVENT_DATE`,`t2`.`TEMPLATE_STR` AS `TEMPLATE_STR`,`t1`.`EVENT_PARAMS` AS `EVENT_PARAMS`,((to_days(date_format(`t1`.`EVENT_DATE`,'%Y-%m-%d')) - to_days(date_format(`t3`.`REG_DATE`,'%Y-%m-%d'))) + 1) AS `DAY` from ((`t_achvmt_user_event_list` `t1` join `t_achvmt_event_template` `t2`) join `t_sys_user` `t3`) where ((`t1`.`EVENT_TEMPLATE` = `t2`.`TEMPLATE_ID`) and (`t1`.`USER_NAME` = `t3`.`USER_NAME`)) order by date_format(`t1`.`EVENT_DATE`,'%Y-%m-%d') desc ;

-- ----------------------------
-- View structure for v_achvmt_user_medal
-- ----------------------------
DROP VIEW IF EXISTS `v_achvmt_user_medal`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_achvmt_user_medal` AS select `t1`.`USER_NAME` AS `USER_NAME`,`t1`.`MEDAL_ID` AS `MEDAL_ID`,`t2`.`MEDAL_DESCRIBE` AS `MEDAL_DESCRIBE`,`t1`.`GET_DATE` AS `GET_DATE`,`t2`.`MEDAL_NAME` AS `MEDAL_NAME` from (`t_achvmt_user_medal_list` `t1` join `t_achvmt_medal` `t2`) where ((`t1`.`MEDAL_ID` = `t2`.`MEDAL_ID`) and (`t1`.`ENABLE` = 'Y')) ;

-- ----------------------------
-- View structure for v_ct_cur_nxt_level_point
-- ----------------------------
DROP VIEW IF EXISTS `v_ct_cur_nxt_level_point`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_ct_cur_nxt_level_point` AS select `t1`.`USER_NAME` AS `USER_NAME`,`t2`.`CURRENT_LEVEL_POINT` AS `CURRENT_LEVEL_POINT`,`t1`.`NEXT_LEVEL_POINT` AS `NEXT_LEVEL_POINT` from ((select `tb`.`USER_NAME` AS `USER_NAME`,`ta`.`LEVEL_POINT` AS `NEXT_LEVEL_POINT` from (`childhood`.`t_pub_level` `ta` join `childhood`.`t_sys_user` `tb`) where ((`ta`.`LEVEL_POINT` > `tb`.`ACHIEVEMENT_POINT`) and (`tb`.`USER_HEADIMAGE` <> 'system')) group by `tb`.`USER_NAME`) `t1` join (select `tb`.`USER_NAME` AS `USER_NAME`,`ta`.`LEVEL_POINT` AS `CURRENT_LEVEL_POINT` from (`childhood`.`t_pub_level` `ta` join `childhood`.`t_sys_user` `tb`) where ((`ta`.`LEVEL_CODE` = `tb`.`USER_LEVEL`) and (`tb`.`USER_HEADIMAGE` <> 'system'))) `t2`) where (`t1`.`USER_NAME` = `t2`.`USER_NAME`) ;

-- ----------------------------
-- View structure for v_ct_total_found_game_count
-- ----------------------------
DROP VIEW IF EXISTS `v_ct_total_found_game_count`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_ct_total_found_game_count` AS select `t_status_game`.`GAME_FOUNDER` AS `USER_NAME`,count(0) AS `TOTAL_FOUND_GAME_COUNT` from `t_status_game` where (`t_status_game`.`GAME_STATUS` = 'S004') group by `USER_NAME` ;

-- ----------------------------
-- View structure for v_ct_total_game_count
-- ----------------------------
DROP VIEW IF EXISTS `v_ct_total_game_count`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_ct_total_game_count` AS select `t_status_joined`.`USER_NAME` AS `USER_NAME`,count(0) AS `TOTAL_GAME_COUNT` from `t_status_joined` where (`t_status_joined`.`JOIN_STATUS` = 'S011') group by `t_status_joined`.`USER_NAME` ;

-- ----------------------------
-- View structure for v_ct_total_kind_game_count
-- ----------------------------
DROP VIEW IF EXISTS `v_ct_total_kind_game_count`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_ct_total_kind_game_count` AS select `t_status_joined`.`USER_NAME` AS `USER_NAME`,count(distinct `t_status_game`.`GAME_CODE`) AS `TOTAL_KIND_GAME_COUNT` from (`t_status_game` join `t_status_joined`) where ((`t_status_game`.`GAME_ID` = `t_status_joined`.`GAME_ID`) and (`t_status_joined`.`JOIN_STATUS` = 'S011')) group by `t_status_joined`.`USER_NAME` ;

-- ----------------------------
-- View structure for v_achvmt_user_kpi
-- ----------------------------
DROP VIEW IF EXISTS `v_achvmt_user_kpi`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_achvmt_user_kpi` AS select `t1`.`USER_NAME` AS `USER_NAME`,`t1`.`USER_HEADIMAGE` AS `USER_HEADIMAGE`,`t1`.`USER_NICKNAME` AS `USER_NICKNAME`,`t1`.`BELONGING_PROVINCE` AS `BELONGING_PROVINCE`,`t1`.`BELONGING_CITY` AS `BELONGING_CITY`,`t1`.`ACHIEVEMENT_POINT` AS `ACHIEVEMENT_POINT`,`t1`.`USER_LEVEL` AS `USER_LEVEL`,`t7`.`LEVEL_NAME` AS `LEVEL_NAME`,`t2`.`CURRENT_LEVEL_POINT` AS `CURRENT_LEVEL_POINT`,ifnull(`t2`.`NEXT_LEVEL_POINT`,5000) AS `NEXT_LEVEL_POINT`,ifnull(`t3`.`TOTAL_GAME_COUNT`,0) AS `TOTAL_GAME_COUNT`,ifnull(`t4`.`LIKE_COUNT`,0) AS `TOTAL_LIKE_COUNT`,ifnull(`t5`.`TOTAL_FOUND_GAME_COUNT`,0) AS `TOTAL_FOUND_GAME_COUNT`,ifnull(`t6`.`TOTAL_KIND_GAME_COUNT`,0) AS `TOTAL_KIND_GAME_COUNT` from ((((((`childhood`.`t_sys_user` `t1` left join `childhood`.`v_ct_cur_nxt_level_point` `t2` on((`t1`.`USER_NAME` = `t2`.`USER_NAME`))) left join `childhood`.`v_ct_total_game_count` `t3` on((`t1`.`USER_NAME` = `t3`.`USER_NAME`))) left join `childhood`.`t_status_user_rank` `t4` on((`t1`.`USER_NAME` = `t4`.`USER_NAME`))) left join `childhood`.`v_ct_total_found_game_count` `t5` on((`t1`.`USER_NAME` = `t5`.`USER_NAME`))) left join `childhood`.`v_ct_total_kind_game_count` `t6` on((`t1`.`USER_NAME` = `t6`.`USER_NAME`))) left join `childhood`.`t_pub_level` `t7` on((`t1`.`USER_LEVEL` = `t7`.`LEVEL_CODE`))) where (`t1`.`USER_HEADIMAGE` <> 'system') ;

-- ----------------------------
-- View structure for v_find_result
-- ----------------------------
DROP VIEW IF EXISTS `v_find_result`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_find_result` AS select `t1`.`USER_NAME` AS `USER_NAME`,`t1`.`MATCH_USER` AS `MATCH_USER`,`t2`.`AGE` AS `AGE`,`t2`.`SEX` AS `SEX`,`t2`.`YOUNG_NICKNAME` AS `YOUNG_NICKNAME`,`t2`.`YOUNG_ROAD` AS `YOUNG_ROAD`,`t2`.`MESSAGE` AS `MESSAGE`,`t3`.`USER_HEADIMAGE` AS `USER_HEADIMAGE`,`t4`.`CITY_NAME` AS `CURRENT_CITY` from (((`t_find_result` `t1` join `t_find_reg` `t2`) join `t_sys_user` `t3`) join `t_pub_city` `t4`) where ((`t1`.`MATCH_USER` = `t2`.`USER_NAME`) and (`t1`.`MATCH_USER` = `t3`.`USER_NAME`) and (`t3`.`BELONGING_CITY` = `t4`.`CITY_CODE`) and (`t1`.`ENABLE` = 'Y')) ;

-- ----------------------------
-- View structure for v_rank_game
-- ----------------------------
DROP VIEW IF EXISTS `v_rank_game`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_rank_game` AS select `t1`.`GAME_CODE` AS `GAME_CODE`,`t3`.`BELONGING_AREA` AS `BELONGING_AREA`,avg(cast(`t2`.`GAME_SCORE` as signed)) AS `GAME_SCORE`,count(0) AS `GAME_HEAT` from ((`t_status_game` `t1` join `t_gamerule_head` `t2`) join `t_sys_user` `t3`) where ((`t1`.`GAME_CODE` = `t2`.`GAME_CODE`) and (`t1`.`GAME_FOUNDER` = `t3`.`USER_NAME`)) group by `t1`.`GAME_CODE`,`t3`.`BELONGING_AREA` ;

-- ----------------------------
-- View structure for v_status_game_canjoin
-- ----------------------------
DROP VIEW IF EXISTS `v_status_game_canjoin`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_status_game_canjoin` AS select `t1`.`GAME_FOUNDER` AS `GAME_FOUNDER`,`t1`.`GAME_ID` AS `GAME_ID`,`t1`.`GAME_CODE` AS `GAME_CODE`,`t3`.`GAME_TITLE` AS `GAME_TITLE`,`t3`.`GAME_ICON` AS `GAME_ICON`,`t4`.`USER_NICKNAME` AS `USER_NICKNAME`,`t1`.`START_TIME` AS `START_TIME`,`t1`.`GAME_LATITUDE` AS `GAME_LATITUDE`,`t1`.`GAME_LONGITUDE` AS `GAME_LONGITUDE`,`t1`.`GATHER_PLACE` AS `GATHER_PLACE`,`t1`.`CUSTOM_INF` AS `CUSTOM_INF`,`t1`.`CUSTOM_COUNT` AS `CUSTOM_COUNT`,`t1`.`GAME_STATUS` AS `GAME_STATUS`,`t2`.`JOINED_COUNT` AS `JOINED_COUNT` from (((`childhood`.`t_status_game` `t1` join (select `childhood`.`t_status_joined`.`GAME_ID` AS `GAME_ID`,count(0) AS `JOINED_COUNT` from `childhood`.`t_status_joined` where (`childhood`.`t_status_joined`.`JOIN_STATUS` = 'S008') group by `childhood`.`t_status_joined`.`GAME_ID`) `t2`) join `childhood`.`t_gamerule_head` `t3`) join `childhood`.`t_sys_user` `t4`) where ((`t1`.`GAME_ID` = `t2`.`GAME_ID`) and (`t1`.`GAME_CODE` = `t3`.`GAME_CODE`) and (`t1`.`GAME_FOUNDER` = `t4`.`USER_NAME`) and (`t4`.`LOGIN_STATUS` = 'online')) ;

-- ----------------------------
-- View structure for v_status_user_canjoin
-- ----------------------------
DROP VIEW IF EXISTS `v_status_user_canjoin`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_status_user_canjoin` AS select `t_sys_user`.`USER_NAME` AS `USER_NAME`,`t_sys_user`.`LAST_LATITUDE` AS `LAST_LATITUDE`,`t_sys_user`.`LAST_LONGITUDE` AS `LAST_LONGITUDE` from `t_sys_user` where ((`t_sys_user`.`LOGIN_STATUS` = 'online') and (not(`t_sys_user`.`USER_NAME` in (select `t_status_joined`.`USER_NAME` from `t_status_joined` where (`t_status_joined`.`JOIN_STATUS` = 'S008')))) and (`t_sys_user`.`USER_HEADIMAGE` <> 'system')) union select `t_sys_user`.`USER_NAME` AS `USER_NAME`,`t_sys_user`.`LAST_LATITUDE` AS `LAST_LATITUDE`,`t_sys_user`.`LAST_LONGITUDE` AS `LAST_LONGITUDE` from `t_sys_user` where ((not(`t_sys_user`.`USER_NAME` in (select distinct `t_status_joined`.`USER_NAME` from `t_status_joined`))) and (`t_sys_user`.`USER_HEADIMAGE` <> 'system')) ;

-- ----------------------------
-- View structure for v_user_game_times_per_day
-- ----------------------------
DROP VIEW IF EXISTS `v_user_game_times_per_day`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_user_game_times_per_day` AS select `t_status_joined`.`USER_NAME` AS `USER_NAME`,date_format(`t_status_joined`.`JOIN_TIME`,'%Y-%m-%d') AS `JOIN_DATE`,count(0) AS `GAME_TIMES` from `t_status_joined` where (`t_status_joined`.`JOIN_STATUS` = 'S011') group by `t_status_joined`.`USER_NAME`,`JOIN_DATE` ;

-- ----------------------------
-- View structure for v_user_history_game_list
-- ----------------------------
DROP VIEW IF EXISTS `v_user_history_game_list`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_user_history_game_list` AS select `ct1`.`USER_NAME` AS `USER_NAME`,`ct2`.`GAME_ID` AS `GAME_ID`,`ct2`.`GAME_CODE` AS `GAME_CODE`,`ct3`.`GAME_TITLE` AS `GAME_TITLE`,`ct3`.`GAME_ICON` AS `GAME_ICON`,`ct2`.`GAME_FOUNDER` AS `GAME_FOUNDER`,`ct4`.`USER_NICKNAME` AS `FOUNDER_NICKNAME`,`ct2`.`START_TIME` AS `START_TIME`,`ct2`.`FINISH_TIME` AS `FINISH_TIME`,`ct2`.`GATHER_PLACE` AS `GATHER_PLACE`,`ct2`.`CUSTOM_INF` AS `CUSTOM_INF`,`ct1`.`GAME_SCORE` AS `GAME_SCORE`,`ct1`.`IS_SCORED` AS `IS_SCORED` from (((`childhood`.`t_status_joined` `ct1` left join (select `childhood`.`t_status_game`.`GAME_ID` AS `GAME_ID`,`childhood`.`t_status_game`.`GAME_CODE` AS `GAME_CODE`,`childhood`.`t_status_game`.`GAME_FOUNDER` AS `GAME_FOUNDER`,`childhood`.`t_status_game`.`START_TIME` AS `START_TIME`,`childhood`.`t_status_game`.`FINISH_TIME` AS `FINISH_TIME`,`childhood`.`t_status_game`.`GATHER_PLACE` AS `GATHER_PLACE`,`childhood`.`t_status_game`.`CUSTOM_INF` AS `CUSTOM_INF` from `childhood`.`t_status_game`) `ct2` on((`ct1`.`GAME_ID` = `ct2`.`GAME_ID`))) left join (select `childhood`.`t_gamerule_head`.`GAME_CODE` AS `GAME_CODE`,`childhood`.`t_gamerule_head`.`GAME_TITLE` AS `GAME_TITLE`,`childhood`.`t_gamerule_head`.`GAME_ICON` AS `GAME_ICON` from `childhood`.`t_gamerule_head`) `ct3` on((`ct2`.`GAME_CODE` = `ct3`.`GAME_CODE`))) left join (select `childhood`.`t_sys_user`.`USER_NAME` AS `USER_NAME`,`childhood`.`t_sys_user`.`USER_NICKNAME` AS `USER_NICKNAME` from `childhood`.`t_sys_user`) `ct4` on((`ct2`.`GAME_FOUNDER` = `ct4`.`USER_NAME`))) where (`ct1`.`JOIN_STATUS` = 'S011') ;

-- ----------------------------
-- View structure for v_user_like_detail
-- ----------------------------
DROP VIEW IF EXISTS `v_user_like_detail`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_user_like_detail` AS select `t1`.`ACCESS` AS `ACCESS`,`t1`.`USER_NAME` AS `USER_NAME`,`t2`.`USER_NICKNAME` AS `USER_NICKNAME`,`t2`.`USER_HEADIMAGE` AS `USER_HEADIMAGE`,`t1`.`LIKE_TIME` AS `LIKE_TIME`,`t1`.`LIKE_USER` AS `LIKE_USER` from (`t_status_user_rank_detail` `t1` join `t_sys_user` `t2`) where (`t1`.`USER_NAME` = `t2`.`USER_NAME`) ;

-- ----------------------------
-- View structure for v_user_like_times_per_day
-- ----------------------------
DROP VIEW IF EXISTS `v_user_like_times_per_day`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_user_like_times_per_day` AS select `t_status_user_rank_detail`.`LIKE_USER` AS `LIKE_USER`,date_format(`t_status_user_rank_detail`.`LIKE_TIME`,'%Y-%m-%d') AS `LIKE_DATE`,count(0) AS `LIKE_TIMES` from `t_status_user_rank_detail` group by `t_status_user_rank_detail`.`USER_NAME`,`LIKE_DATE` ;

-- ----------------------------
-- Procedure structure for COMPUTE_AM_NEARBY_USER
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMPUTE_AM_NEARBY_USER`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `COMPUTE_AM_NEARBY_USER`(IN `disRange` int,IN `latitude` double(8,6),IN `longitude` double(9,6))
BEGIN

#定义游标中字段变量
DECLARE rLastLatitude double(8,6);
DECLARE rLastLongitude double(9,6);

#存储距离
DECLARE dis int;

DECLARE done INT;

    #声明游标
    DECLARE cur CURSOR FOR select LAST_LATITUDE,LAST_LONGITUDE
                                 from T_SYS_USER
																 where USER_NAME NOT IN(select USER_NAME from T_STATUS_JOINED where JOIN_STATUS='S008')
                                 and LOGIN_STATUS='online';

		#将结束标志绑定到游标
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		SET done=FALSE;

		set @tableName=CONCAT("TMP_",UUID_SHORT());
		
		#连接动态临时表表名创建语句
		set @createSqlStr=CONCAT('create temporary table if not exists ',@tableName,'(LAST_LATITUDE double(8,6),LAST_LONGITUDE double(9,6),DISTANCE int);');
		#创建临时表预处理语句并执行
    PREPARE createTableStmt from @createSqlStr;
		EXECUTE createTableStmt;
		DEALLOCATE PREPARE createTableStmt;

    #创建清空临时表预处理语句
		set @truncateSqlStr=CONCAT('truncate TABLE ',@tableName,';');
    PREPARE truncateStmt from @truncateSqlStr;
		EXECUTE truncateStmt;
		DEALLOCATE PREPARE truncateStmt;

		open cur;  #打开游标

    compute_loop: LOOP
        FETCH cur INTO rLastLatitude,
                       rLastLongitude;
				set @rLastLatitude=rLastLatitude;
				set @rLastLongitude=rLastLongitude;
        IF done THEN
            LEAVE compute_loop;
        END IF;
        #计算距离数据
        SET @dis=ExcDistance(latitude,longitude,rLastLatitude,rLastLongitude);
        #距离小于调用存储过程设定距离参数且同小区用户加入临时表
        IF @dis<disRange THEN

					set @insertSqlStr=CONCAT('INSERT INTO ',@tableName,'(LAST_LATITUDE,LAST_LONGITUDE,DISTANCE) VALUES(?,?,?);');
					PREPARE insertStmt from @insertSqlStr;
					EXECUTE insertStmt USING @rLastLatitude,@rLastLongitude,@dis;
					DEALLOCATE PREPARE insertStmt;

        END IF;

        END LOOP;

        CLOSE cur;
    
		#返回临时表数据
		set @querySqlStr=CONCAT('SELECT * FROM ',@tableName,';');
    PREPARE queryStmt from @querySqlStr;
		EXECUTE queryStmt;
		DEALLOCATE PREPARE queryStmt;

		#删除表数据
		set @dropSqlStr=CONCAT('DROP TABLE IF EXISTS ',@tableName,';');
    PREPARE dropStmt from @dropSqlStr;
		EXECUTE dropStmt;
		DEALLOCATE PREPARE dropStmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMPUTE_CAN_INVITE
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMPUTE_CAN_INVITE`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `COMPUTE_CAN_INVITE`(IN userName char(50),IN disRange int,OUT memCount int)
BEGIN

DECLARE district int; #定义用户所属区县变量

#定义游标中字段变量
DECLARE rUserName char(50);
DECLARE rLastLatitude double(8,6);
DECLARE rLastLongitude double(9,6);

#定义当前用户经纬度变量
DECLARE cLatitude double(8,6);
DECLARE cLongitude double(9,6);

#存储距离
DECLARE dis int;

DECLARE done INT;

#声明游标
DECLARE cur CURSOR FOR select USER_NAME,LAST_LATITUDE,LAST_LONGITUDE
                                 from v_status_user_canjoin
                                 where USER_NAME != userName;

#将结束标志绑定到游标
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

		#取出当前用户位置信息
		SELECT LAST_LATITUDE,LAST_LONGITUDE
				INTO cLatitude,cLongitude
				from T_SYS_USER
				where USER_NAME=userName;

		#取出当前用户所属区县信息
		SELECT BELONGING_DISTRICT
				INTO district
				from T_SYS_USER
				where USER_NAME=userName;


		SET done=FALSE;

    set @tableName=CONCAT('TMP_',RTRIM(userName));  #设置自动生成临时表名

		#连接动态临时表表名创建语句
		set @createSqlStr=CONCAT('create temporary table if not exists ',@tableName,'(USER_NAME char(50),LAST_LATITUDE double(8,6),LAST_LONGITUDE double(9,6),DISTANCE int);');
		#创建临时表预处理语句并执行
    PREPARE createTableStmt from @createSqlStr;
		EXECUTE createTableStmt;
		DEALLOCATE PREPARE createTableStmt;

		#创建清空临时表预处理语句
		set @truncateSqlStr=CONCAT('truncate TABLE ',@tableName,';');
    PREPARE truncateStmt from @truncateSqlStr;
		EXECUTE truncateStmt;
		DEALLOCATE PREPARE truncateStmt;

    open cur;  #打开游标

    compute_loop: LOOP
        FETCH cur INTO rUserName,
                       rLastLatitude,
                       rLastLongitude;
				set @rUserName=rUserName;
				set @rLastLatitude=rLastLatitude;
				set @rLastLongitude=rLastLongitude;
        IF done THEN
            LEAVE compute_loop;
        END IF;
        #计算距离数据
        SET @dis=ExcDistance(cLatitude,cLongitude,rLastLatitude,rLastLongitude);
        #距离小于调用存储过程设定距离参数加入临时表
        IF @dis<disRange THEN
						set @insertSqlStr=CONCAT('INSERT INTO ',@tableName,'(USER_NAME,LAST_LATITUDE,LAST_LONGITUDE,DISTANCE) VALUES(?,?,?,?);');
						PREPARE insertStmt from @insertSqlStr;
						EXECUTE insertStmt USING @rUserName,@rLastLatitude,@rLastLongitude,@dis;
						DEALLOCATE PREPARE insertStmt;
        END IF;

        END LOOP;

        CLOSE cur;
		#返回总可邀请人数
		set @memCountSqlStr=CONCAT('SELECT count(*) FROM ',@tableName,' INTO @memNum;');
    PREPARE queryCountStmt from @memCountSqlStr;
		EXECUTE queryCountStmt;
		DEALLOCATE PREPARE queryCountStmt;
		SELECT @memNum INTO memCount;
    
		#返回临时表数据
		set @querySqlStr=CONCAT('SELECT * FROM ',@tableName,';');
    PREPARE queryStmt from @querySqlStr;
		EXECUTE queryStmt;
		DEALLOCATE PREPARE queryStmt;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMPUTE_CAN_JOIN
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMPUTE_CAN_JOIN`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `COMPUTE_CAN_JOIN`(IN `userName` char(50),IN `disRange` int,OUT `gameCount` int)
BEGIN

#DECLARE tableName varchar(100);  #临时表名：TMP_GAME_加用户名
DECLARE district int; #定义用户所属区县变量

#定义游标中字段变量
DECLARE rGameId int;
DECLARE rGameCode int;
DECLARE rGameTitle varchar(100);
DECLARE rGameIcon varchar(100);
DECLARE rUserNickname MEDIUMTEXT;
DECLARE rStartTime datetime;
DECLARE rGameLatitude double(8,6);
DECLARE rGameLongitude double(9,6);
DECLARE rGatherPlace varchar(100);
DECLARE rCustomInf varchar(1000);
DECLARE rCustomCount int;
DECLARE rGameStatus char(4);
DECLARE rJoinedCount int;

#定义当前用户经纬度变量
DECLARE cLatitude double(8,6);
DECLARE cLongitude double(9,6);
DECLARE done INT;

#存储距离
#DECLARE dis int;



    #声明游标
    DECLARE cur CURSOR FOR select GAME_ID,GAME_CODE,GAME_TITLE,GAME_ICON,USER_NICKNAME,START_TIME,GAME_LATITUDE,
																	GAME_LONGITUDE,GATHER_PLACE,CUSTOM_INF,CUSTOM_COUNT,GAME_STATUS,JOINED_COUNT 
																	from v_status_game_canjoin
																	where GAME_FOUNDER!=userName;

		#将结束标志绑定到游标
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		SET done=FALSE;

		#取出当前用户位置与所属区县信息
		SELECT LAST_LATITUDE,LAST_LONGITUDE,BELONGING_DISTRICT
			INTO cLatitude,cLongitude,district
			from T_SYS_USER
			where USER_NAME=userName;

    

    set @tableName=CONCAT('TMP_GAME_',RTRIM(userName));  #设置自动生成临时表名

    #连接动态临时表表名创建语句
		set @createSqlStr=CONCAT('create temporary table if not exists ',@tableName,'(GAME_ID int,GAME_CODE int,GAME_TITLE varchar(100),GAME_ICON varchar(100),USER_NICKNAME varchar(100),START_TIME datetime,GAME_LATITUDE double(8,6),GAME_LONGITUDE double(9,6),GATHER_PLACE varchar(100),CUSTOM_INF varchar(1000),CUSTOM_COUNT int,GAME_STATUS char(4),JOINED_COUNT int,DISTANCE int);');
		#创建临时表预处理语句并执行
    PREPARE createTableStmt from @createSqlStr;
		EXECUTE createTableStmt;
		DEALLOCATE PREPARE createTableStmt;

    #创建清空临时表预处理语句
		set @truncateSqlStr=CONCAT('truncate TABLE ',@tableName,';');
    PREPARE truncateStmt from @truncateSqlStr;
		EXECUTE truncateStmt;
		DEALLOCATE PREPARE truncateStmt;

    open cur;  #打开游标

     compute_loop: LOOP
        FETCH cur INTO rGameId,
											 rGameCode,
											 rGameTitle,
											 rGameIcon,
											 rUserNickname,
											 rStartTime,
											 rGameLatitude,
											 rGameLongitude,
											 rGatherPlace,
											 rCustomInf,
											 rCustomCount,
											 rGameStatus,
											 rJoinedCount;
				set @rGameId=rGameId;
				set @rGameCode=rGameCode;
				set @rGameTitle=rGameTitle;
				set @rGameIcon=rGameIcon;
				set @rUserNickname=rUserNickname;
				set @rStartTime=rStartTime;
				set @rGameLatitude=rGameLatitude;
				set @rGameLongitude=rGameLongitude;
				set @rGatherPlace=rGatherPlace;
				set @rCustomInf=rCustomInf;
				set @rCustomCount=rCustomCount;
				set @rGameStatus=rGameStatus;
				set @rJoinedCount=rJoinedCount;
        IF done THEN
            LEAVE compute_loop;
        END IF;
        #计算距离数据
        SET @dis=ExcDistance(cLatitude,cLongitude,rGameLatitude,rGameLongitude);
        #距离小于调用存储过程设定距离参数且同小区游戏加入临时表
        IF @dis<disRange THEN
            set @insertSqlStr=CONCAT('INSERT INTO ',@tableName,'(GAME_ID,GAME_CODE,GAME_TITLE,GAME_ICON,USER_NICKNAME,START_TIME,GAME_LATITUDE,GAME_LONGITUDE,GATHER_PLACE,CUSTOM_INF,CUSTOM_COUNT,GAME_STATUS,JOINED_COUNT,DISTANCE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)');
						PREPARE insertStmt from @insertSqlStr;
						EXECUTE insertStmt USING @rGameId,@rGameCode,@rGameTitle,@rGameIcon,@rUserNickname,@rStartTime,@rGameLatitude,@rGameLongitude,@rGatherPlace,@rCustomInf,@rCustomCount,@rGameStatus,@rJoinedCount,@dis;
						DEALLOCATE PREPARE insertStmt;
        END IF;

        END LOOP;

        CLOSE cur;

    #返回总可参加游戏数
		set @memCountSqlStr=CONCAT('SELECT count(*) FROM ',@tableName,' INTO @gameNum;');
    PREPARE queryCountStmt from @memCountSqlStr;
		EXECUTE queryCountStmt;
		DEALLOCATE PREPARE queryCountStmt;
		SELECT @gameNum INTO gameCount;

		#返回临时表数据
		set @querySqlStr=CONCAT('SELECT * FROM ',@tableName,';');
    PREPARE queryStmt from @querySqlStr;
		EXECUTE queryStmt;
		DEALLOCATE PREPARE queryStmt;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMPUTE_YOUNG_PARTNER
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMPUTE_YOUNG_PARTNER`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `COMPUTE_YOUNG_PARTNER`(IN `userName` char(50),IN `disRange` int,IN `ageRange` int)
BEGIN
	#定义游标中字段变量
	declare mUserName char(50);
	declare mAge int;
	declare mYoungLatitude double(8,6);
	declare mYoungLongitude double(9,6);
	declare mEnable enum('Y','N');
	#定义当前用户儿时经纬度信息变量
	declare cAge int;
	declare cYoungLattude double(8,6);
	declare cYoungLongitude double(9,6);
	#定义距离变量
	declare dis int;
	#定义游标结束标志位
	DECLARE done INT;

	#声明游标
  DECLARE cur CURSOR FOR select USER_NAME,AGE,YOUNG_LATITUDE,YOUNG_LONGITUDE,ENABLE
                               from T_FIND_REG
                               where USER_NAME!=userName
															 and USER_NAME NOT IN(select MATCH_USER from T_FIND_RESULT where USER_NAME=userName);

	#将结束标志绑定到游标
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	SET done=FALSE;

	#取出当前用户儿时年龄及位置信息
	select AGE,YOUNG_LATITUDE,YOUNG_LONGITUDE
	into cAge,cYoungLattude,cYoungLongitude
	from T_FIND_REG
	where USER_NAME=userName;	

open cur;  #打开游标

compute_loop: LOOP
	FETCH cur INTO mUserName,
								 mAge,
                 mYoungLatitude,
								 mYoungLongitude,
								 mEnable;
	IF done THEN
			LEAVE compute_loop;
	END IF;
	#计算距离数据
	SET dis=ExcDistance(cYoungLattude,cYoungLongitude,mYoungLatitude,mYoungLongitude);
	#距离小于调用存储过程设定距离参数且同小区用户加入临时表
	IF dis<disRange THEN
		IF ABS(cAge-mAge)<=ageRange THEN
			insert into T_FIND_RESULT(USER_NAME,MATCH_USER,ENABLE)
			values(userName,mUserName,mEnable);
		END IF;
	END IF;
END LOOP;
CLOSE cur;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_AUTO_REFRESH_USER_MEDAL
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_AUTO_REFRESH_USER_MEDAL`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `PRC_AUTO_REFRESH_USER_MEDAL`(IN `medalId` int)
BEGIN
DECLARE userName varchar(50);
DECLARE done INT;

DECLARE cur CURSOR FOR SELECT
	USER_NAME
FROM
	t_sys_user
WHERE
	USER_HEADIMAGE <> 'system';

#将结束标志绑定到游标
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

SET done = FALSE;

OPEN cur;

insert_loop :
LOOP
	FETCH cur INTO userName;

	IF userName is null THEN
		LEAVE insert_loop;
	END IF;

	INSERT IGNORE INTO t_achvmt_user_medal_list (USER_NAME, MEDAL_ID) VALUES(userName, medalId);
	IF done THEN
		LEAVE insert_loop;
	END IF;
END LOOP;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_GET_USER_RANK
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_GET_USER_RANK`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `PRC_GET_USER_RANK`(IN `userName` varchar(50),IN `maxRow` int)
BEGIN
DECLARE belongingArea int;

select BELONGING_AREA into belongingArea from T_SYS_USER where USER_NAME = userName;

select * from(select CT1.*,CT2.LIKE_COUNT from (SELECT
	(@rank :=@rank + 1) as RANK,T1.USER_NAME,T1.USER_HEADIMAGE,T1.USER_NICKNAME,T1.ACHIEVEMENT_POINT
FROM
	(
		SELECT
			*
		FROM
			T_SYS_USER
		WHERE
			user_headimage <> 'system'
		and
			BELONGING_AREA = belongingArea
	) T1,
	(SELECT @rank := 0) T2
ORDER BY
	T1.ACHIEVEMENT_POINT desc
limit 0,maxRow) CT1,
T_STATUS_USER_RANK CT2
where CT1.USER_NAME = CT2.USER_NAME) CT
where USER_NAME = userName;

select CT1.*,CT2.LIKE_COUNT from (SELECT
	(@rank :=@rank + 1) as RANK,T1.USER_NAME,T1.USER_HEADIMAGE,T1.USER_NICKNAME,T1.ACHIEVEMENT_POINT
FROM
	(
		SELECT
			*
		FROM
			T_SYS_USER
		WHERE
			user_headimage <> 'system'
		and
			BELONGING_AREA = belongingArea
	) T1,
	(SELECT @rank := 0) T2
ORDER BY
	T1.ACHIEVEMENT_POINT desc
limit 0,maxRow) CT1,
T_STATUS_USER_RANK CT2
where CT1.USER_NAME = CT2.USER_NAME
order by CT1.ACHIEVEMENT_POINT desc;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_RULE_ACHVMT_TOP_EVENT
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_RULE_ACHVMT_TOP_EVENT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PRC_RULE_ACHVMT_TOP_EVENT`(IN `userName` varchar(50),IN `achievementPoint` int)
BEGIN
DECLARE templateStr_medal varchar(200);
DECLARE paramStr_medal varchar(200);
DECLARE tempStr varchar(200);

SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr_medal,paramStr_medal
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 12;

IF achievementPoint = 5000 THEN
	set tempStr = REPLACE(paramStr_medal,'medalNamePlaceHolder','成就之王');
	update T_ACHVMT_USER_MEDAL_LIST set ENABLE='Y' where USER_NAME=userName and MEDAL_ID=7;
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,12,tempStr);
END IF;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_RULE_AUTO_INSERT_USER_MEDAL
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_RULE_AUTO_INSERT_USER_MEDAL`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PRC_RULE_AUTO_INSERT_USER_MEDAL`(IN `userName` varchar(50))
BEGIN

DECLARE medalId INT;
DECLARE done INT;

DECLARE cur CURSOR FOR SELECT
	MEDAL_ID
FROM
	t_achvmt_medal;

#将结束标志绑定到游标
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

SET done = FALSE;

OPEN cur;

insert_loop :
LOOP
	FETCH cur INTO medalId;

	IF medalId is null THEN
		LEAVE insert_loop;
	END IF;

	INSERT IGNORE INTO t_achvmt_user_medal_list (USER_NAME, MEDAL_ID) VALUES(userName, medalId);
	IF done THEN
		LEAVE insert_loop;
	END IF;
END LOOP;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_RULE_CONTINUE_GAME_DAY_EVENT
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_RULE_CONTINUE_GAME_DAY_EVENT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PRC_RULE_CONTINUE_GAME_DAY_EVENT`(IN `gameId` int)
BEGIN
DECLARE userName varchar(50);
DECLARE lastGameDate char(10);
DECLARE continueGameDays int;
DECLARE templateStr_medal varchar(200);
DECLARE paramStr_medal varchar(200);
DECLARE tempStr varchar(200);

DECLARE i int;

DECLARE done int;

DECLARE cur_userName CURSOR FOR SELECT USER_NAME FROM T_STATUS_JOINED where GAME_ID = gameId;

#将结束标志绑定到游标
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

SET done = FALSE;

SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr_medal,paramStr_medal
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 12;

OPEN cur_userName;
userGameCountLoop:LOOP
	FETCH cur_userName INTO userName;

	IF done THEN
		LEAVE userGameCountLoop;
	END IF;

	SELECT DISTINCT
		DATE_FORMAT(JOIN_TIME, '%Y-%m-%d')
	INTO
		lastGameDate
	FROM
		T_STATUS_JOINED
	WHERE
		USER_NAME = userName
	AND
		JOIN_STATUS='S011'
	ORDER BY
		DATE_FORMAT(JOIN_TIME, '%Y-%m-%d') DESC
	LIMIT 0,1;

	SET i = 0;

	SET continueGameDays = 1;

do_while:WHILE i < 9 DO

		IF NOT EXISTS (SELECT
													*
										FROM
											T_STATUS_JOINED
										WHERE
											USER_NAME = userName
										AND JOIN_STATUS = 'S011'
										AND DATE_FORMAT(JOIN_TIME, '%Y-%m-%d') = DATE_SUB(
																																			lastGameDate,
																																			INTERVAL i + 1 DAY
																																			)) THEN
			LEAVE do_while;
		END IF;

		SET continueGameDays = continueGameDays + 1;
		SET i = i + 1;

	END WHILE;

	IF continueGameDays = 10 THEN
		set tempStr = REPLACE(paramStr,'medalNamePlaceHolder','钻石玩家');
		update T_ACHVMT_USER_MEDAL_LIST set ENABLE='Y' where USER_NAME=userName and MEDAL_ID=6;
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
		values(userName,12,tempStr);
	END IF;
END LOOP;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_RULE_CONTINUE_LIKE_DAY_EVENT
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_RULE_CONTINUE_LIKE_DAY_EVENT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PRC_RULE_CONTINUE_LIKE_DAY_EVENT`(IN `userName` varchar(50))
BEGIN
DECLARE lastLikeDate char(10);
DECLARE continueLikeDays int;
DECLARE templateStr_medal varchar(200);
DECLARE paramStr_medal varchar(200);
DECLARE tempStr varchar(200);

DECLARE i int;

SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr_medal,paramStr_medal
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 12;

SELECT DISTINCT
	DATE_FORMAT(LIKE_TIME, '%Y-%m-%d')
INTO
	lastLikeDate
FROM
	T_STATUS_USER_RANK_DETAIL
WHERE
	USER_NAME = userName
ORDER BY
	DATE_FORMAT(LIKE_TIME, '%Y-%m-%d') DESC
LIMIT 0,1;

SET i = 0;

SET continueLikeDays = 1;

do_while:WHILE i < 9 DO

	IF NOT EXISTS (SELECT
														*
											FROM
												T_STATUS_USER_RANK_DETAIL
											WHERE
												USER_NAME = userName
											AND DATE_FORMAT(LIKE_TIME, '%Y-%m-%d') = DATE_SUB(
																																				lastLikeDate,
																																				INTERVAL i + 1 DAY
																																				)) THEN
		LEAVE do_while;
	END IF;

	SET continueLikeDays = continueLikeDays + 1;
	SET i = i + 1;
END WHILE;

IF continueLikeDays = 10 THEN
	set tempStr = REPLACE(paramStr,'medalNamePlaceHolder','人气超凡');
	update T_ACHVMT_USER_MEDAL_LIST set ENABLE='Y' where USER_NAME=userName and MEDAL_ID=9;
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,12,tempStr);
END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_RULE_EVERYDAY_FIRST_LOGIN
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_RULE_EVERYDAY_FIRST_LOGIN`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PRC_RULE_EVERYDAY_FIRST_LOGIN`(IN `userName` varchar(50))
BEGIN
if not exists (SELECT
	*
FROM
	t_sys_log_login
WHERE
	USER_NAME = userName
AND DATE_FORMAT(LOGIN_TIME, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d')) THEN
	update T_SYS_USER set ACHIEVEMENT_POINT = ACHIEVEMENT_POINT + 1 where USER_NAME = userName;
end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_RULE_FIRST_FEEDBACK_EVENT
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_RULE_FIRST_FEEDBACK_EVENT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PRC_RULE_FIRST_FEEDBACK_EVENT`(IN `userName` varchar(50))
BEGIN
DECLARE templateStr varchar(200);
DECLARE paramStr varchar(200);
DECLARE tempStr varchar(200);


SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr,paramStr
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 10;

IF NOT EXISTS (SELECT
									*
								FROM
									t_sys_feedback
								WHERE
									USER_NAME = userName) THEN
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,10,tempStr);
END IF;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_RULE_FOUND_GAME_COUNT_EVENT
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_RULE_FOUND_GAME_COUNT_EVENT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PRC_RULE_FOUND_GAME_COUNT_EVENT`(IN `gameId` int)
BEGIN
DECLARE userName varchar(50);
DECLARE foundGameCount int;
DECLARE templateStr varchar(200);
DECLARE paramStr varchar(200);
DECLARE tempStr varchar(200);

SELECT GAME_FOUNDER INTO userName FROM T_STATUS_GAME WHERE GAME_ID = gameId;

SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr,paramStr
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 9;

SELECT
	TOTAL_FOUND_GAME_COUNT
INTO
	foundGameCount
FROM
	V_ACHVMT_USER_KPI
WHERE
	T1.USER_NAME = userName;

IF foundGameCount = 1 THEN
	set tempStr = REPLACE(paramStr,'countPlaceHolder','1');
	set tempStr = REPLACE(tempStr,'achievementPlaceHolder','足迹寻友');
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,9,tempStr);
END IF;

IF foundGameCount = 10 THEN
	set tempStr = REPLACE(paramStr,'countPlaceHolder','10');
	set tempStr = REPLACE(tempStr,'achievementPlaceHolder','呼朋唤友');
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,9,tempStr);
END IF;

IF foundGameCount = 30 THEN
	set tempStr = REPLACE(paramStr,'countPlaceHolder','30');
	set tempStr = REPLACE(tempStr,'achievementPlaceHolder','活跃分子');
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,9,tempStr);
END IF;

IF foundGameCount = 60 THEN
	set tempStr = REPLACE(paramStr,'countPlaceHolder','60');
	set tempStr = REPLACE(tempStr,'achievementPlaceHolder','组织达人');
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,9,tempStr);
END IF;

IF foundGameCount = 100 THEN
	set tempStr = REPLACE(paramStr,'countPlaceHolder','100');
	set tempStr = REPLACE(tempStr,'achievementPlaceHolder','天下谁人不识君');
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,9,tempStr);
END IF;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_RULE_GAME_COUNT_EVENT
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_RULE_GAME_COUNT_EVENT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PRC_RULE_GAME_COUNT_EVENT`(IN `gameId` int)
BEGIN
DECLARE userName varchar(50);
DECLARE gameCount int;
DECLARE templateStr varchar(200);
DECLARE paramStr varchar(200);
DECLARE templateStr_medal varchar(200);
DECLARE paramStr_medal varchar(200);
DECLARE tempStr varchar(200);

DECLARE done int;

DECLARE cur_userName CURSOR FOR SELECT USER_NAME FROM T_STATUS_JOINED where GAME_ID = gameId;

#将结束标志绑定到游标
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

SET done = FALSE;

SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr,paramStr
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 8;

SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr_medal,paramStr_medal
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 12;

OPEN cur_userName;
userGameCountLoop:LOOP
	FETCH cur_userName INTO userName;

	IF done THEN
		LEAVE userGameCountLoop;
	END IF;

	SELECT
		TOTAL_GAME_COUNT INTO gameCount
	FROM
		V_ACHVMT_USER_KPI
	WHERE
		USER_NAME = userName;

	IF gameCount = 1 THEN
		set tempStr = REPLACE(paramStr,'medalNamePlaceHolder','游戏新星');
		update T_ACHVMT_USER_MEDAL_LIST set ENABLE='Y' where USER_NAME=userName and MEDAL_ID=3;
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
		values(userName,12,tempStr);
	END IF;

	IF gameCount = 10 THEN
		set tempStr = REPLACE(paramStr,'countPlaceHolder','10');
		set tempStr = REPLACE(tempStr,'achievementPlaceHolder','三人行必有我师');
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
		values(userName,8,tempStr);
	END IF;

	IF gameCount = 30 THEN
		set tempStr = REPLACE(paramStr,'countPlaceHolder','30');
		set tempStr = REPLACE(tempStr,'achievementPlaceHolder','广交侠者');
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
		values(userName,8,tempStr);
	END IF;

	IF gameCount = 60 THEN
		set tempStr = REPLACE(paramStr,'countPlaceHolder','60');
		set tempStr = REPLACE(tempStr,'achievementPlaceHolder','八面玲珑');
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
		values(userName,8,tempStr);
	END IF;

	IF gameCount = 100 THEN
		set tempStr = REPLACE(paramStr,'countPlaceHolder','100');
		set tempStr = REPLACE(tempStr,'achievementPlaceHolder','百呼百应');
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
		values(userName,8,tempStr);
	END IF;

	IF gameCount = 150 THEN
		set tempStr = REPLACE(paramStr,'medalNamePlaceHolder','游戏大师');
		update T_ACHVMT_USER_MEDAL_LIST set ENABLE='Y' where USER_NAME=userName and MEDAL_ID=4;
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
		values(userName,12,tempStr);
	END IF;

END LOOP;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_RULE_INVITE_USER_EVENT
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_RULE_INVITE_USER_EVENT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PRC_RULE_INVITE_USER_EVENT`(IN `gameId` int)
BEGIN
DECLARE userName varchar(50);
DECLARE inviteUserCount int;
DECLARE templateStr varchar(200);
DECLARE paramStr varchar(200);
DECLARE tempStr varchar(200);

SELECT GAME_FOUNDER INTO userName FROM T_STATUS_GAME WHERE GAME_ID = gameId;

SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr,paramStr
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 12;

SELECT
	count(DISTINCT T1.USER_NAME)
INTO
	inviteUserCount
FROM
	T_STATUS_JOINED T1,
	T_STATUS_GAME T2
WHERE
	T1.GAME_ID = T2.GAME_ID
AND T2.GAME_FOUNDER = userName
AND T1.JOIN_STATUS = 'S011';

IF inviteUserCount >= 150 THEN
	set tempStr = REPLACE(paramStr,'medalNamePlaceHolder','胜友如云');
	update T_ACHVMT_USER_MEDAL_LIST set ENABLE='Y' where USER_NAME=userName and MEDAL_ID=2;
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,12,tempStr);
END IF;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_RULE_KIND_GAME_COUNT_EVENT
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_RULE_KIND_GAME_COUNT_EVENT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PRC_RULE_KIND_GAME_COUNT_EVENT`(IN `gameId` int)
BEGIN
DECLARE userName varchar(50);
DECLARE totalKindGameCount int;
DECLARE templateStr varchar(200);
DECLARE paramStr varchar(200);
DECLARE templateStr_medal varchar(200);
DECLARE paramStr_medal varchar(200);
DECLARE tempStr varchar(200);

DECLARE done int;

DECLARE cur_userName CURSOR FOR SELECT USER_NAME FROM T_STATUS_JOINED where GAME_ID = gameId;

#将结束标志绑定到游标
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

SET done = FALSE;

SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr,paramStr
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 8;

SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr_medal,paramStr_medal
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 12;

OPEN cur_userName;
userGameCountLoop:LOOP
	FETCH cur_userName INTO userName;

	IF done THEN
		LEAVE userGameCountLoop;
	END IF;

	SELECT
		TOTAL_KIND_GAME_COUNT INTO totalKindGameCount
	FROM
		V_ACHVMT_USER_KPI
	WHERE
		USER_NAME = userName;

	IF gameCount = 10 THEN
		set tempStr = REPLACE(paramStr,'medalNamePlaceHolder','大收藏家');
		update T_ACHVMT_USER_MEDAL_LIST set ENABLE='Y' where USER_NAME=userName and MEDAL_ID=5;
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
		values(userName,12,tempStr);
	END IF;

END LOOP;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_RULE_LIKE_COUNT
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_RULE_LIKE_COUNT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PRC_RULE_LIKE_COUNT`(IN `userName` varchar(50))
BEGIN
DECLARE likeCount int;
DECLARE templateStr varchar(200);
DECLARE paramStr varchar(200);
DECLARE templateStr_medal varchar(200);
DECLARE paramStr_medal varchar(200);
DECLARE tempStr varchar(200);

SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr,paramStr
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 11;

SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr_medal,paramStr_medal
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 12;

SELECT LIKE_COUNT
INTO likeCount
FROM t_status_user_rank
WHERE
USER_NAME = userName;

IF likeCount = 1 THEN
	set tempStr = REPLACE(paramStr_medal,'medalNamePlaceHolder','人气萌芽');
	update T_ACHVMT_USER_MEDAL_LIST set ENABLE='Y' where USER_NAME=userName and MEDAL_ID=8;
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,12,tempStr);
END IF;

IF likeCount = 10 THEN
	set tempStr = REPLACE(paramStr,'likeCountPlaceHolder','10');
	set tempStr = REPLACE(tempStr,'achievementPlaceHolder','赫赫有名');
select tempStr;
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,11,tempStr);
END IF;

IF likeCount = 50 THEN
	set tempStr = REPLACE(paramStr,'likeCountPlaceHolder','50');
	set tempStr = REPLACE(tempStr,'achievementPlaceHolder','名不虚传');
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,11,tempStr);
END IF;

IF likeCount = 100 THEN
	set tempStr = REPLACE(paramStr,'likeCountPlaceHolder','100');
	set tempStr = REPLACE(tempStr,'achievementPlaceHolder','声名远扬');
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,11,tempStr);
END IF;

IF likeCount = 300 THEN
	set tempStr = REPLACE(paramStr,'likeCountPlaceHolder','300');
	set tempStr = REPLACE(tempStr,'achievementPlaceHolder','大名鼎鼎');
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,11,tempStr);
END IF;

IF likeCount = 1000 THEN
	set tempStr = REPLACE(paramStr_medal,'medalNamePlaceHolder','名扬四海');
	update T_ACHVMT_USER_MEDAL_LIST set ENABLE='Y' where USER_NAME=userName and MEDAL_ID=10;
	insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	values(userName,12,tempStr);
END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_RULE_LOGIN_DAYS_EVENT
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_RULE_LOGIN_DAYS_EVENT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PRC_RULE_LOGIN_DAYS_EVENT`(IN `userName` varchar(50))
BEGIN
DECLARE loginDays int;
DECLARE templateStr varchar(200);
DECLARE paramStr varchar(200);
DECLARE tempStr varchar(200);
DECLARE templateStr_medal varchar(200);
DECLARE paramStr_medal varchar(200);

SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr,paramStr
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 7;

SELECT
	TEMPLATE_STR,TEMPLATE_PARAMS
INTO
	templateStr_medal,paramStr_medal
FROM
	t_achvmt_event_template
WHERE
	TEMPLATE_ID = 12;

SELECT
	count(distinct DATE_FORMAT(LOGIN_TIME, '%Y-%m-%d'))
INTO
	loginDays
FROM
	t_sys_log_login
WHERE
	USER_NAME = userName;

IF loginDays = 1 THEN
	set tempStr = REPLACE(paramStr,'countPlaceHolder','1');
	set tempStr = REPLACE(tempStr,'achievementPlaceHolder','成长新星');
	if not exists(select * from T_ACHVMT_USER_EVENT_LIST where USER_NAME=userName and EVENT_TEMPLATE=7 and EVENT_PARAMS=tempStr) then
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
		values(userName,7,tempStr);
	end if;
END IF;

IF loginDays = 30 THEN
	set tempStr = REPLACE(paramStr,'countPlaceHolder','30');
	set tempStr = REPLACE(tempStr,'achievementPlaceHolder','初出茅庐');
	if not exists(select * from T_ACHVMT_USER_EVENT_LIST where USER_NAME=userName and EVENT_TEMPLATE=7 and EVENT_PARAMS=tempStr) then
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
		values(userName,7,tempStr);
	end if;
END IF;

IF loginDays = 60 THEN
	set tempStr = REPLACE(paramStr,'countPlaceHolder','60');
	set tempStr = REPLACE(tempStr,'achievementPlaceHolder','游戏新秀');
	if not exists(select * from T_ACHVMT_USER_EVENT_LIST where USER_NAME=userName and EVENT_TEMPLATE=7 and EVENT_PARAMS=tempStr) then
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
		values(userName,7,tempStr);
	end if;
END IF;

IF loginDays = 100 THEN
	set tempStr = REPLACE(paramStr,'countPlaceHolder','100');
	set tempStr = REPLACE(tempStr,'achievementPlaceHolder','游戏大侠');
	if not exists(select * from T_ACHVMT_USER_EVENT_LIST where USER_NAME=userName and EVENT_TEMPLATE=7 and EVENT_PARAMS=tempStr) then
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
		values(userName,7,tempStr);
	end if;
END IF;

IF loginDays = 300 THEN
	set tempStr = REPLACE(paramStr_medal,'medalNamePlaceHolder','一派掌门');
	update T_ACHVMT_USER_MEDAL_LIST set ENABLE='Y' where USER_NAME=userName and MEDAL_ID=1;
	if not exists(select * from T_ACHVMT_USER_EVENT_LIST where USER_NAME=userName and EVENT_TEMPLATE=12 and EVENT_PARAMS=tempStr) then
		insert into T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
		values(userName,12,tempStr_medal);
	end if;
END IF;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PRC_RULE_REGISTER_WELCOME
-- ----------------------------
DROP PROCEDURE IF EXISTS `PRC_RULE_REGISTER_WELCOME`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PRC_RULE_REGISTER_WELCOME`(IN `userName` varchar(50))
BEGIN
if not exists(select * from T_ACHVMT_USER_EVENT_LIST where USER_NAME=userName and EVENT_TEMPLATE=1) then
	INSERT INTO T_ACHVMT_USER_EVENT_LIST(USER_NAME,EVENT_TEMPLATE,EVENT_PARAMS)
	VALUES(userName,1,'{}');
end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PROCEDURE_DISPATCHER
-- ----------------------------
DROP PROCEDURE IF EXISTS `PROCEDURE_DISPATCHER`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `PROCEDURE_DISPATCHER`(IN `moduleNAME` varchar(50),IN `arg` varchar(50),OUT `result` enum('Y','N'))
dispatcher_prcs:BEGIN
#声明模块ID变量
DECLARE moduleId int;
#声明待执行存储过程名称
DECLARE prcName varchar(50);

DECLARE done int;

DECLARE cur_hasArg CURSOR FOR SELECT	
														RULE_PROCEDURE
												FROM
														T_ACHVMT_EVENT_RULES
												WHERE
														RULE_MODULE = moduleId
												AND	PROCEDURE_HASARG = 'Y'
												AND ENABLE='Y';

DECLARE cur_hasNotArg CURSOR FOR SELECT	
														RULE_PROCEDURE
												FROM
														T_ACHVMT_EVENT_RULES
												WHERE
														RULE_MODULE = moduleId
												AND	PROCEDURE_HASARG = 'N'
												AND ENABLE='Y';

#将结束标志绑定到游标
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

SET done = FALSE;

SELECT
	MODULE_ID
INTO
	moduleId
FROM
	T_ACHVMT_MODULE
WHERE
	MODULE_NAME = moduleName;

IF moduleId IS NULL THEN
	set result = 'N';
	LEAVE dispatcher_prcs;
END IF;



IF arg is null THEN
	OPEN cur_hasNotArg;
	call_loop:
	LOOP
	FETCH cur_hasNotArg INTO prcName;
	IF done THEN
		LEAVE call_loop;
	END IF;

	SET @callSqlStr = CONCAT('call ',prcName);

	#创建执行存储过程预处理语句并执行
	PREPARE callStmt from @callSqlStr;
	EXECUTE callStmt;
	DEALLOCATE PREPARE callStmt;

	END LOOP;
ELSE
	OPEN cur_hasArg;
	call_loop:
	
	LOOP
	FETCH cur_hasArg INTO prcName;
	IF done THEN
		LEAVE call_loop;
	END IF;

	SET @callSqlStr = CONCAT('call ',prcName,'(',arg,')');

	#创建执行存储过程预处理语句并执行
	PREPARE callStmt from @callSqlStr;
	EXECUTE callStmt;
	DEALLOCATE PREPARE callStmt;

	END LOOP;
END IF;
SET result = 'Y';

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for UPDATE_FIND_RESULT
-- ----------------------------
DROP PROCEDURE IF EXISTS `UPDATE_FIND_RESULT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `UPDATE_FIND_RESULT`()
BEGIN

	DECLARE mUserName char(50);
	DECLARE done INT;
	#声明游标
	DECLARE cur CURSOR FOR select USER_NAME
                              from T_FIND_REG
															where ENABLE='Y';
	#将结束标志绑定到游标
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	SET done=FALSE;

	open cur;  #打开游标
update_loop:
LOOP
	FETCH cur INTO mUserName;
	IF done THEN
			LEAVE update_loop;
	END IF;
	call COMPUTE_YOUNG_PARTNER(mUserName,1000,5);
END LOOP;
CLOSE cur;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for ExcDistance
-- ----------------------------
DROP FUNCTION IF EXISTS `ExcDistance`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `ExcDistance`(`LATITUDE_A` double,`LONGITUDE_A` double,`LATITUDE_B` double,`LONGITUDE_B` double) RETURNS double
BEGIN
	#Routine body goes here...

	RETURN round(
		6378.138 * 2 * asin(
			sqrt(
				pow(
					sin(
						(
							LATITUDE_A * pi() / 180 - LATITUDE_B * pi() / 180
						) / 2
					),
					2
				) + cos(LATITUDE_A * pi() / 180) * cos(LATITUDE_B * pi() / 180) * pow(
					sin(
						(
							LONGITUDE_A * pi() / 180 - LONGITUDE_B * pi() / 180
						) / 2
					),
					2
				)
			)
		) * 1000
	);
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for UPDATE_HEAD_GAME_SCORE
-- ----------------------------
DROP EVENT IF EXISTS `UPDATE_HEAD_GAME_SCORE`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `UPDATE_HEAD_GAME_SCORE` ON SCHEDULE EVERY 1 HOUR STARTS '2016-07-12 10:51:43' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN

call UPDATE_FIND_RESULT;

update t_gamerule_head T3 set GAME_SCORE=(
	SELECT
		AVG(T1.GAME_SCORE)
	FROM
		t_status_joined T1,
		t_status_game T2
where T1.GAME_ID=T2.GAME_ID
and T2.GAME_CODE=T3.GAME_CODE)
where T3.GAME_CODE in (select GAME_CODE from T_STATUS_GAME);

end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `AUTO_INSERT_RULE_MODULE_NAME`;
DELIMITER ;;
CREATE TRIGGER `AUTO_INSERT_RULE_MODULE_NAME` BEFORE INSERT ON `t_achvmt_event_rules` FOR EACH ROW begin
select MODULE_NAME into @moduleName from T_ACHVMT_MODULE where MODULE_ID = new.RULE_MODULE;
set new.RULE_MODULE_NAME = @moduleName;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `AUTO_UPDATE_RULE_MODULE_NAME`;
DELIMITER ;;
CREATE TRIGGER `AUTO_UPDATE_RULE_MODULE_NAME` BEFORE UPDATE ON `t_achvmt_event_rules` FOR EACH ROW begin
select MODULE_NAME into @moduleName from T_ACHVMT_MODULE where MODULE_ID = new.RULE_MODULE;
set new.RULE_MODULE_NAME = @moduleName;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `AUTO_INSERT_TEMPLATE_NAME`;
DELIMITER ;;
CREATE TRIGGER `AUTO_INSERT_TEMPLATE_NAME` BEFORE INSERT ON `t_achvmt_event_template` FOR EACH ROW begin
select MODULE_NAME into @moduleName from T_ACHVMT_MODULE where MODULE_ID = new.TEMPLATE_MODULE;
set new.TEMPLATE_MODULE_NAME = @moduleName;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `AUTO_UPDATE_TEMPLATE_NAME`;
DELIMITER ;;
CREATE TRIGGER `AUTO_UPDATE_TEMPLATE_NAME` BEFORE UPDATE ON `t_achvmt_event_template` FOR EACH ROW begin
select MODULE_NAME into @moduleName from T_ACHVMT_MODULE where MODULE_ID = new.TEMPLATE_MODULE;
set new.TEMPLATE_MODULE_NAME = @moduleName;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `AUTO_REFRESH_USER_MEDAL`;
DELIMITER ;;
CREATE TRIGGER `AUTO_REFRESH_USER_MEDAL` AFTER INSERT ON `t_achvmt_medal` FOR EACH ROW call PRC_AUTO_REFRESH_USER_MEDAL(new.MEDAL_ID)
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `AUTO_INSERT_EVENT_TEMPLATE_NAME`;
DELIMITER ;;
CREATE TRIGGER `AUTO_INSERT_EVENT_TEMPLATE_NAME` BEFORE INSERT ON `t_achvmt_user_event_list` FOR EACH ROW begin
select TEMPLATE_NAME into @templateName from T_ACHVMT_EVENT_TEMPLATE where TEMPLATE_ID = new.EVENT_TEMPLATE;
set new.EVENT_TEMPLATE_NAME = @templateName;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `AUTO_UPDATE_EVENT_TEMPLATE_NAME`;
DELIMITER ;;
CREATE TRIGGER `AUTO_UPDATE_EVENT_TEMPLATE_NAME` BEFORE UPDATE ON `t_achvmt_user_event_list` FOR EACH ROW begin
select TEMPLATE_NAME into @templateName from T_ACHVMT_EVENT_TEMPLATE where TEMPLATE_ID = new.EVENT_TEMPLATE;
set new.EVENT_TEMPLATE_NAME = @templateName;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `AUTO_UPDATE_RESULT`;
DELIMITER ;;
CREATE TRIGGER `AUTO_UPDATE_RESULT` AFTER UPDATE ON `t_find_reg` FOR EACH ROW begin
  update T_FIND_RESULT set ENABLE=new.ENABLE where MATCH_USER=new.USER_NAME or USER_NAME=new.USER_NAME;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `AUTO_INSERT_POINT_AND_COUNT`;
DELIMITER ;;
CREATE TRIGGER `AUTO_INSERT_POINT_AND_COUNT` BEFORE INSERT ON `t_status_game` FOR EACH ROW begin
select LAST_LATITUDE,LAST_LONGITUDE into @latitude,@longitude from T_SYS_USER where USER_NAME=new.GAME_FOUNDER;
set new.GAME_LATITUDE=@latitude;
set new.GAME_LONGITUDE=@longitude;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `AUTO_INSERT_PARTICIPATOR`;
DELIMITER ;;
CREATE TRIGGER `AUTO_INSERT_PARTICIPATOR` AFTER INSERT ON `t_status_game` FOR EACH ROW begin
insert into T_STATUS_JOINED(GAME_ID,USER_NAME,JOIN_STATUS) values(new.GAME_ID,new.GAME_FOUNDER,'S008');
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `AUTO_UPDATE_PARTICIPATOR`;
DELIMITER ;;
CREATE TRIGGER `AUTO_UPDATE_PARTICIPATOR` AFTER UPDATE ON `t_status_game` FOR EACH ROW begin
if new.GAME_STATUS='S004' then
update T_STATUS_JOINED set JOIN_STATUS='S011' where GAME_ID=new.GAME_ID and JOIN_STATUS='S008';
end if;
if new.GAME_STATUS='S014' then
update T_STATUS_JOINED set JOIN_STATUS='S012' where GAME_ID=new.GAME_ID and JOIN_STATUS='S008';
end if;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `AUTO_INCREASE_ACHIEVEMENT_POINT_GAME`;
DELIMITER ;;
CREATE TRIGGER `AUTO_INCREASE_ACHIEVEMENT_POINT_GAME` AFTER UPDATE ON `t_status_joined` FOR EACH ROW begin
if new.JOIN_STATUS='S011' then
update T_SYS_USER set ACHIEVEMENT_POINT = ACHIEVEMENT_POINT + 10 where USER_NAME=new.USER_NAME;
end if;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `AUTO_INCREASE_ACHIEVEMENT_POINT_LIKE`;
DELIMITER ;;
