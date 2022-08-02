package com.cognizant.pensioner.detail.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.cognizant.pensioner.detail.utility.DateUtility;

@SpringBootTest
class ModelTests {
	
	@Test
	void testNoArgsBankDetailTest() {
		assertThat(new BankDetail()).isNotNull();
	}

	@Test
	void testAllArgsBankDetailTest() {
		BankDetail bankDetails = new BankDetail("SBI", "1234567890", "private") ;
		assertNotNull(bankDetails) ;
	}

	@Test
	void testSetterBankTest() {
		BankDetail bankDetail = new BankDetail();
		bankDetail.setAccountNumber("102233445566");
		bankDetail.setBankName("SBI");
		bankDetail.setBankType("public");
		assertNotNull(bankDetail) ;

	}
	
	@Test
	void SetterArgsBankDetailTest() {
		BankDetail bankDetails = new BankDetail("SBI", "1234567890", "private") ;
		assertEquals("SBI", bankDetails.getBankName()) ;
		assertEquals("1234567890", bankDetails.getAccountNumber()) ;
		assertEquals("private", bankDetails.getBankType()) ;
	}


	@Test
	void testSetterPensionerDetailTest() {
		PensionerDetail pensionerDetail = new PensionerDetail();
		pensionerDetail.setAadhaarNumber("123471112020");
		pensionerDetail.setDateOfBirth(DateUtility.getSqlDate(DateUtility.getUtilDate("22-06-1999")));
		pensionerDetail.setName("Stephen");
		pensionerDetail.setPanNumber("GTYIK7412L");
		pensionerDetail.setSalary(70000.0);
		pensionerDetail.setAllowance(12000.0);
		pensionerDetail.setPensionType("family");

		BankDetail bankDetail = new BankDetail();
		bankDetail.setAccountNumber("102233445566");
		bankDetail.setBankName("SBI");
		bankDetail.setBankType("public");

		pensionerDetail.setBankDetail(bankDetail);
		assertNotNull(pensionerDetail) ;
		
		assertEquals("123471112020",pensionerDetail.getAadhaarNumber() ) ;
		assertEquals(DateUtility.getSqlDate(DateUtility.getUtilDate("22-06-1999")), pensionerDetail.getDateOfBirth()) ;
		assertEquals("Stephen", pensionerDetail.getName()) ;
		assertEquals("GTYIK7412L", pensionerDetail.getPanNumber()) ;
		assertEquals(70000, pensionerDetail.getSalary()) ;
		assertEquals(12000, pensionerDetail.getAllowance()) ;
		assertEquals("family", pensionerDetail.getPensionType()) ;
		
		assertEquals("102233445566",bankDetail.getAccountNumber() ) ;
		assertEquals("SBI", bankDetail.getBankName()) ;
		assertEquals("public",bankDetail.getBankType() ) ;
		
	}
	
	@Test
	void testCustomHttpResponse() {
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.setHttpStatusCode(HttpStatus.OK.value());
		httpResponse.setHttpStatus(HttpStatus.OK);
		httpResponse.setReason(HttpStatus.OK.getReasonPhrase());
		httpResponse.setMessage("OK");
		assertNotNull(httpResponse);
	}
}
