package com.example.ecommerce.Controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.Service.Admin.AdminUserInterface;
import com.example.ecommerce.Service.Products.ProductInterface;
import com.example.ecommerce.Dto.ProductDto;
import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;

@RestController("")
public class AdminController {

	@Autowired
	AdminUserInterface uinter;
	
	@Autowired
	ProductInterface pinter;
	
	@PostMapping("/admin/product/add")
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
	
	@DeleteMapping("/admin/product/delete/{name}")
	public ResponseEntity<Response> deleteProduct(@PathVariable int qty)
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
	
	@GetMapping("/admin/product/viewall")
	public ResponseEntity<List<Products>> getAllProducts()
	{
		return ResponseEntity.ok(pinter.viewAll());
	}
	
	@GetMapping("/admin/product/viewbyname")
	public ResponseEntity<Products> viewByName(@RequestBody Products p)
	{
		return ResponseEntity.ok(pinter.viewByName(p.getName()));
	}
	@PutMapping("/admin/product/update")
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
	
	@PostMapping("/admin/user/add")
	public ResponseEntity<Response> addUser(@RequestBody Users u)
	{
		Response response=null;
		boolean b=uinter.addUser(u);
		if(b)
		{
			response=new Response(false,"User added Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	} 
	
	@DeleteMapping("/admin/user/delete")
	public ResponseEntity<Response> deleteUser(@RequestBody Users u)
	{
		Response response=null;
		boolean b=uinter.deleteUser(u.getUsername());
		if(b)
		{
			response=new Response(false,"User deleted Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	} 
	
	@GetMapping("/admin/user/viewall")
	public ResponseEntity<List<Users>> viewAllUser()
	{
		return ResponseEntity.ok(uinter.viewAllUsers());
	}
	@GetMapping("/admin/user/viewnewusers")
	public ResponseEntity<List<Users>> viewNewUsers()
	{
		return ResponseEntity.ok(uinter.viewNewUsersList());
	}
	
	@GetMapping("/admin/user/viewunblockusers")
	public ResponseEntity<List<Users>> viewUnblockUsers()
	{
		return ResponseEntity.ok(uinter.viewBlockUsersList());
	}
	
	@GetMapping("/admin/viewuserorders")
	public ResponseEntity<List<Users>> getUserOrders()
	{
		return ResponseEntity.ok(uinter.getUsersOrdered());
	}
	
}
