package com.example.ecommerce.Service.Products;

import java.util.List;

import com.example.ecommerce.Dto.ProductDto;
import com.example.ecommerce.Model.Products;


public interface ProductInterface {

	public boolean addProduct(Products p);
	public boolean deleteProduct(int q);
	public List<Products> viewAll();
	public Products viewByName(String name);
	public boolean updateProduct(ProductDto p);
	
	
	
}
