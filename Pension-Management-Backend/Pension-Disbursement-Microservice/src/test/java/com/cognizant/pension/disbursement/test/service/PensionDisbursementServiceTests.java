package com.cognizant.pension.disbursement.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.pension.disbursement.service.PensionDisbursementService;


@SpringBootTest
@RunWith(SpringRunner.class)
class PensionDisbursementServiceTests {

	@Autowired
	private PensionDisbursementService pensionDisbursementService;

	@Test
	void contextLoads() {
		assertNotNull(pensionDisbursementService);
	}

	@Test
	void checkPrivateBankTestSuccess() throws Exception {
		assertEquals(true, pensionDisbursementService.inputBankCharge("private", 550.0));

	}
	@Test
	void checkPublicBankTestSuccess() throws Exception {
		assertEquals(true, pensionDisbursementService.inputBankCharge("public", 500.0));

	}
	
	@Test
	void checkPrivateBankTestFailure() throws Exception {
		assertNotEquals("false", pensionDisbursementService.inputBankCharge("private", 500.0));

	}
	@Test
	void checkPublicBankTestFailure() throws Exception {
		assertNotEquals("false", pensionDisbursementService.inputBankCharge("public", 550.0));

	}
}
