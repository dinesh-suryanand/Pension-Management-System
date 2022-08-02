package com.cognizant.pension.disbursement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.pension.disbursement.exception.ExceptionHandling;
import com.cognizant.pension.disbursement.exception.ProcessPensionException;
import com.cognizant.pension.disbursement.model.ProcessPensionInput;
import com.cognizant.pension.disbursement.model.ProcessPensionResponse;
import com.cognizant.pension.disbursement.rest.client.PensionerDetailClient;
import com.cognizant.pension.disbursement.service.PensionDisbursementService;



@RestController
public class PensionDisbursementController extends ExceptionHandling {

	private static final Logger logger = LoggerFactory.getLogger(PensionDisbursementController.class);

	private PensionerDetailClient pensionerDetailClient;

	private PensionDisbursementService pensionDisbursementService;

	@Autowired
	public PensionDisbursementController(PensionerDetailClient pensionerDetailClient,
			PensionDisbursementService pensionDisbrusmentService) {

		this.pensionerDetailClient = pensionerDetailClient;
		this.pensionDisbursementService = pensionDisbrusmentService;
	}
	
	//pension amount matched for the provided input will return successcode

	/**
	 * @param token
	 * @param processPensionInput
	 * @return
	 * @throws ProcessPensionException
	 */
	@PostMapping("/disbursePension")
	public ProcessPensionResponse getPensionDisbursement(@RequestHeader(name = "Authorization") String token,
			@RequestBody ProcessPensionInput processPensionInput) throws ProcessPensionException {
		logger.info("STARTED - getPensionDisbursement");
		ProcessPensionResponse processPensionResponseCode = null;
		try {
			processPensionResponseCode = pensionDisbursementService.statusCode(
					pensionerDetailClient.findByAadhaarNumber(token, processPensionInput.getAadhaarNumber()),
					processPensionInput);
		} catch (Exception e) {
			logger.error("EXCEPTION - getPensionDisbursement");
			throw new ProcessPensionException("Pension Amount is not correct");
		}
		logger.info("END - getPensionDisbursement");
		return processPensionResponseCode;

	}
}
