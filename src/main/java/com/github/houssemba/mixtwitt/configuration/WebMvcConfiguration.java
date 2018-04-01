package com.github.houssemba.mixtwitt.configuration;

import com.github.houssemba.mixtwitt.security.interceptors.SecurityInterceptor;
import com.github.houssemba.mixtwitt.security.resolver.AuthenticatedUserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private SecurityInterceptor securityInterceptor;

	@Autowired
	private AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(new CorsInterceptor());
		registry.addInterceptor(securityInterceptor);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);
		argumentResolvers.add(authenticatedUserArgumentResolver);
	}
}
