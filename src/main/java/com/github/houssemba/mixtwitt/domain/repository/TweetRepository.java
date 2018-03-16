package com.github.houssemba.mixtwitt.domain.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.github.houssemba.mixtwitt.domain.model.Tweet;
import com.github.houssemba.mixtwitt.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class TweetRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TweetRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Tweet> findAll() {
		String sql =
			"SELECT " +
				"t.id as id, " +
				"t.creation_date as creation_date, " +
				"t.message as message, " +
				"u.id as user_id, " +
				"u.login as user_login " +
			"FROM tweets t " +
			"INNER JOIN users u ON t.user_id = u.id " +
			"ORDER BY t.creation_date DESC";

		return jdbcTemplate.query(sql, TWEET_ROW_MAPPER);
	}

	public Tweet findById(Long id) {
		String sql =
			"SELECT " +
				"t.id as id, " +
				"t.creation_date as creation_date, " +
				"t.message as message, " +
				"u.id as user_id, " +
				"u.login as user_login " +
				"FROM tweets t " +
				"INNER JOIN users u ON t.user_id = u.id " +
				"WHERE t.id = " + id;

		return jdbcTemplate.queryForObject(sql, TWEET_ROW_MAPPER);
	}

	public Tweet save(User user, String message) {
		KeyHolder key = new GeneratedKeyHolder();
		InsertStatementCreation psc = new InsertStatementCreation(user.getId(), message);
		jdbcTemplate.update(psc, key);
		return findById(key.getKey().longValue());
	}

	private static class InsertStatementCreation implements PreparedStatementCreator {
		private final String message;
		private final Long userId;

		private InsertStatementCreation(Long userId, String message) {
			this.userId = userId;
			this.message = message;
		}

		@Override
		public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			String sql = "INSERT INTO tweets(creation_date, message, user_id) VALUES (NOW(), '" + message + "', " + userId + ")";
			return con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		}
	}

	private static final RowMapper<Tweet> TWEET_ROW_MAPPER = (rs, rowNum) -> new Tweet.Builder()
		.withId(rs.getLong("id"))
		.withCreationDate(rs.getTimestamp("creation_date"))
		.withMessage(rs.getString("message"))
		.withUser(new User.Builder()
			.withId(rs.getLong("user_id"))
			.withLogin(rs.getString("user_login"))
			.build())
		.build();
}
