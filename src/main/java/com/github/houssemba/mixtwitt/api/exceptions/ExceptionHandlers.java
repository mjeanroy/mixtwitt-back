package com.github.houssemba.mixtwitt.api.exceptions;

import com.github.houssemba.mixtwitt.domain.exceptions.DaoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlers {

	@ExceptionHandler(AuthenticationFailedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public void onAuthenticationFailedException() {
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public void onUnauthorizedException() {
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void onUserNotFoundException() {
	}

	@ExceptionHandler(DaoException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void onDaoException() {
	}
}
