package hu.otp.mobile.ticket.client;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;
import hu.otp.mobile.common.dto.ReservationDto;
import hu.otp.mobile.ticket.config.RestclientProperties;
import hu.otp.mobile.ticket.config.SecurityProperties;

@Service
@EnableConfigurationProperties({ RestclientProperties.class, SecurityProperties.class })
public class PartnerClient {

	@Autowired
	private RestclientProperties restclientProperties;
	@Autowired
	private SecurityProperties securityProperties;

	private RestTemplate restTemplate() {

		SSLContext sslContext;

		try {
			sslContext = new SSLContextBuilder()
					.loadTrustMaterial(new URL(securityProperties.getUrl()), securityProperties.getPassword().toCharArray()).build();
		} catch (Exception e) {
			throw new IllegalStateException("Failed to setup client SSL context", e);
		}

		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
		HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return new RestTemplate(factory);
	}

	public List<Event> queryEvents() {

		String url = String.format("%s/getEvents", restclientProperties.getPartnerUrl());

		RestTemplate restTemplate = restTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		HttpEntity<Event[]> response = restTemplate.getForEntity(builder.build().encode().toUri(), Event[].class);

		return response.getBody() != null ? Arrays.asList(response.getBody()) : Collections.emptyList();
	}

	public EventDetails queryEventDetails(long eventId) {

		String url = String.format("%s/getEvent?id=%d", restclientProperties.getPartnerUrl(), eventId);

		RestTemplate restTemplate = restTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		HttpEntity<EventDetails> response = restTemplate.getForEntity(builder.build().encode().toUri(), EventDetails.class);

		return response.getBody();
	}

	public ReservationDto reserveSeat(long eventId, long seatId) {

		// String url = String.format("%s/reserve?eventId=%d&seatId=%d", restclientProperties.getPartnerUrl(), eventId, seatId);
		String url = String.format("%s/reserve", restclientProperties.getPartnerUrl());

		RestTemplate restTemplate = restTemplate();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("eventId", eventId).queryParam("seatId", seatId);

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("eventId", eventId);
		map.add("seatId", seatId);

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

		HttpEntity<ReservationDto> response = restTemplate.postForEntity(builder.build().encode().toUri(), request, ReservationDto.class);

		return response.getBody();
	}
}
