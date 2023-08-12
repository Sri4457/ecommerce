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

import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Service.Admin.AdminUserInterface;
import com.example.ecommerce.Service.Products.ProductInterface;
import com.example.ecommerce.Service.User.UserInterface;

@RestController
public class CommonController {

	@Autowired
	ProductInterface pinter;
	
	@Autowired
	UserInterface uinter;
	
	@Autowired
	AdminUserInterface adinter;
	
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody Users u)
	{
		Response response=adinter.login(u);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@GetMapping("/product/viewall")
	public ResponseEntity<List<Products>> getAllProducts()
	{
		return new ResponseEntity<List<Products>>(pinter.viewAll(),HttpStatus.OK);
	}
	
	@GetMapping("/product/viewbyname/{product_name}")
	public ResponseEntity<Products> viewByName(@PathVariable String product_name)
	{
		return new ResponseEntity<Products>(pinter.viewByName(product_name),HttpStatus.OK);
	}
	@PostMapping("/user/add")
	public ResponseEntity<Response> addUser(@RequestBody Users u)
	{
		Response response=null;
		boolean b=uinter.register(u);
		if(b)
		{
			response=new Response(false,"User added Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@GetMapping("/product/sort/{path}")
	public ResponseEntity<List<Products>> getProductsByReqOrder(@PathVariable("path") String path)
	{
		List<Products> result=pinter.getProductsByReqOrder(path);
		return new ResponseEntity<List<Products>>(result,HttpStatus.OK);
	}
	
}
