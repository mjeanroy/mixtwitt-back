package com.github.houssemba.mixtwitt.configuration;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsInterceptor implements HandlerInterceptor {
	private static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	private static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
	private static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		response.setHeader(ACCESS_CONTROL_EXPOSE_HEADERS, "origin, content-type, accept, mix-twitt");
		response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, PATCH, DELETE");
		response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "origin, content-type, accept, mix-twitt");
		response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		// Nothing to do.
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		// Nothing to do.
	}
}
