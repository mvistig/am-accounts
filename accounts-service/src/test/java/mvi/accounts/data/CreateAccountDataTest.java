package mvi.accounts.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mvi.accounts.data.dao.AccountRepository;
import mvi.accounts.domain.Account;
import mvi.accounts.domain.Address;
import mvi.accounts.domain.Phone;
import mvi.accounts.domain.exceptions.AccountsRuntimeException;
import mvi.accounts.service.AbstractAccountsSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CreateAccountDataTest extends AbstractAccountsSpringBootTest {

    @Mock
    private AccountRepository accountRepository;

    @Autowired
    ObjectMapper objectMapper;

    //CUT
    private CreateAccountData service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addAccountWillCallRepositoryWithAnEntityAccount() throws JsonProcessingException {
        //Given
        service = new CreateAccountData(accountRepository, objectMapper);
        var address = new Address();
        address.setCity("Murcia");
        address.setCountry("Spain");
        address.setPostalCode("30080");
        address.setStreet("Plaza Espana 3");
        address.setStreetNo(3);

        final var account = new Account();
        account.setId(1L);
        account.setFirstName("Foo");
        account.setLastName("Bar");
        account.setAddress(address);
        account.setPhones(Set.of(new Phone("++49887555", ""),
                                 new Phone("++47 8888997", "Test")));

        //WHEN
        var accountCaptor = ArgumentCaptor.forClass(mvi.accounts.data.entities.Account.class);
        when(accountRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        var aLong = service.addAccount(account);

        //VERIFY
        verify(accountRepository).save(accountCaptor.capture());
        var actual = accountCaptor.getValue();
        assertNotNull(actual);
        assertEquals(account.getFirstName(), actual.getFirstName(), "First Name doesn't match");
        assertEquals(account.getLastName(), actual.getLastName(), "Last Name doesn't match");
        assertTrue(actual.getAddress().contains("Plaza Espana 3"));
        var actualAddress = objectMapper.readValue(actual.getAddress(), Address.class);
        assertEquals("30080", actualAddress.getPostalCode());
        assertEquals("Murcia", actualAddress.getCity());
        var actualPhones = objectMapper.readValue(actual.getPhones(), new TypeReference<Set<Phone>>() {});
        assertTrue(actualPhones.stream().anyMatch(phone -> "Test".equals(phone.getOtherInfo())));
        assertEquals(2, actualPhones.size());
    }

    @Test
    void addAccountWillFailIfAddressCantBeParsed() throws JsonProcessingException {
        //GIVEN
        objectMapper = mock(ObjectMapper.class);
        service = new CreateAccountData(accountRepository, objectMapper);

        final var account = new Account();
        account.setId(1L);
        account.setFirstName("Foo");
        account.setLastName("Bar");
        account.setAddress(null);
        account.setPhones(Set.of(new Phone("++49887555", ""),
                                 new Phone("++47 8888997", "Test")));

        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        var are = assertThrows(AccountsRuntimeException.class, () -> service.addAccount(account));

        assertNotNull(are);
        assertEquals("JSON_WRITE", are.getScope());
        assertEquals("PERSISTENCE", are.getOrigin());
        assertTrue(are.getMessage().startsWith("Couldn't parse Address as String"));
    }

    @Test
    void addAccountWillFailIfPhoneListCantBeParsed() throws JsonProcessingException {
        //GIVEN
        objectMapper = mock(ObjectMapper.class);
        service = new CreateAccountData(accountRepository, objectMapper);
        var address = new Address();
        address.setCity("Murcia");
        address.setCountry("Spain");
        address.setPostalCode("30080");
        address.setStreet("Plaza Espana 3");
        final var account = new Account();
        account.setId(1L);
        account.setFirstName("Foo");
        account.setLastName("Bar");
        account.setAddress(address);
        account.setPhones(null);

        when(objectMapper.writeValueAsString(address)).thenReturn("Test");
        when(objectMapper.writeValueAsString(null)).thenThrow(JsonProcessingException.class);
        var are = assertThrows(AccountsRuntimeException.class, () -> service.addAccount(account));

        assertNotNull(are);
        assertEquals("JSON_WRITE", are.getScope());
        assertEquals("PERSISTENCE", are.getOrigin());
        assertTrue(are.getMessage().startsWith("Couldn't parse Phones as String"));
    }
}