/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ift2935_projet;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 *
 * @author Camille Claing, Alexandre Dufour, Raphaël Lajoie and Nahuel Londono
 */
public class SignUp_Controller 
{
    // RegEx depending on the type of input
    private final String TEXT_REGEX = "[a-zA-Z]+";
    private final String EMAIL_REGEX = "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}";
    private final String PHONE_REGEX = "\\d{10}";
    private final String NUMBER_REGEX = "[0-9]+";
    private final String POSTAL_CODE_REGEX = "^(?!.*[DFIOQU])[A-Z][0-9][A-Z][0-9][A-Z][0-9]$";
    
    // Error specification depending of on the type of input
    private final String TEXT_ERROR = "Ce champs doit contenir seulements des lettres.";
    private final String FIRST_NAME_ERROR = TEXT_ERROR + " *20 lettres maximum*";
    private final String LAST_NAME_ERROR = TEXT_ERROR + " *30 lettres maximum*";
    private final String EMAIL_ERROR = "Doit être du format email@provider.extension *60 caractères maximum*";
    private final String PHONE_ERROR = "Doit être du format XXXXXXXXXX où X est un chiffre de 1 à 9. *10 chiffres maximum*.";
    private final String STREET_NUMBER_ERROR = "Ce champs doit contenir seulement des numéros. *20 numéros maximum*";
    private final String STREET_NAME_ERROR = TEXT_ERROR + " *60 lettres maximum*";
    private final String CITY_NAME_ERROR = FIRST_NAME_ERROR;
    private final String POSTAL_ERROR = "Doit être du format XYXXYX où X est un chiffre et Y une lettre.";
    private final String PROVINCE_ERROR = TEXT_ERROR + " *4 lettres maximum*";
    private final String COUNTRY_ERROR = FIRST_NAME_ERROR;
    
    private final int NB_FIELDS = 10;
    
    private int cptID = 00000;
    private int userID;
	
    protected SignUp_View signUp_View;
    private String errorMessage = "";
    
    private Field[] fields;
    
  
    public SignUp_Controller(SignUp_View signUpView)
    {
        this.signUp_View = signUpView;
        
        // init Listener
        this.signUp_View.doneButton.setOnAction(e -> signUpValidation(this.signUp_View.grid));
        this.signUp_View.backButton.setOnAction(e -> Main.Instance.logIn());
    }
   
    private void initFields(GridPane grid) {
    	fields = new Field[this.NB_FIELDS];
    	
    	ObservableList<Node> children = grid.getChildren();
    	
    	fields[0] = new Field("Prénom",this.TEXT_REGEX,(TextField) children.get(1),this.FIRST_NAME_ERROR,20);
    	fields[1] = new Field("Nom",   this.TEXT_REGEX,(TextField) children.get(2),this.LAST_NAME_ERROR,30);
    	fields[2] = new Field("Email", this.EMAIL_REGEX,(TextField) children.get(4),this.EMAIL_ERROR,60);
    	fields[3] = new Field("NumTel",this.PHONE_REGEX,(TextField) children.get(6),this.PHONE_ERROR,10);
    	fields[4] = new Field("NumRue",this.NUMBER_REGEX,(TextField) children.get(8),this.STREET_NUMBER_ERROR,20);
    	fields[5] = new Field("NomRue",this.TEXT_REGEX,(TextField) children.get(9),this.STREET_NAME_ERROR,60);
    	fields[6] = new Field("Ville", this.TEXT_REGEX,(TextField) children.get(10),this.CITY_NAME_ERROR,20);
    	fields[7] = new Field("CodePostal",this.POSTAL_CODE_REGEX,(TextField) children.get(11),this.POSTAL_ERROR,7);
    	fields[8] = new Field("Province",  this.TEXT_REGEX,(TextField) children.get(12),this.PROVINCE_ERROR,4);
    	fields[9] = new Field("Pays",  this.TEXT_REGEX,(TextField) children.get(13),this.COUNTRY_ERROR,20);	
    }
   
    private void signUpValidation(GridPane grid) {
    	
    	initFields(this.signUp_View.grid);
        
        boolean isValid = true;
        int cpt = 0;
        
        for(Field f : this.fields) {
        	isValid = true;
        	if(!f.verifyLength() || !f.isInfoValid()) {
        		//this.errorMessage = f.getErrorMessage();
        		isValid = false;
        		cpt++;
        	}
        	updateField(isValid, f);
        }
        
        if(cpt==0) { // No error        
        	SQLquery query = new SQLquery();
        	query.add_user(fields[0].getInput(), fields[1].getInput(), fields[2].getInput(),
        			       fields[3].getInput(), fields[4].getInput(), fields[5].getInput(),
        			       fields[6].getInput(), fields[7].getInput(), fields[8].getInput(),
        			       fields[9].getInput());
        	
            Alert idInfo = new Alert(Alert.AlertType.INFORMATION, "Veuillez le prendre en note.", ButtonType.OK);
            idInfo.setTitle("UtilisateurID");
            //idInfo.setHeaderText("Votre nouveau UtilisateurID est: "+this.cptID);
            this.userID = query.get_nextID("userId", "utilisateurs");
            idInfo.setHeaderText("Votre nouveau UtilisateurID est: "+ this.userID);
            idInfo.showAndWait();
            Main.Instance.userValidated(Integer.toString(userID));
            this.cptID++;
            
        }
        else
        {
            // Displays error box with list of errors and hints
        	
            Alert error = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
            error.getDialogPane().setPrefWidth(600);
            error.setTitle("Erreur");
            error.setHeaderText("Erreur dans les champs suivants:");
            error.showAndWait();
            errorMessage = "";
        }
    }
    
    private void updateField(boolean isOk, Field field) {

        if(!isOk)
        {
            // Puts the textField border in color red and stores the error, it's type dant it's hint.
            field.setTextFieldStyle("-fx-text-box-border: red");
            errorMessage += field.getInput() + " (champs " + field.getName() + ") n'est pas valide \n Indice: " + field.getErrorMessage() + "\n\n";
        }
        else
        {
            // Sets the textField border to green indicating there's no error.
        	field.setTextFieldStyle("-fx-text-box-border: #70db70");
        }
    }
    
    public int getUserId()
    {
        return this.userID;
    }
}