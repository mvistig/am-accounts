package mvi.accounts.service.config;

import mvi.accounts.data.config.PersistenceConfig;
import mvi.accounts.service.rules.GetAccountService;
import mvi.accounts.service.util.RestErrorAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceConfig.class)
@ComponentScan(basePackageClasses = GetAccountService.class)
public class ServicesConfig {

    @Bean
    public RestErrorAdvice restErrorAdvice() {
        return new RestErrorAdvice();
    }

}
