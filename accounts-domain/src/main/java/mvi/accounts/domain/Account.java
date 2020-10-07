package mvi.accounts.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class Account {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<Phone> phones;
    private Address address;
}
