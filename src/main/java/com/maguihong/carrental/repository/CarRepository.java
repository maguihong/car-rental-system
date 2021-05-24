package com.maguihong.carrental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maguihong.carrental.entity.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
	
	Car findByCarModelIgnoreCase(String carModel);

	List<Car> findByInStockGreaterThan(Integer number);
}
