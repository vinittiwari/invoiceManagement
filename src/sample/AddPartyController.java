package sample;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mtechproject.samples.DBConnectFlogger;
import util.Constants;
import util.GenerateRandom;
import util.ShowAlert;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

public class AddPartyController {
	private Parent parent;
	private Scene scene;
	private Stage stage;
	@FXML
	private Text welcomeText;
	private HomeController homeController;
	@FXML
	private TextField party_name, address, address2, gstin, transport, phone1, phone2, email1, email2;
	@FXML
	private ComboBox state;
	BooleanBinding isValidGST = new BooleanBinding() {
		
		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return false;
		}
	};
	BooleanBinding isValidName = new BooleanBinding() {
		
		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return false;
		}
	};
	BooleanBinding isValidPhone1 = new BooleanBinding() {
		
		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return false;
		}
	};
	
	boolean isValidEmail1= false,isValidState=false;
	

	@FXML
	private Button addPartySubmit;
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
	
		/*addPartySubmit.disableProperty().bind(party_name.textProperty().isEmpty()
        		.or(address.textProperty().isEmpty())
        		.or(isValidGST));*/
		
		/*party_name.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				addPartySubmit.disableProperty().bind(ValidationCheck(newValue, "^[a-zA-Z\\s]{2,29}$"));
			}
		});*/
		gstin.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidGST = ValidationCheck(newValue, "[0123]{1}[0-9]{1}[A-Z]{5}[0-9]{4}[A-Z]{1}[0-9]{1}[A-Z]{1}[0-9]{1}");
				System.out.println("Valid Gst------>"+isValidGST.get());
			}
		});
		
		party_name.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidName = ValidationCheck(newValue, "[a-zA-z0-9 ,]{4}");
				System.out.println("Valid Name------>"+isValidName.get());
			}
		});
		
		phone1.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidPhone1 = ValidationCheck(newValue, "[0-9]{10}");
				System.out.println("Valid Name------>"+isValidPhone1.get());
			}
		});
			
		email1.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidEmail1 = EmailValidator.getInstance().isValid(newValue);
				System.out.println("Valid Name------>"+isValidEmail1);
			}
		});
		

		
		characterLimit(gstin, 15);
		characterLimit(party_name, 25);
		characterLimit(address, 50);
		characterLimit(address2, 50);
		characterLimit(phone1, 12);
		characterLimit(phone2, 12);
		characterLimit(email1, 25);
		characterLimit(email2, 25);
		/*gstin.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				 if (gstin.getText().length() > 15) {
		                String s = gstin.getText().substring(0, 15);
		                gstin.setText(s);
		            }
			}
		});
		
		party_name.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				 if (party_name.getText().length() > 25) {
		                String s = party_name.getText().substring(0, 25);
		                party_name.setText(s);
		            }
			}
		});*/

		fetchStateList();
		System.out.println(getString(5) + getInt(4) + getString(1) + getInt(1) + getString(1) + getInt(1));
	}


	private void characterLimit(TextField textFeildname,int maxlimit) {
		textFeildname.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				 if (textFeildname.getText().length() > 15) {
					 try{
		                String s = textFeildname.getText().substring(0, maxlimit);
		                textFeildname.setText(s);
					 }catch (Exception e) {
					}}
			}
		});
	}

	public void fetchStateList() {
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
		// stage.setFullScreen(true);
	}

	@FXML
	protected void handleAddParty(ActionEvent event) {
		if(ValidatePartyFields()){
			String party_id_gen = party_name.getText().substring(0, 3).toUpperCase() + GenerateRandom.generateRandomChars("0123456789",2);
			Connection c;
			int rowcount = 0;
			try {
				c = DBConnectFlogger.connect();
				String SQL = "INSERT INTO party (party_name,address,address1,state,gstin,transport,phone1,phone2,email1,email2,party_id) VALUES(\""
						+ party_name.getText() + "\",\"" + address.getText() + "\",\"" + address2.getText() + "\",\""
						+ state.getSelectionModel().getSelectedItem() + "\",\"" + gstin.getText() + "\",\""
						+ transport.getText() + "\",\"" + phone1.getText() + "\",\"" + phone2.getText() + "\",\"" + email1.getText()
						+ "\",\"" + email2.getText() + "\",\"" + party_id_gen + "\")";
				System.out.println("Has added party SQL" + SQL);
				int rs = c.createStatement().executeUpdate(SQL);
				System.out.println("Has added party" + rs);
				if (rs == 1) {
					Alert alert = ShowAlert.callAlert2("Add Party","Item added Successfully");
			       	if (alert.showAndWait().get() == ButtonType.OK){
						homeController = new HomeController();
			            homeController.redirectHome(stage, Constants.getUsername());
		            }
				} else {
					ShowAlert.callAlert("Error", "Please check given Details again.");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}else{
			ShowAlert.callAlert("Add Party", "Please enter Correct details.");
		}
	}
	
	private boolean ValidatePartyFields() {
		if(state.getSelectionModel().getSelectedIndex() != -1){
			isValidState = true;
		}
		System.out.println(isValidName.get() +"  "+ isValidGST.get() +"  "+ isValidPhone1.get() +"  "+ isValidEmail1 +"  "+ isValidState);
		if(isValidName.get() && isValidGST.get() && isValidPhone1.get() && isValidEmail1 && isValidState){
			System.out.println("------------->");
			return true;
		}
		return false;
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

	public BooleanBinding ValidationCheck(String Value, String pattern) {
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);
		// Now create matcher object.
		Matcher m = r.matcher(Value);
		BooleanBinding item_name_ck = new BooleanBinding() {
			@Override
			protected boolean computeValue() {
				// TODO Auto-generated method stub
				if (m.find()) {
					return true;
				} else {
					return false;
				}
			}
		};
		return item_name_ck;
	}
	
	

}