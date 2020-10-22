package org.hackerone.twitterengine.domain;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private Map<String,Object> entities; 
}
