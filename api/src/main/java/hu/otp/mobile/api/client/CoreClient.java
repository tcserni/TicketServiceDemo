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
import hu.otp.mobile.api.web.RestTemplateErrorHandler;
import hu.otp.mobile.common.dto.CommonResultDto;

@Service
@EnableConfigurationProperties(RestclientProperties.class)
public class CoreClient {

	@Autowired
	private RestclientProperties restclientProperties;

	public CommonResultDto validateToken(String userToken) {

		String url = String.format("%s/userToken?userToken=%s", restclientProperties.getCoreUrl(), userToken);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new RestTemplateErrorHandler());

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		HttpEntity<CommonResultDto> response = restTemplate.getForEntity(builder.build().encode().toUri(), CommonResultDto.class);

		return response.getBody();
	}

}
