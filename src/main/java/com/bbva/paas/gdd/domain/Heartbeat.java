package com.bbva.paas.gdd.domain;

public class Heartbeat {
	
	private final long id;
	private final String content;
    
	public Heartbeat(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}