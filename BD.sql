--
-- Structure table DOCUMENT
--

CREATE TABLE `document` (
  `Id_Document` int(11) NOT NULL,
  `Titre` varchar(50) NOT NULL,
  `Emprunte` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Données pour DOCUMENT
--

INSERT INTO `document` (`Id_Document`, `Titre`, `Emprunte`) VALUES
(24, 'Discovery', 0),
(26, 'Avatar', 0),
(28, 'Avengers: Endgame', 0),
(30, 'Les Misérables', 1),
(31, 'Le Loup de Wall Street', 1),
(32, 'After hours', 0),
(33, 'Les Fleurs du mal', 0),
(34, 'L Étranger', 0),
(37, 'e', 0),
(38, 'e', 0),
(39, 'e', 0),
(40, 'e', 0),
(41, 'fesf', 0),
(42, 'sefe', 0);

-- --------------------------------------------------------

--
-- Structure table CD
--

CREATE TABLE `cd` (
  `id` int(11) NOT NULL,
  `Artiste` varchar(50) DEFAULT NULL,
  `Id_Document` int(11) NOT NULL
);

--
-- Données pour CD
--

INSERT INTO `cd` (`id`, `Artiste`, `Id_Document`) VALUES
(24, 'Daft Punk', 24),
(32, 'The Weeknd', 32);

-- --------------------------------------------------------

--
-- Stucture pour DVD
--

CREATE TABLE `dvd` (
  `Id` int(11) NOT NULL,
  `Realisateur` varchar(50) DEFAULT NULL,
  `Id_Document` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Données pour DVD
--

INSERT INTO `dvd` (`Id`, `Realisateur`, `Id_Document`) VALUES
(26, 'James Cameron', 26),
(28, 'Joe Russo, Anthony Russo', 28),
(31, 'Martin Scorsese', 31);

-- --------------------------------------------------------

--
-- Structure pour LIVRE
--

CREATE TABLE `livre` (
  `id` int(11) NOT NULL,
  `Auteur` varchar(50) DEFAULT NULL,
  `Id_Document` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Données pour LIVRE
--

INSERT INTO `livre` (`id`, `Auteur`, `Id_Document`) VALUES
(30, 'Victor Hugo', 30),
(33, 'Charles Baudelaire', 33),
(34, 'Albert Camus', 34);

-- --------------------------------------------------------

--
-- Structure pour UTILISATEUR
--

CREATE TABLE `utilisateur` (
  `Login` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Données pour UTILISATEUR
--

INSERT INTO `utilisateur` (`Login`, `Password`) VALUES
('raphaelgc', 'raphael123'),
('mohamed', 'mohamed123'),
('jfbrette', 'jfbrette123');

-- --------------------------------------------------------

--
-- Clé primaires des tables
--
ALTER TABLE `cd`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Id_Document` (`Id_Document`);

ALTER TABLE `document`
  ADD PRIMARY KEY (`Id_Document`);

ALTER TABLE `dvd`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `Id_Document` (`Id_Document`);

ALTER TABLE `livre`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Id_Document` (`Id_Document`);

ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`Login`);


--
-- Contraintes de références des tables
--

ALTER TABLE `cd`
  ADD CONSTRAINT `cd_ibfk_1` FOREIGN KEY (`Id_Document`) REFERENCES `document` (`Id_Document`) ON DELETE CASCADE;

ALTER TABLE `dvd`
  ADD CONSTRAINT `dvd_ibfk_1` FOREIGN KEY (`Id_Document`) REFERENCES `document` (`Id_Document`) ON DELETE CASCADE;

ALTER TABLE `livre`
  ADD CONSTRAINT `livre_ibfk_1` FOREIGN KEY (`Id_Document`) REFERENCES `document` (`Id_Document`) ON DELETE CASCADE;
  
 
COMMIT;
