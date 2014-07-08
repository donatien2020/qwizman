package utils.helpers;

public enum ClassLevel {
	NURSARY("NURSARY"), ORDINARY("ORDINARY"), SECONDARY("SECONDARY"), UNIVERSITY(
			"UNIVERSITY"), COLLEDGE("COLLEDGE"),PRIMARY("PRIMARY");

	private String classLevel;

	private ClassLevel(String classLevel) {
		this.classLevel = classLevel;
	}

	public String getClassLevel() {
		return this.classLevel;
	}
}
