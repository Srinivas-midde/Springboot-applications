package com.capgemini.bankapplication.exception;

public class AccountNotFound extends RuntimeException{
	
	public AccountNotFound(String message) {
		super(message);
	}

}
