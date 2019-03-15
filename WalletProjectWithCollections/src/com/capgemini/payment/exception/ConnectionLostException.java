package com.capgemini.payment.exception;

public class ConnectionLostException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "ConnectionLostException";
	}

	@Override
	public String getMessage() {
		return "Connection problem with server";
	}

}
