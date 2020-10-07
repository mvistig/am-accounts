package mvi.accounts.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import mvi.accounts.data.dao.AccountRepository;
import mvi.accounts.data.entities.Account;
import mvi.accounts.service.AbstractAccountsSpringBootTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GetAccountDataTest extends AbstractAccountsSpringBootTest {

    GetAccountData service;

    @Mock
    AccountRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void existsAccountReturnsTrueOnlyWhenDataFoundInRepository() {
        service = new GetAccountData(repository, objectMapper);

        when(repository.existsById(1L)).thenReturn(true);
        when(repository.existsById(3L)).thenReturn(false);
        when(repository.existsById(2L)).thenReturn(false);

        assertFalse(service.existsAccount(3L));
        assertFalse(service.existsAccount(2L));
        assertTrue(service.existsAccount(1L));
    }

    @Test
    void getAccountReturnsEmptyWhenRepositoryReturnEmpty() {
        service = new GetAccountData(repository, objectMapper);
        when(repository.findById(any())).thenReturn(Optional.empty());

        var actual = service.getAccount(1L);
        assertEquals(Optional.empty(), actual);
    }

    @Test
    void getAccountReturnsValidAccountFromRepositoryEntity() {
        service = new GetAccountData(repository, objectMapper);
        var expected = new Account();
        expected.setId(1L);
        expected.setPhones("[{\"phoneNumber\":\"++33 555\",\"otherInfo\":\"\"}]");
        expected.setAddress(
                "{\"street\":\"Calle Espana\",\"streetNo\":5,\"additionalInfo\":\"nimic\",\"postalCode\":\"3333\",\"city\":\"Bratislava\",\"country\":\"Bulgaria\"}");
        expected.setFirstName("Foo");
        expected.setLastName("Bar");

        when(repository.findById(1L)).thenReturn(Optional.of(expected));

        var actualOpt = service.getAccount(1L);
        assertTrue(actualOpt.isPresent());
        var actual = actualOpt.get();
        assertEquals(1L, actual.getId());
        assertEquals("Foo", actual.getFirstName());
        assertEquals("Bar", actual.getLastName());
        assertNotNull(actual.getPhones());
        assertNotNull(actual.getAddress());
        assertEquals(1, actual.getPhones().size());
        assertTrue(actual.getPhones().stream().anyMatch(phone -> "++33 555".equals(phone.getPhoneNumber())));
        assertEquals("Calle Espana", actual.getAddress().getStreet());
        assertEquals("nimic", actual.getAddress().getAdditionalInfo());
        assertEquals("3333", actual.getAddress().getPostalCode());
        assertEquals("Bratislava", actual.getAddress().getCity());
        assertEquals("Bulgaria", actual.getAddress().getCountry());
        assertEquals(5, actual.getAddress().getStreetNo());
    }

}