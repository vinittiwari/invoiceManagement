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
import java.util.Arrays;
import java.util.List;

public class ModifyPartyController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    @FXML
    private Text welcomeText;
    private HomeController homeController;
    @FXML
    private TextField party_name,address,address1,state,gstin,tin_no,transport,phone1,phone2,email1,email2,party_id;
    String party_name_slt,address_slt,address1_slt,state_slt,gstin_slt,tin_no_slt,transport_slt,phone1_slt,phone2_slt,email1_slt,email2_slt,party_id_slt;

    public ModifyPartyController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyParty.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
        
    }


    public void redirectmodifyparty(Stage stage, String name,String selectedParty) {
    	fetchRowDetals(selectedParty);
    	this.stage = stage;
        stage.setTitle("Modify Party");
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
	
	@FXML
	protected void handleUpdateParty(ActionEvent event) throws SQLException {
		
	}
	
	void fetchRowDetals(String selectedRow){
		String []splits = selectedRow.replaceAll("^\\s*\\[|\\]\\s*$", "").split("\\s*,\\s*");
		List<String> wordList = Arrays.asList(splits); 
		party_name_slt=wordList.get(0);
		address_slt=wordList.get(1);
		address1_slt=wordList.get(2);
		state_slt=wordList.get(3);
		gstin_slt=wordList.get(4);
		tin_no_slt=wordList.get(5);
		transport_slt=wordList.get(6);
		phone1_slt=wordList.get(7);
		phone2_slt=wordList.get(8);
		email1_slt=wordList.get(9);
		email2_slt=wordList.get(10);
		party_id_slt=wordList.get(11);
		party_name.setText(party_name_slt);
		address.setText(address_slt);
		address1.setText(address1_slt);
		state.setText(state_slt);
		gstin.setText(gstin_slt);
		tin_no.setText(tin_no_slt);
		transport.setText(transport_slt);
		phone1.setText(phone1_slt);
		phone2.setText(phone2_slt);
		email1.setText(email1_slt);
		email2.setText(email2_slt);
		party_id.setText(party_id_slt);
	}
    
}