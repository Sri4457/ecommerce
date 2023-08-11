package com.example.ecommerce.Service.Products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.Dto.ProductDto;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Repository.ProductRepo;

@Service
public class ProductServiceImpl implements ProductInterface{

	@Autowired
	ProductRepo prepo;
	
	
	@Override
	public boolean addProduct(Products p) {
		boolean b=false;
		try {
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

	
}
