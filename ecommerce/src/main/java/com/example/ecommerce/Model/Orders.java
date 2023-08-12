package com.example.ecommerce.Model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Orders {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;

	@Column(name="time", nullable=false)
	private Date time;
	
	@Column(name="status", nullable=false)
	private String status;
	
	@Column(name="product_id", nullable=false)
	private long product_id;
	
	@Column(name="quantity", nullable=false)
	private int quantity;
	
	@Column(name="cost",nullable=false)
	private double cost;
	
	
	public Orders(Date time, String status, long product_id, int quantity, double cost) {
		super();
		this.time = time;
		this.status = status;
		this.product_id = product_id;
		this.quantity = quantity;
		this.cost = cost;
	}
	public Orders() {
		
	}
	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	
	
}
