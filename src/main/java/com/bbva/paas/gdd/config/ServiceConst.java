package com.bbva.paas.gdd.config;

public final class ServiceConst {
	
	public class RequestTypes {
		public static final String GENERIC 		= "GENERIC";
		public static final String DASHBOARD 	= "DASHBOARD";
	}
	
	public class SchemaFolders {
		public static final String PRIMARY 		= "schema";
	}
	
	public class SchemaFiles {
		public static final String GENERICREQUEST_SCHEMA 	= "ag-request-schema.json";
		public static final String DASHBOARD_SCHEMA 		= "ag-dashboard-schema.json";
	}
	
	public class Errors {
		public static final String JSONPARSE_ERROR_CODE = "parameterMissing";
		public static final String JSONPARSE_ERROR_DESC = "The content to match the given JSON schema";
	}
}