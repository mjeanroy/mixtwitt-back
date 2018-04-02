package com.github.houssemba.mixtwitt.api.web;

import com.github.houssemba.mixtwitt.api.dto.LoginDto;
import com.github.houssemba.mixtwitt.api.dto.UserDto;
import com.github.houssemba.mixtwitt.api.exceptions.AuthenticationFailedException;
import com.github.houssemba.mixtwitt.api.exceptions.UnauthorizedException;
import com.github.houssemba.mixtwitt.api.mappers.UserMapper;
import com.github.houssemba.mixtwitt.domain.model.User;
import com.github.houssemba.mixtwitt.domain.repository.LoginRepository;
import com.github.houssemba.mixtwitt.domain.repository.UserRepository;
import com.github.houssemba.mixtwitt.security.interceptors.Security;
import com.github.houssemba.mixtwitt.security.parsers.TokenParser;
import com.github.houssemba.mixtwitt.security.resolver.Authenticated;
import com.github.houssemba.mixtwitt.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class AuthenticationController {

	private final LoginRepository loginRepository;
	private final UserMapper userMapper;
	private final SecurityService securityService;

	@Autowired
	public AuthenticationController(LoginRepository loginRepository, UserMapper userMapper, SecurityService securityService) {
		this.loginRepository = loginRepository;
		this.userMapper = userMapper;
		this.securityService = securityService;
	}

	@GetMapping("/api/me")
	@Security
	public UserDto getMe(@Authenticated User me) {
		return userMapper.map(me);
	}

	@PostMapping("/api/login")
	public void authenticate(@RequestBody @Valid  LoginDto loginDto, HttpServletResponse response) {
		User user = loginRepository.findByLoginAndPassword(loginDto.getLogin(), loginDto.getPassword()).orElseThrow(() ->
				new AuthenticationFailedException(loginDto.getLogin())
		);

		securityService.authenticate(response, user);
	}

	@PostMapping("/api/logout")
	@Security
	public void authenticate(HttpServletResponse response) {
		securityService.logout(response);
	}
}
