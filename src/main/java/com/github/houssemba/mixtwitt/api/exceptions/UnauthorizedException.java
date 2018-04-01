package com.github.houssemba.mixtwitt.api.exceptions;

public class UnauthorizedException extends RuntimeException {

	public UnauthorizedException() {
		super("Cannot perform action");
	}
}
