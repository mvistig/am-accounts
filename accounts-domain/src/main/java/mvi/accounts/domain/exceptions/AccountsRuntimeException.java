package mvi.accounts.domain.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class AccountsRuntimeException extends RuntimeException {
    private final String origin;
    private final String message;
    private final String scope;

    public AccountsRuntimeException(String message, String origin, String scope) {
        super(message);
        this.origin = origin;
        this.message = message;
        this.scope = scope;
    }
}
