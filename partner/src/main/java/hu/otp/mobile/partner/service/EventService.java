package hu.otp.mobile.partner.service;

import java.util.List;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;

public interface EventService {

	EventDetails findEventDetails(Long eventId);

	List<Event> findEvents();
}
