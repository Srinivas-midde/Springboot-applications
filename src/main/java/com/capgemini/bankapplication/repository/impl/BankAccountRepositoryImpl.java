 package com.capgemini.bankapplication.repository.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.capgemini.bankapplication.repository.BankAccountRepository;

@Repository
public class BankAccountRepositoryImpl implements BankAccountRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public double getBalance(long accountId) throws SQLException {
		return jdbcTemplate.queryForObject("SELECT balance FROM accounts WHERE account_id = ?",
				new Object[] { accountId }, Double.class);
	}

	@Override
	public boolean updateBalance(long accountId, double newBalance)throws SQLException {
		int count = jdbcTemplate.update(
				"UPDATE accounts SET balance = ? WHERE account_id = ?",
				new Object[] { newBalance,accountId});
		return (count != 0) ? true : false;
	}

}
