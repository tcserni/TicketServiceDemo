package hu.otp.mobile.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.otp.mobile.core.domain.UserBankCard;

public interface UserBankCardRepository extends JpaRepository<UserBankCard, Integer> {

}
