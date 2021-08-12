package hu.otp.mobile.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.otp.mobile.core.service.CoreService;

@RestController
@RequestMapping("/core")
public class CoreController {

	@Autowired
	private CoreService coreService;

	@GetMapping("/userToken")
	ResponseEntity<Object> checkUserToken(@RequestParam("userToken") String userToken) {

		return ResponseEntity.ok(coreService.validateUserToken(userToken));
	}

	@GetMapping("/cardOwner")
	ResponseEntity<Object> validateCardInfo(@RequestParam("userToken") String userToken, @RequestParam("cardId") String cardId) {

		return ResponseEntity.ok(coreService.validateCardInfo(userToken, cardId));
	}

	@GetMapping("/checkPaymentReq")
	ResponseEntity<Object> checkUserToken(@RequestParam("userToken") String userToken, @RequestParam("cardId") String cardId,
			@RequestParam("price") int price) {

		return ResponseEntity.ok(coreService.checkCardAmount(userToken, cardId, price));
	}

}
