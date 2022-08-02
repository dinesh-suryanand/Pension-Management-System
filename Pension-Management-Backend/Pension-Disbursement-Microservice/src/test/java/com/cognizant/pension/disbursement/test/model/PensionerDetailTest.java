package com.cognizant.pension.disbursement.test.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.pension.disbursement.model.BankDetail;
import com.cognizant.pension.disbursement.model.PensionerDetail;

@SpringBootTest
class PensionerDetailTest {

	PensionerDetail pensionerDetails = new PensionerDetail();
	BankDetail bankDetail = new BankDetail();

	@Test
	void PensionerDetailBeanTest() {
		assertNotNull(pensionerDetails);
	}

	@Test
	void BankDetailBeanTest() {
		assertNotNull(bankDetail);
	}

	@Test
	void PensionerDetailNoArgConstructorTest() {
		PensionerDetail pensionerDetails = new PensionerDetail();
		assertThat(pensionerDetails).isNotNull();

	}

	@Test
	void PensionerDetailAllArgConstructorTest() {
		PensionerDetail pensionerDetails = new PensionerDetail("12904284925403", "Pratyush",
				getSqlDate(getUtilDate("06-11-1999")), "PQWER12345", 40000.00, 12000.00, "family", bankDetail);
		assertNotNull(pensionerDetails);
	}

	@Test
	void PensionerDetailSettersTest() {
		PensionerDetail pensionerDetail = new PensionerDetail();
		pensionerDetail.setAadhaarNumber("1211121324343543");
		pensionerDetail.setName("Pratyush");
		pensionerDetail.setDateOfBirth(getSqlDate(getUtilDate("06-11-1999")));
		pensionerDetail.setPanNumber("POQWERT12345");
		pensionerDetail.setSalary(40000.00);
		pensionerDetail.setAllowance(12000.00);
		pensionerDetail.setPensionType("family");
		pensionerDetail.setBankDetail(bankDetail);
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
