package objects;

public class TechProblem {

	
	private String callID;
	private String problemDescription;
	
	public TechProblem(String cID, String desc) {
		this.callID = cID;
		this.problemDescription = desc;
	}

	public String getCallID() {
		return callID;
	}

	public void setCallID(String callID) {
		this.callID = callID;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
}
