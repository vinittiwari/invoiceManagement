package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
 
public class InvoiceEntry {
	private StringProperty table_item_name = new SimpleStringProperty();
	
	private StringProperty table_item_id= new SimpleStringProperty();
	
	private StringProperty table_price = new SimpleStringProperty();
	
	private StringProperty table_quantity = new SimpleStringProperty();
	
	private StringProperty table_gst = new SimpleStringProperty();
	
	private StringProperty table_total = new SimpleStringProperty();

	private StringProperty table_cgst = new SimpleStringProperty();


	public void setTable_item_name(String table_item_name) {
		table_item_nameProperty().set(table_item_name);
	}


	public void setTable_item_id(String table_item_id) {
		table_item_idProperty().set(table_item_id);
	}
	
	public void setTable_price(String table_price) {
		table_priceProperty().set(table_price);
	}
	
	public void setTable_quantity(String table_quantity) {
		table_quantityProperty().set(table_quantity);
	}
	
	public void setTable_gst(String table_gst) {
		table_gstProperty().set(table_gst);
	}
	
	public void setTable_total(String table_total) {
		table_totalProperty().set(table_total);
	}

	
	public final String getTable_item_id() {
        return table_item_idProperty().get();
    }
	
	public final String getTable_item_name() {
        return table_item_nameProperty().get();
    }
	
	public final String getTable_price() {
        return table_priceProperty().get();
    }
	
	public final String getTable_quantity() {
        return table_quantityProperty().get();
    }

	public final String getTable_gst() {
        return table_gstProperty().get();
    }
	

	public final String getTable_total() {
        return table_totalProperty().get();
    }
	
	public StringProperty table_priceProperty() {
        return table_price ;
    }
	
	public StringProperty table_item_idProperty() {
        return table_item_id ;
    }
	
	public StringProperty table_item_nameProperty() {
        return table_item_name ;
    }
	
	public StringProperty table_quantityProperty() {
        return table_quantity ;
    }
	
	public StringProperty table_gstProperty() {
        return table_gst ;
    }
	
	public StringProperty table_totalProperty() {
        return table_total ;
    }



	

}