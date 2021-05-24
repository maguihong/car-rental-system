package com.maguihong.carrental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maguihong.carrental.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByCustomerId(Long customerId);
	
	List<Order> findByCarId(Long carId);
	
	Order findByIdAndCustomerId(Long id, Long customerId);
}
