package com.github.houssemba.mixtwitt.api.web;

import javax.validation.Valid;
import java.util.List;

import com.github.houssemba.mixtwitt.api.dto.TweetDto;
import com.github.houssemba.mixtwitt.api.mappers.TweetMapper;
import com.github.houssemba.mixtwitt.domain.model.Tweet;
import com.github.houssemba.mixtwitt.domain.model.User;
import com.github.houssemba.mixtwitt.domain.repository.TweetRepository;
import com.github.houssemba.mixtwitt.domain.repository.UserRepository;
import com.github.houssemba.mixtwitt.security.interceptors.Security;
import com.github.houssemba.mixtwitt.security.resolver.Authenticated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {

	private static final Logger log = LoggerFactory.getLogger(TweetController.class);

	private final TweetRepository tweetRepository;
	private final TweetMapper tweetMapper;

	@Autowired
	public TweetController(TweetRepository tweetRepository, TweetMapper tweetMapper) {
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
	}

	@GetMapping
	@Security
	public List<TweetDto> fetch() {
		log.info("Fetch Tweets");
		List<Tweet> tweets = tweetRepository.findAll();
		return tweetMapper.map(tweets);
	}

	@PostMapping
	@Security
	public TweetDto create(@RequestBody @Valid TweetDto tweet, @Authenticated User me) {
		log.info("Save new tweet: {}", tweet);
		log.info("Authenticated user: {}", me);

		Tweet result = tweetRepository.save(me, tweet.getMessage());
		return tweetMapper.map(result);
	}
}
