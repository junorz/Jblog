package com.junorz.jblog.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.junorz.jblog.config.SecurityConfig.CorsConfig;
import com.junorz.jblog.context.consts.Authority;
import com.junorz.jblog.context.security.AuthenticationProviderImpl;
import com.junorz.jblog.service.UserService;

import lombok.Data;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CorsConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    
    private final UserService userService;


    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProviderImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // The bean named "corsConfigurationSource" will be applied.
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.formLogin()
            .loginPage("/api/blog/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .successHandler(new AuthSuccessHandler())
            .failureHandler(new AuthFailureHandler());

        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/api/blog/create").permitAll()
            .antMatchers("/api/blog/login").permitAll()
            .antMatchers("/api/posts/create").hasAnyRole(Authority.ROLE_ADMIN.getRoleName(), Authority.ROLE_SUPER_ADMIN.getRoleName())
            .antMatchers("/api/posts/**/edit").hasAnyRole(Authority.ROLE_ADMIN.getRoleName(), Authority.ROLE_SUPER_ADMIN.getRoleName())
            .antMatchers("/api/posts/**/delete").hasAnyRole(Authority.ROLE_ADMIN.getRoleName(), Authority.ROLE_SUPER_ADMIN.getRoleName())
            .antMatchers("/api/comments/**/delete").hasAnyRole(Authority.ROLE_ADMIN.getRoleName(), Authority.ROLE_SUPER_ADMIN.getRoleName());

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationProviderImpl authenticationProviderImpl() {
        return new AuthenticationProviderImpl(userService, passwordEncoder());
    }

    @ConfigurationProperties(prefix = "jblog")
    @Data
    public static class CorsConfig {
        List<String> cors;
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource(CorsConfig corsConfig) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(corsConfig.getCors());
        // Log CORS info
        logger.info("The CORS list: {}", corsConfig.getCors());
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return corsConfigurationSource;
    }

    public static class AuthSuccessHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpStatus.OK.value());
        }

    }

    public static class AuthFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw exception;
        }

    }

}
