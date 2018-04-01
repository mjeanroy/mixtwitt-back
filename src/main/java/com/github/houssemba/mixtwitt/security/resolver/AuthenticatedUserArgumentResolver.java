package com.github.houssemba.mixtwitt.security.resolver;

import com.github.houssemba.mixtwitt.api.exceptions.UnauthorizedException;
import com.github.houssemba.mixtwitt.domain.model.User;
import com.github.houssemba.mixtwitt.security.service.SecurityService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AuthenticatedUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final SecurityService securityService;

	public AuthenticatedUserArgumentResolver(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(User.class) && parameter.getParameterAnnotation(Authenticated.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		return securityService.login(request).orElseThrow(UnauthorizedException::new);
	}
}
