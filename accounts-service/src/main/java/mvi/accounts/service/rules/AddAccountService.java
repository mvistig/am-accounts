package mvi.accounts.service.rules;

import mvi.accounts.data.CreateAccountData;
import mvi.accounts.service.dto.AccountDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddAccountService {

    private final CreateAccountData createAccountData;

    public AddAccountService(CreateAccountData createAccountData) {
        this.createAccountData = createAccountData;
    }

    public Optional<Long> addAccount(AccountDto account) {
        return createAccountData.addAccount(account.toEntity());
    }
}
