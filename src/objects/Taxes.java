package objects;

public class Taxes {

	private String type;
	private String startDate;
	private String endDate;
	private double rates;
	
	public Taxes(String type, String sDate, String eDate, double rates) {
		this.type = type;
		this.startDate = sDate;
		this.endDate = eDate;
		this.rates = rates;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public double getRates() {
		return rates;
	}

	public void setRates(double rates) {
		this.rates = rates;
	}
	
}
