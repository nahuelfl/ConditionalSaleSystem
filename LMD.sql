-- Fichier LMD.sql
-- Projet IFT-2935
-- Camille Claing, Alexandre Dufour, Rapha�l Lajoie & Nahuel Londono


-- ______________________________Requ�tes______________________________

--1) Donne la liste des objets vendu par les utilisateurs.

WITH objVendu AS (SELECT userID, nomObjet FROM Produits WHERE (estVendu = 'true')
SELECT * FROM Utilisateurs natural JOIN objVendu;

--2) Donne les utilisateurs n�ayant pas d�objet � vendre.

SELECT nom, prenom, userID FROM Utilisateurs WHERE NOT EXISTS 
(SELECT * FROM Annonces WHERE Utilisateurs.userID = Annonces.userID);

--3) Donne l�utilisateur ayant le plus d�objet vendu.

WITH nbrVente AS (SELECT userID, count(object_ID) AS ventes FROM Annonces GROUP BY userID),
maxVente as (SELECT * from nbrVente where ventes = (Select Max(ventes) from nbrVente))
SELECT nom, prenom, MAX(ventes) FROM nbrVente NATURAL JOIN Utilisateurs;

--4) Donne le nombre d�offres faite sur les produits de la cat�gorie �Multim�dia� d�un utilisateur.

WITH objInfo AS (SELECT object_ID FROM object WHERE nomCat�gorie = 'Multim�dia'),
           offresInfo  AS(SELECT object_ID, userID, FROM FaitOffre NATURAL JOIN objInfo),
           nbrOffre AS(SELECT userID, count(object_ID) as nombre FROM offresInfo GROUP BY userID)
SELECT nom, prenom FROM Utilisateurs NATURAL JOIN nbrOffre;

--5) Donne le ou les utilisateurs ayant fait des offres inf�rieures au prix de l�estimation pour un produit.

WITH OffresProduit AS (SELECT object_ID, userID, prixoffre, prixEstimation FROM faitOffre NATURAL JOIN estimation),
        OffresValides AS (SELECT * FROM OffresProduit WHERE prixOffre < prixEstimation),
        offresObj as (Select * from offresValides NATURAL JOIN object),
        offresUtil as (Select * from utilisateurs NATURAL JOIN offresObj)
        SELECT nom, prenom, userID, nomObjet, prixOffre, prixEstimation FROM OffresUtil;

--6) Donne le nom de l�utilisateur habitant au Canada qui a achet� le plus de produits de la cat�gorie � Autres �.

WITH userCanada AS (SELECT userID, nom, prenom FROM Utilisateurs WHERE pays = 'Canada'),
           objInfo AS (SELECT object_ID FROM object WHERE nomCat�gorie = 'Autres' AND estVendu = 'true'),
           vendu AS (SELECT object_ID, userID FROM faitOffre WHERE statut = 'accept�e'),
           achat AS (SELECT userID, object_ID, nom, prenom FROM userCanada NATURAL JOIN vendu),
           nbrAchat AS (SELECT nom, prenom, count(object_ID) AS nbAchats FROM achat GROUP BY userID, nom, prenom),
           max AS (SELECT Max(nbAchats) AS max FROM nbrAchat)
SELECT nom, prenom, max FROM (nbrAchat INNER JOIN max ON nbrAchat.nbAchats = max.max);


--7) Donne tous les identifiants et la cat�gorie, le nom et sp�cifications, des objets vendus affich�s � plus de 50$, estim�s par un expert ayant comme nom de famille �Tremblay �. De plus, les objets doivent �tre bleus.

WITH Tremblay AS (SELECT userID FROM utilisateurs WHERE nom = 'Tremblay'),
      experts As (SELECT userID, object_ID FROM Tremblay NATURAL JOIN Estimation),
      obj AS (SELECT object_ID, nomObjet, nomcat�gorie, sp�cifications FROM object WHERE (estVendu = 'true' AND prixdemand� > 50.00 AND sp�cifications LIKE '%bleu%'))
  SELECT userID, nomcat�gorie, nomObjet, sp�cifications FROM experts NATURAL JOIN obj;

--8) Donnez toutes les informations relatives � l�adresse des acheteurs qui ont fait une annonce, au Boxing day (le 26 d�cembre 2017 ou 2018).

WITH annonces AS (SELECT DISTINCT userID FROM Annonces WHERE ( _date = '2018/12/26' OR _date = '2017/12/26')),
            bonAcheteurs AS (SELECT codepostal, numeroAdr, rue, ville, province, pays, userID FROM Utilisateurs NATURAL JOIN annonces)
          SELECT codepostal, numeroAdr, rue, ville, province, pays FROM bonAcheteurs;

--9) Pour la cat�gorie � Article de bureau �, donner la liste des objets qui ont n�ont pas �t� achet�s en ordre croissant de prix demand�. De plus, l�annonceur doit habiter au Qu�bec.

WITH objBureau AS (SELECT object_ID, nomObjet, prixdemand�, sp�cifications FROM object WHERE (nomCat�gorie = 'Article de bureau' AND estVendu = 'false')),
      objet_seller AS (SELECT nomObjet, sp�cifications, userID, prixdemand� FROM annonces NATURAL JOIN objBureau),
      Queb AS (SELECT userID FROM utilisateurs WHERE province = 'QC')
  SELECT nomObjet, sp�cifications, prixdemand� FROM objet_seller NATURAL JOIN Queb Order By(prixdemand�);


--10) Donner la liste des offres sur les lits noirs (qui ont �t� achet�s).

WITH produitsPr�cis AS (SELECT object_ID FROM object WHERE (estVendu = 'true' AND sp�cifications LIKE '%Lit%noir%'))
           SELECT * FROM produitsPr�cis NATURAL JOIN FaitOffre;

-- D'AUTRES REQU�TES qui sont dans l'application...

-- Requ�tes m�thode affiche_annonce(categorie, prix_max) retourne object_Id, nomObjet, sp�cifications, prixDemand�, ville, codePostal des objets en vente (donc estim�s) 
-- de la bonne cat�gorie dont le prix est inf�rieur ou �gal � prix_max.

--Ex 1 : 
--String SQL = "with objet_concern�s as (select object_ID, nomObjet, sp�cifications, prixDemand� from object where (estvendu = 'false' AND prixDemand� < "+prix_max+contrainte_cat+ ")),\n" +
--"       objet_estim as (select distinct object_ID from estimation),\n" +
--"       obj_vente as (select * from objet_concern�s natural join objet_estim),\n" +
--"       Ann as(select object_ID, nomObjet, sp�cifications, prixDemand�, userID from obj_vente natural join Annonces)\n" +
--"       select object_Id, nomObjet, sp�cifications, prixDemand�, ville, codePostal from Ann natural join utilisateurs;"; 


--Ex 2 :
--   String SQL_statut = "with estims as (Select object_ID, prixEstimation from estimation where object_ID = "+objectID+")\n" +
-- "       select Max(prixEstimation) as maxEstimation from estims;";
                
--         try{ Connection conn = connect();
--             Statement stmt = conn.createStatement();
--             ResultSet rs =stmt.executeQuery(SQL_statut);
            
--             rs.next();
            
--             double prixEstim = rs.getInt("maxEstimation");
            
--             if (prix_offert >= prixEstim){ statut = "accept�e";
--             stmt.executeUpdate("Delete from annonces where object_id ="+objectID+";");
--             stmt.executeUpdate("Update object set estVendu = 'true'where object_ID = "+objectID+";");
--             } //acceptation automatique pour offre > estimations.
            
--             else {statut = "en attente";}
            
--             String SQL = "Insert into FaitOffre (object_ID, userID, prixOffre, statut, _date) values (" +objectID+ ","+offreur_ID+", "+prix_offert+", '"+statut+"', '"+date+"');";



