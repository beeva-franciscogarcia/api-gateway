package com.bbva.paas.gdd.domain;

import java.util.List;

public class BatchListResponse {
	
	private List<BatchResponse> data;

	public BatchListResponse() {
		super();
	}
	
	public BatchListResponse(List<BatchResponse> data) {
		super();
		this.data = data;
	}

	public List<BatchResponse> getData() {
		return data;
	}

	public void setData(List<BatchResponse> data) {
		this.data = data;
	}
}
