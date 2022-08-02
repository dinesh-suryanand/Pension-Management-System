package com.cognizant.pension.disbursement.test.constant;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.pension.disbursement.constant.CorsFilterConstant;

@SpringBootTest
class CorsFilterConstantTest {

	@Test
	void test() {
		assertNotNull(CorsFilterConstant.ALLOWED_HEADER_JWT_TOKEN);
	}

}
