package hu.otp.mobile.common.dto;

import java.util.List;

import hu.otp.mobile.common.domain.Event;

public class EventJsonDto {

	private List<Event> data;
	private boolean success;

	public List<Event> getData() {
		return data;
	}

	public void setData(List<Event> data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
