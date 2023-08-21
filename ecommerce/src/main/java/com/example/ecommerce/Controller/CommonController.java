package com.example.ecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.Dto.LoginResponse;
import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Service.Admin.AdminUserInterface;
import com.example.ecommerce.Service.Common.CommonInterface;
import com.example.ecommerce.Service.User.UserInterface;

@CrossOrigin(origins="http://localhost:3000/")
@RestController
public class CommonController {

	
	
	@Autowired
	UserInterface uinter;
	
	@Autowired
	CommonInterface cser;
	
	@Autowired
	AdminUserInterface adinter;
	
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody Users u)
	{
		LoginResponse response=cser.login(u);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@GetMapping("/product/viewall")
	public ResponseEntity<List<Products>> getAllProducts()
	{
		return new ResponseEntity<List<Products>>(cser.viewAll(),HttpStatus.OK);
	}
	
	@GetMapping("/product/viewbyid/{product_id}")
	public ResponseEntity<Products> viewByName(@PathVariable long id)
	{
		return new ResponseEntity<Products>(cser.getByPId(id),HttpStatus.OK);
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
		List<Products> result=cser.getProductsByReqOrder(path);
		return new ResponseEntity<List<Products>>(result,HttpStatus.OK);
	}
	
	@GetMapping("/product/search/{name}")
	public ResponseEntity<List<Products>> getProducts(@PathVariable("name") String name)
	{
		return new ResponseEntity<>(cser.getProductsBySearch(name),HttpStatus.OK);
	}
}
