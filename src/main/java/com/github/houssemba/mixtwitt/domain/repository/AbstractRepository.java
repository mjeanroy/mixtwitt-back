package com.github.houssemba.mixtwitt.domain.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

abstract class AbstractRepository<T> {

	final JdbcTemplate jdbcTemplate;

	AbstractRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	Optional<T> findOne(String query, RowMapper<T> mapper) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(query, mapper));
		} catch (EmptyResultDataAccessException ex) {
			return Optional.empty();
		}
	}

	List<T> findAll(String query, RowMapper<T> mapper) {
		return jdbcTemplate.query(query, mapper);
	}
}
