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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mtechproject.samples.DBConnectFlogger;
import util.Constants;
import util.ShowAlert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CORBA.SetOverrideType;

public class ModifyInvoiceController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    String party_state,gstNo;
    private HomeController homeController;
    String selectedItem;
    String currentgst = null, cess;
    Boolean isSameState = false, isFirstInvoice = false ;
    private ViewInvoiceController viewInvoiceController;
    String party_id_;
    @FXML 
	private Label invoicedate_valid,dispatchdate_valid,party_valid,itemrequired_valid,tramspoter_valid,vehical_valid,mobile_valid;
    @FXML
	private ComboBox itemListO, quantity,transporter;
    List<String> listOfTransport = new ArrayList<String>();
    @FXML
	private TextArea party_address;
    float gst_amount,gstrate,cess_amount;
   // @FXML
    //private Text welcomeText;
    @FXML
    private javafx.scene.control.TextField mobilenumber,vehicalnumber,party_state_slt,item_name,item_id,price,invoicenumber,party_name,gst,item_total,cgst_totalAmount,sgst_totalAmount,igst_totalAmount,cess_totalAmount,itemPriceTotal,invoice_total;
    @FXML
	private DatePicker invoice_date,dispatch_date;
	@FXML
	private TableView<InvoiceEntry> tableView;
	List<String> listOfItem = new ArrayList<String>();
	List<String> listOfItemQuantity = new ArrayList<String>();
	String singleItemPrice;
	private ObservableList<InvoiceEntry> data = FXCollections.observableArrayList();
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
	
    public ModifyInvoiceController() throws SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyInvoice.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
        party_state_slt.setEditable(false);
        invoicenumber.setEditable(false);
        cgst_totalAmount.setEditable(false);
		sgst_totalAmount.setEditable(false);
		igst_totalAmount.setEditable(false);
		cess_totalAmount.setEditable(false);
		itemPriceTotal.setEditable(false);
		item_total.setEditable(false);
		invoice_total.setEditable(false);
		price.setEditable(false);
		item_id.setEditable(false);
		
		
		itemrequired_valid.setVisible(false);
		party_valid.setVisible(false);
		invoicedate_valid.setVisible(false);
		dispatchdate_valid.setVisible(false);
		tramspoter_valid.setVisible(false);
		vehical_valid.setVisible(false);
		mobile_valid.setVisible(false);
		
		
		
		//populateTranspoter();
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
			word.setTable_gst((String) perItem.get("item_gst")+"%");
			word.setTable_gst_amount((String) perItem.get("item_gst_amount"));
			word.setTable_total((String) perItem.get("item_total"));
			word.setTable_cgst((String) perItem.get("item_gst")+"%");
			word.setTable_cgst_amount((String) perItem.get("item_gst_amount"));
			word.setTable_sgst((String) perItem.get("item_sgst")+"%");
			word.setTable_sgst_amount((String) perItem.get("item_sgst_amount"));
			word.setTable_igst((String) perItem.get("item_igst")+"%");
			word.setTable_igst_amount((String) perItem.get("item_igst_amount"));
			word.setTable_cess((String) perItem.get("item_cess")+"%");
			word.setTable_cess_amount((String) perItem.get("item_cess_amount"));
			data.add(word);
		}
		tableView.setItems(data);
		System.out.println(arrayListItem.get(0));
		String newList = selectedInvoice.replace(ItemList,"");
		System.out.println(newList);
		String []splits = newList.replaceAll("^\\s*\\[|\\]\\s*$", "").split("\\s*,\\s*");
		List<String> wordList = Arrays.asList(splits); 
		
		ObservableList<String> party_name_selected = FXCollections.observableArrayList();
		//party_name_selected.add();
		transporter.setValue(wordList.get(8));
		String invoice_date_selected= wordList.get(2);
		String invoice_number_selected= wordList.get(3);
		String dispatch_date_selected= wordList.get(4);
		String party_address_selected= wordList.get(5).replace("~",",");
		String party_address_selected1= wordList.get(6);
		//party_name.setItems(party_name_selected);
		//party_name.getSelectionModel().selectFirst();
		party_name.setText(wordList.get(6));
		invoice_total.setText(wordList.get(7));
		invoicenumber.setText(Constants.getInvoicePrefix()+invoice_number_selected);
		dispatch_date.setValue(localDateFormatter(dispatch_date_selected));
	    invoice_date.setValue(localDateFormatter(invoice_date_selected));
	    party_address.setText(party_address_selected);
	    totalItems("Price (Q*P)",itemPriceTotal);
        subColumn_totalItems(4,"Amount",cgst_totalAmount);
        subColumn_totalItems(5,"Amount",sgst_totalAmount);
        subColumn_totalItems(6,"Amount",igst_totalAmount);
        subColumn_totalItems(7,"Amount",cess_totalAmount);
	    //party_address1.setText(party_address_selected1);
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
		
		try {
			Connection c1;
			c1 = DBConnectFlogger.connect();
			String SQL = "SELECT * from party WHERE party_name = \"" + party_name.getText() + "\"";
			System.out.println("---> query" + SQL);
			// ResultSet
			ResultSet rs = c1.createStatement().executeQuery(SQL);
			String current = null,current1=null,current2 = null;
			String party_state_feild = null,gstNo = null;
			while (rs.next()) {
				if (rs.first()) {
					current = rs.getString("address");
					current1 = rs.getString("address1");
					party_id_ = rs.getString("party_id");
					party_state_feild = rs.getString("state");
					gstNo = rs.getString("gstin");
				}
			}
			party_state_slt.setText(party_state_feild);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		try {
			Connection c1;
			c1 = DBConnectFlogger.connect();
			String SQL = "SELECT * from party WHERE party_name = \"" + party_name.getText() + "\"";
			System.out.println("---> query" + SQL);
			// ResultSet
			ResultSet rs = c1.createStatement().executeQuery(SQL);
			String current = null,current1=null,current2 = null;
			while (rs.next()) {
				if (rs.first()) {
					party_state = rs.getString("state");
					gstNo = rs.getString("gstin");
				}
			}
			System.out.println("---->" + current+ "--->" + gstNo);
			int partyStateCode = Integer.parseInt(gstNo.substring(0,2));
			if(Constants.getState() == partyStateCode){
				isSameState = true;
				System.out.println("isSameState-------------->" + isSameState);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		

	
/*		quantity.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue != null){
				int IntnewValue = Integer.parseInt(newValue);
				int IntsingleItemPrice = Integer.parseInt(singleItemPrice);
				cess_amount = ((IntsingleItemPrice * IntnewValue) * (Float.parseFloat(cess)/100));
				price.setText(String.valueOf(IntsingleItemPrice * IntnewValue));
				float quantity_item_picetotal =((IntsingleItemPrice * IntnewValue) * (Float.parseFloat(currentgst)/100)) + (IntsingleItemPrice * IntnewValue) + cess_amount;
				gst_amount = ((IntsingleItemPrice * IntnewValue) * (Float.parseFloat(currentgst)/100));
				
				//System.out.println("--->"+item_picetotal + "--->" + Integer.parseInt(currentprice) + "--->" + Float.parseFloat(currentgst)/100);
				item_total.setText(String.valueOf(quantity_item_picetotal));
				}}
		});*/
		
		quantity.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue != null){
				int IntnewValue = Integer.parseInt(newValue);
				float IntsingleItemPrice = Float.parseFloat(singleItemPrice);
				float changedPricePer = (IntsingleItemPrice * IntnewValue)/(1+(Float.parseFloat(currentgst)/100)*1);
				gst_amount = ((IntsingleItemPrice * IntnewValue)-(changedPricePer));
				System.out.println("price-->changedPricePer--->"+changedPricePer + "--->" + IntsingleItemPrice + "--->" + Float.parseFloat(currentgst)/100);
				price.setText(String.valueOf(changedPricePer));
				item_total.setText(String.valueOf(IntsingleItemPrice * IntnewValue));
				//cess_amount = ((IntsingleItemPrice * IntnewValue) * (Float.parseFloat(cess)/100));
				//price.setText(String.valueOf(IntsingleItemPrice * IntnewValue));
				//float quantity_item_picetotal =((IntsingleItemPrice * IntnewValue) * (Float.parseFloat(currentgst)/100)) + (IntsingleItemPrice * IntnewValue) + cess_amount;
				//gst_amount = ((IntsingleItemPrice * IntnewValue) * (Float.parseFloat(currentgst)/100));
				
				//System.out.println("--->"+item_picetotal + "--->" + Integer.parseInt(currentprice) + "--->" + Float.parseFloat(currentgst)/100);
				//item_total.setText(String.valueOf(quantity_item_picetotal));
				}}
		});
		
		 invoice_date.valueProperty().addListener((ov, oldValue, newValue) -> {
			 	System.out.println("newval" + newValue);
			 	invoicedate_valid.setVisible(false);
	        });
		 dispatch_date.valueProperty().addListener((ov, oldValue, newValue) -> {
			 	dispatchdate_valid.setVisible(false);
	        });
		 
		 vehicalnumber.textProperty().addListener((ov, oldValue, newValue) -> {
			 if (newValue != null) {
					isValidTVehical = ValidationCheck(newValue, "[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}");
					System.out.println("Valid Name------>"+isValidTVehical.get());
					if(isValidTVehical.get() == false){
						vehical_valid.setVisible(true);
					}else{
						vehical_valid.setVisible(false);
					}
				}
	        });
		 
		
		 
		 
		 transporter.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					tramspoter_valid.setVisible(false);
				}
			});
			
			mobilenumber.textProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue != null) {
					isValidTMobile = ValidationCheck(newValue, "[0-9]{10}");
					System.out.println("Valid Mobile------>"+isValidTMobile.get());
					if(isValidTMobile.get() == false){
						mobile_valid.setVisible(true);
					}else{
						mobile_valid.setVisible(false);
					}
				}
			});
			
			/*vehicalTransporter.textProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue != null) {
					isValidTVehical = ValidationCheck(newValue, "[A-Z]{2}[ -][0-9]{2}[ -][A-Z]{2}[ -][0-9]{4}");
					System.out.println("Valid Name------>"+isValidTVehical.get());
					if(isValidTVehical.get() == false){
						vehical_valid.setVisible(true);
					}else{
						vehical_valid.setVisible(false);
					}
				}
			});*/

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
						ObservableList<String> observableListOfQuantity = FXCollections.observableArrayList(listOfItemQuantity);
											
						singleItemPrice = currentprice;
						
						int IntnewValue = 1;
						float changedPricePer = (Float.parseFloat(currentprice) * IntnewValue)/(1+(Float.parseFloat(currentgst)/100)*IntnewValue);
						
						gst_amount = ((Float.parseFloat(currentprice) * IntnewValue)-(changedPricePer));
						
						System.out.println("price-->changedPricePer--->"+changedPricePer + "--->" + Integer.parseInt(currentprice) + "--->" + Float.parseFloat(currentgst)/100);
						
						
						price.setText(String.valueOf(changedPricePer));
						quantity.getItems().addAll(observableListOfQuantity);
						quantity.getSelectionModel().selectFirst();
						item_id.setText(current);
						item_total.setText(String.valueOf(currentprice));
	 					gst.setText(currentgst + "%");
	 					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}}
			});
			/*itemListO.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
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
			});*/
		
		characterLimit(mobilenumber, 12);
		characterLimit(vehicalnumber, 13);
		
		
		
		populateTranspoter();
    }
 
	LocalDate localDateFormatter(String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate localDate = LocalDate.parse(date, formatter);
	    return localDate;
	}
	
	/*@FXML
	protected void handleOnClickAddItem(ActionEvent event) {
		System.out.println("----------->"+ isSameState);
		if(!party_name.getText().isEmpty()){
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
				word.setTable_cgst(String.valueOf(currentgstRate/2));
				word.setTable_cgst_amount(String.valueOf(gst_amount_set));
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
		
	}*/
	
	@FXML
	protected void handleOnClickAddItem(ActionEvent event) {
		System.out.println("----------->"+ isSameState);
		if( !party_name.getText().isEmpty()){
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
		    price.setText(result.get());
		}
		
		// The Java 8 way to get the response value (with lambda expression).
		//result.ifPresent(name -> System.out.println("Entered price: " + name));
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
	protected void handleOnClickDeleteItem(ActionEvent event) {
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
	protected void handleOnClickUpdateInvoice(ActionEvent event) {
		if(validationCheck()){
			System.out.println("*****handleOnClickAddInvoice**********");
			String JsonItem = ConvertItemIntoJson();
			String party_name_db = party_name.getText();
			String party_id_db = party_id_;
			LocalDate date_invoice = invoice_date.getValue();
			LocalDate dispatch_invoice_date = dispatch_date.getValue();
			String address = party_address.getText().replace(",", "~");
			//String address1 = party_address1.getText();
			//int invoice_number = oldInvoice + 1;
			try{
			Connection c;
			c = DBConnectFlogger.connect();
			String SQL = "DELETE FROM invoice WHERE invoice_number = \"" + invoicenumber.getText().replace(Constants.getInvoicePrefix(),"") + "\"" ;
			System.out.println("---> query" + SQL);
			int rs = c.createStatement().executeUpdate(SQL);
			System.out.println("Has  party" + rs);
			if (rs == 1) {
				Connection c2;
				c2 = DBConnectFlogger.connect();
				String transporterMobile = mobilenumber.getText();
				String VehicalNo = vehicalnumber.getText();
				String transporterName = transporter.getItems().get(transporter.getSelectionModel().getSelectedIndex()).toString();
				String SQL1 = "INSERT INTO invoice (item,party_name,invoice_date,invoice_number,dispatch_date,address,party_id,invoice_total,transporter_name,transporter_mobile,transporter_vehicalno) VALUES( \"" + JsonItem + "\",\"" + party_name_db + "\",\""+ date_invoice + "\","+ "\""+ invoicenumber.getText().replace(Constants.getInvoicePrefix(),"") +"\"" + ",\"" + dispatch_invoice_date +"\",\""+ address+"\",\"" + party_id_db + "\"," + invoice_total.getText() + ",\""+ transporterName +"\",\"" + transporterMobile + "\",\""+ VehicalNo + "\")";
				System.out.println("---> query" + SQL1);
				int rs1 = c2.createStatement().executeUpdate(SQL1);
				System.out.println("Has added party" + rs1);
				if (rs1 == 1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Add Invoice");
					alert.setHeaderText("Modified Invoice");
					alert.setContentText("Modified invoice successfully");
					
					//alert.showAndWait();
					//Optional<ButtonType> result = alert.showAndWait();
					if (alert.showAndWait().get() == ButtonType.OK){
						viewInvoiceController = new ViewInvoiceController();
						viewInvoiceController.redirectviewInvoice(stage,Constants.getUsername());
		            }
				}else{
					ShowAlert.callAlert("Modified Invoice", "Please enter Correct details.");
				}
			}
			else{
				ShowAlert.callAlert("Add Invoice", "Please enter Correct details.");
			}
			}catch (Exception e) {
				
			}
		}else{
			ShowAlert.callAlert("Add Invoice", "Please enter Correct details.");
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
			ItemJson.put("item_cgst", tableView.getItems().get(i).getTable_cgst().replace("%",""));
			ItemJson.put("item_cgst_amount", tableView.getItems().get(i).getTable_cgst_amount());
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

	private boolean validationCheck() {
		System.out.println("invoice_date-->"+invoice_date.getValue() +"dispatch_date--->"+ dispatch_date.getValue()
							+"Party name"+ !party_name.getText().isEmpty()
							+"Item count" + tableView.getItems().size()
							+ " transporter "+  transporter.getSelectionModel().getSelectedIndex() 
							+ "Transporter mobile " + isValidTMobile.get() 
							+ "Transporter Vehical " + isValidTVehical.get());
		if(invoice_date.getValue()!=null && dispatch_date.getValue()!=null && !party_name.getText().isEmpty() 
			&& 	tableView.getItems().size()>0 && 	tableView.getItems().size()>0 && transporter.getSelectionModel().getSelectedIndex() !=-1 && isValidTMobile.get() && isValidTVehical.get()) {
			return true;
		}else{
			if(invoice_date.getValue()!=null) invoicedate_valid.setVisible(false); 
			else invoicedate_valid.setVisible(true);
			if(dispatch_date.getValue()!=null) dispatchdate_valid.setVisible(false); 
			else dispatchdate_valid.setVisible(true);
			if(!party_name.getText().isEmpty()) party_valid.setVisible(false); 
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

	public float sum(List<Float> list) {
	     float sum = 0; 

	     for (float i : list)
	         sum = sum + i;

	     return sum;
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
				System.out.println("transporter---------->"+current);
			}
			ObservableList<String> observableListOfParty = FXCollections.observableArrayList(listOfTransport);
			// partyList.getItems().clear();
			transporter.getItems().addAll(observableListOfParty);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
    
}
