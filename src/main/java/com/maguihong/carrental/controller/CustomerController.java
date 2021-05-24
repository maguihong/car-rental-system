package com.maguihong.carrental.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.maguihong.carrental.entity.Customer;
import com.maguihong.carrental.entity.Response;
import com.maguihong.carrental.exception.MyException;
import com.maguihong.carrental.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public Response getAll(HttpSession session) {
		Long customerId = (Long) session.getAttribute("id");
		if(customerId == null) {
			return Response.fail("not logined yet");
		}
		if(customerId != 0) {
			return Response.fail("permission denied");
		}
		return Response.success(customerService.findAll());
	}
	
	@GetMapping("/customers/{id}")
	public Response getOne(@PathVariable Long id, HttpSession session) {
		Long customerId = (Long) session.getAttribute("id");
		if(customerId == null) {
			return Response.fail("not logined yet");
		}
		if(customerId != 0) {
			return Response.fail("permission denied");
		}
		
		Customer customer = null;
		try {
			customer = customerService.findById(id);
		} catch (MyException e) {
			return Response.fail(e.getMessage());
		}
		return Response.success(customer);
	}
	
	@PostMapping("/customers")
	public Response create(@RequestBody Customer customer) {
		try {
			customer = customerService.save(customer);
		} catch (MyException e) {
			return Response.fail(e.getMessage());
		}
		return Response.success(customer);
	}
	
	@DeleteMapping("/customers/{id}")
	public Response delete(@PathVariable Long id, HttpSession session) {
		Long customerId = (Long) session.getAttribute("id");
		if(customerId == null) {
			return Response.fail("not logined yet");
		}
		if(customerId != 0) {
			return Response.fail("permission denied");
		}
		
		try {
			customerService.deleteById(id);
		} catch (MyException e) {
			return Response.fail(e.getMessage());
		}
		return Response.success(null);
	}
	
	@PostMapping("/login")
	public Response login(@RequestBody Customer customer, HttpSession session) {
		try {
			customer = customerService.login(customer.getUsername(), customer.getPassword());
		} catch (MyException e) {
			return Response.fail(e.getMessage());
		}
		session.setAttribute("id", customer.getId());
		session.setMaxInactiveInterval(6000);
		return Response.success(customer);
	}
	
	@GetMapping("/customers/i")
	public Response test(HttpSession session) {
		Long id = (Long) session.getAttribute("id");
		if(id == null) {
			return Response.fail("not logined yet");
		}
		
		Customer customer = null;
		try {
			customer = customerService.findById(id);
		} catch (MyException e) {
			return Response.fail(e.getMessage());
		}
		return Response.success(customer);
	}
	
	@PostMapping("/logout")
	public Response logout(HttpSession session) {
		session.invalidate();
		return Response.success(null);
	}

}
