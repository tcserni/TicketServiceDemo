package hu.otp.mobile.core.service;

public interface CoreService {

	boolean validateUserToken(String token);

	boolean validateCardInfo(String token, String cardId);

	boolean checkCardAmount(String token, String cardId, int amount);

}
