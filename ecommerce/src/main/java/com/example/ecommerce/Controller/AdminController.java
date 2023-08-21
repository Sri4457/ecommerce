package com.example.ecommerce.Controller;

import java.sql.Date;
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

import com.example.ecommerce.Service.Admin.AdminProductInterface;
import com.example.ecommerce.Service.Admin.AdminUserInterface;
import com.example.ecommerce.Service.Common.CommonInterface;
import com.example.ecommerce.Dto.DateDto;
import com.example.ecommerce.Dto.updateOrdersDto;
import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Orders;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;

@CrossOrigin(origins="http://localhost:3000/")
@RestController()
@RequestMapping("/admin") 
public class AdminController {

	@Autowired
	AdminUserInterface auinter;
	
	@Autowired
	CommonInterface cinter;
	
	@Autowired
	AdminProductInterface apinter;
	
	@PostMapping("/product/add")
	public ResponseEntity<Response> addProduct(@RequestBody Products p)
	{
		Response response=null;
		boolean b=apinter.addProduct(p);
		if(b)
		{
			response=new Response(false,"Product added Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@DeleteMapping("/product/delete/{id}")
	public ResponseEntity<Response> deleteProduct(@PathVariable long id)
	{
		return new ResponseEntity<>(apinter.deleteProductById(id),HttpStatus.OK);
	}
	@DeleteMapping("/product/deletebyqty/{qty}")
	public ResponseEntity<Response> deleteProductByQty(@PathVariable("qty") int qty)
	{
		Response response=null;
		boolean b=apinter.deleteProductByQty(qty);
		if(b)
		{
			response=new Response(false,"Products deleted Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	} 
	
	@GetMapping("/product/findbyid/{id}")
	public ResponseEntity<Products> getProductById(@PathVariable long id)
	{
		return new ResponseEntity<>(apinter.getProductById(id),HttpStatus.OK);
	}
	
	@PutMapping("/product/update")
	public ResponseEntity<Response> UpdateProduct(@RequestBody Products p)
	{
		Response response;
		boolean b=apinter.updateProduct(p);
		if(b)
		{
			response=new Response(false,"Products Updated Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	
	
	
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<Response> deleteUser(@PathVariable long id)
	{
		Response response=null;
		boolean b=auinter.deleteUser(id);
		if(b)
		{
			response=new Response(false,"User deleted Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	} 
	
	@PutMapping("/user/release/{id}")
	public ResponseEntity<Response> releaseUser(@PathVariable long id)
	{
		Response response=null;
		boolean b=auinter.updateUser(id,"release");
		if(b)
		{
			response=new Response(false,"User Released Successfully");
		}
		else {
			response=new Response(true,"something went wrong");
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	} 
	
	@PutMapping("/user/block/{id}")
	public ResponseEntity<Response> blockUser(@PathVariable long id)
	{
		Response response=null;
		boolean b=auinter.updateUser(id,"block");
		if(b)
		{
			response=new Response(false,"User blocked Successfully");
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
	
	@GetMapping("/user/viewordersbyuid/{id}")
	public ResponseEntity<List<Orders>> getOrdersByUid(@PathVariable long id)
	{
		return new ResponseEntity<>(auinter.getOrdersByUid(id),HttpStatus.OK);
	}
	
	@GetMapping("/user/viewuserorders")
	public ResponseEntity<List<Users>> getUserOrders()
	{
		return new ResponseEntity<List<Users>>(auinter.getUsersOrdered(),HttpStatus.OK);
	}
	
	@PutMapping("/orders/updateorder/{id}")
	public ResponseEntity<Response> updateOrders(@PathVariable("id") long id,@RequestBody updateOrdersDto p)
	{
		Response response=null;
		boolean b=auinter.updateOrders(id, p);
		if(b==false)
			response=new Response(true,"Something Went Wrong");
		else
			response=new Response(false,"Updation Done");
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}

	@PostMapping("/countordersby")
	public ResponseEntity<Response> getCountBySpecificDate(@RequestBody DateDto a)
	{
		System.out.println(a.getDatetwo());
		Date d1=Date.valueOf(a.getDateone());
		Date d2=Date.valueOf(a.getDatetwo());
		return new ResponseEntity<Response>(auinter.getOrdersCountByDay(d1,d2),HttpStatus.OK);
	}
}
