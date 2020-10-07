package mvi.accounts.service.config;

import mvi.accounts.service.rest.AccountResource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = AccountResource.class)
public class WebConfig {

}
