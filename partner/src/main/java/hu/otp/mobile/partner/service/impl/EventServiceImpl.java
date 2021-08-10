package hu.otp.mobile.partner.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;
import hu.otp.mobile.common.util.JsonParser;
import hu.otp.mobile.partner.service.EventService;

@Service
public class EventServiceImpl implements EventService {

	@Override
	public EventDetails findEventDetails(Long eventId) {
		return JsonParser.readEventDetailsWithId(eventId);
	}

	@Override
	public List<Event> findEvents() {
		return JsonParser.readEvents();
	}

}
