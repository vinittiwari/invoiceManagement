package sample;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class AddItemController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private HomeController homeController;
    @FXML
    private Text welcomeText;
    @FXML
    private TextField item_name,price,cess,item_code,unit,rate;
    @FXML
    private Button addItemSubmit;
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
	

    public AddItemController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddItem.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
        
        characterLimit(item_name, 20);
        characterLimit(rate, 3);
        characterLimit(cess, 4);
        characterLimit(price,6);
        characterLimit(item_code,7);
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
        item_name.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidName = ValidationCheck(newValue, "[a-zA-z0-9 ,]{4}");
				System.out.println("Valid Name------>"+isValidName.get());
			}
		});
        rate.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidRate = ValidationCheck(newValue, "[0-9]{3}");
				System.out.println("Valid Gst------>"+isValidRate.get());
			}
		});
        cess.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				isValidCess = ValidationCheck(newValue, "[0-9]{3}");
				System.out.println("Valid Gst------>"+isValidCess.get());
			}
		});
        
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


	public void redirectaddItem(Stage stage, String name) {
    	this.stage = stage;
        stage.setTitle("AddParty");
        stage.setScene(scene);
        welcomeText.setText("Hey " + name + "! Enter your party details here");
        stage.hide();
        stage.show();
        //stage.setFullScreen(true);
        
    }
    
    @FXML
    protected void handleAddItem(ActionEvent event) {
    	if(validateFields()){
    		Boolean status = true;
	    	LocalDate create_date = LocalDate.now();
	    	String item_id = item_name.getText().substring(0, 3).toUpperCase() + GenerateRandom.generateRandomChars("0123456789",2);
	    	Connection c;
	        int rowcount = 0;
	        int rs = 0;
	        try{  
		         c = DBConnectFlogger.connect();  
		         String SQL = "INSERT INTO item (item_name,item_id,price,item_code,unit,status,cess,create_date,rate) VALUES(\""
		          + item_name.getText() 
		          + "\",\"" + item_id
		          + "\",\"" + price.getText() 
		          + "\",\"" + item_code.getText() 
		          + "\",\"" + unit.getText() 
		          + "\",\"" + status 
		          + "\",\"" + cess.getText()
		          + "\",\"" + create_date
		          + "\",\"" + rate.getText()
		          + "\")";
		         System.out.println("Has added party SQL" + SQL);
		         rs = c.createStatement().executeUpdate(SQL);
		         System.out.println("Has added party" + rs);
	        }catch (Exception e) {
				 System.out.println(e);
			}
		        if(rs==1){
		       	 	Alert alert = ShowAlert.callAlert2("Add Item","Item added Successfully");
			       	if (alert.showAndWait().get() == ButtonType.OK){
						homeController = new HomeController();
			            homeController.redirectHome(stage, Constants.getUsername());
		            }
		        }else{
		       	 ShowAlert.callAlert("Error","Please check given Details again.");
		        }
    	}else{
    		ShowAlert.callAlert("Error","Please check given Details again.");
    	}
    }
    
    private boolean validateFields() {
    	System.out.println("Item Name Valid" + !isValidName.get()
    			+ "Valid Rate" + isValidRate.get()
    			+ "Valid cess" + isValidCess.get());
    	if(!isValidName.get() && isValidCess.get() && isValidRate.get()){
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
    	        	return false;
    	        }else {
    	        	return true;
    	        }
    		}
    	};
    	return item_name_ck;
    }
    
}