package com.github.houssemba.mixtwitt.security.interceptors;

import com.github.houssemba.mixtwitt.domain.model.User;
import com.github.houssemba.mixtwitt.security.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SecurityInterceptor implements HandlerInterceptor {

	private static final Set<String> METHOD_REQUIRES_AUTH = new HashSet<String>() {{
		add("GET");
		add("PUT");
		add("POST");
		add("PATCH");
		add("DELETE");
	}};

	private final SecurityService securityService;

	public SecurityInterceptor(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (!METHOD_REQUIRES_AUTH.contains(request.getMethod().toUpperCase())) {
			return true;
		}

		if (shouldAuthenticate(handler)) {
			Optional<User> user = securityService.login(request);
			boolean isAuthenticated = user.isPresent();
			if (!isAuthenticated) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}

			return isAuthenticated;
		}

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

	private boolean shouldAuthenticate(Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return false;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		return handlerMethod.hasMethodAnnotation(Security.class) || handlerMethod.getBean().getClass().isAnnotationPresent(Security.class);
	}
}
