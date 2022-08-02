package com.cognizant.pension.management.authentication.constant;

public class EmailConstant {
	public static final String SIMPLE_MAIL_TRANSFER_PROTOCOL = "smtps";
	public static final String USERNAME = "developer.adrish@gmail.com";
	public static final String PASSWORD = "N99gmail1@";
	public static final String FROM_EMAIL = "support@adrish.com";
	public static final String CC_EMAIL = "";
	public static final String EMAIL_SUBJECT = "Adrish Pvt Ltd - New Password";
	public static final String GMAIL_SMTP_SERVER = "smtp.gmail.com";
	public static final String SMTP_HOST = "mail.smtp.host";
	public static final String SMTP_AUTH = "mail.smtp.auth";
	public static final String SMTP_PORT = "mail.smtp.port";
	public static final int DEFAULT_PORT = 465;
	public static final String SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	public static final String SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
	public static final String EMAIL_SENT = "An email with a new password was sent to: ";

	private EmailConstant() {
		throw new IllegalStateException("EmailConstant class");
	}
}
