package com.example.ecommerce.Service.Common;


import java.util.List;

import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;


public interface CommonInterface {

	public Response login(Users u);
	public List<Products> viewAll();
	public Products viewByName(String name);
	public List<Products> getProductsByReqOrder(String path);
	public List<Products> getProductsBySearch(String n);
	
}
