package hu.otp.mobile.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import hu.otp.mobile.common.dto.CommonErrorDto;
import hu.otp.mobile.common.exceptions.OtpMobileException;

@ControllerAdvice
public class CoreExceptionAdvice {

	private static final Logger log = LoggerFactory.getLogger(CoreExceptionAdvice.class);

	@ExceptionHandler(OtpMobileException.class)
	public ResponseEntity<CommonErrorDto> handleExceptionNotFound(OtpMobileException e) {

		log.error("Error occured during validation errorCode={}, errorMsg={}", e.getOtpErrorMessage().getErrorCode(),
				e.getOtpErrorMessage().getErrorMessage());

		CommonErrorDto errorDto = new CommonErrorDto(e.getOtpErrorMessage().getErrorCode(), e.getOtpErrorMessage().getErrorMessage());

		HttpHeaders header = new HttpHeaders();
		header.set("errorCode", String.valueOf(errorDto.getErrorCode()));

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(header).body(errorDto);
	}

}
