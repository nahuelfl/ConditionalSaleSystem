package ift2935_projet;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
* @author Camille Claing, Alexandre Dufour, Raphaël Lajoie and Nahuel Londono
*/
public class Expert extends Application
{   
    private Stage window;       // Stage attribute of Main     
    
    private final int APP_WIDTH = 1000;    
    private final int APP_HEIGTH = 700;  
    
    private String userId;    
    private SQLquery query;
    
    //Déclaration des éléments de la table Annonces
    TableView<Annonces_Vendeur> tableAnnonces;
    TableColumn<Annonces_Vendeur, String> objectIdColumn = new TableColumn<>("Objet ID");
    TableColumn<Annonces_Vendeur, String> objectNameColumn = new TableColumn<>("Nom");
    TableColumn<Annonces_Vendeur, String> prix = new TableColumn<>("Prix demandé");
    TableColumn<Annonces_Vendeur, String> spec = new TableColumn<>("Spécifications");

    
    public Expert(String userId)
    {
        this.userId = userId;
        this.query = new SQLquery();
         
        //Préparation de la table des annonces
        tableAnnonces = new TableView<>();
        GridPane.setConstraints(tableAnnonces, 5, 1, 6, 10);
        
        objectIdColumn.setMinWidth(70);
        objectIdColumn.setCellValueFactory(new PropertyValueFactory<Annonces_Vendeur, String>("objectId"));
        
        // ObjectName Column
        objectNameColumn.setMinWidth(200);
        objectNameColumn.setCellValueFactory(new PropertyValueFactory<Annonces_Vendeur, String>("objectName"));
    
         // price Column
        prix.setMinWidth(128);
        prix.setCellValueFactory(new PropertyValueFactory<Annonces_Vendeur, String>("price"));
        
        // Spécifications Column
        spec.setMinWidth(128);
        spec.setCellValueFactory(new PropertyValueFactory<Annonces_Vendeur, String>("spec"));
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        window = primaryStage;
        expertScene();
    }
    
    private void expertScene()
    {
        // Initialisaiton du gridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(8);
        grid.setHgap(10);
        
        // UserId label
        Label idLabel = new Label("UtilisateurID: " + userId);
        GridPane.setConstraints(idLabel, 0, 0);
        
        //Annonces non estimé
        Label estimationAd = new Label("Annonces à estimer:");
        GridPane.setConstraints(idLabel, 1, 0);
       
       
// ------------- Faire une estimation: ------------------------------------------------
        
        // New Offer Label
        Label makeEstimation = new Label("Faire une estimation: ");
        GridPane.setConstraints(makeEstimation, 0, 27);
       
        // Object Title Label
        Label objectIdLabel = new Label("Identifiant de l'objet: ");
        GridPane.setConstraints(objectIdLabel, 0, 28);
       
        // ObjetId input
        TextField objectIdInput = new TextField("");
        objectIdInput.setPromptText("objetId");
        GridPane.setConstraints(objectIdInput, 1, 28);
       
       
        // Offer Amount Label
        Label estimationLabel = new Label("Montant de l'estimation: ");
        GridPane.setConstraints(estimationLabel, 0, 29);
       
        // Offer Amount input
        TextField estimationInput = new TextField("");
        estimationInput.setPromptText("$$$$$$$");
        GridPane.setConstraints(estimationInput, 1, 29);
       
        // Make Offer Button
        Button makeEstimationButton = new Button("Confirmer");
        makeEstimationButton.setOnAction(e-> {
            query.estimate(userId, objectIdInput.getText(), estimationInput.getText());
            
                });
        
        // Table Set up
        
        tableAnnonces.setItems(query.unestimated_Ad());
        tableAnnonces.setMinWidth(600);
        tableAnnonces.getColumns().addAll(objectIdColumn, objectNameColumn, prix, spec);
        GridPane.setConstraints(tableAnnonces, 5, 1, 6, 10);
        GridPane.setConstraints(makeEstimationButton, 1, 31);
        
        // --------------------------------------------------------------------
        
        // Button Go Back
        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> Main.Instance.userValidated(this.userId));
        GridPane.setConstraints(backButton, 0, 35);
        
        // Ajout composantes du grid
        grid.getChildren().addAll(idLabel, estimationAd,makeEstimation, objectIdLabel, objectIdInput, 
            estimationLabel, estimationInput, makeEstimationButton, backButton, tableAnnonces);
               
        // Initialisation de la Scene
        Scene scene = new Scene(grid, APP_WIDTH, APP_HEIGTH);
       
        // Mise à jour du Stage
        window.setTitle("Expert");
        window.setScene(scene);
        window.show();
    }
}
