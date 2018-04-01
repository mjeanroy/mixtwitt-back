package com.github.houssemba.mixtwitt.api.exceptions;

abstract class AbstractEntityNotFoundException extends RuntimeException {

	AbstractEntityNotFoundException(String message) {
		super(message);
	}
}
