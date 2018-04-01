package com.github.houssemba.mixtwitt.security.service;

import com.github.houssemba.mixtwitt.domain.model.User;
import com.github.houssemba.mixtwitt.domain.repository.UserRepository;
import com.github.houssemba.mixtwitt.security.parsers.TokenParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SecurityService {
	private final TokenParser tokenParser;
	private final UserRepository userRepository;

	public SecurityService(TokenParser tokenParser, UserRepository userRepository) {
		this.tokenParser = tokenParser;
		this.userRepository = userRepository;
	}

	public void authenticate(HttpServletResponse response, User user) {
		tokenParser.put(response, user.getLogin(), false);
	}

	public void logout(HttpServletResponse response) {
		tokenParser.remove(response);
	}

	public Optional<User> login(HttpServletRequest request) {
		String login = tokenParser.parse(request);
		return userRepository.findByLogin(login);
	}
}
