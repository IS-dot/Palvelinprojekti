package com.example.Juoksuprojekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.Juoksuprojekti.web.UserDetailServiceImplement;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailServiceImplement userDetailsService;

	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	public WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
		this.authenticationSuccessHandler = authenticationSuccessHandler;
	}

//
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/**").permitAll() // Enable css when logged out
				.and().authorizeRequests().antMatchers("/signup", "/saveuser").permitAll().and().authorizeRequests()
				.anyRequest().authenticated().and().formLogin().loginPage("/login")
				.defaultSuccessUrl("/runninglist", true).permitAll().and().logout().permitAll()
				.invalidateHttpSession(true); // Invalidate session;
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/css/**").permitAll().antMatchers("/adminlist").hasAuthority("ADMIN")
//				.and().authorizeRequests().antMatchers("/signup", "/saveuser").permitAll().and().authorizeRequests()
//				.anyRequest().authenticated().and().formLogin().loginPage("/login")
//				.successHandler(authenticationSuccessHandler).permitAll().and().logout().permitAll().and().csrf()
//				.disable();
//	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/css/**", "/signup", "/saveuser").permitAll().antMatchers("/adminlist")
//				.hasAuthority("ADMIN").anyRequest().authenticated().and().formLogin().loginPage("/login")
//				.successHandler(authenticationSuccessHandler).permitAll().and().logout().permitAll().and().csrf()
//				.disable();
//	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
