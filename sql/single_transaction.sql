CREATE DATABASE  IF NOT EXISTS `carget` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `carget`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: carget
-- ------------------------------------------------------
-- Server version	5.7.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brands` (
  `brand_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(25) NOT NULL,
  `brand_full_name` varchar(60) NOT NULL,
  PRIMARY KEY (`brand_id`),
  UNIQUE KEY `brands_brand_id_uindex` (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'ВАЗ','ВАЗ'),(2,'Ford','Ford'),(3,'BMW','BMW'),(4,'VW','Volkswagen'),(5,'Suzuki','Suzuki'),(6,'Daewoo','Daewoo'),(7,'Chevrolet','Chevrolet'),(8,'Skoda','Skoda'),(9,'Peugeot','Peugeot'),(10,'Hyundai','Hyundai'),(11,'Kia','Kia'),(12,'Toyota','Toyota'),(13,'Mitsubishi','Mitsubishi'),(14,'Mazda','Mazda'),(15,'Honda','Honda'),(16,'Audi','Audi'),(17,'Mercedes','Mercedes'),(18,'Lexus','Lexus'),(19,'Nissan','Nissan');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cars` (
  `car_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `model_id` int(11) unsigned NOT NULL,
  `price_day` decimal(10,0) NOT NULL DEFAULT '777',
  `fuel_type` int(1) NOT NULL DEFAULT '0',
  `year` int(11) NOT NULL DEFAULT '2008',
  PRIMARY KEY (`car_id`),
  UNIQUE KEY `cars_car_id_uindex` (`car_id`),
  KEY `cars_models_fk_idx` (`model_id`),
  CONSTRAINT `cars_models_fk` FOREIGN KEY (`model_id`) REFERENCES `models` (`model_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES (1,1,200,1,2008),(2,2,400,0,2016),(4,3,900,0,2016),(5,4,250,0,2010),(6,5,230,0,2012),(7,6,250,0,2014),(8,7,350,0,2014),(9,8,450,0,2012),(10,9,430,0,2014),(11,10,470,0,2015),(12,11,650,0,2013),(13,12,650,0,2016),(14,13,700,0,2015),(15,14,750,0,2013),(16,15,700,0,2013),(17,16,730,0,2015),(18,17,710,0,2014),(19,18,650,0,2011),(20,19,800,0,2016),(21,20,810,0,2016),(22,21,850,2,2015),(23,22,950,2,2014),(24,23,1000,2,2015),(25,24,750,0,2013),(26,25,850,0,2015),(27,26,650,0,2015),(28,27,760,0,2014),(29,28,850,0,2015),(30,29,1100,2,2013),(31,30,1500,0,2013);
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `models`
--

DROP TABLE IF EXISTS `models`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `models` (
  `model_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `brand_id` int(10) unsigned NOT NULL,
  `class_name` char(1) NOT NULL DEFAULT 'B',
  `model_name` varchar(45) NOT NULL,
  `doors_qty` int(10) unsigned NOT NULL DEFAULT '4',
  `automat` tinyint(4) NOT NULL DEFAULT '0',
  `power` double unsigned NOT NULL DEFAULT '100',
  `conditioning` tinyint(4) NOT NULL DEFAULT '0',
  `img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`model_id`),
  UNIQUE KEY `model_id_UNIQUE` (`model_id`),
  KEY `models_brands_fk_idx` (`brand_id`),
  CONSTRAINT `models_brands_fk` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`brand_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `models`
--

LOCK TABLES `models` WRITE;
/*!40000 ALTER TABLE `models` DISABLE KEYS */;
INSERT INTO `models` VALUES (1,1,'A','Kalina',4,0,1.6,0,'/res/cars/lada-kalina.png'),(2,2,'B','Focus',4,1,1.6,1,'/res/cars/ford-focus.png'),(3,3,'D','520',4,1,2,1,'/res/cars/BMW-520.png'),(4,5,'A','Swift',5,1,1.2,1,'/res/cars/suzuki-swift.png'),(5,6,'A','Lanos',4,0,1.5,0,'/res/cars/dewoo-lanos.png'),(6,7,'A','Aveo',4,0,1.6,1,'/res/cars/chevrolet-aveo.png'),(7,8,'A','Fabia',5,0,1.4,1,'/res/cars/skoda-fabia.png'),(8,9,'A','207',5,0,1.4,1,'/res/cars/peugeot-207.png'),(9,10,'A','Accent',4,0,1.6,1,'/res/cars/hyundai-accent.png'),(10,11,'A','Rio',4,1,1.4,1,'/res/cars/kia-rio.png'),(11,4,'B','Golf',5,1,1.4,1,'/res/cars/volkswagen-golf.png'),(12,4,'B','Polo',5,1,1.6,1,'/res/cars/volkswagen-polo.png'),(13,12,'B','Corolla',4,1,1.6,1,'/res/cars/toyota-corolla.png'),(14,13,'B','Lancer X',5,1,2,1,'/res/cars/mitsubishi-lancer-x.png'),(15,14,'B','3',4,1,1.6,1,'/res/cars/mazda-3.png'),(16,15,'B','Civic',4,1,1.8,1,'/res/cars/honda-civic.png'),(17,8,'B','Rapid',4,1,1.4,1,'/res/cars/skoda-rapid.png'),(18,12,'C','Camry',4,1,3.5,1,'/res/cars/toyota-camry.png'),(19,10,'C','Sonata',4,1,2,1,'/res/cars/hyundai-sonata.png'),(20,15,'C','Accord',4,1,2.4,1,'/res/cars/honda-accord.png'),(21,3,'D','520',4,1,2,1,'/res/cars/BMW-520.png'),(22,16,'D','A6',4,1,2,1,'/res/cars/audi-a6.png'),(23,17,'D','S500',4,1,5.5,1,'/res/cars/mercedes-s500.png'),(24,12,'E','RAV',5,1,2,1,'/res/cars/toyota-rav.png'),(25,18,'E','RX350',5,1,3.5,1,'/res/cars/lexus-rx350.png'),(26,10,'E','Tucson',5,1,2,1,'/res/cars/hyundai-tucson.png'),(27,19,'E','Qashqai',5,1,1.6,1,'/res/cars/nissan-qashqai.png'),(28,15,'E','CRV',5,1,2,1,'/res/cars/honda-crv.png'),(29,16,'E','Q7',5,1,3,1,'/res/cars/audi-q7.png'),(30,17,'E','G55',5,1,6.3,1,'/res/cars/mercedes-g55.png');
/*!40000 ALTER TABLE `models` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `order_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `car_id` int(11) unsigned NOT NULL,
  `date_start` date NOT NULL,
  `date_end` date NOT NULL,
  `comment` varchar(60) DEFAULT NULL,
  `rent` decimal(18,2) DEFAULT '0.00',
  `fine` decimal(18,2) DEFAULT '0.00',
  `status` int(1) NOT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`),
  KEY `order_car_fk_idx` (`car_id`),
  KEY `orders_users_fk` (`user_id`),
  CONSTRAINT `orders_cars_fk` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`) ON UPDATE CASCADE,
  CONSTRAINT `orders_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,25,1,'2016-08-13','2016-08-14','просьба клиента',200.00,0.00,3),(2,25,7,'2016-08-13','2016-08-16','все отлично',750.00,0.00,4),(3,25,10,'2016-08-14','2016-08-16','царапина',860.00,1000.00,4),(4,26,21,'2016-08-14','2016-09-15','по требованию',25920.00,0.00,3),(5,25,13,'2016-08-16','2016-08-19','',1950.00,0.00,6),(6,25,6,'2016-08-16','2016-08-21','',1150.00,0.00,3),(7,25,2,'2016-08-17','2016-08-18','',400.00,0.00,1),(8,26,13,'2016-08-17','2016-08-20','не оплатил в срок',1950.00,0.00,5),(9,26,31,'2016-08-17','2016-08-28','',16500.00,0.00,1),(10,25,10,'2016-08-17','2016-09-18','',13760.00,0.00,1),(11,27,21,'2016-08-17','2016-08-23','бампер',4860.00,1500.00,4),(12,27,22,'2016-08-17','2016-08-21','все хорошо',3400.00,0.00,4),(13,27,6,'2016-08-17','2016-08-25','',1840.00,0.00,6);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `password` varchar(30) NOT NULL,
  `passport` varchar(45) DEFAULT NULL,
  `email` varchar(60) NOT NULL,
  `is_admin` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `users_id_uindex` (`user_id`),
  UNIQUE KEY `users_email_uindex` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'Admin','Adminov','admin','CD123456','admin@mail.com',1),(25,'User1','Userov1','user1','UU000001','user1@mail.com',0),(26,'User2','Userov2','user2','UU000002','user2@mail.com',0),(27,'User3','Userov3','user3','UU000003','user3@mail.com',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-17 22:49:53
