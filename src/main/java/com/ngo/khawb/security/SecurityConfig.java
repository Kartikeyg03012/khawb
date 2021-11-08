package com.ngo.khawb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Bean
  public UserDetailsService getUserdetails() {
    return new UserDetailServiceImpl();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authentication = new DaoAuthenticationProvider();
    authentication.setUserDetailsService(this.getUserdetails());
    authentication.setPasswordEncoder(passwordEncoder());
    return authentication;
  }

  // config Another Methods
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/admin/**")
        .hasRole("SUPER_ADMIN")
        .antMatchers("/user/**")
        .hasRole("END_USER")
        .antMatchers("/volunteer/**")
        .hasRole("VOLUNTEER")
        .antMatchers("/**")
        .permitAll()
        .and()
        .formLogin()
        //				.failureUrl("/error-page")
        .defaultSuccessUrl("/admin/login-success", false)
        .and()
        .csrf()
        .disable();
  }
}
