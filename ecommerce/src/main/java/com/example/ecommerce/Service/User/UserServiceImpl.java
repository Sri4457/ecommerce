package com.example.ecommerce.Service.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.Dto.OrderDto;
import com.example.ecommerce.Dto.ProductDto;
import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Orders;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Repository.ProductRepo;
import com.example.ecommerce.Repository.UserRepo;
import com.example.ecommerce.Service.Admin.EmailService;

@Service
public class UserServiceImpl implements UserInterface {

	@Autowired
	UserRepo urepo;
	
	@Autowired
	ProductRepo prepo;
	
	@Autowired
	EmailService es;
	
	@Override
	public boolean register(Users u) {
		boolean b=false;
		try
		{
			urepo.save(u);
			String message="Thank you for choosing our ecommerce website. Thanks for Registering";
			es.notifyUser(u.getEmail(), message);
			b=true;
		}
		catch(Exception e)
		{
			System.out.println(e);
			
		}
		return b;
	}

	@Override
	public List<Products> viewAllProducts() {
		return prepo.findAll();
		
	}

	@Override
	public Products viewProductByName(String name) {
		// TODO Auto-generated method stub
		return prepo.findByName(name);
	}

	@Override
	public boolean UpdateProfile(Users u) {
		Users user=urepo.findByUsername(u.getUsername());
		if(u.getEmail()!=null)
			user.setEmail(u.getEmail());
		if(u.getOrders()!=null)
			user.setOrders(u.getOrders());
		if(u.getPassword()!=null)
			user.setPassword(u.getPassword());
		boolean b=false;
		try
		{
			urepo.save(u);
			b=true;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return b;
	}

	@Override
	public Users getByUname(String uname) {
		
		return urepo.findByUsername(uname);
	}

	@Override
	public List<Response> submitProducts(Users s, List<ProductDto> list) {
		List<Response> res=new ArrayList<>();
		try
		{
			for(int i=0;i<list.size();i++)
			{
				Orders o=new Orders();
				o.setStatus("ordered");
				o.setQuantity(list.get(i).getCount());
				o.setProduct_id(prepo.findByName(list.get(i).getName()).getId());
				o.setTime(java.sql.Date.valueOf(LocalDate.now()));
				List<Orders> l=s.getOrders();
				l.add(o);
				s.setOrders(l);
				if(checkProducts(list.get(i)))
				{
					Response r=new Response(false,"The Product with name "+list.get(i).getName()+" is ordered");
					updateProducts(list.get(i));
					urepo.save(s);
					res.add(r);
				}
				else
				{
					Response r=new Response(true,"The Product with name "+list.get(i).getName()+" is not ordered as the products are not avaliable to your provided Quantity");
					res.add(r);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return res;
	}

	private boolean checkProducts(ProductDto pdto)
	{
		Products p=prepo.findByName(pdto.getName());
		if(p.getCount()>=pdto.getCount())
			return true;
		else
			return false;
	}
	private void updateProducts(ProductDto list) {
		Products p=prepo.findByName(list.getName());
		p.setCount((p.getCount())-list.getCount());
		prepo.save(p);
	}

	@Override
	public List<OrderDto> getOrderByUname(String uname) {
		List<Orders> list=urepo.findByUsername(uname).getOrders();
		List<OrderDto> o=new ArrayList<>();
		for(int i=0;i<list.size();i++)
		{
			OrderDto obj=new OrderDto();
			obj.setPname(prepo.findById(list.get(i).getProduct_id()).get().getName());
			obj.setQty(list.get(i).getQuantity());
			obj.setPrice(prepo.findById(list.get(i).getProduct_id()).get().getPrice());
			obj.setStatus(list.get(i).getStatus());
			obj.setUname(uname);
			o.add(obj);
		}
		return o;
	}

}
