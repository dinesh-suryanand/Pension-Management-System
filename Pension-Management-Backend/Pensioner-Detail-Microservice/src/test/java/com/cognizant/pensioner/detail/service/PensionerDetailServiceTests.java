package com.cognizant.pensioner.detail.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.pensioner.detail.exception.PensionerDetailNotFoundException;
import com.cognizant.pensioner.detail.model.BankDetail;
import com.cognizant.pensioner.detail.model.PensionerDetail;
import com.cognizant.pensioner.detail.service.impl.PensionerDetailServiceImpl;
import com.cognizant.pensioner.detail.utility.DateUtility;


@SpringBootTest
@AutoConfigureMockMvc
class PensionerDetailServiceTests {

	@Autowired
	private PensionerDetailServiceImpl pensionerDetailService;
	

	@Test
	void testNotNullPensionDetailServiceObject() {
		assertNotNull(pensionerDetailService);
	}

	@Test
	void testPensionerDetailFunction() {
		BankDetail bankDetail = new BankDetail("SBI", "1234567743", "public");
		PensionerDetail pensionerDetail = new PensionerDetail(21L,"123471112021", "Dinesh",
				DateUtility.getSqlDate(DateUtility.getUtilDate("22-06-1999")), "PASW33334DW", 30000.0, 12000.0,
				"family", bankDetail);
		assertNotNull(bankDetail);
		assertNotNull(pensionerDetail);
	}

	@Test
	void testfindPensionerByAadhaarNumber() throws PensionerDetailNotFoundException {
		assertNotNull(pensionerDetailService.findByAadhaarNumber("123471112001"));
	}
	
	@Test
	void testGetAllPensionerDetails() {
		assertNotEquals(0, pensionerDetailService.getAllPensionerDetails().size());
	}
}
