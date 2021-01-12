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
public class SignUp_View extends Application
{
    Stage window;       // Stage attribute of SignUp     
    int width = 1000;   // Width attribute of the app window
    int height = 700;   // Height attribute of the app window
    
    Button doneButton = new Button("Terminé");
    Button backButton = new Button("Retour");
    GridPane grid;
            
    public SignUp_View()
    {
        
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        window = primaryStage;
        signUpScene();
    }
    
    public void signUpScene()
    {
        // Initialisaiton du gridPane
        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(8);
        grid.setHgap(10);
       
        // Name Label
        Label nameLabel = new Label("Votre nom:");
        GridPane.setConstraints(nameLabel, 0, 0);
       
        // First Name Input
        TextField firstNameInput = new TextField("");
        firstNameInput.setPromptText("Prénom");
        GridPane.setConstraints(firstNameInput, 1, 0);
       
        // Last Name Input
        TextField lastNameInput = new TextField("");
        lastNameInput.setPromptText("Nom de famille");
        GridPane.setConstraints(lastNameInput, 2, 0, 2, 1);
       
        // Email Label
        Label emailLabel = new Label("Votre E-mail:");
        GridPane.setConstraints(emailLabel, 0, 1);
       
        // Email Input
        TextField emailInput = new TextField("");
        emailInput.setPromptText("E-mail");
        GridPane.setConstraints(emailInput, 1, 1, 3, 1);
       
        // Telephone Label
        Label telephoneLabel = new Label("Votre numéro de téléphone:");
        GridPane.setConstraints(telephoneLabel, 0, 2);
       
        // Telephone Input
        TextField telephoneInput = new TextField("");
        telephoneInput.setPromptText("Numéro de téléphone");
        GridPane.setConstraints(telephoneInput, 1, 2, 3, 1);
       
        // Adresse Label
        Label adressLabel = new Label("Votre adresse:");
        GridPane.setConstraints(adressLabel, 0, 3);
       
        // Street Number Input
        TextField streetNbInput = new TextField("");
        streetNbInput.setPromptText("Numéro");
        GridPane.setConstraints(streetNbInput, 1, 3);
       
        // Street Name Input
        TextField streetNameInput = new TextField("");

        streetNameInput.setPromptText("Rue");
        GridPane.setConstraints(streetNameInput, 2, 3);
        
        // City Name Input
        TextField cityInput = new TextField("");
        cityInput.setPromptText("Ville");
        GridPane.setConstraints(cityInput, 3, 3);

       
        // Postal Code Input
        TextField postalCodeInput = new TextField("");
        postalCodeInput.setPromptText("Code postal");
        GridPane.setConstraints(postalCodeInput, 1, 4);
       
        // Province Input
        TextField provinceInput = new TextField("");
        provinceInput.setPromptText("Province");
        GridPane.setConstraints(provinceInput, 2, 4);
       
        // Country Input
        TextField countryInput = new TextField("");
        countryInput.setPromptText("Pays");
        GridPane.setConstraints(countryInput, 3, 4);
       
        // Back Button
        GridPane.setConstraints(backButton, 1, 8);
       
        // Done Button
        doneButton.setPrefWidth(200);
        GridPane.setConstraints(doneButton, 3, 8);
       
        // Ajout composantes du grid
        grid.getChildren().addAll(nameLabel, firstNameInput, lastNameInput,
        emailLabel, emailInput, telephoneLabel, telephoneInput, adressLabel, 

        streetNbInput, streetNameInput, cityInput, postalCodeInput, provinceInput, 
        countryInput, backButton, doneButton);
       
        // Initialisation de la Scene
        Scene scene = new Scene(grid, width, height);
       
        // Mise à jour du Stage
        window.setTitle("Inscription nouveau utilisateur");
        window.setScene(scene);
        window.show();
    }
}
