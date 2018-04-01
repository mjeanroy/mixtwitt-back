package com.github.houssemba.mixtwitt.api.exceptions;

public class AuthenticationFailedException extends RuntimeException {

	private final String login;

	public AuthenticationFailedException(String login) {
		super(createMessage());
		this.login = login;
	}

	public String login() {
		return login;
	}

	private static String createMessage() {
		return String.format("Authentication failed, please check your credentials");
	}
}
