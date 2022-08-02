package com.cognizant.pension.management.authentication.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @author Adrish
 *
 */
@Service
public class LoginAttemptService {
	private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
	private static final int ATTEMPT_INCREMENT = 1;
	private LoadingCache<String, Integer> loginAttemptCache;

	public LoginAttemptService() {
		super();
		this.loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).maximumSize(100)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}

	/** Remove user from the local cache
	 * @param username
	 */
	public void evictUserFromLoginAttemptCache(String username) {
		loginAttemptCache.invalidate(username);
	}

	/** Add user to local cache every time the user tries to log in
	 * @param username
	 */
	public void addUserToLoginAttemptCache(String username) {
		int attempts = 0;
		try {
			attempts = ATTEMPT_INCREMENT + loginAttemptCache.get(username);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		loginAttemptCache.put(username, attempts);
	}

	/** Check if the user has exceeded the maximum number of login attempts
	 * @param username
	 * @return
	 */
	public boolean hasExceededMaximumAttempts(String username) {
		try {
			return loginAttemptCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
}
