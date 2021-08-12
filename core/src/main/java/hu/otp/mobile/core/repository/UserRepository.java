package hu.otp.mobile.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.otp.mobile.core.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserIdAndEmail(Integer userId, String email);
}
