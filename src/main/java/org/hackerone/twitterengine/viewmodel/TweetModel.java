package org.hackerone.twitterengine.viewmodel;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Setter
@AllArgsConstructor
public class TweetModel {
	private String id;
	private String text;
}
