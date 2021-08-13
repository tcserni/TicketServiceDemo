package hu.otp.mobile.ticket.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;
import hu.otp.mobile.common.dto.ReservationDto;
import hu.otp.mobile.ticket.config.RestclientProperties;

@Service
@EnableConfigurationProperties(RestclientProperties.class)
public class PartnerClient {

	@Autowired
	private RestclientProperties restclientProperties;

	public List<Event> queryEvents() {

		String url = String.format("%s/getEvents", restclientProperties.getPartnerUrl());

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		HttpEntity<Event[]> response = restTemplate.getForEntity(builder.build().encode().toUri(), Event[].class);

		return response.getBody() != null ? Arrays.asList(response.getBody()) : Collections.emptyList();
	}

	public EventDetails queryEventDetails(long eventId) {

		String url = String.format("%s/getEvent?id=%d", restclientProperties.getPartnerUrl(), eventId);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		HttpEntity<EventDetails> response = restTemplate.getForEntity(builder.build().encode().toUri(), EventDetails.class);

		return response.getBody();
	}

	public ReservationDto reserveSeat(long eventId, long seatId) {

		// String url = String.format("%s/reserve?eventId=%d&seatId=%d", restclientProperties.getPartnerUrl(), eventId, seatId);
		String url = String.format("%s/reserve", restclientProperties.getPartnerUrl());

		RestTemplate restTemplate = new RestTemplate();

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
