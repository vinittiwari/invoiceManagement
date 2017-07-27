package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mtechproject.samples.DBConnectFlogger;
import util.ShowAlert;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddItemController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private HomeController homeController;
    @FXML
    private Text welcomeText;
    @FXML
    private TextField item_name,item_id,price,status,gms,item_code,unit;
    

    public AddItemController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddItem.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
    }


    public void redirectaddItem(Stage stage, String name) {
    	this.stage = stage;
        stage.setTitle("AddParty");
        stage.setScene(scene);
        welcomeText.setText("Hey " + name + "! Enter your party details here");
        stage.hide();
        stage.show();
        //stage.setFullScreen(true);
    }
    
    @FXML
    protected void handleAddItem(ActionEvent event) {
    	Connection c;
    	
        int rowcount = 0;
        int rs = 0;
        try{  
	         c = DBConnectFlogger.connect();  
	         String SQL = "INSERT INTO item (item_name,item_id,price,item_code,unit,status,gms) VALUES(\""
	          + item_name.getText() 
	          + "\",\"" + item_id.getText() 
	          + "\",\"" + price.getText() 
	          + "\",\"" + item_code.getText() 
	          + "\",\"" + unit.getText() 
	          + "\",\"" + status.getText() 
	          + "\",\"" + gms.getText() 
	          + "\")";
	         System.out.println("Has added party SQL" + SQL);
	         rs = c.createStatement().executeUpdate(SQL);
	         System.out.println("Has added party" + rs);
        }catch (Exception e) {
			 System.out.println(e);
		}
	        if(rs==1){
	       	 ShowAlert.callAlert("Add Item","Item added Successfully");
	        }else{
	       	 ShowAlert.callAlert("Error","Please check given Details again.");
	        }
    }
    
    @FXML
	protected void handleBackToHome(ActionEvent event) throws SQLException {
		homeController = new HomeController();
        homeController.redirectHome(stage, "");
	}
    
}