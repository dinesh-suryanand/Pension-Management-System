package com.cognizant.process.pension.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PensionDetailTest {

	PensionDetail pensionDetails = new PensionDetail();

	@Test
	void BankDetailBeanTest() {
		assertNotNull(pensionDetails);
	}

	@Test
	void PensionerDetailNoArgConstructorTest() {
		PensionDetail pensionDetails = new PensionDetail();
		assertThat(pensionDetails).isNotNull();

	}

	@Test
	void BankDetailAllArgConstructorTest() {
		PensionDetail pensionDetails = new PensionDetail(1L, "Pratyush", getSqlDate(getUtilDate("22-06-1999")), "PQWER12345", "family",
				40000.0);
		assertNotNull(pensionDetails);
	}

	@Test
	void BankDetailSettersTest() {
		PensionDetail pensionDetail = new PensionDetail();
		pensionDetail.setId(1L);
		pensionDetail.setName("Pratyush");
		pensionDetail.setDateOfBirth(getSqlDate(getUtilDate("22-06-1999")));
		pensionDetail.setPanNumber("POQWERT12345");
		pensionDetail.setPensiontype("family");
		pensionDetail.setPensionAmount(40000.00);
		assertNotNull(pensionDetail);
	}
	
	private static java.util.Date getUtilDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date utilDate = new java.util.Date();
		try {
			utilDate = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return utilDate;
	}
	
	private static java.sql.Date getSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
}