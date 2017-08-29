package mtechproject.samples;

import java.sql.Connection;  
import java.sql.ResultSet;  
import javafx.beans.property.SimpleStringProperty;  
import javafx.beans.value.ObservableValue;  
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList;  
import javafx.scene.control.TableColumn;  
import javafx.scene.control.TableColumn.CellDataFeatures;  
import javafx.scene.control.TableView;  
import javafx.util.Callback;  
import mtechproject.samples.*;
import util.Constants;  
public class DisplayDatabase{  
  //Tableview and data  
  private static ObservableList<ObservableList> data;  
  //Connection database  
  public static void buildData(TableView tableview,String tablename){  
     Connection c ;  
     data = FXCollections.observableArrayList();  
     try{  
      c = DBConnectFlogger.connect();  
      String SQL = "SELECT * from " + tablename;  
      //ResultSet  
      ResultSet rs = c.createStatement().executeQuery(SQL); 
      /**********************************  
       * TABLE COLUMN ADDED DYNAMICALLY *  
       **********************************/  
      for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){  
        //We are using non property style for making dynamic table  
        final int j = i;  
        if(!(rs.getMetaData().getColumnName(i+1).equals("item"))){
        	if((rs.getMetaData().getColumnName(i+1).equals("invoice_number"))){
		        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));  
		        col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){            
		          public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                 
		            return new SimpleStringProperty(Constants.getInvoicePrefix()+param.getValue().get(j).toString());              
		          }            
		        });  
		        tableview.getColumns().addAll(col);
        	}else{
        		TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));  
		        col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){            
		          public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                 
		            return new SimpleStringProperty(param.getValue().get(j).toString());              
		          }            
		        });  
		        tableview.getColumns().addAll(col);
        	}
	   }
      }  
      /********************************  
       * Data added to ObservableList *  
       ********************************/  
      while(rs.next()){  
        //Iterate Row  
        ObservableList<String> row = FXCollections.observableArrayList();  
        for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){  
          //Iterate Column  
          row.add(rs.getString(i));  
        }  
        //System.out.println("Row [1] added "+row );  
        data.add(row);  
      }  
      //FINALLY ADDED TO TableView  
      tableview.setItems(data);  
     }catch(Exception e){  
       e.printStackTrace();  
       System.out.println("Error on Building Data");        
     }  
   }  
  public static void querybuildData(TableView tableview,String tablename,String SQL){  
	     Connection c ;  
	     data = FXCollections.observableArrayList();  
	     try{  
	      c = DBConnectFlogger.connect();  
	      //String SQL = "SELECT * from " + tablename;  
	      //ResultSet  
	      ResultSet rs = c.createStatement().executeQuery(SQL); 
	      /**********************************  
	       * TABLE COLUMN ADDED DYNAMICALLY *  
	       **********************************/  
	      for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){  
	        //We are using non property style for making dynamic table  
	        final int j = i;  
	        if(!(rs.getMetaData().getColumnName(i+1).equals("item"))){
	        	if((rs.getMetaData().getColumnName(i+1).equals("invoice_number"))){
			        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));  
			        col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){            
			          public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                 
			            return new SimpleStringProperty(Constants.getInvoicePrefix()+param.getValue().get(j).toString());              
			          }            
			        });  
			        tableview.getColumns().addAll(col);
	        	}else{
	        		TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));  
			        col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){            
			          public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                 
			            return new SimpleStringProperty(param.getValue().get(j).toString());              
			          }            
			        });  
			        tableview.getColumns().addAll(col);
	        	}
		   }
	      }  
	      /********************************  
	       * Data added to ObservableList *  
	       ********************************/  
	      while(rs.next()){  
	        //Iterate Row  
	        ObservableList<String> row = FXCollections.observableArrayList();  
	        for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){  
	          //Iterate Column  
	          row.add(rs.getString(i));  
	        }  
	        //System.out.println("Row [1] added "+row );  
	        data.add(row);  
	      }  
	      //FINALLY ADDED TO TableView  
	      tableview.setItems(data);  
	     }catch(Exception e){  
	       e.printStackTrace();  
	       System.out.println("Error on Building Data");        
	     }  
	   } 
}   