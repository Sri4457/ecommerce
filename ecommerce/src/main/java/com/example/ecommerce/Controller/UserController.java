package com.example.ecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Dto.UserOrderDto;
import com.example.ecommerce.Model.Cart;
import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Service.Common.CommonInterface;
import com.example.ecommerce.Service.User.UserInterface;

@CrossOrigin(origins="http://localhost:3000/")
@RestController()
@RequestMapping("/user") 
public class UserController {

	@Autowired
	UserInterface uinter;
	
	@Autowired
	CommonInterface pinter;
	@GetMapping("/{id}")
	public ResponseEntity<Users> getUserByUname(@PathVariable long id)
	{
		return new ResponseEntity<>(uinter.getById(id),HttpStatus.OK);
	}
	
	@GetMapping("/getid/{uname}")
	public ResponseEntity<Long> getIdForUname(@PathVariable String uname)
	{
		return new ResponseEntity<>(uinter.getIdByUname(uname),HttpStatus.OK);
	}
	@PostMapping("/forgetpassword/{uname}")
	public ResponseEntity<Response> sendPassword(@PathVariable("uname") String uname)
	{
		return new ResponseEntity<Response>(uinter.forgetPassword(uname),HttpStatus.OK);
	}
	
	@PostMapping("/addtocart")
	public ResponseEntity<Response> addToCart(@RequestBody Cart c)
	{
		System.out.println("hi");
		return new ResponseEntity<Response>(uinter.addToCart(c),HttpStatus.OK);
	}
	
	@PutMapping("/updatecart")
	public ResponseEntity<Response> updateCartItem(@RequestBody Cart c)
	{
		return new ResponseEntity<>(uinter.updateCart(c),HttpStatus.OK);
	}
	
	@GetMapping("/getcartbyuname/{id}")
	public ResponseEntity<List<Cart>> getCartByUname(@PathVariable long id)
	{
		return new ResponseEntity<>(uinter.getCartByUId(id),HttpStatus.OK);
	}
	
	@GetMapping("/getcartbyCartid/{id}")
	public ResponseEntity<Cart> getCartById(@PathVariable long id)
	{
		return new ResponseEntity<>(uinter.getCartByCartId(id),HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteitem/{id}")
	public ResponseEntity<Response> deleteItemInCart(@PathVariable long id)
	{
		return new ResponseEntity<>(uinter.deleteCartElement(id),HttpStatus.OK);
	}
	
	@PostMapping("/submitcart")
	public ResponseEntity<List<Response>> submitcart(@RequestBody List<Cart> c)
	{
		return new ResponseEntity<>(uinter.submitCart(c),HttpStatus.OK);
	}
	@GetMapping("/orders/vieworders/{id}")
	public ResponseEntity<List<UserOrderDto>> viewOrders(@PathVariable long id)
	{
		List<UserOrderDto> list=uinter.getOrderByUId(id);
		return new ResponseEntity<List<UserOrderDto>>(list,HttpStatus.OK);
	}
	@PutMapping("/update")
	public ResponseEntity<Response> updateUser(@RequestBody Users u)
	{
		boolean b=uinter.UpdateProfile(u);
		Response response=null;
		if(b)
		{
			response=new Response(false,"Updated Successfully");
		}
		else {
			response=new Response(true,"Something Went Wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
