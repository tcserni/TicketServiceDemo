package hu.otp.mobile.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;
import hu.otp.mobile.common.dto.EventDetailJsonDto;
import hu.otp.mobile.common.dto.EventJsonDto;
import hu.otp.mobile.common.exceptions.OtpErrorMessages;
import hu.otp.mobile.common.exceptions.OtpMobileException;

public class JsonParser {

	/** SLF4J Logger */
	private final static Logger log = LoggerFactory.getLogger(JsonParser.class);

	private static <T> T parseJson(String resourcePath, Class<T> classType) {

		ObjectMapper objectMapper = new ObjectMapper();
		T parsedJson = null;

		try {

			parsedJson = objectMapper.readValue(new ClassPathResource(resourcePath).getInputStream(), classType);

		} catch (FileNotFoundException ex) {

			log.error("Resource not found with path={}", resourcePath);
			throw new OtpMobileException(OtpErrorMessages.PARTNER_EVENT_NOT_EXIST);

		} catch (IOException e) {

			log.error("Error occured during object mapping");
			throw new RuntimeException(e);
		}

		return parsedJson;
	}

	public static EventDetails readEventDetailsWithId(Long eventId) {

		String eventDetailsPath = String.format("data/getEvent%d.json", eventId);

		EventDetailJsonDto parsedJson = parseJson(eventDetailsPath, EventDetailJsonDto.class);
		EventDetails result = null;

		if (parsedJson != null) {
			result = parsedJson.getData();
		}

		return result;
	}

	public static List<Event> readEvents() {

		String eventsPath = "data/getEvents.json";

		EventJsonDto parsedJson = parseJson(eventsPath, EventJsonDto.class);
		List<Event> result = new ArrayList<>();

		if (parsedJson != null) {
			result.addAll(parsedJson.getData());
		}

		return result;
	}

}
