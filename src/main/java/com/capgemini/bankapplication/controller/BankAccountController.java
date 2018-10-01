package com.capgemini.bankapplication.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.capgemini.bankapplication.entities.Customer;
import com.capgemini.bankapplication.exception.InsufficientAccountBalanceException;
import com.capgemini.bankapplication.exception.NegativeAmountException;
import com.capgemini.bankapplication.service.BankAccountService;
import com.capgemini.bankapplication.service.CustomerService;

@Controller
public class BankAccountController {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private CustomerService service;
	
	@Autowired
	private BankAccountService bankService;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value = "/fundTransfer", method = RequestMethod.GET)
	public String fundTransferController() {
		request.getSession(false);

		if (null == session.getAttribute("customer")) {
			return "error";
		} else {
			return "fundTransfer";
		}
	}
	
	@RequestMapping(value = "/fundTransferMethod", method = RequestMethod.POST)
	public String fundTransferMethodController() throws SQLException {
		
		try {
			long from = Long.parseLong(request.getParameter("fromAcc"));
			long to = Long.parseLong(request.getParameter("toAcc"));
			double amount = Double.parseDouble(request.getParameter("amount"));
			if (bankService.fundTransfer(from, to, amount)) {
				request.setAttribute("success", "true");
				HttpSession session = request.getSession();
				Customer cust  = (Customer) session.getAttribute("customer");
				Customer customer = service.updateSession(cust.getCustomerId());
				request.getSession().setAttribute("customer", customer);	
				return "fundTransfer";
				
			}

		} catch (NumberFormatException | InsufficientAccountBalanceException | NegativeAmountException | EmptyResultDataAccessException e) {
			if(e instanceof NegativeAmountException) {
			request.setAttribute("negativeamount", "true");
			return "fundTransfer";
			}
			else if(e instanceof InsufficientAccountBalanceException) {
				request.setAttribute("insufficientbalance", "true");
				return "fundTransfer";
				}
			else if(e instanceof NumberFormatException) {
				request.setAttribute("balance", "true");
				return "fundTransfer";
				}
			else {
				request.setAttribute("accountnotfound", "true");
				return "fundTransfer";
			}
				
		}
		return null;
		
	}

}
