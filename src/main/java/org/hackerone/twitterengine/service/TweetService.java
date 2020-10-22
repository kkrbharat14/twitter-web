package org.hackerone.twitterengine.service;

import org.hackerone.twitterengine.config.ApiConfig;
import org.hackerone.twitterengine.config.IApiConfig;
import org.hackerone.twitterengine.domain.TweetContainer;
import org.hackerone.twitterengine.exceptions.UnexpectedBackendException;
import org.hackerone.twitterengine.handlers.IHttpResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TweetService {

	@Autowired
	private IHttpResponseHandler iHttpResponseHandler;
	@Autowired
	private IApiConfig iApiConfig;

	public TweetService(IHttpResponseHandler iHttpResponseHandler, IApiConfig iApiConfig) {
		this.iHttpResponseHandler = iHttpResponseHandler;
		this.iApiConfig = iApiConfig;
	}

	public TweetContainer searchTweets(String keyword, String maxResults)
			throws UnexpectedBackendException, JsonMappingException, JsonProcessingException {
		ApiConfig apiConfig = this.getTwitterApiConfig(keyword, maxResults);
		ResponseEntity<String> response = iHttpResponseHandler.getResponse(apiConfig.getUriString(),
				apiConfig.getHttpMethod(), apiConfig.getEntity());
		return new ObjectMapper().readValue(response.getBody(), TweetContainer.class);
	}

	private ApiConfig getTwitterApiConfig(String keyword, String maxResults) {
		MultiValueMap<String, String> additionalQueryParams = new LinkedMultiValueMap<String, String>();
		// To be Investigated
		keyword = keyword.replace("#", "");

		additionalQueryParams.add("query", keyword);
		additionalQueryParams.add("max_results", maxResults);

		iApiConfig.addQueryParamtoDefault(additionalQueryParams);
		return iApiConfig.getApiConfiguration();
	}
}
