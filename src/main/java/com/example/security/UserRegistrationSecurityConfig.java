package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class UserRegistrationSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF if you don't need it for your API
                .csrf(csrf -> csrf.disable())
                // Configure authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/register/**").permitAll()
                        .requestMatchers("/api/v1/users/**").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
               // .formLogin(withDefaults());
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


}

