package com.cognizant.pensioner.detail.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@SpringBootTest
@AutoConfigureMockMvc
class PensionerDetailControllerTests {

	private static String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJVc2VyIE1hbmFnZW1lbnQgUG9ydGFsIiwic3ViIjoicmljayIsImlzcyI6IkFkcmlzaCBCb3NlIFB2dCIsIkF1dGhvcml0aWVzIjpbInVzZXI6cmVhZCJdLCJleHAiOjE2NTk4NTA3OTAsImlhdCI6MTY1OTQxODc5MH0.HlHENXCwzH_eUCeXh8ZL4VAorrwdv4TKwvPXgZS2NS_IBPThfFjusG-yrMkgeGBtADzwPN9PK9JCyvmzG5FtIw";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PensionerDetailController pensionerDetailController;

	@Test
	void contextLoadsTestSuccess() {
		assertNotNull(pensionerDetailController);
	}

	@Test
	void getPensionerByAadhaarTestSuccess() throws Exception {
		String aadhaarNumber = "123471112002";
		ResultActions actions = mockMvc.perform(get("/pensionerDetail/"+aadhaarNumber).header("Authorization", "Bearer" + token));
		actions.andExpect(status().isOk());
	}
	
	@Test
	void getAllPensionersTestSuccess() throws Exception {
		ResultActions actions = mockMvc.perform(get("/pensionerDetail/allDetails").header("Authorization", "Bearer" + token));
		actions.andExpect(status().isOk());
	}
}
