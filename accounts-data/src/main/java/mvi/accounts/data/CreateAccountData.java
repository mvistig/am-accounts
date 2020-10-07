package mvi.accounts.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mvi.accounts.data.dao.AccountRepository;
import mvi.accounts.domain.Account;
import mvi.accounts.domain.exceptions.AccountsRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateAccountData {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAccountData.class);

    private final AccountRepository repository;
    private final ObjectMapper objectMapper;

    public CreateAccountData(AccountRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public Optional<Long> addAccount(Account account) {
        mvi.accounts.data.entities.Account entity = new mvi.accounts.data.entities.Account();
        entity.setFirstName(account.getFirstName());
        entity.setLastName(account.getLastName());
        try {
            entity.setAddress(objectMapper.writeValueAsString(account.getAddress()));
        } catch (JsonProcessingException e) {
            var msg = "Couldn't parse Address as String: " + e.getMessage();
            LOGGER.warn(msg);
            throw new AccountsRuntimeException(msg, "PERSISTENCE", "JSON_WRITE");
        }
        try {
            entity.setPhones(objectMapper.writeValueAsString(account.getPhones()));
        } catch (JsonProcessingException e) {
            var msg = "Couldn't parse Phones as String: " + e.getMessage();
            LOGGER.warn(msg);
            throw new AccountsRuntimeException(msg, "PERSISTENCE", "JSON_WRITE");
        }

        entity = repository.save(entity);
        return entity.getId() == null ? Optional.empty() : Optional.of(entity.getId());

    }
}
