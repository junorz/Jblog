package com.junorz.jblog.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.junorz.jblog.context.bean.AppInfo;
import com.junorz.jblog.context.orm.Repository;

@Configuration
public class AppConfig {
    
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource());
        return validatorFactoryBean;
    }
    
    @Bean
    @Scope(scopeName = "application")
    public AppInfo appInfo(Repository rep) {
        return new AppInfo(rep);
    }
    
}
