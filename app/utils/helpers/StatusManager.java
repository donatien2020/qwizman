package utils.helpers;

public enum StatusManager {
	ACTIVE("ACTIVE"), INACTIVE("INACTIVE"), PEDDING("PEDDING"), DELIVERED(
			"DELIVERED"), REVERSED("REVERSED"), TRANSFERED("TRANSFERED"), GRABED(
			"GRABED"), REJECTED("REJECTED"),USED("USED"),PAID("PAID");
	private String statusManager;

	private StatusManager(String statusManager) {
		this.statusManager = statusManager;
	}

	public String getStatus() {
		return this.statusManager;
	}
}
