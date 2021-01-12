/*
 * CLASS SQLquery, 
 */
package ift2935_projet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


/**
* @author Camille Claing, Alexandre Dufour, Raphaël Lajoie and Nahuel Londono
*/
public class SQLquery {
    
    private final String url = "jdbc:postgresql://postgres.iro.umontreal.ca:5432/claingca";
    private final String user = "claingca_app";
    private final String password = "IN33Dapp";    
    
    
    public SQLquery(){}

    public Connection connect() {
        Connection conn = null;
        String SQL_path = "set search_path to app;";
        
        try {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(SQL_path);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
 
        return conn;
    }
     

public ObservableList<Annonces_Acheteur> affiche_annonce(String categorie, int prix_max){ 
        //Affiche les annnce concernées : object Id, nomObjet, spécifications, prix demandé et location.
        //"Toutes" retourne les annonces de toutes les catégories.
        
        String contrainte_cat = new String();
        
        if (categorie == "Toutes") {
            
            contrainte_cat = "";   
        }
        else {
            contrainte_cat = " AND nomCatégorie = '"+categorie+"'";
        }
        
        String SQL = "with objet_concernés as (select object_ID, nomObjet, spécifications, prixDemandé from object where (estvendu = 'false' AND prixDemandé < "+prix_max+contrainte_cat+ ")),\n" +
"	objet_estim as (select distinct object_ID from estimation),\n" +
"       obj_vente as (select * from objet_concernés natural join objet_estim),\n" +
"       Ann as(select object_ID, nomObjet, spécifications, prixDemandé, userID from obj_vente natural join Annonces)\n" +
"       select object_Id, nomObjet, spécifications, prixDemandé, ville, codePostal from Ann natural join utilisateurs;"; 
        
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeQuery(SQL);
            ResultSet rs = stmt.executeQuery(SQL);
            
            String format ="%-15s %-20s %-40s %-15s %-20s\n";
            String data = String.format(format + "\n", "ObjectID", "Titre annonce", "Spécifications", "Prix demandé", "Localisation");
            ObservableList<Annonces_Acheteur> annonces = FXCollections.observableArrayList();
             
            while (rs.next())
            {
                String objectId = rs.getString("object_ID");
                String objet = rs.getString("nomObjet");
                String spec = rs.getString("spécifications");
                String prix = rs.getString("prixdemandé");
                String localisation = rs.getString("ville")+"- "+rs.getString("codePostal");

                data+= String.format(format, objectId, objet, spec, prix, localisation);
                annonces.add(new Annonces_Acheteur(objectId, objet,  spec, prix, localisation));
            }
                return annonces;}
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
        return null;

    }
      

    public void faitOffre(int offreur_ID, int objectID, double prix_offert){ 
        //Est automatiquement acceptée si prix_offert est supérieur à la meilleure estimation sur l'objet
        LocalDate date = LocalDate.now();
        
        String statut = new String();
        
        String SQL_statut = "with estims as (Select object_ID, prixEstimation from estimation where object_ID = "+objectID+")\n" +
"       select Max(prixEstimation) as maxEstimation from estims;";
                
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs =stmt.executeQuery(SQL_statut);
            
            rs.next();
            
            double prixEstim = rs.getInt("maxEstimation");
            
            if (prix_offert >= prixEstim){ statut = "acceptée";
            stmt.executeUpdate("Delete from annonces where object_id ="+objectID+";");
            stmt.executeUpdate("Update object set estVendu = 'true'where object_ID = "+objectID+";");
            } //acceptation automatique pour offre > estimations.
            
            else {statut = "en attente";}
            
            String SQL = "Insert into FaitOffre (object_ID, userID, prixOffre, statut, _date) values (" +objectID+ ","+offreur_ID+", "+prix_offert+", '"+statut+"', '"+date+"');";

            stmt.executeUpdate(SQL);}
     
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
    } 

 //==============================================================================================================================   
    
    //Ajout de l'annonce à la table des annonces.
    public void propose_ad(String objId, String userId, String prixDésiré, String estimation){ 
        
        LocalDate date = LocalDate.now();
        
        String theDate = Integer.toString(date.getDayOfMonth())+ "/" + Integer.toString(date.getMonthValue()) + "/" +Integer.toString(date.getYear());
        
        //Ajout de l'estimation simulé à la table des estimation
        String SQL = "insert into estimation values (" + objId + ", " + userId + ", '" + theDate + "', " + estimation + ");";
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(SQL);
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
        }
    }   
    
      
   
    
    //Ajout de l'objet aux annonces 
    public  void insert_ad(String objId, String userId){  
        
        LocalDate date = LocalDate.now();
        
        String theDate = Integer.toString(date.getDayOfMonth())+ "/" + Integer.toString(date.getMonthValue()) + "/" +Integer.toString(date.getYear());
        
        //Ajout de l'objet aux annonces
        String SQL = "insert into annonces values (" + objId + ", " + userId + ", '" + theDate + "');";
        
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(SQL);}
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
        
    }
    
    
    //Ajout d'un objet dans la table des objets
    public  void insert_obj(String objId, String userId, String title, String cat, String price, String spec){  
        
        
        String SQL = "insert into object values (" + objId + ", '" + title + "', '" + cat + "', " + price + ", '" + false + "', '" + spec + "');";

        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(SQL);}  
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
    }
    
    
    //Va chercher les informations des produits à vendre par un utilisateur
    public ObservableList<Annonces_Vendeur> get_ad(String idUser){
      
        String SQL = "SELECT annonces.object_id, nomObjet, prixDemandé, estVendu, spécifications"
                            + " FROM  object NATURAL JOIN Annonces where (userId = " + idUser + ");";

        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            ObservableList<Annonces_Vendeur> annonces = FXCollections.observableArrayList();
        
            while (rs.next()) { 
            String objectId= rs.getString("object_ID");
            String object= rs.getString("nomObjet");
            String price =  rs.getString("prixDemandé");
            String estVendu =  rs.getString("estVendu");
            String spec =  rs.getString("spécifications");
                
            annonces.add(new Annonces_Vendeur(objectId, object,  price, estVendu, spec));
            }
            return annonces;
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    AlertBox.display("Affichage des annonces", "Un problème est survenu, les annonces ne peuvent être affichées");
                }
        return null;
    }
    
    //Affiche les offres des objets d'un utilisateurs
    public ObservableList<Offres_Vendeur> get_offer(String idUser){

        String SQL = "With ad as (Select annonces.object_ID, faitoffre.userID, prixOffre FROM Annonces JOIN FaitOffre on (annonces.object_ID = faitOffre.object_ID) where Annonces.userID = "+ idUser +")"
                + "SELECT object_ID, userID, nomObjet, prixDemandé, prixOffre FROM ad natural Join Object;";
        
        ObservableList<Offres_Vendeur> annonces = FXCollections.observableArrayList();

        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);  
            
            while(rs.next()){
            
            String objectId= rs.getString("object_ID");
            String object= rs.getString("nomObjet");
            String priceAsk =  rs.getString("prixDemandé");
            String userOffer =  rs.getString("userId");
            String offer =  rs.getString("prixOffre");
            
            annonces.add(new Offres_Vendeur(objectId, object, priceAsk, userOffer, offer));
            }
            return annonces;
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    AlertBox.display("Ajout de l'annonce", "Un problème est survenu, l'annonce n'a pu être ajouté");
                }
        
        return null;
    }
    
    //Affiche les offres des objets d'un utilisateurs
    public ObservableList<Offres_Acheteur> get_offerAcheteur(String idUser){

        String SQL = "SELECT object_ID, prixOffre, statut FROM FaitOffre where( userId = " + idUser + ");";
        
        ObservableList<Offres_Acheteur> annonces = FXCollections.observableArrayList();

        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);  
            
            while(rs.next()){
            
            String objectId= rs.getString("object_ID");
            String prixOffre =  rs.getString("prixOffre");
            String statut =  rs.getString("statut");
            
            annonces.add(new Offres_Acheteur(objectId, prixOffre, statut));
            }
            return annonces;
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
        
        return null;
    }
    
    
    //Permet d'accepter une offre faite par un utilisateur. Change le statut de l'offre pour accepté, et change l'objet pour estVendu.
    public void accept_offer(String buyerId, String boughtId){
    
        //Change le statut de l'offre accepté
        String SQL = "UPDATE faitOffre SET statut = 'accepté' Where (userID =" + buyerId + " AND object_ID = " + boughtId + ");";
                
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeQuery(SQL);
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
        }
        
        //Change le statut des autres offres pour refusé
        SQL = "With offres as (Select * from faitOffre where object_ID = "+ boughtId + ")"
                + "UPDATE offres SET statut = 'refusé' where userID NOT IN (" + buyerId + ");";
              
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeQuery(SQL);
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
        }
        
        //Change l'attribut de l'objet pour estVendu
        SQL = "UPDATE object SET estVendu = 'true' Where (object_ID ="+ boughtId +");";
                
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeQuery(SQL);
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
        }
        
        //Change l'attribut de l'objet pour estVendu
        SQL = "Delete from annonces where object_id ="+ boughtId +";)";
                
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeQuery(SQL);
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
        }
    }
    
//========================================================================================================================================================================  
   
    public int get_nextID(String attribut, String table){  // retourne le prochain user_ID
        
        String SQL = "select max(" + attribut + ") as last_ID from " + table + ";";
        
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            
            rs.next();
            int nextId = rs.getInt("last_ID")+1;
        
            return nextId;}
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
        return 0;
    }     
    
    
    
    public void add_user( String nom, String prenom, String courriel, String telephone, String numeroAdr, String rue, String ville, String province, String pays, String codePostal){
        
        int newId = get_nextID("userId", "utilisateurs") + 1;
        
        String SQL = "insert into utilisateurs values (" + newId + ", '" + nom + "', '" + prenom  + "', '" +  courriel  + "', " +  telephone  + ", " +  numeroAdr  + ", '" +  rue  + "', '" +  ville  + "', '" +  province  + "', '" +  pays  + "', '" +  codePostal +  "');"; 
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeQuery(SQL);
            AlertBox.display("Nouvel utilisateur", "Votre compte a bien été créé");
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
        }
    
    }
    
 //========================================================================================================================================================================
    
    //Affiche les annonces n'ayant pas d'estimation, donc à estimé par l'expert
    public ObservableList<Annonces_Vendeur> unestimated_Ad(){
        
        String SQL = "SELECT object_Id, nomObjet, prixDemandé, spécifications FROM Object WHERE NOT EXISTS" 
                        + "(SELECT * FROM estimation WHERE (object.object_Id = estimation.object_Id));";

        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
           
            ObservableList<Annonces_Vendeur> annonces = FXCollections.observableArrayList();
        
            while (rs.next()) { 
            String objectId= rs.getString("object_ID");
            String object= rs.getString("nomObjet");
            String price = rs.getString("prixDemandé");
            String spec =  rs.getString("spécifications");
            
                
              
            annonces.add(new Annonces_Vendeur(objectId, object, price, "", spec));
            }
            return annonces;
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
        }
        
        return null;
    
    }
   
    
    public void estimate(String userId, String objectId, String estimation){
        
        LocalDate date = LocalDate.now();
       
        String theDate = Integer.toString(date.getDayOfMonth())+ "/" + Integer.toString(date.getMonthValue()) + "/" +Integer.toString(date.getYear());
            
     
        String SQL = "insert into estimation values (" + objectId + ", " + userId + ", '" + theDate + "', " + estimation + ");";
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeQuery(SQL);
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
        }
    
    }
    
//============================================Requête par défaut==================================================
    
    //Requête #1 -- Liste des objets vendu par chaque utilisateur
    public String execute_query1(){
      
        String SQL = "WITH objVendu AS (SELECT userID, nomObjet FROM object Natural Join Annonces WHERE (estVendu = 'true'))"
                + "SELECT * FROM Utilisateurs natural JOIN objVendu;";
        
        
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
         
            String format = "%-30s %-30s %-30s\n";
            String data = String.format(format + "\n", "Nom", "Prenom", "Objet" );
            
            
            while (rs.next()) {
              
                String nom= rs.getString("nom");
                String prenom = rs.getString("prenom");
                String object =  rs.getString("nomObjet");
                
                data += String.format(format, nom, prenom, object);
                
            }
            System.out.println(data);
            
           
            return data;
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    AlertBox.display("Ajout de l'annonce", "Un problème est survenu, les annonces ne peuvent être affichées");
                }
        return null;
    }
    
    //Utilisateurs n'ayant pas d'objet à vendre
    public String execute_query2(){
      
        String SQL = "SELECT nom, prenom, userID FROM Utilisateurs WHERE NOT EXISTS "
                + "(SELECT * FROM Annonces WHERE Utilisateurs.userID = Annonces.userID);";

        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
         
            String format = "%-30s %-30s\n";
            String data = String.format(format + "\n", "Nom", "Prenom" );

            while (rs.next()) {
                
                String nom= rs.getString("nom");
                String prenom = rs.getString("prenom");
                
                data += String.format(format,nom, prenom);
                
            }
            return data;
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    AlertBox.display("Ajout de l'annonce", "Un problème est survenu, les annonces ne peuvent être affichées");
                }
        return null;
    }
    
    //Donne les utilisateurs ayant le plus de vente
    public String execute_query3(){
      
        String SQL = "WITH nbrVente AS (SELECT userID, count(object_ID) AS ventes FROM Annonces GROUP BY userID),"
                        + "maxVente as (select * from nbrVente where ventes = (Select Max(ventes) from nbrVente))"
                      + "SELECT nom, prenom, ventes as nombre FROM utilisateurs natural join maxVente;";

        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            
            String format = "%-30s %30s %30s\n";
            String data = String.format(format + "\n", "Nom", "Prenom", "Nombre d'objet vendu" );
        
            while (rs.next()) {
                
                String nom= rs.getString("nom");
                String prenom = rs.getString("prenom");
                String nombre = rs.getString("nombre");
                
                data += String.format(format, nom, prenom, nombre);
                
            }
            return data;
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    AlertBox.display("Ajout de l'annonce", "Un problème est survenu, les annonces ne peuvent être affichées");
                }
        return null;
    }
    
    //Donne le nombre d'offre sur les produits de la catégorie utilisateurs d'un utilisateurs.
    public String execute_query4(){
      
        String SQL = "WITH objInfo AS (SELECT object_ID FROM Object WHERE nomCatégorie = 'Multimédia'),"
                + "offresInfo  AS(SELECT object_ID, userID FROM FaitOffre NATURAL JOIN objInfo),"
                + "nbrOffre AS(SELECT userID, count(object_ID) as nombre FROM offresInfo GROUP BY userID)"
                + "SELECT nom, prenom, nombre FROM Utilisateurs NATURAL JOIN nbrOffre;";

        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
         

            String format = "%-30s %30s %30s\n";
            String data = String.format(format + "\n", "Nom", "Prenom", "Nombre d'offre" );
        
            while (rs.next()) {
                
                String nom= rs.getString("nom");
                String prenom = rs.getString("prenom");
                String nombre = rs.getString("nombre");
                
                data += String.format(format, nom, prenom, nombre);
               
                
            }
            return data;
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    AlertBox.display("Ajout de l'annonce", "Un problème est survenu, les annonces ne peuvent être affichées");
                }
        return null;
    }
    
    
    public String execute_query5(){
      
        String SQL = "WITH OffresProduit AS (SELECT object_ID, userID, prixoffre, prixEstimation FROM faitOffre NATURAL JOIN estimation),"
                + "OffresValides AS (SELECT * FROM OffresProduit WHERE prixOffre < prixEstimation),"
                + "offresObj as (Select * from offresValides NATURAL JOIN object),"
                + "offresUtil as (Select * from utilisateurs NATURAL JOIN offresObj)"
                + "SELECT nom, prenom, userID, nomObjet, prixOffre, prixEstimation FROM OffresUtil;";

        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
         
            String format = "%-20s %-20s %-20s %-20s %-20s %-20s\n";
            String data = String.format(format, "Prenom", "Nom", "userID", "Objet", "Prix Offert", "Prix Estimé");

            while (rs.next()) {
                
                String prenom = rs.getString("prenom");
                String nom = rs.getString("nom");
                String userId = rs.getString("userId");
                String objet = rs.getString("nomObjet");
                String prixO = rs.getString("prixOffre");
                String prixE = rs.getString("prixEstimation");
                
                
                data += String.format(format, prenom, nom, userId, objet, prixO, prixE);
                
            }
            return data;
        }
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    AlertBox.display("Ajout de l'annonce", "Un problème est survenu, les annonces ne peuvent être affichées");
                }
        return null;
    }
    
    //Donne le nom de la personne habitant au Canada ayant acheté le plus d'objet de la catégorie Autres.
    public String execute_query6(){ 

        String SQL = "WITH userCanada AS (SELECT userID, nom, prenom FROM Utilisateurs WHERE pays = 'Canada'),\n" +
"           objInfo AS (SELECT object_ID FROM object WHERE nomCatégorie = 'Autres' AND estVendu = 'true'),\n" +
"           vendu AS (SELECT object_ID, userID FROM faitOffre WHERE statut = 'acceptée'),\n" +
"           achat AS (SELECT userID, object_ID, nom, prenom FROM userCanada NATURAL JOIN vendu),\n" +
"           nbrAchat AS (SELECT nom, prenom, count(object_ID) AS nbAchats FROM achat GROUP BY userID, nom, prenom),\n" +
"           max AS (SELECT Max(nbAchats) AS max FROM nbrAchat)\n" +
"           SELECT nom, prenom, max FROM (nbrAchat INNER JOIN max ON nbrAchat.nbAchats = max.max);";

 
        try {Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);
                
                String format = "%-30s %30s %30s\n";
                String data = String.format(format + "\n", "Prenom", "nom", "Nombre d'objet" );
                
                while(rs.next()){
                    String prenom = rs.getString("prenom");
                    String nom = rs.getString("nom");
                    String max = rs.getString("max");
             
                data += String.format(format, prenom, nom, max);
                
                }
                
            return data;
        }
        
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
     return null;
    } 
    
    public String execute_query7(){ 
                
        String SQL = "WITH Tremblay AS (SELECT userID FROM utilisateurs WHERE nom = 'Tremblay'),\n" +
"      experts As (SELECT userID, object_ID FROM Tremblay NATURAL JOIN Estimation),\n" +
"      obj AS (SELECT object_ID, nomObjet, nomcatégorie, spécifications FROM object WHERE (estVendu = 'true' AND prixdemandé > 50.00 AND spécifications LIKE '%bleu%'))\n" +
"      SELECT userID, nomcatégorie, nomObjet, spécifications FROM experts NATURAL JOIN obj;";
        
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeQuery(SQL);
            ResultSet rs = stmt.executeQuery(SQL);
            
            String format ="%-15s %-20s %-20s %-30s\n";
            String data = String.format(format + "\n", "userID", "nom catégorie", "nom objet", "Spécifications");

            while (rs.next()){
                
            String userId = rs.getString("userID");
            String categorie = rs.getString("nomcatégorie");
            String objet = rs.getString("nomObjet");
            String spec = rs.getString("spécifications");
            
                data+= String.format(format, userId, categorie, objet, spec);
               
            }
        
                return data;}
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
        return null;

    }
    
 
    public String execute_query8(){ 
                
        String SQL = "WITH annonces AS (SELECT DISTINCT userID FROM Annonces WHERE ( _date = '2018/12/26' OR _date = '2017/12/26')),\n" +
                "bonAcheteurs AS (SELECT codepostal, numeroAdr, rue, ville, province, pays, userID FROM Utilisateurs NATURAL JOIN annonces)\n" +
                "SELECT codepostal, numeroAdr, rue, ville, province, pays FROM bonAcheteurs;";
        
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeQuery(SQL);
            ResultSet rs = stmt.executeQuery(SQL);
            
            String format = "%-15s %-15s %-15s %-15s %-15s %-15s\n";
            String data = String.format(format + "\n", "Numéro Adr", "rue", "ville", "code postal", "province", "pays");

            while (rs.next()){
                
            String adresse = rs.getString("numeroAdr");
            String rue = rs.getString("rue");
            String ville = rs.getString("ville");
            String codePostal = rs.getString("codePostal");
            String province = rs.getString("province");
            String pays = rs.getString("pays");
            
            data+= String.format(format, adresse, rue, ville, codePostal, province, pays);

                }
            return data;}

            catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
        return null;
    }
           
    public String execute_query9(){ 
        
        String SQL = "WITH objBureau AS (SELECT object_ID, nomObjet, prixdemandé, spécifications FROM object WHERE (nomCatégorie = 'Article de bureau' AND estVendu = 'false')),\n" +
"      objet_seller AS (SELECT nomObjet, spécifications, userID, prixdemandé FROM annonces NATURAL JOIN objBureau),\n" +
"      Queb AS (SELECT userID FROM utilisateurs WHERE province = 'QC')\n" +
"  SELECT nomObjet, spécifications, prixdemandé FROM objet_seller NATURAL JOIN Queb Order By(prixdemandé);";
        
 
        try {Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL); 
            
            String format = "%-20s %-40s %-20s\n";
            String data = String.format(format + "\n", "nomObjet", "Spécifications", "Prix demandé");
           
            while (rs.next()){
            
                String objet = rs.getString("nomObjet");
                String spec = rs.getString("spécifications");
                String prix = rs.getString("prixDemandé");
                
            data+= String.format(format, objet, spec, prix);
        
            } 
        return data;}
            
            catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        
        return null;
        
    }    
    
    public String execute_query10(){ 
                
        String SQL = "WITH produitsPrécis AS (SELECT object_ID FROM object WHERE (estVendu = 'true' AND spécifications LIKE '%Lit%noir%'))\n" +
    "       SELECT * FROM produitsPrécis NATURAL JOIN FaitOffre;";
        
        try{ Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeQuery(SQL);
            ResultSet rs = stmt.executeQuery(SQL);
            
            String format = "%-20s %-20s %-20s %-20s %-20s\n";
            String data = String.format(format + "\n", "Faite par user", "Object ID", "Prix offert", "Statut", "Date");

            while (rs.next()){
                
            String userId = rs.getString("userId");
            String objectId = rs.getString("object_Id");
            String prix = rs.getString("prixOffre");
            String statut = rs.getString("statut");
            String date = rs.getString("_date");
                
            data+= String.format(format, userId, objectId, prix, statut, date);
               
            }
        
                return data;}
        catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
        return null;
    }
    

}


