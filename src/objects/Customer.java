/**
 * 
 */
package objects;

/**
 * @author 1633028
 *
 */
public class Customer {
	private String customerId;
	private String packageID;
	private String username;
	private String name;
	private String phone;
	private String email;
	private String upload;
	private String download;
	private String installationID;
	private boolean passwordExpired;
	private String streetNumber;
	private String city;
	private String province;
	private String postalCode;

	
	public Customer(String cID, String pID, String user, String name, String phone, String email, String up, String down, String iID, Boolean expired, String street, String city, String province, String postal) {
		this.customerId = cID;
		this.packageID = pID;
		this.username = user;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.upload = up;
		this.download = down;
		this.installationID = iID;
		this.passwordExpired = expired;
		this.streetNumber = street;
		this.city = city;
		this.province = province;
		this.postalCode = postal;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getPackageID() {
		return packageID;
	}


	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUpload() {
		return upload;
	}


	public void setUpload(String upload) {
		this.upload = upload;
	}


	public String getDownload() {
		return download;
	}


	public void setDownload(String download) {
		this.download = download;
	}


	public String getInstallationID() {
		return installationID;
	}


	public void setInstallationID(String installationID) {
		this.installationID = installationID;
	}


	public Boolean getPasswordExpired() {
		return passwordExpired;
	}


	public void setPasswordExpired(Boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}


	public String getStreetNumber() {
		return streetNumber;
	}


	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	
}
