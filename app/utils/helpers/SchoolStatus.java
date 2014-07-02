package utils.helpers;

public enum SchoolStatus {
	ACVTIVE("ACVTIVE"), INACTIVE("INACTIVE");
	private String organizationStatus;
	private SchoolStatus(String organizationStatus) {
		this.organizationStatus = organizationStatus;
	}
	public String getOrganizationStatus() {
		return this.organizationStatus;
	}
}
