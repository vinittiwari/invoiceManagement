package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mtechproject.samples.DBConnectFlogger;
import mtechproject.samples.DisplayDatabase;
import util.Constants;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ViewPartyController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    @FXML
    private Label welcomeText;
    private HomeController homeController;
    @FXML
	private TableView Viewtable;
    private ModifyPartyController modifyPartyController;
    @FXML
    private ComboBox searchby;
    @FXML
    private TextField search;

    public ViewPartyController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ViewParty.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
        DisplayDatabase.buildData(Viewtable,"party"); 
        String newList = "[party_name,address,address1,state,gstin,transport,phone1,phone2,email1,email2,party_id]";
        String []splits = newList.replaceAll("^\\s*\\[|\\]\\s*$", "").split("\\s*,\\s*");
        List<String> listOfItemSearch = Arrays.asList(splits);
        ObservableList<String> observableListOfSearch = FXCollections.observableArrayList(listOfItemSearch);
		searchby.getItems().addAll(observableListOfSearch);
		searchby.getSelectionModel().selectFirst();
		
        ObservableList data =  Viewtable.getItems();
        search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                Viewtable.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList subentries = FXCollections.observableArrayList();

            long count = Viewtable.getColumns().stream().count();
            for (int i = 0; i < Viewtable.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                	String entry = "" + getTableColumnByName(Viewtable, searchby.getSelectionModel().getSelectedItem().toString()).getCellData(i);
                	if (entry.toLowerCase().contains(value)) {
                        subentries.add(Viewtable.getItems().get(i));
                        break;
                    }
                }
            }
            Viewtable.setItems(subentries);
        });
    }


    public void redirectviewParty(Stage stage, String name) {
    	this.stage = stage;
        stage.setTitle("View Party");
        stage.setScene(scene);
        welcomeText.setText("Hey " + name + "! View your Party Here");
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
	protected void handleModifyParty(ActionEvent event) throws SQLException {
		System.out.println(Viewtable.getSelectionModel().getSelectedItem().toString());
		//Viewtable.getItems().remove(selectedItem);
		//Viewtable.getSelectionModel().clearSelection();
		
		modifyPartyController = new ModifyPartyController();
		modifyPartyController.redirectmodifyparty(stage,Constants.getUsername(),Viewtable.getSelectionModel().getSelectedItem().toString());
		
	}
	
	@FXML
	protected void handleDeleteParty(ActionEvent event) throws SQLException {
		
	}
	
	private <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
        for (TableColumn<T, ?> col : tableView.getColumns())
            if (col.getText().equals(name)) return col ;
        return null ;
    }
	
	
    
    
    
}