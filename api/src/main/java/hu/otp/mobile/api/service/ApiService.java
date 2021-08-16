package hu.otp.mobile.api.service;

import java.util.List;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;
import hu.otp.mobile.common.dto.ReservationDto;

public interface ApiService {

	List<Event> queryEvents();

	EventDetails queryEventDetails(long eventId);

	ReservationDto payTicket(long eventId, long seatId, String userToken, String cardId);
}
