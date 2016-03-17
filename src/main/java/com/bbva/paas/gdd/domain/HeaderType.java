package com.bbva.paas.gdd.domain;

public class HeaderType {

	private String header;
	private String value;
	
	public HeaderType() {
		super();
	}
	
	public HeaderType(String header, String value) {
		super();
		this.header = header;
		this.value = value;
	}
	
	public String getHeader() {
		return header;
	}
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
