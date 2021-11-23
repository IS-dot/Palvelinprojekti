package com.example.Juoksuprojekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.Juoksuprojekti.web.UserDetailServiceImplement;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	private AuthenticationSuccessHandler authenticationSuccessHandler;
//
//	@Autowired
//	public WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
//		this.authenticationSuccessHandler = authenticationSuccessHandler;
//	}

	@Autowired
	private UserDetailServiceImplement userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/**").permitAll() // Enable css when logged out
				.and().authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login")
				.defaultSuccessUrl("/runninglist", true).permitAll().and().logout().permitAll()
				.invalidateHttpSession(true); // Invalidate session;
	}
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/css/**").permitAll().antMatchers("/adminbooklist").hasRole("ADMIN")
//				.anyRequest().authenticated().and().formLogin().loginPage("/login")
//				.successHandler(authenticationSuccessHandler).permitAll().and().logout().permitAll().and().csrf()
//				.disable(); // we'll enable this in a later blog
//							// post
//	}

	// suodatetaanko täällä kuka näkee mitä
	// vai controllerissa if-lauseella?

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	// nämä liittyi in-memory käyttäjiin
//	@Bean
//	@Override
//	public UserDetailsService userDetailsService() {
//		List<UserDetails> users = new ArrayList();
//
//		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//		UserDetails user = User.withUsername("user").password(passwordEncoder.encode("user")).roles("USER").build();
//
//		users.add(user);
//
//		user = User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("USER", "ADMIN").build();
//
//		users.add(user);
//
//		return new InMemoryUserDetailsManager(users);
//	}

}
