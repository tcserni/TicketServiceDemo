package hu.otp.mobile.core.service;

import hu.otp.mobile.common.dto.CommonResultDto;

public interface CoreService {

	CommonResultDto validateUserToken(String token);

	CommonResultDto validateCardInfo(String token, String cardId);

	CommonResultDto checkCardAmount(String token, String cardId, int amount);

}
