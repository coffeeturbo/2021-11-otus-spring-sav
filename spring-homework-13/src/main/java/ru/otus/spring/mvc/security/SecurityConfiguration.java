package ru.otus.spring.mvc.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String CAN_WRITE_BOOKS = "ADMIN";
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/books/edit/*").hasRole(CAN_WRITE_BOOKS)
                .and()
                .authorizeRequests().antMatchers("/books/create").hasRole(CAN_WRITE_BOOKS)
                .and()
                .authorizeRequests().antMatchers("/books/delete/*").hasRole(CAN_WRITE_BOOKS)
                .and()
                .authorizeRequests().antMatchers("/books/", "/books/**").authenticated()
                .and()
                .formLogin()
                .and()
                .logout().logoutUrl("/logout");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
