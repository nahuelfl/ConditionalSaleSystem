/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ift2935_projet;

import com.sun.glass.ui.View;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 

/**
 *
 * @author Alexandre Dufour, Camille Claing, Nahuel Londono, Raphael Lajoie
 */
public class Vendeur extends Application{
    
    Stage window;       // Stage attribute of Main     
    
    int width = 1000;    // Width attribute of the app window
    int height = 700;   // Height attribute of the app window
    
    String userId;
    
    VBox root;
    
    ScrollPane sp;
    
    SQLquery query;
    
    //Déclaration des éléments de la table Annonces
    TableView<Annonces_Vendeur> tableAnnonces;
    

    
    //Déclaration des éléments de la table Offre.
    TableView<Offres_Vendeur> tableOffres;
    


   
    
    //Buttons
    private Vendeur seller;

    //Button backButton = new Button("Go back");
    //OnBackButtonListener onBack;
    
    Button backButton = new Button("Retour");
    
    Button adButton = new Button("Demander une estimation");

    Button acceptOfferButton = new Button("Accepter l'offre");
    
    public Vendeur(String userId){
        this.userId = userId;

        seller = this;
        

        query = new SQLquery();
        sp = new ScrollPane();
        GridPane.setConstraints(sp, 10, 1, 15, 20);
        root = new VBox();
        
        tableAnnonces = new TableView<>();
       
        tableOffres = new TableView<>();
            
    }
    
    
    @Override
    public void start(Stage primaryStage) 
    {
        window = primaryStage;
        sellerScene();
    }
    

    
    private void sellerScene()
    {
        // Initialisaiton du gridPane
       GridPane grid = new GridPane();
       grid.setPadding(new Insets(10, 10, 10, 10));
       grid.setAlignment(Pos.CENTER);
       grid.setVgap(8);
       grid.setHgap(10);
       
       //Ajouter une Annonce:
       // New Ad label
       Label idLabel = new Label("Identifiant: " + userId);
       GridPane.setConstraints(idLabel, 0, 0);
       
        // Back Button

       backButton.setOnAction(e ->Main.Instance.userValidated(userId));
 
        //backButton.setOnAction(e ->{
        //        if(onBack != null)
        //            onBack.onClick();});

       GridPane.setConstraints(backButton, 0, 20);
       
       

//-------------------------------------------------------------------------------

       //Ajouter une Annonce:
       // New Ad label
       Label newAdLabel = new Label("Ajouter une annonce:");
       GridPane.setConstraints(newAdLabel, 0, 3);
       
       // Title Label
       Label titleLabel = new Label("Titre de l'annonce:");
       GridPane.setConstraints(titleLabel, 0, 5);
       
       // Title Input
       TextField titleInput = new TextField("");
       titleInput.setPromptText("Titre");
       GridPane.setConstraints(titleInput, 1, 5, 3, 1);
       
       // Categori Label
       Label catLabel = new Label("Catégorie de l'objet:");
       GridPane.setConstraints(catLabel, 0, 6);
       
       // Categorie input
       MenuButton catInput = new MenuButton("Choisir une catégorie");

       
       MenuItem veh = new MenuItem("Véhicule");
       MenuItem meubles = new MenuItem("Meubles");
       MenuItem info = new MenuItem("Informatique");
       MenuItem multi = new MenuItem("Multimédia");
       MenuItem autre = new MenuItem("Autres");
       catInput.getItems().addAll(veh, meubles, info, multi, autre);
       //Affichage de la catégorie choisie
       veh.setOnAction(e->{
           catInput.setText(veh.getText());
       });
       meubles.setOnAction(e->{
           catInput.setText(meubles.getText());
       });
       info.setOnAction(e->{
           catInput.setText(info.getText());
       });
       multi.setOnAction(e->{
           catInput.setText(multi.getText());
       });
       autre.setOnAction(e->{
           catInput.setText(autre.getText());
       });

       GridPane.setConstraints(catInput, 1, 6, 2, 1);
       
       // Price Label
       Label priceLabel = new Label("Prix demandé:");
       GridPane.setConstraints(priceLabel, 0, 7);
       
       // Price input
       TextField priceInput = new TextField("");
       priceInput.setPromptText("Prix");
       GridPane.setConstraints(priceInput, 1, 7, 2, 1);
       
       // $ symbole
       Label dollarSym = new Label("$");
       GridPane.setConstraints(dollarSym, 3, 7);
       
       // Specification Label
       Label specLabel = new Label("Spécifications:");
       GridPane.setConstraints(specLabel, 0, 8);
       
       // Specification Input
       TextField specInput = new TextField("");
       specInput.setPromptText("Spécifications");
       GridPane.setConstraints(specInput, 1, 8, 2, 1);
       
       
       // Add ad Button
       adButton.setOnAction(e-> {

           //Vérifier si l'Id généré aléatoirement est déjà pris dans la base de donnée
           boolean present = false;
           String objectId = "00001";
           int objId = query.get_nextID("object_ID", "object");
           boolean[] accept = new boolean[1];
           query.insert_obj(objId + "", userId, titleInput.getText(), catInput.getText(), priceInput.getText(), specInput.getText());
           double estimation = Double.parseDouble(priceInput.getText()) * Math.random()*0.5;
           
           
           // Alert dialog to confirm the estimation
           Alert alertEstim = new Alert(Alert.AlertType.CONFIRMATION);
           alertEstim.setTitle("Estimation de l'objet");
           alertEstim.setHeaderText("L'estimateur a estimé votre objet à: " + estimation + "$");
           alertEstim.setContentText("Acceptez vous l'estimation?");
           
           ButtonType buttonAccept = new ButtonType("Accepter");
           ButtonType buttonRefuse = new ButtonType("Refuser");
           
           alertEstim.getButtonTypes().setAll(buttonAccept, buttonRefuse);
           
           Optional<ButtonType> result = alertEstim.showAndWait();
           if (result.get() == buttonAccept)
           {
               query.propose_ad(objId + "", userId, priceInput.getText(), estimation + "");
               query.insert_ad(objId + "", userId);
           }
           else if (result.get() == buttonRefuse)
           {
               window.close();
           }
       });
       GridPane.setConstraints(adButton, 1, 9);
       
       
    //--------------------------------------------------------------------------   
       
       
       //Accepter une offre:
       // New Ad label
       Label acceptOffer = new Label("Accepter une offre:");
       GridPane.setConstraints(acceptOffer, 0, 12);
       
       // Title Label
       Label objectIdLabel = new Label("Identifiant de l'objet:");
       GridPane.setConstraints(objectIdLabel, 0, 13);
       
       // Title Input
       TextField objectIdInput = new TextField("");
       objectIdInput.setPromptText("ObjectId");
       GridPane.setConstraints(objectIdInput, 1, 13, 3, 1);
       
       // Categori Label
       Label userIdLabel = new Label("Identifiant de l'acheter:");
       GridPane.setConstraints(userIdLabel, 0, 14);
       
       // Categorie input
       TextField userIdInput = new TextField("");
       userIdInput.setPromptText("Acheteur");
       GridPane.setConstraints(userIdInput, 1, 14, 2, 1);
       
       // Add ad Button
      
       acceptOfferButton.setOnAction(e-> query.accept_offer(userIdInput.getText(), objectIdInput.getText()));
       GridPane.setConstraints(acceptOfferButton, 1, 15);
       
       
//-------------------------------------------------------------------------------       
       
       // Bouton pour voir mes annonces
       Button viewAd = new Button("Mes Annonces");
       viewAd.setOnAction(e->{
           
        TableColumn<Annonces_Vendeur, String> objectIdColumn = new TableColumn<>("Objet ID");
        TableColumn<Annonces_Vendeur, String> objectNameColumn = new TableColumn<>("Nom");
        TableColumn<Annonces_Vendeur, String> vente = new TableColumn<>("Etat de vente");
        TableColumn<Annonces_Vendeur, String> prix = new TableColumn<>("Prix demandé");
        TableColumn<Annonces_Vendeur, String> spec = new TableColumn<>("Spécifications");
        
        //Préparation de la table des annonces
        objectIdColumn.setMinWidth(70);
        objectIdColumn.setCellValueFactory(new PropertyValueFactory<>("objectId"));
        objectIdColumn.setStyle("-fx-alignment: CENTER;");
        
        // ObjectName Column
        objectNameColumn.setMinWidth(200);
        objectNameColumn.setCellValueFactory(new PropertyValueFactory<>("objectName"));
        objectNameColumn.setStyle("-fx-alignment: CENTER;");
        
        // EstVendu Column
        vente.setMinWidth(128);
        vente.setCellValueFactory(new PropertyValueFactory<>("estVendu"));
        vente.setStyle("-fx-alignment: CENTER;");
        
        // Specifications Column
        prix.setMinWidth(128);
        prix.setCellValueFactory(new PropertyValueFactory<>("price"));
        prix.setStyle("-fx-alignment: CENTER;");
        
        // Specifications Column
        spec.setMinWidth(128);
        spec.setCellValueFactory(new PropertyValueFactory<>("spec"));
        spec.setStyle("-fx-alignment: CENTER;");
        
    
        // Table Set up
        tableAnnonces.setItems(query.get_ad(userId));
        tableAnnonces.setMinWidth(600);
        tableAnnonces.getColumns().addAll(objectIdColumn, objectNameColumn, prix, vente, spec);
        sp.setContent(tableAnnonces);
        GridPane.setConstraints(sp, 10, 1, 15, 15);

       });
       GridPane.setConstraints(viewAd, 10, 0);
       
       // View offer Button
       Button viewOffer = new Button("Mes Offres");
       viewOffer.setOnAction(e->{
        
    //préparation de la tables des offres
       
        TableColumn<Offres_Vendeur, String> objectIdOffer = new TableColumn<>("Objet ID");
        TableColumn<Offres_Vendeur, String> objectNameOffer = new TableColumn<>("Nom");
        TableColumn<Offres_Vendeur, String> priceAsk = new TableColumn<>("Prix demandé");
        TableColumn<Offres_Vendeur, String> user = new TableColumn<>("Utilisateur");
        TableColumn<Offres_Vendeur, String> offre = new TableColumn<>("Offre");
        
        // ObjectID Column
        objectIdOffer.setMinWidth(70);
        objectIdOffer.setCellValueFactory(new PropertyValueFactory<>("objectId"));
        objectIdOffer.setStyle("-fx-alignment: CENTER;");
        
        // ObjectName Column
        objectNameOffer.setMinWidth(200);
        objectNameOffer.setCellValueFactory(new PropertyValueFactory<>("objectName"));
        objectNameOffer.setStyle("-fx-alignment: CENTER;");
        
        // Specifications Column
        priceAsk.setMinWidth(128);
        priceAsk.setCellValueFactory(new PropertyValueFactory<>("priceAsk"));
        priceAsk.setStyle("-fx-alignment: CENTER;");
        
         // Specifications Column
        user.setMinWidth(128);
        user.setCellValueFactory(new PropertyValueFactory<>("userId"));
        user.setStyle("-fx-alignment: CENTER;");
        
        // Specifications Column
        offre.setMinWidth(128);
        offre.setCellValueFactory(new PropertyValueFactory<>("offer"));
        offre.setStyle("-fx-alignment: CENTER;");
           
        // Table Set up
       
        tableOffres.setItems(query.get_offer(userId));
        tableOffres.setMinWidth(600);
        tableOffres.getColumns().addAll(objectIdOffer, objectNameOffer, priceAsk, user, offre);
        //GridPane.setConstraints(tableOffres, 5, 1, 6, 10);
           /*root.getChildren().clear();
           root.getChildren().addAll(new Label(query.get_offer(userId)));
           sp.setContent(root);*/
           sp.setContent(tableOffres);
           GridPane.setConstraints(sp, 10, 1, 15, 15);
       });
       GridPane.setConstraints(viewOffer, 14, 0);
       
       
       //GridPane.setConstraints(sp, 12 , 2, 30, 15);
       

//------------------------------------------------------------------------------
       
       // Ajout composantes du grid
       grid.getChildren().addAll(idLabel, newAdLabel, titleLabel, titleInput,  catLabel, catInput,  dollarSym, priceLabel, priceInput, specLabel, specInput, acceptOffer, objectIdLabel, objectIdInput, userIdLabel, userIdInput, acceptOfferButton,
        backButton, adButton, viewAd, viewOffer, sp);
       
       
       // Initialisation de la Scene
       Scene scene = new Scene(grid, width, height);
       
       // Mise à jour du Stage
       window.setTitle("Vendre");
       window.setScene(scene);
       window.show();
    }
    
}