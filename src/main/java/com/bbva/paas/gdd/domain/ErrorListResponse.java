package com.bbva.paas.gdd.domain;

import java.util.List;

public class ErrorListResponse {

	private List<ErrorResponse> errors;

	public ErrorListResponse() {
		super();
	}
	
	public ErrorListResponse(List<ErrorResponse> errors) {
		super();
		this.errors = errors;
	}

	public List<ErrorResponse> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorResponse> errors) {
		this.errors = errors;
	}
}
