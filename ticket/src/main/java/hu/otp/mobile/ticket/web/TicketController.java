package hu.otp.mobile.ticket.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.otp.mobile.ticket.service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {

	/** SLF4J Logger */
	private final Logger log = LoggerFactory.getLogger(TicketController.class);

	@Autowired
	private TicketService ticketService;

	@GetMapping("/getEvents")
	ResponseEntity<Object> getEvents() {

		return ResponseEntity.ok(ticketService.queryEvents());
	}

	@GetMapping("/getEvent")
	ResponseEntity<Object> getEvents(@RequestParam("id") Long eventId) {

		return ResponseEntity.ok(ticketService.queryEventDetails(eventId));
	}

	@PostMapping("/reserve")
	ResponseEntity<Object> reserve(@RequestParam("eventId") long eventId, @RequestParam("seatId") long seatId,
			@RequestParam("userToken") String userToken, @RequestParam("cardId") String cardId) {
		return ResponseEntity.ok(ticketService.reserveAndPayTicket(eventId, seatId, userToken, cardId));
	}

}
