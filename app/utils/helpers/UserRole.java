package utils.helpers;

public enum UserRole {
	SUPERADMIN("SUPERADMIN"),
	ADMIN("ADMIN"), HEADTEACHER("HEADTEACHER"),
	TEACHER("TEACHER"),STUDENT("STUDENT"),REPRESENTATOR("REPRESENTATOR"),ANONIMOUS("ANONIMOUS");
	private String userRole;

	private UserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getUserRole() {
		return this.userRole;
	}
}
