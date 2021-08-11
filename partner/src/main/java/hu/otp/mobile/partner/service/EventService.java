package hu.otp.mobile.partner.service;

import java.util.List;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;
import hu.otp.mobile.common.dto.ReservationDto;

public interface EventService {

	EventDetails findEventDetails(long eventId);

	List<Event> findEvents();

	ReservationDto reserveSeat(long eventId, long seatId);
}
