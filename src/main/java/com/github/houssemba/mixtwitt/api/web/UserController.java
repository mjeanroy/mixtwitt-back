package com.github.houssemba.mixtwitt.api.web;

import com.github.houssemba.mixtwitt.api.dto.UserDto;
import com.github.houssemba.mixtwitt.api.exceptions.UserNotFoundException;
import com.github.houssemba.mixtwitt.api.mappers.UserMapper;
import com.github.houssemba.mixtwitt.domain.model.User;
import com.github.houssemba.mixtwitt.domain.repository.UserRepository;
import com.github.houssemba.mixtwitt.security.interceptors.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/users")
public class UserController {

	private final UserMapper userMapper;
	private final UserRepository userRepository;

	@Autowired
	public UserController(UserMapper userMapper, UserRepository userRepository) {
		this.userMapper = userMapper;
		this.userRepository = userRepository;
	}

	@GetMapping("/{id}")
	@Security
	public UserDto findOne(@PathVariable("id") Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		return userMapper.map(user);
	}
}
