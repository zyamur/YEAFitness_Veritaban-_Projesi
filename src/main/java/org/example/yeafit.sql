CREATE DATABASE  IF NOT EXISTS `yeafitness` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `yeafitness`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: yeafitness
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `egitmen`
--

DROP TABLE IF EXISTS `egitmen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `egitmen` (
  `egitmen_id` int NOT NULL,
  `isim` varchar(45) DEFAULT NULL,
  `calisma_saatleri` varchar(45) DEFAULT NULL,
  `saha_ismi_eg` varchar(45) DEFAULT NULL,
  `kontenjan_eg` int DEFAULT NULL,
  `anlik_doluluk_eg` int DEFAULT NULL,
  PRIMARY KEY (`egitmen_id`),
  KEY `saha_ismi_id` (`saha_ismi_eg`),
  CONSTRAINT `saha_ismi_cons` FOREIGN KEY (`saha_ismi_eg`) REFERENCES `spor_sahasi` (`saha_ismi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `egitmen`
--

LOCK TABLES `egitmen` WRITE;
/*!40000 ALTER TABLE `egitmen` DISABLE KEYS */;
INSERT INTO `egitmen` VALUES (0,'Yagmur','7-22','pilates',100,0),(1,'Murat','10-16','pilates',10,0),(2,'Ahmet','12-18','basketbol',10,1),(3,'Aylin','8-14','fitness',5,3),(4,'Yusuf','9-15','tenis',2,2),(5,'Acelya','7-14','voleybol',6,2),(6,'Ferit','7-15','yüzme',6,3);
/*!40000 ALTER TABLE `egitmen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `egitmen_brans`
--

DROP TABLE IF EXISTS `egitmen_brans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `egitmen_brans` (
  `egitmen_ID` int NOT NULL,
  `brans` varchar(45) NOT NULL,
  PRIMARY KEY (`egitmen_ID`,`brans`),
  CONSTRAINT `egitmen_id_cons` FOREIGN KEY (`egitmen_ID`) REFERENCES `egitmen` (`egitmen_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `egitmen_brans`
--

LOCK TABLES `egitmen_brans` WRITE;
/*!40000 ALTER TABLE `egitmen_brans` DISABLE KEYS */;
INSERT INTO `egitmen_brans` VALUES (1,'pilates'),(2,'basketbol'),(3,'fitness'),(4,'tenis'),(5,'voleybol'),(6,'yüzme');
/*!40000 ALTER TABLE `egitmen_brans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `egitmen_dersleri`
--

DROP TABLE IF EXISTS `egitmen_dersleri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `egitmen_dersleri` (
  `egitmen_id_ders` int DEFAULT NULL,
  `kullanici_id_ders` int NOT NULL,
  PRIMARY KEY (`kullanici_id_ders`),
  KEY `kullanici_id_idx` (`kullanici_id_ders`),
  KEY `egitmen_id` (`egitmen_id_ders`),
  CONSTRAINT `egitmen_id` FOREIGN KEY (`egitmen_id_ders`) REFERENCES `egitmen` (`egitmen_id`),
  CONSTRAINT `kullanici_id` FOREIGN KEY (`kullanici_id_ders`) REFERENCES `kullanici` (`kullanici_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `egitmen_dersleri`
--

LOCK TABLES `egitmen_dersleri` WRITE;
/*!40000 ALTER TABLE `egitmen_dersleri` DISABLE KEYS */;
INSERT INTO `egitmen_dersleri` VALUES (2,55),(3,66),(3,119),(4,3),(5,4),(5,50),(6,500),(6,571),(6,576);
/*!40000 ALTER TABLE `egitmen_dersleri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `egzersiz_programi`
--

DROP TABLE IF EXISTS `egzersiz_programi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `egzersiz_programi` (
  `kullanici_ID` int NOT NULL,
  `egzersiz_ismi` varchar(250) DEFAULT NULL,
  `zorluk_seviyesi` varchar(45) DEFAULT NULL,
  `ortalama_kalori_yakimi` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`kullanici_ID`),
  CONSTRAINT `Kullanici_IDcons` FOREIGN KEY (`kullanici_ID`) REFERENCES `kullanici` (`kullanici_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `egzersiz_programi`
--

LOCK TABLES `egzersiz_programi` WRITE;
/*!40000 ALTER TABLE `egzersiz_programi` DISABLE KEYS */;
INSERT INTO `egzersiz_programi` VALUES (1,'squat','orta',660),(55,' 4x12 thigh adductor  3x15 barbell hip thrust 4x12 squat 15 dakika 7km/h hızda koşu  10 dakika recumbent bisiklet ','orta',700),(67,' 30 dakika 5km/h hızda yürüyüş  15 dakika eliptik bisiklet ','kolay',600),(90,' 4x12 thigh adductor  3x15 barbell hip thrust 4x12 squat 15 dakika 7km/h hızda koşu  10 dakika recumbent bisiklet ','orta',700),(97,' 4x12 thigh adductor  3x15 barbell hip thrust 4x12 squat 15 dakika 7km/h hızda koşu  10 dakika recumbent bisiklet ','orta',700),(123,'','orta',700),(500,' 4x12 barbell bench press  4x12 wide grip latt pulldown  4x12 squat 15 dakika 7km/h hızda koşu  10 dakika eliptik bisiklet ','orta',900),(576,' 4x12 thigh adductor  3x15 barbell hip thrust 4x12 squat 15 dakika 7km/h hızda koşu  10 dakika recumbent bisiklet ','orta',700),(678,' 4x12 barbell bench press  4x12 wide grip latt pulldown  4x12 squat 15 dakika 7km/h hızda koşu  10 dakika eliptik bisiklet ','orta',900);
/*!40000 ALTER TABLE `egzersiz_programi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kullanici`
--

DROP TABLE IF EXISTS `kullanici`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kullanici` (
  `kullanici_id` int NOT NULL,
  `isim` varchar(45) DEFAULT NULL,
  `hedef` varchar(45) DEFAULT NULL,
  `boy` int DEFAULT NULL,
  `kilo` decimal(10,2) DEFAULT NULL,
  `bazal_metabolizma` decimal(10,2) DEFAULT NULL,
  `cinsiyet` varchar(45) DEFAULT NULL,
  `yas` int DEFAULT NULL,
  `telefon_numarasi` varchar(15) DEFAULT NULL,
  `egitmen_ID` int DEFAULT NULL,
  PRIMARY KEY (`kullanici_id`),
  UNIQUE KEY `kullanici_id` (`kullanici_id`),
  KEY `Eğitmen ID_idx` (`egitmen_ID`),
  CONSTRAINT `egitmen_IDcons` FOREIGN KEY (`egitmen_ID`) REFERENCES `egitmen` (`egitmen_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kullanici`
--

LOCK TABLES `kullanici` WRITE;
/*!40000 ALTER TABLE `kullanici` DISABLE KEYS */;
INSERT INTO `kullanici` VALUES (1,'Can','80',175,90.00,NULL,'erkek',19980215,'05350625898',NULL),(2,'Elif','55',165,70.00,NULL,'kiz',20031002,'05476830715',1),(3,'Arif',NULL,187,92.00,NULL,'erkek',19960402,'05968657213',4),(4,'Melek','65',167,82.00,NULL,'kiz',20030810,'05375525812',5),(5,'Ali',NULL,180,85.00,NULL,'erkek',20040124,'05342347895',NULL),(6,'Ada','65',168,75.00,NULL,'kız',20040214,'05270119916',6),(7,'ahmet','70',170,78.00,0.00,'erkek',19900523,'05551234567',3),(8,'ayşe','56',154,66.00,0.00,'kadın',19990912,'null',4),(50,'ayten','46.94881889763779',154,66.00,1129.79,'Kadın',34,'null',5),(53,'zara','58.72047244094488',167,78.00,1249.43,'Kadın',33,'5677890987',4),(55,'hanne','55.0984251968504',163,50.00,1028.48,'Kadın',23,'5432455678',5),(66,'selin',NULL,167,60.00,NULL,'Kadın',21,'',3),(67,'selim','83.14173228346455',189,99.00,1814.85,'Erkek',32,'567987364',4),(77,'avasdfasdf','70',170,78.00,0.00,'erkek',19900523,'05551234567',3),(90,'ayşe','46.94881889763779',154,66.00,1129.79,'Kadın',34,'null',1),(97,'elif','49.66535433070866',157,89.00,1359.08,'Kadın',32,'5467890353',4),(119,'Aylo',NULL,187,67.00,NULL,'Kadın',21,'',3),(120,'Zeyno',NULL,210,79.00,NULL,'Kadın',21,'286398163',0),(123,'ayşe','46.94881889763779',154,66.00,1129.79,'Kadın',34,'null',4),(124,'ayuk','48.75984251968503',156,34.00,866.03,'Kadın',25,'',0),(345,'ali','53.25984251968503',156,58.00,1303.53,'Erkek',24,'5678909874',4),(500,'emo','65.93700787401575',170,56.00,1303.77,'Erkek',20,'',6),(571,'mehmet','84.04724409448819',190,87.00,1595.82,'Erkek',40,'',6),(576,'yagmur','54.19291338582677',162,53.00,1071.18,'Kadın',20,'5678904345',6),(678,'veli','63.22047244094488',167,78.00,1585.84,'Erkek',23,'5477890398',4);
/*!40000 ALTER TABLE `kullanici` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `randevu_alir`
--

DROP TABLE IF EXISTS `randevu_alir`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `randevu_alir` (
  `kullanici_id_rand` int NOT NULL,
  `saha_ismi_rand` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`kullanici_id_rand`),
  CONSTRAINT `kullanici_id_rand_cons` FOREIGN KEY (`kullanici_id_rand`) REFERENCES `kullanici` (`kullanici_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `randevu_alir`
--

LOCK TABLES `randevu_alir` WRITE;
/*!40000 ALTER TABLE `randevu_alir` DISABLE KEYS */;
INSERT INTO `randevu_alir` VALUES (1,NULL),(2,'yüzme'),(3,'tenis'),(4,'voleybol'),(5,'yüzme'),(6,'yüzme'),(90,'yüzme'),(124,'yüzme'),(576,'pilates');
/*!40000 ALTER TABLE `randevu_alir` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spor_sahasi`
--

DROP TABLE IF EXISTS `spor_sahasi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spor_sahasi` (
  `saha_ismi` varchar(45) NOT NULL,
  `kontenjan` int DEFAULT NULL,
  `hizmet_saatleri` varchar(45) DEFAULT NULL,
  `anlik_doluluk` int DEFAULT NULL,
  PRIMARY KEY (`saha_ismi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spor_sahasi`
--

LOCK TABLES `spor_sahasi` WRITE;
/*!40000 ALTER TABLE `spor_sahasi` DISABLE KEYS */;
INSERT INTO `spor_sahasi` VALUES ('basketbol',10,'9-21',2),('fitness',80,'9-21',2),('pilates',30,'16-21',3),('tenis',6,'9-21',2),('voleybol',12,'9-21',2),('yüzme',16,'10-19',16);
/*!40000 ALTER TABLE `spor_sahasi` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-09 22:28:09
