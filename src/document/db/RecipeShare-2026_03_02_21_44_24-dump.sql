-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 8.163.57.20    Database: recipe_share
-- ------------------------------------------------------
-- Server version	9.6.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '3390d27c-fa09-11f0-9844-02420aff0002:1-101';

--
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient` (
  `ing_id` bigint NOT NULL AUTO_INCREMENT COMMENT '食材ID',
  `ing_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '食材名称',
  `type` int NOT NULL DEFAULT '3' COMMENT '食材类型（荤1、素2、其他3）',
  `calorie` int NOT NULL DEFAULT '0' COMMENT '卡路里',
  PRIMARY KEY (`ing_id`),
  UNIQUE KEY `ingredient_pk` (`ing_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='食材';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;
/*!40000 ALTER TABLE `ingredient` DISABLE KEYS */;
INSERT INTO `ingredient` VALUES (1,'排骨',1,150),(2,'红烧汁',3,200);
/*!40000 ALTER TABLE `ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe` (
  `rec_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜谱ID',
  `u_id` bigint NOT NULL COMMENT '用户ID',
  `rec_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜谱名称',
  `rec_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '文案',
  `create_time` datetime NOT NULL COMMENT '菜谱创建时间',
  `update_time` datetime NOT NULL COMMENT ' 菜谱修改时间',
  PRIMARY KEY (`rec_id`),
  UNIQUE KEY `recipes_pk_2` (`rec_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜谱';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe`
--

LOCK TABLES `recipe` WRITE;
/*!40000 ALTER TABLE `recipe` DISABLE KEYS */;
INSERT INTO `recipe` VALUES (2,1,'红烧排骨','将排骨加入红烧汁中','2025-12-18 19:30:19','2025-12-18 19:30:21'),(3,1,'可乐鸡翅','将鸡翅加入可乐中','2025-12-18 19:31:53','2025-12-18 19:31:55');
/*!40000 ALTER TABLE `recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe_ingredient`
--

DROP TABLE IF EXISTS `recipe_ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe_ingredient` (
  `rec_ing_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜谱-原料中间表ID',
  `rec_id` bigint NOT NULL COMMENT '菜谱ID',
  `ing_id` bigint NOT NULL COMMENT '原料ID',
  `ing_number` int NOT NULL DEFAULT '1' COMMENT '原料数量',
  `ing_unit` int NOT NULL DEFAULT '2' COMMENT '数量单位（个1、克2）',
  `ing_type` int NOT NULL DEFAULT '1' COMMENT '食材关键与否（关键1、可选2、辅料3）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更改时间',
  PRIMARY KEY (`rec_ing_id`),
  UNIQUE KEY `recipes_ingredients_pk_2` (`rec_ing_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜谱-原料';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe_ingredient`
--

LOCK TABLES `recipe_ingredient` WRITE;
/*!40000 ALTER TABLE `recipe_ingredient` DISABLE KEYS */;
INSERT INTO `recipe_ingredient` VALUES (1,2,1,500,2,1,'2025-12-18 20:15:34','2025-12-18 20:15:36'),(2,2,2,300,2,1,'2025-12-18 20:16:08','2025-12-18 20:16:09');
/*!40000 ALTER TABLE `recipe_ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `u_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `u_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `u_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户头像路径',
  `u_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `u_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `create_time` datetime NOT NULL COMMENT '用户创建时间',
  `update_time` datetime NOT NULL COMMENT '用户更改时间',
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `user_pk` (`u_account`),
  UNIQUE KEY `user_pk_2` (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','/picture/admin.jpg','admin0412','123456','2025-12-18 19:50:34','2025-12-18 19:50:37');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_recipe_like`
--

DROP TABLE IF EXISTS `user_recipe_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_recipe_like` (
  `url_id` bigint NOT NULL AUTO_INCREMENT COMMENT '中间表ID',
  `u_id` bigint NOT NULL COMMENT '用户ID',
  `rec_id` bigint NOT NULL COMMENT '菜谱ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`url_id`),
  UNIQUE KEY `users_recipes_like_pk_2` (`url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户菜谱收藏';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_recipe_like`
--

LOCK TABLES `user_recipe_like` WRITE;
/*!40000 ALTER TABLE `user_recipe_like` DISABLE KEYS */;
INSERT INTO `user_recipe_like` VALUES (1,1,2,'2025-12-18 20:45:24','2025-12-18 20:45:26');
/*!40000 ALTER TABLE `user_recipe_like` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-02 21:44:24
