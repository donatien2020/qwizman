package utils.helpers;

public enum SchoolType {
	TRANSPORTATION("TRANSPORTATION"), SHOP("SHOP"), SUPPERMARKET("SUPPERMARKET"), HOTEL(
			"HOTEL"), GOVERNANCE("GOVERNANCE"), NGO("NGO"), EDUCATIONAL(
			"EDUCATIONAL"), ANY("ANY"), BANKING("BANKING");

	private String schoolType;

	private SchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	public String getOrganizationType() {
		return this.schoolType;
	}
}
