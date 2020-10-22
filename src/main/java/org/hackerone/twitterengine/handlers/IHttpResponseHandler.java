package org.hackerone.twitterengine.handlers;

import org.hackerone.twitterengine.exceptions.UnexpectedBackendException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IHttpResponseHandler {
	ResponseEntity<String> getResponse(String uriString, HttpMethod httpmethod, HttpEntity<String> entity) throws UnexpectedBackendException;
}
