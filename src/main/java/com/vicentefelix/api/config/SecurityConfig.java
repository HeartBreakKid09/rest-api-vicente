package com.vicentefelix.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF if not in use
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic(); // Ativar autenticação básica HTTP
    }

    // Do determine users and their roles
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();

        // User 'user' with password “password” and role 'USER'
        userDetailsService.createUser(User.withUsername("user")
                .password("{noop}password") // {noop} for Spring Security to use the password without coding it
                .roles("USER")
                .build());

        // User 'admin' with password 'admin' e role of 'ADMIN'
        userDetailsService.createUser(User.withUsername("admin")
                .password("{noop}admin") // {noop} for Spring Security to use the password without coding it

                .roles("ADMIN")
                .build());

        return userDetailsService;
    }
}
