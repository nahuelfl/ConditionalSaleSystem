/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ift2935_projet;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author nahue
 */
public class Requete extends Application
{   
    Stage window;       // Stage attribute of Main     
    
    int width = 1000;    // Width attribute of the app window
    int height = 700;   // Height attribute of the app window
    
    String userId;
    
    VBox root;
    
    ScrollPane sp;
    
    SQLquery query;
    
    
    private Requete requete;
    
    public Requete()
    {
        this.userId = userId;
        this.query = new SQLquery();
        this.sp = new ScrollPane();
        this.root = new VBox();
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        window = primaryStage;
        queryScene();
    }
    
    private void queryScene()
    {
        // Initialisaiton du gridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(8);
        grid.setHgap(10);
        
         MenuButton queryInput = new MenuButton("Choisir une requête");

       
       MenuItem query1 = new MenuItem("1-Donne la liste des objets vendu par les utilisateurs");
       MenuItem query2 = new MenuItem("2-Donne la liste des utilisateurs n'ayant pas d'objet à vendre");
       MenuItem query3 = new MenuItem("3-Donne l'utilisateur ayant le plus d'objet vendu");
       MenuItem query4 = new MenuItem("4-Donne le nombre d'offre faites sur les produits de la catégorie Multimédia");
       MenuItem query5 = new MenuItem("5-Donne les utilisateurs ayant fait une offre inférieure à l'estimation pour un produit");
       MenuItem query6 = new MenuItem("6-Donnez le nom des utilisateurs habitant le Canada qui a acheté le plus de produit de la catégorie Autres");
       MenuItem query7 = new MenuItem("7-Donnez les Id et la catégorie des objets vendu à plus de 50$ ayant dans la spécification le mot chaise, estimé par un expert ayant Tremblay dans le nom de famille");
       MenuItem query8 = new MenuItem("8-Donner les informations relatives des utilisateurs ayant fait une annonce au Boxing Day.");
       MenuItem query9 = new MenuItem("9-Donner la liste des articles de bureau non vendu en ordre croissant dont l'annonceur a un compte Google");
       MenuItem query10 = new MenuItem("10-Donner la listes des offres sur les lits noirs (qui ont été achetés");
       queryInput.getItems().addAll(query1, query2, query3, query4, query5, query6, query7, query8, query9, query10);
       
       //Affichage de la catégorie choisie
       
       query1.setOnAction(e->{
           queryInput.setText(query1.getText());
           root.getChildren().clear();
           root.getChildren().addAll(new Label(query.execute_query1()));
           sp.setContent(root);
       });
       query2.setOnAction(e->{
           queryInput.setText(query2.getText());
           root.getChildren().clear();
           root.getChildren().addAll(new Label(query.execute_query2()));
           sp.setContent(root);//query.execute_query3();
       });
       query3.setOnAction(e->{
           queryInput.setText(query3.getText());
           root.getChildren().clear();
           root.getChildren().addAll(new Label(query.execute_query3()));
           sp.setContent(root);
       });
       query4.setOnAction(e->{
           queryInput.setText(query4.getText());
           root.getChildren().clear();
           root.getChildren().addAll(new Label(query.execute_query4()));
           sp.setContent(root);
       });
       query5.setOnAction(e->{
           queryInput.setText(query5.getText());
           root.getChildren().clear();
           root.getChildren().addAll(new Label(query.execute_query5()));
           sp.setContent(root);
       });
       query6.setOnAction(e->{
           queryInput.setText(query6.getText());
           root.getChildren().clear();
           root.getChildren().addAll(new Label(query.execute_query6()));
           sp.setContent(root);
       });
       query7.setOnAction(e->{
           queryInput.setText(query7.getText());
           root.getChildren().clear();
           root.getChildren().addAll(new Label(query.execute_query7()));
           sp.setContent(root);
       });
       query8.setOnAction(e->{
           queryInput.setText(query8.getText());
           root.getChildren().clear();
           root.getChildren().addAll(new Label(query.execute_query8()));
           sp.setContent(root);
       });
       query9.setOnAction(e->{
           queryInput.setText(query9.getText());
           root.getChildren().clear();
           root.getChildren().addAll(new Label(query.execute_query9()));
           sp.setContent(root);
       });
        query10.setOnAction(e->{
           queryInput.setText(query10.getText());
           root.getChildren().clear();
           root.getChildren().addAll(new Label(query.execute_query10()));
           sp.setContent(root);
       });
       GridPane.setConstraints(queryInput, 5 , 0 , 70, 2);
       
 
      
        // ScrollPane
        GridPane.setConstraints(sp, 5 , 2, 70, 70);
        
        
        // Button Go Back
        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> Main.Instance.logIn());
        GridPane.setConstraints(backButton, 0, 35);
        
        // Ajout composantes du grid
        grid.getChildren().addAll(queryInput, sp, backButton);
               
        // Initialisation de la Scene
        Scene scene = new Scene(grid, width, height);
       
        // Mise à jour du Stage
        window.setTitle("Requête");
        window.setScene(scene);
        window.show();
    }
}
