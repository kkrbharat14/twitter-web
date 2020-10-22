package org.hackerone.twitterengine.client;

import org.hackerone.twitterengine.exceptions.UnexpectedBackendException;
import org.hackerone.twitterengine.handlers.IHttpResponseHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class TwitterRestAPIClient implements IHttpResponseHandler {

	private final RestTemplate restTemplate;

	public TwitterRestAPIClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public ResponseEntity<String> getResponse(String uriString, HttpMethod httpmethod, HttpEntity<String> entity)
			throws UnexpectedBackendException {
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(uriString, httpmethod, entity, String.class);
		} catch (RestClientException ex) {
			throw new UnexpectedBackendException();
		}
		return response;
	}

}
