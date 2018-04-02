package com.github.houssemba.mixtwitt.domain.model;

import java.util.Date;

public class Tweet {
	private final String id;
	private final Date creationDate;
	private final User user;
	private final String message;

	private Tweet(String id, Date creationDate, User user, String message) {
		this.id = id;
		this.creationDate = creationDate;
		this.user = user;
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public User getUser() {
		return user;
	}

	public String getMessage() {
		return message;
	}

	public static class Builder {
		private String id;
		private Date creationDate;
		private User user;
		private String message;

		public Builder() {
			this.creationDate = new Date();
		}

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withCreationDate(Date creationDate) {
			this.creationDate = creationDate;
			return this;
		}

		public Builder withUser(User user) {
			this.user = user;
			return this;
		}

		public Builder withMessage(String message) {
			this.message = message;
			return this;
		}

		public Tweet build() {
			return new Tweet(id, creationDate, user, message);
		}
	}
}
