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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewInvoiceController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private HomeController homeController;
    private ModifyInvoiceController modifyInvoiceController;
    @FXML
    private Label welcomeText;
    //@FXML
	//private TableView<InvoiceEntry> Viewtable;
    @FXML
	private TableView Viewtable;
    @FXML
    private ComboBox searchby;
    @FXML
    private TextField search;
    

    public ViewInvoiceController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ViewInvoice.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
        DisplayDatabase.buildData(Viewtable,"invoice"); 
        String newList = "[invoice_date,dispatch_date,invoice_number,party_id,party_name,address,address1]";
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


    public void redirectviewInvoice(Stage stage, String name) {
    	this.stage = stage;
        stage.setTitle("View Invoice");
        stage.setScene(scene);
        welcomeText.setText("Hey " + name + "! View your Invoice Here");
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
	protected void handleModifyInvoice(ActionEvent event) throws SQLException {
		System.out.println(Viewtable.getSelectionModel().getSelectedItem().toString());
		modifyInvoiceController = new ModifyInvoiceController();
		modifyInvoiceController.redirectmodifyinvoice(stage,Constants.getUsername(),Viewtable.getSelectionModel().getSelectedItem().toString());
	}
	 private <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
	        for (TableColumn<T, ?> col : tableView.getColumns())
	            if (col.getText().equals(name)) return col ;
	        return null;
	}
}