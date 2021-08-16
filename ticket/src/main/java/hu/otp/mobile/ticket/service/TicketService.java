package hu.otp.mobile.ticket.service;

import java.util.List;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;
import hu.otp.mobile.common.dto.ReservationDto;

public interface TicketService {

	List<Event> queryEvents();

	EventDetails queryEventDetails(long eventId);

	ReservationDto reserveAndPayTicket(long eventId, long seatId, String userToken, String cardId);
}
