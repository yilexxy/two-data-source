# two-data-source
Spring Boot多数据源、分布式事务实现

开发环境说明：
•	DataSource: Alibaba Druid

•	Database: MySQL 5.7

•	SpringBoot: 2.2.2.RELEASE

•	ORM: MyBatis

•	JTA: Atomikos

账户、定单两个数据源、分布式事务提交。

建表语句：
1.订单表
-- ----------------------------
--  Table structure for `order_info`
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `account_id` int(20) NOT NULL,
  `completed` int(5) NOT NULL,
  `order_state` int(5) NOT NULL,
  `detail` varchar(50) NOT NULL,
  `amount` decimal(20,2) NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;


2.账户表
-- ----------------------------
--  Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(30) NOT NULL,
  `balance` decimal(10,2) NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `account`
-- ----------------------------
BEGIN;
INSERT INTO `account` VALUES ('1', 'san', '李红', '2222222', '820.00', '2020-08-31 16:11:41', '2020-08-31 16:11:45');
COMMIT;
