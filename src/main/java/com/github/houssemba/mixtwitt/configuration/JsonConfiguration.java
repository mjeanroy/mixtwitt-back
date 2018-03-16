package com.github.houssemba.mixtwitt.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JsonConfiguration {

	private static final Logger log = LoggerFactory.getLogger(JsonConfiguration.class);

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		log.info("Create custom Jackson Serializer");
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.serializationInclusion(JsonInclude.Include.NON_NULL);
		builder.failOnUnknownProperties(false);
		builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return builder;
	}
}
