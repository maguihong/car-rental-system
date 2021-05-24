package com.maguihong.carrental.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maguihong.carrental.entity.Car;
import com.maguihong.carrental.entity.Order;
import com.maguihong.carrental.exception.MyException;
import com.maguihong.carrental.repository.CarRepository;
import com.maguihong.carrental.repository.CustomerRepository;
import com.maguihong.carrental.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public Order create(Order order) throws MyException {
		
		Long customerId = order.getCustomerId();
		Long carId = order.getCarId();
		
		if(order.getId() != null) {
			throw new MyException("invalid order number");
		}
		if(customerId == null || !customerRepository.findById(customerId).isPresent()) {
			throw new MyException("invalid customer");
		}
		Optional<Car> optional = carRepository.findById(carId);
		if(carId == null || !optional.isPresent()) {
			throw new MyException("invalid car model");
		}
		
		Car car = optional.get();
		Integer inStock = car.getInStock();
		if(inStock == null || inStock < 1) {
			throw new MyException("stock shortage");
		}
		order = orderRepository.save(order);
		
		Car updated = new Car();
		updated.setId(car.getId());
		updated.setCarModel(car.getCarModel());
		updated.setInStock(--inStock);
		updated = carRepository.save(updated);
		
		return order;
	}
	
	@Override
	public Order findById(Long id) throws MyException {
		if(id == null) {
			throw new MyException("invalid id");
		} else {
			Optional<Order> optional =  orderRepository.findById(id);
			if(optional.isPresent()) {
				return optional.get();
			} else {
				throw new MyException("order not exist");
			}
		}
	}
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void deleteById(Long id) throws MyException {
		if(id == null) {
			throw new MyException("invalid id");
		}
		Order order = findById(id);
		if(order == null) {
			throw new MyException("order not exist");
		}
		
		Long carId = order.getCarId();
		Optional<Car> optional = carRepository.findById(carId);
		if(carId != null && optional.isPresent()) {
			Car car = optional.get();
			Integer inStock = car.getInStock();
			if(inStock == null || inStock < 0) {
				throw new MyException("stock error");
			}
			Car updated = new Car();
			updated.setId(car.getId());
			updated.setCarModel(car.getCarModel());
			updated.setInStock(++inStock);
			updated = carRepository.save(updated);
		}
		
		orderRepository.deleteById(id);
	}
	
	@Override
	public List<Order> findByCustomerId(Long customerId) throws MyException {
		if(customerId == null) {
			throw new MyException("invalid customer id");
		} else {
			return orderRepository.findByCustomerId(customerId);
		}
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void deleteByCustomerId(Long customerId) throws MyException {
		
		List<Order> orders = findByCustomerId(customerId);
		if(orders != null && !orders.isEmpty()) {
			for(Order order: orders) {
				deleteById(order.getId());
			}
		}
	}

	@Override
	public Order findByIdAndCustomerId(Long id, Long customerId) throws MyException {
		if(id == null) {
			throw new MyException("invalid order id");
		}
		if(customerId == null) {
			throw new MyException("invalid customer id");
		}
		Order order = orderRepository.findByIdAndCustomerId(id, customerId);
		if(order == null) {
			throw new MyException("order " + id + " does not belong to customer " + customerId);
		}
		return order;
	}

	@Override
	public List<Order> findByCarId(Long carId) throws MyException {
		if(carId == null) {
			throw new MyException("invalid car id");
		} else {
			return orderRepository.findByCarId(carId);
		}
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void deleteByCarId(Long carId) throws MyException {
		
		List<Order> orders = findByCarId(carId);
		if(orders != null && !orders.isEmpty()) {
			for(Order order: orders) {
				deleteById(order.getId());
			}
		}
	}
	
	
	

}
