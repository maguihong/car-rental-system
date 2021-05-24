package com.maguihong.carrental.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maguihong.carrental.entity.Customer;
import com.maguihong.carrental.exception.MyException;
import com.maguihong.carrental.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer save(Customer customer) throws MyException {
		
		String username = customer.getUsername().trim();
		String password = customer.getPassword().trim();
		
		if(StringUtils.isBlank(username)) {
			throw new MyException("invalid username");
		}
		if(StringUtils.isBlank(password)) {
			throw new MyException("invalid password");
		}
		
		Customer source = customerRepository.findByUsername(username);
		if(source != null && username.equals(source.getUsername())) {
			throw new MyException("username already exist");
		}
		
		customer = customerRepository.save(customer);
		return customer;
	}

	@Override
	public void deleteById(Long id) throws MyException {
		if(id == null) {
			throw new MyException("invalid id");
		} else if (findById(id) != null) {
			customerRepository.deleteById(id);
		}
	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer findById(Long id) throws MyException {
		if(id == null) {
			throw new MyException("invalid id");
		} else {
			Optional<Customer> optional =  customerRepository.findById(id);
			if(optional.isPresent()) {
				return optional.get();
			} else {
				throw new MyException("customer not exist");
			}
		}
	}

	@Override
	public Customer login(String username, String password) throws MyException {
		
		if(StringUtils.isBlank(username)) {
			throw new MyException("invalid username");
		}
		if(StringUtils.isBlank(password)) {
			throw new MyException("invalid password");
		}
		
		Customer customer = customerRepository.findByUsernameAndPassword(username, password);
		if(customer == null) {
			throw new MyException("username and password fail to match");
		}
		return customer;
	}

}
