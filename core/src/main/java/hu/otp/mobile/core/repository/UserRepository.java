package hu.otp.mobile.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.otp.mobile.core.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
