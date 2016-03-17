package com.bbva.paas.gdd.util;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public final class RestUtils {
	
	static final Logger logger = LoggerFactory.getLogger(RestUtils.class);
		
	private RestUtils() {
		
	}
		
	/**
	 * Method than return the JSON representation of an object
	 * 
	 * @param object to parse
	 * @return JSON representation of object as a String
	 */
	public static String convertoToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = null;
		try {			
			jsonInString = mapper.writeValueAsString(object);
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonInString;
	}
	
	/**
	 * Method than read a property param from body
	 * 
	 * @param field the filed wished to read
	 * @param jsonResponse the body where value can be 
	 * @return value | null
	 */
	public static String getValueFromJson(String field, HttpResponse<JsonNode> jsonResponse) {
        JsonNode json = jsonResponse.getBody();
        logger.info("response: " + json);
        String value = (String) json.getObject().get(field);
        logger.info("field value: " + value);
        return value;
    }
	
	/**
	 * Method than read a property param list from body
	 * 
	 * @param field the filed wished to read
	 * @param jsonResponse the body where value can be 
	 * @return JSONArray | null
	 */
	public static JSONArray getValueListFromJson(String field, HttpResponse<JsonNode> jsonResponse) {
        JsonNode json = jsonResponse.getBody();
        logger.info("response: " + json);
        JSONArray valueList = (JSONArray) json.getObject().get(field);
        logger.info("field value: " + valueList.toString());
        return valueList;
    }
	
	/**
     * Method than checks a json against a schema stored in path 
     * 
     * @param json the json body
     * @param pathToSchema the path to schema file to check against 
	 * @throws Throwable 
	 * @throws Exception 
     */
	public static void checkJSONAgainstSchema(String json, String pathToSchema) throws Exception, Throwable {
		try {
			String schema = FileUtils.readFile(pathToSchema);
			checkJSONAgainstStringSchema(json, schema);
		} catch (IOException e) {
			logger.error("Error reading " + pathToSchema + " file");
		}
}
	
	/**
     * Method than checks a json against a string that contains a schema 
     * 
     * @param json the json body
     * @param pathToSchema the string with the schema to check against 
     * @throws Throwable 
	 * @throws Exception 
     */
	public static void checkJSONAgainstStringSchema(String json, String schema) throws Exception, Throwable {
		assertThat(json, matchesJsonSchema(schema));
    }
}
