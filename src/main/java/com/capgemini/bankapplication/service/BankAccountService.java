package com.capgemini.bankapplication.service;

import java.sql.SQLException;

import com.capgemini.bankapplication.exception.InsufficientAccountBalanceException;
import com.capgemini.bankapplication.exception.NegativeAmountException;

public interface BankAccountService {

	public double getBalance(long accountId) throws SQLException;
	public double withdraw(long accountId, double amount) throws SQLException;
	public double deposit(long accountId, double amount) throws SQLException;
	public boolean fundTransfer(long fromAcc, long toAcc, double amount) throws InsufficientAccountBalanceException, NegativeAmountException, SQLException;
}
