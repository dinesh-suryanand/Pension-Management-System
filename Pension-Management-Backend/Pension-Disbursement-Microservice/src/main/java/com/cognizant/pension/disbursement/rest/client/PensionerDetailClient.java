package com.cognizant.pension.disbursement.rest.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.pension.disbursement.model.PensionerDetail;

@FeignClient(name = "${pensioner.detail.service.name}", url = "${pensioner.detail.service.url}")
public interface PensionerDetailClient {

	@GetMapping("/pensionerDetail/{aadhaarNumber}")
	public PensionerDetail findByAadhaarNumber(@RequestHeader("Authorization") String token,
			@PathVariable String aadhaarNumber);

	@GetMapping("/pensionerDetail/allDetails")
	public List<PensionerDetail> getAllPensionerDetails(@RequestHeader("Authorization") String token);
}
