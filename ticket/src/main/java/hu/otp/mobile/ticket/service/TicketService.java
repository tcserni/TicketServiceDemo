package hu.otp.mobile.ticket.service;

import java.util.List;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;

public interface TicketService {

	List<Event> queryEvents();

	EventDetails queryEventDetails(long eventId);

	boolean reserveAndPayTicket(long eventId, long seatId, String userToken, String cardId, int price);
}
