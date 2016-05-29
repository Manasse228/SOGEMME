-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.58-1ubuntu1


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema sogemee
--

CREATE DATABASE IF NOT EXISTS sogemee;
USE sogemee;

--
-- Temporary table structure for view `sogemee`.`eric`
--
DROP TABLE IF EXISTS `sogemee`.`eric`;
DROP VIEW IF EXISTS `sogemee`.`eric`;
CREATE TABLE `sogemee`.`eric` (
  `idCommande` int(11) unsigned,
  `dateCommande` date,
  `idFournisseur` int(11) unsigned,
  `nomFournisseur` varchar(45),
  `qteCommande` int(11),
  `yes` decimal(33,0)
);

--
-- Temporary table structure for view `sogemee`.`etatCommande`
--
DROP TABLE IF EXISTS `sogemee`.`etatCommande`;
DROP VIEW IF EXISTS `sogemee`.`etatCommande`;
CREATE TABLE `sogemee`.`etatCommande` (
  `dateCommande` date,
  `idCommande` int(11) unsigned,
  `qte` decimal(33,0)
);

--
-- Temporary table structure for view `sogemee`.`etatStock`
--
DROP TABLE IF EXISTS `sogemee`.`etatStock`;
DROP VIEW IF EXISTS `sogemee`.`etatStock`;
CREATE TABLE `sogemee`.`etatStock` (
  `idPlanche` int(11) unsigned,
  `libelleTypeBois` varchar(45),
  `libelleFormePlanche` varchar(45),
  `valeurLongeur` float,
  `qte` decimal(33,0)
);

--
-- Temporary table structure for view `sogemee`.`livre`
--
DROP TABLE IF EXISTS `sogemee`.`livre`;
DROP VIEW IF EXISTS `sogemee`.`livre`;
CREATE TABLE `sogemee`.`livre` (
  `idCompte` int(11),
  `libelleCompte` varchar(20),
  `sum(montantOperation)` double,
  `dateOperation` date,
  `sensOperation` varchar(10),
  `libelleOperation` varchar(30)
);

--
-- Definition of table `sogemee`.`agent`
--

DROP TABLE IF EXISTS `sogemee`.`agent`;
CREATE TABLE  `sogemee`.`agent` (
  `idAgent` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nomAgent` varchar(50) NOT NULL,
  `prenomAgent` varchar(150) NOT NULL,
  `sexe` varchar(15) NOT NULL,
  `dateEmbaucheAgent` date NOT NULL,
  `dateNaissAgent` date NOT NULL,
  `telAgent` varchar(45) DEFAULT NULL,
  `suppr` int(11) DEFAULT NULL,
  `bpAgent` varchar(15) DEFAULT NULL,
  `villeAgent` varchar(50) DEFAULT NULL,
  `rueAgent` varchar(50) DEFAULT NULL,
  `qualificationAgent` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idAgent`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`agent`
--

/*!40000 ALTER TABLE `agent` DISABLE KEYS */;
LOCK TABLES `agent` WRITE;
INSERT INTO `sogemee`.`agent` VALUES  (11,'AHIALEY','Kossi Eric','Masculin','2012-03-26','1992-04-19','90003991',0,'353','Lomé','22','BAC'),
 (12,'AFEWOU','Kodjo Désiré','Masculin','2012-03-26','1990-04-19','90043064',0,'353','Lomé','22','BAC'),
 (13,'AYITE','Kafui','Masculin','2012-03-26','1989-04-22','90215478',0,'353','Mango','25','BAC'),
 (14,'NSOUGAN','Folly','Masculin','2012-03-26','1989-11-25','9201458',0,'215','Kara','98','BAC'),
 (16,'GNITOU','Tchalim','Masculin','2012-04-04','1991-04-04','9587985',0,'258','Lomé','22','BAC'),
 (18,'BIGNANDI','Assima','Masculin','2012-04-11','1991-04-11','90763389',0,'352','Lomé','22','BAC'),
 (19,'BATAZI','Akizou','Masculin','2012-04-12','1990-04-27','91898040',0,'1104','Lomé','20 tchouk-bè','BAC'),
 (20,'AYIVOR','yao walter','Masculin','2012-04-12','1992-01-09','90810995',0,'85','Lomé','guepards','BAC'),
 (21,'GOZAN','Emmanuel','Masculin','2012-04-24','1986-12-09','90260974',0,'2154','Lomé','Ablogomé','BAC');
UNLOCK TABLES;
/*!40000 ALTER TABLE `agent` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`agent_rubrique`
--

DROP TABLE IF EXISTS `sogemee`.`agent_rubrique`;
CREATE TABLE  `sogemee`.`agent_rubrique` (
  `idAgent` int(10) unsigned NOT NULL,
  `idRubrique` int(10) unsigned NOT NULL,
  `montantRubrique` double DEFAULT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idAgent`,`idRubrique`),
  KEY `idRubrique` (`idRubrique`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`agent_rubrique`
--

/*!40000 ALTER TABLE `agent_rubrique` DISABLE KEYS */;
LOCK TABLES `agent_rubrique` WRITE;
INSERT INTO `sogemee`.`agent_rubrique` VALUES  (18,1,1000000,0),
 (11,1,20000000,0),
 (12,1,15000000,0),
 (13,1,1300000,0),
 (14,1,1200000,0),
 (16,1,1000000,0),
 (11,5,250000,0),
 (12,5,40000,0),
 (13,5,30000,0),
 (14,5,2500,0),
 (16,5,25000,0),
 (18,5,2520,0),
 (19,1,20000,0),
 (19,5,2000,0),
 (20,1,2500000,0),
 (20,5,2000,0),
 (21,1,250000,0),
 (21,5,10000,0),
 (11,6,0,0),
 (12,6,0,0),
 (13,6,0,0),
 (14,6,0,0),
 (16,6,0,0),
 (18,6,0,0),
 (19,6,-1000,0),
 (20,6,0,0),
 (21,6,0,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `agent_rubrique` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`alouer`
--

DROP TABLE IF EXISTS `sogemee`.`alouer`;
CREATE TABLE  `sogemee`.`alouer` (
  `idFormePlanche` int(10) unsigned NOT NULL,
  `idServiceAtelier` int(10) unsigned NOT NULL,
  `idTrimestre` int(10) unsigned NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTrimestre`,`idServiceAtelier`,`idFormePlanche`),
  KEY `idFormePlanche` (`idFormePlanche`),
  KEY `idServiceAtelier` (`idServiceAtelier`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`alouer`
--

/*!40000 ALTER TABLE `alouer` DISABLE KEYS */;
LOCK TABLES `alouer` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `alouer` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`bonSortie`
--

DROP TABLE IF EXISTS `sogemee`.`bonSortie`;
CREATE TABLE  `sogemee`.`bonSortie` (
  `idBonSortie` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dateBonSortie` date NOT NULL,
  `idServiceAtelier` int(10) unsigned NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  `heure` time NOT NULL,
  PRIMARY KEY (`idBonSortie`),
  KEY `idServiceAtelier` (`idServiceAtelier`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`bonSortie`
--

/*!40000 ALTER TABLE `bonSortie` DISABLE KEYS */;
LOCK TABLES `bonSortie` WRITE;
INSERT INTO `sogemee`.`bonSortie` VALUES  (1,'2012-04-14',1,1,'00:00:00'),
 (2,'2012-04-14',1,1,'11:12:54'),
 (3,'2012-04-14',2,0,'11:17:37'),
 (4,'2012-04-14',1,0,'11:26:41'),
 (5,'2012-04-14',1,0,'11:31:30'),
 (6,'2012-04-14',1,0,'11:58:56'),
 (7,'2012-04-14',1,1,'12:31:42'),
 (8,'2012-04-14',1,0,'12:33:27'),
 (9,'2012-04-14',1,0,'12:34:21'),
 (10,'2012-04-14',1,0,'12:35:27'),
 (11,'2012-04-14',1,0,'12:38:23'),
 (12,'2012-04-14',1,0,'12:43:18'),
 (13,'2012-05-09',1,0,'10:31:30');
UNLOCK TABLES;
/*!40000 ALTER TABLE `bonSortie` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`bonSortie_planche`
--

DROP TABLE IF EXISTS `sogemee`.`bonSortie_planche`;
CREATE TABLE  `sogemee`.`bonSortie_planche` (
  `idPlanche` int(10) unsigned NOT NULL,
  `idBonSortie` int(10) unsigned NOT NULL,
  `qteSortie` int(11) DEFAULT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idBonSortie`,`idPlanche`),
  KEY `idPlanche` (`idPlanche`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`bonSortie_planche`
--

/*!40000 ALTER TABLE `bonSortie_planche` DISABLE KEYS */;
LOCK TABLES `bonSortie_planche` WRITE;
INSERT INTO `sogemee`.`bonSortie_planche` VALUES  (6,1,36,0),
 (6,2,36,0),
 (8,3,43,0),
 (6,4,64,0),
 (8,5,100,0),
 (6,6,10,0),
 (8,6,50,0),
 (8,7,50,0),
 (6,7,20,0),
 (6,8,10,0),
 (8,8,20,0),
 (6,9,2,0),
 (8,9,20,0),
 (6,10,1,0),
 (8,10,10,0),
 (6,11,7,0),
 (8,11,20,0),
 (6,12,20,0),
 (8,12,30,0),
 (6,13,1,0),
 (8,13,1,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `bonSortie_planche` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`categorieModele`
--

DROP TABLE IF EXISTS `sogemee`.`categorieModele`;
CREATE TABLE  `sogemee`.`categorieModele` (
  `idCategorieModele` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libelleCategorieModele` varchar(50) NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCategorieModele`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`categorieModele`
--

/*!40000 ALTER TABLE `categorieModele` DISABLE KEYS */;
LOCK TABLES `categorieModele` WRITE;
INSERT INTO `sogemee`.`categorieModele` VALUES  (1,'Chaise',0),
 (2,'Azikpé',0),
 (3,'Aba',0),
 (4,'IReport',0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `categorieModele` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`client`
--

DROP TABLE IF EXISTS `sogemee`.`client`;
CREATE TABLE  `sogemee`.`client` (
  `idClient` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nomClient` varchar(45) NOT NULL,
  `prenomClient` varchar(50) NOT NULL,
  `telClient` varchar(45) DEFAULT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idClient`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`client`
--

/*!40000 ALTER TABLE `client` DISABLE KEYS */;
LOCK TABLES `client` WRITE;
INSERT INTO `sogemee`.`client` VALUES  (1,'AHIALEY','Eric','90003991',0),
 (2,'ATTITSO','Amivi','90003991',0),
 (3,'MOTY','Caleb','92593545',0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `client` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`commande`
--

DROP TABLE IF EXISTS `sogemee`.`commande`;
CREATE TABLE  `sogemee`.`commande` (
  `idCommande` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dateCommande` date NOT NULL,
  `dateProFormat` date DEFAULT NULL,
  `idFournisseur` int(10) unsigned NOT NULL,
  `idTva` int(10) unsigned DEFAULT NULL,
  `suppr` int(11) DEFAULT NULL,
  `typeCommande` varchar(15) NOT NULL,
  PRIMARY KEY (`idCommande`),
  KEY `idFournisseur` (`idFournisseur`),
  KEY `idTva` (`idTva`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`commande`
--

/*!40000 ALTER TABLE `commande` DISABLE KEYS */;
LOCK TABLES `commande` WRITE;
INSERT INTO `sogemee`.`commande` VALUES  (4,'2012-04-04','2012-04-06',1,7,0,'NORMALE'),
 (2,'2012-04-03','2012-04-05',2,1,0,'NORMALE'),
 (3,'2012-04-03','2012-04-05',3,6,0,'NORMALE'),
 (5,'2012-04-20','1960-01-01',1,0,1,'null'),
 (6,'2012-04-20','2012-04-24',1,7,0,'NORMALE'),
 (7,'2012-04-24','1960-01-01',4,0,0,'EXPRESSE');
UNLOCK TABLES;
/*!40000 ALTER TABLE `commande` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`composer`
--

DROP TABLE IF EXISTS `sogemee`.`composer`;
CREATE TABLE  `sogemee`.`composer` (
  `idServiceAtelier` int(10) unsigned NOT NULL,
  `idFonction` int(10) unsigned NOT NULL DEFAULT '0',
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idServiceAtelier`,`idFonction`),
  KEY `idFonction` (`idFonction`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`composer`
--

/*!40000 ALTER TABLE `composer` DISABLE KEYS */;
LOCK TABLES `composer` WRITE;
INSERT INTO `sogemee`.`composer` VALUES  (1,3,0),
 (1,1,0),
 (2,4,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `composer` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`compte`
--

DROP TABLE IF EXISTS `sogemee`.`compte`;
CREATE TABLE  `sogemee`.`compte` (
  `idCompte` int(11) NOT NULL AUTO_INCREMENT,
  `libelleCompte` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idCompte`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`compte`
--

/*!40000 ALTER TABLE `compte` DISABLE KEYS */;
LOCK TABLES `compte` WRITE;
INSERT INTO `sogemee`.`compte` VALUES  (1,'Client'),
 (2,'Fournisseur'),
 (3,'Achat'),
 (4,'Vente'),
 (5,'Caisse');
UNLOCK TABLES;
/*!40000 ALTER TABLE `compte` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`concerner`
--

DROP TABLE IF EXISTS `sogemee`.`concerner`;
CREATE TABLE  `sogemee`.`concerner` (
  `idPlanche` int(10) unsigned NOT NULL,
  `idFactureClient` int(10) unsigned NOT NULL,
  `qteFacture` int(11) DEFAULT NULL,
  `typeTraitement` varchar(50) DEFAULT NULL,
  `suppr` int(11) DEFAULT NULL,
  `typeAction` varchar(15) NOT NULL,
  PRIMARY KEY (`idFactureClient`,`idPlanche`),
  KEY `idPlanche` (`idPlanche`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`concerner`
--

/*!40000 ALTER TABLE `concerner` DISABLE KEYS */;
LOCK TABLES `concerner` WRITE;
INSERT INTO `sogemee`.`concerner` VALUES  (8,9,0,'Aucun',0,'Vente'),
 (6,9,0,'Lissage',0,'Service'),
 (8,10,0,'Aucun',0,'Vente'),
 (6,10,0,'Lissage',0,'Service'),
 (8,11,0,'Aucun',0,'Vente'),
 (6,11,0,'Traitement et Lissage',0,'Vente'),
 (8,19,0,'Aucun',0,'Vente'),
 (8,25,6,'Aucun',0,'Vente'),
 (8,26,20,'Aucun',0,'Vente'),
 (8,27,0,'Traitement',0,'Service'),
 (6,27,0,'Traitement',0,'Service'),
 (6,29,1,'Aucun',0,'Vente'),
 (8,29,0,'Traitement',0,'Service'),
 (8,30,1,'Aucun',0,'Vente'),
 (10,30,2,'Aucun',0,'Vente'),
 (6,32,7,'Aucun',0,'Vente'),
 (6,33,7,'Aucun',0,'Vente'),
 (10,33,7,'Aucun',0,'Vente'),
 (6,34,5,'Lissage',0,'Service'),
 (10,34,2,'Lissage',0,'Service');
UNLOCK TABLES;
/*!40000 ALTER TABLE `concerner` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`evenement`
--

DROP TABLE IF EXISTS `sogemee`.`evenement`;
CREATE TABLE  `sogemee`.`evenement` (
  `idAgent` int(10) unsigned NOT NULL,
  `idTypeEvenement` int(10) unsigned NOT NULL,
  `dateDebEvenement` date NOT NULL,
  `dateFinEvenement` date DEFAULT NULL,
  `dureEvenement` double DEFAULT NULL,
  `motif` text,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idAgent`,`idTypeEvenement`,`dateDebEvenement`),
  KEY `idTypeEvenement` (`idTypeEvenement`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`evenement`
--

/*!40000 ALTER TABLE `evenement` DISABLE KEYS */;
LOCK TABLES `evenement` WRITE;
INSERT INTO `sogemee`.`evenement` VALUES  (14,2,'2012-03-26','2012-03-26',0,'Bonne Moralité',0),
 (15,2,'0004-04-02','0004-04-02',0,'null',0),
 (16,2,'2012-04-01','2012-04-01',0,'Politesse',0),
 (17,2,'2012-04-04','2012-04-04',0,'Aucun',1),
 (16,2,'2012-04-05','2012-04-05',0,'Aucun',0),
 (17,2,'2012-04-02','2012-04-02',0,'Politesse',0),
 (13,2,'2012-04-05','2012-04-05',0,'Aucun',0),
 (18,2,'2012-04-11','2012-04-11',0,'null',0),
 (19,2,'2012-04-12','2012-04-12',0,'null',0),
 (20,2,'2012-04-12','2012-04-12',0,'null',0),
 (21,2,'2012-04-24','2012-04-24',0,'null',0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `evenement` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`factureClient`
--

DROP TABLE IF EXISTS `sogemee`.`factureClient`;
CREATE TABLE  `sogemee`.`factureClient` (
  `idFactureClient` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dateFacture` date NOT NULL,
  `dejaLivrer` int(10) unsigned NOT NULL,
  `idAgent` int(10) unsigned NOT NULL,
  `idClient` int(10) unsigned NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  `montantFacture` double NOT NULL,
  PRIMARY KEY (`idFactureClient`),
  KEY `idAgent` (`idAgent`),
  KEY `idClient` (`idClient`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`factureClient`
--

/*!40000 ALTER TABLE `factureClient` DISABLE KEYS */;
LOCK TABLES `factureClient` WRITE;
INSERT INTO `sogemee`.`factureClient` VALUES  (34,'2012-04-16',0,11,3,0,4300),
 (33,'2012-04-05',0,11,1,0,33250),
 (32,'2012-04-05',0,11,1,0,17500),
 (31,'2012-04-03',0,11,1,0,6600),
 (30,'2012-04-03',1,11,1,0,7300),
 (29,'2012-02-02',0,11,1,0,5200),
 (28,'2012-02-02',0,11,2,1,450),
 (27,'2012-02-02',0,11,1,0,500000);
UNLOCK TABLES;
/*!40000 ALTER TABLE `factureClient` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`fonction`
--

DROP TABLE IF EXISTS `sogemee`.`fonction`;
CREATE TABLE  `sogemee`.`fonction` (
  `idFonction` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libelleFonction` varchar(45) NOT NULL,
  `nbPersonne` int(11) DEFAULT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idFonction`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`fonction`
--

/*!40000 ALTER TABLE `fonction` DISABLE KEYS */;
LOCK TABLES `fonction` WRITE;
INSERT INTO `sogemee`.`fonction` VALUES  (1,'Menusier',0,0),
 (2,'Chef Service',1,0),
 (3,'Chef Atelier',1,0),
 (4,'CAISSIER-COMPTABLE',0,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `fonction` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`formePlanche`
--

DROP TABLE IF EXISTS `sogemee`.`formePlanche`;
CREATE TABLE  `sogemee`.`formePlanche` (
  `idFormePlanche` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libelleFormePlanche` varchar(45) NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idFormePlanche`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`formePlanche`
--

/*!40000 ALTER TABLE `formePlanche` DISABLE KEYS */;
LOCK TABLES `formePlanche` WRITE;
INSERT INTO `sogemee`.`formePlanche` VALUES  (1,'CHEVRON',0),
 (2,'OVALE',0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `formePlanche` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`fournisseur`
--

DROP TABLE IF EXISTS `sogemee`.`fournisseur`;
CREATE TABLE  `sogemee`.`fournisseur` (
  `idFournisseur` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nomFournisseur` varchar(45) NOT NULL,
  `telFournisseur` varchar(50) NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  `bpFournisseur` varchar(15) DEFAULT NULL,
  `villeFournisseur` varchar(50) DEFAULT NULL,
  `rueFournisseur` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idFournisseur`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`fournisseur`
--

/*!40000 ALTER TABLE `fournisseur` DISABLE KEYS */;
LOCK TABLES `fournisseur` WRITE;
INSERT INTO `sogemee`.`fournisseur` VALUES  (1,'BIGNANDI ET FILS','2154875',0,'356 ','Lomé','Forever'),
 (2,'AFEWOU ET FILS','2254875',0,'356','Lomé','sagbado'),
 (3,'LEKEZIM ET FILS','2354875',0,'356 ','Lomé','sagbado'),
 (4,'ATCHOU ET FRÈRES','90774448',0,'2004','Lomé','Eperviers'),
 (5,'TAILLEUR ET SOEURS','99469128',1,'8547','Lomé','Nyékonakpoè');
UNLOCK TABLES;
/*!40000 ALTER TABLE `fournisseur` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`inclure`
--

DROP TABLE IF EXISTS `sogemee`.`inclure`;
CREATE TABLE  `sogemee`.`inclure` (
  `idPlanche` int(10) unsigned NOT NULL,
  `idCommande` int(10) unsigned NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  `qteCommande` int(11) NOT NULL,
  `pu` double NOT NULL,
  PRIMARY KEY (`idCommande`,`idPlanche`),
  KEY `idPlanche` (`idPlanche`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`inclure`
--

/*!40000 ALTER TABLE `inclure` DISABLE KEYS */;
LOCK TABLES `inclure` WRITE;
INSERT INTO `sogemee`.`inclure` VALUES  (8,1,0,250,2540),
 (6,1,0,500,2540),
 (8,2,0,500,100),
 (6,2,0,200,100),
 (8,3,0,250,250),
 (6,3,0,601,300),
 (8,4,0,2500,2000),
 (6,0,0,11,0),
 (12,0,0,21,0),
 (6,6,0,71,2500),
 (12,6,0,54,2300),
 (10,7,0,11,0),
 (11,7,0,21,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `inclure` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`inventaire`
--

DROP TABLE IF EXISTS `sogemee`.`inventaire`;
CREATE TABLE  `sogemee`.`inventaire` (
  `idInventaire` int(11) NOT NULL AUTO_INCREMENT,
  `dateInventaire` date NOT NULL,
  `suppr` int(11) NOT NULL,
  PRIMARY KEY (`idInventaire`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`inventaire`
--

/*!40000 ALTER TABLE `inventaire` DISABLE KEYS */;
LOCK TABLES `inventaire` WRITE;
INSERT INTO `sogemee`.`inventaire` VALUES  (1,'2012-05-09',0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `inventaire` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`inventaire_modele`
--

DROP TABLE IF EXISTS `sogemee`.`inventaire_modele`;
CREATE TABLE  `sogemee`.`inventaire_modele` (
  `idInventaire` int(11) NOT NULL,
  `idModele` int(11) NOT NULL,
  `qteInventaire` int(11) NOT NULL,
  PRIMARY KEY (`idInventaire`,`idModele`),
  KEY `idModele` (`idModele`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`inventaire_modele`
--

/*!40000 ALTER TABLE `inventaire_modele` DISABLE KEYS */;
LOCK TABLES `inventaire_modele` WRITE;
INSERT INTO `sogemee`.`inventaire_modele` VALUES  (1,13,4),
 (1,14,7);
UNLOCK TABLES;
/*!40000 ALTER TABLE `inventaire_modele` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`inventaire_planche`
--

DROP TABLE IF EXISTS `sogemee`.`inventaire_planche`;
CREATE TABLE  `sogemee`.`inventaire_planche` (
  `idInventaire` int(11) NOT NULL,
  `idPlanche` int(11) NOT NULL,
  `qteInventaire` int(11) NOT NULL,
  PRIMARY KEY (`idPlanche`,`idInventaire`),
  KEY `idInventaire` (`idInventaire`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`inventaire_planche`
--

/*!40000 ALTER TABLE `inventaire_planche` DISABLE KEYS */;
LOCK TABLES `inventaire_planche` WRITE;
INSERT INTO `sogemee`.`inventaire_planche` VALUES  (1,8,25),
 (1,6,14);
UNLOCK TABLES;
/*!40000 ALTER TABLE `inventaire_planche` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`livraison`
--

DROP TABLE IF EXISTS `sogemee`.`livraison`;
CREATE TABLE  `sogemee`.`livraison` (
  `idLivraison` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dateLivraison` date NOT NULL,
  `idCommande` int(10) unsigned NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idLivraison`),
  KEY `idCommande` (`idCommande`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`livraison`
--

/*!40000 ALTER TABLE `livraison` DISABLE KEYS */;
LOCK TABLES `livraison` WRITE;
INSERT INTO `sogemee`.`livraison` VALUES  (1,'2012-04-04',2,0),
 (2,'2012-04-04',4,0),
 (3,'2012-04-04',3,0),
 (4,'2012-04-04',3,1),
 (5,'2012-04-03',3,0),
 (6,'2012-04-03',2,0),
 (7,'2012-04-24',2,0),
 (8,'2012-04-24',4,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `livraison` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`livraison_planche`
--

DROP TABLE IF EXISTS `sogemee`.`livraison_planche`;
CREATE TABLE  `sogemee`.`livraison_planche` (
  `idPlanche` int(10) unsigned NOT NULL,
  `idLivraison` int(10) unsigned NOT NULL,
  `qteLivraison` int(11) DEFAULT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idLivraison`,`idPlanche`),
  KEY `idPlanche` (`idPlanche`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`livraison_planche`
--

/*!40000 ALTER TABLE `livraison_planche` DISABLE KEYS */;
LOCK TABLES `livraison_planche` WRITE;
INSERT INTO `sogemee`.`livraison_planche` VALUES  (6,1,100,0),
 (8,2,1000,0),
 (6,3,1,0),
 (8,3,50,0),
 (6,5,120,0),
 (8,5,20,0),
 (6,6,30,0),
 (8,7,100,0),
 (6,7,20,0),
 (8,8,500,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `livraison_planche` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`longeurPlanche`
--

DROP TABLE IF EXISTS `sogemee`.`longeurPlanche`;
CREATE TABLE  `sogemee`.`longeurPlanche` (
  `idLongeurPlanche` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `valeurLongeur` float NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idLongeurPlanche`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`longeurPlanche`
--

/*!40000 ALTER TABLE `longeurPlanche` DISABLE KEYS */;
LOCK TABLES `longeurPlanche` WRITE;
INSERT INTO `sogemee`.`longeurPlanche` VALUES  (1,25,0),
 (2,45,0),
 (3,8,0),
 (4,3,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `longeurPlanche` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`meuble`
--

DROP TABLE IF EXISTS `sogemee`.`meuble`;
CREATE TABLE  `sogemee`.`meuble` (
  `idMeuble` int(11) NOT NULL AUTO_INCREMENT,
  `idModele` int(11) NOT NULL,
  `idFactureClient` int(10) unsigned NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  `idSortie` int(11) NOT NULL,
  PRIMARY KEY (`idMeuble`),
  KEY `idModele` (`idModele`),
  KEY `idFactureClient` (`idFactureClient`),
  KEY `idSortie` (`idSortie`)
) ENGINE=MyISAM AUTO_INCREMENT=124 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`meuble`
--

/*!40000 ALTER TABLE `meuble` DISABLE KEYS */;
LOCK TABLES `meuble` WRITE;
INSERT INTO `sogemee`.`meuble` VALUES  (123,13,31,0,0),
 (122,13,0,0,0),
 (121,13,0,0,0),
 (120,14,31,0,0),
 (119,14,31,0,0),
 (118,14,0,0,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `meuble` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`modeReglement`
--

DROP TABLE IF EXISTS `sogemee`.`modeReglement`;
CREATE TABLE  `sogemee`.`modeReglement` (
  `idModeReglement` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libelleModeReglement` varchar(95) NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idModeReglement`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`modeReglement`
--

/*!40000 ALTER TABLE `modeReglement` DISABLE KEYS */;
LOCK TABLES `modeReglement` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `modeReglement` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`modele`
--

DROP TABLE IF EXISTS `sogemee`.`modele`;
CREATE TABLE  `sogemee`.`modele` (
  `idModele` int(11) NOT NULL AUTO_INCREMENT,
  `libelleModele` varchar(100) NOT NULL,
  `idCategorieModele` int(10) unsigned NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  `prixVente` double NOT NULL,
  PRIMARY KEY (`idModele`),
  KEY `idCategorieModele` (`idCategorieModele`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`modele`
--

/*!40000 ALTER TABLE `modele` DISABLE KEYS */;
LOCK TABLES `modele` WRITE;
INSERT INTO `sogemee`.`modele` VALUES  (12,'3 PIDS',1,1,2000),
 (13,'2 PIEDS',1,0,8250),
 (14,'8 PIEDS',2,0,2250),
 (15,'BOURÉ',3,0,15000),
 (16,'3PIDS',1,0,250);
UNLOCK TABLES;
/*!40000 ALTER TABLE `modele` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`occuper`
--

DROP TABLE IF EXISTS `sogemee`.`occuper`;
CREATE TABLE  `sogemee`.`occuper` (
  `idAgent` int(10) unsigned NOT NULL,
  `idServiceAtelier` int(10) unsigned NOT NULL,
  `idFonction` int(10) unsigned NOT NULL,
  `datedebOccuper` date NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  `datefinOccuper` date DEFAULT NULL,
  PRIMARY KEY (`idAgent`,`idFonction`,`idServiceAtelier`,`datedebOccuper`),
  KEY `idServiceAtelier` (`idServiceAtelier`),
  KEY `idFonction` (`idFonction`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`occuper`
--

/*!40000 ALTER TABLE `occuper` DISABLE KEYS */;
LOCK TABLES `occuper` WRITE;
INSERT INTO `sogemee`.`occuper` VALUES  (11,1,3,'2012-03-26',0,NULL),
 (12,1,1,'2012-03-26',0,NULL),
 (13,1,1,'2012-03-26',0,'2012-04-02'),
 (14,1,1,'2012-03-26',0,NULL),
 (15,1,1,'2012-04-04',0,NULL),
 (16,1,1,'2012-04-04',0,NULL),
 (17,1,1,'2012-04-04',0,NULL),
 (13,1,1,'2012-04-05',0,NULL),
 (18,1,1,'2012-04-11',0,NULL),
 (19,2,4,'2012-04-12',0,NULL),
 (20,2,4,'2012-04-12',0,NULL),
 (21,2,4,'2012-04-24',0,NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `occuper` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`operation`
--

DROP TABLE IF EXISTS `sogemee`.`operation`;
CREATE TABLE  `sogemee`.`operation` (
  `idOperation` int(11) NOT NULL AUTO_INCREMENT,
  `dateOperation` date NOT NULL,
  `montantOperation` double NOT NULL,
  `idCompte` int(11) NOT NULL,
  `libelleOperation` varchar(30) DEFAULT NULL,
  `sensOperation` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idOperation`),
  KEY `idCompte` (`idCompte`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`operation`
--

/*!40000 ALTER TABLE `operation` DISABLE KEYS */;
LOCK TABLES `operation` WRITE;
INSERT INTO `sogemee`.`operation` VALUES  (1,'2012-05-09',80000,2,'Reglement Fournisseur','Credit'),
 (2,'2012-05-09',80000,2,'Reglement Fournisseur','Debit'),
 (3,'2012-05-09',80000,2,'Reglement Fournisseur','Credit'),
 (4,'2012-05-09',500,1,'Reglement Client','Credit'),
 (5,'2012-05-09',500,1,'Reglement Client','Credit'),
 (6,'2012-05-09',2000,2,'Reglement Fournisseur','Credit'),
 (7,'2012-05-09',2000,5,'Reglement Fournisseur','Debit'),
 (8,'2012-05-09',2000,3,'Reglement Fournisseur','Credit');
UNLOCK TABLES;
/*!40000 ALTER TABLE `operation` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`payer`
--

DROP TABLE IF EXISTS `sogemee`.`payer`;
CREATE TABLE  `sogemee`.`payer` (
  `idAgent` int(10) unsigned NOT NULL,
  `idRubrique` int(10) unsigned NOT NULL,
  `idPeriodePaie` int(10) unsigned NOT NULL,
  `montantPayer` double DEFAULT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idAgent`,`idPeriodePaie`,`idRubrique`),
  KEY `idRubrique` (`idRubrique`),
  KEY `idPeriodePaie` (`idPeriodePaie`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`payer`
--

/*!40000 ALTER TABLE `payer` DISABLE KEYS */;
LOCK TABLES `payer` WRITE;
INSERT INTO `sogemee`.`payer` VALUES  (11,1,1,20000000,0),
 (11,5,1,250000,0),
 (13,1,1,1300000,0),
 (13,5,1,0,0),
 (18,5,1,2520,0),
 (18,1,1,1000000,0),
 (18,1,8,1000000,0),
 (18,5,8,2520,0),
 (16,1,1,1000000,0),
 (16,5,1,0,0),
 (14,1,6,1200000,0),
 (14,5,6,2500,0),
 (13,1,9,1300000,0),
 (13,5,9,30000,0),
 (19,1,9,20000,0),
 (19,5,9,0,0),
 (20,1,9,2500000,0),
 (19,1,3,20000,0),
 (18,1,10,1000000,0),
 (18,5,10,2520,0),
 (16,1,11,1000000,0),
 (16,6,11,0,0),
 (19,1,11,20000,0),
 (19,6,11,-1000,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `payer` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`periodePaie`
--

DROP TABLE IF EXISTS `sogemee`.`periodePaie`;
CREATE TABLE  `sogemee`.`periodePaie` (
  `idPeriodePaie` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libellePeriodePaie` date NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idPeriodePaie`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`periodePaie`
--

/*!40000 ALTER TABLE `periodePaie` DISABLE KEYS */;
LOCK TABLES `periodePaie` WRITE;
INSERT INTO `sogemee`.`periodePaie` VALUES  (1,'2012-04-11',0),
 (2,'2012-04-11',0),
 (3,'2012-04-10',0),
 (4,'2012-04-09',0),
 (5,'2012-03-31',0),
 (6,'2012-04-06',0),
 (7,'2012-03-22',0),
 (8,'2012-03-15',0),
 (9,'2012-04-12',0),
 (10,'2012-04-23',0),
 (11,'2012-05-01',0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `periodePaie` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`planche`
--

DROP TABLE IF EXISTS `sogemee`.`planche`;
CREATE TABLE  `sogemee`.`planche` (
  `idPlanche` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idLongeurPlanche` int(10) unsigned NOT NULL,
  `idTypeBois` int(10) unsigned NOT NULL,
  `idFormePlanche` int(10) unsigned NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  `coutTraitement` int(11) NOT NULL,
  `coutLissage` int(11) NOT NULL,
  `prixVentePlanche` double NOT NULL,
  PRIMARY KEY (`idPlanche`),
  KEY `idLongeurPlanche` (`idLongeurPlanche`),
  KEY `idTypeBois` (`idTypeBois`),
  KEY `idFormePlanche` (`idFormePlanche`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`planche`
--

/*!40000 ALTER TABLE `planche` DISABLE KEYS */;
LOCK TABLES `planche` WRITE;
INSERT INTO `sogemee`.`planche` VALUES  (8,1,3,1,0,5000,20010,2800),
 (6,1,1,1,0,250,500,2500),
 (9,2,2,2,0,250,900,2250),
 (10,3,3,2,0,250,900,2250),
 (11,1,1,2,0,250,800,4250),
 (12,1,2,2,0,250,800,3000),
 (13,2,2,1,1,200,500,1250);
UNLOCK TABLES;
/*!40000 ALTER TABLE `planche` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`profil`
--

DROP TABLE IF EXISTS `sogemee`.`profil`;
CREATE TABLE  `sogemee`.`profil` (
  `idProfil` int(11) NOT NULL AUTO_INCREMENT,
  `libelleProfil` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idProfil`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`profil`
--

/*!40000 ALTER TABLE `profil` DISABLE KEYS */;
LOCK TABLES `profil` WRITE;
INSERT INTO `sogemee`.`profil` VALUES  (1,'Administrateur'),
 (2,'Approvisionnement'),
 (3,'Chef Approvisionneme'),
 (4,'Chef SAF'),
 (5,'Chef Fabrication'),
 (6,'Chef Facturaction'),
 (7,'Facturaction'),
 (8,'Fabrication'),
 (9,'Comptable');
UNLOCK TABLES;
/*!40000 ALTER TABLE `profil` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`reglement`
--

DROP TABLE IF EXISTS `sogemee`.`reglement`;
CREATE TABLE  `sogemee`.`reglement` (
  `idReglement` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `montantReglement` float NOT NULL,
  `idFactureClient` int(10) unsigned NOT NULL,
  `idModeReglement` int(10) unsigned NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  `dateReglement` date NOT NULL,
  PRIMARY KEY (`idReglement`),
  KEY `idFactureClient` (`idFactureClient`),
  KEY `idModeReglemen` (`idModeReglement`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`reglement`
--

/*!40000 ALTER TABLE `reglement` DISABLE KEYS */;
LOCK TABLES `reglement` WRITE;
INSERT INTO `sogemee`.`reglement` VALUES  (1,200,20,1,0,'2012-04-03'),
 (2,15,10,1,0,'2012-04-03'),
 (3,25401,12,1,0,'2012-04-03'),
 (4,20548,10,1,0,'2012-04-03'),
 (5,201541,9,1,0,'2012-04-03'),
 (6,2500,27,1,0,'2012-04-03'),
 (7,750,27,1,0,'2012-04-03'),
 (8,400,28,1,0,'2012-04-03'),
 (9,6000,31,1,0,'2012-04-03'),
 (10,7300,30,1,0,'2012-04-03'),
 (11,250,33,1,0,'2012-04-03'),
 (12,50,28,1,1,'2012-04-03'),
 (13,100,29,1,0,'2012-04-06'),
 (14,25,28,1,0,'2012-04-09'),
 (15,500,32,1,0,'2012-04-09'),
 (16,750,27,1,0,'2012-04-09'),
 (17,100,29,1,0,'2012-04-09'),
 (18,6000,27,1,0,'2012-04-12'),
 (19,90000,27,1,0,'2012-04-20'),
 (20,500,29,1,0,'2012-05-09');
UNLOCK TABLES;
/*!40000 ALTER TABLE `reglement` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`reglementFournisseur`
--

DROP TABLE IF EXISTS `sogemee`.`reglementFournisseur`;
CREATE TABLE  `sogemee`.`reglementFournisseur` (
  `idReglementFournisseur` int(11) NOT NULL AUTO_INCREMENT,
  `dateReglementFournisseur` date NOT NULL,
  `montantReglementFournisseur` double NOT NULL,
  `idLivraison` int(11) NOT NULL,
  `suppr` int(11) NOT NULL,
  PRIMARY KEY (`idReglementFournisseur`),
  KEY `idLivraison` (`idLivraison`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`reglementFournisseur`
--

/*!40000 ALTER TABLE `reglementFournisseur` DISABLE KEYS */;
LOCK TABLES `reglementFournisseur` WRITE;
INSERT INTO `sogemee`.`reglementFournisseur` VALUES  (1,'2012-04-04',2000,1,0),
 (2,'2012-04-04',2000,6,0),
 (3,'2012-04-04',2000,1,0),
 (4,'2012-04-04',500,6,1),
 (5,'2012-04-04',200,6,0),
 (6,'2012-04-04',1000,5,0),
 (7,'2012-04-04',200,6,0),
 (8,'2012-04-06',120000,2,0),
 (9,'2012-04-12',5000,1,0),
 (10,'2012-05-09',600,6,0),
 (11,'2012-05-09',800,3,0),
 (12,'2012-05-09',80000,2,0),
 (13,'2012-05-09',2000,7,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `reglementFournisseur` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`rubrique`
--

DROP TABLE IF EXISTS `sogemee`.`rubrique`;
CREATE TABLE  `sogemee`.`rubrique` (
  `idRubrique` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libelleRubrique` varchar(95) NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  `etat` int(11) NOT NULL,
  PRIMARY KEY (`idRubrique`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`rubrique`
--

/*!40000 ALTER TABLE `rubrique` DISABLE KEYS */;
LOCK TABLES `rubrique` WRITE;
INSERT INTO `sogemee`.`rubrique` VALUES  (1,'Salaire de base',0,1),
 (2,'Ms',1,0),
 (3,'Ms',1,0),
 (5,'Primes',0,1),
 (6,'HAHAHAHA',0,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `rubrique` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`serviceAtelier`
--

DROP TABLE IF EXISTS `sogemee`.`serviceAtelier`;
CREATE TABLE  `sogemee`.`serviceAtelier` (
  `idServiceAtelier` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libelleServiceAtelier` varchar(55) NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idServiceAtelier`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`serviceAtelier`
--

/*!40000 ALTER TABLE `serviceAtelier` DISABLE KEYS */;
LOCK TABLES `serviceAtelier` WRITE;
INSERT INTO `sogemee`.`serviceAtelier` VALUES  (1,'APPROVISIONNEMENT',0),
 (2,'SAF',0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `serviceAtelier` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`sortie`
--

DROP TABLE IF EXISTS `sogemee`.`sortie`;
CREATE TABLE  `sogemee`.`sortie` (
  `idSortie` int(11) NOT NULL AUTO_INCREMENT,
  `dateSortie` date NOT NULL,
  PRIMARY KEY (`idSortie`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`sortie`
--

/*!40000 ALTER TABLE `sortie` DISABLE KEYS */;
LOCK TABLES `sortie` WRITE;
INSERT INTO `sogemee`.`sortie` VALUES  (1,'2012-05-01'),
 (2,'2012-05-09');
UNLOCK TABLES;
/*!40000 ALTER TABLE `sortie` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`sortie_planche`
--

DROP TABLE IF EXISTS `sogemee`.`sortie_planche`;
CREATE TABLE  `sogemee`.`sortie_planche` (
  `idSortie` int(11) NOT NULL,
  `idPlanche` int(11) NOT NULL,
  `qteSortie` int(11) NOT NULL,
  `motifSortie` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`idSortie`,`idPlanche`,`motifSortie`),
  KEY `idPlanche` (`idPlanche`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`sortie_planche`
--

/*!40000 ALTER TABLE `sortie_planche` DISABLE KEYS */;
LOCK TABLES `sortie_planche` WRITE;
INSERT INTO `sogemee`.`sortie_planche` VALUES  (2,6,128,'Vole'),
 (2,8,1351,'Vole');
UNLOCK TABLES;
/*!40000 ALTER TABLE `sortie_planche` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`trimestre`
--

DROP TABLE IF EXISTS `sogemee`.`trimestre`;
CREATE TABLE  `sogemee`.`trimestre` (
  `idTrimestre` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libelletrimestre` varchar(45) NOT NULL,
  `anneeTrimestre` text NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTrimestre`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`trimestre`
--

/*!40000 ALTER TABLE `trimestre` DISABLE KEYS */;
LOCK TABLES `trimestre` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `trimestre` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`tva`
--

DROP TABLE IF EXISTS `sogemee`.`tva`;
CREATE TABLE  `sogemee`.`tva` (
  `idTva` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `valeurTva` float NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTva`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`tva`
--

/*!40000 ALTER TABLE `tva` DISABLE KEYS */;
LOCK TABLES `tva` WRITE;
INSERT INTO `sogemee`.`tva` VALUES  (1,25,0),
 (2,25,0),
 (3,25,0),
 (4,25,0),
 (5,25,0),
 (6,30,0),
 (7,12,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `tva` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`typeBois`
--

DROP TABLE IF EXISTS `sogemee`.`typeBois`;
CREATE TABLE  `sogemee`.`typeBois` (
  `idTypeBois` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libelleTypeBois` varchar(45) NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTypeBois`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`typeBois`
--

/*!40000 ALTER TABLE `typeBois` DISABLE KEYS */;
LOCK TABLES `typeBois` WRITE;
INSERT INTO `sogemee`.`typeBois` VALUES  (1,'IROKO',0),
 (2,'ACAJOU',0),
 (3,'TECK',0),
 (4,'MELANA',0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `typeBois` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`typeEvenement`
--

DROP TABLE IF EXISTS `sogemee`.`typeEvenement`;
CREATE TABLE  `sogemee`.`typeEvenement` (
  `idTypeEvenement` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libelleTypeEvenement` varchar(95) NOT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTypeEvenement`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`typeEvenement`
--

/*!40000 ALTER TABLE `typeEvenement` DISABLE KEYS */;
LOCK TABLES `typeEvenement` WRITE;
INSERT INTO `sogemee`.`typeEvenement` VALUES  (1,'DEMISSION',0),
 (2,'MUTATION',0),
 (3,'LICENCIEMENT',1),
 (4,'LICENCIEMENT',0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `typeEvenement` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`utilisateur`
--

DROP TABLE IF EXISTS `sogemee`.`utilisateur`;
CREATE TABLE  `sogemee`.`utilisateur` (
  `idUtilisateur` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  `idAgent` int(11) NOT NULL,
  `suppr` int(11) NOT NULL,
  `idProfil` int(11) NOT NULL,
  PRIMARY KEY (`idUtilisateur`),
  KEY `idAgent` (`idAgent`),
  KEY `idProfil` (`idProfil`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`utilisateur`
--

/*!40000 ALTER TABLE `utilisateur` DISABLE KEYS */;
LOCK TABLES `utilisateur` WRITE;
INSERT INTO `sogemee`.`utilisateur` VALUES  (1,'roldick','roldick',11,0,1),
 (2,'Desire','desire',12,0,4),
 (3,'tchalim','tchalim',16,0,8);
UNLOCK TABLES;
/*!40000 ALTER TABLE `utilisateur` ENABLE KEYS */;


--
-- Definition of table `sogemee`.`utiliser`
--

DROP TABLE IF EXISTS `sogemee`.`utiliser`;
CREATE TABLE  `sogemee`.`utiliser` (
  `idPlanche` int(10) unsigned NOT NULL,
  `idModele` int(10) unsigned NOT NULL,
  `qte` int(11) DEFAULT NULL,
  `suppr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idModele`,`idPlanche`),
  KEY `idPlanche` (`idPlanche`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sogemee`.`utiliser`
--

/*!40000 ALTER TABLE `utiliser` DISABLE KEYS */;
LOCK TABLES `utiliser` WRITE;
INSERT INTO `sogemee`.`utiliser` VALUES  (8,0,5,0),
 (6,0,3,0),
 (8,12,3,0),
 (6,12,4,0),
 (9,13,7,0),
 (10,13,5,0),
 (6,14,3,0),
 (11,14,2,0),
 (8,15,10,0),
 (6,15,5,0),
 (6,16,7,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `utiliser` ENABLE KEYS */;


--
-- Definition of view `sogemee`.`eric`
--

DROP TABLE IF EXISTS `sogemee`.`eric`;
DROP VIEW IF EXISTS `sogemee`.`eric`;
CREATE ALGORITHM=UNDEFINED DEFINER=`roldick`@`localhost` SQL SECURITY DEFINER VIEW `sogemee`.`eric` AS select `c`.`idCommande` AS `idCommande`,`c`.`dateCommande` AS `dateCommande`,`f`.`idFournisseur` AS `idFournisseur`,`f`.`nomFournisseur` AS `nomFournisseur`,`i`.`qteCommande` AS `qteCommande`,sum(`i`.`qteCommande`) AS `yes` from ((`sogemee`.`commande` `c` join `sogemee`.`fournisseur` `f`) join `sogemee`.`inclure` `i`) where ((`c`.`idFournisseur` = `f`.`idFournisseur`) and (`c`.`dateProFormat` <> '1960-01-01') and (`c`.`suppr` = 0) and (`c`.`idCommande` = `i`.`idCommande`)) group by `c`.`idCommande` union select `c`.`idCommande` AS `idCommande`,`c`.`dateCommande` AS `dateCommande`,`f`.`idFournisseur` AS `idFournisseur`,`f`.`nomFournisseur` AS `nomFournisseur`,`q`.`qteLivraison` AS `qteLivraison`,-(sum(`q`.`qteLivraison`)) AS `-sum(q.qteLivraison)` from (((`sogemee`.`commande` `c` join `sogemee`.`fournisseur` `f`) join `sogemee`.`livraison` `l`) join `sogemee`.`livraison_planche` `q`) where ((`c`.`idFournisseur` = `f`.`idFournisseur`) and (`c`.`idCommande` = `l`.`idCommande`) and (`l`.`idLivraison` = `q`.`idLivraison`) and (`c`.`suppr` = 0) and (`l`.`suppr` = 0)) group by `c`.`idCommande`;

--
-- Definition of view `sogemee`.`etatCommande`
--

DROP TABLE IF EXISTS `sogemee`.`etatCommande`;
DROP VIEW IF EXISTS `sogemee`.`etatCommande`;
CREATE ALGORITHM=UNDEFINED DEFINER=`roldick`@`localhost` SQL SECURITY DEFINER VIEW `sogemee`.`etatCommande` AS select `c`.`dateCommande` AS `dateCommande`,`c`.`idCommande` AS `idCommande`,sum(`i`.`qteCommande`) AS `qte` from ((`sogemee`.`commande` `c` join `sogemee`.`planche` `p`) join `sogemee`.`inclure` `i`) where ((`i`.`idCommande` = `c`.`idCommande`) and (`i`.`idPlanche` = `p`.`idPlanche`)) group by `c`.`idCommande` union select `c`.`dateCommande` AS `dateCommande`,`c`.`idCommande` AS `idCommande`,-(sum(`i`.`qteLivraison`)) AS `-sum(qteLivraison)` from (((`sogemee`.`commande` `c` join `sogemee`.`planche` `p`) join `sogemee`.`livraison` `l`) join `sogemee`.`livraison_planche` `i`) where ((`l`.`idCommande` = `c`.`idCommande`) and (`i`.`idPlanche` = `p`.`idPlanche`) and (`l`.`idLivraison` = `i`.`idLivraison`)) group by `c`.`idCommande`;

--
-- Definition of view `sogemee`.`etatStock`
--

DROP TABLE IF EXISTS `sogemee`.`etatStock`;
DROP VIEW IF EXISTS `sogemee`.`etatStock`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `sogemee`.`etatStock` AS select `p`.`idPlanche` AS `idPlanche`,`t`.`libelleTypeBois` AS `libelleTypeBois`,`f`.`libelleFormePlanche` AS `libelleFormePlanche`,`l`.`valeurLongeur` AS `valeurLongeur`,-(sum(`i`.`qteSortie`)) AS `qte` from (((((`sogemee`.`planche` `p` join `sogemee`.`typeBois` `t`) join `sogemee`.`formePlanche` `f`) join `sogemee`.`longeurPlanche` `l`) join `sogemee`.`bonSortie_planche` `i`) join `sogemee`.`bonSortie` `b`) where ((`p`.`suppr` = 0) and (`p`.`idTypeBois` = `t`.`idTypeBois`) and (`p`.`idFormePlanche` = `f`.`idFormePlanche`) and (`b`.`idBonSortie` = `i`.`idBonSortie`) and (`b`.`suppr` = 0) and (`p`.`idLongeurPlanche` = `l`.`idLongeurPlanche`) and (`i`.`idPlanche` = `p`.`idPlanche`) and (`b`.`dateBonSortie` <= '2012-05-09')) group by `i`.`idPlanche` union select `p`.`idPlanche` AS `idPlanche`,`t`.`libelleTypeBois` AS `libelleTypeBois`,`f`.`libelleFormePlanche` AS `libelleFormePlanche`,`l`.`valeurLongeur` AS `valeurLongeur`,-(sum(`i`.`qteSortie`)) AS `-sum(i.qteSortie)` from (((((`sogemee`.`planche` `p` join `sogemee`.`typeBois` `t`) join `sogemee`.`formePlanche` `f`) join `sogemee`.`longeurPlanche` `l`) join `sogemee`.`sortie_planche` `i`) join `sogemee`.`sortie` `b`) where ((`p`.`suppr` = 0) and (`p`.`idTypeBois` = `t`.`idTypeBois`) and (`p`.`idFormePlanche` = `f`.`idFormePlanche`) and (`b`.`idSortie` = `i`.`idSortie`) and (`p`.`idLongeurPlanche` = `l`.`idLongeurPlanche`) and (`i`.`idPlanche` = `p`.`idPlanche`) and (`b`.`dateSortie` <= '2012-05-09')) group by `i`.`idPlanche` union select `p`.`idPlanche` AS `idPlanche`,`t`.`libelleTypeBois` AS `libelleTypeBois`,`f`.`libelleFormePlanche` AS `libelleFormePlanche`,`l`.`valeurLongeur` AS `valeurLongeur`,sum(`i`.`qteLivraison`) AS `sum(i.qteLivraison)` from (((((`sogemee`.`planche` `p` join `sogemee`.`typeBois` `t`) join `sogemee`.`formePlanche` `f`) join `sogemee`.`longeurPlanche` `l`) join `sogemee`.`livraison_planche` `i`) join `sogemee`.`livraison` `k`) where ((`p`.`idTypeBois` = `t`.`idTypeBois`) and (`p`.`idFormePlanche` = `f`.`idFormePlanche`) and (`p`.`idLongeurPlanche` = `l`.`idLongeurPlanche`) and (`k`.`idLivraison` = `i`.`idLivraison`) and (`i`.`idPlanche` = `p`.`idPlanche`) and (`k`.`dateLivraison` <= '2012-05-09') and (`k`.`suppr` = 0)) group by `i`.`idPlanche` union select `p`.`idPlanche` AS `idPlanche`,`t`.`libelleTypeBois` AS `libelleTypeBois`,`f`.`libelleFormePlanche` AS `libelleFormePlanche`,`l`.`valeurLongeur` AS `valeurLongeur`,-(sum(`c`.`qteFacture`)) AS `-sum(c.qteFacture)` from (((((`sogemee`.`planche` `p` join `sogemee`.`typeBois` `t`) join `sogemee`.`formePlanche` `f`) join `sogemee`.`longeurPlanche` `l`) join `sogemee`.`factureClient` `i`) join `sogemee`.`concerner` `c`) where ((`p`.`idTypeBois` = `t`.`idTypeBois`) and (`p`.`idFormePlanche` = `f`.`idFormePlanche`) and (`p`.`idLongeurPlanche` = `l`.`idLongeurPlanche`) and (`c`.`idFactureClient` = `i`.`idFactureClient`) and (`c`.`idPlanche` = `p`.`idPlanche`) and (`i`.`dateFacture` <= '2012-05-09') and (`i`.`suppr` = 0) and (`c`.`typeAction` = 'Vente')) group by `c`.`idPlanche`;

--
-- Definition of view `sogemee`.`livre`
--

DROP TABLE IF EXISTS `sogemee`.`livre`;
DROP VIEW IF EXISTS `sogemee`.`livre`;
CREATE ALGORITHM=UNDEFINED DEFINER=`roldick`@`localhost` SQL SECURITY DEFINER VIEW `sogemee`.`livre` AS select `c`.`idCompte` AS `idCompte`,`c`.`libelleCompte` AS `libelleCompte`,sum(`o`.`montantOperation`) AS `sum(montantOperation)`,`o`.`dateOperation` AS `dateOperation`,`o`.`sensOperation` AS `sensOperation`,`o`.`libelleOperation` AS `libelleOperation` from (`sogemee`.`compte` `c` join `sogemee`.`operation` `o`) where ((`c`.`idCompte` = `o`.`idCompte`) and (`o`.`sensOperation` = 'Credit')) union select `c`.`idCompte` AS `idCompte`,`c`.`libelleCompte` AS `libelleCompte`,sum(`o`.`montantOperation`) AS `sum(montantOperation)`,`o`.`dateOperation` AS `dateOperation`,`o`.`sensOperation` AS `sensOperation`,`o`.`libelleOperation` AS `libelleOperation` from (`sogemee`.`compte` `c` join `sogemee`.`operation` `o`) where ((`c`.`idCompte` = `o`.`idCompte`) and (`o`.`sensOperation` = 'Debit'));



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
