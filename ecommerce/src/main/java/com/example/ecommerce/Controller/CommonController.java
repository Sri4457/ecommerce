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

@CrossOrigin()
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
	
	@GetMapping("/product/viewallcategories")
	public ResponseEntity<List<String>> getAllCategories(){
		return new ResponseEntity<>(cser.getAllCategories(),HttpStatus.OK);
	}
	
	@GetMapping("/product/getproductsbycategory/{category}")
	public ResponseEntity<List<Products>> getAllProductsByCategory(@PathVariable String category)
	{
		return new ResponseEntity<List<Products>>(cser.getAllProductsByCategory(category),HttpStatus.OK);
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
	public ResponseEntity<List<Products>> getProductsByName(@PathVariable("name") String name)
	{
		return new ResponseEntity<>(cser.getProductsBySearch(name),HttpStatus.OK);
	}
	
	@GetMapping("/product/sortbycategory/{category}/{path}")
	public ResponseEntity<List<Products>> getProductsByCategoryByReqOrder(@PathVariable("category") String category,@PathVariable("path") String path)
	{
		
		return new ResponseEntity<List<Products>>(cser.getProductsByCategoryByReqOrder(category, path),HttpStatus.OK);
	}
	
	@GetMapping("/product/searchNameByCategory/{category}/{name}")
	public ResponseEntity<List<Products>> getProductsByCategory(@PathVariable("category") String category,@PathVariable("name") String name)
	{
		return new ResponseEntity<>(cser.getProductsByCategoryBySearch(category, name),HttpStatus.OK);
	}
}
