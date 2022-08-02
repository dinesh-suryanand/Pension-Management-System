package com.cognizant.pension.management.authentication.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.cognizant.pension.management.authentication.domain.UserPrincipal;
import com.cognizant.pension.management.authentication.service.LoginAttemptService;

/**
 * @author Adrish
 *
 */
@Component
public class AuthenticationSuccessListener {

	@Autowired
	LoginAttemptService loginAttemptService;
	
	/** Remove user from local cache if he logs in successfully
	 * @param event
	 */
	@EventListener
	public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		if(principal instanceof UserPrincipal) {
			UserPrincipal user = (UserPrincipal) event.getAuthentication().getPrincipal();
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
		}
	}
}
