package com.cognizant.pension.management.authentication.constant;

public class CorsFilterConstant {
	public static final String ANGULAR_URL = "*";
	public static final String ALLOWED_HEADER_ORIGIN = "Origin";
	public static final String ALLOWED_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	public static final String ALLOWED_HEADER_CONTENT_TYPE = "Content-Type";
	public static final String ALLOWED_HEADER_ACCEPT = "Accept";
	public static final String ALLOWED_HEADER_JWT_TOKEN = "Jwt-Token";
	public static final String ALLOWED_HEADER_AUTHORIZATION = "Authorization";
	public static final String ALLOWED_HEADER_ORIGIN_ACCEPT = "Origin, Accept";
	public static final String ALLOWED_HEADER_X_REQUESTED_WITH = "X-Requested-With";
	public static final String ALLOWED_HEADER_ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
	public static final String ALLOWED_HEADER_ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
	public static final String ALLOWED_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
	public static final String HTTP_GET = "GET";
	public static final String HTTP_POST = "POST";
	public static final String HTTP_PUT = "PUT";
	public static final String HTTP_DELETE = "DELETE";
	public static final String HTTP_OPTIONS = "OPTIONS";

	private CorsFilterConstant() {
		throw new IllegalStateException("CorsFilterConstant class");
	}

}
