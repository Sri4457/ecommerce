package com.example.ecommerce.Service.User;

import java.util.List;

import com.example.ecommerce.Dto.CommonDto;
import com.example.ecommerce.Dto.ProductDto;
import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;

public interface UserInterface {

	public boolean register(Users u);
	public List<Products> viewAllProducts();
	public Products viewProductByName(String name);
	public boolean UpdateProfile(Users u);
	public Users getByUname(String uname);
	public List<Response> submitProducts(String s,List<ProductDto> list);
	public List<CommonDto> getOrderByUname(String uname);
	
}
