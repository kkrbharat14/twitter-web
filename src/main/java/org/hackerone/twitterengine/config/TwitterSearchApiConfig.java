package org.hackerone.twitterengine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class TwitterSearchApiConfig implements IApiConfig {

	@Value("${twitter.searchapi.url}")
	private String twitterBaseApiUrl;
	@Value("${twitter.searchapi.bearertoken}")
	private String bearerToken;
	
	private MultiValueMap<String, String> queryParams;
	
	
	@Override
	public ApiConfig getApiConfiguration() {
		this.initializeQueryParams();
		return new ApiConfig(getUriString(), HttpMethod.GET, getHttpEntity());
	}

	@Override
	public void addQueryParamtoDefault(MultiValueMap<String, String> additionalQueryParams) {
		this.queryParams = getDefaultQueryParams();
		this.queryParams.addAll(additionalQueryParams);
	}

	private String getUriString() {
		return UriComponentsBuilder.fromHttpUrl(twitterBaseApiUrl).queryParams(this.queryParams).encode().toUriString();
	}

	private MultiValueMap<String, String> getDefaultQueryParams() {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		queryParams.add("tweet.fields", "entities");
		return queryParams;
	}

	private HttpEntity<String> getHttpEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + bearerToken);
		return new HttpEntity<>(null, headers);
	}

	private void initializeQueryParams() {
		if (null == this.queryParams) {
			this.queryParams = getDefaultQueryParams();
		}
	}
}
