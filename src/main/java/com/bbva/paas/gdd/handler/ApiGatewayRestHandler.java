package com.bbva.paas.gdd.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.bbva.paas.gdd.config.ServiceConst;
import com.bbva.paas.gdd.config.ServiceValidation;
import com.bbva.paas.gdd.domain.BatchRequest;
import com.bbva.paas.gdd.domain.BatchResponse;
import com.bbva.paas.gdd.domain.ErrorListResponse;
import com.bbva.paas.gdd.domain.ErrorResponse;
import com.bbva.paas.gdd.domain.HeaderType;
import com.bbva.paas.gdd.exception.RestException;
import com.bbva.paas.gdd.util.RestClient;
import com.bbva.paas.gdd.util.RestUtils;
import com.google.common.base.Strings;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

@Component
public class ApiGatewayRestHandler {

	static final Logger logger = LoggerFactory.getLogger(ApiGatewayRestHandler.class);
	
	@Autowired
	private RestClient restClient;
	
	@Autowired
	private ServiceValidation serviceValidation;
	
	/**
	 * Method for checking if correct response code is received in last response made
	 * 
	 * @param data response list
	 * @return true | false
	 */
	public boolean checkResponseForChain(List<BatchResponse> data) {
		int responseCode = Integer.parseInt(data.get(data.size()-1).getCode());
		if(responseCode >=200 && responseCode < 400) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method for reading headers from request
	 * 
	 * @param request the request for reading headers
	 * @return list of headers in key,value format
	 */
	public List<HeaderType> getHeadersFromRequest(HttpServletRequest request) {
		List<HeaderType> headers = new ArrayList<HeaderType>();
		Enumeration<String> e = request.getHeaderNames();
		String headerKey;
		while(e.hasMoreElements()) {
			headerKey = e.nextElement();
			headers.add(new HeaderType(headerKey, request.getHeader(headerKey)));
		}
		return headers;
	}
	
	/**
	 * Method for reading JSON Schema file
	 * 
	 * @param requestType kind of request between GENERIC, DASHBOARD, etc...
	 * @return JSON Schema as String
	 */
	public String getJSONSchemaFile(String requestType) {
		String schemaFolder = Strings.isNullOrEmpty(serviceValidation.getFolder()) == false ? serviceValidation.getFolder() : 
			this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath().concat(ServiceConst.SchemaFolders.PRIMARY);
		String schemaFile 	= ServiceConst.SchemaFiles.GENERICREQUEST_SCHEMA;
		switch (requestType) {
			case ServiceConst.RequestTypes.DASHBOARD:
				schemaFile = ServiceConst.SchemaFiles.DASHBOARD_SCHEMA;
				break;
			default:
				break;
		}
		String pathToSchema = schemaFolder.concat(File.separator).concat(schemaFile);
		logger.info("getJSONSchemaFile: " + pathToSchema);
		return pathToSchema;
	}
	
	/**
	 * Method for creating a default JSON Schema error list
	 * 
	 * @return default error list
	 */
	public ErrorListResponse getParseErrorList() {
		ErrorListResponse errorList = new ErrorListResponse(new ArrayList<ErrorResponse>());
		errorList.getErrors().add(new ErrorResponse(ServiceConst.Errors.JSONPARSE_ERROR_CODE, ServiceConst.Errors.JSONPARSE_ERROR_DESC));
		return errorList;
	}
	
	/**
	 * Method for making a REST API request
	 * 
	 * @param request a structure that contains url, body, headers and http method 
	 * @return a structure with response code, body and headers
	 */
	@SuppressWarnings("rawtypes")
	public BatchResponse makeRequest(BatchRequest request) {
		HttpResponse<JsonNode> response = null;
		List<String> headerList = new ArrayList<String>();
		List<String> headerValue = new ArrayList<String>();
		List<HeaderType> responseHeaderList = new ArrayList<HeaderType>();
		Iterator it;
		BatchResponse batch = new BatchResponse();
		for(int i=0;i<request.getHeaders().size();i++) {
			headerList.add(request.getHeaders().get(i).getHeader());
			headerValue.add(request.getHeaders().get(i).getValue());
		}
		try {
			response = restClient.request(request.getMethod().toUpperCase(), request.getUrl(), request.getBody(), headerList, headerValue);
			it = response.getHeaders().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry)it.next();
				responseHeaderList.add(new HeaderType(pairs.getKey().toString(), pairs.getValue().toString()));
			}
			batch = new BatchResponse(request.getId(), HttpStatus.OK.toString(), RestUtils.getValueListFromJson("data", response).toString(), responseHeaderList);
		} catch (RestException e) {
			e.printStackTrace();
		}
		return batch;
	}
}