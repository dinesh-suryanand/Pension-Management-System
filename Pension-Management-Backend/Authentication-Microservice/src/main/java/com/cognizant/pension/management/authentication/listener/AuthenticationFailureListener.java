package com.cognizant.pension.management.authentication.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.cognizant.pension.management.authentication.service.LoginAttemptService;

/**
 * @author Adrish
 *
 */
@Component
public class AuthenticationFailureListener {
	
	@Autowired
	LoginAttemptService loginAttemptService;
	
	/** Add user to local cache every time he fails to log in
	 * @param event
	 */
	@EventListener
	public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		if(principal instanceof String) {
			String username = (String) event.getAuthentication().getPrincipal();
			loginAttemptService.addUserToLoginAttemptCache(username);
		}
	}
}
