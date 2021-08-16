package hu.otp.mobile.api.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import hu.otp.mobile.common.dto.ReservationErrorDto;
import hu.otp.mobile.common.exceptions.OtpMobileException;

@ControllerAdvice
public class ApiExceptionAdvice {

	private static final Logger log = LoggerFactory.getLogger(ApiExceptionAdvice.class);

	@ExceptionHandler(OtpMobileException.class)
	public ResponseEntity<ReservationErrorDto> handleExceptionNotFound(OtpMobileException e) {

		log.error("An error occured errorCode={}, errorMsg={}", e.getOtpErrorMessage().getErrorCode(),
				e.getOtpErrorMessage().getErrorMessage());

		ReservationErrorDto errorDto = new ReservationErrorDto(e.getOtpErrorMessage().getErrorCode());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
	}
}
