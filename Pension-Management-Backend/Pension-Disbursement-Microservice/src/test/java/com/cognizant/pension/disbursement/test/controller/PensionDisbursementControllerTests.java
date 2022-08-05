package com.cognizant.pension.disbursement.test.controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.cognizant.pension.disbursement.controller.PensionDisbursementController;
import com.cognizant.pension.disbursement.model.ProcessPensionInput;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PensionDisbursementControllerTests {

	private static String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJVc2VyIE1hbmFnZW1lbnQgUG9ydGFsIiwic3ViIjoicmljayIsImlzcyI6IkFkcmlzaCBCb3NlIFB2dCIsIkF1dGhvcml0aWVzIjpbInVzZXI6cmVhZCJdLCJleHAiOjE2NjAxMDM4MDUsImlhdCI6MTY1OTY3MTgwNX0.Vd8EIXrTg5ZB1qVOTtazf32gSn_JnQlYewi0Vy0dsQr9Hed6thC-F5tsnT2yBp35mNP8yjbZyjtbJ3K5P_YKdw";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PensionDisbursementController pensionDisbursementController;

	@Test
	void contextLoadsTestSuccess() {
		assertNotNull(pensionDisbursementController);
	}


	@Test
	void disbursePensionTestSuccess() throws Exception {
		ProcessPensionInput processPensionInput = new ProcessPensionInput("123471112009", 57500.0, 500.0);
		ResultActions actions = mockMvc.perform(post("/disbursePension").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(processPensionInput)).header("Authorization", "Bearer" + token));
		actions.andExpect(status().isOk());
	}

	@Test
	void disbursePensionTestFail() throws Exception {
		ProcessPensionInput processPensionInput = new ProcessPensionInput("123471112009", 57500.0, 500.0);
		ResultActions actions = mockMvc.perform(get("/disbursePension").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(processPensionInput)).header("Authorization", "Bearer" + token));
		actions.andExpect(status().isInternalServerError());

	}

	public static String asJsonString(ProcessPensionInput processPensionInput) {
		try {
			return new ObjectMapper().writeValueAsString(processPensionInput);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

