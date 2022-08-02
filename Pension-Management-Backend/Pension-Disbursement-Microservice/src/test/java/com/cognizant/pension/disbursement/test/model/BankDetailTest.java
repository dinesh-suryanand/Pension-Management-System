package com.cognizant.pension.disbursement.test.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.pension.disbursement.model.BankDetail;


@SpringBootTest
class BankDetailTest {

	BankDetail bankDetail = new BankDetail();

	@Test
	void BankDetailBeanTest() {
		assertNotNull(bankDetail);
	}

	@Test
	void BankDetailNoArgConstructorTest() {
		BankDetail bankDetail = new BankDetail();
		assertThat(bankDetail).isNotNull();

	}

	@Test
	void BankDetailAllArgConstructorTest() {
		BankDetail bankDetail = new BankDetail("SBI", "123456789009", "private");
		assertNotNull(bankDetail);
	}

	@Test
	void BankDetailSettersTest() {
		BankDetail bankDetail = new BankDetail();
		bankDetail.setAccountNumber("1111111111");
		bankDetail.setBankName("SBI");
		bankDetail.setBankType("public");
		assertNotNull(bankDetail);
	}

}
