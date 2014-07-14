package utils.helpers;

public enum EvaluationType {
	EXERCISE("EXERCISE"), EXAM("EXAM"), QUIZ("QUIZ"),GENERALQUIZ("GENERAL-QUIZ");

	private String evaluationType;

	private EvaluationType(String evaluationType) {
		this.evaluationType = evaluationType;
	}

	public String getEvaluationType() {
		return this.evaluationType;
	}
}
