package com.maguihong.carrental.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maguihong.carrental.entity.Car;
import com.maguihong.carrental.entity.Response;
import com.maguihong.carrental.exception.MyException;
import com.maguihong.carrental.service.CarService;

@RestController
public class CarController {
	
	@Autowired
	private CarService carService;
	
	@GetMapping("/cars")
	public Response getAll(@RequestParam(required = false) Boolean avaliable, @RequestParam(required = false) String model) {
		if (StringUtils.isNotBlank(model)) {
			Car car = null;
			try {
				car = carService.findByCarModelIgnoreCase(model);
			} catch (MyException e) {
				return Response.fail(e.getMessage());
			}
			return Response.success(car);
		}
		
		if(avaliable != null && avaliable.booleanValue()) {
			return Response.success(carService.findAvaliable());
		}
		
		return Response.success(carService.findAll());
		
	}
	
	@GetMapping("/cars/{id}")
	public Response getOne(@PathVariable Long id) {
		Car car = null;
		try {
			car = carService.findById(id);
		} catch (MyException e) {
			return Response.fail(e.getMessage());
		}
		return Response.success(car);
	}
	
	@PostMapping("/cars")
	public Response create(@RequestBody Car car, HttpSession session) {
		Long customerId = (Long) session.getAttribute("id");
		if(customerId == null) {
			return Response.fail("not logined yet");
		}
		if(customerId != 0) {
			return Response.fail("permission denied");
		}
		
		try {
			car = carService.save(car);
		} catch (MyException e) {
			return Response.fail(e.getMessage());
		}
		return Response.success(car);
	}
	
	@DeleteMapping("/cars/{id}")
	public Response delete(@PathVariable Long id, HttpSession session) {
		Long customerId = (Long) session.getAttribute("id");
		if(customerId == null) {
			return Response.fail("not logined yet");
		}
		if(customerId != 0) {
			return Response.fail("permission denied");
		}
		
		try {
			carService.deleteById(id);
		} catch (MyException e) {
			return Response.fail(e.getMessage());
		}
		return Response.success(null);
	}

}
