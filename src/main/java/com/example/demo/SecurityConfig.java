package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

//https://ishiis.net/2016/08/27/spring-security-custom-authentication/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService() {
		return new MyAuthenticationUserDetailsService();
	}

	@Bean
	public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
		PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
		provider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService());
		provider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
		return provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.authenticationProvider(preAuthenticatedAuthenticationProvider());
	}

	@Bean
	public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter() throws Exception {
		MyPreAuthenticatedProcessingFilter filter = new MyPreAuthenticatedProcessingFilter();
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}

	@Override
	    protected void configure(HttpSecurity http) throws Exception {
			http
				.addFilter(preAuthenticatedProcessingFilter());
	        http
	        	.csrf().disable()
//	            .authorizeRequests()
//	            	.anyRequest().authenticated().and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
	    }

}
