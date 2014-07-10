package utils.helpers;

public enum OperatorDegree {
	A0("A0"), A1("A1"),A2("A2"), STUDENT("STUDENT"), SYSTEM("SYSTEM"),MUSTERS("MUSTERS"), PHD("PhD"), POSTGRADUATE("POSTGRADUATE"), ANY("ANY");
	private String operatorDegree;
	private OperatorDegree(String operatorDegree) {
		this.operatorDegree = operatorDegree;
	}
	public String getOperatorDegree() {
		return this.operatorDegree;
	}
}
