package com.maguihong.carrental.service;

import java.util.List;

import com.maguihong.carrental.entity.Customer;
import com.maguihong.carrental.exception.MyException;

public interface CustomerService {
	
	Customer save(Customer customer) throws MyException;
	
	void deleteById(Long id) throws MyException;
	
	List<Customer> findAll();
	
	Customer findById(Long id) throws MyException;
	
	Customer login(String username, String password) throws MyException;

}
