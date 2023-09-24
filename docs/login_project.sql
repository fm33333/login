/*
 Navicat Premium Data Transfer

 Source Server         : mysql 8.0
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : login_project

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 18/09/2023 14:11:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'abcde', 'e10adc3949ba59abbe56e057f20f883e', '1487792164@qq.com', '2023-09-18 13:55:36', '2023-09-18 14:11:03', 0);
INSERT INTO `user` VALUES (2, 'fming', '827ccb0eea8a706c4c34a16891f84e7b', NULL, '2023-09-18 13:55:36', '2023-09-18 13:55:36', 0);
INSERT INTO `user` VALUES (3, 'test', 'e10adc3949ba59abbe56e057f20f883e', '1487792164@qq.com', '2023-09-18 13:55:36', '2023-09-18 14:08:47', 0);

SET FOREIGN_KEY_CHECKS = 1;
