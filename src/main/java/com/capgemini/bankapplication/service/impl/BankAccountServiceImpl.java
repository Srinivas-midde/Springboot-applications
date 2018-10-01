package com.capgemini.bankapplication.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.bankapplication.exception.InsufficientAccountBalanceException;
import com.capgemini.bankapplication.exception.NegativeAmountException;
import com.capgemini.bankapplication.repository.impl.BankAccountRepositoryImpl;
import com.capgemini.bankapplication.service.BankAccountService;

@Service
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	BankAccountRepositoryImpl bankaccountrepositoryimpl;
	
	@Autowired
	BankAccountServiceImpl serviceobject;

	@Override
	public double getBalance(long accountId) throws SQLException {
		return bankaccountrepositoryimpl.getBalance(accountId);
	}

	@Override
	public double withdraw(long accountId, double amount) throws SQLException {
		double accountBalance = bankaccountrepositoryimpl.getBalance(accountId);
		bankaccountrepositoryimpl.updateBalance(accountId, accountBalance - amount);
		return accountBalance - amount;
	}

	@Override
	public double deposit(long accountId, double amount) throws SQLException {
		double accountBalance = bankaccountrepositoryimpl.getBalance(accountId);
		bankaccountrepositoryimpl.updateBalance(accountId, accountBalance + amount);
		return accountBalance + amount;
	}

	@Override
	public boolean fundTransfer(long fromAcc, long toAcc, double amount)
			throws InsufficientAccountBalanceException, NegativeAmountException, SQLException {
		double accountBalanceFrom = bankaccountrepositoryimpl.getBalance(fromAcc);
		
		if (accountBalanceFrom < amount) 
			throw new InsufficientAccountBalanceException("There isn't sufficient balance in your account!");
		else if (amount < 0)
			throw new NegativeAmountException("The amount cannot be negative!");
		else {
			serviceobject.deposit(toAcc, amount);
			serviceobject.withdraw(fromAcc, amount);
			return true;
		}
	}

}
