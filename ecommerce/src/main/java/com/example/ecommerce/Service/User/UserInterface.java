package com.example.ecommerce.Service.User;

import java.util.List;

import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Dto.UserOrderDto;
import com.example.ecommerce.Model.Cart;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;

public interface UserInterface {

	
	public boolean register(Users u);
	public Response forgetPassword(String username);
	public boolean UpdateProfile(Users u);
	public Users getById(long id);
	public long getIdByUname(String uname);
	
	public List<Products> viewAllProducts();
	public Products viewProductByName(String name);
	
	
	
	public Response addToCart(Cart list);
	public List<Cart> getCartByUId(long uid);
	public Response deleteCartElement(long id);
	public Response updateCart(Cart c);
	public Cart getCartByCartId(long id);
	
	
	public List<UserOrderDto> getOrderByUId(long id);
	public List<Response> submitCart(List<Cart> list);
	
}
