/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ift2935_projet;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author nahue
 */
public class Login_Controller 
{
    protected Login_View login_View;
    
    private String userId;
    private boolean loginIsValid;
    
    public Login_Controller(Login_View loginView)
    {
        this.login_View = loginView;
        
        this.login_View.loginButton.setOnAction(e -> isValid(this.login_View.grid));
        this.login_View.newUserButton.setOnAction(e -> Main.Instance.signUp());
    }
    
    private boolean isValid(GridPane grid)
    {
        SQLquery query = new SQLquery();
        TextField inputTextField = (TextField) grid.getChildren().get(1);
        String input = inputTextField.getText();
        int inputInt = Integer.parseInt(input);
        
        //Vérifier dans la base de donnée si le userName entré est valide.
        //Si oui, on l'attribut à userID
        if(0 <= inputInt && inputInt < query.get_nextID("userId", "utilisateurs"))
        {
            userId = input;
            System.out.println(input + " (UtilisateurID) est valide");
            loginIsValid = true;
            
            Main.Instance.userValidated(userId);
            
            return true;
        }
        else
        {
            Alert error = new Alert(Alert.AlertType.ERROR, input + " (UtilisateurID) n'est PAS valide", ButtonType.OK);
            error.setTitle("Erreur");
            error.setHeaderText("Erreur de LogIn:");
            error.showAndWait();
            return false;
        }
    }
}
