package com.capgemini.payment.exception;

public class InvalidMobileNumberException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidMobileNumberException(String str) {
		super(str);
	}

	@Override
	public String toString() {
		return "InvalidMobileNumberException";
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
