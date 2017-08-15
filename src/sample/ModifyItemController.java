package sample;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyItemController {
	private Parent parent;
	private Scene scene;
	private Stage stage;
	private HomeController homeController;
	@FXML
	private Text welcomeText;
	private ViewItemController viewItemController;
	@FXML
	private TextField item_name, price, cess, item_code, unit, rate;
	String item_name_slt, item_id_slt, price_slt, status_slt, gms_slt, item_code_slt, unit_slt, rate_slt;
	BooleanBinding isValidName = new BooleanBinding() {

		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return false;
		}
	};
	BooleanBinding isValidRate = new BooleanBinding() {

		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return false;
		}
	};
	BooleanBinding isValidCess = new BooleanBinding() {

		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return false;
		}
	};
	BooleanBinding isVaidItemCode = new BooleanBinding() {

		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return false;
		}
	};
	BooleanBinding isVaidPrice = new BooleanBinding() {

		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return false;
		}
	};

	public ModifyItemController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyItem.fxml"));
		fxmlLoader.setController(this);
		try {
			parent = (Parent) fxmlLoader.load();
			scene = new Scene(parent, 600, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
		characterLimit(item_name, 20);
		characterLimit(rate, 3);
		characterLimit(cess, 3);
		characterLimit(price, 6);
		characterLimit(item_code, 7);
		/*
		 * addItemSubmit.disableProperty().bind(
		 * item_name.textProperty().isEmpty()
		 * .or(item_id.textProperty().isEmpty())
		 * .or(price.textProperty().isEmpty())
		 * .or(status.textProperty().isEmpty())
		 * .or(gms.textProperty().isEmpty())
		 * .or(item_code.textProperty().isEmpty())
		 * .or(unit.textProperty().isEmpty())
		 * .or(rate.textProperty().isEmpty()));
		 */
		// [^\\s*$]
		/*
		 * addItemSubmit.disableProperty().bind(item_name.textProperty().isEmpty
		 * ());
		 * 
		 * item_name.textProperty().addListener((observable, oldValue, newValue)
		 * -> { if(newValue != null){
		 * addItemSubmit.disableProperty().bind(ValidationCheck(newValue,
		 * "^[a-zA-Z\\s]{2,29}$")); } });
		 */
		item_name.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidName = ValidationCheck(newValue, "^.{3,20}$");
				System.out.println("Valid Name------>" + isValidName.get());
			}
		});
		rate.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidRate = ValidationCheck(newValue, "^\\d{1,}$");
				System.out.println("Valid Gst------>" + isValidRate.get());
			}
		});
		cess.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidCess = ValidationCheck(newValue, "^\\d{1,}$");
				System.out.println("Valid Gst------>" + isValidCess.get());
			}
		});
		item_code.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isVaidItemCode = ValidationCheck(newValue, "^.+$");
				System.out.println("Valid ItemCode------>" + isVaidItemCode.get());
			}
		});
		price.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isVaidPrice = ValidationCheck(newValue, "^\\d{1,}$");
				System.out.println("Valid isVaidPrice------>" + isVaidPrice.get());
			}
		});
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

	public void redirectmodifyitem(Stage stage, String name, String selectedItem) {
		fetchRowDetals(selectedItem);
		this.stage = stage;
		stage.setTitle("AddParty");
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

	private boolean validateFields() {
		System.out.println("Item Name Valid" + isValidName.get() + "Valid Rate " + isValidRate.get() + "Valid cess "
				+ isValidCess.get() + "valid Code " + isVaidItemCode.get() + "Valid Price " + isVaidPrice.get());
		if (isValidName.get() && isValidCess.get() && isValidRate.get() && isVaidItemCode.get() && isVaidPrice.get()) {
			return true;
		}
		return false;
	}

	@FXML
	protected void handleupdateItem(ActionEvent event) throws SQLException {
		if (validateFields()) {
			System.out.println("---->valid");
			Connection c;
			try {
			    		Boolean status = true;
				    	//LocalDate create_date = LocalDate.now();
				    	String item_id = item_id_slt;
				    	Connection c2;
				        int rowcount2 = 0;
				        int rs2 = 0;
				        try{ c2 = DBConnectFlogger.connect();  
					         /*String SQL2 = "INSERT INTO item (item_name,item_id,price,item_code,unit,status,cess,create_date,rate) VALUES(\""
					          + item_name.getText() 
					          + "\",\"" + item_id
					          + "\",\"" + price.getText() 
					          + "\",\"" + item_code.getText() 
					          + "\",\"" + unit.getText() 
					          + "\",\"" + status 
					          + "\",\"" + cess.getText()
					          + "\",\"" + rate.getText()
					          + "\")";*/
					         String SQL3 = "UPDATE item SET item_name=\"" + item_name.getText() 
					         						+ "\", price=\"" + price.getText()
					         						+ "\", item_code=\"" + item_code.getText()
					         						+ "\", unit=\"" + unit.getText()
					         						+ "\", cess=\"" +  cess.getText()
					         						+ "\", rate=\"" + rate.getText() 
					         						+ "\" WHERE item_id = \"" + item_id + "\"";
					         System.out.println("Has added party SQL" + SQL3);
					         rs2 = c2.createStatement().executeUpdate(SQL3);
					         System.out.println("Has added party" + rs2);
				        }catch (Exception e) {
							 System.out.println(e);
						}
					        if(rs2==1){
					       	 	Alert alert = ShowAlert.callAlert2("Add Item","Item added Successfully");
						       	if (alert.showAndWait().get() == ButtonType.OK){
						       		viewItemController = new ViewItemController();
									viewItemController.redirectviewItem(stage, Constants.getUsername());
					            }
					        }else{
					       	 ShowAlert.callAlert("Error","Please check given Details again.");
					        }
				
				
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} else {
			ShowAlert.callAlert("Error","Please check given Details again.");
		}
	}

	void fetchRowDetals(String selectedRow) {
		String[] splits = selectedRow.replaceAll("^\\s*\\[|\\]\\s*$", "").split("\\s*,\\s*");
		List<String> wordList = Arrays.asList(splits);
		item_name_slt = wordList.get(0);
		rate_slt = wordList.get(4);
		item_id_slt = wordList.get(1);
		price_slt = wordList.get(2);
		status_slt = wordList.get(4);
		gms_slt = wordList.get(8);
		item_code_slt = wordList.get(3);
		unit_slt = wordList.get(7);
		item_name.setText(item_name_slt);
		rate.setText(rate_slt);
		price.setText(price_slt);
		cess.setText(gms_slt);
		item_code.setText(item_code_slt);
		unit.setText(unit_slt);
	}

}