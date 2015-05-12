/*
SQLyog Ultimate v8.32 
MySQL - 5.5.28 : Database - wm
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`weixinTest` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `weixinTest`;

/*Table structure for table `weixin` */

DROP TABLE IF EXISTS `weixin`;

CREATE TABLE `weixin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT 'name',
  `value` longtext COMMENT 'value',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `weixin` */

insert  into `weixin`(`id`,`name`,`value`) values (1,'appid',''),(2,'appsecret',''),(3,'TOKEN',''),(4,'token_url','https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET'),(5,'user_url','https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN'),(6,'menu_url','https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN'),(7,'access_token','x1BYUYH67Ske8BFI8b9sDju8HER4lklXXjnHYSfizGJFqFK7uNJg5ZTt0TFniDbgSWOTRdpXeGb5ycrWhLdIww'),(8,'access_token_Time','1410516266341'),(9,'menu_urlDel','https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN'),(10,'menu_urlGet','https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
