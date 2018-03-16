package com.github.houssemba.mixtwitt.api.mappers;

import com.github.houssemba.mixtwitt.api.dto.TweetDto;
import com.github.houssemba.mixtwitt.domain.model.Tweet;
import org.springframework.stereotype.Component;

@Component
public class TweetMapper extends AbstractMapper<Tweet, TweetDto> {
	@Override
	public TweetDto map(Tweet tweet) {
		TweetDto dto = new TweetDto();
		dto.setId(tweet.getId());
		dto.setCreationDate(tweet.getCreationDate());
		dto.setMessage(tweet.getMessage());
		dto.setLogin(tweet.getUser().getLogin());
		return dto;
	}
}
