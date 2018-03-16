package com.github.houssemba.mixtwitt.api.web;

import javax.validation.Valid;
import java.util.List;

import com.github.houssemba.mixtwitt.api.dto.TweetDto;
import com.github.houssemba.mixtwitt.api.mappers.TweetMapper;
import com.github.houssemba.mixtwitt.domain.model.Tweet;
import com.github.houssemba.mixtwitt.domain.model.User;
import com.github.houssemba.mixtwitt.domain.repository.TweetRepository;
import com.github.houssemba.mixtwitt.domain.repository.UserRepository;
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
	private final UserRepository userRepository;
	private final TweetMapper tweetMapper;

	@Autowired
	public TweetController(UserRepository userRepository, TweetRepository tweetRepository, TweetMapper tweetMapper) {
		this.userRepository = userRepository;
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
	}

	@GetMapping
	public List<TweetDto> fetch() {
		log.info("Fetch Tweets");
		List<Tweet> tweets = tweetRepository.findAll();
		return tweetMapper.map(tweets);
	}

	@PostMapping
	public TweetDto create(@RequestBody @Valid TweetDto tweet) {
		log.info("Save new tweet: {}", tweet);

		String login = tweet.getLogin();
		User user = userRepository.findByLogin(login);
		Tweet result = tweetRepository.save(user, tweet.getMessage());
		return tweetMapper.map(result);
	}
}
