package com.example.ecommerce.Service.Products;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ecommerce.Dto.ProductDto;
import com.example.ecommerce.Dto.Response;
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
	
	@Override
	public boolean addProduct(Products p) {
		boolean b=false;
		try {
			p.setDate(java.sql.Date.valueOf(LocalDate.now()));
			prepo.save(p);
			b=true;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return b;
	}

	@Override
	public boolean deleteProduct(int q) {
		boolean b=false;
		List<Products> list=prepo.findByCount(q);
		try
		{
			for(Products p:list)
			{
				prepo.delete(p);
				b=true;
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return b;
	}

	@Override
	public List<Products> viewAll() {
		return prepo.findAll();
	}

	@Override
	public Products viewByName(String name) {
		return prepo.findByName(name);
	}

	@Override
	public boolean updateProduct(ProductDto p) {
		boolean b=false;
		try {
			Products prod=prepo.findByName(p.getName());
			if(p.getCount()!=0)
			prod.setCount(prod.getCount()+p.getCount());
			if(p.getPrice()!=0)
			prod.setPrice(p.getPrice());
			prepo.save(prod);
			b=true;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return b;
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

	
}
