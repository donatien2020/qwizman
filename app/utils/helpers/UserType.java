/**
 * (c) Copyright Pivot access,www.pivotaccess.com
 */
package utils.helpers;

/**
 * @author Fourier
 */
public enum UserType {
	SUPERADMIN("SUPERADMIN"), ADMIN("ADMIN"), HEADTEACHER("HEADTEACHER"), TEACHER(
			"TEACHER"), STUDENT("STUDENT"), REPRESENTATOR("REPRESENTATOR"),ANONIMOUS("ANONIMOUS");

	private String userType;

	private UserType(String userType) {
		this.userType = userType;
	}

	public String getUserType() {
		return this.userType;
	}
}
