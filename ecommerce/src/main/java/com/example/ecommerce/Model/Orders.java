package com.example.ecommerce.Model;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;


@Entity
public class Orders {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;

	@Column(name="time", nullable=false)
	private Date time;
	
	@Column(name="order_status", nullable=false)
	private String order_status;
	
	@Column(name="quantity", nullable=false)
	private int quantity;
	
	@Column(name="cost",nullable=false)
	private double cost;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Products p;
	
	
	public Orders(Date time, String order_status, int quantity, double cost, Products p) {
		super();
		this.time = time;
		this.order_status = order_status;
		this.quantity = quantity;
		this.cost = cost;
		this.p = p;
	}
	
	public Orders() {
		
	}
	
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
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

	public Products getP() {
		return p;
	}

	public void setP(Products p) {
		this.p = p;
	}
	
	
	
}
