package com.example.ecommerce.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	
	@Column(name="userid",nullable=false)
	private long userid;
	
	@Column(name="productname",nullable=false)
	private String productname;
	
	@Column(name="productcat",nullable=false)
	private String productcat;
	
	@Column(name="price",nullable=false)
	private double price;
	
	@Column(name="qty",nullable=false)
	private int qty;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProductcat() {
		return productcat;
	}

	public void setProductcat(String productcat) {
		this.productcat = productcat;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	
	
	
}
