package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ShowAlert {
	public static void callAlert(String Title,String Header){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(Title);
		alert.setHeaderText(Header);
		//alert.setContentText();
		alert.showAndWait();
	}
	public static Alert callAlert2(String Title,String Header){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(Title);
		alert.setHeaderText(Header);
		//alert.setContentText();
		//alert.showAndWait();
		return alert;
	}
}
