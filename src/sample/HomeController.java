package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.Constants;

import java.io.IOException;

public class HomeController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private String name;
    @FXML
    private Text welcomeText;
    private AddPartyController addPartyController;
    private AddInvoiceController addInvoiceController;
    private AddItemController addItemController;
    private ViewInvoiceController viewInvoiceController;
	private ViewItemController viewItemController;
	private ViewPartyController viewPartyController;

    public HomeController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) { 
            e.printStackTrace();
        }
    }


    public void redirectHome(Stage stage, String name) {
    	this.stage = stage;
        stage.setTitle("Home");
        stage.setScene(scene);
        welcomeText.setText("Hello " + name + "! You are welcome.");
        this.name = name;
        stage.hide();
        stage.show();
        //stage.setFullScreen(true);
        stage.setMaximized(true);
    }
    
    @FXML
    protected void handleaddParty(ActionEvent event) {
    	 addPartyController = new AddPartyController();
    	 addPartyController.redirectaddParty(stage, name);
    }
    
    @FXML
    protected void handlemodifyParty(ActionEvent event) {
    	
    }
    
    @FXML
    protected void handleviewParty(ActionEvent event) {
		viewPartyController = new ViewPartyController();
		viewPartyController.redirectviewParty(stage,Constants.getUsername());
    }
    @FXML
    protected void handleviewItem(ActionEvent event) {
    	viewItemController = new ViewItemController();
		viewItemController.redirectviewItem(stage,Constants.getUsername());
    }
    @FXML
    protected void handleviewInvoice(ActionEvent event) {
    	viewInvoiceController = new ViewInvoiceController();
		viewInvoiceController.redirectviewInvoice(stage,Constants.getUsername());
    }
    @FXML
    protected void handleviewTransporter(ActionEvent event) {
    	
    }
    
    @FXML
    protected void handleaddInvoice(ActionEvent event) {
    	addInvoiceController = new AddInvoiceController();
    	addInvoiceController.redirectaddInvoice(stage,Constants.getUsername());
    }
    @FXML
    protected void handleaddItem(ActionEvent event) {
    	addItemController = new AddItemController();
    	addItemController.redirectaddItem(stage,Constants.getUsername());
    }
}