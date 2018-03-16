package com.github.houssemba.mixtwitt.domain.repository;

import com.github.houssemba.mixtwitt.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public User findByLogin(String login) {
		String sql =
			"SELECT id, creation_date, login, password " +
			"FROM users " +
			"WHERE login = '" + login + "'";

		return jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER);
	}

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> new User.Builder()
		.withId(rs.getLong("id"))
		.withCreationDate(rs.getDate("creation_date"))
		.withLogin(rs.getString("login"))
		.withPassword(rs.getString("password"))
		.build();
}
