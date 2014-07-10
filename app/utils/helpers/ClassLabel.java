package utils.helpers;

public enum ClassLabel {
	A("A"),B("B"),C("C"), D("D"),E("E"),F("F"),CS("CS"),ENG("ENG");
	private String classLabel;
	private ClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}

	public String getClassLabel() {
		return this.classLabel;
	}
}
