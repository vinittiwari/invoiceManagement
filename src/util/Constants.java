package util;

public class Constants {
	Constants(String Username,int state,String invoiceNo){
		this.Username = Username;
		this.state= state;
		this.invoiceNo = invoiceNo;
	}
	static String Username;
	static int state = 28;
	static String invoiceNo = "1";
	static String invoicePrefix = "MT";
	static String companyName = "Mahavir Traders";
	static String selectedInvoiceNumber = "";
	static String company_gstin="23ADEPK828K1Z8";
	public static String getCompany_gstin() {
		return company_gstin;
	}
	public static void setCompany_gstin(String company_gstin) {
		Constants.company_gstin = company_gstin;
	}
	public static String getSelectedInvoiceNumber() {
		return selectedInvoiceNumber;
	}
	public static void setSelectedInvoiceNumber(String selectedInvoiceNumber) {
		Constants.selectedInvoiceNumber = selectedInvoiceNumber;
	}
	public static String getCompanyName() {
		return companyName;
	}
	public static void setCompanyName(String companyName) {
		Constants.companyName = companyName;
	}
	public static String getCompanyAdd() {
		return companyAdd;
	}
	public static void setCompanyAdd(String companyAdd) {
		Constants.companyAdd = companyAdd;
	}
	public static String getCompanyPhone() {
		return companyPhone;
	}
	public static void setCompanyPhone(String companyPhone) {
		Constants.companyPhone = companyPhone;
	}
	public static String getCompanyDetails() {
		return companyDetails;
	}
	public static void setCompanyDetails(String companyDetails) {
		Constants.companyDetails = companyDetails;
	}
	static String companyAdd = "17-18 Kshram Shivir Koyla Phatak Square";
	static String companyPhone = "9827086122";
	static String companyDetails = "Ujjain";
	public static String getInvoicePrefix() {
		return invoicePrefix;
	}
	public static void setInvoicePrefix(String invoicePrefix) {
		Constants.invoicePrefix = invoicePrefix;
	}
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
