package sample;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import mtechproject.samples.DBConnectFlogger;
import util.Constants;

public class AddInvoiceController {

	private Parent parent;
	private Scene scene;
	private Stage stage;
	
	String singleItemPrice;
	// @FXML
	// private Text welcomeText;
	@FXML
	private ComboBox itemListO, quantity, party_name;
	String selectedParty_id;
	private HomeController homeController;
	@FXML
	private javafx.scene.control.TextField item_name,item_id, price;
	@FXML
	private TextArea party_address,party_address1;
	private ObservableList<InvoiceEntry> data = FXCollections.observableArrayList();
	ResultSet rs;
	List<String> listOfItem = new ArrayList<String>();
	List<String> listOfParty = new ArrayList<String>();
	List<String> listOfItemQuantity = new ArrayList<String>();
	@FXML
	private TableView<InvoiceEntry> tableView, table;
	@FXML
	private DatePicker invoice_date,dispatch_date;
	TableColumn<InvoiceEntry, String> table_item_id, table_price, table_item_name;
	TableColumn<InvoiceEntry, String> col = new TableColumn<>();
	private ObservableList<ObservableList<String>> data2;

	public AddInvoiceController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddInvoice.fxml"));
		fxmlLoader.setController(this);
		try {
			parent = (Parent) fxmlLoader.load();
			scene = new Scene(parent, 600, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Connection c;
		int rowcount = 0;
		try {
			c = DBConnectFlogger.connect();
			String SQL = "SELECT * from item";
			// String SQL = "select * from user where username = \"" +
			// userName.getText() + "\" and password = \"" +
			// passwordField.getText() + "\";" ;
			System.out.println("---> query" + SQL);
			// ResultSet
			rs = c.createStatement().executeQuery(SQL);

			if (rs.last()) {
				rowcount = rs.getRow();
				rs.beforeFirst(); // not rs.first() because the rs.next() below
									// will move on, missing the first element
			}
			System.out.println("RS size for party " + rowcount);
			ObservableList<String> row = FXCollections.observableArrayList("CSE");
			while (rs.next()) {
				String current = rs.getString("item_name");
				listOfItem.add(current);
			}
			ObservableList<String> observableListOfParty = FXCollections.observableArrayList(listOfItem);
			// partyList.getItems().clear();
			itemListO.getItems().addAll(observableListOfParty);
		} catch (Exception e) {
			System.out.println(e);
		}

		quantity.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				int IntnewValue = Integer.parseInt(newValue);
				int IntsingleItemPrice = Integer.parseInt(singleItemPrice);
				price.setText(String.valueOf(IntsingleItemPrice * IntnewValue));
			}
		});

		itemListO.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("Value is: " + newValue);
				try {
					Connection c1;
					c1 = DBConnectFlogger.connect();
					String SQL = "SELECT * from item WHERE item_name = \"" + newValue + "\"";
					// String SQL = "select * from user where username = \"" +
					// userName.getText() + "\" and password = \"" +
					// passwordField.getText() + "\";" ;
					System.out.println("---> query" + SQL);
					// ResultSet
					ResultSet rs = c1.createStatement().executeQuery(SQL);
					String current = null, currentprice = null;
					while (rs.next()) {
						if (rs.first()) {
							current = rs.getString("item_id");
							currentprice = rs.getString("price");
						}
					}
					for (int i = 1; i <= 100; i++) {
						listOfItemQuantity.add(String.valueOf(i));
					}
					ObservableList<String> observableListOfQuantity = FXCollections
							.observableArrayList(listOfItemQuantity);
					System.out.println("---->" + current);
					item_id.setText(current);
					singleItemPrice = currentprice;
					price.setText(currentprice);
					quantity.getItems().addAll(observableListOfQuantity);
					quantity.getSelectionModel().selectFirst();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		Connection c3;
		int rowcount3 = 0;
		try {
			c3 = DBConnectFlogger.connect();
			String SQL = "SELECT * from party";
			// String SQL = "select * from user where username = \"" +
			// userName.getText() + "\" and password = \"" +
			// passwordField.getText() + "\";" ;
			System.out.println("---> query" + SQL);
			// ResultSet
			ResultSet rs_party = c3.createStatement().executeQuery(SQL);

			if (rs_party.last()) {
				rowcount3 = rs_party.getRow();
				rs_party.beforeFirst(); // not rs.first() because the rs.next()
										// below will move on, missing the first
										// element
			}
			System.out.println("RS size for party " + rowcount3);
			ObservableList<String> row = FXCollections.observableArrayList("CSE");
			while (rs_party.next()) {
				String current = rs_party.getString("party_name");
				listOfParty.add(current);
			}
			ObservableList<String> observableListOfParty = FXCollections.observableArrayList(listOfParty);
			// partyList.getItems().clear();
			party_name.getItems().addAll(observableListOfParty);
		} catch (Exception e) {
			System.out.println(e);
		}

		party_name.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("Value is: " + newValue);
				try {
					Connection c1;
					c1 = DBConnectFlogger.connect();
					String SQL = "SELECT * from party WHERE party_name = \"" + newValue + "\"";
					// String SQL = "select * from user where username = \"" +
					// userName.getText() + "\" and password = \"" +
					// passwordField.getText() + "\";" ;
					System.out.println("---> query" + SQL);
					// ResultSet
					ResultSet rs = c1.createStatement().executeQuery(SQL);
					String current = null,current1=null,current2 = null;
					while (rs.next()) {
						if (rs.first()) {
							current = rs.getString("address");
							current1 = rs.getString("address1");
							current2 = rs.getString("party_id");
						}
					}
					System.out.println("---->" + current);
					party_address.setText(current);
					party_address1.setText(current1);
					selectedParty_id = current2;
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		new ComboBoxAutoComplete(party_name);
		new ComboBoxAutoComplete(itemListO);
	}
	
	

	public void redirectaddInvoice(Stage stage, String name) {
		this.stage = stage;
		stage.setTitle("AddInvoice");
		stage.setScene(scene);
		// welcomeText.setText("Hey " + name + "! Enter your Invoice details
		// here");
		stage.hide();
		stage.show();
		//stage.setFullScreen(true);
	}

	public void populatePartyList() {

	}

	@FXML
	protected void handleOnClickAddItem(ActionEvent event) {
		InvoiceEntry word = new InvoiceEntry();
		word.setTable_item_id(item_id.getText());
		word.setTable_item_name(itemListO.getItems().get(itemListO.getSelectionModel().getSelectedIndex()).toString());
		word.setTable_quantity(quantity.getItems().get(quantity.getSelectionModel().getSelectedIndex()).toString());
		word.setTable_price(price.getText());
		data.add(word);
		tableView.setItems(data);
		itemListO.getSelectionModel().clearSelection();
		item_id.clear();
		item_name.clear();
		quantity.getSelectionModel().clearSelection();
	}

	@FXML
	protected void handleOnClickDeleteInvoice(ActionEvent event) {
		InvoiceEntry selectedItem = tableView.getSelectionModel().getSelectedItem();
		tableView.getItems().remove(selectedItem);
		tableView.getSelectionModel().clearSelection();
	}

	@FXML
	protected void handleOnClickAddInvoice(ActionEvent event) throws SQLException {
		System.out.println("*****handleOnClickAddInvoice**********");
		String JsonItem = ConvertItemIntoJson();
		String party_name_db = party_name.getItems().get(party_name.getSelectionModel().getSelectedIndex()).toString();
		String party_id_db = selectedParty_id;
		LocalDate date_invoice = invoice_date.getValue();
		LocalDate dispatch_invoice_date = dispatch_date.getValue();
		String address = party_address.getText();
		String address1 = party_address1.getText();
		int invoice_number=122321;
		Connection c2;
		c2 = DBConnectFlogger.connect();
		String SQL = "INSERT INTO invoice (item,party_name,invoice_date,invoice_number,dispatch_date,address,address1,party_id) VALUES( \"" + JsonItem + "\",\"" + party_name_db + "\",\""+ date_invoice + "\","+ invoice_number +",\"" + dispatch_invoice_date +"\",\""+ address+"\",\""+ address1+"\",\"" + party_id_db + "\")";
		System.out.println("---> query" + SQL);
		int rs = c2.createStatement().executeUpdate(SQL);
		System.out.println("Has added party" + rs);
		if (rs == 1) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Add Invoice");
			alert.setHeaderText("Added Invoice");
			alert.setContentText("Added invoice successfully");
			
			//alert.showAndWait();
			//Optional<ButtonType> result = alert.showAndWait();
			if (alert.showAndWait().get() == ButtonType.OK){
				homeController = new HomeController();
	            homeController.redirectHome(stage, Constants.getUsername());
            }
		}
	}

	private String ConvertItemIntoJson() {

		JSONArray ItemArray = new JSONArray();
		for (int i = 0; i < tableView.getItems().size(); i++) {
			JSONObject ItemJson = new JSONObject();
			ItemJson.put("item_id", tableView.getItems().get(i).getTable_item_id());
			ItemJson.put("item_name", tableView.getItems().get(i).getTable_item_name());
			ItemJson.put("item_quantity", tableView.getItems().get(i).getTable_quantity());
			ItemJson.put("item_total_price", tableView.getItems().get(i).getTable_price());
			ItemArray.put(ItemJson);
		}
		String JsonItem = ItemArray.toString().replace("\"", "\\\"");
		System.out.println(JsonItem);
		return JsonItem;
	}
	
	@FXML
	protected void handleBackToHome(ActionEvent event) throws SQLException {
		homeController = new HomeController();
        homeController.redirectHome(stage, "");
	}
	
	

}
