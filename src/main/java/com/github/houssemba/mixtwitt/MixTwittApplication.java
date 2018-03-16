package com.github.houssemba.mixtwitt;

import static java.util.Collections.singleton;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = MixTwittApplication.class)
public class MixTwittApplication extends SpringBootServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// This can be done here or as the last step in the method
		// Doing it in this order will initialize the Spring
		// Framework first, doing it as last step will initialize
		// the Spring Framework after the Servlet configuration is
		// established.
		super.onStartup(servletContext);

		// This will set to use COOKIE only.
		servletContext.setSessionTrackingModes(singleton(SessionTrackingMode.COOKIE));

		// This will prevent any JS on the page from accessing the
		// cookie - it will only be used/accessed by the HTTP transport
		// mechanism in use.
		SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
		sessionCookieConfig.setHttpOnly(true);
	}

	public static void main(String[] args) {
		SpringApplication.run(MixTwittApplication.class, args);
	}
}
