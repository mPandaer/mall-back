package com.pandaer.project.validation.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 修改Validation的模式为快速失败
 */
@Configuration
public class ValidationConfig {
    private static final String FAIL_FAST = "hibernate.validator.fail_fast";


    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        localValidatorFactoryBean.getValidationPropertyMap().put(FAIL_FAST,"true");
        return localValidatorFactoryBean;
    }

}
