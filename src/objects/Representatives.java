package objects;

public class Representatives {

	private String employeeID;
	private String name;
	private String position;
	private String phone;
	private String email;
	private String managerID;
	private boolean passwordExpired;

	
	
	public Representatives(String eID, String name, String position, String phone, String email, String mID, boolean expired) {
		this.employeeID = eID;
		this.name = name;
		this.position = position;
		this.phone = phone;
		this.email = email;
		this.managerID = mID;
		this.passwordExpired = expired;
	}



	public String getEmployeeID() {
		return employeeID;
	}



	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPosition() {
		return position;
	}



	public void setPosition(String position) {
		this.position = position;
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



	public String getManagerID() {
		return managerID;
	}



	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}



	public boolean isPasswordExpired() {
		return passwordExpired;
	}



	public void setPasswordExpired(boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}
}

