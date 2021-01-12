/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ift2935_projet;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 

/**
 *
 * @author nahue
 */
//public class Main extends Application 
public class Main extends Application 
{   
    public static Main Instance;
    
    Stage window;       // Stage attribute of Main     
    
    int width = 1000;    // Width attribute of the app window
    int height = 700;   // Height attribute of the app window
    
    String userId;
    
    Vendeur seller;
    Acheteur buyer;
    Requete query;
    Expert expert;
    
    // Login Instances
    Login_View login_View = new Login_View();
    Login_Controller login_Controller = new Login_Controller(login_View);

    // SignUp Instances
    SignUp_View signUp_View = new SignUp_View();
    SignUp_Controller signUp_Controller = new SignUp_Controller(signUp_View);
    

    // Choice Instances

    Choice_View choice_View = new Choice_View();
    
    
    @Override
    public void start(Stage primaryStage) 
    {
        Instance = this;
        
        window = primaryStage;

        
        login_View.start(window);

    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
        
    }
    
    public void logIn()
    {
        this.login_View.loginScene();
    }
    
    public void signUp()
    {
        this.signUp_View.start(window);
    }
    
    public void userValidated(String userId)
    {
        this.userId = userId;
        this.choice_View.start(window);
    }
    
    public void buy()
    {
        buyer = new Acheteur(userId);
        this.buyer.start(window);
    }
    
    public void sell()
    {
        seller = new Vendeur(userId);
        this.seller.start(window);
    }
    
    public void estimate()
    {
    expert = new Expert(userId);
    this.expert.start(window);
    }
    
    public void requete()
    {
        query = new Requete();
        this.query.start(window);
    }
}





