package com.example.Juoksuprojekti;

/*
 * Copyright (c) 2018 codenerve.com
 *
 * You may study, use, and modify this example. Redistribution is not permitted.
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login");
		registry.addViewController("/runninglist").setViewName("runninglist");
		registry.addViewController("/adminlist").setViewName("adminlist");
		registry.addViewController("/login").setViewName("login");
	}
}