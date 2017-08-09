package sample;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mtechproject.samples.DBConnectFlogger;
import util.ShowAlert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CORBA.SetOverrideType;

public class ModifyInvoiceController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private HomeController homeController;
    String selectedItem;
    @FXML
	private ComboBox itemListO, quantity;
    @FXML
	private TextArea party_address,party_address1;
   // @FXML
    //private Text welcomeText;
    @FXML
    private TextField item_name,item_id,price,invoicenumber,party_name,gst,item_total;
    @FXML
	private DatePicker invoice_date,dispatch_date;
	@FXML
	private TableView<InvoiceEntry> tableView;
	List<String> listOfItem = new ArrayList<String>();
	List<String> listOfItemQuantity = new ArrayList<String>();
	String singleItemPrice;
	private ObservableList<InvoiceEntry> data = FXCollections.observableArrayList();
	
    public ModifyInvoiceController() throws SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyInvoice.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
    }


    public void redirectmodifyinvoice(Stage stage, String name, String selectedInvoice) {
    	fetchDetails(selectedInvoice);
    	this.stage = stage;
        stage.setTitle("Modify Invoice");
        stage.setScene(scene);
       // welcomeText.setText("Hey " + name + "! Modify Invoice Here.");
        stage.hide();
        stage.show();
        //stage.setFullScreen(true);
    }
    
    /*@FXML
    protected void handleAddItem(ActionEvent event) {
    	
    }*/
    
    @FXML
	protected void handleBackToHome(ActionEvent event) throws SQLException {
		homeController = new HomeController();
        homeController.redirectHome(stage, "");
	}
    
    void fetchDetails(String selectedInvoice){
    	Pattern p = Pattern.compile("\\[(.*?)\\]");
		Matcher m = p.matcher(selectedInvoice);
		String ItemList = null;
		while(m.find()) {
			ItemList = m.group(1) + "]";
		    System.out.println(ItemList);
		}
		JSONArray arrayListItem = new JSONArray(ItemList);
		for(int i=0;i<arrayListItem.length();i++){
			JSONObject perItem = (JSONObject) arrayListItem.get(i);
			InvoiceEntry word = new InvoiceEntry();
			word.setTable_item_id((String) perItem.get("item_id"));
			word.setTable_item_name((String) perItem.get("item_name"));
			word.setTable_quantity((String) perItem.get("item_quantity"));
			word.setTable_price((String) perItem.get("item_total_price"));
			word.setTable_gst((String) perItem.get("item_gst"));
			word.setTable_total((String) perItem.get("item_total"));
			data.add(word);
		}
		tableView.setItems(data);
		System.out.println(arrayListItem.get(0));
		String newList = selectedInvoice.replace(ItemList,"");
		System.out.println(newList);
		String []splits = newList.replaceAll("^\\s*\\[|\\]\\s*$", "").split("\\s*,\\s*");
		List<String> wordList = Arrays.asList(splits); 
		
		ObservableList<String> party_name_selected = FXCollections.observableArrayList();
		//party_name_selected.add(wordList.get(7));
		String invoice_date_selected= wordList.get(2);
		String invoice_number_selected= wordList.get(3);
		String dispatch_date_selected= wordList.get(4);
		String party_address_selected= wordList.get(5);
		String party_address_selected1= wordList.get(6);
		//party_name.setItems(party_name_selected);
		//party_name.getSelectionModel().selectFirst();
		party_name.setText(wordList.get(7));
		invoicenumber.setText(invoice_number_selected);
		dispatch_date.setValue(localDateFormatter(dispatch_date_selected));
	    invoice_date.setValue(localDateFormatter(invoice_date_selected));
	    party_address.setText(party_address_selected);
	    party_address1.setText(party_address_selected1);
		//System.out.println(party_id_selected+ "," +invoice_date_selected+ "," + dispatch_date_selected + ","+ invoice_number_selected);
	    /*for(int i=0;i<1;i++){
		    InvoiceEntry word = new InvoiceEntry();
			word.setTable_item_id(item_id.getText());
			word.setTable_item_name(itemListO.getItems().get(itemListO.getSelectionModel().getSelectedIndex()).toString());
			word.setTable_quantity(quantity.getItems().get(quantity.getSelectionModel().getSelectedIndex()).toString());
			word.setTable_price(price.getText());
			data.add(word);
	    }
		tableView.setItems(data);*/
	    
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
			ResultSet rs_item = c.createStatement().executeQuery(SQL);

			if (rs_item.last()) {
				rowcount = rs_item.getRow();
				rs_item.beforeFirst(); // not rs.first() because the rs.next() below
									// will move on, missing the first element
			}
			System.out.println("RS size for party " + rowcount);
			ObservableList<String> row = FXCollections.observableArrayList("CSE");
			while (rs_item.next()) {
				String current = rs_item.getString("item_name");
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
    }
    
	@FXML
	protected void handleOnClickAddInvoice(ActionEvent event) throws SQLException {
		
		
	}
	LocalDate localDateFormatter(String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate localDate = LocalDate.parse(date, formatter);
	    return localDate;
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
	protected void handleOnClickUpdateInvoice(ActionEvent event) {
		InvoiceEntry selectedItem = tableView.getSelectionModel().getSelectedItem();
		tableView.getItems().remove(selectedItem);
		tableView.getSelectionModel().clearSelection();
	}
	@FXML
	protected void OnfreeItemClick(ActionEvent event) throws SQLException {
		InvoiceEntry word = new InvoiceEntry();
		word.setTable_item_id(item_id.getText());
		word.setTable_item_name(itemListO.getItems().get(itemListO.getSelectionModel().getSelectedIndex()).toString());
		word.setTable_quantity(quantity.getItems().get(quantity.getSelectionModel().getSelectedIndex()).toString());
		word.setTable_price(String.valueOf(0));
		word.setTable_gst(String.valueOf(0));
		word.setTable_total(String.valueOf(0));
		data.add(word);
		tableView.setItems(data);
		item_id.clear();
		price.clear();
		gst.clear();
		item_total.clear();
		itemListO.getSelectionModel().clearSelection();
		quantity.getSelectionModel().clearSelection();
	}
    
}