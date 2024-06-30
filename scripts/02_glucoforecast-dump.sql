-- MySQL dump 10.13  Distrib 5.7.24, for osx11.1 (x86_64)
--
-- Host: 127.0.0.1    Database: glucoforecast
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `cuidadores`
--

DROP TABLE IF EXISTS `cuidadores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuidadores` (
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_cuidador_usuario` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuidadores`
--

LOCK TABLES `cuidadores` WRITE;
/*!40000 ALTER TABLE `cuidadores` DISABLE KEYS */;
INSERT INTO `cuidadores` (`id`) VALUES (7),(8);
/*!40000 ALTER TABLE `cuidadores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estimaciones_glicosiladas`
--

DROP TABLE IF EXISTS `estimaciones_glicosiladas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estimaciones_glicosiladas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `paciente_id` bigint NOT NULL,
  `valor_calculado` double NOT NULL,
  `fecha_calculo` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_estimacion_paciente` (`paciente_id`),
  CONSTRAINT `fk_estimacion_paciente` FOREIGN KEY (`paciente_id`) REFERENCES `pacientes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estimaciones_glicosiladas`
--

LOCK TABLES `estimaciones_glicosiladas` WRITE;
/*!40000 ALTER TABLE `estimaciones_glicosiladas` DISABLE KEYS */;
INSERT INTO `estimaciones_glicosiladas` (`id`, `paciente_id`, `valor_calculado`, `fecha_calculo`) VALUES (1,1,6.5,'2024-04-01 00:00:00'),(2,1,7.1,'2024-05-01 00:00:00'),(3,1,6.8,'2024-05-20 00:00:00'),(4,2,7,'2024-04-01 00:00:00'),(5,2,6.9,'2024-05-01 00:00:00'),(6,2,7.2,'2024-05-20 00:00:00'),(7,3,6.8,'2024-04-01 00:00:00'),(8,3,7.4,'2024-05-01 00:00:00'),(9,3,7,'2024-05-20 00:00:00'),(10,4,6.9,'2024-04-01 00:00:00'),(11,4,7,'2024-05-01 00:00:00'),(12,4,6.7,'2024-05-20 00:00:00'),(13,5,7.1,'2024-04-01 00:00:00'),(14,5,7.3,'2024-05-01 00:00:00'),(15,5,7,'2024-05-20 00:00:00'),(16,1,5.779326364692219,'2024-06-30 00:00:00'),(17,1,6.430562468889996,'2024-06-30 00:00:00'),(18,1,6.352787456445993,'2024-06-30 00:00:00'),(19,1,5.349065568577763,'2024-06-30 00:00:00'),(20,1,5.44541231126597,'2024-06-30 00:00:00'),(21,1,5.634146341463414,'2024-06-30 00:00:00'),(22,1,5.634146341463414,'2024-06-30 00:00:00'),(23,1,5.646590343454456,'2024-06-30 00:00:00'),(24,1,5.646590343454456,'2024-06-30 00:00:00'),(25,1,5.657375145180024,'2024-06-30 00:00:00'),(26,1,5.15989159891599,'2024-06-30 00:00:00'),(27,1,5.230698697964423,'2024-06-30 00:00:00'),(28,1,5.36411149825784,'2024-06-30 00:00:00'),(29,1,5.36411149825784,'2024-06-30 00:00:00'),(30,1,5.385266301642609,'2024-06-30 00:00:00'),(31,1,5.385266301642609,'2024-06-30 00:00:00'),(32,1,5.404497941083307,'2024-06-30 00:00:00'),(33,1,5.076655052264808,'2024-06-30 00:00:00'),(34,1,5.131600107209864,'2024-06-30 00:00:00'),(35,1,5.2340947218996,'2024-06-30 00:00:00'),(36,1,5.2340947218996,'2024-06-30 00:00:00'),(37,1,5.254604280736686,'2024-06-30 00:00:00'),(38,1,5.254604280736686,'2024-06-30 00:00:00'),(39,2,6.505226480836237,'2024-06-30 00:00:00');
/*!40000 ALTER TABLE `estimaciones_glicosiladas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mediciones`
--

DROP TABLE IF EXISTS `mediciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mediciones` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `paciente_id` bigint NOT NULL,
  `fecha_hora` datetime NOT NULL,
  `glucemia` double NOT NULL,
  `carbohidratos` double DEFAULT NULL,
  `insulina_comida` double DEFAULT NULL,
  `insulina_correccion` double DEFAULT NULL,
  `descripcion_comida` varchar(255) DEFAULT NULL,
  `insulina_lenta` double DEFAULT NULL,
  `notas` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_medicion_paciente` (`paciente_id`),
  CONSTRAINT `fk_medicion_paciente` FOREIGN KEY (`paciente_id`) REFERENCES `pacientes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mediciones`
--

LOCK TABLES `mediciones` WRITE;
/*!40000 ALTER TABLE `mediciones` DISABLE KEYS */;
INSERT INTO `mediciones` (`id`, `paciente_id`, `fecha_hora`, `glucemia`, `carbohidratos`, `insulina_comida`, `insulina_correccion`, `descripcion_comida`, `insulina_lenta`, `notas`, `tags`) VALUES (1,1,'2024-04-01 08:00:00',85,30,2,1,'Desayuno',0,'Normal','DESAYUNO'),(2,1,'2024-04-02 12:00:00',150,50,4,2,'Almuerzo',0,'Normal','ALMUERZO'),(3,1,'2024-04-03 18:00:00',190,60,5,2,'Cena',0,'Hiperglucemia','CENA'),(4,1,'2024-04-04 22:00:00',70,20,1,0,'Merienda',0,'Normal','MERIENDA'),(5,1,'2024-04-05 23:00:00',60,10,0,0,'Snack',0,'Hipoglucemia','ANTES_DE_COMER'),(6,2,'2024-04-01 08:00:00',110,20,2,1,'Desayuno',0,'Normal','DESAYUNO'),(7,2,'2024-04-02 12:00:00',175,55,4,2,'Almuerzo',0,'Normal','ALMUERZO'),(8,2,'2024-04-03 18:00:00',200,70,6,3,'Cena',0,'Hiperglucemia','CENA'),(9,2,'2024-04-04 22:00:00',80,30,3,1,'Merienda',0,'Normal','MERIENDA'),(10,2,'2024-04-05 23:00:00',65,15,0,0,'Snack',0,'Hipoglucemia','ANTES_DE_COMER'),(11,3,'2024-04-01 08:00:00',130,25,3,1,'Desayuno',0,'Normal','DESAYUNO'),(12,3,'2024-04-02 12:00:00',160,45,4,2,'Almuerzo',0,'Normal','ALMUERZO'),(13,3,'2024-04-03 18:00:00',185,65,5,2,'Cena',0,'Hiperglucemia','CENA'),(14,3,'2024-04-04 22:00:00',90,20,2,1,'Merienda',0,'Normal','MERIENDA'),(15,3,'2024-04-05 23:00:00',50,10,0,0,'Snack',0,'Hipoglucemia','ANTES_DE_COMER'),(16,4,'2024-04-01 08:00:00',120,30,3,1,'Desayuno',0,'Normal','DESAYUNO'),(17,4,'2024-04-02 12:00:00',140,50,4,2,'Almuerzo',0,'Normal','ALMUERZO'),(18,4,'2024-04-03 18:00:00',195,60,5,2,'Cena',0,'Hiperglucemia','CENA'),(19,4,'2024-04-04 22:00:00',75,20,1,0,'Merienda',0,'Normal','MERIENDA'),(20,4,'2024-04-05 23:00:00',55,10,0,0,'Snack',0,'Hipoglucemia','ANTES_DE_COMER'),(21,5,'2024-04-01 08:00:00',100,20,2,1,'Desayuno',0,'Normal','DESAYUNO'),(22,5,'2024-04-02 12:00:00',170,40,4,2,'Almuerzo',0,'Normal','ALMUERZO'),(23,5,'2024-04-03 18:00:00',185,55,5,2,'Cena',0,'Hiperglucemia','CENA'),(24,5,'2024-04-04 22:00:00',90,25,2,1,'Merienda',0,'Normal','MERIENDA'),(25,5,'2024-04-05 23:00:00',65,15,0,0,'Snack',0,'Hipoglucemia','ANTES_DE_COMER'),(26,1,'2024-06-30 18:37:28',160,0,0,0,'',0,'cena','a'),(27,1,'2024-06-30 18:37:49',250,0,0,0,'',0,'almuerzo','a'),(28,1,'2024-06-30 18:39:22',120,0,0,0,'',0,'Desayuno','DESAYUNO'),(29,1,'2024-06-30 18:39:22',0,50,0,0,'Almuerzo',0,'',''),(30,1,'2024-06-30 18:39:23',0,0,4,2,'',1,'',''),(31,1,'2024-06-30 18:39:23',90,0,0,0,'',0,'Desayuno','DESAYUNO'),(32,1,'2024-06-30 18:39:23',140,0,0,0,'',0,'Almuerzo','ALMUERZO'),(33,1,'2024-06-30 18:39:23',180,0,0,0,'',0,'Cena','CENA'),(34,1,'2024-06-30 18:39:23',120,0,0,0,'',0,'Desayuno','DESAYUNO'),(35,1,'2024-06-30 18:39:32',120,0,0,0,'',0,'Desayuno','DESAYUNO'),(36,1,'2024-06-30 18:39:32',0,50,0,0,'Almuerzo',0,'',''),(37,1,'2024-06-30 18:39:32',0,0,4,2,'',1,'',''),(38,1,'2024-06-30 18:39:32',90,0,0,0,'',0,'Desayuno','DESAYUNO'),(39,1,'2024-06-30 18:39:32',140,0,0,0,'',0,'Almuerzo','ALMUERZO'),(40,1,'2024-06-30 18:39:32',180,0,0,0,'',0,'Cena','CENA'),(41,1,'2024-06-30 18:39:32',120,0,0,0,'',0,'Desayuno','DESAYUNO'),(42,1,'2024-06-30 18:39:38',120,0,0,0,'',0,'Desayuno','DESAYUNO'),(43,1,'2024-06-30 18:39:38',0,50,0,0,'Almuerzo',0,'',''),(44,1,'2024-06-30 18:39:38',0,0,4,2,'',1,'',''),(45,1,'2024-06-30 18:39:38',90,0,0,0,'',0,'Desayuno','DESAYUNO'),(46,1,'2024-06-30 18:39:38',140,0,0,0,'',0,'Almuerzo','ALMUERZO'),(47,1,'2024-06-30 18:39:38',180,0,0,0,'',0,'Cena','CENA'),(48,1,'2024-06-30 18:39:38',120,0,0,0,'',0,'Desayuno','DESAYUNO'),(49,2,'2024-06-30 18:40:41',0,45,0,0,'pan',0,'',''),(50,2,'2024-06-30 18:41:11',350,0,0,0,'',0,'merienda','');
/*!40000 ALTER TABLE `mediciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicos`
--

DROP TABLE IF EXISTS `medicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicos` (
  `id` bigint NOT NULL,
  `institucion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_medico_usuario` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicos`
--

LOCK TABLES `medicos` WRITE;
/*!40000 ALTER TABLE `medicos` DISABLE KEYS */;
INSERT INTO `medicos` (`id`, `institucion`) VALUES (6,'Hospital General');
/*!40000 ALTER TABLE `medicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente_cuidador`
--

DROP TABLE IF EXISTS `paciente_cuidador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paciente_cuidador` (
  `paciente_id` bigint NOT NULL,
  `cuidador_id` bigint NOT NULL,
  PRIMARY KEY (`paciente_id`,`cuidador_id`),
  KEY `fk_cuidador` (`cuidador_id`),
  CONSTRAINT `fk_cuidador` FOREIGN KEY (`cuidador_id`) REFERENCES `cuidadores` (`id`),
  CONSTRAINT `fk_paciente` FOREIGN KEY (`paciente_id`) REFERENCES `pacientes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente_cuidador`
--

LOCK TABLES `paciente_cuidador` WRITE;
/*!40000 ALTER TABLE `paciente_cuidador` DISABLE KEYS */;
INSERT INTO `paciente_cuidador` (`paciente_id`, `cuidador_id`) VALUES (1,7),(3,7),(5,7),(2,8),(4,8);
/*!40000 ALTER TABLE `paciente_cuidador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente_medico`
--

DROP TABLE IF EXISTS `paciente_medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paciente_medico` (
  `paciente_id` bigint NOT NULL,
  `medico_id` bigint NOT NULL,
  PRIMARY KEY (`paciente_id`,`medico_id`),
  KEY `medico_id` (`medico_id`),
  CONSTRAINT `paciente_medico_ibfk_1` FOREIGN KEY (`paciente_id`) REFERENCES `pacientes` (`id`),
  CONSTRAINT `paciente_medico_ibfk_2` FOREIGN KEY (`medico_id`) REFERENCES `medicos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente_medico`
--

LOCK TABLES `paciente_medico` WRITE;
/*!40000 ALTER TABLE `paciente_medico` DISABLE KEYS */;
INSERT INTO `paciente_medico` (`paciente_id`, `medico_id`) VALUES (1,6);
/*!40000 ALTER TABLE `paciente_medico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pacientes`
--

DROP TABLE IF EXISTS `pacientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pacientes` (
  `id` bigint NOT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `peso_corporal` double DEFAULT NULL,
  `fecha_diagnostico` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_paciente_usuario` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pacientes`
--

LOCK TABLES `pacientes` WRITE;
/*!40000 ALTER TABLE `pacientes` DISABLE KEYS */;
INSERT INTO `pacientes` (`id`, `fecha_nacimiento`, `peso_corporal`, `fecha_diagnostico`) VALUES (1,'2011-06-24',30.5,'2020-01-15'),(2,'2007-02-14',40,'2015-03-20'),(3,'2012-09-02',35,'2018-05-25'),(4,'2009-01-31',42.5,'2017-07-10'),(5,'2010-05-24',38,'2019-09-15');
/*!40000 ALTER TABLE `pacientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportes`
--

DROP TABLE IF EXISTS `reportes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reportes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `paciente_id` bigint NOT NULL,
  `tipo_reporte` enum('TENDENCIAS_GLUCEMIA','PATRONES_GLUCEMIA','RANGOS_GLUCEMIA','PROMEDIOS_GLUCEMIA','HB1AC_HISTORICO','DOSIFICACION_INSULINA','CANTIDAD_CARBOHIDRATOS','NOTAS_OBSERVACIONES') NOT NULL,
  `datos` blob,
  `fecha_generacion` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reporte_paciente` (`paciente_id`),
  CONSTRAINT `fk_reporte_paciente` FOREIGN KEY (`paciente_id`) REFERENCES `pacientes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reportes`
--

LOCK TABLES `reportes` WRITE;
/*!40000 ALTER TABLE `reportes` DISABLE KEYS */;
INSERT INTO `reportes` (`id`, `paciente_id`, `tipo_reporte`, `datos`, `fecha_generacion`) VALUES (1,1,'TENDENCIAS_GLUCEMIA',_binary '[{\"tendencia\": {\"fecha\": \"2024-05-01\", \"valor\": 140}}]','2024-05-20 00:00:00'),(2,1,'TENDENCIAS_GLUCEMIA',_binary '[{\"tendencia\": {\"fecha\": \"2024-05-15\", \"valor\": 150}}]','2024-05-20 00:00:00'),(3,2,'PATRONES_GLUCEMIA',_binary '[{\"patron\": {\"fecha\": \"2024-05-01\", \"valor\": 200}}]','2024-05-20 00:00:00'),(4,2,'PATRONES_GLUCEMIA',_binary '[{\"patron\": {\"fecha\": \"2024-05-15\", \"valor\": 180}}]','2024-05-20 00:00:00'),(5,3,'RANGOS_GLUCEMIA',_binary '[{\"rango\": {\"fecha\": \"2024-05-01\", \"valor\": \"70-180\"}}]','2024-05-20 00:00:00'),(6,3,'RANGOS_GLUCEMIA',_binary '[{\"rango\": {\"fecha\": \"2024-05-15\", \"valor\": \"50-200\"}}]','2024-05-20 00:00:00'),(9,5,'HB1AC_HISTORICO',_binary '[{\"hb1ac\": {\"fecha\": \"2024-04-01\", \"valor\": 6.5}}]','2024-05-20 00:00:00'),(10,5,'HB1AC_HISTORICO',_binary '[{\"hb1ac\": {\"fecha\": \"2024-05-01\", \"valor\": 7.0}}]','2024-05-20 00:00:00'),(11,1,'DOSIFICACION_INSULINA',_binary '[{\"dosis\": {\"fecha\": \"2024-05-01\", \"valor\": 8}}]','2024-05-20 00:00:00'),(12,1,'DOSIFICACION_INSULINA',_binary '[{\"dosis\": {\"fecha\": \"2024-05-15\", \"valor\": 10}}]','2024-05-20 00:00:00'),(13,2,'CANTIDAD_CARBOHIDRATOS',_binary '[{\"carbohidratos\": {\"fecha\": \"2024-05-01\", \"valor\": 50}}]','2024-05-20 00:00:00'),(14,2,'CANTIDAD_CARBOHIDRATOS',_binary '[{\"carbohidratos\": {\"fecha\": \"2024-05-15\", \"valor\": 60}}]','2024-05-20 00:00:00'),(15,3,'NOTAS_OBSERVACIONES',_binary '[{\"nota\": {\"fecha\": \"2024-05-01\", \"contenido\": \"Sensación de fatiga\"}}]','2024-05-20 00:00:00'),(16,3,'NOTAS_OBSERVACIONES',_binary '[{\"nota\": {\"fecha\": \"2024-05-15\", \"contenido\": \"Mejor ánimo y energía\"}}]','2024-05-20 00:00:00'),(17,5,'HB1AC_HISTORICO',_binary '[{\"hb1ac\": {\"fecha\": \"2024-04-01\", \"valor\": 6.5}}]','2024-05-20 00:00:00'),(18,5,'HB1AC_HISTORICO',_binary '[{\"hb1ac\": {\"fecha\": \"2024-05-01\", \"valor\": 7.0}}]','2024-05-20 00:00:00'),(19,5,'HB1AC_HISTORICO',_binary '[{\"hb1ac\": {\"fecha\": \"2024-04-01\", \"valor\": 6.5}}]','2024-05-20 00:00:00'),(20,5,'HB1AC_HISTORICO',_binary '[{\"hb1ac\": {\"fecha\": \"2024-05-01\", \"valor\": 7.0}}]','2024-05-20 00:00:00'),(21,1,'HB1AC_HISTORICO',_binary '[{\"fecha\": \"2024-04-01\", \"valor\": 6.50},{\"fecha\": \"2024-05-01\", \"valor\": 7.10},{\"fecha\": \"2024-05-20\", \"valor\": 6.80},{\"fecha\": \"2024-06-30\", \"valor\": 5.78},{\"fecha\": \"2024-06-30\", \"valor\": 6.43}]','2024-06-30 00:00:00'),(22,1,'PROMEDIOS_GLUCEMIA',_binary '[{\"hora\": 18, \"promedio\": 200.00},{\"hora\": 22, \"promedio\": 70.00},{\"hora\": 23, \"promedio\": 60.00},{\"hora\": 8, \"promedio\": 85.00},{\"hora\": 12, \"promedio\": 150.00}]','2024-06-30 00:00:00'),(23,1,'RANGOS_GLUCEMIA',_binary '{\"hipoglucemias\": 14.29, \"enRango\": 28.57, \"alto\": 14.29, \"hiperglucemias\": 28.57}','2024-06-30 00:00:00'),(24,5,'HB1AC_HISTORICO',_binary '[{\"fecha\": \"2024-04-01\", \"valor\": 7.10},{\"fecha\": \"2024-05-01\", \"valor\": 7.30},{\"fecha\": \"2024-05-20\", \"valor\": 7.00}]','2024-06-30 00:00:00'),(25,2,'PROMEDIOS_GLUCEMIA',_binary '[{\"hora\": 18, \"promedio\": 100.00},{\"hora\": 22, \"promedio\": 80.00},{\"hora\": 23, \"promedio\": 65.00},{\"hora\": 8, \"promedio\": 110.00},{\"hora\": 12, \"promedio\": 175.00}]','2024-06-30 00:00:00'),(26,2,'HB1AC_HISTORICO',_binary '[{\"fecha\": \"2024-04-01\", \"valor\": 7.00},{\"fecha\": \"2024-05-01\", \"valor\": 6.90},{\"fecha\": \"2024-05-20\", \"valor\": 7.20},{\"fecha\": \"2024-06-30\", \"valor\": 6.51}]','2024-06-30 00:00:00');
/*!40000 ALTER TABLE `reportes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `tipo` enum('PACIENTE','CUIDADOR','MEDICO') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`id`, `nombre`, `apellido`, `email`, `telefono`, `tipo`) VALUES (1,'Lionel','Messi','lionel.messi@gmail.com','123456789','PACIENTE'),(2,'Angel','Di Maria','angel.dimaria@gmail.com','123456789','PACIENTE'),(3,'Emiliano','Martinez','emiliano.martinez@gmail.com','123456789','PACIENTE'),(4,'Julian','Alvarez','julian.alvarez@gmail.com','123456789','PACIENTE'),(5,'Rodrigo','De Paul','rodrigo.depaul@gmail.com','123456789','PACIENTE'),(6,'Maria','Perez','maria.perez@hotmail.com','123456789','MEDICO'),(7,'Jose','Lopez','jose.lopez@gmail.com','123456789','CUIDADOR'),(8,'Laura','Gonzalez','laura.gonzalez@hotmail.com','123456789','CUIDADOR');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-30 18:51:14
