package com.github.houssemba.mixtwitt.configuration;

import com.github.houssemba.mixtwitt.domain.repository.LoginRepository;
import com.github.houssemba.mixtwitt.domain.repository.UserRepository;
import com.github.houssemba.mixtwitt.security.encoders.Base64TokenEncoder;
import com.github.houssemba.mixtwitt.security.encoders.TokenEncoder;
import com.github.houssemba.mixtwitt.security.interceptors.SecurityInterceptor;
import com.github.houssemba.mixtwitt.security.parsers.AuthHeaderTokenParser;
import com.github.houssemba.mixtwitt.security.parsers.CookieTokenParser;
import com.github.houssemba.mixtwitt.security.parsers.TokenParser;
import com.github.houssemba.mixtwitt.security.resolver.AuthenticatedUserArgumentResolver;
import com.github.houssemba.mixtwitt.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

	public static final String SECURITY_HEADER = "Mix-Twitt";

	private static final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

	@Bean
	public TokenEncoder base64TokenEncoder() {
		log.info("Create token encoder");
		return new Base64TokenEncoder();
	}

	@Bean
	public TokenParser headerTokenParser(TokenEncoder encoder) {
		log.info("Create token parser");
		return new AuthHeaderTokenParser(SECURITY_HEADER, encoder);
	}

	@Bean
	public SecurityService securityService(TokenParser tokenParser, LoginRepository loginRepository) {
		log.info("Create security service");
		return new SecurityService(tokenParser, loginRepository);
	}

	@Bean
	public AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver(SecurityService securityService) {
		return new AuthenticatedUserArgumentResolver(securityService);
	}

	@Bean
	public SecurityInterceptor securityInterceptor(SecurityService securityService) {
		log.info("Create security interceptor");
		return new SecurityInterceptor(securityService);
	}
}
