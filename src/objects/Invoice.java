package objects;

public class Invoice {

	private String invoiceID;
	private String customerID;
	private String duedate;
	private String creationDate;
	
	
	public Invoice(String iID, String cID, String dDate, String cDate){
		this.invoiceID = iID;
		this.customerID = cID;
		this.duedate = dDate;
		this.creationDate = cDate;
	}


	public String getInvoiceID() {
		return invoiceID;
	}


	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}


	public String getCustomerID() {
		return customerID;
	}


	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}


	public String getDuedate() {
		return duedate;
	}


	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}


	public String getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
}
