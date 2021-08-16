package hu.otp.mobile.common.dto;

public class CommonResultDto {

	private boolean success;

	public CommonResultDto() {
		super();
	}

	public CommonResultDto(boolean success) {
		super();
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
