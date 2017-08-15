package sample;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mtechproject.samples.DBConnectFlogger;
import util.Constants;
import util.GenerateRandom;
import util.ShowAlert;

public class ModifyTransporterController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private HomeController homeController;
    @FXML
    private Text welcomeText;
    @FXML
    private TextField transporter_name,gst_no,email1,phone1,phone2,email2,address1,address2;
    @FXML
    private Button addItemSubmit;
    String transporter_name_slt, phone1_slt, phone2_slt, status_slt, gms_slt, email1_slt, email2_slt, gst_no_slt,address1_slt,address2_slt,transporter_id_slt;
    BooleanBinding isValidName = new BooleanBinding() {
		
		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return false;
		}
	};
	boolean isValidEmail2 = false;
	boolean isValidEmail1 = false;
	BooleanBinding isVaidPhone1 = new BooleanBinding() {
		
		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return false;
		}
	};
	BooleanBinding isVaidPhone2 = new BooleanBinding() {
		
		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return false;
		}
	};
	BooleanBinding isVaidGSTNo = new BooleanBinding() {
		
		@Override
		protected boolean computeValue() {
			// TODO Auto-generated method stub
			return false;
		}
	};
	

    public ModifyTransporterController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyTransporter.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
        
        characterLimit(transporter_name, 20);
        characterLimit(email2, 25);
        characterLimit(email1, 25);
        characterLimit(gst_no,15);
        characterLimit(phone1,12);
        characterLimit(phone2,12);
        characterLimit(address1,75);
        characterLimit(address2,75);
        /*addItemSubmit.disableProperty().bind(
        		item_name.textProperty().isEmpty()
        		.or(item_id.textProperty().isEmpty())
        		.or(price.textProperty().isEmpty())
        		.or(status.textProperty().isEmpty())
        		.or(gms.textProperty().isEmpty())
        		.or(item_code.textProperty().isEmpty())
        		.or(unit.textProperty().isEmpty())        		
        	    .or(rate.textProperty().isEmpty()));*/
        	    //[^\\s*$]
        /*addItemSubmit.disableProperty().bind(item_name.textProperty().isEmpty());
        
        item_name.textProperty().addListener((observable, oldValue, newValue) -> {
        	if(newValue != null){
        		addItemSubmit.disableProperty().bind(ValidationCheck(newValue,"^[a-zA-Z\\s]{2,29}$"));
        	}
        });*/
        transporter_name.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidName = ValidationCheck(newValue, "^.{3,20}$");
				System.out.println("Valid Name------>"+isValidName.get());
			}
		});
        email2.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidEmail2 = EmailValidator.getInstance().isValid(newValue);
				System.out.println("Valid Gst------>"+isValidEmail2);
			}
		});
        email1.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidEmail1 = EmailValidator.getInstance().isValid(newValue);
				System.out.println("Valid Gst------>"+isValidEmail1);
			}
		});
        phone1.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isVaidPhone1 = ValidationCheck(newValue, "[0-9]{10}");
				System.out.println("Valid ItemCode------>"+isVaidPhone1.get());
			}
		});
        phone2.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isVaidPhone2 = ValidationCheck(newValue, "[0-9]{10}");
				System.out.println("Valid ItemCode------>"+isVaidPhone2.get());
			}
		});
        gst_no.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isVaidGSTNo = ValidationCheck(newValue, "[0123]{1}[0-9]{1}[A-Z]{5}[0-9]{4}[A-Z]{1}[0-9]{1}[A-Z]{1}[0-9]{1}");
				System.out.println("Valid Gst------>"+isVaidGSTNo.get());
			}
		});
        
    }


    private void characterLimit(TextField textFeildname,int maxlimit) {
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


	public void redirectmodifyTransporter(Stage stage, String name,String selectedItem) {
		fetchRowDetals(selectedItem);
    	this.stage = stage;
        stage.setTitle("AddParty");
        stage.setScene(scene);
        welcomeText.setText("Hey " + name + "! Enter your party details here");
        stage.hide();
        stage.show();
        //stage.setFullScreen(true);
        
    }
	
	void fetchRowDetals(String selectedRow) {
		String[] splits = selectedRow.replaceAll("^\\s*\\[|\\]\\s*$", "").split("\\s*,\\s*");
		List<String> wordList = Arrays.asList(splits);
		transporter_name_slt = wordList.get(0);
		transporter_id_slt =  wordList.get(1);
		gst_no_slt = wordList.get(3);
		phone1_slt = wordList.get(4);
		phone2_slt = wordList.get(5);
		status_slt = wordList.get(4);
		gms_slt = wordList.get(8);
		email1_slt = wordList.get(6);
		email2_slt = wordList.get(7);
		address1_slt = wordList.get(8);
		address2_slt = wordList.get(9);
		transporter_name.setText(transporter_name_slt);
		gst_no.setText(gst_no_slt);
		phone1.setText(phone1_slt);
		phone2.setText(phone2_slt);
		email1.setText(email1_slt);
		email2.setText(email2_slt);
		address1.setText(address1_slt);
		address2.setText(address2_slt);
	}
    
    @FXML
    protected void handleUpdateTransporter(ActionEvent event) {
    	if(validateFields()){
    		Connection c;
			try {
				c = DBConnectFlogger.connect();
				String SQL = "DELETE FROM transporter WHERE transport_id = \"" + transporter_id_slt + "\"" ;
				System.out.println("---> query" + SQL);
				//int rs = c.createStatement().executeUpdate(SQL);
				int rs = c.createStatement().executeUpdate(SQL);
				System.out.println("Has  party" + rs);
				if (rs == 1) {
    		
    		
    		
    		
    		
    		Boolean status = true;
	    	LocalDate create_date = LocalDate.now();
	    	String transporter_id = transporter_name.getText().substring(0, 3).toUpperCase() + GenerateRandom.generateRandomChars("0123456789",2);
	    	Connection c1;
	        int rowcount = 0;
	        int rs1 = 0;
	        try{  
		         c1 = DBConnectFlogger.connect();  
		         String SQL1 = "INSERT INTO transporter (transport_name,transport_id,gstin,phone1,phone2,status,email1,create_date,email2,address1,address2) VALUES(\""
		          + transporter_name.getText() 
		          + "\",\"" + transporter_id
		          + "\",\"" + gst_no.getText() 
		          + "\",\"" + phone1.getText() 
		          + "\",\"" + phone2.getText() 
		          + "\",\"" + status 
		          + "\",\"" + email1.getText()
		          + "\",\"" + create_date
		          + "\",\"" + email2.getText()
		          + "\",\"" + address1.getText() 
		          + "\",\"" + address2.getText()
		          + "\")";
		         System.out.println("Has added party SQL" + SQL1);
		         rs1 = c1.createStatement().executeUpdate(SQL1);
		         System.out.println("Has added party" + rs1);
	        }catch (Exception e) {
				 System.out.println(e);
			}
		        if(rs1==1){
		       	 	Alert alert = ShowAlert.callAlert2("Add Transporter","Transporter added Successfully");
			       	if (alert.showAndWait().get() == ButtonType.OK){
						homeController = new HomeController();
			            homeController.redirectHome(stage, Constants.getUsername());
		            }
		        }else{
		       	 ShowAlert.callAlert("Error","Please check given Details again.");
		        }
		        
    	}}catch (Exception e) {
			// TODO: handle exception
		}
    	}else{
    		ShowAlert.callAlert("Error","Please check given Details again.");
    	}
    }
    
    private boolean validateFields() {
    	System.out.println("Name Valid " + isValidName.get()
    			+ "Valid Email2" + isValidEmail2
    			+ "Valid Email1" + isValidEmail1 
    			+"valid Phone1" + isVaidPhone1.get()
    			+"valid Phone2" + isVaidPhone2.get()
    			+"Valid GST" + isVaidGSTNo.get());
    	if(isValidName.get() && isValidEmail1 && isValidEmail2 && isVaidPhone1.get() && isVaidPhone2.get() && isVaidGSTNo.get()){
    		return true;
    	}
		return false;
	}


	@FXML
	protected void handleBackToHome(ActionEvent event) throws SQLException {
		homeController = new HomeController();
        homeController.redirectHome(stage, "");
	}
    
    public BooleanBinding ValidationCheck(String Value, String pattern){
    	        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);
        // Now create matcher object.
        Matcher m = r.matcher(Value);
        BooleanBinding item_name_ck = new BooleanBinding() {
    		@Override
    		protected boolean computeValue() {
    			// TODO Auto-generated method stub
    			if (m.find( )) {
    	        	return true;
    	        }else {
    	        	return false;
    	        }
    		}
    	};
    	return item_name_ck;
    }
    
}