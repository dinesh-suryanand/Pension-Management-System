package com.cognizant.pension.management.authentication.service;

import static com.cognizant.pension.management.authentication.constant.EmailConstant.*;

import java.util.Date;
import java.util.Properties;

import com.sun.mail.smtp.SMTPTransport;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

/**
 * @author Adrish
 *
 */
@Service
public class EmailService {
	
	/** Mail New Password to the user on Password Reset
	 * @param firstName
	 * @param password
	 * @param email
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendNewPasswordEmail(String firstName, String password, String email) throws AddressException, MessagingException {
		Message message = createEmail(firstName, password, email);
		SMTPTransport smtpTransport= (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL); 
		smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
		SMTPTransport.send(message, message.getAllRecipients());
		smtpTransport.close();
	}
	
	/** Create the mail
	 * @param firstName
	 * @param password
	 * @param email
	 * @return
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private Message createEmail(String firstName, String password, String email) throws AddressException, MessagingException {
		Message message = new MimeMessage(getEmailSession());
		message.setFrom(new InternetAddress(FROM_EMAIL));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
		message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CC_EMAIL, false));
		message.setSubject(EMAIL_SUBJECT);
		message.setText("Hello "+ firstName + ", \n \n Your new account password is: "+ password + "\n\n The Support Team");
		message.setSentDate(new Date());
		message.saveChanges();
		return message;
	}
	
	/** Create the email session
	 * @return
	 */
	private Session getEmailSession() {
		Properties properties = System.getProperties();
		properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
		properties.put(SMTP_AUTH, true);
		properties.put(SMTP_PORT, DEFAULT_PORT);
		properties.put(SMTP_STARTTLS_ENABLE, true);
		properties.put(SMTP_STARTTLS_REQUIRED, true);
		return Session.getInstance(properties, null);
	}
}
