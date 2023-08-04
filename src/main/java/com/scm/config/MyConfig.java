package com.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import jakarta.servlet.http.HttpSession;


@Configuration
public class MyConfig   
{
	
	@Bean
	public UserDetailsService getuserDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
   @Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getuserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
		return daoAuthenticationProvider;
	}	
	
   @Bean
	public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception
	{
//			httpSecurity.authenticationProvider(this.authenticationProvider())
			httpSecurity.authorizeRequests()
			.requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
			.requestMatchers("/user/**").hasAuthority("ROLE_USER")
			.requestMatchers("/**").permitAll()
			.and().formLogin().loginPage("/signin")
			.loginProcessingUrl("/dologin")
			.defaultSuccessUrl("/user/index")
			.failureUrl("/login-fail")
			.and().csrf().disable();
			return httpSecurity.build();
	}
   
	
}
