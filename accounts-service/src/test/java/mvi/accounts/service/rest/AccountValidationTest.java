package mvi.accounts.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static mvi.accounts.service.rest.AccountTestUtils.createCompleteAccountDto;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountValidationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testResourceGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account/1")).andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    @DisplayName("When inserting an account without address, respond with BAD_REQUEST")
    void testInvalidWithoutAddress() {
        final var dto = createCompleteAccountDto();
        dto.setAddress(null);
        mockMvc.perform(MockMvcRequestBuilders
                                .put("/account/")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(dto)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    @DisplayName("When inserting an account without phone list, respond with BAD_REQUEST")
    void testInvalidWithoutPhones() {
        final var dto = createCompleteAccountDto();
        dto.setAddress(null);
        mockMvc.perform(MockMvcRequestBuilders
                                .put("/account")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(dto)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    @DisplayName("When inserting an account without firstname, respond with BAD_REQUEST")
    void testInvalidWithoutFirstName() {
        final var dto = createCompleteAccountDto();
        dto.setFirstName(null);
        mockMvc.perform(MockMvcRequestBuilders
                                .put("/account")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(dto)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    @DisplayName("When inserting an account without lasttname, respond with BAD_REQUEST")
    void testInvalidWithoutLastName() {
        final var dto = createCompleteAccountDto();
        dto.setLastName(null);
        mockMvc.perform(MockMvcRequestBuilders
                                .put("/account")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(dto)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
