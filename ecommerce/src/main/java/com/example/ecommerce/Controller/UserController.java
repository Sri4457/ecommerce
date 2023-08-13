package com.example.ecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.Dto.CommonDto;
import com.example.ecommerce.Dto.ProductDto;
import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Service.Products.ProductInterface;
import com.example.ecommerce.Service.User.UserInterface;

@CrossOrigin(origins="http://localhost:3000/")
@RestController()
@RequestMapping("/user") 
public class UserController {

	@Autowired
	UserInterface uinter;
	
	@Autowired
	ProductInterface pinter;
	
	
	@PostMapping("/submitcart/{username}")
	public ResponseEntity<List<Response>> submitCart(@PathVariable("username") String uname,@RequestBody List<ProductDto> prods)
	{
		return new ResponseEntity<List<Response>>(uinter.submitProducts(uname, prods),HttpStatus.OK);
	}
	@GetMapping("/orders/vieworders/{uname}")
	public ResponseEntity<List<CommonDto>> viewOrders(@PathVariable String uname)
	{
		List<CommonDto> list=uinter.getOrderByUname(uname);
		return new ResponseEntity<List<CommonDto>>(list,HttpStatus.OK);
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
