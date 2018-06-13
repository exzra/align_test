package com.tabak.test.task2.aligntest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/rest/api/find")
                .access("hasRole('USER') or hasRole('ADMIN')").and()
                .authorizeRequests().antMatchers("/rest/api/leftovers")
                .access("hasRole('USER') or hasRole('ADMIN')").and()
                .authorizeRequests().antMatchers("/rest/api/logout")
                .access("hasRole('USER') or hasRole('ADMIN')").and()
                .authorizeRequests().antMatchers("/**")
                .access("hasRole('ADMIN')")
                .and().httpBasic().authenticationEntryPoint(authEntryPoint).and();
    }

    /**
     * здесь конечно можно было бы вынести пользователей в конфиг, дабы было красивее
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).
                withUser("user").password("user").roles("USER").and().
                withUser("admin").password("admin").roles("ADMIN");
    }
}
