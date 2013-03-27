package de.netz39.mediastorage;

public class MediaItem {
	public static MediaItem forUploadData(String name, String contentType) {
		return new MediaItem(name, contentType);
	}

	private final String original_name;
	private final String content_type;

	private MediaItem(String name, String type) {
		this.original_name = name;
		this.content_type = type;
	}

	public String getOriginalName() {
		return original_name;
	}

	public String getContentType() {
		return content_type;
	}

}
