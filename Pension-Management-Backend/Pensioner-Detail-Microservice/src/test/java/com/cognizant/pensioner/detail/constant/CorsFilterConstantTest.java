package com.cognizant.pensioner.detail.constant;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CorsFilterConstantTest {

	@Test
	void test() {
		assertNotNull(CorsFilterConstant.ALLOWED_HEADER_JWT_TOKEN);
	}

}
