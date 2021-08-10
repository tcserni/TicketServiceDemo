package hu.otp.mobile.common.dto;

import hu.otp.mobile.common.domain.EventDetails;

public class EventDetailJsonDto {

	private EventDetails data;
	private boolean success;

	public EventDetails getData() {
		return data;
	}

	public void setData(EventDetails data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
