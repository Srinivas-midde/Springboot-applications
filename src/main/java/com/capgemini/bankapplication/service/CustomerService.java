package com.capgemini.bankapplication.service;

import com.capgemini.bankapplication.entities.Customer;
import com.capgemini.bankapplication.exception.AccountNotFound;

public interface CustomerService {

	public Customer authenticate(Customer customer) throws AccountNotFound;
	public Customer updateProfile(Customer customer);
	public boolean updatePassword(Customer customer, String oldPassword, String newPassword);
	public Customer updateSession(long customerId);

}
