package hu.otp.mobile.ticket.service;

import java.util.List;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;

public interface TicketService {

	List<Event> queryEvents();

	EventDetails queryEventDetails(long eventId);

	void reserveAndPayTicket();
}
