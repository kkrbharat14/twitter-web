package org.hackerone.twitterengine.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hackerone.twitterengine.domain.Tweet;
import org.hackerone.twitterengine.domain.TweetContainer;
import org.hackerone.twitterengine.exceptions.InvalidMappingException;
import org.hackerone.twitterengine.viewmodel.TweetResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TweetResultMapperTest {

	@Autowired
	private TweetResultMapper tweetResultMapper;
	
	@Test
	public void getTweetResultsShouldMapTweetToTweetResults() throws InvalidMappingException {
		TweetResult tweetResult = tweetResultMapper.getTweetResults(getMockedTweetContainer());
		
		assertEquals(tweetResult.getTweets().size(), 2);
		assertEquals(tweetResult.getCountByTrendingsTagNames().size(), 2);
		assertEquals(tweetResult.getCountByTrendingsTagNames().get("somehashtag"), 2);
		assertEquals(tweetResult.getCountByTrendingsTagNames().get("someothertag"), 1);
	}
	
	private List<Tweet> getMockedTweetContainer() {
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
		
		Tweet someOtherTweet = new Tweet();
		someOtherTweet.setId("someId");
		someOtherTweet.setText("someText");
		someOtherTweet.setEntities(new HashMap<String, Object>());
		Map<String, Object> someOtherMockedHashTag = new HashMap<String, Object>();
		someOtherMockedHashTag.put("start", 64);
		someOtherMockedHashTag.put("end", 75);
		someOtherMockedHashTag.put("tag", "somehashtag");
		Map<String, Object> someAnotherMockedHashTag = new HashMap<String, Object>();
		someAnotherMockedHashTag.put("start", 64);
		someAnotherMockedHashTag.put("end", 75);
		someAnotherMockedHashTag.put("tag", "someothertag");
		List<Map<String, Object>> someOtherMockedHashTags = new ArrayList<Map<String, Object>>();
		someOtherMockedHashTags.add(someOtherMockedHashTag);
		someOtherMockedHashTags.add(someAnotherMockedHashTag);
		someOtherTweet.getEntities().put("hashtags", someOtherMockedHashTags);

		mockTweetContainer.setData(new ArrayList<Tweet>());
		mockTweetContainer.getData().add(mockTweet);
		mockTweetContainer.getData().add(someOtherTweet);
		return mockTweetContainer.getData();
	}
}
