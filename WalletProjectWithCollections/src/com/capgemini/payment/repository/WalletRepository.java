package com.capgemini.payment.repository;

import com.capgemini.payment.beans.Customer;

public interface WalletRepository {

	public Customer save(Customer customer);
	
	public Customer update(Customer customer);

	public Customer findCustomer(String mobileNumber);

}
