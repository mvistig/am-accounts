package mvi.accounts.service.rules;

import mvi.accounts.data.GetAccountData;
import mvi.accounts.domain.Account;
import mvi.accounts.domain.Phone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAccountServiceTest {

    //CUT
    private GetAccountService service;
    @Mock
    private GetAccountData getAccountData;

    @BeforeEach
    void setUp() {
        this.service = new GetAccountService(getAccountData);
    }

    @Test
    void existsAccountReturnsFalse() {
        when(getAccountData.existsAccount(anyLong())).thenReturn(false);
        assertFalse(service.existsAccount(2));
        assertFalse(service.existsAccount(5));
    }

    @Test
    void existsAccountReturnsTrueOnlyForExistingAccounts() {
        when(getAccountData.existsAccount(2)).thenReturn(false);
        when(getAccountData.existsAccount(5)).thenReturn(true);
        assertFalse(service.existsAccount(2));
        assertTrue(service.existsAccount(5));
    }

    @Test
    void getAccountReturnsEmptyWhenNotFound() {
        when(getAccountData.getAccount(2)).thenReturn(Optional.empty());
        when(getAccountData.getAccount(4)).thenReturn(Optional.empty());
        assertTrue(service.getAccount(2).isEmpty());
        assertTrue(service.getAccount(4).isEmpty());
    }

    @Test
    void getAccountReturnsNonEmptyAccountOnlyWhenFound() {
        when(getAccountData.getAccount(2)).thenReturn(Optional.empty());
        when(getAccountData.getAccount(4)).thenReturn(Optional.of(new Account()));
        assertTrue(service.getAccount(2).isEmpty());
        assertTrue(service.getAccount(4).isPresent());
    }

    @Test
    void getAccountReturnsValidAccount() {
        //Given
        var account = new Account();
        account.setPhones(Set.of(new Phone("+33 555 1111", "test"),
                                 new Phone("++49 547 6666", "dont call this number")));
        account.setFirstName("Joe");
        account.setLastName("Foo");
        account.setAddress(null);


        when(getAccountData.getAccount(4)).thenReturn(Optional.of(account));
        var actualAccount = service.getAccount(4);
        assertTrue(actualAccount.isPresent());
        assertEquals("Joe", actualAccount.get().getFirstName());
        assertEquals("Foo", actualAccount.get().getLastName());
        assertNull(actualAccount.get().getAddress());
        assertTrue(actualAccount.get().getPhones().stream().anyMatch(phone -> "test".equals(phone.getOtherInfo())));
    }
}