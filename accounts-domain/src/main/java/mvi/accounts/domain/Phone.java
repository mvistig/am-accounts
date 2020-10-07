package mvi.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    private String phoneNumber;
    private String otherInfo;

    public Phone(Phone that) {
        this.phoneNumber = that.phoneNumber;
        this.otherInfo = that.otherInfo;
    }
}
