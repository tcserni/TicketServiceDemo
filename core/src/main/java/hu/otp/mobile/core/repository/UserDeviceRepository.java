package hu.otp.mobile.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.otp.mobile.core.domain.UserDevice;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Integer> {

}
