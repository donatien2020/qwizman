package utils.helpers;

public enum QuestionStatus {
	ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

	private String questionStatus;

	private QuestionStatus(String questionStatus) {
		this.questionStatus = questionStatus;
	}

	public String getQuestionStatus() {
		return this.questionStatus;
	}
}
