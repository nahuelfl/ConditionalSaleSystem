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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author nahue
 */
public class Login_View extends Application
{
    Stage window;       // Stage attribute of SignUp     
    int width = 1000;   // Width attribute of the app window
    int height = 700;   // Height attribute of the app window
    
    Button loginButton = new Button("Connexion");
    Button newUserButton = new Button("Nouveau utilisateur?");
    GridPane grid;
    
    public Login_View()
    {
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        window = primaryStage;
        loginScene();
    }
   
    public void loginScene()
    {
       // Initialisaiton du gridPane
       grid = new GridPane();
       grid.setPadding(new Insets(10, 10, 10, 10));
       grid.setAlignment(Pos.CENTER);
       grid.setVgap(8);
       grid.setHgap(10);
       
       // Name Label
       Label nameLabel = new Label("UtilisateurID:");
       GridPane.setConstraints(nameLabel, 0, 0);
       
       // Name Input
       TextField nameInput = new TextField("00001");
       GridPane.setConstraints(nameInput, 1, 0);
       
       // Password Label
       Label passwordLabel = new Label("Mot de passe:");
       GridPane.setConstraints(passwordLabel, 0, 1);
       
       // Password Input
       TextField passwordInput = new TextField("*****");
       GridPane.setConstraints(passwordInput, 1, 1);
       passwordInput.setEditable(false);
       
       // New User Button
       newUserButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #0000ff;");
       GridPane.setConstraints(newUserButton, 0, 2);
       

       // Login Button
       GridPane.setConstraints(loginButton, 1, 2);
      
       
       // Ajout composantes du grid
       grid.getChildren().addAll(nameLabel, nameInput, passwordLabel, passwordInput, newUserButton, loginButton);
       
       // Initialisation de la Scene
       Scene scene = new Scene(grid, width, height);
       
       // Mise à jour du Stage
       window.setTitle("Login");
       window.setScene(scene);
       window.show();
    }
}
