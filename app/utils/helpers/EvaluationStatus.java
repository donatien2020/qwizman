package utils.helpers;

public enum EvaluationStatus {
	PENDING("PENDING"), READY("READY"), CANCELED("CANCELED");
	private String evaluationStatus;
	private EvaluationStatus(String evaluationStatus) {
		this.evaluationStatus = evaluationStatus;
	}
	public String getEvaluationStatus() {
		return this.evaluationStatus;
	}

}
