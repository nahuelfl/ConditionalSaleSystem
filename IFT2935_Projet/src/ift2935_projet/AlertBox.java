/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ift2935_projet;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author nahue
 */
public class AlertBox 
{
    
    public static void display(String title, String message)
    {
        Stage window = new Stage();
        
        // Window properties
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(500);
        window.setMinHeight(250);
        
        // Label properties
        Label label = new Label();
        System.out.println(message);
        label.setText(message);
        
        // Close button properties
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());
        
        // VBox layout container properties
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        
        // Scene initialization
        Scene scene = new Scene(layout);
        
        // Window scene instantiate
        window.setScene(scene);
        window.showAndWait();
    }
    
    public static void accept_estim(String title, String message, boolean[] accept)
    {
        Stage window = new Stage();
        
        // Window properties
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(500);
        window.setMinHeight(250);
        
        // Label properties
        Label label = new Label();
        System.out.println(message);
        label.setText(message);
        
        // Close button properties
        Button acceptButton = new Button("Accepter");
        acceptButton.setOnAction(e -> {
            window.close();
            accept[0] = true;
                });
        
        
        Button refuseButton = new Button("Refuser");
        refuseButton.setOnAction(e -> {
            window.close();
            accept[0] = false;
                });
        
        // VBox layout container properties
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, acceptButton, refuseButton);
        layout.setAlignment(Pos.CENTER);
        
        // Scene initialization
        Scene scene = new Scene(layout);
        
        // Window scene instantiate
        window.setScene(scene);
        window.showAndWait();
       
    }
}
