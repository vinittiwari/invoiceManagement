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
    private TextField item_name,price,cess,item_code,unit,rate;
    String item_name_slt,item_id_slt,price_slt,status_slt,gms_slt,item_code_slt,unit_slt,rate_slt;

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
		rate_slt = wordList.get(4);
		item_id_slt=wordList.get(1);
		price_slt=wordList.get(2);
		status_slt=wordList.get(4);
		gms_slt=wordList.get(8);
		item_code_slt=wordList.get(3);
		unit_slt=wordList.get(7);
		item_name.setText(item_name_slt);
		rate.setText(rate_slt);
		price.setText(price_slt);
		cess.setText(gms_slt);
		item_code.setText(item_code_slt);
		unit.setText(unit_slt);
    }
    
    
    
    
    
}