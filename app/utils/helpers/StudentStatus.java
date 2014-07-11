package utils.helpers;

public enum StudentStatus {
	ACTIVE("ACTIVE"), INWEEKEND("IN-WEE-KEND"),DIMISSED("DIMISSED"),FREECANDIDATE("FREE-CANDIDATE");

	private String studentStatus;

	private StudentStatus(String studentStatus) {
		this.studentStatus = studentStatus;
	}

	public String getStudentStatus() {
		return this.studentStatus;
	}
}
