package com.maguihong.carrental.service;

import java.util.List;

import com.maguihong.carrental.entity.Car;
import com.maguihong.carrental.exception.MyException;

public interface CarService {

	Car save(Car car) throws MyException;
	
	void deleteById(Long id) throws MyException;
	
	List<Car> findAll();
	
	List<Car> findAvaliable();
	
	Car findByCarModelIgnoreCase(String carModel) throws MyException;
	
	Car findById(Long id) throws MyException;
}
