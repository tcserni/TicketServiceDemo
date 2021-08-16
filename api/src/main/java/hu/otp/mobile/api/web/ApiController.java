package hu.otp.mobile.api.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.otp.mobile.api.service.ApiService;

@RestController
@RequestMapping("/api")
public class ApiController {

	/** SLF4J Logger */
	private final Logger log = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private ApiService apiService;

	@GetMapping("/getEvents")
	ResponseEntity<Object> getEvents() {

		return ResponseEntity.ok(apiService.queryEvents());
	}

	@GetMapping("/getEvent")
	ResponseEntity<Object> getEvents(@RequestParam("eventId") Long eventId) {

		return ResponseEntity.ok(apiService.queryEventDetails(eventId));
	}

	@PostMapping("/pay")
	ResponseEntity<Object> reserve(@RequestHeader("userToken") String userToken, @RequestParam("eventId") long eventId,
			@RequestParam("seatId") long seatId, @RequestParam("cardId") String cardId) {
		return ResponseEntity.ok(apiService.payTicket(eventId, seatId, userToken, cardId));
	}
}
