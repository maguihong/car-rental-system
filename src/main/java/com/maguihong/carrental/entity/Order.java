package com.maguihong.carrental.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "customer_id", nullable = false)
	private Long customerId;
	
	@Column(name = "car_id", nullable = false)
	private Long carId;
	
	public Order() {}

	public Order(Long customerId, Long carId) {
		this.customerId = customerId;
		this.carId = carId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + ", carId=" + carId + "]";
	}
	

}
