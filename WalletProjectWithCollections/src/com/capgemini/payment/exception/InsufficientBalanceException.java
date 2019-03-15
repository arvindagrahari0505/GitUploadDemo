package com.capgemini.payment.exception;

public class InsufficientBalanceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "InsufficientBalanceException";
	}

	@Override
	public String getMessage() {
		return "You have less balance in your account";
	}

}
