package hu.otp.mobile.common.domain;

import java.util.List;

public class EventDetails {

	private Long eventId;
	private List<Seat> seats;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

}
