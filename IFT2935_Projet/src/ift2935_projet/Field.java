package ift2935_projet;

import javafx.scene.control.TextField;

/**
* @author Camille Claing, Alexandre Dufour, Raphaël Lajoie and Nahuel Londono
*/

public class Field {
	
	private String name;
	private String regex;
	private TextField textField;
	private String errorMessage;
	private String input;
	private int maxLength;
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	public String getInput() {
		return this.input;
	}
	public void setTextFieldStyle(String style) {
		this.textField.setStyle(style);
	}
	public String getName() {
		return this.name;
	}
	
	
	public Field(String name, String regex, TextField textField, String errorMessage, int maxLength) {
		this.name = name;
		this.regex = regex;
		this.textField = textField;
		this.errorMessage = errorMessage;
		this.maxLength = maxLength;
		this.input = textField.getText();
	}
	
	public boolean verifyLength() {
		return this.textField.getLength() <= this.maxLength;
	}
	
	public boolean isInfoValid() {
		return this.input.matches(this.regex);
	}
}
