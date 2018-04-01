package com.github.houssemba.mixtwitt.api.dto;

import javax.validation.constraints.Size;
import java.util.Date;

import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotBlank;

public class TweetDto {

	private Long id;
	private Date creationDate;
	private String login;

	@NotBlank
	@Size(max = 140)
	private String message;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(getClass())
			.add("id", id)
			.add("creationDate", creationDate)
			.add("login", login)
			.add("message", message)
			.toString();
	}
}
