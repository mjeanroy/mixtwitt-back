package com.github.houssemba.mixtwitt.domain.repository;

import com.github.houssemba.mixtwitt.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LoginRepository extends AbstractRepository<User> {

	@Autowired
	public LoginRepository(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public Optional<User> findByLogin(String login) {
		String sql =
				"SELECT id, creation_date, login, password " +
						"FROM users " +
						"WHERE login = '" + login + "'";

		return findOne(sql, USER_ROW_MAPPER);
	}

	public Optional<User> findByLoginAndPassword(String login, String password) {
		String sql =
				"SELECT id, creation_date, login, password " +
						"FROM users " +
						"WHERE login = '" + login + "' " +
						"AND password = '" + password + "'";

		return findOne(sql, USER_ROW_MAPPER);
	}

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> new User.Builder()
			.withId(rs.getLong("id"))
			.withCreationDate(rs.getDate("creation_date"))
			.withLogin(rs.getString("login"))
			.withPassword(rs.getString("password"))
			.build();
}
