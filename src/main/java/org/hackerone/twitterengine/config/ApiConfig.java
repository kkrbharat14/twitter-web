package org.hackerone.twitterengine.config;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class ApiConfig {
	private String uriString;
	private HttpMethod httpMethod;
	private HttpEntity<String> entity;

	public ApiConfig(String uriString, HttpMethod httpMethod,
			HttpEntity<String> entity) {
		this.uriString = uriString;
		this.httpMethod = httpMethod;
		this.entity = entity;
	}
}
