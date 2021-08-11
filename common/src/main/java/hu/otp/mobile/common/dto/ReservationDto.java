package hu.otp.mobile.common.dto;

public class ReservationDto {

	private Long reserver;
	private boolean success;

	public Long getReserver() {
		return reserver;
	}

	public void setReserver(Long reserver) {
		this.reserver = reserver;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
