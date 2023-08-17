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
	
	@Column(name="order_status", nullable=false)
	private String order_status;
	
	@Column(name="quantity", nullable=false)
	private int quantity;
	
	@Column(name="cost",nullable=false)
	private double cost;
	
	@Column(name="pname", nullable=false)
	private String pname;
	
	@Column(name="category" , nullable=false)
	private String category;
	
	@Column(name="pcost", nullable=false)
	private double pcost;
	
	
	

	public double getPcost() {
		return pcost;
	}

	public void setPcost(double pcost) {
		this.pcost = pcost;
	}

	public Orders(Date time, String order_status, int quantity, double cost, String pname, String category,
			double pcost) {
		super();
		this.time = time;
		this.order_status = order_status;
		this.quantity = quantity;
		this.cost = cost;
		this.pname = pname;
		this.category = category;
		this.pcost = pcost;
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

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	
}
