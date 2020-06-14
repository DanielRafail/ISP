package objects;

public class InvoiceItems {

	private String invoiceID;
	private double serviceCharges;
	private double setupFee;
	private double price;
	private String itemNumber;
	
	public InvoiceItems(String iID, double sCharges, double sFee, double price, String iNumber){
		this.invoiceID = iID;
		this.serviceCharges = sCharges;
		this.setupFee = sFee;
		this.price = price;
		this.itemNumber = iNumber;
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}

	public double getServiceCharges() {
		return serviceCharges;
	}

	public void setServiceCharges(double serviceCharges) {
		this.serviceCharges = serviceCharges;
	}

	public double getSetupFee() {
		return setupFee;
	}

	public void setSetupFee(double setupFee) {
		this.setupFee = setupFee;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	
}
