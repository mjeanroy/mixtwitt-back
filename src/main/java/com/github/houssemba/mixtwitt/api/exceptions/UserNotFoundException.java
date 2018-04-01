package com.github.houssemba.mixtwitt.api.exceptions;

public class UserNotFoundException extends AbstractEntityNotFoundException {

	private final Long id;

	public UserNotFoundException(Long id) {
		super(createMessage(id));
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	private static String createMessage(Long id) {
		return String.format("User with id #%s does not exist", id);
	}
}
