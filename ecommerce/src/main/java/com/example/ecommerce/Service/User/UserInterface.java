package com.example.ecommerce.Service.User;

import java.util.List;

import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Dto.UserOrderDto;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;

public interface UserInterface {

	
	public boolean register(Users u);
	public Response forgetPassword(String username);
	public List<Products> viewAllProducts();
	public Products viewProductByName(String name);
	public boolean UpdateProfile(Users u);
	public Users getByUname(String uname);
	public List<Response> submitProducts(String s,List<Products> list);
	public List<UserOrderDto> getOrderByUname(String uname);
	
}
