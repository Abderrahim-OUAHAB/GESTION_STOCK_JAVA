CREATE TABLE `client` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `firstname` varchar(30) DEFAULT NULL,
  `lastname` varchar(30) DEFAULT NULL,
  `tel` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `inventaire` (
  `id` bigint NOT NULL,
  `produit_id` bigint DEFAULT NULL,
  `quantite` int DEFAULT NULL,
  `date_ajout` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `inventaire_ibfk_1` (`produit_id`),
  CONSTRAINT `inventaire_ibfk_1` FOREIGN KEY (`produit_id`) REFERENCES `produit` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `paiements` (
  `id` bigint NOT NULL,
  `cli_id` bigint DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `date_paiement` date DEFAULT NULL,
  `methode_paiement` varchar(30) DEFAULT NULL,
  `statut_paiement` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `paiements_ibfk_1` (`cli_id`),
  CONSTRAINT `paiements_ibfk_1` FOREIGN KEY (`cli_id`) REFERENCES `client` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `produit` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `designation` varchar(255) DEFAULT NULL,
  `prix` double DEFAULT NULL,
  `qte` int DEFAULT NULL,
  `total` double DEFAULT NULL,
  `datec` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `vente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `client_id` bigint DEFAULT NULL,
  `produit_id` bigint DEFAULT NULL,
  `quantite` int DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `date_vente` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `vente_ibfk_2` (`produit_id`),
  KEY `vente_ibfk_1` (`client_id`),
  CONSTRAINT `vente_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE SET NULL,
  CONSTRAINT `vente_ibfk_2` FOREIGN KEY (`produit_id`) REFERENCES `produit` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
