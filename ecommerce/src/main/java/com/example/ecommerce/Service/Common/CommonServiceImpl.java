package com.example.ecommerce.Service.Common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.Dto.Response;
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
	public Response login(Users u) {
		Response res=null;
		try 
		{
			Users user=urepo.findByUsername(u.getUsername());
			if(user==null)
			{
				res=new Response(true,"User not exit with the Username you provided");
			}
			else
			{
				String pass=user.getPassword();
				if(pass.equals(u.getPassword()))
				{
					res=new Response(false,"Login Successfull");
				}
				else
				{
					res=new Response(true,"Password you provided is wrong");
				}
			}
		}
		catch(Exception e)
		{
			res=new Response(true,"Sonething Went Wrong");
			System.out.println(e);
		}
		return res;
	}
	
	
	
}
