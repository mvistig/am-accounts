package mvi.accounts.data;

import mvi.accounts.data.dao.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteAccountData {
    final AccountRepository repository;


    public DeleteAccountData(AccountRepository repository) {this.repository = repository;}

    public void deleteAccount(long id) {
        repository.deleteById(id);
    }
}
