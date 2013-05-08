package com.azusasoft.lenit.error;

public class RestApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3401892663000976637L;
	
	private String mIntend;

	public RestApiException(String message, String intend) {
		super(message);
		mIntend = intend;
	}

	public RestApiException(Throwable e, String intend) {
		super(e);
		mIntend = intend;
	}

	@Override
	public String getMessage() {
		return String.format("%s\nIntended to %s", super.getMessage(), mIntend);
	}
	
}
