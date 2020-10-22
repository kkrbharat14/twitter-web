package org.hackerone.twitterengine.controller;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hackerone.twitterengine.domain.TweetContainer;
import org.hackerone.twitterengine.exceptions.InvalidMappingException;
import org.hackerone.twitterengine.exceptions.UnexpectedBackendException;
import org.hackerone.twitterengine.mapper.TweetResultMapper;
import org.hackerone.twitterengine.service.TweetService;
import org.hackerone.twitterengine.viewmodel.TweetResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping(value = "/api/v2/tweet")
@Validated
public class TweetController {

	@Autowired
	private TweetService tweetService;
	@Autowired
	private TweetResultMapper tweetResultMapper;

	@CrossOrigin(origins = { "*" })
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TweetResult> searchTweets(
			@RequestParam(name = "query", required = true) @Size(max = 50) String query,
			@RequestParam(name = "max_results", required = false, defaultValue = "10") @Min(10) @Max(100) int maxResults)
			throws JsonMappingException, JsonProcessingException, UnexpectedBackendException, InvalidMappingException {
		TweetContainer tweetContainer = tweetService.searchTweets(query, String.valueOf(maxResults));
		TweetResult tweetResult = tweetResultMapper.getTweetResults(tweetContainer.getData());
		return new ResponseEntity<TweetResult>(tweetResult, HttpStatus.OK);
	}
}
