package com.capgemini.bankapplication.exception;

public class NegativeAmountException extends Exception {

	public NegativeAmountException(String message)
	{
		super(message);
	}
}
