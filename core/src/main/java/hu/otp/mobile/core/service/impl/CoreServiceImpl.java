package hu.otp.mobile.core.service.impl;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import hu.otp.mobile.common.dto.CommonResultDto;
import hu.otp.mobile.common.exceptions.OtpErrorMessages;
import hu.otp.mobile.common.exceptions.OtpMobileException;
import hu.otp.mobile.core.domain.User;
import hu.otp.mobile.core.domain.UserBankCard;
import hu.otp.mobile.core.domain.UserDevice;
import hu.otp.mobile.core.domain.UserToken;
import hu.otp.mobile.core.dto.UserDto;
import hu.otp.mobile.core.repository.UserBankCardRepository;
import hu.otp.mobile.core.repository.UserDeviceRepository;
import hu.otp.mobile.core.repository.UserRepository;
import hu.otp.mobile.core.repository.UserTokenRepository;
import hu.otp.mobile.core.service.CoreService;

@Service
public class CoreServiceImpl implements CoreService {

	/** SLF4J Logger */
	private final Logger log = LoggerFactory.getLogger(CoreServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserTokenRepository userTokenRepository;
	@Autowired
	private UserDeviceRepository userDeviceRepository;
	@Autowired
	private UserBankCardRepository userBankCardRepository;

	private UserDto userTokenDecode(String token) {

		UserDto userDto = new UserDto();

		String decodedToken = new String(Base64.getDecoder().decode(token));
		List<String> decodedTokenList = Arrays.asList(decodedToken.split("&"));

		userDto.setEmail(decodedTokenList.get(0));
		userDto.setUserId(Integer.parseInt(decodedTokenList.get(1)));
		userDto.setDeviceHash(decodedTokenList.get(2));

		return userDto;
	}

	private boolean validateCardOwner(Integer userId, String cardId) {

		Optional<UserBankCard> cardOpt = userBankCardRepository.findByUserIdAndCardId(userId, cardId);

		return cardOpt.isPresent();
	}

	@Override
	public CommonResultDto validateUserToken(String token) {

		if (token.isEmpty()) {

			log.error("User token is empty.");
			throw new OtpMobileException(OtpErrorMessages.CORE_USER_TOKEN_EXPIRED);

		}

		Optional<UserToken> userTokenOpt = userTokenRepository.findByToken(token);

		if (!userTokenOpt.isPresent()) {

			log.error("UserToken not found.");
			throw new OtpMobileException(OtpErrorMessages.CORE_USER_TOKEN_EXPIRED);

		}

		UserToken userToken = userTokenOpt.get();
		UserDto userDto = userTokenDecode(userToken.getToken());

		if (!userDto.getUserId().equals(userToken.getUserId())) {

			log.error("UserId mismatch userToken={}.", token);
			throw new OtpMobileException(OtpErrorMessages.CORE_INVALID_USER_TOKEN);

		}

		Optional<UserDevice> userDeviceOpt = userDeviceRepository.findByUserIdAndDeviceHash(userDto.getUserId(), userDto.getDeviceHash());

		if (!userDeviceOpt.isPresent()) {

			log.error("UserDevice mismatch userToken={}.", token);
			throw new OtpMobileException(OtpErrorMessages.CORE_UNKNOWN_DEVICE);

		}

		Optional<User> userOpt = userRepository.findByUserIdAndEmail(userDto.getUserId(), userDto.getEmail());

		if (!userOpt.isPresent()) {

			log.error("User data mismatch userToken={}.", token);
			throw new OtpMobileException(OtpErrorMessages.CORE_OWNER_CARD_MISMATCH);

		}

		return new CommonResultDto(true);
	}

	@Override
	public CommonResultDto validateCardInfo(String token, String cardId) {

		Assert.notNull(token, "Token is null.");

		UserDto userDto = userTokenDecode(token);

		if (validateCardOwner(userDto.getUserId(), cardId)) {
			return new CommonResultDto(true);
		} else {

			log.error("Card owner mismatch userToken={}, cardId={}.", token, cardId);
			throw new OtpMobileException(OtpErrorMessages.CORE_OWNER_CARD_MISMATCH);

		}

	}

	@Override
	public CommonResultDto checkCardAmount(String token, String cardId, int amount) {

		Assert.notNull(token, "Token is null.");

		UserDto userDto = userTokenDecode(token);

		if (!validateCardOwner(userDto.getUserId(), cardId)) {

			log.error("Card owner mismatch userToken={}, cardId={}.", token, cardId);
			throw new OtpMobileException(OtpErrorMessages.CORE_OWNER_CARD_MISMATCH);

		}

		UserBankCard userBankCard = userBankCardRepository.findByUserIdAndCardId(userDto.getUserId(), cardId).get();

		if (amount > userBankCard.getAmount()) {

			log.error("Not enough coverage userToken={}, cardId={}.", token, cardId);
			throw new OtpMobileException(OtpErrorMessages.CORE_NOT_ENOUGH_COVERAGE);

		}

		return new CommonResultDto(true);
	}
}
