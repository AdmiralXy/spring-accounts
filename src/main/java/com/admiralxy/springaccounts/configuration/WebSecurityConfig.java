package com.admiralxy.springaccounts.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTER_ENDPOINT = "/register";
    private static final String JS_ENDPOINT = "/css/**";
    private static final String CSS_ENDPOINT = "/js/**";
    private static final String IMAGES_ENDPOINT = "/images/**";

    @Autowired
    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(REGISTER_ENDPOINT).permitAll()
                    .antMatchers(JS_ENDPOINT).permitAll()
                    .antMatchers(CSS_ENDPOINT).permitAll()
                    .antMatchers(IMAGES_ENDPOINT).permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage(LOGIN_ENDPOINT)
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT username, password, active FROM users WHERE username=?")
                .authoritiesByUsernameQuery(
                        "SELECT u.username, ur.roles FROM users u " +
                        "INNER JOIN user_roles ur " +
                        "ON u.id = ur.user_id WHERE u.username=?"
                );
    }
}
