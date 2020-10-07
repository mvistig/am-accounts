package mvi.accounts.service.rules;

import mvi.accounts.data.DeleteAccountData;
import org.springframework.stereotype.Component;

@Component
public class DeleteAccountService {

    private final DeleteAccountData deleteAccountData;

    public DeleteAccountService(DeleteAccountData deleteAccountData) {this.deleteAccountData = deleteAccountData;}

    public void deleteAccount(long id) {
        deleteAccountData.deleteAccount(id);
    }
}
