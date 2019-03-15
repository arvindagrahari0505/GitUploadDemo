package com.capgemini.payment.view;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.payment.exception.ConnectionLostException;
import com.capgemini.payment.exception.InsufficientBalanceException;
import com.capgemini.payment.exception.InvalidMobileNumberException;
import com.capgemini.payment.repository.WalletRepository;
import com.capgemini.payment.repository.WalletRepositoryImpl;
import com.capgemini.payment.service.WalletService;
import com.capgemini.payment.service.WalletServiceImpl;

public class MainApp {

	private static WalletRepository walletRepository = new WalletRepositoryImpl();
	private static WalletService walletService = new WalletServiceImpl(walletRepository);
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		showMenu();
	}

	private static void showMenu() {
		int choice;
		System.out.println("****************Welcome to wallet project***************");
		while (true) {
			System.out.println("1.Create a new account");
			System.out.println("2.Show Balance");
			System.out.println("3.Fund Transfer");
			System.out.println("4.Deposit Amount");
			System.out.println("5.Withdraw Amount");
			System.out.println("6.Exit");

			System.out.print("Enter your choice : ");
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				createAccount();
				break;
			case 2:
				showBalance();
				break;
			case 3:
				fundTransfer();
				break;
			case 4:
				depositAmount();
				break;
			case 5:
				withdrawAmount();
				break;
			case 6:
				System.out.println("Thank you for using this application!!");
				System.exit(0);
				break;
			default:
				System.out.println("***********You enter a wrong choice !!***************");

			}
		}

	}

	private static void createAccount() {
		String mobileNumber;
		String customerName;
		String amountString;
		do {
			System.out.print("Enter a valid mobile number : ");
			mobileNumber = sc.next();
		} while (!validateMobileNumber(mobileNumber));

		sc.nextLine();
		do {
			System.out.print("Enter a your name : ");
			customerName = sc.nextLine();
		} while (!validateCustomerName(customerName));

		do {
			System.out.print("Enter amount : ");
			amountString = sc.next();
		} while (!validateAmount(amountString));

		BigDecimal amount = new BigDecimal(amountString);

		try {
			System.out.println(walletService.createAccount(customerName, mobileNumber, amount));
		} catch (InvalidMobileNumberException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void showBalance() {
		String mobileNumber;
		do {
			System.out.print("Enter your valid mobile number : ");
			mobileNumber = sc.next();
		} while (!validateMobileNumber(mobileNumber));

		try {
			System.out.println(walletService.showBalance(mobileNumber));
		} catch (InvalidMobileNumberException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void fundTransfer() {
		String sourceMobileNumber;
		String targetMobileNumber;
		String amountString;
		do {
			System.out.print("Enter your valid mobile number : ");
			sourceMobileNumber = sc.next();
		} while (!validateMobileNumber(sourceMobileNumber));

		do {
			System.out.print("Enter other one valid mobile number : ");
			targetMobileNumber = sc.next();
		} while (!validateMobileNumber(targetMobileNumber));

		do {
			System.out.print("Enter amount : ");
			amountString = sc.next();
		} while (!validateAmount(amountString));

		BigDecimal amount = new BigDecimal(amountString);

		try {
			System.out.println(walletService.fundTransfer(sourceMobileNumber, targetMobileNumber, amount));
		} catch (InvalidMobileNumberException | InsufficientBalanceException | ConnectionLostException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void depositAmount() {
		String mobileNumber;
		String amountString;
		do {
			System.out.print("Enter your valid mobile number : ");
			mobileNumber = sc.next();
		} while (!validateMobileNumber(mobileNumber));

		do {
			System.out.print("Enter amount : ");
			amountString = sc.next();
		} while (!validateAmount(amountString));

		BigDecimal amount = new BigDecimal(amountString);

		try {
			System.out.println(walletService.depositAmount(mobileNumber, amount));
		} catch (InvalidMobileNumberException | ConnectionLostException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void withdrawAmount() {
		String mobileNumber;
		String amountString;
		do {
			System.out.print("Enter your valid mobile number : ");
			mobileNumber = sc.next();
		} while (!validateMobileNumber(mobileNumber));

		do {
			System.out.print("Enter amount : ");
			amountString = sc.next();
		} while (!validateAmount(amountString));

		BigDecimal amount = new BigDecimal(amountString);

		try {
			System.out.println(walletService.withdrawAmount(mobileNumber, amount));
		} catch (InvalidMobileNumberException | InsufficientBalanceException | ConnectionLostException e) {
			System.out.println(e.getMessage());
		}
	}

	private static boolean validateMobileNumber(String mobileNumber) {
		Pattern p = Pattern.compile("[6-9][0-9]{9}");
		Matcher m = p.matcher(mobileNumber);
		return (m.find() && m.group().equals(mobileNumber)) && (mobileNumber.length() == 10);
	}

	private static boolean validateCustomerName(String customerName) {
		return customerName == null || customerName.equals("") ? false : true;
	}

	private static boolean validateAmount(String amount) {
		Pattern p = Pattern.compile("[0-9]+.?[0-9]*");
		Matcher m = p.matcher(amount);
		return (m.find() && m.group().equals(amount));
	}

}
