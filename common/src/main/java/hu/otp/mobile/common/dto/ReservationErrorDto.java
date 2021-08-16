package hu.otp.mobile.common.dto;

public class ReservationErrorDto {

	private boolean success = false;
	private int errorCode;

	public ReservationErrorDto(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
