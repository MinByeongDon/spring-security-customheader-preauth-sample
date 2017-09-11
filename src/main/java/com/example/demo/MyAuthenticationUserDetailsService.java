package com.example.demo;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class MyAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>{
	private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationUserDetailsService.class);
	
	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		String principal = (String)token.getPrincipal();
		String credentials = (String)token.getCredentials();
		logger.info("principal:{}, credentials:{}", principal, credentials);
		if("deepfree".equals(principal)) {
			if("1234".equals(credentials)) {
				Collection<GrantedAuthority> authorities =new HashSet<>();
				authorities.add(new SimpleGrantedAuthority("USER_ROLE"));
				return new User(principal, "1234", authorities);	
			} else {
				throw new BadCredentialsException("인증정보가 올바르지 않습니다.");
			}
		} else {
			throw new UsernameNotFoundException("인증정보가 없습니다.");
		}
	}

}
