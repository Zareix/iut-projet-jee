DROP TABLE DVD;
DROP TABLE CD;
DROP TABLE LIVRE;
DROP TABLE DOCUMENT;
DROP TABLE UTILISATEUR;

CREATE TABLE Document(
   Id_Document int auto_increment,
   Titre VARCHAR(50),
   Emprunte boolean DEFAULT '0',
   PRIMARY KEY(Id_Document)
);

CREATE TABLE DVD(
   Id INT auto_increment,
   Realisateur VARCHAR(50),
   Id_Document INT NOT NULL UNIQUE,
   PRIMARY KEY(Id),
   FOREIGN KEY (Id_Document) REFERENCES Document(Id_Document ) ON DELETE CASCADE
);

CREATE TABLE CD(
   Id INT auto_increment,
   Artiste VARCHAR(50),
   Id_Document INT NOT NULL UNIQUE,
   PRIMARY KEY(Id),
   FOREIGN KEY (Id_Document) REFERENCES Document(Id_Document ) ON DELETE CASCADE
);

CREATE TABLE LIVRE(
   Id int auto_increment,
   Auteur VARCHAR(50),
   Id_Document INT NOT NULL UNIQUE,
   PRIMARY KEY(Id),
   FOREIGN KEY (Id_Document) REFERENCES Document(Id_Document ) ON DELETE CASCADE
);

CREATE TABLE UTILISATEUR(
   Login VARCHAR(50),
   Password VARCHAR(50) NOT NULL,
   PRIMARY KEY(Login)
);
