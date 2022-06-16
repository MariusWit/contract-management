package com.example.application.security;

import com.example.application.views.classic.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// Note: Logging disabled in application.properties due to bug https://github.com/vaadin/flow/issues/13868

// Enable Spring Security.
// Extend from VaadinWebSecurityConfigurerAdapter to configure Spring Security for Vaadin.
@EnableWebSecurity 
@Configuration
public class SecurityConfig extends VaadinWebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Allow access to the login view.
        setLoginView(http, LoginView.class);

        // Allow access to static images with or without authorization.
        //http.authorizeHttpRequests().antMatchers("/images/**", "/actuator/health").permitAll();

        super.configure(http);
    }


    /**
     * Allows access to static resources, bypassing Spring security.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Exclude the image directory to allow public access.
        web.ignoring().antMatchers("/images/**", "/actuator/health");
        super.configure(web);
    }

    /**
     * Demo UserDetailService, which only provides two hardcoded
     * in-memory users and their roles.
     * NOTE: This should not be used in real-world applications.
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // Configure an in-memory user for testing
        return new InMemoryUserDetailsManager(User.withUsername("user")
                .password("{noop}userpass")
                .roles("USER")
                .build());
    }

}

