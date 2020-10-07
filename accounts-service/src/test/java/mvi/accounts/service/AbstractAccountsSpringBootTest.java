package mvi.accounts.service;

import mvi.accounts.data.config.PersistenceConfig;
import mvi.accounts.service.config.JacksonConfig;
import mvi.accounts.service.config.ServicesConfig;
import mvi.accounts.service.config.SwaggerConfig;
import mvi.accounts.service.config.WebConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {Application.class, JacksonConfig.class, ServicesConfig.class, WebConfig.class, SwaggerConfig.class, PersistenceConfig.class},
        properties = {
                "spring.main.web-application-type=none",
                "spring.jpa.hibernate.ddl-auto=none",
                "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1",
                "spring.datasource.username=sa",
                "spring.datasource.password=sa"
        })
public abstract class AbstractAccountsSpringBootTest {
}
