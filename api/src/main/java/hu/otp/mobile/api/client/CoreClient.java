package hu.otp.mobile.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import hu.otp.mobile.api.config.RestclientProperties;

@Service
@EnableConfigurationProperties(RestclientProperties.class)
public class CoreClient {

	@Autowired
	private RestclientProperties restclientProperties;

	public boolean validateToken(String userToken) {

		String url = String.format("%s/userToken?userToken=%s", restclientProperties.getCoreUrl(), userToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		HttpEntity<Boolean> response = restTemplate.getForEntity(builder.build().encode().toUri(), Boolean.class);

		return response.getBody();
	}

}
