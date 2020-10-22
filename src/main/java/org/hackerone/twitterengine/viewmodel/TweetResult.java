package org.hackerone.twitterengine.viewmodel;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter@Getter@AllArgsConstructor
public class TweetResult {
	private List<TweetModel> tweets;
	private Map<String, Integer> countByTrendingsTagNames;
}
