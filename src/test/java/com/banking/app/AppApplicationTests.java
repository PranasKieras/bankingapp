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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
	public void givenValidRequest_WhenCreateUser_ThenStatus200AndUserExists() throws Exception {
		mvc.perform(post("/api/user")
				.content("{" +
						 "\"email\" : \"name@domain.com\", " +
						"\"password\" : \"password\"" +
						"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(get("/api/user")
				.content("{" +
						"\"email\" : \"name@domain.com\"" +
						"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{" +
						"\"email\" : \"name@domain.com\", \n" +
						"\"balance\" : 0.00" +
						"}"));
	}

	@Test
	public void givenExistingUser_WhenCreateUser_ThenStatus400() throws Exception {
		mvc.perform(post("/api/user")
				.content("{" +
						 "\"email\" : \"name2@domain.com\", " +
						 "\"password\" : \"password\"" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post("/api/user")
				.content("{" +
						 "\"email\" : \"name2@domain.com\", " +
						 "\"password\" : \"password\"" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}


	@Test
	public void givenInvalidRequest_WhenCreateUser_Returns400() throws Exception {
		mvc.perform(post("/api/user")
				.content("{" +
						 "\"password\" : \"password\"" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void givenInvalidRequest_WhenFetchUser_Returns400() throws Exception {
		mvc.perform(get("/api/user")
				.content("{" +
						 "\"email\" : \"email\"" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void givenNoUser_WhenFetchUser_Returns404() throws Exception {
		mvc.perform(get("/api/user")
				.content("{" +
						 "\"email\" : \"email@email.com\"" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

}
