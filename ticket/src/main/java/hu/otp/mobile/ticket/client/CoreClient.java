package hu.otp.mobile.ticket.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import hu.otp.mobile.common.dto.CommonResultDto;
import hu.otp.mobile.ticket.config.RestclientProperties;
import hu.otp.mobile.ticket.web.RestTemplateErrorHandler;

@Service
@EnableConfigurationProperties(RestclientProperties.class)
public class CoreClient {

	@Autowired
	private RestclientProperties restclientProperties;

	public CommonResultDto checkPaymentRequirements(String userToken, String cardId, int price) {

		String url = String.format("%s/checkPaymentReq?userToken=%s&cardId=%s&price=%d", restclientProperties.getCoreUrl(), userToken,
				cardId, price);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new RestTemplateErrorHandler());

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		HttpEntity<CommonResultDto> response = restTemplate.getForEntity(builder.build().encode().toUri(), CommonResultDto.class);

		return response.getBody();
	}

}
