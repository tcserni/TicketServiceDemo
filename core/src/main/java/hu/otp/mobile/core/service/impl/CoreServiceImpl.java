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
	public boolean validateUserToken(String token) {

		Optional<UserToken> userTokenOpt = userTokenRepository.findByToken(token);

		if (!userTokenOpt.isPresent()) {
			// TODO: exception
			log.error("UserToken not found.");
		}

		UserToken userToken = userTokenOpt.get();
		UserDto userDto = userTokenDecode(userToken.getToken());

		if (!userDto.getUserId().equals(userToken.getUserId())) {
			// TODO: userId nem egyezik
			log.error("UserId mismatch.");
		}

		Optional<UserDevice> userDeviceOpt = userDeviceRepository.findByUserIdAndDeviceHash(userDto.getUserId(), userDto.getDeviceHash());

		if (!userDeviceOpt.isPresent()) {
			// TODO: userDevice nem egyezik
			log.error("UserDevice mismatch.");
		}

		Optional<User> userOpt = userRepository.findByUserIdAndEmail(userDto.getUserId(), userDto.getEmail());

		if (!userOpt.isPresent()) {
			// TODO: user nem egyezik
			log.error("User data mismatch.");
		}

		return true;
	}

	@Override
	public boolean validateCardInfo(String token, String cardId) {

		Assert.notNull(token, "Token is null.");

		UserDto userDto = userTokenDecode(token);

		if (validateCardOwner(userDto.getUserId(), cardId)) {
			return true;
		} else {
			// TODO: kártya tulaj nem egyezik
			log.error("Card owner mismatch.");
			return false;
		}

	}

	@Override
	public boolean checkCardAmount(String token, String cardId, int amount) {

		Assert.notNull(token, "Token is null.");

		UserDto userDto = userTokenDecode(token);

		if (!validateCardOwner(userDto.getUserId(), cardId)) {
			// TODO: kártya tulaj nem egyezik
			log.error("Card owner mismatch.");
			return false;
		}

		UserBankCard userBankCard = userBankCardRepository.findByUserIdAndCardId(userDto.getUserId(), cardId).get();

		if (amount > userBankCard.getAmount()) {
			// TODO: nincs elegendő pénz
			log.info("Not enough.");
			return false;
		}

		return true;
	}
}
