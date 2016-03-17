package com.bbva.paas.gdd.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BatchListRequest implements Serializable {

	private static final long serialVersionUID = -2312318602400417626L;
	
	@JsonProperty("batch")
	private List<BatchRequest> batch;

	public BatchListRequest() {
		super();
	}
	
	public BatchListRequest(List<BatchRequest> batch) {
		super();
		this.batch = batch;
	}

	public List<BatchRequest> getBatch() {
		return batch;
	}

	public void setBatch(List<BatchRequest> batch) {
		this.batch = batch;
	}

	public String toString() {
		return "BatchListRequest [batch=" + batch.toString() + "]";
	}
}
