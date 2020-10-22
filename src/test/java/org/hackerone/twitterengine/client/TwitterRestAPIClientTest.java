package org.hackerone.twitterengine.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withException;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;

import org.hackerone.twitterengine.exceptions.UnexpectedBackendException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(value = TwitterRestAPIClient.class)
public class TwitterRestAPIClientTest {

	@Autowired
	private TwitterRestAPIClient twitterRestAPIClient;

	@Autowired
	private MockRestServiceServer mockRestServiceServer;

	@Test
	public void getResponseShouldReturnResponseEntity() throws UnexpectedBackendException {
		mockRestServiceServer.expect(requestTo("/someURI"))
				.andRespond(withSuccess("{\"key\":\"value\"}", MediaType.APPLICATION_JSON));
		ResponseEntity<String> response = twitterRestAPIClient.getResponse("/someURI", HttpMethod.GET, null);
		
		assertNotNull(response);
	}
	
	@Test
	public void getResponseShouldThrowUnexpectedBackendExceptionOnBadBackendResponse() throws UnexpectedBackendException {
		mockRestServiceServer.expect(requestTo("/someURI"))
				.andRespond(withException(new IOException()));
		
		assertThrows(UnexpectedBackendException.class, ()->twitterRestAPIClient.getResponse("/someURI", HttpMethod.GET, null));
	}

}
