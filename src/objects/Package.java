package objects;

public class Package {

	private String packageID;
	private double price;
	private double upload;
	private double download;
	private double limit;
	private double overcharge;
	private String description;
	
	
	public Package(String pId, double price, double up, double down, double itemPrice, double maxLimit, double charge, String desc) {
		this.packageID = pId;
		this.price = itemPrice;
		this.upload = up;
		this.download = down;
		this. limit = maxLimit;
		this.overcharge = charge;
		this.description = desc;
		
	}


	public String getPackageID() {
		return packageID;
	}


	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public double getUpload() {
		return upload;
	}


	public void setUpload(double upload) {
		this.upload = upload;
	}


	public double getDownload() {
		return download;
	}


	public void setDownload(double download) {
		this.download = download;
	}


	public double getLimit() {
		return limit;
	}


	public void setLimit(double limit) {
		this.limit = limit;
	}


	public double getOvercharge() {
		return overcharge;
	}


	public void setOvercharge(double overcharge) {
		this.overcharge = overcharge;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
}
