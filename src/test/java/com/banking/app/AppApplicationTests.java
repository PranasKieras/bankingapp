package com.banking.app;

import com.banking.app.controller.BankingController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AppApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	BankingController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void givenUsers_WhenCreateUser_thenStatus200() throws Exception {
		mvc.perform(post("/api/user")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
