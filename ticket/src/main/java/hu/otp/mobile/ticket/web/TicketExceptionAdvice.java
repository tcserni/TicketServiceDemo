package hu.otp.mobile.ticket.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import hu.otp.mobile.common.dto.CommonErrorDto;
import hu.otp.mobile.common.exceptions.OtpErrorMessages;
import hu.otp.mobile.common.exceptions.OtpMobileException;

@ControllerAdvice
public class TicketExceptionAdvice {

	private static final Logger log = LoggerFactory.getLogger(TicketExceptionAdvice.class);

	@ExceptionHandler(OtpMobileException.class)
	public ResponseEntity<CommonErrorDto> handleExceptionNotFound(OtpMobileException e) {

		log.error("An error occured errorCode={}, errorMsg={}", e.getOtpErrorMessage().getErrorCode(),
				e.getOtpErrorMessage().getErrorMessage());

		CommonErrorDto errorDto;

		switch (e.getOtpErrorMessage()) {
		case PARTNER_EVENT_NOT_EXIST:

			errorDto = new CommonErrorDto(OtpErrorMessages.TICKET_EVENT_NOT_EXIST.getErrorCode(),
					OtpErrorMessages.TICKET_EVENT_NOT_EXIST.getErrorMessage());
			break;

		case PARTNER_SEAT_NOT_EXIST:

			errorDto = new CommonErrorDto(OtpErrorMessages.TICKET_SEAT_NOT_EXIST.getErrorCode(),
					OtpErrorMessages.TICKET_SEAT_NOT_EXIST.getErrorMessage());
			break;

		case PARTNER_SEAT_RESERVED:

			errorDto = new CommonErrorDto(OtpErrorMessages.TICKET_SEAT_RESERVED.getErrorCode(),
					OtpErrorMessages.TICKET_SEAT_RESERVED.getErrorMessage());
			break;

		default:
			errorDto = new CommonErrorDto(e.getOtpErrorMessage().getErrorCode(), e.getOtpErrorMessage().getErrorMessage());
		}

		HttpHeaders header = new HttpHeaders();
		header.set("errorCode", String.valueOf(errorDto.getErrorCode()));

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(header).body(errorDto);
	}
}
