package org.hackerone.twitterengine.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hackerone.twitterengine.domain.Tweet;
import org.hackerone.twitterengine.domain.TweetContainer;
import org.hackerone.twitterengine.exceptions.InvalidMappingException;
import org.hackerone.twitterengine.exceptions.UnexpectedBackendException;
import org.hackerone.twitterengine.mapper.TweetResultMapper;
import org.hackerone.twitterengine.service.TweetService;
import org.hackerone.twitterengine.viewmodel.TweetModel;
import org.hackerone.twitterengine.viewmodel.TweetResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = TweetController.class)
public class TweetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TweetService tweetService;
	@MockBean
	private TweetResultMapper resultMapper;


	@Test
	public void searchTweetsShouldReturnStatusOk() throws Exception {
		TweetContainer mockContainer = getMockedTweetContainer();
		when(tweetService.searchTweets("someKeyword", "10")).thenReturn(mockContainer);
		when(resultMapper.getTweetResults(mockContainer.getData())).thenReturn(getMockedTweetResult());
		
		this.mockMvc.perform(get("/api/v2/tweet/search?query=someKeyword")).andExpect(status().isOk());
	}
	
	@Test
	public void searchTweetsShouldReturnStatusBadRequest() throws Exception {
		TweetContainer mockContainer = getMockedTweetContainer();
		
		when(tweetService.searchTweets("someKeyword", "10")).thenReturn(mockContainer);
		when(resultMapper.getTweetResults(mockContainer.getData())).thenReturn(getMockedTweetResult());
		
		this.mockMvc.perform(get("/api/v2/tweet/search")).andExpect(status().is4xxClientError());
	}
	
	@Test
	public void searchTweetsShouldReturnStatusUnavailable() throws Exception {
		TweetContainer mockContainer = getMockedTweetContainer();
		when(tweetService.searchTweets("someKeyword", "10")).thenThrow(UnexpectedBackendException.class);
		when(resultMapper.getTweetResults(mockContainer.getData())).thenReturn(getMockedTweetResult());
		
		this.mockMvc.perform(get("/api/v2/tweet/search?query=someKeyword")).andExpect(status().isServiceUnavailable());
	}
	
	@Test
	public void searchTweetsShouldReturnInteralServerError() throws Exception {
		TweetContainer mockContainer = getMockedTweetContainer();
		
		when(tweetService.searchTweets("someKeyword", "10")).thenReturn(mockContainer);
		when(resultMapper.getTweetResults(mockContainer.getData())).thenThrow(InvalidMappingException.class);
		
		this.mockMvc.perform(get("/api/v2/tweet/search?query=someKeyword")).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void searchTweetsShouldReturnBadRequest() throws Exception {
		TweetContainer mockContainer = getMockedTweetContainer();
		
		when(tweetService.searchTweets("someKeyword", "100")).thenReturn(mockContainer);
		when(resultMapper.getTweetResults(mockContainer.getData())).thenReturn(getMockedTweetResult());
		
		this.mockMvc.perform(get("/api/v2/tweet/search?query=someKeyword&max_results=102")).andExpect(status().is4xxClientError());
	}
	
	
	
	private TweetResult getMockedTweetResult() {
		TweetModel mockTweet = new TweetModel("someId","someText");
		Map<String, Integer> countByTrendingsTagNames = new HashMap<String, Integer>();
		countByTrendingsTagNames.put("someHashTag", 1);
		List<TweetModel> tweetModels = new ArrayList<TweetModel>();
		tweetModels.add(mockTweet);
		return new TweetResult(tweetModels, countByTrendingsTagNames);
	}
	
	
	private TweetContainer getMockedTweetContainer() {
		TweetContainer mockTweetContainer = new TweetContainer();

		Tweet mockTweet = new Tweet();
		mockTweet.setId("someId");
		mockTweet.setText("someText");
		mockTweet.setEntities(new HashMap<String, Object>());
		Map<String, Object> mockedHashTag = new HashMap<String, Object>();
		mockedHashTag.put("start", 64);
		mockedHashTag.put("end", 75);
		mockedHashTag.put("tag", "somehashtag");
		List<Map<String, Object>> mockedHashTags = new ArrayList<Map<String, Object>>();
		mockedHashTags.add(mockedHashTag);
		mockTweet.getEntities().put("hashtags", mockedHashTags);

		mockTweetContainer.setData(new ArrayList<Tweet>());
		mockTweetContainer.getData().add(mockTweet);
		return mockTweetContainer;
	}

}
