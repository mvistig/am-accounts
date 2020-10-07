package mvi.accounts.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import mvi.accounts.domain.Address;
import mvi.accounts.service.AbstractAccountsSpringBootTest;
import mvi.accounts.service.dto.AccountDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static mvi.accounts.service.rest.AccountTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

class AccountResourceTest extends AbstractAccountsSpringBootTest {

    @Autowired
    AccountResource resource;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("Test that get and put work correctly when used in order")
    void putThenGetShouldWorkFine() {
        final AccountDto dto = createBareboneAccountDto();
        final Address expectedAddress = createCompleteAddress();
        dto.setAddress(expectedAddress);
        dto.setPhones(createCompletePhoneList());
        var longResponseEntity = resource.putAccount(dto);

        assertNotNull(longResponseEntity, "Response from resource shouldn't be null");
        assertEquals(longResponseEntity.getStatusCode(), OK, "Response should be OK for put");
        var actualId = longResponseEntity.getBody();
        assertNotNull(actualId, "Id from Put Should be not null");

        // now recover data with get
        var responseEntity = resource.getAccount(actualId);

        assertNotNull(responseEntity, "Account from resource shouldn't be null");
        assertEquals(OK, responseEntity.getStatusCode());
        final var actualAccount = responseEntity.getBody();
        assertNotNull(actualAccount);
        assertAll(
                () -> assertEquals(dto.getFirstName(), actualAccount.getFirstName()),
                () -> assertEquals(dto.getLastName(), actualAccount.getLastName()),
                () -> assertNotNull(actualAccount.getAddress()),
                () -> assertEquals(expectedAddress, actualAccount.getAddress()),
                () -> assertNotNull(actualAccount.getPhones()),
                () -> assertEquals(dto.getPhones().size(), actualAccount.getPhones().size()),
                () -> assertIterableEquals(dto.getPhones(), actualAccount.getPhones())
        );
    }

    @Test
    @DisplayName("Test that you can delete correctly an order that you just added")
    void putThenGetThenDeleteThenGet() {
        final AccountDto dto = createBareboneAccountDto();
        final Address expectedAddress = createCompleteAddress();
        dto.setAddress(expectedAddress);
        dto.setPhones(createCompletePhoneList());
        var longResponseEntity = resource.putAccount(dto);

        assertNotNull(longResponseEntity, "Response from resource shouldn't be null");
        assertEquals(longResponseEntity.getStatusCode(), OK, "Response should be OK for put");
        var actualId = longResponseEntity.getBody();
        assertNotNull(actualId, "Id from Put Should be not null");

        // now recover data with get
        var responseEntity = resource.getAccount(actualId);

        assertNotNull(responseEntity, "Account from resource shouldn't be null");
        assertEquals(OK, responseEntity.getStatusCode());
        final var actualAccount = responseEntity.getBody();
        assertNotNull(actualAccount);

        var deleteResponseEntity = resource.deleteAccount(actualId);
        assertEquals(ACCEPTED, deleteResponseEntity.getStatusCode());

        assertThrows(NoSuchElementException.class, () -> resource.getAccount(actualId));
    }

}