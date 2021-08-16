package hu.otp.mobile.ticket.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;
import hu.otp.mobile.common.domain.Seat;
import hu.otp.mobile.common.dto.ReservationDto;
import hu.otp.mobile.ticket.client.CoreClient;
import hu.otp.mobile.ticket.client.PartnerClient;
import hu.otp.mobile.ticket.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private PartnerClient partnerClient;

	@Autowired
	private CoreClient coreClient;

	@Override
	public List<Event> queryEvents() {
		return partnerClient.queryEvents();
	}

	@Override
	public EventDetails queryEventDetails(long eventId) {
		return partnerClient.queryEventDetails(eventId);
	}

	@Override
	public boolean reserveAndPayTicket(long eventId, long seatId, String userToken, String cardId) {

		ReservationDto reservationDto = partnerClient.reserveSeat(eventId, seatId);

		if (!reservationDto.isSuccess()) {
			// TODO: hiba kezelés
			return false;
		}

		EventDetails eventDetails = partnerClient.queryEventDetails(eventId);

		Optional<Seat> seatOpt = eventDetails.getSeats().stream().filter(s -> s.getId().equalsIgnoreCase("s" + seatId)).findFirst();

		if (!seatOpt.isPresent()) {
			// TODO: hiba
		}

		Seat seat = seatOpt.get();

		boolean coreResponse = coreClient.checkPaymentRequirements(userToken, cardId, seat.getPrice());

		if (!coreResponse) {
			// TODO: hiba kezelés
			return false;
		}

		return true;
	}
}
