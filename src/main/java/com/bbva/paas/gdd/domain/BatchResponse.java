package com.bbva.paas.gdd.domain;

import java.util.List;

public class BatchResponse {
	
	private String id;
	private String code;
	private String body;
	private List<HeaderType> headers;
	
	public BatchResponse() {
		super();
	}
	
	public BatchResponse(String id, String code, String body, List<HeaderType> headers) {
		super();
		this.setId(id);
		this.code = code;
		this.body = body;
		this.headers = headers;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<HeaderType> getHeaders() {
		return headers;
	}

	public void setHeaders(List<HeaderType> headers) {
		this.headers = headers;
	}
}
