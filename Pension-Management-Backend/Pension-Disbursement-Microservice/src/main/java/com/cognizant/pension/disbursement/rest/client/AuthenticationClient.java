package com.cognizant.pension.disbursement.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${authentication.service.name}", url = "${authentication.service.url}")
public interface AuthenticationClient {
	
	// validating jwt token with authorization microservice
	@GetMapping("/authorize")
	public Boolean authorization(@RequestHeader("Authorization") String inputToken);
}
