package com.bbva.paas.gdd.util;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.bbva.paas.gdd.exception.ConfigurationException;
import com.bbva.paas.gdd.exception.RestException;
import com.jayway.restassured.RestAssured;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Component
public class RestClient {

	static final Logger logger = LoggerFactory.getLogger(RestClient.class);
	
    @Autowired
    public RestClient(Environment env) throws ConfigurationException {
    	logger.debug(env.getDefaultProfiles().toString());
        Unirest.setHttpClient(makeClient());
        RestAssured.useRelaxedHTTPSValidation();
    }
    
    public HttpResponse<JsonNode> request(String method, String url, String body) throws RestException {
        try {
        	HttpResponse<JsonNode> jsonResponse;
        	Map<String,String> headers = new HashMap<String,String>();
        	headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        	headers.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
        	switch (method) {
				case HttpMethod.GET:
					jsonResponse = Unirest.get(url).headers(headers).asJson();
					break;
				case HttpMethod.POST:
					jsonResponse = Unirest.post(url).headers(headers).body((body!=null) ? body : "").asJson();
					break;
				case HttpMethod.PUT:
					jsonResponse = Unirest.put(url).headers(headers).body((body!=null) ? body : "").asJson();
					break;
				case HttpMethod.DELETE:
					jsonResponse = Unirest.delete(url).headers(headers).body((body!=null) ? body : "").asJson();
					break;
				default:
					throw new UnirestException("Method not supported by com.beeva.qa.qanvil.rest.RestClient");
			}
            return jsonResponse;
        } catch (UnirestException e) {
        	logger.error("Error sending " + method.toUpperCase() + " call.", e);
            throw new RestException(e);
        }
    }
    
    public HttpResponse<JsonNode> request(String method, String url, String body, List<String> headerList, List<String> headerValueList) throws RestException {
        try {
        	HttpResponse<JsonNode> jsonResponse;
        	Map<String,String> headers = new HashMap<String,String>();
        	if((headerList!=null) && (headerValueList!=null) && (headerList.size()==headerValueList.size())) {
            	for(int i=0;i<headerList.size();i++) {
            		headers.put(headerList.get(i), headerValueList.get(i));
            	}
        	} else {
                headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        	}
        	switch (method) {
				case HttpMethod.GET:
					jsonResponse = Unirest.get(url).headers(headers).asJson();					
					break;
				case HttpMethod.POST:
					jsonResponse = Unirest.post(url).headers(headers).body((body!=null) ? body : "").asJson();
					break;
				case HttpMethod.PUT:
					jsonResponse = Unirest.put(url).headers(headers).body((body!=null) ? body : "").asJson();
					break;
				case HttpMethod.DELETE:
					jsonResponse = Unirest.delete(url).headers(headers).body((body!=null) ? body : "").asJson();
					break;
				default:
					throw new UnirestException("Method not supported by com.beeva.qa.qanvil.rest.RestClient");
        	}
            return jsonResponse;
        } catch (UnirestException e) {
        	logger.error("Error sending " + method.toUpperCase() + " call", e);
            throw new RestException(e);
        }
    }
    
    public HttpResponse<String> requestAsPlain(String method, String url, String body, List<String> headerList, List<String> headerValueList) throws RestException {
        try {
        	HttpResponse<String> plainResponse;
        	Map<String,String> headers = new HashMap<String,String>();
        	if((headerList!=null) && (headerValueList!=null) && (headerList.size()==headerValueList.size())) {
            	for(int i=0;i<headerList.size();i++) {
            		headers.put(headerList.get(i), headerValueList.get(i));
            	}
        	} else {
                headers.put(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN);
        	}
        	switch (method) {
				case HttpMethod.GET:				
					plainResponse = Unirest.get(url).headers(headers).asString();
					break;
				case HttpMethod.POST:
					plainResponse = Unirest.post(url).headers(headers).body((body!=null) ? body : "").asString();
					break;
				case HttpMethod.PUT:
					plainResponse = Unirest.put(url).headers(headers).body((body!=null) ? body : "").asString();
					break;
				case HttpMethod.DELETE:
					plainResponse = Unirest.delete(url).headers(headers).body((body!=null) ? body : "").asString();
					break;
				default:
					throw new UnirestException("Method not supported by com.beeva.qa.qanvil.rest.RestClient");
        	}
            return plainResponse;
        } catch (UnirestException e) {
        	logger.error("Error sending " + method.toUpperCase() + " call", e);
            throw new RestException(e);
        }
    }
        
    private static HttpClient makeClient() throws ConfigurationException {
        SSLContextBuilder builder = new SSLContextBuilder();
        CloseableHttpClient httpclient = null;
        try {
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            logger.info("custom httpclient called");
        } catch (NoSuchAlgorithmException e) {
        	logger.error("Error creating HttpClient", e);
            throw new ConfigurationException(e);
        } catch (KeyStoreException e) {
        	logger.error("Error creating HttpClient", e);
            throw new ConfigurationException(e);
        } catch (KeyManagementException e) {
        	logger.error("Error creating HttpClient", e);
            throw new ConfigurationException(e);
        }
        return httpclient;
    }
}
