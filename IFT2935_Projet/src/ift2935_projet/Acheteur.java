/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */   
package ift2935_projet;

import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author nahue
 */
public class Acheteur extends Application
{   
    Stage window;       // Stage attribute of Main     
    
    int width = 1000;    // Width attribute of the app window
    int height = 700;   // Height attribute of the app window
    
    String userId;
    
    VBox root;
    
    ScrollPane sp;
    
    SQLquery query;
    
    Button backButton = new Button("Retour");
    Button newOfferButton = new Button("Confirmer");
    
    private Acheteur buyer;
    
    public Acheteur(String userId)
    {
        this.userId = userId;

        buyer = this;
        
        query = new SQLquery();
        sp = new ScrollPane();
        GridPane.setConstraints(sp, 10, 1, 15, 20);
        
        root = new VBox();
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        window = primaryStage;
        buyerScene();
    }
    
    private void buyerScene()
    {
        // Initialisaiton du gridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(8);
        grid.setHgap(10);
       
        // Identifiant label
        Label idLabel = new Label("Identifiant: " + userId);
        GridPane.setConstraints(idLabel, 0, 0);
       
        // Back Button
        backButton.setOnAction(e ->Main.Instance.userValidated(this.userId));
        GridPane.setConstraints(backButton, 0, 20);
       
       

        //-------------------------------------------------------------------------------
        
        // Sort Label
        Label sortLabel = new Label("Trier par:");
        GridPane.setConstraints(sortLabel, 1, 5);
       
        // Categorie Label
        Label catLabel = new Label("Catégorie de l'objet:");
        GridPane.setConstraints(catLabel, 0, 6);
       
        // Categorie input
        MenuButton catInput = new MenuButton("Choisir une catégorie");
        GridPane.setConstraints(catInput, 1, 6, 2, 1);
       
        // Different choices for Category Input
        MenuItem all = new MenuItem("Toutes");
        MenuItem veh = new MenuItem("Véhicule");
        MenuItem meubles = new MenuItem("Meubles");
        MenuItem info = new MenuItem("Informatique");
        MenuItem multi = new MenuItem("Multimédia");
        MenuItem autre = new MenuItem("Autres");
        catInput.getItems().addAll(all, veh, meubles, info, multi, autre);
        
        //Show the chosen category
        all.setOnAction(e->{
            catInput.setText(all.getText());
        });
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
       
        // Price Label
        Label priceLabel = new Label("Prix maximum:");
        GridPane.setConstraints(priceLabel, 0, 7);

        // Price input
        TextField priceInput = new TextField("");
        priceInput.setPromptText("Prix");
        GridPane.setConstraints(priceInput, 1, 7, 2, 1);

        // $ symbole
        Label dollarSym = new Label("$");
        GridPane.setConstraints(dollarSym, 3, 7);
       
       
        //--------------------------------------------------------------------------   
       
        //Faire une offre ---------------------------------------------------------
        
        // New Offer Label
        Label newOffer = new Label("Faire une offre:");
        GridPane.setConstraints(newOffer, 1, 12);

        // ObjectID Label
        Label objectIdLabel = new Label("Identifiant de l'objet:");
        GridPane.setConstraints(objectIdLabel, 0, 13);

        // ObjectID Input
        TextField objectIdInput = new TextField("");
        objectIdInput.setPromptText("ObjectId");
        GridPane.setConstraints(objectIdInput, 1, 13, 2, 1);

        // Offer Amount Label
        Label offerAmountLabel = new Label("Montant de l'offre:");
        GridPane.setConstraints(offerAmountLabel, 0, 14);

        // Offer Amount Input
        TextField offerAmountInput = new TextField("");
        offerAmountInput.setPromptText("Montant");
        GridPane.setConstraints(offerAmountInput, 1, 14, 2, 1);
        
        // $ symbole
        Label dollarSym2 = new Label("$");
        GridPane.setConstraints(dollarSym2, 3, 14);

        // New Offer Button
        newOfferButton.setOnAction(e-> {
            
            // Action make offer dans SQLquery
            query.faitOffre(Integer.parseInt(this.userId), 
                            Integer.parseInt(objectIdInput.getText()), 
                            Integer.parseInt(offerAmountInput.getText()));
                
            // String dialog of confirmation
            String confirmation = "Vous avez fait une offre de " 
                    + offerAmountInput.getText() + "$ pour l'objet #" 
                    + objectIdInput.getText();
            
            // Alert dialog to confirm new offer was made
            Alert alertOffer = new Alert(Alert.AlertType.CONFIRMATION, confirmation, ButtonType.OK);
            alertOffer.setTitle("Confirmation nouvelle offre");
            alertOffer.setHeaderText("Votre offre a été traitée.");
            alertOffer.showAndWait();
        });
        GridPane.setConstraints(newOfferButton, 1, 15);
       
       
        //-------------------------------------------------------------------------------       
       
        // Bouton pour voir mes annonces
        Button viewAd = new Button("Annonces");
        GridPane.setConstraints(viewAd, 10, 0);
        
        viewAd.setOnAction(e->{
           
            // Table initialization
            TableView<Annonces_Acheteur> tableAnnonces = new TableView<>();
            TableColumn<Annonces_Acheteur, String> objectIdColumn = new TableColumn<>("Objet ID");
            TableColumn<Annonces_Acheteur, String> objectNameColumn = new TableColumn<>("Nom");
            TableColumn<Annonces_Acheteur, String> specs = new TableColumn<>("Spécifications");
            TableColumn<Annonces_Acheteur, String> prix = new TableColumn<>("Prix demandé");
            TableColumn<Annonces_Acheteur, String> adresse = new TableColumn<>("Adresse");

            //Préparation de la table des annonces

            //ObjectID Column
            objectIdColumn.setMinWidth(70);
            objectIdColumn.setCellValueFactory(new PropertyValueFactory<>("objectId"));
            objectIdColumn.setStyle("-fx-alignment: CENTER;");

            // ObjectName Column
            objectNameColumn.setMinWidth(150);
            objectNameColumn.setCellValueFactory(new PropertyValueFactory<>("objectName"));
            objectNameColumn.setStyle("-fx-alignment: CENTER;");

            // Specifications Column
            specs.setMinWidth(128);
            specs.setCellValueFactory(new PropertyValueFactory<>("specs"));
            specs.setStyle("-fx-alignment: CENTER;");

            // Prix Column
            prix.setMinWidth(100);
            prix.setCellValueFactory(new PropertyValueFactory<>("priceAsked"));
            prix.setStyle("-fx-alignment: CENTER;");

            // Adresse Column
            adresse.setMinWidth(150);
            adresse.setCellValueFactory(new PropertyValueFactory<>("location"));
            adresse.setStyle("-fx-alignment: CENTER;");

            // Table Set up
            if(priceInput.getText().equals("")) //If no price_max input show all the items
                tableAnnonces.setItems(query.affiche_annonce(catInput.getText(), 1000000));
            else
                tableAnnonces.setItems(query.affiche_annonce(catInput.getText(), Integer.parseInt(priceInput.getText())));

            tableAnnonces.setMinWidth(556);
            tableAnnonces.getColumns().addAll(objectIdColumn, objectNameColumn, specs, prix, adresse);
            sp.setContent(tableAnnonces);
            GridPane.setConstraints(sp, 10, 1, 15, 25);

           });
       
        // View offer Button
        Button viewOffer = new Button("Mes Offres");
        GridPane.setConstraints(viewOffer, 14, 0);
        
        viewOffer.setOnAction(e->{
        
            //préparation de la tables des offres
            
            //Déclaration des éléments de la table Offre.
            TableView<Offres_Acheteur> tableOffres = new TableView<>();
            TableColumn<Offres_Acheteur, String> objectIdOffer = new TableColumn<>("Objet ID");
            TableColumn<Offres_Acheteur, String> priceAsk = new TableColumn<>("Prix de l'offre");
            TableColumn<Offres_Acheteur, String> status = new TableColumn<>("Statut");
       
            // tableAnnonces = new TableView<>();
            objectIdOffer.setMinWidth(70);
            objectIdOffer.setCellValueFactory(new PropertyValueFactory<>("objectId"));
            objectIdOffer.setStyle("-fx-alignment: CENTER;");

            // Specifications Column
            priceAsk.setMinWidth(128);
            priceAsk.setCellValueFactory(new PropertyValueFactory<>("priceAsk"));
            priceAsk.setStyle("-fx-alignment: CENTER;");

            // User Column
            status.setMinWidth(128);
            status.setCellValueFactory(new PropertyValueFactory<>("userId"));
            status.setStyle("-fx-alignment: CENTER;");

            // Table Set up
            tableOffres.setItems(query.get_offerAcheteur(userId));
            tableOffres.setMinWidth(300);
            tableOffres.getColumns().addAll(objectIdOffer, priceAsk, status);
            sp.setContent(tableOffres);
            GridPane.setConstraints(sp, 10, 1, 15, 25);
           });
       
      
        //------------------------------------------------------------------------------
       
        // Ajout composantes du grid
        grid.getChildren().addAll(idLabel, sortLabel, catLabel, catInput,  
               dollarSym, priceLabel, priceInput, newOffer, objectIdLabel, 
               objectIdInput, offerAmountLabel, offerAmountInput, dollarSym2,
               newOfferButton,backButton, viewAd, viewOffer, sp);
       
       
        // Initialisation de la Scene
        Scene scene = new Scene(grid, width, height);
       
        // Mise à jour du Stage
        window.setTitle("Acheter");
        window.setScene(scene);
        window.show();
    }
}