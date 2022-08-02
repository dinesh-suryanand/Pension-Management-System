package com.cognizant.pension.management.authentication.constant;

public class SecurityConstant {
	public static final long EXPIRATION_TIME = 432_000_000; // 5 days expressed in milliseconds
	public static final String TOKEN_HEADER = "Bearer";
	public static final String JWT_TOKEN_HEADER = "Jwt-Token";
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token Cannot Be Verified";
	public static final String ADRISH_BOSE_PVT = "Adrish Bose Pvt";
	public static final String ADMINISTRATION = "User Management Portal";
	public static final String AUTHORITIES = "Authorities";
	public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
	public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
	public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
	public static final String[] PUBLIC_URLS = { "/user/login", "/user/register", "/user/resetpassword/**",
			"/user/image/**", "/user/authorize" };

	private SecurityConstant() {
		throw new IllegalStateException("SecurityConstant class");
	}
}
