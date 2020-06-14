package objects;

public class ServiceRequest {

	private String employeeID;
	private String customerId;
	private String meetingDate;
	private String notes;
	private String requestType;
	
	public ServiceRequest(String eID, String cID, String date, String notes, String request) {
		this.employeeID = eID;
		this.customerId = cID;
		this.meetingDate = date;
		this.notes = notes;
		this.requestType = request;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
}
