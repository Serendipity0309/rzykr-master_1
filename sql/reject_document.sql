/*
 Navicat Premium Data Transfer

 Source Server         : ccj
 Source Server Type    : MySQL
 Source Server Version : 100902 (10.9.2-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : rzyk

 Target Server Type    : MySQL
 Target Server Version : 100902 (10.9.2-MariaDB)
 File Encoding         : 65001

 Date: 10/12/2023 22:58:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for reject_document
-- ----------------------------
DROP TABLE IF EXISTS `reject_document`;
CREATE TABLE `reject_document`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `document` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '驳回材料',
  `time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '驳回时间',
  `exam_log_id` bigint NULL DEFAULT NULL COMMENT '驳回日子id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reject_document
-- ----------------------------
INSERT INTO `reject_document` VALUES (1, '2;3;4;5;6;7', '2023-10-03 16:17:49', 45);
INSERT INTO `reject_document` VALUES (2, '1;2;4', '2023-10-04 16:17:30', 45);
INSERT INTO `reject_document` VALUES (3, '1;2;4', NULL, NULL);
INSERT INTO `reject_document` VALUES (4, '1;2;4', NULL, NULL);
INSERT INTO `reject_document` VALUES (5, '1;2;4', NULL, NULL);
INSERT INTO `reject_document` VALUES (6, '1;2;4', NULL, NULL);
INSERT INTO `reject_document` VALUES (7, '1;2;4', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
