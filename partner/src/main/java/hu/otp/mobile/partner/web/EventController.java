package hu.otp.mobile.partner.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.otp.mobile.partner.service.EventService;

@RestController
@RequestMapping("/partner")
public class EventController {

	/** SLF4J Logger */
	private final Logger log = LoggerFactory.getLogger(EventController.class);

	@Autowired
	private EventService eventService;

	@GetMapping("/getEvents")
	ResponseEntity<Object> getEvents() {

		return ResponseEntity.ok(eventService.findEvents());
	}

	@GetMapping("/getEvent")
	ResponseEntity<Object> getEvents(@RequestParam("id") Long eventId) {

		return ResponseEntity.ok(eventService.findEventDetails(eventId));
	}

	@PostMapping("/reserve")
	ResponseEntity<Object> reserve(@RequestParam("eventId") long eventId, @RequestParam("seatId") long seatId) {
		return ResponseEntity.ok(eventService.reserveSeat(eventId, seatId));
	}

}
