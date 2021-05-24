package com.maguihong.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maguihong.carrental.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	Customer findByUsername(String username);
	
	Customer findByUsernameAndPassword(String username, String password);
	
}
