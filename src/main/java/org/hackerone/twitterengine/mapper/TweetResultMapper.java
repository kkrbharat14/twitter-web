package org.hackerone.twitterengine.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hackerone.twitterengine.domain.Tweet;
import org.hackerone.twitterengine.exceptions.InvalidMappingException;
import org.hackerone.twitterengine.viewmodel.TweetModel;
import org.hackerone.twitterengine.viewmodel.TweetResult;
import org.springframework.stereotype.Component;

@Component
public class TweetResultMapper {
	private Map<String, Integer> countByTrendingsTagNames;

	public TweetResult getTweetResults(List<Tweet> tweets) throws InvalidMappingException{
		List<TweetModel> tweetModels = new ArrayList<TweetModel>();
		try {
			this.countByTrendingsTagNames = new HashMap<String, Integer>();
			Map<String, Integer> countByTagNames = null;
			if (null != tweets) {
				for (Tweet tweet : tweets) {

					tweetModels.add(new TweetModel(tweet.getId(), tweet.getText()));
					Map<String, Object> entities = tweet.getEntities();

					if (null != entities) {
						@SuppressWarnings("unchecked")
						List<Map<String, Object>> hashTags = (List<Map<String, Object>>) entities.get("hashtags");

						if (null != hashTags) {
							countByTagNames = countTagByNames(hashTags, countByTagNames);
						}

					}

				}
			}

			if (null != countByTagNames) {
				this.countByTrendingsTagNames = countByTagNames.entrySet().stream()
						.sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).limit(10).collect(Collectors
								.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new InvalidMappingException();
		}

		return new TweetResult(tweetModels, countByTrendingsTagNames);
		
	}

	private Map<String, Integer> countTagByNames(List<Map<String, Object>> hashTags,
			Map<String, Integer> countByTagNames) {
		for (Map<String, Object> hashTag : hashTags) {
			String tagName = ((String) hashTag.get("tag")).toLowerCase();

			if (null == countByTagNames) {
				countByTagNames = new HashMap<String, Integer>();
			}

			if (countByTagNames.containsKey(tagName)) {
				countByTagNames.put(tagName, countByTagNames.get(tagName) + 1);
			} else {
				countByTagNames.put(tagName, 1);
			}
		}

		return countByTagNames;
	}
}
