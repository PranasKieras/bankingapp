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
	public void givenValidRequest_WhenCreateUser_ThenStatus200AndBalanceIsZero() throws Exception {
		mvc.perform(post("/api/user")
				.content("{" +
						 "\"email\" : \"email1@domain.com\", " +
						"\"password\" : \"password\"" +
						"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(get("/api/user/balance")
				.content("{" +
						"\"email\" : \"email1@domain.com\", \n" +
						"\"password\" : \"password\"" +
						"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{" +
						"\"email\" : \"email1@domain.com\", \n" +
						"\"balance\" : 0.00" +
						"}"));
	}

	@Test
	public void givenExistingUser_WhenCreateUser_ThenStatus400() throws Exception {
		mvc.perform(post("/api/user")
				.content("{" +
						 "\"email\" : \"email2@domain.com\", " +
						 "\"password\" : \"password\"" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post("/api/user")
				.content("{" +
						 "\"email\" : \"email2@domain.com\", " +
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
	public void givenInvalidRequest_WhenFetchBalance_Returns400() throws Exception {
		mvc.perform(get("/api/user/balance")
				.content("{" +
						 "\"email\" : \"email\"" +
						 "\"password\" : \"password\"" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void givenNoUser_WhenFetchBalance_Returns404() throws Exception {
		mvc.perform(get("/api/user/balance")
				.content("{" +
						 "\"email\" : \"email3@email.com\", \n" +
						 "\"password\" : \"password\"" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

	@Test
	public void givenUserExists_WhenDeposit10_BalanceIs10() throws Exception {
		mvc.perform(post("/api/user")
				.content("{" +
						 "\"email\" : \"email4@email.com\", \n" +
						 "\"password\" : \"password\"" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post("/api/user/deposit")
				.content("{" +
						 "\"email\" : \"email4@email.com\", \n" +
						 "\"password\" : \"password\", \n" +
						 "\"amount\" : 10.00 \n" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(get("/api/user/balance")
				.content("{" +
						 "\"email\" : \"email4@email.com\", \n" +
						 "\"password\" : \"password\"" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{" +
										  "\"email\" : \"email4@email.com\", \n" +
										  "\"balance\" : 10.00 " +
										  "}"));


	}

	@Test
	public void givenUserExists_WhenDeposit100_BalanceIs100() throws Exception {
		mvc.perform(post("/api/user")
				.content("{" +
						 "\"email\" : \"email5@email.com\", \n" +
						 "\"password\" : \"password\"" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post("/api/user/deposit")
				.content("{" +
						 "\"email\" : \"email5@email.com\", \n" +
						 "\"password\" : \"password\", \n" +
						 "\"amount\" : 100.00 \n" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(get("/api/user/balance")
				.content("{" +
						 "\"email\" : \"email5@email.com\", \n" +
						 "\"password\" : \"password\"" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{" +
										  "\"email\" : \"email5@email.com\", \n" +
										  "\"balance\" : 100.00 " +
										  "}"));


	}

	@Test
	public void givenUserExistsAndBalance0_whenWithdraw_throwException() throws Exception {
		mvc.perform(post("/api/user")
				.content("{" +
						 "\"email\" : \"email5@email.com\", \n" +
						 "\"password\" : \"password\"" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post("/api/user/deposit")
				.content("{" +
						 "\"email\" : \"email5@email.com\", \n" +
						 "\"password\" : \"password\", \n" +
						 "\"amount\" : 100.00 \n" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post("/api/user/withdraw")
				.content("{" +
						 "\"email\" : \"email5@email.com\", \n" +
						 "\"password\" : \"password\", \n" +
						 "\"amount\" : 90.00 \n" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post("/api/user/withdraw")
				.content("{" +
						 "\"email\" : \"email5@email.com\", \n" +
						 "\"password\" : \"password\", \n" +
						 "\"amount\" : 20.00 \n" +
						 "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());



	}

}
