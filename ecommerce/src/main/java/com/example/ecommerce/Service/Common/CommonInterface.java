package com.example.ecommerce.Service.Common;


import java.util.List;

import com.example.ecommerce.Dto.LoginResponse;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;


public interface CommonInterface {

	public LoginResponse login(Users u);
	public List<Products> viewAll();
	public Products getByPId(long name);
	public List<Products> getProductsByReqOrder(String path);
	public List<Products> getProductsBySearch(String n);
	
}
