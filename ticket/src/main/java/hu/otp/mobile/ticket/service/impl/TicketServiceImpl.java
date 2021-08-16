package hu.otp.mobile.ticket.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;
import hu.otp.mobile.common.domain.Seat;
import hu.otp.mobile.common.dto.ReservationDto;
import hu.otp.mobile.common.exceptions.OtpErrorMessages;
import hu.otp.mobile.common.exceptions.OtpMobileException;
import hu.otp.mobile.ticket.client.CoreClient;
import hu.otp.mobile.ticket.client.PartnerClient;
import hu.otp.mobile.ticket.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	/** SLF4J Logger */
	private final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

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
	public ReservationDto reserveAndPayTicket(long eventId, long seatId, String userToken, String cardId) {

		List<Event> eventList = partnerClient.queryEvents();

		Optional<Event> eventOpt = eventList.stream().filter(e -> e.getEventId().equals(eventId)).findFirst();

		if (!eventOpt.isPresent()) {

			log.error("Event not exists eventId={}", eventId);
			throw new OtpMobileException(OtpErrorMessages.TICKET_EVENT_NOT_EXIST);

		}

		Event event = eventOpt.get();
		Date eventStartTime = new Date(Long.parseLong(event.getStartTimeStamp()) * 1000);

		if (eventStartTime.before(new Date())) {

			log.error("Event has already started eventId={}", eventId);
			throw new OtpMobileException(OtpErrorMessages.TICKET_EVENT_STARTED);

		}

		EventDetails eventDetails = partnerClient.queryEventDetails(eventId);
		Optional<Seat> seatOpt = eventDetails.getSeats().stream().filter(s -> s.getId().equalsIgnoreCase("s" + seatId)).findFirst();

		if (!seatOpt.isPresent()) {

			log.error("Seat not exists eventId={}, seatId={}", eventId, seatId);
			throw new OtpMobileException(OtpErrorMessages.TICKET_SEAT_NOT_EXIST);

		}

		Seat seat = seatOpt.get();

		coreClient.checkPaymentRequirements(userToken, cardId, seat.getPrice());

		return partnerClient.reserveSeat(eventId, seatId);
	}
}
