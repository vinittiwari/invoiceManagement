package sample;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import mtechproject.samples.DBConnectFlogger;
import util.Constants;
import util.ShowAlert;

public class AddInvoiceController {

	private Parent parent;
	private Scene scene;
	private Stage stage;
	String currentgst = null, cess;
	// @FXML
	// private Text welcomeText;
	int isSelectedFreeItem = 0;
	String singleItemPrice;
	String party_state,gstNo=null;
	int userstate;
	Boolean isSameState = false, isFirstInvoice = false ;
	int oldInvoice = 0;
	@FXML 
	private Label invoicedate_valid,dispatchdate_valid,party_valid,itemrequired_valid,tramspoter_valid,vehical_valid,mobile_valid;
	@FXML
	private ComboBox itemListO, quantity, party_name,transporter;
	String selectedParty_id;
	float gst_amount,gstrate,cess_amount,reduceAfterGST;
	private HomeController homeController;
	@FXML
	private javafx.scene.control.TextField item_id, price,gst,item_total,invoice_total,party_state_slt,invoicenumber,cgst_totalAmount,sgst_totalAmount,igst_totalAmount,cess_totalAmount,itemPriceTotal,mobilenumTransporter,vehicalTransporter;	
	@FXML
	private TextArea party_address;
	private ObservableList<InvoiceEntry> data = FXCollections.observableArrayList();
	ResultSet rs;
	List<String> listOfItem = new ArrayList<String>();
	List<String> listOfTransport = new ArrayList<String>();
	List<String> listOfParty = new ArrayList<String>();
	List<String> listOfItemQuantity = new ArrayList<String>();
	@FXML
	private TableView<InvoiceEntry> tableView, table;
	@FXML
	private DatePicker invoice_date,dispatch_date;
	TableColumn<InvoiceEntry, String> table_item_id, table_price, table_item_name;
	TableColumn<InvoiceEntry, String> col = new TableColumn<>();
	private ObservableList<ObservableList<String>> data2;
	BooleanBinding isValidPhone1 = new BooleanBinding() {
		
		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return false;
		}
	};
	BooleanBinding isValidTMobile = new BooleanBinding() {
		
		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return true;
		}
	};

	BooleanBinding isValidTVehical = new BooleanBinding() {
		
		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return true;
		}
	};
	
	public AddInvoiceController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddInvoice.fxml"));
		fxmlLoader.setController(this);
		try {
			parent = (Parent) fxmlLoader.load();
			scene = new Scene(parent, 600, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
		gst.setEditable(false);
		cgst_totalAmount.setEditable(false);
		sgst_totalAmount.setEditable(false);
		igst_totalAmount.setEditable(false);
		cess_totalAmount.setEditable(false);
		itemPriceTotal.setEditable(false);
		item_total.setEditable(false);
		price.setEditable(false);
		item_id.setEditable(false);
		invoice_total.setEditable(false);
		//party_address.setEditable(false);
		//invoicenumber.setEditable(false);
		party_state_slt.setEditable(false);
		userstate = Constants.getState();
		
		itemrequired_valid.setVisible(false);
		party_valid.setVisible(false);
		invoicedate_valid.setVisible(false);
		dispatchdate_valid.setVisible(false);
		tramspoter_valid.setVisible(false);
		vehical_valid.setVisible(false);
		mobile_valid.setVisible(false);
		
		
		
		System.out.println("userstate " + userstate);
		populateTranspoter();
		
		mobilenumTransporter.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidPhone1 = ValidationCheck(newValue, "[0-9]{10}");
				System.out.println("Valid Name------>"+isValidPhone1.get());
			}
		});
		
		
		
		characterLimit(mobilenumTransporter, 12);
		characterLimit(vehicalTransporter, 13);
		
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
		
		Connection c1;
		try{
			c1 = DBConnectFlogger.connect();
			String SQL = "SELECT invoice_number from invoice";
			System.out.println("---> query" + SQL);
			ResultSet rs = c1.createStatement().executeQuery(SQL);
			String current = null;
			int size=0;
			while (rs.next()) {
			    size++;
			}
			rs.beforeFirst();
			System.out.println("Size of invoice query"+ size);
			if(size != 0){
				while (rs.next()) {
					if (rs.last()) {
						current = rs.getString("invoice_number");
						System.out.println(current);
						break;
					}
				}
			}else{
				current = String.valueOf(Constants.getInvoiceNo());
			}
			oldInvoice = Integer.parseInt(current);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		quantity.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("onChangeItem Quantity"+newValue);
				if(newValue != null){
					int IntnewValue = Integer.parseInt(newValue);
					int IntsingleItemPrice = Integer.parseInt(singleItemPrice);
					cess_amount = ((IntsingleItemPrice * IntnewValue) * (Float.parseFloat(cess)/100));
					
					float quantity_item_picetotal =((IntsingleItemPrice * IntnewValue) * (Float.parseFloat(currentgst)/100)) + (IntsingleItemPrice * IntnewValue) + cess_amount;
					//gst_amount = ((IntsingleItemPrice * IntnewValue) * (Float.parseFloat(currentgst)/100));
					reduceAfterGST = (IntsingleItemPrice * 1)/(1+(Float.parseFloat(currentgst)/100)*1) * IntnewValue;
					//System.out.println("--->"+item_picetotal + "--->" + Integer.parseInt(currentprice) + "--->" + Float.parseFloat(currentgst)/100);
					//item_total.setText(String.valueOf(quantity_item_picetotal));
					price.setText(String.valueOf(reduceAfterGST));
					
					
					
					System.out.println("reducedGST--->"+reduceAfterGST);
					gst_amount = ((IntsingleItemPrice * IntnewValue)-(reduceAfterGST));
					item_total.setText(String.valueOf(IntsingleItemPrice * IntnewValue));
					}
				}
		});
		
		 invoice_date.valueProperty().addListener((ov, oldValue, newValue) -> {
			 	invoicedate_valid.setVisible(false);
	        });
		 dispatch_date.valueProperty().addListener((ov, oldValue, newValue) -> {
			 	dispatchdate_valid.setVisible(false);
	        });

		itemListO.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("onChangeItem itemList"+newValue + "---->oldValue--->" + oldValue);
				if(newValue != null && oldValue != newValue){
				try {
					Connection c5;
					c5 = DBConnectFlogger.connect();
					String SQL = "SELECT * from item WHERE item_name = \"" + newValue + "\"";
					// String SQL = "select * from user where username = \"" +
					// userName.getText() + "\" and password = \"" +
					// passwordField.getText() + "\";" ;
					System.out.println("---> query" + SQL);
					// ResultSet
					ResultSet rs = c5.createStatement().executeQuery(SQL);
					String current = null, currentprice = null;
					while (rs.next()) {
						if (rs.first()) {
							current = rs.getString("item_id");
							currentprice = rs.getString("price");
							currentgst = rs.getString("rate");
							cess = rs.getString("cess");
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
					
					 	int IntnewValue = 1;
					    float changedPricePer = (Float.parseFloat(currentprice) * IntnewValue)/(1+(Float.parseFloat(currentgst)/100)*IntnewValue);
						price.setText(String.valueOf(changedPricePer));
						
						gst_amount = ((Float.parseFloat(currentprice) * IntnewValue)-(changedPricePer));
						
					quantity.getItems().addAll(observableListOfQuantity);
					quantity.getSelectionModel().selectFirst();
					float item_picetotal =(Integer.parseInt(currentprice) * (Float.parseFloat(currentgst)/100)) + Integer.parseInt(currentprice);
					System.out.println("--->"+item_picetotal + "--->" + Integer.parseInt(currentprice) + "--->" + Float.parseFloat(currentgst)/100);
					//item_total.setText(String.valueOf(item_picetotal));
					item_total.setText(String.valueOf(currentprice));
 					gst.setText(currentgst + "%");
 					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}}
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

		transporter.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				tramspoter_valid.setVisible(false);
			}
		});
		
		mobilenumTransporter.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidTMobile = ValidationCheck(newValue, "[0-9]{10}");
				System.out.println("Valid Name------>"+isValidTMobile.get());
				if(isValidTMobile.get() == false){
					mobile_valid.setVisible(true);
				}else{
					mobile_valid.setVisible(false);
				}
			}
		});
		
		vehicalTransporter.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidTVehical = ValidationCheck(newValue, "[A-Z]{2}[ -][0-9]{2}[ -][A-Z]{2}[ -][0-9]{4}");
				System.out.println("Valid Name------>"+isValidTVehical.get());
				if(isValidTVehical.get() == false){
					vehical_valid.setVisible(true);
				}else{
					vehical_valid.setVisible(false);
				}
			}
		});
		
		party_name.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("onChangeItem Party"+newValue);
				party_valid.setVisible(false);
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
							party_state = rs.getString("state");
							if(!rs.getString("gstin").isEmpty()) gstNo = rs.getString("gstin");
						}
					}
					if(gstNo != null) System.out.println("---->" + current+ "--->" + gstNo);
					int partyStateCode = Constants.getState();
					if(gstNo != null) partyStateCode = Integer.parseInt(gstNo.substring(0,2));
					System.out.println("partyStateCode---->" + partyStateCode +"->Userstate---->"+ userstate);
					if(userstate == partyStateCode){
						isSameState = true;
						System.out.println("-------------->" + isSameState);
					}
					party_address.setText(current);
					//party_address1.setText(current1);
					selectedParty_id = current2;
					party_state_slt.setText(party_state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		new ComboBoxAutoComplete(party_name);
		new ComboBoxAutoComplete(itemListO);
		invoicenumber.setText(String.valueOf(oldInvoice+1));
	}
	
	private void characterLimit(TextField textFeildname, int maxlimit) {
		textFeildname.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				 if (textFeildname.getText().length() > maxlimit) {
					 try{
		                String s = textFeildname.getText().substring(0, maxlimit);
		                textFeildname.setText(s);
					 }catch (Exception e) {
					}}
			}
		});
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
		System.out.println("----------->"+ isSameState);
		if( party_name.getSelectionModel().getSelectedIndex() != -1){
			if(!isSameState){
				InvoiceEntry word = new InvoiceEntry();
				word.setTable_item_id(item_id.getText());
				word.setTable_item_name(itemListO.getItems().get(itemListO.getSelectionModel().getSelectedIndex()).toString());
				word.setTable_quantity(quantity.getItems().get(quantity.getSelectionModel().getSelectedIndex()).toString());
				word.setTable_price(price.getText());
				word.setTable_gst("0" +"%");
				word.setTable_gst_amount("0");
				word.setTable_total(item_total.getText());
				word.setTable_igst(gst.getText()+ "%");
				word.setTable_igst_amount(String.valueOf((gst_amount)));
				word.setTable_cgst("0"+ "%");
				word.setTable_cgst_amount("0");
				word.setTable_sgst("0"+ "%");
				word.setTable_sgst_amount("0");
				word.setTable_cess(String.valueOf(cess)+ "%");
				word.setTable_cess_amount(String.valueOf(cess_amount));
				data.add(word);
				tableView.setItems(data);
				
				item_id.clear();
				price.clear();
				gst.clear();
				item_total.clear();
				itemListO.getSelectionModel().clearSelection();
				quantity.getSelectionModel().clearSelection();
				
				
				//ObservableList subentries = FXCollections.observableArrayList();
				
		        
		        totalItems("Total(AP+GST)",invoice_total);
		        totalItems("Price (Q*P)",itemPriceTotal);
		        subColumn_totalItems(4,"Amount",cgst_totalAmount);
		        subColumn_totalItems(5,"Amount",sgst_totalAmount);
		        subColumn_totalItems(6,"Amount",igst_totalAmount);
		        subColumn_totalItems(7,"Amount",cess_totalAmount);
		       
			}else{
				InvoiceEntry word = new InvoiceEntry();
				word.setTable_item_id(item_id.getText());
				word.setTable_item_name(itemListO.getItems().get(itemListO.getSelectionModel().getSelectedIndex()).toString());
				word.setTable_quantity(quantity.getItems().get(quantity.getSelectionModel().getSelectedIndex()).toString());
				word.setTable_price(price.getText());
				float gst_amount_set = gst_amount/2;
				float currentgstRate = Integer.parseInt(currentgst); 
				word.setTable_gst(String.valueOf(currentgstRate/2) + "%");
				word.setTable_gst_amount(String.valueOf(gst_amount_set));
				//word.setTable_cgst(String.valueOf(currentgstRate/2));
				//word.setTable_cgst_amount(String.valueOf(gst_amount_set));
				word.setTable_sgst(String.valueOf(currentgstRate/2) + "%");
				word.setTable_sgst_amount(String.valueOf(gst_amount_set));
				word.setTable_igst("0" + "%");
				word.setTable_igst_amount("0");
				word.setTable_total(item_total.getText());
				word.setTable_cess(String.valueOf(cess)+ "%");
				word.setTable_cess_amount(String.valueOf(cess_amount));
				data.add(word);
				tableView.setItems(data);
				
				item_id.clear();
				price.clear();
				gst.clear();
				item_total.clear();
				itemListO.getSelectionModel().clearSelection();
				quantity.getSelectionModel().clearSelection();
				
				
				totalItems("Total(AP+GST)",invoice_total);
		        totalItems("Price (Q*P)",itemPriceTotal);
		        subColumn_totalItems(4,"Amount",cgst_totalAmount);
		        subColumn_totalItems(5,"Amount",sgst_totalAmount);
		        subColumn_totalItems(6,"Amount",igst_totalAmount);
		        subColumn_totalItems(7,"Amount",cess_totalAmount);
			}
			itemrequired_valid.setVisible(false);
		}else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Add Item");
			alert.setHeaderText("Please Select Party first");
			//alert.setContentText("Added invoice successfully");
			alert.showAndWait();
			item_id.clear();
			price.clear();
			gst.clear();
			item_total.clear();
			itemListO.getSelectionModel().clearSelection();
			quantity.getSelectionModel().clearSelection();
		}
	}
	
	private void totalItems(String columnName, TextField columnField) {
		List<Float> item_priceTotal = new ArrayList<>();
		long count = tableView.getColumns().stream().count();
        for (int i = 0; i < tableView.getItems().size(); i++) {
            //for (int j = 0; j < count; j++) {
            	String entry = "" + getTableColumnByName(tableView,columnName).getCellData(i);
            	System.out.println(entry);
            	item_priceTotal.add(Float.parseFloat(entry));
            	columnField.setText(String.valueOf(sum(item_priceTotal)));
            //}
        }
	}
	
	private void subColumn_totalItems(int a,String columnName, TextField columnField) {
		List<Float> item_priceTotal = new ArrayList<>();
		long count = tableView.getColumns().stream().count();
        for (int i = 0; i < tableView.getItems().size(); i++) {
            //for (int j = 0; j < count; j++) {
            	String entry = "" + getTableSubColumnByName(a,tableView,columnName).getCellData(i);
            	System.out.println(entry);
            	item_priceTotal.add(Float.parseFloat(entry));
            	columnField.setText(String.valueOf(sum(item_priceTotal)));
            //}
        }
	}



	public float sum(List<Float> list) {
	     float sum = 0; 

	     for (float i : list)
	         sum = sum + i;

	     return sum;
	}
	
	private <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
	    for (TableColumn<T, ?> col : tableView.getColumns())
	    		if (col.getText().equals(name)) return col ;
	    return null ;
	}
	
	private <T> TableColumn<T, ?> getTableSubColumnByName(int a,TableView<T> tableView, String name) {
	    for (TableColumn<T, ?> col : tableView.getColumns().get(a).getColumns())
	    	{	System.out.println(col.getText());
	    		if (col.getText().equals(name)) return col ;}
	    return null ;
	}

	@FXML
	protected void handleOnClickDeleteInvoice(ActionEvent event) {
		InvoiceEntry selectedItem = tableView.getSelectionModel().getSelectedItem();
		tableView.getItems().remove(selectedItem);
		tableView.getSelectionModel().clearSelection();
		totalItems("Total(AP+GST)",invoice_total);
        totalItems("Price (Q*P)",itemPriceTotal);
        subColumn_totalItems(4,"Amount",cgst_totalAmount);
        subColumn_totalItems(5,"Amount",sgst_totalAmount);
        subColumn_totalItems(6,"Amount",igst_totalAmount);
        subColumn_totalItems(7,"Amount",cess_totalAmount);
	}

	@FXML
	protected void handleOnClickAddInvoice(ActionEvent event) throws SQLException {
		if(validationCheck()){
			System.out.println("*****handleOnClickAddInvoice**********");
			String transporterMobile = mobilenumTransporter.getText();
			String VehicalNo = vehicalTransporter.getText();
			String transporterName = transporter.getItems().get(transporter.getSelectionModel().getSelectedIndex()).toString();
			String JsonItem = ConvertItemIntoJson();
			String party_name_db = party_name.getItems().get(party_name.getSelectionModel().getSelectedIndex()).toString();
			String party_id_db = selectedParty_id;
			LocalDate date_invoice = invoice_date.getValue();
			LocalDate dispatch_invoice_date = dispatch_date.getValue();
			String address = party_address.getText().replace(",", "~");
			//String address1 = party_address1.getText();
			//int invoice_number = oldInvoice + 1;
			String invoice_number = invoicenumber.getText();
			Connection c2;
			c2 = DBConnectFlogger.connect();
			String SQL = "INSERT INTO invoice (item,party_name,invoice_date,invoice_number,dispatch_date,address,party_id,invoice_total,transporter_name,transporter_mobile,transporter_vehicalno) VALUES( \"" + JsonItem + "\",\"" + party_name_db + "\",\""+ date_invoice + "\",\""+ invoice_number +"\",\"" + dispatch_invoice_date +"\",\""+ address+"\",\"" + party_id_db + "\"," + invoice_total.getText() + ",\""+ transporterName +"\",\"" + transporterMobile + "\",\""+ VehicalNo + "\")";
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
		}else{
			ShowAlert.callAlert("Add Invoice", "Please enter Correct details.");
		}
	}

	private boolean validationCheck() {
		System.out.println("invoice_date-->"+invoice_date.getValue() +"dispatch_date--->"+ dispatch_date.getValue()
							+"Party name"+ party_name.getSelectionModel().getSelectedIndex()
							+"Item count" + tableView.getItems().size()
							+ " transporter "+  transporter.getSelectionModel().getSelectedIndex() 
							+ "Transporter mobile " + isValidTMobile.get());
		if(invoice_date.getValue()!=null && dispatch_date.getValue()!=null && party_name.getSelectionModel().getSelectedIndex() !=-1 
			&& 	tableView.getItems().size()>0 && transporter.getSelectionModel().getSelectedIndex() !=-1 && isValidTMobile.get()) {
			return true;
		}else{
			if(invoice_date.getValue()!=null) invoicedate_valid.setVisible(false); 
			else invoicedate_valid.setVisible(true);
			if(dispatch_date.getValue()!=null) dispatchdate_valid.setVisible(false); 
			else dispatchdate_valid.setVisible(true);
			if(party_name.getSelectionModel().getSelectedIndex() !=-1) party_valid.setVisible(false); 
			else party_valid.setVisible(true);
			if(tableView.getItems().size()>0) itemrequired_valid.setVisible(false); 
			else itemrequired_valid.setVisible(true);
			if( transporter.getSelectionModel().getSelectedIndex() !=-1) tramspoter_valid.setVisible(false); 
			else tramspoter_valid.setVisible(true);
			if( isValidTMobile.get()) mobile_valid.setVisible(false); 
			else mobile_valid.setVisible(true);
			if( isValidTVehical.get()) vehical_valid.setVisible(false); 
			else vehical_valid.setVisible(true);
			return false;
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
			ItemJson.put("item_gst", tableView.getItems().get(i).getTable_gst().replace("%",""));
			ItemJson.put("item_gst_amount", tableView.getItems().get(i).getTable_gst_amount());
			//ItemJson.put("item_cgst", tableView.getItems().get(i).getTable_cgst().replace("%",""));
			//ItemJson.put("item_cgst_amount", tableView.getItems().get(i).getTable_cgst_amount());
			ItemJson.put("item_igst", tableView.getItems().get(i).getTable_igst().replace("%",""));
			ItemJson.put("item_igst_amount", tableView.getItems().get(i).getTable_igst_amount());
			ItemJson.put("item_sgst", tableView.getItems().get(i).getTable_sgst().replace("%",""));
			ItemJson.put("item_sgst_amount", tableView.getItems().get(i).getTable_sgst_amount());
			ItemJson.put("item_cess", tableView.getItems().get(i).getTable_cess().replace("%",""));
			ItemJson.put("item_cess_amount", tableView.getItems().get(i).getTable_cess_amount());
			ItemJson.put("item_total", tableView.getItems().get(i).getTable_total());
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
	
	@FXML
	protected void handlechangePrice(ActionEvent event) throws SQLException {
		TextInputDialog dialog = new TextInputDialog("10");
		dialog.setTitle("Change Price");
		dialog.setHeaderText("Change Item Price");
		dialog.setContentText("Enter Price:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    System.out.println("Entered Price: " + result.get());
		    singleItemPrice = result.get();
		    quantity.getSelectionModel().selectFirst();
		    //float rate = Float.parseFloat(currentgst)/100;
		    //float rate_1 = 1 + rate;
		   // float pr = Float.parseFloat(result.get()) / rate_1;
		    /*float pricechanged = (Float.parseFloat(result.get()))/((1+(Float.parseFloat(currentgst)/100))*Float.parseFloat(result.get()));
		    System.out.println("-------->"+pricechanged+"--------->"+currentgst);
		    price.setText(String.valueOf(pricechanged));*/
		    int IntnewValue = 1;
		    float changedPricePer = (Float.parseFloat(result.get()) * IntnewValue)/(1+(Float.parseFloat(currentgst)/100)*IntnewValue);
			//System.out.println("--->"+item_picetotal + "--->" + Integer.parseInt(currentprice) + "--->" + Float.parseFloat(currentgst)/100);
			//item_total.setText(String.valueOf(quantity_item_picetotal));
			price.setText(String.valueOf(changedPricePer));
			System.out.println("changedPricePer--->"+changedPricePer);
		    item_total.setText(singleItemPrice);
		    gst_amount = ((Float.parseFloat(result.get()) * IntnewValue)-(changedPricePer));
		    
		}
		
		// The Java 8 way to get the response value (with lambda expression).
		//result.ifPresent(name -> System.out.println("Entered price: " + name));
	}
	
	@FXML
	protected void OnfreeItemClick(ActionEvent event) throws SQLException {
		if(party_name.getSelectionModel().getSelectedIndex() != -1){
			InvoiceEntry word = new InvoiceEntry();
			word.setTable_item_id(item_id.getText());
			word.setTable_item_name(itemListO.getItems().get(itemListO.getSelectionModel().getSelectedIndex()).toString());
			word.setTable_quantity(quantity.getItems().get(quantity.getSelectionModel().getSelectedIndex()).toString());
			word.setTable_price("0");
			word.setTable_gst("0" + "%");
			word.setTable_gst_amount("0");
			word.setTable_sgst("0" + "%");
			word.setTable_sgst_amount("0");
			word.setTable_igst("0" + "%");
			word.setTable_igst_amount("0");
			word.setTable_cess("0" + "%");
			word.setTable_cess_amount("0");
			word.setTable_total("0");
			data.add(word);
			tableView.setItems(data);
			item_id.clear();
			price.clear();
			gst.clear();
			item_total.clear();
			itemListO.getSelectionModel().clearSelection();
			quantity.getSelectionModel().clearSelection();
		}else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Add Item");
			alert.setHeaderText("Please Select Party first");
			//alert.setContentText("Added invoice successfully");
			alert.showAndWait();
		}
	}
	void populateTranspoter(){
		Connection c4;
		int rowcount = 0;
		try {
			c4 = DBConnectFlogger.connect();
			String SQL = "SELECT * from transporter";
			// String SQL = "select * from user where username = \"" +
			// userName.getText() + "\" and password = \"" +
			// passwordField.getText() + "\";" ;
			System.out.println("---> query" + SQL);
			// ResultSet
			ResultSet rs1 = c4.createStatement().executeQuery(SQL);

			if (rs1.last()) {
				rowcount = rs1.getRow();
				rs1.beforeFirst(); // not rs.first() because the rs.next() below
									// will move on, missing the first element
			}
			System.out.println("RS size for party " + rowcount);
			ObservableList<String> row1 = FXCollections.observableArrayList("CSE");
			while (rs1.next()) {
				String current = rs1.getString("transport_name");
				listOfTransport.add(current);
			}
			ObservableList<String> observableListOfParty = FXCollections.observableArrayList(listOfTransport);
			// partyList.getItems().clear();
			transporter.getItems().addAll(observableListOfParty);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
