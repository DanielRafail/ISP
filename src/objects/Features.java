package objects;

public class Features {

	public String getFeaturesID() {
		return featuresID;
	}


	public void setFeaturesID(String featuresID) {
		this.featuresID = featuresID;
	}


	public String getFeaturesDesc() {
		return featuresDesc;
	}


	public void setFeaturesDesc(String featuresDesc) {
		this.featuresDesc = featuresDesc;
	}


	public int getFeaturesPrice() {
		return featuresPrice;
	}


	public void setFeaturesPrice(int featuresPrice) {
		this.featuresPrice = featuresPrice;
	}


	private String featuresID;
	private String featuresDesc;
	private int featuresPrice;

	
	public Features(String fID, String fDesc, int fPrice) {
		this.featuresID = fID;
		this.featuresDesc = fDesc;
		this.featuresPrice = fPrice;
	}
}
