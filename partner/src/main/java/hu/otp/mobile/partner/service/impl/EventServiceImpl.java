package hu.otp.mobile.partner.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;
import hu.otp.mobile.common.domain.Seat;
import hu.otp.mobile.common.dto.ReservationDto;
import hu.otp.mobile.common.util.JsonParser;
import hu.otp.mobile.partner.service.EventService;

@Service
public class EventServiceImpl implements EventService {

	@Override
	public EventDetails findEventDetails(long eventId) {
		return JsonParser.readEventDetailsWithId(eventId);
	}

	@Override
	public List<Event> findEvents() {
		return JsonParser.readEvents();
	}

	@Override
	public ReservationDto reserveSeat(long eventId, long seatId) {

		ReservationDto result = new ReservationDto();
		EventDetails eventDetails = JsonParser.readEventDetailsWithId(eventId);
		Seat seat = eventDetails.getSeats().stream().filter(s -> s.getId().equalsIgnoreCase("s" + seatId)).findFirst().orElse(null);

		if (seat == null) {

			// TODO: egyedi exception
			result.setSuccess(false);
			throw new RuntimeException();

		} else if (seat.isReserved()) {

			// TODO: egyedi exception
			result.setSuccess(false);
			throw new RuntimeException();

		} else {

			result.setReserver(new Random().nextLong());
			result.setSuccess(true);

		}

		return result;
	}
}
