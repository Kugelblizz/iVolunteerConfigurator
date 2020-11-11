package at.jku.cis.iVolunteer.configurator.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.configurator.model._httprequests.ClassConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.ClassInstanceConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.MatchingConfiguratorResponseRequestBody;


@Service
public class ResponseRestClient {

	@Autowired private RestTemplate restTemplate;

	public HttpStatus sendClassConfiguratorResponse(String url, ClassConfiguratorResponseRequestBody body) {
		System.out.println(url);
		ResponseEntity<Object> resp = restTemplate.exchange(url, HttpMethod.POST, buildEntity(body), Object.class);
		return resp.getStatusCode();
	}

	public HttpStatus sendClassInstanceConfiguratorResponse(String url, ClassInstanceConfiguratorResponseRequestBody body) {
		ResponseEntity<Object> resp = restTemplate.exchange(url, HttpMethod.POST, buildEntity(body), Object.class);
		return resp.getStatusCode();
	}

	public HttpStatus sendMatchingConfiguratorResponse(String url, MatchingConfiguratorResponseRequestBody body) {
		ResponseEntity<Object> resp = restTemplate.exchange(url, HttpMethod.POST, buildEntity(body), Object.class);
		return resp.getStatusCode();
	}

	private HttpEntity<?> buildEntity(Object body) {
		return new HttpEntity<>(body, buildHeaders());
	}

	private HttpHeaders buildHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}
}
