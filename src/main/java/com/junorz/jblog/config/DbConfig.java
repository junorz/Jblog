package com.junorz.jblog.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.junorz.jblog.config.DbConfig.DbInfo;
import com.junorz.jblog.context.orm.DefaultRepository;
import com.junorz.jblog.context.orm.Repository;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Data;

@Configuration
@EnableConfigurationProperties(DbInfo.class)
public class DbConfig {
    
    @Data
    @ConfigurationProperties("jblog.datasource")
    public static class DbInfo {
        private String jdbc;
        private String username;
        private String password;
        private boolean showSql;
        private String mode;
        private boolean createDdlScript;
        
        public HibernateJpaVendorAdapter vendorAdapter() {
            HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
            jpaVendorAdapter.setShowSql(showSql);
            return jpaVendorAdapter;
        }
        
        public Properties jpaProperties() {
            Properties properties = new Properties();
            properties.put("hibernate.hbm2ddl.auto", mode);
            if (createDdlScript) {
            	properties.put("javax.persistence.schema-generation.create-source", "metadata");
            	properties.put("javax.persistence.schema-generation.scripts.action", "create");
            	properties.put("javax.persistence.schema-generation.scripts.create-target", "create.sql");
            }
            return properties;
        }
        
    }

    @Bean
    public DataSource dataSource(DbInfo dbInfo) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dbInfo.getJdbc());
        dataSource.setUsername(dbInfo.getUsername());
        dataSource.setPassword(dbInfo.getPassword());
        dataSource.setMaxLifetime(60000);
        return dataSource;
    }
    
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource, DbInfo dbInfo) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(dbInfo.vendorAdapter());
        entityManagerFactoryBean.setJpaProperties(dbInfo.jpaProperties());
        entityManagerFactoryBean.setPackagesToScan("com.junorz.jblog.domain");
        entityManagerFactoryBean.setPersistenceUnitName("jblog");
        return entityManagerFactoryBean;
    }
    
    @Bean
    public PlatformTransactionManager txManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
    
    @Bean
    public Repository repository() {
        return new DefaultRepository();
    }
    
    
}
