package utils.helpers;

public enum ChapterStatus {
	READY("READY"),PENDING("PENDING"),CANCELED("CANCELED");
	private String chapterStatus;
	private ChapterStatus(String chapterStatus) {
		this.chapterStatus = chapterStatus;
	}

	public String getChapterStatus() {
		return this.chapterStatus;
	}
}
