package com.cognizant.pension.management.authentication.constant;

public class UserServiceImplConstant {

	public static final String RETURNING_USER_FOUND_BY_USERNAME_S = "Returning user found by username: %s";

	public static final String USER_NOT_FOUND_BY_USERNAME_S = "User not found by username: %s";

	public static final String NO_USER_FOUND_BY_USERNAME = "No user found by username: ";

	public static final String NO_USER_FOUND_BY_EMAIL = "No user found by email: ";

	public static final String EMAIL_ALREADY_EXISTS = "Email already exists";

	public static final String USERNAME_ALREADY_EXISTS = "Username already exists";

	private UserServiceImplConstant() {
		throw new IllegalStateException("UserServiceImplConstant class");
	}
}
