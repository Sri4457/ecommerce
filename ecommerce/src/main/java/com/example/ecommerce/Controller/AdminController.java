package com.example.ecommerce.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.Service.Admin.AdminUserInterface;
import com.example.ecommerce.Service.Products.ProductInterface;
import com.example.ecommerce.Dto.ProductDto;
import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;

@RestController()
@RequestMapping("/admin") 
public class AdminController {

	@Autowired
	AdminUserInterface auinter;
	
	@Autowired
	ProductInterface pinter;
	
	@PostMapping("/product/add")
	public ResponseEntity<Response> addProduct(@RequestBody Products p)
	{
		Response response=null;
		boolean b=pinter.addProduct(p);
		if(b)
		{
			response=new Response(false,"Product added Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@DeleteMapping("/product/delete/{qty}")
	public ResponseEntity<Response> deleteProduct(@PathVariable("qty") int qty)
	{
		Response response=null;
		boolean b=pinter.deleteProduct(qty);
		if(b)
		{
			response=new Response(false,"Products deleted Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	} 
	
	@GetMapping("/product/viewall")
	public ResponseEntity<List<Products>> getAllProducts()
	{
		return new ResponseEntity<List<Products>>(pinter.viewAll(),HttpStatus.OK);
	}
	
	@GetMapping("/product/viewbyname")
	public ResponseEntity<Products> viewByName(@RequestBody Products p)
	{
		return new ResponseEntity<Products>(pinter.viewByName(p.getName()),HttpStatus.OK);
	}
	@PutMapping("/product/update")
	public ResponseEntity<Response> UpdateProduct(@RequestBody ProductDto p)
	{
		Response response;
		boolean b=pinter.updateProduct(p);
		if(b)
		{
			response=new Response(false,"Products Updated Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	
	@PostMapping("/user/add")
	public ResponseEntity<Response> addUser(@RequestBody Users u)
	{
		Response response=null;
		boolean b=auinter.addUser(u);
		if(b)
		{
			response=new Response(false,"User added Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	} 
	
	@DeleteMapping("/user/delete")
	public ResponseEntity<Response> deleteUser(@RequestBody Users u)
	{
		Response response=null;
		boolean b=auinter.deleteUser(u.getUsername());
		if(b)
		{
			response=new Response(false,"User deleted Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	} 
	
	@PutMapping("/user/update/{uname}")
	public ResponseEntity<Response> updateUser(@PathVariable String uname)
	{
		Response response=null;
		boolean b=auinter.updateUser(uname);
		if(b)
		{
			response=new Response(false,"User Released Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	} 
	
	@GetMapping("/user/viewall")
	public ResponseEntity<List<Users>> viewAllUser()
	{
		return new ResponseEntity<List<Users>>(auinter.viewAllUsers(),HttpStatus.OK);
	}
	@GetMapping("/user/viewnewusers")
	public ResponseEntity<List<Users>> viewNewUsers()
	{
		return new ResponseEntity<List<Users>>(auinter.viewNewUsersList(),HttpStatus.OK);
	}
	
	@GetMapping("/user/viewunblockusers")
	public ResponseEntity<List<Users>> viewUnblockUsers()
	{
		return new ResponseEntity<List<Users>>(auinter.viewBlockUsersList(),HttpStatus.OK);
	}
	
	@GetMapping("/user/viewuserorders")
	public ResponseEntity<List<Users>> getUserOrders()
	{
		return new ResponseEntity<List<Users>>(auinter.getUsersOrdered(),HttpStatus.OK);
	}
	
	@PutMapping("/orders/updateorder/{uname}")
	public ResponseEntity<Response> updateOrders(@PathVariable("uname") String uname,@RequestBody List<ProductDto> p)
	{
		List<String> names=new ArrayList<>();
		Response response=null;
		for(ProductDto pdto:p)
		{
			names.add(pdto.getName());
		}
		boolean b=auinter.updateOrders(uname, names);
		if(b==false)
			response=new Response(true,"Something Went Wrong");
		else
			response=new Response(false,"Updation Done");
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
}
