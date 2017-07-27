package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mtechproject.samples.DBConnectFlogger;
import util.ShowAlert;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ModifyItemController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private HomeController homeController;
    @FXML
    private Text welcomeText;
    @FXML
    private TextField item_name,item_id,price,status,gms,item_code,unit;
    String item_name_slt,item_id_slt,price_slt,status_slt,gms_slt,item_code_slt,unit_slt;

    public ModifyItemController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyItem.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
    }


    public void redirectmodifyitem(Stage stage, String name,String selectedItem) {
    	fetchRowDetals(selectedItem);
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
    	Connection c;
    	
        int rowcount = 0;
        int rs = 0;
        try{  
	         c = DBConnectFlogger.connect();  
	         String SQL = "INSERT INTO item (item_name,item_id,price,item_code,unit,status,gms) VALUES(\""
	          + item_name.getText() 
	          + "\",\"" + item_id.getText() 
	          + "\",\"" + price.getText() 
	          + "\",\"" + item_code.getText() 
	          + "\",\"" + unit.getText() 
	          + "\",\"" + status.getText() 
	          + "\",\"" + gms.getText() 
	          + "\")";
	         System.out.println("Has added party SQL" + SQL);
	         rs = c.createStatement().executeUpdate(SQL);
	         System.out.println("Has added party" + rs);
        }catch (Exception e) {
			 System.out.println(e);
		}
	        if(rs==1){
	       	 ShowAlert.callAlert("Add Item","Item added Successfully");
	        }else{
	       	 ShowAlert.callAlert("Error","Please check given Details again.");
	        }
    }
    
    @FXML
	protected void handleBackToHome(ActionEvent event) throws SQLException {
		homeController = new HomeController();
        homeController.redirectHome(stage, "");
	}
    
    @FXML
	protected void handleupdateItem(ActionEvent event) throws SQLException {
		
	}
    
    void fetchRowDetals(String selectedRow){
		String []splits = selectedRow.replaceAll("^\\s*\\[|\\]\\s*$", "").split("\\s*,\\s*");
		List<String> wordList = Arrays.asList(splits); 
		item_name_slt=wordList.get(0);
		item_id_slt=wordList.get(1);
		price_slt=wordList.get(2);
		status_slt=wordList.get(4);
		gms_slt=wordList.get(6);
		item_code_slt=wordList.get(3);
		unit_slt=wordList.get(5);
		item_name.setText(item_name_slt);
		item_id.setText(item_id_slt);
		price.setText(price_slt);
		status.setText(status_slt);
		gms.setText(gms_slt);
		item_code.setText(item_code_slt);
		unit.setText(unit_slt);
    }
    
    
    
    
    
}