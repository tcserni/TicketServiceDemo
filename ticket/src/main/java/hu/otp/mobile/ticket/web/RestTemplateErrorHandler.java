package hu.otp.mobile.ticket.web;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import hu.otp.mobile.common.exceptions.OtpErrorMessages;
import hu.otp.mobile.common.exceptions.OtpMobileException;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

		return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
				|| httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {

		if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {

			if (httpResponse.getHeaders().containsKey("errorCode")) {

				OtpErrorMessages[] errorList = OtpErrorMessages.values();
				OtpErrorMessages errorMessage = OtpErrorMessages.UNKNOWN_ERROR;

				for (int i = 0; i < errorList.length; i++) {

					int errorCodeFromHeader = Integer.valueOf(httpResponse.getHeaders().get("errorCode").get(0));
					if (errorList[i].getErrorCode() == errorCodeFromHeader) {

						errorMessage = errorList[i];
						break;

					}
				}

				throw new OtpMobileException(errorMessage);
			}
		}
	}
}