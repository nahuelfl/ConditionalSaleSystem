-- Fichier LDD.sql
-- Projet IFT-2935
-- Camille Claing, Alexandre Dufour, Raphaël Lajoie & Nahuel Londono


-- SET_PATH
set search_path to app;

-- ___________________________Ménage des tables__________________________
DROP TABLE Utilisateurs CASCADE;
DROP TABLE Catégories CASCADE;
DROP TABLE object CASCADE;
DROP TABLE Annonces CASCADE;
DROP TABLE FaitOffre CASCADE;
DROP TABLE Estimation CASCADE;


-- ______________________Initialisation des domaines______________________
CREATE DOMAIN status_type as text 
check (value = 'refusée' or value = 'en attente' or value = 'acceptée');

-- ____________________ Ajout d'attributs aux tables ____________________
create table Utilisateurs (
   userID NUMERIC(5) PRIMARY KEY,
   nom VARCHAR(30) not null,
   prenom VARCHAR(20) not null,
   courriel VARCHAR(60) not null,
   telephone NUMERIC(10) not null,
   numeroAdr NUMERIC(8) not null,
   rue VARCHAR(40) not null,
   ville CHAR(20) not null,
   province CHAR(4) not null,
   pays CHAR(20) not null,
   codePostal VARCHAR(6) not null
);

create table Catégories(
   nomCatégorie VARCHAR(60) PRIMARY KEY
);

create table object(
   object_ID NUMERIC(5) PRIMARY KEY,
   nomObjet VARCHAR(30) not null,
   nomCatégorie VARCHAR(60),
   prixDemandé numeric(7,2),
   estVendu bool_type not null,
   spécifications VARCHAR(500)
);
ALTER TABLE object ADD CONSTRAINT foreign_key_nomCompagnie FOREIGN KEY (nomCatégorie) REFERENCES Catégories(nomCatégorie) ON DELETE CASCADE;

create table Annonces(
   object_ID INTEGER not null,
   userID INTEGER not null,
   _date DATE not null
);
ALTER TABLE Annonces ADD CONSTRAINT primary_key_Annonces PRIMARY KEY(object_ID, userID);
ALTER TABLE Annonces ADD CONSTRAINT foreign_key_userID FOREIGN KEY (userID) REFERENCES Utilisateurs(userID) ON DELETE CASCADE;
ALTER TABLE Annonces ADD CONSTRAINT foreign_key_ID FOREIGN KEY (object_ID) REFERENCES object(object_ID) ON DELETE CASCADE;

create table FaitOffre(
   object_ID INTEGER not null,
   userID INTEGER not null,
   prixOffre numeric(7,2) not null,
   statut status_type not null,
   _date DATE not null
);
ALTER TABLE FaitOffre ADD CONSTRAINT primary_key_FaitOffre PRIMARY KEY(object_ID, userID, prixOffre);
ALTER TABLE FaitOffre ADD CONSTRAINT foreign_key_userID FOREIGN KEY (userID) REFERENCES Utilisateurs(userID) ON DELETE CASCADE;
ALTER TABLE FaitOffre ADD CONSTRAINT foreign_key_ID FOREIGN KEY (object_ID) REFERENCES object(object_ID) ON DELETE CASCADE;

create table Estimation(
   object_ID INTEGER not null,
   expert_ID INTEGER not null,
   _date DATE not null,
   prixEstimation numeric(7,2) not null
);
ALTER TABLE Estimation ADD CONSTRAINT primary_key_Estimations PRIMARY KEY(object_ID, expert_ID, _date);
ALTER TABLE Estimation ADD CONSTRAINT foreign_key_userID FOREIGN KEY (expert_ID) REFERENCES Utilisateurs(userID) ON DELETE CASCADE;
ALTER TABLE Estimation ADD CONSTRAINT foreign_key_ID FOREIGN KEY (object_ID) REFERENCES object(object_ID) ON DELETE CASCADE;


-- ___________________ Ajout d'éléments aux tables ___________________

-- Ajout de 54 éléments
INSERT INTO Utilisateurs (userID, nom, prenom, courriel, telephone, numeroAdr, rue, ville, province, pays, codePostal) Values 
 (00001, 'Piché', 'Paul', 'ppiché@gmail.com', 4501522231, 556, 'Concorde', 'Laval', 'QC', 'Canada', 'J7J5T5'),
 (00002, 'Lajoie', 'Martine', 'mlajoie@hotmail.ca', 4501144444, 558, 'Lajoie', 'Montréal', 'QC', 'Canada', 'J7J5T6'),
 (00003, 'Martin', 'Réjean', 'rmartin@outlook.com', 4388443345, 557, 'Alyssums', 'Québec', 'QC', 'Canada', 'J7J5T7'),
 (00004, 'Bock-Côté','Louis', 'lbockcote@gmail.cu', 5145145144, 559, 'Guy', 'Gatineau', 'QC', 'Canada', 'J7J5T8'),
 (00005, 'Martineault', 'Isabelle', 'imartineault@hotmail.com', 4388384455, 560, 'Hull', 'Laval', 'QC', 'Canada', 'J7J5T9') ,
 (00006, 'Laroque', 'Monique', 'mlaroque@gmail.ca', 9891231234, 561, 'Amherst', 'Sherbrooke', 'QC', 'Canada', 'J7J5T0') ,
 (00007, 'Painchaud', 'Jeanne', 'jpinchaud@gmail.com', 5503435654, 562, 'Dubois', 'Trois-Rivière', 'QC', 'Canada', 'J7J5T1') ,
 (00008, 'Massé', 'Stéphanie', 'smasse@gmail.com', 4398275034, 563, 'Jean-Talon', 'Rouyn-Noranda', 'QC', 'Canada', 'J7J5T2') ,
 (00009, 'Harper', 'Raphael', 'rharper@hotmail.com', 4505555556, 564, 'Édouard', 'Val-DOr', 'QC', 'Canada', 'J7J5T3') ,
 (00010, 'Saint-Hilaire', 'Martin', 'msainthilaire@gmail.com', 4500123349, 565, 'Champlain', 'Lachute', 'QC', 'Canada', 'J7J5T4') ,
 (00011, 'Byron', 'Jules', 'jbyron@outlook.com', 4507574388, 566, 'rueaa', 'Rosemère', 'QC', 'Canada', 'J7J5T5') ,
 (00012, 'Desharnais', 'Magalie', 'madesharnais@gmail.com', 4509432424, 567, 'ruebb', 'Candiac', 'QC', 'Canada', 'J8J5T5') ,
 (00013, 'Price', 'Thalia', 'tprice@outlook.com', 4503131323, 568, 'ruecc', 'Bromont', 'QC', 'Canada', 'J9J5T5') ,
 (00014, 'Laprise', 'Marc', 'mlaprise@gmail.ca', 4504464466, 569, 'ruedd', 'Blainville', 'QC', 'Canada', 'J0J5T5') ,
 (00015, 'Claing', 'Napoléon', 'nclaing@hotmail.ca', 4507474747, 570, 'rueee', 'Saint-Janvier', 'QC', 'Canada', 'J1J5T5') ,
 (00016, 'Dufour', 'Michel', 'mdufour@gmail.ca', 4504242422, 571, 'ruff', 'Mont-Laurier', 'QC', 'Canada', 'J2J5T5') ,
 (00017, 'Lagacé', 'Jean-Pierre', 'jplagace@outlook.com', 4502223333, 572, 'ruegg', 'Gaspé', 'QC', 'Canada', 'J3J5T5') ,
 (00018, 'Lalancette', 'Maurice', 'mlalancette@gmail.com', 4509659654, 573, 'ruehh', 'Chioutimi', 'QC', 'Canada', 'J4J5T5') ,
 (00019, 'Brunet', 'Cathy', 'cbrunet@gmail.ca', 4501212122, 574, 'rueii', 'LaTuque', 'QC', 'Canada', 'J7J6T5') ,
 (00020, 'Couillard', 'Éloi', 'ecouillard@gmail.com', 4509548543, 575, 'ruejj', 'Oka', 'QC', 'Canada', 'J7J7T5') ,
 (00021, 'Legault', 'Catherine', 'clegault@gmail.com', 4503243546, 576, 'ruekk', 'Laval', 'QC', 'Canada', 'J7J8T5') ,
 (00022, 'Lisée', 'Nicole', 'nlisee@gmail.ca', 4501233211, 577, 'ruell', 'Mirabel', 'QC', 'Canada', 'J7J9T5') ,
 (00023, 'Marois', 'Paulette', 'pmarois@gmail.com', 4501111432, 578, 'ruemm', 'Deux-Montagnes', 'QC', 'Canada', 'J7J0T5') ,
 (00024, 'Ménard', 'Rose', 'rmenard@gmail.com', 4501111667, 579, 'ruenn', 'Saint-Jérôme', 'QC', 'Canada', 'J7J1T5') ,
 (00025, 'Samson', 'Margot', 'msamson@gmail.ca', 4501111177, 580, 'ruejjj', 'Dorval', 'QC', 'Canada', 'J7J2T5') ,
 (00026, 'Bergeron', 'Chantale', 'cbergeron@gmail.com', 4501111188, 581, 'ruemm', 'Saint-Laurent', 'QC', 'Canada', 'J7J3T5') ,
 (00027, 'Delorme', 'Maude', 'mdelorme@gmail.com', 4501111199, 582, 'ruenn', 'Shefford', 'QC', 'Canada', 'J7J4T5') ,
 (00028, 'Carbonneau', 'Camille', 'ccarbonneau@gmail.com', 4501111110, 583, 'ruepp', 'Saint-Eustache', 'QC', 'Canada', 'M7J5T5') ,
 (00029, 'Richard', 'Caroline', 'crichard@gmail.ca', 4508585432, 584, 'rueqq', 'Blainville', 'QC', 'Canada', 'N7J5T5') ,
 (00030, 'Mainville', 'Jean', 'jmainville@hotmail.com', 4509090999, 585, 'ruqoo', 'Montréal', 'QC', 'Canada', 'O7J5T5') ,
 (00031, 'Brulé', 'Pierre', 'pbrule@gmail.com', 4506777777, 586, 'ruerr', 'Chicoutimi', 'QC', 'Canada', 'P7J5T5') ,
 (00032, 'Koclas', 'Jérôme', 'jkoclas@gmail.com', 4507773333, 587, 'rueaaa', 'Bécancourt', 'QC', 'Canada', 'Q7J5T5') ,
 (00033, 'Proulx', 'Gaétan', 'gproulx@hotmail.ca', 4508888888, 588, 'ruebbb', 'Berthier', 'QC', 'Canada', 'R7J5T5') ,
 (00034, 'Beauvillier', 'Vincent', 'vbeauvillier@gmail.com', 4509999999, 589, 'rueccc', 'Saint-Janvier', 'QC', 'Canada', 'S7J5T5') ,
 (00035, 'Trudeau', 'Sean', 'strudeau@gmail.com', 4509767644, 590, 'rueddd', 'Ottawa', 'ON', 'Canada', 'T7J5T5') ,
 (00036, 'Mulroney', 'Jason', 'jmulroney@gmail.com', 4505556667, 591, 'rueffff', 'Halifax', 'NB', 'Canada', 'U7J5T5') ,
 (00037, 'Lévesque', 'Camille', 'clevesque@gmail.com', 4500002223, 592, 'ruegfff', 'Halifax', 'NB', 'Canada', 'V7J5T5') ,
 (00038, 'Maurais', 'Nahuel', 'nmaurais@gmail.ca', 4507654321, 593, 'ruegag', 'Laval', 'QC', 'Canada', 'W7J5T5') ,
 (00039, 'Pépin', 'Raphaël', 'rpepin@gmail.com', 4501234567, 594, 'ruemnmn', 'Yellowknife', 'YK', 'Canada', 'X7J5T5') ,
 (00040, 'Lépine', 'Stéphane', 'slepine@hotmail.com', 4506565444, 595, 'ruemnb', 'Oka', 'QC', 'Canada', 'Y7J5T5') ,
 (00041, 'Gendron', 'Martin', 'mgendron@outlook.com', 4504733333, 596, 'ruenbvc', 'Dorval', 'QC', 'Canada', 'Z7J5T5') ,
 (00042, 'Piché', 'Louis', 'lpiche@gmail.com', 4508787777, 597, 'ruenbhg', 'Montréal', 'QC', 'Canada', 'A7J5T5') ,
 (00043, 'Laprise', 'Jean-Guy', 'jglaprise@gmail.com', 4504739244, 598, 'ruemnjh', 'Saint-Marc', 'QC', 'Canada', 'B7J5T5') ,
 (00044, 'Michel', 'François', 'fmichel@hotmail.com', 4388328231, 599, 'ruezxf', 'Longeuil', 'QC', 'Canada', 'C7J5T5') ,
 (00045, 'Lemay', 'Éric', 'elemay@gmail.com', 4503234545, 600, 'rueasdf', 'Bois-des-Filions', 'QC', 'Canada', 'D7J5T5') ,
 (00046, 'Trudeau', 'Paul', 'ptrudeau@gmail.com', 4508443356, 601, 'ruezzzzz', 'Assomption', 'QC', 'Canada', 'E7J5T5') ,
 (00047, 'Piché', 'Jonathan', 'jpiche@gmail.com', 4506565454, 602, 'ruelkm', 'Terrebonne', 'QC', 'Canada', 'F7J5T5') ,
 (00048, 'Buie', 'Antoine', 'abuie@hotmailgmail.com', 4505555555, 603, 'ruejhg', 'Repentigny', 'QC', 'Canada', 'G7J5T5') ,
 (00049, 'Tremblay', 'Félix', 'ftremblay@gmail.com', 4501212222, 604, 'ruegfg', 'Lavaltrie', 'QC', 'Canada', 'H7J5T5') ,
 (00050, 'Valcourt', 'Laurier', 'lvalcourt@gmail.com', 4509876543, 605, 'ruett', 'Saint-Laurent', 'QC', 'Canada', 'I7J5T5') ,
 (00051, 'Dagenais', 'Jean-Sébastien', 'jsdagenais@gmail.com', 4501212230, 606, 'ruet', 'Gaspé', 'QC', 'Canada', 'J7A5T5') ,
 (00052, 'Sauvé', 'Jean-Paul', 'jpsauve@hotmail.com', 4509343141, 607, 'ruex', 'Shawinigan', 'QC', 'Canada', 'J7B5T5') ,
 (00053, 'Richard', 'Marc-André', 'marichard@gmail.com', 4505454544, 608, 'ruey', 'Trois-Rivières', 'QC', 'Canada', 'J7C5T5') ,
 (00054, 'Deltel','Gérard', 'gdeltel@gmail.com', 4500004455, 777, 'ruez', 'Québec', 'QC', 'Canada', 'J7D5T5');


-- Ajout de 7 éléments, exceptionnellement, car le nombre de catérogie est limité.
INSERT INTO Catégories (nomCatégorie) Values ('Meubles');
INSERT INTO Catégories (nomCatégorie) Values ('Multimédia');
INSERT INTO Catégories (nomCatégorie) Values ('Informatique');
INSERT INTO Catégories (nomCatégorie) Values ('Article de bureau');
INSERT INTO Catégories (nomCatégorie) Values ('Décoration');
INSERT INTO Catégories (nomCatégorie) Values ('Objet de luxe');
INSERT INTO Catégories (nomCatégorie) Values ('Autre');

-- Ajout de 54 éléments

INSERT INTO object (object_ID, nomObjet, nomcatégorie, prixDemandé, estVendu, spécifications) Values 
  (00001, 'Chaise', 'Meubles', '19.40', 'false', 'bleue'),
  (00002, 'Chaise', 'Meubles', '15.10', 'false', 'rouge'),
  (00003, 'Chaise', 'Meubles', '17.20', 'true', 'propre'),
  (00004, 'Table', 'Meubles', '40.90', 'true', 'petite'),
  (00005, 'Table', 'Meubles', '45.40', 'false', 'grand'),
  (00006, 'Table', 'Décoration', '43.50', 'false', 'rouge'),
  (00007, 'Lumière', 'Décoration', '3.70', 'false', 'verte'),
  (00008, 'Lumière', 'Décoration', '4.60', 'false', 'mauve'),
  (00009, 'Lumière', 'Décoration', '5.80', 'false', '55kg'),
  (00010, 'Voiture', 'Objet de luxe', '5349.99', 'false', '6cm x 5cm'),
  (00011, 'Voiture', 'Objet de luxe', '7009.99', 'true', 'bleue et rouge'),
  (00012, 'Voiture', 'Objet de luxe', '10454.90', 'false', 'doux'),
  (00013, 'Pantalon', 'Autre', '10.34', 'true', ''),
  (00014, 'Pantalon', 'Autre', '12.73', 'true', ''),
  (00015, 'Pantalon', 'Autre', '15.28', 'false', ''),
  (00016, 'Cadre', 'Décoration', '134.10', 'false', 'bleue'),
  (00017, 'Cadre', 'Décoration', '120.99', 'false', 'De 150cm'),
  (00018, 'Cadre', 'Décoration', '90.99', 'true', 'bleue'),
  (00019, 'Ordinateur', 'Informatique', '500.30', 'false', 'En bon état'),
  (00020, 'Ordinateur', 'Informatique', '490.44', 'false', 'Joli'),
  (00021, 'Ordinateur', 'Informatique','340.56', 'false', 'Performant'),
  (00022, 'Tapis', 'Décoration', '20.22', 'true', 'Confortable'),
  (00023, 'Tapis', 'Décoration', '13.99', 'false', 'Petit'),
  (00024, 'Tapis', 'Décoration', '40.15', 'false', 'Douillet'),
  (00025, 'Divan', 'Meubles', '62.50', 'false', 'Confortable'),
  (00026, 'Divan', 'Meubles', '70.99', 'false', 'Noir'),
  (00027, 'Divan', 'Meubles', '34.82', 'true', 'En cuir'),
  (00028, 'Lit', 'Meubles', '100.99', 'true', 'Lit noir confortable'),
  (00029, 'Lit', 'Meubles', '103.00', 'false', 'De 20kg'),
  (00030, 'Lit', 'Meubles', '89.20', 'false', 'Lit noir pas confortable.'),
  (00031, 'Chandail', 'Autre', '22.22', 'false', 'À manches courtes'),
  (00032, 'Chandail', 'Autre', '10.00', 'false', 'Bleu'),
  (00033, 'Chandail', 'Autre', '16.95', 'false', 'Large'),
  (00034, 'CD', 'Multimédia', '19.25', 'false', 'De Simple Plan'),
  (00035, 'CD', 'Multimédia', '12.14', 'false', 'De Pitbull'),
  (00036, 'CD', 'Multimédia', '8.93', 'false', 'De Gilles Vignault'),
  (00037, 'DVD', 'Multimédia', '25.62', 'false', 'De James Bond'),
  (00038, 'DVD', 'Multimédia', '20.03', 'false', 'De George Lucas'),
  (00039, 'DVD', 'Multimédia', '6.99', 'false', 'En bon état'),
  (00040, 'Table', 'Meubles', '66.66', 'false', 'Basse'),
  (00041, 'Table', 'Meubles', '60.99', 'false', 'En bois'),
  (00042, 'Table', 'Meubles','50.40', 'false', 'Huilée'),
  (00043, 'Chaise', 'Meubles', '22.51', 'false', 'Confortable'),
  (00044, 'Chaise', 'Meubles', '25.64', 'false', 'En bois'),
  (00045, 'Chaise', 'Meubles', '20.00', 'true', 'Bleue'),
  (00046, 'Taille-crayon', 'Article de bureau', '3.00', 'true', 'En bon état'),
  (00047, 'Taille-crayon', 'Article de bureau', '2.95', 'false', 'Performant.'),
  (00048, 'Taille-crayon', 'Article de bureau', '4.50', 'false', 'Rouge rutilant, moyen performant.'),
  (00049, 'Télévision', 'Multimédia', '6.00', 'true', 'HD'),
  (00050, 'Télévision', 'Multimédia', '600.00', 'false', '4K'),
  (00051, 'Télévision', 'Multimédia', '634.60', 'true', '50 pouces'),
  (00052, 'Grille-pain', 'Autre', '30.02', 'true', '10 lbs'),
  (00053, 'Grille-pain', 'Autre', '34.50', 'true', 'En mauvais état'),
  (00054, 'Grille-pain', 'Autre','25.55', 'true', 'En bon état'); 


-- Ajout de 54 éléments
INSERT INTO Annonces (object_ID, userID, _date) Values 
 (00001, 00001, TO_DATE('11/06/2017', 'DD/MM/YYYY')),
 (00002, 00001, TO_DATE('29/06/2017', 'DD/MM/YYYY')),
 (00003, 00001, TO_DATE('30/06/2017', 'DD/MM/YYYY')),
 (00004, 00005, TO_DATE('30/06/2017', 'DD/MM/YYYY')),
 (00005, 00008, TO_DATE('10/08/2017', 'DD/MM/YYYY')),
 (00006, 00012, TO_DATE('26/12/2017', 'DD/MM/YYYY')),
 (00007, 00012, TO_DATE('23/01/2018', 'DD/MM/YYYY')),
 (00008, 00012, TO_DATE('24/01/2018', 'DD/MM/YYYY')),
 (00009, 00012, TO_DATE('26/12/2018', 'DD/MM/YYYY')),
 (00010, 00019, TO_DATE('03/02/2018', 'DD/MM/YYYY')),
 (00011, 00019, TO_DATE('12/02/2018', 'DD/MM/YYYY')),
 (00012, 00020, TO_DATE('19/02/2018', 'DD/MM/YYYY')),
 (00013, 00020, TO_DATE('25/02/2018', 'DD/MM/YYYY')),
 (00014, 00020, TO_DATE('27/02/2018', 'DD/MM/YYYY')),
 (00015, 00023, TO_DATE('31/03/2018', 'DD/MM/YYYY')),
 (00016, 00029, TO_DATE('23/04/2018', 'DD/MM/YYYY')),
 (00017, 00029, TO_DATE('28/05/2018', 'DD/MM/YYYY')),
 (00018, 00030, TO_DATE('11/06/2018', 'DD/MM/YYYY')),
 (00019, 00030, TO_DATE('22/06/2018', 'DD/MM/YYYY')),
 (00020, 00030, TO_DATE('08/07/2018', 'DD/MM/YYYY')),
 (00021, 00030, TO_DATE('23/08/2018', 'DD/MM/YYYY')),
 (00022, 00030, TO_DATE('13/09/2018', 'DD/MM/YYYY')),
 (00023, 00030, TO_DATE('01/10/2018', 'DD/MM/YYYY')),
 (00024, 00035, TO_DATE('12/10/2018', 'DD/MM/YYYY')),
 (00025, 00035, TO_DATE('01/12/2018', 'DD/MM/YYYY')),
 (00026, 00036, TO_DATE('01/12/2018', 'DD/MM/YYYY')),
 (00027, 00036, TO_DATE('24/01/2019', 'DD/MM/YYYY')),
 (00028, 00036, TO_DATE('28/01/2019', 'DD/MM/YYYY')),
 (00029, 00036, TO_DATE('28/01/2019', 'DD/MM/YYYY')),
 (00030, 00036, TO_DATE('29/01/2019', 'DD/MM/YYYY')),
 (00031, 00039, TO_DATE('29/01/2019', 'DD/MM/YYYY')),
 (00032, 00039, TO_DATE('30/01/2019', 'DD/MM/YYYY')),
 (00033, 00039, TO_DATE('31/01/2019', 'DD/MM/YYYY')),
 (00034, 00042, TO_DATE('31/01/2019', 'DD/MM/YYYY')),
 (00035, 00042, TO_DATE('31/01/2019', 'DD/MM/YYYY')),
 (00036, 00042, TO_DATE('02/02/2019', 'DD/MM/YYYY')),
 (00037, 00042, TO_DATE('02/02/2019', 'DD/MM/YYYY')),
 (00038, 00042, TO_DATE('02/02/2019', 'DD/MM/YYYY')),
 (00039, 00042, TO_DATE('12/02/2019', 'DD/MM/YYYY')),
 (00040, 00052, TO_DATE('17/02/2019', 'DD/MM/YYYY')),
 (00041, 00053, TO_DATE('17/02/2019', 'DD/MM/YYYY')),
 (00042, 00053, TO_DATE('18/02/2019', 'DD/MM/YYYY')),
 (00043, 00053, TO_DATE('19/02/2019', 'DD/MM/YYYY')),
 (00044, 00007, TO_DATE('04/03/2019', 'DD/MM/YYYY')),
 (00045, 00002, TO_DATE('06/03/2019', 'DD/MM/YYYY')),
 (00046, 00013, TO_DATE('07/03/2019', 'DD/MM/YYYY')),
 (00047, 00026, TO_DATE('07/03/2019', 'DD/MM/YYYY')),
 (00048, 00003, TO_DATE('11/03/2019', 'DD/MM/YYYY')),
 (00049, 00038, TO_DATE('20/04/2019', 'DD/MM/YYYY')),
 (00050, 00054, TO_DATE('26/04/2019', 'DD/MM/YYYY')),
 (00051, 00001, TO_DATE('26/04/2019', 'DD/MM/YYYY')),
 (00052, 00011, TO_DATE('26/04/2019', 'DD/MM/YYYY')),
 (00053, 00032, TO_DATE('27/04/2019', 'DD/MM/YYYY')),
 (00054, 00017, TO_DATE('28/04/2019', 'DD/MM/YYYY'));


-- Ajout de 54 éléments

INSERT INTO FaitOffre (object_ID, userID, prixOffre, statut, _date) Values 
  (00001, 00001, 6.00, 'en attente', TO_DATE('19/07/2017', 'DD/MM/YYYY')),
  (00002, 00001, 7.00, 'refusée', TO_DATE('20/08/2017', 'DD/MM/YYYY')),
  (00002, 00002, 8.00, 'refusée', TO_DATE('01/08/2017', 'DD/MM/YYYY')),
  (00002, 00002, 10.50, 'en attente', TO_DATE('04/08/2017', 'DD/MM/YYYY')),
  (00003, 00002, 19.03, 'refusée', TO_DATE('08/08/2017', 'DD/MM/YYYY')),
  (00003, 00002, 21.35, 'refusée', TO_DATE('09/09/2017', 'DD/MM/YYYY')),
  (00003, 00005, 40.46, 'acceptée', TO_DATE('28/09/2017', 'DD/MM/YYYY')),
  (00004, 00005, 42.58, 'acceptée', TO_DATE('29/09/2017', 'DD/MM/YYYY')),
  (00011, 00005, 8000.99, 'acceptée', TO_DATE('29/10/2017', 'DD/MM/YYYY')),
  (00013, 00005, 13.86, 'acceptée', TO_DATE('30/12/2017', 'DD/MM/YYYY')),
  (00014, 00005, 14.09, 'acceptée', TO_DATE('30/12/2017', 'DD/MM/YYYY')),
  (00018, 00008, 2000.00, 'refusée', TO_DATE('31/12/2017', 'DD/MM/YYYY')),
  (00018, 00009, 4560.90, 'refusée', TO_DATE('31/12/2017', 'DD/MM/YYYY')),
  (00018, 00010, 4990.61, 'refusée', TO_DATE('31/12/2017', 'DD/MM/YYYY')),
  (00018, 00010, 5000.73, 'refusée', TO_DATE('31/12/2017', 'DD/MM/YYYY')),
  (00018, 00011, 6030.20, 'acceptée', TO_DATE('31/12/2017', 'DD/MM/YYYY')),
  (00022, 00012, 12660.00, 'acceptée', TO_DATE('01/01/2018', 'DD/MM/YYYY')),
  (00025, 00015, 55.10, 'refusée', TO_DATE('12/02/2018', 'DD/MM/YYYY')),
  (00026, 00015, 52.55, 'refusée', TO_DATE('15/03/2018', 'DD/MM/YYYY')),
  (00027, 00015, 19.00, 'refusée', TO_DATE('18/03/2018', 'DD/MM/YYYY')),
  (00028, 00015, 10.33, 'refusée', TO_DATE('18/05/2018', 'DD/MM/YYYY')),
  (00028, 00015, 16.00, 'refusée', TO_DATE('19/05/2018', 'DD/MM/YYYY')),
  (00028, 00018, 26.43, 'refusée', TO_DATE('20/05/2018', 'DD/MM/YYYY')),
  (00028, 00018, 37.41, 'refusée', TO_DATE('21/07/2018', 'DD/MM/YYYY')),
  (00028, 00019, 80.96, 'refusée', TO_DATE('23/07/2018', 'DD/MM/YYYY')),
  (00028, 00019, 110.93, 'acceptée', TO_DATE('04/08/2018', 'DD/MM/YYYY')),
  (00045, 00029, 16.00, 'refusée', TO_DATE('07/08/2018', 'DD/MM/YYYY')),
  (00045, 00029, 19.30, 'refusée', TO_DATE('09/08/2018', 'DD/MM/YYYY')),
  (00045, 00029, 23.40, 'acceptée', TO_DATE('23/08/2018', 'DD/MM/YYYY')),
  (00046, 00029, 8.88, 'acceptée', TO_DATE('24/09/2018', 'DD/MM/YYYY')),
  (00047, 00029, 9.99, 'acceptée', TO_DATE('25/09/2018', 'DD/MM/YYYY')),
  (00048, 00029, 10.00, 'acceptée', TO_DATE('28/09/2018', 'DD/MM/YYYY')),
  (00049, 00031, 2.00, 'refusée', TO_DATE('30/10/2018', 'DD/MM/YYYY')),
  (00049, 00031, 10.00, 'acceptée', TO_DATE('30/10/2018', 'DD/MM/YYYY')),
  (00051, 00032, 299.79, 'refusée', TO_DATE('01/11/2018', 'DD/MM/YYYY')),
  (00051, 00033, 300.31, 'refusée', TO_DATE('01/11/2018', 'DD/MM/YYYY')),
  (00051, 00035, 312.41, 'refusée', TO_DATE('12/12/2018', 'DD/MM/YYYY')),
  (00051, 00038, 314.89, 'refusée', TO_DATE('14/01/2019', 'DD/MM/YYYY')),
  (00051, 00038, 419.82, 'refusée', TO_DATE('18/01/2019', 'DD/MM/YYYY')),
  (00051, 00038, 522.94, 'acceptée', TO_DATE('19/01/2019', 'DD/MM/YYYY')),
  (00051, 00042, 540.00, 'refusée', TO_DATE('11/01/2019', 'DD/MM/YYYY')),
  (00051, 00042, 550.40, 'refusée', TO_DATE('20/02/2019', 'DD/MM/YYYY')),
  (00051, 00042, 700.20, 'acceptée', TO_DATE('28/02/2019', 'DD/MM/YYYY')),
  (00052, 00042, 22.20, 'refusée', TO_DATE('29/02/2019', 'DD/MM/YYYY')),
  (00052, 00044, 26.70, 'refusée', TO_DATE('30/02/2019', 'DD/MM/YYYY')),
  (00052, 00044, 29.80, 'refusée', TO_DATE('31/02/2019', 'DD/MM/YYYY')),
  (00052, 00045, 32.90, 'acceptée', TO_DATE('01/03/2019', 'DD/MM/YYYY')),
  (00053, 00046, 11.00, 'refusée', TO_DATE('01/03/2019', 'DD/MM/YYYY')),
  (00053, 00048, 12.92, 'refusée', TO_DATE('01/03/2019', 'DD/MM/YYYY')),
  (00053, 00048, 13.31, 'refusée', TO_DATE('03/03/2019', 'DD/MM/YYYY')),
  (00053, 00048, 40.51, 'acceptée', TO_DATE('05/03/2019', 'DD/MM/YYYY')),
  (00054, 00052, 20.32, 'refusée', TO_DATE('11/04/2019', 'DD/MM/YYYY')),
  (00054, 00054, 24.59, 'refusée', TO_DATE('18/04/2019', 'DD/MM/YYYY')),
  (00054, 00054, 26.98, 'acceptée', TO_DATE('23/04/2019', 'DD/MM/YYYY'));

-- Ajout de 49 éléments
INSERT INTO Estimation (object_ID, expert_ID, _date, prixEstimation) Values 
  (00001, 00022, TO_DATE('19/07/2017', 'DD/MM/YYYY'), 6.00),
  (00002, 00022, TO_DATE('20/08/2017', 'DD/MM/YYYY'), 7.00),
  (00002, 00022, TO_DATE('01/08/2017', 'DD/MM/YYYY'), 8.00),
  (00002, 00022, TO_DATE('04/08/2017', 'DD/MM/YYYY'), 10.50),
  (00003, 00022, TO_DATE('08/08/2017', 'DD/MM/YYYY'), 19.03),
  (00003, 00022, TO_DATE('09/09/2017', 'DD/MM/YYYY'), 21.35),
  (00003, 00022, TO_DATE('28/09/2017', 'DD/MM/YYYY'), 40.46),
  (00004, 00022, TO_DATE('29/09/2017', 'DD/MM/YYYY'), 42.58),
  (00011, 00022, TO_DATE('29/10/2017', 'DD/MM/YYYY'), 43.61),
  (00013, 00045, TO_DATE('30/12/2017', 'DD/MM/YYYY'), 44.86),
  (00014, 00045, TO_DATE('30/12/2017', 'DD/MM/YYYY'), 46.09),
  (00018, 00045, TO_DATE('31/12/2017', 'DD/MM/YYYY'), 100.00),
  (00022, 00048, TO_DATE('01/01/2018', 'DD/MM/YYYY'), 12660.00),
  (00025, 00048, TO_DATE('12/02/2018', 'DD/MM/YYYY'), 8.10),
  (00026, 00048, TO_DATE('15/03/2018', 'DD/MM/YYYY'), 8.55),
  (00027, 00048, TO_DATE('18/03/2018', 'DD/MM/YYYY'), 9.00),
  (00028, 00003, TO_DATE('18/05/2018', 'DD/MM/YYYY'), 10.33),
  (00028, 00003, TO_DATE('19/05/2018', 'DD/MM/YYYY'), 16.00),
  (00028, 00003, TO_DATE('20/05/2018', 'DD/MM/YYYY'), 6.43),
  (00028, 00003, TO_DATE('21/07/2018', 'DD/MM/YYYY'), 7.41),
  (00028, 00022, TO_DATE('23/07/2018', 'DD/MM/YYYY'), 400.96),
  (00028, 00022, TO_DATE('04/08/2018', 'DD/MM/YYYY'), 510.93),
  (00045, 00022, TO_DATE('07/08/2018', 'DD/MM/YYYY'), 90.00),
  (00045, 00051, TO_DATE('09/08/2018', 'DD/MM/YYYY'), 92.30),
  (00045, 00051, TO_DATE('23/08/2018', 'DD/MM/YYYY'), 93.40),
  (00046, 00051, TO_DATE('24/09/2018', 'DD/MM/YYYY'), 94.50),
  (00047, 00051, TO_DATE('25/09/2018', 'DD/MM/YYYY'), 100.80),
  (00048, 00051, TO_DATE('28/09/2018', 'DD/MM/YYYY'), 106.99),
  (00049, 00022, TO_DATE('30/10/2018', 'DD/MM/YYYY'), 6.00),
  (00049, 00022, TO_DATE('30/12/2018', 'DD/MM/YYYY'), 8.00),
  (00051, 00022, TO_DATE('01/10/2018', 'DD/MM/YYYY'), 20.79),
  (00051, 00022, TO_DATE('01/11/2018', 'DD/MM/YYYY'), 21.31),
  (00051, 00045, TO_DATE('12/12/2018', 'DD/MM/YYYY'), 20.41),
  (00051, 00045, TO_DATE('14/01/2019', 'DD/MM/YYYY'), 14.89),
  (00051, 00045, TO_DATE('18/01/2019', 'DD/MM/YYYY'), 19.82),
  (00051, 00045, TO_DATE('19/01/2019', 'DD/MM/YYYY'), 22.94),
  (00051, 00045, TO_DATE('11/01/2019', 'DD/MM/YYYY'), 40.00),
  (00051, 00045, TO_DATE('20/02/2019', 'DD/MM/YYYY'), 43.40),
  (00051, 00045, TO_DATE('28/02/2019', 'DD/MM/YYYY'), 44.20),
  (00052, 00048, TO_DATE('29/02/2019', 'DD/MM/YYYY'), 52.20),
  (00052, 00048, TO_DATE('30/02/2019', 'DD/MM/YYYY'), 26.70),
  (00052, 00048, TO_DATE('31/02/2019', 'DD/MM/YYYY'), 30.80),
  (00053, 00048, TO_DATE('01/03/2019', 'DD/MM/YYYY'), 5.00),
  (00053, 00048, TO_DATE('02/03/2019', 'DD/MM/YYYY'), 2.92),
  (00053, 00048, TO_DATE('03/03/2019', 'DD/MM/YYYY'), 3.31),
  (00053, 00048, TO_DATE('05/03/2019', 'DD/MM/YYYY'), 8.51),
  (00054, 00022, TO_DATE('11/04/2019', 'DD/MM/YYYY'), 30.32),
  (00054, 00022, TO_DATE('18/04/2019', 'DD/MM/YYYY'), 24.59),
  (00054, 00022, TO_DATE('23/04/2019', 'DD/MM/YYYY'), 26.98);

