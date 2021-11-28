package com.example.Juoksuprojekti;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

/*
 * Copyright (c) 2018 codenerve.com
 *
 * You may study, use, and modify this example. Redistribution is not permitted.
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.Juoksuprojekti.domain.UserRepository;
import com.example.Juoksuprojekti.web.UserDetailServiceImplement;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserDetailServiceImplement userDetailsService;

	private UserRepository repository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {

		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

		if (roles.contains("ROLE_ADMIN")) {
			httpServletResponse.sendRedirect("/adminlist");
		} else {
			httpServletResponse.sendRedirect("/runninglist");
		}
	}
}