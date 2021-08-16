package hu.otp.mobile.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.otp.mobile.api.client.CoreClient;
import hu.otp.mobile.api.client.TicketClient;
import hu.otp.mobile.api.service.ApiService;
import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;
import hu.otp.mobile.common.dto.ReservationDto;

@Service
public class ApiServiceImpl implements ApiService {

	@Autowired
	private TicketClient ticketClient;
	@Autowired
	private CoreClient coreClient;

	@Override
	public List<Event> queryEvents() {
		return ticketClient.queryEvents();
	}

	@Override
	public EventDetails queryEventDetails(long eventId) {
		return ticketClient.queryEventDetails(eventId);
	}

	@Override
	public ReservationDto payTicket(long eventId, long seatId, String userToken, String cardId) {
		return ticketClient.payTicket(eventId, seatId, userToken, cardId);
	}

}
