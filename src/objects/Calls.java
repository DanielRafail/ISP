package objects;

public class Calls {
	private String callID;
	private String customerId;
	private String employeeId;
	private String requestType;
	
	public Calls(String caID, String cuID, String eID, String request) {
		this.callID = caID;
		this.customerId = cuID;
		this.employeeId = eID;
		this.requestType = request;
	}

	public String getCallID() {
		return callID;
	}

	public void setCallID(String callID) {
		this.callID = callID;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
}
