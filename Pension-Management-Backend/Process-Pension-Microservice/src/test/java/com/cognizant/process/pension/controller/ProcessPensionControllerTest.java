package com.cognizant.process.pension.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.cognizant.process.pension.model.PensionerInput;
import com.cognizant.process.pension.model.ProcessPensionInput;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class ProcessPensionControllerTest {

	@Autowired
	ProcessPensionController processpensioncontroller;

	@Autowired
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	private static String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJVc2VyIE1hbmFnZW1lbnQgUG9ydGFsIiwic3ViIjoicmljayIsImlzcyI6IkFkcmlzaCBCb3NlIFB2dCIsIkF1dGhvcml0aWVzIjpbInVzZXI6cmVhZCJdLCJleHAiOjE2NjAxMDM4MDUsImlhdCI6MTY1OTY3MTgwNX0.Vd8EIXrTg5ZB1qVOTtazf32gSn_JnQlYewi0Vy0dsQr9Hed6thC-F5tsnT2yBp35mNP8yjbZyjtbJ3K5P_YKdw";

	@Test
	void contextLoads() {
		assertNotNull(processpensioncontroller);
	}

	@Test
	void getStatusCodeTestSuccess() throws Exception {
		ProcessPensionInput processPensionInput = new ProcessPensionInput("123471112009", 57500.0, 500.0);
		ResultActions actions = mockMvc.perform(post("/processPension").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonStringProcessPensionInput(processPensionInput))
				.header("Authorization", "Bearer" + token));
		actions.andExpect(status().isOk());
	}

	@Test
	void getAllPensionersTestSuccess() throws Exception {
		ResultActions actions = mockMvc
				.perform(get("/processPension/details").header("Authorization", "Bearer" + token));
		actions.andExpect(status().isOk());
	}

	@Test
	void getPensionDetailTestSuccess() throws Exception {
		PensionerInput pensionerInput = new PensionerInput("Michael", getSqlDate(getUtilDate("07-03-1990")),
				"TGOOFG2WP", "123471112009", "self");
		ResultActions actions = mockMvc
				.perform(post("/processPension/pensionerInput").contentType(MediaType.APPLICATION_JSON)
						.content(asJsonStringPensionerInput(pensionerInput)).header("Authorization", "Bearer" + token));
		actions.andExpect(status().isOk());
	}

	private static String asJsonStringPensionerInput(PensionerInput pensionerInput) {
		try {
			return new ObjectMapper().writeValueAsString(pensionerInput);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String asJsonStringProcessPensionInput(ProcessPensionInput pensionerInput) {
		try {
			return new ObjectMapper().writeValueAsString(pensionerInput);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
