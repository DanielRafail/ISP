package objects;

public class PackageFeatures {

	private String featuresID;
	private String packageID;
	
	public PackageFeatures(String fID, String pID) {
		this.featuresID = fID;
		this.packageID = pID;
	}

	public String getFeaturesID() {
		return featuresID;
	}

	public void setFeaturesID(String featuresID) {
		this.featuresID = featuresID;
	}

	public String getPackageID() {
		return packageID;
	}

	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}
	
}
