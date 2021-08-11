package hu.otp.mobile.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.otp.mobile.core.domain.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {

}
