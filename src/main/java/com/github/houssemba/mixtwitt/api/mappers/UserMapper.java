package com.github.houssemba.mixtwitt.api.mappers;

import com.github.houssemba.mixtwitt.api.dto.UserDto;
import com.github.houssemba.mixtwitt.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserDto> {
	@Override
	public UserDto map(User user) {
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setLogin(user.getLogin());
		return dto;
	}
}
