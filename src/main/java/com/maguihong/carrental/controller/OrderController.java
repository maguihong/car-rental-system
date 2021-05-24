package com.maguihong.carrental.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maguihong.carrental.entity.Order;
import com.maguihong.carrental.entity.Response;
import com.maguihong.carrental.exception.MyException;
import com.maguihong.carrental.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/orders/{carId}")
	public Response book(@PathVariable Long carId, HttpSession session) {
		Long customerId = (Long) session.getAttribute("id");
		if(customerId == null) {
			return Response.fail("not logined yet");
		}
		if(carId == null) {
			return Response.fail("invalid car id");
		}
		
		Order order = new Order();
		order.setCustomerId(customerId);
		order.setCarId(carId);
		try {
			order = orderService.create(order);
		} catch (MyException e) {
			return Response.fail(e.getMessage());
		}
		return Response.success(order);
	}
	
	@GetMapping("/orders")
	public Response getAll(HttpSession session) {
		Long customerId = (Long) session.getAttribute("id");
		if(customerId == null) {
			return Response.fail("not logined yet");
		}
		
		List<Order> orders = null;
		try {
			orders = orderService.findByCustomerId(customerId);
		} catch (MyException e) {
			return Response.fail(e.getMessage());
		}
		return Response.success(orders);
	}
	
	@DeleteMapping("/orders/{id}")
	public Response cancleOne(@PathVariable Long id, HttpSession session) {
		Long customerId = (Long) session.getAttribute("id");
		if(customerId == null) {
			return Response.fail("not logined yet");
		}
		if(id == null) {
			return Response.fail("invalid order id");
		}
		
		try {
			if(orderService.findByIdAndCustomerId(id, customerId) != null) {
				orderService.deleteById(id);
			}
		} catch (MyException e) {
			return Response.fail(e.getMessage());
		}
		return Response.success(null);
	}
	
	@DeleteMapping("/orders")
	public Response cancleAll(HttpSession session) {
		Long customerId = (Long) session.getAttribute("id");
		if(customerId == null) {
			return Response.fail("not logined yet");
		}
		try {
			orderService.deleteByCustomerId(customerId);
		} catch (MyException e) {
			return Response.fail(e.getMessage());
		}
		return Response.success(null);
	}

}
