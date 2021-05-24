package com.maguihong.carrental.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maguihong.carrental.entity.Car;
import com.maguihong.carrental.exception.MyException;
import com.maguihong.carrental.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private OrderService orderService;

	@Override
	public Car save(Car car) throws MyException {

		String carModel = car.getCarModel().trim();
		Integer inStock = car.getInStock();
		
		if(StringUtils.isBlank(carModel)) {
			throw new MyException("invalid car model");
		}
		if(inStock == null || inStock < 0) {
			throw new MyException("invalid in stock");
		}
		
		Car source = carRepository.findByCarModelIgnoreCase(carModel);
		if(source != null && carModel.equals(source.getCarModel())) {
			throw new MyException("car model already exist");
		}
		car = carRepository.save(car);
		return car;
	}

	@Override
	public List<Car> findAll() {
		return carRepository.findAll();
	}

	@Override
	public List<Car> findAvaliable() {
		return carRepository.findByInStockGreaterThan(0);
	}

	@Override
	public Car findByCarModelIgnoreCase(String carModel) throws MyException {
		if(StringUtils.isBlank(carModel)) {
			throw new MyException("invalid car model");
		} else {
			Car car = carRepository.findByCarModelIgnoreCase(carModel);
			if(car != null) {
				return car;
			} else {
				throw new MyException("car model not exist");
			}
		}
	}

	@Override
	public Car findById(Long id) throws MyException {
		if(id == null) {
			throw new MyException("invalid id");
		} else {
			Optional<Car> optional = carRepository.findById(id);
			if(optional.isPresent()) {
				return optional.get();
			} else {
				throw new MyException("car not exist");
			}
		}
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void deleteById(Long id) throws MyException {
		if(id == null) {
			throw new MyException("invalid id");
		} else if (findById(id) != null) {
			orderService.deleteByCarId(id);
			carRepository.deleteById(id);
		}
		
	}
	
	

}
