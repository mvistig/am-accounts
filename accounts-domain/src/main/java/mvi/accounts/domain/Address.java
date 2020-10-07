package mvi.accounts.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {

    private String street;
    private int streetNo;
    private String additionalInfo;
    private String postalCode;
    private String city;
    private String country;

    //for shallow copying
    public Address(Address that) {
        this.street = that.street;
        this.streetNo = that.streetNo;
        this.additionalInfo = that.additionalInfo;
        this.city = that.city;
        this.postalCode = that.postalCode;
        this.country = that.country;
    }
}
