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

import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddPartyController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    @FXML
    private Text welcomeText;
    private HomeController homeController;
    @FXML
    private TextField party_name,address,state,gstin,tin_no,transport,phone1,phone2,email1,email2,party_id;
    

    public AddPartyController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddParty.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
    }


    public void redirectaddParty(Stage stage, String name) {
    	this.stage = stage;
        stage.setTitle("AddParty");
        stage.setScene(scene);
        welcomeText.setText("Hey " + name + "! Enter your party details here");
        stage.hide();
        stage.show();
        //stage.setFullScreen(true);
    }
    
    @FXML
    protected void handleAddParty(ActionEvent event) {
    	Connection c;
    	
        int rowcount = 0;
        try{  
	         c = DBConnectFlogger.connect();  
	         String SQL = "INSERT INTO party (party_name,address,state,gstin,tin_no,transport,phone1,phone2,email1,email2,party_id) VALUES(\""
	          + party_name.getText() 
	          + "\",\"" + address.getText() 
	          + "\",\"" + state.getText() 
	          + "\",\"" + gstin.getText()
	          + "\"," + tin_no.getText()
	          + ",\"" + transport.getText()
	          + "\"," + phone1.getText()
	          + "," + phone2.getText()
	          + ",\"" + email1.getText()
	          + "\",\"" + email2.getText()
	          + "\",\"" + party_id.getText()
	          + "\")";
	         System.out.println("Has added party SQL" + SQL);
	         int rs = c.createStatement().executeUpdate(SQL);
	         System.out.println("Has added party" + rs);
        }catch (Exception e) {
			 System.out.println(e);
		}
    }

	@FXML
	protected void handleBackToHome(ActionEvent event) throws SQLException {
		homeController = new HomeController();
        homeController.redirectHome(stage, "");
	}
    
}