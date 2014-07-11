package utils.helpers;

public enum TeacherStatus {
	PERMANANTRYACTIVE("PERMANANTRY-ACTIVE"), SUSPENDED("SUSPENDED"),DIMISSED("DIMISSED"),TEMORALARYACTIVE("TEMPORALARY-ACTIVE"),VISITING("VISITING");
	private String teacherStatus;
	private TeacherStatus(String teacherStatus) {
		this.teacherStatus = teacherStatus;
	}

	public String getTeacherStatus() {
		return this.teacherStatus;
	}
}
