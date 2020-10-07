package mvi.accounts.data.dao;

import mvi.accounts.data.entities.Account;
import mvi.accounts.service.AbstractAccountsSpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountRepositoryTest extends AbstractAccountsSpringBootTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void accountRepositorySaveAndFindShouldWork() {
        var account = new Account();
        account.setLastName("Bar");
        account.setFirstName("Foo");

        Account actual = accountRepository.save(account);
        Assertions.assertNotNull(actual.getId());

        var loaded = accountRepository.findById(actual.getId());
        Assertions.assertTrue(loaded.isPresent());
        Assertions.assertEquals(account.getFirstName(), loaded.get().getFirstName());
        Assertions.assertEquals(account.getLastName(), loaded.get().getLastName());

    }

}
