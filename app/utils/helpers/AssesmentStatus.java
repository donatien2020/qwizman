package utils.helpers;

public enum AssesmentStatus {
	STARTED("STARTED"), TERMINATED("TERMINATED"), RESET("RESET");
	private String assesmentStatus;
	private AssesmentStatus(String assesmentStatus) {
		this.assesmentStatus = assesmentStatus;
	}
	public String getAssesmentStatus() {
		return this.assesmentStatus;
	}

}
