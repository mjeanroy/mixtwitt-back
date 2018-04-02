package com.github.houssemba.mixtwitt.domain.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.github.houssemba.mixtwitt.domain.exceptions.DaoException;
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
public class TweetRepository extends AbstractRepository<Tweet> {

	@Autowired
	public TweetRepository(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
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

		return findAll(sql, TWEET_ROW_MAPPER);
	}

	public Optional<Tweet> findById(String id) {
		String sql =
			"SELECT " +
				"t.id as id, " +
				"t.creation_date as creation_date, " +
				"t.message as message, " +
				"u.id as user_id, " +
				"u.login as user_login " +
				"FROM tweets t " +
				"INNER JOIN users u ON t.user_id = u.id " +
				"WHERE t.id = '" + id + "'";

		return findOne(sql, TWEET_ROW_MAPPER);
	}

	public Tweet save(User user, String message) {
		String id = UUID.randomUUID().toString();
		Long userId = user.getId();
		String sql = "INSERT INTO tweets(id, creation_date, message, user_id) VALUES ('" + id + "', NOW(), '" + message + "', " + userId + ")";
		jdbcTemplate.update(sql);

		return findById(id).orElseThrow(() ->
				new DaoException("Cannot find previously inserted tweet")
		);
	}

	private static final RowMapper<Tweet> TWEET_ROW_MAPPER = (rs, rowNum) -> new Tweet.Builder()
		.withId(rs.getString("id"))
		.withCreationDate(rs.getTimestamp("creation_date"))
		.withMessage(rs.getString("message"))
		.withUser(new User.Builder()
			.withId(rs.getLong("user_id"))
			.withLogin(rs.getString("user_login"))
			.build())
		.build();
}
