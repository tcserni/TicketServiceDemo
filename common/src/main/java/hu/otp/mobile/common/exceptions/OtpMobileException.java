package hu.otp.mobile.common.exceptions;

public class OtpMobileException extends RuntimeException {

	/** Serial */
	private static final long serialVersionUID = -5204506459454343990L;

	private OtpErrorMessages otpErrorMessage;

	public OtpMobileException(OtpErrorMessages otpErrorMessage) {
		super();
		this.otpErrorMessage = otpErrorMessage;
	}

	public OtpErrorMessages getOtpErrorMessage() {
		return otpErrorMessage;
	}

}
