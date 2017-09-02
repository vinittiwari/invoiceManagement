package com.database;

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*; 

import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.*;
import com.mysql.jdbc.Constants;

import mtechproject.samples.DBConnectFlogger;
public class PrintInvoice {  
		static Font font = new Font(FontFamily.HELVETICA, 10);
		static PdfWriter writer;
        
        public static PdfPCell createCell(String content, int colspan, int rowspan, int border) {
            PdfPCell cell = new PdfPCell(new Phrase(content, font));
            cell.setColspan(colspan);
            cell.setRowspan(rowspan);
            cell.setBorder(border);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            return cell;
        }
        
        public static float sum(List<Float> list) {
	   	     float sum = 0; 
	   	     for (float i : list)
	   	         sum = sum + i;
	   	     return sum;
        }
        
        public static void printInvoice(String invoice_number) throws SQLException, DocumentException, MalformedURLException, IOException{

            
            /* Create Connection objects */
    		Connection c;
    		c = DBConnectFlogger.connect();  
            Statement stmt = c.createStatement();
            /* Define the SQL query */
            String SQL = "SELECT * from invoice WHERE invoice_number = \""+ invoice_number +"\"";
            ResultSet query_set = stmt.executeQuery(SQL);
            /* Step-2: Initialize PDF documents - logical objects */
            //Document my_pdf_report = new Document();
            Document my_pdf_report = new Document(PageSize.A3, 60, 60, 120, 80);
            writer = PdfWriter.getInstance(my_pdf_report, new FileOutputStream("D:\\Ocean Transact\\"+ invoice_number + ".pdf"));
            util.Constants.setSelectedInvoiceNumber(invoice_number);
            my_pdf_report.open();            
            //we have four columns in our table
            PdfPTable my_report_table = new PdfPTable(1);
            my_report_table.setWidthPercentage(100);
            
            PdfPTable my_report_table2 = new PdfPTable(2);
            my_report_table2.setWidthPercentage(100);
            PdfPTable my_report_table3 = new PdfPTable(9);
            my_report_table3.setWidthPercentage(100);
            PdfPTable my_report_table5 = new PdfPTable(9);
            my_report_table5.setWidthPercentage(100);
            PdfPTable my_report_table6 = new PdfPTable(1);
            my_report_table6.setWidthPercentage(100);
           // PdfPTable my_report_table4 = new PdfPTable(9);
            //create a cell object
            List<Float> item_priceTotal = new ArrayList<>();
            List<Float> item_cgstTotal = new ArrayList<>();
            List<Float> item_sgstTotal = new ArrayList<>();
            List<Float> item_igstTotal = new ArrayList<>();
            List<Float> item_cessTotal = new ArrayList<>();
            PdfPCell table_cell;
            PdfPCell cell = new PdfPCell();
           
            while (query_set.next()) {   
		                	String dept_id = util.Constants.getCompanyName();
		                    table_cell=new PdfPCell(new Phrase(dept_id));
		                    table_cell.addElement(new Phrase(dept_id));
		                    table_cell.addElement(new Phrase("Phone Number: " + util.Constants.getCompanyPhone(), font));
		                    table_cell.addElement(new Phrase("Address: " + util.Constants.getCompanyAdd(), font));
		                    table_cell.addElement(new Phrase("GSTIN: " + util.Constants.getCompany_gstin(), font));
		                    table_cell.addElement(new Phrase("Place of Supply: "+util.Constants.getCompanyDetails()));

		                    table_cell.setFixedHeight(100);
		                    my_report_table.addCell(table_cell);
		                    
                            String dept_id1 = query_set.getString("party_name");
                            table_cell=new PdfPCell(new Phrase("Customer Name: "+dept_id1));
                            table_cell.setFixedHeight(100);
		                    my_report_table2.addCell(table_cell);

		                    String address = query_set.getString("address");
                            cell.addElement(new Phrase("Address: " + address, font));
                            
		                    
		                    String dept_id2 = query_set.getString("invoice_date");
                            cell.addElement(new Phrase("Invoice Date: " + dept_id2, font));

                            String dept_id3 = query_set.getString("dispatch_date");
                            cell.addElement(new Phrase("Dispatch Date: " + dept_id3, font));
                            
                           
                            
                            my_report_table2.addCell(cell);
                            
                            
                            table_cell=new PdfPCell(new Phrase("Item Name"));
		                    my_report_table3.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase("Item Id"));
		                    my_report_table3.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase("Qty"));
		                    my_report_table3.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase("Price/item"));
		                    my_report_table3.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase("CGST"));
		                    my_report_table3.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase("SGST"));
		                    my_report_table3.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase("IGST"));
		                    my_report_table3.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase("Cess"));
		                    my_report_table3.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase("Total"));
		                    my_report_table3.addCell(table_cell);
		                    
		                    my_pdf_report.add(my_report_table); 
		                    my_pdf_report.add(my_report_table2);
		                    my_pdf_report.add(my_report_table3);
		                    
		                    String item = query_set.getString("item");
		                    JSONArray jryItem = new JSONArray(item);
		                    for(int i=0;i<jryItem.length();i++){
		                    	PdfPTable my_report_table4 = new PdfPTable(9);
		                    	my_report_table4.setWidthPercentage(100);
			                    JSONObject jobItem = (JSONObject) jryItem.get(i);
			                   
			                    table_cell=new PdfPCell(new Phrase((String) jobItem.get("item_name")));
			                    my_report_table4.addCell(table_cell);
			                    table_cell=new PdfPCell(new Phrase((String) jobItem.get("item_id")));
			                    my_report_table4.addCell(table_cell);
			                    table_cell=new PdfPCell(new Phrase( (String) jobItem.get("item_quantity")));
			                    my_report_table4.addCell(table_cell);
			                    
			                    table_cell=new PdfPCell(new Phrase( (String)  jobItem.get("item_total_price")));
			                    item_priceTotal.add(Float.parseFloat((String) jobItem.get("item_total_price")));
			                    my_report_table4.addCell(table_cell);

			                    table_cell.addElement(new Phrase((String) jobItem.get("item_gst_amount")));
			                    item_cgstTotal.add(Float.parseFloat((String) jobItem.get("item_gst_amount")));
			                    table_cell.addElement(new Phrase((String) jobItem.get("item_gst")+ "%", font));
			                    my_report_table4.addCell(table_cell);
			                    
			                    table_cell=new PdfPCell();
			                    table_cell.addElement(new Phrase((String) jobItem.get("item_sgst_amount")));
			                    item_sgstTotal.add(Float.parseFloat((String) jobItem.get("item_sgst_amount")));
			                    table_cell.addElement(new Phrase((String) jobItem.get("item_sgst")+ "%", font));
			                    my_report_table4.addCell(table_cell);

			                    table_cell=new PdfPCell();
			                    table_cell.addElement(new Phrase((String) jobItem.get("item_igst_amount")));
			                    item_igstTotal.add(Float.parseFloat((String) jobItem.get("item_igst_amount")));
			                    table_cell.addElement(new Phrase((String) jobItem.get("item_igst")+ "%", font));
			                    my_report_table4.addCell(table_cell);

			                    table_cell=new PdfPCell();
			                    table_cell.addElement(new Phrase((String) jobItem.get("item_cess_amount")));
			                    item_cessTotal.add(Float.parseFloat((String) jobItem.get("item_cess_amount")));
			                    table_cell.addElement(new Phrase((String) jobItem.get("item_cess")+ "%", font));
			                    my_report_table4.addCell(table_cell);
			                    
			                    table_cell=new PdfPCell(new Phrase((String) jobItem.get("item_total")));
			                    my_report_table4.addCell(table_cell);
			                    my_pdf_report.add(my_report_table4);
		                    }
		                    
		                    
		                    
		                    table_cell=new PdfPCell(new Phrase("Total"));
		                    my_report_table5.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase(" "));
		                    my_report_table5.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase(" "));
		                    my_report_table5.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase(String.valueOf(sum(item_priceTotal))));
		                    my_report_table5.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase(String.valueOf(sum(item_cgstTotal))));
		                    my_report_table5.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase(String.valueOf(sum(item_sgstTotal))));
		                    my_report_table5.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase(String.valueOf(sum(item_igstTotal))));
		                    my_report_table5.addCell(table_cell);
		                    table_cell=new PdfPCell(new Phrase(String.valueOf(sum(item_cessTotal))));
		                    my_report_table5.addCell(table_cell);
		                    String invoice_total = query_set.getString("invoice_total");
                            table_cell=new PdfPCell(new Phrase(invoice_total ));
		                    my_report_table5.addCell(table_cell);
		                    my_pdf_report.add(my_report_table5);
            }
            
            table_cell=new PdfPCell(new Phrase("Terms"));
            table_cell.addElement(new Phrase("Terms and Conditions"));
            table_cell.addElement(new Phrase("Subjected to "+util.Constants.getCompanyDetails()+" Jurisdiction "));
            table_cell.addElement(new Phrase(" "));
            table_cell.addElement(new Phrase(" "));
            table_cell.addElement(new Phrase("This is Printed Invoice"));
            table_cell.setFixedHeight(100);
            my_report_table6.addCell(table_cell);
            my_pdf_report.add(my_report_table6);
            /* Attach report table to PDF */
            //Image img = Image.getInstance("F:\\abc.jpg");
            //img.setAbsolutePosition(0, 0);
           // img.setTransparency(new int[]{ 0xF0 , 0xFF});
            //my_pdf_report.add(img);
           
            //my_pdf_report.close();
            
            
            PdfContentByte canvas = writer.getDirectContentUnder();
            writer.setRgbTransparencyBlending(true);
            Image image = Image.getInstance("D:\\Ocean Transact\\Logo1.jpg");
            image.scaleAbsolute(300,300);
            
            image.setAlignment(Image.ALIGN_JUSTIFIED);
            //my_pdf_report.add(image);
            image.setAbsolutePosition(300,750);
            PdfGState state = new PdfGState();
            state.setFillOpacity(0.4f);
            canvas.setGState(state);
            canvas.addImage(image);
            
            
            my_pdf_report.close();
            
            /* Close all DB related objects */
            query_set.close();
            stmt.close(); 
            c.close();               
        }
}
