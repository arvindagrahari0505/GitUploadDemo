package com.capgemini.payment.service;

import java.math.BigDecimal;

import com.capgemini.payment.beans.Customer;
import com.capgemini.payment.exception.ConnectionLostException;
import com.capgemini.payment.exception.InsufficientBalanceException;
import com.capgemini.payment.exception.InvalidMobileNumberException;

public interface WalletService {

	public Customer createAccount(String customerName, String mobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException;

	public Customer showBalance(String mobileNumber) throws InvalidMobileNumberException;

	public Customer fundTransfer(String sourceMobileNumber, String targetMobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException, InsufficientBalanceException, ConnectionLostException;

	public Customer depositAmount(String mobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException, ConnectionLostException;

	public Customer withdrawAmount(String mobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException, InsufficientBalanceException, ConnectionLostException;
}
