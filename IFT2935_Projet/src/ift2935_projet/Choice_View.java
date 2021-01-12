/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ift2935_projet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author nahue
 */
public class Choice_View extends Application
{
    Stage window;       // Stage attribute of SignUp     
    int width = 1000;   // Width attribute of the app window
    int height = 700;   // Height attribute of the app window
    Button makeQuery;
    
    public Choice_View()
    {
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        window = primaryStage;
        choiceScene();
    }
    
    public void choiceScene()
    {
        // Initialisaiton du gridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(8);
        grid.setHgap(10);
       
        // Welcome Text
        Text welcomeText = new Text("Bienvenus, veuillez définir votre intention:");
        GridPane.setConstraints(welcomeText, 0, 0, 3, 1);
       
        // Vue Acheteur Button
        Button buyerButton = new Button("Acheter");
        GridPane.setConstraints(buyerButton, 0, 1);
        buyerButton.setPrefWidth(105);
        //buyer = new Acheteur(userId);
        buyerButton.setOnAction(e -> Main.Instance.buy());

        GridPane.setConstraints(buyerButton, 0, 1);

       
        // Vue Vendeur Button
        Button sellerButton = new Button("Vendre");
        GridPane.setConstraints(sellerButton, 1, 1);
        sellerButton.setPrefWidth(120);
        //seller = new Vendeur(userId);
        sellerButton.setOnAction(e -> Main.Instance.sell());
        GridPane.setConstraints(sellerButton, 1, 1);

        // Estimation Button
        Button expertButton = new Button("Estimer");
        expertButton.setOnAction(e-> Main.Instance.estimate());
        expertButton.setPrefWidth(105);
        GridPane.setConstraints(expertButton, 0, 2);
        
        //Make Query button
        makeQuery = new Button("Requêtes par défaut");
        makeQuery.setOnAction(e -> Main.Instance.requete());
        GridPane.setConstraints(makeQuery, 1, 2);
        
        // Button Go Back
        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> Main.Instance.logIn());

        GridPane.setConstraints(backButton, 0, 10);

       
        // Ajout composantes du grid
        grid.getChildren().addAll(welcomeText, buyerButton, sellerButton, expertButton, backButton, makeQuery);
       
        // Initialisation de la Scene
        Scene scene = new Scene(grid, width, height);
    
        // Mise à jour du Stage
        window.setTitle("Bienvenue");
        window.setScene(scene);
        window.show();
    }
}
