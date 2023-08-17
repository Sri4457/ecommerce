package com.example.ecommerce.Service.Admin;


import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Products;

public interface AdminProductInterface {

	public boolean addProduct(Products p);
	public boolean deleteProductByQty(int q);
	public Products getProductById(long id);
	public Response deleteProductById(long id);
	public boolean updateProduct(Products p);
	
}
