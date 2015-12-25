package edu.ubb.si.model;

import java.util.UUID;

public class Document {
	
	private UUID uid;
	
	private String url;
	private String caption;
	private String context;
	
	public Document(String url, String caption, String context) {
		this.url = url;
		this.caption = caption;
		this.context = context;
		uid = UUID.randomUUID();
	}

	public Document() {
		this("", "", "");
	}

	@Override
	public int hashCode() {
		return url.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (!(obj instanceof Document)) {
			return false;
		}
		
		return hashCode() == obj.hashCode();
	}
	
	public UUID getUid() {
		return uid;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
}
