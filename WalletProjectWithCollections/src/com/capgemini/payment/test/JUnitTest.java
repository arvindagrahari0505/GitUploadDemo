package com.capgemini.payment.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.payment.beans.Customer;
import com.capgemini.payment.beans.Wallet;
import com.capgemini.payment.exception.ConnectionLostException;
import com.capgemini.payment.exception.InsufficientBalanceException;
import com.capgemini.payment.exception.InvalidMobileNumberException;
import com.capgemini.payment.repository.WalletRepository;
import com.capgemini.payment.repository.WalletRepositoryImpl;
import com.capgemini.payment.service.WalletService;
import com.capgemini.payment.service.WalletServiceImpl;

public class JUnitTest {

	private WalletRepository walletRepository;
	private WalletService walletService;

	@Before
	public void setUp() throws Exception {
		walletRepository = new WalletRepositoryImpl();
		walletService = new WalletServiceImpl(walletRepository);
	}

	/*
	 * Create a new account 
	 * 1.When Mobile Number already in use for other person
	 * 2.When All the information is correct
	 */

	@Test(expected = com.capgemini.payment.exception.InvalidMobileNumberException.class)
	public void whenMobileNumberIsAlreadyUsedForAnyOtherCustomerThenSystemShouldThrowAnException()
			throws ConnectionLostException, InvalidMobileNumberException {
		walletService.createAccount("Rajan", "7000000000", new BigDecimal("1000"));
		walletService.createAccount("Rajan", "7000000000", new BigDecimal("1000"));
	}

	@Test
	public void whenCustomerInformationIsCorrectThenSystemShouldCreateAnAccount()
			throws ConnectionLostException, InvalidMobileNumberException {
		Customer customer = new Customer();
		Wallet wallet = new Wallet();
		customer.setMobileNumber("7000000001");
		customer.setCustomerName("Ram");
		wallet.setWalletId("7000000001");
		wallet.setBalance(new BigDecimal("2000"));
		customer.setWallet(wallet);
		assertEquals(customer, walletService.createAccount("Ram", "7000000001", new BigDecimal("2000")));
	}

	/*
	 * Show balance 
	 * 1.When Your Mobile Number is not in use 
	 * 2.When All the information is correct
	 */

	@Test(expected = com.capgemini.payment.exception.InvalidMobileNumberException.class)
	public void whenMobileNumberIsUsedForShowBalanceIsNotInDatabaseThenSystemShouldThrowAnException()
			throws InvalidMobileNumberException, ConnectionLostException {
		walletService.createAccount("Ram", "7000000002", new BigDecimal("2000"));
		walletService.showBalance("7000000019");
	}

	@Test
	public void whenCustomerInformationIsCorrectThenSystemShouldShowTheBalance()
			throws InvalidMobileNumberException, ConnectionLostException {
		Customer customer = new Customer();
		Wallet wallet = new Wallet();
		customer.setMobileNumber("7000000003");
		customer.setCustomerName("Ram");
		wallet.setWalletId("7000000003");
		wallet.setBalance(new BigDecimal("2000"));
		customer.setWallet(wallet);
		walletService.createAccount("Ram", "7000000003", new BigDecimal("2000"));
		assertEquals(customer, walletService.showBalance("7000000003"));

	}

	/*
	 * Fund Transfer 
	 * 1.When Your Mobile Number is not in use 
	 * 2.When receiver mobile number is not in use 
	 * 3.When have less amount in account 
	 * 4.When All the information is correct
	 */

	@Test(expected = com.capgemini.payment.exception.InvalidMobileNumberException.class)
	public void whenYourMobileNumberIsUsedForFundTransferIsNotInDatabaseThenSystemShouldThrowAnException()
			throws InvalidMobileNumberException, InsufficientBalanceException, ConnectionLostException {
		walletService.createAccount("Rajan", "7000000004", new BigDecimal("1000"));
		walletService.createAccount("Rajan", "7000000005", new BigDecimal("1000"));
		walletService.fundTransfer("7890654378", "7000000005", new BigDecimal("100"));
	}

	@Test(expected = com.capgemini.payment.exception.InvalidMobileNumberException.class)
	public void whenPaymentReceiverMobileNumberIsUsedForFundTransferIsNotInDatabaseThenSystemShouldThrowAnException()
			throws InvalidMobileNumberException, InsufficientBalanceException, ConnectionLostException {
		walletService.createAccount("Rajan", "7000000020", new BigDecimal("1000"));
		walletService.createAccount("Rajan", "7000000021", new BigDecimal("1000"));
		walletService.fundTransfer("7000000020", "7890654378", new BigDecimal("100"));
	}

	@Test(expected = com.capgemini.payment.exception.InsufficientBalanceException.class)
	public void whenThereIsLessMoneyInTheAccountForFundTransferThenSystemShouldThrowAnException()
			throws InvalidMobileNumberException, InsufficientBalanceException, ConnectionLostException {
		walletService.createAccount("Rajan", "7000000006", new BigDecimal("1000"));
		walletService.createAccount("Rajan", "7000000007", new BigDecimal("1000"));
		walletService.fundTransfer("7000000006", "7000000007", new BigDecimal("5000"));
	}

	@Test
	public void whenCustomerInformationIsCorrectThenSystemShouldTransferFundFromOneAccountToAnother()
			throws InvalidMobileNumberException, InsufficientBalanceException, ConnectionLostException {
		Customer customer = new Customer();
		Wallet wallet = new Wallet();
		customer.setMobileNumber("7000000008");
		customer.setCustomerName("Ram");
		wallet.setWalletId("7000000008");
		wallet.setBalance(new BigDecimal("1900"));
		customer.setWallet(wallet);
		walletService.createAccount("Ram", "7000000008", new BigDecimal("2000"));
		walletService.createAccount("Rajan", "7000000009", new BigDecimal("1000"));
		assertEquals(customer, walletService.fundTransfer("7000000008", "7000000009", new BigDecimal("100")));
	}

	/*
	 * Deposit Amount 
	 * 1.When Your Mobile Number is not in use 
	 * 2.When All the information is correct
	 */

	@Test(expected = com.capgemini.payment.exception.InvalidMobileNumberException.class)
	public void whenMobileNumberIsUsedForDepositIsNotInDatabaseThenSystemShouldThrowAnException()
			throws InvalidMobileNumberException, ConnectionLostException {
		walletService.createAccount("Rajan", "7000000010", new BigDecimal("1000"));
		walletService.depositAmount("7890654374", new BigDecimal("100"));
	}

	@Test
	public void whenCustomerInformationIsCorrectThenSystemShouldDepositMoneyToAccount()
			throws InvalidMobileNumberException, ConnectionLostException {
		Customer customer = new Customer();
		Wallet wallet = new Wallet();
		customer.setMobileNumber("7000000011");
		customer.setCustomerName("Rajan");
		wallet.setWalletId("7000000011");
		wallet.setBalance(new BigDecimal("1500"));
		customer.setWallet(wallet);
		walletService.createAccount("Rajan", "7000000011", new BigDecimal("1000"));
		assertEquals(customer, walletService.depositAmount("7000000011", new BigDecimal("500")));
	}

	/*
	 * Withdraw Amount 
	 * 1.When Your Mobile Number is not in use 
	 * 2.When have less amount in account 
	 * 3.When All the information is correct
	 */

	@Test(expected = com.capgemini.payment.exception.InvalidMobileNumberException.class)
	public void whenMobileNumberIsUsedForWithdrawIsNotInDatabaseThenSystemShouldThrowAnException()
			throws InvalidMobileNumberException, InsufficientBalanceException, ConnectionLostException {
		walletService.createAccount("Rajan", "7000000012", new BigDecimal("1000"));
		walletService.withdrawAmount("7890654302", new BigDecimal("100"));
	}

	@Test(expected = com.capgemini.payment.exception.InsufficientBalanceException.class)
	public void whenThereIsLessMoneyInTheAccountForWithdrawThenSystemShouldThrowAnException()
			throws InvalidMobileNumberException, InsufficientBalanceException, ConnectionLostException {
		walletService.createAccount("Rajan", "7000000013", new BigDecimal("1000"));
		walletService.withdrawAmount("7000000013", new BigDecimal("2000"));
	}

	@Test
	public void whenCustomerInformationIsCorrectThenSystemShouldWithdrawAmountFromAccount()
			throws InvalidMobileNumberException, InsufficientBalanceException, ConnectionLostException {
		Customer customer = new Customer();
		Wallet wallet = new Wallet();
		customer.setMobileNumber("7000000014");
		customer.setCustomerName("Rajan");
		wallet.setWalletId("7000000014");
		wallet.setBalance(new BigDecimal("900"));
		customer.setWallet(wallet);
		walletService.createAccount("Rajan", "7000000014", new BigDecimal("1000"));
		assertEquals(customer, walletService.withdrawAmount("7000000014", new BigDecimal("100")));
	}

}
