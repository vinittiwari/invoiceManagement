package util;

public class Constants {
	Constants(String Username,int state,String invoiceNo){
		this.Username = Username;
		this.state= state;
		this.invoiceNo = invoiceNo;
	}
	static String Username;
	static int state = 28;
	static String invoiceNo = "18";
	
	public static String getInvoiceNo() {
		return invoiceNo;
	}
	public static void setInvoiceNo(String invoiceNo) {
		Constants.invoiceNo = invoiceNo;
	}
	public static int getState() {
		return state;
	}
	public static void setState(int state) {
		Constants.state = state;
	}
	public static void setUsername(String username) {
		Username = username;
	}
	public static String getUsername() {
		return Username;
	}
	
	 
}
