-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  mar. 14 jan. 2020 à 03:36
-- Version du serveur :  5.7.26
-- Version de PHP :  7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `taskmanagement`
--

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

DROP TABLE IF EXISTS `commentaire`;
CREATE TABLE IF NOT EXISTS `commentaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  `tache_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqka989ijm49oi6ffsyvdiv8qj` (`employee_id`),
  KEY `FK9qf64okrkcxwalxp12esrtp33` (`tache_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `commentaire`
--

INSERT INTO `commentaire` (`id`, `created_at`, `message`, `type`, `employee_id`, `tache_id`) VALUES
(1, '2020-01-14 03:02:01', 'Tache de conception', 'QUOTIDIEN', 1, 1),
(2, '2020-01-14 03:03:54', 'La partie de maquettage ', 'QUOTIDIEN', 2, 2),
(3, '2020-01-14 03:06:29', 'projet 1', 'QUOTIDIEN', 3, 3);

-- --------------------------------------------------------

--
-- Structure de la table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `date_naissance` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `sexe` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3046kvjyysq288vy3lsbtc9nw` (`role_id`),
  KEY `FKpeiwfkfseogttspgvybly8cp` (`service_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `employee`
--

INSERT INTO `employee` (`id`, `active`, `date_naissance`, `email`, `nom`, `password`, `prenom`, `sexe`, `tel`, `role_id`, `service_id`) VALUES
(1, b'1', '19/04/1997', 'fettah.souad@gmail.com', 'souad', '$2a$10$ys1I8dqLEYAJLSJ556x4EO8nUZVHldn20MhCHp/FkyMCWuqr.Vhl.', 'fettah', NULL, NULL, 1, 1),
(2, b'1', '19/04/1997', 'mouaad.oumnia@gmail.com', 'oumnia', '$2a$10$.NTlCeJpxadg3m2CCQm3ve2mbegj9QySip5JzAs0ufV7KWBS3aqTK', 'mouaad', NULL, NULL, 2, 2),
(3, b'1', '19/04/1997', 'shaili.oumaima@gmail.com', 'shaili', '$2a$10$AtJy.UuDCFm8a6TE/GI9iOWj4t4VtHtwtOwnChylfFIB.JjXMBb8u', 'oumaima', NULL, NULL, 1, 3),
(4, b'1', '19/04/1997', 'oufkir.ibrahim@email.com', 'oufkir', '$2a$10$1AEl6YAAz0tsCI4sh12lre/ecS0KLmUu2SzozPdUlZM1tWumxQhPu', 'ibrahim', NULL, NULL, 3, 2);

-- --------------------------------------------------------

--
-- Structure de la table `leave_details`
--

DROP TABLE IF EXISTS `leave_details`;
CREATE TABLE IF NOT EXISTS `leave_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accept_reject_flag` bit(1) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `completed` varchar(255) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `employee_name` varchar(255) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  `employee_id_manager` int(11) DEFAULT NULL,
  `from_date` datetime NOT NULL,
  `leave_type` varchar(255) NOT NULL,
  `nom_tache` varchar(255) DEFAULT NULL,
  `reason` varchar(255) NOT NULL,
  `to_date` datetime NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `projet_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4mh0o2sqf3514sbj907mr2jr3` (`projet_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `leave_details`
--

INSERT INTO `leave_details` (`id`, `accept_reject_flag`, `active`, `completed`, `duration`, `employee_name`, `employee_id`, `employee_id_manager`, `from_date`, `leave_type`, `nom_tache`, `reason`, `to_date`, `username`, `projet_id`) VALUES
(1, b'0', b'1', '0', 3, 'souad fettah', 1, 1, '2019-12-31 23:00:00', 'QUOTIDIEN', 'Conception', 'Tache de conception', '2020-01-02 23:00:00', 'fettah.souad@gmail.com', 1),
(2, b'1', b'0', '1', 4, 'oumnia mouaad', 2, 1, '2020-01-03 23:00:00', 'QUOTIDIEN', 'design UX/UI', 'La partie de maquettage ', '2020-01-06 23:00:00', 'mouaad.oumnia@gmail.com', 1),
(3, b'0', b'1', '0', 2, 'shaili oumaima', 3, 3, '2020-01-02 23:00:00', 'QUOTIDIEN', 'Conception', 'projet 1', '2020-01-03 23:00:00', 'shail.oumaima@gmail.com', 2);

-- --------------------------------------------------------

--
-- Structure de la table `projet`
--

DROP TABLE IF EXISTS `projet`;
CREATE TABLE IF NOT EXISTS `projet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accept_reject_flag` bit(1) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `budget` varchar(255) DEFAULT NULL,
  `date_creation` varchar(255) DEFAULT NULL,
  `date_debut` datetime DEFAULT NULL,
  `date_fin` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `id_manager` int(11) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK44i5n4yk1h98xrfuptm5hkfrh` (`service_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `projet`
--

INSERT INTO `projet` (`id`, `accept_reject_flag`, `active`, `budget`, `date_creation`, `date_debut`, `date_fin`, `description`, `id_manager`, `nom`, `username`, `service_id`) VALUES
(1, b'0', b'0', '100000000', NULL, '2019-12-31 23:00:00', '2020-01-17 23:00:00', 'Développement d\'une plateforme dédie à gérer les taches de chaque employée', 1, 'projet JEE', 'souad', 1),
(2, b'0', b'0', '12300000', NULL, '2020-01-02 23:00:00', '2020-01-17 23:00:00', 'une application mobile pour notre entreprise', 3, 'Application Mobile', 'shaili', 1);

-- --------------------------------------------------------

--
-- Structure de la table `projet_employee`
--

DROP TABLE IF EXISTS `projet_employee`;
CREATE TABLE IF NOT EXISTS `projet_employee` (
  `projet_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  KEY `FKdcnfc2wvy4j9s6hsik0xpj46u` (`employee_id`),
  KEY `FKodavi5dek9p7pnl3xl9tqly3i` (`projet_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'Manager'),
(2, 'Employee'),
(3, 'Directeur');

-- --------------------------------------------------------

--
-- Structure de la table `service`
--

DROP TABLE IF EXISTS `service`;
CREATE TABLE IF NOT EXISTS `service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `service`
--

INSERT INTO `service` (`id`, `name`) VALUES
(1, 'Developpement'),
(2, 'Administration systeme'),
(3, 'Cloud');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
