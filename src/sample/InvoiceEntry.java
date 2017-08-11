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

	private StringProperty table_gst_amount = new SimpleStringProperty();
	
	private StringProperty table_cgst = new SimpleStringProperty();
	
	private StringProperty table_cgst_amount = new SimpleStringProperty();
	
	private StringProperty table_sgst = new SimpleStringProperty();
	
	private StringProperty table_sgst_amount = new SimpleStringProperty();
	
	private StringProperty table_igst = new SimpleStringProperty();
	
	private StringProperty table_igst_amount = new SimpleStringProperty();

	private StringProperty table_cess = new SimpleStringProperty();
	
	private StringProperty table_cess_amount = new SimpleStringProperty();


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
	
	public void setTable_gst_amount(String table_gst_amount) {
		table_gstAmountProperty().set(table_gst_amount);
	}
	
	public void setTable_cgst(String table_cgst) {
		table_cgstProperty().set(table_cgst);
	}
	
	public void setTable_cgst_amount(String table_cgst_amount) {
		table_cgstProperty().set(table_cgst_amount);
	}
	
	public void setTable_sgst(String table_sgst) {
		table_sgstProperty().set(table_sgst);
	}
	
	public void setTable_sgst_amount(String table_sgst_amount) {
		table_sgstAmountProperty().set(table_sgst_amount);
	}
	
	public void setTable_igst(String table_igst) {
		table_igstProperty().set(table_igst);
	}
	
	public void setTable_igst_amount(String table_igst_amount) {
		table_igstAmountProperty().set(table_igst_amount);
	}
	

	public void setTable_cess(String table_cess) {
		table_cessProperty().set(table_cess);
	}
	
	public void setTable_cess_amount(String table_cess_amount) {
		table_cessAmountProperty().set(table_cess_amount);
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
	
	public final String getTable_gst_amount() {
        return table_gstAmountProperty().get();
    }
	
	public final String getTable_cgst() {
        return table_cgstProperty().get();
    }
	
	public final String getTable_cgst_amount() {
        return table_cgstAmountProperty().get();
    }
	
	public final String getTable_sgst() {
        return table_sgstProperty().get();
    }
	
	public final String getTable_sgst_amount() {
        return table_sgstAmountProperty().get();
    }
	
	public final String getTable_igst() {
        return table_igstProperty().get();
    }
	
	public final String getTable_igst_amount() {
        return table_igstAmountProperty().get();
    }
	
	public final String getTable_cess() {
        return table_cessProperty().get();
    }
	
	public final String getTable_cess_amount() {
        return table_cessAmountProperty().get();
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
	
	public StringProperty table_gstAmountProperty() {
        return table_gst_amount ;
    }
	
	public StringProperty table_cgstProperty() {
        return table_cgst ;
    }
	
	public StringProperty table_cgstAmountProperty() {
        return table_cgst_amount ;
    }
	
	public StringProperty table_sgstProperty() {
        return table_sgst ;
    }
	
	public StringProperty table_sgstAmountProperty() {
        return table_sgst_amount ;
    }
	
	public StringProperty table_igstProperty() {
        return table_igst ;
    }
	
	public StringProperty table_igstAmountProperty() {
        return table_igst_amount ;
    }
	
	public StringProperty table_cessProperty() {
        return table_cess ;
    }
	
	public StringProperty table_cessAmountProperty() {
        return table_cess_amount ;
    }



	

}