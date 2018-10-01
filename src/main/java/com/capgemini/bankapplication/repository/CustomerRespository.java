package com.capgemini.bankapplication.repository;

import java.sql.SQLException;

import com.capgemini.bankapplication.entities.Customer;

public interface CustomerRespository {

	public Customer authenticate(Customer customer)throws SQLException;

	public Customer updateProfile(Customer customer);

	public boolean updatePassword(Customer customer, String oldPassword, String newPassword);
	
	public Customer updateSession(long customerId);
}
