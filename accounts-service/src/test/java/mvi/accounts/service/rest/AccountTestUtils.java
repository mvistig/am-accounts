package mvi.accounts.service.rest;

import mvi.accounts.domain.Address;
import mvi.accounts.domain.Phone;
import mvi.accounts.service.dto.AccountDto;

import java.util.List;

interface AccountTestUtils {

    static Address createCompleteAddress() {
        final var expectedAddress = new Address();
        expectedAddress.setStreetNo(3);
        expectedAddress.setStreet("Calle Street");
        expectedAddress.setCountry("Frenchlandia");
        expectedAddress.setCity("Testing sur Marne");
        expectedAddress.setPostalCode("58879");
        return expectedAddress;
    }

    static AccountDto createBareboneAccountDto() {
        final var dto = new AccountDto();
        dto.setFirstName("Toto");
        dto.setLastName("Fifi");
        return dto;
    }

    static AccountDto createCompleteAccountDto() {
        final var dto = new AccountDto();
        dto.setFirstName("Toto");
        dto.setLastName("Fifi");
        dto.setAddress(createCompleteAddress());
        dto.setPhones(createCompletePhoneList());
        return dto;
    }

    static List<Phone> createCompletePhoneList() {
        return List.of(new Phone("+555 8887", "Nothing"), new Phone("+33 555 7766", ""));
    }
}
