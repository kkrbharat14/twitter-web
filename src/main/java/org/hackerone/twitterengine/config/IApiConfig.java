package org.hackerone.twitterengine.config;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public interface IApiConfig {
	ApiConfig getApiConfiguration();
	void addQueryParamtoDefault(MultiValueMap<String, String> additionalQueryParams);
}
