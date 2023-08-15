package com.example.ecommerce.Service.Product;

import java.util.List;

import com.example.ecommerce.Model.Products;

public interface ProductService {

	public boolean addProduct(Products p);
	public boolean deleteProduct(int q);
	public List<Products> viewAll();
	public Products viewByName(String name);
	public boolean updateProduct(Products p);
	public List<Products> getProductsByReqOrder(String path);
	public List<Products> getProductsBySearch(String n);
}
