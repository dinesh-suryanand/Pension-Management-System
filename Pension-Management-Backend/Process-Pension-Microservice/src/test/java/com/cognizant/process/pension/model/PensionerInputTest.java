package com.cognizant.process.pension.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PensionerInputTest {

	PensionerInput pensionerInput = new PensionerInput();

	@Test
	void PensionerDetailBeanTest() {
		assertNotNull(pensionerInput);
	}

	@Test
	void PensionerInputNoArgConstructorTest() {
		PensionerInput pensionerInput = new PensionerInput();
		assertThat(pensionerInput).isNotNull();
	}

	@Test
	void PensionerInputAllArgConstructorTest() {
		PensionerInput pensionerInput = new PensionerInput("Pratyush", getSqlDate(getUtilDate("06-11-1999")),
				"PQWER12345", "1234567654678", "family");
		assertNotNull(pensionerInput);
	}

	@Test
	void PensionerDetailSettersTest() {
		PensionerInput pensionerDetail = new PensionerInput();
		pensionerDetail.setAadhaarNumber("1211121324343543");
		pensionerDetail.setName("Pratyush");
		pensionerDetail.setDateOfBirth(getSqlDate(getUtilDate("06-11-1999")));
		pensionerDetail.setPanNumber("POQWERT12345");
		pensionerDetail.setPensionType("family");
		assertNotNull(pensionerDetail);

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
