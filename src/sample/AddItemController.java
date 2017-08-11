package sample;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mtechproject.samples.DBConnectFlogger;
import util.ShowAlert;

public class AddItemController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private HomeController homeController;
    @FXML
	private DatePicker created_date;
    @FXML
    private Text welcomeText;
    @FXML
    private TextField item_name,item_id,price,status,cess,item_code,unit,rate;
    @FXML
    private Button addItemSubmit;

    public AddItemController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddItem.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
        LocalDate currentDate = LocalDate.now();
        created_date.setValue(currentDate);
        
        /*addItemSubmit.disableProperty().bind(
        		item_name.textProperty().isEmpty()
        		.or(item_id.textProperty().isEmpty())
        		.or(price.textProperty().isEmpty())
        		.or(status.textProperty().isEmpty())
        		.or(cess.textProperty().isEmpty())
        		.or(item_code.textProperty().isEmpty())
        		.or(unit.textProperty().isEmpty())        		
        	    .or(rate.textProperty().isEmpty()));*/
        	    //[^\\s*$]
        addItemSubmit.disableProperty().bind(item_name.textProperty().isEmpty());
        
        item_name.textProperty().addListener((observable, oldValue, newValue) -> {
        	if(newValue != null){
        		addItemSubmit.disableProperty().bind(ValidationCheck(newValue,"^[a-zA-Z\\s]{2,29}$"));
        	}
        });
    }


    public void redirectaddItem(Stage stage, String name) {
    	this.stage = stage;
        stage.setTitle("AddItem");
        stage.setScene(scene);
        welcomeText.setText("Hey " + name + "! Enter your Item details here");
        stage.hide();
        stage.show();
        //stage.setFullScreen(true);
        
    }
    
    @FXML
    protected void handleAddItem(ActionEvent event) {
    	LocalDate date_invoice = created_date.getValue();
    	Connection c;
        int rowcount = 0;
        int rs = 0;
	this.status = "Y";
        try{  
	         c = DBConnectFlogger.connect();  
	         String SQL = "INSERT INTO item (item_name,item_id,price,item_code,unit,status,cess,create_date,rate) VALUES(\""
	          + item_name.getText() 
	          + "\",\"" + item_id.getText() 
	          + "\",\"" + price.getText() 
	          + "\",\"" + item_code.getText() 
	          + "\",\"" + unit.getText() 
	          + "\",\"" + status.getText() 
	          + "\",\"" + cess.getText()
	          + "\",\"" + date_invoice
	          + "\",\"" + rate.getText()
	          + "\")";
	         System.out.println("Has added item SQL" + SQL);
	         rs = c.createStatement().executeUpdate(SQL);
	         System.out.println("Has added item" + rs);
        }catch (Exception e) {
			 System.out.println(e);
		}
	        if(rs==1){
	       	 ShowAlert.callAlert("Add Item","Item has been added Successfully");
	        }else{
	       	 ShowAlert.callAlert("Error","Please check given Details again.");
	        }
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
