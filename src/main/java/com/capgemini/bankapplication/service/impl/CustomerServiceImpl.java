package com.capgemini.bankapplication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.capgemini.bankapplication.entities.Customer;
import com.capgemini.bankapplication.exception.AccountNotFound;
import com.capgemini.bankapplication.repository.impl.CustomerRepositoryImpl;
import com.capgemini.bankapplication.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepositoryImpl customerrepositoryimpl;
	@Override
	public Customer authenticate(Customer customer) throws AccountNotFound {
		try{
			return customerrepositoryimpl.authenticate(customer);
		}catch (DataAccessException e) {
			AccountNotFound accountNotFound = new AccountNotFound("User does not exist!");
			accountNotFound.initCause(e);
			throw accountNotFound;
		}
	}

	@Override
	public Customer updateProfile(Customer customer) {
		return customerrepositoryimpl.updateProfile(customer);
	}

	@Override
	public boolean updatePassword(Customer customer, String oldPassword, String newPassword) {
		return customerrepositoryimpl.updatePassword(customer, oldPassword, newPassword);
	}

	@Override
	public Customer updateSession(long customerId) {
		return customerrepositoryimpl.updateSession(customerId);
	}

}
