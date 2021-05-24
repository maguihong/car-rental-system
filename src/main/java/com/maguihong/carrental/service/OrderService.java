package com.maguihong.carrental.service;

import java.util.List;

import com.maguihong.carrental.entity.Order;
import com.maguihong.carrental.exception.MyException;

public interface OrderService {
	
	Order create(Order order) throws MyException;
	
	void deleteById(Long id) throws MyException;
	
	void deleteByCustomerId(Long customerId) throws MyException;
	
	void deleteByCarId(Long carId) throws MyException;
	
	Order findById(Long id) throws MyException;
	
	Order findByIdAndCustomerId(Long id, Long customerId) throws MyException;
	
	List<Order> findByCustomerId(Long customerId) throws MyException;
	
	List<Order> findByCarId(Long carId) throws MyException;

}
