package sample;

import javafx.beans.binding.BooleanBinding;
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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

public class ModifyPartyController {
	private Parent parent;
	private Scene scene;
	private Stage stage;
	@FXML
	private Text welcomeText;
	private HomeController homeController;
	@FXML
	private ComboBox state;
	private ViewPartyController viewPartyController;
	@FXML
	private TextField party_name, address, address2, gstin, transport, phone1, phone2, email1, email2;
	String party_name_slt, address_slt, address1_slt, state_slt, gstin_slt, tin_no_slt, transport_slt, phone1_slt,
			phone2_slt, email1_slt, email2_slt, party_id_slt;
	List<String> listOfItem = new ArrayList<String>();
	BooleanBinding isValidGST = new BooleanBinding() {

		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return true;
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
			return true;
		}
	};

	boolean isValidEmail1 = true, isValidState = false;

	public ModifyPartyController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyParty.fxml"));
		fxmlLoader.setController(this);
		try {
			parent = (Parent) fxmlLoader.load();
			scene = new Scene(parent, 600, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
		fetchStateList();
		gstin.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidGST = ValidationCheck(newValue,
						"[0123]{1}[0-9]{1}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9]{1}[A-Za-z]{1}[0-9A-Za-z]{1}");
				System.out.println("Valid Gst------>" + isValidGST.get());
			}
		});

		party_name.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidName = ValidationCheck(newValue, "[a-zA-z0-9 ,]{4}");
				System.out.println("Valid Name------>" + isValidName.get());
			}
		});

		phone1.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidPhone1 = ValidationCheck(newValue, "[0-9]{10}");
				System.out.println("Valid Name------>" + isValidPhone1.get());
			}
		});

		email1.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidEmail1 = EmailValidator.getInstance().isValid(newValue);
				System.out.println("Valid Name------>" + isValidEmail1);
			}
		});
		characterLimit(gstin, 15);
		characterLimit(party_name, 25);
		characterLimit(address, 50);
		characterLimit(phone1, 12);
		characterLimit(phone2, 12);
		characterLimit(email1, 25);
		characterLimit(email2, 25);
		
	}

	private void fetchStateList() {

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

	private void characterLimit(TextField textFeildname, int maxlimit) {
		textFeildname.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (textFeildname.getText().length() > maxlimit) {
					try {
						String s = textFeildname.getText().substring(0, maxlimit);
						textFeildname.setText(s);
					} catch (Exception e) {
					}
				}
			}
		});
	}

	public void redirectmodifyparty(Stage stage, String name, String selectedParty) {
		fetchRowDetals(selectedParty);
		this.stage = stage;
		stage.setTitle("Modify Party");
		stage.setScene(scene);
		welcomeText.setText("Hey " + name + "! Enter your party details here");
		stage.hide();
		stage.show();
		// stage.setFullScreen(true);
	}

	@FXML
	protected void handleBackToHome(ActionEvent event) throws SQLException {
		homeController = new HomeController();
		homeController.redirectHome(stage, "");
	}

	@FXML
	protected void handleUpdateParty(ActionEvent event) throws SQLException {
		if (ValidatePartyFields()) {
			System.out.println("---->valid");
			Connection c;
			try {
				c = DBConnectFlogger.connect();
				String SQL = "DELETE FROM party WHERE party_id = \"" + party_id_slt + "\"" ;
				System.out.println("---> query" + SQL);
				//int rs = c.createStatement().executeUpdate(SQL);
				int rs = c.createStatement().executeUpdate(SQL);
				System.out.println("Has  party" + rs);
				if (rs == 1) {
					String party_id_gen = party_id_slt;
					Connection c2;
					int rowcount2 = 0;
					try {
						c2 = DBConnectFlogger.connect();
						String SQL2 = "INSERT INTO party (party_name,address,address1,state,gstin,transport,phone1,phone2,email1,email2,party_id) VALUES(\""
								+ party_name.getText() + "\",\"" + address.getText() + "\",\"" + address2.getText() + "\",\""
								+ state.getSelectionModel().getSelectedItem() + "\",\"" + gstin.getText() + "\",\""
								+ transport.getText() + "\",\"" + phone1.getText() + "\",\"" + phone2.getText() + "\",\"" + email1.getText()
								+ "\",\"" + email2.getText() + "\",\"" + party_id_gen + "\")";
						System.out.println("Has added party SQL" + SQL2);
						int rs2 = c.createStatement().executeUpdate(SQL2);
						System.out.println("Has added party" + rs);
						if (rs2 == 1) {
							Alert alert2 = ShowAlert.callAlert2("Add Party","Party added Successfully");
					       	if (alert2.showAndWait().get() == ButtonType.OK){
								viewPartyController = new ViewPartyController();
								viewPartyController.redirectviewParty(stage, Constants.getUsername());
				            }
						} else {
							ShowAlert.callAlert("Error", "Please check given Details again.");
						}
					} catch (Exception e) {
							ShowAlert.callAlert("Error", e.getMessage()");
							System.out.println(e);
					}
				}else{
					ShowAlert.callAlert("Error", "Please check given Details again.");
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} else {
			ShowAlert.callAlert("Error", "Please check given Details again.");
		}
	}

	private boolean ValidatePartyFields() {
		if (state.getSelectionModel().getSelectedIndex() != -1) {
			isValidState = true;
		}
		System.out.println(isValidName.get() + "  " + isValidGST.get() + "  " + isValidPhone1.get() + "  "
				+ isValidEmail1 + "  " + isValidState);
		if (isValidName.get() && isValidGST.get() && isValidPhone1.get() && isValidEmail1 && isValidState) {
			System.out.println("------------->");
			return true;
		}
		return false;
	}

	void fetchRowDetals(String selectedRow) {
		String[] splits = selectedRow.replaceAll("^\\s*\\[|\\]\\s*$", "").split("\\s*,\\s*");
		List<String> wordList = Arrays.asList(splits);
		party_name_slt = wordList.get(0);
		address_slt = wordList.get(1);
		address1_slt = wordList.get(2);
		state_slt = wordList.get(3);
		gstin_slt = wordList.get(4);
		transport_slt = wordList.get(5);
		phone1_slt = wordList.get(6);
		phone2_slt = wordList.get(7);
		email1_slt = wordList.get(8);
		email2_slt = wordList.get(9);
		party_id_slt = wordList.get(10);
		party_name.setText(party_name_slt);
		address.setText(address_slt);
		address2.setText(address1_slt);
		gstin.setText(gstin_slt);
		//tin_no.setText(tin_no_slt);
		transport.setText(transport_slt);
		phone1.setText(phone1_slt);
		phone2.setText(phone2_slt);
		email1.setText(email1_slt);
		email2.setText(email2_slt);
		state.setValue(state_slt);
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
