package sample;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mtechproject.samples.DisplayDatabase;
import util.Constants;

public class ReportController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    @FXML
    private TextField partyname;
    private HomeController homeController;
    @FXML
    private Label welcomeText;
    @FXML
	private TableView Viewtable;
    @FXML
    private DatePicker fromdate,todate;
   

    public ReportController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Report.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
        
        
        String newList = "[item_name,item_id,price,item_code,status,unit,cess,rate]";
        String []splits = newList.replaceAll("^\\s*\\[|\\]\\s*$", "").split("\\s*,\\s*");
        List<String> listOfItemSearch = Arrays.asList(splits);
        ObservableList<String> observableListOfSearch = FXCollections.observableArrayList(listOfItemSearch);
		
        
        
    }


    public void redirectreport(Stage stage, String name) {
    	this.stage = stage;
        stage.setTitle("View Item");
        stage.setScene(scene);
        welcomeText.setText("Hey " + name + "! View your Item Here");
        stage.hide();
        stage.show();
        //stage.setFullScreen(true);
    }

	@FXML
	protected void handleBackToHome(ActionEvent event) throws SQLException {
		homeController = new HomeController();
        homeController.redirectHome(stage, "");
	}
	
	@FXML
	protected void handleDeleteItem(ActionEvent event) throws SQLException {
		
	}
	
	@FXML
	protected void handlePrint(ActionEvent event) throws SQLException {
		
	}
	
	@FXML
	protected void handleOnClickGO(ActionEvent event) throws SQLException {
		 String SQL = "SELECT * from invoice where party_name=\"" + partyname.getText() + "\""; 
		 System.out.println("---->"+SQL);
	     DisplayDatabase.querybuildData(Viewtable,"item",SQL);
	}
	
    private <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
        for (TableColumn<T, ?> col : tableView.getColumns())
            if (col.getText().equals(name)) return col ;
        return null ;
    }
    
    
    
}