package hu.otp.mobile.ticket.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.otp.mobile.common.domain.Event;
import hu.otp.mobile.common.domain.EventDetails;
import hu.otp.mobile.ticket.client.PartnerClient;
import hu.otp.mobile.ticket.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private PartnerClient partnerClient;

	@Override
	public List<Event> queryEvents() {
		return partnerClient.queryEvents();
	}

	@Override
	public EventDetails queryEventDetails(long eventId) {
		return partnerClient.queryEventDetails(eventId);
	}

	@Override
	public void reserveAndPayTicket() {
		// TODO Auto-generated method stub

	}
}
