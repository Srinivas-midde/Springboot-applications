package com.capgemini.bankapplication.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.capgemini.bankapplication.entities.Customer;
import com.capgemini.bankapplication.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	HttpServletRequest request;

	@Autowired
	private CustomerService service;

	@Autowired
	HttpSession session;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHomePage(Model model) {
		model.addAttribute("customer", new Customer());
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginController(HttpServletRequest request, HttpSession session,
			@Valid @ModelAttribute Customer customer, BindingResult result) {
		if (result.hasErrors()) {
			return "index";
		}
		request = (HttpServletRequest) request;
		if (null == request.getCookies()) {
			return "enableCookie";
		} else {
			customer = service.authenticate(customer);

			request.getSession(false);
			session.setAttribute("customer", customer);
			return "redirect:/home";

		}

	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homeController() {
		request.getSession(false);
		Customer cust = (Customer) session.getAttribute("customer");
		Customer customer = service.updateSession(cust.getCustomerId());
		request.getSession().setAttribute("customer", customer);
		return "home";
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public String editController(Model model) {
		request.getSession(false);
		model.addAttribute("customer", session.getAttribute("customer"));
		if (null == session.getAttribute("customer")) {
			return "error";
		} else {
			return "edit";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutController(Model model) {
		HttpSession session = request.getSession();
		model.addAttribute("customer", new Customer());
		session.invalidate();
		return "index";
	}

	@RequestMapping(value = "/updatePasswordMethod", method = RequestMethod.POST)
	public String updatePasswordMethod() {
		HttpSession session = request.getSession(false);
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");

		Customer customer = (Customer) (session.getAttribute("customer"));
		if (oldPassword.equals(customer.getCustomerPassword())) {
			if (service.updatePassword(customer, oldPassword, newPassword)) {
				return "redirect:/home";
			} else {
				request.setAttribute("passwordnotchanged", "true");
				return "changePassword";
			}
		} else {
			request.setAttribute("oldpassword", "false");
			return "changePassword";
		}

	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
	public String updatePassword() {
		request.getSession(false);
		if (null == session.getAttribute("customer")) {
			return "error";
		} else {
			return "changePassword";
		}
	}

	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public String updateProfile(@ModelAttribute Customer customer) {
		customer = service.updateProfile(customer);
		if (customer != null) {
			request.getSession().setAttribute("customer", customer);
			return "redirect:/home";
		} else {
			request.setAttribute("profileupdate", "false");
			return "edit";
		}
	}
}
