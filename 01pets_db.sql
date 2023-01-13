-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.31 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for pets_db
CREATE DATABASE IF NOT EXISTS `pets_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pets_db`;

-- Dumping structure for table pets_db.animals
CREATE TABLE IF NOT EXISTS `animals` (
  `id` int NOT NULL AUTO_INCREMENT,
  `specie` char(50) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table pets_db.animals: ~5 rows (approximately)
INSERT INTO `animals` (`id`, `specie`, `name`) VALUES
	(1, 'DOGS', NULL),
	(2, 'CATS', NULL),
	(3, 'BIRDS', NULL),
	(4, 'AQUATIC', NULL),
	(5, 'REPTILE', NULL);

-- Dumping structure for table pets_db.breeds
CREATE TABLE IF NOT EXISTS `breeds` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table pets_db.breeds: ~4 rows (approximately)
INSERT INTO `breeds` (`id`, `name`) VALUES
	(1, 'LABRADOR RETRIEVER'),
	(2, 'MIX'),
	(3, 'BULLDOG'),
	(4, 'PARROT');

-- Dumping structure for table pets_db.pets
CREATE TABLE IF NOT EXISTS `pets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `animal_id` int NOT NULL,
  `breed_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pets_animals` (`animal_id`),
  KEY `FK_pets_breeds` (`breed_id`) USING BTREE,
  CONSTRAINT `FK_pets_animals` FOREIGN KEY (`animal_id`) REFERENCES `animals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_pets_breeds` FOREIGN KEY (`breed_id`) REFERENCES `breeds` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKsxj4fviv7fwr4u9k3l7um9fqj` FOREIGN KEY (`breed_id`) REFERENCES `animals` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table pets_db.pets: ~5 rows (approximately)
INSERT INTO `pets` (`id`, `name`, `animal_id`, `breed_id`) VALUES
	(1, 'Kali', 1, 1),
	(2, 'Loli', 2, 2),
	(3, 'Maximo', 2, 2),
	(4, 'Rocco', 2, 2),
	(5, 'Pepi', 3, 4);

-- Dumping structure for table pets_db.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_unique` (`role`),
  KEY `role` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table pets_db.roles: ~3 rows (approximately)
INSERT INTO `roles` (`id`, `role`) VALUES
	(1, 'ADMIN'),
	(3, 'BANNED'),
	(2, 'USER');

-- Dumping structure for table pets_db.userroles
CREATE TABLE IF NOT EXISTS `userroles` (
  `user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  UNIQUE KEY `user_role` (`user`,`role`),
  KEY `user` (`user`),
  KEY `role` (`role`),
  CONSTRAINT `FK1_user` FOREIGN KEY (`user`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK2_role` FOREIGN KEY (`role`) REFERENCES `roles` (`role`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table pets_db.userroles: ~1 rows (approximately)
INSERT INTO `userroles` (`user`, `role`) VALUES
	('admin', 'ADMIN'),
	('user', 'USER');

-- Dumping structure for table pets_db.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `email` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `accountNonExpired` tinyint DEFAULT '1',
  `credentialsNonExpired` tinyint DEFAULT '1',
  `accountNonLocked` tinyint DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_unique` (`username`),
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table pets_db.users: ~2 rows (approximately)
INSERT INTO `users` (`id`, `username`, `password`, `enabled`, `email`, `accountNonExpired`, `credentialsNonExpired`, `accountNonLocked`) VALUES
	(4, 'user', '$2a$10$uERHmHyCgIVNYM2f58h7tO.oFAGCZKaBO3TyjLPjeFXWsuW8b4IPi', 1, 'user@mail.com', 1, 1, 1),
	(5, 'admin', '$2a$10$yHc8q.KWYg02R2aMWIdf1OiS4ld129bdXWbbfF9d8uur/qRly2W9G', 1, 'admin@mail.com', 1, 1, 1);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
