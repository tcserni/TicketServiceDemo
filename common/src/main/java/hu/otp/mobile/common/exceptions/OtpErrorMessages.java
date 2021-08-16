package hu.otp.mobile.common.exceptions;

public enum OtpErrorMessages {

	PARTNER_EVENT_NOT_EXIST(90001, "Nem létezik ilyen esemény!"),
	PARTNER_SEAT_NOT_EXIST(90002, "Nem létezik ilyen szék!"),
	PARTNER_SEAT_RESERVED(90010, "Már lefoglalt székre nem lehet jegyet eladni!"),
	CORE_USERTOKEN_NOT_FOUND(10050, "A felhasználói token nem szerepel"),
	CORE_USER_TOKEN_EXPIRED(10051, "A felhasználói token lejárt vagy nem értelmezhető"),
	CORE_UNKNOWN_DEVICE(10052, "Ismeretlen eszköz."),
	CORE_INVALID_USER_TOKEN(10053, "A felhasználói token nem megfelelő."),
	CORE_OWNER_CARD_MISMATCH(10100, "Ez a bankkártya nem ehhez a felhasználóhoz tartozik"),
	CORE_NOT_ENOUGH_COVERAGE(10101, "A felhasználónak nincs elegendő pénze hogy megvásárolja a jegyet!"),
	TICKET_EVENT_NOT_EXIST(20001, "Nem létezik ilyen esemény!"),
	TICKET_SEAT_NOT_EXIST(20002, "Nem létezik ilyen szék!"),
	TICKET_SEAT_RESERVED(20010, "Már lefoglalt székre nem lehet jegyet eladni!"),
	TICKET_EVENT_STARTED(20011, "Olyan eseményre ami már elkezdődött nem lehet jegyet eladni!"),
	PARTNER_NOT_RESPONDING(20404, "A külső rendszer nem elérhető!"),
	UNKNOWN_ERROR(99999, "Ismeretlen hiba.");

	private OtpErrorMessages(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	private int errorCode;
	private String errorMessage;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
