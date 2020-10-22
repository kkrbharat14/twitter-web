package org.hackerone.twitterengine.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetContainer implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Tweet> data;
}
