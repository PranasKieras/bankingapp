package com.banking.app;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BankingApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenValidRequest_whenCreateUser_thenStatus200() throws Exception {
        mvc.perform(post("/api/user")
                .content("{" +
                        "\"email\" : \"email1@domain.com\", " +
                        "\"password\" : \"password\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/user/balance")
                .header("authenticated", "true")
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
    public void givenExistingUser_whenCreateUser_thenStatus400() throws Exception {
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
                .andExpect(status().isBadRequest())
                .andExpect(content().string("a user with a given email already exists"));
    }

    @Test
    public void givenNoEmail_whenCreateUser_thenStatus400() throws Exception {
        mvc.perform(post("/api/user")
                .content("{" +
                        "\"password\" : \"password\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenBadEmailFormat_whenFetchBalance_thenStatus400() throws Exception {
        mvc.perform(get("/api/user/balance")
                .header("authenticated", "true")
                .content("{" +
                        "\"email\" : \"email\"" +
                        "\"password\" : \"password\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void givenUserExists_whenDeposit10_thenBalanceIs10() throws Exception {
        mvc.perform(post("/api/user")
                .content("{" +
                        "\"email\" : \"email4@email.com\", \n" +
                        "\"password\" : \"password\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post("/api/user/deposit")
                .header("authenticated", "true")
                .content("{" +
                        "\"email\" : \"email4@email.com\", \n" +
                        "\"password\" : \"password\", \n" +
                        "\"amount\" : 10.00 \n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/user/balance")
                .header("authenticated", "true")
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
    public void givenNoUser_whenFetchBalance_thenStatus404() throws Exception {
        mvc.perform(get("/api/user/balance")
                .header("authenticated", "true")
                .content("{" +
                        "\"email\" : \"email3@email.com\", \n" +
                        "\"password\" : \"password\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("a user with such an email was not found"));

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
                .header("authenticated", "true")
                .content("{" +
                        "\"email\" : \"email5@email.com\", \n" +
                        "\"password\" : \"password\", \n" +
                        "\"amount\" : 100.00 \n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/user/balance")
                .header("authenticated", "true")
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
                        "\"email\" : \"email6@email.com\", \n" +
                        "\"password\" : \"password\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post("/api/user/deposit")
                .header("authenticated", "true")
                .content("{" +
                        "\"email\" : \"email6@email.com\", \n" +
                        "\"password\" : \"password\", \n" +
                        "\"amount\" : 100.00 \n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post("/api/user/withdraw")
                .header("authenticated", "true")
                .content("{" +
                        "\"email\" : \"email6@email.com\", \n" +
                        "\"password\" : \"password\", \n" +
                        "\"amount\" : 90.00 \n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post("/api/user/withdraw")
                .header("authenticated", "true")
                .content("{" +
                        "\"email\" : \"email6@email.com\", \n" +
                        "\"password\" : \"password\", \n" +
                        "\"amount\" : 20.00 \n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("not enough money on account to perform this operation"));
    }

    @Test
    public void givenUserExists_whenDeposit10AndWithDraw7_accountEventsDataCorrect() throws Exception {
        mvc.perform(post("/api/user")
                .content("{" +
                        "\"email\" : \"email7@email.com\", \n" +
                        "\"password\" : \"password\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post("/api/user/deposit")
                .header("authenticated", "true")
                .content("{" +
                        "\"email\" : \"email7@email.com\", \n" +
                        "\"password\" : \"password\", \n" +
                        "\"amount\" : 10.00 \n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post("/api/user/withdraw")
                .header("authenticated", "true")
                .content("{" +
                        "\"email\" : \"email7@email.com\", \n" +
                        "\"password\" : \"password\", \n" +
                        "\"amount\" : 7.00 \n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/user/statement")
                .header("authenticated", "true")
                .content("{" +
                        "\"email\" : \"email7@email.com\", \n" +
                        "\"password\" : \"password\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"statement\":[" +
                        "{\"amount\":10.00,\"operation\":\"DEPOSIT\",\"balanceAfterOperation\":10.00}," +
                        "{\"amount\":7.00,\"operation\":\"WITHDRAWAL\",\"balanceAfterOperation\":3.00}]" +
                        "}"));


    }

}
