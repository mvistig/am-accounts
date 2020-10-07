package mvi.accounts.service.dto;

import lombok.Data;
import mvi.accounts.domain.Account;
import mvi.accounts.domain.Address;
import mvi.accounts.domain.Phone;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
public class AccountDto {
    @NotEmpty(message = "firstName must not be empty")
    private String firstName;
    @NotEmpty(message = "lastName must not be empty")
    private String lastName;
    @NotEmpty(message = "phone list must not be empty")
    private List<Phone> phones;
    @NotNull(message = "address can't be null")
    private Address address;

    public Account toEntity() {
        final Account account = new Account();
        account.setFirstName(this.firstName);
        account.setLastName(this.lastName);
        account.setPhones(Set.copyOf(this.phones));
        account.setAddress(address);
        return account;
    }
}
