package com.maguihong.carrental.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cars")
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "car_model", unique = true, nullable = false)
	private String carModel;
	
	@Column(name = "in_stock", nullable = false)
	private Integer inStock;
	
	public Car() {}

	public Car(String carModel, Integer inStock) {
		this.carModel = carModel;
		this.inStock = inStock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public Integer getInStock() {
		return inStock;
	}

	public void setInStock(Integer inStock) {
		this.inStock = inStock;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", carModel=" + carModel + ", inStock=" + inStock + "]";
	}

}
