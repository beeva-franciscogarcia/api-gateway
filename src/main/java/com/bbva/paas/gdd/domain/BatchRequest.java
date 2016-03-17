package com.bbva.paas.gdd.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BatchRequest implements Serializable {

	private static final long serialVersionUID = 9051946275114973757L;

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("method")
	private String method;
	
	@JsonProperty("headers")
	private List<HeaderType> headers;
	
	@JsonProperty("body")
	private String body;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public BatchRequest() {
		super();
	}
	
	public BatchRequest(String id, String url, String method, List<HeaderType> headers, String body) {
		super();
		this.id = id;
		this.url = url;
		this.method = method;
		this.headers = headers;
		this.setBody(body);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<HeaderType> getHeaders() {
		return headers;
	}

	public void setHeaders(List<HeaderType> headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String toString() {
		return "BatchRequest [url=" + url + ", method=" + method + ", headers=" + headers + ", body=" + body + "]";
	}
}
