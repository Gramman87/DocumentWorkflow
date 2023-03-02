package com.fbscolorado.documentworkflow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private PasswordEncoder encoder;

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests().requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and().httpBasic();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String userQuery = "SELECT username, password, enabled FROM User WHERE username=?";
        String authQuery = "SELECT username, role FROM User WHERE username=?";
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(authQuery).passwordEncoder(encoder);
    }
}
