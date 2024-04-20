
package com.example.autovuokraamo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;

@Configuration

@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                // mihi sivulle pääsee ilma mitää tunnuksia
                .requestMatchers("/", "/cars", "/bikes", "/css/**", "/js/**",
                        "/images/**")
                .permitAll()
                // mihi sivulle pitää olla ADMIN
                .requestMatchers("/editbike/{id}").hasRole("ADMIN")
                .requestMatchers("/deletebike/{id}").hasRole("ADMIN")
                .requestMatchers("/rentbike/{id}").hasRole("USER")
                .anyRequest().authenticated())
                .formLogin(formlogin -> formlogin.loginPage("/login").defaultSuccessUrl("/", true).permitAll()
                // login page thymeleaffi ja mihi mennää onnistuneen login jälkee
                )
                .logout(logout -> logout.permitAll());
        http.anonymous().authorities("ROLE_ANONYMOUS");
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails ilari = User.withDefaultPasswordEncoder()
                .username("ilari")
                .password("ilari")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER").build();

        return new InMemoryUserDetailsManager(ilari, admin, user);
    }
}
