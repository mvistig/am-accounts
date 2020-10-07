package mvi.accounts.service.rules;

import mvi.accounts.data.GetAccountData;
import mvi.accounts.domain.Account;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetAccountService {

    private GetAccountData getAccountDataService;

    public GetAccountService(GetAccountData getAccountDataService) {
        this.getAccountDataService = getAccountDataService;
    }

    public boolean existsAccount(long id) {
        return getAccountDataService.existsAccount(id);
    }

    public Optional<Account> getAccount(long id) {
        return getAccountDataService.getAccount(id);

    }
}
