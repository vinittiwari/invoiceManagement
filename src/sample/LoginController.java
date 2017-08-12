package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mtechproject.samples.DBConnectFlogger;
import util.Constants;
import util.ShowAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class LoginController {

	private Parent parent;
	private Scene scene;
	private Stage stage;
	@FXML
	private TextField userName;
	@FXML
	private TextField passwordField;

	private HomeController homeController;
	private AddInvoiceController addInvoiceController;
	private AddItemController addItemController;
	private ViewInvoiceController viewInvoiceController;
	private ViewItemController viewItemController;
	private ViewPartyController viewPartyController;

	public LoginController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
		fxmlLoader.setController(this);
		try {
			parent = (Parent) fxmlLoader.load();
			scene = new Scene(parent, 600, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        Connection c ;
        int rowcount = 0;  
        try{  
	         c = DBConnectFlogger.connect();  
	         String SQL = "SELECT * from user WHERE username=\"" + userName.getText() + "\" AND password=\"" + passwordField.getText() + "\"";
	         //String SQL = "select * from user where username = \"" + userName.getText() + "\" and password = \"" + passwordField.getText() + "\";" ;
	         System.out.println("---> query" + SQL);
	         //ResultSet  
	         ResultSet rs = c.createStatement().executeQuery(SQL);
	        
	         if (rs.last()) {
	           rowcount = rs.getRow();
	           rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
	         }
	         System.out.println("RS size " + rowcount);
	         
        }catch (Exception e) {
			// TODO: handle exception
		}
        
        if (userName.getText().trim().length() > 0 && passwordField.getText().trim().length() > 0 && rowcount > 0) {
            homeController = new HomeController();
            Constants.setUsername(userName.getText().trim());
            homeController.redirectHome(stage, Constants.getUsername());
        }else{
        	System.out.println("Wrong Password");
        	ShowAlert.callAlert("Error","Please enter corrent credentials.");
        }
    }

	public void launchLogingController(Stage stage) {
		this.stage = stage;
		/*addInvoiceController = new AddInvoiceController();
    	addInvoiceController.redirectaddInvoice(stage,"vinit");*/
		/*addItemController = new AddItemController();
		addItemController.redirectaddItem(stage,"vinit");*/
		/*viewInvoiceController = new ViewInvoiceController();
		viewInvoiceController.redirectviewInvoice(stage,"vinit");*/
		//this.stage = stage;
		homeController = new HomeController();
        Constants.setUsername(userName.getText().trim());
        homeController.redirectHome(stage, Constants.getUsername());
		/*
		stage.setTitle("User Login");
		stage.setScene(scene);
		stage.setResizable(true);
		stage.hide();
		stage.show();
		stage.setMaximized(true);*/
	}
}
