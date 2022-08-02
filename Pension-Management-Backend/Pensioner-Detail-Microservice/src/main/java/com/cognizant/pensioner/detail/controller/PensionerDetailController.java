package com.cognizant.pensioner.detail.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.pensioner.detail.exception.ExceptionHandling;
import com.cognizant.pensioner.detail.exception.PensionerDetailNotFoundException;
import com.cognizant.pensioner.detail.exception.ResourceNotFoundException;
import com.cognizant.pensioner.detail.model.PensionerDetail;
import com.cognizant.pensioner.detail.rest.client.AuthenticationClient;
import com.cognizant.pensioner.detail.service.PensionerDetailService;

@RestController
@RequestMapping("/pensionerDetail")
public class PensionerDetailController extends ExceptionHandling {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private AuthenticationClient authenticationClient;
	
	private PensionerDetailService pensionerDetailService;

	/**
	 * @param pensionerDetailService
	 * @param authenticationClient
	 */
	@Autowired
	public PensionerDetailController(PensionerDetailService pensionerDetailService, AuthenticationClient authenticationClient) {
		this.pensionerDetailService = pensionerDetailService;
		this.authenticationClient = authenticationClient;
	}

	/**
	 * @param token
	 * @param aadhaarNumber
	 * @return
	 * @throws ResourceNotFoundException
	 * @throws PensionerDetailNotFoundException
	 */
	@GetMapping("/{aadhaarNumber}")
	public PensionerDetail findByAadhaarNumber(@RequestHeader("Authorization") String token,
			@PathVariable String aadhaarNumber) throws ResourceNotFoundException, PensionerDetailNotFoundException  {
		logger.info("STARTED - findByAadhaarNumber");

		if (authenticationClient.authorization(token)) {
			PensionerDetail pensionerDetail = pensionerDetailService.findByAadhaarNumber(aadhaarNumber);
			logger.info("END - findByAadhaarNumber");
			return pensionerDetail;
		} else {
			logger.error("EXCEPTION - findByAadhaarNumber");
			throw new ResourceNotFoundException("Token is not valid");
		}
	}

	// all pensioner details

	/**
	 * @param token
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/allDetails")
	public List<PensionerDetail> getAllPensionerDetails(@RequestHeader("Authorization") String token) throws ResourceNotFoundException {
		logger.info("STARTED - getAllPensionerDetails");
		if (authenticationClient.authorization(token)) {
			return pensionerDetailService.getAllPensionerDetails();
		}
		else {
			logger.error("EXCEPTION - getAllPensionerDetails");
			throw new ResourceNotFoundException("Token is not valid");
		}
	}
}
