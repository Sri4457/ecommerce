package com.example.ecommerce.Service.Common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ecommerce.Dto.LoginResponse;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Repository.ProductRepo;
import com.example.ecommerce.Repository.UserRepo;

@Service
public class CommonServiceImpl implements CommonInterface{

	@Autowired
	ProductRepo prepo;
	
	@Autowired
	UserRepo urepo;
	
	
	@Override
	public List<Products> viewAll() {
		return prepo.findAll();
	}

	

	

	@Override
	public List<Products> getProductsByReqOrder(String path) {
		List<Products> list=null;
		try
		{
			String[] s=path.split("-");
			if(s[1].equalsIgnoreCase("asc"))
				list=prepo.findAll(Sort.by(Sort.Direction.ASC, s[0]));
			else if(s[1].equalsIgnoreCase("desc"))
				list=prepo.findAll(Sort.by(Sort.Direction.DESC, s[0]));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return list;
	}

	@Override
	public List<Products> getProductsBySearch(String n) {
		return prepo.getByName("%"+n+"%");
	}
	
	@Override
	public LoginResponse login(Users u) {
		try 
		{
			Users user=urepo.findByUsername(u.getUsername());
			if(user==null)
			{
				return new LoginResponse(true,"No User Exists",0);
			}
			else
			{
				String pass=user.getPassword();
				if(pass.equals(u.getPassword()))
				{
					return new LoginResponse(false,"Login Successful",user.getId());
				}
				else
				{
					return new LoginResponse(true,"Password Entered Wrong",0);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			return new LoginResponse(true,"Something Went Wrong",0);
		}
	}

	@Override
	public Products getByPId(long id) {
		
		return prepo.findById(id).get();
	}

	@Override
	public List<String> getAllCategories() {
		
		return prepo.getAllCategories();
	}

	@Override
	public List<Products> getAllProductsByCategory(String cat) {
		return prepo.findByCategory(cat);
	}

	@Override
	public List<Products> getProductsByCategoryByReqOrder(String category, String path) {
		List<Products> list=null;
		try
		{
			System.out.println(category+" "+path);
			String[] s=path.split("-");
			if(s[1].equalsIgnoreCase("asc"))
				list=prepo.findProductsByCatByAscOrder(category,s[0]);
			else if(s[1].equalsIgnoreCase("desc"))
				list=prepo.findProductsByCatByDescOrder(category,s[0]);
			for(int i=0;i<list.size();i++)
			System.out.println(list.get(i).getPrice());
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return list;
	}

	@Override
	public List<Products> getProductsByCategoryBySearch(String category, String n) {
		System.out.println(category+" "+n);
		return prepo.getByNameByCategory(category, "%"+n+"%");
	}
	
	
	
}
