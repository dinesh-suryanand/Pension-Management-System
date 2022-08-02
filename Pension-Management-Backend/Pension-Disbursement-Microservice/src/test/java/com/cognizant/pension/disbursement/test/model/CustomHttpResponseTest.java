package com.cognizant.pension.disbursement.test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.cognizant.pension.disbursement.model.HttpResponse;

@SpringBootTest
class CustomHttpResponseTest {

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
