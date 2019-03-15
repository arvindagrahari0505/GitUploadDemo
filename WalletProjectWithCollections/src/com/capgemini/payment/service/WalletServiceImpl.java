package com.capgemini.payment.service;

import java.math.BigDecimal;

import com.capgemini.payment.beans.Customer;
import com.capgemini.payment.beans.Wallet;
import com.capgemini.payment.exception.ConnectionLostException;
import com.capgemini.payment.exception.InsufficientBalanceException;
import com.capgemini.payment.exception.InvalidMobileNumberException;
import com.capgemini.payment.repository.WalletRepository;

public class WalletServiceImpl implements WalletService {

	private WalletRepository walletRepository;

	public WalletServiceImpl(WalletRepository walletRepository) {
		super();
		this.walletRepository = walletRepository;
	}

	@Override
	public Customer createAccount(String customerName, String mobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException {
		Customer findCustomer = walletRepository.findCustomer(mobileNumber);
		if (findCustomer == null) {
			Customer customer = createCustomer(customerName, mobileNumber, amount);
			if (walletRepository.save(customer) != null) {
				return walletRepository.findCustomer(mobileNumber);
			}
		}
		throw new InvalidMobileNumberException("Mobile number already in use");
	}

	@Override
	public Customer showBalance(String mobileNumber) throws InvalidMobileNumberException {
		Customer customer = walletRepository.findCustomer(mobileNumber);
		if (customer == null) {
			throw new InvalidMobileNumberException("This number is not in use for this application");
		}
		return customer;
	}

	@Override
	public Customer fundTransfer(String sourceMobileNumber, String targetMobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException, InsufficientBalanceException, ConnectionLostException {
		Customer sourceCustomer = walletRepository.findCustomer(sourceMobileNumber);
		Customer targetCustomer = walletRepository.findCustomer(targetMobileNumber);

		if (sourceCustomer == null) {
			throw new InvalidMobileNumberException("Your number is not in use for this application");
		}
		if (targetCustomer == null) {
			throw new InvalidMobileNumberException("Payment Receiver's number is not in use for this application");
		}
		if (sourceCustomer.getWallet().getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException();
		}
		sourceCustomer.getWallet().setBalance(sourceCustomer.getWallet().getBalance().subtract(amount));
		targetCustomer.getWallet().setBalance(targetCustomer.getWallet().getBalance().add(amount));

		if (walletRepository.update(sourceCustomer) != null && walletRepository.update(targetCustomer) != null) {
			return walletRepository.findCustomer(sourceMobileNumber);
		}
		throw new ConnectionLostException();
	}

	@Override
	public Customer depositAmount(String mobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException, ConnectionLostException {
		Customer customer = walletRepository.findCustomer(mobileNumber);
		if (customer == null) {
			throw new InvalidMobileNumberException("This number is not in use for this application");
		}
		customer.getWallet().setBalance(customer.getWallet().getBalance().add(amount));
		if (walletRepository.update(customer) != null) {
			return walletRepository.findCustomer(mobileNumber);
		}
		throw new ConnectionLostException();
	}

	@Override
	public Customer withdrawAmount(String mobileNumber, BigDecimal amount)
			throws InvalidMobileNumberException, InsufficientBalanceException, ConnectionLostException {
		Customer customer = walletRepository.findCustomer(mobileNumber);
		if (customer == null) {
			throw new InvalidMobileNumberException("This number is not in use for this application");
		}
		if (customer.getWallet().getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException();
		}
		customer.getWallet().setBalance(customer.getWallet().getBalance().subtract(amount));
		if (walletRepository.update(customer) != null) {
			return walletRepository.findCustomer(mobileNumber);
		}
		throw new ConnectionLostException();
	}

	private Customer createCustomer(String customerName, String mobileNumber, BigDecimal amount) {
		Customer customer = new Customer();
		Wallet wallet = new Wallet();
		wallet.setWalletId(mobileNumber);
		wallet.setBalance(amount);
		customer.setCustomerName(customerName);
		customer.setMobileNumber(mobileNumber);
		customer.setWallet(wallet);
		return customer;
	}
}
