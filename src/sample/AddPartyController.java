package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mtechproject.samples.DBConnectFlogger;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddPartyController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    @FXML
    private Text welcomeText;
    private HomeController homeController;
    @FXML
    private TextField party_name,address,address2,gstin,tin_no,transport,phone1,phone2,email1,email2,party_id;
    @FXML
    private ComboBox state;
    List<String> listOfItem = new ArrayList<String>();
    public AddPartyController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddParty.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
        fetchStateList();
        System.out.println(getString(5) + getInt(4) + getString(1) + getInt(1) + getString(1) + getInt(1));
    }
    
    public void fetchStateList(){
    	Connection c;
		int rowcount = 0;
		try {
			c = DBConnectFlogger.connect();
			String SQL = "SELECT * from state";
			// String SQL = "select * from user where username = \"" +
			// userName.getText() + "\" and password = \"" +
			// passwordField.getText() + "\";" ;
			System.out.println("---> query" + SQL);
			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			if (rs.last()) {
				rowcount = rs.getRow();
				rs.beforeFirst(); // not rs.first() because the rs.next() below
									// will move on, missing the first element
			}
			System.out.println("RS size for party " + rowcount);
			ObservableList<String> row = FXCollections.observableArrayList("CSE");
			while (rs.next()) {
				String current = rs.getString("state_name");
				listOfItem.add(current);
			}
			ObservableList<String> observableListOfParty = FXCollections.observableArrayList(listOfItem);
			// partyList.getItems().clear();
			state.getItems().addAll(observableListOfParty);
		} catch (Exception e) {
			System.out.println(e);
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
	         String SQL = "INSERT INTO party (party_name,address,address1,state,gstin,tin_no,transport,phone1,phone2,email1,email2,party_id) VALUES(\""
	          + party_name.getText() 
	          + "\",\"" + address.getText()
	          + "\",\"" + address2.getText() 
	          + "\",\"" + state.getSelectionModel().getSelectedItem() 
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
	
	protected String getString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
	
	protected String getInt(int length) {
        String SALTCHARS = "0123456789";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
    
}