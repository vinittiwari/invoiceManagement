package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mtechproject.samples.DBConnectFlogger;
import util.Constants;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class HomeController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private String name;
    @FXML
    private Text welcomeText;
    private AddPartyController addPartyController;
    private AddInvoiceController addInvoiceController;
    private AddItemController addItemController;
    private ViewInvoiceController viewInvoiceController;
	private ViewItemController viewItemController;
	private ViewPartyController viewPartyController;

    public HomeController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
       
       
    }


    /*private void getStateCode() {
    	Connection c ;
        int rowcount = 0;  
        try{  
	         c = DBConnectFlogger.connect();  
	         String SQL = "SELECT * from user WHERE username=\"" + Constants.getUsername() + "\"";
	         //String SQL = "select * from user where username = \"" + userName.getText() + "\" and password = \"" + passwordField.getText() + "\";" ;
	         System.out.println("---> query" + SQL);
	         //ResultSet  
	         ResultSet rs = c.createStatement().executeQuery(SQL);
	         String state = null;
	         while (rs.next()) {
					if (rs.first()) {
						 state = rs.getString("state_code");
					}
				}
	        
	         System.out.println("RS size " + rowcount);
	         Constants.setState(state);
        }catch (Exception e) {
			// TODO: handle exception
		}
	}*/


	public void redirectHome(Stage stage, String name) {
		//getStateCode();
    	this.stage = stage;
        stage.setTitle("Home");
        stage.setScene(scene);
        welcomeText.setText("Hello " + name + "! You are welcome.");
        this.name = name;
        stage.hide();
        stage.show();
        //stage.setFullScreen(true);
        stage.setMaximized(true);
    }
    
    @FXML
    protected void handleaddParty(ActionEvent event) {
    	 addPartyController = new AddPartyController();
    	 addPartyController.redirectaddParty(stage, name);
    }
    
    @FXML
    protected void handlemodifyParty(ActionEvent event) {
    	
    }
    
    @FXML
    protected void handleviewParty(ActionEvent event) {
		viewPartyController = new ViewPartyController();
		viewPartyController.redirectviewParty(stage,Constants.getUsername());
    }
    @FXML
    protected void handleviewItem(ActionEvent event) {
    	viewItemController = new ViewItemController();
		viewItemController.redirectviewItem(stage,Constants.getUsername());
    }
    @FXML
    protected void handleviewInvoice(ActionEvent event) {
    	viewInvoiceController = new ViewInvoiceController();
		viewInvoiceController.redirectviewInvoice(stage,Constants.getUsername());
    }
    @FXML
    protected void handleviewTransporter(ActionEvent event) {
    	
    }
    
    @FXML
    protected void handleaddInvoice(ActionEvent event) {
    	addInvoiceController = new AddInvoiceController();
    	addInvoiceController.redirectaddInvoice(stage,Constants.getUsername());
    }
    @FXML
    protected void handleaddItem(ActionEvent event) {
    	addItemController = new AddItemController();
    	addItemController.redirectaddItem(stage,Constants.getUsername());
    }
}