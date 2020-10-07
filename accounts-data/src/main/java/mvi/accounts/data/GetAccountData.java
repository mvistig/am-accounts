package mvi.accounts.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mvi.accounts.data.dao.AccountRepository;
import mvi.accounts.domain.Account;
import mvi.accounts.domain.Address;
import mvi.accounts.domain.exceptions.AccountsRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetAccountData {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetAccountData.class);
    private final AccountRepository repository;
    private final ObjectMapper objectMapper;

    public GetAccountData(AccountRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public boolean existsAccount(long id) {
        return repository.existsById(id);
    }

    public Optional<Account> getAccount(long id) {
        var byId = repository.findById(id);
        if (byId.isEmpty()) {
            return Optional.empty();
        } else {
            var entity = byId.get();
            final Account account = new Account();
            account.setId(entity.getId());
            account.setFirstName(entity.getFirstName());
            account.setLastName(entity.getLastName());
            //parse json of address
            try {
                account.setAddress(objectMapper.readValue(entity.getAddress(), Address.class));
            } catch (JsonProcessingException e) {
                var msg = "Couldn't read json from DB: " + e.getMessage();
                LOGGER.warn(msg);
                throw new AccountsRuntimeException(msg, "GET_ACCOUNT", "JSON_READ");
            }
            //parse json of phones
            try {
                account.setPhones(objectMapper.readValue(entity.getPhones(),
                                                         new TypeReference<>() {}));
            } catch (JsonProcessingException e) {
                var msg = "Couldn't read json from DB: " + e.getMessage();
                LOGGER.warn(msg);
                throw new AccountsRuntimeException(msg, "GET_ACCOUNT", "JSON_READ");
            }
            return Optional.of(account);
        }

    }
}
