package com.cognizant.pension.disbursement.test.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.pension.disbursement.model.PensionerDetail;
import com.cognizant.pension.disbursement.model.ProcessPensionInput;

@SpringBootTest
class ProcessPensionInputTest {
	
	ProcessPensionInput processPensionInput = new ProcessPensionInput();

	@Test
	void ProcessPensionInputBeanTest() {
		assertNotNull( processPensionInput);
	}


	@Test
	void ProcessPensionInputNoArgConstructorTest() {
		PensionerDetail pensionerDetails = new PensionerDetail();
		assertThat(pensionerDetails).isNotNull();

	}

	@Test
	void ProcessPensionInputAllArgConstructorTest() {
		ProcessPensionInput processPensionInput = new ProcessPensionInput("123459993245", 3000.0, 100.0);
		assertNotNull(processPensionInput);
	}

	@Test
	void PensionerDetailSettersTest() {
		ProcessPensionInput processPensionInput = new ProcessPensionInput();
		processPensionInput.setAadhaarNumber("123456789012");
		processPensionInput.setPensionAmount(10000.0);
		processPensionInput.setBankServiceCharge(200.0);
		assertNotNull(processPensionInput);

	}
}
