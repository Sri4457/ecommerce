package com.example.ecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.Dto.OrderDto;
import com.example.ecommerce.Dto.ProductDto;
import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Service.Products.ProductInterface;
import com.example.ecommerce.Service.User.UserInterface;

@RestController()
public class UserController {

	@Autowired
	UserInterface uinter;
	
	@Autowired
	ProductInterface pinter;
	
	@PostMapping("/register")
	private ResponseEntity<Response> addUsers(@RequestBody Users users)
	{
		Response response;
		boolean b=uinter.register(users);
		if(b)
		{
			response=new Response(false,"user registered successfull");
		}
		else
			response=new Response(true,"Something Went Wrong");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@GetMapping("/viewProducts")
	private ResponseEntity<List<Products>> viewAll()
	{
		return ResponseEntity.ok(pinter.viewAll());
	}
	
	@GetMapping("/product/viewbyname/{product_name}")
	public ResponseEntity<Products> viewByName(@PathVariable String product_name)
	{
		return ResponseEntity.ok(pinter.viewByName(product_name));
	}
	
	@PostMapping("/submitcart/{username}")
	public ResponseEntity<List<Response>> submitCart(@PathVariable("username") String uname,@RequestBody List<ProductDto> prods)
	{
		Users u=uinter.getByUname(uname);
		return ResponseEntity.ok(uinter.submitProducts(u, prods));
	}
	@GetMapping("/orders/vieworders/{uname}")
	public ResponseEntity<List<OrderDto>> viewOrders(@PathVariable String uname)
	{
		List<OrderDto> list=uinter.getOrderByUname(uname);
		return ResponseEntity.ok(list);
	}
}
