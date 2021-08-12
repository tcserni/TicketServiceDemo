package hu.otp.mobile.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.otp.mobile.core.domain.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {

	Optional<UserToken> findByUserId(int userId);

	Optional<UserToken> findByToken(String token);
}
