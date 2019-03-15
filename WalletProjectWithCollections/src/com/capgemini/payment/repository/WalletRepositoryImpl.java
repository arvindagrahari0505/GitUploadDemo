package com.capgemini.payment.repository;

import java.util.HashMap;
import java.util.Map;

import com.capgemini.payment.beans.Customer;

public class WalletRepositoryImpl implements WalletRepository {

	private Map<String, Customer> mapRepo = new HashMap<>();

	@Override
	public Customer save(Customer customer) {
		mapRepo.put(customer.getMobileNumber(), customer);
		return mapRepo.get(customer.getMobileNumber());
	}

	@Override
	public Customer update(Customer customer) {
		mapRepo.replace(customer.getMobileNumber(), customer);
		return mapRepo.get(customer.getMobileNumber());
	}

	@Override
	public Customer findCustomer(String mobileNumber) {
		Customer customer = mapRepo.get(mobileNumber);
		return customer;
	}

}
