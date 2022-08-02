package com.cognizant.process.pension.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.process.pension.exception.ExceptionHandling;
import com.cognizant.process.pension.exception.ProcessPensionException;
import com.cognizant.process.pension.model.PensionDetail;
import com.cognizant.process.pension.model.PensionerDetail;
import com.cognizant.process.pension.model.PensionerInput;
import com.cognizant.process.pension.model.ProcessPensionInput;
import com.cognizant.process.pension.model.ProcessPensionResponse;
import com.cognizant.process.pension.rest.client.PensionDisbursementClient;
import com.cognizant.process.pension.rest.client.PensionerDetailClient;
import com.cognizant.process.pension.service.ProcessPensionService;

@RestController
@RequestMapping("/processPension")
public class ProcessPensionController extends ExceptionHandling {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessPensionController.class);
	private PensionerDetailClient pensionerDetailClient;
	private ProcessPensionService processPensionService;
	private PensionDisbursementClient pensionDisbursementClient;

	@Autowired
	public ProcessPensionController(PensionerDetailClient pensionerDetailClient,
			PensionDisbursementClient pensionDisbursementClient, ProcessPensionService processPensionService) {
		this.pensionerDetailClient = pensionerDetailClient;
		this.pensionDisbursementClient = pensionDisbursementClient;
		this.processPensionService = processPensionService;
	}

	//getting all details from pensioner details microservice
	/**
	 * @param token
	 * @return
	 */
	@GetMapping("/details")
	public List<PensionerDetail> allDetail(@RequestHeader("Authorization") String token) {
		LOGGER.info("STARTED - allDetail");
		return pensionerDetailClient.getAllPensionerDetails(token);
	}

	//generating pension detail with pension amount for given user input
	/**
	 * @param token
	 * @param pensionerInput
	 * @return
	 * @throws ProcessPensionException
	 */
	@PostMapping("/pensionerInput")
	public PensionDetail getPensionDetail(@RequestHeader(name = "Authorization") String token,
			@RequestBody PensionerInput pensionerInput) throws ProcessPensionException {
		LOGGER.info("STARTED - allDetail");
		PensionDetail pensionDetail = null;
		try {
			pensionDetail = processPensionService.getPensionDetail(
					pensionerDetailClient.findByAadhaarNumber(token, pensionerInput.getAadhaarNumber()));

		} catch (Exception e) {
			LOGGER.error("EXCEPTION - allDetail");
			throw new ProcessPensionException("Pensioner Detail not correct");
		}
		LOGGER.info("END - allDetail");
		return processPensionService.savePensionDetail(pensionDetail);

	}

	
	//success code in case of valid pension amount 
	/**
	 * @param token
	 * @param processPensionInput
	 * @return
	 */
	@PostMapping
	public ProcessPensionResponse getStatusCode(@RequestHeader(name = "Authorization") String token,
			@RequestBody ProcessPensionInput processPensionInput) {
		LOGGER.info("STARTED - getStatusCode");
		LOGGER.info("END - getStatusCode");
		return pensionDisbursementClient.getPensionDisbursement(token, processPensionInput);
	}
}
