package objects;

public class BillingProblem {
	private String callID;
	private String detailsCall;
	
	public BillingProblem(String id, String detail) {
		this.callID = id;
		this.detailsCall = detail;
	}

	public String getCallID() {
		return callID;
	}

	public void setCallID(String callID) {
		this.callID = callID;
	}

	public String getDetailsCall() {
		return detailsCall;
	}

	public void setDetailsCall(String detailsCall) {
		this.detailsCall = detailsCall;
	}
}
