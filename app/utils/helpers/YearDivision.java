package utils.helpers;

public enum YearDivision {
	I("I"), II("II"), III("III");

	private String yearDivision;

	private YearDivision(String yearDivision) {
		this.yearDivision = yearDivision;
	}

	public String getYearDivision() {
		return this.yearDivision;
	}
}
