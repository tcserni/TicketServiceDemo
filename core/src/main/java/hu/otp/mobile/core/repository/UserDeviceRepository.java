package hu.otp.mobile.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.otp.mobile.core.domain.UserDevice;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Integer> {

	Optional<UserDevice> findByUserIdAndDeviceHash(Integer userId, String deviceHash);
}
