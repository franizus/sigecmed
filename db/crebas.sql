DROP TABLE IF EXISTS `cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cita` (
  `ID_CITA` int(11) NOT NULL AUTO_INCREMENT,
  `ID_DOCTOR` int(11) DEFAULT NULL,
  `ID_PACIENTE` int(11) DEFAULT NULL,
  `FECHA` date NOT NULL,
  `HORA_INICIO` time NOT NULL,
  `HORA_FIN` time NOT NULL,
  `COMENTARIO` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_CITA`),
  KEY `FK_RELATIONSHIP_4` (`ID_DOCTOR`),
  KEY `FK_RELATIONSHIP_5` (`ID_PACIENTE`),
  CONSTRAINT `FK_RELATIONSHIP_4` FOREIGN KEY (`ID_DOCTOR`) REFERENCES `doctor` (`id_doctor`),
  CONSTRAINT `FK_RELATIONSHIP_5` FOREIGN KEY (`ID_PACIENTE`) REFERENCES `paciente` (`id_paciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita`
--

LOCK TABLES `cita` WRITE;
/*!40000 ALTER TABLE `cita` DISABLE KEYS */;
/*!40000 ALTER TABLE `cita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `doctor` (
  `ID_DOCTOR` int(11) NOT NULL AUTO_INCREMENT,
  `ID_ESPECIALIDAD` int(11) NOT NULL,
  `NOMBRE_SEC` varchar(40) COLLATE utf8_bin NOT NULL,
  `CEDULA_SEC` char(10) COLLATE utf8_bin NOT NULL,
  `DIRECCION_SEC` varchar(100) COLLATE utf8_bin NOT NULL,
  `TELEFONO_SEC` varchar(12) COLLATE utf8_bin NOT NULL,
  `CORREO_SEC` varchar(30) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID_DOCTOR`),
  KEY `FK_TIENE` (`ID_ESPECIALIDAD`),
  CONSTRAINT `FK_TIENE` FOREIGN KEY (`ID_ESPECIALIDAD`) REFERENCES `especialidad` (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,1,'Diego Serrano','1709778441','La Granja','0978982158','diego@gmail.com'),(2,1,'Francisco','1723126122','Carvajal','0978897812','francisco@gmail.com');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `especialidad`
--

DROP TABLE IF EXISTS `especialidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `especialidad` (
  `ID_ESPECIALIDAD` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID_ESPECIALIDAD`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `especialidad`
--

LOCK TABLES `especialidad` WRITE;
/*!40000 ALTER TABLE `especialidad` DISABLE KEYS */;
INSERT INTO `especialidad` VALUES (1,'Dentista'),(2,'Medico General');
/*!40000 ALTER TABLE `especialidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horario`
--

DROP TABLE IF EXISTS `horario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `horario` (
  `ID_HORIARIO` int(11) NOT NULL AUTO_INCREMENT,
  `ID_DOCTOR` int(11) DEFAULT NULL,
  `INTERVALO_DIAS` char(5) COLLATE utf8_bin NOT NULL,
  `HORA_INICIO` time NOT NULL,
  `HORA_FIN` time NOT NULL,
  `FECHA_INICIO` date NOT NULL,
  `FECHA_FIN` date NOT NULL,
  PRIMARY KEY (`ID_HORIARIO`),
  KEY `FK_POSEE` (`ID_DOCTOR`),
  CONSTRAINT `FK_POSEE` FOREIGN KEY (`ID_DOCTOR`) REFERENCES `doctor` (`id_doctor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horario`
--

LOCK TABLES `horario` WRITE;
/*!40000 ALTER TABLE `horario` DISABLE KEYS */;
/*!40000 ALTER TABLE `horario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente`
--

DROP TABLE IF EXISTS `paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `paciente` (
  `ID_PACIENTE` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_SEC` varchar(40) COLLATE utf8_bin NOT NULL,
  `CEDULA_SEC` char(10) COLLATE utf8_bin NOT NULL,
  `DIRECCION_SEC` varchar(100) COLLATE utf8_bin NOT NULL,
  `TELEFONO_SEC` varchar(12) COLLATE utf8_bin NOT NULL,
  `CORREO_SEC` varchar(30) COLLATE utf8_bin NOT NULL,
  `TRATAMIENTO` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID_PACIENTE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente`
--

LOCK TABLES `paciente` WRITE;
/*!40000 ALTER TABLE `paciente` DISABLE KEYS */;
/*!40000 ALTER TABLE `paciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rol` (
  `ID_ROL` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_ROL` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID_ROL`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'Administrador'),(2,'Doctor'),(3,'Secretaria');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `secretaria`
--

DROP TABLE IF EXISTS `secretaria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `secretaria` (
  `ID_SECRETARIA` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_SEC` varchar(40) COLLATE utf8_bin NOT NULL,
  `CEDULA_SEC` char(10) COLLATE utf8_bin NOT NULL,
  `DIRECCION_SEC` varchar(100) COLLATE utf8_bin NOT NULL,
  `TELEFONO_SEC` varchar(12) COLLATE utf8_bin NOT NULL,
  `CORREO_SEC` varchar(30) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID_SECRETARIA`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secretaria`
--

LOCK TABLES `secretaria` WRITE;
/*!40000 ALTER TABLE `secretaria` DISABLE KEYS */;
INSERT INTO `secretaria` VALUES (1,'Maria','1544877852','El Condado','123213478','maria@gmai.com');
/*!40000 ALTER TABLE `secretaria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tratamiento`
--

DROP TABLE IF EXISTS `tratamiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tratamiento` (
  `ID_TRATAMIENTO` int(11) NOT NULL AUTO_INCREMENT,
  `ID_DOCTOR` int(11) DEFAULT NULL,
  `ID_PACIENTE` int(11) DEFAULT NULL,
  `FECHA_INICIO` date NOT NULL,
  `FECHA_FIN` date NOT NULL,
  PRIMARY KEY (`ID_TRATAMIENTO`),
  KEY `FK_RELATIONSHIP_6` (`ID_DOCTOR`),
  KEY `FK_RELATIONSHIP_7` (`ID_PACIENTE`),
  CONSTRAINT `FK_RELATIONSHIP_6` FOREIGN KEY (`ID_DOCTOR`) REFERENCES `doctor` (`id_doctor`),
  CONSTRAINT `FK_RELATIONSHIP_7` FOREIGN KEY (`ID_PACIENTE`) REFERENCES `paciente` (`id_paciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tratamiento`
--

LOCK TABLES `tratamiento` WRITE;
/*!40000 ALTER TABLE `tratamiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `tratamiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usuario` (
  `ID_USUARIO` int(11) NOT NULL AUTO_INCREMENT,
  `ID_ROL` int(11) DEFAULT NULL,
  `USUARIO` varchar(20) COLLATE utf8_bin NOT NULL,
  `CONTRASENA` varchar(256) COLLATE utf8_bin NOT NULL,
  `ID_ASOCIADO` int(11) NOT NULL,
  `SALT` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_USUARIO`),
  KEY `FK_RELATIONSHIP_3` (`ID_ROL`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (2,1,'admin','Mr5pWPRnitgNq7l4QaFv4GrdhEMFcMwaGAlaNJBmVYQ=',0,'Z7UGFTNtINRnjBV56izv4UXwyBWIYT'),(3,3,'secre','LEVVmL3RnCgVm8QDw3NzN3SaedMPewdUs992i0iiQzs=',1,'PqPbunO2Tpm45nJtQ6w1SXl3HrK3bB'),(4,2,'francisco','kZekmYtGBXm2CJ7SD52myglQzBRTFV8BKNmUQ0DpW7M=',2,'B7DEmQEKIQOcAAHy6lGSDNCMeQbF5i');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!50606 SET GLOBAL INNODB_STATS_AUTO_RECALC=@OLD_INNODB_STATS_AUTO_RECALC */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-03 14:25:35
