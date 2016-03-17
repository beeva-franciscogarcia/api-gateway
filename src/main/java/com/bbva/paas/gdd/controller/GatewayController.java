package com.bbva.paas.gdd.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bbva.paas.gdd.config.ServiceConst;
import com.bbva.paas.gdd.config.ServiceURIs;
import com.bbva.paas.gdd.domain.BatchListRequest;
import com.bbva.paas.gdd.domain.BatchListResponse;
import com.bbva.paas.gdd.domain.BatchRequest;
import com.bbva.paas.gdd.domain.BatchResponse;
import com.bbva.paas.gdd.domain.HeaderType;
import com.bbva.paas.gdd.handler.ApiGatewayRestHandler;
import com.bbva.paas.gdd.util.RestUtils;

@RestController()
@RequestMapping("apigateway")
public class GatewayController {

	@Autowired
	private ApiGatewayRestHandler restHandler;
	
	@Autowired
	private ServiceURIs serviceURIs;
	
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("*")
	@ResponseBody
	public String fallbackMethod(){
	    return "fallback method";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.POST, value = "/dashboard")
	ResponseEntity getDashboardByPost(HttpServletRequest request, @RequestParam(value="break", defaultValue="false") String breakChain) {
		List<BatchResponse> data = new ArrayList<BatchResponse>();
		List<HeaderType> headers = restHandler.getHeadersFromRequest(request);
		try {	
			//Request to /cards
			data.add(restHandler.makeRequest(new BatchRequest(String.valueOf(counter.incrementAndGet()), serviceURIs.getCards(), HttpMethod.GET, headers, null)));
			//Request to /accounts
			if(!restHandler.checkResponseForChain(data) && breakChain.equalsIgnoreCase("true")) {
				data.add(new BatchResponse(String.valueOf(counter.incrementAndGet()), null, null, null));
			} else {
				data.add(restHandler.makeRequest(new BatchRequest(String.valueOf(counter.incrementAndGet()), serviceURIs.getAccounts(), HttpMethod.GET, headers, null)));
			}
			//Request to /transactions
			if(!restHandler.checkResponseForChain(data) && breakChain.equalsIgnoreCase("true")) {
				data.add(new BatchResponse(String.valueOf(counter.incrementAndGet()), null, null, null));
			} else {
				data.add(restHandler.makeRequest(new BatchRequest(String.valueOf(counter.incrementAndGet()), serviceURIs.getTransactions(), HttpMethod.GET, headers, null)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(new BatchListResponse(data), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.POST, value = "/single")
	ResponseEntity getByPost(@RequestBody BatchRequest request) {
		try {
			RestUtils.checkJSONAgainstSchema(RestUtils.convertoToJson(request), restHandler.getJSONSchemaFile(ServiceConst.RequestTypes.GENERIC));
			return new ResponseEntity(restHandler.makeRequest(request), HttpStatus.OK);
		} catch (Throwable t) {
			return new ResponseEntity(restHandler.getParseErrorList(), HttpStatus.BAD_REQUEST);
    	}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity getListByPost(@RequestBody BatchListRequest request) {
		try {
			RestUtils.checkJSONAgainstSchema(RestUtils.convertoToJson(request), restHandler.getJSONSchemaFile(ServiceConst.RequestTypes.GENERIC));
		} catch (Throwable t) {
    		return new ResponseEntity(restHandler.getParseErrorList(), HttpStatus.BAD_REQUEST);
    	}
		List<BatchResponse> data = new ArrayList<BatchResponse>();
		for(BatchRequest currRequest: request.getBatch()) {
			data.add(restHandler.makeRequest(currRequest));
		}
		return new ResponseEntity(new BatchListResponse(data), HttpStatus.OK);
	}
}