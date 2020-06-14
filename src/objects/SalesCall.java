package objects;

public class SalesCall {

	private String employeeID;
	private boolean sold;
	private String customerID;
	
	public SalesCall(String eID, boolean sold, String cID) {
		this.employeeID = eID;
		this.sold = sold;
		this.customerID = cID;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public boolean isSold() {
		return sold;
	}

	public void setSold(boolean sold) {
		this.sold = sold;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	
}
