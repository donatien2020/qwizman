package utils.helpers;

public enum SchoolType {
	TRANSPORTATION("TRANSPORTATION"), SHOP("SHOP"), SUPPERMARKET("SUPPERMARKET"), HOTEL(
			"HOTEL"), GOVERNANCE("GOVERNANCE"), NGO("NGO"), EDUCATIONAL(
			"EDUCATIONAL"), ANY("ANY"), BANKING("BANKING");

	private String organizationType;

	private SchoolType(String organizationType) {
		this.organizationType = organizationType;
	}

	public String getOrganizationType() {
		return this.organizationType;
	}
}
