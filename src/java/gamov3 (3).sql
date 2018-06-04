-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Lun 04 Juin 2018 à 14:54
-- Version du serveur :  5.6.15-log
-- Version de PHP :  5.4.24

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `gamov3`
--

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE IF NOT EXISTS `categorie` (
  `ID` bigint(20) NOT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `device`
--

CREATE TABLE IF NOT EXISTS `device` (
  `ID` bigint(20) NOT NULL,
  `BROWSER` varchar(255) DEFAULT NULL,
  `DEVICECATEGORIE` varchar(255) DEFAULT NULL,
  `OPERATINGSYSTEM` varchar(255) DEFAULT NULL,
  `UTILISATEUR_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_DEVICE_UTILISATEUR_ID` (`UTILISATEUR_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `entree`
--

CREATE TABLE IF NOT EXISTS `entree` (
  `ID` bigint(20) NOT NULL,
  `DATEENTREE` date DEFAULT NULL,
  `NOMBRETOTAL` double DEFAULT NULL,
  `FOURNISSEUR_ID` bigint(20) DEFAULT NULL,
  `UTILISATEUR_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ENTREE_FOURNISSEUR_ID` (`FOURNISSEUR_ID`),
  KEY `FK_ENTREE_UTILISATEUR_ID` (`UTILISATEUR_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `entree`
--

INSERT INTO `entree` (`ID`, `DATEENTREE`, `NOMBRETOTAL`, `FOURNISSEUR_ID`, `UTILISATEUR_ID`) VALUES
(1, '2018-06-09', 77, 2, 'ana');

-- --------------------------------------------------------

--
-- Structure de la table `entreeitem`
--

CREATE TABLE IF NOT EXISTS `entreeitem` (
  `ID` bigint(20) NOT NULL,
  `NUMEROSERIE` int(11) DEFAULT NULL,
  `OBSERVATION` varchar(255) DEFAULT NULL,
  `QUANTITE` int(11) DEFAULT NULL,
  `QUANTITEMINIMALE` int(11) DEFAULT NULL,
  `EQUIPEMENT_ID` bigint(20) DEFAULT NULL,
  `ENTREE_ID` bigint(20) DEFAULT NULL,
  `MAGASIN_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ENTREEITEM_MAGASIN_ID` (`MAGASIN_ID`),
  KEY `FK_ENTREEITEM_ENTREE_ID` (`ENTREE_ID`),
  KEY `FK_ENTREEITEM_EQUIPEMENT_ID` (`EQUIPEMENT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `entreeitem`
--

INSERT INTO `entreeitem` (`ID`, `NUMEROSERIE`, `OBSERVATION`, `QUANTITE`, `QUANTITEMINIMALE`, `EQUIPEMENT_ID`, `ENTREE_ID`, `MAGASIN_ID`) VALUES
(151, 88, 'sjhhs', 77, 6, 51, 1, 52),
(152, 88, 'sjhhs', 77, 6, 52, 1, 51);

-- --------------------------------------------------------

--
-- Structure de la table `epuisement`
--

CREATE TABLE IF NOT EXISTS `epuisement` (
  `ID` bigint(20) NOT NULL,
  `DATEENTREE` date DEFAULT NULL,
  `DATEEPUISEMENT` date DEFAULT NULL,
  `DATESORTIE` date DEFAULT NULL,
  `NUMEROSERIE` int(11) DEFAULT NULL,
  `OBSERVATION` varchar(255) DEFAULT NULL,
  `QUANTITE` int(11) DEFAULT NULL,
  `QUANTITEMINIMALE` int(11) DEFAULT NULL,
  `EQUIPEMENT_ID` bigint(20) DEFAULT NULL,
  `FOURNISSEUR_ID` bigint(20) DEFAULT NULL,
  `MAGASIN_ID` bigint(20) DEFAULT NULL,
  `SALLE_ID` bigint(20) DEFAULT NULL,
  `UTILISATEUR_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_EPUISEMENT_SALLE_ID` (`SALLE_ID`),
  KEY `FK_EPUISEMENT_FOURNISSEUR_ID` (`FOURNISSEUR_ID`),
  KEY `FK_EPUISEMENT_EQUIPEMENT_ID` (`EQUIPEMENT_ID`),
  KEY `FK_EPUISEMENT_UTILISATEUR_ID` (`UTILISATEUR_ID`),
  KEY `FK_EPUISEMENT_MAGASIN_ID` (`MAGASIN_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `equipement`
--

CREATE TABLE IF NOT EXISTS `equipement` (
  `ID` bigint(20) NOT NULL,
  `AFFECTATION` varchar(255) DEFAULT NULL,
  `DATEMISEENSERVICE` date DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `DESIGNATION` varchar(255) DEFAULT NULL,
  `DETAILS` varchar(255) DEFAULT NULL,
  `EMPLACEMENT` varchar(255) DEFAULT NULL,
  `MARQUE` varchar(255) DEFAULT NULL,
  `OBSERVATION` varchar(255) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `FOURNISSEUR_ID` bigint(20) DEFAULT NULL,
  `UTILISATEUR_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_EQUIPEMENT_UTILISATEUR_ID` (`UTILISATEUR_ID`),
  KEY `FK_EQUIPEMENT_FOURNISSEUR_ID` (`FOURNISSEUR_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `equipement`
--

INSERT INTO `equipement` (`ID`, `AFFECTATION`, `DATEMISEENSERVICE`, `DESCRIPTION`, `DESIGNATION`, `DETAILS`, `EMPLACEMENT`, `MARQUE`, `OBSERVATION`, `TYPE`, `FOURNISSEUR_ID`, `UTILISATEUR_ID`) VALUES
(51, 'hhhh', '2018-05-11', 'mdl7', 'delah', 'tfo', 'zbel', 'walo', 'tfoooo', 'tqdlqet', 1, NULL),
(52, 'k', '2018-05-15', 'k', 'kio', 'hhh', 'k', 'k', 'h', 'o', 1, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `fournisseur`
--

CREATE TABLE IF NOT EXISTS `fournisseur` (
  `ID` bigint(20) NOT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `fournisseur`
--

INSERT INTO `fournisseur` (`ID`, `NOM`) VALUES
(1, 'HP'),
(2, 'SHNEIDER'),
(3, 'Societe');

-- --------------------------------------------------------

--
-- Structure de la table `magasin`
--

CREATE TABLE IF NOT EXISTS `magasin` (
  `ID` bigint(20) NOT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `magasin`
--

INSERT INTO `magasin` (`ID`, `NOM`) VALUES
(51, 'MAG 1'),
(52, 'MAG 2');

-- --------------------------------------------------------

--
-- Structure de la table `maintenance`
--

CREATE TABLE IF NOT EXISTS `maintenance` (
  `ID` bigint(20) NOT NULL,
  `ANNEE` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `MOIS` int(11) DEFAULT NULL,
  `PLANING_ID` bigint(20) DEFAULT NULL,
  `TYPEMAINTENANCE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_MAINTENANCE_PLANING_ID` (`PLANING_ID`),
  KEY `FK_MAINTENANCE_TYPEMAINTENANCE_ID` (`TYPEMAINTENANCE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `maintenanceitem`
--

CREATE TABLE IF NOT EXISTS `maintenanceitem` (
  `ID` bigint(20) NOT NULL,
  `DATEDEBUTTRAVAUX` datetime DEFAULT NULL,
  `DATEFINTRAVAUX` datetime DEFAULT NULL,
  `DATEMAINTENANCE` date DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `OBSERVATION` varchar(255) DEFAULT NULL,
  `EQUIPEMENT_ID` bigint(20) DEFAULT NULL,
  `RESPONSABLEMAINTENANCE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_MAINTENANCEITEM_EQUIPEMENT_ID` (`EQUIPEMENT_ID`),
  KEY `FK_MAINTENANCEITEM_RESPONSABLEMAINTENANCE_ID` (`RESPONSABLEMAINTENANCE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `planing`
--

CREATE TABLE IF NOT EXISTS `planing` (
  `ID` bigint(20) NOT NULL,
  `ANNEE` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `MOIS` int(11) DEFAULT NULL,
  `TYPEPLANING_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PLANING_TYPEPLANING_ID` (`TYPEPLANING_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `planingitem`
--

CREATE TABLE IF NOT EXISTS `planingitem` (
  `ID` bigint(20) NOT NULL,
  `DATEPLANING` date DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `ETAT` int(11) DEFAULT NULL,
  `EQUIPEMENT_ID` bigint(20) DEFAULT NULL,
  `CATEGORIE_ID` bigint(20) DEFAULT NULL,
  `SALLE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PLANINGITEM_CATEGORIE_ID` (`CATEGORIE_ID`),
  KEY `FK_PLANINGITEM_SALLE_ID` (`SALLE_ID`),
  KEY `FK_PLANINGITEM_EQUIPEMENT_ID` (`EQUIPEMENT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `responsablemaintenance`
--

CREATE TABLE IF NOT EXISTS `responsablemaintenance` (
  `ID` bigint(20) NOT NULL,
  `FONCTION` varchar(255) DEFAULT NULL,
  `GRADE` varchar(255) DEFAULT NULL,
  `MATRICULE` varchar(255) DEFAULT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `PRENOM` varchar(255) DEFAULT NULL,
  `QUALIFICATION` varchar(255) DEFAULT NULL,
  `EQUIPEMENT_ID` bigint(20) DEFAULT NULL,
  `UTILISATEUR_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_RESPONSABLEMAINTENANCE_UTILISATEUR_ID` (`UTILISATEUR_ID`),
  KEY `FK_RESPONSABLEMAINTENANCE_EQUIPEMENT_ID` (`EQUIPEMENT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `salle`
--

CREATE TABLE IF NOT EXISTS `salle` (
  `ID` bigint(20) NOT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `SERVICE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SALLE_SERVICE_ID` (`SERVICE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `salle`
--

INSERT INTO `salle` (`ID`, `NOM`, `SERVICE_ID`) VALUES
(3, 'Salle Technique', 1),
(4, 'Salle Eenergetique', 1),
(5, 'Salle3', 1);

-- --------------------------------------------------------

--
-- Structure de la table `sequence`
--

CREATE TABLE IF NOT EXISTS `sequence` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `sequence`
--


-- --------------------------------------------------------

--
-- Structure de la table `service`
--

CREATE TABLE IF NOT EXISTS `service` (
  `ID` bigint(20) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `service`
--

INSERT INTO `service` (`ID`, `LIBELLE`) VALUES
(1, 'radar/cir'),
(2, 'Communication/navigation');

-- --------------------------------------------------------

--
-- Structure de la table `sortie`
--

CREATE TABLE IF NOT EXISTS `sortie` (
  `ID` bigint(20) NOT NULL,
  `DATESORTIE` date DEFAULT NULL,
  `NOMBRETOTAL` double DEFAULT NULL,
  `FOURNISSEUR_ID` bigint(20) DEFAULT NULL,
  `UTILISATEUR_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SORTIE_FOURNISSEUR_ID` (`FOURNISSEUR_ID`),
  KEY `FK_SORTIE_UTILISATEUR_ID` (`UTILISATEUR_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `sortie`
--

INSERT INTO `sortie` (`ID`, `DATESORTIE`, `NOMBRETOTAL`, `FOURNISSEUR_ID`, `UTILISATEUR_ID`) VALUES
(1, '2018-06-09', 33, 1, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `sortieitem`
--

CREATE TABLE IF NOT EXISTS `sortieitem` (
  `ID` bigint(20) NOT NULL,
  `NUMEROSERIE` int(11) DEFAULT NULL,
  `OBSERVATION` varchar(255) DEFAULT NULL,
  `QUANTITE` int(11) DEFAULT NULL,
  `QUANTITEMINIMALE` int(11) DEFAULT NULL,
  `EQUIPEMENT_ID` bigint(20) DEFAULT NULL,
  `MAGASIN_ID` bigint(20) DEFAULT NULL,
  `SALLE_ID` bigint(20) DEFAULT NULL,
  `SORTIE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SORTIEITEM_EQUIPEMENT_ID` (`EQUIPEMENT_ID`),
  KEY `FK_SORTIEITEM_SALLE_ID` (`SALLE_ID`),
  KEY `FK_SORTIEITEM_MAGASIN_ID` (`MAGASIN_ID`),
  KEY `FK_SORTIEITEM_SORTIE_ID` (`SORTIE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `sortieitem`
--

INSERT INTO `sortieitem` (`ID`, `NUMEROSERIE`, `OBSERVATION`, `QUANTITE`, `QUANTITEMINIMALE`, `EQUIPEMENT_ID`, `MAGASIN_ID`, `SALLE_ID`, `SORTIE_ID`) VALUES
(101, 44, 'dddd', 4, 3, 51, 51, 3, 1),
(102, 44, 'dddd', 4, 3, 52, 52, 4, 1);

-- --------------------------------------------------------

--
-- Structure de la table `stock`
--

CREATE TABLE IF NOT EXISTS `stock` (
  `ID` bigint(20) NOT NULL,
  `QUANTITE` int(11) DEFAULT NULL,
  `QUANTITEMIN` int(11) DEFAULT NULL,
  `EQUIPEMENT_ID` bigint(20) DEFAULT NULL,
  `MAGASIN_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_STOCK_EQUIPEMENT_ID` (`EQUIPEMENT_ID`),
  KEY `FK_STOCK_MAGASIN_ID` (`MAGASIN_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `transfert`
--

CREATE TABLE IF NOT EXISTS `transfert` (
  `ID` bigint(20) NOT NULL,
  `DATESORTIE` date DEFAULT NULL,
  `NOMBRETOTAL` double DEFAULT NULL,
  `UTILISATEUR_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_TRANSFERT_UTILISATEUR_ID` (`UTILISATEUR_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `transfertitem`
--

CREATE TABLE IF NOT EXISTS `transfertitem` (
  `ID` bigint(20) NOT NULL,
  `NUMEROSERIE` int(11) DEFAULT NULL,
  `OBSERVATION` varchar(255) DEFAULT NULL,
  `QUANTITE` int(11) DEFAULT NULL,
  `EQUIPEMENT_ID` bigint(20) DEFAULT NULL,
  `SALLEDESTINATION_ID` bigint(20) DEFAULT NULL,
  `SALLESOURCE_ID` bigint(20) DEFAULT NULL,
  `TRANSFERT_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_TRANSFERTITEM_SALLEDESTINATION_ID` (`SALLEDESTINATION_ID`),
  KEY `FK_TRANSFERTITEM_SALLESOURCE_ID` (`SALLESOURCE_ID`),
  KEY `FK_TRANSFERTITEM_EQUIPEMENT_ID` (`EQUIPEMENT_ID`),
  KEY `FK_TRANSFERTITEM_TRANSFERT_ID` (`TRANSFERT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `typeintervention`
--

CREATE TABLE IF NOT EXISTS `typeintervention` (
  `ID` bigint(20) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `typemaintenance`
--

CREATE TABLE IF NOT EXISTS `typemaintenance` (
  `ID` bigint(20) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `typeplaning`
--

CREATE TABLE IF NOT EXISTS `typeplaning` (
  `ID` bigint(20) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE IF NOT EXISTS `utilisateur` (
  `ID` varchar(255) NOT NULL,
  `BLOCKED` int(11) DEFAULT NULL,
  `MDPCHANGED` tinyint(1) DEFAULT '0',
  `NBRCNX` int(11) DEFAULT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `PRENOM` varchar(255) DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`ID`, `BLOCKED`, `MDPCHANGED`, `NBRCNX`, `NOM`, `PASSWORD`, `PRENOM`, `TYPE`) VALUES
('sas', 0, 0, 0, 'sas', '96166e27af5a3c431ffa7247ad4e1b2d488008311887cedc655121565721cbce', 'sas', 0),
('ana', 0, 0, 0, 'ana', '24d4b96f58da6d4a8512313bbd02a28ebf0ca95dec6e4c86ef78ce7f01e788ac', 'ana', 0);
SET FOREIGN_KEY_CHECKS=1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
