package com.example.ecommerce.Service.Admin;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Repository.ProductRepo;

@Service
public class AdminProductImpl implements AdminProductInterface{

	@Autowired
	ProductRepo prepo;
	
	
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
	public boolean deleteProductByQty(int q) {
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
	public Response deleteProductById(long id) {
		Response response=null;
		try
		{
			prepo.delete(prepo.findById(id).get());
			response=new Response(false,"Product deleted Successfully");
		}
		catch(Exception e)
		{
			response=new Response(true,e.getMessage());
		}
		return response;
	}

	@Override
	public Products getProductById(long id) {
		return prepo.findById(id).get();
	}

	@Override
	public boolean updateProduct(Products p) {
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
}
