package com.github.houssemba.mixtwitt.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "users")
public class User {
	@Id
	private Long id;
	private Date creationDate;
	private String login;
	private String password;

	public User() {
	}

	private User(Long id, Date creationDate, String login, String password) {
		this.id = id;
		this.creationDate = creationDate;
		this.login = login;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public static class Builder {
		private Long id;
		private Date creationDate;
		private String login;
		private String password;

		public Builder() {
			this.creationDate = new Date();
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withLogin(String login) {
			this.login = login;
			return this;
		}

		public Builder withPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder withCreationDate(Date creationDate) {
			this.creationDate = creationDate;
			return this;
		}

		public User build() {
			return new User(id, creationDate, login, password);
		}
	}
}
