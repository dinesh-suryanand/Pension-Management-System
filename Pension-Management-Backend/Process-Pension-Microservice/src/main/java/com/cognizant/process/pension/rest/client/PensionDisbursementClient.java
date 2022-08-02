package com.cognizant.process.pension.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.process.pension.model.ProcessPensionInput;
import com.cognizant.process.pension.model.ProcessPensionResponse;

@FeignClient(name = "${pension.disbursement.service.name}", url = "${pension.disbursement.service.url}")
public interface PensionDisbursementClient {

	@PostMapping("/disbursePension")
	public ProcessPensionResponse getPensionDisbursement(@RequestHeader(name = "Authorization") String token,
			@RequestBody ProcessPensionInput processPensionInput);
}
