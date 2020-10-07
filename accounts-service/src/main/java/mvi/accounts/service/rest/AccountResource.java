package mvi.accounts.service.rest;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import mvi.accounts.domain.Account;
import mvi.accounts.domain.Address;
import mvi.accounts.service.dto.AccountDto;
import mvi.accounts.service.rules.AddAccountService;
import mvi.accounts.service.rules.DeleteAccountService;
import mvi.accounts.service.rules.GetAccountService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AccountResource {

    private final GetAccountService getAccountService;
    private final AddAccountService addAccountService;
    private final DeleteAccountService deleteAccountService;

    public AccountResource(GetAccountService getAccountService,
                           AddAccountService addAccountService,
                           DeleteAccountService deleteAccountService) {
        this.getAccountService = getAccountService;
        this.addAccountService = addAccountService;
        this.deleteAccountService = deleteAccountService;
    }

    @GetMapping(value = "account/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, response = Account.class, message = "OK"),
            @ApiResponse(code = 204, response = Account.class, message = "No content found")
    })
    public ResponseEntity<Account> getAccount(@NonNull @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(getAccountService.getAccount(id).orElseThrow());
    }

    @PutMapping(value = "account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> putAccount(@Valid @NonNull @RequestBody AccountDto account) {
        var id = addAccountService.addAccount(account);
        return ResponseEntity.ok(id.orElseThrow());
    }

    @DeleteMapping(value = "account/{id}")
    public ResponseEntity<Void> deleteAccount(@NonNull @PathVariable(name = "id") long id) {
        deleteAccountService.deleteAccount(id);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping(value = "account/{id}/address")
    public ResponseEntity<Void> patchAccountChangeAddress(@NonNull @PathVariable(name = "id") long id, Address address) {
        //fixme to do
        return ResponseEntity.accepted().build();
    }
}
